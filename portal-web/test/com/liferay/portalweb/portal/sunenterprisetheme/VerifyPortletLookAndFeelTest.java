package com.liferay.portalweb.portal.sunenterprisetheme;

import com.liferay.portalweb.portal.BaseTestCase;


/**
  * @author Prajna
  */


public class VerifyPortletLookAndFeelTest extends BaseTestCase {
	public void sleep(int millis){
		try{
			Thread.sleep(millis);
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
	public void testVerifyPortletLookAndFeel(){
	    selenium.click("my-community-private-pages");
	    sleep(5000);
	    selenium.click("//span[. ='suntheme']");
        sleep(5000);
        selenium.click("//span[contains(. , 'Admin')]/../div/nobr/a/img[@title = 'Look and Feel']");
        sleep(5000);
        assertTrue(selenium.isElementPresent("//legend[contains(. ,'Portlet Configuration')]"));
	    selenium.click("link=X");
        sleep(5000); 
    }
}