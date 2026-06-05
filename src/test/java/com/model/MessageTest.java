package com.model;

import com.bytesquad.pingo.model.Message;
import com.bytesquad.pingo.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class MessageTest {

	private User user;
	private Message message;

	@BeforeEach
	void setup() {
		user = new User("Test Name", 12345);
		message = new Message(user, "Test Message: Hello World!");
	}

	/*
	 * Test Sender
	 */

	@Test
	void getSender_returnsSameUserObject() {
		assertSame(user, message.getSender());
	}

	@Test
	void getSender_returnsCorrectName() {
		assertEquals("Test Name", message.getSender().getName());
	}

	/*
	 * Test Message Content
	 */

	@Test
	void getMessageContent_returnsCorrectContent() {
		assertEquals("Test Message: Hello World!", message.getMessageContent());
	}

	@Test
	void getMessageContent_emptyString() {
		Message empty = new Message(user, "");
		assertEquals("", empty.getMessageContent());
	}

	/*
	 * Test Timestamp
	 */

	@Test
	void getTimestamp_isNotNull() {
		assertNotNull(message.getTimestamp());
	}

	@Test
	void getTimestamp_isRecentlyCreated() {
		LocalDateTime before =  LocalDateTime.now().minusSeconds(2);
		LocalDateTime after =  LocalDateTime.now().plusSeconds(2);
		assertTrue(message.getTimestamp().isBefore(after));
		assertTrue(message.getTimestamp().isAfter(before));
	}

	/*
	 * Test Formatted Time
	 */

	@Test
	void getFormattedTime_matchesBracketPatter() {
		// Expected format: [MM-dd-yyyy HH:mm:ss]
		String formatted = message.getFormattedTime();
		assertTrue(
				formatted.matches(
						"\\[\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}]"),
				"Formatted time did not match expected pattern: " + formatted);
	}

	/*
	 * Test toString()
	 */

	@Test
	void toString_containsSenderName() {
		assertTrue(message.toString().contains("Test Name"));
	}

	@Test
	void toString_containsMessageContent() {
		assertTrue(message.toString().contains("Test Message: Hello World!"));
	}

	@Test
	void toString_startsWithTimestamp() {
		assertTrue(message.toString().startsWith("["));
	}

	@Test
	void toString_followsExpectedFormat() {
		// Full format: [MM-dd-yyyy HH:mm:ss] Alice: Hello!
		String result = message.toString();
		assertTrue(
				result.matches(
						"\\[\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}] Test Name: Test Message: Hello World!"),
				"toString format unexpected: " + result);
	}
}
