package fr.karmakat.wiflic.exceptions;

public class Code403Exception extends RuntimeException {
    public Code403Exception(String errorMessage){
        super(errorMessage);
    }
    public Code403Exception(String errorMessage, Throwable err){
        super(errorMessage, err);
    }
}
