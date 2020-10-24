import java.io.File;  // Import the File class
import java.io.FileNotFoundException;  // Import this class to handle errors
import java.util.*;

public class BBInterpreter {
    static String filePath;
    static Integer instructionPointer = 0;
    static HashMap<String,Integer> variables = new HashMap<String,Integer>();
    static List<List<Integer>> stack = new ArrayList<List<Integer>>();
    static String[] instructionArray;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Please enter the Full Path to the BB file");
        filePath = sc.nextLine();
        String fileString = "";

        System.out.println("Loading File");
        fileString = loadFile(fileString);
        System.out.println("File Loaded:");
        instructionArray = parseFile(fileString);
        System.out.println();

        while(instructionPointer < instructionArray.length){
            System.out.println("INSTRUCTION: " + instructionPointer);

            String[] instruction = instructionArray[instructionPointer].trim().split(" ");
            String instructionType = instruction[0];
            String varName = null;
            try {
                varName = instruction[1]; // Exspression if while is used
            }catch(Exception ArrayIndexOutOfBoundsException){

            }
            System.out.println("Instructio:"+instructionType);
            switch(instructionType){
                case "clear":
                    System.out.println("Clear Instruction:");
                    setVar(varName, 0);
                    break;
                case "incr":
                    System.out.println("Incr Instruction:");
                    setVar(varName, (getVar(varName) + 1));
                    break;

                case "decr":
                    System.out.println("Decr Instruction:");
                    setVar(varName, (getVar(varName) - 1));
                    break;

                case "while":
                    if (elavuateStatment(instruction)) {

                        try{
                            System.out.println(stack.get(stack.size()-1).get(0) + " " + instructionPointer);
                            if(!(stack.get(stack.size()-1).get(0).equals(instructionPointer))){
                                constructStackFrame();
                            }
                        }catch(Exception IndexOutOfBoundsException) {
                            System.out.println("Error Caught Construct first Stack Frame");
                            constructStackFrame();
                        }

                    }else{
                        instructionPointer = stack.get(stack.size()-1).get(1);
                        deconstructStackFrame();
                    }
                    break;

                case "end":
                    instructionPointer = stack.get(stack.size()-1).get(0) - 1;
                    break;

                default:
                    System.out.println("Unknown Instruction ");
            }
            checkVars();
            printVars();
            instructionPointer++;
            sc.nextLine();
        }
    }

    static void printVars(){
        System.out.println();
        System.out.println("Varibles:");
        for ( String key : variables.keySet()) {
            System.out.println(key + ": " + variables.get(key));
        }
    }

    static int getVar(String varName){
        int value = 0;
        try{
            value = variables.get(varName); // NullPointerException
        }
        catch (NullPointerException e){
            System.out.println("No variable '" + varName + "' found. Error at line " + instructionPointer);
            System.exit(0);
        }
        return value;
    }

    static void setVar(String varName, int value){

        variables.remove(varName);
        variables.put(varName, value);
    }

    static String loadFile(String fileString){
        try {
            File file = new File(filePath);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                fileString = fileString + sc.nextLine();
            }
            sc.close();
        } catch (FileNotFoundException e) {
            System.out.println("No File Found!!!.");
        }
        return fileString;
    }

    static String[] parseFile(String fileString){
        fileString = fileString.trim();
        String[] instructionArray = (fileString.split(";"));
        for (int i = 0; i < instructionArray.length; i++) {
            System.out.println(instructionArray[i]);
        }
        return instructionArray;
    }

    static Boolean elavuateStatment(String[] instruction){
        String varName = instruction[1];
        String condition = instruction[2];
        int value = Integer.parseInt(instruction[3]);
        int var = 0;
        try{
            var = variables.get(varName);
        }catch (NullPointerException e){
            System.out.println("No variable '" + varName + "' found. Error at line " + instructionPointer);
            System.exit(0);
        }
        switch (condition){
            case "not":
                System.out.println(var + ":" + value);
                if (var != value) {
                    System.out.println("Return True");
                    return true;
                }else{
                    System.out.println("Return False");
                    return false;
                }
        }
        System.out.println("Evaluative Statment not known");
        System.exit(0);
        return false;

    }

    static void constructStackFrame() {
        //System.out.println("Start of Construct Stack Frame");
        int whileStart = instructionPointer;
        int tempInstructionPointer = instructionPointer + 1;

        int numOfWhile = 1;
        int numOfEnd = 0;

        //System.out.println(whileStart + " " + tempInstructionPointer + " " + numOfWhile + " " + numOfEnd);

        while (numOfWhile != numOfEnd) {
            //System.out.println("\n While Loop Starting with instruction pointer: " + tempInstructionPointer);
            String[] instruction = instructionArray[tempInstructionPointer].trim().split(" ");
            //System.out.println("Instruction is: " + Arrays.toString(instruction));
            String instructionType = instruction[0];
            //System.out.println("Instruction Type is:" + instructionType + ":");


            if (numOfEnd > numOfWhile) {
                System.out.println("ERROR constructStackFrame");
                System.exit(1);
            }
            if (instructionType.equals("while")) {
                numOfWhile++;
                //System.out.println("While instruction found: " + numOfWhile);
            } else if (instructionType.equals("end")) {
                numOfEnd++;
                //System.out.println("End instruction found: " + numOfEnd);
            }
            tempInstructionPointer++;
        }
        int whileEnd = tempInstructionPointer - 1;

        ArrayList<Integer> stackFrame = new ArrayList<Integer>();
        stackFrame.add(whileStart);
        stackFrame.add(whileEnd);
        stack.add(stackFrame);
        System.out.println("End of Construct Stack Frame");
        System.out.println(Arrays.toString(stackFrame.toArray()));
    }

    static void deconstructStackFrame(){
        stack.remove(stack.size()-1);
    }

    static void checkVars(){

        for ( String var : variables.keySet()) {
            if (variables.get(var) < 0){
                System.out.println("Variable '" + var + "' is negative. Caused at line " + instructionPointer);
                System.exit(0);
            }
        }
    }

}


