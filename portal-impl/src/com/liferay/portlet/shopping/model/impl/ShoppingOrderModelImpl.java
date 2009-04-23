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

package com.liferay.portlet.shopping.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.DateUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.impl.BaseModelImpl;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.impl.ExpandoBridgeImpl;
import com.liferay.portlet.shopping.model.ShoppingOrder;
import com.liferay.portlet.shopping.model.ShoppingOrderSoap;

import java.io.Serializable;

import java.lang.StringBuilder;
import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="ShoppingOrderModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>ShoppingOrder</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.shopping.model.ShoppingOrder
 * @see com.liferay.portlet.shopping.model.ShoppingOrderModel
 * @see com.liferay.portlet.shopping.model.impl.ShoppingOrderImpl
 *
 */
public class ShoppingOrderModelImpl extends BaseModelImpl<ShoppingOrder> {
	public static final String TABLE_NAME = "ShoppingOrder";
	public static final Object[][] TABLE_COLUMNS = {
			{ "orderId", new Integer(Types.BIGINT) },
			

			{ "groupId", new Integer(Types.BIGINT) },
			

			{ "companyId", new Integer(Types.BIGINT) },
			

			{ "userId", new Integer(Types.BIGINT) },
			

			{ "userName", new Integer(Types.VARCHAR) },
			

			{ "createDate", new Integer(Types.TIMESTAMP) },
			

			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			

			{ "number_", new Integer(Types.VARCHAR) },
			

			{ "tax", new Integer(Types.DOUBLE) },
			

			{ "shipping", new Integer(Types.DOUBLE) },
			

			{ "altShipping", new Integer(Types.VARCHAR) },
			

			{ "requiresShipping", new Integer(Types.BOOLEAN) },
			

			{ "insure", new Integer(Types.BOOLEAN) },
			

			{ "insurance", new Integer(Types.DOUBLE) },
			

			{ "couponCodes", new Integer(Types.VARCHAR) },
			

			{ "couponDiscount", new Integer(Types.DOUBLE) },
			

			{ "billingFirstName", new Integer(Types.VARCHAR) },
			

			{ "billingLastName", new Integer(Types.VARCHAR) },
			

			{ "billingEmailAddress", new Integer(Types.VARCHAR) },
			

			{ "billingCompany", new Integer(Types.VARCHAR) },
			

			{ "billingStreet", new Integer(Types.VARCHAR) },
			

			{ "billingCity", new Integer(Types.VARCHAR) },
			

			{ "billingState", new Integer(Types.VARCHAR) },
			

			{ "billingZip", new Integer(Types.VARCHAR) },
			

			{ "billingCountry", new Integer(Types.VARCHAR) },
			

			{ "billingPhone", new Integer(Types.VARCHAR) },
			

			{ "shipToBilling", new Integer(Types.BOOLEAN) },
			

			{ "shippingFirstName", new Integer(Types.VARCHAR) },
			

			{ "shippingLastName", new Integer(Types.VARCHAR) },
			

			{ "shippingEmailAddress", new Integer(Types.VARCHAR) },
			

			{ "shippingCompany", new Integer(Types.VARCHAR) },
			

			{ "shippingStreet", new Integer(Types.VARCHAR) },
			

			{ "shippingCity", new Integer(Types.VARCHAR) },
			

			{ "shippingState", new Integer(Types.VARCHAR) },
			

			{ "shippingZip", new Integer(Types.VARCHAR) },
			

			{ "shippingCountry", new Integer(Types.VARCHAR) },
			

			{ "shippingPhone", new Integer(Types.VARCHAR) },
			

			{ "ccName", new Integer(Types.VARCHAR) },
			

			{ "ccType", new Integer(Types.VARCHAR) },
			

			{ "ccNumber", new Integer(Types.VARCHAR) },
			

			{ "ccExpMonth", new Integer(Types.INTEGER) },
			

			{ "ccExpYear", new Integer(Types.INTEGER) },
			

			{ "ccVerNumber", new Integer(Types.VARCHAR) },
			

			{ "comments", new Integer(Types.VARCHAR) },
			

			{ "ppTxnId", new Integer(Types.VARCHAR) },
			

			{ "ppPaymentStatus", new Integer(Types.VARCHAR) },
			

			{ "ppPaymentGross", new Integer(Types.DOUBLE) },
			

			{ "ppReceiverEmail", new Integer(Types.VARCHAR) },
			

			{ "ppPayerEmail", new Integer(Types.VARCHAR) },
			

			{ "sendOrderEmail", new Integer(Types.BOOLEAN) },
			

			{ "sendShippingEmail", new Integer(Types.BOOLEAN) }
		};
	public static final String TABLE_SQL_CREATE = "create table ShoppingOrder (orderId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,number_ VARCHAR(75) null,tax DOUBLE,shipping DOUBLE,altShipping VARCHAR(75) null,requiresShipping BOOLEAN,insure BOOLEAN,insurance DOUBLE,couponCodes VARCHAR(75) null,couponDiscount DOUBLE,billingFirstName VARCHAR(75) null,billingLastName VARCHAR(75) null,billingEmailAddress VARCHAR(75) null,billingCompany VARCHAR(75) null,billingStreet VARCHAR(75) null,billingCity VARCHAR(75) null,billingState VARCHAR(75) null,billingZip VARCHAR(75) null,billingCountry VARCHAR(75) null,billingPhone VARCHAR(75) null,shipToBilling BOOLEAN,shippingFirstName VARCHAR(75) null,shippingLastName VARCHAR(75) null,shippingEmailAddress VARCHAR(75) null,shippingCompany VARCHAR(75) null,shippingStreet VARCHAR(75) null,shippingCity VARCHAR(75) null,shippingState VARCHAR(75) null,shippingZip VARCHAR(75) null,shippingCountry VARCHAR(75) null,shippingPhone VARCHAR(75) null,ccName VARCHAR(75) null,ccType VARCHAR(75) null,ccNumber VARCHAR(75) null,ccExpMonth INTEGER,ccExpYear INTEGER,ccVerNumber VARCHAR(75) null,comments STRING null,ppTxnId VARCHAR(75) null,ppPaymentStatus VARCHAR(75) null,ppPaymentGross DOUBLE,ppReceiverEmail VARCHAR(75) null,ppPayerEmail VARCHAR(75) null,sendOrderEmail BOOLEAN,sendShippingEmail BOOLEAN)";
	public static final String TABLE_SQL_DROP = "drop table ShoppingOrder";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portlet.shopping.model.ShoppingOrder"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.shopping.model.ShoppingOrder"),
			true);

	public static ShoppingOrder toModel(ShoppingOrderSoap soapModel) {
		ShoppingOrder model = new ShoppingOrderImpl();

		model.setOrderId(soapModel.getOrderId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setNumber(soapModel.getNumber());
		model.setTax(soapModel.getTax());
		model.setShipping(soapModel.getShipping());
		model.setAltShipping(soapModel.getAltShipping());
		model.setRequiresShipping(soapModel.getRequiresShipping());
		model.setInsure(soapModel.getInsure());
		model.setInsurance(soapModel.getInsurance());
		model.setCouponCodes(soapModel.getCouponCodes());
		model.setCouponDiscount(soapModel.getCouponDiscount());
		model.setBillingFirstName(soapModel.getBillingFirstName());
		model.setBillingLastName(soapModel.getBillingLastName());
		model.setBillingEmailAddress(soapModel.getBillingEmailAddress());
		model.setBillingCompany(soapModel.getBillingCompany());
		model.setBillingStreet(soapModel.getBillingStreet());
		model.setBillingCity(soapModel.getBillingCity());
		model.setBillingState(soapModel.getBillingState());
		model.setBillingZip(soapModel.getBillingZip());
		model.setBillingCountry(soapModel.getBillingCountry());
		model.setBillingPhone(soapModel.getBillingPhone());
		model.setShipToBilling(soapModel.getShipToBilling());
		model.setShippingFirstName(soapModel.getShippingFirstName());
		model.setShippingLastName(soapModel.getShippingLastName());
		model.setShippingEmailAddress(soapModel.getShippingEmailAddress());
		model.setShippingCompany(soapModel.getShippingCompany());
		model.setShippingStreet(soapModel.getShippingStreet());
		model.setShippingCity(soapModel.getShippingCity());
		model.setShippingState(soapModel.getShippingState());
		model.setShippingZip(soapModel.getShippingZip());
		model.setShippingCountry(soapModel.getShippingCountry());
		model.setShippingPhone(soapModel.getShippingPhone());
		model.setCcName(soapModel.getCcName());
		model.setCcType(soapModel.getCcType());
		model.setCcNumber(soapModel.getCcNumber());
		model.setCcExpMonth(soapModel.getCcExpMonth());
		model.setCcExpYear(soapModel.getCcExpYear());
		model.setCcVerNumber(soapModel.getCcVerNumber());
		model.setComments(soapModel.getComments());
		model.setPpTxnId(soapModel.getPpTxnId());
		model.setPpPaymentStatus(soapModel.getPpPaymentStatus());
		model.setPpPaymentGross(soapModel.getPpPaymentGross());
		model.setPpReceiverEmail(soapModel.getPpReceiverEmail());
		model.setPpPayerEmail(soapModel.getPpPayerEmail());
		model.setSendOrderEmail(soapModel.getSendOrderEmail());
		model.setSendShippingEmail(soapModel.getSendShippingEmail());

		return model;
	}

	public static List<ShoppingOrder> toModels(ShoppingOrderSoap[] soapModels) {
		List<ShoppingOrder> models = new ArrayList<ShoppingOrder>(soapModels.length);

		for (ShoppingOrderSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.shopping.model.ShoppingOrder"));

	public ShoppingOrderModelImpl() {
	}

	public long getPrimaryKey() {
		return _orderId;
	}

	public void setPrimaryKey(long pk) {
		setOrderId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_orderId);
	}

	public long getOrderId() {
		return _orderId;
	}

	public void setOrderId(long orderId) {
		_orderId = orderId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public String getUserName() {
		return GetterUtil.getString(_userName);
	}

	public void setUserName(String userName) {
		_userName = userName;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getModifiedDate() {
		return _modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		_modifiedDate = modifiedDate;
	}

	public String getNumber() {
		return GetterUtil.getString(_number);
	}

	public void setNumber(String number) {
		_number = number;

		if (_originalNumber == null) {
			_originalNumber = number;
		}
	}

	public String getOriginalNumber() {
		return GetterUtil.getString(_originalNumber);
	}

	public double getTax() {
		return _tax;
	}

	public void setTax(double tax) {
		_tax = tax;
	}

	public double getShipping() {
		return _shipping;
	}

	public void setShipping(double shipping) {
		_shipping = shipping;
	}

	public String getAltShipping() {
		return GetterUtil.getString(_altShipping);
	}

	public void setAltShipping(String altShipping) {
		_altShipping = altShipping;
	}

	public boolean getRequiresShipping() {
		return _requiresShipping;
	}

	public boolean isRequiresShipping() {
		return _requiresShipping;
	}

	public void setRequiresShipping(boolean requiresShipping) {
		_requiresShipping = requiresShipping;
	}

	public boolean getInsure() {
		return _insure;
	}

	public boolean isInsure() {
		return _insure;
	}

	public void setInsure(boolean insure) {
		_insure = insure;
	}

	public double getInsurance() {
		return _insurance;
	}

	public void setInsurance(double insurance) {
		_insurance = insurance;
	}

	public String getCouponCodes() {
		return GetterUtil.getString(_couponCodes);
	}

	public void setCouponCodes(String couponCodes) {
		_couponCodes = couponCodes;
	}

	public double getCouponDiscount() {
		return _couponDiscount;
	}

	public void setCouponDiscount(double couponDiscount) {
		_couponDiscount = couponDiscount;
	}

	public String getBillingFirstName() {
		return GetterUtil.getString(_billingFirstName);
	}

	public void setBillingFirstName(String billingFirstName) {
		_billingFirstName = billingFirstName;
	}

	public String getBillingLastName() {
		return GetterUtil.getString(_billingLastName);
	}

	public void setBillingLastName(String billingLastName) {
		_billingLastName = billingLastName;
	}

	public String getBillingEmailAddress() {
		return GetterUtil.getString(_billingEmailAddress);
	}

	public void setBillingEmailAddress(String billingEmailAddress) {
		_billingEmailAddress = billingEmailAddress;
	}

	public String getBillingCompany() {
		return GetterUtil.getString(_billingCompany);
	}

	public void setBillingCompany(String billingCompany) {
		_billingCompany = billingCompany;
	}

	public String getBillingStreet() {
		return GetterUtil.getString(_billingStreet);
	}

	public void setBillingStreet(String billingStreet) {
		_billingStreet = billingStreet;
	}

	public String getBillingCity() {
		return GetterUtil.getString(_billingCity);
	}

	public void setBillingCity(String billingCity) {
		_billingCity = billingCity;
	}

	public String getBillingState() {
		return GetterUtil.getString(_billingState);
	}

	public void setBillingState(String billingState) {
		_billingState = billingState;
	}

	public String getBillingZip() {
		return GetterUtil.getString(_billingZip);
	}

	public void setBillingZip(String billingZip) {
		_billingZip = billingZip;
	}

	public String getBillingCountry() {
		return GetterUtil.getString(_billingCountry);
	}

	public void setBillingCountry(String billingCountry) {
		_billingCountry = billingCountry;
	}

	public String getBillingPhone() {
		return GetterUtil.getString(_billingPhone);
	}

	public void setBillingPhone(String billingPhone) {
		_billingPhone = billingPhone;
	}

	public boolean getShipToBilling() {
		return _shipToBilling;
	}

	public boolean isShipToBilling() {
		return _shipToBilling;
	}

	public void setShipToBilling(boolean shipToBilling) {
		_shipToBilling = shipToBilling;
	}

	public String getShippingFirstName() {
		return GetterUtil.getString(_shippingFirstName);
	}

	public void setShippingFirstName(String shippingFirstName) {
		_shippingFirstName = shippingFirstName;
	}

	public String getShippingLastName() {
		return GetterUtil.getString(_shippingLastName);
	}

	public void setShippingLastName(String shippingLastName) {
		_shippingLastName = shippingLastName;
	}

	public String getShippingEmailAddress() {
		return GetterUtil.getString(_shippingEmailAddress);
	}

	public void setShippingEmailAddress(String shippingEmailAddress) {
		_shippingEmailAddress = shippingEmailAddress;
	}

	public String getShippingCompany() {
		return GetterUtil.getString(_shippingCompany);
	}

	public void setShippingCompany(String shippingCompany) {
		_shippingCompany = shippingCompany;
	}

	public String getShippingStreet() {
		return GetterUtil.getString(_shippingStreet);
	}

	public void setShippingStreet(String shippingStreet) {
		_shippingStreet = shippingStreet;
	}

	public String getShippingCity() {
		return GetterUtil.getString(_shippingCity);
	}

	public void setShippingCity(String shippingCity) {
		_shippingCity = shippingCity;
	}

	public String getShippingState() {
		return GetterUtil.getString(_shippingState);
	}

	public void setShippingState(String shippingState) {
		_shippingState = shippingState;
	}

	public String getShippingZip() {
		return GetterUtil.getString(_shippingZip);
	}

	public void setShippingZip(String shippingZip) {
		_shippingZip = shippingZip;
	}

	public String getShippingCountry() {
		return GetterUtil.getString(_shippingCountry);
	}

	public void setShippingCountry(String shippingCountry) {
		_shippingCountry = shippingCountry;
	}

	public String getShippingPhone() {
		return GetterUtil.getString(_shippingPhone);
	}

	public void setShippingPhone(String shippingPhone) {
		_shippingPhone = shippingPhone;
	}

	public String getCcName() {
		return GetterUtil.getString(_ccName);
	}

	public void setCcName(String ccName) {
		_ccName = ccName;
	}

	public String getCcType() {
		return GetterUtil.getString(_ccType);
	}

	public void setCcType(String ccType) {
		_ccType = ccType;
	}

	public String getCcNumber() {
		return GetterUtil.getString(_ccNumber);
	}

	public void setCcNumber(String ccNumber) {
		_ccNumber = ccNumber;
	}

	public int getCcExpMonth() {
		return _ccExpMonth;
	}

	public void setCcExpMonth(int ccExpMonth) {
		_ccExpMonth = ccExpMonth;
	}

	public int getCcExpYear() {
		return _ccExpYear;
	}

	public void setCcExpYear(int ccExpYear) {
		_ccExpYear = ccExpYear;
	}

	public String getCcVerNumber() {
		return GetterUtil.getString(_ccVerNumber);
	}

	public void setCcVerNumber(String ccVerNumber) {
		_ccVerNumber = ccVerNumber;
	}

	public String getComments() {
		return GetterUtil.getString(_comments);
	}

	public void setComments(String comments) {
		_comments = comments;
	}

	public String getPpTxnId() {
		return GetterUtil.getString(_ppTxnId);
	}

	public void setPpTxnId(String ppTxnId) {
		_ppTxnId = ppTxnId;

		if (_originalPpTxnId == null) {
			_originalPpTxnId = ppTxnId;
		}
	}

	public String getOriginalPpTxnId() {
		return GetterUtil.getString(_originalPpTxnId);
	}

	public String getPpPaymentStatus() {
		return GetterUtil.getString(_ppPaymentStatus);
	}

	public void setPpPaymentStatus(String ppPaymentStatus) {
		_ppPaymentStatus = ppPaymentStatus;
	}

	public double getPpPaymentGross() {
		return _ppPaymentGross;
	}

	public void setPpPaymentGross(double ppPaymentGross) {
		_ppPaymentGross = ppPaymentGross;
	}

	public String getPpReceiverEmail() {
		return GetterUtil.getString(_ppReceiverEmail);
	}

	public void setPpReceiverEmail(String ppReceiverEmail) {
		_ppReceiverEmail = ppReceiverEmail;
	}

	public String getPpPayerEmail() {
		return GetterUtil.getString(_ppPayerEmail);
	}

	public void setPpPayerEmail(String ppPayerEmail) {
		_ppPayerEmail = ppPayerEmail;
	}

	public boolean getSendOrderEmail() {
		return _sendOrderEmail;
	}

	public boolean isSendOrderEmail() {
		return _sendOrderEmail;
	}

	public void setSendOrderEmail(boolean sendOrderEmail) {
		_sendOrderEmail = sendOrderEmail;
	}

	public boolean getSendShippingEmail() {
		return _sendShippingEmail;
	}

	public boolean isSendShippingEmail() {
		return _sendShippingEmail;
	}

	public void setSendShippingEmail(boolean sendShippingEmail) {
		_sendShippingEmail = sendShippingEmail;
	}

	public ShoppingOrder toEscapedModel() {
		if (isEscapedModel()) {
			return (ShoppingOrder)this;
		}
		else {
			ShoppingOrder model = new ShoppingOrderImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setOrderId(getOrderId());
			model.setGroupId(getGroupId());
			model.setCompanyId(getCompanyId());
			model.setUserId(getUserId());
			model.setUserName(HtmlUtil.escape(getUserName()));
			model.setCreateDate(getCreateDate());
			model.setModifiedDate(getModifiedDate());
			model.setNumber(HtmlUtil.escape(getNumber()));
			model.setTax(getTax());
			model.setShipping(getShipping());
			model.setAltShipping(HtmlUtil.escape(getAltShipping()));
			model.setRequiresShipping(getRequiresShipping());
			model.setInsure(getInsure());
			model.setInsurance(getInsurance());
			model.setCouponCodes(HtmlUtil.escape(getCouponCodes()));
			model.setCouponDiscount(getCouponDiscount());
			model.setBillingFirstName(HtmlUtil.escape(getBillingFirstName()));
			model.setBillingLastName(HtmlUtil.escape(getBillingLastName()));
			model.setBillingEmailAddress(HtmlUtil.escape(
					getBillingEmailAddress()));
			model.setBillingCompany(HtmlUtil.escape(getBillingCompany()));
			model.setBillingStreet(HtmlUtil.escape(getBillingStreet()));
			model.setBillingCity(HtmlUtil.escape(getBillingCity()));
			model.setBillingState(HtmlUtil.escape(getBillingState()));
			model.setBillingZip(HtmlUtil.escape(getBillingZip()));
			model.setBillingCountry(HtmlUtil.escape(getBillingCountry()));
			model.setBillingPhone(HtmlUtil.escape(getBillingPhone()));
			model.setShipToBilling(getShipToBilling());
			model.setShippingFirstName(HtmlUtil.escape(getShippingFirstName()));
			model.setShippingLastName(HtmlUtil.escape(getShippingLastName()));
			model.setShippingEmailAddress(HtmlUtil.escape(
					getShippingEmailAddress()));
			model.setShippingCompany(HtmlUtil.escape(getShippingCompany()));
			model.setShippingStreet(HtmlUtil.escape(getShippingStreet()));
			model.setShippingCity(HtmlUtil.escape(getShippingCity()));
			model.setShippingState(HtmlUtil.escape(getShippingState()));
			model.setShippingZip(HtmlUtil.escape(getShippingZip()));
			model.setShippingCountry(HtmlUtil.escape(getShippingCountry()));
			model.setShippingPhone(HtmlUtil.escape(getShippingPhone()));
			model.setCcName(HtmlUtil.escape(getCcName()));
			model.setCcType(HtmlUtil.escape(getCcType()));
			model.setCcNumber(HtmlUtil.escape(getCcNumber()));
			model.setCcExpMonth(getCcExpMonth());
			model.setCcExpYear(getCcExpYear());
			model.setCcVerNumber(HtmlUtil.escape(getCcVerNumber()));
			model.setComments(HtmlUtil.escape(getComments()));
			model.setPpTxnId(HtmlUtil.escape(getPpTxnId()));
			model.setPpPaymentStatus(HtmlUtil.escape(getPpPaymentStatus()));
			model.setPpPaymentGross(getPpPaymentGross());
			model.setPpReceiverEmail(HtmlUtil.escape(getPpReceiverEmail()));
			model.setPpPayerEmail(HtmlUtil.escape(getPpPayerEmail()));
			model.setSendOrderEmail(getSendOrderEmail());
			model.setSendShippingEmail(getSendShippingEmail());

			model = (ShoppingOrder)Proxy.newProxyInstance(ShoppingOrder.class.getClassLoader(),
					new Class[] { ShoppingOrder.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = new ExpandoBridgeImpl(ShoppingOrder.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public Object clone() {
		ShoppingOrderImpl clone = new ShoppingOrderImpl();

		clone.setOrderId(getOrderId());
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setNumber(getNumber());
		clone.setTax(getTax());
		clone.setShipping(getShipping());
		clone.setAltShipping(getAltShipping());
		clone.setRequiresShipping(getRequiresShipping());
		clone.setInsure(getInsure());
		clone.setInsurance(getInsurance());
		clone.setCouponCodes(getCouponCodes());
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

	public int compareTo(ShoppingOrder shoppingOrder) {
		int value = 0;

		value = DateUtil.compareTo(getCreateDate(),
				shoppingOrder.getCreateDate());

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

		long pk = shoppingOrder.getPrimaryKey();

		if (getPrimaryKey() == pk) {
			return true;
		}
		else {
			return false;
		}
	}

	public int hashCode() {
		return (int)getPrimaryKey();
	}

	public String toHtmlString() {
		StringBuilder sb = new StringBuilder();

		sb.append("<table class=\"lfr-table\">\n");

		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>orderId</b></td><td>" +
			getOrderId() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>groupId</b></td><td>" +
			getGroupId() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>companyId</b></td><td>" +
			getCompanyId() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>userId</b></td><td>" +
			getUserId() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>userName</b></td><td>" +
			getUserName() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>createDate</b></td><td>" +
			getCreateDate() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>modifiedDate</b></td><td>" +
			getModifiedDate() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>number</b></td><td>" +
			getNumber() + "</td></tr>\n");
		sb.append("<tr><td align=\"right\" valign=\"top\"><b>tax</b></td><td>" +
			getTax() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>shipping</b></td><td>" +
			getShipping() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>altShipping</b></td><td>" +
			getAltShipping() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>requiresShipping</b></td><td>" +
			getRequiresShipping() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>insure</b></td><td>" +
			getInsure() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>insurance</b></td><td>" +
			getInsurance() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>couponCodes</b></td><td>" +
			getCouponCodes() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>couponDiscount</b></td><td>" +
			getCouponDiscount() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>billingFirstName</b></td><td>" +
			getBillingFirstName() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>billingLastName</b></td><td>" +
			getBillingLastName() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>billingEmailAddress</b></td><td>" +
			getBillingEmailAddress() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>billingCompany</b></td><td>" +
			getBillingCompany() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>billingStreet</b></td><td>" +
			getBillingStreet() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>billingCity</b></td><td>" +
			getBillingCity() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>billingState</b></td><td>" +
			getBillingState() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>billingZip</b></td><td>" +
			getBillingZip() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>billingCountry</b></td><td>" +
			getBillingCountry() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>billingPhone</b></td><td>" +
			getBillingPhone() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>shipToBilling</b></td><td>" +
			getShipToBilling() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>shippingFirstName</b></td><td>" +
			getShippingFirstName() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>shippingLastName</b></td><td>" +
			getShippingLastName() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>shippingEmailAddress</b></td><td>" +
			getShippingEmailAddress() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>shippingCompany</b></td><td>" +
			getShippingCompany() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>shippingStreet</b></td><td>" +
			getShippingStreet() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>shippingCity</b></td><td>" +
			getShippingCity() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>shippingState</b></td><td>" +
			getShippingState() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>shippingZip</b></td><td>" +
			getShippingZip() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>shippingCountry</b></td><td>" +
			getShippingCountry() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>shippingPhone</b></td><td>" +
			getShippingPhone() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>ccName</b></td><td>" +
			getCcName() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>ccType</b></td><td>" +
			getCcType() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>ccNumber</b></td><td>" +
			getCcNumber() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>ccExpMonth</b></td><td>" +
			getCcExpMonth() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>ccExpYear</b></td><td>" +
			getCcExpYear() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>ccVerNumber</b></td><td>" +
			getCcVerNumber() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>comments</b></td><td>" +
			getComments() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>ppTxnId</b></td><td>" +
			getPpTxnId() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>ppPaymentStatus</b></td><td>" +
			getPpPaymentStatus() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>ppPaymentGross</b></td><td>" +
			getPpPaymentGross() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>ppReceiverEmail</b></td><td>" +
			getPpReceiverEmail() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>ppPayerEmail</b></td><td>" +
			getPpPayerEmail() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>sendOrderEmail</b></td><td>" +
			getSendOrderEmail() + "</td></tr>\n");
		sb.append(
			"<tr><td align=\"right\" valign=\"top\"><b>sendShippingEmail</b></td><td>" +
			getSendShippingEmail() + "</td></tr>\n");

		sb.append("</table>");

		return sb.toString();
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();

		sb.append("com.liferay.portlet.shopping.model.ShoppingOrder (");

		sb.append("orderId: " + getOrderId() + ", ");
		sb.append("groupId: " + getGroupId() + ", ");
		sb.append("companyId: " + getCompanyId() + ", ");
		sb.append("userId: " + getUserId() + ", ");
		sb.append("userName: " + getUserName() + ", ");
		sb.append("createDate: " + getCreateDate() + ", ");
		sb.append("modifiedDate: " + getModifiedDate() + ", ");
		sb.append("number: " + getNumber() + ", ");
		sb.append("tax: " + getTax() + ", ");
		sb.append("shipping: " + getShipping() + ", ");
		sb.append("altShipping: " + getAltShipping() + ", ");
		sb.append("requiresShipping: " + getRequiresShipping() + ", ");
		sb.append("insure: " + getInsure() + ", ");
		sb.append("insurance: " + getInsurance() + ", ");
		sb.append("couponCodes: " + getCouponCodes() + ", ");
		sb.append("couponDiscount: " + getCouponDiscount() + ", ");
		sb.append("billingFirstName: " + getBillingFirstName() + ", ");
		sb.append("billingLastName: " + getBillingLastName() + ", ");
		sb.append("billingEmailAddress: " + getBillingEmailAddress() + ", ");
		sb.append("billingCompany: " + getBillingCompany() + ", ");
		sb.append("billingStreet: " + getBillingStreet() + ", ");
		sb.append("billingCity: " + getBillingCity() + ", ");
		sb.append("billingState: " + getBillingState() + ", ");
		sb.append("billingZip: " + getBillingZip() + ", ");
		sb.append("billingCountry: " + getBillingCountry() + ", ");
		sb.append("billingPhone: " + getBillingPhone() + ", ");
		sb.append("shipToBilling: " + getShipToBilling() + ", ");
		sb.append("shippingFirstName: " + getShippingFirstName() + ", ");
		sb.append("shippingLastName: " + getShippingLastName() + ", ");
		sb.append("shippingEmailAddress: " + getShippingEmailAddress() + ", ");
		sb.append("shippingCompany: " + getShippingCompany() + ", ");
		sb.append("shippingStreet: " + getShippingStreet() + ", ");
		sb.append("shippingCity: " + getShippingCity() + ", ");
		sb.append("shippingState: " + getShippingState() + ", ");
		sb.append("shippingZip: " + getShippingZip() + ", ");
		sb.append("shippingCountry: " + getShippingCountry() + ", ");
		sb.append("shippingPhone: " + getShippingPhone() + ", ");
		sb.append("ccName: " + getCcName() + ", ");
		sb.append("ccType: " + getCcType() + ", ");
		sb.append("ccNumber: " + getCcNumber() + ", ");
		sb.append("ccExpMonth: " + getCcExpMonth() + ", ");
		sb.append("ccExpYear: " + getCcExpYear() + ", ");
		sb.append("ccVerNumber: " + getCcVerNumber() + ", ");
		sb.append("comments: " + getComments() + ", ");
		sb.append("ppTxnId: " + getPpTxnId() + ", ");
		sb.append("ppPaymentStatus: " + getPpPaymentStatus() + ", ");
		sb.append("ppPaymentGross: " + getPpPaymentGross() + ", ");
		sb.append("ppReceiverEmail: " + getPpReceiverEmail() + ", ");
		sb.append("ppPayerEmail: " + getPpPayerEmail() + ", ");
		sb.append("sendOrderEmail: " + getSendOrderEmail() + ", ");
		sb.append("sendShippingEmail: " + getSendShippingEmail() + ", ");

		sb.append(")");

		return sb.toString();
	}

	private long _orderId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private String _number;
	private String _originalNumber;
	private double _tax;
	private double _shipping;
	private String _altShipping;
	private boolean _requiresShipping;
	private boolean _insure;
	private double _insurance;
	private String _couponCodes;
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
	private String _originalPpTxnId;
	private String _ppPaymentStatus;
	private double _ppPaymentGross;
	private String _ppReceiverEmail;
	private String _ppPayerEmail;
	private boolean _sendOrderEmail;
	private boolean _sendShippingEmail;
	private transient ExpandoBridge _expandoBridge;
}