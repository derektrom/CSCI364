/*
 Derek Trom 
 HW 2 Account class for running BankAccount Package
 */
package BankAccount;

import java.util.concurrent.atomic.AtomicInteger;

public class Account {
    private int balance;
	//singelton creation
    private volatile static Account instance = null;
    private Account(int bal) { balance = bal; }
    public static Account getInstance(int balance){
        if (instance == null){
            synchronized (Account.class){
                if (instance == null){
                    instance = new Account(balance);
                }
            }
        }
        return instance;
    }
    public synchronized int getBalance() { return balance; } //get balance 

    public synchronized int[] deposit(int amt) throws InterruptedException {
        AtomicInteger totalDeposited = new AtomicInteger();//thread safe trackers
        AtomicInteger waitedDeposit= new AtomicInteger();
        AtomicInteger timesDeposit= new AtomicInteger();
        int waited = 0;
        int[] stuff = new int[3];
        String name = Thread.currentThread().getName();
		//max to not overflow account        
		int maxBalance = 2 * Main.accountBalance;
       if ((getBalance() + amt) > (maxBalance)) {
           waitedDeposit.getAndAdd(1);
           while ((getBalance() + amt) > (maxBalance)) {
               //wait while more than double balance or if deposit will cause overflow
               System.out.println("Deposit " + name + " waiting balance too high");
               wait(); //thread wait
               stuff[2] = timesDeposit.get();
               stuff[1] = waitedDeposit.get();
               stuff[0] = totalDeposited.get();
               return stuff;

           }
       }

        Thread.sleep(100); // simulate consumption time
        int temp = balance;
        temp = temp + amt;
        totalDeposited.getAndAdd(amt) ;//increment total
        timesDeposit.getAndAdd(1);
        balance = temp;
        System.out.println("After "+ name+ " deposited $"+amt+" balance = $" + getBalance());
        stuff[2] = timesDeposit.get();
        stuff[1] = waitedDeposit.get();
        stuff[0] = totalDeposited.get();
        notifyAll();//notify all threads that it has left the critical section
        return stuff;

    }
    public synchronized int[] withdraw(int amt) throws InterruptedException{
        AtomicInteger totalWithdrawn = new AtomicInteger(); //thread safe tracker
        AtomicInteger timesWithDrawn = new AtomicInteger();
        AtomicInteger waitedWithdrawn = new AtomicInteger();
        AtomicInteger waited = new AtomicInteger();
        String name = Thread.currentThread().getName(); //get thread name
        int minBalance = 0;
        int wait  = 0;
        int[] stuff = new int[3];
        if ((getBalance() - amt) <= minBalance){
            waitedWithdrawn.getAndAdd(1);
            while ((getBalance() - amt) <= minBalance) {
                //wait if withdrawal will put below $0
                //waitedWithdrawn.getAndAdd(1);
                System.out.println("Withdraw "+ name+ " waiting balance too low");
                wait();
                stuff[2] = timesWithDrawn.get();
                stuff[1] = waitedWithdrawn.get();//increment waited times
                stuff[0] = totalWithdrawn.get();
                return stuff;
            }
        }


        Thread.sleep(100); // simulate consumption time
        int temp = balance;
        temp = temp - amt;
        //System.out.println(amt);
        totalWithdrawn.getAndAdd(amt); //increment total amount withdrawn for runnable
        timesWithDrawn.getAndAdd(1);



        balance = temp;
        System.out.println("After "+name+ " withdrew $"+amt+ " balance = $" + getBalance());
        stuff[2] = timesWithDrawn.get();
        stuff[1] = waitedWithdrawn.get();//increment waited times
        stuff[0] = totalWithdrawn.get();
        notifyAll(); //notify all threads it has left section
        return stuff;
    }
}


