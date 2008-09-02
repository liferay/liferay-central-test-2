package com.liferay.portalweb.portal.sunenterprisetheme;

import com.liferay.portalweb.portal.BaseTestCase;


/**
  * @author Prajna
  */


public class CleanUpTest extends BaseTestCase {
	public void sleep(int millis){
		try{
			Thread.sleep(millis);
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
	public void testCleanUp(){
		selenium.click("my-community-private-pages");
		sleep(5000);
		selenium.chooseOkOnNextConfirmation();
        selenium.click("//div[id('navigation')]/ul/li/a/span[contains(.,'suntheme')]/../../span");
		if(selenium.isConfirmationPresent())
			selenium.getConfirmation();
		
        sleep(5000);
        assertFalse(selenium.isElementPresent("//span[. ='suntheme']"));
    }
}
