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

package com.liferay.portlet.shopping.service.http;

import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.util.StringPool;

import com.liferay.portlet.shopping.model.ShoppingOrder;

import java.util.Date;
import java.util.List;

/**
 * <a href="ShoppingOrderJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by {@link ShoppingOrderServiceJSON} to translate objects.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portlet.shopping.service.http.ShoppingOrderServiceJSON
 * @generated
 */
public class ShoppingOrderJSONSerializer {
	public static JSONObject toJSONObject(ShoppingOrder model) {
		JSONObject jsonObj = JSONFactoryUtil.createJSONObject();

		jsonObj.put("orderId", model.getOrderId());
		jsonObj.put("groupId", model.getGroupId());
		jsonObj.put("companyId", model.getCompanyId());
		jsonObj.put("userId", model.getUserId());
		jsonObj.put("userName", model.getUserName());

		Date createDate = model.getCreateDate();

		String createDateJSON = StringPool.BLANK;

		if (createDate != null) {
			createDateJSON = String.valueOf(createDate.getTime());
		}

		jsonObj.put("createDate", createDateJSON);

		Date modifiedDate = model.getModifiedDate();

		String modifiedDateJSON = StringPool.BLANK;

		if (modifiedDate != null) {
			modifiedDateJSON = String.valueOf(modifiedDate.getTime());
		}

		jsonObj.put("modifiedDate", modifiedDateJSON);
		jsonObj.put("number", model.getNumber());
		jsonObj.put("tax", model.getTax());
		jsonObj.put("shipping", model.getShipping());
		jsonObj.put("altShipping", model.getAltShipping());
		jsonObj.put("requiresShipping", model.getRequiresShipping());
		jsonObj.put("insure", model.getInsure());
		jsonObj.put("insurance", model.getInsurance());
		jsonObj.put("couponCodes", model.getCouponCodes());
		jsonObj.put("couponDiscount", model.getCouponDiscount());
		jsonObj.put("billingFirstName", model.getBillingFirstName());
		jsonObj.put("billingLastName", model.getBillingLastName());
		jsonObj.put("billingEmailAddress", model.getBillingEmailAddress());
		jsonObj.put("billingCompany", model.getBillingCompany());
		jsonObj.put("billingStreet", model.getBillingStreet());
		jsonObj.put("billingCity", model.getBillingCity());
		jsonObj.put("billingState", model.getBillingState());
		jsonObj.put("billingZip", model.getBillingZip());
		jsonObj.put("billingCountry", model.getBillingCountry());
		jsonObj.put("billingPhone", model.getBillingPhone());
		jsonObj.put("shipToBilling", model.getShipToBilling());
		jsonObj.put("shippingFirstName", model.getShippingFirstName());
		jsonObj.put("shippingLastName", model.getShippingLastName());
		jsonObj.put("shippingEmailAddress", model.getShippingEmailAddress());
		jsonObj.put("shippingCompany", model.getShippingCompany());
		jsonObj.put("shippingStreet", model.getShippingStreet());
		jsonObj.put("shippingCity", model.getShippingCity());
		jsonObj.put("shippingState", model.getShippingState());
		jsonObj.put("shippingZip", model.getShippingZip());
		jsonObj.put("shippingCountry", model.getShippingCountry());
		jsonObj.put("shippingPhone", model.getShippingPhone());
		jsonObj.put("ccName", model.getCcName());
		jsonObj.put("ccType", model.getCcType());
		jsonObj.put("ccNumber", model.getCcNumber());
		jsonObj.put("ccExpMonth", model.getCcExpMonth());
		jsonObj.put("ccExpYear", model.getCcExpYear());
		jsonObj.put("ccVerNumber", model.getCcVerNumber());
		jsonObj.put("comments", model.getComments());
		jsonObj.put("ppTxnId", model.getPpTxnId());
		jsonObj.put("ppPaymentStatus", model.getPpPaymentStatus());
		jsonObj.put("ppPaymentGross", model.getPpPaymentGross());
		jsonObj.put("ppReceiverEmail", model.getPpReceiverEmail());
		jsonObj.put("ppPayerEmail", model.getPpPayerEmail());
		jsonObj.put("sendOrderEmail", model.getSendOrderEmail());
		jsonObj.put("sendShippingEmail", model.getSendShippingEmail());

		return jsonObj;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.shopping.model.ShoppingOrder[] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (ShoppingOrder model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		com.liferay.portlet.shopping.model.ShoppingOrder[][] models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (ShoppingOrder[] model : models) {
			jsonArray.put(toJSONArray(model));
		}

		return jsonArray;
	}

	public static JSONArray toJSONArray(
		List<com.liferay.portlet.shopping.model.ShoppingOrder> models) {
		JSONArray jsonArray = JSONFactoryUtil.createJSONArray();

		for (ShoppingOrder model : models) {
			jsonArray.put(toJSONObject(model));
		}

		return jsonArray;
	}
}