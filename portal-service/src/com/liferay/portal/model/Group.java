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


/**
 * <a href="Group.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the Group_ table in the
 * database.
 * </p>
 *
 * <p>
 * Customize {@link com.liferay.portal.model.impl.GroupImpl} and rerun the
 * ServiceBuilder to generate the new methods.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       GroupModel
 * @see       com.liferay.portal.model.impl.GroupImpl
 * @see       com.liferay.portal.model.impl.GroupModelImpl
 * @generated
 */
public interface Group extends GroupModel {
	public boolean isCommunity();

	public boolean isCompany();

	public boolean isControlPanel();

	public boolean isLayout();

	public boolean isLayoutPrototype();

	public boolean isLayoutSetPrototype();

	public boolean isOrganization();

	public boolean isUser();

	public boolean isUserGroup();

	public com.liferay.portal.model.Group getLiveGroup();

	public com.liferay.portal.model.Group getStagingGroup();

	public boolean hasStagingGroup();

	public boolean isStagingGroup();

	public java.lang.String getDescriptiveName()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.lang.String getTypeLabel();

	public java.lang.String getTypeSettings();

	public void setTypeSettings(java.lang.String typeSettings);

	public com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties();

	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties);

	public java.lang.String getTypeSettingsProperty(java.lang.String key);

	public java.lang.String getPathFriendlyURL(boolean privateLayout,
		com.liferay.portal.theme.ThemeDisplay themeDisplay);

	public long getDefaultPrivatePlid();

	public com.liferay.portal.model.LayoutSet getPrivateLayoutSet();

	public int getPrivateLayoutsPageCount();

	public boolean hasPrivateLayouts();

	public long getDefaultPublicPlid();

	public com.liferay.portal.model.LayoutSet getPublicLayoutSet();

	public int getPublicLayoutsPageCount();

	public boolean hasPublicLayouts();

	public boolean isWorkflowEnabled();

	public int getWorkflowStages();

	public java.lang.String getWorkflowRoleNames();
}