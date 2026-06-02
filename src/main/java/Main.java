import com.model.Message;
import com.model.User;

public class Main {
	public static void main(String[] args) {
		System.out.println("Hello World!");

		User user = new User("Test Name", 12345);
		Message testMessage = new Message(user, "This is a test message.");
		System.out.println(testMessage);
	}
}
