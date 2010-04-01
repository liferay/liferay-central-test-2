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

package com.liferay.portal.googleapps;

/**
 * <a href="GoogleApps.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class GoogleApps {

	public GoogleApps(long companyId) {
		_gAuthenticator = new GAuthenticator(companyId);

		init();
	}

	public GoogleApps(String domain, String userName, String password) {
		_gAuthenticator = new GAuthenticator(domain, userName, password);

		init();
	}

	public GEmailSettingsService getGEmailSettingsService() {
		return _gEmailSettingsService;
	}

	public GGroupService getGGroupService() {
		return _gGroupService;
	}

	public GNicknameService getGNicknameService() {
		return _gNicknameService;
	}

	public GUserService getGUserService() {
		return _gUserService;
	}

	protected void init() {
		_gEmailSettingsService = new GEmailSettingsServiceImpl(_gAuthenticator);
		_gGroupService = new GGroupServiceImpl(_gAuthenticator);
		_gNicknameService = new GNicknameServiceImpl(_gAuthenticator);
		_gUserService = new GUserServiceImpl(_gAuthenticator);
	}

	private GAuthenticator _gAuthenticator;
	private GEmailSettingsService _gEmailSettingsService;
	private GGroupService _gGroupService;
	private GNicknameService _gNicknameService;
	private GUserService _gUserService;

}