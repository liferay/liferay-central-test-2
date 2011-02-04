/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.forms.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.forms.NoSuchFormStructureException;
import com.liferay.portlet.forms.model.FormStructure;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class FormStructurePersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (FormStructurePersistence)PortalBeanLocatorUtil.locate(FormStructurePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		FormStructure formStructure = _persistence.create(pk);

		assertNotNull(formStructure);

		assertEquals(formStructure.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		FormStructure newFormStructure = addFormStructure();

		_persistence.remove(newFormStructure);

		FormStructure existingFormStructure = _persistence.fetchByPrimaryKey(newFormStructure.getPrimaryKey());

		assertNull(existingFormStructure);
	}

	public void testUpdateNew() throws Exception {
		addFormStructure();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		FormStructure newFormStructure = _persistence.create(pk);

		newFormStructure.setUuid(randomString());
		newFormStructure.setGroupId(nextLong());
		newFormStructure.setCompanyId(nextLong());
		newFormStructure.setUserId(nextLong());
		newFormStructure.setUserName(randomString());
		newFormStructure.setCreateDate(nextDate());
		newFormStructure.setModifiedDate(nextDate());
		newFormStructure.setFormStructureId(randomString());
		newFormStructure.setName(randomString());
		newFormStructure.setDescription(randomString());
		newFormStructure.setXsd(randomString());

		_persistence.update(newFormStructure, false);

		FormStructure existingFormStructure = _persistence.findByPrimaryKey(newFormStructure.getPrimaryKey());

		assertEquals(existingFormStructure.getUuid(), newFormStructure.getUuid());
		assertEquals(existingFormStructure.getId(), newFormStructure.getId());
		assertEquals(existingFormStructure.getGroupId(),
			newFormStructure.getGroupId());
		assertEquals(existingFormStructure.getCompanyId(),
			newFormStructure.getCompanyId());
		assertEquals(existingFormStructure.getUserId(),
			newFormStructure.getUserId());
		assertEquals(existingFormStructure.getUserName(),
			newFormStructure.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingFormStructure.getCreateDate()),
			Time.getShortTimestamp(newFormStructure.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingFormStructure.getModifiedDate()),
			Time.getShortTimestamp(newFormStructure.getModifiedDate()));
		assertEquals(existingFormStructure.getFormStructureId(),
			newFormStructure.getFormStructureId());
		assertEquals(existingFormStructure.getName(), newFormStructure.getName());
		assertEquals(existingFormStructure.getDescription(),
			newFormStructure.getDescription());
		assertEquals(existingFormStructure.getXsd(), newFormStructure.getXsd());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		FormStructure newFormStructure = addFormStructure();

		FormStructure existingFormStructure = _persistence.findByPrimaryKey(newFormStructure.getPrimaryKey());

		assertEquals(existingFormStructure, newFormStructure);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchFormStructureException");
		}
		catch (NoSuchFormStructureException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		FormStructure newFormStructure = addFormStructure();

		FormStructure existingFormStructure = _persistence.fetchByPrimaryKey(newFormStructure.getPrimaryKey());

		assertEquals(existingFormStructure, newFormStructure);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		FormStructure missingFormStructure = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingFormStructure);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		FormStructure newFormStructure = addFormStructure();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(FormStructure.class,
				FormStructure.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id",
				newFormStructure.getId()));

		List<FormStructure> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		FormStructure existingFormStructure = result.get(0);

		assertEquals(existingFormStructure, newFormStructure);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(FormStructure.class,
				FormStructure.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id", nextLong()));

		List<FormStructure> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected FormStructure addFormStructure() throws Exception {
		long pk = nextLong();

		FormStructure formStructure = _persistence.create(pk);

		formStructure.setUuid(randomString());
		formStructure.setGroupId(nextLong());
		formStructure.setCompanyId(nextLong());
		formStructure.setUserId(nextLong());
		formStructure.setUserName(randomString());
		formStructure.setCreateDate(nextDate());
		formStructure.setModifiedDate(nextDate());
		formStructure.setFormStructureId(randomString());
		formStructure.setName(randomString());
		formStructure.setDescription(randomString());
		formStructure.setXsd(randomString());

		_persistence.update(formStructure, false);

		return formStructure;
	}

	private FormStructurePersistence _persistence;
}