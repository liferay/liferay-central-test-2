package com.liferay.portalweb.portal.sunenterprisetheme;

import com.liferay.portalweb.portal.BaseTestCase;


/**
  * @author Prajna
  */


public class VerifyPortletMaximizeTest extends BaseTestCase {
	public void sleep(int millis){
		try{
			Thread.sleep(millis);
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
	public void testVerifyPortletMaximize(){
	    selenium.click("my-community-private-pages");
	    sleep(5000);
	    selenium.click("//span[. ='suntheme']");
        sleep(5000);
        selenium.click("//span[contains(. , 'Admin')]/../div/nobr/a/img[@title ='Maximize']");
		sleep(5000);
        selenium.click("link=Return to Full Page");
        sleep(5000); 
    }
}