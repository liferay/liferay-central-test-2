/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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
 * <a href="MBBanLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the <code>com.liferay.portlet.messageboards.service.MBBanLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean instance.
 * It's convenient to be able to just write one line to call a method on a bean
 * instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.messageboards.service.MBBanLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.messageboards.service.MBBanLocalService
 * @see com.liferay.portlet.messageboards.service.MBBanLocalServiceFactory
 *
 */
public class MBBanLocalServiceUtil {
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		MBBanLocalService mbBanLocalService = MBBanLocalServiceFactory.getService();

		return mbBanLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		MBBanLocalService mbBanLocalService = MBBanLocalServiceFactory.getService();

		return mbBanLocalService.dynamicQuery(queryInitializer, begin, end);
	}

	public static com.liferay.portlet.messageboards.model.MBBan addBan(
		java.lang.String userId, java.lang.String plid,
		java.lang.String banUserId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBBanLocalService mbBanLocalService = MBBanLocalServiceFactory.getService();

		return mbBanLocalService.addBan(userId, plid, banUserId);
	}

	public static void checkBan(long groupId, java.lang.String banUserId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBBanLocalService mbBanLocalService = MBBanLocalServiceFactory.getService();
		mbBanLocalService.checkBan(groupId, banUserId);
	}

	public static void deleteBan(java.lang.String plid,
		java.lang.String banUserId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBBanLocalService mbBanLocalService = MBBanLocalServiceFactory.getService();
		mbBanLocalService.deleteBan(plid, banUserId);
	}

	public static void deleteBans(long groupId)
		throws com.liferay.portal.SystemException {
		MBBanLocalService mbBanLocalService = MBBanLocalServiceFactory.getService();
		mbBanLocalService.deleteBans(groupId);
	}

	public static void deleteBans(java.lang.String banUserId)
		throws com.liferay.portal.SystemException {
		MBBanLocalService mbBanLocalService = MBBanLocalServiceFactory.getService();
		mbBanLocalService.deleteBans(banUserId);
	}

	public static void expireBans() throws com.liferay.portal.SystemException {
		MBBanLocalService mbBanLocalService = MBBanLocalServiceFactory.getService();
		mbBanLocalService.expireBans();
	}

	public static java.util.List getBans(long groupId, int start, int end)
		throws com.liferay.portal.SystemException {
		MBBanLocalService mbBanLocalService = MBBanLocalServiceFactory.getService();

		return mbBanLocalService.getBans(groupId, start, end);
	}

	public static int getBansCount(long groupId)
		throws com.liferay.portal.SystemException {
		MBBanLocalService mbBanLocalService = MBBanLocalServiceFactory.getService();

		return mbBanLocalService.getBansCount(groupId);
	}

	public static boolean hasBan(long groupId, java.lang.String banUserId)
		throws com.liferay.portal.SystemException {
		MBBanLocalService mbBanLocalService = MBBanLocalServiceFactory.getService();

		return mbBanLocalService.hasBan(groupId, banUserId);
	}
}