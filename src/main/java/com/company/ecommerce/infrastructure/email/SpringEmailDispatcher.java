package com.company.ecommerce.infrastructure.email;

import com.company.ecommerce.domain.email.EmailDispatcher;
import com.company.ecommerce.domain.email.EmailMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class SpringEmailDispatcher implements EmailDispatcher {

    private static final String NO_REPLAY_ECOMMERCE_EMAIL = "no-replay@ecommerce.com";

    @Autowired
    private JavaMailSender sender;

    @Override
    public void sendEmailMessage(EmailMessage emailMessage) throws MessagingException {
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setFrom(NO_REPLAY_ECOMMERCE_EMAIL);
        helper.setTo(emailMessage.to());
        helper.setText(emailMessage.body());
        helper.setSubject(emailMessage.subject());
        sender.send(message);
    }
}
