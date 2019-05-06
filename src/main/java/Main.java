import Exceptions.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) throws DuplicateException, MissingObjectException, WrongInputException, WrongValueException, UnfireableTransitionException {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("views\\Mainview.fxml"));
        Parent root = loader.load();
        MainViewController controller = loader.getController();
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Petri Nets");
        primaryStage.show();

        //new comment

    }
}
