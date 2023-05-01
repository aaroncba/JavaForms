package programacion.ipac.javaforms.javaforms;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
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
    Button CreateFormBT;
    @FXML
    Text tryC;
    @FXML
    Text errorCreate;
    @FXML
    TextField cantPreguntas;


    SaveAndStoreAns n = new SaveAndStoreAns();
    List<String> toAdd = n.getIDandName();
    ObservableList ValuesCB = FXCollections.observableList(toAdd);
    @FXML
    private ComboBox values;

    private Stage stage;
    private Scene scene;
    boolean CuestionarioRespondido = false;



    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText(toAdd.toString());

    }
    @FXML
    protected void resetCBClick() {
        values.setItems(ValuesCB);
        values.getSelectionModel().select(0);
    }


    /*
    * goAnswerFormScene() is in charge of handling the option to answer an Form based of the user's selection.
    * */
    @FXML
    protected void goAnswerFormScene(ActionEvent event){

        String selectedForm = (String) values.getValue();
        int FormId;
        String FormName, information;
        ArrayList preguntas = new ArrayList();
        ArrayList<Boolean> respuestas = new ArrayList<Boolean>();

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
                questionsAndAnswers[1] = questionsAndAnswers[1].replace(" ", "");
                boolean addAns = Boolean.parseBoolean(questionsAndAnswers[1]);
                respuestas.add(addAns);
            }
        }

        System.out.println("respuestas en el arraylist ----> " + respuestas.toString());


        GridPane root = new GridPane();
        //as.setContent(loquesea);
        Button continuar = new Button("Continuar");
        Text name, id, preguntasTxt, RespuestaTxt, Descripcion;
        Text[] q = new Text[preguntas.size()];
        CheckBox[] userAns = new CheckBox[preguntas.size()];
        name = new Text("Cuestionario: " + FormName);
        name.setFont(Font.font("verdana", FontWeight.BOLD, FontPosture.REGULAR, 20));
        id = new Text("ID de cuestionario: " + FormId);
        preguntasTxt = new Text("PREGUNTAS");
        RespuestaTxt = new Text("RESPUESTAS");
        Descripcion = new Text("Lea detenidamente cada una de las preguntas, una vez sean leidas y este listo para responder, debe seguir estas reglas, si la respuesta es verdadera darle click a la caja, si cree que es falsa puede dejarla vacia");
        root.add(name, 1, 0);
        root.add(id, 0, 1);
        root.add(Descripcion, 0, 2);
        root.add(preguntasTxt, 0, 3);
        root.add(RespuestaTxt, 2, 3);

        for(int i = 0; i < respuestas.size(); i++){
            q[i] = new Text((i+1) + ". " + (String) preguntas.get(i));
            userAns[i] = new CheckBox();
            root.add(q[i], 0, i+4);
            root.add(userAns[i], 2, i+4);
            if(!((i+1) < respuestas.size())){
                root.add(continuar, 1, i+5);
                //root.setAlignment(Pos.CENTER);
            }
        }
        //On click of continuar, it should check if

        EventHandler<ActionEvent> even = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(CuestionarioRespondido){
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
                }else{
                    int countCorrectas = 0;
                    CuestionarioRespondido = true;
                    for(int i = 0; i < respuestas.size(); i++){
                        if(respuestas.get(i) == userAns[i].isSelected()){
                            q[i].setFill(Color.GREEN);
                            countCorrectas++;

                        }else{
                            q[i].setFill(Color.RED);
                        }
                    }
                    Text correctas = new Text("Puntaje: " + countCorrectas + "/" + preguntas.size());
                    root.add(correctas, 2, 1 );
                }
            }

        };
        continuar.setOnAction(even);


        ScrollPane as = new ScrollPane(root);

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(as, 1800, 500);
        stage.setTitle("Cuestionario " + FormId + " - " + FormName);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    protected void CreateForm(ActionEvent event){
        //Crear un GridPane, y poner el GridPane dentro de un ScrollPane.
        GridPane rootGP = new GridPane();
        boolean error = false;
        Text FormName = new Text("Nombre de Cuestionario: ");
        Text preguntas, respuestas, instrucciones;
        Button continuar;
        TextField[] inputPreguntas;
        CheckBox[] inputRespuestas;
        TextField FormNameInput;

        //Check if there is a valid input in the textfield to continue:
        String cantPValue = cantPreguntas.getText();
        if(cantPValue.isEmpty()){
            error = true;
        }
        String acceptedValues = "0123456789";
        cantPValue.replace(" ", "");
        for(int i = 0; i < cantPValue.length(); i++){
            char a = cantPValue.charAt(i);
            if (!acceptedValues.contains(Character.toString(a))) {
                error = true;
                break;
            }
        }

        if(error){
            errorCreate.setText("Error, asegurese de ingresar algun valor y/o ingresar un valor numerico");
        }else {
            //Agregar un text Field para agregar el nombre y un textField para encontrar el input.
            FormNameInput = new TextField();
            inputPreguntas = new TextField[Integer.parseInt(cantPValue)];
            inputRespuestas = new CheckBox[Integer.parseInt(cantPValue)];
            preguntas = new Text("PREGUNTAS");
            respuestas = new Text("RESPUESTAS");
            instrucciones = new Text("INSTRUCCIONES: Agregar las preguntas en el espacion en blanco \n Si su respuesta es verdadera darle click al checkbox, de lo contrario dejar vacio.");


            rootGP.add(FormName, 0, 0);
            rootGP.add(FormNameInput, 1, 0);
            rootGP.add(instrucciones, 0, 1);
            rootGP.add(preguntas, 0, 2);
            rootGP.add(respuestas, 2, 2);
            Text a = new Text("This is the textField " + cantPreguntas.getText());
            ScrollPane SProot = new ScrollPane(rootGP);


            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(SProot, 700, 500);
            stage.setTitle("adding this");
            stage.setScene(scene);
            stage.show();
        }
    }




}


