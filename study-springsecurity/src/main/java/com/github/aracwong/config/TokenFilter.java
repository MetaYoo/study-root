package com.github.aracwong.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.util.JSONWrappedObject;
import com.github.aracwong.utils.JwtTokenUtil;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Component
public class TokenFilter extends UsernamePasswordAuthenticationFilter {

    public TokenFilter(AuthenticationManager authenticationManager) {
        this.setAuthenticationManager(authenticationManager);
        this.setFilterProcessesUrl("/auth/tokenLogin");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            ServletInputStream inputStream = request.getInputStream();
            Map jsonNode = new ObjectMapper().readValue(inputStream, Map.class);
            String username = (String) jsonNode.get("username");
            String password = (String) jsonNode.get("password");
            User user = new User(username, password, Collections.emptyList());
            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
            return this.getAuthenticationManager().authenticate(token);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        UserDetails userDetails = (UserDetails)authResult.getPrincipal();
        PrintWriter writer = response.getWriter();
        String jwtToken = new JwtTokenUtil().generateToken(userDetails);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("msg", "登录成功");
        result.put("token", jwtToken);
        writer.write(new ObjectMapper().writeValueAsString(result));
        writer.flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed)
            throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> result = new HashMap<>();
        result.put("code", 403);
        result.put("msg", "登录失败");
        PrintWriter writer = response.getWriter();
        writer.write(new ObjectMapper().writeValueAsString(result));
        writer.flush();
    }
}
