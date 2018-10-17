package com.crud.kodillatasks.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Mail {
    private String mailTo;
    //private String toCc;
    private String subject;
    private String message;
}
