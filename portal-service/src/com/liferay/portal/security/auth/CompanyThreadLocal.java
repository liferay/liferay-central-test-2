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

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.AutoResetThreadLocal;
import com.liferay.portal.model.CompanyConstants;

/**
 * <a href="CompanyThreadLocal.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class CompanyThreadLocal {

	public static long getCompanyId() {
		long companyId = _companyId.get();

		if (_log.isDebugEnabled()) {
			_log.debug("getCompanyId " + companyId);
		}

		return companyId;
	}

	public static void setCompanyId(long companyId) {
		if (_log.isDebugEnabled()) {
			_log.debug("setCompanyId " + companyId);
		}

		if (companyId > 0) {
			_companyId.set(companyId);
		}
		else {
			_companyId.set(CompanyConstants.SYSTEM);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CompanyThreadLocal.class);

	private static ThreadLocal<Long> _companyId =
		new AutoResetThreadLocal<Long>(CompanyConstants.SYSTEM);

}