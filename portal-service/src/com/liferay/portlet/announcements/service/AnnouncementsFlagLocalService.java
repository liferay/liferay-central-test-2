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

package com.liferay.portlet.announcements.service;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.transaction.Isolation;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;

/**
 * The interface for the announcements flag local service.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AnnouncementsFlagLocalServiceUtil
 * @see com.liferay.portlet.announcements.service.base.AnnouncementsFlagLocalServiceBaseImpl
 * @see com.liferay.portlet.announcements.service.impl.AnnouncementsFlagLocalServiceImpl
 * @generated
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface AnnouncementsFlagLocalService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AnnouncementsFlagLocalServiceUtil} to access the announcements flag local service. Add custom service methods to {@link com.liferay.portlet.announcements.service.impl.AnnouncementsFlagLocalServiceImpl} and rerun ServiceBuilder to automatically copy the method declarations to this interface.
	 */

	/**
	* Adds the announcements flag to the database. Also notifies the appropriate model listeners.
	*
	* @param announcementsFlag the announcements flag to add
	* @return the announcements flag that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.announcements.model.AnnouncementsFlag addAnnouncementsFlag(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Creates a new announcements flag with the primary key. Does not add the announcements flag to the database.
	*
	* @param flagId the primary key for the new announcements flag
	* @return the new announcements flag
	*/
	public com.liferay.portlet.announcements.model.AnnouncementsFlag createAnnouncementsFlag(
		long flagId);

	/**
	* Deletes the announcements flag with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param flagId the primary key of the announcements flag to delete
	* @throws PortalException if a announcements flag with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteAnnouncementsFlag(long flagId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Deletes the announcements flag from the database. Also notifies the appropriate model listeners.
	*
	* @param announcementsFlag the announcements flag to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteAnnouncementsFlag(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag)
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
	* Gets the announcements flag with the primary key.
	*
	* @param flagId the primary key of the announcements flag to get
	* @return the announcements flag
	* @throws PortalException if a announcements flag with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.announcements.model.AnnouncementsFlag getAnnouncementsFlag(
		long flagId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Gets a range of all the announcements flags.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of announcements flags to return
	* @param end the upper bound of the range of announcements flags to return (not inclusive)
	* @return the range of announcements flags
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.announcements.model.AnnouncementsFlag> getAnnouncementsFlags(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Gets the number of announcements flags.
	*
	* @return the number of announcements flags
	* @throws SystemException if a system exception occurred
	*/
	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getAnnouncementsFlagsCount()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates the announcements flag in the database. Also notifies the appropriate model listeners.
	*
	* @param announcementsFlag the announcements flag to update
	* @return the announcements flag that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.announcements.model.AnnouncementsFlag updateAnnouncementsFlag(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Updates the announcements flag in the database. Also notifies the appropriate model listeners.
	*
	* @param announcementsFlag the announcements flag to update
	* @param merge whether to merge the announcements flag with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the announcements flag that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.announcements.model.AnnouncementsFlag updateAnnouncementsFlag(
		com.liferay.portlet.announcements.model.AnnouncementsFlag announcementsFlag,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.announcements.model.AnnouncementsFlag addFlag(
		long userId, long entryId, int value)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void deleteFlag(
		com.liferay.portlet.announcements.model.AnnouncementsFlag flag)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void deleteFlag(long flagId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void deleteFlags(long entryId)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.announcements.model.AnnouncementsFlag getFlag(
		long userId, long entryId, int value)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;
}