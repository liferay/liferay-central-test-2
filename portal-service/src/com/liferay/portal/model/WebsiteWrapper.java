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
 * <a href="WebsiteSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link Website}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       Website
 * @generated
 */
public class WebsiteWrapper implements Website {
	public WebsiteWrapper(Website website) {
		_website = website;
	}

	public long getPrimaryKey() {
		return _website.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_website.setPrimaryKey(pk);
	}

	public long getWebsiteId() {
		return _website.getWebsiteId();
	}

	public void setWebsiteId(long websiteId) {
		_website.setWebsiteId(websiteId);
	}

	public long getCompanyId() {
		return _website.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_website.setCompanyId(companyId);
	}

	public long getUserId() {
		return _website.getUserId();
	}

	public void setUserId(long userId) {
		_website.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.SystemException {
		return _website.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_website.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _website.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_website.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _website.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_website.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _website.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_website.setModifiedDate(modifiedDate);
	}

	public java.lang.String getClassName() {
		return _website.getClassName();
	}

	public long getClassNameId() {
		return _website.getClassNameId();
	}

	public void setClassNameId(long classNameId) {
		_website.setClassNameId(classNameId);
	}

	public long getClassPK() {
		return _website.getClassPK();
	}

	public void setClassPK(long classPK) {
		_website.setClassPK(classPK);
	}

	public java.lang.String getUrl() {
		return _website.getUrl();
	}

	public void setUrl(java.lang.String url) {
		_website.setUrl(url);
	}

	public int getTypeId() {
		return _website.getTypeId();
	}

	public void setTypeId(int typeId) {
		_website.setTypeId(typeId);
	}

	public boolean getPrimary() {
		return _website.getPrimary();
	}

	public boolean isPrimary() {
		return _website.isPrimary();
	}

	public void setPrimary(boolean primary) {
		_website.setPrimary(primary);
	}

	public com.liferay.portal.model.Website toEscapedModel() {
		return _website.toEscapedModel();
	}

	public boolean isNew() {
		return _website.isNew();
	}

	public boolean setNew(boolean n) {
		return _website.setNew(n);
	}

	public boolean isCachedModel() {
		return _website.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_website.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _website.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_website.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _website.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _website.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_website.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _website.clone();
	}

	public int compareTo(com.liferay.portal.model.Website website) {
		return _website.compareTo(website);
	}

	public int hashCode() {
		return _website.hashCode();
	}

	public java.lang.String toString() {
		return _website.toString();
	}

	public java.lang.String toXmlString() {
		return _website.toXmlString();
	}

	public com.liferay.portal.model.ListType getType() {
		return _website.getType();
	}

	public Website getWrappedWebsite() {
		return _website;
	}

	private Website _website;
}