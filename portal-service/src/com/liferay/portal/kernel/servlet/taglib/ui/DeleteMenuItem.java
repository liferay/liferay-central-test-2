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

package com.liferay.portal.kernel.servlet.taglib.ui;

/**
 * @author Iván Zaera
 */
public class DeleteMenuItem extends MenuItem {

	public DeleteMenuItem(String id, String url) {
		this(id, url, false);
	}

	public DeleteMenuItem(String id, String url, boolean trash) {
		super(id, null, null);

		_url = url;
		_trash = trash;
	}

	public boolean getTrash() {
		return _trash;
	}

	public String getURL() {
		return _url;
	}

	private boolean _trash;
	private String _url;

}