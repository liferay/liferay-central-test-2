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
 * {@link com.liferay.portlet.social.service.http.SocialEquityUserServiceSoap}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portlet.social.service.http.SocialEquityUserServiceSoap
 * @generated
 */
public class SocialEquityUserSoap implements Serializable {
	public static SocialEquityUserSoap toSoapModel(SocialEquityUser model) {
		SocialEquityUserSoap soapModel = new SocialEquityUserSoap();

		soapModel.setEquityUserId(model.getEquityUserId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setContributionK(model.getContributionK());
		soapModel.setContributionB(model.getContributionB());
		soapModel.setParticipationK(model.getParticipationK());
		soapModel.setParticipationB(model.getParticipationB());
		soapModel.setRank(model.getRank());

		return soapModel;
	}

	public static SocialEquityUserSoap[] toSoapModels(SocialEquityUser[] models) {
		SocialEquityUserSoap[] soapModels = new SocialEquityUserSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static SocialEquityUserSoap[][] toSoapModels(
		SocialEquityUser[][] models) {
		SocialEquityUserSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new SocialEquityUserSoap[models.length][models[0].length];
		}
		else {
			soapModels = new SocialEquityUserSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static SocialEquityUserSoap[] toSoapModels(
		List<SocialEquityUser> models) {
		List<SocialEquityUserSoap> soapModels = new ArrayList<SocialEquityUserSoap>(models.size());

		for (SocialEquityUser model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new SocialEquityUserSoap[soapModels.size()]);
	}

	public SocialEquityUserSoap() {
	}

	public long getPrimaryKey() {
		return _equityUserId;
	}

	public void setPrimaryKey(long pk) {
		setEquityUserId(pk);
	}

	public long getEquityUserId() {
		return _equityUserId;
	}

	public void setEquityUserId(long equityUserId) {
		_equityUserId = equityUserId;
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

	public double getContributionK() {
		return _contributionK;
	}

	public void setContributionK(double contributionK) {
		_contributionK = contributionK;
	}

	public double getContributionB() {
		return _contributionB;
	}

	public void setContributionB(double contributionB) {
		_contributionB = contributionB;
	}

	public double getParticipationK() {
		return _participationK;
	}

	public void setParticipationK(double participationK) {
		_participationK = participationK;
	}

	public double getParticipationB() {
		return _participationB;
	}

	public void setParticipationB(double participationB) {
		_participationB = participationB;
	}

	public int getRank() {
		return _rank;
	}

	public void setRank(int rank) {
		_rank = rank;
	}

	private long _equityUserId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private double _contributionK;
	private double _contributionB;
	private double _participationK;
	private double _participationB;
	private int _rank;
}