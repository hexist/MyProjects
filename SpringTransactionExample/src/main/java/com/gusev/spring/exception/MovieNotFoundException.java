package com.gusev.spring.exception;

public class MovieNotFoundException extends Exception {

    private String title;

    public String getTitle() {
        return title;
    }

    public MovieNotFoundException(String message, String title){
        super(message);
        this.title = title;
    }

}
