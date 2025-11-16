package com.lauro.resource.server.service.email;

import com.lauro.resource.server.exception.EmailServiceException;
import com.lauro.resource.server.repository.TaskRepository;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
//@Slf4j
public class EmailService implements Email {

    private final JavaMailSenderImpl mailSender;
    private final TaskRepository taskRepository;

    @Override
    public void sendEmail(final String id) {
//log.info("[EmailService] - sendEmail");
        final var message = this.mailSender.createMimeMessage();
        final var task = this.taskRepository.findById(Long.parseLong(id))
                .orElseThrow(() -> new RuntimeException("Task not found with Id: " + id));
        try {
            message.setFrom(new InternetAddress("lauro.silveira@ymail.com"));
            message.setSubject("Test email from Sticky-note-app");
            message.setRecipients(Message.RecipientType.TO, "lauro.silveira@outlook.com.br");
            message.setText("Hello this is your friendly reminder." + "\n" + "You have the followings lists" + "\n "
                    + task.getTitle() + "\n\n" + "Items" + "\n" + task.getDescription());
           // log.info("[EmailService] - Sending Email...");
            this.mailSender.send(message);
//log.info("[EmailService] - Email Sent!");
        } catch (MessagingException ex) {
           // log.error("Failed to send email cause: {}", ex.getMessage());
            throw new EmailServiceException(ex.getMessage());
        }
    }
}
