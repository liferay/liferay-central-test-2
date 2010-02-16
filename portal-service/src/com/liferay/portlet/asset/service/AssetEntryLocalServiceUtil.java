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

package com.liferay.portlet.asset.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="AssetEntryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link AssetEntryLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetEntryLocalService
 * @generated
 */
public class AssetEntryLocalServiceUtil {
	public static com.liferay.portlet.asset.model.AssetEntry addAssetEntry(
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addAssetEntry(assetEntry);
	}

	public static com.liferay.portlet.asset.model.AssetEntry createAssetEntry(
		long entryId) {
		return getService().createAssetEntry(entryId);
	}

	public static void deleteAssetEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAssetEntry(entryId);
	}

	public static void deleteAssetEntry(
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAssetEntry(assetEntry);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.asset.model.AssetEntry getAssetEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetEntry(entryId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetEntries(start, end);
	}

	public static int getAssetEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAssetEntriesCount();
	}

	public static com.liferay.portlet.asset.model.AssetEntry updateAssetEntry(
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateAssetEntry(assetEntry);
	}

	public static com.liferay.portlet.asset.model.AssetEntry updateAssetEntry(
		com.liferay.portlet.asset.model.AssetEntry assetEntry, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateAssetEntry(assetEntry, merge);
	}

	public static void deleteEntry(
		com.liferay.portlet.asset.model.AssetEntry entry)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteEntry(entry);
	}

	public static void deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteEntry(entryId);
	}

	public static void deleteEntry(java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteEntry(className, classPK);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> getCompanyEntries(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyEntries(companyId, start, end);
	}

	public static int getCompanyEntriesCount(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCompanyEntriesCount(companyId);
	}

	public static com.liferay.portlet.asset.model.AssetEntryDisplay[] getCompanyEntryDisplays(
		long companyId, int start, int end, java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getCompanyEntryDisplays(companyId, start, end, languageId);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> getEntries(
		com.liferay.portlet.asset.service.persistence.AssetEntryQuery entryQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntries(entryQuery);
	}

	public static int getEntriesCount(
		com.liferay.portlet.asset.service.persistence.AssetEntryQuery entryQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntriesCount(entryQuery);
	}

	public static com.liferay.portlet.asset.model.AssetEntry getEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntry(entryId);
	}

	public static com.liferay.portlet.asset.model.AssetEntry getEntry(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntry(className, classPK);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> getTopViewedEntries(
		java.lang.String className, boolean asc, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTopViewedEntries(className, asc, start, end);
	}

	public static java.util.List<com.liferay.portlet.asset.model.AssetEntry> getTopViewedEntries(
		java.lang.String[] className, boolean asc, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTopViewedEntries(className, asc, start, end);
	}

	public static com.liferay.portlet.asset.model.AssetEntry incrementViewCounter(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().incrementViewCounter(className, classPK);
	}

	public static com.liferay.portal.kernel.search.Hits search(long companyId,
		java.lang.String portletId, java.lang.String keywords, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().search(companyId, portletId, keywords, start, end);
	}

	public static com.liferay.portlet.asset.model.AssetEntryDisplay[] searchEntryDisplays(
		long companyId, java.lang.String portletId, java.lang.String keywords,
		java.lang.String languageId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .searchEntryDisplays(companyId, portletId, keywords,
			languageId, start, end);
	}

	public static int searchEntryDisplaysCount(long companyId,
		java.lang.String portletId, java.lang.String keywords,
		java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .searchEntryDisplaysCount(companyId, portletId, keywords,
			languageId);
	}

	public static com.liferay.portlet.asset.model.AssetEntry updateEntry(
		long userId, long groupId, java.lang.String className, long classPK,
		long[] categoryIds, java.lang.String[] tagNames)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateEntry(userId, groupId, className, classPK,
			categoryIds, tagNames);
	}

	public static com.liferay.portlet.asset.model.AssetEntry updateEntry(
		long userId, long groupId, java.lang.String className, long classPK,
		long[] categoryIds, java.lang.String[] tagNames, boolean visible,
		java.util.Date startDate, java.util.Date endDate,
		java.util.Date publishDate, java.util.Date expirationDate,
		java.lang.String mimeType, java.lang.String title,
		java.lang.String description, java.lang.String summary,
		java.lang.String url, int height, int width,
		java.lang.Integer priority, boolean sync)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateEntry(userId, groupId, className, classPK,
			categoryIds, tagNames, visible, startDate, endDate, publishDate,
			expirationDate, mimeType, title, description, summary, url, height,
			width, priority, sync);
	}

	public static com.liferay.portlet.asset.model.AssetEntry updateVisible(
		java.lang.String className, long classPK, boolean visible)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateVisible(className, classPK, visible);
	}

	public static void validate(java.lang.String className, long[] categoryIds,
		java.lang.String[] tagNames)
		throws com.liferay.portal.kernel.exception.PortalException {
		getService().validate(className, categoryIds, tagNames);
	}

	public static AssetEntryLocalService getService() {
		if (_service == null) {
			_service = (AssetEntryLocalService)PortalBeanLocatorUtil.locate(AssetEntryLocalService.class.getName());
		}

		return _service;
	}

	public void setService(AssetEntryLocalService service) {
		_service = service;
	}

	private static AssetEntryLocalService _service;
}