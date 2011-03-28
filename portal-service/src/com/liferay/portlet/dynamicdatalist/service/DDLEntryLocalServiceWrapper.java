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

package com.liferay.portlet.dynamicdatalist.service;

/**
 * <p>
 * This class is a wrapper for {@link DDLEntryLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DDLEntryLocalService
 * @generated
 */
public class DDLEntryLocalServiceWrapper implements DDLEntryLocalService {
	public DDLEntryLocalServiceWrapper(
		DDLEntryLocalService ddlEntryLocalService) {
		_ddlEntryLocalService = ddlEntryLocalService;
	}

	/**
	* Adds the d d l entry to the database. Also notifies the appropriate model listeners.
	*
	* @param ddlEntry the d d l entry to add
	* @return the d d l entry that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatalist.model.DDLEntry addDDLEntry(
		com.liferay.portlet.dynamicdatalist.model.DDLEntry ddlEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlEntryLocalService.addDDLEntry(ddlEntry);
	}

	/**
	* Creates a new d d l entry with the primary key. Does not add the d d l entry to the database.
	*
	* @param entryId the primary key for the new d d l entry
	* @return the new d d l entry
	*/
	public com.liferay.portlet.dynamicdatalist.model.DDLEntry createDDLEntry(
		long entryId) {
		return _ddlEntryLocalService.createDDLEntry(entryId);
	}

	/**
	* Deletes the d d l entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the d d l entry to delete
	* @throws PortalException if a d d l entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDDLEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddlEntryLocalService.deleteDDLEntry(entryId);
	}

	/**
	* Deletes the d d l entry from the database. Also notifies the appropriate model listeners.
	*
	* @param ddlEntry the d d l entry to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDDLEntry(
		com.liferay.portlet.dynamicdatalist.model.DDLEntry ddlEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		_ddlEntryLocalService.deleteDDLEntry(ddlEntry);
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
		return _ddlEntryLocalService.dynamicQuery(dynamicQuery);
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
		return _ddlEntryLocalService.dynamicQuery(dynamicQuery, start, end);
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
		return _ddlEntryLocalService.dynamicQuery(dynamicQuery, start, end,
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
		return _ddlEntryLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the d d l entry with the primary key.
	*
	* @param entryId the primary key of the d d l entry to get
	* @return the d d l entry
	* @throws PortalException if a d d l entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatalist.model.DDLEntry getDDLEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlEntryLocalService.getDDLEntry(entryId);
	}

	/**
	* Gets the d d l entry with the UUID and group id.
	*
	* @param uuid the UUID of d d l entry to get
	* @param groupId the group id of the d d l entry to get
	* @return the d d l entry
	* @throws PortalException if a d d l entry with the UUID and group id could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatalist.model.DDLEntry getDDLEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlEntryLocalService.getDDLEntryByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Gets a range of all the d d l entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d d l entries to return
	* @param end the upper bound of the range of d d l entries to return (not inclusive)
	* @return the range of d d l entries
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatalist.model.DDLEntry> getDDLEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlEntryLocalService.getDDLEntries(start, end);
	}

	/**
	* Gets the number of d d l entries.
	*
	* @return the number of d d l entries
	* @throws SystemException if a system exception occurred
	*/
	public int getDDLEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlEntryLocalService.getDDLEntriesCount();
	}

	/**
	* Updates the d d l entry in the database. Also notifies the appropriate model listeners.
	*
	* @param ddlEntry the d d l entry to update
	* @return the d d l entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatalist.model.DDLEntry updateDDLEntry(
		com.liferay.portlet.dynamicdatalist.model.DDLEntry ddlEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlEntryLocalService.updateDDLEntry(ddlEntry);
	}

	/**
	* Updates the d d l entry in the database. Also notifies the appropriate model listeners.
	*
	* @param ddlEntry the d d l entry to update
	* @param merge whether to merge the d d l entry with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the d d l entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatalist.model.DDLEntry updateDDLEntry(
		com.liferay.portlet.dynamicdatalist.model.DDLEntry ddlEntry,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlEntryLocalService.updateDDLEntry(ddlEntry, merge);
	}

	/**
	* Gets the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _ddlEntryLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_ddlEntryLocalService.setBeanIdentifier(beanIdentifier);
	}

	public DDLEntryLocalService getWrappedDDLEntryLocalService() {
		return _ddlEntryLocalService;
	}

	public void setWrappedDDLEntryLocalService(
		DDLEntryLocalService ddlEntryLocalService) {
		_ddlEntryLocalService = ddlEntryLocalService;
	}

	private DDLEntryLocalService _ddlEntryLocalService;
}