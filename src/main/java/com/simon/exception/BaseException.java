package com.simon.exception;

/**
 * Base Exception
 * 
 * @author Simon
 *
 */
public class BaseException extends RuntimeException {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = -644111770428988498L;

    public BaseException(String msg) {
        super(msg);
    }
}
