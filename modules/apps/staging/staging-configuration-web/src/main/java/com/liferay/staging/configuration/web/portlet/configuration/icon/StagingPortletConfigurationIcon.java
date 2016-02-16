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

package com.liferay.staging.configuration.web.portlet.configuration.icon;

import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.portlet.configuration.icon.BasePortletConfigurationIcon;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;

import javax.portlet.PortletRequest;

/**
 * @author Eudaldo Alonso
 */
public class StagingPortletConfigurationIcon
	extends BasePortletConfigurationIcon {

	public StagingPortletConfigurationIcon(PortletRequest portletRequest) {
		super(portletRequest);
	}

	@Override
	public String getCssClass() {
		return "portlet-export-import portlet-export-import-icon";
	}

	@Override
	public String getMessage() {
		return "staging";
	}

	@Override
	public String getMethod() {
		return "get";
	}

	@Override
	public String getOnClick() {
		StringBundler sb = new StringBundler(11);

		sb.append("Liferay.Portlet.openWindow({namespace: '");
		sb.append(portletDisplay.getNamespace());
		sb.append("', portlet: '#p_p_id_");
		sb.append(portletDisplay.getId());
		sb.append("_', portletId: '");
		sb.append(portletDisplay.getId());
		sb.append("', title: '");
		sb.append(LanguageUtil.get(themeDisplay.getLocale(), "staging"));
		sb.append("', uri: '");
		sb.append(HtmlUtil.escapeJS(portletDisplay.getURLStaging()));
		sb.append("'}); return false;");

		return sb.toString();
	}

	@Override
	public String getURL() {
		return portletDisplay.getURLStaging();
	}

	@Override
	public boolean isShow(PortletRequest portletRequest) {
		return portletDisplay.isShowStagingIcon();
	}

	@Override
	public boolean isToolTip() {
		return false;
	}

}