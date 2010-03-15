/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portalweb.plugins.wsrp.portlet.viewportletwsrpmb;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="ViewPortletWSRPMBTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ViewPortletWSRPMBTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(AddPageMBTest.class);
		testSuite.addTestSuite(AddPortletMBTest.class);
		testSuite.addTestSuite(AddMBCategoryTest.class);
		testSuite.addTestSuite(AddMBCategoryMessageTest.class);
		testSuite.addTestSuite(AddProducerMBPortletTest.class);
		testSuite.addTestSuite(AddConsumerMBPortletTest.class);
		testSuite.addTestSuite(ManagePortletConsumerMBPortletTest.class);
		testSuite.addTestSuite(AddPageWSRPMBTest.class);
		testSuite.addTestSuite(AddPortletWSRPMBTest.class);
		testSuite.addTestSuite(ViewPortletWSRPMBTest.class);
		testSuite.addTestSuite(TearDownMBCategoryTest.class);
		testSuite.addTestSuite(TearDownConsumerTest.class);
		testSuite.addTestSuite(TearDownProducerTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}

}