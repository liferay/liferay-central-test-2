/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.blogs.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="BlogsEntryFinderUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class BlogsEntryFinderUtil {
	public static int countByOrganizationId(long organizationId,
		java.util.Date displayDate, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .countByOrganizationId(organizationId, displayDate, status);
	}

	public static int countByOrganizationIds(
		java.util.List<Long> organizationIds, java.util.Date displayDate,
		int status) throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .countByOrganizationIds(organizationIds, displayDate, status);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByOrganizationId(
		long organizationId, java.util.Date displayDate, int status, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findByOrganizationId(organizationId, displayDate, status,
			start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByOrganizationIds(
		java.util.List<Long> organizationIds, java.util.Date displayDate,
		int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findByOrganizationIds(organizationIds, displayDate, status,
			start, end);
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> findByNoAssets()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByNoAssets();
	}

	public static BlogsEntryFinder getFinder() {
		if (_finder == null) {
			_finder = (BlogsEntryFinder)PortalBeanLocatorUtil.locate(BlogsEntryFinder.class.getName());
		}

		return _finder;
	}

	public void setFinder(BlogsEntryFinder finder) {
		_finder = finder;
	}

	private static BlogsEntryFinder _finder;
}