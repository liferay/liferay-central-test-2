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

package com.liferay.sync.service.impl;

import com.liferay.oauth.model.OAuthApplication;
import com.liferay.oauth.model.OAuthApplicationConstants;
import com.liferay.oauth.service.OAuthApplicationLocalServiceUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.sync.configuration.SyncServiceConfigurationKeys;
import com.liferay.sync.service.base.SyncPreferencesLocalServiceBaseImpl;

import java.io.InputStream;

import java.util.Map;
import java.util.Properties;

import javax.portlet.PortletPreferences;

/**
 * @author Dennis Ju
 */
public class SyncPreferencesLocalServiceImpl
	extends SyncPreferencesLocalServiceBaseImpl {

}