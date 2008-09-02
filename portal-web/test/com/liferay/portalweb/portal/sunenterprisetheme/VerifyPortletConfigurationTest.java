package com.liferay.portalweb.portal.sunenterprisetheme;

import com.liferay.portalweb.portal.BaseTestCase;


/**
  * @author Prajna
  */


public class VerifyPortletConfigurationTest extends BaseTestCase {
	public void sleep(int millis){
		try{
			Thread.sleep(millis);
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
	public void testVerifyPortletConfiguration(){
	    selenium.click("my-community-private-pages");
	    sleep(5000);
	    selenium.click("//span[. ='suntheme']");
        sleep(5000);
        selenium.click("//span[contains(. , 'Admin')]/../div/nobr/a/img[@title = 'Configuration']");
        sleep(5000);
        assertTrue(selenium.isElementPresent("//a[contains(. , 'Permissions')]"));
        assertTrue(selenium.isElementPresent("//a[contains(. , 'Export / Import')]"));
        assertTrue(selenium.isElementPresent("//a[contains(. , 'Sharing')]"));
	    selenium.click("link=Return to Full Page");
        sleep(5000); 
    }
}