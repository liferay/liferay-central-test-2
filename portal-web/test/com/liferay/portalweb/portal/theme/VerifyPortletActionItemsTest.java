 package com.liferay.portalweb.portal.theme;

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
	
	    selenium.click("//span[contains(. , 'Calendar')]/../div/nobr/a/img[@title = 'Look and Feel']");
        sleep(5000);
        assertTrue(selenium.isElementPresent("//legend[contains(. ,'Portlet Configuration')]"));
	    selenium.click("link=X");
        sleep(5000); 
		selenium.click("//span[contains(. , 'Calendar')]/../div/nobr/a/img[@title = 'Configuration']");
        sleep(5000);
	    assertTrue(selenium.isElementPresent("//a[contains(. , 'Permissions')]"));
        assertTrue(selenium.isElementPresent("//a[contains(. , 'Export / Import')]"));
        assertTrue(selenium.isElementPresent("//a[contains(. , 'Sharing')]"));
	    selenium.click("link=Return to Full Page");
        sleep(5000); 
		selenium.click("//span[contains(. , 'Calendar')]/../div/nobr/a/img[@title ='Minimize']");
        sleep(5000);
        selenium.click("//span[contains(. , 'Calendar')]/../div/nobr/a/img[@title = 'Restore']");
        sleep(5000); 
		selenium.click("//span[contains(. , 'Calendar')]/../div/nobr/a/img[@title ='Maximize']");
		sleep(5000);
        selenium.click("link=Return to Full Page");
        sleep(5000); 
		
	    
    }
}	 