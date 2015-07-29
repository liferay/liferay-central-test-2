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

package com.liferay.taglib.ui;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortletCategoryKeys;
import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class LayoutCommonTag extends IncludeTag {

	@Override
	protected void cleanUp() {
		_includeStaticPortlets = false;
		_includeWebServerDisplayNode = false;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected boolean isCleanUpSetAttributes() {
		return _CLEAN_UP_SET_ATTRIBUTES;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (!themeDisplay.isFacebook() && !themeDisplay.isStateExclusive() &&
			!themeDisplay.isStatePopUp() && !themeDisplay.isWidget() &&
			(!_DOCKBAR_ADMINISTRATIVE_LINKS_SHOW_IN_POP_UP ||
			 !Validator.equals(
				 themeDisplay.getControlPanelCategory(),
				 PortletCategoryKeys.MY))) {

			_includeStaticPortlets = true;
		}

		request.setAttribute(
			"liferay-ui:layout_common:includeStaticPortlets",
			_includeStaticPortlets);

		if (_WEB_SERVER_DISPLAY_NODE && !themeDisplay.isStatePopUp()) {
			_includeWebServerDisplayNode = true;
		}

		request.setAttribute(
			"liferay-ui:layout_common:includeWebServerDisplayNode",
			_includeWebServerDisplayNode);
	}

	private static final boolean _CLEAN_UP_SET_ATTRIBUTES = true;

	private static final boolean _DOCKBAR_ADMINISTRATIVE_LINKS_SHOW_IN_POP_UP =
		GetterUtil.getBoolean(
			PropsUtil.get(
				PropsKeys.DOCKBAR_ADMINISTRATIVE_LINKS_SHOW_IN_POP_UP));

	private static final String _PAGE =
		"/html/taglib/ui/layout_common/page.jsp";

	private static final boolean _WEB_SERVER_DISPLAY_NODE =
		GetterUtil.getBoolean(PropsUtil.get(PropsKeys.WEB_SERVER_DISPLAY_NODE));

	private boolean _includeStaticPortlets = false;
	private boolean _includeWebServerDisplayNode = false;

}