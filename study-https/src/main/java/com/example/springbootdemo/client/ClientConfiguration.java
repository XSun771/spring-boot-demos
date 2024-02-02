package com.example.springbootdemo.client;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.tomcat.util.security.KeyStoreUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.net.http.HttpClient;
import java.security.KeyStore;
import java.security.Security;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author sun-yixin
 */
@Profile("client")
@Configuration
@Slf4j
public class ClientConfiguration {

    @Bean
    @Qualifier("clientXavier")
    @ConfigurationProperties("rest.ssl.client.xavier")
    public ClientKeyStoreInfo clientXavier() {
        return new ClientKeyStoreInfo();
    }

    @Bean
    @Qualifier("clientBob")
    @ConfigurationProperties("rest.ssl.client.bob")
    public ClientKeyStoreInfo clientBob() {
        return new ClientKeyStoreInfo();
    }

    @Bean
    public RestTemplate restTemplate() throws Exception {
        ClientKeyStoreInfo client = clientBob();
        log.info("client xavier: {}", client);

        final SSLContext sslContext = new SSLContextBuilder()
                //.setKeyStoreType("jks")
                .setKeyStoreType("pkcs12")
                .loadKeyMaterial(client.getKeyStoreFile().getURL(),
                        client.getKeyStorePwd().toCharArray(),
                        client.getPrivateKeyPwd().toCharArray())
                .loadTrustMaterial((TrustStrategy) (chain, authType) -> true)
                .build();

        SSLConnectionSocketFactory sslConFactory = new SSLConnectionSocketFactory(sslContext);

        final CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslConFactory)
                .build();

        ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);

        RestTemplate restTemplate = new RestTemplate(requestFactory);

        return restTemplate;
    }
}
