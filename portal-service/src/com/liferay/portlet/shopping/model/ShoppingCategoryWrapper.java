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

package com.liferay.portlet.shopping.model;


/**
 * <a href="ShoppingCategorySoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ShoppingCategory}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ShoppingCategory
 * @generated
 */
public class ShoppingCategoryWrapper implements ShoppingCategory {
	public ShoppingCategoryWrapper(ShoppingCategory shoppingCategory) {
		_shoppingCategory = shoppingCategory;
	}

	public long getPrimaryKey() {
		return _shoppingCategory.getPrimaryKey();
	}

	public void setPrimaryKey(long pk) {
		_shoppingCategory.setPrimaryKey(pk);
	}

	public long getCategoryId() {
		return _shoppingCategory.getCategoryId();
	}

	public void setCategoryId(long categoryId) {
		_shoppingCategory.setCategoryId(categoryId);
	}

	public long getGroupId() {
		return _shoppingCategory.getGroupId();
	}

	public void setGroupId(long groupId) {
		_shoppingCategory.setGroupId(groupId);
	}

	public long getCompanyId() {
		return _shoppingCategory.getCompanyId();
	}

	public void setCompanyId(long companyId) {
		_shoppingCategory.setCompanyId(companyId);
	}

	public long getUserId() {
		return _shoppingCategory.getUserId();
	}

	public void setUserId(long userId) {
		_shoppingCategory.setUserId(userId);
	}

	public java.lang.String getUserUuid()
		throws com.liferay.portal.SystemException {
		return _shoppingCategory.getUserUuid();
	}

	public void setUserUuid(java.lang.String userUuid) {
		_shoppingCategory.setUserUuid(userUuid);
	}

	public java.lang.String getUserName() {
		return _shoppingCategory.getUserName();
	}

	public void setUserName(java.lang.String userName) {
		_shoppingCategory.setUserName(userName);
	}

	public java.util.Date getCreateDate() {
		return _shoppingCategory.getCreateDate();
	}

	public void setCreateDate(java.util.Date createDate) {
		_shoppingCategory.setCreateDate(createDate);
	}

	public java.util.Date getModifiedDate() {
		return _shoppingCategory.getModifiedDate();
	}

	public void setModifiedDate(java.util.Date modifiedDate) {
		_shoppingCategory.setModifiedDate(modifiedDate);
	}

	public long getParentCategoryId() {
		return _shoppingCategory.getParentCategoryId();
	}

	public void setParentCategoryId(long parentCategoryId) {
		_shoppingCategory.setParentCategoryId(parentCategoryId);
	}

	public java.lang.String getName() {
		return _shoppingCategory.getName();
	}

	public void setName(java.lang.String name) {
		_shoppingCategory.setName(name);
	}

	public java.lang.String getDescription() {
		return _shoppingCategory.getDescription();
	}

	public void setDescription(java.lang.String description) {
		_shoppingCategory.setDescription(description);
	}

	public com.liferay.portlet.shopping.model.ShoppingCategory toEscapedModel() {
		return _shoppingCategory.toEscapedModel();
	}

	public boolean isNew() {
		return _shoppingCategory.isNew();
	}

	public boolean setNew(boolean n) {
		return _shoppingCategory.setNew(n);
	}

	public boolean isCachedModel() {
		return _shoppingCategory.isCachedModel();
	}

	public void setCachedModel(boolean cachedModel) {
		_shoppingCategory.setCachedModel(cachedModel);
	}

	public boolean isEscapedModel() {
		return _shoppingCategory.isEscapedModel();
	}

	public void setEscapedModel(boolean escapedModel) {
		_shoppingCategory.setEscapedModel(escapedModel);
	}

	public java.io.Serializable getPrimaryKeyObj() {
		return _shoppingCategory.getPrimaryKeyObj();
	}

	public com.liferay.portlet.expando.model.ExpandoBridge getExpandoBridge() {
		return _shoppingCategory.getExpandoBridge();
	}

	public void setExpandoBridgeAttributes(
		com.liferay.portal.service.ServiceContext serviceContext) {
		_shoppingCategory.setExpandoBridgeAttributes(serviceContext);
	}

	public java.lang.Object clone() {
		return _shoppingCategory.clone();
	}

	public int compareTo(
		com.liferay.portlet.shopping.model.ShoppingCategory shoppingCategory) {
		return _shoppingCategory.compareTo(shoppingCategory);
	}

	public int hashCode() {
		return _shoppingCategory.hashCode();
	}

	public java.lang.String toString() {
		return _shoppingCategory.toString();
	}

	public java.lang.String toXmlString() {
		return _shoppingCategory.toXmlString();
	}

	public boolean isRoot() {
		return _shoppingCategory.isRoot();
	}

	public ShoppingCategory getWrappedShoppingCategory() {
		return _shoppingCategory;
	}

	private ShoppingCategory _shoppingCategory;
}