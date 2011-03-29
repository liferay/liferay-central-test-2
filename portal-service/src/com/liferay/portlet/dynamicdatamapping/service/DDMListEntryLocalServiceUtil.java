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
 * The utility for the d d m list entry local service. This utility wraps {@link com.liferay.portlet.dynamicdatamapping.service.impl.DDMListEntryLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMListEntryLocalService
 * @see com.liferay.portlet.dynamicdatamapping.service.base.DDMListEntryLocalServiceBaseImpl
 * @see com.liferay.portlet.dynamicdatamapping.service.impl.DDMListEntryLocalServiceImpl
 * @generated
 */
public class DDMListEntryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.dynamicdatamapping.service.impl.DDMListEntryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the d d m list entry to the database. Also notifies the appropriate model listeners.
	*
	* @param ddmListEntry the d d m list entry to add
	* @return the d d m list entry that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMListEntry addDDMListEntry(
		com.liferay.portlet.dynamicdatamapping.model.DDMListEntry ddmListEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addDDMListEntry(ddmListEntry);
	}

	/**
	* Creates a new d d m list entry with the primary key. Does not add the d d m list entry to the database.
	*
	* @param listEntryId the primary key for the new d d m list entry
	* @return the new d d m list entry
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMListEntry createDDMListEntry(
		long listEntryId) {
		return getService().createDDMListEntry(listEntryId);
	}

	/**
	* Deletes the d d m list entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param listEntryId the primary key of the d d m list entry to delete
	* @throws PortalException if a d d m list entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteDDMListEntry(long listEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteDDMListEntry(listEntryId);
	}

	/**
	* Deletes the d d m list entry from the database. Also notifies the appropriate model listeners.
	*
	* @param ddmListEntry the d d m list entry to delete
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteDDMListEntry(
		com.liferay.portlet.dynamicdatamapping.model.DDMListEntry ddmListEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteDDMListEntry(ddmListEntry);
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
	* Gets the d d m list entry with the primary key.
	*
	* @param listEntryId the primary key of the d d m list entry to get
	* @return the d d m list entry
	* @throws PortalException if a d d m list entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMListEntry getDDMListEntry(
		long listEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDMListEntry(listEntryId);
	}

	/**
	* Gets the d d m list entry with the UUID and group id.
	*
	* @param uuid the UUID of d d m list entry to get
	* @param groupId the group id of the d d m list entry to get
	* @return the d d m list entry
	* @throws PortalException if a d d m list entry with the UUID and group id could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMListEntry getDDMListEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDMListEntryByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Gets a range of all the d d m list entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d d m list entries to return
	* @param end the upper bound of the range of d d m list entries to return (not inclusive)
	* @return the range of d d m list entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMListEntry> getDDMListEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDMListEntries(start, end);
	}

	/**
	* Gets the number of d d m list entries.
	*
	* @return the number of d d m list entries
	* @throws SystemException if a system exception occurred
	*/
	public static int getDDMListEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDMListEntriesCount();
	}

	/**
	* Updates the d d m list entry in the database. Also notifies the appropriate model listeners.
	*
	* @param ddmListEntry the d d m list entry to update
	* @return the d d m list entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMListEntry updateDDMListEntry(
		com.liferay.portlet.dynamicdatamapping.model.DDMListEntry ddmListEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateDDMListEntry(ddmListEntry);
	}

	/**
	* Updates the d d m list entry in the database. Also notifies the appropriate model listeners.
	*
	* @param ddmListEntry the d d m list entry to update
	* @param merge whether to merge the d d m list entry with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the d d m list entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMListEntry updateDDMListEntry(
		com.liferay.portlet.dynamicdatamapping.model.DDMListEntry ddmListEntry,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateDDMListEntry(ddmListEntry, merge);
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

	public static DDMListEntryLocalService getService() {
		if (_service == null) {
			_service = (DDMListEntryLocalService)PortalBeanLocatorUtil.locate(DDMListEntryLocalService.class.getName());

			ReferenceRegistry.registerReference(DDMListEntryLocalServiceUtil.class,
				"_service");
			MethodCache.remove(DDMListEntryLocalService.class);
		}

		return _service;
	}

	public void setService(DDMListEntryLocalService service) {
		MethodCache.remove(DDMListEntryLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(DDMListEntryLocalServiceUtil.class,
			"_service");
		MethodCache.remove(DDMListEntryLocalService.class);
	}

	private static DDMListEntryLocalService _service;
}