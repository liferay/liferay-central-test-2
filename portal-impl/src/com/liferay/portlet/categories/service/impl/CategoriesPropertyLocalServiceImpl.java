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

package com.liferay.portlet.categories.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portlet.tags.PropertyKeyException;
import com.liferay.portlet.tags.PropertyValueException;
import com.liferay.portlet.tags.util.TagsUtil;
import com.liferay.portlet.categories.service.base.CategoriesPropertyLocalServiceBaseImpl;
import com.liferay.portlet.categories.model.CategoriesProperty;

import java.util.Date;
import java.util.List;

/**
 * <a href="CategoriesPropertyLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class CategoriesPropertyLocalServiceImpl
	extends CategoriesPropertyLocalServiceBaseImpl {

	public CategoriesProperty addProperty(
			long userId, long entryId, String key, String value)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		validate(key, value);

		long propertyId = counterLocalService.increment();

		CategoriesProperty property = categoriesPropertyPersistence.create(propertyId);

		property.setCompanyId(user.getCompanyId());
		property.setUserId(user.getUserId());
		property.setUserName(user.getFullName());
		property.setCreateDate(now);
		property.setModifiedDate(now);
		property.setEntryId(entryId);
		property.setKey(key);
		property.setValue(value);

		categoriesPropertyPersistence.update(property, false);

		return property;
	}

	public void deleteProperties(long entryId) throws SystemException {
		List<CategoriesProperty> properties = categoriesPropertyPersistence.findByEntryId(
			entryId);

		for (CategoriesProperty property : properties) {
			deleteProperty(property);
		}
	}

	public void deleteProperty(long propertyId)
		throws PortalException, SystemException {

		CategoriesProperty property = categoriesPropertyPersistence.findByPrimaryKey(
			propertyId);

		deleteProperty(property);
	}

	public void deleteProperty(CategoriesProperty property) throws SystemException {
		categoriesPropertyPersistence.remove(property);
	}

	public List<CategoriesProperty> getProperties() throws SystemException {
		return categoriesPropertyPersistence.findAll();
	}

	public List<CategoriesProperty> getProperties(long entryId)
		throws SystemException {

		return categoriesPropertyPersistence.findByEntryId(entryId);
	}

	public CategoriesProperty getProperty(long propertyId)
		throws PortalException, SystemException {

		return categoriesPropertyPersistence.findByPrimaryKey(propertyId);
	}

	public CategoriesProperty getProperty(long entryId, String key)
		throws PortalException, SystemException {

		return categoriesPropertyPersistence.findByE_K(entryId, key);
	}

	public List<CategoriesProperty> getPropertyValues(long groupId, String key)
		throws SystemException {

		return categoriesPropertyFinder.findByG_K(groupId, key);
	}

	public CategoriesProperty updateProperty(
			long propertyId, String key, String value)
		throws PortalException, SystemException {

		validate(key, value);

		CategoriesProperty property = categoriesPropertyPersistence.findByPrimaryKey(
			propertyId);

		property.setModifiedDate(new Date());
		property.setKey(key);
		property.setValue(value);

		categoriesPropertyPersistence.update(property, false);

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