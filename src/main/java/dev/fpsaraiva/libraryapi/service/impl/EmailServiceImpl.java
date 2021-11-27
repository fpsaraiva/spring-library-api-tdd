package dev.fpsaraiva.libraryapi.service.impl;

import dev.fpsaraiva.libraryapi.service.EmailService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    @Override
    public void sendMails(String message, List<String> maisList) {

    }
}
