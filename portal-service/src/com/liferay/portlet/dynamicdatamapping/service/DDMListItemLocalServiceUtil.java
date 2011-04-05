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

package com.liferay.portlet.dynamicdatamapping.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the d d m list item local service. This utility wraps {@link com.liferay.portlet.dynamicdatamapping.service.impl.DDMListItemLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMListItemLocalService
 * @see com.liferay.portlet.dynamicdatamapping.service.base.DDMListItemLocalServiceBaseImpl
 * @see com.liferay.portlet.dynamicdatamapping.service.impl.DDMListItemLocalServiceImpl
 * @generated
 */
public class DDMListItemLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.dynamicdatamapping.service.impl.DDMListItemLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the d d m list item to the database. Also notifies the appropriate model listeners.
	*
	* @param ddmListItem the d d m list item to add
	* @return the d d m list item that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMListItem addDDMListItem(
		com.liferay.portlet.dynamicdatamapping.model.DDMListItem ddmListItem)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addDDMListItem(ddmListItem);
	}

	/**
	* Creates a new d d m list item with the primary key. Does not add the d d m list item to the database.
	*
	* @param listItemId the primary key for the new d d m list item
	* @return the new d d m list item
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMListItem createDDMListItem(
		long listItemId) {
		return getService().createDDMListItem(listItemId);
	}

	/**
	* Deletes the d d m list item with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param listItemId the primary key of the d d m list item to delete
	* @throws PortalException if a d d m list item with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteDDMListItem(long listItemId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteDDMListItem(listItemId);
	}

	/**
	* Deletes the d d m list item from the database. Also notifies the appropriate model listeners.
	*
	* @param ddmListItem the d d m list item to delete
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteDDMListItem(
		com.liferay.portlet.dynamicdatamapping.model.DDMListItem ddmListItem)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteDDMListItem(ddmListItem);
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
	* Gets the d d m list item with the primary key.
	*
	* @param listItemId the primary key of the d d m list item to get
	* @return the d d m list item
	* @throws PortalException if a d d m list item with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMListItem getDDMListItem(
		long listItemId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDMListItem(listItemId);
	}

	/**
	* Gets a range of all the d d m list items.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d d m list items to return
	* @param end the upper bound of the range of d d m list items to return (not inclusive)
	* @return the range of d d m list items
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMListItem> getDDMListItems(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDMListItems(start, end);
	}

	/**
	* Gets the number of d d m list items.
	*
	* @return the number of d d m list items
	* @throws SystemException if a system exception occurred
	*/
	public static int getDDMListItemsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDMListItemsCount();
	}

	/**
	* Updates the d d m list item in the database. Also notifies the appropriate model listeners.
	*
	* @param ddmListItem the d d m list item to update
	* @return the d d m list item that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMListItem updateDDMListItem(
		com.liferay.portlet.dynamicdatamapping.model.DDMListItem ddmListItem)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateDDMListItem(ddmListItem);
	}

	/**
	* Updates the d d m list item in the database. Also notifies the appropriate model listeners.
	*
	* @param ddmListItem the d d m list item to update
	* @param merge whether to merge the d d m list item with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the d d m list item that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMListItem updateDDMListItem(
		com.liferay.portlet.dynamicdatamapping.model.DDMListItem ddmListItem,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateDDMListItem(ddmListItem, merge);
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

	public static com.liferay.portlet.dynamicdatamapping.model.DDMListItem addListItem(
		long listId,
		com.liferay.portlet.dynamicdatamapping.storage.Fields fields,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addListItem(listId, fields, serviceContext);
	}

	public static void deleteListItem(
		com.liferay.portlet.dynamicdatamapping.model.DDMListItem listItem)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteListItem(listItem);
	}

	public static void deleteListItem(long listItemId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteListItem(listItemId);
	}

	public static void deleteListItems(long listId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteListItems(listId);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMListItem getListItem(
		long listItemId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getListItem(listItemId);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMListItem> getListItems(
		long listId) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getListItems(listId);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMListItem updateListItem(
		long listItemId,
		com.liferay.portlet.dynamicdatamapping.storage.Fields fields,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateListItem(listItemId, fields, serviceContext);
	}

	public static DDMListItemLocalService getService() {
		if (_service == null) {
			_service = (DDMListItemLocalService)PortalBeanLocatorUtil.locate(DDMListItemLocalService.class.getName());

			ReferenceRegistry.registerReference(DDMListItemLocalServiceUtil.class,
				"_service");
			MethodCache.remove(DDMListItemLocalService.class);
		}

		return _service;
	}

	public void setService(DDMListItemLocalService service) {
		MethodCache.remove(DDMListItemLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(DDMListItemLocalServiceUtil.class,
			"_service");
		MethodCache.remove(DDMListItemLocalService.class);
	}

	private static DDMListItemLocalService _service;
}