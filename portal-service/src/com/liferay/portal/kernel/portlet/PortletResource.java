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
package com.liferay.portal.kernel.portlet;

/**
 * @author Michael Young
 */
public class PortletResource {

	public PortletResource(String path, boolean singleton) {
		_path = path;
		_singleton = singleton;
	}

	public String getPath() {
		return _path;
	}

	public boolean getSingleton() {
		return _singleton;
	}

	public boolean isSingleton() {
		return _singleton;
	}

	public void setPath(String path) {
		_path = path;
	}

	public void setSingleton(boolean singleton) {
		_singleton = singleton;
	}

	private String _path;
	private boolean _singleton;

}