package com.model;

import java.io.Serial;
import java.io.Serializable;

public class User implements Serializable {
	@Serial
	private static final long serialVersionUID = 1L;

	private String name;
	private int id;

	public User(String name, int id) {
		this.name = name;
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	@Override
	public String toString() {
		return "User: [name=" + name + " , id=" + id + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o instanceof User user) {
			return this.name.equals(user.name) && this.id == user.id;
		}
		return false;
	}

	@Override
	public int hashCode() {
		return Integer.hashCode(this.id);
	}
}
