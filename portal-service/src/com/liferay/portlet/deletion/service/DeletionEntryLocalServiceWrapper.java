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

package com.liferay.portlet.deletion.service;

/**
 * <p>
 * This class is a wrapper for {@link DeletionEntryLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DeletionEntryLocalService
 * @generated
 */
public class DeletionEntryLocalServiceWrapper
	implements DeletionEntryLocalService {
	public DeletionEntryLocalServiceWrapper(
		DeletionEntryLocalService deletionEntryLocalService) {
		_deletionEntryLocalService = deletionEntryLocalService;
	}

	/**
	* Adds the deletion entry to the database. Also notifies the appropriate model listeners.
	*
	* @param deletionEntry the deletion entry to add
	* @return the deletion entry that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.deletion.model.DeletionEntry addDeletionEntry(
		com.liferay.portlet.deletion.model.DeletionEntry deletionEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _deletionEntryLocalService.addDeletionEntry(deletionEntry);
	}

	/**
	* Creates a new deletion entry with the primary key. Does not add the deletion entry to the database.
	*
	* @param entryId the primary key for the new deletion entry
	* @return the new deletion entry
	*/
	public com.liferay.portlet.deletion.model.DeletionEntry createDeletionEntry(
		long entryId) {
		return _deletionEntryLocalService.createDeletionEntry(entryId);
	}

	/**
	* Deletes the deletion entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the deletion entry to delete
	* @throws PortalException if a deletion entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDeletionEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_deletionEntryLocalService.deleteDeletionEntry(entryId);
	}

	/**
	* Deletes the deletion entry from the database. Also notifies the appropriate model listeners.
	*
	* @param deletionEntry the deletion entry to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDeletionEntry(
		com.liferay.portlet.deletion.model.DeletionEntry deletionEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		_deletionEntryLocalService.deleteDeletionEntry(deletionEntry);
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
		return _deletionEntryLocalService.dynamicQuery(dynamicQuery);
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
		return _deletionEntryLocalService.dynamicQuery(dynamicQuery, start, end);
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
		return _deletionEntryLocalService.dynamicQuery(dynamicQuery, start,
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
		return _deletionEntryLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the deletion entry with the primary key.
	*
	* @param entryId the primary key of the deletion entry to get
	* @return the deletion entry
	* @throws PortalException if a deletion entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.deletion.model.DeletionEntry getDeletionEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _deletionEntryLocalService.getDeletionEntry(entryId);
	}

	/**
	* Gets a range of all the deletion entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of deletion entries to return
	* @param end the upper bound of the range of deletion entries to return (not inclusive)
	* @return the range of deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> getDeletionEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _deletionEntryLocalService.getDeletionEntries(start, end);
	}

	/**
	* Gets the number of deletion entries.
	*
	* @return the number of deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public int getDeletionEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _deletionEntryLocalService.getDeletionEntriesCount();
	}

	/**
	* Updates the deletion entry in the database. Also notifies the appropriate model listeners.
	*
	* @param deletionEntry the deletion entry to update
	* @return the deletion entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.deletion.model.DeletionEntry updateDeletionEntry(
		com.liferay.portlet.deletion.model.DeletionEntry deletionEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _deletionEntryLocalService.updateDeletionEntry(deletionEntry);
	}

	/**
	* Updates the deletion entry in the database. Also notifies the appropriate model listeners.
	*
	* @param deletionEntry the deletion entry to update
	* @param merge whether to merge the deletion entry with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the deletion entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.deletion.model.DeletionEntry updateDeletionEntry(
		com.liferay.portlet.deletion.model.DeletionEntry deletionEntry,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _deletionEntryLocalService.updateDeletionEntry(deletionEntry,
			merge);
	}

	public com.liferay.portlet.deletion.model.DeletionEntry addEntry(
		long companyId, long groupId, java.lang.String className, long classPK,
		java.lang.String classUuid, long parentId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _deletionEntryLocalService.addEntry(companyId, groupId,
			className, classPK, classUuid, parentId);
	}

	public com.liferay.portlet.deletion.model.DeletionEntry addEntry(
		long companyId, long groupId, long classNameId, long classPK,
		java.lang.String classUuid, long parentId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _deletionEntryLocalService.addEntry(companyId, groupId,
			classNameId, classPK, classUuid, parentId);
	}

	public void deleteEntries(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_deletionEntryLocalService.deleteEntries(groupId);
	}

	public void deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_deletionEntryLocalService.deleteEntry(entryId);
	}

	public void deleteEntry(
		com.liferay.portlet.deletion.model.DeletionEntry entry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_deletionEntryLocalService.deleteEntry(entry);
	}

	public com.liferay.portlet.deletion.model.DeletionEntry getEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _deletionEntryLocalService.getEntry(entryId);
	}

	public com.liferay.portlet.deletion.model.DeletionEntry getEntry(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _deletionEntryLocalService.getEntry(classNameId, classPK);
	}

	public com.liferay.portlet.deletion.model.DeletionEntry getEntry(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _deletionEntryLocalService.getEntry(className, classPK);
	}

	public java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> getEntries(
		long groupId, long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _deletionEntryLocalService.getEntries(groupId, classNameId);
	}

	public java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> getEntries(
		long groupId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _deletionEntryLocalService.getEntries(groupId, className);
	}

	public java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> getEntries(
		long groupId, java.util.Date createDate, long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _deletionEntryLocalService.getEntries(groupId, createDate,
			classNameId);
	}

	public java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> getEntries(
		long groupId, java.util.Date createDate, java.lang.String className)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _deletionEntryLocalService.getEntries(groupId, createDate,
			className);
	}

	public java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> getEntries(
		long groupId, long classNameId, long parentId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _deletionEntryLocalService.getEntries(groupId, classNameId,
			parentId);
	}

	public java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> getEntries(
		long groupId, java.lang.String className, long parentId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _deletionEntryLocalService.getEntries(groupId, className,
			parentId);
	}

	public java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> getEntries(
		long groupId, java.util.Date createDate, long classNameId, long parentId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _deletionEntryLocalService.getEntries(groupId, createDate,
			classNameId, parentId);
	}

	public java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> getEntries(
		long groupId, java.util.Date createDate, java.lang.String className,
		long parentId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _deletionEntryLocalService.getEntries(groupId, createDate,
			className, parentId);
	}

	public DeletionEntryLocalService getWrappedDeletionEntryLocalService() {
		return _deletionEntryLocalService;
	}

	public void setWrappedDeletionEntryLocalService(
		DeletionEntryLocalService deletionEntryLocalService) {
		_deletionEntryLocalService = deletionEntryLocalService;
	}

	private DeletionEntryLocalService _deletionEntryLocalService;
}