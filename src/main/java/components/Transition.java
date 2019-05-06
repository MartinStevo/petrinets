package components;

import Exceptions.MissingObjectException;
import Exceptions.UnfireableTransitionException;

import java.util.ArrayList;
import java.util.List;

public class Transition {

    private Long id;
    private String name;
    public List<PlaceToTransitionArc> placeToTransitionArcList = new ArrayList();
    public List<TransitionToPlaceArc> transitionToPlaceArcList = new ArrayList();
    public List<ResetArc> resetArcList = new ArrayList();


    public Transition(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Transition() {
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

    public void fire() throws UnfireableTransitionException, MissingObjectException {

        if(!isValidTransition()) throw new UnfireableTransitionException(getName());
        setInputPlaces();
        resetPlace();
        setOutputPlaces();
    }

    public boolean isValidTransition() throws MissingObjectException, UnfireableTransitionException {
        for (PlaceToTransitionArc a : placeToTransitionArcList) {
            Place p = a.getPlace();
            if (a.getValue() > p.getValue()) {
                return false;
            }
        }
        return true;
    }

    private void setInputPlaces() {
        for (PlaceToTransitionArc a : placeToTransitionArcList) {
            Place p = a.getPlace();
            p.setValue(p.getValue() - a.getValue());
        }
    }

    private void resetPlace() throws MissingObjectException {
        for (ResetArc a : resetArcList) {
            Place p = a.getPlace();
            p.setValue(0);
        }
    }

    private void setOutputPlaces() throws MissingObjectException {
        for (TransitionToPlaceArc a : transitionToPlaceArcList) {
            Place p = a.getPlace();
            p.setValue(p.getValue() + a.getValue());
        }
    }
}
