package com.example.springbootdemo.server;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @author sun-yixin
 */
@Configuration
@Profile("server")
@Slf4j
public class ServerConfiguration {

    @Value("${server.ssl.valid-commonname}")
    private List<String> validCommonNameList;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .x509()
                .subjectPrincipalRegex("CN=(.*?)(?:,|$)")
                .userDetailsService(userDetailsService());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) {
                log.info("common name = {}", username);
                return new ValidCert(username, CollectionUtils.contains(validCommonNameList.listIterator(), username));
            }
        };
    }
}
