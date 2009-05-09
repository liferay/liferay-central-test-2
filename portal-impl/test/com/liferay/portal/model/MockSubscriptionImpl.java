/*
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

package com.liferay.portal.model;

import java.util.Date;

/**
 * <a href="MockSubscription.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class MockSubscriptionImpl
	extends MockBaseModelImpl<Subscription> implements Subscription {

	public MockSubscriptionImpl(
		long companyId, long subscriptionId, long userId) {
		_companyId = companyId;
		_subscriptionId = subscriptionId;
		_userId = userId;
	}

	public long getSubscriptionId() {
		return _subscriptionId;
	}

	public void setSubscriptionId(long subscriptionId) {
		_subscriptionId = subscriptionId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getOriginalCompanyId() {
		return _originalCompanyId;
	}

	public void setOriginalCompanyId(long originalCompanyId) {
		_originalCompanyId = originalCompanyId;
	}

	public boolean isSetOriginalCompanyId() {
		return _setOriginalCompanyId;
	}

	public void setSetOriginalCompanyId(boolean setOriginalCompanyId) {
		_setOriginalCompanyId = setOriginalCompanyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public long getOriginalUserId() {
		return _originalUserId;
	}

	public void setOriginalUserId(long originalUserId) {
		_originalUserId = originalUserId;
	}

	public boolean isSetOriginalUserId() {
		return _setOriginalUserId;
	}

	public void setSetOriginalUserId(boolean setOriginalUserId) {
		_setOriginalUserId = setOriginalUserId;
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

	public long getClassNameId() {
		return _classNameId;
	}

	public void setClassNameId(long classNameId) {
		_classNameId = classNameId;
	}

	public long getOriginalClassNameId() {
		return _originalClassNameId;
	}

	public void setOriginalClassNameId(long originalClassNameId) {
		_originalClassNameId = originalClassNameId;
	}

	public boolean isSetOriginalClassNameId() {
		return _setOriginalClassNameId;
	}

	public void setSetOriginalClassNameId(boolean setOriginalClassNameId) {
		_setOriginalClassNameId = setOriginalClassNameId;
	}

	public long getClassPK() {
		return _classPK;
	}

	public void setClassPK(long classPK) {
		_classPK = classPK;
	}

	public long getOriginalClassPK() {
		return _originalClassPK;
	}

	public void setOriginalClassPK(long originalClassPK) {
		_originalClassPK = originalClassPK;
	}

	public boolean isSetOriginalClassPK() {
		return _setOriginalClassPK;
	}

	public void setSetOriginalClassPK(boolean setOriginalClassPK) {
		_setOriginalClassPK = setOriginalClassPK;
	}

	public String getFrequency() {
		return _frequency;
	}

	public void setFrequency(String frequency) {
		_frequency = frequency;
	}


	public long getPrimaryKey() {
		return 0;
	}

	public void setPrimaryKey(long pk) {

	}

	public String getClassName() {
		return null;
	}

	public Subscription toEscapedModel() {
		return null;
	}

	private long _subscriptionId;
	private long _companyId;
	private long _originalCompanyId;
	private boolean _setOriginalCompanyId;
	private long _userId;
	private long _originalUserId;
	private boolean _setOriginalUserId;
	private String _userName;
	private Date _createDate;
	private Date _modifiedDate;
	private long _classNameId;
	private long _originalClassNameId;
	private boolean _setOriginalClassNameId;
	private long _classPK;
	private long _originalClassPK;
	private boolean _setOriginalClassPK;
	private String _frequency;

}