package priv.weilinwu.codecollection.note.socketprogramming.tcp;

import java.io.BufferedReader;  
import java.io.InputStreamReader;  
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TcpServer implements Runnable {
	
    private Socket clientSocket = null;  
    public TcpServer(Socket clientSocket){  
        this.clientSocket = clientSocket;  
    }  
    
    public static void main(String[] args) throws Exception{  
        //服务端在20006端口监听客户端请求的TCP连接  
        ServerSocket server = new ServerSocket(20006);  
        Socket clientSocket = null;  
        boolean flag = true;  
        while(flag){  
            //等待客户端的连接，如果没有获取连接  
            clientSocket = server.accept();  
            System.out.println("与客户端连接成功！");  
            //为每个客户端连接开启一个线程  
            new Thread(new TcpServer(clientSocket)).start();  
        }  
        server.close();  
    } 
      
    @Override  
    public void run() {  
        try{  
            //获取Socket的输出流，用来向客户端发送数据  
            PrintStream out = new PrintStream(clientSocket.getOutputStream());  
            //获取Socket的输入流，用来接收从客户端发送过来的数据  
            BufferedReader buf = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));  
            boolean flag =true;  
            while(flag){  
                //接收从客户端发送过来的数据  
                String str =  buf.readLine();  
                if(str == null || "".equals(str)){  
                    flag = false;  
                }else{  
                    if("bye".equals(str)){  
                        flag = false;  
                    }else{  
                        //将接收到的字符串前面加上echo，发送到对应的客户端  
                        out.println("echo:" + str);  
                    }  
                }  
            }  
            out.close();  
            clientSocket.close();  
        }catch(Exception e){  
            e.printStackTrace();  
        }  
    } 
}
