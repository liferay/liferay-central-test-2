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

package com.liferay.portlet.messageboards.service;

/**
 * <p>
 * This class is a wrapper for {@link MBMessageFlagLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBMessageFlagLocalService
 * @generated
 */
public class MBMessageFlagLocalServiceWrapper
	implements MBMessageFlagLocalService {
	public MBMessageFlagLocalServiceWrapper(
		MBMessageFlagLocalService mbMessageFlagLocalService) {
		_mbMessageFlagLocalService = mbMessageFlagLocalService;
	}

	/**
	* Adds the message boards message flag to the database. Also notifies the appropriate model listeners.
	*
	* @param mbMessageFlag the message boards message flag to add
	* @return the message boards message flag that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBMessageFlag addMBMessageFlag(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageFlagLocalService.addMBMessageFlag(mbMessageFlag);
	}

	/**
	* Creates a new message boards message flag with the primary key. Does not add the message boards message flag to the database.
	*
	* @param messageFlagId the primary key for the new message boards message flag
	* @return the new message boards message flag
	*/
	public com.liferay.portlet.messageboards.model.MBMessageFlag createMBMessageFlag(
		long messageFlagId) {
		return _mbMessageFlagLocalService.createMBMessageFlag(messageFlagId);
	}

	/**
	* Deletes the message boards message flag with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param messageFlagId the primary key of the message boards message flag to delete
	* @throws PortalException if a message boards message flag with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteMBMessageFlag(long messageFlagId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbMessageFlagLocalService.deleteMBMessageFlag(messageFlagId);
	}

	/**
	* Deletes the message boards message flag from the database. Also notifies the appropriate model listeners.
	*
	* @param mbMessageFlag the message boards message flag to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteMBMessageFlag(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag)
		throws com.liferay.portal.kernel.exception.SystemException {
		_mbMessageFlagLocalService.deleteMBMessageFlag(mbMessageFlag);
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
		return _mbMessageFlagLocalService.dynamicQuery(dynamicQuery);
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
		return _mbMessageFlagLocalService.dynamicQuery(dynamicQuery, start, end);
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
		return _mbMessageFlagLocalService.dynamicQuery(dynamicQuery, start,
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
		return _mbMessageFlagLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the message boards message flag with the primary key.
	*
	* @param messageFlagId the primary key of the message boards message flag to get
	* @return the message boards message flag
	* @throws PortalException if a message boards message flag with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBMessageFlag getMBMessageFlag(
		long messageFlagId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageFlagLocalService.getMBMessageFlag(messageFlagId);
	}

	/**
	* Gets a range of all the message boards message flags.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of message boards message flags to return
	* @param end the upper bound of the range of message boards message flags to return (not inclusive)
	* @return the range of message boards message flags
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> getMBMessageFlags(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageFlagLocalService.getMBMessageFlags(start, end);
	}

	/**
	* Gets the number of message boards message flags.
	*
	* @return the number of message boards message flags
	* @throws SystemException if a system exception occurred
	*/
	public int getMBMessageFlagsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageFlagLocalService.getMBMessageFlagsCount();
	}

	/**
	* Updates the message boards message flag in the database. Also notifies the appropriate model listeners.
	*
	* @param mbMessageFlag the message boards message flag to update
	* @return the message boards message flag that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBMessageFlag updateMBMessageFlag(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageFlagLocalService.updateMBMessageFlag(mbMessageFlag);
	}

	/**
	* Updates the message boards message flag in the database. Also notifies the appropriate model listeners.
	*
	* @param mbMessageFlag the message boards message flag to update
	* @param merge whether to merge the message boards message flag with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the message boards message flag that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.messageboards.model.MBMessageFlag updateMBMessageFlag(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageFlagLocalService.updateMBMessageFlag(mbMessageFlag,
			merge);
	}

	public void addReadFlags(long userId,
		com.liferay.portlet.messageboards.model.MBThread thread)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbMessageFlagLocalService.addReadFlags(userId, thread);
	}

	public void addQuestionFlag(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbMessageFlagLocalService.addQuestionFlag(messageId);
	}

	public void deleteAnswerFlags(long threadId, long messageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_mbMessageFlagLocalService.deleteAnswerFlags(threadId, messageId);
	}

	public void deleteFlags(long userId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_mbMessageFlagLocalService.deleteFlags(userId);
	}

	public void deleteFlags(long messageId, int flag)
		throws com.liferay.portal.kernel.exception.SystemException {
		_mbMessageFlagLocalService.deleteFlags(messageId, flag);
	}

	public void deleteQuestionAndAnswerFlags(long threadId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_mbMessageFlagLocalService.deleteQuestionAndAnswerFlags(threadId);
	}

	public void deleteThreadFlags(long threadId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_mbMessageFlagLocalService.deleteThreadFlags(threadId);
	}

	public com.liferay.portlet.messageboards.model.MBMessageFlag getReadFlag(
		long userId, com.liferay.portlet.messageboards.model.MBThread thread)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageFlagLocalService.getReadFlag(userId, thread);
	}

	public boolean hasAnswerFlag(long messageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageFlagLocalService.hasAnswerFlag(messageId);
	}

	public boolean hasQuestionFlag(long messageId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageFlagLocalService.hasQuestionFlag(messageId);
	}

	public boolean hasReadFlag(long userId,
		com.liferay.portlet.messageboards.model.MBThread thread)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageFlagLocalService.hasReadFlag(userId, thread);
	}

	public MBMessageFlagLocalService getWrappedMBMessageFlagLocalService() {
		return _mbMessageFlagLocalService;
	}

	private MBMessageFlagLocalService _mbMessageFlagLocalService;
}