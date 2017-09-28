package priv.weilinwu.codecollection.note.socketprogramming.tcp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class TestSingleThreadTCPServer {
	
    public static void main(String[] arg) throws IOException {
        try(ServerSocket serverSocket = new ServerSocket(8189)) {
            try(Socket clientSocket = serverSocket.accept()) {
                InputStream inputStream = clientSocket.getInputStream();
                OutputStream outputStream = clientSocket.getOutputStream();
                
                try(Scanner in = new Scanner(inputStream, "UTF-8")) {
                    PrintWriter out = new PrintWriter(new OutputStreamWriter(outputStream, "UTF-8"), true);
                    
                    out.println("Hello! Enter BYE to exit.");
                    
                    boolean done = false;
                    while(done != true && in.hasNextLine()){
                        String line = in.nextLine();
                        out.println("Echo: " + line);
                        if(line.trim().equals("BYE")) {
                            done = true;
                        }
                    }
                }
            }
        }

    }
}
