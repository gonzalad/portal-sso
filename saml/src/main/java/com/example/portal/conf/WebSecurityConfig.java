package com.example.portal.conf;

import com.github.ulisesbocchio.spring.boot.security.saml.annotation.EnableSAMLSSO;
import org.springframework.context.annotation.Configuration;

/**
 * See https://github.com/ulisesbocchio/spring-boot-security-saml-samples/blob/master/spring-security-saml-sample/src/main/java/com/ulisesbocchio/security/saml/config/WebSecurityConfig.java
 */
@Configuration
@EnableSAMLSSO
public class WebSecurityConfig {
}