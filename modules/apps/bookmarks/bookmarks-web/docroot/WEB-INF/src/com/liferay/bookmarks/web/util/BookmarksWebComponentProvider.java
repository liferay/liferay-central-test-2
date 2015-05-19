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

package com.liferay.bookmarks.web.util;

import com.liferay.bookmarks.configuration.BookmarksGroupServiceConfiguration;
import com.liferay.portal.kernel.settings.SettingsFactory;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Iván Zaera
 */
@Component(immediate = true)
public class BookmarksWebComponentProvider {

	public static BookmarksWebComponentProvider
		getBookmarksWebComponentProvider() {

		return _bookmarksWebComponentProvider;
	}

	public BookmarksGroupServiceConfiguration
		getBookmarksGroupServiceConfiguration() {

		return _bookmarksGroupServiceConfiguration;
	}

	public SettingsFactory getSettingsFactory() {
		return _settingsFactory;
	}

	@Activate
	protected void activate() {
		_bookmarksWebComponentProvider = this;
	}

	@Deactivate
	protected void deactivate() {
		_bookmarksWebComponentProvider = null;
	}

	@Reference
	protected void setBookmarksGroupServiceConfiguration(
		BookmarksGroupServiceConfiguration bookmarksGroupServiceConfiguration) {

		_bookmarksGroupServiceConfiguration =
			bookmarksGroupServiceConfiguration;
	}

	@Reference(unbind = "-")
	protected void setSettingsFactory(SettingsFactory settingsFactory) {
		_settingsFactory = settingsFactory;
	}

	protected void unsetBookmarksGroupServiceConfiguration(
		BookmarksGroupServiceConfiguration bookmarksGroupServiceConfiguration) {

		_bookmarksGroupServiceConfiguration = null;
	}

	private static BookmarksWebComponentProvider _bookmarksWebComponentProvider;

	private BookmarksGroupServiceConfiguration
		_bookmarksGroupServiceConfiguration;
	private SettingsFactory _settingsFactory;

}