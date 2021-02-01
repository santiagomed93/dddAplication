package com.company.ecommerce.domain.email;

import com.company.ecommerce.domain.DomainUtils;
import org.springframework.util.StringUtils;

public class EmailMessage {
    private String to;
    private String subject;
    private String body;

    public EmailMessage() {
        super();
    }

    public EmailMessage(String to, String subject, String body) {
        setTo(to);
        setSubject(subject);
        setBody(body);
    }

    private void setTo(String to) {
        if (StringUtils.isEmpty(to)) {
            throw new IllegalArgumentException("To is required");
        } else if (DomainUtils.validateEmail(to)) {
            this.to = to;
        } else {
            throw new IllegalArgumentException("To email is invalid");
        }

    }

    private void setSubject(String subject) {
        if (StringUtils.isEmpty(subject)) {
            throw new IllegalArgumentException("To is required");
        }
        this.subject = subject;
    }

    private void setBody(String body) {
        if (StringUtils.isEmpty(body)) {
            throw new IllegalArgumentException("To is required");
        }
        this.body = body;
    }

    public String to() {
        return to;
    }

    public String subject() {
        return subject;
    }

    public String body() {
        return body;
    }
}
