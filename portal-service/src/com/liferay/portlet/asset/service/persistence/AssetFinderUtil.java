/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

/**
 * <a href="AssetFinderUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AssetFinderUtil {
	public static int countAssets(long groupId, long[] classNameIds,
		boolean excludeZeroViewCount, java.util.Date publishDate,
		java.util.Date expirationDate)
		throws com.liferay.portal.SystemException {
		return getFinder()
				   .countAssets(groupId, classNameIds, excludeZeroViewCount,
			publishDate, expirationDate);
	}

	public static int countByAndTagIds(long groupId, long[] classNameIds,
		long[] tagIds, long[] notTagIds, boolean excludeZeroViewCount,
		java.util.Date publishDate, java.util.Date expirationDate)
		throws com.liferay.portal.SystemException {
		return getFinder()
				   .countByAndTagIds(groupId, classNameIds, tagIds, notTagIds,
			excludeZeroViewCount, publishDate, expirationDate);
	}

	public static int countByOrTagIds(long groupId, long[] classNameIds,
		long[] tagIds, long[] notTagIds, boolean excludeZeroViewCount,
		java.util.Date publishDate, java.util.Date expirationDate)
		throws com.liferay.portal.SystemException {
		return getFinder()
				   .countByOrTagIds(groupId, classNameIds, tagIds, notTagIds,
			excludeZeroViewCount, publishDate, expirationDate);
	}

	public static java.util.List<com.liferay.portlet.asset.model.Asset> findAssets(
		long groupId, long[] classNameIds, java.lang.String orderByCol1,
		java.lang.String orderByCol2, java.lang.String orderByType1,
		java.lang.String orderByType2, boolean excludeZeroViewCount,
		java.util.Date publishDate, java.util.Date expirationDate, int start,
		int end) throws com.liferay.portal.SystemException {
		return getFinder()
				   .findAssets(groupId, classNameIds, orderByCol1, orderByCol2,
			orderByType1, orderByType2, excludeZeroViewCount, publishDate,
			expirationDate, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.Asset> findByAndTagIds(
		long groupId, long[] classNameIds, long[] tagIds, long[] notTagIds,
		java.lang.String orderByCol1, java.lang.String orderByCol2,
		java.lang.String orderByType1, java.lang.String orderByType2,
		boolean excludeZeroViewCount, java.util.Date publishDate,
		java.util.Date expirationDate, int start, int end)
		throws com.liferay.portal.SystemException {
		return getFinder()
				   .findByAndTagIds(groupId, classNameIds, tagIds, notTagIds,
			orderByCol1, orderByCol2, orderByType1, orderByType2,
			excludeZeroViewCount, publishDate, expirationDate, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.Asset> findByOrTagIds(
		long groupId, long[] classNameIds, long[] tagIds, long[] notTagIds,
		java.util.Date publishDate, java.util.Date expirationDate)
		throws com.liferay.portal.SystemException {
		return getFinder()
				   .findByOrTagIds(groupId, classNameIds, tagIds, notTagIds,
			publishDate, expirationDate);
	}

	public static java.util.List<com.liferay.portlet.asset.model.Asset> findByOrTagIds(
		long groupId, long[] classNameIds, long[] tagIds, long[] notTagIds,
		java.lang.String orderByCol1, java.lang.String orderByCol2,
		java.lang.String orderByType1, java.lang.String orderByType2,
		boolean excludeZeroViewCount, java.util.Date publishDate,
		java.util.Date expirationDate, int start, int end)
		throws com.liferay.portal.SystemException {
		return getFinder()
				   .findByOrTagIds(groupId, classNameIds, tagIds, notTagIds,
			orderByCol1, orderByCol2, orderByType1, orderByType2,
			excludeZeroViewCount, publishDate, expirationDate, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.Asset> findByViewCount(
		long[] classNameId, boolean asc, int start, int end)
		throws com.liferay.portal.SystemException {
		return getFinder().findByViewCount(classNameId, asc, start, end);
	}

	public static AssetFinder getFinder() {
		return _finder;
	}

	public void setFinder(AssetFinder finder) {
		_finder = finder;
	}

	private static AssetFinder _finder;
}