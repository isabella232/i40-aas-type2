package com.sap.i40aas.datamanager.webService.security;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnableWebSecurity
public class JWTSecurity extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests(authorize -> authorize
        .anyRequest().authenticated()
      )
      .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
  }

  private AuthenticationEntryPoint authenticationEntryPoint() {
    return new AuthenticationEntryPoint() {
      // You can use a lambda here
      @Override
      public void commence(HttpServletRequest aRequest, HttpServletResponse aResponse,
                           AuthenticationException aAuthException) throws IOException, ServletException {
        aResponse.sendRedirect("https://admin-shell-io.com:50001/connect/authorize");
      }
    };
  }


//  @Bean
//  public JwtDecoder jwtDecoder() throws KeySourceException {
//
//    JWSKeySelector<SecurityContext> jwsKeySelector =
//      JWSAlgorithmFamilyJWSKeySelector.fromJWKSetURL("https://admin-shell-io.com:50001/.well-known/openid-configuration/jwks");
//
//    DefaultJWTProcessor<SecurityContext> jwtProcessor =
//      new DefaultJWTProcessor<>();
//    jwtProcessor.setJWSKeySelector(jwsKeySelector);
//
//    jwtProcessor.setJWSTypeVerifier(new DefaultJOSEObjectTypeVerifier(new JOSEObjectType("at+jwt")));
//
//    return new NimbusJwtDecoder(jwtProcessor);
//  }

}