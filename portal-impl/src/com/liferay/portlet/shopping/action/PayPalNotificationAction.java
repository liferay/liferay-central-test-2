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

package com.liferay.portlet.shopping.action;

import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.shopping.model.ShoppingOrder;
import com.liferay.portlet.shopping.service.ShoppingOrderLocalServiceUtil;
import com.liferay.portlet.shopping.util.ShoppingPreferences;
import com.liferay.portlet.shopping.util.ShoppingUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import java.net.URL;
import java.net.URLConnection;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="PayPalNotificationAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PayPalNotificationAction extends Action {

	public ActionForward execute(
			ActionMapping mapping, ActionForm form, HttpServletRequest req,
			HttpServletResponse res)
		throws Exception {

		String invoice = null;

		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Receiving notification from PayPal");
			}

			String query = "cmd=_notify-validate";

			Enumeration<String> enu = req.getParameterNames();

			while (enu.hasMoreElements()) {
				String name = enu.nextElement();

				String value = req.getParameter(name);

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

			BufferedReader br = new BufferedReader(
				new InputStreamReader(urlc.getInputStream()));

			String payPalStatus = br.readLine();

			br.close();

			String itemName = ParamUtil.getString(req, "item_name");
			String itemNumber = ParamUtil.getString(req, "item_number");
			invoice = ParamUtil.getString(req, "invoice");
			String txnId = ParamUtil.getString(req, "txn_id");
			String paymentStatus = ParamUtil.getString(req, "payment_status");
			double paymentGross = ParamUtil.getDouble(req, "mc_gross");
			String receiverEmail = ParamUtil.getString(req, "receiver_email");
			String payerEmail = ParamUtil.getString(req, "payer_email");

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

			if (payPalStatus.equals("VERIFIED") && validate(req)) {
				ShoppingOrderLocalServiceUtil.completeOrder(
					invoice, txnId, paymentStatus, paymentGross, receiverEmail,
					payerEmail, true);
			}
			else if (payPalStatus.equals("INVALID")) {
			}

			return null;
		}
		catch (Exception e) {
			PortalUtil.sendError(e, req, res);

			return null;
		}
	}

	protected boolean validate(HttpServletRequest req) {
		try {
			String ppInvoice = ParamUtil.getString(req, "invoice");

			ShoppingOrder order = ShoppingOrderLocalServiceUtil.getOrder(
				ppInvoice);

			ShoppingPreferences shoppingPrefs = ShoppingPreferences.getInstance(
				order.getCompanyId(), order.getGroupId());

			// Receiver Email

			String ppReceiverEmail = ParamUtil.getString(
				req, "receiver_email");

			String payPalEmailAddress = shoppingPrefs.getPayPalEmailAddress();

			if (!payPalEmailAddress.equals(ppReceiverEmail)) {
				return false;
			}

			// Payment Gross

			double ppGross = ParamUtil.getDouble(req, "mc_gross");

			double orderTotal = ShoppingUtil.calculateTotal(order);

			if (orderTotal != ppGross) {
				return false;
			}

			// Payment Currency

			String ppCurrency = ParamUtil.getString(req, "mc_currency");

			String currencyId = shoppingPrefs.getCurrencyId();

			if (!currencyId.equals(ppCurrency)) {
				return false;
			}

			// Transaction ID

			String ppTxnId = ParamUtil.getString(req, "txn_id");

			int ppTxnIdCount =
				ShoppingOrderLocalServiceUtil.getPayPalTxnIdOrderCount(
					ppTxnId);

			if (ppTxnIdCount > 0) {
				return false;
			}
		}
		catch (Exception e) {
			return false;
		}

		return true;
	}

	private static Log _log = LogFactory.getLog(PayPalNotificationAction.class);

}