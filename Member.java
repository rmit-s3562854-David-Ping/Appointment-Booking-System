package main;

import java.util.ArrayList;

public abstract class Member {

	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String address;
	private String contactNumber;

	public Member(String username, String password, String firstName, String lastName, String address,
			String contactNumber) {
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.contactNumber = contactNumber;
	}

	public abstract Boolean login();

	public Boolean register() {
		return null;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastname() {
		return lastName;
	}

	public String getAddress() {
		return address;
	}

	public String getContactNumber() {
		return contactNumber;
	}
}
