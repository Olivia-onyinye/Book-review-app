package com.holyvia.Bookreview.services;

import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface JavaMailService {
    ResponseEntity<String>sendMail(String receiverEmail, String subject, String text) throws IOException;

}
