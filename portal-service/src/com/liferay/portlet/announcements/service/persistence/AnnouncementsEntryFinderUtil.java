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
 * <a href="AnnouncementsEntryFinderUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AnnouncementsEntryFinderUtil {
	public static int countByScope(long userId, long classNameId,
		long[] classPKs, int displayMonth, int displayDay, int displayYear,
		int expirationMonth, int expirationDay, int expirationYear,
		boolean alert, int flagValue) throws com.liferay.portal.SystemException {
		return getFinder()
				   .countByScope(userId, classNameId, classPKs, displayMonth,
			displayDay, displayYear, expirationMonth, expirationDay,
			expirationYear, alert, flagValue);
	}

	public static int countByScopes(long userId,
		java.util.LinkedHashMap scopes, int displayMonth, int displayDay,
		int displayYear, int expirationMonth, int expirationDay,
		int expirationYear, boolean alert, int flagValue)
		throws com.liferay.portal.SystemException {
		return getFinder()
				   .countByScopes(userId, scopes, displayMonth, displayDay,
			displayYear, expirationMonth, expirationDay, expirationYear, alert,
			flagValue);
	}

	public static java.util.List findByScope(long userId, long classNameId,
		long[] classPKs, int displayMonth, int displayDay, int displayYear,
		int expirationMonth, int expirationDay, int expirationYear,
		boolean alert, int flagValue, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getFinder()
				   .findByScope(userId, classNameId, classPKs, displayMonth,
			displayDay, displayYear, expirationMonth, expirationDay,
			expirationYear, alert, flagValue, begin, end);
	}

	public static java.util.List findByScopes(long userId,
		java.util.LinkedHashMap scopes, int displayMonth, int displayDay,
		int displayYear, int expirationMonth, int expirationDay,
		int expirationYear, boolean alert, int flagValue, int begin, int end)
		throws com.liferay.portal.SystemException {
		return getFinder()
				   .findByScopes(userId, scopes, displayMonth, displayDay,
			displayYear, expirationMonth, expirationDay, expirationYear, alert,
			flagValue, begin, end);
	}

	public static AnnouncementsEntryFinder getFinder() {
		return _getUtil()._finder;
	}

	public void setFinder(AnnouncementsEntryFinder finder) {
		_finder = finder;
	}

	private static AnnouncementsEntryFinderUtil _getUtil() {
		if (_util == null) {
			_util = (AnnouncementsEntryFinderUtil)com.liferay.portal.kernel.bean.BeanLocatorUtil.locate(_UTIL);
		}

		return _util;
	}

	private static final String _UTIL = AnnouncementsEntryFinderUtil.class.getName();
	private static AnnouncementsEntryFinderUtil _util;
	private AnnouncementsEntryFinder _finder;
}