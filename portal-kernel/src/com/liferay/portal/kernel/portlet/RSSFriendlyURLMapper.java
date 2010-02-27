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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import java.util.Map;

import javax.portlet.PortletMode;

/**
 * <a href="RSSFriendlyURLMapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public abstract class RSSFriendlyURLMapper extends BaseFriendlyURLMapper {

	public String buildPath(LiferayPortletURL portletURL) {
		String friendlyURLPath = null;

		boolean rss = GetterUtil.getBoolean(portletURL.getParameter("rss"));

		if (rss) {
			friendlyURLPath = StringPool.SLASH + getMapping() + "/rss";

			portletURL.addParameterIncludedInPath("rss");
		}

		if (Validator.isNotNull(friendlyURLPath)) {
			portletURL.addParameterIncludedInPath("p_p_id");
		}

		return friendlyURLPath;
	}

	public void populateParams(
		String friendlyURLPath, Map<String, String[]> params) {

		int x = friendlyURLPath.indexOf(StringPool.SLASH, 1);

		if (x == -1) {
			return;
		}

		String rss = friendlyURLPath.substring(x + 1);

		if (!rss.equals("rss")) {
			return;
		}

		addParam(params, "p_p_id", getPortletId());
		addParam(params, "p_p_lifecycle", "0");
		addParam(params, "p_p_state", LiferayWindowState.EXCLUSIVE);
		addParam(params, "p_p_mode", PortletMode.VIEW);
	}

}