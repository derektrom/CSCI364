/**
 * PrimeChecker.java
 */
package server;

import api.Worker;

import java.util.UUID;

/**
 * @author Derek Trom
 *
 */
class PrimeChecker extends Worker {
    private static final long serialVersionUID = -4051898108461870503L;
    private int number;
    private boolean prime;
    /**
     * @param id
     * @param number a number to be checked
     */
    PrimeChecker(UUID id, int number) {
        super(id, "PrimeChecker");
        this.number = number;
        this.prime = true;
    }


    @Override
    public void doWork() {

        if (number <= 1)
            prime = false;

        // Check from 2 to n-1
        for (int i = 2; i < number; i++)
            if (number % i == 0)
                prime = false;

    }
    boolean getPrime(){return prime;}


    /**
     * Test harness
     * @param args There are no arguments.
     */
    public static void main(String[] args) {
        UUID uid = UUID.randomUUID();
        PrimeChecker pc = new PrimeChecker(uid, 30 );
        pc.doWork();

    }
}
