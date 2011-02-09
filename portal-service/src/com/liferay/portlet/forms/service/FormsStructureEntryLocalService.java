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

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

/**
 * The interface for the forms structure entry local service.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FormsStructureEntryLocalServiceUtil
 * @see com.liferay.portlet.forms.service.base.FormsStructureEntryLocalServiceBaseImpl
 * @see com.liferay.portlet.forms.service.impl.FormsStructureEntryLocalServiceImpl
 * @generated
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface FormsStructureEntryLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FormsStructureEntryLocalServiceUtil} to access the forms structure entry local service. Add custom service methods to {@link com.liferay.portlet.forms.service.impl.FormsStructureEntryLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the forms structure entry to the database. Also notifies the appropriate model listeners.
	*
	* @param formsStructureEntry the forms structure entry to add
	* @return the forms structure entry that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormsStructureEntry addFormsStructureEntry(
		com.liferay.portlet.forms.model.FormsStructureEntry formsStructureEntry)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Creates a new forms structure entry with the primary key. Does not add the forms structure entry to the database.
	*
	* @param structureEntryId the primary key for the new forms structure entry
	* @return the new forms structure entry
	*/
	public com.liferay.portlet.forms.model.FormsStructureEntry createFormsStructureEntry(
		long structureEntryId);

	/**
	* Deletes the forms structure entry with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param structureEntryId the primary key of the forms structure entry to delete
	* @throws PortalException if a forms structure entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteFormsStructureEntry(long structureEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Deletes the forms structure entry from the database. Also notifies the appropriate model listeners.
	*
	* @param formsStructureEntry the forms structure entry to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteFormsStructureEntry(
		com.liferay.portlet.forms.model.FormsStructureEntry formsStructureEntry)
		throws com.liferay.portal.kernel.exception.SystemException;

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
		throws com.liferay.portal.kernel.exception.SystemException;

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
		int end) throws com.liferay.portal.kernel.exception.SystemException;

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
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Gets the forms structure entry with the primary key.
	*
	* @param structureEntryId the primary key of the forms structure entry to get
	* @return the forms structure entry
	* @throws PortalException if a forms structure entry with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.forms.model.FormsStructureEntry getFormsStructureEntry(
		long structureEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Gets the forms structure entry with the UUID and group id.
	*
	* @param uuid the UUID of forms structure entry to get
	* @param groupId the group id of the forms structure entry to get
	* @return the forms structure entry
	* @throws PortalException if a forms structure entry with the UUID and group id could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.forms.model.FormsStructureEntry getFormsStructureEntryByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Gets a range of all the forms structure entries.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of forms structure entries to return
	* @param end the upper bound of the range of forms structure entries to return (not inclusive)
	* @return the range of forms structure entries
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.forms.model.FormsStructureEntry> getFormsStructureEntries(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Gets the number of forms structure entries.
	*
	* @return the number of forms structure entries
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFormsStructureEntriesCount()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates the forms structure entry in the database. Also notifies the appropriate model listeners.
	*
	* @param formsStructureEntry the forms structure entry to update
	* @return the forms structure entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormsStructureEntry updateFormsStructureEntry(
		com.liferay.portlet.forms.model.FormsStructureEntry formsStructureEntry)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates the forms structure entry in the database. Also notifies the appropriate model listeners.
	*
	* @param formsStructureEntry the forms structure entry to update
	* @param merge whether to merge the forms structure entry with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the forms structure entry that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormsStructureEntry updateFormsStructureEntry(
		com.liferay.portlet.forms.model.FormsStructureEntry formsStructureEntry,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.forms.model.FormsStructureEntry addStructureEntry(
		long userId, long groupId, java.lang.String structureId,
		boolean autoStructureId, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void addStructureEntryResources(
		com.liferay.portlet.forms.model.FormsStructureEntry structureEntry,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void addStructureEntryResources(
		com.liferay.portlet.forms.model.FormsStructureEntry structureEntry,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void deleteStructureEntries(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void deleteStructureEntry(
		com.liferay.portlet.forms.model.FormsStructureEntry structureEntry)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void deleteStructureEntry(long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.forms.model.FormsStructureEntry> getStructureEntries()
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.forms.model.FormsStructureEntry> getStructureEntries(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.forms.model.FormsStructureEntry> getStructureEntries(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getStructureEntriesCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.forms.model.FormsStructureEntry getStructureEntry(
		long structureEntryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.forms.model.FormsStructureEntry getStructureEntry(
		long groupId, java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.forms.model.FormsStructureEntry updateStructureEntry(
		long groupId, java.lang.String structureId, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;
}