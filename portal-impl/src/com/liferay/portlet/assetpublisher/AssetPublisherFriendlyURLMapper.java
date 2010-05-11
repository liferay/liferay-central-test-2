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

package com.liferay.portlet.assetpublisher;

import com.liferay.portal.kernel.portlet.BaseFriendlyURLMapper;
import com.liferay.portal.kernel.portlet.LiferayPortletURL;
import com.liferay.portal.kernel.portlet.LiferayWindowState;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.util.PortletKeys;

import java.util.Map;

import javax.portlet.PortletMode;
import javax.portlet.WindowState;

/**
 * <a href="AssetPublisherFriendlyURLMapper.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Julio Camarero
 */
public class AssetPublisherFriendlyURLMapper extends BaseFriendlyURLMapper {

	public String buildPath(LiferayPortletURL portletURL) {
		String friendlyURLPath = null;

		String strutsAction = GetterUtil.getString(
			portletURL.getParameter("struts_action"));

		WindowState windowState = portletURL.getWindowState();

		if ((strutsAction.equals("/asset_publisher/view_content"))  &&
			((windowState == null) ||
			 (!windowState.equals(LiferayWindowState.EXCLUSIVE) &&
			  !windowState.equals(LiferayWindowState.POP_UP)))) {

			String portletId = portletURL.getPortletId();
			String assetEntryId = portletURL.getParameter("assetEntryId");
			String type = GetterUtil.getString(
				portletURL.getParameter("type"), "content");
			long groupId = GetterUtil.getLong(
				portletURL.getParameter("groupId"));
			String urlTitle = portletURL.getParameter("urlTitle");

			if (Validator.isNotNull(portletId) &&
				Validator.isNotNull(assetEntryId)) {

				if (portletId.equals(_PORTLET_DEFAULT_INSTANCE)) {
					portletId = _PORTLET_ID;
				}

				int pos = portletId.indexOf(
					PortletConstants.INSTANCE_SEPARATOR);

				String instanceId = null;

				if (pos > 0) {
					instanceId = portletId.substring(pos + 10);
				}
				else {
					instanceId = portletId;
				}

				friendlyURLPath =
					"/asset_publisher/" + instanceId + StringPool.SLASH + type +
						StringPool.SLASH;

				if (Validator.isNotNull(urlTitle)) {
					friendlyURLPath += urlTitle;

					portletURL.addParameterIncludedInPath("urlTitle");

					if (groupId > 0) {
						friendlyURLPath += StringPool.SLASH + groupId;

						portletURL.addParameterIncludedInPath("groupId");
					}
				}
				else {
					friendlyURLPath += "id/" + assetEntryId;
				}

				portletURL.addParameterIncludedInPath("type");
				portletURL.addParameterIncludedInPath("assetEntryId");
			}
		}
		else if (windowState.equals(WindowState.MAXIMIZED)) {
			friendlyURLPath += StringPool.SLASH + windowState;
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

	public String getPortletId() {
		return _PORTLET_ID;
	}

	public void populateParams(
		String friendlyURLPath, Map<String, String[]> params) {

		int x = friendlyURLPath.indexOf(StringPool.SLASH, 1);

		String[] urlFragments = StringUtil.split(
			friendlyURLPath.substring(x + 1), StringPool.SLASH);

		if (urlFragments.length > 2) {
			String instanceId = urlFragments[0];
			String type = urlFragments[1];
			String assetEntryId = null;
			long groupId = 0;
			String urlTitle = null;

			if ((urlFragments.length > 3) && urlFragments[2].equals("id")) {
				assetEntryId = urlFragments[3];
			}
			else if (urlFragments.length > 3) {
				urlTitle = urlFragments[2];

				groupId = GetterUtil.getLong(urlFragments[3]);
			}
			else {
				urlTitle = urlFragments[2];
			}

			String portletId =
				_PORTLET_ID + PortletConstants.INSTANCE_SEPARATOR + instanceId;

			params.put("p_p_id", new String[] {portletId});
			params.put("p_p_lifecycle", new String[] {"0"});

			if (friendlyURLPath.indexOf("maximized", x) != -1) {
				addParam(params, "p_p_state", WindowState.MAXIMIZED);
			}

			params.put("p_p_mode", new String[] {PortletMode.VIEW.toString()});

			String namespace =
				StringPool.UNDERLINE + portletId + StringPool.UNDERLINE;

			params.put(
				namespace + "struts_action",
				new String[] {"/asset_publisher/view_content"});
			params.put(namespace + "type", new String[] {type});

			if (Validator.isNotNull(assetEntryId)) {
				params.put(
					namespace + "assetEntryId", new String[] {assetEntryId});
			}
			else {
				if (groupId > 0) {
					params.put(
						namespace + "groupId",
						new String[] {String.valueOf(groupId)});
				}

				params.put(namespace + "urlTitle", new String[] {urlTitle});
			}
		}
	}

	private static final String _MAPPING = "asset_publisher";

	private static final String _PORTLET_DEFAULT_INSTANCE =
		PortletKeys.ASSET_PUBLISHER + "_INSTANCE_0000";

	private static final String _PORTLET_ID = PortletKeys.ASSET_PUBLISHER;

}