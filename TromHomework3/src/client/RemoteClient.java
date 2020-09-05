/**
 * RemoteClient.java
 */
package client;

import api.ClientManager;
import api.Worker;

import java.rmi.Naming;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * @author Derek Trom
 *
 */
public class RemoteClient {
    public static String userID;
    public static String host;
    public static String port;
    private static ClientManager look_up;
    public static void main(String[] args) {

        int counter = 5;

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        userID = host = port = "";
        if(args.length == 3){
            userID = args[0];
            host = args[1];
            port = args[2];
        } else if(args.length == 2){
            userID = args[0];
            host = args[1];
            port = "1099";
        } else if(args.length == 1){
            userID = args[0];
            host = "localhost";
            port = "1099";
        } else {
            System.err.println("java -cp build -Djava.security.policy=app.policy " +
                    "client.RemoteClient [user id] [host] [port]\n");
            System.exit(0);
        }
        Scanner sc = new Scanner(System.in);
        String url = "rmi://" + host + ":" + port + "/" + ClientManager.REMOTE_OBJECT;
        try {
            look_up = (ClientManager) Naming.lookup(url);
            List<String> tasks =  look_up.register(userID);
            // Print all tasks
            System.out.println("All available tasks:");
            for (int j = 1; j <= tasks.size(); j++) {
                System.out.println("\t" + j + ": " + tasks.get(j - 1));
            }
            for(int i = 0; i < 5; i++) {

                Random rand = new Random();
                int taskIndex = rand.nextInt(4);


                System.out.println(taskIndex);

                System.out.println("You selected: " + tasks.get(taskIndex));

                Worker worker = look_up.requestWork(userID, tasks.get(taskIndex));
                worker.doWork();

                System.out.println("Completed: " + (i + 1));
                look_up.submitResults(userID, worker);
                System.out.println("Running Client score is: " + look_up.getScore(userID));
            }

            System.out.println("Final Client score for " +userID+" is: " + look_up.getScore(userID));

            System.out.println("Finished");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


