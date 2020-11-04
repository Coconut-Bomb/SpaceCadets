import java.util.HashMap;

public class Vars {

    static HashMap<String,Integer> variables = new HashMap<String,Integer>();

    public int getVar(String varName){
        int value = 0;
        try{
            value = variables.get(varName); // NullPointerException
        }
        catch (NullPointerException e){
            System.out.println("No variable '" + varName + "' found. Error at line " + BBInterpreter.instructionPointer);
            System.exit(0);
        }
        return value;
    }

    public void setVar(String varName, int value){
        variables.remove(varName);
        variables.put(varName, value);
    }

    public void incr(String varName){
        setVar(varName, (getVar(varName) + 1));
    }

    public void decr(String varName){
        setVar(varName, (getVar(varName) - 1));
    }

    public void printVars(){
        System.out.println();
        System.out.println("Varibles:");
        for ( String key : variables.keySet()) {
            System.out.println(key + ": " + variables.get(key));
        }
    }

    static void checkVars(){

        for ( String var : variables.keySet()) {
            if (variables.get(var) < 0){
                System.out.println("Variable '" + var + "' is negative. Caused at line " + BBInterpreter.instructionPointer);
                System.exit(0);
            }
        }
    }

}
