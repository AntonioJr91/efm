package afsj.efm.auth.security.token;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "revoked_tokens")
public class RevokedToken {

   @Id
   @Column(nullable = false, updatable = false)
   private String tokenId;

   @Column(nullable = false)
   private Instant expiresAt;

   protected RevokedToken() {
   }

   public RevokedToken(String tokenId, Instant expiresAt) {
      this.tokenId = tokenId;
      this.expiresAt = expiresAt;
   }

   public String getTokenId() {
      return tokenId;
   }

   public Instant getExpiresAt() {
      return expiresAt;
   }
}
