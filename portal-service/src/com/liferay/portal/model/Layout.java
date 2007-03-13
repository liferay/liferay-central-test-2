/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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
 * <a href="Layout.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public interface Layout extends LayoutModel {
	public void setOwnerId(java.lang.String ownerId);

	public java.lang.String getPlid();

	public com.liferay.portal.model.Group getGroup();

	public long getGroupId();

	public boolean isPrivateLayout();

	public boolean isShared();

	public java.lang.String getAncestorLayoutId();

	public boolean hasAncestor(java.lang.String layoutId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public boolean isFirstParent();

	public boolean isFirstChild();

	public java.util.List getChildren()
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.util.List getChildren(
		com.liferay.portal.kernel.security.permission.PermissionChecker permissionChecker)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.lang.String getName(java.util.Locale locale);

	public java.lang.String getName(java.lang.String localeLanguageId);

	public void setName(java.lang.String name, java.util.Locale locale);

	public java.lang.String getTitle(java.util.Locale locale);

	public java.lang.String getTitle(java.lang.String localeLanguageId);

	public java.lang.String getHTMLTitle(java.util.Locale locale);

	public java.lang.String getHTMLTitle(java.lang.String localeLanguageId);

	public void setTitle(java.lang.String title, java.util.Locale locale);

	public com.liferay.portal.model.LayoutType getLayoutType();

	public java.lang.String getTypeSettings();

	public void setTypeSettings(java.lang.String typeSettings);

	public java.util.Properties getTypeSettingsProperties();

	public void setTypeSettingsProperties(
		java.util.Properties typeSettingsProperties);

	public java.lang.String getDefaultFriendlyURL();

	public java.lang.String getIconImageId();

	public com.liferay.portal.model.LayoutSet getLayoutSet();

	public boolean isInheritLookAndFeel();

	public com.liferay.portal.model.Theme getTheme()
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public com.liferay.portal.model.ColorScheme getColorScheme()
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.lang.String getRegularURL(
		javax.servlet.http.HttpServletRequest req)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.lang.String getResetMaxStateURL(
		javax.servlet.http.HttpServletRequest req)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.lang.String getResetLayoutURL(
		javax.servlet.http.HttpServletRequest req)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException;

	public java.lang.String getTarget();

	public boolean isSelected(boolean selectable,
		com.liferay.portal.model.Layout layout,
		java.lang.String ancestorLayoutId);
}