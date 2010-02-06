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
 * <a href="MBBanLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link MBBanLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBBanLocalService
 * @generated
 */
public class MBBanLocalServiceUtil {
	public static com.liferay.portlet.messageboards.model.MBBan addMBBan(
		com.liferay.portlet.messageboards.model.MBBan mbBan)
		throws com.liferay.portal.SystemException {
		return getService().addMBBan(mbBan);
	}

	public static com.liferay.portlet.messageboards.model.MBBan createMBBan(
		long banId) {
		return getService().createMBBan(banId);
	}

	public static void deleteMBBan(long banId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteMBBan(banId);
	}

	public static void deleteMBBan(
		com.liferay.portlet.messageboards.model.MBBan mbBan)
		throws com.liferay.portal.SystemException {
		getService().deleteMBBan(mbBan);
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

	public static com.liferay.portlet.messageboards.model.MBBan getMBBan(
		long banId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getMBBan(banId);
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBBan> getMBBans(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getMBBans(start, end);
	}

	public static int getMBBansCount()
		throws com.liferay.portal.SystemException {
		return getService().getMBBansCount();
	}

	public static com.liferay.portlet.messageboards.model.MBBan updateMBBan(
		com.liferay.portlet.messageboards.model.MBBan mbBan)
		throws com.liferay.portal.SystemException {
		return getService().updateMBBan(mbBan);
	}

	public static com.liferay.portlet.messageboards.model.MBBan updateMBBan(
		com.liferay.portlet.messageboards.model.MBBan mbBan, boolean merge)
		throws com.liferay.portal.SystemException {
		return getService().updateMBBan(mbBan, merge);
	}

	public static com.liferay.portlet.messageboards.model.MBBan addBan(
		long userId, long banUserId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().addBan(userId, banUserId, serviceContext);
	}

	public static void checkBan(long groupId, long banUserId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().checkBan(groupId, banUserId);
	}

	public static void deleteBan(long banUserId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.SystemException {
		getService().deleteBan(banUserId, serviceContext);
	}

	public static void deleteBansByBanUserId(long banUserId)
		throws com.liferay.portal.SystemException {
		getService().deleteBansByBanUserId(banUserId);
	}

	public static void deleteBansByGroupId(long groupId)
		throws com.liferay.portal.SystemException {
		getService().deleteBansByGroupId(groupId);
	}

	public static void expireBans() throws com.liferay.portal.SystemException {
		getService().expireBans();
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBBan> getBans(
		long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		return getService().getBans(groupId, start, end);
	}

	public static int getBansCount(long groupId)
		throws com.liferay.portal.SystemException {
		return getService().getBansCount(groupId);
	}

	public static boolean hasBan(long groupId, long banUserId)
		throws com.liferay.portal.SystemException {
		return getService().hasBan(groupId, banUserId);
	}

	public static MBBanLocalService getService() {
		if (_service == null) {
			_service = (MBBanLocalService)PortalBeanLocatorUtil.locate(MBBanLocalService.class.getName());
		}

		return _service;
	}

	public void setService(MBBanLocalService service) {
		_service = service;
	}

	private static MBBanLocalService _service;
}