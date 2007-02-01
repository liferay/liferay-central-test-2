/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.shopping.service.http;

import com.liferay.portlet.shopping.model.ShoppingOrder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * <a href="ShoppingOrderJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingOrderJSONSerializer {
	public static JSONObject toJSONObject(ShoppingOrder model) {
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("orderId", model.getOrderId().toString());
		jsonObj.put("groupId", model.getGroupId());
		jsonObj.put("companyId", model.getCompanyId().toString());
		jsonObj.put("userId", model.getUserId().toString());
		jsonObj.put("userName", model.getUserName().toString());
		jsonObj.put("createDate", model.getCreateDate().toString());
		jsonObj.put("modifiedDate", model.getModifiedDate().toString());
		jsonObj.put("tax", model.getTax());
		jsonObj.put("shipping", model.getShipping());
		jsonObj.put("altShipping", model.getAltShipping().toString());
		jsonObj.put("requiresShipping", model.getRequiresShipping());
		jsonObj.put("insure", model.getInsure());
		jsonObj.put("insurance", model.getInsurance());
		jsonObj.put("couponIds", model.getCouponIds().toString());
		jsonObj.put("couponDiscount", model.getCouponDiscount());
		jsonObj.put("billingFirstName", model.getBillingFirstName().toString());
		jsonObj.put("billingLastName", model.getBillingLastName().toString());
		jsonObj.put("billingEmailAddress",
			model.getBillingEmailAddress().toString());
		jsonObj.put("billingCompany", model.getBillingCompany().toString());
		jsonObj.put("billingStreet", model.getBillingStreet().toString());
		jsonObj.put("billingCity", model.getBillingCity().toString());
		jsonObj.put("billingState", model.getBillingState().toString());
		jsonObj.put("billingZip", model.getBillingZip().toString());
		jsonObj.put("billingCountry", model.getBillingCountry().toString());
		jsonObj.put("billingPhone", model.getBillingPhone().toString());
		jsonObj.put("shipToBilling", model.getShipToBilling());
		jsonObj.put("shippingFirstName", model.getShippingFirstName().toString());
		jsonObj.put("shippingLastName", model.getShippingLastName().toString());
		jsonObj.put("shippingEmailAddress",
			model.getShippingEmailAddress().toString());
		jsonObj.put("shippingCompany", model.getShippingCompany().toString());
		jsonObj.put("shippingStreet", model.getShippingStreet().toString());
		jsonObj.put("shippingCity", model.getShippingCity().toString());
		jsonObj.put("shippingState", model.getShippingState().toString());
		jsonObj.put("shippingZip", model.getShippingZip().toString());
		jsonObj.put("shippingCountry", model.getShippingCountry().toString());
		jsonObj.put("shippingPhone", model.getShippingPhone().toString());
		jsonObj.put("ccName", model.getCcName().toString());
		jsonObj.put("ccType", model.getCcType().toString());
		jsonObj.put("ccNumber", model.getCcNumber().toString());
		jsonObj.put("ccExpMonth", model.getCcExpMonth());
		jsonObj.put("ccExpYear", model.getCcExpYear());
		jsonObj.put("ccVerNumber", model.getCcVerNumber().toString());
		jsonObj.put("comments", model.getComments().toString());
		jsonObj.put("ppTxnId", model.getPpTxnId().toString());
		jsonObj.put("ppPaymentStatus", model.getPpPaymentStatus().toString());
		jsonObj.put("ppPaymentGross", model.getPpPaymentGross());
		jsonObj.put("ppReceiverEmail", model.getPpReceiverEmail().toString());
		jsonObj.put("ppPayerEmail", model.getPpPayerEmail().toString());
		jsonObj.put("sendOrderEmail", model.getSendOrderEmail());
		jsonObj.put("sendShippingEmail", model.getSendShippingEmail());

		return jsonObj;
	}

	public static JSONArray toJSONArray(List models) {
		JSONArray jsonArray = new JSONArray();

		for (int i = 0; i < models.size(); i++) {
			ShoppingOrder model = (ShoppingOrder)models.get(i);
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}