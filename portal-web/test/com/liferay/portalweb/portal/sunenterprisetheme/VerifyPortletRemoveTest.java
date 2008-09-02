package com.liferay.portalweb.portal.sunenterprisetheme;

import com.liferay.portalweb.portal.BaseTestCase;


/**
  * @author Prajna
 */


public class VerifyPortletRemoveTest extends BaseTestCase {
	public void sleep(int millis){
		try{
			Thread.sleep(millis);
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
	public void testVerifyPortletRemove(){
	    selenium.click("my-community-private-pages");
	    sleep(5000);
	    selenium.click("//span[. ='suntheme']");
        sleep(5000);
		selenium.chooseOkOnNextConfirmation();
	    selenium.click("//span[contains(. , 'Admin')]/../div/nobr/a/img[@title ='Remove']");
		if(selenium.isConfirmationPresent())
			selenium.getConfirmation();
			
        sleep(5000);
        assertFalse(selenium.isElementPresent("//span[contains(. , 'Admin')]"));
 
    }
}