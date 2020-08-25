/*
 Derek Trom 
 HW 2 deposit class for running BankAccount Package
 */
package BankAccount;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

public class Deposit implements Runnable{
    private Account account;

    public Deposit(Account acct) {
        account = acct;
    }
    

    @Override
    public void run(){
        AtomicInteger totalDeposited = new AtomicInteger(); //thread safe trackers
        AtomicInteger waitedDeposit= new AtomicInteger(); //thread safe trackers
        AtomicInteger timesDeposit= new AtomicInteger();
        int[] stuff ;
        String name = Thread.currentThread().getName(); // get thread name
        while(true) {
            try {
				//random number generated for deposit                
				int amount = ThreadLocalRandom.current().nextInt(10, 100);
                //store returned deposits and wait times
				stuff = account.deposit(amount);
                totalDeposited.getAndAdd(stuff[0]);
                waitedDeposit.getAndAdd(stuff[1]);
                timesDeposit.getAndAdd(stuff[2]);


            }
            catch(InterruptedException e){
                //when thread ends print totals
                System.out.println("Total Deposited by "+ name +" $"+ totalDeposited.get()+" Times Deposited: "+ timesDeposit.get() +" Waited: " + waitedDeposit.get());
                break;

            }
        }
    }

}
