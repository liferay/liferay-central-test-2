/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portlet.portletconfiguration.action;

import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.JavaConstants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletConfigFactory;
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
 * <a href="EditScopeAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jesper Weissglas
 * @author Jorge Ferrer
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

			sendRedirect(actionRequest, actionResponse);
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

			PortletConfig portletConfig = PortletConfigFactory.create(
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

		long scopeLayoutId = ParamUtil.getLong(actionRequest, "scopeLayoutId");
		long oldScopeLayoutId = GetterUtil.getLong(
			preferences.getValue("lfr-scope-layout-id", null));
		String title = getPortletTitle(actionRequest, portlet, preferences);
		String newTitle = title;

		// Remove old scope suffix from the title if present

		if (oldScopeLayoutId > 0) {
			try {
				Layout oldScopeLayout = LayoutLocalServiceUtil.getLayout(
					layout.getGroupId(), layout.isPrivateLayout(),
					oldScopeLayoutId);

				StringBundler sb = new StringBundler(4);

				sb.append(StringPool.SPACE);
				sb.append(StringPool.OPEN_PARENTHESIS);
				sb.append(oldScopeLayout.getName(themeDisplay.getLocale()));
				sb.append(StringPool.CLOSE_PARENTHESIS);

				String suffix = sb.toString();

				if (newTitle.endsWith(suffix)) {
					newTitle = newTitle.substring(
						0, title.length() - suffix.length());
				}
			}
			catch (NoSuchLayoutException nsle) {
			}
		}

		// Add new scope suffix to the title

		if (scopeLayoutId > 0) {
			Layout scopeLayout = LayoutLocalServiceUtil.getLayout(
				layout.getGroupId(), layout.isPrivateLayout(), scopeLayoutId);

			if (!scopeLayout.hasScopeGroup()) {
				String name = String.valueOf(scopeLayout.getPlid());

				GroupLocalServiceUtil.addGroup(
					themeDisplay.getUserId(), Layout.class.getName(),
					scopeLayout.getPlid(), name, null, 0, null, true, null);
			}

			StringBundler sb = new StringBundler(5);

			sb.append(newTitle);
			sb.append(StringPool.SPACE);
			sb.append(StringPool.OPEN_PARENTHESIS);
			sb.append(scopeLayout.getName(themeDisplay.getLocale()));
			sb.append(StringPool.CLOSE_PARENTHESIS);

			newTitle = sb.toString();
		}

		preferences.setValue(
			"lfr-scope-layout-id", String.valueOf(scopeLayoutId));

		if (!newTitle.equals(title)) {
			preferences.setValue(
				"portlet-setup-title-" + themeDisplay.getLanguageId(),
				newTitle);
			preferences.setValue("portlet-setup-use-custom-title", "true");
		}

		preferences.store();
	}

}