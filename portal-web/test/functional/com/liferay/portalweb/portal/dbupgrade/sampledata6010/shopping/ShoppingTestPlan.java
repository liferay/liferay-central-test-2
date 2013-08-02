/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal.dbupgrade.sampledata6010.shopping;

import com.liferay.portalweb.portal.BaseTestSuite;
import com.liferay.portalweb.portal.dbupgrade.sampledata6010.shopping.category.CategoryTests;
import com.liferay.portalweb.portal.dbupgrade.sampledata6010.shopping.coupon.CouponTests;
import com.liferay.portalweb.portal.dbupgrade.sampledata6010.shopping.item.ItemTests;
import com.liferay.portalweb.portal.dbupgrade.sampledata6010.shopping.order.OrderTests;

import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class ShoppingTestPlan extends BaseTestSuite {

	public static Test suite() {
		TestSuite testSuite = new TestSuite();

		testSuite.addTest(CategoryTests.suite());
		testSuite.addTest(CouponTests.suite());
		testSuite.addTest(ItemTests.suite());
		testSuite.addTest(OrderTests.suite());

		return testSuite;
	}

}