/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service;

/**
 * <p>
 * This class is a wrapper for {@link DLFileShortcutLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLFileShortcutLocalService
 * @generated
 */
public class DLFileShortcutLocalServiceWrapper
	implements DLFileShortcutLocalService {
	public DLFileShortcutLocalServiceWrapper(
		DLFileShortcutLocalService dlFileShortcutLocalService) {
		_dlFileShortcutLocalService = dlFileShortcutLocalService;
	}

	/**
	* Adds the d l file shortcut to the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileShortcut the d l file shortcut to add
	* @return the d l file shortcut that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut addDLFileShortcut(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileShortcutLocalService.addDLFileShortcut(dlFileShortcut);
	}

	/**
	* Creates a new d l file shortcut with the primary key. Does not add the d l file shortcut to the database.
	*
	* @param fileShortcutId the primary key for the new d l file shortcut
	* @return the new d l file shortcut
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut createDLFileShortcut(
		long fileShortcutId) {
		return _dlFileShortcutLocalService.createDLFileShortcut(fileShortcutId);
	}

	/**
	* Deletes the d l file shortcut with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileShortcutId the primary key of the d l file shortcut to delete
	* @throws PortalException if a d l file shortcut with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDLFileShortcut(long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileShortcutLocalService.deleteDLFileShortcut(fileShortcutId);
	}

	/**
	* Deletes the d l file shortcut from the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileShortcut the d l file shortcut to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDLFileShortcut(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileShortcutLocalService.deleteDLFileShortcut(dlFileShortcut);
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
		return _dlFileShortcutLocalService.dynamicQuery(dynamicQuery);
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
		return _dlFileShortcutLocalService.dynamicQuery(dynamicQuery, start, end);
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
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileShortcutLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
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
		return _dlFileShortcutLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the d l file shortcut with the primary key.
	*
	* @param fileShortcutId the primary key of the d l file shortcut to get
	* @return the d l file shortcut
	* @throws PortalException if a d l file shortcut with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut getDLFileShortcut(
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileShortcutLocalService.getDLFileShortcut(fileShortcutId);
	}

	/**
	* Gets the d l file shortcut with the UUID and group id.
	*
	* @param uuid the UUID of d l file shortcut to get
	* @param groupId the group id of the d l file shortcut to get
	* @return the d l file shortcut
	* @throws PortalException if a d l file shortcut with the UUID and group id could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut getDLFileShortcutByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileShortcutLocalService.getDLFileShortcutByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Gets a range of all the d l file shortcuts.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d l file shortcuts to return
	* @param end the upper bound of the range of d l file shortcuts to return (not inclusive)
	* @return the range of d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLFileShortcut> getDLFileShortcuts(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileShortcutLocalService.getDLFileShortcuts(start, end);
	}

	/**
	* Gets the number of d l file shortcuts.
	*
	* @return the number of d l file shortcuts
	* @throws SystemException if a system exception occurred
	*/
	public int getDLFileShortcutsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileShortcutLocalService.getDLFileShortcutsCount();
	}

	/**
	* Updates the d l file shortcut in the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileShortcut the d l file shortcut to update
	* @return the d l file shortcut that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut updateDLFileShortcut(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileShortcutLocalService.updateDLFileShortcut(dlFileShortcut);
	}

	/**
	* Updates the d l file shortcut in the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileShortcut the d l file shortcut to update
	* @param merge whether to merge the d l file shortcut with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the d l file shortcut that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLFileShortcut updateDLFileShortcut(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut dlFileShortcut,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlFileShortcutLocalService.updateDLFileShortcut(dlFileShortcut,
			merge);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut addFileShortcut(
		long userId, long groupId, long toGroupId, long folderId,
		long toFolderId, java.lang.String toName,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileShortcutLocalService.addFileShortcut(userId, groupId,
			toGroupId, folderId, toFolderId, toName, serviceContext);
	}

	public void addFileShortcutResources(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut fileShortcut,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileShortcutLocalService.addFileShortcutResources(fileShortcut,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFileShortcutResources(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut fileShortcut,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileShortcutLocalService.addFileShortcutResources(fileShortcut,
			communityPermissions, guestPermissions);
	}

	public void addFileShortcutResources(long fileShortcutId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileShortcutLocalService.addFileShortcutResources(fileShortcutId,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFileShortcutResources(long fileShortcutId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileShortcutLocalService.addFileShortcutResources(fileShortcutId,
			communityPermissions, guestPermissions);
	}

	public void deleteFileShortcut(
		com.liferay.portlet.documentlibrary.model.DLFileShortcut fileShortcut)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileShortcutLocalService.deleteFileShortcut(fileShortcut);
	}

	public void deleteFileShortcut(long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileShortcutLocalService.deleteFileShortcut(fileShortcutId);
	}

	public void deleteFileShortcuts(long toGroupId, long toFolderId,
		java.lang.String toName)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileShortcutLocalService.deleteFileShortcuts(toGroupId, toFolderId,
			toName);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut getFileShortcut(
		long fileShortcutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileShortcutLocalService.getFileShortcut(fileShortcutId);
	}

	public void updateAsset(long userId,
		com.liferay.portlet.documentlibrary.model.DLFileShortcut fileShortcut,
		long[] assetCategoryIds, java.lang.String[] assetTagNames)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlFileShortcutLocalService.updateAsset(userId, fileShortcut,
			assetCategoryIds, assetTagNames);
	}

	public com.liferay.portlet.documentlibrary.model.DLFileShortcut updateFileShortcut(
		long userId, long fileShortcutId, long toGroupId, long folderId,
		long toFolderId, java.lang.String toName,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlFileShortcutLocalService.updateFileShortcut(userId,
			fileShortcutId, toGroupId, folderId, toFolderId, toName,
			serviceContext);
	}

	public void updateFileShortcuts(long toGroupId, long oldToFolderId,
		java.lang.String oldToName, long newToFolderId,
		java.lang.String newToName)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlFileShortcutLocalService.updateFileShortcuts(toGroupId,
			oldToFolderId, oldToName, newToFolderId, newToName);
	}

	public DLFileShortcutLocalService getWrappedDLFileShortcutLocalService() {
		return _dlFileShortcutLocalService;
	}

	public void setWrappedDLFileShortcutLocalService(
		DLFileShortcutLocalService dlFileShortcutLocalService) {
		_dlFileShortcutLocalService = dlFileShortcutLocalService;
	}

	private DLFileShortcutLocalService _dlFileShortcutLocalService;
}