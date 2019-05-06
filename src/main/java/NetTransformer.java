import Exceptions.DuplicateException;
import Exceptions.MissingObjectException;
import Exceptions.WrongValueException;
import generated.Document;
import generated.Place;
import generated.Transformer;
import java.util.List;

public class NetTransformer extends Transformer<Net> {

    @Override
    public Net transform(Document document) throws WrongValueException, DuplicateException, MissingObjectException {
        Net net = new Net();

        List<Place> genPlaces = document.getPlace();
        List<generated.Transition> genTransitions = document.getTransition();
        List<generated.Arc> genArcs = document.getArc();

        for (generated.Place p : genPlaces) {
            net.addPlace((long) p.getId(), p.getLabel(), p.getTokens());
        }

        for (generated.Transition t : genTransitions) {
            net.addTransition((long) t.getId(), t.getLabel());
        }

        for (generated.Arc a : genArcs) {
            Long from = (long) a.getSourceId();
            Long to = (long) a.getDestinationId();

            if (net.checkTransition(from) && net.checkPlace(to)) {
                net.addTransitionToPlaceArc((long)a.getId(), from, to, a.getMultiplicity());

            } else if (net.checkPlace(from) && net.checkTransition(to)) {
                if (a.getType().equals("regular")) {
                    net.addPlaceToTransitionArc((long)a.getId(), from, to, a.getMultiplicity());
                }
                else if (a.getType().equals("reset")) {
                    net.addResetArc((long)a.getId(), from,to);
                }
            }
        }

        return net;
    }
}
