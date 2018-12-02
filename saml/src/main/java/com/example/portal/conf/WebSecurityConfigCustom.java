package com.example.portal.conf;

import com.github.ulisesbocchio.spring.boot.security.saml.bean.SAMLConfigurerBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * See https://github.com/ulisesbocchio/spring-boot-security-saml-samples/blob/master/spring-security-saml-sample/src/main/java/com/ulisesbocchio/security/saml/config/WebSecurityConfig.java
 */
//@Configuration
//@EnableSAMLSSO
public class WebSecurityConfigCustom extends WebSecurityConfigurerAdapter {

    /*
    @Autowired
    SAMLConfigurerBean saml;

    @Autowired
    AuthenticationManager authenticationManager;
    SAMLConfigurerBean saml() {
        return saml;
    }

    public AuthenticationManager authenticationManagerBean() throws Exception {
        return authenticationManager;
    }
    */

    @Bean
    SAMLConfigurerBean saml() {
        return new SAMLConfigurerBean();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        // @formatter:off
        http.httpBasic()
                .disable()
                .csrf()
                .disable()
                .anonymous()
                .and()
                .apply(saml())
                .and()
                //.http()
                .authorizeRequests()
                .requestMatchers(saml().endpointsMatcher())
                .permitAll()
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated();
        // @formatter:on
    }
}