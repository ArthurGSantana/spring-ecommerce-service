package com.ags.spring_ecommerce_service.filters;

import io.grpc.Metadata;
import io.grpc.ServerCall;
import io.grpc.ServerCallHandler;
import io.grpc.ServerInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.grpc.server.GlobalServerInterceptor;
import org.springframework.stereotype.Component;

@GlobalServerInterceptor
@Component
@Slf4j
public class GrpcAuthenticationFilter implements ServerInterceptor {
  @Override
  public <ReqT, RespT> ServerCall.Listener<ReqT> interceptCall(ServerCall<ReqT, RespT> serverCall, Metadata metadata, ServerCallHandler<ReqT, RespT> serverCallHandler) {
    String token = metadata.get(Metadata.Key.of("authorization", Metadata.ASCII_STRING_MARSHALLER));
    log.info("token para avaliação {}", token);
    return serverCallHandler.startCall(serverCall, metadata);
  }
}
