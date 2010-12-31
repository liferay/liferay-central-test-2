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
 * {@link com.liferay.portlet.social.service.http.SocialEquityAssetEntryServiceSoap}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portlet.social.service.http.SocialEquityAssetEntryServiceSoap
 * @generated
 */
public class SocialEquityAssetEntrySoap implements Serializable {
	public static SocialEquityAssetEntrySoap toSoapModel(
		SocialEquityAssetEntry model) {
		SocialEquityAssetEntrySoap soapModel = new SocialEquityAssetEntrySoap();

		soapModel.setEquityAssetEntryId(model.getEquityAssetEntryId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setAssetEntryId(model.getAssetEntryId());
		soapModel.setInformationK(model.getInformationK());
		soapModel.setInformationB(model.getInformationB());

		return soapModel;
	}

	public static SocialEquityAssetEntrySoap[] toSoapModels(
		SocialEquityAssetEntry[] models) {
		SocialEquityAssetEntrySoap[] soapModels = new SocialEquityAssetEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SocialEquityAssetEntrySoap[][] toSoapModels(
		SocialEquityAssetEntry[][] models) {
		SocialEquityAssetEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SocialEquityAssetEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new SocialEquityAssetEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SocialEquityAssetEntrySoap[] toSoapModels(
		List<SocialEquityAssetEntry> models) {
		List<SocialEquityAssetEntrySoap> soapModels = new ArrayList<SocialEquityAssetEntrySoap>(models.size());

		for (SocialEquityAssetEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SocialEquityAssetEntrySoap[soapModels.size()]);
	}

	public SocialEquityAssetEntrySoap() {
	}

	public long getPrimaryKey() {
		return _equityAssetEntryId;
	}

	public void setPrimaryKey(long pk) {
		setEquityAssetEntryId(pk);
	}

	public long getEquityAssetEntryId() {
		return _equityAssetEntryId;
	}

	public void setEquityAssetEntryId(long equityAssetEntryId) {
		_equityAssetEntryId = equityAssetEntryId;
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

	public long getAssetEntryId() {
		return _assetEntryId;
	}

	public void setAssetEntryId(long assetEntryId) {
		_assetEntryId = assetEntryId;
	}

	public double getInformationK() {
		return _informationK;
	}

	public void setInformationK(double informationK) {
		_informationK = informationK;
	}

	public double getInformationB() {
		return _informationB;
	}

	public void setInformationB(double informationB) {
		_informationB = informationB;
	}

	private long _equityAssetEntryId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private long _assetEntryId;
	private double _informationK;
	private double _informationB;
}