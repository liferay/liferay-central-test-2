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
 * <a href="AssetTagSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link AssetTag}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AssetTag
 * @generated
 */
public class AssetTagWrapper implements AssetTag {
	public AssetTagWrapper(AssetTag assetTag) {
		_assetTag = assetTag;
	}

	public long getPrimaryKey() {
		return _assetTag.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_assetTag.setPrimaryKey(pk);
	}

	public long getTagId() {
		return _assetTag.getTagId();
	}

	public void setTagId(long tagId) {
		_assetTag.setTagId(tagId);
	}

	public long getGroupId() {
		return _assetTag.getGroupId();
	}

	public void setGroupId(long groupId) {
		_assetTag.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _assetTag.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_assetTag.setCompanyId(companyId);
	}

	public long getUserId() {
		return _assetTag.getUserId();
	}

	public void setUserId(long userId) {
		_assetTag.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.SystemException {
		return _assetTag.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_assetTag.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _assetTag.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_assetTag.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _assetTag.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_assetTag.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _assetTag.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_assetTag.setModifiedDate(modifiedDate);
	}

	public java.lang.String getName() {
		return _assetTag.getName();
	}

	public void setName(java.lang.String name) {
		_assetTag.setName(name);
	}

	public int getAssetCount() {
		return _assetTag.getAssetCount();
	}

	public void setAssetCount(int assetCount) {
		_assetTag.setAssetCount(assetCount);
	}

	public com.liferay.portlet.asset.model.AssetTag toEscapedModel() {
		return _assetTag.toEscapedModel();
	}

	public boolean isNew() {
		return _assetTag.isNew();
	}

	public boolean setNew(boolean n) {
		return _assetTag.setNew(n);
	}

	public boolean isCachedModel() {
		return _assetTag.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_assetTag.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _assetTag.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_assetTag.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _assetTag.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _assetTag.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_assetTag.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _assetTag.clone();
	}

	public int compareTo(com.liferay.portlet.asset.model.AssetTag assetTag) {
		return _assetTag.compareTo(assetTag);
	}

	public int hashCode() {
		return _assetTag.hashCode();
	}

	public java.lang.String toString() {
		return _assetTag.toString();
	}

	public java.lang.String toXmlString() {
		return _assetTag.toXmlString();
	}

	public AssetTag getWrappedAssetTag() {
		return _assetTag;
	}

	private AssetTag _assetTag;
}