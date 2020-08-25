//Derek Trom
package cs364;

public class SingletonExample implements java.io.Serializable{
	private static SingletonExample instance = new SingletonExample();
	String name = "UND";
	
	private SingletonExample() {
		this.name = name;
		
	}

	public static SingletonExample getInstance() {
		return instance;
	}
	
	public String toString() {
		return name;
	}
	public Object readResolve() throws java.io.ObjectStreamException {
 		System.out.println("Resolving a serailized customer information with name, " + name );
 		return new SingletonExample().getInstance();
 	} 	
}
