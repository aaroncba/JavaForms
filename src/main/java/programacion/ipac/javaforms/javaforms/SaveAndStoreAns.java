package programacion.ipac.javaforms.javaforms;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;

public class SaveAndStoreAns {
    Scanner userInput;
    File FormInformation = new File("Cuestionarios.csv");;
    String FormName;
    private HashMap<String, Boolean> userQuestion;

    //inputUserQ, will take the input from CreateForm scene.
    //it will store each question in a
    public void inputUserQ(HashMap AddInformation, String FormNameInput) throws IOException {
        this.userInput = new Scanner(System.in);
        boolean nextQuestion = true;
        this.userQuestion = AddInformation;
        this.FormName = FormNameInput;

        SaveQuestions();
    }

    public void SaveQuestions() throws IOException {
        //this function will work by getting the File where the information will be stored,
        //then it will take the hashmap were the information was stored
        //it will read the last line of the CSV file and add 1 to that value
        //that will be the formID

        String output = "\"" + this.userQuestion.toString() + "\"";
        int FormID  = 0;
        if(this.FormInformation.createNewFile()){

        }


        ArrayList<String> values = returnLastID();

        //esta parte nos dara el ID
        /*
        * Obtiene el ID obteniendo la ultima linea que fue escrita
        * Le suma uno al ultimo valor, luego de transformar la string a int para que se pueda operar.
        * */
        if(!values.isEmpty()){
            String[] getID = values.get(values.size()-1).split(",");
            FormID = Integer.parseInt(getID[0]) + 1;

        }

        FileWriter writeQuestions = new FileWriter(this.FormInformation);
        for(String toAdd : values){
            if(toAdd == ""){
                continue;
            }
            writeQuestions.write("\n" + toAdd);
        }
        writeQuestions.write("\n" + FormID + "," + this.FormName + "," + output);
        writeQuestions.close();
        getIDandName();
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
                cuestionarios.add(CurrLine);
                CurrLine = reader.readLine();
                count++;
            }
            //values = lastLline.split(",");
        }catch (IOException e){
        }

        return cuestionarios;
    }

    public ArrayList<String> getIDandName(){
        ArrayList<String> valuestoReturn = new ArrayList<String>();
        ArrayList<String> plainValues = new ArrayList<>();
        plainValues = returnLastID();

        for(String toEdit : plainValues){
            String[] sepValues = toEdit.split(",");
            if(sepValues.length > 2){
                valuestoReturn.add("#" + sepValues[0] + " - " + sepValues[1]);
            }
        }


        return valuestoReturn;
    }

    public String getInformationLine(int FormID){
        ArrayList<String> plainValues = new ArrayList<>();
        plainValues = returnLastID();
        String toReturn = "";
        for(String v : plainValues){
            String[] sepValues = v.split(",");
            if(sepValues.length > 1){
                if(Integer.parseInt(sepValues[0]) == FormID){
                    toReturn = v;
                }
            }
        }
        String[] sepQandA = toReturn.split(",");
        toReturn = "";
        //toReturn = sepQandA[2] +  + sepQandA[sepQandA.length-1];
        for(int i = 2; i < sepQandA.length; i++){
            if(i == 2){
             toReturn += sepQandA[i];
            }else {
                toReturn += " | " + sepQandA[i];
            }
        }

        toReturn = toReturn.replace("\"", "");
        toReturn = toReturn.replace("{", "");
        toReturn = toReturn.replace("}", "");
        return toReturn;
    }
}
