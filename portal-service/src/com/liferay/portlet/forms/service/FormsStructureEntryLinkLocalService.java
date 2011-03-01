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
 * The interface for the forms structure entry link local service.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FormsStructureEntryLinkLocalServiceUtil
 * @see com.liferay.portlet.forms.service.base.FormsStructureEntryLinkLocalServiceBaseImpl
 * @see com.liferay.portlet.forms.service.impl.FormsStructureEntryLinkLocalServiceImpl
 * @generated
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface FormsStructureEntryLinkLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link FormsStructureEntryLinkLocalServiceUtil} to access the forms structure entry link local service. Add custom service methods to {@link com.liferay.portlet.forms.service.impl.FormsStructureEntryLinkLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the forms structure entry link to the database. Also notifies the appropriate model listeners.
	*
	* @param formsStructureEntryLink the forms structure entry link to add
	* @return the forms structure entry link that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormsStructureEntryLink addFormsStructureEntryLink(
		com.liferay.portlet.forms.model.FormsStructureEntryLink formsStructureEntryLink)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Creates a new forms structure entry link with the primary key. Does not add the forms structure entry link to the database.
	*
	* @param structureEntryLinkId the primary key for the new forms structure entry link
	* @return the new forms structure entry link
	*/
	public com.liferay.portlet.forms.model.FormsStructureEntryLink createFormsStructureEntryLink(
		long structureEntryLinkId);

	/**
	* Deletes the forms structure entry link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param structureEntryLinkId the primary key of the forms structure entry link to delete
	* @throws PortalException if a forms structure entry link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteFormsStructureEntryLink(long structureEntryLinkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Deletes the forms structure entry link from the database. Also notifies the appropriate model listeners.
	*
	* @param formsStructureEntryLink the forms structure entry link to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteFormsStructureEntryLink(
		com.liferay.portlet.forms.model.FormsStructureEntryLink formsStructureEntryLink)
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
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
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
	* Gets the forms structure entry link with the primary key.
	*
	* @param structureEntryLinkId the primary key of the forms structure entry link to get
	* @return the forms structure entry link
	* @throws PortalException if a forms structure entry link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.forms.model.FormsStructureEntryLink getFormsStructureEntryLink(
		long structureEntryLinkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Gets a range of all the forms structure entry links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of forms structure entry links to return
	* @param end the upper bound of the range of forms structure entry links to return (not inclusive)
	* @return the range of forms structure entry links
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.forms.model.FormsStructureEntryLink> getFormsStructureEntryLinks(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Gets the number of forms structure entry links.
	*
	* @return the number of forms structure entry links
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFormsStructureEntryLinksCount()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates the forms structure entry link in the database. Also notifies the appropriate model listeners.
	*
	* @param formsStructureEntryLink the forms structure entry link to update
	* @return the forms structure entry link that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormsStructureEntryLink updateFormsStructureEntryLink(
		com.liferay.portlet.forms.model.FormsStructureEntryLink formsStructureEntryLink)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates the forms structure entry link in the database. Also notifies the appropriate model listeners.
	*
	* @param formsStructureEntryLink the forms structure entry link to update
	* @param merge whether to merge the forms structure entry link with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the forms structure entry link that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.forms.model.FormsStructureEntryLink updateFormsStructureEntryLink(
		com.liferay.portlet.forms.model.FormsStructureEntryLink formsStructureEntryLink,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Gets the Spring bean id for this ServiceBean.
	*
	* @return the Spring bean id for this ServiceBean
	*/
	public java.lang.String getIdentifier();

	/**
	* Sets the Spring bean id for this ServiceBean.
	*
	* @param identifier the Spring bean id for this ServiceBean
	*/
	public void setIdentifier(java.lang.String identifier);

	public com.liferay.portlet.forms.model.FormsStructureEntryLink addStructureEntryLink(
		java.lang.String structureId, java.lang.String className, long classPK,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void deleteStructureEntryLink(
		com.liferay.portlet.forms.model.FormsStructureEntryLink structureEntryLink)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void deleteStructureEntryLink(long structureEntryLinkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void deleteStructureEntryLink(java.lang.String structureId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.forms.model.FormsStructureEntryLink getStructureEntryLink(
		long structureEntryLinkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.forms.model.FormsStructureEntryLink getStructureEntryLink(
		java.lang.String structureId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.forms.model.FormsStructureEntryLink> getStructureEntryLinks(
		java.lang.String structureId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.forms.model.FormsStructureEntryLink updateStructureEntryLink(
		long structureEntryLinkId, java.lang.String structureId, long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;
}