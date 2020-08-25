/*
 Derek Trom 
 HW 2 Withdraw class for running BankAccount Package
 */
package BankAccount;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

class Withdraw implements Runnable {
    private Account account; //account
    public Withdraw(Account acct) {
        account = acct;

    }
    @Override //override run method 
    public void run() {
        AtomicInteger totalWithdrawn = new AtomicInteger(); //thread safe trackers
        AtomicInteger waitedWithdrawn= new AtomicInteger(); //thread safe trackers
        AtomicInteger timesWithDrawn = new AtomicInteger();
        int[] stuff ;
        String name = Thread.currentThread().getName(); //get thread name
        while(true) {
            try {
                int amount = ThreadLocalRandom.current().nextInt(10, 100);
                stuff = account.withdraw(amount); //store returned values from deposited
                totalWithdrawn.getAndAdd(stuff[0]);
                waitedWithdrawn.getAndAdd(stuff[1]);
                timesWithDrawn.getAndAdd(stuff[2]);

                }
            catch(InterruptedException e){
                //print all required stats
                System.out.println("Total Withdrawn by " + name +" $"+ totalWithdrawn.get() +" Times Withdrew: "+timesWithDrawn.get()+ " Waited: " + waitedWithdrawn.get());
                break;

            }
        }
    }
}
