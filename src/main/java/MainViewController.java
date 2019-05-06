import Exceptions.*;
import generated.Document;
import gui.Drawable;
import gui.NetUpdateListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainViewController implements Initializable, NetUpdateListener {

    private final double width = 700;
    private final double height = 460;
    private double coefX = -1;
    private double coefY = -1;
    private Net net;
    private List<Drawable> drawables;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        net = new Net();
    }

    @FXML
    private Button importButton;

    @FXML
    private Button addPlaceButton;

    @FXML
    private Pane pane;

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
        for(Drawable d : drawables){
            try {
                d.draw(pane, coefX, coefY,this);
            } catch (UnfireableTransitionException | MissingObjectException e) {
                e.printStackTrace();
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
