package com.liferay.portalweb.portal.sunenterprisetheme;

import com.liferay.portalweb.portal.BaseTestCase;


/**
  * @author Prajna
  */


public class VerifyPortletMinimizeTest extends BaseTestCase {
	public void sleep(int millis){
		try{
			Thread.sleep(millis);
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
	public void testVerifyPortletMinimize(){
		selenium.click("my-community-private-pages");
	    sleep(5000);
	    selenium.click("//span[. ='suntheme']");
        sleep(5000);
        selenium.click("//span[contains(. , 'Admin')]/../div/nobr/a/img[@title ='Minimize']");
        sleep(5000);
        selenium.click("//span[contains(. , 'Admin')]/../div/nobr/a/img[@title = 'Restore']");
        sleep(5000); 
    }
}