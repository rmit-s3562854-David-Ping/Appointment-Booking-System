package application.main;

import java.util.ArrayList;
import java.util.logging.Logger;

import application.MainApp;

public class Owner extends Member {

	/**
	 * Owner class, contains attributes and behaviors of owner
	 * 
	 * @version 1.00 05 Apr 2017
	 * @author David Ping, Hassan Mender, Luke Waldren
	 */

	private static final Logger LOGGER = Logger.getLogger("MyLog");
	private String businessName;

	public Owner() {
		super(null, null, null, null, null, null);
	}

	public Owner(String username, String password, String firstName, String lastName, String address,
			String contactNumber, String businessName) {
		super(username, password, firstName, lastName, address, contactNumber);
		this.businessName = businessName;
	}
	public Owner(String username, String password, String businessName) {
		super(username, password, "null", "null", "null", "null");
		this.businessName = businessName;
	}
	public  String getBusinessName() {
		return businessName;
	}
	
	public Boolean checkLogin(String username, String password){
		MainApp main = new MainApp();
		ArrayList<String> MembersSearch = new ArrayList<String>();
		ArrayList<Owner> ownerArray = main.getOwnerArray();
		int index = 0;
		while (index < ownerArray.size()) {
			MembersSearch.add(ownerArray.get(index).getUsername() + ownerArray.get(index).getPassword());
			index++;
		}

	      
	    //Gets the business name and passes it to the reader class.
		if (MembersSearch.contains(username + password)){
			Reader reader = new Reader();
    		reader.readBusiness(ownerArray.get(MembersSearch.indexOf(username+password)).getBusinessName());
			return true;
		}
		return false;
	}


}
