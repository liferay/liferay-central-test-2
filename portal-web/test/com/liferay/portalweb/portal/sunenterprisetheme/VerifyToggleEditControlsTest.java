 package com.liferay.portalweb.portal.sunenterprisetheme;

import com.liferay.portalweb.portal.BaseTestCase;


/**
  * @author Prajna
  */


public class VerifyToggleEditControlsTest extends BaseTestCase {
	public void sleep(int millis){
		try{
			Thread.sleep(millis);
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
	public void testVerifyToggleEditControls(){
		selenium.click("my-community-private-pages");
        sleep(5000);
        selenium.click("//span[. ='suntheme']");
		sleep(5000);
        selenium.click("//li[@class = 'toggle-controls']/a");
        sleep(5000);
        assertFalse(selenium.isVisible("//span[contains(.,'Admin')]/../div/nobr/a/img[@title = 'Look and Feel']"));
        assertFalse(selenium.isVisible("//span[contains(. , 'Admin')]/../div/nobr/a/img[@title = 'Configuration']"));
        assertFalse(selenium.isVisible("//span[contains(. , 'Admin')]/../div/nobr/a/img[@title = 'Minimize']"));
        assertFalse(selenium.isVisible("//span[contains(. , 'Admin')]/../div/nobr/a/img[@title = 'Maximize']"));
        assertFalse(selenium.isVisible("//span[contains(. , 'Admin')]/../div/nobr/a/img[@title = 'Remove']"));
        selenium.click("//li[@class = 'toggle-controls']/a");
        sleep(5000);
    }
}