/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet.mail.action;

import com.liferay.mail.service.spring.MailServiceUtil;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.ParamUtil;
import com.liferay.util.StringPool;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;
import com.liferay.util.servlet.SessionMessages;

import java.rmi.RemoteException;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletException;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditPreferencesAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class EditPreferencesAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		String cmd = ParamUtil.getString(req, Constants.CMD);

		if (!cmd.equals(Constants.UPDATE)) {
			return;
		}

		PortletPreferences prefs = req.getPreferences();

		String tabs1 = ParamUtil.getString(req, "tabs1");

		if (tabs1.equals("forward-address")) {
			String forwardAddress = ParamUtil.getString(req, "forwardAddress");

			String[] forwardAddressArray = StringUtil.split(
				forwardAddress, "\n");

			List forwardAddressList = new ArrayList();

			for (int i = 0; i < forwardAddressArray.length; i++) {
				if (Validator.isEmailAddress(forwardAddressArray[i])) {
					forwardAddressList.add(forwardAddressArray[i]);
				}
			}

			if (forwardAddressList.size() > 0) {
				forwardAddressArray =
					(String[])forwardAddressList.toArray(new String[0]);

				forwardAddress = StringUtil.merge(
					forwardAddressArray, StringPool.SPACE);
			}
			else {
				forwardAddress = StringPool.BLANK;
			}

			prefs.setValue("forward-address", forwardAddress);

			res.setRenderParameter("forwardAddress", forwardAddress);

			try {
				MailServiceUtil.addForward(
					req.getRemoteUser(), forwardAddressList);
			}
			catch (SystemException se) {
				throw new PortletException(se);
			}
		}
		else if (tabs1.equals("signature")) {
			String signature = ParamUtil.getString(req, "signature");

			prefs.setValue("signature", signature);
		}
		else if (tabs1.equals("vacation-message")) {
			String vacationMessage = ParamUtil.getString(
				req, "vacationMessage");

			prefs.setValue("vacation-message", vacationMessage);

			try {
				User user = PortalUtil.getUser(req);

				MailServiceUtil.addVacationMessage(
					user.getUserId(), user.getEmailAddress(), vacationMessage);
			}
			catch (RemoteException re) {
				throw new SystemException(re);
			}
			catch (SystemException se) {
				throw new PortletException(se);
			}
		}

		prefs.store();

		SessionMessages.add(req, config.getPortletName() + ".doEdit");
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			RenderRequest req, RenderResponse res)
		throws Exception {

		return mapping.findForward("portlet.mail.edit");
	}

}