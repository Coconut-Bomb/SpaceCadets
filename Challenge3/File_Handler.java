import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class File_Handler{
    private static String filePath;
    private static ArrayList<String> fileArrayList = new ArrayList<String>();
    private static ArrayList<String> instructionArray = new ArrayList<String>();

    static void setFilePath(String tempFilePath){
        filePath = tempFilePath;
    }

    static void loadFile(){
        try {
            File file = new File(filePath);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                fileArrayList.add(sc.nextLine());
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("No File Found!!!.");
            System.exit(0);
        }
    }
    // white space after the final ';' for a file line will cause a new "fileLinePart" but it dissapears with the "trim" and then error at substring as length is 0
    static ArrayList<String> parseFile(){
        // for each line in the source file
        for ( String fileLine : fileArrayList) {
            String[] fileLineArray = {};
            // if the line doesnt start with "//" it will split the line at every ';'
            try {
                if (!(fileLine.trim().substring(0, 2).equals("//"))) {
                    fileLineArray = fileLine.split(";");
                }
            }catch(Exception e){}
            // for each segment sperated by ';' in the line if it doesnt start with "//" add it to the instructionArray
            // a segment might be just white space after a ';' therefore "try" is used
            for ( String fileLinePart: fileLineArray) {
                try {
                    if (!(fileLinePart.trim().substring(0, 2).equals("//"))) {
                        instructionArray.add(fileLinePart.trim());
                    }
                }catch (Exception e){
                }
            }
        }
        for ( String element: instructionArray) {
            System.out.println(element);
        }
        return instructionArray;
    }

    /*static List<List<Integer>> getSubroutines(){
        List<List<Integer>> subroutines = new ArrayList<List<Integer>>();

        for (int i = 0; i < instructionArray.size(); i++) {
            if(instructionArray.get(i).equals("def")){
                String[] instructionParts = instructionArray.get(i).trim().split(" ");
                ArrayList<Integer> returnArray = new ArrayList<Integer>();

                int start = i ;
                int tempInstructionPointer = i + 1;

                int numOfNeedEnd = 1;
                int numOfEnd = 0;

                while (numOfNeedEnd != numOfEnd) {
                    String[] instruction = instructionArray.get(tempInstructionPointer).trim().split(" ");
                    String instructionType = instruction[0];

                    if (numOfEnd > numOfNeedEnd) {
                        System.out.println("ERROR Too many End Statements");
                        System.exit(1);
                    }
                    if (instructionType.equals("while") || instructionType.equals("if")) {
                        numOfNeedEnd++;
                    } else if (instructionType.equals("end")) {
                        numOfEnd++;
                    }
                    tempInstructionPointer++;
                }
                int end = tempInstructionPointer - 1;

                returnArray.add(start);
                returnArray.add(end);

                subroutines.add(returnArray);

            }
        }
    }*/

}
