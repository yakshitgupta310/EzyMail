package com.EzyMail.writer.controller;

import com.EzyMail.writer.dto.Request;
import com.EzyMail.writer.service.generateEmailService;
import com.EzyMail.writer.service.rephraseTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/ezymail/mail")
@CrossOrigin(origins = "*")
public class MailGeneratorController {

    @Autowired
    private generateEmailService emailService;

    @Autowired
    private rephraseTextService textService;


    @PostMapping("/generate")
    public ResponseEntity<?> generateEmail(@RequestBody Request request){
        String response = emailService.generateEmail(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/rephrase")
    public ResponseEntity<String> rephraseData(@RequestBody Request request){
        String rephrasedData = textService.rephraseText(request);
        return new ResponseEntity<>(rephrasedData, HttpStatus.OK);
    }
}
