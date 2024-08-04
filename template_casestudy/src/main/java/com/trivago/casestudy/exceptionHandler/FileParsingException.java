package com.trivago.casestudy.exceptionHandler;

/**
 * This class is used to throw exception
 *
 */

public class FileParsingException extends Exception {

    public FileParsingException(String message) {
        super(message);
    }

    public FileParsingException(String message, Throwable cause) {
        super(message, cause);
    }

}
