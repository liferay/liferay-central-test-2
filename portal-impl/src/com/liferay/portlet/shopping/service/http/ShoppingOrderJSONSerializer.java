/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
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
 * @author    Brian Wing Shun Chan
 * @generated
 */
public class ShoppingOrderJSONSerializer {
	public static JSONObject toJSONObject(ShoppingOrder model) {
		JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

		jsonObject.put("orderId", model.getOrderId());
		jsonObject.put("groupId", model.getGroupId());
		jsonObject.put("companyId", model.getCompanyId());
		jsonObject.put("userId", model.getUserId());
		jsonObject.put("userName", model.getUserName());

		Date createDate = model.getCreateDate();

		String createDateJSON = StringPool.BLANK;

		if (createDate != null) {
			createDateJSON = String.valueOf(createDate.getTime());
		}

		jsonObject.put("createDate", createDateJSON);

		Date modifiedDate = model.getModifiedDate();

		String modifiedDateJSON = StringPool.BLANK;

		if (modifiedDate != null) {
			modifiedDateJSON = String.valueOf(modifiedDate.getTime());
		}

		jsonObject.put("modifiedDate", modifiedDateJSON);
		jsonObject.put("number", model.getNumber());
		jsonObject.put("tax", model.getTax());
		jsonObject.put("shipping", model.getShipping());
		jsonObject.put("altShipping", model.getAltShipping());
		jsonObject.put("requiresShipping", model.getRequiresShipping());
		jsonObject.put("insure", model.getInsure());
		jsonObject.put("insurance", model.getInsurance());
		jsonObject.put("couponCodes", model.getCouponCodes());
		jsonObject.put("couponDiscount", model.getCouponDiscount());
		jsonObject.put("billingFirstName", model.getBillingFirstName());
		jsonObject.put("billingLastName", model.getBillingLastName());
		jsonObject.put("billingEmailAddress", model.getBillingEmailAddress());
		jsonObject.put("billingCompany", model.getBillingCompany());
		jsonObject.put("billingStreet", model.getBillingStreet());
		jsonObject.put("billingCity", model.getBillingCity());
		jsonObject.put("billingState", model.getBillingState());
		jsonObject.put("billingZip", model.getBillingZip());
		jsonObject.put("billingCountry", model.getBillingCountry());
		jsonObject.put("billingPhone", model.getBillingPhone());
		jsonObject.put("shipToBilling", model.getShipToBilling());
		jsonObject.put("shippingFirstName", model.getShippingFirstName());
		jsonObject.put("shippingLastName", model.getShippingLastName());
		jsonObject.put("shippingEmailAddress", model.getShippingEmailAddress());
		jsonObject.put("shippingCompany", model.getShippingCompany());
		jsonObject.put("shippingStreet", model.getShippingStreet());
		jsonObject.put("shippingCity", model.getShippingCity());
		jsonObject.put("shippingState", model.getShippingState());
		jsonObject.put("shippingZip", model.getShippingZip());
		jsonObject.put("shippingCountry", model.getShippingCountry());
		jsonObject.put("shippingPhone", model.getShippingPhone());
		jsonObject.put("ccName", model.getCcName());
		jsonObject.put("ccType", model.getCcType());
		jsonObject.put("ccNumber", model.getCcNumber());
		jsonObject.put("ccExpMonth", model.getCcExpMonth());
		jsonObject.put("ccExpYear", model.getCcExpYear());
		jsonObject.put("ccVerNumber", model.getCcVerNumber());
		jsonObject.put("comments", model.getComments());
		jsonObject.put("ppTxnId", model.getPpTxnId());
		jsonObject.put("ppPaymentStatus", model.getPpPaymentStatus());
		jsonObject.put("ppPaymentGross", model.getPpPaymentGross());
		jsonObject.put("ppReceiverEmail", model.getPpReceiverEmail());
		jsonObject.put("ppPayerEmail", model.getPpPayerEmail());
		jsonObject.put("sendOrderEmail", model.getSendOrderEmail());
		jsonObject.put("sendShippingEmail", model.getSendShippingEmail());

		return jsonObject;
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