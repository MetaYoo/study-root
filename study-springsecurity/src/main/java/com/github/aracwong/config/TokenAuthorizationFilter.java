package com.github.aracwong.config;

import com.github.aracwong.utils.JwtTokenUtil;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TokenAuthorizationFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = ((HttpServletRequest) request).getHeader("token");
        if (token == null) {
            chain.doFilter(request, response);
            return;
        }
        // 如果请求头中有token，则进行解析，并且设置认证信息
        SecurityContextHolder.getContext().setAuthentication(getAuthentication(token));
        chain.doFilter(request, response);
    }

    private Authentication getAuthentication(String token) {
        JwtTokenUtil jwtTokenUtil = new JwtTokenUtil();
        Claims claims = jwtTokenUtil.getAllClaimsFromToken(token);
        String username = claims.get("username", String.class);
        List authorizeStrList = claims.get("authorizes", List.class);
        Set<GrantedAuthority> authorizes = new HashSet<>();
        if (!CollectionUtils.isEmpty(authorizeStrList)) {
            for (Object o : authorizeStrList) {
                if (null != o) {
                    authorizes.add(new SimpleGrantedAuthority((String) o));
                }
            }
        }

        if (username != null) {
            return new UsernamePasswordAuthenticationToken(username, null, authorizes);
        }
        return null;
    }
}
