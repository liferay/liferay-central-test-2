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
 * The utility for the d d m structure entry local service. This utility wraps {@link com.liferay.portlet.dynamicdatamapping.service.impl.DDMStructureEntryLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureEntryLocalService
 * @see com.liferay.portlet.dynamicdatamapping.service.base.DDMStructureEntryLocalServiceBaseImpl
 * @see com.liferay.portlet.dynamicdatamapping.service.impl.DDMStructureEntryLocalServiceImpl
 * @generated
 */
public class DDMStructureEntryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.dynamicdatamapping.service.impl.DDMStructureEntryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the d d m structure entry to the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructureEntry the d d m structure entry to add
	* @return the d d m structure entry that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntry addDDMStructureEntry(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntry ddmStructureEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addDDMStructureEntry(ddmStructureEntry);
	}

	/**
	* Creates a new d d m structure entry with the primary key. Does not add the d d m structure entry to the database.
	*
	* @param structureEntryId the primary key for the new d d m structure entry
	* @return the new d d m structure entry
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntry createDDMStructureEntry(
		long structureEntryId) {
		return getService().createDDMStructureEntry(structureEntryId);
	}

	/**
	* Deletes the d d m structure entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param structureEntryId the primary key of the d d m structure entry to delete
	* @throws PortalException if a d d m structure entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteDDMStructureEntry(long structureEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteDDMStructureEntry(structureEntryId);
	}

	/**
	* Deletes the d d m structure entry from the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructureEntry the d d m structure entry to delete
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteDDMStructureEntry(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntry ddmStructureEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteDDMStructureEntry(ddmStructureEntry);
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
	* Gets the d d m structure entry with the primary key.
	*
	* @param structureEntryId the primary key of the d d m structure entry to get
	* @return the d d m structure entry
	* @throws PortalException if a d d m structure entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntry getDDMStructureEntry(
		long structureEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDMStructureEntry(structureEntryId);
	}

	/**
	* Gets the d d m structure entry with the UUID and group id.
	*
	* @param uuid the UUID of d d m structure entry to get
	* @param groupId the group id of the d d m structure entry to get
	* @return the d d m structure entry
	* @throws PortalException if a d d m structure entry with the UUID and group id could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntry getDDMStructureEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDMStructureEntryByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Gets a range of all the d d m structure entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d d m structure entries to return
	* @param end the upper bound of the range of d d m structure entries to return (not inclusive)
	* @return the range of d d m structure entries
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntry> getDDMStructureEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDMStructureEntries(start, end);
	}

	/**
	* Gets the number of d d m structure entries.
	*
	* @return the number of d d m structure entries
	* @throws SystemException if a system exception occurred
	*/
	public static int getDDMStructureEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDMStructureEntriesCount();
	}

	/**
	* Updates the d d m structure entry in the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructureEntry the d d m structure entry to update
	* @return the d d m structure entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntry updateDDMStructureEntry(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntry ddmStructureEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateDDMStructureEntry(ddmStructureEntry);
	}

	/**
	* Updates the d d m structure entry in the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructureEntry the d d m structure entry to update
	* @param merge whether to merge the d d m structure entry with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the d d m structure entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntry updateDDMStructureEntry(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntry ddmStructureEntry,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateDDMStructureEntry(ddmStructureEntry, merge);
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

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntry addStructureEntry(
		long userId, long groupId, java.lang.String structureId,
		boolean autoStructureId, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addStructureEntry(userId, groupId, structureId,
			autoStructureId, name, description, xsd, serviceContext);
	}

	public static void addStructureEntryResources(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntry structureEntry,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addStructureEntryResources(structureEntry,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addStructureEntryResources(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntry structureEntry,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addStructureEntryResources(structureEntry, communityPermissions,
			guestPermissions);
	}

	public static void deleteStructureEntries(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteStructureEntries(groupId);
	}

	public static void deleteStructureEntry(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntry structureEntry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteStructureEntry(structureEntry);
	}

	public static void deleteStructureEntry(long groupId,
		java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteStructureEntry(groupId, structureId);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntry> getStructureEntries()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getStructureEntries();
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntry> getStructureEntries(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getStructureEntries(groupId);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntry> getStructureEntries(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getStructureEntries(groupId, start, end);
	}

	public static int getStructureEntriesCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getStructureEntriesCount(groupId);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntry getStructureEntry(
		long structureEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getStructureEntry(structureEntryId);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntry getStructureEntry(
		long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getStructureEntry(groupId, structureId);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntry updateStructureEntry(
		long groupId, java.lang.String structureId, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateStructureEntry(groupId, structureId, name,
			description, xsd, serviceContext);
	}

	public static DDMStructureEntryLocalService getService() {
		if (_service == null) {
			_service = (DDMStructureEntryLocalService)PortalBeanLocatorUtil.locate(DDMStructureEntryLocalService.class.getName());

			ReferenceRegistry.registerReference(DDMStructureEntryLocalServiceUtil.class,
				"_service");
			MethodCache.remove(DDMStructureEntryLocalService.class);
		}

		return _service;
	}

	public void setService(DDMStructureEntryLocalService service) {
		MethodCache.remove(DDMStructureEntryLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(DDMStructureEntryLocalServiceUtil.class,
			"_service");
		MethodCache.remove(DDMStructureEntryLocalService.class);
	}

	private static DDMStructureEntryLocalService _service;
}