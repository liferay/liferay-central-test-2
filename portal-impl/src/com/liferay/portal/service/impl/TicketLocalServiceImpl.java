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

package com.liferay.portal.service.impl;

import java.util.Date;
import java.util.UUID;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Ticket;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.TicketLocalServiceBaseImpl;

/**
 * <a href="TicketLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Mika Koivisto
 */
public class TicketLocalServiceImpl extends TicketLocalServiceBaseImpl {
	public Ticket addTicket(
		long expirationTime, String className, long classPK,
		ServiceContext serviceContext) throws SystemException {

		long ticketId = counterLocalService.increment(Ticket.class.getName());
		long classNameId = classNameLocalService.getClassNameId(className);
		Date now = new Date();

		Ticket ticket = ticketPersistence.create(ticketId);

		ticket.setCompanyId(serviceContext.getCompanyId());
		ticket.setCreateDate(now);
		if (expirationTime == 0) {
			ticket.setExpirationDate(expirationTime);
		}
		else {
			ticket.setExpirationDate(now.getTime() + expirationTime);
		}

		ticket.setClassNameId(classNameId);
		ticket.setClassPK(classPK);
		ticket.setKey(UUID.randomUUID().toString());

		ticketPersistence.update(ticket, false);

		return ticket;
	}

	public Ticket findByKey(String key) throws PortalException, SystemException {
		return ticketPersistence.findByKey(key);
	}
}