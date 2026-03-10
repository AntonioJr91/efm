package afsj.efm.auth.security.config;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Configuration
@EnableWebSecurity
public class WebConfig {

   @Value("${jwt.rsa.pri}")
   private RSAPrivateKey privateKey;

   @Value("${jwt.rsa.pub}")
   private RSAPublicKey publicKey;

   @Bean
   public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http
              .csrf(csrf -> csrf.disable())
              .cors(cors -> {
              })
              .headers(headers ->
                      headers.frameOptions(frame -> frame.disable()))
              .sessionManagement(session ->
                      session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
              .authorizeHttpRequests((authorize) -> authorize
                      .requestMatchers("/h2-console/**", "/auth/login").permitAll()
                      .requestMatchers(HttpMethod.GET).permitAll()
                      .anyRequest().permitAll()
              );
//              .oauth2ResourceServer(oauth2 ->
//                      oauth2.jwt(jwt ->
//                              jwt.jwtAuthenticationConverter(jwtAuthenticationConverter())));
      return http.build();
   }

   @Bean
   public PasswordEncoder passwordEncoder() {
      return new BCryptPasswordEncoder();
   }

   @Bean
   public AuthenticationManager authenticationManager(
           AuthenticationConfiguration configuration
   ) throws Exception {
      return configuration.getAuthenticationManager();
   }

   @Bean
   public JwtEncoder jwtEncoder() {
      JWK jwk = new RSAKey.Builder(publicKey).privateKey(privateKey).build();
      JWKSource<SecurityContext> jwks = new ImmutableJWKSet<>(new JWKSet(jwk));
      return new NimbusJwtEncoder(jwks);
   }

   @Bean
   public JwtDecoder jwtDecoder() {
      return NimbusJwtDecoder.withPublicKey(publicKey).build();
   }

   @Bean
   public JwtAuthenticationConverter jwtAuthenticationConverter() {

      JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter =
              new JwtGrantedAuthoritiesConverter();

      grantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
      grantedAuthoritiesConverter.setAuthorityPrefix("");

      JwtAuthenticationConverter jwtAuthenticationConverter =
              new JwtAuthenticationConverter();

      jwtAuthenticationConverter
              .setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);

      return jwtAuthenticationConverter;
   }
}
