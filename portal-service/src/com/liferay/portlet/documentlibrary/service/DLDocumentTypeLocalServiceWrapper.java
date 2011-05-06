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

/**
 * <p>
 * This class is a wrapper for {@link DLDocumentTypeLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DLDocumentTypeLocalService
 * @generated
 */
public class DLDocumentTypeLocalServiceWrapper
	implements DLDocumentTypeLocalService {
	public DLDocumentTypeLocalServiceWrapper(
		DLDocumentTypeLocalService dlDocumentTypeLocalService) {
		_dlDocumentTypeLocalService = dlDocumentTypeLocalService;
	}

	/**
	* Adds the d l document type to the database. Also notifies the appropriate model listeners.
	*
	* @param dlDocumentType the d l document type to add
	* @return the d l document type that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLDocumentType addDLDocumentType(
		com.liferay.portlet.documentlibrary.model.DLDocumentType dlDocumentType)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlDocumentTypeLocalService.addDLDocumentType(dlDocumentType);
	}

	/**
	* Creates a new d l document type with the primary key. Does not add the d l document type to the database.
	*
	* @param documentTypeId the primary key for the new d l document type
	* @return the new d l document type
	*/
	public com.liferay.portlet.documentlibrary.model.DLDocumentType createDLDocumentType(
		long documentTypeId) {
		return _dlDocumentTypeLocalService.createDLDocumentType(documentTypeId);
	}

	/**
	* Deletes the d l document type with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param documentTypeId the primary key of the d l document type to delete
	* @throws PortalException if a d l document type with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDLDocumentType(long documentTypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlDocumentTypeLocalService.deleteDLDocumentType(documentTypeId);
	}

	/**
	* Deletes the d l document type from the database. Also notifies the appropriate model listeners.
	*
	* @param dlDocumentType the d l document type to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDLDocumentType(
		com.liferay.portlet.documentlibrary.model.DLDocumentType dlDocumentType)
		throws com.liferay.portal.kernel.exception.SystemException {
		_dlDocumentTypeLocalService.deleteDLDocumentType(dlDocumentType);
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
		return _dlDocumentTypeLocalService.dynamicQuery(dynamicQuery);
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
		return _dlDocumentTypeLocalService.dynamicQuery(dynamicQuery, start, end);
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
		return _dlDocumentTypeLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
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
		return _dlDocumentTypeLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the d l document type with the primary key.
	*
	* @param documentTypeId the primary key of the d l document type to get
	* @return the d l document type
	* @throws PortalException if a d l document type with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLDocumentType getDLDocumentType(
		long documentTypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlDocumentTypeLocalService.getDLDocumentType(documentTypeId);
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
	public java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentType> getDLDocumentTypes(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlDocumentTypeLocalService.getDLDocumentTypes(start, end);
	}

	/**
	* Gets the number of d l document types.
	*
	* @return the number of d l document types
	* @throws SystemException if a system exception occurred
	*/
	public int getDLDocumentTypesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlDocumentTypeLocalService.getDLDocumentTypesCount();
	}

	/**
	* Updates the d l document type in the database. Also notifies the appropriate model listeners.
	*
	* @param dlDocumentType the d l document type to update
	* @return the d l document type that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLDocumentType updateDLDocumentType(
		com.liferay.portlet.documentlibrary.model.DLDocumentType dlDocumentType)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlDocumentTypeLocalService.updateDLDocumentType(dlDocumentType);
	}

	/**
	* Updates the d l document type in the database. Also notifies the appropriate model listeners.
	*
	* @param dlDocumentType the d l document type to update
	* @param merge whether to merge the d l document type with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the d l document type that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.documentlibrary.model.DLDocumentType updateDLDocumentType(
		com.liferay.portlet.documentlibrary.model.DLDocumentType dlDocumentType,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlDocumentTypeLocalService.updateDLDocumentType(dlDocumentType,
			merge);
	}

	/**
	* Gets the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _dlDocumentTypeLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_dlDocumentTypeLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.documentlibrary.model.DLDocumentType addDocumentType(
		long userId, long groupId, java.lang.String name,
		java.lang.String description, long[] ddmStructureIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlDocumentTypeLocalService.addDocumentType(userId, groupId,
			name, description, ddmStructureIds, serviceContext);
	}

	public void deleteDocumentType(long documentTypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlDocumentTypeLocalService.deleteDocumentType(documentTypeId);
	}

	public com.liferay.portlet.documentlibrary.model.DLDocumentType getDocumentType(
		long documentTypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _dlDocumentTypeLocalService.getDocumentType(documentTypeId);
	}

	public java.util.List<com.liferay.portlet.documentlibrary.model.DLDocumentType> getGroupDocumentTypes(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlDocumentTypeLocalService.getGroupDocumentTypes(groupId,
			start, end);
	}

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getDDMStructures(
		long documentTypeId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _dlDocumentTypeLocalService.getDDMStructures(documentTypeId);
	}

	public void updateDocumentType(long userId, long documentTypeId,
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_dlDocumentTypeLocalService.updateDocumentType(userId, documentTypeId,
			name, description, serviceContext);
	}

	public DLDocumentTypeLocalService getWrappedDLDocumentTypeLocalService() {
		return _dlDocumentTypeLocalService;
	}

	public void setWrappedDLDocumentTypeLocalService(
		DLDocumentTypeLocalService dlDocumentTypeLocalService) {
		_dlDocumentTypeLocalService = dlDocumentTypeLocalService;
	}

	private DLDocumentTypeLocalService _dlDocumentTypeLocalService;
}