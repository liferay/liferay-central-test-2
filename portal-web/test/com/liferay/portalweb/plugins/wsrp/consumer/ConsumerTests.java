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

package com.liferay.portalweb.plugins.wsrp.consumer;

import com.liferay.portalweb.plugins.wsrp.consumer.addconsumerblogsportlet.AddConsumerBlogsPortletTests;
import com.liferay.portalweb.plugins.wsrp.consumer.addconsumermbportlet.AddConsumerMBPortletTests;
import com.liferay.portalweb.plugins.wsrp.consumer.manageportletconsumerblogsportlet.ManagePortletConsumerBlogsPortletTests;
import com.liferay.portalweb.plugins.wsrp.consumer.manageportletconsumermbportlet.ManagePortletConsumerMBPortletTests;
import com.liferay.portalweb.portal.BaseTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * <a href="ConsumerTests.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ConsumerTests extends BaseTests {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(AddConsumerBlogsPortletTests.suite());
		testSuite.addTest(AddConsumerMBPortletTests.suite());
		testSuite.addTest(ManagePortletConsumerBlogsPortletTests.suite());
		testSuite.addTest(ManagePortletConsumerMBPortletTests.suite());

		return testSuite;
	}

}