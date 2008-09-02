package com.liferay.portalweb.portal.sunenterprisetheme;

import com.liferay.portalweb.portal.BaseTestCase;


/**
  * @author Prajna
  */


public class LayoutTemplateTest extends BaseTestCase {
	public void sleep(int millis){
		try{
			Thread.sleep(millis);
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
	public void testLayoutTemplate(){
		selenium.click("my-community-private-pages");
		sleep(5000);
		selenium.click("//span[. ='suntheme']");
		sleep(5000);
		selenium.click("link=Layout Template");
		sleep(5000);
		selenium.click("layoutTemplateId1");
		sleep(5000);
		selenium.click("//input[@value='Save']");
		sleep(5000);
	}
}
