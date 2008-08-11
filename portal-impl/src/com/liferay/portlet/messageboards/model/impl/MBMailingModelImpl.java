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

package com.liferay.portlet.messageboards.model.impl;

import com.liferay.portal.kernel.bean.ReadOnlyBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.model.impl.BaseModelImpl;

import com.liferay.portlet.messageboards.model.MBMailing;
import com.liferay.portlet.messageboards.model.MBMailingSoap;

import java.io.Serializable;

import java.lang.reflect.Proxy;

import java.sql.Types;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="MBMailingModelImpl.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a model that represents the <code>MBMailing</code> table
 * in the database.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.messageboards.service.model.MBMailing
 * @see com.liferay.portlet.messageboards.service.model.MBMailingModel
 * @see com.liferay.portlet.messageboards.service.model.impl.MBMailingImpl
 *
 */
public class MBMailingModelImpl extends BaseModelImpl {
	public static final String TABLE_NAME = "MBMailing";
	public static final Object[][] TABLE_COLUMNS = {
			{ "uuid_", new Integer(Types.VARCHAR) },
			

			{ "mailingId", new Integer(Types.BIGINT) },
			

			{ "groupId", new Integer(Types.BIGINT) },
			

			{ "companyId", new Integer(Types.BIGINT) },
			

			{ "userId", new Integer(Types.BIGINT) },
			

			{ "userName", new Integer(Types.VARCHAR) },
			

			{ "createDate", new Integer(Types.TIMESTAMP) },
			

			{ "modifiedDate", new Integer(Types.TIMESTAMP) },
			

			{ "categoryId", new Integer(Types.BIGINT) },
			

			{ "mailingListAddress", new Integer(Types.VARCHAR) },
			

			{ "mailAddress", new Integer(Types.VARCHAR) },
			

			{ "mailInProtocol", new Integer(Types.VARCHAR) },
			

			{ "mailInServerName", new Integer(Types.VARCHAR) },
			

			{ "mailInUseSSL", new Integer(Types.BOOLEAN) },
			

			{ "mailInServerPort", new Integer(Types.INTEGER) },
			

			{ "mailInUserName", new Integer(Types.VARCHAR) },
			

			{ "mailInPassword", new Integer(Types.VARCHAR) },
			

			{ "mailInReadInterval", new Integer(Types.INTEGER) },
			

			{ "mailOutConfigured", new Integer(Types.BOOLEAN) },
			

			{ "mailOutServerName", new Integer(Types.VARCHAR) },
			

			{ "mailOutUseSSL", new Integer(Types.BOOLEAN) },
			

			{ "mailOutServerPort", new Integer(Types.INTEGER) },
			

			{ "mailOutUserName", new Integer(Types.VARCHAR) },
			

			{ "mailOutPassword", new Integer(Types.VARCHAR) },
			

			{ "active_", new Integer(Types.BOOLEAN) }
		};
	public static final String TABLE_SQL_CREATE = "create table MBMailing (uuid_ VARCHAR(75) null,mailingId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,categoryId LONG,mailingListAddress VARCHAR(75) null,mailAddress VARCHAR(75) null,mailInProtocol VARCHAR(75) null,mailInServerName VARCHAR(75) null,mailInUseSSL BOOLEAN,mailInServerPort INTEGER,mailInUserName VARCHAR(75) null,mailInPassword VARCHAR(75) null,mailInReadInterval INTEGER,mailOutConfigured BOOLEAN,mailOutServerName VARCHAR(75) null,mailOutUseSSL BOOLEAN,mailOutServerPort INTEGER,mailOutUserName VARCHAR(75) null,mailOutPassword VARCHAR(75) null,active_ BOOLEAN)";
	public static final String TABLE_SQL_DROP = "drop table MBMailing";
	public static final String DATA_SOURCE = "liferayDataSource";
	public static final String SESSION_FACTORY = "liferaySessionFactory";
	public static final String TX_MANAGER = "liferayTransactionManager";
	public static final boolean CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.portal.util.PropsUtil.get(
				"value.object.finder.cache.enabled.com.liferay.portlet.messageboards.model.MBMailing"),
			true);

	public static MBMailing toModel(MBMailingSoap soapModel) {
		MBMailing model = new MBMailingImpl();

		model.setUuid(soapModel.getUuid());
		model.setMailingId(soapModel.getMailingId());
		model.setGroupId(soapModel.getGroupId());
		model.setCompanyId(soapModel.getCompanyId());
		model.setUserId(soapModel.getUserId());
		model.setUserName(soapModel.getUserName());
		model.setCreateDate(soapModel.getCreateDate());
		model.setModifiedDate(soapModel.getModifiedDate());
		model.setCategoryId(soapModel.getCategoryId());
		model.setMailingListAddress(soapModel.getMailingListAddress());
		model.setMailAddress(soapModel.getMailAddress());
		model.setMailInProtocol(soapModel.getMailInProtocol());
		model.setMailInServerName(soapModel.getMailInServerName());
		model.setMailInUseSSL(soapModel.getMailInUseSSL());
		model.setMailInServerPort(soapModel.getMailInServerPort());
		model.setMailInUserName(soapModel.getMailInUserName());
		model.setMailInPassword(soapModel.getMailInPassword());
		model.setMailInReadInterval(soapModel.getMailInReadInterval());
		model.setMailOutConfigured(soapModel.getMailOutConfigured());
		model.setMailOutServerName(soapModel.getMailOutServerName());
		model.setMailOutUseSSL(soapModel.getMailOutUseSSL());
		model.setMailOutServerPort(soapModel.getMailOutServerPort());
		model.setMailOutUserName(soapModel.getMailOutUserName());
		model.setMailOutPassword(soapModel.getMailOutPassword());
		model.setActive(soapModel.getActive());

		return model;
	}

	public static List<MBMailing> toModels(MBMailingSoap[] soapModels) {
		List<MBMailing> models = new ArrayList<MBMailing>(soapModels.length);

		for (MBMailingSoap soapModel : soapModels) {
			models.add(toModel(soapModel));
		}

		return models;
	}

	public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.portal.util.PropsUtil.get(
				"lock.expiration.time.com.liferay.portlet.messageboards.model.MBMailing"));

	public MBMailingModelImpl() {
	}

	public long getPrimaryKey() {
		return _mailingId;
	}

	public void setPrimaryKey(long pk) {
		setMailingId(pk);
	}

	public Serializable getPrimaryKeyObj() {
		return new Long(_mailingId);
	}

	public String getUuid() {
		return GetterUtil.getString(_uuid);
	}

	public void setUuid(String uuid) {
		if ((uuid != null) && (uuid != _uuid)) {
			_uuid = uuid;
		}
	}

	public long getMailingId() {
		return _mailingId;
	}

	public void setMailingId(long mailingId) {
		if (mailingId != _mailingId) {
			_mailingId = mailingId;
		}
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		if (groupId != _groupId) {
			_groupId = groupId;
		}
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		if (companyId != _companyId) {
			_companyId = companyId;
		}
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		if (userId != _userId) {
			_userId = userId;
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
			_userName = userName;
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
		}
	}

	public long getCategoryId() {
		return _categoryId;
	}

	public void setCategoryId(long categoryId) {
		if (categoryId != _categoryId) {
			_categoryId = categoryId;
		}
	}

	public String getMailingListAddress() {
		return GetterUtil.getString(_mailingListAddress);
	}

	public void setMailingListAddress(String mailingListAddress) {
		if (((mailingListAddress == null) && (_mailingListAddress != null)) ||
				((mailingListAddress != null) && (_mailingListAddress == null)) ||
				((mailingListAddress != null) && (_mailingListAddress != null) &&
				!mailingListAddress.equals(_mailingListAddress))) {
			_mailingListAddress = mailingListAddress;
		}
	}

	public String getMailAddress() {
		return GetterUtil.getString(_mailAddress);
	}

	public void setMailAddress(String mailAddress) {
		if (((mailAddress == null) && (_mailAddress != null)) ||
				((mailAddress != null) && (_mailAddress == null)) ||
				((mailAddress != null) && (_mailAddress != null) &&
				!mailAddress.equals(_mailAddress))) {
			_mailAddress = mailAddress;
		}
	}

	public String getMailInProtocol() {
		return GetterUtil.getString(_mailInProtocol);
	}

	public void setMailInProtocol(String mailInProtocol) {
		if (((mailInProtocol == null) && (_mailInProtocol != null)) ||
				((mailInProtocol != null) && (_mailInProtocol == null)) ||
				((mailInProtocol != null) && (_mailInProtocol != null) &&
				!mailInProtocol.equals(_mailInProtocol))) {
			_mailInProtocol = mailInProtocol;
		}
	}

	public String getMailInServerName() {
		return GetterUtil.getString(_mailInServerName);
	}

	public void setMailInServerName(String mailInServerName) {
		if (((mailInServerName == null) && (_mailInServerName != null)) ||
				((mailInServerName != null) && (_mailInServerName == null)) ||
				((mailInServerName != null) && (_mailInServerName != null) &&
				!mailInServerName.equals(_mailInServerName))) {
			_mailInServerName = mailInServerName;
		}
	}

	public boolean getMailInUseSSL() {
		return _mailInUseSSL;
	}

	public boolean isMailInUseSSL() {
		return _mailInUseSSL;
	}

	public void setMailInUseSSL(boolean mailInUseSSL) {
		if (mailInUseSSL != _mailInUseSSL) {
			_mailInUseSSL = mailInUseSSL;
		}
	}

	public int getMailInServerPort() {
		return _mailInServerPort;
	}

	public void setMailInServerPort(int mailInServerPort) {
		if (mailInServerPort != _mailInServerPort) {
			_mailInServerPort = mailInServerPort;
		}
	}

	public String getMailInUserName() {
		return GetterUtil.getString(_mailInUserName);
	}

	public void setMailInUserName(String mailInUserName) {
		if (((mailInUserName == null) && (_mailInUserName != null)) ||
				((mailInUserName != null) && (_mailInUserName == null)) ||
				((mailInUserName != null) && (_mailInUserName != null) &&
				!mailInUserName.equals(_mailInUserName))) {
			_mailInUserName = mailInUserName;
		}
	}

	public String getMailInPassword() {
		return GetterUtil.getString(_mailInPassword);
	}

	public void setMailInPassword(String mailInPassword) {
		if (((mailInPassword == null) && (_mailInPassword != null)) ||
				((mailInPassword != null) && (_mailInPassword == null)) ||
				((mailInPassword != null) && (_mailInPassword != null) &&
				!mailInPassword.equals(_mailInPassword))) {
			_mailInPassword = mailInPassword;
		}
	}

	public int getMailInReadInterval() {
		return _mailInReadInterval;
	}

	public void setMailInReadInterval(int mailInReadInterval) {
		if (mailInReadInterval != _mailInReadInterval) {
			_mailInReadInterval = mailInReadInterval;
		}
	}

	public boolean getMailOutConfigured() {
		return _mailOutConfigured;
	}

	public boolean isMailOutConfigured() {
		return _mailOutConfigured;
	}

	public void setMailOutConfigured(boolean mailOutConfigured) {
		if (mailOutConfigured != _mailOutConfigured) {
			_mailOutConfigured = mailOutConfigured;
		}
	}

	public String getMailOutServerName() {
		return GetterUtil.getString(_mailOutServerName);
	}

	public void setMailOutServerName(String mailOutServerName) {
		if (((mailOutServerName == null) && (_mailOutServerName != null)) ||
				((mailOutServerName != null) && (_mailOutServerName == null)) ||
				((mailOutServerName != null) && (_mailOutServerName != null) &&
				!mailOutServerName.equals(_mailOutServerName))) {
			_mailOutServerName = mailOutServerName;
		}
	}

	public boolean getMailOutUseSSL() {
		return _mailOutUseSSL;
	}

	public boolean isMailOutUseSSL() {
		return _mailOutUseSSL;
	}

	public void setMailOutUseSSL(boolean mailOutUseSSL) {
		if (mailOutUseSSL != _mailOutUseSSL) {
			_mailOutUseSSL = mailOutUseSSL;
		}
	}

	public int getMailOutServerPort() {
		return _mailOutServerPort;
	}

	public void setMailOutServerPort(int mailOutServerPort) {
		if (mailOutServerPort != _mailOutServerPort) {
			_mailOutServerPort = mailOutServerPort;
		}
	}

	public String getMailOutUserName() {
		return GetterUtil.getString(_mailOutUserName);
	}

	public void setMailOutUserName(String mailOutUserName) {
		if (((mailOutUserName == null) && (_mailOutUserName != null)) ||
				((mailOutUserName != null) && (_mailOutUserName == null)) ||
				((mailOutUserName != null) && (_mailOutUserName != null) &&
				!mailOutUserName.equals(_mailOutUserName))) {
			_mailOutUserName = mailOutUserName;
		}
	}

	public String getMailOutPassword() {
		return GetterUtil.getString(_mailOutPassword);
	}

	public void setMailOutPassword(String mailOutPassword) {
		if (((mailOutPassword == null) && (_mailOutPassword != null)) ||
				((mailOutPassword != null) && (_mailOutPassword == null)) ||
				((mailOutPassword != null) && (_mailOutPassword != null) &&
				!mailOutPassword.equals(_mailOutPassword))) {
			_mailOutPassword = mailOutPassword;
		}
	}

	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		if (active != _active) {
			_active = active;
		}
	}

	public MBMailing toEscapedModel() {
		if (isEscapedModel()) {
			return (MBMailing)this;
		}
		else {
			MBMailing model = new MBMailingImpl();

			model.setEscapedModel(true);

			model.setUuid(HtmlUtil.escape(getUuid()));
			model.setMailingId(getMailingId());
			model.setGroupId(getGroupId());
			model.setCompanyId(getCompanyId());
			model.setUserId(getUserId());
			model.setUserName(HtmlUtil.escape(getUserName()));
			model.setCreateDate(getCreateDate());
			model.setModifiedDate(getModifiedDate());
			model.setCategoryId(getCategoryId());
			model.setMailingListAddress(HtmlUtil.escape(getMailingListAddress()));
			model.setMailAddress(HtmlUtil.escape(getMailAddress()));
			model.setMailInProtocol(HtmlUtil.escape(getMailInProtocol()));
			model.setMailInServerName(HtmlUtil.escape(getMailInServerName()));
			model.setMailInUseSSL(getMailInUseSSL());
			model.setMailInServerPort(getMailInServerPort());
			model.setMailInUserName(HtmlUtil.escape(getMailInUserName()));
			model.setMailInPassword(HtmlUtil.escape(getMailInPassword()));
			model.setMailInReadInterval(getMailInReadInterval());
			model.setMailOutConfigured(getMailOutConfigured());
			model.setMailOutServerName(HtmlUtil.escape(getMailOutServerName()));
			model.setMailOutUseSSL(getMailOutUseSSL());
			model.setMailOutServerPort(getMailOutServerPort());
			model.setMailOutUserName(HtmlUtil.escape(getMailOutUserName()));
			model.setMailOutPassword(HtmlUtil.escape(getMailOutPassword()));
			model.setActive(getActive());

			model = (MBMailing)Proxy.newProxyInstance(MBMailing.class.getClassLoader(),
					new Class[] { MBMailing.class },
					new ReadOnlyBeanHandler(model));

			return model;
		}
	}

	public Object clone() {
		MBMailingImpl clone = new MBMailingImpl();

		clone.setUuid(getUuid());
		clone.setMailingId(getMailingId());
		clone.setGroupId(getGroupId());
		clone.setCompanyId(getCompanyId());
		clone.setUserId(getUserId());
		clone.setUserName(getUserName());
		clone.setCreateDate(getCreateDate());
		clone.setModifiedDate(getModifiedDate());
		clone.setCategoryId(getCategoryId());
		clone.setMailingListAddress(getMailingListAddress());
		clone.setMailAddress(getMailAddress());
		clone.setMailInProtocol(getMailInProtocol());
		clone.setMailInServerName(getMailInServerName());
		clone.setMailInUseSSL(getMailInUseSSL());
		clone.setMailInServerPort(getMailInServerPort());
		clone.setMailInUserName(getMailInUserName());
		clone.setMailInPassword(getMailInPassword());
		clone.setMailInReadInterval(getMailInReadInterval());
		clone.setMailOutConfigured(getMailOutConfigured());
		clone.setMailOutServerName(getMailOutServerName());
		clone.setMailOutUseSSL(getMailOutUseSSL());
		clone.setMailOutServerPort(getMailOutServerPort());
		clone.setMailOutUserName(getMailOutUserName());
		clone.setMailOutPassword(getMailOutPassword());
		clone.setActive(getActive());

		return clone;
	}

	public int compareTo(Object obj) {
		if (obj == null) {
			return -1;
		}

		MBMailingImpl mbMailing = (MBMailingImpl)obj;

		int value = 0;

		if (getCategoryId() < mbMailing.getCategoryId()) {
			value = -1;
		}
		else if (getCategoryId() > mbMailing.getCategoryId()) {
			value = 1;
		}
		else {
			value = 0;
		}

		if (value != 0) {
			return value;
		}

		value = getMailingListAddress().toLowerCase()
					.compareTo(mbMailing.getMailingListAddress().toLowerCase());

		if (value != 0) {
			return value;
		}

		return 0;
	}

	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		MBMailingImpl mbMailing = null;

		try {
			mbMailing = (MBMailingImpl)obj;
		}
		catch (ClassCastException cce) {
			return false;
		}

		long pk = mbMailing.getPrimaryKey();

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

	private String _uuid;
	private long _mailingId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _categoryId;
	private String _mailingListAddress;
	private String _mailAddress;
	private String _mailInProtocol;
	private String _mailInServerName;
	private boolean _mailInUseSSL;
	private int _mailInServerPort;
	private String _mailInUserName;
	private String _mailInPassword;
	private int _mailInReadInterval;
	private boolean _mailOutConfigured;
	private String _mailOutServerName;
	private boolean _mailOutUseSSL;
	private int _mailOutServerPort;
	private String _mailOutUserName;
	private String _mailOutPassword;
	private boolean _active;
}