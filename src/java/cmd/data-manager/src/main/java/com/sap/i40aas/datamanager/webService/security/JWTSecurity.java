package com.sap.i40aas.datamanager.webService.security;

import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnableWebSecurity
//lower order takes predecence when multiple auth mechanisms are used. In this case basic auth comes after
@Order(100)
public class JWTSecurity extends WebSecurityConfigurerAdapter {
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .authorizeRequests(authorize -> {
          try {
            authorize
              .mvcMatchers("/listaas").hasAuthority("userName_aorzelski@phoenixcontact.com")
              .mvcMatchers("/aasx").hasAuthority("userName_aorzelski@phoenixcontact.com")
              .anyRequest().permitAll()
              .and()
              .exceptionHandling()
              .authenticationEntryPoint(authenticationEntryPoint());
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      )
      .oauth2ResourceServer().jwt()
      .jwtAuthenticationConverter(jwtAuthenticationConverter());
  }

  private AuthenticationEntryPoint authenticationEntryPoint() {
    return new AuthenticationEntryPoint() {
      // You can use a lambda here
      @Override
      public void commence(HttpServletRequest aRequest, HttpServletResponse aResponse,
                           AuthenticationException aAuthException) throws IOException, ServletException {
        aResponse.setStatus(307);
      }
    };
  }

  //The jwt uses a custom claim (userName) that needs to be validated to check if the user is known
  @Bean
  public JwtAuthenticationConverter jwtAuthenticationConverter() {
    JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
    grantedAuthoritiesConverter.setAuthoritiesClaimName("userName");
    grantedAuthoritiesConverter.setAuthorityPrefix("userName_");

    JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
    jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
    return jwtAuthenticationConverter;
  }


//  @Component
//  public class FailureEvents {
//    @EventListener
//    public void onFailure(AuthenticationFailureEvent failure) {
//      if (badCredentials.getAuthentication() instanceof BearerTokenAuthenticationToken) {
//        // ... handle
//      }
//    }
//  }


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
