package com.liferay.portalweb.portal.sunenterprisetheme;

import com.liferay.portalweb.portal.BaseTestCase;


/**
  * @author Prajna
  */


public class AddPageTest extends BaseTestCase {
	public void sleep(int millis){
		try{
			Thread.sleep(millis);
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
	public void testAddPage(){
		selenium.click("my-community-private-pages");
		sleep(5000);
		selenium.click("//span[. ='suntheme']");
		sleep(5000);
		selenium.click("//div[@id='add-page']/a/span");
		sleep(5000);
		selenium.type("new_page", "sunthemepage2");
		sleep(5000);
		selenium.click("link=Save");
		sleep(5000);
		selenium.click("//span[. ='sunthemepage2']");
		sleep(5000);
        assertTrue(selenium.isElementPresent("//span[. ='sunthemepage2']"));
	}
}