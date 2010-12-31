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
import java.util.List;

/**
 * <p>
 * This class is used by
 * {@link com.liferay.portlet.social.service.http.SocialEquityGroupSettingServiceSoap}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portlet.social.service.http.SocialEquityGroupSettingServiceSoap
 * @generated
 */
public class SocialEquityGroupSettingSoap implements Serializable {
	public static SocialEquityGroupSettingSoap toSoapModel(
		SocialEquityGroupSetting model) {
		SocialEquityGroupSettingSoap soapModel = new SocialEquityGroupSettingSoap();

		soapModel.setEquityGroupSettingId(model.getEquityGroupSettingId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setClassNameId(model.getClassNameId());
		soapModel.setType(model.getType());
		soapModel.setEnabled(model.getEnabled());

		return soapModel;
	}

	public static SocialEquityGroupSettingSoap[] toSoapModels(
		SocialEquityGroupSetting[] models) {
		SocialEquityGroupSettingSoap[] soapModels = new SocialEquityGroupSettingSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SocialEquityGroupSettingSoap[][] toSoapModels(
		SocialEquityGroupSetting[][] models) {
		SocialEquityGroupSettingSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SocialEquityGroupSettingSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SocialEquityGroupSettingSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SocialEquityGroupSettingSoap[] toSoapModels(
		List<SocialEquityGroupSetting> models) {
		List<SocialEquityGroupSettingSoap> soapModels = new ArrayList<SocialEquityGroupSettingSoap>(models.size());

		for (SocialEquityGroupSetting model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SocialEquityGroupSettingSoap[soapModels.size()]);
	}

	public SocialEquityGroupSettingSoap() {
	}

	public long getPrimaryKey() {
		return _equityGroupSettingId;
	}

	public void setPrimaryKey(long pk) {
		setEquityGroupSettingId(pk);
	}

	public long getEquityGroupSettingId() {
		return _equityGroupSettingId;
	}

	public void setEquityGroupSettingId(long equityGroupSettingId) {
		_equityGroupSettingId = equityGroupSettingId;
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

	public int getType() {
		return _type;
	}

	public void setType(int type) {
		_type = type;
	}

	public boolean getEnabled() {
		return _enabled;
	}

	public boolean isEnabled() {
		return _enabled;
	}

	public void setEnabled(boolean enabled) {
		_enabled = enabled;
	}

	private long _equityGroupSettingId;
	private long _groupId;
	private long _companyId;
	private long _classNameId;
	private int _type;
	private boolean _enabled;
}