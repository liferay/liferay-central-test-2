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

package com.liferay.portlet.journalcontent;

import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.PortletKeys;

import java.util.Map;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;

/**
 * <a href="JournalContentFriendlyURLMapper.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Raymond Augé
 */
public class JournalContentFriendlyURLMapper implements FriendlyURLMapper {

	public String buildPath(LiferayPortletURL portletURL) {
		String friendlyURLPath = null;

		String strutsAction = GetterUtil.getString(
			portletURL.getParameter("struts_action"));

		WindowState windowState = portletURL.getWindowState();

		if ((strutsAction.equals("/journal_content/view")) &&
			((windowState == null) ||
			 (!windowState.equals(LiferayWindowState.EXCLUSIVE) &&
			  !windowState.equals(LiferayWindowState.POP_UP)))) {

			String portletId = portletURL.getPortletId();
			String groupId = portletURL.getParameter("groupId");
			String articleId = portletURL.getParameter("articleId");
			String templateId = portletURL.getParameter("templateId");

			if (Validator.isNotNull(portletId) &&
				Validator.isNotNull(groupId) &&
				Validator.isNotNull(articleId)) {

				if (portletId.equals(_PORTLET_DEFAULT_INSTANCE)) {
					portletId = _PORTLET_ID;
				}

				friendlyURLPath =
					"/journal_content/" + portletId + "/" + groupId + "/" +
						articleId;

				portletURL.addParameterIncludedInPath("groupId");
				portletURL.addParameterIncludedInPath("articleId");

				if (Validator.isNotNull(templateId)) {
					friendlyURLPath += "/" + templateId;

					portletURL.addParameterIncludedInPath("templateId");
				}
			}
		}

		if (Validator.isNotNull(friendlyURLPath)) {
			portletURL.addParameterIncludedInPath("p_p_id");

			portletURL.addParameterIncludedInPath("struts_action");
		}

		return friendlyURLPath;
	}

	public String getMapping() {
		return _MAPPING;
	}

	public boolean isCheckMappingWithPrefix() {
		return _CHECK_MAPPING_WITH_PREFIX;
	}

	public void populateParams(
		String friendlyURLPath, Map<String, String[]> params) {

		int w = friendlyURLPath.indexOf("/", 1);
		int x = friendlyURLPath.indexOf("/", w + 1);
		int y = friendlyURLPath.indexOf("/", x + 1);
		int z = friendlyURLPath.indexOf("/", y + 1);

		if (x == -1) {
			return;
		}

		String portletId = friendlyURLPath.substring(w + 1, x);

		String namespace =
			StringPool.UNDERLINE + portletId + StringPool.UNDERLINE;

		if (portletId.equals(_PORTLET_ID)) {
			portletId = _PORTLET_DEFAULT_INSTANCE;

			namespace = StringPool.UNDERLINE + portletId + StringPool.UNDERLINE;

			params.put("p_p_id", new String[] {portletId});
			params.put(
				"p_p_state", new String[] {WindowState.MAXIMIZED.toString()});
		}
		else {
			params.put("p_p_id", new String[] {portletId});
			params.put(
				"p_p_state", new String[] {WindowState.NORMAL.toString()});
		}

		params.put("p_p_lifecycle", new String[] {"0"});
		params.put("p_p_mode", new String[] {PortletMode.VIEW.toString()});

		String groupId = friendlyURLPath.substring(x + 1, y);

		params.put(namespace + "groupId", new String[] {groupId});

		String articleId = null;

		if (z == -1) {
			articleId =
				friendlyURLPath.substring(y + 1, friendlyURLPath.length());

			params.put(namespace + "articleId", new String[] {articleId});
		}
		else {
			articleId = friendlyURLPath.substring(y + 1, z);

			params.put(namespace + "articleId", new String[] {articleId});

			String templateId =
				friendlyURLPath.substring(z + 1, friendlyURLPath.length());

			params.put(namespace + "templateId", new String[] {templateId});
		}

		params.put(
			namespace + "struts_action",
			new String[] {"/journal_content/view"});
	}

	private static final boolean _CHECK_MAPPING_WITH_PREFIX = true;

	private static final String _MAPPING = "journal_content";

	private static final String _PORTLET_DEFAULT_INSTANCE =
		PortletKeys.JOURNAL_CONTENT + "_INSTANCE_0000";

	private static final String _PORTLET_ID = PortletKeys.JOURNAL_CONTENT;

}