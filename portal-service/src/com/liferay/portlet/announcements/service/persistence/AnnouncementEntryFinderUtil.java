/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.announcements.service.persistence;

/**
 * <a href="AnnouncementEntryFinderUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AnnouncementEntryFinderUtil {
	public static int countByC_C_F(long userId, long classNameId,
		long[] classPKs, int displayMonth, int displayDay, int displayYear,
		int expirationMonth, int expirationDay, int expirationYear, int flag,
		boolean alert) throws com.liferay.portal.SystemException {
		return getFinder()
				   .countByC_C_F(userId, classNameId, classPKs, displayMonth,
			displayDay, displayYear, expirationMonth, expirationDay,
			expirationYear, flag, alert);
	}

	public static int countByCNM_F(long userId,
		java.util.LinkedHashMap entriesParams, int displayMonth,
		int displayDay, int displayYear, int expirationMonth,
		int expirationDay, int expirationYear, int flag, boolean alert)
		throws com.liferay.portal.SystemException {
		return getFinder()
				   .countByCNM_F(userId, entriesParams, displayMonth,
			displayDay, displayYear, expirationMonth, expirationDay,
			expirationYear, flag, alert);
	}

	public static java.util.List findByC_C_F(long userId, long classNameId,
		long[] classPKs, int displayMonth, int displayDay, int displayYear,
		int expirationMonth, int expirationDay, int expirationYear, int flag,
		boolean alert, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getFinder()
				   .findByC_C_F(userId, classNameId, classPKs, displayMonth,
			displayDay, displayYear, expirationMonth, expirationDay,
			expirationYear, flag, alert, begin, end);
	}

	public static java.util.List findByCNM_F(long userId,
		java.util.LinkedHashMap entriesParams, int displayMonth,
		int displayDay, int displayYear, int expirationMonth,
		int expirationDay, int expirationYear, int flag, boolean alert,
		int begin, int end) throws com.liferay.portal.SystemException {
		return getFinder()
				   .findByCNM_F(userId, entriesParams, displayMonth,
			displayDay, displayYear, expirationMonth, expirationDay,
			expirationYear, flag, alert, begin, end);
	}

	public static AnnouncementEntryFinder getFinder() {
		return _getUtil()._finder;
	}

	public void setFinder(AnnouncementEntryFinder finder) {
		_finder = finder;
	}

	private static AnnouncementEntryFinderUtil _getUtil() {
		if (_util == null) {
			_util = (AnnouncementEntryFinderUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static final String _UTIL = AnnouncementEntryFinderUtil.class.getName();
	private static AnnouncementEntryFinderUtil _util;
	private AnnouncementEntryFinder _finder;
}