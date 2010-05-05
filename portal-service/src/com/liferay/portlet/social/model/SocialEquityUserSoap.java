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
 * <a href="SocialEquityUserSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
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
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setContributionEquity(model.getContributionEquity());
		soapModel.setParticipationK(model.getParticipationK());
		soapModel.setParticipationB(model.getParticipationB());
		soapModel.setParticipationEquity(model.getParticipationEquity());
		soapModel.setPersonalEquity(model.getPersonalEquity());

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

	public double getContributionEquity() {
		return _contributionEquity;
	}

	public void setContributionEquity(double contributionEquity) {
		_contributionEquity = contributionEquity;
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

	public double getParticipationEquity() {
		return _participationEquity;
	}

	public void setParticipationEquity(double participationEquity) {
		_participationEquity = participationEquity;
	}

	public double getPersonalEquity() {
		return _personalEquity;
	}

	public void setPersonalEquity(double personalEquity) {
		_personalEquity = personalEquity;
	}

	private long _equityUserId;
	private long _companyId;
	private long _userId;
	private double _contributionEquity;
	private double _participationK;
	private double _participationB;
	private double _participationEquity;
	private double _personalEquity;
}