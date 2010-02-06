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

package com.liferay.portal.model;

import com.liferay.portal.SystemException;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.expando.model.ExpandoBridge;

import java.io.Serializable;

import java.util.Date;

/**
 * <a href="ContactModel.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface is a model that represents the Contact_ table in the
 * database.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Contact
 * @see       com.liferay.portal.model.impl.ContactImpl
 * @see       com.liferay.portal.model.impl.ContactModelImpl
 * @generated
 */
public interface ContactModel extends BaseModel<Contact> {
	public long getPrimaryKey();

	public void setPrimaryKey(long pk);

	public long getContactId();

	public void setContactId(long contactId);

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

	public long getAccountId();

	public void setAccountId(long accountId);

	public long getParentContactId();

	public void setParentContactId(long parentContactId);

	public String getFirstName();

	public void setFirstName(String firstName);

	public String getMiddleName();

	public void setMiddleName(String middleName);

	public String getLastName();

	public void setLastName(String lastName);

	public int getPrefixId();

	public void setPrefixId(int prefixId);

	public int getSuffixId();

	public void setSuffixId(int suffixId);

	public boolean getMale();

	public boolean isMale();

	public void setMale(boolean male);

	public Date getBirthday();

	public void setBirthday(Date birthday);

	public String getSmsSn();

	public void setSmsSn(String smsSn);

	public String getAimSn();

	public void setAimSn(String aimSn);

	public String getFacebookSn();

	public void setFacebookSn(String facebookSn);

	public String getIcqSn();

	public void setIcqSn(String icqSn);

	public String getJabberSn();

	public void setJabberSn(String jabberSn);

	public String getMsnSn();

	public void setMsnSn(String msnSn);

	public String getMySpaceSn();

	public void setMySpaceSn(String mySpaceSn);

	public String getSkypeSn();

	public void setSkypeSn(String skypeSn);

	public String getTwitterSn();

	public void setTwitterSn(String twitterSn);

	public String getYmSn();

	public void setYmSn(String ymSn);

	public String getEmployeeStatusId();

	public void setEmployeeStatusId(String employeeStatusId);

	public String getEmployeeNumber();

	public void setEmployeeNumber(String employeeNumber);

	public String getJobTitle();

	public void setJobTitle(String jobTitle);

	public String getJobClass();

	public void setJobClass(String jobClass);

	public String getHoursOfOperation();

	public void setHoursOfOperation(String hoursOfOperation);

	public Contact toEscapedModel();

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

	public int compareTo(Contact contact);

	public int hashCode();

	public String toString();

	public String toXmlString();
}