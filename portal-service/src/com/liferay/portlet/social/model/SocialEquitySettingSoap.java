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

package com.liferay.portlet.social.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="SocialEquitySettingSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * {@link com.liferay.portlet.social.service.http.SocialEquitySettingServiceSoap}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portlet.social.service.http.SocialEquitySettingServiceSoap
 * @generated
 */
public class SocialEquitySettingSoap implements Serializable {
	public static SocialEquitySettingSoap toSoapModel(SocialEquitySetting model) {
		SocialEquitySettingSoap soapModel = new SocialEquitySettingSoap();

		soapModel.setEquitySettingId(model.getEquitySettingId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setActionId(model.getActionId());
		soapModel.setType(model.getType());
		soapModel.setValue(model.getValue());
		soapModel.setValidity(model.getValidity());

		return soapModel;
	}

	public static SocialEquitySettingSoap[] toSoapModels(
		SocialEquitySetting[] models) {
		SocialEquitySettingSoap[] soapModels = new SocialEquitySettingSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SocialEquitySettingSoap[][] toSoapModels(
		SocialEquitySetting[][] models) {
		SocialEquitySettingSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SocialEquitySettingSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SocialEquitySettingSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SocialEquitySettingSoap[] toSoapModels(
		List<SocialEquitySetting> models) {
		List<SocialEquitySettingSoap> soapModels = new ArrayList<SocialEquitySettingSoap>(models.size());

		for (SocialEquitySetting model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SocialEquitySettingSoap[soapModels.size()]);
	}

	public SocialEquitySettingSoap() {
	}

	public long getPrimaryKey() {
		return _equitySettingId;
	}

	public void setPrimaryKey(long pk) {
		setEquitySettingId(pk);
	}

	public long getEquitySettingId() {
		return _equitySettingId;
	}

	public void setEquitySettingId(long equitySettingId) {
		_equitySettingId = equitySettingId;
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

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public String getActionId() {
		return _actionId;
	}

	public void setActionId(String actionId) {
		_actionId = actionId;
	}

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	public int getValue() {
		return _value;
	}

	public void setValue(int value) {
		_value = value;
	}

	public int getValidity() {
		return _validity;
	}

	public void setValidity(int validity) {
		_validity = validity;
	}

	private long _equitySettingId;
	private long _groupId;
	private long _companyId;
	private long _classNameId;
	private String _actionId;
	private int _type;
	private int _value;
	private int _validity;
}