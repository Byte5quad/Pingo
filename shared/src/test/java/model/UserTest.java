package model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the User model class.
 * User equality is based on the user ID, which matters because the server
 * stores clients in a map keyed by ID and ChatController compares senders by User.
 */
public class UserTest {

    @Test
    void gettersReturnConstructorValues() {
        User user = new User("Alice", 7);
        assertEquals("Alice", user.getName());
        assertEquals(7, user.getId());
    }

    @Test
    void usersWithSameIdAreEqual() {
        User a = new User("Alice", 7);
        User b = new User("DifferentName", 7);
        // Equality is by ID only, so name does not matter.
        assertEquals(a, b);
    }

    @Test
    void usersWithDifferentIdsAreNotEqual() {
        User a = new User("Alice", 7);
        User b = new User("Alice", 8);
        assertNotEquals(a, b);
    }

    @Test
    void equalUsersHaveSameHashCode() {
        User a = new User("Alice", 7);
        User b = new User("Bob", 7);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    void setNameUpdatesName() {
        User user = new User("Alice", 7);
        user.setName("Alicia");
        assertEquals("Alicia", user.getName());
    }
}
