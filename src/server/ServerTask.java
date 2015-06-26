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

    private void answerClient(BufferedReader in,PrintWriter out) throws IOException{

        boolean running=true;

        String textFromClient;
        while (running) {
            textFromClient = in.readLine();
            System.out.println(textFromClient);

            out.print(textFromClient.toUpperCase());
            out.flush();

            if(textFromClient.equals("END")){
                System.out.println("Connection ended with client: "+clientSocket.getInetAddress().getHostAddress());
                running=false;
                in.close();
                out.close();
                clientSocket.close();
            }

        }
    }
}
