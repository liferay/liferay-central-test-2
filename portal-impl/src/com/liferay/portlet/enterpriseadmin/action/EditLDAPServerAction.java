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

package com.liferay.portlet.enterpriseadmin.action;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PropertiesParamUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.ldap.PortalLDAPUtil;
import com.liferay.portal.service.CompanyServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.WebKeys;

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
 * <a href="EditLDAPServerAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Ryan Park
 */
public class EditLDAPServerAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateLDAPServer(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteLDAPServer(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof PrincipalException) {
				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.enterprise_admin.error");
			}
			else {
				throw e;
			}
		}
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		return mapping.findForward(getForward(
			renderRequest, "portlet.enterprise_admin.edit_ldap_server"));
	}

	protected UnicodeProperties addLDAPServer(
			long companyId, UnicodeProperties properties)
		throws Exception {

		long ldapServerId = CounterLocalServiceUtil.increment();

		String postfix = PortalLDAPUtil.getPropertyPostfix(ldapServerId);

		String[] keys = properties.keySet().toArray(new String[0]);

		for (String key : keys) {
			if (ArrayUtil.contains(_LDAP_SERVER_PREF_KEYS, key)) {
				String value = properties.remove(key);

				properties.setProperty(key + postfix, value);
			}
		}

		PortletPreferences preferences = PrefsPropsUtil.getPreferences(
			companyId);

		String ldapServerIds = preferences.getValue(
			"ldap.server.ids", StringPool.BLANK);

		ldapServerIds = StringUtil.add(
			ldapServerIds, String.valueOf(ldapServerId));

		properties.setProperty("ldap.server.ids", ldapServerIds);

		return properties;
	}

	protected void deleteLDAPServer(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long ldapServerId = ParamUtil.getLong(actionRequest, "ldapServerId");

		// Remove preferences

		String postfix = PortalLDAPUtil.getPropertyPostfix(ldapServerId);

		String[] keys = new String[_LDAP_SERVER_PREF_KEYS.length];

		for (int i = 0; i < _LDAP_SERVER_PREF_KEYS.length; i++) {
			keys[i] = _LDAP_SERVER_PREF_KEYS[i] + postfix;
		}

		CompanyServiceUtil.removePreferences(
			themeDisplay.getCompanyId(), keys);

		// Update preferences

		PortletPreferences preferences = PrefsPropsUtil.getPreferences(
			themeDisplay.getCompanyId());

		UnicodeProperties properties = new UnicodeProperties();

		String ldapServerIds = preferences.getValue(
			"ldap.server.ids", StringPool.BLANK);

		ldapServerIds = StringUtil.remove(
			ldapServerIds, String.valueOf(ldapServerId));

		properties.put("ldap.server.ids", ldapServerIds);

		CompanyServiceUtil.updatePreferences(
			themeDisplay.getCompanyId(), properties);
	}

	protected void updateLDAPServer(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long ldapServerId = ParamUtil.getLong(actionRequest, "ldapServerId");

		UnicodeProperties properties = PropertiesParamUtil.getProperties(
			actionRequest, "settings(");

		if (ldapServerId <= 0) {
			properties = addLDAPServer(
				themeDisplay.getCompanyId(), properties);
		}

		CompanyServiceUtil.updatePreferences(
			themeDisplay.getCompanyId(), properties);
	}

	private final String[] _LDAP_SERVER_PREF_KEYS = {
		"ldap.server.name",
		PropsKeys.LDAP_BASE_PROVIDER_URL,
		PropsKeys.LDAP_BASE_DN,
		PropsKeys.LDAP_SECURITY_PRINCIPAL,
		PropsKeys.LDAP_SECURITY_CREDENTIALS,
		PropsKeys.LDAP_AUTH_SEARCH_FILTER,
		PropsKeys.LDAP_IMPORT_USER_SEARCH_FILTER,
		PropsKeys.LDAP_IMPORT_GROUP_SEARCH_FILTER,
		PropsKeys.LDAP_USERS_DN,
		PropsKeys.LDAP_USER_DEFAULT_OBJECT_CLASSES,
		PropsKeys.LDAP_GROUPS_DN,
		PropsKeys.LDAP_USER_MAPPINGS,
		PropsKeys.LDAP_GROUP_MAPPINGS
	};

}