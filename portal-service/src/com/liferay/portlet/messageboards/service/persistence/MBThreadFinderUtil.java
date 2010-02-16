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

package com.liferay.portlet.messageboards.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="MBThreadFinderUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class MBThreadFinderUtil {
	public static int countByS_G_U_S(long groupId, long userId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByS_G_U_S(groupId, userId, status);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBThread> findByS_G_U_S(
		long groupId, long userId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByS_G_U_S(groupId, userId, status, start, end);
	}

	public static MBThreadFinder getFinder() {
		if (_finder == null) {
			_finder = (MBThreadFinder)PortalBeanLocatorUtil.locate(MBThreadFinder.class.getName());
		}

		return _finder;
	}

	public void setFinder(MBThreadFinder finder) {
		_finder = finder;
	}

	private static MBThreadFinder _finder;
}