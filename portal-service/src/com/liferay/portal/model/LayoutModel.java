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

package com.liferay.portal.model;

import com.liferay.portal.kernel.annotation.AutoEscape;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

/**
 * <a href="LayoutModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the Layout table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Layout
 * @see       com.liferay.portal.model.impl.LayoutImpl
 * @see       com.liferay.portal.model.impl.LayoutModelImpl
 * @generated
 */
public interface LayoutModel extends BaseModel<Layout> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getPlid();

	public void setPlid(long plid);

	public long getGroupId();

	public void setGroupId(long groupId);

	public long getCompanyId();

	public void setCompanyId(long companyId);

	public boolean getPrivateLayout();

	public boolean isPrivateLayout();

	public void setPrivateLayout(boolean privateLayout);

	public long getLayoutId();

	public void setLayoutId(long layoutId);

	public long getParentLayoutId();

	public void setParentLayoutId(long parentLayoutId);

	@AutoEscape
	public String getName();

	public void setName(String name);

	@AutoEscape
	public String getTitle();

	public void setTitle(String title);

	@AutoEscape
	public String getDescription();

	public void setDescription(String description);

	@AutoEscape
	public String getType();

	public void setType(String type);

	@AutoEscape
	public String getTypeSettings();

	public void setTypeSettings(String typeSettings);

	public boolean getHidden();

	public boolean isHidden();

	public void setHidden(boolean hidden);

	@AutoEscape
	public String getFriendlyURL();

	public void setFriendlyURL(String friendlyURL);

	public boolean getIconImage();

	public boolean isIconImage();

	public void setIconImage(boolean iconImage);

	public long getIconImageId();

	public void setIconImageId(long iconImageId);

	@AutoEscape
	public String getThemeId();

	public void setThemeId(String themeId);

	@AutoEscape
	public String getColorSchemeId();

	public void setColorSchemeId(String colorSchemeId);

	@AutoEscape
	public String getWapThemeId();

	public void setWapThemeId(String wapThemeId);

	@AutoEscape
	public String getWapColorSchemeId();

	public void setWapColorSchemeId(String wapColorSchemeId);

	@AutoEscape
	public String getCss();

	public void setCss(String css);

	public int getPriority();

	public void setPriority(int priority);

	public long getLayoutPrototypeId();

	public void setLayoutPrototypeId(long layoutPrototypeId);

	public long getDlFolderId();

	public void setDlFolderId(long dlFolderId);

	public Layout toEscapedModel();

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

	public int compareTo(Layout layout);

	public int hashCode();

	public String toString();

	public String toXmlString();
}