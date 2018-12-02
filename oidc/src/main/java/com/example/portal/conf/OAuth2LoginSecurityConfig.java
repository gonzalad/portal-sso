package com.example.portal.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultOAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestRedirectFilter;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizationRequestResolver;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class OAuth2LoginSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private ClientRegistrationRepository clientRegistrationRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .oauth2Login();
                //.authorizationEndpoint().authorizationRequestResolver(authorizationRequestResolver());
    }

    /**
     * Needed to propagate additional queryParameters to Authorization server
     * <p>
     * See https://github.com/spring-projects/spring-security/issues/4911#issuecomment-405356324
     */
    private OAuth2AuthorizationRequestResolver authorizationRequestResolver() {
        DefaultOAuth2AuthorizationRequestResolver defaultAuthorizationRequestResolver = new DefaultOAuth2AuthorizationRequestResolver(clientRegistrationRepository,
                OAuth2AuthorizationRequestRedirectFilter.DEFAULT_AUTHORIZATION_REQUEST_BASE_URI);

        OAuth2AuthorizationRequestResolver resolver = new OAuth2AuthorizationRequestResolver() {
            @Override
            public OAuth2AuthorizationRequest resolve(HttpServletRequest request) {
                OAuth2AuthorizationRequest defaultAuthorizationRequest = defaultAuthorizationRequestResolver.resolve(request);
                return OAuth2AuthorizationRequest.from(defaultAuthorizationRequest)
                        .additionalParameters(additionalParams(request, defaultAuthorizationRequest))
                        .build();
            }

            @Override
            public OAuth2AuthorizationRequest resolve(HttpServletRequest request, String clientRegistrationId) {
                OAuth2AuthorizationRequest defaultAuthorizationRequest = defaultAuthorizationRequestResolver.resolve(request, clientRegistrationId);
                return OAuth2AuthorizationRequest.from(defaultAuthorizationRequest)
                        .additionalParameters(additionalParams(request, defaultAuthorizationRequest))
                        .build();
            }

            private Map<String, Object> additionalParams(HttpServletRequest request, OAuth2AuthorizationRequest defaultAuthorizationRequest) {
                Map<String, Object> additionalParameters = new HashMap<>(defaultAuthorizationRequest.getAdditionalParameters());
                additionalParameters.put("kc_idp_hint", computeIdpFromWhatever());
                return additionalParameters;
            }

            /**
             * Override this to use whatever logic you want
             */
            private String computeIdpFromWhatever() {
                return "google";
            }
        };
        return resolver;
    }
}