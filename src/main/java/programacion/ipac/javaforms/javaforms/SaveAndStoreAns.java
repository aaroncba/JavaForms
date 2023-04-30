package programacion.ipac.javaforms.javaforms;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;

public class SaveAndStoreAns {
    Scanner userInput;
    File FormInformation;
    private HashMap<String, Boolean> userQuestion;
    public static void main(String[] args) throws IOException {
        SaveAndStoreAns n = new SaveAndStoreAns();
        n.inputUserQ();
    }
    //inputUserQ, will take the input from CreateForm scene.
    //it will store each question in a
    public void inputUserQ() throws IOException {
        this.userInput = new Scanner(System.in);
        boolean nextQuestion = true;
        this.userQuestion = new HashMap<String, Boolean>();
        String question;
        boolean answer;
        while(nextQuestion){
            question = "";
            answer = false;
            System.out.println("Ingrese la pregunta que usted desea");
            question = this.userInput.nextLine();
            System.out.println("Ingrese la respuesta para su pregunta: ");
            if(this.userInput.nextLine().isEmpty()){
                System.out.println("Entro aqui");
            }
            else{
                System.out.println("Si hay algo en la string");
                answer = true;
            }

            this.userQuestion.put(question, answer);

            System.out.println("Enter any letter if you want to exit");
            if(!this.userInput.nextLine().isEmpty()){
                nextQuestion = false;
            }
        }
        System.out.println(this.userQuestion);
        SaveQuestions();
    }

    public void SaveQuestions() throws IOException {
        //this function will work by getting the File where the information will be stored,
        //then it will take the hashmap were the information was stored
        //it will read the last line of the CSV file and add 1 to that value
        //that will be the formID
        this.FormInformation = new File("Cuestionarios.csv");

        String output = "\"" + this.userQuestion.toString() + "\"";
        int FormID  = 0;
        if(this.FormInformation.createNewFile()){
            System.out.println("********CREANDO********");
            //writeQuestions.write("FormID, QuestionAndAnswers");
        }


        System.out.println("Va a entrar");
        ArrayList<String> values = returnLastID();
        System.out.println(values.toString());

        //esta parte nos dara el ID
        /*
        * Obtiene el ID obteniendo la ultima linea que fue escrita
        * Le suma uno al ultimo valor, luego de transformar la string a int para que se pueda operar.
        * */
        String[] getID = values.get(values.size()-1).split(",");
        FormID = Integer.parseInt(getID[0]) + 1;

        FileWriter writeQuestions = new FileWriter(this.FormInformation);
        for(String toAdd : values){
            writeQuestions.write("\n" + toAdd);
        }
        writeQuestions.write("\n" + FormID + "," + output);
        writeQuestions.close();
    }

    //returnLastID()
    /*
    * Esta funcion se encarga de devolver un arraylist con los valores que estan en el CSV.
    * */
    public ArrayList<String> returnLastID(){
        ArrayList<String> cuestionarios = new ArrayList<String>();
        try{
            FileReader readForm = new FileReader(this.FormInformation);
            BufferedReader reader = new BufferedReader(readForm);
            String CurrLine = reader.readLine();
            int count = 0;
            while(CurrLine != null){
                System.out.println("Esta entrando por aqui");
                cuestionarios.add(CurrLine);
                System.out.println(CurrLine);
                CurrLine = reader.readLine();
                count++;
            }
            //values = lastLline.split(",");
        }catch (IOException e){
            System.out.println("File is empty");
        }

        return cuestionarios;
    }
}
