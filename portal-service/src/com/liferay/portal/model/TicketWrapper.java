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

	public long getPrimaryKey() {
		return _ticket.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_ticket.setPrimaryKey(pk);
	}

	public long getTicketId() {
		return _ticket.getTicketId();
	}

	public void setTicketId(long ticketId) {
		_ticket.setTicketId(ticketId);
	}

	public long getCompanyId() {
		return _ticket.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_ticket.setCompanyId(companyId);
	}

	public java.util.Date getCreateDate() {
		return _ticket.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_ticket.setCreateDate(createDate);
	}

	public java.lang.String getClassName() {
		return _ticket.getClassName();
	}

	public long getClassNameId() {
		return _ticket.getClassNameId();
	}

	public void setClassNameId(long classNameId) {
		_ticket.setClassNameId(classNameId);
	}

	public long getClassPK() {
		return _ticket.getClassPK();
	}

	public void setClassPK(long classPK) {
		_ticket.setClassPK(classPK);
	}

	public java.lang.String getKey() {
		return _ticket.getKey();
	}

	public void setKey(java.lang.String key) {
		_ticket.setKey(key);
	}

	public java.util.Date getExpirationDate() {
		return _ticket.getExpirationDate();
	}

	public void setExpirationDate(java.util.Date expirationDate) {
		_ticket.setExpirationDate(expirationDate);
	}

	public com.liferay.portal.model.Ticket toEscapedModel() {
		return _ticket.toEscapedModel();
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
		return _ticket.clone();
	}

	public int compareTo(com.liferay.portal.model.Ticket ticket) {
		return _ticket.compareTo(ticket);
	}

	public int hashCode() {
		return _ticket.hashCode();
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

	private Ticket _ticket;
}