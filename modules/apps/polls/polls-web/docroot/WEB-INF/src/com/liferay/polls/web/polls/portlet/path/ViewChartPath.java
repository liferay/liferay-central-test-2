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

package com.liferay.polls.web.polls.portlet.path;

import com.liferay.portal.kernel.struts.path.AuthPublicPath;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;

/**
 * @author Miguel Pastor
 */
@Component(
	immediate = true,
	property = ViewChartPath.AUTH_PUBLIC_PATH + "=/polls/view_chart"
)
public class ViewChartPath implements AuthPublicPath {

	public static final String AUTH_PUBLIC_PATH = "auth.public.path";

	@Modified
	public void modified(Map<String, String> properties) {
		updatePath(properties);
	}

	@Override
	public String path() {
		return _path;
	}

	@Activate
	protected void activate(Map<String, String> properties) {
		updatePath(properties);
	}

	protected void updatePath(Map<String, String> properties) {
		if (!properties.containsKey(AUTH_PUBLIC_PATH)) {
			_path = null;

			return;
		}

		String path = properties.get(AUTH_PUBLIC_PATH);

		_path = path;
	}

	private String _path;

}