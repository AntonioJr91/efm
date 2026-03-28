package afsj.efm.auth.security.token;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class TokenRevocationService {

   private final RevokedTokenRepository revokedTokenRepository;

   public TokenRevocationService(RevokedTokenRepository revokedTokenRepository) {
      this.revokedTokenRepository = revokedTokenRepository;
   }

   @Transactional
   public void revoke(Jwt jwt) {
      clearExpiredTokens();

      String tokenId = jwt.getId();
      Instant expiresAt = jwt.getExpiresAt();

      if (tokenId == null || tokenId.isBlank() || expiresAt == null) {
         throw new IllegalArgumentException("TOKEN_WITHOUT_REQUIRED_CLAIMS");
      }

      revokedTokenRepository.save(new RevokedToken(tokenId, expiresAt));
   }

   @Transactional(readOnly = true)
   public boolean isRevoked(String tokenId) {
      if (tokenId == null || tokenId.isBlank()) {
         return true;
      }

      return revokedTokenRepository.existsById(tokenId);
   }

   @Transactional
   public void clearExpiredTokens() {
      revokedTokenRepository.deleteByExpiresAtBefore(Instant.now());
   }
}
