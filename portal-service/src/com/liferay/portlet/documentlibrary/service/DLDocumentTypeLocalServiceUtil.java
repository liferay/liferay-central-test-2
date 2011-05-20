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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the d l document type local service. This utility wraps {@link com.liferay.portlet.documentlibrary.service.impl.DLDocumentTypeLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DLDocumentTypeLocalService
 * @see com.liferay.portlet.documentlibrary.service.base.DLDocumentTypeLocalServiceBaseImpl
 * @see com.liferay.portlet.documentlibrary.service.impl.DLDocumentTypeLocalServiceImpl
 * @generated
 */
public class DLDocumentTypeLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.documentlibrary.service.impl.DLDocumentTypeLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the d l document type to the database. Also notifies the appropriate model listeners.
	*
	* @param dlDocumentType the d l document type to add
	* @return the d l document type that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLDocumentType addDLDocumentType(
		com.liferay.portlet.documentlibrary.model.DLDocumentType dlDocumentType)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addDLDocumentType(dlDocumentType);
	}

	/**
	* Creates a new d l document type with the primary key. Does not add the d l document type to the database.
	*
	* @param documentTypeId the primary key for the new d l document type
	* @return the new d l document type
	*/
	public static com.liferay.portlet.documentlibrary.model.DLDocumentType createDLDocumentType(
		long documentTypeId) {
		return getService().createDLDocumentType(documentTypeId);
	}

	/**
	* Deletes the d l document type with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param documentTypeId the primary key of the d l document type to delete
	* @throws PortalException if a d l document type with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteDLDocumentType(long documentTypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteDLDocumentType(documentTypeId);
	}

	/**
	* Deletes the d l document type from the database. Also notifies the appropriate model listeners.
	*
	* @param dlDocumentType the d l document type to delete
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteDLDocumentType(
		com.liferay.portlet.documentlibrary.model.DLDocumentType dlDocumentType)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteDLDocumentType(dlDocumentType);
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
	* Gets the d l document type with the primary key.
	*
	* @param documentTypeId the primary key of the d l document type to get
	* @return the d l document type
	* @throws PortalException if a d l document type with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLDocumentType getDLDocumentType(
		long documentTypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDLDocumentType(documentTypeId);
	}

	/**
	* Gets a range of all the d l document types.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d l document types to return
	* @param end the upper bound of the range of d l document types to return (not inclusive)
	* @return the range of d l document types
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentType> getDLDocumentTypes(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDLDocumentTypes(start, end);
	}

	/**
	* Gets the number of d l document types.
	*
	* @return the number of d l document types
	* @throws SystemException if a system exception occurred
	*/
	public static int getDLDocumentTypesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDLDocumentTypesCount();
	}

	/**
	* Updates the d l document type in the database. Also notifies the appropriate model listeners.
	*
	* @param dlDocumentType the d l document type to update
	* @return the d l document type that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLDocumentType updateDLDocumentType(
		com.liferay.portlet.documentlibrary.model.DLDocumentType dlDocumentType)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateDLDocumentType(dlDocumentType);
	}

	/**
	* Updates the d l document type in the database. Also notifies the appropriate model listeners.
	*
	* @param dlDocumentType the d l document type to update
	* @param merge whether to merge the d l document type with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the d l document type that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.documentlibrary.model.DLDocumentType updateDLDocumentType(
		com.liferay.portlet.documentlibrary.model.DLDocumentType dlDocumentType,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateDLDocumentType(dlDocumentType, merge);
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

	public static com.liferay.portlet.documentlibrary.model.DLDocumentType addDocumentType(
		long userId, long groupId, java.lang.String name,
		java.lang.String description, long[] ddmStructureIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addDocumentType(userId, groupId, name, description,
			ddmStructureIds, serviceContext);
	}

	public static void deleteDocumentType(long documentTypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteDocumentType(documentTypeId);
	}

	public static com.liferay.portlet.documentlibrary.model.DLDocumentType getDocumentType(
		long documentTypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDocumentType(documentTypeId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentType> getDocumentTypes(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDocumentTypes(groupId);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentType> getDocumentTypes(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDocumentTypes(groupId, start, end);
	}

	public static java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentType> search(
		long companyId, long groupId, java.lang.String keywords, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .search(companyId, groupId, keywords, start, end,
			orderByComparator);
	}

	public static int searchCount(long companyId, long groupId,
		java.lang.String keywords)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().searchCount(companyId, groupId, keywords);
	}

	public static void updateDocumentType(long documentTypeId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateDocumentType(documentTypeId, name, description,
			serviceContext);
	}

	public static DLDocumentTypeLocalService getService() {
		if (_service == null) {
			_service = (DLDocumentTypeLocalService)PortalBeanLocatorUtil.locate(DLDocumentTypeLocalService.class.getName());

			ReferenceRegistry.registerReference(DLDocumentTypeLocalServiceUtil.class,
				"_service");
			MethodCache.remove(DLDocumentTypeLocalService.class);
		}

		return _service;
	}

	public void setService(DLDocumentTypeLocalService service) {
		MethodCache.remove(DLDocumentTypeLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(DLDocumentTypeLocalServiceUtil.class,
			"_service");
		MethodCache.remove(DLDocumentTypeLocalService.class);
	}

	private static DLDocumentTypeLocalService _service;
}