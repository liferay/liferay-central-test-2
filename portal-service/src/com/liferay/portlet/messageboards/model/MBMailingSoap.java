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

package com.liferay.portlet.messageboards.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="MBMailingSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * <code>com.liferay.portlet.messageboards.service.http.MBMailingServiceSoap</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.messageboards.service.http.MBMailingServiceSoap
 *
 */
public class MBMailingSoap implements Serializable {
	public static MBMailingSoap toSoapModel(MBMailing model) {
		MBMailingSoap soapModel = new MBMailingSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setMailingId(model.getMailingId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCategoryId(model.getCategoryId());
		soapModel.setMailingListAddress(model.getMailingListAddress());
		soapModel.setMailAddress(model.getMailAddress());
		soapModel.setMailInProtocol(model.getMailInProtocol());
		soapModel.setMailInServerName(model.getMailInServerName());
		soapModel.setMailInUseSSL(model.getMailInUseSSL());
		soapModel.setMailInServerPort(model.getMailInServerPort());
		soapModel.setMailInUserName(model.getMailInUserName());
		soapModel.setMailInPassword(model.getMailInPassword());
		soapModel.setMailInReadInterval(model.getMailInReadInterval());
		soapModel.setMailOutConfigured(model.getMailOutConfigured());
		soapModel.setMailOutServerName(model.getMailOutServerName());
		soapModel.setMailOutUseSSL(model.getMailOutUseSSL());
		soapModel.setMailOutServerPort(model.getMailOutServerPort());
		soapModel.setMailOutUserName(model.getMailOutUserName());
		soapModel.setMailOutPassword(model.getMailOutPassword());
		soapModel.setActive(model.getActive());

		return soapModel;
	}

	public static MBMailingSoap[] toSoapModels(List<MBMailing> models) {
		List<MBMailingSoap> soapModels = new ArrayList<MBMailingSoap>(models.size());

		for (MBMailing model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new MBMailingSoap[soapModels.size()]);
	}

	public MBMailingSoap() {
	}

	public long getPrimaryKey() {
		return _mailingId;
	}

	public void setPrimaryKey(long pk) {
		setMailingId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getMailingId() {
		return _mailingId;
	}

	public void setMailingId(long mailingId) {
		_mailingId = mailingId;
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
		return _userName;
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

	public long getCategoryId() {
		return _categoryId;
	}

	public void setCategoryId(long categoryId) {
		_categoryId = categoryId;
	}

	public String getMailingListAddress() {
		return _mailingListAddress;
	}

	public void setMailingListAddress(String mailingListAddress) {
		_mailingListAddress = mailingListAddress;
	}

	public String getMailAddress() {
		return _mailAddress;
	}

	public void setMailAddress(String mailAddress) {
		_mailAddress = mailAddress;
	}

	public String getMailInProtocol() {
		return _mailInProtocol;
	}

	public void setMailInProtocol(String mailInProtocol) {
		_mailInProtocol = mailInProtocol;
	}

	public String getMailInServerName() {
		return _mailInServerName;
	}

	public void setMailInServerName(String mailInServerName) {
		_mailInServerName = mailInServerName;
	}

	public boolean getMailInUseSSL() {
		return _mailInUseSSL;
	}

	public boolean isMailInUseSSL() {
		return _mailInUseSSL;
	}

	public void setMailInUseSSL(boolean mailInUseSSL) {
		_mailInUseSSL = mailInUseSSL;
	}

	public int getMailInServerPort() {
		return _mailInServerPort;
	}

	public void setMailInServerPort(int mailInServerPort) {
		_mailInServerPort = mailInServerPort;
	}

	public String getMailInUserName() {
		return _mailInUserName;
	}

	public void setMailInUserName(String mailInUserName) {
		_mailInUserName = mailInUserName;
	}

	public String getMailInPassword() {
		return _mailInPassword;
	}

	public void setMailInPassword(String mailInPassword) {
		_mailInPassword = mailInPassword;
	}

	public int getMailInReadInterval() {
		return _mailInReadInterval;
	}

	public void setMailInReadInterval(int mailInReadInterval) {
		_mailInReadInterval = mailInReadInterval;
	}

	public boolean getMailOutConfigured() {
		return _mailOutConfigured;
	}

	public boolean isMailOutConfigured() {
		return _mailOutConfigured;
	}

	public void setMailOutConfigured(boolean mailOutConfigured) {
		_mailOutConfigured = mailOutConfigured;
	}

	public String getMailOutServerName() {
		return _mailOutServerName;
	}

	public void setMailOutServerName(String mailOutServerName) {
		_mailOutServerName = mailOutServerName;
	}

	public boolean getMailOutUseSSL() {
		return _mailOutUseSSL;
	}

	public boolean isMailOutUseSSL() {
		return _mailOutUseSSL;
	}

	public void setMailOutUseSSL(boolean mailOutUseSSL) {
		_mailOutUseSSL = mailOutUseSSL;
	}

	public int getMailOutServerPort() {
		return _mailOutServerPort;
	}

	public void setMailOutServerPort(int mailOutServerPort) {
		_mailOutServerPort = mailOutServerPort;
	}

	public String getMailOutUserName() {
		return _mailOutUserName;
	}

	public void setMailOutUserName(String mailOutUserName) {
		_mailOutUserName = mailOutUserName;
	}

	public String getMailOutPassword() {
		return _mailOutPassword;
	}

	public void setMailOutPassword(String mailOutPassword) {
		_mailOutPassword = mailOutPassword;
	}

	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
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