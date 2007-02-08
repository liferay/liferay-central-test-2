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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portlet.tags.DuplicateEntryException;
import com.liferay.portlet.tags.EntryNameException;
import com.liferay.portlet.tags.model.TagsEntry;
import com.liferay.portlet.tags.model.TagsProperty;
import com.liferay.portlet.tags.service.TagsPropertyLocalServiceUtil;
import com.liferay.portlet.tags.service.base.TagsEntryLocalServiceBaseImpl;
import com.liferay.portlet.tags.service.persistence.TagsEntryFinder;
import com.liferay.portlet.tags.service.persistence.TagsEntryUtil;
import com.liferay.portlet.tags.service.persistence.TagsPropertyUtil;
import com.liferay.util.CollectionFactory;
import com.liferay.util.GetterUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;
import com.liferay.util.Autocomplete;

import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * <a href="TagsEntryLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TagsEntryLocalServiceImpl extends TagsEntryLocalServiceBaseImpl {

	public TagsEntry addEntry(String userId, String name)
		throws PortalException, SystemException {

		User user = UserUtil.findByPrimaryKey(userId);
		Date now = new Date();
		name = name.trim().toLowerCase();

		validate(name);

		if (hasEntry(user.getCompanyId(), name)) {
			throw new DuplicateEntryException();
		}

		long entryId = CounterLocalServiceUtil.increment(
			Counter.class.getName());

		TagsEntry entry = TagsEntryUtil.create(entryId);

		entry.setCompanyId(user.getCompanyId());
		entry.setUserId(user.getUserId());
		entry.setUserName(user.getFullName());
		entry.setCreateDate(now);
		entry.setModifiedDate(now);
		entry.setName(name);

		TagsEntryUtil.update(entry);

		return entry;
	}

	public void deleteEntry(long entryId)
		throws PortalException, SystemException {

		TagsEntry entry = TagsEntryUtil.findByPrimaryKey(entryId);

		deleteEntry(entry);
	}

	public void deleteEntry(TagsEntry entry)
		throws PortalException, SystemException {

		// Properties

		TagsPropertyLocalServiceUtil.deleteProperties(entry.getEntryId());

		// Entry

		TagsEntryUtil.remove(entry.getEntryId());
	}

	public boolean hasEntry(String companyId, String name)
		throws PortalException, SystemException {

		if (TagsEntryUtil.fetchByC_N(companyId, name) == null) {
			return false;
		}
		else {
			return true;
		}
	}

	public List getEntries() throws SystemException {
		return TagsEntryUtil.findAll();
	}

	public TagsEntry getEntry(long entryId)
		throws PortalException, SystemException {

		return TagsEntryUtil.findByPrimaryKey(entryId);
	}

	public TagsEntry getEntry(String companyId, String name)
		throws PortalException, SystemException {

		return TagsEntryUtil.findByC_N(companyId, name);
	}

	public List search(String companyId, String name, String[] properties)
		throws SystemException {

		return TagsEntryFinder.findByC_N_P(companyId, name, properties);
	}

	public List search(
			String companyId, String name, String[] properties, int begin,
			int end)
		throws SystemException {

		return TagsEntryFinder.findByC_N_P(
			companyId, name, properties, begin, end);
	}

	public String searchAutocomplete(
			String companyId, String name, String[] properties, int begin,
			int end)
		throws SystemException {

		List list = TagsEntryFinder.findByC_N_P(
			companyId, name, properties, begin, end);

		return Autocomplete.listToXml(list, "entryId", "name");
	}

	public int searchCount(String companyId, String name, String[] properties)
		throws SystemException {

		return TagsEntryFinder.countByC_N_P(companyId, name, properties);
	}

	public TagsEntry updateEntry(long entryId, String name)
		throws PortalException, SystemException {

		name = name.trim().toLowerCase();

		validate(name);

		TagsEntry entry = TagsEntryUtil.findByPrimaryKey(entryId);

		if (!entry.getName().equals(name)) {
			if (hasEntry(entry.getCompanyId(), name)) {
				throw new DuplicateEntryException();
			}
		}

		entry.setModifiedDate(new Date());
		entry.setName(name);

		TagsEntryUtil.update(entry);

		return entry;
	}

	public TagsEntry updateEntry(
			String userId, long entryId, String name, String[] properties)
		throws PortalException, SystemException {

		TagsEntry entry = updateEntry(entryId, name);

		List curProperties = TagsPropertyUtil.findByEntryId(entryId);
		Set keepProperties = CollectionFactory.getHashSet();

		for (int i = 0; i < properties.length; i++) {
			String[] property = StringUtil.split(properties[i], "|");

			Long propertyId = new Long(0);

			if (property.length > 0) {
				propertyId = new Long(GetterUtil.getLong(property[0]));
			}

			String key = StringPool.BLANK;

			if (property.length > 1) {
				key = GetterUtil.getString(property[1]);
			}

			String value = StringPool.BLANK;

			if (property.length > 2) {
				value = GetterUtil.getString(property[2]);
			}

			if (propertyId.longValue() == 0) {
				if (Validator.isNotNull(key)) {
					TagsPropertyLocalServiceUtil.addProperty(
						userId, entryId, key, value);
				}
			}
			else {
				if (Validator.isNull(key)) {
					TagsPropertyLocalServiceUtil.deleteProperty(
						propertyId.longValue());
				}
				else {
					TagsPropertyLocalServiceUtil.updateProperty(
						propertyId.longValue(), key, value);

					keepProperties.add(new Long(propertyId.longValue()));
				}
			}
		}

		Iterator itr = curProperties.iterator();

		while (itr.hasNext()) {
			TagsProperty property = (TagsProperty)itr.next();

			if (!keepProperties.contains(new Long(property.getPropertyId()))) {
				TagsPropertyLocalServiceUtil.deleteProperty(property);
			}
		}

		return entry;
	}

	protected void validate(String name)
		throws PortalException, SystemException {

		if (Validator.isNull(name)) {
			throw new EntryNameException();
		}
		else {
			char[] c = name.toCharArray();

			for (int i = 0; i < c.length; i++) {
				if (!Validator.isChar(c[i]) && (c[i] != ' ')) {
					throw new EntryNameException();
				}
			}
		}
	}

}