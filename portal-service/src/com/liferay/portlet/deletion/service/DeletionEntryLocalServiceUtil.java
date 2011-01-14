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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the deletion entry local service. This utility wraps {@link com.liferay.portlet.deletion.service.impl.DeletionEntryLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DeletionEntryLocalService
 * @see com.liferay.portlet.deletion.service.base.DeletionEntryLocalServiceBaseImpl
 * @see com.liferay.portlet.deletion.service.impl.DeletionEntryLocalServiceImpl
 * @generated
 */
public class DeletionEntryLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.deletion.service.impl.DeletionEntryLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the deletion entry to the database. Also notifies the appropriate model listeners.
	*
	* @param deletionEntry the deletion entry to add
	* @return the deletion entry that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.deletion.model.DeletionEntry addDeletionEntry(
		com.liferay.portlet.deletion.model.DeletionEntry deletionEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addDeletionEntry(deletionEntry);
	}

	/**
	* Creates a new deletion entry with the primary key. Does not add the deletion entry to the database.
	*
	* @param entryId the primary key for the new deletion entry
	* @return the new deletion entry
	*/
	public static com.liferay.portlet.deletion.model.DeletionEntry createDeletionEntry(
		long entryId) {
		return getService().createDeletionEntry(entryId);
	}

	/**
	* Deletes the deletion entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param entryId the primary key of the deletion entry to delete
	* @throws PortalException if a deletion entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteDeletionEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteDeletionEntry(entryId);
	}

	/**
	* Deletes the deletion entry from the database. Also notifies the appropriate model listeners.
	*
	* @param deletionEntry the deletion entry to delete
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteDeletionEntry(
		com.liferay.portlet.deletion.model.DeletionEntry deletionEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteDeletionEntry(deletionEntry);
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
	* @param orderByComparator the comparator to order the results by
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
	* Gets the deletion entry with the primary key.
	*
	* @param entryId the primary key of the deletion entry to get
	* @return the deletion entry
	* @throws PortalException if a deletion entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.deletion.model.DeletionEntry getDeletionEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDeletionEntry(entryId);
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
	public static java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> getDeletionEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDeletionEntries(start, end);
	}

	/**
	* Gets the number of deletion entries.
	*
	* @return the number of deletion entries
	* @throws SystemException if a system exception occurred
	*/
	public static int getDeletionEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDeletionEntriesCount();
	}

	/**
	* Updates the deletion entry in the database. Also notifies the appropriate model listeners.
	*
	* @param deletionEntry the deletion entry to update
	* @return the deletion entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.deletion.model.DeletionEntry updateDeletionEntry(
		com.liferay.portlet.deletion.model.DeletionEntry deletionEntry)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateDeletionEntry(deletionEntry);
	}

	/**
	* Updates the deletion entry in the database. Also notifies the appropriate model listeners.
	*
	* @param deletionEntry the deletion entry to update
	* @param merge whether to merge the deletion entry with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the deletion entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.deletion.model.DeletionEntry updateDeletionEntry(
		com.liferay.portlet.deletion.model.DeletionEntry deletionEntry,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateDeletionEntry(deletionEntry, merge);
	}

	public static com.liferay.portlet.deletion.model.DeletionEntry addEntry(
		long companyId, long groupId, java.lang.String className, long classPK,
		java.lang.String classUuid, long parentId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addEntry(companyId, groupId, className, classPK, classUuid,
			parentId);
	}

	public static com.liferay.portlet.deletion.model.DeletionEntry addEntry(
		long companyId, long groupId, long classNameId, long classPK,
		java.lang.String classUuid, long parentId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addEntry(companyId, groupId, classNameId, classPK,
			classUuid, parentId);
	}

	public static void deleteEntries(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteEntries(groupId);
	}

	public static void deleteEntry(long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteEntry(entryId);
	}

	public static void deleteEntry(
		com.liferay.portlet.deletion.model.DeletionEntry entry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteEntry(entry);
	}

	public static com.liferay.portlet.deletion.model.DeletionEntry getEntry(
		long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntry(entryId);
	}

	public static com.liferay.portlet.deletion.model.DeletionEntry getEntry(
		long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntry(classNameId, classPK);
	}

	public static com.liferay.portlet.deletion.model.DeletionEntry getEntry(
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntry(className, classPK);
	}

	public static java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> getEntries(
		long groupId, long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntries(groupId, classNameId);
	}

	public static java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> getEntries(
		long groupId, java.lang.String className)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntries(groupId, className);
	}

	public static java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> getEntries(
		long groupId, java.util.Date createDate, long classNameId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntries(groupId, createDate, classNameId);
	}

	public static java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> getEntries(
		long groupId, java.util.Date createDate, java.lang.String className)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntries(groupId, createDate, className);
	}

	public static java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> getEntries(
		long groupId, long classNameId, long parentId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntries(groupId, classNameId, parentId);
	}

	public static java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> getEntries(
		long groupId, java.lang.String className, long parentId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntries(groupId, className, parentId);
	}

	public static java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> getEntries(
		long groupId, java.util.Date createDate, long classNameId, long parentId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getEntries(groupId, createDate, classNameId, parentId);
	}

	public static java.util.List<com.liferay.portlet.deletion.model.DeletionEntry> getEntries(
		long groupId, java.util.Date createDate, java.lang.String className,
		long parentId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getEntries(groupId, createDate, className, parentId);
	}

	public static DeletionEntryLocalService getService() {
		if (_service == null) {
			_service = (DeletionEntryLocalService)PortalBeanLocatorUtil.locate(DeletionEntryLocalService.class.getName());

			ReferenceRegistry.registerReference(DeletionEntryLocalServiceUtil.class,
				"_service");
			MethodCache.remove(DeletionEntryLocalService.class);
		}

		return _service;
	}

	public void setService(DeletionEntryLocalService service) {
		MethodCache.remove(DeletionEntryLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(DeletionEntryLocalServiceUtil.class,
			"_service");
		MethodCache.remove(DeletionEntryLocalService.class);
	}

	private static DeletionEntryLocalService _service;
}