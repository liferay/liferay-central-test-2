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

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.service.permission.PortletPermissionUtil;
import com.liferay.portal.struts.JSONAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.InvokerPortletImpl;
import com.liferay.portlet.PortletPreferencesFactoryUtil;

import java.util.Locale;

import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="UpdateLookAndFeelAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Wilson Man
 */
public class UpdateLookAndFeelAction extends JSONAction {

	public String getJSON(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		HttpSession session = request.getSession();

		ThemeDisplay themeDisplay = (ThemeDisplay)request.getAttribute(
			WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		String portletId = ParamUtil.getString(request, "portletId");

		if (!PortletPermissionUtil.contains(
				permissionChecker, themeDisplay.getPlid(), portletId,
				ActionKeys.CONFIGURATION)) {

			return null;
		}

		PortletPreferences portletSetup =
			PortletPreferencesFactoryUtil.getLayoutPortletSetup(
				layout, portletId);

		String css = ParamUtil.getString(request, "css");

		if (_log.isDebugEnabled()) {
			_log.debug("Updating css " + css);
		}

		JSONObject jsonObj = JSONFactoryUtil.createJSONObject(css);

		JSONObject portletData = jsonObj.getJSONObject("portletData");

		jsonObj.remove("portletData");

		css = jsonObj.toString();

		boolean useCustomTitle = portletData.getBoolean("useCustomTitle");
		boolean showBorders = portletData.getBoolean("showBorders");
		long linkToLayoutId = GetterUtil.getLong(
			portletData.getString("portletLinksTarget"));

		JSONObject titles = portletData.getJSONObject("titles");

		Locale[] locales = LanguageUtil.getAvailableLocales();

		for (int i = 0; i < locales.length; i++) {
			String languageId = LocaleUtil.toLanguageId(locales[i]);

			String title = null;

			if (titles.has(languageId)) {
				title = GetterUtil.getString(titles.getString(languageId));
			}

			if (Validator.isNotNull(title)) {
				portletSetup.setValue(
					"portlet-setup-title-" + languageId, title);
			}
			else {
				portletSetup.reset("portlet-setup-title-" + languageId);
			}
		}

		portletSetup.setValue(
			"portlet-setup-use-custom-title", String.valueOf(useCustomTitle));
		portletSetup.setValue(
			"portlet-setup-show-borders", String.valueOf(showBorders));

		if (linkToLayoutId > 0) {
			portletSetup.setValue(
				"portlet-setup-link-to-layout-id",
				String.valueOf(linkToLayoutId));
		}
		else {
			portletSetup.reset("portlet-setup-link-to-layout-id");
		}

		portletSetup.setValue("portlet-setup-css", css);

		JSONObject wapData = jsonObj.getJSONObject("wapData");

		String wapTitle = wapData.getString("title");
		String wapInitialWindowState = wapData.getString("initialWindowState");

		portletSetup.setValue("lfr-wap-title", wapTitle);
		portletSetup.setValue(
			"lfr-wap-initial-window-state", wapInitialWindowState);

		portletSetup.store();

		InvokerPortletImpl.clearResponse(
			session, layout.getPrimaryKey(), portletId,
			LanguageUtil.getLanguageId(request));

		return null;
	}

	private static Log _log =
		LogFactoryUtil.getLog(UpdateLookAndFeelAction.class);

}