package programacion.ipac.javaforms.javaforms;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    Button AnsFormScene;

    @FXML
    Text tryC;
    SaveAndStoreAns n = new SaveAndStoreAns();
    List<String> toAdd = n.getIDandName();
    ObservableList ValuesCB = FXCollections.observableList(toAdd);
    @FXML
    private ComboBox values;

    private Stage stage;
    private Scene scene;
    Pane backMain;


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText(toAdd.toString());

    }
    @FXML
    protected void resetCBClick() {
        values.setItems(ValuesCB);
        values.getSelectionModel().select(0);
    }

    @FXML
    protected void goAnswerFormScene(ActionEvent event){
        String selectedForm = (String) values.getValue();
        int FormId;
        String FormName, information;
        ArrayList preguntas = new ArrayList();
        ArrayList respuestas = new ArrayList();

        //System.out.println(selectedForm);
        String[] plainValues = selectedForm.split("-");
        plainValues[0] = plainValues[0].replace("#", "");
        plainValues[0] = plainValues[0].replace(" ", "");
        FormId = Integer.parseInt(plainValues[0]);
        FormName = plainValues[1];

        SaveAndStoreAns getInformation = new SaveAndStoreAns();
        information = getInformation.getInformationLine(FormId);

        String[] selectedLine = information.split("\\|");


        //this will split the questions and the answers:
        for(String currQuestion : selectedLine){
            String[] questionsAndAnswers =  currQuestion.split("=");
            if(questionsAndAnswers.length < 2){
                continue;
            } else {
                preguntas.add(questionsAndAnswers[0]);
                respuestas.add(questionsAndAnswers[1]);
            }
        }


        Button[] muchosBotones = new Button[31];
        EventHandler<ActionEvent> even = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Yo estuve por aqui");
                FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("MainScene.fxml"));

                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                try {
                    scene = new Scene(fxmlLoader.load(), 600, 400);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                stage.setScene(scene);
                stage.show();
            }
        };


        GridPane root = new GridPane();
        //as.setContent(loquesea);

        Text q, name, id;
        CheckBox[] userAns = new CheckBox[preguntas.size()];
        name = new Text("Cuestionario: " + FormName);
        id = new Text("ID de cuestionario: " + FormId);
        root.add(name, 1, 0);
        root.add(id, 0, 1);
        for(int i = 0; i < respuestas.size(); i++){
            muchosBotones[i] = new Button("boton numero #" + i);
            q = new Text((String) preguntas.get(i));
            userAns[i] = new CheckBox();
            root.add(q, 0, i+2);
            root.add(userAns[i], 2, i+2);
        }
        ScrollPane as = new ScrollPane(root);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(as, 600, 400);
        stage.setScene(scene);
        stage.show();
    }
}


