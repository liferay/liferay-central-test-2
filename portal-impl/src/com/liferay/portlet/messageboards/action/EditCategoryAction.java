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

package com.liferay.portlet.messageboards.action;

import com.liferay.portal.captcha.CaptchaTextException;
import com.liferay.portal.captcha.CaptchaUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.messageboards.CategoryNameException;
import com.liferay.portlet.messageboards.MailAddressException;
import com.liferay.portlet.messageboards.MailInServerNameException;
import com.liferay.portlet.messageboards.MailInUserNameException;
import com.liferay.portlet.messageboards.MailOutServerNameException;
import com.liferay.portlet.messageboards.MailOutUserNameException;
import com.liferay.portlet.messageboards.MailingListAddressException;
import com.liferay.portlet.messageboards.NoSuchCategoryException;
import com.liferay.portlet.messageboards.service.MBCategoryServiceUtil;
import com.liferay.portlet.messageboards.service.MBMailingServiceUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditCategoryAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class EditCategoryAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateCategory(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteCategory(actionRequest);
			}
			else if (cmd.equals(Constants.SUBSCRIBE)) {
				subscribeCategory(actionRequest);
			}
			else if (cmd.equals(Constants.UNSUBSCRIBE)) {
				unsubscribeCategory(actionRequest);
			}
			else if (cmd.equals(Constants.ACTIVATE)) {
				activeMailing(actionRequest);
			}
			else if (cmd.equals(Constants.DEACTIVATE)) {
				deactiveMailing(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof NoSuchCategoryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.message_boards.error");
			}
			else if (e instanceof CaptchaTextException ||
					 e instanceof CategoryNameException ||
					 e instanceof MailingListAddressException ||
					 e instanceof MailAddressException ||
					 e instanceof MailInServerNameException ||
					 e instanceof MailInUserNameException ||
					 e instanceof MailOutServerNameException ||
					 e instanceof MailOutUserNameException) {

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
			ActionUtil.getCategory(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchCategoryException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.message_boards.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(renderRequest, "portlet.message_boards.edit_category"));
	}

	protected void deleteCategory(ActionRequest actionRequest)
		throws Exception {

		long categoryId = ParamUtil.getLong(actionRequest, "categoryId");

		MBCategoryServiceUtil.deleteCategory(categoryId);
	}

	protected void subscribeCategory(ActionRequest actionRequest)
		throws Exception {

		long categoryId = ParamUtil.getLong(actionRequest, "categoryId");

		MBCategoryServiceUtil.subscribeCategory(categoryId);
	}

	protected void unsubscribeCategory(ActionRequest actionRequest)
		throws Exception {

		long categoryId = ParamUtil.getLong(actionRequest, "categoryId");

		MBCategoryServiceUtil.unsubscribeCategory(categoryId);
	}

	protected void activeMailing(ActionRequest actionRequest)
		throws Exception {

		long mailingId = ParamUtil.getLong(actionRequest, "mailingId");

		MBMailingServiceUtil.updateActive(mailingId, true);
	}

	protected void deactiveMailing(ActionRequest actionRequest)
			throws Exception {

		long mailingId = ParamUtil.getLong(actionRequest, "mailingId");

		MBMailingServiceUtil.updateActive(mailingId, false);
	}

	protected void updateCategory(ActionRequest actionRequest)
		throws Exception {

		Layout layout = (Layout)actionRequest.getAttribute(WebKeys.LAYOUT);

		long categoryId = ParamUtil.getLong(actionRequest, "categoryId");
		long parentCategoryId = ParamUtil.getLong(
			actionRequest, "parentCategoryId");
		String name = ParamUtil.getString(actionRequest, "name");
		String description = ParamUtil.getString(actionRequest, "description");

		boolean mergeWithParentCategory = ParamUtil.getBoolean(
			actionRequest, "mergeWithParentCategory");

		String[] communityPermissions = actionRequest.getParameterValues(
			"communityPermissions");
		String[] guestPermissions = actionRequest.getParameterValues(
			"guestPermissions");

		//mailing list
		String mailingListAddress = ParamUtil.getString(
			actionRequest, "mailingListAddress");
		String mailAddress = ParamUtil.getString(actionRequest, "mailAddress");

		//incoming server
		String mailInProtocol = ParamUtil.getString(
			actionRequest, "mailInProtocol");
		String mailInServerName = ParamUtil.getString(
			actionRequest, "mailInServerName");
		Boolean mailInUseSSL = Boolean.valueOf(
			ParamUtil.getBoolean(actionRequest, "mailInUseSSL"));
		Integer mailInServerPort = new Integer(
			ParamUtil.getInteger(actionRequest, "mailInServerPort"));
		String mailInUserName = ParamUtil.getString(
			actionRequest, "mailInUserName");
		String mailInPassword = ParamUtil.getString(
			actionRequest, "mailInPassword");
		Integer mailInReadInterval = ParamUtil.getInteger(
			actionRequest, "mailInReadInterval");

		//outgoing server
		Boolean mailOutConfigured = Boolean.valueOf(
			ParamUtil.getBoolean(actionRequest, "mailOutConfigured"));
		String mailOutServerName = ParamUtil.getString(
			actionRequest, "mailOutServerName");
		Boolean mailOutUseSSL = Boolean.valueOf(
			ParamUtil.getBoolean(actionRequest, "mailOutUseSSL"));
		Integer mailOutServerPort = new Integer(
			ParamUtil.getInteger(actionRequest, "mailOutServerPort"));
		String mailOutUserName = ParamUtil.getString(
			actionRequest, "mailOutUserName");
		String mailOutPassword = ParamUtil.getString(
			actionRequest, "mailOutPassword");

		if (categoryId <= 0) {
			if (PropsValues.
					CAPTCHA_CHECK_PORTLET_MESSAGE_BOARDS_EDIT_CATEGORY) {

				CaptchaUtil.check(actionRequest);
			}

			// Add category

			MBCategoryServiceUtil.addCategory(
				layout.getPlid(), parentCategoryId, name, description,
				mailingListAddress, mailAddress, mailInProtocol,
				mailInServerName, mailInUseSSL, mailInServerPort,
				mailInUserName, mailInPassword, mailInReadInterval,
				mailOutConfigured, mailOutServerName, mailOutUseSSL,
				mailOutServerPort, mailOutUserName, mailOutPassword,
				communityPermissions, guestPermissions);
		}
		else {

			// Update category

			MBCategoryServiceUtil.updateCategory(
				categoryId, parentCategoryId, name, description,
				mergeWithParentCategory, mailingListAddress, mailAddress,
				mailInProtocol, mailInServerName, mailInUseSSL,
				mailInServerPort, mailInUserName, mailInPassword,
				mailInReadInterval, mailOutConfigured, mailOutServerName,
				mailOutUseSSL, mailOutServerPort, mailOutUserName,
				mailOutPassword);
		}
	}

}