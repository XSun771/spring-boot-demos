package com.example.springbootdemo.client;

import lombok.*;
import org.springframework.core.io.Resource;

/**
 * @author sun-yixin
 */
@Getter
@ToString
public class ClientKeyStoreInfo {

    private Resource keyStoreFile;
    private String keyStorePwd;
    private String privateKeyPwd;

    public void setKeyStoreFile(Resource keyStoreFile) {
        if (this.keyStoreFile == null) {
            this.keyStoreFile = keyStoreFile;
        }
    }

    public void setKeyStorePwd(String keyStorePwd) {
        if (this.keyStorePwd == null) {
            this.keyStorePwd = keyStorePwd;
        }
    }

    public void setPrivateKeyPwd(String privateKeyPwd) {
        if (this.privateKeyPwd == null) {
            this.privateKeyPwd = privateKeyPwd;
        }
    }
}
