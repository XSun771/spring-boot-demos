package com.example.springbootdemo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.logging.LogLevel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.print.DocFlavor;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@SpringBootApplication
@RestController
@Slf4j
public class SpringBootDemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootDemoApplication.class, args);
    }

    @RequestMapping("/")
    public String index() {
        return LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME);
    }

    @Bean
    @Profile({"!server"})
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .permitAll();
        return http.build();
    }

    @Override
    public void run(String... args) throws Exception {
    }
}
