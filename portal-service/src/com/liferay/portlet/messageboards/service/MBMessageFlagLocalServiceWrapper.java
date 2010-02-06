/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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
		throws com.liferay.portal.SystemException {
		return _mbMessageFlagLocalService.addMBMessageFlag(mbMessageFlag);
	}

	public com.liferay.portlet.messageboards.model.MBMessageFlag createMBMessageFlag(
		long messageFlagId) {
		return _mbMessageFlagLocalService.createMBMessageFlag(messageFlagId);
	}

	public void deleteMBMessageFlag(long messageFlagId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_mbMessageFlagLocalService.deleteMBMessageFlag(messageFlagId);
	}

	public void deleteMBMessageFlag(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag)
		throws com.liferay.portal.SystemException {
		_mbMessageFlagLocalService.deleteMBMessageFlag(mbMessageFlag);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _mbMessageFlagLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _mbMessageFlagLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.messageboards.model.MBMessageFlag getMBMessageFlag(
		long messageFlagId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _mbMessageFlagLocalService.getMBMessageFlag(messageFlagId);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> getMBMessageFlags(
		int start, int end) throws com.liferay.portal.SystemException {
		return _mbMessageFlagLocalService.getMBMessageFlags(start, end);
	}

	public int getMBMessageFlagsCount()
		throws com.liferay.portal.SystemException {
		return _mbMessageFlagLocalService.getMBMessageFlagsCount();
	}

	public com.liferay.portlet.messageboards.model.MBMessageFlag updateMBMessageFlag(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag)
		throws com.liferay.portal.SystemException {
		return _mbMessageFlagLocalService.updateMBMessageFlag(mbMessageFlag);
	}

	public com.liferay.portlet.messageboards.model.MBMessageFlag updateMBMessageFlag(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag,
		boolean merge) throws com.liferay.portal.SystemException {
		return _mbMessageFlagLocalService.updateMBMessageFlag(mbMessageFlag,
			merge);
	}

	public void addReadFlags(long userId,
		com.liferay.portlet.messageboards.model.MBThread thread)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_mbMessageFlagLocalService.addReadFlags(userId, thread);
	}

	public void addQuestionFlag(long messageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_mbMessageFlagLocalService.addQuestionFlag(messageId);
	}

	public void deleteFlags(long userId)
		throws com.liferay.portal.SystemException {
		_mbMessageFlagLocalService.deleteFlags(userId);
	}

	public void deleteFlags(long messageId, int flag)
		throws com.liferay.portal.SystemException {
		_mbMessageFlagLocalService.deleteFlags(messageId, flag);
	}

	public void deleteQuestionAndAnswerFlags(long threadId)
		throws com.liferay.portal.SystemException {
		_mbMessageFlagLocalService.deleteQuestionAndAnswerFlags(threadId);
	}

	public void deleteThreadFlags(long threadId)
		throws com.liferay.portal.SystemException {
		_mbMessageFlagLocalService.deleteThreadFlags(threadId);
	}

	public com.liferay.portlet.messageboards.model.MBMessageFlag getReadFlag(
		long userId, com.liferay.portlet.messageboards.model.MBThread thread)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _mbMessageFlagLocalService.getReadFlag(userId, thread);
	}

	public boolean hasAnswerFlag(long messageId)
		throws com.liferay.portal.SystemException {
		return _mbMessageFlagLocalService.hasAnswerFlag(messageId);
	}

	public boolean hasQuestionFlag(long messageId)
		throws com.liferay.portal.SystemException {
		return _mbMessageFlagLocalService.hasQuestionFlag(messageId);
	}

	public boolean hasReadFlag(long userId,
		com.liferay.portlet.messageboards.model.MBThread thread)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _mbMessageFlagLocalService.hasReadFlag(userId, thread);
	}

	public MBMessageFlagLocalService getWrappedMBMessageFlagLocalService() {
		return _mbMessageFlagLocalService;
	}

	private MBMessageFlagLocalService _mbMessageFlagLocalService;
}