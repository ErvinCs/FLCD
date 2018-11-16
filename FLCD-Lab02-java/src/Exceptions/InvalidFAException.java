package Exceptions;

public class InvalidFAException extends Exception {
    public InvalidFAException() {
        super();
    }

    public InvalidFAException(String message) {
        super(message);
    }

    public InvalidFAException(String message, Throwable cause) {
        super(message, cause);
    }
}
