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

package com.liferay.portlet.blogs.action;

import com.liferay.portal.kernel.portlet.DefaultConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

/**
 * @author Jorge Ferrer
 * @author Thiago Moreira
 */
public class ConfigurationActionImpl extends DefaultConfigurationAction {

	@Override
	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (Validator.isNotNull(cmd)) {
			validateEmailFrom(actionRequest);
			validateEmailEntryAdded(actionRequest);
			validateEmailEntryUpdated(actionRequest);
		}

		super.processAction(portletConfig, actionRequest, actionResponse);
	}

	protected void validateEmailEntryAdded(ActionRequest actionRequest)
		throws Exception {

		boolean emailEntryAddedEnabled = GetterUtil.getBoolean(
			getParameter(actionRequest, "emailEntryAddedEnabled"));
		String emailEntryAddedSubject = getLocalizedParameter(
			actionRequest, "emailEntryAddedSubject");
		String emailEntryAddedBody = getLocalizedParameter(
			actionRequest, "emailEntryAddedBody");

		if (emailEntryAddedEnabled) {
			if (Validator.isNull(emailEntryAddedSubject)) {
				SessionErrors.add(actionRequest, "emailEntryAddedSubject");
			}
			else if (Validator.isNull(emailEntryAddedBody)) {
				SessionErrors.add(actionRequest, "emailEntryAddedBody");
			}
		}
	}

	protected void validateEmailEntryUpdated(ActionRequest actionRequest)
		throws Exception {

		boolean emailEntryUpdatedEnabled = GetterUtil.getBoolean(
			getParameter(actionRequest, "emailEntryUpdatedEnabled"));
		String emailEntryUpdatedSubject = getLocalizedParameter(
			actionRequest, "emailEntryUpdatedSubject");
		String emailEntryUpdatedBody = getLocalizedParameter(
			actionRequest, "emailEntryUpdatedBody");

		if (emailEntryUpdatedEnabled) {
			if (Validator.isNull(emailEntryUpdatedSubject)) {
				SessionErrors.add(actionRequest, "emailEntryUpdatedSubject");
			}
			else if (Validator.isNull(emailEntryUpdatedBody)) {
				SessionErrors.add(actionRequest, "emailEntryUpdatedBody");
			}
		}
	}

}