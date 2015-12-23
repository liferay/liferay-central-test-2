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

package com.liferay.map.taglib.servlet.taglib;

import com.liferay.taglib.util.IncludeTag;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Chema Balsas
 */
public class MapTag extends IncludeTag {

	public void setApiKey(String apiKey) {
		_apiKey = apiKey;
	}

	public void setGeolocation(boolean geolocation) {
		_geolocation = geolocation;
	}

	public void setLatitude(double latitude) {
		_latitude = latitude;
	}

	public void setLongitude(double longitude) {
		_longitude = longitude;
	}

	public void setName(String name) {
		_name = name;
	}

	public void setPoints(String points) {
		_points = points;
	}

	public void setProvider(String provider) {
		_provider = provider;
	}

	@Override
	protected void cleanUp() {
		_apiKey = null;
		_geolocation = false;
		_latitude = 0;
		_longitude = 0;
		_name = null;
		_points = null;
		_provider = null;
	}

	@Override
	protected String getPage() {
		return _PAGE;
	}

	@Override
	protected void setAttributes(HttpServletRequest request) {
		request.setAttribute("liferay-map:map:apiKey", _apiKey);
		request.setAttribute("liferay-map:map:geolocation", _geolocation);
		request.setAttribute("liferay-map:map:latitude", _latitude);
		request.setAttribute("liferay-map:map:longitude", _longitude);
		request.setAttribute("liferay-map:map:name", _name);
		request.setAttribute("liferay-map:map:points", _points);
		request.setAttribute("liferay-map:map:provider", _provider);
	}

	private static final String _PAGE = "/map/page.jsp";

	private String _apiKey;
	private boolean _geolocation;
	private double _latitude;
	private double _longitude;
	private String _name;
	private String _points;
	private String _provider;

}