package com.lauro.resource.server.controller;

import com.lauro.resource.server.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EmailController {
    private final EmailService emailService;

    @GetMapping("/email/{id}")
    public ResponseEntity<Void> senEmail(@PathVariable String id, @AuthenticationPrincipal Jwt jwt) {
        //log.info("[TaskController] - Received request of user: {} to send e-mail", jwt.getSubject());
        this.emailService.sendEmail(id);
        return ResponseEntity.ok().build();
    }
}
