package com.vetrix.gest_API.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MailRequest {
    private String mailTo;
    private String subject;
    private String body;
}
