/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the ticket local service. This utility wraps {@link com.liferay.portal.service.impl.TicketLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see TicketLocalService
 * @see com.liferay.portal.service.base.TicketLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.TicketLocalServiceImpl
 * @generated
 */
public class TicketLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.TicketLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the ticket to the database. Also notifies the appropriate model listeners.
	*
	* @param ticket the ticket to add
	* @return the ticket that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Ticket addTicket(
		com.liferay.portal.model.Ticket ticket)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addTicket(ticket);
	}

	/**
	* Creates a new ticket with the primary key. Does not add the ticket to the database.
	*
	* @param ticketId the primary key for the new ticket
	* @return the new ticket
	*/
	public static com.liferay.portal.model.Ticket createTicket(long ticketId) {
		return getService().createTicket(ticketId);
	}

	/**
	* Deletes the ticket with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param ticketId the primary key of the ticket to delete
	* @throws PortalException if a ticket with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteTicket(long ticketId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTicket(ticketId);
	}

	/**
	* Deletes the ticket from the database. Also notifies the appropriate model listeners.
	*
	* @param ticket the ticket to delete
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteTicket(com.liferay.portal.model.Ticket ticket)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteTicket(ticket);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Counts the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the ticket with the primary key.
	*
	* @param ticketId the primary key of the ticket to get
	* @return the ticket
	* @throws PortalException if a ticket with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Ticket getTicket(long ticketId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getTicket(ticketId);
	}

	/**
	* Gets a range of all the tickets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of tickets to return
	* @param end the upper bound of the range of tickets to return (not inclusive)
	* @return the range of tickets
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Ticket> getTickets(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTickets(start, end);
	}

	/**
	* Gets the number of tickets.
	*
	* @return the number of tickets
	* @throws SystemException if a system exception occurred
	*/
	public static int getTicketsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getTicketsCount();
	}

	/**
	* Updates the ticket in the database. Also notifies the appropriate model listeners.
	*
	* @param ticket the ticket to update
	* @return the ticket that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Ticket updateTicket(
		com.liferay.portal.model.Ticket ticket)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateTicket(ticket);
	}

	/**
	* Updates the ticket in the database. Also notifies the appropriate model listeners.
	*
	* @param ticket the ticket to update
	* @param merge whether to merge the ticket with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the ticket that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Ticket updateTicket(
		com.liferay.portal.model.Ticket ticket, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateTicket(ticket, merge);
	}

	/**
	* Gets the Spring bean id for this ServiceBean.
	*
	* @return the Spring bean id for this ServiceBean
	*/
	public static java.lang.String getIdentifier() {
		return getService().getIdentifier();
	}

	/**
	* Sets the Spring bean id for this ServiceBean.
	*
	* @param identifier the Spring bean id for this ServiceBean
	*/
	public static void setIdentifier(java.lang.String identifier) {
		getService().setIdentifier(identifier);
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

			ReferenceRegistry.registerReference(TicketLocalServiceUtil.class,
				"_service");
			MethodCache.remove(TicketLocalService.class);
		}

		return _service;
	}

	public void setService(TicketLocalService service) {
		MethodCache.remove(TicketLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(TicketLocalServiceUtil.class,
			"_service");
		MethodCache.remove(TicketLocalService.class);
	}

	private static TicketLocalService _service;
}