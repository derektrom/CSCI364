/*
 Derek Trom
 HW1 Server Side of guess game
 */

import java.net.*;
import java.io.*;
import java.util.Random;
public class GameServer { 
    public static int generateRandomInt(int upperRange){ //create random number method
        Random random = new Random();
        return random.nextInt(upperRange);
    }
    public static void main(String[] args) throws IOException { //main
       if (args.length != 2 || Integer.parseInt(args[0]) <= 0 
                            || Integer.parseInt(args[1]) <= 0) {
            System.err.println("Usage: java -cp build EchoServer <port number> <max random int>"); //if command line args incorrect exit
            System.exit(1);
        }
        
        int portNumber = Integer.parseInt(args[0]); //get port from command line
        int topRange = Integer.parseInt(args[1]); //get max for random # 
        while(true){
            try {
                int attempts = 1; //initiate counter
                int correctNumber = generateRandomInt(topRange); //call random generator
                ServerSocket serverSocket = new ServerSocket(portNumber);//initiate socket
                System.out.println("Waiting for client...");
                //wait for client to connect    
                Socket clientSocket = serverSocket.accept(); 
                //output stream with auto flush
                PrintWriter out =
                new PrintWriter(clientSocket.getOutputStream(), true);                  
                //input stream
                BufferedReader in = new BufferedReader(
                new InputStreamReader(clientSocket.getInputStream()));
                System.out.println("Number is: " + correctNumber);//show in server what #
                //send once client connects
                out.println("Guess a number between 0 and " + topRange);
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    //System.out.println("I received: " + inputLine);
                    //if the guess is correct
                    if(Integer.parseInt(inputLine) == correctNumber){
                        out.println("Correct in "+ attempts + " guesses!");
                    }
                    //if low guess
                    if(Integer.parseInt(inputLine) < correctNumber){
                        out.println("Guess Higher!");
                        attempts++;
                    }
                    //if high guess
                    if(Integer.parseInt(inputLine) > correctNumber){
                        out.println("Guess Lower!");
                        attempts++;
                    }
                }
                serverSocket.close();//close sever
                clientSocket.close();//close client
            } 
            catch (IOException e) { //catch exception
                System.out.println("Exception caught when trying to listen on port "
                + portNumber + " or listening for a connection");
                System.out.println(e.getMessage());
            }
        }
    }
}
