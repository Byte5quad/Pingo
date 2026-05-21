import com.model.Message;
public class Main {
	public static void main(String[] args) {
		System.out.println("Hello World!");
		Message testMessage = new Message("Test name", "This is a test message.");
		System.out.println(testMessage);
	}
}
