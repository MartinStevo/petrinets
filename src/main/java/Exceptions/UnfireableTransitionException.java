package Exceptions;

public class UnfireableTransitionException extends Throwable {

    private String message;

    public UnfireableTransitionException(String name) {
        this.message = "transition " + name + " is not fireable";
    }

    @Override
    public String getMessage() {
        return message;
    }
}
