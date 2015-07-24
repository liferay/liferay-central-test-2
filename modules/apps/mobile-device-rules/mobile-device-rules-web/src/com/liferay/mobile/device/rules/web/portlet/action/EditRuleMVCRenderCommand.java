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

import com.liferay.mobile.device.rules.constants.MDRPortletKeys;
import com.liferay.mobile.device.rules.web.constants.MDRWebKeys;
import com.liferay.portal.kernel.bean.BeanParamUtil;
import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portlet.mobiledevicerules.model.MDRRule;
import com.liferay.portlet.mobiledevicerules.model.MDRRuleGroup;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleGroupServiceUtil;
import com.liferay.portlet.mobiledevicerules.service.MDRRuleServiceUtil;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.osgi.service.component.annotations.Component;

/**
 * @author Mate Thurzo
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + MDRPortletKeys.MOBILE_DEVICE_RULES,
		"mvc.command.name=/mobile_device_rules/edit_rule"
	},
	service = MVCRenderCommand.class
)
public class EditRuleMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(
		RenderRequest renderRequest, RenderResponse renderResponse) {

		try {
			long ruleId = ParamUtil.getLong(renderRequest, "ruleId");

			MDRRule rule = MDRRuleServiceUtil.fetchRule(ruleId);

			renderRequest.setAttribute(
				MDRWebKeys.MOBILE_DEVICE_RULES_RULE, rule);

			String type = BeanPropertiesUtil.getString(rule, "type");

			renderRequest.setAttribute(
				MDRWebKeys.MOBILE_DEVICE_RULES_RULE_TYPE, type);

			String editorJSP = ActionUtil.getRuleEditorJSPMethod.getEditorJSP(
				type);

			renderRequest.setAttribute(
				MDRWebKeys.MOBILE_DEVICE_RULES_RULE_EDITOR_JSP, editorJSP);

			long ruleGroupId = BeanParamUtil.getLong(
				rule, renderRequest, "ruleGroupId");

			MDRRuleGroup ruleGroup = MDRRuleGroupServiceUtil.getRuleGroup(
				ruleGroupId);

			renderRequest.setAttribute(
				MDRWebKeys.MOBILE_DEVICE_RULES_RULE_GROUP, ruleGroup);

			return "/edit_rule.jsp";
		}
		catch (PortalException pe) {
			return "/error.jsp";
		}
	}

}