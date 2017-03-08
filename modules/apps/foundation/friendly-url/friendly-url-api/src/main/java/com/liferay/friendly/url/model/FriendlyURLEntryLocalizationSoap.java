/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.friendly.url.model;

import aQute.bnd.annotation.ProviderType;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services.
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class FriendlyURLEntryLocalizationSoap implements Serializable {
	public static FriendlyURLEntryLocalizationSoap toSoapModel(
		FriendlyURLEntryLocalization model) {
		FriendlyURLEntryLocalizationSoap soapModel = new FriendlyURLEntryLocalizationSoap();

		soapModel.setFriendlyURLEntryLocalizationId(model.getFriendlyURLEntryLocalizationId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setFriendlyURLEntryId(model.getFriendlyURLEntryId());
		soapModel.setUrlTitle(model.getUrlTitle());
		soapModel.setLanguageId(model.getLanguageId());

		return soapModel;
	}

	public static FriendlyURLEntryLocalizationSoap[] toSoapModels(
		FriendlyURLEntryLocalization[] models) {
		FriendlyURLEntryLocalizationSoap[] soapModels = new FriendlyURLEntryLocalizationSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static FriendlyURLEntryLocalizationSoap[][] toSoapModels(
		FriendlyURLEntryLocalization[][] models) {
		FriendlyURLEntryLocalizationSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new FriendlyURLEntryLocalizationSoap[models.length][models[0].length];
		}
		else {
			soapModels = new FriendlyURLEntryLocalizationSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static FriendlyURLEntryLocalizationSoap[] toSoapModels(
		List<FriendlyURLEntryLocalization> models) {
		List<FriendlyURLEntryLocalizationSoap> soapModels = new ArrayList<FriendlyURLEntryLocalizationSoap>(models.size());

		for (FriendlyURLEntryLocalization model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new FriendlyURLEntryLocalizationSoap[soapModels.size()]);
	}

	public FriendlyURLEntryLocalizationSoap() {
	}

	public long getPrimaryKey() {
		return _friendlyURLEntryLocalizationId;
	}

	public void setPrimaryKey(long pk) {
		setFriendlyURLEntryLocalizationId(pk);
	}

	public long getFriendlyURLEntryLocalizationId() {
		return _friendlyURLEntryLocalizationId;
	}

	public void setFriendlyURLEntryLocalizationId(
		long friendlyURLEntryLocalizationId) {
		_friendlyURLEntryLocalizationId = friendlyURLEntryLocalizationId;
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

	public long getFriendlyURLEntryId() {
		return _friendlyURLEntryId;
	}

	public void setFriendlyURLEntryId(long friendlyURLEntryId) {
		_friendlyURLEntryId = friendlyURLEntryId;
	}

	public String getUrlTitle() {
		return _urlTitle;
	}

	public void setUrlTitle(String urlTitle) {
		_urlTitle = urlTitle;
	}

	public String getLanguageId() {
		return _languageId;
	}

	public void setLanguageId(String languageId) {
		_languageId = languageId;
	}

	private long _friendlyURLEntryLocalizationId;
	private long _groupId;
	private long _companyId;
	private long _friendlyURLEntryId;
	private String _urlTitle;
	private String _languageId;
}