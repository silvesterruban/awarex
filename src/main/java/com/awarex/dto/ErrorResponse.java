package com.awarex.dto;

import java.util.List;

public class ErrorResponse {
    private String developerErrorMessage;
    private List<String> errors;
    
    // Constructors
    public ErrorResponse() {}
    
    public ErrorResponse(String developerErrorMessage, List<String> errors) {
        this.developerErrorMessage = developerErrorMessage;
        this.errors = errors;
    }
    
    // Getters and Setters
    public String getDeveloperErrorMessage() { return developerErrorMessage; }
    public void setDeveloperErrorMessage(String developerErrorMessage) { 
        this.developerErrorMessage = developerErrorMessage; 
    }
    
    public List<String> getErrors() { return errors; }
    public void setErrors(List<String> errors) { this.errors = errors; }
}