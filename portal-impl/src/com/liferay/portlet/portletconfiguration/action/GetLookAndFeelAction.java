/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.language.LanguageUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.permission.PortletPermission;
import com.liferay.portal.struts.JSONAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactory;
import com.liferay.util.GetterUtil;
import com.liferay.util.LocaleUtil;
import com.liferay.util.ParamUtil;
import com.liferay.util.Validator;

import java.text.ParseException;

import java.util.Locale;

import javax.portlet.PortletPreferences;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import org.json.JSONObject;

/**
 * <a href="GetLookAndFeelAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class GetLookAndFeelAction extends JSONAction {

	public String getJSON(
			ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res)
		throws Exception {

		ThemeDisplay themeDisplay =
			(ThemeDisplay)req.getAttribute(WebKeys.THEME_DISPLAY);

		Layout layout = themeDisplay.getLayout();

		PermissionChecker permissionChecker =
			themeDisplay.getPermissionChecker();

		String portletId = ParamUtil.getString(req, "portletId");

		if (!PortletPermission.contains(
				permissionChecker, themeDisplay.getPlid(), portletId,
				ActionKeys.CONFIGURATION)) {

			return null;
		}

		PortletPreferences portletSetup =
			PortletPreferencesFactory.getPortletSetup(layout, portletId);

		String css = portletSetup.getValue(
			"portlet-setup-css", StringPool.BLANK);

		try {
			if (Validator.isNotNull(css)) {
				if (_log.isDebugEnabled()) {
					_log.debug("Getting css " + css);
				}

				JSONObject jsonObj = new JSONObject(css);

				JSONObject portletData = new JSONObject();

				jsonObj.put("portletData", portletData);

				JSONObject titles = new JSONObject();

				portletData.put("titles", titles);

				Locale[] locales = LanguageUtil.getAvailableLocales();

				for (int i = 0; i < locales.length; i++) {
					String languageId = LocaleUtil.toLanguageId(locales[i]);

					String title = portletSetup.getValue(
						"portlet-setup-title-" + languageId, null);

					if (Validator.isNotNull(languageId)) {
						titles.put(languageId, title);
					}
				}

				boolean useCustomTitle = GetterUtil.getBoolean(
					portletSetup.getValue(
						"portlet-setup-use-custom-title", null));
				boolean showBorders = GetterUtil.getBoolean(
					portletSetup.getValue("portlet-setup-show-borders", null),
					true);
				long linkToPlid = GetterUtil.getLong(
					portletSetup.getValue("portlet-setup-link-to-plid", null));

				portletData.put("useCustomTitle", useCustomTitle);
				portletData.put("showBorders", showBorders);
				portletData.put("portletLinksTarget", linkToPlid);

				return jsonObj.toString();
			}
		}
		catch (ParseException pe) {
			if (_log.isWarnEnabled()) {
				_log.warn(pe);
			}
		}

		return null;
	}

	private static Log _log = LogFactory.getLog(GetLookAndFeelAction.class);

}