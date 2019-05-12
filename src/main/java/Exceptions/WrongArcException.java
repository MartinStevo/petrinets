package Exceptions;

public class WrongArcException extends Exception {

    public WrongArcException() {}
    private String message = "Can not add arc matching of same instances";

    public String getMessage() {
        return message;
    }

}
