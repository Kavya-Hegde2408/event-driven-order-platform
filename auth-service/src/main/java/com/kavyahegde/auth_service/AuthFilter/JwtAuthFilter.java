//package com.kavyahegde.auth_service.AuthFilter;
//
//import com.kavyahegde.auth_service.utility.JwtUtil;
//import org.springframework.cloud.gateway.filter.GatewayFilter;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import reactor.core.publisher.Mono;
//
//@Component
//public class JwtAuthFilter implements GatewayFilter {
//
//    private final JwtUtil jwtUtil;
//
//    public JwtAuthFilter(JwtUtil jwtUtil) {
//        this.jwtUtil = jwtUtil;
//    }
//
//    @Override
//    public Mono<Void> filter(
//            ServerWebExchange exchange,
//            GatewayFilterChain chain) {
//
//        String path =
//                exchange.getRequest().getURI().getPath();
//
//        if (path.startsWith("/auth")) {
//            return chain.filter(exchange);
//        }
//
//        String header = exchange.getRequest()
//                .getHeaders()
//                .getFirst(HttpHeaders.AUTHORIZATION);
//
//        if (header == null || !header.startsWith("Bearer ")) {
//            exchange.getResponse()
//                    .setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//        }
//
//        try {
//            jwtUtil.validate(header.substring(7));
//        } catch (Exception e) {
//            exchange.getResponse()
//                    .setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//        }
//
//        return chain.filter(exchange);
//    }
//}
//
