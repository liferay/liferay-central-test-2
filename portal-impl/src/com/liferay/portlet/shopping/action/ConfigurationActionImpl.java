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

import com.liferay.portal.kernel.portlet.ConfigurationAction;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.servlet.SessionMessages;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.shopping.util.ShoppingPreferences;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * <a href="ConfigurationActionImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ConfigurationActionImpl implements ConfigurationAction {

	public void processAction(
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		if (!cmd.equals(Constants.UPDATE)) {
			return;
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		ShoppingPreferences prefs = ShoppingPreferences.getInstance(
			themeDisplay.getCompanyId(), themeDisplay.getPortletGroupId());

		String tabs2 = ParamUtil.getString(actionRequest, "tabs2");
		String tabs3 = ParamUtil.getString(actionRequest, "tabs3");

		if (tabs2.equals("payment-settings")) {
			updatePayment(actionRequest, prefs);
		}
		else if (tabs2.equals("shipping-calculation")) {
			updateShippingCalculation(actionRequest, prefs);
		}
		else if (tabs2.equals("insurance-calculation")) {
			updateInsuranceCalculation(actionRequest, prefs);
		}
		else if (tabs2.equals("emails")) {
			if (tabs3.equals("email-from")) {
				updateEmailFrom(actionRequest, prefs);
			}
			else if (tabs3.equals("confirmation-email")) {
				updateEmailOrderConfirmation(actionRequest, prefs);
			}
			else if (tabs3.equals("shipping-email")) {
				updateEmailOrderShipping(actionRequest, prefs);
			}
		}

		if (SessionErrors.isEmpty(actionRequest)) {
			prefs.store();

			SessionMessages.add(
				actionRequest, portletConfig.getPortletName() + ".doConfigure");
		}
	}

	public String render(
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		return "/html/portlet/shopping/configuration.jsp";
	}

	protected void updateEmailFrom(
			ActionRequest actionRequest, ShoppingPreferences prefs)
		throws Exception {

		String emailFromName = ParamUtil.getString(
			actionRequest, "emailFromName");
		String emailFromAddress = ParamUtil.getString(
			actionRequest, "emailFromAddress");

		if (Validator.isNull(emailFromName)) {
			SessionErrors.add(actionRequest, "emailFromName");
		}
		else if (!Validator.isEmailAddress(emailFromAddress)) {
			SessionErrors.add(actionRequest, "emailFromAddress");
		}
		else {
			prefs.setEmailFromName(emailFromName);
			prefs.setEmailFromAddress(emailFromAddress);
		}
	}

	protected void updateEmailOrderConfirmation(
			ActionRequest actionRequest, ShoppingPreferences prefs)
		throws Exception {

		boolean emailOrderConfirmationEnabled = ParamUtil.getBoolean(
			actionRequest, "emailOrderConfirmationEnabled");
		String emailOrderConfirmationSubject = ParamUtil.getString(
			actionRequest, "emailOrderConfirmationSubject");
		String emailOrderConfirmationBody = ParamUtil.getString(
			actionRequest, "emailOrderConfirmationBody");

		if (Validator.isNull(emailOrderConfirmationSubject)) {
			SessionErrors.add(actionRequest, "emailOrderConfirmationSubject");
		}
		else if (Validator.isNull(emailOrderConfirmationBody)) {
			SessionErrors.add(actionRequest, "emailOrderConfirmationBody");
		}
		else {
			prefs.setEmailOrderConfirmationEnabled(
				emailOrderConfirmationEnabled);
			prefs.setEmailOrderConfirmationSubject(
				emailOrderConfirmationSubject);
			prefs.setEmailOrderConfirmationBody(emailOrderConfirmationBody);
		}
	}

	protected void updateEmailOrderShipping(
			ActionRequest actionRequest, ShoppingPreferences prefs)
		throws Exception {

		boolean emailOrderShippingEnabled = ParamUtil.getBoolean(
			actionRequest, "emailOrderShippingEnabled");
		String emailOrderShippingSubject = ParamUtil.getString(
			actionRequest, "emailOrderShippingSubject");
		String emailOrderShippingBody = ParamUtil.getString(
			actionRequest, "emailOrderShippingBody");

		if (Validator.isNull(emailOrderShippingSubject)) {
			SessionErrors.add(actionRequest, "emailOrderShippingSubject");
		}
		else if (Validator.isNull(emailOrderShippingBody)) {
			SessionErrors.add(actionRequest, "emailOrderShippingBody");
		}
		else {
			prefs.setEmailOrderShippingEnabled(emailOrderShippingEnabled);
			prefs.setEmailOrderShippingSubject(emailOrderShippingSubject);
			prefs.setEmailOrderShippingBody(emailOrderShippingBody);
		}
	}

	protected void updateInsuranceCalculation(
			ActionRequest actionRequest, ShoppingPreferences prefs)
		throws Exception {

		String insuranceFormula = ParamUtil.getString(
			actionRequest, "insuranceFormula");

		String[] insurance = new String[5];

		for (int i = 0; i < insurance.length; i++) {
			insurance[i] = String.valueOf(
				ParamUtil.getDouble(actionRequest, "insurance" + i));
		}

		prefs.setInsuranceFormula(insuranceFormula);
		prefs.setInsurance(insurance);
	}

	protected void updatePayment(
			ActionRequest actionRequest, ShoppingPreferences prefs)
		throws Exception {

		String payPalEmailAddress = ParamUtil.getString(
			actionRequest, "payPalEmailAddress");
		String[] ccTypes = StringUtil.split(
			ParamUtil.getString(actionRequest, "ccTypes"));
		String currencyId = ParamUtil.getString(actionRequest, "currencyId");
		String taxState = ParamUtil.getString(actionRequest, "taxState");
		double taxRate = ParamUtil.getDouble(actionRequest, "taxRate") / 100;
		double minOrder = ParamUtil.getDouble(actionRequest, "minOrder");

		prefs.setPayPalEmailAddress(payPalEmailAddress);
		prefs.setCcTypes(ccTypes);
		prefs.setCurrencyId(currencyId);
		prefs.setTaxState(taxState);
		prefs.setTaxRate(taxRate);
		prefs.setMinOrder(minOrder);
	}

	protected void updateShippingCalculation(
			ActionRequest actionRequest, ShoppingPreferences prefs)
		throws Exception {

		String shippingFormula = ParamUtil.getString(
			actionRequest, "shippingFormula");

		String[] shipping = new String[5];

		for (int i = 0; i < shipping.length; i++) {
			shipping[i] = String.valueOf(
				ParamUtil.getDouble(actionRequest, "shipping" + i));
		}

		prefs.setShippingFormula(shippingFormula);
		prefs.setShipping(shipping);
	}

}