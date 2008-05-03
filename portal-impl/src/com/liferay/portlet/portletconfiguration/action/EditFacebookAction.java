/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.util.servlet.SessionErrors;
import com.liferay.util.servlet.SessionMessages;

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
 * <a href="EditFacebookAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class EditFacebookAction extends EditConfigurationAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		Portlet portlet = null;

		try {
			portlet = getPortlet(req);
		}
		catch (PrincipalException pe) {
			SessionErrors.add(req, PrincipalException.class.getName());

			setForward(req, "portlet.portlet_configuration.error");
		}

		String portletResource = ParamUtil.getString(req, "portletResource");

		String facebookAppName = ParamUtil.getString(req, "facebookAppName");
		String facebookAPIKey = ParamUtil.getString(req, "facebookAPIKey");
		boolean facebookShowAddAppLink = ParamUtil.getBoolean(
			req, "facebookShowAddAppLink");

		PortletPreferences prefs =
			PortletPreferencesFactoryUtil.getPortletSetup(
				req, portlet.getPortletId());

		prefs.setValue("lfr-facebook-app-name", facebookAppName);
		prefs.setValue("lfr-facebook-api-key", facebookAPIKey);
		prefs.setValue(
			"lfr-facebook-show-add-app-link",
			String.valueOf(facebookShowAddAppLink));

		prefs.store();

		SessionMessages.add(req, config.getPortletName() + ".doConfigure");
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			RenderRequest req, RenderResponse res)
		throws Exception {

		Portlet portlet = null;

		try {
			portlet = getPortlet(req);
		}
		catch (PrincipalException pe) {
			SessionErrors.add(req, PrincipalException.class.getName());

			return mapping.findForward("portlet.portlet_configuration.error");
		}

		res.setTitle(getTitle(portlet, req));

		return mapping.findForward(
			getForward(req, "portlet.portlet_configuration.edit_facebook"));
	}

}