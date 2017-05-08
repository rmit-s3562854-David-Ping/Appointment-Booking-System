package application.main;

import java.util.ArrayList;
import java.util.logging.Logger;

import application.MainApp;

public class Admin extends Member {

	/**
	 * Admin class, contains attributes and behaviors of Admin
	 * 
	 * @version 1.00 08 May 2017
	 * @author Luke Waldren
	 */

	private static final Logger LOGGER = Logger.getLogger("MyLog");

	public Admin() {
		super(null, null, null, null, null, null);
	}

	public Admin(String username, String password, String firstName, String lastName, String address,
			String contactNumber) {
		super(username, password, firstName, lastName, address, contactNumber);
	}
	
	
	public Boolean checkLogin(String username, String password){
		System.out.println("here");
		MainApp main = new MainApp();
		ArrayList<String> MembersSearch = new ArrayList<String>();
		ArrayList<Admin> adminArray = main.getAdminArray();
		int index = 0;
		while (index < adminArray.size()) {
			MembersSearch.add(adminArray.get(index).getUsername() + adminArray.get(index).getPassword());
			index++;
		}
		if (MembersSearch.contains(username + password)){
			return true;
		}
		return false;
	}


}
