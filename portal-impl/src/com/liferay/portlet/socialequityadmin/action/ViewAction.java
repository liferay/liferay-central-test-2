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

package com.liferay.portlet.socialequityadmin.action;

import com.liferay.portal.kernel.bean.BeanPropertiesUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.social.model.SocialEquityActionMapping;
import com.liferay.portlet.social.model.SocialEquitySetting;
import com.liferay.portlet.social.model.SocialEquitySettingConstants;
import com.liferay.portlet.social.service.SocialEquitySettingLocalServiceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Zsolt Balogh
 * @author Zsolt Berentey
 * @author Brian Wing Shun Chan
 */
public class ViewAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String[] classNames = PortalUtil.getSocialEquityClassNames();

		for (String className : classNames) {
			List<SocialEquityActionMapping> mergedEquityActionMappings =
				getMergedEquityActionMappings(actionRequest, className);

			SocialEquitySettingLocalServiceUtil.updateSocialEquitySettings(
				themeDisplay.getScopeGroupId(), className,
				mergedEquityActionMappings);
		}

		sendRedirect(actionRequest, actionResponse);
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Map<String, List<SocialEquityActionMapping>> equityActionMappingsMap =
			new HashMap<String, List<SocialEquityActionMapping>>();

		String[] classNames = PortalUtil.getSocialEquityClassNames();

		for (String className : classNames) {
			List<SocialEquityActionMapping> mergedEquityActionMappings =
				getMergedEquityActionMappings(
					themeDisplay.getScopeGroupId(), className);

			equityActionMappingsMap.put(className, mergedEquityActionMappings);
		}

		renderRequest.setAttribute(
			WebKeys.SOCIAL_EQUITY_ACTION_MAPPINGS_MAP, equityActionMappingsMap);

		return mapping.findForward("portlet.social_equity_admin.view");
	}

	protected SocialEquityActionMapping getMergedEquityActionMapping(
			ActionRequest actionRequest,
			SocialEquityActionMapping equityActionMapping)
		throws Exception {

		SocialEquityActionMapping mergedMapping = equityActionMapping.clone();

		updateModel(actionRequest, mergedMapping, "informationDailyLimit");
		updateModel(actionRequest, mergedMapping, "informationLifespan");
		updateModel(actionRequest, mergedMapping, "informationValue");
		updateModel(actionRequest, mergedMapping, "participationDailyLimit");
		updateModel(actionRequest, mergedMapping, "participationLifespan");
		updateModel(actionRequest, mergedMapping, "participationValue");

		return mergedMapping;
	}

	protected SocialEquityActionMapping getMergedEquityActionMapping(
			long groupId, SocialEquityActionMapping equityActionMapping)
		throws Exception {

		SocialEquityActionMapping mergedEquityActionMapping =
			equityActionMapping.clone();

		List<SocialEquitySetting> equitySettings =
			SocialEquitySettingLocalServiceUtil.getEquitySettings(
				groupId, equityActionMapping.getClassName(),
				equityActionMapping.getActionId());

		for (SocialEquitySetting equitySetting : equitySettings) {
			if (equitySetting.getType() ==
					SocialEquitySettingConstants.TYPE_INFORMATION) {

				mergedEquityActionMapping.setInformationDailyLimit(
					equitySetting.getDailyLimit());
				mergedEquityActionMapping.setInformationLifespan(
					equitySetting.getLifespan());
				mergedEquityActionMapping.setInformationValue(
					equitySetting.getValue());
			}
			else {
				mergedEquityActionMapping.setParticipationDailyLimit(
					equitySetting.getDailyLimit());
				mergedEquityActionMapping.setParticipationLifespan(
					equitySetting.getLifespan());
				mergedEquityActionMapping.setParticipationValue(
					equitySetting.getValue());
			}
		}

		return mergedEquityActionMapping;
	}

	protected List<SocialEquityActionMapping> getMergedEquityActionMappings(
			ActionRequest actionRequest, String className)
		throws Exception {

		List<SocialEquityActionMapping> mergedEquityActionMappings =
			new ArrayList<SocialEquityActionMapping>();

		List<SocialEquityActionMapping> equityActionMappings =
			PortalUtil.getSocialEquityActionMappings(className);

		for (SocialEquityActionMapping equityActionMapping :
				equityActionMappings) {

			SocialEquityActionMapping mergedEquityActionMapping =
				getMergedEquityActionMapping(
					actionRequest, equityActionMapping);

			mergedEquityActionMappings.add(mergedEquityActionMapping);
		}

		return mergedEquityActionMappings;
	}

	protected List<SocialEquityActionMapping> getMergedEquityActionMappings(
			long groupId, String className)
		throws Exception {

		List<SocialEquityActionMapping> mergedEquityActionMappings =
			new ArrayList<SocialEquityActionMapping>();

		List<SocialEquityActionMapping> equityActionMappings =
			PortalUtil.getSocialEquityActionMappings(className);

		for (SocialEquityActionMapping equityActionMapping :
				equityActionMappings) {

			SocialEquityActionMapping mergedEquityActionMapping =
				getMergedEquityActionMapping(groupId, equityActionMapping);

			mergedEquityActionMappings.add(mergedEquityActionMapping);
		}

		return mergedEquityActionMappings;
	}

	protected void updateModel(
			ActionRequest actionRequest,
			SocialEquityActionMapping equityActionMapping, String param)
		throws Exception {

		String className = equityActionMapping.getClassName();

		int value = ParamUtil.getInteger(
			actionRequest,
			className + "." + equityActionMapping.getActionId() + "." + param,
			-1);

		if (value >= 0) {
			BeanPropertiesUtil.setProperty(equityActionMapping, param, value);
		}
	}

}