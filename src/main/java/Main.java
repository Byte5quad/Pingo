import com.model.Message;
import com.model.User;

public class Main {
	public static void main(String[] args) {
		System.out.println("Hello World!");
		Message testMessage = new Message(new User("Test Name", 12345), "This is a test message.");
		System.out.println(testMessage);
	}
}
