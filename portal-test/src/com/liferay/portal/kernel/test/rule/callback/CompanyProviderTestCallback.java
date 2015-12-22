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

package com.liferay.portal.kernel.test.rule.callback;

import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runner.Runner;

/**
 * @author Cristina González
 */
public class CompanyProviderTestCallback extends BaseTestCallback<Long, Long> {

	public static final CompanyProviderTestCallback INSTANCE =
			new CompanyProviderTestCallback();

	@Override
	public void afterClass(Description description, Long previousCompanyId) {
		CompanyThreadLocal.setCompanyId(previousCompanyId);
	}

	@Override
	public Long beforeClass(Description description) throws Throwable {
		Long previousCompanyId = CompanyThreadLocal.getCompanyId();

		CompanyThreadLocal.setCompanyId(TestPropsValues.getCompanyId());

		return previousCompanyId;
	}

}
