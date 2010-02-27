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

import com.liferay.portal.kernel.annotation.Isolation;
import com.liferay.portal.kernel.annotation.Propagation;
import com.liferay.portal.kernel.annotation.Transactional;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

/**
 * <a href="MBMessageFlagLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * {@link
 * com.liferay.portlet.messageboards.service.impl.MBMessageFlagLocalServiceImpl}}.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBMessageFlagLocalServiceUtil
 * @generated
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface MBMessageFlagLocalService {
	public com.liferay.portlet.messageboards.model.MBMessageFlag addMBMessageFlag(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessageFlag createMBMessageFlag(
		long messageFlagId);

	public void deleteMBMessageFlag(long messageFlagId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void deleteMBMessageFlag(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.messageboards.model.MBMessageFlag getMBMessageFlag(
		long messageFlagId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> getMBMessageFlags(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getMBMessageFlagsCount()
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessageFlag updateMBMessageFlag(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessageFlag updateMBMessageFlag(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void addReadFlags(long userId,
		com.liferay.portlet.messageboards.model.MBThread thread)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void addQuestionFlag(long messageId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void deleteFlags(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void deleteFlags(long messageId, int flag)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void deleteQuestionAndAnswerFlags(long threadId)
		throws com.liferay.portal.kernel.exception.SystemException;

	public void deleteThreadFlags(long threadId)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.messageboards.model.MBMessageFlag getReadFlag(
		long userId, com.liferay.portlet.messageboards.model.MBThread thread)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasAnswerFlag(long messageId)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasQuestionFlag(long messageId)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public boolean hasReadFlag(long userId,
		com.liferay.portlet.messageboards.model.MBThread thread)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;
}