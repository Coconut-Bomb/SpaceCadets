import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler {
    protected Socket client;
    protected PrintWriter out;
    protected BufferedReader in;


    public ClientHandler (Socket client){
        this.client = client;

        try {
            this.out = new PrintWriter(client.getOutputStream());

            InputStreamReader inSteam = new InputStreamReader(client.getInputStream());
            in = new BufferedReader(inSteam);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
