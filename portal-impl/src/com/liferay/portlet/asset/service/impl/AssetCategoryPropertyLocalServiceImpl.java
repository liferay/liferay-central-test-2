/**
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

package com.liferay.portlet.asset.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portlet.asset.PropertyKeyException;
import com.liferay.portlet.asset.PropertyValueException;
import com.liferay.portlet.asset.model.AssetCategoryProperty;
import com.liferay.portlet.asset.service.base.AssetCategoryPropertyLocalServiceBaseImpl;
import com.liferay.portlet.tags.util.TagsUtil;

import java.util.Date;
import java.util.List;

/**
 * <a href="AssetCategoryPropertyLocalServiceImpl.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 *
 */
public class AssetCategoryPropertyLocalServiceImpl
	extends AssetCategoryPropertyLocalServiceBaseImpl {

	public AssetCategoryProperty addProperty(
			long userId, long categoryId, String key, String value)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		validate(key, value);

		long propertyId = counterLocalService.increment();

		AssetCategoryProperty property =
			assetCategoryPropertyPersistence.create(propertyId);

		property.setCompanyId(user.getCompanyId());
		property.setUserId(user.getUserId());
		property.setUserName(user.getFullName());
		property.setCreateDate(now);
		property.setModifiedDate(now);
		property.setCategoryId(categoryId);
		property.setKey(key);
		property.setValue(value);

		assetCategoryPropertyPersistence.update(property, false);

		return property;
	}

	public void deleteProperties(long entryId) throws SystemException {
		List<AssetCategoryProperty> properties =
			assetCategoryPropertyPersistence.findByCategoryId(entryId);

		for (AssetCategoryProperty property : properties) {
			deleteProperty(property);
		}
	}

	public void deleteProperty(long propertyId)
		throws PortalException, SystemException {

		AssetCategoryProperty property =
			assetCategoryPropertyPersistence.findByPrimaryKey(propertyId);

		deleteProperty(property);
	}

	public void deleteProperty(AssetCategoryProperty property)
		throws SystemException {

		assetCategoryPropertyPersistence.remove(property);
	}

	public List<AssetCategoryProperty> getProperties() throws SystemException {
		return assetCategoryPropertyPersistence.findAll();
	}

	public List<AssetCategoryProperty> getProperties(long entryId)
		throws SystemException {

		return assetCategoryPropertyPersistence.findByCategoryId(entryId);
	}

	public AssetCategoryProperty getProperty(long propertyId)
		throws PortalException, SystemException {

		return assetCategoryPropertyPersistence.findByPrimaryKey(propertyId);
	}

	public AssetCategoryProperty getProperty(long categoryId, String key)
		throws PortalException, SystemException {

		return assetCategoryPropertyPersistence.findByCA_K(categoryId, key);
	}

	public List<AssetCategoryProperty> getPropertyValues(
			long groupId, String key)
		throws SystemException {

		return assetCategoryPropertyFinder.findByG_K(groupId, key);
	}

	public AssetCategoryProperty updateProperty(
			long propertyId, String key, String value)
		throws PortalException, SystemException {

		validate(key, value);

		AssetCategoryProperty property =
			assetCategoryPropertyPersistence.findByPrimaryKey(propertyId);

		property.setModifiedDate(new Date());
		property.setKey(key);
		property.setValue(value);

		assetCategoryPropertyPersistence.update(property, false);

		return property;
	}

	protected void validate(String key, String value) throws PortalException {
		if (!TagsUtil.isValidWord(key)) {
			throw new PropertyKeyException();
		}

		if (!TagsUtil.isValidWord(value)) {
			throw new PropertyValueException();
		}
	}

}