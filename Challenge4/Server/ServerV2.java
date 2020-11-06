import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServerV2 extends Thread {
    private static ServerSocket ss;
    private static List<ClientHandler> clients;
    private static String userName;

    public ServerV2(String userName) {
        try {
            this.userName = userName;
            ss = new ServerSocket(4445); //port
            System.out.println("Waiting for client to connect");
            clients = Collections.synchronizedList(new ArrayList<ClientHandler>());
            this.start();
        }catch(Exception e){
            System.out.println("Error Server set up");
            e.printStackTrace();
        }
    }

    public void run(){
        while(true) {
            try {
                Socket clientSocket = ss.accept();
                System.out.println("Client Connected");
                ClientHandler newClient = new ClientHandler(clientSocket);
                clients.add(newClient);

                new ServerReciveMessages(clients, System.out);
                new ServerSendMessage(clients,userName);

            } catch (Exception e) {
                System.out.println("Error couldnt read chat ");
                e.printStackTrace();
            }
        }
    }
}
