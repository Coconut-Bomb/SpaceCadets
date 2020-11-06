import java.io.*;
import java.net.Socket;

public class ClientSendMessages extends Thread {
    private String userInput;
    private BufferedReader console;
    private PrintWriter pr;
    private String userName;

    public ClientSendMessages(Socket server, String userName) {
        try {
            this.userName = userName;
            pr = new PrintWriter(server.getOutputStream());
            this.userInput = null;
            this.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        System.out.println("New Send Thread Started");
        try {

            this.console = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("S - Enter your messages: ");
            while ((this.userInput = console.readLine()) != null) {
                if (userInput != null & userInput.length() > 0) {
                    //System.out.println("S - Message sent \"" + userInput + "\"");
                    pr.println("[" + userName + "] " + userInput);
                    pr.flush();

                    Thread.currentThread();
                    Thread.sleep(1 * 50);

                }
            }
        } catch (Exception e) {
            System.out.println("ERRRRRRRORRRRRRRRR");
            e.printStackTrace();
        }
    }
}