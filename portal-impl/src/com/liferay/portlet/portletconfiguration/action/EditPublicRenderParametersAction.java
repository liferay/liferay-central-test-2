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

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PublicRenderParameter;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.portletconfiguration.util.PublicRenderParameterConfiguration;

import java.util.Enumeration;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditPublicRenderParametersAction.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Alberto Montero
 */
public class EditPublicRenderParametersAction extends EditConfigurationAction {

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

		updatePreferences(actionRequest, portlet);

		if (SessionErrors.isEmpty(actionRequest)) {
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

		ActionUtil.getLayoutPublicRenderParameters(renderRequest);

		ActionUtil.getPublicRenderParameterConfigurationList(
			renderRequest, portlet);

		renderResponse.setTitle(getTitle(portlet, renderRequest));

		return mapping.findForward(getForward(
			renderRequest,
			"portlet.portlet_configuration.edit_public_render_parameters"));
	}

	protected void updatePreferences(
			ActionRequest actionRequest, Portlet portlet)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		PortletPreferences preferences =
			PortletPreferencesFactoryUtil.getLayoutPortletSetup(
				layout, portlet.getPortletId());

		Enumeration<String> enu = preferences.getNames();

		while (enu.hasMoreElements()) {
			String name = enu.nextElement();

			if (name.startsWith(_IGNORE_PREFIX) ||
				name.startsWith(_MAPPING_PREFIX)) {

				preferences.reset(name);
			}
		}

		for (PublicRenderParameter publicRenderParameter :
				portlet.getPublicRenderParameters()) {

			String ignoreName =
				_IGNORE_PREFIX + publicRenderParameter.getIdentifier();

			if (Validator.isNotNull(
					ParamUtil.getString(actionRequest, ignoreName))) {

				preferences.setValue(ignoreName, StringPool.TRUE);
			}
			else {
				String mappingName =
					_MAPPING_PREFIX + publicRenderParameter.getIdentifier();

				String mappingValue = ParamUtil.getString(
					actionRequest, mappingName);

				if (Validator.isNotNull(mappingValue)) {
					String mappingKey = _MAPPING_PREFIX + mappingValue;

					if (Validator.isNull(
							preferences.getValue(mappingKey, null))) {

						preferences.setValue(
							mappingKey, publicRenderParameter.getIdentifier());
					}
					else {
						SessionErrors.add(actionRequest, "duplicateMapping");

						break;
					}
				}
			}
		}

		if (SessionErrors.isEmpty(actionRequest)) {
			preferences.store();
		}
	}

	private static final String _IGNORE_PREFIX =
		PublicRenderParameterConfiguration.IGNORE_PREFIX;

	private static final String _MAPPING_PREFIX =
		PublicRenderParameterConfiguration.MAPPING_PREFIX;

}