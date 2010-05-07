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

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

/**
 * <a href="SocialEquityUserModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the SocialEquityUser table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquityUser
 * @see       com.liferay.portlet.social.model.impl.SocialEquityUserImpl
 * @see       com.liferay.portlet.social.model.impl.SocialEquityUserModelImpl
 * @generated
 */
public interface SocialEquityUserModel extends BaseModel<SocialEquityUser> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getEquityUserId();

	public void setEquityUserId(long equityUserId);

	public String getEquityUserUuid() throws SystemException;

	public void setEquityUserUuid(String equityUserUuid);

	public long getGroupId();

	public void setGroupId(long groupId);

	public long getCompanyId();

	public void setCompanyId(long companyId);

	public long getUserId();

	public void setUserId(long userId);

	public String getUserUuid() throws SystemException;

	public void setUserUuid(String userUuid);

	public double getContributionEquity();

	public void setContributionEquity(double contributionEquity);

	public double getParticipationK();

	public void setParticipationK(double participationK);

	public double getParticipationB();

	public void setParticipationB(double participationB);

	public double getParticipationEquity();

	public void setParticipationEquity(double participationEquity);

	public double getPersonalEquity();

	public void setPersonalEquity(double personalEquity);

	public SocialEquityUser toEscapedModel();

	public boolean isNew();

	public boolean setNew(boolean n);

	public boolean isCachedModel();

	public void setCachedModel(boolean cachedModel);

	public boolean isEscapedModel();

	public void setEscapedModel(boolean escapedModel);

	public Serializable getPrimaryKeyObj();

	public ExpandoBridge getExpandoBridge();

	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	public Object clone();

	public int compareTo(SocialEquityUser socialEquityUser);

	public int hashCode();

	public String toString();

	public String toXmlString();
}