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
 * The utility for the d d l entry local service. This utility wraps {@link com.liferay.portlet.dynamicdatalists.service.impl.DDLEntryLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDLEntryLocalService
 * @see com.liferay.portlet.dynamicdatalists.service.base.DDLEntryLocalServiceBaseImpl
 * @see com.liferay.portlet.dynamicdatalists.service.impl.DDLEntryLocalServiceImpl
 * @generated
 */
public class DDLEntryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.dynamicdatalists.service.impl.DDLEntryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the d d l entry to the database. Also notifies the appropriate model listeners.
	*
	* @param ddlEntry the d d l entry to add
	* @return the d d l entry that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLEntry addDDLEntry(
		com.liferay.portlet.dynamicdatalists.model.DDLEntry ddlEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addDDLEntry(ddlEntry);
	}

	/**
	* Creates a new d d l entry with the primary key. Does not add the d d l entry to the database.
	*
	* @param entryId the primary key for the new d d l entry
	* @return the new d d l entry
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLEntry createDDLEntry(
		long entryId) {
		return getService().createDDLEntry(entryId);
	}

	/**
	* Deletes the d d l entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the d d l entry to delete
	* @throws PortalException if a d d l entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteDDLEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteDDLEntry(entryId);
	}

	/**
	* Deletes the d d l entry from the database. Also notifies the appropriate model listeners.
	*
	* @param ddlEntry the d d l entry to delete
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteDDLEntry(
		com.liferay.portlet.dynamicdatalists.model.DDLEntry ddlEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteDDLEntry(ddlEntry);
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
	* Gets the d d l entry with the primary key.
	*
	* @param entryId the primary key of the d d l entry to get
	* @return the d d l entry
	* @throws PortalException if a d d l entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLEntry getDDLEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDLEntry(entryId);
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
	public static com.liferay.portlet.dynamicdatalists.model.DDLEntry getDDLEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDLEntryByUuidAndGroupId(uuid, groupId);
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
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLEntry> getDDLEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDLEntries(start, end);
	}

	/**
	* Gets the number of d d l entries.
	*
	* @return the number of d d l entries
	* @throws SystemException if a system exception occurred
	*/
	public static int getDDLEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDLEntriesCount();
	}

	/**
	* Updates the d d l entry in the database. Also notifies the appropriate model listeners.
	*
	* @param ddlEntry the d d l entry to update
	* @return the d d l entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLEntry updateDDLEntry(
		com.liferay.portlet.dynamicdatalists.model.DDLEntry ddlEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateDDLEntry(ddlEntry);
	}

	/**
	* Updates the d d l entry in the database. Also notifies the appropriate model listeners.
	*
	* @param ddlEntry the d d l entry to update
	* @param merge whether to merge the d d l entry with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the d d l entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLEntry updateDDLEntry(
		com.liferay.portlet.dynamicdatalists.model.DDLEntry ddlEntry,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateDDLEntry(ddlEntry, merge);
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

	public static com.liferay.portlet.dynamicdatalists.model.DDLEntry addEntry(
		long userId, long groupId, long ddmStructureId,
		java.lang.String entryKey, boolean autoEntryKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addEntry(userId, groupId, ddmStructureId, entryKey,
			autoEntryKey, nameMap, description, serviceContext);
	}

	public static void addEntryResources(
		com.liferay.portlet.dynamicdatalists.model.DDLEntry entry,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addEntryResources(entry, addCommunityPermissions,
			addGuestPermissions);
	}

	public static void addEntryResources(
		com.liferay.portlet.dynamicdatalists.model.DDLEntry entry,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.addEntryResources(entry, communityPermissions, guestPermissions);
	}

	public static void deleteEntry(
		com.liferay.portlet.dynamicdatalists.model.DDLEntry entry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteEntry(entry);
	}

	public static void deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteEntry(entryId);
	}

	public static void deleteEntry(long groupId, java.lang.String entryKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteEntry(groupId, entryKey);
	}

	public static void deleteEntries(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteEntries(groupId);
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLEntry getEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntry(entryId);
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLEntry getEntry(
		long groupId, java.lang.String entryKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntry(groupId, entryKey);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLEntry> getEntries(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntries(groupId);
	}

	public static int getEntriesCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntriesCount(groupId);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLEntry> search(
		long companyId, long groupId, java.lang.String keywords, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, groupId, keywords, start, end,
			orderByComparator);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLEntry> search(
		long companyId, long groupId, java.lang.String entryKey,
		java.lang.String name, java.lang.String description,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, groupId, entryKey, name, description,
			andOperator, start, end, orderByComparator);
	}

	public static int searchCount(long companyId, long groupId,
		java.lang.String keywords)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().searchCount(companyId, groupId, keywords);
	}

	public static int searchCount(long companyId, long groupId,
		java.lang.String entryKey, java.lang.String name,
		java.lang.String description, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .searchCount(companyId, groupId, entryKey, name,
			description, andOperator);
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLEntry updateEntry(
		long groupId, long ddmStructureId, java.lang.String entryKey,
		java.util.Map<java.util.Locale, java.lang.String> nameMap,
		java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateEntry(groupId, ddmStructureId, entryKey, nameMap,
			description, serviceContext);
	}

	public static DDLEntryLocalService getService() {
		if (_service == null) {
			_service = (DDLEntryLocalService)PortalBeanLocatorUtil.locate(DDLEntryLocalService.class.getName());

			ReferenceRegistry.registerReference(DDLEntryLocalServiceUtil.class,
				"_service");
			MethodCache.remove(DDLEntryLocalService.class);
		}

		return _service;
	}

	public void setService(DDLEntryLocalService service) {
		MethodCache.remove(DDLEntryLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(DDLEntryLocalServiceUtil.class,
			"_service");
		MethodCache.remove(DDLEntryLocalService.class);
	}

	private static DDLEntryLocalService _service;
}