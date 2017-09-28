package priv.weilinwu.codecollection.note.socketprogramming.tcp;

import java.io.BufferedReader;  
import java.io.IOException;  
import java.io.InputStreamReader;  
import java.io.PrintStream;  
import java.net.Socket;  
import java.net.SocketTimeoutException; 

public class TcpClient {
    public static void main(String[] args) throws IOException {  
    	
        //客户端请求与本机在20006端口建立TCP连接   
        Socket clientSocket = new Socket("localhost", 20006);  
        clientSocket.setSoTimeout(10000);  
        //获取键盘输入   
        BufferedReader bufDataToSend = new BufferedReader(new InputStreamReader(System.in));  
        //获取Socket的输出流，用来发送数据到服务端    
        PrintStream out = new PrintStream(clientSocket.getOutputStream());  
        //获取Socket的输入流，用来接收从服务端发送过来的数据    
        BufferedReader bufDataReceived =  new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));  
        boolean flag = true;  
        while(flag){  
            System.out.print("输入信息：");  
            String str = bufDataToSend.readLine();  
            //发送数据到服务端    
            out.println(str);  
            if("bye".equals(str)){  
                flag = false;  
            }else{  
                try{  
                    //从服务器端接收数据有个时间限制（系统自设，也可以自己设置），超过了这个时间，便会抛出该异常  
                    String echo = bufDataReceived.readLine();  
                    System.out.println(echo);  
                }catch(SocketTimeoutException e){  
                    System.out.println("Time out, No response");  
                }  
            }  
        }  
        bufDataToSend.close();  
        if(clientSocket != null){  
            //如果构造函数建立起了连接，则关闭套接字，如果没有建立起连接，自然不用关闭  
            clientSocket.close(); //!!!只关闭socket，其关联的输入输出流也会被关闭  
        }  
    } 
}
