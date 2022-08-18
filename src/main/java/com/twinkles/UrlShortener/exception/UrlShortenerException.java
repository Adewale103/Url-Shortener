package com.twinkles.UrlShortener.exception;

public class UrlShortenerException extends Exception{

    public UrlShortenerException(String message){
        super(message);
    }
    public UrlShortenerException(String message, Throwable cause){
        super(message, cause);
    }
}
