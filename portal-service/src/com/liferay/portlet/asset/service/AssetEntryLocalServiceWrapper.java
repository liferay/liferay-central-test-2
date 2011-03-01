/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.asset.service;

/**
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

	/**
	* Adds the asset entry to the database. Also notifies the appropriate model listeners.
	*
	* @param assetEntry the asset entry to add
	* @return the asset entry that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetEntry addAssetEntry(
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.addAssetEntry(assetEntry);
	}

	/**
	* Creates a new asset entry with the primary key. Does not add the asset entry to the database.
	*
	* @param entryId the primary key for the new asset entry
	* @return the new asset entry
	*/
	public com.liferay.portlet.asset.model.AssetEntry createAssetEntry(
		long entryId) {
		return _assetEntryLocalService.createAssetEntry(entryId);
	}

	/**
	* Deletes the asset entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the asset entry to delete
	* @throws PortalException if a asset entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteAssetEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetEntryLocalService.deleteAssetEntry(entryId);
	}

	/**
	* Deletes the asset entry from the database. Also notifies the appropriate model listeners.
	*
	* @param assetEntry the asset entry to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteAssetEntry(
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		_assetEntryLocalService.deleteAssetEntry(assetEntry);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Counts the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the asset entry with the primary key.
	*
	* @param entryId the primary key of the asset entry to get
	* @return the asset entry
	* @throws PortalException if a asset entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetEntry getAssetEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getAssetEntry(entryId);
	}

	/**
	* Gets a range of all the asset entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of asset entries to return
	* @param end the upper bound of the range of asset entries to return (not inclusive)
	* @return the range of asset entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAssetEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getAssetEntries(start, end);
	}

	/**
	* Gets the number of asset entries.
	*
	* @return the number of asset entries
	* @throws SystemException if a system exception occurred
	*/
	public int getAssetEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getAssetEntriesCount();
	}

	/**
	* Updates the asset entry in the database. Also notifies the appropriate model listeners.
	*
	* @param assetEntry the asset entry to update
	* @return the asset entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetEntry updateAssetEntry(
		com.liferay.portlet.asset.model.AssetEntry assetEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.updateAssetEntry(assetEntry);
	}

	/**
	* Updates the asset entry in the database. Also notifies the appropriate model listeners.
	*
	* @param assetEntry the asset entry to update
	* @param merge whether to merge the asset entry with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the asset entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.asset.model.AssetEntry updateAssetEntry(
		com.liferay.portlet.asset.model.AssetEntry assetEntry, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.updateAssetEntry(assetEntry, merge);
	}

	/**
	* Gets the Spring bean id for this ServiceBean.
	*
	* @return the Spring bean id for this ServiceBean
	*/
	public java.lang.String getIdentifier() {
		return _assetEntryLocalService.getIdentifier();
	}

	/**
	* Sets the Spring bean id for this ServiceBean.
	*
	* @param identifier the Spring bean id for this ServiceBean
	*/
	public void setIdentifier(java.lang.String identifier) {
		_assetEntryLocalService.setIdentifier(identifier);
	}

	public void deleteEntry(com.liferay.portlet.asset.model.AssetEntry entry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetEntryLocalService.deleteEntry(entry);
	}

	public void deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetEntryLocalService.deleteEntry(entryId);
	}

	public void deleteEntry(java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetEntryLocalService.deleteEntry(className, classPK);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getAncestorEntries(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getAncestorEntries(entryId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getChildEntries(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getChildEntries(entryId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getCompanyEntries(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getCompanyEntries(companyId, start, end);
	}

	public int getCompanyEntriesCount(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getCompanyEntriesCount(companyId);
	}

	public com.liferay.portlet.asset.model.AssetEntryDisplay[] getCompanyEntryDisplays(
		long companyId, int start, int end, java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getCompanyEntryDisplays(companyId,
			start, end, languageId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getEntries(
		com.liferay.portlet.asset.service.persistence.AssetEntryQuery entryQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getEntries(entryQuery);
	}

	public int getEntriesCount(
		com.liferay.portlet.asset.service.persistence.AssetEntryQuery entryQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getEntriesCount(entryQuery);
	}

	public com.liferay.portlet.asset.model.AssetEntry getEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getEntry(entryId);
	}

	public com.liferay.portlet.asset.model.AssetEntry getEntry(long groupId,
		java.lang.String classUuid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getEntry(groupId, classUuid);
	}

	public com.liferay.portlet.asset.model.AssetEntry getEntry(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getEntry(className, classPK);
	}

	public com.liferay.portlet.asset.model.AssetEntry getNextEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getNextEntry(entryId);
	}

	public com.liferay.portlet.asset.model.AssetEntry getParentEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getParentEntry(entryId);
	}

	public com.liferay.portlet.asset.model.AssetEntry getPreviousEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getPreviousEntry(entryId);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getTopViewedEntries(
		java.lang.String className, boolean asc, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getTopViewedEntries(className, asc,
			start, end);
	}

	public java.util.List<com.liferay.portlet.asset.model.AssetEntry> getTopViewedEntries(
		java.lang.String[] className, boolean asc, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.getTopViewedEntries(className, asc,
			start, end);
	}

	public void incrementViewCounter(long userId, java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetEntryLocalService.incrementViewCounter(userId, className, classPK);
	}

	public void incrementViewCounter(long userId, java.lang.String className,
		long classPK, int increment)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_assetEntryLocalService.incrementViewCounter(userId, className,
			classPK, increment);
	}

	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long[] groupIds, java.lang.String portletId, java.lang.String keywords,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.search(companyId, groupIds, portletId,
			keywords, start, end);
	}

	public com.liferay.portal.kernel.search.Hits search(long companyId,
		long[] groupIds, java.lang.String portletId, java.lang.String userName,
		java.lang.String title, java.lang.String description,
		java.lang.String assetCategoryIds, java.lang.String assetTagNames,
		boolean andSearch, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.search(companyId, groupIds, portletId,
			userName, title, description, assetCategoryIds, assetTagNames,
			andSearch, start, end);
	}

	public com.liferay.portlet.asset.model.AssetEntryDisplay[] searchEntryDisplays(
		long companyId, long[] groupIds, java.lang.String portletId,
		java.lang.String keywords, java.lang.String languageId, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.searchEntryDisplays(companyId, groupIds,
			portletId, keywords, languageId, start, end);
	}

	public int searchEntryDisplaysCount(long companyId, long[] groupIds,
		java.lang.String portletId, java.lang.String keywords,
		java.lang.String languageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.searchEntryDisplaysCount(companyId,
			groupIds, portletId, keywords, languageId);
	}

	public com.liferay.portlet.asset.model.AssetEntry updateEntry(long userId,
		long groupId, java.lang.String className, long classPK,
		long[] categoryIds, java.lang.String[] tagNames)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.updateEntry(userId, groupId, className,
			classPK, categoryIds, tagNames);
	}

	public com.liferay.portlet.asset.model.AssetEntry updateEntry(long userId,
		long groupId, java.lang.String className, long classPK,
		java.lang.String classUuid, long[] categoryIds,
		java.lang.String[] tagNames, boolean visible, java.util.Date startDate,
		java.util.Date endDate, java.util.Date publishDate,
		java.util.Date expirationDate, java.lang.String mimeType,
		java.lang.String title, java.lang.String description,
		java.lang.String summary, java.lang.String url, int height, int width,
		java.lang.Integer priority, boolean sync)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.updateEntry(userId, groupId, className,
			classPK, classUuid, categoryIds, tagNames, visible, startDate,
			endDate, publishDate, expirationDate, mimeType, title, description,
			summary, url, height, width, priority, sync);
	}

	public com.liferay.portlet.asset.model.AssetEntry updateVisible(
		java.lang.String className, long classPK, boolean visible)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _assetEntryLocalService.updateVisible(className, classPK, visible);
	}

	public void validate(java.lang.String className, long[] categoryIds,
		java.lang.String[] tagNames)
		throws com.liferay.portal.kernel.exception.PortalException {
		_assetEntryLocalService.validate(className, categoryIds, tagNames);
	}

	public AssetEntryLocalService getWrappedAssetEntryLocalService() {
		return _assetEntryLocalService;
	}

	public void setWrappedAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {
		_assetEntryLocalService = assetEntryLocalService;
	}

	private AssetEntryLocalService _assetEntryLocalService;
}