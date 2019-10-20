package com.example.errorhandling;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class CustumException extends RuntimeException {


    public  CustumException(){

        super("My own exception");
    }
}
