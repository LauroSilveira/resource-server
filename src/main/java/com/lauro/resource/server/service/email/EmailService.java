package com.lauro.resource.server.service.email;

import com.lauro.resource.server.model.Task;
import com.lauro.resource.server.repository.TaskRepository;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;
import reactor.core.publisher.Mono;
import org.thymeleaf.context.Context;

import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

@Service
@Slf4j
public class EmailService implements Email {

    private final JavaMailSenderImpl mailSender;
    private final TaskRepository taskRepository;

    public EmailService(JavaMailSenderImpl mailSender, TaskRepository taskRepository) {
        this.mailSender = mailSender;
        this.taskRepository = taskRepository;
    }

    @Override
    public Mono<Boolean> sendEmail(String id) {
        log.info("[EmailService] - sendEmail");
        final var message = this.mailSender.createMimeMessage();
        Optional<Task> task = this.taskRepository.findById(Long.parseLong(id));
        try {
            if (task.isPresent()) {
                message.setFrom(new InternetAddress("lauro.silveira@ymail.com"));
                message.setSubject("Test email from Sticky-note-app");
                message.setRecipients(Message.RecipientType.TO, "lauro.silveira@outlook.com.br");
                message.setText("Hello this is your friendly reminder." + "\n" + "You have the followings lists" + "\n "
                        + task.get().getTitle() + "\n\n" + "Items" + "\n" + task.get().getDescription());
            }

            log.info("[EmailService] - Sending Email...");
            this.mailSender.send(message);
            log.info("[EmailService] - Email Sent!");
            return Mono.just(true);
        } catch (MessagingException ex) {
            log.error("Faild to send email cause: {}", ex.getMessage());
        }
        return Mono.just(false);
    }
}
