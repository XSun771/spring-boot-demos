package com.example.springbootdemo.client;

import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author sun-yixin
 */
@Profile("client")
@RestController
@Slf4j
@RequestMapping("/client")
public class ClientController {

    private RestTemplate restTemplate;

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @RequestMapping("/")
    public String client() {
        final ResponseEntity<String> responseEntity = restTemplate.getForEntity("https://192.168.19.128:8443/", String.class);
        return responseEntity.getBody();
    }
}
