package Exceptions;

public class WrongValueException extends Throwable {
    private String message = " value must be at least ";
    private String object;
    private int value;

    public WrongValueException(int value) {
       if (value == 1) object = "arc";
       if (value == 0) object = "place";
       this.value = value;
    }

    @Override
    public String getMessage() {
        return (object + message + value);
    }
}
