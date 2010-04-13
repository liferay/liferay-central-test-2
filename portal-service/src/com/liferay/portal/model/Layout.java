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
	public com.liferay.portal.model.Group getGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Group getScopeGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public boolean hasScopeGroup()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public boolean isPublicLayout();

	public boolean isTypeArticle();

	public boolean isTypeControlPanel();

	public boolean isTypeEmbedded();

	public boolean isTypeLinkToLayout();

	public boolean isTypePanel();

	public boolean isTypePortlet();

	public boolean isTypeURL();

	public long getAncestorPlid()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public long getAncestorLayoutId()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

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

	public com.liferay.portal.model.LayoutSet getLayoutSet()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public boolean isInheritLookAndFeel();

	public com.liferay.portal.model.Theme getTheme()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ColorScheme getColorScheme()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public boolean isInheritWapLookAndFeel();

	public com.liferay.portal.model.Theme getWapTheme()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.ColorScheme getWapColorScheme()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.lang.String getCssText()
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.lang.String getRegularURL(
		javax.servlet.http.HttpServletRequest request)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.lang.String getResetMaxStateURL(
		javax.servlet.http.HttpServletRequest request)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.lang.String getResetLayoutURL(
		javax.servlet.http.HttpServletRequest request)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public java.lang.String getTarget();

	public boolean isChildSelected(boolean selectable,
		com.liferay.portal.model.Layout layout)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public boolean isSelected(boolean selectable,
		com.liferay.portal.model.Layout layout, long ancestorPlid);
}