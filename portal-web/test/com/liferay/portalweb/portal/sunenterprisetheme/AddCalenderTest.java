package com.liferay.portalweb.portal.sunenterprisetheme;

import com.liferay.portalweb.portal.BaseTestCase;


/**
  * @author Prajna
  */

public class AddCalenderTest extends BaseTestCase {
	public void sleep(int millis){
		try{
			Thread.sleep(millis);
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
	public void testAddCalender(){
	    selenium.click("my-community-private-pages");
		sleep(5000);
		selenium.click("//span[. ='suntheme']");
		sleep(5000);
		selenium.click("link=Add Application");
		sleep(5000);
		selenium.click("//div[@id='Collaboration-Calendar']/p/a");
		sleep(5000);
		selenium.click("link=X");
		sleep(5000);
		assertTrue(selenium.isElementPresent("//span[contains(. ,'Calendar')]"));
		selenium.click("//input[@value='Add Event']");
		sleep(5000);
		selenium.type("_8_title", "SunThemes");
		sleep(5000);
		selenium.click("_8_allDayCheckbox");
		sleep(5000);
		selenium.select("_8_type", "label=Meeting");
		sleep(5000);
		selenium.click("//input[@value='Save']");
		sleep(5000);
        assertTrue(selenium.isElementPresent("//a[contains(. , 'SunThemes')]"));
		selenium.click("//a[contains(. , 'SunThemes')]");
		sleep(5000);
		selenium.click("link=Return to Full Page");
		sleep(5000);
	}
}
