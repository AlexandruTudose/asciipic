package com.asciipic.services;

public interface EmailService {
    void send(String to, String subject, String body);
}
