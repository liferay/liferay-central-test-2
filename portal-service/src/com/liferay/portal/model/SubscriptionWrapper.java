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
 * <a href="SubscriptionSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link Subscription}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Subscription
 * @generated
 */
public class SubscriptionWrapper implements Subscription {
	public SubscriptionWrapper(Subscription subscription) {
		_subscription = subscription;
	}

	public long getPrimaryKey() {
		return _subscription.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_subscription.setPrimaryKey(pk);
	}

	public long getSubscriptionId() {
		return _subscription.getSubscriptionId();
	}

	public void setSubscriptionId(long subscriptionId) {
		_subscription.setSubscriptionId(subscriptionId);
	}

	public long getCompanyId() {
		return _subscription.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_subscription.setCompanyId(companyId);
	}

	public long getUserId() {
		return _subscription.getUserId();
	}

	public void setUserId(long userId) {
		_subscription.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.SystemException {
		return _subscription.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_subscription.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _subscription.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_subscription.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _subscription.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_subscription.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _subscription.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_subscription.setModifiedDate(modifiedDate);
	}

	public java.lang.String getClassName() {
		return _subscription.getClassName();
	}

	public long getClassNameId() {
		return _subscription.getClassNameId();
	}

	public void setClassNameId(long classNameId) {
		_subscription.setClassNameId(classNameId);
	}

	public long getClassPK() {
		return _subscription.getClassPK();
	}

	public void setClassPK(long classPK) {
		_subscription.setClassPK(classPK);
	}

	public java.lang.String getFrequency() {
		return _subscription.getFrequency();
	}

	public void setFrequency(java.lang.String frequency) {
		_subscription.setFrequency(frequency);
	}

	public com.liferay.portal.model.Subscription toEscapedModel() {
		return _subscription.toEscapedModel();
	}

	public boolean isNew() {
		return _subscription.isNew();
	}

	public boolean setNew(boolean n) {
		return _subscription.setNew(n);
	}

	public boolean isCachedModel() {
		return _subscription.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_subscription.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _subscription.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_subscription.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _subscription.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _subscription.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_subscription.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _subscription.clone();
	}

	public int compareTo(com.liferay.portal.model.Subscription subscription) {
		return _subscription.compareTo(subscription);
	}

	public int hashCode() {
		return _subscription.hashCode();
	}

	public java.lang.String toString() {
		return _subscription.toString();
	}

	public java.lang.String toXmlString() {
		return _subscription.toXmlString();
	}

	public Subscription getWrappedSubscription() {
		return _subscription;
	}

	private Subscription _subscription;
}