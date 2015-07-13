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
        ServerSocket server=null;
        try {

            //We create the server scocket with the port wanted
            server = new ServerSocket(ServerParameters.PORT);

            System.out.println("Server Started");

            //We wait client connection request
            while (true){
                //the server listen and handle client connection
                Socket socket = server.accept();
                //we create a new serverTask object that will dialog with the client in another thread
                ServerTask newTask = new ServerTask(socket);
                //We create and start the thread
                Thread thread = new Thread(newTask);
                thread.start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
