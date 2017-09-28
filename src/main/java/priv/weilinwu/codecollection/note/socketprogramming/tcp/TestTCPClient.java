package priv.weilinwu.codecollection.note.socketprogramming.tcp;

import java.net.Socket;
import java.util.Scanner;

public class TestTCPClient {
    public static void main(String[] args) throws Exception {
        try(Socket socket = new Socket("time-a.nist.gov", 13);
                Scanner scanner = new Scanner(socket.getInputStream(), "UTF-8")) {
            while(scanner.hasNextLine()) {
                System.out.println(scanner.nextLine());
            }
        }
    }
}
