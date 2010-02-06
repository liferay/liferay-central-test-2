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

package com.liferay.portlet.messageboards.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="MBMailingListSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * {@link com.liferay.portlet.messageboards.service.http.MBMailingListServiceSoap}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portlet.messageboards.service.http.MBMailingListServiceSoap
 * @generated
 */
public class MBMailingListSoap implements Serializable {
	public static MBMailingListSoap toSoapModel(MBMailingList model) {
		MBMailingListSoap soapModel = new MBMailingListSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setMailingListId(model.getMailingListId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setModifiedDate(model.getModifiedDate());
		soapModel.setCategoryId(model.getCategoryId());
		soapModel.setEmailAddress(model.getEmailAddress());
		soapModel.setInProtocol(model.getInProtocol());
		soapModel.setInServerName(model.getInServerName());
		soapModel.setInServerPort(model.getInServerPort());
		soapModel.setInUseSSL(model.getInUseSSL());
		soapModel.setInUserName(model.getInUserName());
		soapModel.setInPassword(model.getInPassword());
		soapModel.setInReadInterval(model.getInReadInterval());
		soapModel.setOutEmailAddress(model.getOutEmailAddress());
		soapModel.setOutCustom(model.getOutCustom());
		soapModel.setOutServerName(model.getOutServerName());
		soapModel.setOutServerPort(model.getOutServerPort());
		soapModel.setOutUseSSL(model.getOutUseSSL());
		soapModel.setOutUserName(model.getOutUserName());
		soapModel.setOutPassword(model.getOutPassword());
		soapModel.setActive(model.getActive());

		return soapModel;
	}

	public static MBMailingListSoap[] toSoapModels(MBMailingList[] models) {
		MBMailingListSoap[] soapModels = new MBMailingListSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static MBMailingListSoap[][] toSoapModels(MBMailingList[][] models) {
		MBMailingListSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new MBMailingListSoap[models.length][models[0].length];
		}
		else {
			soapModels = new MBMailingListSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static MBMailingListSoap[] toSoapModels(List<MBMailingList> models) {
		List<MBMailingListSoap> soapModels = new ArrayList<MBMailingListSoap>(models.size());

		for (MBMailingList model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new MBMailingListSoap[soapModels.size()]);
	}

	public MBMailingListSoap() {
	}

	public long getPrimaryKey() {
		return _mailingListId;
	}

	public void setPrimaryKey(long pk) {
		setMailingListId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getMailingListId() {
		return _mailingListId;
	}

	public void setMailingListId(long mailingListId) {
		_mailingListId = mailingListId;
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

	public String getEmailAddress() {
		return _emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		_emailAddress = emailAddress;
	}

	public String getInProtocol() {
		return _inProtocol;
	}

	public void setInProtocol(String inProtocol) {
		_inProtocol = inProtocol;
	}

	public String getInServerName() {
		return _inServerName;
	}

	public void setInServerName(String inServerName) {
		_inServerName = inServerName;
	}

	public int getInServerPort() {
		return _inServerPort;
	}

	public void setInServerPort(int inServerPort) {
		_inServerPort = inServerPort;
	}

	public boolean getInUseSSL() {
		return _inUseSSL;
	}

	public boolean isInUseSSL() {
		return _inUseSSL;
	}

	public void setInUseSSL(boolean inUseSSL) {
		_inUseSSL = inUseSSL;
	}

	public String getInUserName() {
		return _inUserName;
	}

	public void setInUserName(String inUserName) {
		_inUserName = inUserName;
	}

	public String getInPassword() {
		return _inPassword;
	}

	public void setInPassword(String inPassword) {
		_inPassword = inPassword;
	}

	public int getInReadInterval() {
		return _inReadInterval;
	}

	public void setInReadInterval(int inReadInterval) {
		_inReadInterval = inReadInterval;
	}

	public String getOutEmailAddress() {
		return _outEmailAddress;
	}

	public void setOutEmailAddress(String outEmailAddress) {
		_outEmailAddress = outEmailAddress;
	}

	public boolean getOutCustom() {
		return _outCustom;
	}

	public boolean isOutCustom() {
		return _outCustom;
	}

	public void setOutCustom(boolean outCustom) {
		_outCustom = outCustom;
	}

	public String getOutServerName() {
		return _outServerName;
	}

	public void setOutServerName(String outServerName) {
		_outServerName = outServerName;
	}

	public int getOutServerPort() {
		return _outServerPort;
	}

	public void setOutServerPort(int outServerPort) {
		_outServerPort = outServerPort;
	}

	public boolean getOutUseSSL() {
		return _outUseSSL;
	}

	public boolean isOutUseSSL() {
		return _outUseSSL;
	}

	public void setOutUseSSL(boolean outUseSSL) {
		_outUseSSL = outUseSSL;
	}

	public String getOutUserName() {
		return _outUserName;
	}

	public void setOutUserName(String outUserName) {
		_outUserName = outUserName;
	}

	public String getOutPassword() {
		return _outPassword;
	}

	public void setOutPassword(String outPassword) {
		_outPassword = outPassword;
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
	private long _mailingListId;
	private long _groupId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _categoryId;
	private String _emailAddress;
	private String _inProtocol;
	private String _inServerName;
	private int _inServerPort;
	private boolean _inUseSSL;
	private String _inUserName;
	private String _inPassword;
	private int _inReadInterval;
	private String _outEmailAddress;
	private boolean _outCustom;
	private String _outServerName;
	private int _outServerPort;
	private boolean _outUseSSL;
	private String _outUserName;
	private String _outPassword;
	private boolean _active;
}