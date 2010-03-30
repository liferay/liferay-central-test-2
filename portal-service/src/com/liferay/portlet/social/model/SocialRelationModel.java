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
 * <a href="SocialRelationModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the SocialRelation table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SocialRelation
 * @see       com.liferay.portlet.social.model.impl.SocialRelationImpl
 * @see       com.liferay.portlet.social.model.impl.SocialRelationModelImpl
 * @generated
 */
public interface SocialRelationModel extends BaseModel<SocialRelation> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	@AutoEscape
	public String getUuid();

	public void setUuid(String uuid);

	public long getRelationId();

	public void setRelationId(long relationId);

	public long getCompanyId();

	public void setCompanyId(long companyId);

	public long getCreateDate();

	public void setCreateDate(long createDate);

	public long getUserId1();

	public void setUserId1(long userId1);

	public long getUserId2();

	public void setUserId2(long userId2);

	public int getType();

	public void setType(int type);

	public SocialRelation toEscapedModel();

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

	public int compareTo(SocialRelation socialRelation);

	public int hashCode();

	public String toString();

	public String toXmlString();
}