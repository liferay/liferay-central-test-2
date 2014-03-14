/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.login.action;

import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.util.ContentUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;

/**
 * @author Brian Wing Shun Chan
 * @author Julio Camarero
 */
public class ConfigurationActionImpl extends DefaultConfigurationAction {

	@Override
	public void postProcessPortletPreferences(
			long companyId, PortletRequest portletRequest,
			PortletPreferences portletPreferences)
		throws Exception {

		removeDefaultValue(
			portletRequest, portletPreferences, "emailFromAddress",
			PrefsPropsUtil.getString(
				companyId, PropsKeys.ADMIN_EMAIL_FROM_ADDRESS));
		removeDefaultValue(
			portletRequest, portletPreferences, "emailFromName",
			PrefsPropsUtil.getString(
				companyId, PropsKeys.ADMIN_EMAIL_FROM_NAME));

		String defaultLanguageId = LocaleUtil.toLanguageId(
			LocaleUtil.getSiteDefault());

		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailPasswordResetBody_" + defaultLanguageId,
			ContentUtil.get(PropsValues.ADMIN_EMAIL_PASSWORD_RESET_BODY));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailPasswordResetSubject_" + defaultLanguageId,
			ContentUtil.get(PropsValues.ADMIN_EMAIL_PASSWORD_RESET_SUBJECT));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailPasswordSentBody_" + defaultLanguageId,
			ContentUtil.get(PropsValues.ADMIN_EMAIL_PASSWORD_SENT_BODY));
		removeDefaultValue(
			portletRequest, portletPreferences,
			"emailPasswordSentSubject_" + defaultLanguageId,
			ContentUtil.get(PropsValues.ADMIN_EMAIL_PASSWORD_SENT_SUBJECT));
	}

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		validateEmailFrom(actionRequest);

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

}