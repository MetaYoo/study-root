package com.github.aracwong;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MyProperties.class)
public class MyConfiguration {

    @Bean
    public User getUser(MyProperties myProperties) {
        User user = new User();
        user.setId(myProperties.getId());
        user.setName(myProperties.getName());
        return user;
    }
}
