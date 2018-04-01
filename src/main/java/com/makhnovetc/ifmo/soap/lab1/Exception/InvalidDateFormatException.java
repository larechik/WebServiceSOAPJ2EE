package com.makhnovetc.ifmo.soap.lab1.Exception;

import javax.xml.ws.WebFault;

@WebFault(faultBean = "com.makhnovetc.ifmo.soap.Exception.ExceptionBean")
public class InvalidDateFormatException extends Exception{
    private static String defMessage = "Invalid date format.";
    private final ExceptionBean fault;

    public InvalidDateFormatException(String message, ExceptionBean fault){
        super(message);
        this.fault = fault;
    }

    public InvalidDateFormatException(String message, ExceptionBean fault, Throwable cause){
        super(message);
        this.fault = fault;
    }

    public InvalidDateFormatException(ExceptionBean fault){
        super(defMessage);
        this.fault = fault;
    }

    public ExceptionBean getFaultInfo() {
        return fault;
    }
}
