package exceptions;

public class RegisterConflictException extends Exception {
    public RegisterConflictException (String message) {
        super(message);
    }
}
