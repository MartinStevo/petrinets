package components;

import Exceptions.WrongValueException;

public class Place {

    private Long id;
    private String name;
    private int value;

    public Place(Long id, String name, int value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public Place() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean checkPlaceValue() throws WrongValueException {
        if (this.value > -1) return true;
        throw new WrongValueException(0);

    }


}
