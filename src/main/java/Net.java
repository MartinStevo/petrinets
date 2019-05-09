import Exceptions.*;
import components.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Net {

    private List<Place> placeList = new ArrayList();
    private List<Transition> transitionList = new ArrayList();
    private Map<Long,Arc> arcMap = new HashMap<>();
    private Long id = 1L;

    public Map<Long, Arc> getArcMap() {
        return arcMap;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void addPlace(Long id, String placeName, int placeValue) throws DuplicateException, WrongValueException {
        Place p = new Place(id, placeName, placeValue);
        id++;
        p.checkPlaceValue();
        checkUniqueness(p);
    }

    public Place addPlace() {
        Place p = new Place();
        p.setId(id);
        p.setName("p" + id);
        id++;
        placeList.add(p);
        return p;
    }

    public void addTransition(Long id, String transitionName) throws DuplicateException {
        Transition t = new Transition(id, transitionName);
        id++;
        checkUniqueness(t);
    }

    public Transition addTransition() {
        Transition t = new Transition();
        t.setId(id);
        t.setName("t" + id);
        id++;
        transitionList.add(t);
        return t;
    }

    public void addPlaceToTransitionArc(Long id, Long place, Long transition, int value) throws MissingObjectException, WrongValueException {
        Transition t;
        Place p;
        if ((p = findPlace(place)) != null && (t = findTransition(transition)) != null) {
            PlaceToTransitionArc a = new PlaceToTransitionArc(id, p, t, value);
            a.checkArcValue();
            t.placeToTransitionArcList.add(a);
            arcMap.put(a.getId(),a);
        }
    }

    public void addTransitionToPlaceArc(Long id, Long transition, Long place, int value) throws MissingObjectException, WrongValueException {
        Transition t;
        Place p;
        if ((p = findPlace(place)) != null && (t = findTransition(transition)) != null) {
            TransitionToPlaceArc a = new TransitionToPlaceArc(id, t, p, value);
            a.checkArcValue();
            t.transitionToPlaceArcList.add(a);
            arcMap.put(a.getId(),a);
        }
    }

    public void addResetArc(Long id, Long place, Long transition) throws MissingObjectException {
        Transition t;
        Place p;
        if ((p = findPlace(place)) != null && (t = findTransition(transition)) != null) {
            ResetArc a = new ResetArc(id, p, t);
            t.resetArcList.add(a);
            arcMap.put(a.getId(),a);
        }
    }

    private void checkUniqueness(Place place) throws DuplicateException {

        for (Place p : placeList) {
            if (p.getName().equals(place.getName())) {
                throw new DuplicateException("place " + place.getName());
            }
        }
        placeList.add(place);
    }

    private void checkUniqueness(Transition transition) throws DuplicateException {

        for (Transition t : transitionList) {
            if (t.getName().equals(transition.getName())) {
                throw new DuplicateException("transition " + transition.getName());
            }
        }
        transitionList.add(transition);
    }

    public Place findPlace(Long id) throws MissingObjectException {
        for (Place p : placeList) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        throw new MissingObjectException("place", id);
    }

    public Transition findTransition(Long id) throws MissingObjectException {
        for (Transition t : transitionList) {
            if (t.getId().equals(id)) {
                return t;
            }
        }
        throw new MissingObjectException("transition", id);
    }

    public void run() throws WrongInputException, MissingObjectException, UnfireableTransitionException {
        while (true) {
            placeList.stream().forEach(place -> System.out.println(place.getName() + " has value: " + place.getValue()));
            System.out.println("\nchoose the id of transition to be fired:");
            transitionList.stream().forEach(transition -> System.out.println(transition.getName() + " [" + transition.getId() + "]"));
            System.out.println("quit [0]");
            Scan sc = new Scan();
            Long idToBeFired = sc.scanId();
            if (idToBeFired.equals(0L)) System.exit(0);
            Transition t1 = findTransition(idToBeFired);
            t1.fire();
        }
    }

    public boolean checkPlace(Long id) {
        for (Place p : placeList) {
            if (p.getId().equals(id)) {
                return true;
            }
        }
        return  false;
    }

    public boolean checkTransition(Long id) {
        for (Transition t : transitionList) {
            if (t.getId().equals(id)) {
                return true;
            }
        }
        return  false;
    }
}