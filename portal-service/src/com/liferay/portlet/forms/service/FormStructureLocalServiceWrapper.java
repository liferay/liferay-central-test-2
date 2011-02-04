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

package com.liferay.portlet.forms.service;

/**
 * <p>
 * This class is a wrapper for {@link FormStructureLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       FormStructureLocalService
 * @generated
 */
public class FormStructureLocalServiceWrapper
	implements FormStructureLocalService {
	public FormStructureLocalServiceWrapper(
		FormStructureLocalService formStructureLocalService) {
		_formStructureLocalService = formStructureLocalService;
	}

	/**
	* Adds the form structure to the database. Also notifies the appropriate model listeners.
	*
	* @param formStructure the form structure to add
	* @return the form structure that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormStructure addFormStructure(
		com.liferay.portlet.forms.model.FormStructure formStructure)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _formStructureLocalService.addFormStructure(formStructure);
	}

	/**
	* Creates a new form structure with the primary key. Does not add the form structure to the database.
	*
	* @param id the primary key for the new form structure
	* @return the new form structure
	*/
	public com.liferay.portlet.forms.model.FormStructure createFormStructure(
		long id) {
		return _formStructureLocalService.createFormStructure(id);
	}

	/**
	* Deletes the form structure with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param id the primary key of the form structure to delete
	* @throws PortalException if a form structure with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteFormStructure(long id)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_formStructureLocalService.deleteFormStructure(id);
	}

	/**
	* Deletes the form structure from the database. Also notifies the appropriate model listeners.
	*
	* @param formStructure the form structure to delete
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public void deleteFormStructure(
		com.liferay.portlet.forms.model.FormStructure formStructure)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_formStructureLocalService.deleteFormStructure(formStructure);
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
		return _formStructureLocalService.dynamicQuery(dynamicQuery);
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
		return _formStructureLocalService.dynamicQuery(dynamicQuery, start, end);
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
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _formStructureLocalService.dynamicQuery(dynamicQuery, start,
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
		return _formStructureLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the form structure with the primary key.
	*
	* @param id the primary key of the form structure to get
	* @return the form structure
	* @throws PortalException if a form structure with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormStructure getFormStructure(
		long id)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _formStructureLocalService.getFormStructure(id);
	}

	/**
	* Gets the form structure with the UUID and group id.
	*
	* @param uuid the UUID of form structure to get
	* @param groupId the group id of the form structure to get
	* @return the form structure
	* @throws PortalException if a form structure with the UUID and group id could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormStructure getFormStructureByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _formStructureLocalService.getFormStructureByUuidAndGroupId(uuid,
			groupId);
	}

	/**
	* Gets a range of all the form structures.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of form structures to return
	* @param end the upper bound of the range of form structures to return (not inclusive)
	* @return the range of form structures
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.forms.model.FormStructure> getFormStructures(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _formStructureLocalService.getFormStructures(start, end);
	}

	/**
	* Gets the number of form structures.
	*
	* @return the number of form structures
	* @throws SystemException if a system exception occurred
	*/
	public int getFormStructuresCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _formStructureLocalService.getFormStructuresCount();
	}

	/**
	* Updates the form structure in the database. Also notifies the appropriate model listeners.
	*
	* @param formStructure the form structure to update
	* @return the form structure that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormStructure updateFormStructure(
		com.liferay.portlet.forms.model.FormStructure formStructure)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _formStructureLocalService.updateFormStructure(formStructure);
	}

	/**
	* Updates the form structure in the database. Also notifies the appropriate model listeners.
	*
	* @param formStructure the form structure to update
	* @param merge whether to merge the form structure with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the form structure that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormStructure updateFormStructure(
		com.liferay.portlet.forms.model.FormStructure formStructure,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _formStructureLocalService.updateFormStructure(formStructure,
			merge);
	}

	public com.liferay.portlet.forms.model.FormStructure addFormStructure(
		long userId, long groupId, java.lang.String formStructureId,
		boolean autoFormStructureId, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _formStructureLocalService.addFormStructure(userId, groupId,
			formStructureId, autoFormStructureId, name, description, xsd,
			serviceContext);
	}

	public void addFormStructureResources(
		com.liferay.portlet.forms.model.FormStructure formStructure,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_formStructureLocalService.addFormStructureResources(formStructure,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFormStructureResources(
		com.liferay.portlet.forms.model.FormStructure formStructure,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_formStructureLocalService.addFormStructureResources(formStructure,
			communityPermissions, guestPermissions);
	}

	public void deleteFormStructure(long groupId,
		java.lang.String formStructureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_formStructureLocalService.deleteFormStructure(groupId, formStructureId);
	}

	public void deleteFormStructures(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_formStructureLocalService.deleteFormStructures(groupId);
	}

	public com.liferay.portlet.forms.model.FormStructure fetchByG_F(
		long groupId, java.lang.String formStructureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _formStructureLocalService.fetchByG_F(groupId, formStructureId);
	}

	public com.liferay.portlet.forms.model.FormStructure getFormStructure(
		long groupId, java.lang.String formStructureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _formStructureLocalService.getFormStructure(groupId,
			formStructureId);
	}

	public java.util.List<com.liferay.portlet.forms.model.FormStructure> getFormStructures()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _formStructureLocalService.getFormStructures();
	}

	public java.util.List<com.liferay.portlet.forms.model.FormStructure> getFormStructures(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _formStructureLocalService.getFormStructures(groupId);
	}

	public java.util.List<com.liferay.portlet.forms.model.FormStructure> getFormStructures(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _formStructureLocalService.getFormStructures(groupId, start, end);
	}

	public int getFormStructuresCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _formStructureLocalService.getFormStructuresCount(groupId);
	}

	public com.liferay.portlet.forms.model.FormStructure updateFormStructure(
		long groupId, java.lang.String formStructureId, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _formStructureLocalService.updateFormStructure(groupId,
			formStructureId, name, description, xsd, serviceContext);
	}

	public FormStructureLocalService getWrappedFormStructureLocalService() {
		return _formStructureLocalService;
	}

	public void setWrappedFormStructureLocalService(
		FormStructureLocalService formStructureLocalService) {
		_formStructureLocalService = formStructureLocalService;
	}

	private FormStructureLocalService _formStructureLocalService;
}