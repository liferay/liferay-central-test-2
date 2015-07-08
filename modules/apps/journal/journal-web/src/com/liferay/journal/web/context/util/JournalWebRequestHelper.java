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

package com.liferay.journal.web.context.util;

import com.liferay.journal.configuration.JournalGroupServiceConfiguration;
import com.liferay.journal.constants.JournalConstants;
import com.liferay.journal.service.util.JournalServiceComponentProvider;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.settings.GroupServiceSettingsLocator;
import com.liferay.portal.kernel.settings.ParameterMapSettingsLocator;
import com.liferay.portal.kernel.settings.SettingsFactory;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.theme.PortletDisplay;
import com.liferay.portal.theme.ThemeDisplay;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Juergen Kappler
 */
public class JournalWebRequestHelper {

	public JournalWebRequestHelper(HttpServletRequest request) {
		_request = request;
	}

	public JournalGroupServiceConfiguration
		getJournalGroupServiceConfiguration() {

		try {
			if (_journalGroupServiceConfiguration == null) {
				ThemeDisplay themeDisplay = (ThemeDisplay)_request.getAttribute(
					WebKeys.THEME_DISPLAY);

				PortletDisplay portletDisplay =
					themeDisplay.getPortletDisplay();

				String portletResource = portletDisplay.getPortletResource();

				long siteGroupId = themeDisplay.getSiteGroupId();

				JournalServiceComponentProvider journalWebComponentProvider =
					JournalServiceComponentProvider.
						getJournalServiceComponentProvider();

				SettingsFactory settingsFactory =
					journalWebComponentProvider.getSettingsFactory();

				if (Validator.isNotNull(portletResource)) {
					_journalGroupServiceConfiguration =
						settingsFactory.getSettings(
							JournalGroupServiceConfiguration.class,
						new ParameterMapSettingsLocator(
							_request.getParameterMap(),
							new GroupServiceSettingsLocator(
								siteGroupId, JournalConstants.SERVICE_NAME)));
				}
				else {
					_journalGroupServiceConfiguration =
						settingsFactory.getSettings(
							JournalGroupServiceConfiguration.class,
						new GroupServiceSettingsLocator(
							siteGroupId, JournalConstants.SERVICE_NAME));
				}
			}

			return _journalGroupServiceConfiguration;
		}
		catch (PortalException pe) {
			throw new SystemException(pe);
		}
	}

	private JournalGroupServiceConfiguration _journalGroupServiceConfiguration;
	private final HttpServletRequest _request;

}