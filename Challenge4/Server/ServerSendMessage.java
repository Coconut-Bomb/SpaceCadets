import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

public class ServerSendMessage extends Thread {
    private List<ClientHandler> clients;
    private String userInput;
    private BufferedReader console;
    private Scanner sc = new Scanner(System.in);
    private String userName;

    public ServerSendMessage(List<ClientHandler> clients, String userName) {
        this.userName = userName;
        this.clients = clients;
        this.userInput = null;
        this.start();
    }

    public void run() {
        System.out.println("New Send Thread Started");
        try {
            if (clients.size() > 0) {
                //System.out.println("S - In Send Messages");

                this.console = new BufferedReader(new InputStreamReader(System.in));

                //System.out.println("S - Enter your messages: ");
                // program holds for console.readLine() to have an input
                while ((this.userInput = console.readLine()) != null) {
                    //System.out.println("S -Message is: " + userInput);
                    if (userInput != null & userInput.length() > 0) {
                        //System.out.println("S - Sending message");
                        for (ClientHandler client : clients) {
                            client.out.println("[" + userName + "] " + userInput);
                            client.out.flush();
                        }
                        Thread.currentThread();
                        Thread.sleep(1 * 50);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}