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
        int serverPort= 3401;//Integer.parseInt(args[1]);

        try {
            Socket mySocket = new Socket(serverIp,serverPort);
            System.out.println("Client started");
            PrintWriter out;
            BufferedReader in;

            //we open a print writer to send the string to the server
            out = new PrintWriter(mySocket.getOutputStream(), true);
            //we open the bufferreader to be able to read what the server sends
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

    /**
     * Method that send the text enter by the user through the keyboard to the server
     * @param mySocket  (used to get the client's IP address)
     * @param in        Stream from the server
     * @param out       Stream to the server
     * @throws IOException
     */
    private static void dialogueWithServer(Socket mySocket, BufferedReader in, PrintWriter out) throws IOException {

        //buffer used to catch the text enter by the client
        BufferedReader read = new BufferedReader(new InputStreamReader(System.in));

        String textToSend;
        String textReceived;

        while (true){
            textToSend=read.readLine();
            //We send the text enter by the client to the server with the ip adress at the end
            out.println(textToSend + " "+mySocket.getInetAddress().getHostAddress());
            out.flush();
            //We read the answer form the server
            textReceived=in.readLine();
            System.out.println(textReceived);

            //If END is received, we close the buffers, the socket and we stop the application
            if(textReceived.substring(0,3).equals(new String("END"))){
                System.out.println("Connection ended with the server");
                in.close();
                read.close();
                out.close();
                mySocket.close();
                break;
            }
        }
    }

}
