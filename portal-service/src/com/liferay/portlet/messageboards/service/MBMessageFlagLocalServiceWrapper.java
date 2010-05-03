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
 * <a href="MBMessageFlagLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
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

	public com.liferay.portlet.messageboards.model.MBMessageFlag addMBMessageFlag(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageFlagLocalService.addMBMessageFlag(mbMessageFlag);
	}

	public com.liferay.portlet.messageboards.model.MBMessageFlag createMBMessageFlag(
		long messageFlagId) {
		return _mbMessageFlagLocalService.createMBMessageFlag(messageFlagId);
	}

	public void deleteMBMessageFlag(long messageFlagId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbMessageFlagLocalService.deleteMBMessageFlag(messageFlagId);
	}

	public void deleteMBMessageFlag(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag)
		throws com.liferay.portal.kernel.exception.SystemException {
		_mbMessageFlagLocalService.deleteMBMessageFlag(mbMessageFlag);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageFlagLocalService.dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageFlagLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageFlagLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageFlagLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portlet.messageboards.model.MBMessageFlag getMBMessageFlag(
		long messageFlagId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageFlagLocalService.getMBMessageFlag(messageFlagId);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> getMBMessageFlags(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageFlagLocalService.getMBMessageFlags(start, end);
	}

	public int getMBMessageFlagsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageFlagLocalService.getMBMessageFlagsCount();
	}

	public com.liferay.portlet.messageboards.model.MBMessageFlag updateMBMessageFlag(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbMessageFlagLocalService.updateMBMessageFlag(mbMessageFlag);
	}

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