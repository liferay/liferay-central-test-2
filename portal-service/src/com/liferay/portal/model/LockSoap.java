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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="LockSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * {@link com.liferay.portal.service.http.LockServiceSoap}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       com.liferay.portal.service.http.LockServiceSoap
 * @generated
 */
public class LockSoap implements Serializable {
	public static LockSoap toSoapModel(Lock model) {
		LockSoap soapModel = new LockSoap();

		soapModel.setUuid(model.getUuid());
		soapModel.setLockId(model.getLockId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setUserName(model.getUserName());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setClassName(model.getClassName());
		soapModel.setKey(model.getKey());
		soapModel.setOwner(model.getOwner());
		soapModel.setInheritable(model.getInheritable());
		soapModel.setExpirationDate(model.getExpirationDate());

		return soapModel;
	}

	public static LockSoap[] toSoapModels(Lock[] models) {
		LockSoap[] soapModels = new LockSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static LockSoap[][] toSoapModels(Lock[][] models) {
		LockSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels = new LockSoap[models.length][models[0].length];
		}
		else {
			soapModels = new LockSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static LockSoap[] toSoapModels(List<Lock> models) {
		List<LockSoap> soapModels = new ArrayList<LockSoap>(models.size());

		for (Lock model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new LockSoap[soapModels.size()]);
	}

	public LockSoap() {
	}

	public long getPrimaryKey() {
		return _lockId;
	}

	public void setPrimaryKey(long pk) {
		setLockId(pk);
	}

	public String getUuid() {
		return _uuid;
	}

	public void setUuid(String uuid) {
		_uuid = uuid;
	}

	public long getLockId() {
		return _lockId;
	}

	public void setLockId(long lockId) {
		_lockId = lockId;
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

	public String getClassName() {
		return _className;
	}

	public void setClassName(String className) {
		_className = className;
	}

	public String getKey() {
		return _key;
	}

	public void setKey(String key) {
		_key = key;
	}

	public String getOwner() {
		return _owner;
	}

	public void setOwner(String owner) {
		_owner = owner;
	}

	public boolean getInheritable() {
		return _inheritable;
	}

	public boolean isInheritable() {
		return _inheritable;
	}

	public void setInheritable(boolean inheritable) {
		_inheritable = inheritable;
	}

	public Date getExpirationDate() {
		return _expirationDate;
	}

	public void setExpirationDate(Date expirationDate) {
		_expirationDate = expirationDate;
	}

	private String _uuid;
	private long _lockId;
	private long _companyId;
	private long _userId;
	private String _userName;
	private Date _createDate;
	private String _className;
	private String _key;
	private String _owner;
	private boolean _inheritable;
	private Date _expirationDate;
}