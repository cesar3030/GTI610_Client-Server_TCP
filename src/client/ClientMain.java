package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Iron_Cesar on 15-06-23.
 */
public class ClientMain {

    public static void main(String[] args) {

        String serverIp = "127.0.0.1";//args[0];
        int serverPort= 3400;//Integer.parseInt(args[1]);

        try {
            Socket mySocket = new Socket(serverIp,serverPort);
            System.out.println("Client started");
            PrintWriter out;
            BufferedReader in;

            //we open a print writer to send back the string to the client
            out = new PrintWriter(mySocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
            dialogueWithServer(mySocket,in, out);
            in.close();
            out.close();
            mySocket.close();
            System.exit(0);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    private static void dialogueWithServer(Socket mySocket, BufferedReader in, PrintWriter out) throws IOException {

        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));
        boolean running=true;

        String textToSend;
        String textReceived;

        while (running){
            textToSend=read.readLine();
            out.println(textToSend + " "+mySocket.getInetAddress().getHostAddress());  // send to server
            out.flush();
            textReceived=in.readLine();
            System.out.println(textReceived);

            if(textReceived.equals(new String("END"))){
                System.out.println("Connection ended with the server");
                running=false;
                in.close();
                read.close();
                out.close();
                mySocket.close();
            }
        }
    }

}
