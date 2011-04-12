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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the d d l entry item local service. This utility wraps {@link com.liferay.portlet.dynamicdatalists.service.impl.DDLEntryItemLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDLEntryItemLocalService
 * @see com.liferay.portlet.dynamicdatalists.service.base.DDLEntryItemLocalServiceBaseImpl
 * @see com.liferay.portlet.dynamicdatalists.service.impl.DDLEntryItemLocalServiceImpl
 * @generated
 */
public class DDLEntryItemLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.dynamicdatalists.service.impl.DDLEntryItemLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the d d l entry item to the database. Also notifies the appropriate model listeners.
	*
	* @param ddlEntryItem the d d l entry item to add
	* @return the d d l entry item that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLEntryItem addDDLEntryItem(
		com.liferay.portlet.dynamicdatalists.model.DDLEntryItem ddlEntryItem)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addDDLEntryItem(ddlEntryItem);
	}

	/**
	* Creates a new d d l entry item with the primary key. Does not add the d d l entry item to the database.
	*
	* @param entryItemId the primary key for the new d d l entry item
	* @return the new d d l entry item
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLEntryItem createDDLEntryItem(
		long entryItemId) {
		return getService().createDDLEntryItem(entryItemId);
	}

	/**
	* Deletes the d d l entry item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryItemId the primary key of the d d l entry item to delete
	* @throws PortalException if a d d l entry item with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteDDLEntryItem(long entryItemId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteDDLEntryItem(entryItemId);
	}

	/**
	* Deletes the d d l entry item from the database. Also notifies the appropriate model listeners.
	*
	* @param ddlEntryItem the d d l entry item to delete
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteDDLEntryItem(
		com.liferay.portlet.dynamicdatalists.model.DDLEntryItem ddlEntryItem)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteDDLEntryItem(ddlEntryItem);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
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
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
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
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Counts the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the d d l entry item with the primary key.
	*
	* @param entryItemId the primary key of the d d l entry item to get
	* @return the d d l entry item
	* @throws PortalException if a d d l entry item with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLEntryItem getDDLEntryItem(
		long entryItemId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDLEntryItem(entryItemId);
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
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLEntryItem> getDDLEntryItems(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDLEntryItems(start, end);
	}

	/**
	* Gets the number of d d l entry items.
	*
	* @return the number of d d l entry items
	* @throws SystemException if a system exception occurred
	*/
	public static int getDDLEntryItemsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDLEntryItemsCount();
	}

	/**
	* Updates the d d l entry item in the database. Also notifies the appropriate model listeners.
	*
	* @param ddlEntryItem the d d l entry item to update
	* @return the d d l entry item that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLEntryItem updateDDLEntryItem(
		com.liferay.portlet.dynamicdatalists.model.DDLEntryItem ddlEntryItem)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateDDLEntryItem(ddlEntryItem);
	}

	/**
	* Updates the d d l entry item in the database. Also notifies the appropriate model listeners.
	*
	* @param ddlEntryItem the d d l entry item to update
	* @param merge whether to merge the d d l entry item with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the d d l entry item that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLEntryItem updateDDLEntryItem(
		com.liferay.portlet.dynamicdatalists.model.DDLEntryItem ddlEntryItem,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateDDLEntryItem(ddlEntryItem, merge);
	}

	/**
	* Gets the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLEntryItem addEntryItem(
		long entryId,
		com.liferay.portlet.dynamicdatamapping.storage.Fields fields,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addEntryItem(entryId, fields, serviceContext);
	}

	public static void deleteEntryItem(
		com.liferay.portlet.dynamicdatalists.model.DDLEntryItem entryItem)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteEntryItem(entryItem);
	}

	public static void deleteEntryItem(long entryItemId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteEntryItem(entryItemId);
	}

	public static void deleteEntryItems(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteEntryItems(entryId);
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLEntryItem getEntryItem(
		long entryItemId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntryItem(entryItemId);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLEntryItem> getEntryItems(
		long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntryItems(entryId);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLEntryItem> getEntryItems(
		long entryId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntryItems(entryId, start, end, orderByComparator);
	}

	public static int getEntryItemsCount(long entryId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntryItemsCount(entryId);
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLEntryItem updateEntryItem(
		long entryItemId,
		com.liferay.portlet.dynamicdatamapping.storage.Fields fields,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateEntryItem(entryItemId, fields, serviceContext);
	}

	public static DDLEntryItemLocalService getService() {
		if (_service == null) {
			_service = (DDLEntryItemLocalService)PortalBeanLocatorUtil.locate(DDLEntryItemLocalService.class.getName());

			ReferenceRegistry.registerReference(DDLEntryItemLocalServiceUtil.class,
				"_service");
			MethodCache.remove(DDLEntryItemLocalService.class);
		}

		return _service;
	}

	public void setService(DDLEntryItemLocalService service) {
		MethodCache.remove(DDLEntryItemLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(DDLEntryItemLocalServiceUtil.class,
			"_service");
		MethodCache.remove(DDLEntryItemLocalService.class);
	}

	private static DDLEntryItemLocalService _service;
}