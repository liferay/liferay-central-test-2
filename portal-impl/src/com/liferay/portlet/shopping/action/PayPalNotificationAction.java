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

package com.liferay.portlet.shopping.action;

import com.liferay.portal.kernel.io.unsync.UnsyncBufferedReader;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.shopping.NoSuchOrderException;
import com.liferay.portlet.shopping.model.ShoppingOrder;
import com.liferay.portlet.shopping.service.ShoppingOrderLocalServiceUtil;
import com.liferay.portlet.shopping.util.ShoppingPreferences;
import com.liferay.portlet.shopping.util.ShoppingUtil;

import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.URL;
import java.net.URLConnection;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="PayPalNotificationAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class PayPalNotificationAction extends Action {

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest request,
			HttpServletResponse response)
		throws Exception {

		String invoice = null;

		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Receiving notification from PayPal");
			}

			String query = "cmd=_notify-validate";

			Enumeration<String> enu = request.getParameterNames();

			while (enu.hasMoreElements()) {
				String name = enu.nextElement();

				String value = request.getParameter(name);

				query = query + "&" + name + "=" + HttpUtil.encodeURL(value);
			}

			if (_log.isDebugEnabled()) {
				_log.debug("Sending response to PayPal " + query);
			}

			URL url = new URL("https://www.paypal.com/cgi-bin/webscr");

			URLConnection urlc = url.openConnection();

			urlc.setDoOutput(true);
			urlc.setRequestProperty(
				"Content-Type","application/x-www-form-urlencoded");

			PrintWriter pw = new PrintWriter(urlc.getOutputStream());

			pw.println(query);

			pw.close();

			UnsyncBufferedReader br = new UnsyncBufferedReader(
				new InputStreamReader(urlc.getInputStream()));

			String payPalStatus = br.readLine();

			br.close();

			String itemName = ParamUtil.getString(request, "item_name");
			String itemNumber = ParamUtil.getString(request, "item_number");
			invoice = ParamUtil.getString(request, "invoice");
			String txnId = ParamUtil.getString(request, "txn_id");
			String paymentStatus = ParamUtil.getString(
				request, "payment_status");
			double paymentGross = ParamUtil.getDouble(request, "mc_gross");
			String receiverEmail = ParamUtil.getString(
				request, "receiver_email");
			String payerEmail = ParamUtil.getString(request, "payer_email");

			if (_log.isDebugEnabled()) {
				_log.debug("Receiving response from PayPal");
				_log.debug("Item name " + itemName);
				_log.debug("Item number " + itemNumber);
				_log.debug("Invoice " + invoice);
				_log.debug("Transaction ID " + txnId);
				_log.debug("Payment status " + paymentStatus);
				_log.debug("Payment gross " + paymentGross);
				_log.debug("Receiver email " + receiverEmail);
				_log.debug("Payer email " + payerEmail);
			}

			if (payPalStatus.equals("VERIFIED") && validate(request)) {
				ShoppingOrderLocalServiceUtil.completeOrder(
					invoice, txnId, paymentStatus, paymentGross, receiverEmail,
					payerEmail, true);
			}
			else if (payPalStatus.equals("INVALID")) {
			}

			return null;
		}
		catch (Exception e) {
			PortalUtil.sendError(e, request, response);

			return null;
		}
	}

	protected boolean validate(HttpServletRequest request) throws Exception {

		// Invoice

		String ppInvoice = ParamUtil.getString(request, "invoice");

		ShoppingOrder order = ShoppingOrderLocalServiceUtil.getOrder(
			ppInvoice);

		ShoppingPreferences shoppingPrefs = ShoppingPreferences.getInstance(
			order.getCompanyId(), order.getGroupId());

		// Receiver email address

		String ppReceiverEmail = ParamUtil.getString(
			request, "receiver_email");

		String payPalEmailAddress = shoppingPrefs.getPayPalEmailAddress();

		if (!payPalEmailAddress.equals(ppReceiverEmail)) {
			return false;
		}

		// Payment gross

		double ppGross = ParamUtil.getDouble(request, "mc_gross");

		double orderTotal = ShoppingUtil.calculateTotal(order);

		if (orderTotal != ppGross) {
			return false;
		}

		// Payment currency

		String ppCurrency = ParamUtil.getString(request, "mc_currency");

		String currencyId = shoppingPrefs.getCurrencyId();

		if (!currencyId.equals(ppCurrency)) {
			return false;
		}

		// Transaction ID

		String ppTxnId = ParamUtil.getString(request, "txn_id");

		try {
			ShoppingOrderLocalServiceUtil.getPayPalTxnIdOrder(ppTxnId);

			return false;
		}
		catch (NoSuchOrderException nsoe) {
		}

		return true;
	}

	private static Log _log =
		LogFactoryUtil.getLog(PayPalNotificationAction.class);

}