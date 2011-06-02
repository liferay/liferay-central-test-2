/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portlet.PortalPreferences;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 */
public class SessionTreeJSClicks {

	public static final String CLASS_NAME = SessionTreeJSClicks.class.getName();

	public static void closeNode(
		HttpServletRequest request, String treeId, String nodeId) {

		try {
			PortalPreferences preferences =
				PortletPreferencesFactoryUtil.getPortalPreferences(request);

			String openNodesString = preferences.getValue(CLASS_NAME, treeId);

			openNodesString = StringUtil.remove(openNodesString, nodeId);

			preferences.setValue(CLASS_NAME, treeId, openNodesString);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public static void closeNodes(HttpServletRequest request, String treeId) {
		try {
			PortalPreferences preferences =
				PortletPreferencesFactoryUtil.getPortalPreferences(request);

			String openNodesString = StringPool.BLANK;

			preferences.setValue(CLASS_NAME, treeId, openNodesString);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public static String getOpenNodes(
		HttpServletRequest request, String treeId) {

		try {
			PortalPreferences preferences =
				PortletPreferencesFactoryUtil.getPortalPreferences(request);

			return preferences.getValue(CLASS_NAME, treeId);
		}
		catch (Exception e) {
			_log.error(e, e);

			return null;
		}
	}

	public static void openNode(
		HttpServletRequest request, String treeId, String nodeId) {

		try {
			PortalPreferences preferences =
				PortletPreferencesFactoryUtil.getPortalPreferences(request);

			String openNodesString = preferences.getValue(CLASS_NAME, treeId);

			openNodesString = StringUtil.add(openNodesString, nodeId);

			preferences.setValue(CLASS_NAME, treeId, openNodesString);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public static void openNodes(
		HttpServletRequest request, String treeId, String[] nodeIds) {

		try {
			PortalPreferences preferences =
				PortletPreferencesFactoryUtil.getPortalPreferences(request);

			String openNodesString = preferences.getValue(CLASS_NAME, treeId);

			for (int i = 0; i < nodeIds.length; i++) {
				openNodesString = StringUtil.add(openNodesString, nodeIds[i]);
			}

			preferences.setValue(CLASS_NAME, treeId, openNodesString);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(SessionTreeJSClicks.class);

}