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

package com.liferay.portlet.social.action;

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
 * <a href="ViewAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Zsolt Balogh
 * @author Zsolt Berentey
 */
public class ViewAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)actionRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		String[] models = PortalUtil.getSocialEquityModels();

		for (String model : models) {
			List<SocialEquityActionMapping> equityMappings =
				PortalUtil.getSocialEquityActionMappings(model);

			long classNameId = PortalUtil.getClassNameId(model);

			List<SocialEquityActionMapping> mergedMappings =
				new ArrayList<SocialEquityActionMapping>();

			for (SocialEquityActionMapping equityMapping : equityMappings) {
				SocialEquityActionMapping clone = equityMapping.clone();

				mergedMappings.add(clone);

				int iqValue = ParamUtil.getInteger(actionRequest, model + "." +
					clone.getActionKey() + ".iqvalue", -1);

				if (iqValue >= 0) {
					clone.setInformationValue(iqValue);
				}

				int iqExpiry = ParamUtil.getInteger(actionRequest, model + "." +
					clone.getActionKey() + ".iqexpiry", -1);

				if (iqExpiry >= 0) {
					clone.setInformationLifespan(iqExpiry);
				}

				int pqValue = ParamUtil.getInteger(actionRequest, model + "." +
					clone.getActionKey() + ".pqvalue", -1);

				if (pqValue >= 0) {
					clone.setParticipationValue(pqValue);
				}

				int pqExpiry = ParamUtil.getInteger(actionRequest, model + "." +
					clone.getActionKey() + ".pqexpiry", -1);

				if (pqExpiry >= 0) {
					clone.setParticipationLifespan(pqExpiry);
				}
			}

			SocialEquitySettingLocalServiceUtil.updateSocialEquitySettings(
				model, themeDisplay.getScopeGroupId(), classNameId,
				mergedMappings);
		}

		addSuccessMessage(actionRequest, actionResponse);

		sendRedirect(actionRequest, actionResponse);
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)renderRequest.getAttribute(
				WebKeys.THEME_DISPLAY);

		Map<String, List<SocialEquityActionMapping>> equityActionMapping =
			new HashMap<String, List<SocialEquityActionMapping>>();

		String[] equityModels = PortalUtil.getSocialEquityModels();

		for (String model : equityModels) {
			long classNameId = PortalUtil.getClassNameId(model);

			List<SocialEquityActionMapping> mappings =
				PortalUtil.getSocialEquityActionMappings(model);

			List<SocialEquityActionMapping> mergedMappings =
				new ArrayList<SocialEquityActionMapping>();

			for (SocialEquityActionMapping actionMapping : mappings) {
				SocialEquityActionMapping clone = actionMapping.clone();

				mergedMappings.add(clone);

				List<SocialEquitySetting> settings =
					SocialEquitySettingLocalServiceUtil.getEquitySettings(
						themeDisplay.getScopeGroupId(), classNameId,
						actionMapping.getActionKey());

				for (SocialEquitySetting setting : settings) {
					if (setting.getType() ==
						SocialEquitySettingConstants.TYPE_INFORMATION) {

						clone.setInformationValue(
							setting.getValue());
						clone.setInformationLifespan(
							setting.getValidity());
					}
					else {
						clone.setParticipationValue(
							setting.getValue());
						clone.setParticipationLifespan(
							setting.getValidity());
					}
				}
			}

			equityActionMapping.put(model, mergedMappings);
		}

		renderRequest.setAttribute("equity-action-mapping",equityActionMapping);

		return mapping.findForward("portlet.social_equity.view");
	}

}