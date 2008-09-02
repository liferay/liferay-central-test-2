package com.liferay.portalweb.portal.sunenterprisetheme;

import com.liferay.portalweb.portal.BaseTestCase;


/**
 * @author Prajna
  */


public class SelectSunThemeTest extends BaseTestCase {
    public void sleep(int millis){
		try{
			Thread.sleep(millis);
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
	public void testSelectSunTheme(){
		selenium.click("my-community-private-pages");
		sleep(5000);
		selenium.click("//div[@id='add-page']/a/span");
		sleep(5000);
		selenium.type("new_page", "suntheme");
		selenium.click("link=Save");
		sleep(5000);
		selenium.click("//span[.='suntheme']");
		sleep(5000);
		selenium.click("link=Manage Pages");
		sleep(5000);
		selenium.click("//li[@id='_88_tabs3look-and-feelTabsId']/a");
		sleep(5000);
		selenium.click("//input[@name='_88_themeId' and @value='sunenterprise_WAR_sunenterprisetheme']");
		sleep(5000);
		selenium.click("link=Return to Full Page");
		sleep(5000);
	}
}
