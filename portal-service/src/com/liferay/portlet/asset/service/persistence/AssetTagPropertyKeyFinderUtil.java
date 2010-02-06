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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="AssetTagPropertyKeyFinderUtil.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class AssetTagPropertyKeyFinderUtil {
	public static int countByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		return getFinder().countByGroupId(groupId);
	}

	public static java.lang.String[] findByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		return getFinder().findByGroupId(groupId);
	}

	public static java.lang.String[] findByGroupId(long groupId, int start,
		int end) throws com.liferay.portal.SystemException {
		return getFinder().findByGroupId(groupId, start, end);
	}

	public static AssetTagPropertyKeyFinder getFinder() {
		if (_finder == null) {
			_finder = (AssetTagPropertyKeyFinder)PortalBeanLocatorUtil.locate(AssetTagPropertyKeyFinder.class.getName());
		}

		return _finder;
	}

	public void setFinder(AssetTagPropertyKeyFinder finder) {
		_finder = finder;
	}

	private static AssetTagPropertyKeyFinder _finder;
}