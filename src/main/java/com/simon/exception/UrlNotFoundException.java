package com.simon.exception;

public class UrlNotFoundException extends RuntimeException  {
    private static final long serialVersionUID = 7899313199880020393L;

    public UrlNotFoundException(String msg) {
        super(msg);
    }
}
