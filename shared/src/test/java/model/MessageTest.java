package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the Message model class.
 * These run without a server because they only test the data model.
 */
public class MessageTest {

    @Test
    void publicConstructorSetsPublicTypeAndChatRoom() {
        User sender = new User("Alice", 1);
        Message msg = new Message(sender, "hello everyone", "De Anza-Computer Science");

        assertEquals(Message.MessageType.PUBLIC, msg.getType());
        assertEquals(-1, msg.getRecipientId());
        assertEquals("De Anza-Computer Science", msg.getChatRoom());
        assertEquals("hello everyone", msg.getMessageContent());
        assertEquals(sender, msg.getSender());
    }

    @Test
    void privateConstructorSetsPrivateTypeAndRecipient() {
        User sender = new User("Alice", 1);
        Message msg = new Message(sender, "secret", 42);

        assertEquals(Message.MessageType.PRIVATE, msg.getType());
        assertEquals(42, msg.getRecipientId());
        assertEquals("secret", msg.getMessageContent());
    }

    @Test
    void privateMessageHasNoChatRoom() {
        Message msg = new Message(new User("Alice", 1), "secret", 42);
        assertNull(msg.getChatRoom());
    }

    @Test
    void timestampIsSetOnCreation() {
        Message msg = new Message(new User("Bob", 2), "hi", "De Anza-Math");
        assertNotNull(msg.getTimestamp());
    }

    @Test
    void formattedTimeMatchesExpectedPattern() {
        Message msg = new Message(new User("Bob", 2), "hi", "De Anza-Math");
        // Expected format: [MM-dd-yyyy HH:mm:ss]
        assertTrue(msg.getFormattedTime().matches("\\[\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}\\]"));
    }
}
