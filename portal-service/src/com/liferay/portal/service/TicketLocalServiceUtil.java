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

package com.liferay.portal.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="TicketLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link TicketLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       TicketLocalService
 * @generated
 */
public class TicketLocalServiceUtil {
	public static com.liferay.portal.model.Ticket addTicket(
		com.liferay.portal.model.Ticket ticket)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addTicket(ticket);
	}

	public static com.liferay.portal.model.Ticket createTicket(long ticketId) {
		return getService().createTicket(ticketId);
	}

	public static void deleteTicket(long ticketId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTicket(ticketId);
	}

	public static void deleteTicket(com.liferay.portal.model.Ticket ticket)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTicket(ticket);
	}

	@SuppressWarnings("unchecked")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	@SuppressWarnings("unchecked")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portal.model.Ticket getTicket(long ticketId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getTicket(ticketId);
	}

	public static java.util.List<com.liferay.portal.model.Ticket> getTickets(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTickets(start, end);
	}

	public static int getTicketsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTicketsCount();
	}

	public static com.liferay.portal.model.Ticket updateTicket(
		com.liferay.portal.model.Ticket ticket)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateTicket(ticket);
	}

	public static com.liferay.portal.model.Ticket updateTicket(
		com.liferay.portal.model.Ticket ticket, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateTicket(ticket, merge);
	}

	public static com.liferay.portal.model.Ticket addTicket(long companyId,
		java.lang.String className, long classPK,
		java.util.Date expirationDate,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addTicket(companyId, className, classPK, expirationDate,
			serviceContext);
	}

	public static com.liferay.portal.model.Ticket getTicket(
		java.lang.String key)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getTicket(key);
	}

	public static TicketLocalService getService() {
		if (_service == null) {
			_service = (TicketLocalService)PortalBeanLocatorUtil.locate(TicketLocalService.class.getName());
		}

		return _service;
	}

	public void setService(TicketLocalService service) {
		_service = service;
	}

	private static TicketLocalService _service;
}