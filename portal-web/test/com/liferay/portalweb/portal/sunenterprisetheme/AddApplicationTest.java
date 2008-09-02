package com.liferay.portalweb.portal.sunenterprisetheme;

import com.liferay.portalweb.portal.BaseTestCase;


/**
  * @author Prajna
  */

public class AddApplicationTest extends BaseTestCase {
	public void sleep(int millis){
		try{
			Thread.sleep(millis);
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
	public void testAddApplication(){
	    selenium.click("my-community-private-pages");
		sleep(5000);
		selenium.click("//span[. ='suntheme']");
		sleep(5000);
		selenium.click("link=Add Application");
		sleep(5000);
		selenium.click("//div[@id = 'Admin-Admin']/p/a");
		sleep(5000);
		selenium.click("link=X");
		sleep(5000);
        assertTrue(selenium.isElementPresent("//span[contains(. , 'Admin')]"));
	}
}
