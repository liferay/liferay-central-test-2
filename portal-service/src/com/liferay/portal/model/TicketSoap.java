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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="TicketSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * {@link com.liferay.portal.service.http.TicketServiceSoap}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portal.service.http.TicketServiceSoap
 * @generated
 */
public class TicketSoap implements Serializable {
	public static TicketSoap toSoapModel(Ticket model) {
		TicketSoap soapModel = new TicketSoap();

		soapModel.setTicketId(model.getTicketId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setExpirationDate(model.getExpirationDate());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setClassPK(model.getClassPK());
		soapModel.setKey(model.getKey());

		return soapModel;
	}

	public static TicketSoap[] toSoapModels(Ticket[] models) {
		TicketSoap[] soapModels = new TicketSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static TicketSoap[][] toSoapModels(Ticket[][] models) {
		TicketSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new TicketSoap[models.length][models[0].length];
		}
		else {
			soapModels = new TicketSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static TicketSoap[] toSoapModels(List<Ticket> models) {
		List<TicketSoap> soapModels = new ArrayList<TicketSoap>(models.size());

		for (Ticket model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new TicketSoap[soapModels.size()]);
	}

	public TicketSoap() {
	}

	public long getPrimaryKey() {
		return _ticketId;
	}

	public void setPrimaryKey(long pk) {
		setTicketId(pk);
	}

	public long getTicketId() {
		return _ticketId;
	}

	public void setTicketId(long ticketId) {
		_ticketId = ticketId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public long getExpirationDate() {
		return _expirationDate;
	}

	public void setExpirationDate(long expirationDate) {
		_expirationDate = expirationDate;
	}

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public String getKey() {
		return _key;
	}

	public void setKey(String key) {
		_key = key;
	}

	private long _ticketId;
	private long _companyId;
	private Date _createDate;
	private long _expirationDate;
	private long _classNameId;
	private long _classPK;
	private String _key;
}