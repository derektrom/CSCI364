/**
 * RemoteServer.java
 */
package server;

import java.rmi.*;
import java.rmi.server.RemoteObject;
import java.rmi.server.UID;
import java.util.*;

import api.ClientManager;
import api.Worker;

import java.rmi.server.UnicastRemoteObject;
/**
 * @author Derek Trom
 *
 */
public class RemoteServer extends UnicastRemoteObject implements ClientManager {

    private static final long serialVersionUID = 1L;
    private final String[] tasks = { "SortWorker", "SumReducer", "PrimeChecker", "FractionReducer" };
    private HashMap<String, Float > clientsScores;
    private HashMap<String , List<UUID>> tasksIDs;
    private HashMap<UUID, Object> workers;
    private HashMap<UUID, Object> outputs;
    private List<UUID> SortWorkers;
    private List<UUID> FractionReducers;
    private List<UUID> SumReducers;
    private List<UUID> PrimeCheckers;
    private class stuff{
    }

    public RemoteServer(List<String> list, HashMap<String, Float> clients, HashMap<String, List<UUID>> taskIDs, HashMap<UUID,
            Object> workers,HashMap<UUID, Object> outputs, List<UUID> SortWorkers, List<UUID> FractionReducers,
                        List<UUID> SumReducers, List<UUID> PrimeCheckers) throws RemoteException{
        super();
        this.clientsScores = clients;
        this.tasksIDs = taskIDs;
        this.workers = workers;
        this.outputs = outputs;
        this.SortWorkers = SortWorkers;
        this.FractionReducers = FractionReducers;
        this.SumReducers = SumReducers;
        this.PrimeCheckers = PrimeCheckers;
    }

    public HashMap<String,Float> getUsers()
    {
        return clientsScores;
    }
    @Override
    public float getScore(String userID) throws RemoteException {
        return clientsScores.get(userID);
    }


