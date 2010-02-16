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


/**
 * <a href="LockSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link Lock}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Lock
 * @generated
 */
public class LockWrapper implements Lock {
	public LockWrapper(Lock lock) {
		_lock = lock;
	}

	public long getPrimaryKey() {
		return _lock.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_lock.setPrimaryKey(pk);
	}

	public java.lang.String getUuid() {
		return _lock.getUuid();
	}

	public void setUuid(java.lang.String uuid) {
		_lock.setUuid(uuid);
	}

	public long getLockId() {
		return _lock.getLockId();
	}

	public void setLockId(long lockId) {
		_lock.setLockId(lockId);
	}

	public long getCompanyId() {
		return _lock.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_lock.setCompanyId(companyId);
	}

	public long getUserId() {
		return _lock.getUserId();
	}

	public void setUserId(long userId) {
		_lock.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _lock.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_lock.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _lock.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_lock.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _lock.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_lock.setCreateDate(createDate);
	}

	public java.lang.String getClassName() {
		return _lock.getClassName();
	}

	public void setClassName(java.lang.String className) {
		_lock.setClassName(className);
	}

	public java.lang.String getKey() {
		return _lock.getKey();
	}

	public void setKey(java.lang.String key) {
		_lock.setKey(key);
	}

	public java.lang.String getOwner() {
		return _lock.getOwner();
	}

	public void setOwner(java.lang.String owner) {
		_lock.setOwner(owner);
	}

	public boolean getInheritable() {
		return _lock.getInheritable();
	}

	public boolean isInheritable() {
		return _lock.isInheritable();
	}

	public void setInheritable(boolean inheritable) {
		_lock.setInheritable(inheritable);
	}

	public java.util.Date getExpirationDate() {
		return _lock.getExpirationDate();
	}

	public void setExpirationDate(java.util.Date expirationDate) {
		_lock.setExpirationDate(expirationDate);
	}

	public Lock toEscapedModel() {
		return _lock.toEscapedModel();
	}

	public boolean isNew() {
		return _lock.isNew();
	}

	public boolean setNew(boolean n) {
		return _lock.setNew(n);
	}

	public boolean isCachedModel() {
		return _lock.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_lock.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _lock.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_lock.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _lock.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _lock.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_lock.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _lock.clone();
	}

	public int compareTo(Lock lock) {
		return _lock.compareTo(lock);
	}

	public int hashCode() {
		return _lock.hashCode();
	}

	public java.lang.String toString() {
		return _lock.toString();
	}

	public java.lang.String toXmlString() {
		return _lock.toXmlString();
	}

	public long getExpirationTime() {
		return _lock.getExpirationTime();
	}

	public boolean isExpired() {
		return _lock.isExpired();
	}

	public Lock getWrappedLock() {
		return _lock;
	}

	private Lock _lock;
}