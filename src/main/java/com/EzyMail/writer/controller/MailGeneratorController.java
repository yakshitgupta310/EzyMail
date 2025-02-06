package com.EzyMail.writer.controller;

import com.EzyMail.writer.dto.Request;
import com.EzyMail.writer.service.generateEmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ezymail/mail")
public class MailGeneratorController {

    @Autowired
    private generateEmailService service;

    @PostMapping("/generate")
    public ResponseEntity<?> generateEmail(@RequestBody Request request){
        String response = service.generateEmail(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
