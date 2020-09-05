/**
 * RemoteClient.java
 */
package server;
/**
 * @author Derek Trom
 *
 */
public class UserData {
    private String task;
    private Object inputObject;

    public UserData(String task, Object inputObject){
        this.inputObject = inputObject;
        this.task = task;
    }
    public Object getStuff(){
        if (inputObject instanceof Float){
            return (float) inputObject;
        }
        else if(inputObject instanceof Double){
            return (double) inputObject;
        }
        else if(inputObject instanceof Integer){
            return (int) inputObject;
        }
        else {
            return inputObject;
        }
    }
}
