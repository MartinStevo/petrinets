import Exceptions.DuplicateException;
import Exceptions.MissingObjectException;
import Exceptions.WrongValueException;
import components.*;
import generated.Document;
import generated.Transformer;
import gui.Arc2D;
import gui.Drawable;
import gui.Place2D;
import gui.Transition2D;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DrawableTransformer extends Transformer<List<Drawable>> {

    private Net net = new Net();

    public DrawableTransformer(Net net) {
        this.net = net;
    }

    @Override
    public List<Drawable> transform(Document document) throws WrongValueException, MissingObjectException, DuplicateException {

        List<Drawable> drawables = new LinkedList<>();

        Map<Short,Place2D> place2DMap = new HashMap();
        Map<Short,Transition2D> transition2DMap = new HashMap();

        List<Place2D> place2DList = new LinkedList<>();
        List<Transition2D> transition2DList = new LinkedList<>();

        for (generated.Place p : document.getPlace()) {
            Place place = net.findPlace((long)p.getId());
            Place2D place2d = new Place2D((long)p.getId(),p.getX(), p.getY(), place);
            place2DList.add(place2d);
            place2DMap.put(p.getId(), place2d);
        }

        for (generated.Transition t : document.getTransition()) {
            Transition transition = net.findTransition((long)t.getId());
            Transition2D transition2D = new Transition2D((long)t.getId(),t.getX(), t.getY(), transition);
            transition2DList.add(transition2D);
            transition2DMap.put(t.getId(), transition2D);
        }

        for (generated.Arc a : document.getArc()) {

            Short from = a.getSourceId();
            Short to =  a.getDestinationId();

            Arc arc = net.getArcMap().get((long)a.getId());

            if (arc instanceof PlaceToTransitionArc || arc instanceof ResetArc) {
                Place2D place2D = place2DMap.get(a.getSourceId());
                Transition2D transition2D = transition2DMap.get(a.getDestinationId());
                Arc2D arc2D = new Arc2D(place2D.getX(), place2D.getY(), transition2D.getX(), transition2D.getY(), a.getMultiplicity(), arc);
                drawables.add(arc2D);
            }
            if (arc instanceof TransitionToPlaceArc) {
                Place2D place2D = place2DMap.get(a.getDestinationId());
                Transition2D transition2D = transition2DMap.get(a.getSourceId());
                Arc2D arc2D = new Arc2D(transition2D.getX(), transition2D.getY(), place2D.getX(), place2D.getY(), a.getMultiplicity(), arc);
                drawables.add(arc2D);
            }
        }

        drawables.addAll(place2DList);
        drawables.addAll(transition2DList);

        return drawables;
    }
}
