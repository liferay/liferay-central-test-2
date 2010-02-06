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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="MBMessageFlagLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link MBMessageFlagLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBMessageFlagLocalService
 * @generated
 */
public class MBMessageFlagLocalServiceUtil {
	public static com.liferay.portlet.messageboards.model.MBMessageFlag addMBMessageFlag(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag)
		throws com.liferay.portal.SystemException {
		return getService().addMBMessageFlag(mbMessageFlag);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag createMBMessageFlag(
		long messageFlagId) {
		return getService().createMBMessageFlag(messageFlagId);
	}

	public static void deleteMBMessageFlag(long messageFlagId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteMBMessageFlag(messageFlagId);
	}

	public static void deleteMBMessageFlag(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag)
		throws com.liferay.portal.SystemException {
		getService().deleteMBMessageFlag(mbMessageFlag);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag getMBMessageFlag(
		long messageFlagId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getMBMessageFlag(messageFlagId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBMessageFlag> getMBMessageFlags(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getMBMessageFlags(start, end);
	}

	public static int getMBMessageFlagsCount()
		throws com.liferay.portal.SystemException {
		return getService().getMBMessageFlagsCount();
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag updateMBMessageFlag(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag)
		throws com.liferay.portal.SystemException {
		return getService().updateMBMessageFlag(mbMessageFlag);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag updateMBMessageFlag(
		com.liferay.portlet.messageboards.model.MBMessageFlag mbMessageFlag,
		boolean merge) throws com.liferay.portal.SystemException {
		return getService().updateMBMessageFlag(mbMessageFlag, merge);
	}

	public static void addReadFlags(long userId,
		com.liferay.portlet.messageboards.model.MBThread thread)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().addReadFlags(userId, thread);
	}

	public static void addQuestionFlag(long messageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().addQuestionFlag(messageId);
	}

	public static void deleteFlags(long userId)
		throws com.liferay.portal.SystemException {
		getService().deleteFlags(userId);
	}

	public static void deleteFlags(long messageId, int flag)
		throws com.liferay.portal.SystemException {
		getService().deleteFlags(messageId, flag);
	}

	public static void deleteQuestionAndAnswerFlags(long threadId)
		throws com.liferay.portal.SystemException {
		getService().deleteQuestionAndAnswerFlags(threadId);
	}

	public static void deleteThreadFlags(long threadId)
		throws com.liferay.portal.SystemException {
		getService().deleteThreadFlags(threadId);
	}

	public static com.liferay.portlet.messageboards.model.MBMessageFlag getReadFlag(
		long userId, com.liferay.portlet.messageboards.model.MBThread thread)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getReadFlag(userId, thread);
	}

	public static boolean hasAnswerFlag(long messageId)
		throws com.liferay.portal.SystemException {
		return getService().hasAnswerFlag(messageId);
	}

	public static boolean hasQuestionFlag(long messageId)
		throws com.liferay.portal.SystemException {
		return getService().hasQuestionFlag(messageId);
	}

	public static boolean hasReadFlag(long userId,
		com.liferay.portlet.messageboards.model.MBThread thread)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().hasReadFlag(userId, thread);
	}

	public static MBMessageFlagLocalService getService() {
		if (_service == null) {
			_service = (MBMessageFlagLocalService)PortalBeanLocatorUtil.locate(MBMessageFlagLocalService.class.getName());
		}

		return _service;
	}

	public void setService(MBMessageFlagLocalService service) {
		_service = service;
	}

	private static MBMessageFlagLocalService _service;
}