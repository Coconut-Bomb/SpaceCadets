import java.util.Scanner;

public class ClientV2Interface {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("What is your name?");
        String name = sc.nextLine();
        new ClientV2("localhost",4445, name);
    }

}
