/**
* FractionReducer.java
 */
package server;

import api.Worker;

import java.util.UUID;

/**
 * @author Derek Trom
 *
 */
class FractionReducer extends Worker {
    private static final long serialVersionUID = -4051898108461870503L;
    private int numerator;
    private int denominator;
    private int reducedDenominator;
    private int reducednumerator;
    private int GCF;

    /**
     * @param id
     * @param numerator a number to be checked
     * @param denominator a number to be checked
     */
    FractionReducer(UUID id, int numerator, int denominator) {
        super(id, "FractionReducer");
        this.numerator = numerator;
        this.denominator = denominator;

    }


    public static int gcm(int a, int b) {
        if (b==0) return a;
        return gcm(b,a%b); // Not bad for one line of code :)
    }
    @Override
    public void doWork() {

        GCF = gcm(numerator, denominator);
        if (GCF== 1){
            reducednumerator = numerator;
            reducedDenominator = denominator;
        }
        else {
            reducednumerator = (numerator / GCF);
            reducedDenominator = (denominator / GCF);
        }
    }
    int getReducedNumerator(){return reducednumerator;}
    int getReducedDenominator(){return reducedDenominator;}
    int getGCF(){return GCF;}
    int getNumerator(){return numerator;}
    int getDenominator(){return denominator;}


    /**
     * Test harness
     * @param args There are no arguments.
     */
    public static void main(String[] args) {
        UUID uid = UUID.randomUUID();
        FractionReducer fr = new FractionReducer(uid, 30, 40 );
        fr.doWork();

    }
}

