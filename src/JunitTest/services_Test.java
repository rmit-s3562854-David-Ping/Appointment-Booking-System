package JunitTest;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import application.main.Utility;

/**
 *  test if the service provided has a vaild name and a duration that is available
 *  the durations available for services are : 30, 60, 90, 120, 150, 180 by minutes
 *  the name could be any string but empty
 *  if any of those data is wrong or not provided service won't valid
 */

public class services_Test {
	
	private Utility util;
	
	@Before
	public void before(){
		util = new Utility();
	}
	
	public boolean addService(String name, int duration){
		String half = "30";
		String hr = "60";
		String hr1min30 = "90";
		String hr2 = "120";
		String hr2min30 = "150";
		String hr3 = "180";
		int i = 30;
		int x = 0;
		String opt = String.valueOf(duration);
		
			if(util.checkString(name) == false){
				return false;
			}
		
			if (opt.equals(half)){
				x = i*1;
				return true;
			}
			if (opt.equals(hr)){
				x = i*2;
				return true;
			}
			if (opt.equals(hr1min30)){
				x = i*3;
				return true;
			}
			if (opt.equals(hr2)){
				x = i*4;
				return true;
			}
			if (opt.equals(hr2min30)){
				x = i*5;
				return true;
			}
			if (opt.equals(hr3)){
				x = i*6;
				return true;
			}			
			return false;		
	}
	
	@Test
	public void addService_test() {
		assertTrue("valid name and duration", addService("back massage",30 ));
	}
	@Test
	public void addService_test2() {
		assertTrue("valid name and duration", addService("neck massage",90 ));
	}	
	@Test
	public void addService_test3() {
		assertFalse("invalid duration", addService("back massage",40 ));
	}
	@Test
	public void addService_test4() {
		assertFalse("invalid name", addService("",30 ));
	}	
	@Test
	public void addService_test5() {
		assertFalse("invalid name and duration", addService("",45 ));
	}
	@Test
	public void addService_test6() {
		assertFalse("invalid null", addService(null,45 ));
	}
	@Test
	public void addService_test7() {
		assertFalse("invalid name and duration", addService("", 0 ));
	}

}
