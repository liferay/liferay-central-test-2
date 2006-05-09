/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet.shopping.model;

import com.liferay.portal.model.BaseModel;
import com.liferay.portal.util.PropsUtil;

import com.liferay.util.GetterUtil;
import com.liferay.util.XSSUtil;

import java.util.Date;

/**
 * <a href="ShoppingOrderModel.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ShoppingOrderModel extends BaseModel {
	public static boolean XSS_ALLOW_BY_MODEL = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder"),
			XSS_ALLOW);
	public static boolean XSS_ALLOW_ORDERID = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder.orderId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_GROUPID = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder.groupId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_COMPANYID = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder.companyId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERID = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder.userId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_USERNAME = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder.userName"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_ALTSHIPPING = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder.altShipping"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_COUPONIDS = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder.couponIds"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_BILLINGFIRSTNAME = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder.billingFirstName"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_BILLINGLASTNAME = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder.billingLastName"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_BILLINGEMAILADDRESS = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder.billingEmailAddress"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_BILLINGCOMPANY = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder.billingCompany"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_BILLINGSTREET = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder.billingStreet"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_BILLINGCITY = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder.billingCity"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_BILLINGSTATE = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder.billingState"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_BILLINGZIP = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder.billingZip"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_BILLINGCOUNTRY = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder.billingCountry"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_BILLINGPHONE = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder.billingPhone"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_SHIPPINGFIRSTNAME = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder.shippingFirstName"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_SHIPPINGLASTNAME = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder.shippingLastName"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_SHIPPINGEMAILADDRESS = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder.shippingEmailAddress"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_SHIPPINGCOMPANY = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder.shippingCompany"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_SHIPPINGSTREET = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder.shippingStreet"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_SHIPPINGCITY = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder.shippingCity"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_SHIPPINGSTATE = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder.shippingState"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_SHIPPINGZIP = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder.shippingZip"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_SHIPPINGCOUNTRY = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder.shippingCountry"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_SHIPPINGPHONE = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder.shippingPhone"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_CCNAME = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder.ccName"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_CCTYPE = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder.ccType"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_CCNUMBER = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder.ccNumber"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_CCVERNUMBER = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder.ccVerNumber"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_COMMENTS = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder.comments"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_PPTXNID = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder.ppTxnId"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_PPPAYMENTSTATUS = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder.ppPaymentStatus"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_PPRECEIVEREMAIL = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder.ppReceiverEmail"),
			XSS_ALLOW_BY_MODEL);
	public static boolean XSS_ALLOW_PPPAYEREMAIL = GetterUtil.get(PropsUtil.get(
				"xss.allow.com.liferay.portlet.shopping.model.ShoppingOrder.ppPayerEmail"),
			XSS_ALLOW_BY_MODEL);
	public static long LOCK_EXPIRATION_TIME = GetterUtil.getLong(PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.shopping.model.ShoppingOrderModel"));

	public ShoppingOrderModel() {
	}

	public String getPrimaryKey() {
		return _orderId;
	}

	public void setPrimaryKey(String pk) {
		setOrderId(pk);
	}

	public String getOrderId() {
		return GetterUtil.getString(_orderId);
	}

	public void setOrderId(String orderId) {
		if (((orderId == null) && (_orderId != null)) ||
				((orderId != null) && (_orderId == null)) ||
				((orderId != null) && (_orderId != null) &&
				!orderId.equals(_orderId))) {
			if (!XSS_ALLOW_ORDERID) {
				orderId = XSSUtil.strip(orderId);
			}

			_orderId = orderId;
			setModified(true);
		}
	}

	public String getGroupId() {
		return GetterUtil.getString(_groupId);
	}

	public void setGroupId(String groupId) {
		if (((groupId == null) && (_groupId != null)) ||
				((groupId != null) && (_groupId == null)) ||
				((groupId != null) && (_groupId != null) &&
				!groupId.equals(_groupId))) {
			if (!XSS_ALLOW_GROUPID) {
				groupId = XSSUtil.strip(groupId);
			}

			_groupId = groupId;
			setModified(true);
		}
	}

	public String getCompanyId() {
		return GetterUtil.getString(_companyId);
	}

	public void setCompanyId(String companyId) {
		if (((companyId == null) && (_companyId != null)) ||
				((companyId != null) && (_companyId == null)) ||
				((companyId != null) && (_companyId != null) &&
				!companyId.equals(_companyId))) {
			if (!XSS_ALLOW_COMPANYID) {
				companyId = XSSUtil.strip(companyId);
			}

			_companyId = companyId;
			setModified(true);
		}
	}

	public String getUserId() {
		return GetterUtil.getString(_userId);
	}

	public void setUserId(String userId) {
		if (((userId == null) && (_userId != null)) ||
				((userId != null) && (_userId == null)) ||
				((userId != null) && (_userId != null) &&
				!userId.equals(_userId))) {
			if (!XSS_ALLOW_USERID) {
				userId = XSSUtil.strip(userId);
			}

			_userId = userId;
			setModified(true);
		}
	}

	public String getUserName() {
		return GetterUtil.getString(_userName);
	}

	public void setUserName(String userName) {
		if (((userName == null) && (_userName != null)) ||
				((userName != null) && (_userName == null)) ||
				((userName != null) && (_userName != null) &&
				!userName.equals(_userName))) {
			if (!XSS_ALLOW_USERNAME) {
				userName = XSSUtil.strip(userName);
			}

			_userName = userName;
			setModified(true);
		}
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		if (((createDate == null) && (_createDate != null)) ||
				((createDate != null) && (_createDate == null)) ||
				((createDate != null) && (_createDate != null) &&
				!createDate.equals(_createDate))) {
			_createDate = createDate;
			setModified(true);
		}
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		if (((modifiedDate == null) && (_modifiedDate != null)) ||
				((modifiedDate != null) && (_modifiedDate == null)) ||
				((modifiedDate != null) && (_modifiedDate != null) &&
				!modifiedDate.equals(_modifiedDate))) {
			_modifiedDate = modifiedDate;
			setModified(true);
		}
	}

	public double getTax() {
		return _tax;
	}

	public void setTax(double tax) {
		if (tax != _tax) {
			_tax = tax;
			setModified(true);
		}
	}

	public double getShipping() {
		return _shipping;
	}

	public void setShipping(double shipping) {
		if (shipping != _shipping) {
			_shipping = shipping;
			setModified(true);
		}
	}

	public String getAltShipping() {
		return GetterUtil.getString(_altShipping);
	}

	public void setAltShipping(String altShipping) {
		if (((altShipping == null) && (_altShipping != null)) ||
				((altShipping != null) && (_altShipping == null)) ||
				((altShipping != null) && (_altShipping != null) &&
				!altShipping.equals(_altShipping))) {
			if (!XSS_ALLOW_ALTSHIPPING) {
				altShipping = XSSUtil.strip(altShipping);
			}

			_altShipping = altShipping;
			setModified(true);
		}
	}

	public boolean getRequiresShipping() {
		return _requiresShipping;
	}

	public boolean isRequiresShipping() {
		return _requiresShipping;
	}

	public void setRequiresShipping(boolean requiresShipping) {
		if (requiresShipping != _requiresShipping) {
			_requiresShipping = requiresShipping;
			setModified(true);
		}
	}

	public boolean getInsure() {
		return _insure;
	}

	public boolean isInsure() {
		return _insure;
	}

	public void setInsure(boolean insure) {
		if (insure != _insure) {
			_insure = insure;
			setModified(true);
		}
	}

	public double getInsurance() {
		return _insurance;
	}

	public void setInsurance(double insurance) {
		if (insurance != _insurance) {
			_insurance = insurance;
			setModified(true);
		}
	}

	public String getCouponIds() {
		return GetterUtil.getString(_couponIds);
	}

	public void setCouponIds(String couponIds) {
		if (((couponIds == null) && (_couponIds != null)) ||
				((couponIds != null) && (_couponIds == null)) ||
				((couponIds != null) && (_couponIds != null) &&
				!couponIds.equals(_couponIds))) {
			if (!XSS_ALLOW_COUPONIDS) {
				couponIds = XSSUtil.strip(couponIds);
			}

			_couponIds = couponIds;
			setModified(true);
		}
	}

	public double getCouponDiscount() {
		return _couponDiscount;
	}

	public void setCouponDiscount(double couponDiscount) {
		if (couponDiscount != _couponDiscount) {
			_couponDiscount = couponDiscount;
			setModified(true);
		}
	}

	public String getBillingFirstName() {
		return GetterUtil.getString(_billingFirstName);
	}

	public void setBillingFirstName(String billingFirstName) {
		if (((billingFirstName == null) && (_billingFirstName != null)) ||
				((billingFirstName != null) && (_billingFirstName == null)) ||
				((billingFirstName != null) && (_billingFirstName != null) &&
				!billingFirstName.equals(_billingFirstName))) {
			if (!XSS_ALLOW_BILLINGFIRSTNAME) {
				billingFirstName = XSSUtil.strip(billingFirstName);
			}

			_billingFirstName = billingFirstName;
			setModified(true);
		}
	}

	public String getBillingLastName() {
		return GetterUtil.getString(_billingLastName);
	}

	public void setBillingLastName(String billingLastName) {
		if (((billingLastName == null) && (_billingLastName != null)) ||
				((billingLastName != null) && (_billingLastName == null)) ||
				((billingLastName != null) && (_billingLastName != null) &&
				!billingLastName.equals(_billingLastName))) {
			if (!XSS_ALLOW_BILLINGLASTNAME) {
				billingLastName = XSSUtil.strip(billingLastName);
			}

			_billingLastName = billingLastName;
			setModified(true);
		}
	}

	public String getBillingEmailAddress() {
		return GetterUtil.getString(_billingEmailAddress);
	}

	public void setBillingEmailAddress(String billingEmailAddress) {
		if (((billingEmailAddress == null) && (_billingEmailAddress != null)) ||
				((billingEmailAddress != null) &&
				(_billingEmailAddress == null)) ||
				((billingEmailAddress != null) &&
				(_billingEmailAddress != null) &&
				!billingEmailAddress.equals(_billingEmailAddress))) {
			if (!XSS_ALLOW_BILLINGEMAILADDRESS) {
				billingEmailAddress = XSSUtil.strip(billingEmailAddress);
			}

			_billingEmailAddress = billingEmailAddress;
			setModified(true);
		}
	}

	public String getBillingCompany() {
		return GetterUtil.getString(_billingCompany);
	}

	public void setBillingCompany(String billingCompany) {
		if (((billingCompany == null) && (_billingCompany != null)) ||
				((billingCompany != null) && (_billingCompany == null)) ||
				((billingCompany != null) && (_billingCompany != null) &&
				!billingCompany.equals(_billingCompany))) {
			if (!XSS_ALLOW_BILLINGCOMPANY) {
				billingCompany = XSSUtil.strip(billingCompany);
			}

			_billingCompany = billingCompany;
			setModified(true);
		}
	}

	public String getBillingStreet() {
		return GetterUtil.getString(_billingStreet);
	}

	public void setBillingStreet(String billingStreet) {
		if (((billingStreet == null) && (_billingStreet != null)) ||
				((billingStreet != null) && (_billingStreet == null)) ||
				((billingStreet != null) && (_billingStreet != null) &&
				!billingStreet.equals(_billingStreet))) {
			if (!XSS_ALLOW_BILLINGSTREET) {
				billingStreet = XSSUtil.strip(billingStreet);
			}

			_billingStreet = billingStreet;
			setModified(true);
		}
	}

	public String getBillingCity() {
		return GetterUtil.getString(_billingCity);
	}

	public void setBillingCity(String billingCity) {
		if (((billingCity == null) && (_billingCity != null)) ||
				((billingCity != null) && (_billingCity == null)) ||
				((billingCity != null) && (_billingCity != null) &&
				!billingCity.equals(_billingCity))) {
			if (!XSS_ALLOW_BILLINGCITY) {
				billingCity = XSSUtil.strip(billingCity);
			}

			_billingCity = billingCity;
			setModified(true);
		}
	}

	public String getBillingState() {
		return GetterUtil.getString(_billingState);
	}

	public void setBillingState(String billingState) {
		if (((billingState == null) && (_billingState != null)) ||
				((billingState != null) && (_billingState == null)) ||
				((billingState != null) && (_billingState != null) &&
				!billingState.equals(_billingState))) {
			if (!XSS_ALLOW_BILLINGSTATE) {
				billingState = XSSUtil.strip(billingState);
			}

			_billingState = billingState;
			setModified(true);
		}
	}

	public String getBillingZip() {
		return GetterUtil.getString(_billingZip);
	}

	public void setBillingZip(String billingZip) {
		if (((billingZip == null) && (_billingZip != null)) ||
				((billingZip != null) && (_billingZip == null)) ||
				((billingZip != null) && (_billingZip != null) &&
				!billingZip.equals(_billingZip))) {
			if (!XSS_ALLOW_BILLINGZIP) {
				billingZip = XSSUtil.strip(billingZip);
			}

			_billingZip = billingZip;
			setModified(true);
		}
	}

	public String getBillingCountry() {
		return GetterUtil.getString(_billingCountry);
	}

	public void setBillingCountry(String billingCountry) {
		if (((billingCountry == null) && (_billingCountry != null)) ||
				((billingCountry != null) && (_billingCountry == null)) ||
				((billingCountry != null) && (_billingCountry != null) &&
				!billingCountry.equals(_billingCountry))) {
			if (!XSS_ALLOW_BILLINGCOUNTRY) {
				billingCountry = XSSUtil.strip(billingCountry);
			}

			_billingCountry = billingCountry;
			setModified(true);
		}
	}

	public String getBillingPhone() {
		return GetterUtil.getString(_billingPhone);
	}

	public void setBillingPhone(String billingPhone) {
		if (((billingPhone == null) && (_billingPhone != null)) ||
				((billingPhone != null) && (_billingPhone == null)) ||
				((billingPhone != null) && (_billingPhone != null) &&
				!billingPhone.equals(_billingPhone))) {
			if (!XSS_ALLOW_BILLINGPHONE) {
				billingPhone = XSSUtil.strip(billingPhone);
			}

			_billingPhone = billingPhone;
			setModified(true);
		}
	}

	public boolean getShipToBilling() {
		return _shipToBilling;
	}

	public boolean isShipToBilling() {
		return _shipToBilling;
	}

	public void setShipToBilling(boolean shipToBilling) {
		if (shipToBilling != _shipToBilling) {
			_shipToBilling = shipToBilling;
			setModified(true);
		}
	}

	public String getShippingFirstName() {
		return GetterUtil.getString(_shippingFirstName);
	}

	public void setShippingFirstName(String shippingFirstName) {
		if (((shippingFirstName == null) && (_shippingFirstName != null)) ||
				((shippingFirstName != null) && (_shippingFirstName == null)) ||
				((shippingFirstName != null) && (_shippingFirstName != null) &&
				!shippingFirstName.equals(_shippingFirstName))) {
			if (!XSS_ALLOW_SHIPPINGFIRSTNAME) {
				shippingFirstName = XSSUtil.strip(shippingFirstName);
			}

			_shippingFirstName = shippingFirstName;
			setModified(true);
		}
	}

	public String getShippingLastName() {
		return GetterUtil.getString(_shippingLastName);
	}

	public void setShippingLastName(String shippingLastName) {
		if (((shippingLastName == null) && (_shippingLastName != null)) ||
				((shippingLastName != null) && (_shippingLastName == null)) ||
				((shippingLastName != null) && (_shippingLastName != null) &&
				!shippingLastName.equals(_shippingLastName))) {
			if (!XSS_ALLOW_SHIPPINGLASTNAME) {
				shippingLastName = XSSUtil.strip(shippingLastName);
			}

			_shippingLastName = shippingLastName;
			setModified(true);
		}
	}

	public String getShippingEmailAddress() {
		return GetterUtil.getString(_shippingEmailAddress);
	}

	public void setShippingEmailAddress(String shippingEmailAddress) {
		if (((shippingEmailAddress == null) && (_shippingEmailAddress != null)) ||
				((shippingEmailAddress != null) &&
				(_shippingEmailAddress == null)) ||
				((shippingEmailAddress != null) &&
				(_shippingEmailAddress != null) &&
				!shippingEmailAddress.equals(_shippingEmailAddress))) {
			if (!XSS_ALLOW_SHIPPINGEMAILADDRESS) {
				shippingEmailAddress = XSSUtil.strip(shippingEmailAddress);
			}

			_shippingEmailAddress = shippingEmailAddress;
			setModified(true);
		}
	}

	public String getShippingCompany() {
		return GetterUtil.getString(_shippingCompany);
	}

	public void setShippingCompany(String shippingCompany) {
		if (((shippingCompany == null) && (_shippingCompany != null)) ||
				((shippingCompany != null) && (_shippingCompany == null)) ||
				((shippingCompany != null) && (_shippingCompany != null) &&
				!shippingCompany.equals(_shippingCompany))) {
			if (!XSS_ALLOW_SHIPPINGCOMPANY) {
				shippingCompany = XSSUtil.strip(shippingCompany);
			}

			_shippingCompany = shippingCompany;
			setModified(true);
		}
	}

	public String getShippingStreet() {
		return GetterUtil.getString(_shippingStreet);
	}

	public void setShippingStreet(String shippingStreet) {
		if (((shippingStreet == null) && (_shippingStreet != null)) ||
				((shippingStreet != null) && (_shippingStreet == null)) ||
				((shippingStreet != null) && (_shippingStreet != null) &&
				!shippingStreet.equals(_shippingStreet))) {
			if (!XSS_ALLOW_SHIPPINGSTREET) {
				shippingStreet = XSSUtil.strip(shippingStreet);
			}

			_shippingStreet = shippingStreet;
			setModified(true);
		}
	}

	public String getShippingCity() {
		return GetterUtil.getString(_shippingCity);
	}

	public void setShippingCity(String shippingCity) {
		if (((shippingCity == null) && (_shippingCity != null)) ||
				((shippingCity != null) && (_shippingCity == null)) ||
				((shippingCity != null) && (_shippingCity != null) &&
				!shippingCity.equals(_shippingCity))) {
			if (!XSS_ALLOW_SHIPPINGCITY) {
				shippingCity = XSSUtil.strip(shippingCity);
			}

			_shippingCity = shippingCity;
			setModified(true);
		}
	}

	public String getShippingState() {
		return GetterUtil.getString(_shippingState);
	}

	public void setShippingState(String shippingState) {
		if (((shippingState == null) && (_shippingState != null)) ||
				((shippingState != null) && (_shippingState == null)) ||
				((shippingState != null) && (_shippingState != null) &&
				!shippingState.equals(_shippingState))) {
			if (!XSS_ALLOW_SHIPPINGSTATE) {
				shippingState = XSSUtil.strip(shippingState);
			}

			_shippingState = shippingState;
			setModified(true);
		}
	}

	public String getShippingZip() {
		return GetterUtil.getString(_shippingZip);
	}

	public void setShippingZip(String shippingZip) {
		if (((shippingZip == null) && (_shippingZip != null)) ||
				((shippingZip != null) && (_shippingZip == null)) ||
				((shippingZip != null) && (_shippingZip != null) &&
				!shippingZip.equals(_shippingZip))) {
			if (!XSS_ALLOW_SHIPPINGZIP) {
				shippingZip = XSSUtil.strip(shippingZip);
			}

			_shippingZip = shippingZip;
			setModified(true);
		}
	}

	public String getShippingCountry() {
		return GetterUtil.getString(_shippingCountry);
	}

	public void setShippingCountry(String shippingCountry) {
		if (((shippingCountry == null) && (_shippingCountry != null)) ||
				((shippingCountry != null) && (_shippingCountry == null)) ||
				((shippingCountry != null) && (_shippingCountry != null) &&
				!shippingCountry.equals(_shippingCountry))) {
			if (!XSS_ALLOW_SHIPPINGCOUNTRY) {
				shippingCountry = XSSUtil.strip(shippingCountry);
			}

			_shippingCountry = shippingCountry;
			setModified(true);
		}
	}

	public String getShippingPhone() {
		return GetterUtil.getString(_shippingPhone);
	}

	public void setShippingPhone(String shippingPhone) {
		if (((shippingPhone == null) && (_shippingPhone != null)) ||
				((shippingPhone != null) && (_shippingPhone == null)) ||
				((shippingPhone != null) && (_shippingPhone != null) &&
				!shippingPhone.equals(_shippingPhone))) {
			if (!XSS_ALLOW_SHIPPINGPHONE) {
				shippingPhone = XSSUtil.strip(shippingPhone);
			}

			_shippingPhone = shippingPhone;
			setModified(true);
		}
	}

	public String getCcName() {
		return GetterUtil.getString(_ccName);
	}

	public void setCcName(String ccName) {
		if (((ccName == null) && (_ccName != null)) ||
				((ccName != null) && (_ccName == null)) ||
				((ccName != null) && (_ccName != null) &&
				!ccName.equals(_ccName))) {
			if (!XSS_ALLOW_CCNAME) {
				ccName = XSSUtil.strip(ccName);
			}

			_ccName = ccName;
			setModified(true);
		}
	}

	public String getCcType() {
		return GetterUtil.getString(_ccType);
	}

	public void setCcType(String ccType) {
		if (((ccType == null) && (_ccType != null)) ||
				((ccType != null) && (_ccType == null)) ||
				((ccType != null) && (_ccType != null) &&
				!ccType.equals(_ccType))) {
			if (!XSS_ALLOW_CCTYPE) {
				ccType = XSSUtil.strip(ccType);
			}

			_ccType = ccType;
			setModified(true);
		}
	}

	public String getCcNumber() {
		return GetterUtil.getString(_ccNumber);
	}

	public void setCcNumber(String ccNumber) {
		if (((ccNumber == null) && (_ccNumber != null)) ||
				((ccNumber != null) && (_ccNumber == null)) ||
				((ccNumber != null) && (_ccNumber != null) &&
				!ccNumber.equals(_ccNumber))) {
			if (!XSS_ALLOW_CCNUMBER) {
				ccNumber = XSSUtil.strip(ccNumber);
			}

			_ccNumber = ccNumber;
			setModified(true);
		}
	}

	public int getCcExpMonth() {
		return _ccExpMonth;
	}

	public void setCcExpMonth(int ccExpMonth) {
		if (ccExpMonth != _ccExpMonth) {
			_ccExpMonth = ccExpMonth;
			setModified(true);
		}
	}

	public int getCcExpYear() {
		return _ccExpYear;
	}

	public void setCcExpYear(int ccExpYear) {
		if (ccExpYear != _ccExpYear) {
			_ccExpYear = ccExpYear;
			setModified(true);
		}
	}

	public String getCcVerNumber() {
		return GetterUtil.getString(_ccVerNumber);
	}

	public void setCcVerNumber(String ccVerNumber) {
		if (((ccVerNumber == null) && (_ccVerNumber != null)) ||
				((ccVerNumber != null) && (_ccVerNumber == null)) ||
				((ccVerNumber != null) && (_ccVerNumber != null) &&
				!ccVerNumber.equals(_ccVerNumber))) {
			if (!XSS_ALLOW_CCVERNUMBER) {
				ccVerNumber = XSSUtil.strip(ccVerNumber);
			}

			_ccVerNumber = ccVerNumber;
			setModified(true);
		}
	}

	public String getComments() {
		return GetterUtil.getString(_comments);
	}

	public void setComments(String comments) {
		if (((comments == null) && (_comments != null)) ||
				((comments != null) && (_comments == null)) ||
				((comments != null) && (_comments != null) &&
				!comments.equals(_comments))) {
			if (!XSS_ALLOW_COMMENTS) {
				comments = XSSUtil.strip(comments);
			}

			_comments = comments;
			setModified(true);
		}
	}

	public String getPpTxnId() {
		return GetterUtil.getString(_ppTxnId);
	}

	public void setPpTxnId(String ppTxnId) {
		if (((ppTxnId == null) && (_ppTxnId != null)) ||
				((ppTxnId != null) && (_ppTxnId == null)) ||
				((ppTxnId != null) && (_ppTxnId != null) &&
				!ppTxnId.equals(_ppTxnId))) {
			if (!XSS_ALLOW_PPTXNID) {
				ppTxnId = XSSUtil.strip(ppTxnId);
			}

			_ppTxnId = ppTxnId;
			setModified(true);
		}
	}

	public String getPpPaymentStatus() {
		return GetterUtil.getString(_ppPaymentStatus);
	}

	public void setPpPaymentStatus(String ppPaymentStatus) {
		if (((ppPaymentStatus == null) && (_ppPaymentStatus != null)) ||
				((ppPaymentStatus != null) && (_ppPaymentStatus == null)) ||
				((ppPaymentStatus != null) && (_ppPaymentStatus != null) &&
				!ppPaymentStatus.equals(_ppPaymentStatus))) {
			if (!XSS_ALLOW_PPPAYMENTSTATUS) {
				ppPaymentStatus = XSSUtil.strip(ppPaymentStatus);
			}

			_ppPaymentStatus = ppPaymentStatus;
			setModified(true);
		}
	}

	public double getPpPaymentGross() {
		return _ppPaymentGross;
	}

	public void setPpPaymentGross(double ppPaymentGross) {
		if (ppPaymentGross != _ppPaymentGross) {
			_ppPaymentGross = ppPaymentGross;
			setModified(true);
		}
	}

	public String getPpReceiverEmail() {
		return GetterUtil.getString(_ppReceiverEmail);
	}

	public void setPpReceiverEmail(String ppReceiverEmail) {
		if (((ppReceiverEmail == null) && (_ppReceiverEmail != null)) ||
				((ppReceiverEmail != null) && (_ppReceiverEmail == null)) ||
				((ppReceiverEmail != null) && (_ppReceiverEmail != null) &&
				!ppReceiverEmail.equals(_ppReceiverEmail))) {
			if (!XSS_ALLOW_PPRECEIVEREMAIL) {
				ppReceiverEmail = XSSUtil.strip(ppReceiverEmail);
			}

			_ppReceiverEmail = ppReceiverEmail;
			setModified(true);
		}
	}

	public String getPpPayerEmail() {
		return GetterUtil.getString(_ppPayerEmail);
	}

	public void setPpPayerEmail(String ppPayerEmail) {
		if (((ppPayerEmail == null) && (_ppPayerEmail != null)) ||
				((ppPayerEmail != null) && (_ppPayerEmail == null)) ||
				((ppPayerEmail != null) && (_ppPayerEmail != null) &&
				!ppPayerEmail.equals(_ppPayerEmail))) {
			if (!XSS_ALLOW_PPPAYEREMAIL) {
				ppPayerEmail = XSSUtil.strip(ppPayerEmail);
			}

			_ppPayerEmail = ppPayerEmail;
			setModified(true);
		}
	}

	public boolean getSendOrderEmail() {
		return _sendOrderEmail;
	}

	public boolean isSendOrderEmail() {
		return _sendOrderEmail;
	}

	public void setSendOrderEmail(boolean sendOrderEmail) {
		if (sendOrderEmail != _sendOrderEmail) {
			_sendOrderEmail = sendOrderEmail;
			setModified(true);
		}
	}

	public boolean getSendShippingEmail() {
		return _sendShippingEmail;
	}

	public boolean isSendShippingEmail() {
		return _sendShippingEmail;
	}

	public void setSendShippingEmail(boolean sendShippingEmail) {
		if (sendShippingEmail != _sendShippingEmail) {
			_sendShippingEmail = sendShippingEmail;
			setModified(true);
		}
	}

	public Object clone() {
		ShoppingOrder clone = new ShoppingOrder();
		clone.setOrderId(getOrderId());
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setTax(getTax());
		clone.setShipping(getShipping());
		clone.setAltShipping(getAltShipping());
		clone.setRequiresShipping(getRequiresShipping());
		clone.setInsure(getInsure());
		clone.setInsurance(getInsurance());
		clone.setCouponIds(getCouponIds());
		clone.setCouponDiscount(getCouponDiscount());
		clone.setBillingFirstName(getBillingFirstName());
		clone.setBillingLastName(getBillingLastName());
		clone.setBillingEmailAddress(getBillingEmailAddress());
		clone.setBillingCompany(getBillingCompany());
		clone.setBillingStreet(getBillingStreet());
		clone.setBillingCity(getBillingCity());
		clone.setBillingState(getBillingState());
		clone.setBillingZip(getBillingZip());
		clone.setBillingCountry(getBillingCountry());
		clone.setBillingPhone(getBillingPhone());
		clone.setShipToBilling(getShipToBilling());
		clone.setShippingFirstName(getShippingFirstName());
		clone.setShippingLastName(getShippingLastName());
		clone.setShippingEmailAddress(getShippingEmailAddress());
		clone.setShippingCompany(getShippingCompany());
		clone.setShippingStreet(getShippingStreet());
		clone.setShippingCity(getShippingCity());
		clone.setShippingState(getShippingState());
		clone.setShippingZip(getShippingZip());
		clone.setShippingCountry(getShippingCountry());
		clone.setShippingPhone(getShippingPhone());
		clone.setCcName(getCcName());
		clone.setCcType(getCcType());
		clone.setCcNumber(getCcNumber());
		clone.setCcExpMonth(getCcExpMonth());
		clone.setCcExpYear(getCcExpYear());
		clone.setCcVerNumber(getCcVerNumber());
		clone.setComments(getComments());
		clone.setPpTxnId(getPpTxnId());
		clone.setPpPaymentStatus(getPpPaymentStatus());
		clone.setPpPaymentGross(getPpPaymentGross());
		clone.setPpReceiverEmail(getPpReceiverEmail());
		clone.setPpPayerEmail(getPpPayerEmail());
		clone.setSendOrderEmail(getSendOrderEmail());
		clone.setSendShippingEmail(getSendShippingEmail());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		ShoppingOrder shoppingOrder = (ShoppingOrder)obj;
		int value = 0;
		value = getCreateDate().compareTo(shoppingOrder.getCreateDate());
		value = value * -1;

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		ShoppingOrder shoppingOrder = null;

		try {
			shoppingOrder = (ShoppingOrder)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		String pk = shoppingOrder.getPrimaryKey();

		if (getPrimaryKey().equals(pk)) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return getPrimaryKey().hashCode();
	}

	private String _orderId;
	private String _groupId;
	private String _companyId;
	private String _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private double _tax;
	private double _shipping;
	private String _altShipping;
	private boolean _requiresShipping;
	private boolean _insure;
	private double _insurance;
	private String _couponIds;
	private double _couponDiscount;
	private String _billingFirstName;
	private String _billingLastName;
	private String _billingEmailAddress;
	private String _billingCompany;
	private String _billingStreet;
	private String _billingCity;
	private String _billingState;
	private String _billingZip;
	private String _billingCountry;
	private String _billingPhone;
	private boolean _shipToBilling;
	private String _shippingFirstName;
	private String _shippingLastName;
	private String _shippingEmailAddress;
	private String _shippingCompany;
	private String _shippingStreet;
	private String _shippingCity;
	private String _shippingState;
	private String _shippingZip;
	private String _shippingCountry;
	private String _shippingPhone;
	private String _ccName;
	private String _ccType;
	private String _ccNumber;
	private int _ccExpMonth;
	private int _ccExpYear;
	private String _ccVerNumber;
	private String _comments;
	private String _ppTxnId;
	private String _ppPaymentStatus;
	private double _ppPaymentGross;
	private String _ppReceiverEmail;
	private String _ppPayerEmail;
	private boolean _sendOrderEmail;
	private boolean _sendShippingEmail;
}