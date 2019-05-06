package components;

public abstract class Arc {

    public abstract long getId();

    public abstract void setId(Long id);

    public abstract int getValue();

    public abstract Place getPlace();

    public abstract void setPlace(Place place);

    public abstract Transition getTransition();

    public abstract void setTransition(Transition transition);
}
