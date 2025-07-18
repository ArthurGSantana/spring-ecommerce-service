package com.ags.spring_ecommerce_service.interceptor;

import com.ags.spring_ecommerce_service.config.security.JwtConfig;
import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.grpc.server.GlobalServerInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@GlobalServerInterceptor
@Component
@RequiredArgsConstructor
@Slf4j
public class GrpcAuthenticationInterceptor implements ServerInterceptor {
  private final JwtConfig jwtConfig;

  @Override
  public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(
      ServerCall<ReqT, RespT> serverCall,
      Metadata metadata,
      ServerCallHandler<ReqT, RespT> serverCallHandler) {
    try {
      String token =
          metadata.get(Metadata.Key.of("authorization", Metadata.ASCII_STRING_MARSHALLER));
      SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtConfig.getSecret()));

      var claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();

      String username = claims.getSubject();
      String tokenType = claims.get("tokenType", String.class);

      // Verificar se é um token de acesso (não de refresh)
      if (!"access".equals(tokenType)) {
        throw new JwtException("Token inválido");
      }

      @SuppressWarnings("unchecked")
      List<String> roles = (List<String>) claims.getOrDefault("roles", Collections.emptyList());

      var authorities =
          roles.stream()
              .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
              .collect(Collectors.toList());

      // Criar o objeto Authentication
      var authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);

      // Definir a autenticação no contexto de segurança
      SecurityContextHolder.getContext().setAuthentication(authentication);

      return serverCallHandler.startCall(serverCall, metadata);

    } catch (Exception e) {
      serverCall.close(
          io.grpc.Status.UNAUTHENTICATED.withDescription("Invalid token").withCause(e),
          new Metadata());
      return new ServerCall.Listener<ReqT>() {};
    }
  }
}
