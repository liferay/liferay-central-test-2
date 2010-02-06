/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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

	public java.lang.String getDescriptiveName();

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