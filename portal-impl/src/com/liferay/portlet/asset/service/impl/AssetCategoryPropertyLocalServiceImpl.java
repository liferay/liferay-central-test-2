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

package com.liferay.portlet.asset.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portlet.asset.CategoryPropertyKeyException;
import com.liferay.portlet.asset.CategoryPropertyValueException;
import com.liferay.portlet.asset.model.AssetCategoryProperty;
import com.liferay.portlet.asset.service.base.AssetCategoryPropertyLocalServiceBaseImpl;
import com.liferay.portlet.asset.util.AssetUtil;

import java.util.Date;
import java.util.List;

/**
 * <a href="AssetCategoryPropertyLocalServiceImpl.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Jorge Ferrer
 */
public class AssetCategoryPropertyLocalServiceImpl
	extends AssetCategoryPropertyLocalServiceBaseImpl {

	public AssetCategoryProperty addCategoryProperty(
			long userId, long categoryId, String key, String value)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		validate(key, value);

		long categoryPropertyId = counterLocalService.increment();

		AssetCategoryProperty categoryProperty =
			assetCategoryPropertyPersistence.create(categoryPropertyId);

		categoryProperty.setCompanyId(user.getCompanyId());
		categoryProperty.setUserId(user.getUserId());
		categoryProperty.setUserName(user.getFullName());
		categoryProperty.setCreateDate(now);
		categoryProperty.setModifiedDate(now);
		categoryProperty.setCategoryId(categoryId);
		categoryProperty.setKey(key);
		categoryProperty.setValue(value);

		assetCategoryPropertyPersistence.update(categoryProperty, false);

		return categoryProperty;
	}

	public void deleteCategoryProperties(long entryId) throws SystemException {
		List<AssetCategoryProperty> categoryProperties =
			assetCategoryPropertyPersistence.findByCategoryId(entryId);

		for (AssetCategoryProperty categoryProperty : categoryProperties) {
			deleteCategoryProperty(categoryProperty);
		}
	}

	public void deleteCategoryProperty(AssetCategoryProperty categoryProperty)
		throws SystemException {

		assetCategoryPropertyPersistence.remove(categoryProperty);
	}

	public void deleteCategoryProperty(long categoryPropertyId)
		throws PortalException, SystemException {

		AssetCategoryProperty categoryProperty =
			assetCategoryPropertyPersistence.findByPrimaryKey(
				categoryPropertyId);

		deleteCategoryProperty(categoryProperty);
	}

	public List<AssetCategoryProperty> getCategoryProperties()
		throws SystemException {

		return assetCategoryPropertyPersistence.findAll();
	}

	public List<AssetCategoryProperty> getCategoryProperties(long entryId)
		throws SystemException {

		return assetCategoryPropertyPersistence.findByCategoryId(entryId);
	}

	public AssetCategoryProperty getCategoryProperty(long categoryPropertyId)
		throws PortalException, SystemException {

		return assetCategoryPropertyPersistence.findByPrimaryKey(
			categoryPropertyId);
	}

	public AssetCategoryProperty getCategoryProperty(
			long categoryId, String key)
		throws PortalException, SystemException {

		return assetCategoryPropertyPersistence.findByCA_K(categoryId, key);
	}

	public List<AssetCategoryProperty> getCategoryPropertyValues(
			long groupId, String key)
		throws SystemException {

		return assetCategoryPropertyFinder.findByG_K(groupId, key);
	}

	public AssetCategoryProperty updateCategoryProperty(
			long categoryPropertyId, String key, String value)
		throws PortalException, SystemException {

		validate(key, value);

		AssetCategoryProperty categoryProperty =
			assetCategoryPropertyPersistence.findByPrimaryKey(
				categoryPropertyId);

		categoryProperty.setModifiedDate(new Date());
		categoryProperty.setKey(key);
		categoryProperty.setValue(value);

		assetCategoryPropertyPersistence.update(categoryProperty, false);

		return categoryProperty;
	}

	protected void validate(String key, String value) throws PortalException {
		if (!AssetUtil.isValidWord(key)) {
			throw new CategoryPropertyKeyException();
		}

		if (!AssetUtil.isValidWord(value)) {
			throw new CategoryPropertyValueException();
		}
	}

}