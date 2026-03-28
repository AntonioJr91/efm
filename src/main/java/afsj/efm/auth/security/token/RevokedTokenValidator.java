package afsj.efm.auth.security.token;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class RevokedTokenValidator implements OAuth2TokenValidator<Jwt> {

   private static final OAuth2Error REVOKED_TOKEN_ERROR =
           new OAuth2Error("invalid_token", "The token has been revoked", null);

   private final TokenRevocationService tokenRevocationService;

   public RevokedTokenValidator(TokenRevocationService tokenRevocationService) {
      this.tokenRevocationService = tokenRevocationService;
   }

   @Override
   public OAuth2TokenValidatorResult validate(Jwt token) {
      if (tokenRevocationService.isRevoked(token.getId())) {
         return OAuth2TokenValidatorResult.failure(REVOKED_TOKEN_ERROR);
      }

      return OAuth2TokenValidatorResult.success();
   }
}