    @Override
    public List<String> register (String userid) throws RemoteException{
        System.err.println(userid+" is trying to connect");
        Float avail = clientsScores.get(userid);
        if (avail == null) {
            System.out.print("----------------\nClient List: ");
            clientsScores.put(userid, (float) 0);
        }

        System.out.println(clientsScores+ "\n---------------------");
        return Arrays.asList(tasks);
    }
    @Override
    public Worker requestWork (String userID, String taskName) throws RemoteException{
        UUID uid = UUID.randomUUID();
        System.err.println(userID+" is trying to request work");
        String task = taskName;
        System.out.println("User: "+userID+" Requested: "+ task);

        switch(task) {
            case "SortWorker":
                Random rand = new Random();
                int numOfNums = Math.abs(rand.nextInt()%5)+1;
                Integer[] array = new Integer[numOfNums];
                List<Integer> beforeSort = new ArrayList<Integer>();
                for (int i = 0; i<numOfNums; i++){
                    int rando = Math.abs(rand.nextInt()%101);
                    array[i] = rando;
                    beforeSort.add(rando);
                }
                SortWorker<Integer> sw2 = new SortWorker<>(uid, array);
                SortWorkers.add(uid);
                tasksIDs.put(task, SortWorkers);
                UserData stuff = new UserData(task, beforeSort);
                workers.put(uid, stuff);
                return sw2;
            case "SumReducer":
                SumReducers.add(uid);
                tasksIDs.put(task, SumReducers);
                Random rand2 = new Random();
                int ran1_3 = Math.abs(rand2.nextInt()%3)+1;
                int numOfNums2 = Math.abs(rand2.nextInt()%5)+1;
                switch (ran1_3){
                    case 1:
                        List<Integer> forUser = new ArrayList<Integer>();
                        Integer[] array2 = new Integer[numOfNums2];
                        for (int i = 0; i<numOfNums2; i++){
                            int num = Math.abs(rand2.nextInt()%1001);
                            array2[i] = num;
                            forUser.add(num);
                        }
                        UserData stuff1 = new UserData(task, forUser);
                        SumReducer sr = new SumReducer (uid, array2 );

                        workers.put(uid, stuff1);
                        return sr;
                    case 2:
                        List<Float> forUser2 = new ArrayList<Float>();
                        Float[] array3 = new Float[numOfNums2];
                        for (int i = 0; i<numOfNums2; i++){
                            float num1 = rand2.nextFloat() * (1000- 1) + 1;
                            array3[i]= num1;
                            forUser2.add(num1);
                        }
                        UserData stuff2 = new UserData(task, forUser2);
                        SumReducer sr1 = new SumReducer(uid, array3);
                        workers.put(uid, stuff2);
                        return sr1;
                    case 3:
                        Double[] array4 = new Double[numOfNums2];
                        List<Double> forUser3 = new ArrayList<Double>();
                        for (int i = 0; i<numOfNums2; i++){
                            double num2 = rand2.nextDouble() * (1000- 1) + 1;
                            array4[i]= num2;
                            forUser3.add(num2);
                        }
                        SumReducer sr2 = new SumReducer(uid, array4);
                        UserData stuff3 = new UserData(task, forUser3);
                        workers.put(uid, stuff3);
                        return sr2;
                    default:
                        return null;
                }


            case "PrimeChecker":
                Random rand1 = new Random();
                int randNum = Math.abs(rand1.nextInt()%1000);
                PrimeChecker pc = new PrimeChecker(uid, randNum);
                PrimeCheckers.add(uid);
                tasksIDs.put(task, PrimeCheckers);
                UserData stuff3 = new UserData(task, randNum);
                workers.put(uid, stuff3);
                return pc;
            case "FractionReducer":
                Random rand3 = new Random();
                int numerator = Math.abs(rand3.nextInt()%1000);
                int denominator = Math.abs(rand3.nextInt()%1000);
                FractionReducer fr = new FractionReducer(uid, numerator, denominator);
                FractionReducers.add(uid);
                List<Integer> numDum= new ArrayList<Integer>();
                numDum.add(numerator);
                numDum.add(denominator);
                tasksIDs.put(task, FractionReducers);
                UserData stuff4 = new UserData(task, numDum);
                workers.put(uid, stuff4);
                return fr;
            default:
                return null;
        }


    }
    @Override
    public void submitResults (String userID, Worker answer){
        System.out.println("----------------------------------");
        System.err.println(userID+" is trying to submit work");
        String taskType = answer.getTaskName();

        System.out.println(taskType);
        switch (taskType){
            case "SortWorker":
                SortWorker<Integer> ans = (SortWorker<Integer>) answer;
                List<Integer> result = ans.getList();
                outputs.put(ans.getTaskId(), result);
                System.out.println("Sorted : "+result);
                Float clientScore = clientsScores.get(userID);
                clientsScores.put(userID, clientScore+1);
                System.out.println("SortWorkers:");
                for(UUID task : tasksIDs.get(taskType)){
                    System.out.print("UID: "+task);
                    UserData next = (UserData) workers.get(task);
                    System.out.println(" Input: "+ next.getStuff() + " Output: "+outputs.get(task));
                }
                break;
            case "PrimeChecker":
                PrimeChecker ans1 = (PrimeChecker) answer;

                boolean result1 = ans1.getPrime();
                outputs.put(ans1.getTaskId(), result1);

                if (result1){
                    System.out.println("Number is Prime");
                }
                else {
                    System.out.println("Number is not Prime");
                }
                Float clientScore2 = clientsScores.get(userID);
                clientsScores.put(userID, clientScore2+1);
                System.out.println("Prime Checkers");
                for(UUID task : tasksIDs.get(taskType)){
                    System.out.print("UID: "+task);
                    UserData next = (UserData) workers.get(task);
                    System.out.println(" Input: "+ next.getStuff() + " Output: "+outputs.get(task));
                }
                break;
            case "SumReducer":
                SumReducer ans2 = (SumReducer) answer;
                outputs.put(ans2.getTaskId(), ans2.getAnswer());
                String answerType = ((SumReducer) answer).findType();
                System.out.println(ans2.getAnswer());
                Float clientScore3 = clientsScores.get(userID);
                clientsScores.put(userID, clientScore3+1);
                System.out.println("Sum Reducers");
                for(UUID task : tasksIDs.get(taskType)){
                    System.out.print("UID: "+task);
                    UserData next = (UserData) workers.get(task);
                    System.out.println(" Input: "+ next.getStuff() + " Output: "+outputs.get(task));
                }
                break;
            case "FractionReducer":
                FractionReducer ans3 = (FractionReducer) answer;
                List<Integer> numDum = new ArrayList<Integer>();
                numDum.add(ans3.getReducedNumerator());
                numDum.add(ans3.getReducedDenominator());
                outputs.put(ans3.getTaskId(), numDum);
                System.out.println("GCF: " + ans3.getGCF() +
                        " Original Fraction: "+ ans3.getNumerator()+"/"+ ans3.getDenominator()+
                        " Reduced Fraction: "+ ans3.getReducedNumerator()+"/" + ans3.getReducedDenominator());
                Float clientScore4 = clientsScores.get(userID);
                clientsScores.put(userID, clientScore4+1);
                System.out.println("Fraction Reducers:");
                for(UUID task : tasksIDs.get(taskType)){
                    System.out.print("UID: "+task);
                    UserData next = (UserData) workers.get(task);
                    System.out.println(" Input: "+ next.getStuff() + " Output: "+ outputs.get(task));
                }

                break;
            default:
                break;
        }
    }

    private static List<String> toList(){
        List<String> list = new ArrayList<>();
        list.add("SortWorker");
        list.add("SumReducer");
        list.add("PrimeChecker");
        list.add("FractionReducer");
        return list;
    }

    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String host = "localhost";
            int port = 1099;
            List<String> list = toList();
            HashMap<String, Float > clientsScores = new HashMap<String, Float>() ;
            HashMap<String, List<UUID>> taskIDs = new HashMap<String, List<UUID>>();
            HashMap<UUID, Object> workers = new HashMap<UUID, Object>();
            HashMap<UUID, Object> outputs = new HashMap<UUID, Object>();
            List<UUID> SortWorkers = new ArrayList<UUID>();
            List<UUID> FractionReducers = new ArrayList<UUID>();
            List<UUID> SumReducers = new ArrayList<UUID>();
            List<UUID> PrimeCheckers = new ArrayList<UUID>();
            RemoteObject obj = new RemoteServer(list, clientsScores, taskIDs, workers, outputs,
            SortWorkers,FractionReducers, SumReducers, PrimeCheckers );
            switch (args.length) {
                case 2:
                    host = args[0];
                    port = Integer.parseInt(args[1]);
                case 1:
                    host = args[0];

                case 0:
                    break;
                default:
                    System.out.println("Usage: Server [RMIhost] [RMIport]");
                    System.exit(0);
            }
            Naming.rebind("rmi://" + host +
                    ":" + port +
                    "/ClientManager", obj);
            System.out.println("Server Ready");




        } catch (Exception e) {

            System.err.println("Server exception: " + e.toString());
            e.printStackTrace();

            }

        }

}
