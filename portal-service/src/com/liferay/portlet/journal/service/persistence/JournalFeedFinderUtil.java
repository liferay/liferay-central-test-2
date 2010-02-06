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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="JournalFeedFinderUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class JournalFeedFinderUtil {
	public static int countByKeywords(long companyId, long groupId,
		java.lang.String keywords) throws com.liferay.portal.SystemException {
		return getFinder().countByKeywords(companyId, groupId, keywords);
	}

	public static int countByC_G_F_N_D(long companyId, long groupId,
		java.lang.String feedId, java.lang.String name,
		java.lang.String description, boolean andOperator)
		throws com.liferay.portal.SystemException {
		return getFinder()
				   .countByC_G_F_N_D(companyId, groupId, feedId, name,
			description, andOperator);
	}

	public static int countByC_G_F_N_D(long companyId, long groupId,
		java.lang.String[] feedIds, java.lang.String[] names,
		java.lang.String[] descriptions, boolean andOperator)
		throws com.liferay.portal.SystemException {
		return getFinder()
				   .countByC_G_F_N_D(companyId, groupId, feedIds, names,
			descriptions, andOperator);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalFeed> findByKeywords(
		long companyId, long groupId, java.lang.String keywords, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getFinder()
				   .findByKeywords(companyId, groupId, keywords, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalFeed> findByC_G_F_N_D(
		long companyId, long groupId, java.lang.String feedId,
		java.lang.String name, java.lang.String description,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getFinder()
				   .findByC_G_F_N_D(companyId, groupId, feedId, name,
			description, andOperator, start, end, obc);
	}

	public static java.util.List<com.liferay.portlet.journal.model.JournalFeed> findByC_G_F_N_D(
		long companyId, long groupId, java.lang.String[] feedIds,
		java.lang.String[] names, java.lang.String[] descriptions,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException {
		return getFinder()
				   .findByC_G_F_N_D(companyId, groupId, feedIds, names,
			descriptions, andOperator, start, end, obc);
	}

	public static JournalFeedFinder getFinder() {
		if (_finder == null) {
			_finder = (JournalFeedFinder)PortalBeanLocatorUtil.locate(JournalFeedFinder.class.getName());
		}

		return _finder;
	}

	public void setFinder(JournalFeedFinder finder) {
		_finder = finder;
	}

	private static JournalFeedFinder _finder;
}