import java.io.BufferedReader;
import java.net.Socket;

public class ClientV2 {
    private Socket server;
    private BufferedReader in;
    private String userName;

    public ClientV2(String hostName, int port, String userName){
        try {
            this.userName = userName;
            this.server = new Socket(hostName, port);
            new ClientReciveMessages(this.server);
            new ClientSendMessages(this.server, userName);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
