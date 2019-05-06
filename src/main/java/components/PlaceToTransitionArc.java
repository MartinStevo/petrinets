package components;

import Exceptions.WrongValueException;

public class PlaceToTransitionArc extends Arc {

    private int value;
    private Place place;
    private Transition transition;
    private long id;

    public PlaceToTransitionArc(Long id, Place place, Transition transition, int value) {
        this.id = id;
        this.value = value;
        this.place = place;
        this.transition = transition;
    }

    public boolean checkArcValue() throws WrongValueException {
        if (this.value > 0) return true;
        throw new WrongValueException(1);
    }

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public Place getPlace() {
        return place;
    }

    @Override
    public void setPlace(Place place) {
        this.place = place;
    }

    @Override
    public Transition getTransition() {
        return transition;
    }

    @Override
    public void setTransition(Transition transition) {
        this.transition = transition;
    }
}
