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

package com.liferay.portlet.shopping.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="ShoppingItemFinderUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ShoppingItemFinderUtil {
	public static int countByG_C(long groupId, java.util.List<Long> categoryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByG_C(groupId, categoryIds);
	}

	public static int countByFeatured(long groupId, long[] categoryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByFeatured(groupId, categoryIds);
	}

	public static int countByKeywords(long groupId, long[] categoryIds,
		java.lang.String keywords)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countByKeywords(groupId, categoryIds, keywords);
	}

	public static int countBySale(long groupId, long[] categoryIds)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().countBySale(groupId, categoryIds);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> findByFeatured(
		long groupId, long[] categoryIds, int numOfItems)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findByFeatured(groupId, categoryIds, numOfItems);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> findByKeywords(
		long groupId, long[] categoryIds, java.lang.String keywords, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder()
				   .findByKeywords(groupId, categoryIds, keywords, start, end);
	}

	public static java.util.List<com.liferay.portlet.shopping.model.ShoppingItem> findBySale(
		long groupId, long[] categoryIds, int numOfItems)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getFinder().findBySale(groupId, categoryIds, numOfItems);
	}

	public static ShoppingItemFinder getFinder() {
		if (_finder == null) {
			_finder = (ShoppingItemFinder)PortalBeanLocatorUtil.locate(ShoppingItemFinder.class.getName());
		}

		return _finder;
	}

	public void setFinder(ShoppingItemFinder finder) {
		_finder = finder;
	}

	private static ShoppingItemFinder _finder;
}