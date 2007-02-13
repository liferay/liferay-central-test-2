/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.StringPool;

import com.liferay.portlet.shopping.model.ShoppingOrder;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * <a href="ShoppingOrderJSONSerializer.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ShoppingOrderJSONSerializer {
	public static JSONObject toJSONObject(ShoppingOrder model) {
		JSONObject jsonObj = new JSONObject();
		String orderId = model.getOrderId();

		if (orderId == null) {
			jsonObj.put("orderId", StringPool.BLANK);
		}
		else {
			jsonObj.put("orderId", orderId.toString());
		}

		jsonObj.put("groupId", model.getGroupId());

		String companyId = model.getCompanyId();

		if (companyId == null) {
			jsonObj.put("companyId", StringPool.BLANK);
		}
		else {
			jsonObj.put("companyId", companyId.toString());
		}

		String userId = model.getUserId();

		if (userId == null) {
			jsonObj.put("userId", StringPool.BLANK);
		}
		else {
			jsonObj.put("userId", userId.toString());
		}

		String userName = model.getUserName();

		if (userName == null) {
			jsonObj.put("userName", StringPool.BLANK);
		}
		else {
			jsonObj.put("userName", userName.toString());
		}

		Date createDate = model.getCreateDate();

		if (createDate == null) {
			jsonObj.put("createDate", StringPool.BLANK);
		}
		else {
			jsonObj.put("createDate", createDate.toString());
		}

		Date modifiedDate = model.getModifiedDate();

		if (modifiedDate == null) {
			jsonObj.put("modifiedDate", StringPool.BLANK);
		}
		else {
			jsonObj.put("modifiedDate", modifiedDate.toString());
		}

		jsonObj.put("tax", model.getTax());
		jsonObj.put("shipping", model.getShipping());

		String altShipping = model.getAltShipping();

		if (altShipping == null) {
			jsonObj.put("altShipping", StringPool.BLANK);
		}
		else {
			jsonObj.put("altShipping", altShipping.toString());
		}

		jsonObj.put("requiresShipping", model.getRequiresShipping());
		jsonObj.put("insure", model.getInsure());
		jsonObj.put("insurance", model.getInsurance());

		String couponIds = model.getCouponIds();

		if (couponIds == null) {
			jsonObj.put("couponIds", StringPool.BLANK);
		}
		else {
			jsonObj.put("couponIds", couponIds.toString());
		}

		jsonObj.put("couponDiscount", model.getCouponDiscount());

		String billingFirstName = model.getBillingFirstName();

		if (billingFirstName == null) {
			jsonObj.put("billingFirstName", StringPool.BLANK);
		}
		else {
			jsonObj.put("billingFirstName", billingFirstName.toString());
		}

		String billingLastName = model.getBillingLastName();

		if (billingLastName == null) {
			jsonObj.put("billingLastName", StringPool.BLANK);
		}
		else {
			jsonObj.put("billingLastName", billingLastName.toString());
		}

		String billingEmailAddress = model.getBillingEmailAddress();

		if (billingEmailAddress == null) {
			jsonObj.put("billingEmailAddress", StringPool.BLANK);
		}
		else {
			jsonObj.put("billingEmailAddress", billingEmailAddress.toString());
		}

		String billingCompany = model.getBillingCompany();

		if (billingCompany == null) {
			jsonObj.put("billingCompany", StringPool.BLANK);
		}
		else {
			jsonObj.put("billingCompany", billingCompany.toString());
		}

		String billingStreet = model.getBillingStreet();

		if (billingStreet == null) {
			jsonObj.put("billingStreet", StringPool.BLANK);
		}
		else {
			jsonObj.put("billingStreet", billingStreet.toString());
		}

		String billingCity = model.getBillingCity();

		if (billingCity == null) {
			jsonObj.put("billingCity", StringPool.BLANK);
		}
		else {
			jsonObj.put("billingCity", billingCity.toString());
		}

		String billingState = model.getBillingState();

		if (billingState == null) {
			jsonObj.put("billingState", StringPool.BLANK);
		}
		else {
			jsonObj.put("billingState", billingState.toString());
		}

		String billingZip = model.getBillingZip();

		if (billingZip == null) {
			jsonObj.put("billingZip", StringPool.BLANK);
		}
		else {
			jsonObj.put("billingZip", billingZip.toString());
		}

		String billingCountry = model.getBillingCountry();

		if (billingCountry == null) {
			jsonObj.put("billingCountry", StringPool.BLANK);
		}
		else {
			jsonObj.put("billingCountry", billingCountry.toString());
		}

		String billingPhone = model.getBillingPhone();

		if (billingPhone == null) {
			jsonObj.put("billingPhone", StringPool.BLANK);
		}
		else {
			jsonObj.put("billingPhone", billingPhone.toString());
		}

		jsonObj.put("shipToBilling", model.getShipToBilling());

		String shippingFirstName = model.getShippingFirstName();

		if (shippingFirstName == null) {
			jsonObj.put("shippingFirstName", StringPool.BLANK);
		}
		else {
			jsonObj.put("shippingFirstName", shippingFirstName.toString());
		}

		String shippingLastName = model.getShippingLastName();

		if (shippingLastName == null) {
			jsonObj.put("shippingLastName", StringPool.BLANK);
		}
		else {
			jsonObj.put("shippingLastName", shippingLastName.toString());
		}

		String shippingEmailAddress = model.getShippingEmailAddress();

		if (shippingEmailAddress == null) {
			jsonObj.put("shippingEmailAddress", StringPool.BLANK);
		}
		else {
			jsonObj.put("shippingEmailAddress", shippingEmailAddress.toString());
		}

		String shippingCompany = model.getShippingCompany();

		if (shippingCompany == null) {
			jsonObj.put("shippingCompany", StringPool.BLANK);
		}
		else {
			jsonObj.put("shippingCompany", shippingCompany.toString());
		}

		String shippingStreet = model.getShippingStreet();

		if (shippingStreet == null) {
			jsonObj.put("shippingStreet", StringPool.BLANK);
		}
		else {
			jsonObj.put("shippingStreet", shippingStreet.toString());
		}

		String shippingCity = model.getShippingCity();

		if (shippingCity == null) {
			jsonObj.put("shippingCity", StringPool.BLANK);
		}
		else {
			jsonObj.put("shippingCity", shippingCity.toString());
		}

		String shippingState = model.getShippingState();

		if (shippingState == null) {
			jsonObj.put("shippingState", StringPool.BLANK);
		}
		else {
			jsonObj.put("shippingState", shippingState.toString());
		}

		String shippingZip = model.getShippingZip();

		if (shippingZip == null) {
			jsonObj.put("shippingZip", StringPool.BLANK);
		}
		else {
			jsonObj.put("shippingZip", shippingZip.toString());
		}

		String shippingCountry = model.getShippingCountry();

		if (shippingCountry == null) {
			jsonObj.put("shippingCountry", StringPool.BLANK);
		}
		else {
			jsonObj.put("shippingCountry", shippingCountry.toString());
		}

		String shippingPhone = model.getShippingPhone();

		if (shippingPhone == null) {
			jsonObj.put("shippingPhone", StringPool.BLANK);
		}
		else {
			jsonObj.put("shippingPhone", shippingPhone.toString());
		}

		String ccName = model.getCcName();

		if (ccName == null) {
			jsonObj.put("ccName", StringPool.BLANK);
		}
		else {
			jsonObj.put("ccName", ccName.toString());
		}

		String ccType = model.getCcType();

		if (ccType == null) {
			jsonObj.put("ccType", StringPool.BLANK);
		}
		else {
			jsonObj.put("ccType", ccType.toString());
		}

		String ccNumber = model.getCcNumber();

		if (ccNumber == null) {
			jsonObj.put("ccNumber", StringPool.BLANK);
		}
		else {
			jsonObj.put("ccNumber", ccNumber.toString());
		}

		jsonObj.put("ccExpMonth", model.getCcExpMonth());
		jsonObj.put("ccExpYear", model.getCcExpYear());

		String ccVerNumber = model.getCcVerNumber();

		if (ccVerNumber == null) {
			jsonObj.put("ccVerNumber", StringPool.BLANK);
		}
		else {
			jsonObj.put("ccVerNumber", ccVerNumber.toString());
		}

		String comments = model.getComments();

		if (comments == null) {
			jsonObj.put("comments", StringPool.BLANK);
		}
		else {
			jsonObj.put("comments", comments.toString());
		}

		String ppTxnId = model.getPpTxnId();

		if (ppTxnId == null) {
			jsonObj.put("ppTxnId", StringPool.BLANK);
		}
		else {
			jsonObj.put("ppTxnId", ppTxnId.toString());
		}

		String ppPaymentStatus = model.getPpPaymentStatus();

		if (ppPaymentStatus == null) {
			jsonObj.put("ppPaymentStatus", StringPool.BLANK);
		}
		else {
			jsonObj.put("ppPaymentStatus", ppPaymentStatus.toString());
		}

		jsonObj.put("ppPaymentGross", model.getPpPaymentGross());

		String ppReceiverEmail = model.getPpReceiverEmail();

		if (ppReceiverEmail == null) {
			jsonObj.put("ppReceiverEmail", StringPool.BLANK);
		}
		else {
			jsonObj.put("ppReceiverEmail", ppReceiverEmail.toString());
		}

		String ppPayerEmail = model.getPpPayerEmail();

		if (ppPayerEmail == null) {
			jsonObj.put("ppPayerEmail", StringPool.BLANK);
		}
		else {
			jsonObj.put("ppPayerEmail", ppPayerEmail.toString());
		}

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