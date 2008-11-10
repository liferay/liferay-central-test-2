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

package com.liferay.portlet.enterpriseadmin.action;

import com.liferay.portal.EmailAddressException;
import com.liferay.portal.NoSuchEmailAddressException;
import com.liferay.portal.NoSuchListTypeException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.EmailAddressServiceUtil;
import com.liferay.portal.struts.PortletAction;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditEmailAddressAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 *
 */
public class EditEmailAddressAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateEmailAddress(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteEmailAddress(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof NoSuchEmailAddressException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.enterprise_admin.error");
			}
			else if (e instanceof EmailAddressException ||
					 e instanceof NoSuchListTypeException) {

				SessionErrors.add(actionRequest, e.getClass().getName());
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

		try {
			ActionUtil.getEmailAddress(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchEmailAddressException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.enterprise_admin.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(getForward(
			renderRequest, "portlet.enterprise_admin.edit_email_address"));
	}

	protected void deleteEmailAddress(ActionRequest actionRequest)
		throws Exception {

		long emailAddressId = ParamUtil.getLong(
			actionRequest, "emailAddressId");

		EmailAddressServiceUtil.deleteEmailAddress(emailAddressId);
	}

	protected void updateEmailAddress(ActionRequest actionRequest)
		throws Exception {

		long emailAddressId = ParamUtil.getLong(
			actionRequest, "emailAddressId");

		String className = ParamUtil.getString(actionRequest, "className");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");

		String address = ParamUtil.getString(actionRequest, "address");
		int typeId = ParamUtil.getInteger(actionRequest, "typeId");
		boolean primary = ParamUtil.getBoolean(actionRequest, "primary");

		if (emailAddressId <= 0) {

			// Add email address

			EmailAddressServiceUtil.addEmailAddress(
				className, classPK, address, typeId, primary);
		}
		else {

			// Update email address

			EmailAddressServiceUtil.updateEmailAddress(
				emailAddressId, address, typeId, primary);
		}
	}

}