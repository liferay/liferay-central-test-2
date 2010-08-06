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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * The utility for the d l file version local service. This utility wraps {@link com.liferay.portlet.documentlibrary.service.impl.DLFileVersionLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLFileVersionLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLFileVersionLocalService
 * @see com.liferay.portlet.documentlibrary.service.base.DLFileVersionLocalServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.impl.DLFileVersionLocalServiceImpl
 * @generated
 */
public class DLFileVersionLocalServiceUtil {
	/**
	* Adds the d l file version to the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileVersion the d l file version to add
	* @return the d l file version that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileVersion addDLFileVersion(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addDLFileVersion(dlFileVersion);
	}

	/**
	* Creates a new d l file version with the primary key. Does not add the d l file version to the database.
	*
	* @param fileVersionId the primary key for the new d l file version
	* @return the new d l file version
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileVersion createDLFileVersion(
		long fileVersionId) {
		return getService().createDLFileVersion(fileVersionId);
	}

	/**
	* Deletes the d l file version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param fileVersionId the primary key of the d l file version to delete
	* @throws PortalException if a d l file version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteDLFileVersion(long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteDLFileVersion(fileVersionId);
	}

	/**
	* Deletes the d l file version from the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileVersion the d l file version to delete
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteDLFileVersion(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteDLFileVersion(dlFileVersion);
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
	* Gets the d l file version with the primary key.
	*
	* @param fileVersionId the primary key of the d l file version to get
	* @return the d l file version
	* @throws PortalException if a d l file version with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileVersion getDLFileVersion(
		long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDLFileVersion(fileVersionId);
	}

	/**
	* Gets a range of all the d l file versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d l file versions to return
	* @param end the upper bound of the range of d l file versions to return (not inclusive)
	* @return the range of d l file versions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> getDLFileVersions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDLFileVersions(start, end);
	}

	/**
	* Gets the number of d l file versions.
	*
	* @return the number of d l file versions
	* @throws SystemException if a system exception occurred
	*/
	public static int getDLFileVersionsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDLFileVersionsCount();
	}

	/**
	* Updates the d l file version in the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileVersion the d l file version to update
	* @return the d l file version that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileVersion updateDLFileVersion(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateDLFileVersion(dlFileVersion);
	}

	/**
	* Updates the d l file version in the database. Also notifies the appropriate model listeners.
	*
	* @param dlFileVersion the d l file version to update
	* @param merge whether to merge the d l file version with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the d l file version that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLFileVersion updateDLFileVersion(
		com.liferay.portlet.documentlibrary.model.DLFileVersion dlFileVersion,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateDLFileVersion(dlFileVersion, merge);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersion getFileVersion(
		long fileVersionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileVersion(fileVersionId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersion getFileVersion(
		long groupId, long folderId, java.lang.String name,
		java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileVersion(groupId, folderId, name, version);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLFileVersion> getFileVersions(
		long groupId, long folderId, java.lang.String name, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getFileVersions(groupId, folderId, name, status);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersion getLatestFileVersion(
		long groupId, long folderId, java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getLatestFileVersion(groupId, folderId, name);
	}

	public static com.liferay.portlet.documentlibrary.model.DLFileVersion updateDescription(
		long fileVersionId, java.lang.String description)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().updateDescription(fileVersionId, description);
	}

	public static DLFileVersionLocalService getService() {
		if (_service == null) {
			_service = (DLFileVersionLocalService)PortalBeanLocatorUtil.locate(DLFileVersionLocalService.class.getName());
		}

		return _service;
	}

	public void setService(DLFileVersionLocalService service) {
		_service = service;
	}

	private static DLFileVersionLocalService _service;
}