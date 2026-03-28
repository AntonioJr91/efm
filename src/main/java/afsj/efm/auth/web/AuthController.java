package afsj.efm.auth.web;

import afsj.efm.auth.application.dtos.LoginRequest;
import afsj.efm.auth.application.dtos.TokenResponse;
import afsj.efm.auth.security.jwt.JwtService;
import afsj.efm.auth.security.token.TokenRevocationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

   private final AuthenticationManager authenticationManager;
   private final JwtService jwtService;
   private final TokenRevocationService tokenRevocationService;

   public AuthController(
           AuthenticationManager authenticationManager,
           JwtService jwtService,
           TokenRevocationService tokenRevocationService
   ) {
      this.authenticationManager = authenticationManager;
      this.jwtService = jwtService;
      this.tokenRevocationService = tokenRevocationService;
   }

   @PostMapping("/login")
   public TokenResponse login(@RequestBody LoginRequest request) {
      Authentication authentication = authenticationManager
              .authenticate(
                      new UsernamePasswordAuthenticationToken(
                              request.username(),
                              request.password()
                      )
              );

      String token = jwtService.generateToken(authentication);

      return new TokenResponse(token);
   }

   @PostMapping("/logout")
   public ResponseEntity<Void> logout(@AuthenticationPrincipal Jwt jwt) {
      tokenRevocationService.revoke(jwt);
      return ResponseEntity.noContent().build();
   }
}
