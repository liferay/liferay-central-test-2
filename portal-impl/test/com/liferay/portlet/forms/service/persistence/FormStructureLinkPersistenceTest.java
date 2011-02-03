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
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.forms.NoSuchFormStructureLinkException;
import com.liferay.portlet.forms.model.FormStructureLink;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class FormStructureLinkPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (FormStructureLinkPersistence)PortalBeanLocatorUtil.locate(FormStructureLinkPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		FormStructureLink formStructureLink = _persistence.create(pk);

		assertNotNull(formStructureLink);

		assertEquals(formStructureLink.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		FormStructureLink newFormStructureLink = addFormStructureLink();

		_persistence.remove(newFormStructureLink);

		FormStructureLink existingFormStructureLink = _persistence.fetchByPrimaryKey(newFormStructureLink.getPrimaryKey());

		assertNull(existingFormStructureLink);
	}

	public void testUpdateNew() throws Exception {
		addFormStructureLink();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		FormStructureLink newFormStructureLink = _persistence.create(pk);

		newFormStructureLink.setFormStructureId(randomString());
		newFormStructureLink.setClassName(randomString());
		newFormStructureLink.setClassPK(nextLong());

		_persistence.update(newFormStructureLink, false);

		FormStructureLink existingFormStructureLink = _persistence.findByPrimaryKey(newFormStructureLink.getPrimaryKey());

		assertEquals(existingFormStructureLink.getFormStructureLinkId(),
			newFormStructureLink.getFormStructureLinkId());
		assertEquals(existingFormStructureLink.getFormStructureId(),
			newFormStructureLink.getFormStructureId());
		assertEquals(existingFormStructureLink.getClassName(),
			newFormStructureLink.getClassName());
		assertEquals(existingFormStructureLink.getClassPK(),
			newFormStructureLink.getClassPK());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		FormStructureLink newFormStructureLink = addFormStructureLink();

		FormStructureLink existingFormStructureLink = _persistence.findByPrimaryKey(newFormStructureLink.getPrimaryKey());

		assertEquals(existingFormStructureLink, newFormStructureLink);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail(
				"Missing entity did not throw NoSuchFormStructureLinkException");
		}
		catch (NoSuchFormStructureLinkException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		FormStructureLink newFormStructureLink = addFormStructureLink();

		FormStructureLink existingFormStructureLink = _persistence.fetchByPrimaryKey(newFormStructureLink.getPrimaryKey());

		assertEquals(existingFormStructureLink, newFormStructureLink);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		FormStructureLink missingFormStructureLink = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingFormStructureLink);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		FormStructureLink newFormStructureLink = addFormStructureLink();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(FormStructureLink.class,
				FormStructureLink.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("formStructureLinkId",
				newFormStructureLink.getFormStructureLinkId()));

		List<FormStructureLink> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		FormStructureLink existingFormStructureLink = result.get(0);

		assertEquals(existingFormStructureLink, newFormStructureLink);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(FormStructureLink.class,
				FormStructureLink.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("formStructureLinkId",
				nextLong()));

		List<FormStructureLink> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected FormStructureLink addFormStructureLink()
		throws Exception {
		long pk = nextLong();

		FormStructureLink formStructureLink = _persistence.create(pk);

		formStructureLink.setFormStructureId(randomString());
		formStructureLink.setClassName(randomString());
		formStructureLink.setClassPK(nextLong());

		_persistence.update(formStructureLink, false);

		return formStructureLink;
	}

	private FormStructureLinkPersistence _persistence;
}