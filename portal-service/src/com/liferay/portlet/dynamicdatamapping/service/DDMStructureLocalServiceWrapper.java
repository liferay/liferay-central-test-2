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

/**
 * <p>
 * This class is a wrapper for {@link DDMStructureLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DDMStructureLocalService
 * @generated
 */
public class DDMStructureLocalServiceWrapper implements DDMStructureLocalService {
	public DDMStructureLocalServiceWrapper(
		DDMStructureLocalService ddmStructureLocalService) {
		_ddmStructureLocalService = ddmStructureLocalService;
	}

	/**
	* Adds the d d m structure to the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructure the d d m structure to add
	* @return the d d m structure that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure addDDMStructure(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.addDDMStructure(ddmStructure);
	}

	/**
	* Creates a new d d m structure with the primary key. Does not add the d d m structure to the database.
	*
	* @param structureId the primary key for the new d d m structure
	* @return the new d d m structure
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure createDDMStructure(
		long structureId) {
		return _ddmStructureLocalService.createDDMStructure(structureId);
	}

	/**
	* Deletes the d d m structure with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param structureId the primary key of the d d m structure to delete
	* @throws PortalException if a d d m structure with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDDMStructure(long structureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddmStructureLocalService.deleteDDMStructure(structureId);
	}

	/**
	* Deletes the d d m structure from the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructure the d d m structure to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDDMStructure(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure)
		throws com.liferay.portal.kernel.exception.SystemException {
		_ddmStructureLocalService.deleteDDMStructure(ddmStructure);
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
		return _ddmStructureLocalService.dynamicQuery(dynamicQuery);
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
		return _ddmStructureLocalService.dynamicQuery(dynamicQuery, start, end);
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
		return _ddmStructureLocalService.dynamicQuery(dynamicQuery, start, end,
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
		return _ddmStructureLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the d d m structure with the primary key.
	*
	* @param structureId the primary key of the d d m structure to get
	* @return the d d m structure
	* @throws PortalException if a d d m structure with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure getDDMStructure(
		long structureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.getDDMStructure(structureId);
	}

	/**
	* Gets the d d m structure with the UUID and group id.
	*
	* @param uuid the UUID of d d m structure to get
	* @param groupId the group id of the d d m structure to get
	* @return the d d m structure
	* @throws PortalException if a d d m structure with the UUID and group id could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure getDDMStructureByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.getDDMStructureByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Gets a range of all the d d m structures.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d d m structures to return
	* @param end the upper bound of the range of d d m structures to return (not inclusive)
	* @return the range of d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getDDMStructures(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.getDDMStructures(start, end);
	}

	/**
	* Gets the number of d d m structures.
	*
	* @return the number of d d m structures
	* @throws SystemException if a system exception occurred
	*/
	public int getDDMStructuresCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.getDDMStructuresCount();
	}

	/**
	* Updates the d d m structure in the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructure the d d m structure to update
	* @return the d d m structure that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure updateDDMStructure(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.updateDDMStructure(ddmStructure);
	}

	/**
	* Updates the d d m structure in the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructure the d d m structure to update
	* @param merge whether to merge the d d m structure with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the d d m structure that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure updateDDMStructure(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure ddmStructure,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.updateDDMStructure(ddmStructure, merge);
	}

	/**
	* Gets the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _ddmStructureLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_ddmStructureLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure addStructure(
		long userId, long groupId, java.lang.String structureKey,
		boolean autoStructureKey, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		java.lang.String storageType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.addStructure(userId, groupId,
			structureKey, autoStructureKey, name, description, xsd,
			storageType, serviceContext);
	}

	public void addStructureResources(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure structure,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddmStructureLocalService.addStructureResources(structure,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addStructureResources(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure structure,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddmStructureLocalService.addStructureResources(structure,
			communityPermissions, guestPermissions);
	}

	public void deleteStructureEntries(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddmStructureLocalService.deleteStructureEntries(groupId);
	}

	public void deleteStructure(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructure structure)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddmStructureLocalService.deleteStructure(structure);
	}

	public void deleteStructure(long groupId, java.lang.String structureKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddmStructureLocalService.deleteStructure(groupId, structureKey);
	}

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructureEntries()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.getStructureEntries();
	}

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructureEntries(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.getStructureEntries(groupId);
	}

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructure> getStructureEntries(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.getStructureEntries(groupId, start, end);
	}

	public int getStructureEntriesCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.getStructureEntriesCount(groupId);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure getStructure(
		long structureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.getStructure(structureId);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure getStructure(
		long groupId, java.lang.String structureKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.getStructure(groupId, structureKey);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructure updateStructure(
		long groupId, java.lang.String structureKey, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		java.lang.String storageType,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureLocalService.updateStructure(groupId, structureKey,
			name, description, xsd, storageType, serviceContext);
	}

	public DDMStructureLocalService getWrappedDDMStructureLocalService() {
		return _ddmStructureLocalService;
	}

	public void setWrappedDDMStructureLocalService(
		DDMStructureLocalService ddmStructureLocalService) {
		_ddmStructureLocalService = ddmStructureLocalService;
	}

	private DDMStructureLocalService _ddmStructureLocalService;
}