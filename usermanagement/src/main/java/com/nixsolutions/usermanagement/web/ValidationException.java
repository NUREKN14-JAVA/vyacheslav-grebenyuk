package com.nixsolutions.usermanagement.web;

/**
 * @author mak
 */
public class ValidationException extends Exception {
    private static final long serialVersionUID = -2167686536311212458L;

    /**
     * 
     */
    public ValidationException() {
        super();
    }

    /**
     * @param message
     */
    public ValidationException(String message) {
        super(message);
    }

    /**
     * @param message
     * @param cause
     */
    public ValidationException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @param cause
     */
    public ValidationException(Throwable cause) {
        super(cause);
    }

}
