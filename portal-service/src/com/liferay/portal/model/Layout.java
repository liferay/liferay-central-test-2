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
 * <a href="Layout.java.html"><b><i>View Source</i></b></a>
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
 * <p>
 * Customize {@link com.liferay.portal.model.impl.LayoutImpl} and rerun the
 * ServiceBuilder to generate the new methods.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutModel
 * @see       com.liferay.portal.model.impl.LayoutImpl
 * @see       com.liferay.portal.model.impl.LayoutModelImpl
 * @generated
 */
public interface Layout extends LayoutModel {
	public com.liferay.portal.model.Group getGroup();

	public com.liferay.portal.model.Group getScopeGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public boolean hasScopeGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public boolean isPublicLayout();

	public long getAncestorPlid();

	public long getAncestorLayoutId();

	public java.util.List<com.liferay.portal.model.Layout> getAncestors()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public boolean hasAncestor(long layoutId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public boolean isFirstParent();

	public boolean isFirstChild();

	public boolean isRootLayout();

	public java.util.List<com.liferay.portal.model.Layout> getChildren()
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean hasChildren()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Layout> getAllChildren()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<com.liferay.portal.model.Layout> getChildren(
		com.liferay.portal.security.permission.PermissionChecker permissionChecker)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.lang.String getName(java.util.Locale locale);

	public java.lang.String getName(java.lang.String localeLanguageId);

	public java.lang.String getName(java.util.Locale locale, boolean useDefault);

	public java.lang.String getName(java.lang.String localeLanguageId,
		boolean useDefault);

	public void setName(java.lang.String name, java.util.Locale locale);

	public java.lang.String getTitle(java.util.Locale locale);

	public java.lang.String getTitle(java.lang.String localeLanguageId);

	public java.lang.String getTitle(java.util.Locale locale, boolean useDefault);

	public java.lang.String getTitle(java.lang.String localeLanguageId,
		boolean useDefault);

	public java.lang.String getHTMLTitle(java.util.Locale locale);

	public java.lang.String getHTMLTitle(java.lang.String localeLanguageId);

	public void setTitle(java.lang.String title, java.util.Locale locale);

	public com.liferay.portal.model.LayoutType getLayoutType();

	public java.lang.String getTypeSettings();

	public void setTypeSettings(java.lang.String typeSettings);

	public com.liferay.portal.kernel.util.UnicodeProperties getTypeSettingsProperties();

	public void setTypeSettingsProperties(
		com.liferay.portal.kernel.util.UnicodeProperties typeSettingsProperties);

	public com.liferay.portal.model.LayoutSet getLayoutSet();

	public boolean isInheritLookAndFeel();

	public com.liferay.portal.model.Theme getTheme()
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ColorScheme getColorScheme()
		throws com.liferay.portal.kernel.exception.SystemException;

	public boolean isInheritWapLookAndFeel();

	public com.liferay.portal.model.Theme getWapTheme()
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ColorScheme getWapColorScheme()
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.lang.String getCssText();

	public java.lang.String getRegularURL(
		javax.servlet.http.HttpServletRequest request)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.lang.String getResetMaxStateURL(
		javax.servlet.http.HttpServletRequest request)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.lang.String getResetLayoutURL(
		javax.servlet.http.HttpServletRequest request)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.lang.String getTarget();

	public boolean isChildSelected(boolean selectable,
		com.liferay.portal.model.Layout layout);

	public boolean isSelected(boolean selectable,
		com.liferay.portal.model.Layout layout, long ancestorPlid);
}