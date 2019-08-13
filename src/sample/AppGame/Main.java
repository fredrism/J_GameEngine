package sample.AppGame;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import sample.SceneControl.InputController;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        /*Parent root = FXMLLoader.load(getClass().getResource("cvs.fxml"));
        primaryStage.setTitle("SWAPP");
        primaryStage.setScene(new sample.Scene(root, 640, 400));
        primaryStage.show();*/

        FXMLLoader loader = new FXMLLoader(getClass().getResource("cvs3.fxml"));
        Parent root = loader.load();
        primaryStage.setTitle("SWAPP");
        primaryStage.setScene( new Scene(root, 640, 400));
        primaryStage.show();

        primaryStage.getScene().addEventHandler(KeyEvent.KEY_PRESSED, (key) ->
        {
            InputController.keyEvent.add(key);
            key.consume();
        });
        primaryStage.getScene().addEventHandler(KeyEvent.KEY_RELEASED, (key) ->
        {
            InputController.keyEvent.add(key);
            key.consume();
        });
    }


    public static void main(String[] args) {
        launch(args);
    }
}
