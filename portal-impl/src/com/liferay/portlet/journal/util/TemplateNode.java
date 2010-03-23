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

package com.liferay.portlet.journal.util;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <a href="TemplateNode.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 * @author Raymond Augé
 */
public class TemplateNode extends LinkedHashMap<String, Object> {

	public static final String PRIVATE_GROUP_LAYOUT_TYPE = "private-group";

	public static final String PRIVATE_USER_LAYOUT_TYPE = "private-user";

	public static final String PUBLIC_LAYOUT_TYPE = "public";

	public TemplateNode(
		ThemeDisplay themeDisplay, String name, String data, String type) {

		super();

		put("name", name);
		put("data", data);
		put("type", type);
		put("options", new ArrayList<String>());

		_themeDisplay = themeDisplay;
	}

	public void appendChild(TemplateNode child) {
		_children.put(child.getName(), child);
		put(child.getName(), child);
	}

	public void appendChildren(List<TemplateNode> children) {
		for (TemplateNode child : children) {
			appendChild(child);
		}
	}

	public void appendOption(String option) {
		getOptions().add(option);
	}

	public void appendOptions(List<String> options) {
		getOptions().addAll(options);
	}

	public void appendSibling(TemplateNode sibling) {
		_siblings.add(sibling);
	}

	public TemplateNode getChild(String name) {
		return _children.get(name);
	}

	public List<TemplateNode> getChildren() {
		return new ArrayList<TemplateNode>(_children.values());
	}

	public String getData() {
		if (getType().equals("link_to_layout")) {
			String data = (String)get("data");

			int pos = data.indexOf(StringPool.AT);

			if (pos != -1) {
				data = data.substring(0, pos);
			}

			return data;
		}
		else {
			return (String)get("data");
		}
	}

	public String getFriendlyUrl() {
		if (_themeDisplay == null) {
			return getUrl();
		}

		if (getType().equals("link_to_layout")) {
			String layoutType = _getLayoutType();
			long layoutId = _getLayoutId();

			boolean privateLayout = layoutType.startsWith("private");

			try {
				Layout layout = LayoutLocalServiceUtil.getLayout(
					_themeDisplay.getScopeGroupId(), privateLayout, layoutId);

				return PortalUtil.getLayoutFriendlyURL(layout, _themeDisplay);
			}
			catch (Exception e) {
				if (_log.isDebugEnabled()) {
					_log.debug(
						"Error finding friendly Url on page " +
							_themeDisplay.getURLCurrent(), e);
				}

				return getUrl();
			}
		}

		return StringPool.BLANK;
	}

	public String getName() {
		return (String)get("name");
	}

	public List<String> getOptions() {
		return (List<String>)get("options");
	}

	public List<TemplateNode> getSiblings() {
		return _siblings;
	}

	public String getType() {
		return (String)get("type");
	}

	public String getUrl() {
		if (getType().equals("link_to_layout")) {
			StringBundler sb = new StringBundler(5);

			String layoutType = _getLayoutType();
			long layoutId = _getLayoutId();

			if (layoutType.equals(PUBLIC_LAYOUT_TYPE)) {
				sb.append(PortalUtil.getPathFriendlyURLPublic());
			}
			else if (layoutType.equals(PRIVATE_GROUP_LAYOUT_TYPE)) {
				sb.append(PortalUtil.getPathFriendlyURLPrivateGroup());
			}
			else if (layoutType.equals(PRIVATE_USER_LAYOUT_TYPE)) {
				sb.append(PortalUtil.getPathFriendlyURLPrivateUser());
			}
			else {
				sb.append("@friendly_url_current@");
			}

			sb.append(StringPool.SLASH);
			sb.append("@group_id@");
			sb.append(StringPool.SLASH);
			sb.append(layoutId);

			return sb.toString();
		}

		return StringPool.BLANK;
	}

	private long _getLayoutId() {
		String data = (String)get("data");

		int pos = data.indexOf(StringPool.AT);

		if (pos != -1) {
			data = data.substring(0, pos);
		}

		return GetterUtil.getLong(data);
	}

	private String _getLayoutType() {
		String data = (String)get("data");

		int pos = data.indexOf(StringPool.AT);

		if (pos != -1) {
			data = data.substring(pos + 1);
		}

		return data;
	}

	private Map<String, TemplateNode> _children =
		new LinkedHashMap<String, TemplateNode>();
	private List<TemplateNode> _siblings = new ArrayList<TemplateNode>();
	private ThemeDisplay _themeDisplay;

	private static Log _log = LogFactoryUtil.getLog(TemplateNode.class);

}