package com.liferay.portalweb.portal.sunenterprisetheme;

import com.liferay.portalweb.portal.BaseTestCase;

/**
   * @author Prajna
  */
public class VerifyThumbnailImageTest extends BaseTestCase {
	public void sleep(int millis){
		try{
			Thread.sleep(millis);
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
	public void testVerifyThumbnailImage(){
	    selenium.click("link=Manage Pages");
		sleep(5000);
		selenium.click("link=Look and Feel");		
		sleep(5000);
        assertTrue(selenium.isElementPresent("//img[@src='/sun-enterprise-theme/images/thumbnail.png']"));
        sleep(5000);
    }
 }

                