 package com.liferay.portalweb.portal.sunenterprisetheme;

import com.liferay.portalweb.portal.BaseTestCase;


/**
  * @author Prajna
  */


public class VerifyPortletActionItemsTest extends BaseTestCase {
	public void sleep(int millis){
		try{
			Thread.sleep(millis);
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
	public void testVerifyPortletActionItems(){
	    selenium.click("my-community-private-pages");
        sleep(5000);
        selenium.click("//span[. ='suntheme']");
		sleep(5000);
	    assertTrue(selenium.isVisible("//span[contains(. , 'Admin')]/../div/nobr/a/img[@title = 'Look and Feel']"));
        assertTrue(selenium.isVisible("//span[contains(. , 'Admin')]/../div/nobr/a/img[@title = 'Configuration']"));
        assertTrue(selenium.isVisible("//span[contains(. , 'Admin')]/../div/nobr/a/img[@title = 'Minimize']"));
        assertTrue(selenium.isVisible("//span[contains(. , 'Admin')]/../div/nobr/a/img[@title = 'Maximize']"));
        assertTrue(selenium.isVisible("//span[contains(. , 'Admin')]/../div/nobr/a/img[@title = 'Remove']"));
    }
}	 