/*

            private int evalStatement(String str) {
		str = str.trim();

		// Get rid of paranthesis
		if (str.indexOf("(") != -1) {
			int index = str.indexOf("(");
			int parenthesisOpened = 0;
			for (; index < str.length(); index++) {
				if (str.charAt(index) == '(') parenthesisOpened++;
				if (str.charAt(index) == ')') parenthesisOpened--;
				if (parenthesisOpened == 0) break;
			}

			if (index == str.length()) {
				System.out.println("Line " + PC + ": Unclosed paranthesis");
				System.exit(1);
			}

			str = str.substring(0, str.indexOf("(")) + evalStatement(str.substring(str.indexOf("(") + 1, index)) + str.substring(index + 1);
		}

		try {
			return Integer.parseInt(str);
		} catch (NumberFormatException e) {
			if (vars.containsKey(str)) return getVar(str);

			String operands = "><=|&/%+-"; // This order defines priority
        String leftstr = new String();
        String rightstr = new String();
        char op = '\0';

        for (int i = 0; i < operands.length(); i++) {
        int index = str.indexOf(operands.charAt(i));
        if (index == -1) continue;

        op = operands.charAt(i);
        leftstr = str.substring(0, index);
        rightstr = str.substring(index + 1);
        }

        int left = evalStatement(leftstr);
        int right = evalStatement(rightstr);

        switch (op) {
        case '|': return ((left != 0) || (right != 0)) ? 1 : 0;
        case '&': return ((left != 0) && (right != 0)) ? 1 : 0;
        case '>': return (left > right) ? 1 : 0;
        case '<': return (left < right) ? 1 : 0;
        case '=': return (left == right) ? 1 : 0;
        case '+': return left + right;
        case '-': return left - right;
        case '*': return left * right;
        case '/': return left / right;
        case '%': return left % right;
default: {
        System.out.println("Line " + PC + ": Unknown operator " + op);
        System.exit(1);
        }
        }
        }

        System.out.println("Line " + PC + ": Invalid expression " + str);
        System.exit(1);
        return 0;


           case "clear": setVar(cmd[1], 0); break;
			case "incr": setVar(cmd[1], getVar(cmd[1]) + 1); break;
			case "decr": setVar(cmd[1], getVar(cmd[1]) - 1); break;
			case "read": {
				String inp = System.console().readLine();
				while (inp.length() == 0) { inp = System.console().readLine(); }
				setVar(cmd[1], getInt(inp));
			} break;
			case "set": setVar(cmd[1], evalStatement(strJoin(cmd, 2, cmd.length))); break;
			case "print": {
				String msg = strJoin(cmd, 1, cmd.length);

				if (msg.charAt(0) == '"' && msg.charAt(msg.length() - 1) == '"') {
					System.out.println(msg.substring(1, msg.length() - 1));
				} else {
					System.out.println(getVar(cmd[1]));
				}
			} break;

			case "while": whileList.addFirst(PC); if (!evalBlockStatement(cmd)) skipBlock ("while", "end"); break;
			case "end": PC = whileList.getFirst() - 1; whileList.removeFirst(); break;
			//case "if": ifList.addFirst(PC); if (!evalBlockStatement(cmd)) skipBlock ("if", "fi"); break;
			//case "else": if (evalBlockStatement()) break;
			//case "fi": ifList.removeFirst(); break;
			default: System.out.println("Unknown instruction: " + cmd[0]);
		}
*/