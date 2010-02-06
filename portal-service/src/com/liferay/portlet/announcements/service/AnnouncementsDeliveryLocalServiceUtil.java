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

package com.liferay.portlet.announcements.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="AnnouncementsDeliveryLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link AnnouncementsDeliveryLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AnnouncementsDeliveryLocalService
 * @generated
 */
public class AnnouncementsDeliveryLocalServiceUtil {
	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery addAnnouncementsDelivery(
		com.liferay.portlet.announcements.model.AnnouncementsDelivery announcementsDelivery)
		throws com.liferay.portal.SystemException {
		return getService().addAnnouncementsDelivery(announcementsDelivery);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery createAnnouncementsDelivery(
		long deliveryId) {
		return getService().createAnnouncementsDelivery(deliveryId);
	}

	public static void deleteAnnouncementsDelivery(long deliveryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteAnnouncementsDelivery(deliveryId);
	}

	public static void deleteAnnouncementsDelivery(
		com.liferay.portlet.announcements.model.AnnouncementsDelivery announcementsDelivery)
		throws com.liferay.portal.SystemException {
		getService().deleteAnnouncementsDelivery(announcementsDelivery);
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

	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery getAnnouncementsDelivery(
		long deliveryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getAnnouncementsDelivery(deliveryId);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> getAnnouncementsDeliveries(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getAnnouncementsDeliveries(start, end);
	}

	public static int getAnnouncementsDeliveriesCount()
		throws com.liferay.portal.SystemException {
		return getService().getAnnouncementsDeliveriesCount();
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery updateAnnouncementsDelivery(
		com.liferay.portlet.announcements.model.AnnouncementsDelivery announcementsDelivery)
		throws com.liferay.portal.SystemException {
		return getService().updateAnnouncementsDelivery(announcementsDelivery);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery updateAnnouncementsDelivery(
		com.liferay.portlet.announcements.model.AnnouncementsDelivery announcementsDelivery,
		boolean merge) throws com.liferay.portal.SystemException {
		return getService()
				   .updateAnnouncementsDelivery(announcementsDelivery, merge);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery addUserDelivery(
		long userId, java.lang.String type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().addUserDelivery(userId, type);
	}

	public static void deleteDeliveries(long userId)
		throws com.liferay.portal.SystemException {
		getService().deleteDeliveries(userId);
	}

	public static void deleteDelivery(long deliveryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteDelivery(deliveryId);
	}

	public static void deleteDelivery(long userId, java.lang.String type)
		throws com.liferay.portal.SystemException {
		getService().deleteDelivery(userId, type);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery getDelivery(
		long deliveryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getDelivery(deliveryId);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> getUserDeliveries(
		long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getUserDeliveries(userId);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery getUserDelivery(
		long userId, java.lang.String type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getUserDelivery(userId, type);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery updateDelivery(
		long userId, java.lang.String type, boolean email, boolean sms,
		boolean website)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().updateDelivery(userId, type, email, sms, website);
	}

	public static AnnouncementsDeliveryLocalService getService() {
		if (_service == null) {
			_service = (AnnouncementsDeliveryLocalService)PortalBeanLocatorUtil.locate(AnnouncementsDeliveryLocalService.class.getName());
		}

		return _service;
	}

	public void setService(AnnouncementsDeliveryLocalService service) {
		_service = service;
	}

	private static AnnouncementsDeliveryLocalService _service;
}