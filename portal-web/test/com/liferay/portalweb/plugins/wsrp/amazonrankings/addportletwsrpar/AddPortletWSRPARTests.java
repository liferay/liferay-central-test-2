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

package com.liferay.portalweb.plugins.wsrp.amazonrankings.addportletwsrpar;

import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="AddPortletWSRPARTests.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class AddPortletWSRPARTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(AddProducerARTest.class);
		testSuite.addTestSuite(AddConsumerARTest.class);
		testSuite.addTestSuite(ManagePortletConsumerARTest.class);
		testSuite.addTestSuite(AddPageWSRPARTest.class);
		testSuite.addTestSuite(AddPortletWSRPARTest.class);
		testSuite.addTestSuite(TearDownConsumerTest.class);
		testSuite.addTestSuite(TearDownProducerTest.class);
		testSuite.addTestSuite(TearDownPageTest.class);

		return testSuite;
	}

}