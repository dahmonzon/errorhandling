package com.example.errorhandling;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@Data
@ToString
@AllArgsConstructor
public class Error {

    private int status;
    private String message;
}
