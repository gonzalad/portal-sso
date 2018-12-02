package com.example.portal.conf;

import com.github.ulisesbocchio.spring.boot.security.saml.configurer.ServiceProviderConfigurerAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
public class MyServiceProviderConfig extends ServiceProviderConfigurerAdapter {

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/**")
                .authenticated();
    }
/*
    @Override
    public void configure(ServiceProviderBuilder serviceProvider) throws Exception {
        // @formatter:off
        serviceProvider
                .metadataGenerator()
                .entityId("localhost-demo")
                .and()
                .sso()
                .defaultSuccessURL("/home")
                .idpSelectionPageURL("/idpselection")
                .and()
                .logout()
                .defaultTargetURL("/afterlogout")
                .and()
                .metadataManager()
                .metadataLocations("classpath:/idp-ssocircle.xml")
                .refreshCheckInterval(0)
                .and()
                .extendedMetadata()
                .idpDiscoveryEnabled(true)
                .and()
                .keyManager()
                .privateKeyDERLocation("classpath:/localhost.key.der")
                .publicKeyPEMLocation("classpath:/localhost.cert")
                .and()
                .samlContextProviderLb()
                .scheme("http")
                .contextPath("/")
                .serverName("localhost")
                .serverPort(8080)
                .includeServerPortInRequestURL(true)
                .and();
        // @formatter:on

    }
    */
}