package Exceptions;

public class WrongInputException extends Exception {
    public WrongInputException() {}
    private String message = "this is not valid number of transition";

    public String getMessage() {
        return message;
    }
}
