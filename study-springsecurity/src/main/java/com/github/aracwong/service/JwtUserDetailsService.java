package com.github.aracwong.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("JwtUserDetailsService:" + username);
        List<GrantedAuthority> authorityList = new ArrayList<>();
        if ("user".equals(username)) {
            authorityList.add(new SimpleGrantedAuthority("USER"));
        } else if ("admin".equals(username)) {
            authorityList.add(new SimpleGrantedAuthority("ADMIN"));
        }
        return new User(username, "$2a$10$PwelInA/Ywr9VhN1o65WK.eqPQKhEKu3edUdeqVRsb2J62YIROlwO", authorityList);
    }
}
