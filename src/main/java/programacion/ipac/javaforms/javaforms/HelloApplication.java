package programacion.ipac.javaforms.javaforms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    private Stage nStage;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainScene.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 400);
        this.nStage = stage;
        nStage.setTitle("JavaForms");
        nStage.setScene(scene);
        nStage.show();
    }



    public static void main(String[] args) {
        launch();
    }
}