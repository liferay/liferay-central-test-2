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
 * <a href="PasswordTrackerSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link PasswordTracker}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PasswordTracker
 * @generated
 */
public class PasswordTrackerWrapper implements PasswordTracker {
	public PasswordTrackerWrapper(PasswordTracker passwordTracker) {
		_passwordTracker = passwordTracker;
	}

	public long getPrimaryKey() {
		return _passwordTracker.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_passwordTracker.setPrimaryKey(pk);
	}

	public long getPasswordTrackerId() {
		return _passwordTracker.getPasswordTrackerId();
	}

	public void setPasswordTrackerId(long passwordTrackerId) {
		_passwordTracker.setPasswordTrackerId(passwordTrackerId);
	}

	public long getUserId() {
		return _passwordTracker.getUserId();
	}

	public void setUserId(long userId) {
		_passwordTracker.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.SystemException {
		return _passwordTracker.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_passwordTracker.setUserUuid(userUuid);
	}

	public java.util.Date getCreateDate() {
		return _passwordTracker.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_passwordTracker.setCreateDate(createDate);
	}

	public java.lang.String getPassword() {
		return _passwordTracker.getPassword();
	}

	public void setPassword(java.lang.String password) {
		_passwordTracker.setPassword(password);
	}

	public com.liferay.portal.model.PasswordTracker toEscapedModel() {
		return _passwordTracker.toEscapedModel();
	}

	public boolean isNew() {
		return _passwordTracker.isNew();
	}

	public boolean setNew(boolean n) {
		return _passwordTracker.setNew(n);
	}

	public boolean isCachedModel() {
		return _passwordTracker.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_passwordTracker.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _passwordTracker.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_passwordTracker.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _passwordTracker.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _passwordTracker.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_passwordTracker.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _passwordTracker.clone();
	}

	public int compareTo(
		com.liferay.portal.model.PasswordTracker passwordTracker) {
		return _passwordTracker.compareTo(passwordTracker);
	}

	public int hashCode() {
		return _passwordTracker.hashCode();
	}

	public java.lang.String toString() {
		return _passwordTracker.toString();
	}

	public java.lang.String toXmlString() {
		return _passwordTracker.toXmlString();
	}

	public PasswordTracker getWrappedPasswordTracker() {
		return _passwordTracker;
	}

	private PasswordTracker _passwordTracker;
}