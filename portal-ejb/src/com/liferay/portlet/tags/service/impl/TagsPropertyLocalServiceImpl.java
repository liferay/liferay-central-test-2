/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.tags.service.impl;

import com.liferay.counter.model.Counter;
import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portlet.tags.PropertyKeyException;
import com.liferay.portlet.tags.PropertyValueException;
import com.liferay.portlet.tags.model.TagsEntry;
import com.liferay.portlet.tags.model.TagsProperty;
import com.liferay.portlet.tags.service.TagsEntryLocalServiceUtil;
import com.liferay.portlet.tags.service.base.TagsPropertyLocalServiceBaseImpl;
import com.liferay.portlet.tags.service.persistence.TagsPropertyFinder;
import com.liferay.portlet.tags.service.persistence.TagsPropertyKeyFinder;
import com.liferay.portlet.tags.service.persistence.TagsPropertyUtil;
import com.liferay.util.Validator;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * <a href="TagsPropertyLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TagsPropertyLocalServiceImpl
	extends TagsPropertyLocalServiceBaseImpl {

	public TagsProperty addProperty(
			long userId, long entryId, String key, String value)
		throws PortalException, SystemException {

		User user = UserUtil.findByPrimaryKey(userId);
		Date now = new Date();

		validate(key, value);

		long propertyId = CounterLocalServiceUtil.increment(
			Counter.class.getName());

		TagsProperty property = TagsPropertyUtil.create(propertyId);

		property.setCompanyId(user.getCompanyId());
		property.setUserId(user.getUserId());
		property.setUserName(user.getFullName());
		property.setCreateDate(now);
		property.setModifiedDate(now);
		property.setEntryId(entryId);
		property.setKey(key);
		property.setValue(value);

		TagsPropertyUtil.update(property);

		return property;
	}

	public TagsProperty addProperty(
			long userId, String entryName, String key, String value)
		throws PortalException, SystemException {

		User user = UserUtil.findByPrimaryKey(userId);

		TagsEntry entry = TagsEntryLocalServiceUtil.getEntry(
			user.getCompanyId(), entryName);

		return addProperty(userId, entry.getEntryId(), key, value);
	}

	public void deleteProperties(long entryId)
		throws PortalException, SystemException {

		Iterator itr = TagsPropertyUtil.findByEntryId(entryId).iterator();

		while (itr.hasNext()) {
			TagsProperty property = (TagsProperty)itr.next();

			deleteProperty(property);
		}
	}

	public void deleteProperty(long propertyId)
		throws PortalException, SystemException {

		TagsProperty property = TagsPropertyUtil.findByPrimaryKey(propertyId);

		deleteProperty(property);
	}

	public void deleteProperty(TagsProperty property)
		throws PortalException, SystemException {

		TagsPropertyUtil.remove(property.getPropertyId());
	}

	public List getProperties() throws SystemException {
		return TagsPropertyUtil.findAll();
	}

	public List getProperties(long entryId) throws SystemException {
		return TagsPropertyUtil.findByEntryId(entryId);
	}

	public TagsProperty getProperty(long propertyId)
		throws PortalException, SystemException {

		return TagsPropertyUtil.findByPrimaryKey(propertyId);
	}

	public TagsProperty getProperty(long entryId, String key)
		throws PortalException, SystemException {

		return TagsPropertyUtil.findByE_K(entryId, key);
	}

	public String[] getPropertyKeys(String companyId) throws SystemException {
		return TagsPropertyKeyFinder.findByCompanyId(companyId);
	}

	public List getPropertyValues(String companyId, String key)
		throws SystemException {

		return TagsPropertyFinder.findByC_K(companyId, key);
	}

	public TagsProperty updateProperty(
			long propertyId, String key, String value)
		throws PortalException, SystemException {

		validate(key, value);

		TagsProperty property = TagsPropertyUtil.findByPrimaryKey(propertyId);

		property.setModifiedDate(new Date());
		property.setKey(key);
		property.setValue(value);

		TagsPropertyUtil.update(property);

		return property;
	}

	protected void validate(String key, String value)
		throws PortalException, SystemException {

		if (Validator.isNull(key)) {
			throw new PropertyKeyException();
		}
		else {
			char[] c = key.toCharArray();

			for (int i = 0; i < c.length; i++) {
				if (!Validator.isChar(c[i]) && !Validator.isDigit(c[i]) &&
					(c[i] != ' ')) {

					throw new PropertyKeyException();
				}
			}
		}

		if (Validator.isNull(value)) {
			throw new PropertyValueException();
		}
		else {
			char[] c = value.toCharArray();

			for (int i = 0; i < c.length; i++) {
				if (!Validator.isChar(c[i]) && !Validator.isDigit(c[i]) &&
					(c[i] != ' ')) {

					throw new PropertyValueException();
				}
			}
		}
	}

}