/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.iframe.web.display.context;

import com.liferay.iframe.web.configuration.IFrameConfiguration;
import com.liferay.iframe.web.configuration.IFramePortletInstanceConfiguration;
import com.liferay.portal.kernel.settings.SettingsException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Juergen Kappler
 */
public class IFrameDisplayContext {

	public IFrameDisplayContext(
			IFrameConfiguration iFrameConfiguration, HttpServletRequest request)
		throws SettingsException {

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletDisplay portletDisplay = themeDisplay.getPortletDisplay();

		_iFramePortletInstanceConfiguration =
			portletDisplay.getPortletInstanceConfiguration(
				IFramePortletInstanceConfiguration.class);

		_iFrameConfiguration = iFrameConfiguration;
	}

	public String getAlt() {
		if (_alt != null) {
			return _alt;
		}

		_alt = _iFramePortletInstanceConfiguration.alt();

		return _alt;
	}

	public String getAuthType() {
		if (_authType != null) {
			return _authType;
		}

		_authType = _iFramePortletInstanceConfiguration.authType();

		if (Validator.isNull(_authType)) {
			_authType = _iFrameConfiguration.authType();
		}

		return _authType;
	}

	public String getBasicPassword() {
		if (_basicPassword != null) {
			return _basicPassword;
		}

		_basicPassword = _iFramePortletInstanceConfiguration.basicPassword();

		return _basicPassword;
	}

	public String getBasicUserName() {
		if (_basicUserName != null) {
			return _basicUserName;
		}

		_basicUserName = _iFramePortletInstanceConfiguration.basicUserName();

		return _basicUserName;
	}

	public String getBorder() {
		if (_border != null) {
			return _border;
		}

		_border = _iFramePortletInstanceConfiguration.border();

		return _border;
	}

	public String getBordercolor() {
		if (_bordercolor != null) {
			return _bordercolor;
		}

		_bordercolor = _iFramePortletInstanceConfiguration.bordercolor();

		return _bordercolor;
	}

	public String getFormMethod() {
		if (_formMethod != null) {
			return _formMethod;
		}

		_formMethod = _iFramePortletInstanceConfiguration.formMethod();

		if (Validator.isNull(_formMethod)) {
			_formMethod = _iFrameConfiguration.formMethod();
		}

		return _formMethod;
	}

	public String getFormPassword() {
		if (_formPassword != null) {
			return _formPassword;
		}

		_formPassword = _iFramePortletInstanceConfiguration.formPassword();

		return _formPassword;
	}

	public String getFormUserName() {
		if (_formUserName != null) {
			return _formUserName;
		}

		_formUserName = _iFramePortletInstanceConfiguration.formUserName();

		return _formUserName;
	}

	public String getFrameborder() {
		if (_frameborder != null) {
			return _frameborder;
		}

		_frameborder = _iFramePortletInstanceConfiguration.frameborder();

		return _frameborder;
	}

	public String getHeightMaximized() {
		if (_heightMaximized != null) {
			return _heightMaximized;
		}

		_heightMaximized =
			_iFramePortletInstanceConfiguration.heightMaximized();

		return _heightMaximized;
	}

	public String getHeightNormal() {
		if (_heightNormal != null) {
			return _heightNormal;
		}

		_heightNormal = _iFramePortletInstanceConfiguration.heightNormal();

		return _heightNormal;
	}

	public String getHiddenVariables() {
		if (_hiddenVariables != null) {
			return _hiddenVariables;
		}

		_hiddenVariables =
			_iFramePortletInstanceConfiguration.hiddenVariables();

		if (Validator.isNull(_hiddenVariables)) {
			_hiddenVariables = StringUtil.merge(
				_iFrameConfiguration.hiddenVariables(), StringPool.SEMICOLON);
		}

		return _hiddenVariables;
	}

	public String getHspace() {
		if (_hspace != null) {
			return _hspace;
		}

		_hspace = _iFramePortletInstanceConfiguration.hspace();

		return _hspace;
	}

	public String getLongdesc() {
		if (_longdesc != null) {
			return _longdesc;
		}

		_longdesc = _iFramePortletInstanceConfiguration.longdesc();

		return _longdesc;
	}

	public String getPasswordField() {
		if (_passwordField != null) {
			return _passwordField;
		}

		_passwordField = _iFramePortletInstanceConfiguration.passwordField();

		return _passwordField;
	}

	public String getScrolling() {
		if (_scrolling != null) {
			return _scrolling;
		}

		_scrolling = _iFramePortletInstanceConfiguration.scrolling();

		return _scrolling;
	}

	public String getSrc() {
		if (_src != null) {
			return _src;
		}

		_src = _iFramePortletInstanceConfiguration.src();

		return _src;
	}

	public String getTitle() {
		if (_title != null) {
			return _title;
		}

		_title = _iFramePortletInstanceConfiguration.title();

		return _title;
	}

	public String getUserNameField() {
		if (_userNameField != null) {
			return _userNameField;
		}

		_userNameField = _iFramePortletInstanceConfiguration.userNameField();

		return _userNameField;
	}

	public String getVspace() {
		if (_vspace != null) {
			return _vspace;
		}

		_vspace = _iFramePortletInstanceConfiguration.vspace();

		return _vspace;
	}

	public String getWidth() {
		if (_width != null) {
			return _width;
		}

		_width = _iFramePortletInstanceConfiguration.width();

		return _width;
	}

	public boolean isAuth() {
		if (_auth != null) {
			return _auth;
		}

		_auth = _iFramePortletInstanceConfiguration.auth();

		return _auth;
	}

	public boolean isRelative() {
		if (_relative != null) {
			return _relative;
		}

		_relative = _iFramePortletInstanceConfiguration.relative();

		return _relative;
	}

	public boolean isResizeAutomatically() {
		if (_resizeAutomatically != null) {
			return _resizeAutomatically;
		}

		_resizeAutomatically =
			_iFramePortletInstanceConfiguration.resizeAutomatically();

		return _resizeAutomatically;
	}

	private String _alt;
	private Boolean _auth;
	private String _authType;
	private String _basicPassword;
	private String _basicUserName;
	private String _border;
	private String _bordercolor;
	private String _formMethod;
	private String _formPassword;
	private String _formUserName;
	private String _frameborder;
	private String _heightMaximized;
	private String _heightNormal;
	private String _hiddenVariables;
	private String _hspace;
	private final IFrameConfiguration _iFrameConfiguration;
	private final IFramePortletInstanceConfiguration
		_iFramePortletInstanceConfiguration;
	private String _longdesc;
	private String _passwordField;
	private Boolean _relative;
	private Boolean _resizeAutomatically;
	private String _scrolling;
	private String _src;
	private String _title;
	private String _userNameField;
	private String _vspace;
	private String _width;

}