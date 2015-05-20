/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.store;

import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.test.rule.MainServletTestRule;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.documentlibrary.store.test.BaseStoreTestCase;

import org.junit.Assume;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Rule;

/**
 * @author Preston Crary
 */
public class S3StoreTest extends BaseStoreTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(), MainServletTestRule.INSTANCE);

	@BeforeClass
	public static void setUpClass() throws Exception {
		Assume.assumeTrue(
			"Property \"" + PropsKeys.DL_STORE_S3_ACCESS_KEY + "\" is not set",
			Validator.isNotNull(
				PropsUtil.get(PropsKeys.DL_STORE_S3_ACCESS_KEY)));
		Assume.assumeTrue(
			"Property \"" + PropsKeys.DL_STORE_S3_SECRET_KEY + "\" is not set",
			Validator.isNotNull(
				PropsUtil.get(PropsKeys.DL_STORE_S3_SECRET_KEY)));
		Assume.assumeTrue(
			"Property \"" + PropsKeys.DL_STORE_S3_BUCKET_NAME + "\" is not set",
			Validator.isNotNull(
				PropsUtil.get(PropsKeys.DL_STORE_S3_BUCKET_NAME)));
	}

	@Override
	protected Store getStore() {
		return new S3Store();
	}

}