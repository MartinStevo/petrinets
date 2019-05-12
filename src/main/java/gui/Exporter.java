package gui;

import components.PlaceToTransitionArc;
import components.ResetArc;
import components.TransitionToPlaceArc;
import generated.Arc;
import generated.Document;
import generated.Place;
import generated.Transition;

import java.util.ArrayList;
import java.util.List;

public class Exporter {

    private List<Drawable> drawables;
    private List<Place> places = new ArrayList<>();
    private List<Transition> transitions = new ArrayList<>();
    private List<Arc> arcs = new ArrayList<>();

    public Exporter(List<Drawable> drawables) {
        this.drawables = drawables;
    }

    public Document getDocument() {

        for (Drawable d : drawables) {
            if (d instanceof Place2D) {
                Place place = new Place();
                place.setId((short)d.getId());
                place.setLabel(((Place2D) d).getPlace().getName());
                place.setTokens((short)((Place2D) d).getPlace().getValue());
                place.setX(d.getX());
                place.setY(d.getY());
                places.add(place);
            }
            if (d instanceof Transition2D) {
                Transition transition = new Transition();
                transition.setId((short)d.getId());
                transition.setLabel(((Transition2D) d).getTransition().getName());
                transition.setX(d.getX());
                transition.setY(d.getY());
                transitions.add(transition);
            }
            if (d instanceof Arc2D) {
                Arc arc = new Arc();
                arc.setId((short)d.getId());
                arc.setMultiplicity((short)((Arc2D) d).getArc().getValue());

                if (((Arc2D) d).getArc() instanceof PlaceToTransitionArc || ((Arc2D) d).getArc() instanceof ResetArc) {
                    arc.setSourceId(((Arc2D) d).getArc().getPlace().getId().shortValue());
                    arc.setDestinationId(((Arc2D) d).getArc().getTransition().getId().shortValue());
                }
                else if (((Arc2D) d).getArc() instanceof TransitionToPlaceArc) {
                    arc.setSourceId(((Arc2D) d).getArc().getTransition().getId().shortValue());
                    arc.setDestinationId(((Arc2D) d).getArc().getPlace().getId().shortValue());
                }

                if (((Arc2D) d).getArc() instanceof ResetArc) {
                    arc.setType("reset");
                }
                else {
                    arc.setType("regular");
                }
                arcs.add(arc);
            }
        }
        Document document = new Document();
        document.setPlace(places);
        document.setTransition(transitions);
        document.setArc(arcs);

        return document;
    }
}
