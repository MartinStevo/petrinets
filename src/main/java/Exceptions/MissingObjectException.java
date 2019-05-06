package Exceptions;

public class MissingObjectException extends Throwable {

    private String message;

    public MissingObjectException(String s,  Long id) {
        this.message = s + " with id " + id + " doesn't exist";
    }

    @Override
    public String getMessage() {
        return message;
    }
}