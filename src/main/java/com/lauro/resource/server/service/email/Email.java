package com.lauro.resource.server.service.email;

import org.springframework.http.ResponseEntity;
import reactor.core.publisher.Mono;

public interface Email {
    Mono<Boolean> sendEmail(String id);
}
