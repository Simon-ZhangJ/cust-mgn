package com.simon.exception;

public class DataNotFoundException extends RuntimeException  {
    /**
     * 
     */
    private static final long serialVersionUID = 1574279017078885646L;
    
    
    public DataNotFoundException(String message) {
        super(message);
    }
}
