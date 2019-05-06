package components;

public class ResetArc extends Arc {

    private Place place;
    private Transition transition;
    private long id;

    public ResetArc(Long id, Place place, Transition transition) {
        this.id = id;
        this.place = place;
        this.transition = transition;
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
        return 1;
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
