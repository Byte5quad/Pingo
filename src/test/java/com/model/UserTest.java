package com.model;

import com.bytesquad.pingo.model.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

	/*
	 * Test Constructors/Getters
	 */

	@Test
	void getName_returnsCorrectName() {
		User user = new User("Test Name", 12345);
		assertEquals("Test Name", user.getName());
	}

	@Test
	void getId_returnsCorrectId() {
		User user = new User("Test Name", 12345);
		assertEquals(12345, user.getId());
	}

	/*
	 * Test setName
	 */

	@Test
	void setName_updatesName() {
		User user = new User("Test Name", 12345);
		user.setName("New Name");
		assertEquals("New Name", user.getName());
	}

	@Test
	void setName_doesNotAffectId() {
		User user = new User("Test Name", 12345);
		user.setName("New Name");
		assertEquals(12345, user.getId());
	}

	/*
	 * Test equals
	 */

	@Test
	void equals_SameIdSameName_isEqual() {
		User a = new User("Test Name", 12345);
		User b = new User("Test Name", 12345);
		assertEquals(a, b);
	}

	@Test
	void equals_sameIdDifferentName_isEqual() {
		// Identity is tied to ID, not name
		User a = new User("Test Name", 12345);
		User b = new User("Name", 12345);
		assertEquals(a, b);
	}

	@Test
	void equals_differentId_isNotEqual() {
		User a = new User("New Name", 12345);
		User b = new User("New Name", 67890);
		assertNotEquals(a, b);
	}

	@Test
	void equals_sameReference_isEqual() {
		User a = new User("Test Name", 12345);
		assertEquals(a, a);
	}

	/*
	 * Test hashCode
	 */

	@Test
	void hashCode_equalUsers_haveSameHashCode() {
		User a = new User("Test Name", 12345);
		User b = new User("Test Name", 12345);
		assertEquals(a.hashCode(), b.hashCode());
	}

	/*
	 * Test toString
	 */

	@Test
	void toString_returnsUserInfo() {
		User user = new User("Test Name", 12345);
		assertEquals(
				"User: [name=Test Name, id=12345]",
				user.toString());
	}
}
