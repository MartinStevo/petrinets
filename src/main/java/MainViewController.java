import Exceptions.*;
import components.Arc;
import components.Place;
import components.PlaceToTransitionArc;
import components.Transition;
import generated.Document;
import gui.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import jdk.nashorn.internal.runtime.arrays.IteratorAction;

import javax.swing.text.html.HTMLDocument;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

public class MainViewController implements Initializable, NetUpdateListener {

    private final double width = 700;
    private final double height = 460;
    private double coefX = 1;
    private double coefY = 1;
    private Net net;
    private List<Drawable> drawables = new ArrayList<>();
    private NetClickEventEnu selectedEventType;
    private Button selectedButton = null;
    private long clickedId;
    private long previousClickedId;


    @FXML
    private Button importButton;

    @FXML
    private Button addPlaceButton;

    @FXML
    private Button addTransitionButton;

    @FXML
    private Button addArcButton;

    @FXML
    private Button addTokenButton;

    @FXML
    private Button removeTokenButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Pane pane;

    @FXML
    private Button fireButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        net = new Net();
        pane.setOnMouseClicked(event -> {
            try {
                draw(event.getX(), event.getY());
            } catch (UnfireableTransitionException | MissingObjectException e) {
                e.printStackTrace();
            }
        });

        addTokenButton.setOnMouseClicked(event -> selectEvent(addTokenButton, NetClickEventEnu.ADD_TOKEN));
        removeTokenButton.setOnMouseClicked(event -> selectEvent(addTokenButton, NetClickEventEnu.REMOVE_TOKEN));
        addPlaceButton.setOnMouseClicked(event -> selectEvent(addPlaceButton, NetClickEventEnu.ADD_PLACE));
        addTransitionButton.setOnMouseClicked(event -> selectEvent(addTransitionButton, NetClickEventEnu.ADD_TRANSITION));
        addArcButton.setOnMouseClicked(event -> selectEvent(addArcButton, NetClickEventEnu.ADD_ARC_START));
        fireButton.setOnMouseClicked(event -> selectEvent(fireButton, NetClickEventEnu.FIRE));
        deleteButton.setOnMouseClicked(event -> selectEvent(deleteButton, NetClickEventEnu.DELETE));

    }

    @FXML
    public void importXml(ActionEvent event) throws DuplicateException, WrongValueException, MissingObjectException, UnfireableTransitionException, WrongInputException {
        File file = getFileWithFileChooser((Node) event.getSource());
        if (file != null) {
            createNetFromXml(file);
            //drawNet(net);
        }
    }

    @FXML
    public void onDrawPlaceAction(ActionEvent actionEvent) {

    }

    @FXML
    public void onAddTokenAction(ActionEvent actionEvent) {

    }

    private void selectEvent(Button button, NetClickEventEnu eventEnu) {
        selectedEventType = eventEnu;
        if (selectedButton != null) {
            selectedButton.setCancelButton(false);
        }
        button.setCancelButton(true);
        selectedButton = button;
    }

    private void draw(double x, double y) throws UnfireableTransitionException, MissingObjectException {
        if (NetClickEventEnu.ADD_PLACE.equals(selectedEventType)) {
            Place p = net.addPlace();
            x /= coefX;
            y /= coefY;
            Place2D place2d = new Place2D(p.getId(), (short) x, (short) y, p);
            place2d.draw(pane, coefX, coefY, this);
            drawables.add(place2d);
        }

        if (NetClickEventEnu.ADD_TRANSITION.equals(selectedEventType)) {
            Transition t = net.addTransition();
            x /= coefX;
            y /= coefY;
            Transition2D transition2D = new Transition2D(t.getId(), (short) x, (short) y, t);
            transition2D.draw(pane, coefX, coefY, this);
            drawables.add(transition2D);
        }
    }

    private void drawArc2D(Long idArc, Long idFrom, Long idTo) {

        Arc arc = net.getArcMap().get(idArc);
        short startX = 0;
        short startY = 0;
        short endX = 0;
        short endY = 0;

        for (Drawable d : drawables) {
            if (idFrom == d.getId()) {
                startX = d.getX();
                startY = d.getY();
            }
            if (idTo == d.getId()) {
                endX = d.getX();
                endY = d.getY();
            }
        }

        Arc2D arc2D = new Arc2D(startX, startY, endX, endY, 1, arc);
        drawables.add(0, arc2D);
        updateNet();

    }

    private File getFileWithFileChooser(Node node) {
        FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);

        return fileChooser.showOpenDialog(node.getScene().getWindow());
    }

    private void configureFileChooser(FileChooser fileChooser) {
        fileChooser.setTitle("Choose xml file");
        fileChooser.setInitialDirectory(

                new File("src\\main\\resources")
        );
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("XML", "*.xml")
        );
    }

    private void createNetFromXml(File file) throws WrongValueException, DuplicateException, MissingObjectException, UnfireableTransitionException, WrongInputException {
        Document document = new Document();
        try {
            //TODO need to remove <subnet> tag from generated xml
            JAXBContext context = JAXBContext.newInstance(Document.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();

            document = (Document) unmarshaller.unmarshal(file);

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        createPetriNetFromParsedDocument(document);
    }

    private void createPetriNetFromParsedDocument(Document document) throws WrongValueException, DuplicateException, MissingObjectException, UnfireableTransitionException, WrongInputException {
        NetTransformer nt = new NetTransformer();
        net = nt.transform(document);
        //net.run();

        getPositionData(document);

        DrawableTransformer dt = new DrawableTransformer(net);
        drawables = dt.transform(document);

        updateNet();
    }

    @Override
    public void updateNet() {
        pane.getChildren().clear();
        for (Drawable d : drawables) {
            try {
                d.draw(pane, coefX, coefY, this);
            } catch (UnfireableTransitionException | MissingObjectException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public NetClickEventEnu getSelectedEventType() {
        return selectedEventType;
    }

    @Override
    public void setOnClickedId(Long id) {
        clickedId = id;
    }

    @Override
    public void createArcStart(Long id) {
        this.previousClickedId = id;

        if (selectedEventType.equals(NetClickEventEnu.ADD_ARC_START)) {
            selectedEventType = NetClickEventEnu.ADD_ARC_END;
            // addArcButton.setOnMouseClicked(event -> selectEvent(addArcButton, NetClickEventEnu.ADD_ARC_END));
        }
    }

    @Override
    public void createArcEnd(Long id) throws WrongArcException, WrongValueException, MissingObjectException {

        if (net.checkPlace(previousClickedId) && net.checkTransition(id)) {
            net.addPlaceToTransitionArc(net.getId(), previousClickedId, id, 1);
            drawArc2D(net.getId(), previousClickedId, id);
            net.setId(net.getId() + 1);
        } else if (net.checkTransition(previousClickedId) && net.checkPlace(id)) {
            net.addTransitionToPlaceArc(net.getId(), previousClickedId, id, 1);
            drawArc2D(net.getId(), previousClickedId, id);
            net.setId(net.getId() + 1);
        }
        //else throw new WrongArcException();

        if (selectedEventType.equals(NetClickEventEnu.ADD_ARC_END)) {
            selectedEventType = NetClickEventEnu.ADD_ARC_START;
            //addArcButton.setOnMouseClicked(event -> selectEvent(addArcButton, NetClickEventEnu.ADD_ARC_START));
        }

    }

    public void deleteDrawable(Long id) {
        for (Drawable d : drawables) {

            if (d.getId() == id) {
                drawables.remove(d);
                break;
            }
        }
        Iterator<Drawable> iterator = drawables.iterator();

        while (iterator.hasNext()) {
            Drawable d = iterator.next();
            if (d instanceof Arc2D) {
                Arc2D arc2D = (Arc2D) d;
                if (arc2D.getArc().getPlace().getId() == id || arc2D.getArc().getTransition().getId() == id) {
                    iterator.remove();
                }
            }
        }
    }

    private void getPositionData(Document document) {
        for (generated.Place place : document.getPlace()) {
            if (place.getX() > coefX) {
                coefX = place.getX();
            }
            if (place.getY() > coefY) {
                coefY = place.getY();
            }
        }
        for (generated.Transition transition : document.getTransition()) {
            if (transition.getX() > coefX) {
                coefX = transition.getX();
            }
            if (transition.getY() > coefY) {
                coefY = transition.getY();
            }
        }
        coefX = width / coefX;
        coefY = height / coefY;
    }
}
