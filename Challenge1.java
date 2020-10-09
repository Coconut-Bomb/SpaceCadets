import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

// Find the Name of someone given the beginning of their email using the UoS website
public class Challenge1 {

    final static String preUrl = "https://www.ecs.soton.ac.uk/people/";
    // asks the user for the beginning of the email and then calls the meathod "getName" which will return the name of the person
    public static void main(String[] args) throws Exception {
        BufferedReader cmdReader = new BufferedReader(new InputStreamReader(System.in));
        String id;
        while (true){
            System.out.println("What is their email ID?");
            id = cmdReader.readLine();
            // if "People" is returned the user input was invalid and they are asked to input again
            if(!getName(id).equals("People")){
                System.out.println("The name of this person is "+ getName(id));
                break;
            }
            else{
                System.out.println("Name not found try again. ");
            }
        }
    }

    // gets the name of the person described on the given URL and returns it (returns "People" is invalid url)
    public static String getName(String id) throws Exception{
        URL url = new URL(preUrl + id);
        BufferedReader siteReader = new BufferedReader(new InputStreamReader(url.openStream()));
        // reads the site line by line untill the correct line is found and a substring is created containing the name of the person
        String currentLine;
        int i = 1;
        String name = null;
        while((currentLine = siteReader.readLine()) != null) {
            if (i == 8) {
                name = currentLine.substring(currentLine.indexOf('>')+1,currentLine.indexOf(" |"));
                break;
            }
            i++;
        }
        return name;
    }
}