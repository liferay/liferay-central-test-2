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

package com.liferay.mobile.device.rules.web.portlet.action;

import com.liferay.mobile.device.rules.constants.MobileDeviceRulesPortletKeys;
import com.liferay.portal.kernel.mobile.device.rulegroup.RuleGroupProcessorUtil;
import com.liferay.portal.kernel.mobile.device.rulegroup.rule.RuleHandler;
import com.liferay.portal.kernel.mobile.device.rulegroup.rule.UnknownRuleHandlerException;
import com.liferay.portal.kernel.portlet.bridges.mvc.BaseMVCActionCommand;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCActionCommand;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.LocalizationUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portlet.mobiledevicerules.NoSuchActionException;
import com.liferay.portlet.mobiledevicerules.NoSuchRuleGroupException;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleServiceUtil;

import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Edward Han
 * @author Mate Thurzo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + MobileDeviceRulesPortletKeys.MOBILE_DEVICE_SITE_ADMIN,
		"mvc.command.name=/mobile_device_rules/edit_rule"
	},
	service = MVCActionCommand.class
)
public class EditRuleMVCActionCommand extends BaseMVCActionCommand {

	protected void deleteRule(ActionRequest request) throws Exception {
		long ruleId = ParamUtil.getLong(request, "ruleId");

		MDRRuleServiceUtil.deleteRule(ruleId);
	}

	@Override
	protected void doProcessAction(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateRule(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteRule(actionRequest);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchActionException ||
				e instanceof NoSuchRuleGroupException ||
				e instanceof PrincipalException ||
				e instanceof UnknownRuleHandlerException) {

				SessionErrors.add(actionRequest, e.getClass());

				actionResponse.setRenderParameter("mvcPath", "/error.jsp");
			}
			else {
				throw e;
			}
		}
	}

	protected void updateRule(ActionRequest actionRequest) throws Exception {
		long ruleId = ParamUtil.getLong(actionRequest, "ruleId");

		long ruleGroupId = ParamUtil.getLong(actionRequest, "ruleGroupId");
		Map<Locale, String> nameMap = LocalizationUtil.getLocalizationMap(
			actionRequest, "name");
		Map<Locale, String> descriptionMap =
			LocalizationUtil.getLocalizationMap(actionRequest, "description");
		String type = ParamUtil.getString(actionRequest, "type");

		RuleHandler ruleHandler = RuleGroupProcessorUtil.getRuleHandler(type);

		if (ruleHandler == null) {
			throw new UnknownRuleHandlerException(type);
		}

		UnicodeProperties typeSettingsProperties =
			ActionUtil.getTypeSettingsProperties(
				actionRequest, ruleHandler.getPropertyNames());

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			actionRequest);

		if (ruleId <= 0) {
			MDRRuleServiceUtil.addRule(
				ruleGroupId, nameMap, descriptionMap, type,
				typeSettingsProperties, serviceContext);
		}
		else {
			MDRRuleServiceUtil.updateRule(
				ruleId, nameMap, descriptionMap, type, typeSettingsProperties,
				serviceContext);
		}
	}

}