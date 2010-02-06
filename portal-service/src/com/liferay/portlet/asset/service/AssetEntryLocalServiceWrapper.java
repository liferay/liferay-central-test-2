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


/**
 * <a href="AssetEntryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link AssetEntryLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetEntryLocalService
 * @generated
 */
public class AssetEntryLocalServiceWrapper implements AssetEntryLocalService {
	public AssetEntryLocalServiceWrapper(
		AssetEntryLocalService assetEntryLocalService) {
		_assetEntryLocalService = assetEntryLocalService;
	}

	public com.liferay.portlet.asset.model.AssetEntry addAssetEntry(
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.SystemException {
		return _assetEntryLocalService.addAssetEntry(assetEntry);
	}

	public com.liferay.portlet.asset.model.AssetEntry createAssetEntry(
		long entryId) {
		return _assetEntryLocalService.createAssetEntry(entryId);
	}

	public void deleteAssetEntry(long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_assetEntryLocalService.deleteAssetEntry(entryId);
	}

	public void deleteAssetEntry(
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.SystemException {
		_assetEntryLocalService.deleteAssetEntry(assetEntry);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _assetEntryLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _assetEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.asset.model.AssetEntry getAssetEntry(
		long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetEntryLocalService.getAssetEntry(entryId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		int start, int end) throws com.liferay.portal.SystemException {
		return _assetEntryLocalService.getAssetEntries(start, end);
	}

	public int getAssetEntriesCount() throws com.liferay.portal.SystemException {
		return _assetEntryLocalService.getAssetEntriesCount();
	}

	public com.liferay.portlet.asset.model.AssetEntry updateAssetEntry(
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.SystemException {
		return _assetEntryLocalService.updateAssetEntry(assetEntry);
	}

	public com.liferay.portlet.asset.model.AssetEntry updateAssetEntry(
		com.liferay.portlet.asset.model.AssetEntry assetEntry, boolean merge)
		throws com.liferay.portal.SystemException {
		return _assetEntryLocalService.updateAssetEntry(assetEntry, merge);
	}

	public void deleteEntry(com.liferay.portlet.asset.model.AssetEntry entry)
		throws com.liferay.portal.SystemException {
		_assetEntryLocalService.deleteEntry(entry);
	}

	public void deleteEntry(long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_assetEntryLocalService.deleteEntry(entryId);
	}

	public void deleteEntry(java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException {
		_assetEntryLocalService.deleteEntry(className, classPK);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getCompanyEntries(
		long companyId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _assetEntryLocalService.getCompanyEntries(companyId, start, end);
	}

	public int getCompanyEntriesCount(long companyId)
		throws com.liferay.portal.SystemException {
		return _assetEntryLocalService.getCompanyEntriesCount(companyId);
	}

	public com.liferay.portlet.asset.model.AssetEntryDisplay[] getCompanyEntryDisplays(
		long companyId, int start, int end, java.lang.String languageId)
		throws com.liferay.portal.SystemException {
		return _assetEntryLocalService.getCompanyEntryDisplays(companyId,
			start, end, languageId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getEntries(
		com.liferay.portlet.asset.service.persistence.AssetEntryQuery entryQuery)
		throws com.liferay.portal.SystemException {
		return _assetEntryLocalService.getEntries(entryQuery);
	}

	public int getEntriesCount(
		com.liferay.portlet.asset.service.persistence.AssetEntryQuery entryQuery)
		throws com.liferay.portal.SystemException {
		return _assetEntryLocalService.getEntriesCount(entryQuery);
	}

	public com.liferay.portlet.asset.model.AssetEntry getEntry(long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetEntryLocalService.getEntry(entryId);
	}

	public com.liferay.portlet.asset.model.AssetEntry getEntry(
		java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetEntryLocalService.getEntry(className, classPK);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getTopViewedEntries(
		java.lang.String className, boolean asc, int start, int end)
		throws com.liferay.portal.SystemException {
		return _assetEntryLocalService.getTopViewedEntries(className, asc,
			start, end);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getTopViewedEntries(
		java.lang.String[] className, boolean asc, int start, int end)
		throws com.liferay.portal.SystemException {
		return _assetEntryLocalService.getTopViewedEntries(className, asc,
			start, end);
	}

	public com.liferay.portlet.asset.model.AssetEntry incrementViewCounter(
		java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException {
		return _assetEntryLocalService.incrementViewCounter(className, classPK);
	}

	public com.liferay.portal.kernel.search.Hits search(long companyId,
		java.lang.String portletId, java.lang.String keywords, int start,
		int end) throws com.liferay.portal.SystemException {
		return _assetEntryLocalService.search(companyId, portletId, keywords,
			start, end);
	}

	public com.liferay.portlet.asset.model.AssetEntryDisplay[] searchEntryDisplays(
		long companyId, java.lang.String portletId, java.lang.String keywords,
		java.lang.String languageId, int start, int end)
		throws com.liferay.portal.SystemException {
		return _assetEntryLocalService.searchEntryDisplays(companyId,
			portletId, keywords, languageId, start, end);
	}

	public int searchEntryDisplaysCount(long companyId,
		java.lang.String portletId, java.lang.String keywords,
		java.lang.String languageId) throws com.liferay.portal.SystemException {
		return _assetEntryLocalService.searchEntryDisplaysCount(companyId,
			portletId, keywords, languageId);
	}

	public com.liferay.portlet.asset.model.AssetEntry updateEntry(long userId,
		long groupId, java.lang.String className, long classPK,
		long[] categoryIds, java.lang.String[] tagNames)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetEntryLocalService.updateEntry(userId, groupId, className,
			classPK, categoryIds, tagNames);
	}

	public com.liferay.portlet.asset.model.AssetEntry updateEntry(long userId,
		long groupId, java.lang.String className, long classPK,
		long[] categoryIds, java.lang.String[] tagNames, boolean visible,
		java.util.Date startDate, java.util.Date endDate,
		java.util.Date publishDate, java.util.Date expirationDate,
		java.lang.String mimeType, java.lang.String title,
		java.lang.String description, java.lang.String summary,
		java.lang.String url, int height, int width,
		java.lang.Integer priority, boolean sync)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetEntryLocalService.updateEntry(userId, groupId, className,
			classPK, categoryIds, tagNames, visible, startDate, endDate,
			publishDate, expirationDate, mimeType, title, description, summary,
			url, height, width, priority, sync);
	}

	public com.liferay.portlet.asset.model.AssetEntry updateVisible(
		java.lang.String className, long classPK, boolean visible)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _assetEntryLocalService.updateVisible(className, classPK, visible);
	}

	public void validate(java.lang.String className, long[] categoryIds,
		java.lang.String[] tagNames) throws com.liferay.portal.PortalException {
		_assetEntryLocalService.validate(className, categoryIds, tagNames);
	}

	public AssetEntryLocalService getWrappedAssetEntryLocalService() {
		return _assetEntryLocalService;
	}

	private AssetEntryLocalService _assetEntryLocalService;
}