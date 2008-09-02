package com.liferay.portalweb.portal.sunenterprisetheme;

import com.liferay.portalweb.portal.BaseTests;

/**
  * @author Prajna
  */
  
  
public class SunEnterpriseThemeTests extends BaseTests {

	public SunEnterpriseThemeTests() {
		addTestSuite(VerifyThumbnailImageTest.class);
		addTestSuite(SelectSunThemeTest.class);
		addTestSuite(VerifyDropDownItemsTest.class);
		addTestSuite(AddApplicationTest.class);
		addTestSuite(LayoutTemplateTest.class);
		addTestSuite(VerifyToggleEditControlsTest.class);
		addTestSuite(AddPageTest.class);
		addTestSuite(DeletePageTest.class);
		addTestSuite(VerifyPortletActionItemsTest.class);
		addTestSuite(VerifyPortletLookAndFeelTest.class);
		addTestSuite(VerifyPortletConfigurationTest.class);
		addTestSuite(VerifyPortletMinimizeTest.class);
		addTestSuite(VerifyPortletMaximizeTest.class);
		addTestSuite(VerifyPortletRemoveTest.class);
		addTestSuite(AddCalenderTest.class);
		addTestSuite(CleanUpTest.class);
	}
}