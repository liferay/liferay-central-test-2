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

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutTypePortlet;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PublicRenderParameter;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.portlet.portletconfiguration.util.PublicRenderParameterConfiguration;
import com.liferay.portlet.portletconfiguration.util.PublicRenderParameterIdentifierComparator;
import com.liferay.portlet.portletconfiguration.util.PublicRenderParameterIdentifierConfigurationComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;

/**
 * <a href="ActionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 */
public class ActionUtil {

	public static void getLayoutPublicRenderParameters(
			RenderRequest renderRequest)
		throws SystemException {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Set<String> identifiers = new HashSet<String>();

		Set<PublicRenderParameter> publicRenderParameters =
			new TreeSet<PublicRenderParameter>(
				new PublicRenderParameterIdentifierComparator());

		LayoutTypePortlet layoutTypePortlet =
			themeDisplay.getLayoutTypePortlet();

		List<Portlet> portlets = layoutTypePortlet.getAllPortlets();

		for (Portlet portlet : portlets) {
			for (PublicRenderParameter publicRenderParameter:
					portlet.getPublicRenderParameters()) {

				if (!identifiers.contains(
						publicRenderParameter.getIdentifier())) {

					identifiers.add(publicRenderParameter.getIdentifier());

					publicRenderParameters.add(publicRenderParameter);
				}
			}
		}

		renderRequest.setAttribute(
			WebKeys.PUBLIC_RENDER_PARAMETERS, publicRenderParameters);
	}

	public static void getPublicRenderParameterConfigurationList(
			RenderRequest renderRequest, Portlet portlet)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		PortletPreferences preferences =
			PortletPreferencesFactoryUtil.getLayoutPortletSetup(
				layout, portlet.getPortletId());

		Map<String, String> mappings = new HashMap<String, String>();

		if (SessionErrors.isEmpty(renderRequest)) {
			Set<PublicRenderParameter> publicRenderParameters =
				(Set<PublicRenderParameter>)renderRequest.getAttribute(
					WebKeys.PUBLIC_RENDER_PARAMETERS);

			for (PublicRenderParameter publicRenderParameter :
					publicRenderParameters) {

				String mapping = preferences.getValue(
					_MAPPING_PREFIX + publicRenderParameter.getIdentifier(),
					null);

				if (Validator.isNotNull(mapping)) {
					mappings.put(
						mapping, publicRenderParameter.getIdentifier());
				}
			}
		}
		else {
			for (PublicRenderParameter publicRenderParameter :
					portlet.getPublicRenderParameters()) {

				String mapping = ParamUtil.getString(
					renderRequest,
					_MAPPING_PREFIX + publicRenderParameter.getIdentifier());

				mappings.put(publicRenderParameter.getIdentifier(), mapping);
			}
		}

		List<PublicRenderParameterConfiguration>
			publicRenderParameterConfigurations =
				new ArrayList<PublicRenderParameterConfiguration>();

		for (PublicRenderParameter publicRenderParameter:
				portlet.getPublicRenderParameters()) {

			boolean ignore = false;

			String ignoreKey =
				_IGNORE_PREFIX + publicRenderParameter.getIdentifier();

			if (SessionErrors.isEmpty(renderRequest)) {
				ignore = GetterUtil.getBoolean(
					preferences.getValue(ignoreKey, null));
			}
			else {
				ignore = GetterUtil.getBoolean(
					ParamUtil.getString(renderRequest, ignoreKey));
			}

			publicRenderParameterConfigurations.add(
				new PublicRenderParameterConfiguration(
					publicRenderParameter,
					mappings.get(publicRenderParameter.getIdentifier()),
					ignore));
		}

		Collections.sort(
			publicRenderParameterConfigurations,
			new PublicRenderParameterIdentifierConfigurationComparator());

		renderRequest.setAttribute(
			WebKeys.PUBLIC_RENDER_PARAMETER_CONFIGURATIONS,
			publicRenderParameterConfigurations);
	}

	private static final String _IGNORE_PREFIX =
		PublicRenderParameterConfiguration.IGNORE_PREFIX;

	private static final String _MAPPING_PREFIX =
		PublicRenderParameterConfiguration.MAPPING_PREFIX;

}