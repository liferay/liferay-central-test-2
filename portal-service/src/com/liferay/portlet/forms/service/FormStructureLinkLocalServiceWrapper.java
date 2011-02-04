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
 * This class is a wrapper for {@link FormStructureLinkLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       FormStructureLinkLocalService
 * @generated
 */
public class FormStructureLinkLocalServiceWrapper
	implements FormStructureLinkLocalService {
	public FormStructureLinkLocalServiceWrapper(
		FormStructureLinkLocalService formStructureLinkLocalService) {
		_formStructureLinkLocalService = formStructureLinkLocalService;
	}

	/**
	* Adds the form structure link to the database. Also notifies the appropriate model listeners.
	*
	* @param formStructureLink the form structure link to add
	* @return the form structure link that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormStructureLink addFormStructureLink(
		com.liferay.portlet.forms.model.FormStructureLink formStructureLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _formStructureLinkLocalService.addFormStructureLink(formStructureLink);
	}

	/**
	* Creates a new form structure link with the primary key. Does not add the form structure link to the database.
	*
	* @param formStructureLinkId the primary key for the new form structure link
	* @return the new form structure link
	*/
	public com.liferay.portlet.forms.model.FormStructureLink createFormStructureLink(
		long formStructureLinkId) {
		return _formStructureLinkLocalService.createFormStructureLink(formStructureLinkId);
	}

	/**
	* Deletes the form structure link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param formStructureLinkId the primary key of the form structure link to delete
	* @throws PortalException if a form structure link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteFormStructureLink(long formStructureLinkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_formStructureLinkLocalService.deleteFormStructureLink(formStructureLinkId);
	}

	/**
	* Deletes the form structure link from the database. Also notifies the appropriate model listeners.
	*
	* @param formStructureLink the form structure link to delete
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public void deleteFormStructureLink(
		com.liferay.portlet.forms.model.FormStructureLink formStructureLink)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_formStructureLinkLocalService.deleteFormStructureLink(formStructureLink);
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
		return _formStructureLinkLocalService.dynamicQuery(dynamicQuery);
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
		return _formStructureLinkLocalService.dynamicQuery(dynamicQuery, start,
			end);
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
		return _formStructureLinkLocalService.dynamicQuery(dynamicQuery, start,
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
		return _formStructureLinkLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the form structure link with the primary key.
	*
	* @param formStructureLinkId the primary key of the form structure link to get
	* @return the form structure link
	* @throws PortalException if a form structure link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormStructureLink getFormStructureLink(
		long formStructureLinkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _formStructureLinkLocalService.getFormStructureLink(formStructureLinkId);
	}

	/**
	* Gets a range of all the form structure links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of form structure links to return
	* @param end the upper bound of the range of form structure links to return (not inclusive)
	* @return the range of form structure links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.forms.model.FormStructureLink> getFormStructureLinks(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _formStructureLinkLocalService.getFormStructureLinks(start, end);
	}

	/**
	* Gets the number of form structure links.
	*
	* @return the number of form structure links
	* @throws SystemException if a system exception occurred
	*/
	public int getFormStructureLinksCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _formStructureLinkLocalService.getFormStructureLinksCount();
	}

	/**
	* Updates the form structure link in the database. Also notifies the appropriate model listeners.
	*
	* @param formStructureLink the form structure link to update
	* @return the form structure link that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormStructureLink updateFormStructureLink(
		com.liferay.portlet.forms.model.FormStructureLink formStructureLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _formStructureLinkLocalService.updateFormStructureLink(formStructureLink);
	}

	/**
	* Updates the form structure link in the database. Also notifies the appropriate model listeners.
	*
	* @param formStructureLink the form structure link to update
	* @param merge whether to merge the form structure link with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the form structure link that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormStructureLink updateFormStructureLink(
		com.liferay.portlet.forms.model.FormStructureLink formStructureLink,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _formStructureLinkLocalService.updateFormStructureLink(formStructureLink,
			merge);
	}

	public com.liferay.portlet.forms.model.FormStructureLink addFormStructureLink(
		java.lang.String formStructureId, java.lang.String className,
		long classPK, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _formStructureLinkLocalService.addFormStructureLink(formStructureId,
			className, classPK, serviceContext);
	}

	public void addFormStructureLinkResources(
		com.liferay.portlet.forms.model.FormStructureLink formStructureLink,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_formStructureLinkLocalService.addFormStructureLinkResources(formStructureLink,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFormStructureLinkResources(
		com.liferay.portlet.forms.model.FormStructureLink formStructureLink,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_formStructureLinkLocalService.addFormStructureLinkResources(formStructureLink,
			communityPermissions, guestPermissions);
	}

	public void deleteFormStructureLink(java.lang.String formStructureId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_formStructureLinkLocalService.deleteFormStructureLink(formStructureId,
			className, classPK);
	}

	public com.liferay.portlet.forms.model.FormStructureLink getFormStructureLink(
		java.lang.String formStructureId, java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _formStructureLinkLocalService.getFormStructureLink(formStructureId,
			className, classPK);
	}

	public java.util.List<com.liferay.portlet.forms.model.FormStructureLink> getFormStructureLinks(
		java.lang.String formStructureId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _formStructureLinkLocalService.getFormStructureLinks(formStructureId,
			start, end);
	}

	public com.liferay.portlet.forms.model.FormStructureLink updateFormStructureLink(
		long formStructureLinkId, java.lang.String formStructureId,
		long groupId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _formStructureLinkLocalService.updateFormStructureLink(formStructureLinkId,
			formStructureId, groupId, className, classPK);
	}

	public FormStructureLinkLocalService getWrappedFormStructureLinkLocalService() {
		return _formStructureLinkLocalService;
	}

	public void setWrappedFormStructureLinkLocalService(
		FormStructureLinkLocalService formStructureLinkLocalService) {
		_formStructureLinkLocalService = formStructureLinkLocalService;
	}

	private FormStructureLinkLocalService _formStructureLinkLocalService;
}