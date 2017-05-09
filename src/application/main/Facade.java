package application.main;

import application.MainApp;


/**
 * 
 * Facade class to manage design pattern
 * 
 */


public class Facade {

	
	private String username;
	private String password;
	
	MainApp main;
	Owner owner;
	Customer customer;
	Admin admin;
	
	public Facade(String name, String pass){
		
		username = name;
		password = pass;		
		main = new MainApp();
		owner = new Owner();
		customer = new Customer();
		admin = new Admin();		
	}
	
	public String getUser(){
		return username;
	}
	public String getPass(){
		return password;
	}
	
	//checks if the entries are for owner, customer or admin login
	// according to that it displays the corresponding view\controller
	
	public boolean login(String user, String pass){
		
		boolean logged = false;
		if(owner.checkLogin(user, pass)){
			main.setUsername(user);
			main.showOwnerHomePage();
			return true;
		}else if (customer.checkLogin(user, pass)) {
			main.setUsername(user);
			main.showChooseBusinessPage();
			return true;
		}else if (admin.checkLogin(user, pass)) {
			main.showNewBusinessPage();
			return true;
		}else{
			return false;
		}
		
	}
	
}
