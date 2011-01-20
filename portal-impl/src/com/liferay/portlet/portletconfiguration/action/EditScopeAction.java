/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.portletconfiguration.action;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletConfigFactoryUtil;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.portletconfiguration.util.PortletConfigurationUtil;

import java.util.ResourceBundle;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletRequest;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import javax.servlet.ServletContext;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Jesper Weissglas
 * @author Jorge Ferrer
 * @author Hugo Huijser
 */
public class EditScopeAction extends EditConfigurationAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		Portlet portlet = null;

		try {
			portlet = getPortlet(actionRequest);
		}
		catch (PrincipalException pe) {
			SessionErrors.add(
				actionRequest, PrincipalException.class.getName());

			setForward(actionRequest, "portlet.portlet_configuration.error");
		}

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (cmd.equals(Constants.SAVE)) {
			updateScope(actionRequest, portlet);
		}

		if (SessionErrors.isEmpty(actionRequest)) {
			SessionMessages.add(
				actionRequest,
				portletConfig.getPortletName() + ".doConfigure");

			String redirect = ParamUtil.getString(actionRequest, "redirect");

			actionResponse.sendRedirect(redirect);
		}
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		Portlet portlet = null;

		try {
			portlet = getPortlet(renderRequest);
		}
		catch (PrincipalException pe) {
			SessionErrors.add(
				renderRequest, PrincipalException.class.getName());

			return mapping.findForward("portlet.portlet_configuration.error");
		}

		renderResponse.setTitle(getTitle(portlet, renderRequest));

		return mapping.findForward(getForward(
			renderRequest, "portlet.portlet_configuration.edit_scope"));
	}

	protected String getPortletTitle(
		PortletRequest portletRequest, Portlet portlet,
		PortletPreferences preferences) {

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String portletTitle = PortletConfigurationUtil.getPortletTitle(
			preferences, themeDisplay.getLanguageId());

		if (Validator.isNull(portletTitle)) {
			ServletContext servletContext =
				(ServletContext)portletRequest.getAttribute(WebKeys.CTX);

			PortletConfig portletConfig = PortletConfigFactoryUtil.create(
				portlet, servletContext);

			ResourceBundle resourceBundle = portletConfig.getResourceBundle(
				themeDisplay.getLocale());

			portletTitle = resourceBundle.getString(
				JavaConstants.JAVAX_PORTLET_TITLE);
		}

		return portletTitle;
	}

	protected void updateScope(ActionRequest actionRequest, Portlet portlet)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		PortletPreferences preferences =
			PortletPreferencesFactoryUtil.getLayoutPortletSetup(
				layout, portlet.getPortletId());

		String scopeType = ParamUtil.getString(actionRequest, "scopeType");
		String oldScopeType =
			GetterUtil.getString(preferences.getValue("lfr-scope-type", null));
		String scopeLayoutUuid = StringPool.BLANK;
		String title = getPortletTitle(actionRequest, portlet, preferences);
		String newTitle = title;

		// Remove old scope suffix from the title if present

		if (Validator.isNotNull(oldScopeType)) {
			String scopeName = null;

			if (oldScopeType.equals(GroupConstants.GLOBAL)) {
				scopeName = themeDisplay.translate("global");
			}
			else {
				String oldScopeLayoutUuid = GetterUtil.getString(
					preferences.getValue("lfr-scope-layout-uuid", null));

				try {
					Layout oldScopeLayout =
						LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
							oldScopeLayoutUuid, layout.getGroupId());

					scopeName =
						oldScopeLayout.getName(themeDisplay.getLocale());
				}
				catch (NoSuchLayoutException nsle) {
				}
			}

			StringBundler sb = new StringBundler(4);

			sb.append(StringPool.SPACE);
			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(scopeName);
			sb.append(StringPool.CLOSE_PARENTHESIS);

			String suffix = sb.toString();

			if (newTitle.endsWith(suffix)) {
				newTitle = newTitle.substring(
					0, title.length() - suffix.length());
			}
		}

		// Add new scope suffix to the title

		if (Validator.isNotNull(scopeType)) {
			String scopeName = null;

			if (scopeType.equals(GroupConstants.GLOBAL)) {
				scopeName = themeDisplay.translate("global");
			}
			else {
				scopeLayoutUuid =
					ParamUtil.getString(actionRequest, "scopeLayoutUuid");

				Layout scopeLayout =
					LayoutLocalServiceUtil.getLayoutByUuidAndGroupId(
						scopeLayoutUuid, layout.getGroupId());

				if (!scopeLayout.hasScopeGroup()) {
					String name = String.valueOf(scopeLayout.getPlid());

					GroupLocalServiceUtil.addGroup(
						themeDisplay.getUserId(), Layout.class.getName(),
						scopeLayout.getPlid(), name, null, 0, null, true, null);
				}

				scopeName = scopeLayout.getName(themeDisplay.getLocale());
			}

			StringBundler sb = new StringBundler(5);

			sb.append(newTitle);
			sb.append(StringPool.SPACE);
			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(scopeName);
			sb.append(StringPool.CLOSE_PARENTHESIS);

			newTitle = sb.toString();
		}

		preferences.setValue("lfr-scope-layout-uuid", scopeLayoutUuid);
		preferences.setValue("lfr-scope-type", scopeType);

		if (!newTitle.equals(title)) {
			preferences.setValue(
				"portlet-setup-title-" + themeDisplay.getLanguageId(),
				newTitle);
			preferences.setValue("portlet-setup-use-custom-title", "true");
		}

		preferences.store();
	}

}