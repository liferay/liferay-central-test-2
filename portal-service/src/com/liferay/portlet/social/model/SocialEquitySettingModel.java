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

import com.liferay.portal.kernel.annotation.AutoEscape;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

/**
 * <a href="SocialEquitySettingModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the SocialEquitySetting table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialEquitySetting
 * @see       com.liferay.portlet.social.model.impl.SocialEquitySettingImpl
 * @see       com.liferay.portlet.social.model.impl.SocialEquitySettingModelImpl
 * @generated
 */
public interface SocialEquitySettingModel extends BaseModel<SocialEquitySetting> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getEquitySettingId();

	public void setEquitySettingId(long equitySettingId);

	public long getGroupId();

	public void setGroupId(long groupId);

	public long getCompanyId();

	public void setCompanyId(long companyId);

	public String getClassName();

	public long getClassNameId();

	public void setClassNameId(long classNameId);

	@AutoEscape
	public String getActionId();

	public void setActionId(String actionId);

	public int getType();

	public void setType(int type);

	public int getValue();

	public void setValue(int value);

	public int getValidity();

	public void setValidity(int validity);

	public boolean getActive();

	public boolean isActive();

	public void setActive(boolean active);

	public SocialEquitySetting toEscapedModel();

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

	public int compareTo(SocialEquitySetting socialEquitySetting);

	public int hashCode();

	public String toString();

	public String toXmlString();
}