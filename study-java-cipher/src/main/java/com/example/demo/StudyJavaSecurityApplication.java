package com.example.demo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.security.Provider;
import java.security.Security;
import java.util.Map;

@SpringBootApplication
public class StudyJavaSecurityApplication implements CommandLineRunner {

    private static final System.Logger log = System.getLogger(StudyJavaSecurityApplication.class.getName());

    public static void main(String[] args) {
        SpringApplication.run(StudyJavaSecurityApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        final Provider[] providers = Security.getProviders();
        for (Provider provider : providers) {
            log.log(System.Logger.Level.INFO,provider);
            for (Map.Entry<Object, Object> entry : provider.entrySet()) {
                log.log(System.Logger.Level.INFO,"\t" + entry.getKey() + ":\t" + entry.getValue());
            }
        }


    }
}
