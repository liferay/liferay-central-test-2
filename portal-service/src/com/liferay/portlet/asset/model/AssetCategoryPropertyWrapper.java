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

package com.liferay.portlet.asset.model;


/**
 * <a href="AssetCategoryPropertySoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link AssetCategoryProperty}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetCategoryProperty
 * @generated
 */
public class AssetCategoryPropertyWrapper implements AssetCategoryProperty {
	public AssetCategoryPropertyWrapper(
		AssetCategoryProperty assetCategoryProperty) {
		_assetCategoryProperty = assetCategoryProperty;
	}

	public long getPrimaryKey() {
		return _assetCategoryProperty.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_assetCategoryProperty.setPrimaryKey(pk);
	}

	public long getCategoryPropertyId() {
		return _assetCategoryProperty.getCategoryPropertyId();
	}

	public void setCategoryPropertyId(long categoryPropertyId) {
		_assetCategoryProperty.setCategoryPropertyId(categoryPropertyId);
	}

	public long getCompanyId() {
		return _assetCategoryProperty.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_assetCategoryProperty.setCompanyId(companyId);
	}

	public long getUserId() {
		return _assetCategoryProperty.getUserId();
	}

	public void setUserId(long userId) {
		_assetCategoryProperty.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.SystemException {
		return _assetCategoryProperty.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_assetCategoryProperty.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _assetCategoryProperty.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_assetCategoryProperty.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _assetCategoryProperty.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_assetCategoryProperty.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _assetCategoryProperty.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_assetCategoryProperty.setModifiedDate(modifiedDate);
	}

	public long getCategoryId() {
		return _assetCategoryProperty.getCategoryId();
	}

	public void setCategoryId(long categoryId) {
		_assetCategoryProperty.setCategoryId(categoryId);
	}

	public java.lang.String getKey() {
		return _assetCategoryProperty.getKey();
	}

	public void setKey(java.lang.String key) {
		_assetCategoryProperty.setKey(key);
	}

	public java.lang.String getValue() {
		return _assetCategoryProperty.getValue();
	}

	public void setValue(java.lang.String value) {
		_assetCategoryProperty.setValue(value);
	}

	public com.liferay.portlet.asset.model.AssetCategoryProperty toEscapedModel() {
		return _assetCategoryProperty.toEscapedModel();
	}

	public boolean isNew() {
		return _assetCategoryProperty.isNew();
	}

	public boolean setNew(boolean n) {
		return _assetCategoryProperty.setNew(n);
	}

	public boolean isCachedModel() {
		return _assetCategoryProperty.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_assetCategoryProperty.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _assetCategoryProperty.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_assetCategoryProperty.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _assetCategoryProperty.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _assetCategoryProperty.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_assetCategoryProperty.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _assetCategoryProperty.clone();
	}

	public int compareTo(
		com.liferay.portlet.asset.model.AssetCategoryProperty assetCategoryProperty) {
		return _assetCategoryProperty.compareTo(assetCategoryProperty);
	}

	public int hashCode() {
		return _assetCategoryProperty.hashCode();
	}

	public java.lang.String toString() {
		return _assetCategoryProperty.toString();
	}

	public java.lang.String toXmlString() {
		return _assetCategoryProperty.toXmlString();
	}

	public AssetCategoryProperty getWrappedAssetCategoryProperty() {
		return _assetCategoryProperty;
	}

	private AssetCategoryProperty _assetCategoryProperty;
}