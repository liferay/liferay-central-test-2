/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
 * <code>com.liferay.portlet.announcements.service.AnnouncementsDeliveryLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.announcements.service.AnnouncementsDeliveryLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.announcements.service.AnnouncementsDeliveryLocalService
 * @see com.liferay.portlet.announcements.service.AnnouncementsDeliveryLocalServiceFactory
 *
 */
public class AnnouncementsDeliveryLocalServiceUtil {
	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery addAnnouncementsDelivery(
		com.liferay.portlet.announcements.model.AnnouncementsDelivery announcementsDelivery)
		throws com.liferay.portal.SystemException {
		AnnouncementsDeliveryLocalService announcementsDeliveryLocalService = AnnouncementsDeliveryLocalServiceFactory.getService();

		return announcementsDeliveryLocalService.addAnnouncementsDelivery(announcementsDelivery);
	}

	public static void deleteAnnouncementsDelivery(long deliveryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementsDeliveryLocalService announcementsDeliveryLocalService = AnnouncementsDeliveryLocalServiceFactory.getService();

		announcementsDeliveryLocalService.deleteAnnouncementsDelivery(deliveryId);
	}

	public static void deleteAnnouncementsDelivery(
		com.liferay.portlet.announcements.model.AnnouncementsDelivery announcementsDelivery)
		throws com.liferay.portal.SystemException {
		AnnouncementsDeliveryLocalService announcementsDeliveryLocalService = AnnouncementsDeliveryLocalServiceFactory.getService();

		announcementsDeliveryLocalService.deleteAnnouncementsDelivery(announcementsDelivery);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		AnnouncementsDeliveryLocalService announcementsDeliveryLocalService = AnnouncementsDeliveryLocalServiceFactory.getService();

		return announcementsDeliveryLocalService.dynamicQuery(dynamicQuery);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		AnnouncementsDeliveryLocalService announcementsDeliveryLocalService = AnnouncementsDeliveryLocalServiceFactory.getService();

		return announcementsDeliveryLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery getAnnouncementsDelivery(
		long deliveryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementsDeliveryLocalService announcementsDeliveryLocalService = AnnouncementsDeliveryLocalServiceFactory.getService();

		return announcementsDeliveryLocalService.getAnnouncementsDelivery(deliveryId);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery updateAnnouncementsDelivery(
		com.liferay.portlet.announcements.model.AnnouncementsDelivery announcementsDelivery)
		throws com.liferay.portal.SystemException {
		AnnouncementsDeliveryLocalService announcementsDeliveryLocalService = AnnouncementsDeliveryLocalServiceFactory.getService();

		return announcementsDeliveryLocalService.updateAnnouncementsDelivery(announcementsDelivery);
	}

	public static void deleteDeliveries(long userId)
		throws com.liferay.portal.SystemException {
		AnnouncementsDeliveryLocalService announcementsDeliveryLocalService = AnnouncementsDeliveryLocalServiceFactory.getService();

		announcementsDeliveryLocalService.deleteDeliveries(userId);
	}

	public static void deleteDelivery(long deliveryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementsDeliveryLocalService announcementsDeliveryLocalService = AnnouncementsDeliveryLocalServiceFactory.getService();

		announcementsDeliveryLocalService.deleteDelivery(deliveryId);
	}

	public static void deleteDelivery(long userId, java.lang.String type)
		throws com.liferay.portal.SystemException {
		AnnouncementsDeliveryLocalService announcementsDeliveryLocalService = AnnouncementsDeliveryLocalServiceFactory.getService();

		announcementsDeliveryLocalService.deleteDelivery(userId, type);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery getDelivery(
		long deliveryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementsDeliveryLocalService announcementsDeliveryLocalService = AnnouncementsDeliveryLocalServiceFactory.getService();

		return announcementsDeliveryLocalService.getDelivery(deliveryId);
	}

	public static java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> getUserDeliveries(
		long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementsDeliveryLocalService announcementsDeliveryLocalService = AnnouncementsDeliveryLocalServiceFactory.getService();

		return announcementsDeliveryLocalService.getUserDeliveries(userId);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery getUserDelivery(
		long userId, java.lang.String type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementsDeliveryLocalService announcementsDeliveryLocalService = AnnouncementsDeliveryLocalServiceFactory.getService();

		return announcementsDeliveryLocalService.getUserDelivery(userId, type);
	}

	public static com.liferay.portlet.announcements.model.AnnouncementsDelivery updateDelivery(
		long userId, java.lang.String type, boolean email, boolean sms,
		boolean website)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		AnnouncementsDeliveryLocalService announcementsDeliveryLocalService = AnnouncementsDeliveryLocalServiceFactory.getService();

		return announcementsDeliveryLocalService.updateDelivery(userId, type,
			email, sms, website);
	}
}