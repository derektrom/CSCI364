/**
 * SumReducer.java
 */
package server;

import api.Worker;

import java.util.UUID;


/**
 * @author Derek Trom
 *
 */
public class SumReducer extends Worker {
    private static final long serialVersionUID = -4051898108461870504L;
    private String type ;
    private int integerSum;
    private float floatSum;
    private double doubleSum;
    private Integer[] integerList;
    private Float[] floatList;
    private Double[] doubleList;

    public String findType(){return type;}


    SumReducer(UUID id, Integer[] list) {
        super(id, "SumReducer");
        integerList = list;
        type = "Int";
    }
    SumReducer(UUID id, Double[] list) {
        super(id, "SumReducer");
        doubleList = list;
        type = "Double";
    }
    SumReducer(UUID id, Float[] list) {
        super(id, "SumReducer");
        floatList = list;
        type = "Float";
    }


    @Override
    public void doWork() {


        switch (type) {
            case "Int":
                integerSum = 0;
                for (int i : integerList) {
                    integerSum += i;
                }
                break;
            case "Double":
                doubleSum = (double) 0;
                for (double i : doubleList) {
                    doubleSum += i;
                }
                break;
            case "Float":
                floatSum = (float)0;
                for(float i : floatList){
                    floatSum += i;
                }
                break;

        }
    }
    public Object getAnswer(){
        switch (type){
            case "Int":
                return integerSum;
            case "Float":
                return floatSum;
            case "Double":
                return doubleSum;
            default:
                return null;
        }
    }




    /**
     * Test harness
     * @param args There are no arguments.
     */
    public static void main(String[] args) {
        UUID uid = UUID.randomUUID();
        SortWorker<String> sw1 = new SortWorker<String>(uid, "cat", "mouse", "dog");
        sw1.doWork();
        System.out.println(sw1.getList());

        SortWorker<Integer> sw2 = new SortWorker<Integer>(uid, 3, 1, 5, 4);
        sw2.doWork();
        System.out.println(sw2.getList());
    }
}