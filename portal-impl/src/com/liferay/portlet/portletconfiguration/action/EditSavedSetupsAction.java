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

import com.liferay.portal.NoSuchPortletItemException;
import com.liferay.portal.PortletItemNameException;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.PortletPreferencesServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.PortletPreferencesFactoryUtil;
import com.liferay.util.servlet.SessionErrors;

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
 * <a href="EditSavedSetupsAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class EditSavedSetupsAction extends EditConfigurationAction {

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

		String cmd = ParamUtil.getString(req, Constants.CMD);

		try {
			if (cmd.equals(Constants.SAVE)) {
				save(req, portlet);

				sendRedirect(req, res);
			}
			else if (cmd.equals(Constants.RESTORE)) {
				restore(req, portlet);

				sendRedirect(req, res);
			}
			else if (cmd.equals(Constants.DELETE)) {
				delete(req);

				sendRedirect(req, res);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchPortletItemException ||
				e instanceof PortletItemNameException) {

				SessionErrors.add(req, e.getClass().getName());

				setForward(
					req, "portlet.portlet_configuration.edit_saved_setups");
			}
			else if (e instanceof PrincipalException) {

				SessionErrors.add(req, e.getClass().getName());

				setForward(req, "portlet.portlet_configuration.error");
			}
			else {
				throw e;
			}
		}
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

		return mapping.findForward(getForward(
			req, "portlet.portlet_configuration.edit_saved_setups"));
	}

	private void delete(ActionRequest req)
		throws Exception {

		long portletItemId = ParamUtil.getLong(req, "portletItemId");

		PortletPreferencesServiceUtil.deleteSavedPreferences(portletItemId);
	}

	private void restore(
		ActionRequest req, Portlet portlet)
		throws Exception {

		Layout layout = (Layout)req.getAttribute(WebKeys.LAYOUT);

		String name = ParamUtil.getString(req, "name");

		PortletPreferences setup =
			PortletPreferencesFactoryUtil.getPortletSetup(
				req, portlet.getPortletId());

		PortletPreferencesServiceUtil.restoreSavedPreferences(
			layout.getGroupId(), portlet.getRootPortletId(), name, setup);
	}

	protected void save(ActionRequest req, Portlet portlet)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)req.getAttribute(
			WebKeys.THEME_DISPLAY);

		String name = ParamUtil.getString(req, "name");

		if (Validator.isNull(name)) {
			throw new PortletItemNameException();
		}

		PortletPreferences setup =
			PortletPreferencesFactoryUtil.getPortletSetup(
				req, portlet.getPortletId());

		PortletPreferencesServiceUtil.savePreferences(
			themeDisplay.getUserId(), themeDisplay.getLayout().getGroupId(),
		    portlet.getRootPortletId(), name, setup);
	}

}