package Exceptions;


public class DuplicateException extends Throwable {
    private String message = " already exists";
    private String duplicate;

    public DuplicateException(String duplicate) {
        this.duplicate = duplicate;
    }

    public String getMessage() {
        return (duplicate + message);
    }
}
