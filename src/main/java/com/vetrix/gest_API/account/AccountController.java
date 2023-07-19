package com.vetrix.gest_API.account;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/account")
@Tag(name = "Account")
@CrossOrigin("*")
public class AccountController {
    @Autowired
    private AccountRepository repository;
    @Autowired
    private ServiceMail serviceMail;

    @GetMapping(path = "/{login}")
    public Account findByLogin(@PathVariable String login){
        return repository.findByLogin(login);
    }

    @PostMapping(path = "/mail")
    public void sendMail(@RequestBody MailRequest mail){
        serviceMail.sendSimpleEmail(mail.getMailTo(), mail.getSubject(), mail.getBody());
    }
}
