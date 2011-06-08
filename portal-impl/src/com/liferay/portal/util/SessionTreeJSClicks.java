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
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portlet.PortalPreferences;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Eduardo Lundgren
 */
public class SessionTreeJSClicks {

	public static final String CLASS_NAME = SessionTreeJSClicks.class.getName();

	public static void addLayoutNodes(
		HttpServletRequest request, String treeId, long layoutId,
		boolean privateLayout, boolean recursive) {

		try {
			List<String> layoutIds = new ArrayList<String>();

			layoutIds.add(String.valueOf(layoutId));

			if (recursive) {
				getLayoutChildrenIds(
					request, layoutIds, layoutId, privateLayout);
			}

			addNodes(request, treeId, layoutIds.toArray(
				new String[layoutIds.size()]));
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public static void addNode(
		HttpServletRequest request, String treeId, String nodeId) {

		String addNodesString = get(request, treeId);

		addNodesString = StringUtil.add(addNodesString, nodeId);

		put(request, treeId, addNodesString);
	}

	public static void addNodes(
			HttpServletRequest request, String treeId, String[] nodeIds) {

		String addNodesString = get(request, treeId);

		for (int i = 0; i < nodeIds.length; i++) {
			addNodesString = StringUtil.add(addNodesString, nodeIds[i]);
		}

		put(request, treeId, addNodesString);
	}

	public static String getAddedNodes(
		HttpServletRequest request, String treeId) {

		return get(request, treeId);
	}

	public static void removeLayoutNodes(
		HttpServletRequest request, String treeId, long layoutId,
		boolean privateLayout, boolean recursive) {

		try {
			List<String> layoutIds = new ArrayList<String>();

			layoutIds.add(String.valueOf(layoutId));

			if (recursive) {
				getLayoutChildrenIds(
					request, layoutIds, layoutId, privateLayout);
			}

			removeNodes(request, treeId, layoutIds.toArray(
				new String[layoutIds.size()]));
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	public static void removeNode(
		HttpServletRequest request, String treeId, String nodeId) {

		String addNodesString = get(request, treeId);

		addNodesString = StringUtil.remove(addNodesString, nodeId);

		put(request, treeId, addNodesString);
	}

	public static void removeNodes(HttpServletRequest request, String treeId) {
		String addNodesString = StringPool.BLANK;

		put(request, treeId, addNodesString);
	}

	public static void removeNodes(
		HttpServletRequest request, String treeId, String[] nodeIds) {

		String addNodesString = get(request, treeId);

		for (int i = 0; i < nodeIds.length; i++) {
			addNodesString = StringUtil.remove(addNodesString, nodeIds[i]);
		}

		put(request, treeId, addNodesString);
	}

	protected static String get(HttpServletRequest request, String key) {
		try {
			PortalPreferences preferences =
				PortletPreferencesFactoryUtil.getPortalPreferences(request);

			return preferences.getValue(CLASS_NAME, key, StringPool.BLANK);
		}
		catch (Exception e) {
			_log.error(e, e);

			return null;
		}
	}

	protected static List<String> getLayoutChildrenIds(
			HttpServletRequest request, List<String> layoutIds,
			long parentLayoutId, boolean privateLayout)
		throws Exception {

		long groupId = ParamUtil.getLong(request, "groupId");

		List<Layout> layouts = LayoutLocalServiceUtil.getLayouts(
			groupId, privateLayout, parentLayoutId);

		for (Layout layout : layouts) {
			layoutIds.add(String.valueOf(layout.getLayoutId()));

			getLayoutChildrenIds(
				request, layoutIds, layout.getLayoutId(), privateLayout);
		}

		return layoutIds;
	}

	protected static void put(
		HttpServletRequest request, String key, String value) {

		try {
			PortalPreferences preferences =
				PortletPreferencesFactoryUtil.getPortalPreferences(request);

			preferences.setValue(CLASS_NAME, key, value);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(SessionTreeJSClicks.class);

}