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

package com.liferay.youtube.web.internal.display.context;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Eudaldo Alonso
 */
public class YouTubeDisplayContext {

	public YouTubeDisplayContext(
		HttpServletRequest request, PortletPreferences portletPreferences) {

		_request = request;
		_portletPreferences = portletPreferences;
	}

	public String getEmbedURL() {
		StringBundler sb = new StringBundler(13);

		sb.append(HttpUtil.getProtocol(_request));
		sb.append("://www.youtube.com/embed/");
		sb.append(getId());
		sb.append("?wmode=transparent");

		if (isAutoPlay()) {
			sb.append("&amp;autoplay=1");
		}

		if (isClosedCaptioning()) {
			sb.append("&amp;cc_load_policy=1");
		}

		if (!isEnableKeyboardControls()) {
			sb.append("&amp;disablekb=1");
		}

		if (isAnnotations()) {
			sb.append("&amp;iv_load_policy=1");
		}
		else {
			sb.append("&amp;iv_load_policy=3");
		}

		if (isLoop()) {
			sb.append("&amp;loop=1&amp;playlist=");
			sb.append(getId());
		}

		if (Validator.isNotNull(getStartTime())) {
			sb.append("&amp;start=");
			sb.append(getStartTime());
		}

		return sb.toString();
	}

	public String getHeight() {
		if (_height != null) {
			return _height;
		}

		_height = _portletPreferences.getValue("height", "360");

		return _height;
	}

	public String getId() {
		if (_id != null) {
			return _id;
		}

		String url = getURL();

		_id = url.replaceAll("^.*?v=([a-zA-Z0-9_-]+).*$", "$1");

		return _id;
	}

	public String getImageURL() {
		StringBundler sb = new StringBundler(4);

		sb.append(HttpUtil.getProtocol(_request));
		sb.append("://img.youtube.com/vi/");
		sb.append(getId());
		sb.append("/0.jpg");

		return sb.toString();
	}

	public String getPresetSize() {
		return getWidth() + "x" + getHeight();
	}

	public String getStartTime() {
		if (_startTime != null) {
			return _startTime;
		}

		_startTime = _portletPreferences.getValue(
			"startTime", StringPool.BLANK);

		return _startTime;
	}

	public String getURL() {
		if (_url != null) {
			return _url;
		}

		_url = _portletPreferences.getValue("url", StringPool.BLANK);

		return _url;
	}

	public String getWatchURL() {
		return HttpUtil.getProtocol(_request) + "://www.youtube.com/watch?v=" +
			getId();
	}

	public String getWidth() {
		if (_width != null) {
			return _width;
		}

		_width = _portletPreferences.getValue("width", "480");

		return _width;
	}

	public boolean isAnnotations() {
		if (_annotations != null) {
			return _annotations;
		}

		_annotations = GetterUtil.getBoolean(
			_portletPreferences.getValue("annotations", "true"));

		return _annotations;
	}

	public boolean isAutoPlay() {
		if (_autoPlay != null) {
			return _autoPlay;
		}

		_autoPlay = GetterUtil.getBoolean(
			_portletPreferences.getValue("autoplay", "false"));

		return _autoPlay;
	}

	public boolean isClosedCaptioning() {
		if (_closedCaptioning != null) {
			return _closedCaptioning;
		}

		_closedCaptioning = GetterUtil.getBoolean(
			_portletPreferences.getValue("closedCaptioning", "false"));

		return _closedCaptioning;
	}

	public boolean isEnableKeyboardControls() {
		if (_enableKeyboardControls != null) {
			return _enableKeyboardControls;
		}

		_enableKeyboardControls = GetterUtil.getBoolean(
			_portletPreferences.getValue("enableKeyboardControls", "true"));

		return _enableKeyboardControls;
	}

	public boolean isLoop() {
		if (_loop != null) {
			return _loop;
		}

		_loop = GetterUtil.getBoolean(
			_portletPreferences.getValue("loop", "false"));

		return _loop;
	}

	public boolean isShowThumbnail() {
		if (_showThumbnail != null) {
			return _showThumbnail;
		}

		_showThumbnail = GetterUtil.getBoolean(
			_portletPreferences.getValue("showThumbnail", "false"));

		return _showThumbnail;
	}

	private Boolean _annotations;
	private Boolean _autoPlay;
	private Boolean _closedCaptioning;
	private Boolean _enableKeyboardControls;
	private String _height;
	private String _id;
	private Boolean _loop;
	private final PortletPreferences _portletPreferences;
	private final HttpServletRequest _request;
	private Boolean _showThumbnail;
	private String _startTime;
	private String _url;
	private String _width;

}