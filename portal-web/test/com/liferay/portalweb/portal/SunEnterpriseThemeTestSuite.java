
package com.liferay.portalweb.portal;

import com.liferay.portalweb.portal.login.LoginTests;
import com.liferay.portalweb.portal.sunenterprisetheme.SunEnterpriseThemeTests;

/**
 * <a href="SunEnterpriseThemeTestSuite.java.html"><b><i>View Source</i></b></a>
 *
 * @author Prajna
 *
 */
public class SunEnterpriseThemeTestSuite extends BaseTests {

	public SunEnterpriseThemeTestSuite() {
		addTestSuite(LoginTests.class);
		addTestSuite(SunEnterpriseThemeTests.class);
	    addTestSuite(StopSeleniumTest.class);
	}

}