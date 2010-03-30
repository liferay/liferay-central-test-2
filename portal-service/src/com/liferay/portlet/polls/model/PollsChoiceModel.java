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

package com.liferay.portlet.polls.model;

import com.liferay.portal.kernel.annotation.AutoEscape;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Locale;
import java.util.Map;

/**
 * <a href="PollsChoiceModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the PollsChoice table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PollsChoice
 * @see       com.liferay.portlet.polls.model.impl.PollsChoiceImpl
 * @see       com.liferay.portlet.polls.model.impl.PollsChoiceModelImpl
 * @generated
 */
public interface PollsChoiceModel extends BaseModel<PollsChoice> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	@AutoEscape
	public String getUuid();

	public void setUuid(String uuid);

	public long getChoiceId();

	public void setChoiceId(long choiceId);

	public long getQuestionId();

	public void setQuestionId(long questionId);

	@AutoEscape
	public String getName();

	public void setName(String name);

	public String getDescription();

	public String getDescription(Locale locale);

	public String getDescription(Locale locale, boolean useDefault);

	public String getDescription(String languageId);

	public String getDescription(String languageId, boolean useDefault);

	public Map<Locale, String> getDescriptionMap();

	public void setDescription(String description);

	public void setDescription(Locale locale, String description);

	public void setDescriptionMap(Map<Locale, String> descriptionMap);

	public PollsChoice toEscapedModel();

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

	public int compareTo(PollsChoice pollsChoice);

	public int hashCode();

	public String toString();

	public String toXmlString();
}