/*
Sub routines
*/
public class Main {
    // Does all the proccessing to pass each line to the Interprater

    //static String[] instructionArray;
    static File_Handler file = new File_Handler();
    static BBInterpreter interpreter = new BBInterpreter();


    public static void main(String[] args) throws Exception{

        System.out.println("Please enter the Full Path to the BB file");
        file.setFilePath("C:\\Users\\Delmar\\Desktop\\Space Cadets\\Challenge3\\input2.txt");
        System.out.println("Loading File");
        file.loadFile();
        System.out.println("File Loaded:");
        interpreter.loadInstructions(file.parseFile()); // , file.getSubroutines()
        System.out.println("Instructions Loaded:");
        System.out.println("Executing:\n");
        interpreter.execute();


    }
}
