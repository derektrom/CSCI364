/*
 Derek Trom 
 HW 2 Main class for running BankAccount Package
 */

package BankAccount;


import java.util.ArrayList;
import java.util.List;

public class Main {

    public static int accountBalance; //create balance 
    public static void main(String[] args) {
        //error checking command line
		if (args.length != 4 || Integer.parseInt(args[0]) <= 0|| Integer.parseInt(args[1]) <= 0 ||
                Integer.parseInt(args[2]) <= 0){
            System.err.println(
                    "Usage: java -cp build BankAccount.Main <#deposit threads>   <#withdrawal threads> <account balance> <sleep time>");
            System.exit(1);
        }
		//getting all args from command line        
		int numWithdraw = Integer.parseInt(args[1]);
        int numDeposit = Integer.parseInt(args[0]);
        accountBalance = Integer.parseInt(args[2]);
        int sleepTime = Integer.parseInt(args[3]);
        int accountAmount = accountBalance;

        Account account = Account.getInstance(accountAmount); // singleton instance
        List <Thread> runnables = new ArrayList<Thread>();//list of threads
        Runnable[] deposits = new Runnable[numDeposit + numWithdraw]; //array of runnables
		//create deposit runnables
        for(int i = 0; i < numDeposit; i++) {
            deposits[i] = new Deposit(account);
            System.out.println("Deposit created: " + i);
        }
		//create withdraw runnables
        for(int i = numDeposit; i < deposits.length; i++) {
            deposits[i] = new Withdraw(account);
            System.out.println("Withdraw created: " + i);
        }
		//create threads and start threads 
        for (int i = 0; i < deposits.length; i++) {
            Thread t = new Thread(deposits[i]);
            deposits[i] = t;
            t.start();
            runnables.add(t);
            //Thread.currentThread().sleep(sleepTime);
            //t.interrupt();
        }
        try {
            Thread.sleep(sleepTime); //sleep thread
        }
        catch(InterruptedException e){
            System.err.println(e.getMessage());
        }
        System.out.println("Main thread waking up");
        //interrupt each thread after sleep
		for (Thread t : runnables){
            t.interrupt(); 
        }
        //join threads 
		for (Thread t : runnables){
            try{
                t.join();
            }
            catch(InterruptedException e){
                System.err.println(e.getMessage());
            }
        }
        System.out.println("Closing balance = $" + account.getBalance());

    }


}
