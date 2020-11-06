import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientReciveMessages extends Thread {
    private BufferedReader in;

    public ClientReciveMessages(Socket server) {
        try {
            this.in = new BufferedReader(new InputStreamReader(server.getInputStream()));
            this.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void run() {
        System.out.println("New Receive Thread Started");
        try{
            String buffer = null;
            System.out.println("Waiting to Receive Messages");
            // holds here waiting to readline from server
            while ((buffer = in.readLine()) != null) {
                //System.out.println("R - Message REceived!");
                //System.out.println("R - Message is: " + buffer);
                System.out.println(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}