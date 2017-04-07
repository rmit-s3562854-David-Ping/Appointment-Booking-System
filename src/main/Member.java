package main;

public abstract class Member {

	/**
	 * Member class, superclass of owners and customers, contain attributes that
	 * are inherited by both
	 *
	 * @version 1.00 05 Mar 2017
	 * @author David Ping
	 */

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

	public abstract Boolean login(String username, String password);

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
