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

package com.liferay.portlet.shopping.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.shopping.NoSuchOrderException;
import com.liferay.portlet.shopping.service.ShoppingOrderServiceUtil;
import com.liferay.portlet.shopping.util.ShoppingUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditOrderAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class EditOrderAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateOrder(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteOrders(actionRequest);
			}
			else if (cmd.equals("sendEmail")) {
				sendEmail(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse);
		}
		catch (Exception e) {
			if (e instanceof NoSuchOrderException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.shopping.error");
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
			ActionUtil.getOrder(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchOrderException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.shopping.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(renderRequest, "portlet.shopping.edit_order"));
	}

	protected void deleteOrders(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long[] deleteOrderIds = StringUtil.split(
			ParamUtil.getString(actionRequest, "deleteOrderIds"), 0L);

		for (int i = 0; i < deleteOrderIds.length; i++) {
			ShoppingOrderServiceUtil.deleteOrder(
				themeDisplay.getScopeGroupId(), deleteOrderIds[i]);
		}
	}

	protected void sendEmail(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long orderId = ParamUtil.getLong(actionRequest, "orderId");

		String emailType = ParamUtil.getString(actionRequest, "emailType");

		ShoppingOrderServiceUtil.sendEmail(
			themeDisplay.getScopeGroupId(), orderId, emailType);
	}

	protected void updateOrder(ActionRequest actionRequest) throws Exception {
		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String number = ParamUtil.getString(actionRequest, "number");
		String ppTxnId = ParamUtil.getString(actionRequest, "ppTxnId");
		String ppPaymentStatus = ShoppingUtil.getPpPaymentStatus(
			ParamUtil.getString(actionRequest, "ppPaymentStatus"));
		double ppPaymentGross = ParamUtil.getDouble(
			actionRequest, "ppPaymentGross");
		String ppReceiverEmail = ParamUtil.getString(
			actionRequest, "ppReceiverEmail");
		String ppPayerEmail = ParamUtil.getString(
			actionRequest, "ppPayerEmail");

		ShoppingOrderServiceUtil.completeOrder(
			themeDisplay.getScopeGroupId(), number, ppTxnId, ppPaymentStatus,
			ppPaymentGross, ppReceiverEmail, ppPayerEmail);
	}

}