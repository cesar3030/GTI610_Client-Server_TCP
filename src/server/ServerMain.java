package server;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Iron_Cesar on 15-06-24.
 */
public class ServerMain {

    public static void main(String[] args) {
        try {

            ServerSocket server = new ServerSocket(ServerParameters.PORT);
            System.out.println("Server Started");
            while (true){
                Socket clientSocket = server.accept();
                ServerTask newTask = new ServerTask(clientSocket);
                Thread thread = new Thread(newTask);
                thread.run();
            }



        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
