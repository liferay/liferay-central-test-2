/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.StringPool;
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

		Set<PublicRenderParameter> layoutPublicRenderParameters =
			new TreeSet<PublicRenderParameter>(
				new PublicRenderParameterIdentifierComparator());
		Set<String> prpIdendifiersInLayout = new HashSet<String>();

		LayoutTypePortlet layoutTypePortlet =
			themeDisplay.getLayoutTypePortlet();
		List<Portlet> layoutPortlets = layoutTypePortlet.getAllPortlets();

		for (Portlet portletInLayout: layoutPortlets ) {
			Set<PublicRenderParameter> publicRenderParameters =
				portletInLayout.getPublicRenderParameters();

			for (PublicRenderParameter publicRenderParameter:
				publicRenderParameters) {

				if (!prpIdendifiersInLayout.contains(
					publicRenderParameter.getIdentifier())) {

					prpIdendifiersInLayout.add(
						publicRenderParameter.getIdentifier());
					layoutPublicRenderParameters.add(publicRenderParameter);
				}
			}
		}

		renderRequest.setAttribute(
			WebKeys.LAYOUT_PUBLIC_RENDER_PARAMETERS,
			layoutPublicRenderParameters);
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

		Set<PublicRenderParameter> portletPublicRenderParameters =
			portlet.getPublicRenderParameters();

		Set<PublicRenderParameter> layoutPublicRenderParameters =
			(Set<PublicRenderParameter>)renderRequest.getAttribute(
				WebKeys.LAYOUT_PUBLIC_RENDER_PARAMETERS);

		Map<String, String> prpMappings = new HashMap<String, String>();

		if (SessionErrors.isEmpty(renderRequest)) {
			for (PublicRenderParameter layoutPrp:
				layoutPublicRenderParameters) {

				String portletPrpId = preferences.getValue(
					_MAPPING_PREFIX + layoutPrp.getIdentifier(),
					StringPool.BLANK);

				if (Validator.isNotNull(portletPrpId)) {
					prpMappings.put(portletPrpId, layoutPrp.getIdentifier());
				}
			}
		}
		else {
			for (PublicRenderParameter portletPrp:
				portletPublicRenderParameters) {

				String prpIdentifier = portletPrp.getIdentifier();

				String layoutPrpId = ParamUtil.getString(
					renderRequest, _MAPPING_PREFIX + prpIdentifier);

				prpMappings.put(portletPrp.getIdentifier(), layoutPrpId);
			}
		}

		List<PublicRenderParameterConfiguration> prpConfigurationList =
			new ArrayList<PublicRenderParameterConfiguration>();

		for (PublicRenderParameter publicRenderParameter:
				portletPublicRenderParameters) {

			String ignoreKey = _IGNORE_PREFIX +
				publicRenderParameter.getIdentifier();

			boolean ignore;

			if (SessionErrors.isEmpty(renderRequest)) {
				ignore = GetterUtil.getBoolean(preferences.getValue(
					ignoreKey, StringPool.FALSE));
			}
			else {
				ignore = GetterUtil.getBoolean(
					ParamUtil.getString(renderRequest, ignoreKey));
			}

			prpConfigurationList.add(
				new PublicRenderParameterConfiguration(
					publicRenderParameter,
					prpMappings.get(publicRenderParameter.getIdentifier()),
					ignore));
		}

		Collections.sort(
			prpConfigurationList,
			new PublicRenderParameterIdentifierConfigurationComparator());

		renderRequest.setAttribute(
			WebKeys.PUBLIC_RENDER_PARAMETER_CONFIGURATION_LIST,
			prpConfigurationList);
	}

	private static final String _IGNORE_PREFIX =
		PublicRenderParameterConfiguration.IGNORE_PREFIX;
	private static final String _MAPPING_PREFIX =
		PublicRenderParameterConfiguration.MAPPING_PREFIX;

}