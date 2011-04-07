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

package com.liferay.portlet.dynamicdatalists.service;

/**
 * <p>
 * This class is a wrapper for {@link DDLEntryItemLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DDLEntryItemLocalService
 * @generated
 */
public class DDLEntryItemLocalServiceWrapper implements DDLEntryItemLocalService {
	public DDLEntryItemLocalServiceWrapper(
		DDLEntryItemLocalService ddlEntryItemLocalService) {
		_ddlEntryItemLocalService = ddlEntryItemLocalService;
	}

	/**
	* Adds the d d l entry item to the database. Also notifies the appropriate model listeners.
	*
	* @param ddlEntryItem the d d l entry item to add
	* @return the d d l entry item that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLEntryItem addDDLEntryItem(
		com.liferay.portlet.dynamicdatalists.model.DDLEntryItem ddlEntryItem)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlEntryItemLocalService.addDDLEntryItem(ddlEntryItem);
	}

	/**
	* Creates a new d d l entry item with the primary key. Does not add the d d l entry item to the database.
	*
	* @param entryItemId the primary key for the new d d l entry item
	* @return the new d d l entry item
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLEntryItem createDDLEntryItem(
		long entryItemId) {
		return _ddlEntryItemLocalService.createDDLEntryItem(entryItemId);
	}

	/**
	* Deletes the d d l entry item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryItemId the primary key of the d d l entry item to delete
	* @throws PortalException if a d d l entry item with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDDLEntryItem(long entryItemId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddlEntryItemLocalService.deleteDDLEntryItem(entryItemId);
	}

	/**
	* Deletes the d d l entry item from the database. Also notifies the appropriate model listeners.
	*
	* @param ddlEntryItem the d d l entry item to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDDLEntryItem(
		com.liferay.portlet.dynamicdatalists.model.DDLEntryItem ddlEntryItem)
		throws com.liferay.portal.kernel.exception.SystemException {
		_ddlEntryItemLocalService.deleteDDLEntryItem(ddlEntryItem);
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
		return _ddlEntryItemLocalService.dynamicQuery(dynamicQuery);
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
		return _ddlEntryItemLocalService.dynamicQuery(dynamicQuery, start, end);
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
		return _ddlEntryItemLocalService.dynamicQuery(dynamicQuery, start, end,
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
		return _ddlEntryItemLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the d d l entry item with the primary key.
	*
	* @param entryItemId the primary key of the d d l entry item to get
	* @return the d d l entry item
	* @throws PortalException if a d d l entry item with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLEntryItem getDDLEntryItem(
		long entryItemId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlEntryItemLocalService.getDDLEntryItem(entryItemId);
	}

	/**
	* Gets a range of all the d d l entry items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d d l entry items to return
	* @param end the upper bound of the range of d d l entry items to return (not inclusive)
	* @return the range of d d l entry items
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLEntryItem> getDDLEntryItems(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlEntryItemLocalService.getDDLEntryItems(start, end);
	}

	/**
	* Gets the number of d d l entry items.
	*
	* @return the number of d d l entry items
	* @throws SystemException if a system exception occurred
	*/
	public int getDDLEntryItemsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlEntryItemLocalService.getDDLEntryItemsCount();
	}

	/**
	* Updates the d d l entry item in the database. Also notifies the appropriate model listeners.
	*
	* @param ddlEntryItem the d d l entry item to update
	* @return the d d l entry item that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLEntryItem updateDDLEntryItem(
		com.liferay.portlet.dynamicdatalists.model.DDLEntryItem ddlEntryItem)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlEntryItemLocalService.updateDDLEntryItem(ddlEntryItem);
	}

	/**
	* Updates the d d l entry item in the database. Also notifies the appropriate model listeners.
	*
	* @param ddlEntryItem the d d l entry item to update
	* @param merge whether to merge the d d l entry item with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the d d l entry item that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatalists.model.DDLEntryItem updateDDLEntryItem(
		com.liferay.portlet.dynamicdatalists.model.DDLEntryItem ddlEntryItem,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlEntryItemLocalService.updateDDLEntryItem(ddlEntryItem, merge);
	}

	/**
	* Gets the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _ddlEntryItemLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_ddlEntryItemLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.dynamicdatalists.model.DDLEntryItem addEntryItem(
		long entryId,
		com.liferay.portlet.dynamicdatamapping.storage.Fields fields,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlEntryItemLocalService.addEntryItem(entryId, fields,
			serviceContext);
	}

	public void deleteEntryItem(
		com.liferay.portlet.dynamicdatalists.model.DDLEntryItem entryItem)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddlEntryItemLocalService.deleteEntryItem(entryItem);
	}

	public void deleteEntryItem(long entryItemId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddlEntryItemLocalService.deleteEntryItem(entryItemId);
	}

	public void deleteEntryItems(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddlEntryItemLocalService.deleteEntryItems(entryId);
	}

	public com.liferay.portlet.dynamicdatalists.model.DDLEntryItem getEntryItem(
		long entryItemId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlEntryItemLocalService.getEntryItem(entryItemId);
	}

	public java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLEntryItem> getEntryItems(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddlEntryItemLocalService.getEntryItems(entryId);
	}

	public com.liferay.portlet.dynamicdatalists.model.DDLEntryItem updateEntryItem(
		long entryItemId,
		com.liferay.portlet.dynamicdatamapping.storage.Fields fields,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddlEntryItemLocalService.updateEntryItem(entryItemId, fields,
			serviceContext);
	}

	public DDLEntryItemLocalService getWrappedDDLEntryItemLocalService() {
		return _ddlEntryItemLocalService;
	}

	public void setWrappedDDLEntryItemLocalService(
		DDLEntryItemLocalService ddlEntryItemLocalService) {
		_ddlEntryItemLocalService = ddlEntryItemLocalService;
	}

	private DDLEntryItemLocalService _ddlEntryItemLocalService;
}