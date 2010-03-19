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

package com.liferay.portal.util;

import com.liferay.portal.kernel.configuration.Filter;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.Layout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <a href="LayoutSetting.java.html"><b><i>View Source</i></b></a>
 *
 * @author Shuyang Zhou
 */
public class LayoutSetting {

	public static LayoutSetting getLayoutSetting(Layout layout) {
		return getLayoutSetting(layout.getType());
	}

	public static LayoutSetting getLayoutSetting(String type) {
		return _SETTINGS.get(type);
	}

	public String getConfigurationActionDelete() {
		return _configurationActionDelete;
	}

	public String getConfigurationActionUpdate() {
		return _configurationActionUpdate;
	}

	public String getEditPage() {
		return _editPage;
	}

	public String getType() {
		return _type;
	}

	public String getUrl() {
		return _url;
	}

	public String getViewPage() {
		return _viewPage;
	}

	public boolean isFirstPageable() {
		return _firstPageable;
	}

	public boolean isParentable() {
		return _parentable;
	}

	public boolean isSitemapable() {
		return _sitemapable;
	}

	public boolean isUrlFriendliable() {
		return _urlFriendliable;
	}

	public String toString() {
		StringBundler sb = new StringBundler(21);

		sb.append("{type=");
		sb.append(_type);
		sb.append(", editPage=");
		sb.append(_editPage);
		sb.append(", viewPage=");
		sb.append(_viewPage);
		sb.append(", url=");
		sb.append(_url);
		sb.append(", urlFriendliable=");
		sb.append(_urlFriendliable);
		sb.append(", parentable=");
		sb.append(_parentable);
		sb.append(", sitemapable=");
		sb.append(_sitemapable);
		sb.append(", firstPageable=");
		sb.append(_firstPageable);
		sb.append(", configurationActionUpdate=");
		sb.append(_configurationActionUpdate);
		sb.append(", configurationActionDelete=");
		sb.append(_configurationActionDelete);
		sb.append("}");

		return sb.toString();
	}

	private LayoutSetting() {
	}

	private static Map<String, LayoutSetting> _SETTINGS =
		new HashMap<String, LayoutSetting>();

	static {
		List<String> types = new ArrayList<String>();
		types.addAll(Arrays.asList(PropsUtil.getArray(PropsKeys.LAYOUT_TYPES)));
		types.addAll(
			Arrays.asList(PropsUtil.getArray(PropsKeys.LAYOUT_SYSTEM_TYPES)));

		for (String type : types) {
			LayoutSetting setting = new LayoutSetting();
			setting._type = type;
			setting._editPage = GetterUtil.getString(
				PropsUtil.get(PropsKeys.LAYOUT_EDIT_PAGE, new Filter(type)));
			setting._viewPage = GetterUtil.getString(
				PropsUtil.get(PropsKeys.LAYOUT_VIEW_PAGE, new Filter(type)));
			setting._url = GetterUtil.getString(
				PropsUtil.get(PropsKeys.LAYOUT_URL, new Filter(type)));
			setting._urlFriendliable = GetterUtil.getBoolean(
				PropsUtil.get(
					PropsKeys.LAYOUT_URL_FRIENDLIABLE, new Filter(type)), true);
			setting._parentable = GetterUtil.getBoolean(
				PropsUtil.get(
					PropsKeys.LAYOUT_PARENTABLE, new Filter(type)), true);
			setting._sitemapable = GetterUtil.getBoolean(
				PropsUtil.get(
					PropsKeys.LAYOUT_SITEMAPABLE, new Filter(type)), true);
			setting._firstPageable = GetterUtil.getBoolean(
				PropsUtil.get(
					PropsKeys.LAYOUT_FIRST_PAGEABLE, new Filter(type)), false);
			setting._configurationActionUpdate = GetterUtil.getString(
				PropsUtil.get(
					PropsKeys.LAYOUT_CONFIGURATION_ACTION_UPDATE,
						new Filter(type)));
			setting._configurationActionDelete = GetterUtil.getString(
				PropsUtil.get(
					PropsKeys.LAYOUT_CONFIGURATION_ACTION_DELETE,
						new Filter(type)));

			_SETTINGS.put(type, setting);
		}
	}

	private String _configurationActionDelete;
	private String _configurationActionUpdate;
	private String _editPage;
	private boolean _firstPageable;
	private boolean _parentable;
	private boolean _sitemapable;
	private String _type;
	private String _url;
	private boolean _urlFriendliable;
	private String _viewPage;

}