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

package com.liferay.portlet.ratings.model;


/**
 * <a href="RatingsEntrySoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link RatingsEntry}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       RatingsEntry
 * @generated
 */
public class RatingsEntryWrapper implements RatingsEntry {
	public RatingsEntryWrapper(RatingsEntry ratingsEntry) {
		_ratingsEntry = ratingsEntry;
	}

	public long getPrimaryKey() {
		return _ratingsEntry.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_ratingsEntry.setPrimaryKey(pk);
	}

	public long getEntryId() {
		return _ratingsEntry.getEntryId();
	}

	public void setEntryId(long entryId) {
		_ratingsEntry.setEntryId(entryId);
	}

	public long getCompanyId() {
		return _ratingsEntry.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_ratingsEntry.setCompanyId(companyId);
	}

	public long getUserId() {
		return _ratingsEntry.getUserId();
	}

	public void setUserId(long userId) {
		_ratingsEntry.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ratingsEntry.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_ratingsEntry.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _ratingsEntry.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_ratingsEntry.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _ratingsEntry.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_ratingsEntry.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _ratingsEntry.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_ratingsEntry.setModifiedDate(modifiedDate);
	}

	public java.lang.String getClassName() {
		return _ratingsEntry.getClassName();
	}

	public long getClassNameId() {
		return _ratingsEntry.getClassNameId();
	}

	public void setClassNameId(long classNameId) {
		_ratingsEntry.setClassNameId(classNameId);
	}

	public long getClassPK() {
		return _ratingsEntry.getClassPK();
	}

	public void setClassPK(long classPK) {
		_ratingsEntry.setClassPK(classPK);
	}

	public double getScore() {
		return _ratingsEntry.getScore();
	}

	public void setScore(double score) {
		_ratingsEntry.setScore(score);
	}

	public com.liferay.portlet.ratings.model.RatingsEntry toEscapedModel() {
		return _ratingsEntry.toEscapedModel();
	}

	public boolean isNew() {
		return _ratingsEntry.isNew();
	}

	public boolean setNew(boolean n) {
		return _ratingsEntry.setNew(n);
	}

	public boolean isCachedModel() {
		return _ratingsEntry.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_ratingsEntry.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _ratingsEntry.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_ratingsEntry.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _ratingsEntry.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _ratingsEntry.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_ratingsEntry.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _ratingsEntry.clone();
	}

	public int compareTo(
		com.liferay.portlet.ratings.model.RatingsEntry ratingsEntry) {
		return _ratingsEntry.compareTo(ratingsEntry);
	}

	public int hashCode() {
		return _ratingsEntry.hashCode();
	}

	public java.lang.String toString() {
		return _ratingsEntry.toString();
	}

	public java.lang.String toXmlString() {
		return _ratingsEntry.toXmlString();
	}

	public RatingsEntry getWrappedRatingsEntry() {
		return _ratingsEntry;
	}

	private RatingsEntry _ratingsEntry;
}