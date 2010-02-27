/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.shopping.model;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.BaseModel;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;

/**
 * <a href="ShoppingOrderModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the ShoppingOrder table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingOrder
 * @see       com.liferay.portlet.shopping.model.impl.ShoppingOrderImpl
 * @see       com.liferay.portlet.shopping.model.impl.ShoppingOrderModelImpl
 * @generated
 */
public interface ShoppingOrderModel extends BaseModel<ShoppingOrder> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getOrderId();

	public void setOrderId(long orderId);

	public long getGroupId();

	public void setGroupId(long groupId);

	public long getCompanyId();

	public void setCompanyId(long companyId);

	public long getUserId();

	public void setUserId(long userId);

	public String getUserUuid() throws SystemException;

	public void setUserUuid(String userUuid);

	public String getUserName();

	public void setUserName(String userName);

	public Date getCreateDate();

	public void setCreateDate(Date createDate);

	public Date getModifiedDate();

	public void setModifiedDate(Date modifiedDate);

	public String getNumber();

	public void setNumber(String number);

	public double getTax();

	public void setTax(double tax);

	public double getShipping();

	public void setShipping(double shipping);

	public String getAltShipping();

	public void setAltShipping(String altShipping);

	public boolean getRequiresShipping();

	public boolean isRequiresShipping();

	public void setRequiresShipping(boolean requiresShipping);

	public boolean getInsure();

	public boolean isInsure();

	public void setInsure(boolean insure);

	public double getInsurance();

	public void setInsurance(double insurance);

	public String getCouponCodes();

	public void setCouponCodes(String couponCodes);

	public double getCouponDiscount();

	public void setCouponDiscount(double couponDiscount);

	public String getBillingFirstName();

	public void setBillingFirstName(String billingFirstName);

	public String getBillingLastName();

	public void setBillingLastName(String billingLastName);

	public String getBillingEmailAddress();

	public void setBillingEmailAddress(String billingEmailAddress);

	public String getBillingCompany();

	public void setBillingCompany(String billingCompany);

	public String getBillingStreet();

	public void setBillingStreet(String billingStreet);

	public String getBillingCity();

	public void setBillingCity(String billingCity);

	public String getBillingState();

	public void setBillingState(String billingState);

	public String getBillingZip();

	public void setBillingZip(String billingZip);

	public String getBillingCountry();

	public void setBillingCountry(String billingCountry);

	public String getBillingPhone();

	public void setBillingPhone(String billingPhone);

	public boolean getShipToBilling();

	public boolean isShipToBilling();

	public void setShipToBilling(boolean shipToBilling);

	public String getShippingFirstName();

	public void setShippingFirstName(String shippingFirstName);

	public String getShippingLastName();

	public void setShippingLastName(String shippingLastName);

	public String getShippingEmailAddress();

	public void setShippingEmailAddress(String shippingEmailAddress);

	public String getShippingCompany();

	public void setShippingCompany(String shippingCompany);

	public String getShippingStreet();

	public void setShippingStreet(String shippingStreet);

	public String getShippingCity();

	public void setShippingCity(String shippingCity);

	public String getShippingState();

	public void setShippingState(String shippingState);

	public String getShippingZip();

	public void setShippingZip(String shippingZip);

	public String getShippingCountry();

	public void setShippingCountry(String shippingCountry);

	public String getShippingPhone();

	public void setShippingPhone(String shippingPhone);

	public String getCcName();

	public void setCcName(String ccName);

	public String getCcType();

	public void setCcType(String ccType);

	public String getCcNumber();

	public void setCcNumber(String ccNumber);

	public int getCcExpMonth();

	public void setCcExpMonth(int ccExpMonth);

	public int getCcExpYear();

	public void setCcExpYear(int ccExpYear);

	public String getCcVerNumber();

	public void setCcVerNumber(String ccVerNumber);

	public String getComments();

	public void setComments(String comments);

	public String getPpTxnId();

	public void setPpTxnId(String ppTxnId);

	public String getPpPaymentStatus();

	public void setPpPaymentStatus(String ppPaymentStatus);

	public double getPpPaymentGross();

	public void setPpPaymentGross(double ppPaymentGross);

	public String getPpReceiverEmail();

	public void setPpReceiverEmail(String ppReceiverEmail);

	public String getPpPayerEmail();

	public void setPpPayerEmail(String ppPayerEmail);

	public boolean getSendOrderEmail();

	public boolean isSendOrderEmail();

	public void setSendOrderEmail(boolean sendOrderEmail);

	public boolean getSendShippingEmail();

	public boolean isSendShippingEmail();

	public void setSendShippingEmail(boolean sendShippingEmail);

	public ShoppingOrder toEscapedModel();

	public boolean isNew();

	public boolean setNew(boolean n);

	public boolean isCachedModel();

	public void setCachedModel(boolean cachedModel);

	public boolean isEscapedModel();

	public void setEscapedModel(boolean escapedModel);

	public Serializable getPrimaryKeyObj();

	public ExpandoBridge getExpandoBridge();

	public void setExpandoBridgeAttributes(ServiceContext serviceContext);

	public Object clone();

	public int compareTo(ShoppingOrder shoppingOrder);

	public int hashCode();

	public String toString();

	public String toXmlString();
}