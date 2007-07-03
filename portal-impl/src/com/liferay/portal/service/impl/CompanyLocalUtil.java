/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.service.impl;

import com.liferay.portal.SystemException;
import com.liferay.portal.service.persistence.CompanyUtil;
import com.liferay.portal.util.ClusterPool;
import com.liferay.portal.util.comparator.CompanyComparator;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

import java.util.Collections;
import java.util.List;

/**
 * <a href="CompanyLocalUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class CompanyLocalUtil {

	public static final String GROUP_NAME = CompanyLocalUtil.class.getName();

	public static final String[] GROUP_NAME_ARRAY = new String[] {GROUP_NAME};

	public static void clearCompanies() {
		_cache.flushGroup(GROUP_NAME);
	}

	public static List getCompanies() throws SystemException {
		String key = GROUP_NAME;

		List companies = null;

		try {
			companies = (List)_cache.getFromCache(key);
		}
		catch (NeedsRefreshException nfe) {
			companies = CompanyUtil.findAll();

			Collections.sort(companies, new CompanyComparator());

			_cache.putInCache(key, companies, GROUP_NAME_ARRAY);
		}
		finally {
			if (companies == null) {
				_cache.cancelUpdate(key);
			}
		}

		return companies;
	}

	private static GeneralCacheAdministrator _cache = ClusterPool.getCache();

}