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

package com.liferay.portal.model.impl;

import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.ContactSoap;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.util.PortalUtil;

import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.util.ExpandoBridgeFactoryUtil;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="ContactModelImpl.java.html"><b><i>View Source</i></b></a>
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
 * @see       ContactImpl
 * @see       com.liferay.portal.model.Contact
 * @see       com.liferay.portal.model.ContactModel
 * @generated
 */
public class ContactModelImpl extends BaseModelImpl<Contact> {
	public static final String TABLE_NAME = "Contact_";
	public static final Object[][] TABLE_COLUMNS = {
			{ "contactId", new Integer(Types.BIGINT) },
			{ "companyId", new Integer(Types.BIGINT) },
			{ "userId", new Integer(Types.BIGINT) },
			{ "userName", new Integer(Types.VARCHAR) },
			{ "createDate", new Integer(Types.TIMESTAMP) },
			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			{ "accountId", new Integer(Types.BIGINT) },
			{ "parentContactId", new Integer(Types.BIGINT) },
			{ "firstName", new Integer(Types.VARCHAR) },
			{ "middleName", new Integer(Types.VARCHAR) },
			{ "lastName", new Integer(Types.VARCHAR) },
			{ "prefixId", new Integer(Types.INTEGER) },
			{ "suffixId", new Integer(Types.INTEGER) },
			{ "male", new Integer(Types.BOOLEAN) },
			{ "birthday", new Integer(Types.TIMESTAMP) },
			{ "smsSn", new Integer(Types.VARCHAR) },
			{ "aimSn", new Integer(Types.VARCHAR) },
			{ "facebookSn", new Integer(Types.VARCHAR) },
			{ "icqSn", new Integer(Types.VARCHAR) },
			{ "jabberSn", new Integer(Types.VARCHAR) },
			{ "msnSn", new Integer(Types.VARCHAR) },
			{ "mySpaceSn", new Integer(Types.VARCHAR) },
			{ "skypeSn", new Integer(Types.VARCHAR) },
			{ "twitterSn", new Integer(Types.VARCHAR) },
			{ "ymSn", new Integer(Types.VARCHAR) },
			{ "employeeStatusId", new Integer(Types.VARCHAR) },
			{ "employeeNumber", new Integer(Types.VARCHAR) },
			{ "jobTitle", new Integer(Types.VARCHAR) },
			{ "jobClass", new Integer(Types.VARCHAR) },
			{ "hoursOfOperation", new Integer(Types.VARCHAR) }
		};
	public static final String TABLE_SQL_CREATE = "create table Contact_ (contactId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,accountId LONG,parentContactId LONG,firstName VARCHAR(75) null,middleName VARCHAR(75) null,lastName VARCHAR(75) null,prefixId INTEGER,suffixId INTEGER,male BOOLEAN,birthday DATE null,smsSn VARCHAR(75) null,aimSn VARCHAR(75) null,facebookSn VARCHAR(75) null,icqSn VARCHAR(75) null,jabberSn VARCHAR(75) null,msnSn VARCHAR(75) null,mySpaceSn VARCHAR(75) null,skypeSn VARCHAR(75) null,twitterSn VARCHAR(75) null,ymSn VARCHAR(75) null,employeeStatusId VARCHAR(75) null,employeeNumber VARCHAR(75) null,jobTitle VARCHAR(100) null,jobClass VARCHAR(75) null,hoursOfOperation VARCHAR(75) null)";
	public static final String TABLE_SQL_DROP = "drop table Contact_";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.entity.cache.enabled.com.liferay.portal.model.Contact"),
			true);
	public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portal.model.Contact"),
			true);

	public static Contact toModel(ContactSoap soapModel) {
		Contact model = new ContactImpl();

		model.setContactId(soapModel.getContactId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setAccountId(soapModel.getAccountId());
		model.setParentContactId(soapModel.getParentContactId());
		model.setFirstName(soapModel.getFirstName());
		model.setMiddleName(soapModel.getMiddleName());
		model.setLastName(soapModel.getLastName());
		model.setPrefixId(soapModel.getPrefixId());
		model.setSuffixId(soapModel.getSuffixId());
		model.setMale(soapModel.getMale());
		model.setBirthday(soapModel.getBirthday());
		model.setSmsSn(soapModel.getSmsSn());
		model.setAimSn(soapModel.getAimSn());
		model.setFacebookSn(soapModel.getFacebookSn());
		model.setIcqSn(soapModel.getIcqSn());
		model.setJabberSn(soapModel.getJabberSn());
		model.setMsnSn(soapModel.getMsnSn());
		model.setMySpaceSn(soapModel.getMySpaceSn());
		model.setSkypeSn(soapModel.getSkypeSn());
		model.setTwitterSn(soapModel.getTwitterSn());
		model.setYmSn(soapModel.getYmSn());
		model.setEmployeeStatusId(soapModel.getEmployeeStatusId());
		model.setEmployeeNumber(soapModel.getEmployeeNumber());
		model.setJobTitle(soapModel.getJobTitle());
		model.setJobClass(soapModel.getJobClass());
		model.setHoursOfOperation(soapModel.getHoursOfOperation());

		return model;
	}

	public static List<Contact> toModels(ContactSoap[] soapModels) {
		List<Contact> models = new ArrayList<Contact>(soapModels.length);

		for (ContactSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portal.model.Contact"));

	public ContactModelImpl() {
	}

	public long getPrimaryKey() {
		return _contactId;
	}

	public void setPrimaryKey(long pk) {
		setContactId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_contactId);
	}

	public long getContactId() {
		return _contactId;
	}

	public void setContactId(long contactId) {
		_contactId = contactId;
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

	public String getUserUuid() throws SystemException {
		return PortalUtil.getUserValue(getUserId(), "uuid", _userUuid);
	}

	public void setUserUuid(String userUuid) {
		_userUuid = userUuid;
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

	public long getAccountId() {
		return _accountId;
	}

	public void setAccountId(long accountId) {
		_accountId = accountId;
	}

	public long getParentContactId() {
		return _parentContactId;
	}

	public void setParentContactId(long parentContactId) {
		_parentContactId = parentContactId;
	}

	public String getFirstName() {
		return GetterUtil.getString(_firstName);
	}

	public void setFirstName(String firstName) {
		_firstName = firstName;
	}

	public String getMiddleName() {
		return GetterUtil.getString(_middleName);
	}

	public void setMiddleName(String middleName) {
		_middleName = middleName;
	}

	public String getLastName() {
		return GetterUtil.getString(_lastName);
	}

	public void setLastName(String lastName) {
		_lastName = lastName;
	}

	public int getPrefixId() {
		return _prefixId;
	}

	public void setPrefixId(int prefixId) {
		_prefixId = prefixId;
	}

	public int getSuffixId() {
		return _suffixId;
	}

	public void setSuffixId(int suffixId) {
		_suffixId = suffixId;
	}

	public boolean getMale() {
		return _male;
	}

	public boolean isMale() {
		return _male;
	}

	public void setMale(boolean male) {
		_male = male;
	}

	public Date getBirthday() {
		return _birthday;
	}

	public void setBirthday(Date birthday) {
		_birthday = birthday;
	}

	public String getSmsSn() {
		return GetterUtil.getString(_smsSn);
	}

	public void setSmsSn(String smsSn) {
		_smsSn = smsSn;
	}

	public String getAimSn() {
		return GetterUtil.getString(_aimSn);
	}

	public void setAimSn(String aimSn) {
		_aimSn = aimSn;
	}

	public String getFacebookSn() {
		return GetterUtil.getString(_facebookSn);
	}

	public void setFacebookSn(String facebookSn) {
		_facebookSn = facebookSn;
	}

	public String getIcqSn() {
		return GetterUtil.getString(_icqSn);
	}

	public void setIcqSn(String icqSn) {
		_icqSn = icqSn;
	}

	public String getJabberSn() {
		return GetterUtil.getString(_jabberSn);
	}

	public void setJabberSn(String jabberSn) {
		_jabberSn = jabberSn;
	}

	public String getMsnSn() {
		return GetterUtil.getString(_msnSn);
	}

	public void setMsnSn(String msnSn) {
		_msnSn = msnSn;
	}

	public String getMySpaceSn() {
		return GetterUtil.getString(_mySpaceSn);
	}

	public void setMySpaceSn(String mySpaceSn) {
		_mySpaceSn = mySpaceSn;
	}

	public String getSkypeSn() {
		return GetterUtil.getString(_skypeSn);
	}

	public void setSkypeSn(String skypeSn) {
		_skypeSn = skypeSn;
	}

	public String getTwitterSn() {
		return GetterUtil.getString(_twitterSn);
	}

	public void setTwitterSn(String twitterSn) {
		_twitterSn = twitterSn;
	}

	public String getYmSn() {
		return GetterUtil.getString(_ymSn);
	}

	public void setYmSn(String ymSn) {
		_ymSn = ymSn;
	}

	public String getEmployeeStatusId() {
		return GetterUtil.getString(_employeeStatusId);
	}

	public void setEmployeeStatusId(String employeeStatusId) {
		_employeeStatusId = employeeStatusId;
	}

	public String getEmployeeNumber() {
		return GetterUtil.getString(_employeeNumber);
	}

	public void setEmployeeNumber(String employeeNumber) {
		_employeeNumber = employeeNumber;
	}

	public String getJobTitle() {
		return GetterUtil.getString(_jobTitle);
	}

	public void setJobTitle(String jobTitle) {
		_jobTitle = jobTitle;
	}

	public String getJobClass() {
		return GetterUtil.getString(_jobClass);
	}

	public void setJobClass(String jobClass) {
		_jobClass = jobClass;
	}

	public String getHoursOfOperation() {
		return GetterUtil.getString(_hoursOfOperation);
	}

	public void setHoursOfOperation(String hoursOfOperation) {
		_hoursOfOperation = hoursOfOperation;
	}

	public Contact toEscapedModel() {
		if (isEscapedModel()) {
			return (Contact)this;
		}
		else {
			Contact model = new ContactImpl();

			model.setNew(isNew());
			model.setEscapedModel(true);

			model.setContactId(getContactId());
			model.setCompanyId(getCompanyId());
			model.setUserId(getUserId());
			model.setUserName(HtmlUtil.escape(getUserName()));
			model.setCreateDate(getCreateDate());
			model.setModifiedDate(getModifiedDate());
			model.setAccountId(getAccountId());
			model.setParentContactId(getParentContactId());
			model.setFirstName(HtmlUtil.escape(getFirstName()));
			model.setMiddleName(HtmlUtil.escape(getMiddleName()));
			model.setLastName(HtmlUtil.escape(getLastName()));
			model.setPrefixId(getPrefixId());
			model.setSuffixId(getSuffixId());
			model.setMale(getMale());
			model.setBirthday(getBirthday());
			model.setSmsSn(HtmlUtil.escape(getSmsSn()));
			model.setAimSn(HtmlUtil.escape(getAimSn()));
			model.setFacebookSn(HtmlUtil.escape(getFacebookSn()));
			model.setIcqSn(HtmlUtil.escape(getIcqSn()));
			model.setJabberSn(HtmlUtil.escape(getJabberSn()));
			model.setMsnSn(HtmlUtil.escape(getMsnSn()));
			model.setMySpaceSn(HtmlUtil.escape(getMySpaceSn()));
			model.setSkypeSn(HtmlUtil.escape(getSkypeSn()));
			model.setTwitterSn(HtmlUtil.escape(getTwitterSn()));
			model.setYmSn(HtmlUtil.escape(getYmSn()));
			model.setEmployeeStatusId(HtmlUtil.escape(getEmployeeStatusId()));
			model.setEmployeeNumber(HtmlUtil.escape(getEmployeeNumber()));
			model.setJobTitle(HtmlUtil.escape(getJobTitle()));
			model.setJobClass(HtmlUtil.escape(getJobClass()));
			model.setHoursOfOperation(HtmlUtil.escape(getHoursOfOperation()));

			model = (Contact)Proxy.newProxyInstance(Contact.class.getClassLoader(),
					new Class[] { Contact.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public ExpandoBridge getExpandoBridge() {
		if (_expandoBridge == null) {
			_expandoBridge = ExpandoBridgeFactoryUtil.getExpandoBridge(Contact.class.getName(),
					getPrimaryKey());
		}

		return _expandoBridge;
	}

	public void setExpandoBridgeAttributes(ServiceContext serviceContext) {
		getExpandoBridge().setAttributes(serviceContext);
	}

	public Object clone() {
		ContactImpl clone = new ContactImpl();

		clone.setContactId(getContactId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setAccountId(getAccountId());
		clone.setParentContactId(getParentContactId());
		clone.setFirstName(getFirstName());
		clone.setMiddleName(getMiddleName());
		clone.setLastName(getLastName());
		clone.setPrefixId(getPrefixId());
		clone.setSuffixId(getSuffixId());
		clone.setMale(getMale());
		clone.setBirthday(getBirthday());
		clone.setSmsSn(getSmsSn());
		clone.setAimSn(getAimSn());
		clone.setFacebookSn(getFacebookSn());
		clone.setIcqSn(getIcqSn());
		clone.setJabberSn(getJabberSn());
		clone.setMsnSn(getMsnSn());
		clone.setMySpaceSn(getMySpaceSn());
		clone.setSkypeSn(getSkypeSn());
		clone.setTwitterSn(getTwitterSn());
		clone.setYmSn(getYmSn());
		clone.setEmployeeStatusId(getEmployeeStatusId());
		clone.setEmployeeNumber(getEmployeeNumber());
		clone.setJobTitle(getJobTitle());
		clone.setJobClass(getJobClass());
		clone.setHoursOfOperation(getHoursOfOperation());

		return clone;
	}

	public int compareTo(Contact contact) {
		long pk = contact.getPrimaryKey();

		if (getPrimaryKey() < pk) {
			return -1;
		}
		else if (getPrimaryKey() > pk) {
			return 1;
		}
		else {
			return 0;
		}
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		Contact contact = null;

		try {
			contact = (Contact)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = contact.getPrimaryKey();

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

	public String toString() {
		StringBundler sb = new StringBundler(61);

		sb.append("{contactId=");
		sb.append(getContactId());
		sb.append(", companyId=");
		sb.append(getCompanyId());
		sb.append(", userId=");
		sb.append(getUserId());
		sb.append(", userName=");
		sb.append(getUserName());
		sb.append(", createDate=");
		sb.append(getCreateDate());
		sb.append(", modifiedDate=");
		sb.append(getModifiedDate());
		sb.append(", accountId=");
		sb.append(getAccountId());
		sb.append(", parentContactId=");
		sb.append(getParentContactId());
		sb.append(", firstName=");
		sb.append(getFirstName());
		sb.append(", middleName=");
		sb.append(getMiddleName());
		sb.append(", lastName=");
		sb.append(getLastName());
		sb.append(", prefixId=");
		sb.append(getPrefixId());
		sb.append(", suffixId=");
		sb.append(getSuffixId());
		sb.append(", male=");
		sb.append(getMale());
		sb.append(", birthday=");
		sb.append(getBirthday());
		sb.append(", smsSn=");
		sb.append(getSmsSn());
		sb.append(", aimSn=");
		sb.append(getAimSn());
		sb.append(", facebookSn=");
		sb.append(getFacebookSn());
		sb.append(", icqSn=");
		sb.append(getIcqSn());
		sb.append(", jabberSn=");
		sb.append(getJabberSn());
		sb.append(", msnSn=");
		sb.append(getMsnSn());
		sb.append(", mySpaceSn=");
		sb.append(getMySpaceSn());
		sb.append(", skypeSn=");
		sb.append(getSkypeSn());
		sb.append(", twitterSn=");
		sb.append(getTwitterSn());
		sb.append(", ymSn=");
		sb.append(getYmSn());
		sb.append(", employeeStatusId=");
		sb.append(getEmployeeStatusId());
		sb.append(", employeeNumber=");
		sb.append(getEmployeeNumber());
		sb.append(", jobTitle=");
		sb.append(getJobTitle());
		sb.append(", jobClass=");
		sb.append(getJobClass());
		sb.append(", hoursOfOperation=");
		sb.append(getHoursOfOperation());
		sb.append("}");

		return sb.toString();
	}

	public String toXmlString() {
		StringBundler sb = new StringBundler(94);

		sb.append("<model><model-name>");
		sb.append("com.liferay.portal.model.Contact");
		sb.append("</model-name>");

		sb.append(
			"<column><column-name>contactId</column-name><column-value><![CDATA[");
		sb.append(getContactId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>companyId</column-name><column-value><![CDATA[");
		sb.append(getCompanyId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userId</column-name><column-value><![CDATA[");
		sb.append(getUserId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>userName</column-name><column-value><![CDATA[");
		sb.append(getUserName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>createDate</column-name><column-value><![CDATA[");
		sb.append(getCreateDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>modifiedDate</column-name><column-value><![CDATA[");
		sb.append(getModifiedDate());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>accountId</column-name><column-value><![CDATA[");
		sb.append(getAccountId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>parentContactId</column-name><column-value><![CDATA[");
		sb.append(getParentContactId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>firstName</column-name><column-value><![CDATA[");
		sb.append(getFirstName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>middleName</column-name><column-value><![CDATA[");
		sb.append(getMiddleName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>lastName</column-name><column-value><![CDATA[");
		sb.append(getLastName());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>prefixId</column-name><column-value><![CDATA[");
		sb.append(getPrefixId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>suffixId</column-name><column-value><![CDATA[");
		sb.append(getSuffixId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>male</column-name><column-value><![CDATA[");
		sb.append(getMale());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>birthday</column-name><column-value><![CDATA[");
		sb.append(getBirthday());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>smsSn</column-name><column-value><![CDATA[");
		sb.append(getSmsSn());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>aimSn</column-name><column-value><![CDATA[");
		sb.append(getAimSn());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>facebookSn</column-name><column-value><![CDATA[");
		sb.append(getFacebookSn());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>icqSn</column-name><column-value><![CDATA[");
		sb.append(getIcqSn());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>jabberSn</column-name><column-value><![CDATA[");
		sb.append(getJabberSn());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>msnSn</column-name><column-value><![CDATA[");
		sb.append(getMsnSn());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>mySpaceSn</column-name><column-value><![CDATA[");
		sb.append(getMySpaceSn());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>skypeSn</column-name><column-value><![CDATA[");
		sb.append(getSkypeSn());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>twitterSn</column-name><column-value><![CDATA[");
		sb.append(getTwitterSn());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>ymSn</column-name><column-value><![CDATA[");
		sb.append(getYmSn());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>employeeStatusId</column-name><column-value><![CDATA[");
		sb.append(getEmployeeStatusId());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>employeeNumber</column-name><column-value><![CDATA[");
		sb.append(getEmployeeNumber());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>jobTitle</column-name><column-value><![CDATA[");
		sb.append(getJobTitle());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>jobClass</column-name><column-value><![CDATA[");
		sb.append(getJobClass());
		sb.append("]]></column-value></column>");
		sb.append(
			"<column><column-name>hoursOfOperation</column-name><column-value><![CDATA[");
		sb.append(getHoursOfOperation());
		sb.append("]]></column-value></column>");

		sb.append("</model>");

		return sb.toString();
	}

	private long _contactId;
	private long _companyId;
	private long _userId;
	private String _userUuid;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _accountId;
	private long _parentContactId;
	private String _firstName;
	private String _middleName;
	private String _lastName;
	private int _prefixId;
	private int _suffixId;
	private boolean _male;
	private Date _birthday;
	private String _smsSn;
	private String _aimSn;
	private String _facebookSn;
	private String _icqSn;
	private String _jabberSn;
	private String _msnSn;
	private String _mySpaceSn;
	private String _skypeSn;
	private String _twitterSn;
	private String _ymSn;
	private String _employeeStatusId;
	private String _employeeNumber;
	private String _jobTitle;
	private String _jobClass;
	private String _hoursOfOperation;
	private transient ExpandoBridge _expandoBridge;
}