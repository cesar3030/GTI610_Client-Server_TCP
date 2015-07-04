package server;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Class that handle a connection from a client
 */
public class ServerTask implements Runnable{


    private Socket clientSocket;
    private boolean running = true;

    public ServerTask(Socket clientSocket) {
     this.clientSocket = clientSocket;
    }

    @Override
    public void run() {

        System.out.println("New client: "+clientSocket.getInetAddress().getHostAddress());

        PrintWriter out;
        BufferedReader in;

        //we open a print writer to send back the string to the client
        try {
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            answerClient(in,out);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Method that listen that catch the string sent by the client and send it back with upper case.
     * @param in    The stream from the client
     * @param out   The stream to the client
     * @throws IOException
     */
    private void answerClient(BufferedReader in,PrintWriter out) throws IOException{

        String textFromClient;
        while (true) {
            textFromClient = in.readLine();
            System.out.println(textFromClient);

            out.println(textFromClient.toUpperCase());
            out.flush();

            if(textFromClient.substring(0,3).equals("end")){
                System.out.println("Connection ended with client: "+clientSocket.getInetAddress().getHostAddress());
                in.close();
                out.close();
                clientSocket.close();
                break;
            }

        }
    }
}
