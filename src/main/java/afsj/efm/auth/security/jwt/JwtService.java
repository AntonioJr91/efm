package afsj.efm.auth.security.jwt;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class JwtService {

   private final JwtEncoder jwtEncoder;

   public JwtService(JwtEncoder jwtEncoder) {
      this.jwtEncoder = jwtEncoder;
   }

   public String generateToken(Authentication authentication) {

      Instant now = Instant.now();
      long expiry = 60;

      List<String> authorities = authentication.getAuthorities()
              .stream()
              .map(GrantedAuthority::getAuthority)
              .toList();

      JwtClaimsSet claims = JwtClaimsSet.builder()
              .id(UUID.randomUUID().toString())
              .issuer("efm-auth")
              .issuedAt(now)
              .expiresAt(now.plusSeconds(expiry))
              .subject(authentication.getName())
              .claim("authorities", authorities)
              .build();

      return jwtEncoder.encode(
              JwtEncoderParameters.from(claims)
      ).getTokenValue();
   }

   private JwtEncoder getJwtEncoder() {
      return jwtEncoder;
   }
}
