package com.phai.customerservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

/**
 * Standard application exception that includes HTTP status information
 * for proper error response generation.
 */
@Getter
public class AppException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    
    private final HttpStatus httpStatus;
    private final String errorCode;
    
    /**
     * Creates a new AppException with BAD_REQUEST status
     * @param message the error message
     */
    public AppException(String message) {
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
        this.errorCode = null;
    }
    
    /**
     * Creates a new AppException with the specified status
     * @param httpStatus the HTTP status
     * @param message the error message
     */
    public AppException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = null;
    }
    
    /**
     * Creates a new AppException with a cause
     * @param httpStatus the HTTP status
     * @param message the error message
     * @param cause the cause
     */
    public AppException(HttpStatus httpStatus, String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
        this.errorCode = null;
    }
    
    /**
     * Creates a new AppException with an error code
     * @param httpStatus the HTTP status
     * @param errorCode the error code
     * @param message the error message
     */
    public AppException(HttpStatus httpStatus, String errorCode, String message) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }
    
    /**
     * Creates a new AppException with an error code and cause
     * @param httpStatus the HTTP status
     * @param errorCode the error code
     * @param message the error message
     * @param cause the cause
     */
    public AppException(HttpStatus httpStatus, String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }
    
    /**
     * Gets the error message
     * @return the error message
     */
    @Override
    public String getMessage() {
        return super.getMessage();
    }
    
    /**
     * String representation of the exception
     */
    @Override
    public String toString() {
        return "AppException{" +
                "httpStatus=" + httpStatus +
                ", errorCode='" + errorCode + '\'' +
                ", message='" + getMessage() + '\'' +
                '}';
    }
}
