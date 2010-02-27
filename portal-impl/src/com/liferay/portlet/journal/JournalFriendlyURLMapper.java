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

package com.liferay.portlet.journal;

import com.liferay.portal.kernel.portlet.BaseFriendlyURLMapper;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortletKeys;

import java.util.Map;

import javax.portlet.PortletRequest;
import javax.portlet.ResourceURL;

/**
 * <a href="JournalFriendlyURLMapper.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class JournalFriendlyURLMapper extends BaseFriendlyURLMapper {

	public String buildPath(LiferayPortletURL portletURL) {
		String friendlyURLPath = null;

		String strutsAction = GetterUtil.getString(
			portletURL.getParameter("struts_action"));

		if (strutsAction.equals("/journal/rss") &&
			portletURL.getLifecycle().equals(PortletRequest.RESOURCE_PHASE)) {

			String groupId = portletURL.getParameter("groupId");
			String feedId = portletURL.getParameter("feedId");

			if (Validator.isNotNull(groupId) && Validator.isNotNull(feedId)) {
				friendlyURLPath = "/journal/rss/" + groupId + "/" + feedId;

				portletURL.addParameterIncludedInPath("groupId");
				portletURL.addParameterIncludedInPath("feedId");
			}
		}

		if (Validator.isNotNull(friendlyURLPath)) {
			portletURL.addParameterIncludedInPath("p_p_id");
			portletURL.addParameterIncludedInPath("p_p_lifecycle");
			portletURL.addParameterIncludedInPath("p_p_cacheability");

			portletURL.addParameterIncludedInPath("struts_action");
		}

		return friendlyURLPath;
	}

	public String getMapping() {
		return _MAPPING;
	}

	public String getPortletId() {
		return _PORTLET_ID;
	}

	public void populateParams(
		String friendlyURLPath, Map<String, String[]> params) {

		String[] parts = StringUtil.split(friendlyURLPath, StringPool.SLASH);

		if ((parts.length >= 4) && parts[2].equals("rss")) {
			addParam(params, "p_p_id", _PORTLET_ID);
			addParam(params, "p_p_lifecycle", "2");
			addParam(params, "p_p_cacheability", ResourceURL.FULL);

			addParam(params, "struts_action", "/journal/rss");

			if (parts.length == 4) {
				addParam(params, "feedId", parts[3]);
			}
			else if (parts.length == 5) {
				addParam(params, "groupId", parts[3]);
				addParam(params, "feedId", parts[4]);
			}
		}
	}

	private static final String _MAPPING = "journal";

	private static final String _PORTLET_ID = PortletKeys.JOURNAL;

}