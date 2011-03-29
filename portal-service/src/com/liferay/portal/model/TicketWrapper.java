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

package com.liferay.portal.model;

/**
 * <p>
 * This class is a wrapper for {@link Ticket}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Ticket
 * @generated
 */
public class TicketWrapper implements Ticket {
	public TicketWrapper(Ticket ticket) {
		_ticket = ticket;
	}

	public Class<?> getModelClass() {
		return Ticket.class;
	}

	public String getModelClassName() {
		return Ticket.class.getName();
	}

	/**
	* Gets the primary key of this ticket.
	*
	* @return the primary key of this ticket
	*/
	public long getPrimaryKey() {
		return _ticket.getPrimaryKey();
	}

	/**
	* Sets the primary key of this ticket
	*
	* @param pk the primary key of this ticket
	*/
	public void setPrimaryKey(long pk) {
		_ticket.setPrimaryKey(pk);
	}

	/**
	* Gets the ticket ID of this ticket.
	*
	* @return the ticket ID of this ticket
	*/
	public long getTicketId() {
		return _ticket.getTicketId();
	}

	/**
	* Sets the ticket ID of this ticket.
	*
	* @param ticketId the ticket ID of this ticket
	*/
	public void setTicketId(long ticketId) {
		_ticket.setTicketId(ticketId);
	}

	/**
	* Gets the company ID of this ticket.
	*
	* @return the company ID of this ticket
	*/
	public long getCompanyId() {
		return _ticket.getCompanyId();
	}

	/**
	* Sets the company ID of this ticket.
	*
	* @param companyId the company ID of this ticket
	*/
	public void setCompanyId(long companyId) {
		_ticket.setCompanyId(companyId);
	}

	/**
	* Gets the create date of this ticket.
	*
	* @return the create date of this ticket
	*/
	public java.util.Date getCreateDate() {
		return _ticket.getCreateDate();
	}

	/**
	* Sets the create date of this ticket.
	*
	* @param createDate the create date of this ticket
	*/
	public void setCreateDate(java.util.Date createDate) {
		_ticket.setCreateDate(createDate);
	}

	/**
	* Gets the class name of the model instance this ticket is polymorphically associated with.
	*
	* @return the class name of the model instance this ticket is polymorphically associated with
	*/
	public java.lang.String getClassName() {
		return _ticket.getClassName();
	}

	/**
	* Gets the class name ID of this ticket.
	*
	* @return the class name ID of this ticket
	*/
	public long getClassNameId() {
		return _ticket.getClassNameId();
	}

	/**
	* Sets the class name ID of this ticket.
	*
	* @param classNameId the class name ID of this ticket
	*/
	public void setClassNameId(long classNameId) {
		_ticket.setClassNameId(classNameId);
	}

	/**
	* Gets the class p k of this ticket.
	*
	* @return the class p k of this ticket
	*/
	public long getClassPK() {
		return _ticket.getClassPK();
	}

	/**
	* Sets the class p k of this ticket.
	*
	* @param classPK the class p k of this ticket
	*/
	public void setClassPK(long classPK) {
		_ticket.setClassPK(classPK);
	}

	/**
	* Gets the key of this ticket.
	*
	* @return the key of this ticket
	*/
	public java.lang.String getKey() {
		return _ticket.getKey();
	}

	/**
	* Sets the key of this ticket.
	*
	* @param key the key of this ticket
	*/
	public void setKey(java.lang.String key) {
		_ticket.setKey(key);
	}

	/**
	* Gets the expiration date of this ticket.
	*
	* @return the expiration date of this ticket
	*/
	public java.util.Date getExpirationDate() {
		return _ticket.getExpirationDate();
	}

	/**
	* Sets the expiration date of this ticket.
	*
	* @param expirationDate the expiration date of this ticket
	*/
	public void setExpirationDate(java.util.Date expirationDate) {
		_ticket.setExpirationDate(expirationDate);
	}

	public boolean isNew() {
		return _ticket.isNew();
	}

	public void setNew(boolean n) {
		_ticket.setNew(n);
	}

	public boolean isCachedModel() {
		return _ticket.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_ticket.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _ticket.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_ticket.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _ticket.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ticket.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ticket.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return new TicketWrapper((Ticket)_ticket.clone());
	}

	public int compareTo(com.liferay.portal.model.Ticket ticket) {
		return _ticket.compareTo(ticket);
	}

	public int hashCode() {
		return _ticket.hashCode();
	}

	public com.liferay.portal.model.Ticket toEscapedModel() {
		return new TicketWrapper(_ticket.toEscapedModel());
	}

	public java.lang.String toString() {
		return _ticket.toString();
	}

	public java.lang.String toXmlString() {
		return _ticket.toXmlString();
	}

	public boolean isExpired() {
		return _ticket.isExpired();
	}

	public Ticket getWrappedTicket() {
		return _ticket;
	}

	public void resetOriginalValues() {
		_ticket.resetOriginalValues();
	}

	private Ticket _ticket;
}