import java.io.BufferedReader;
import java.io.PrintStream;
import java.util.List;

public class ServerReciveMessages extends Thread {
    private List<ClientHandler> clients;
    private String userInput;
    private BufferedReader console;
    private PrintStream ps;

    public ServerReciveMessages(List<ClientHandler> clients, PrintStream ps) {
        this.clients = clients;
        this.userInput = null;
        this.ps = ps;
        this.start();
    }

    public void run() {
        System.out.println("New Receive Thread Started");
        try {
            //System.out.println("R - Checking received messages");
            while (clients.size() > 0) {
                for (ClientHandler client : clients) {
                    if (client.in.ready()) {
                        String message = client.in.readLine();
                        ps.println(message);
                        reBroadcastMessageReceived(message, client, message);

                        Thread.currentThread();
                        Thread.sleep(1 * 100);
                    }
                }
                sleep(100);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reBroadcastMessageReceived(String message, ClientHandler SenderClient, String relayMessage){
        try {
            for (ClientHandler client : clients) {
                if(client != SenderClient) {
                    client.out.println(relayMessage);
                    client.out.flush();

                    Thread.currentThread();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}