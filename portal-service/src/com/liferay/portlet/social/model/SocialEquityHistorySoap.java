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

package com.liferay.portlet.social.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * This class is used by
 * {@link com.liferay.portlet.social.service.http.SocialEquityHistoryServiceSoap}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portlet.social.service.http.SocialEquityHistoryServiceSoap
 * @generated
 */
public class SocialEquityHistorySoap implements Serializable {
	public static SocialEquityHistorySoap toSoapModel(SocialEquityHistory model) {
		SocialEquityHistorySoap soapModel = new SocialEquityHistorySoap();

		soapModel.setEquityHistoryId(model.getEquityHistoryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setPersonalEquity(model.getPersonalEquity());

		return soapModel;
	}

	public static SocialEquityHistorySoap[] toSoapModels(
		SocialEquityHistory[] models) {
		SocialEquityHistorySoap[] soapModels = new SocialEquityHistorySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SocialEquityHistorySoap[][] toSoapModels(
		SocialEquityHistory[][] models) {
		SocialEquityHistorySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SocialEquityHistorySoap[models.length][models[0].length];
		}
		else {
			soapModels = new SocialEquityHistorySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SocialEquityHistorySoap[] toSoapModels(
		List<SocialEquityHistory> models) {
		List<SocialEquityHistorySoap> soapModels = new ArrayList<SocialEquityHistorySoap>(models.size());

		for (SocialEquityHistory model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SocialEquityHistorySoap[soapModels.size()]);
	}

	public SocialEquityHistorySoap() {
	}

	public long getPrimaryKey() {
		return _equityHistoryId;
	}

	public void setPrimaryKey(long pk) {
		setEquityHistoryId(pk);
	}

	public long getEquityHistoryId() {
		return _equityHistoryId;
	}

	public void setEquityHistoryId(long equityHistoryId) {
		_equityHistoryId = equityHistoryId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public int getPersonalEquity() {
		return _personalEquity;
	}

	public void setPersonalEquity(int personalEquity) {
		_personalEquity = personalEquity;
	}

	private long _equityHistoryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private Date _createDate;
	private int _personalEquity;
}