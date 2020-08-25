/*
 Derek Trom 
 HW1 Game Client side class
 */


import java.io.*;
import java.net.*;

public class GameClient {
    public static void main(String[] args) throws IOException {
        //make sure command line is correct
        if (args.length != 2 || Integer.parseInt(args[1]) <= 0) {
            System.err.println(
                "Usage: java -cp build EchoClient <host name> <port number>");
            System.exit(1);
        }

        String hostName = args[0]; //host from command line
        int portNumber = Integer.parseInt(args[1]);// port from command line

        try {
            //connect to socket
            Socket gameSocket = new Socket(hostName, portNumber);
            //initiate output stream to socket
            PrintWriter out = new PrintWriter(gameSocket.getOutputStream(), true);
            //initiate input stream from socket
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(gameSocket.getInputStream()));
            //initate reader for user input
            BufferedReader stdIn = new BufferedReader(
                    new InputStreamReader(System.in));
            //initiate variable to store user input
            String userInput;
            //read in first line of server input
            System.out.println("Server: " + in.readLine());
            while ((userInput = stdIn.readLine()) != null) {
                //output guess
                out.println(userInput);
                //read server input for guess
                String serverInput= in.readLine();
                //print to console
                System.out.println("Server: " + serverInput);
                //if correct message recieved exit client program
                if (serverInput.contains("Correct")){
                    System.exit(0);
                }
            }
        } 
        catch (UnknownHostException e) {//can't find host
            System.err.println("Don't know about host " + hostName);
            System.exit(1);
        } 
        catch (IOException e) {//can't connect to socket
            System.err.println("Couldn't get I/O for the connection to " +
                hostName);
            System.exit(1);
        } 
    }
}
