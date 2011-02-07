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

import com.liferay.portlet.forms.NoSuchStructureEntryLinkException;
import com.liferay.portlet.forms.model.FormsStructureEntryLink;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class FormsStructureEntryLinkPersistenceTest
	extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (FormsStructureEntryLinkPersistence)PortalBeanLocatorUtil.locate(FormsStructureEntryLinkPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		FormsStructureEntryLink formsStructureEntryLink = _persistence.create(pk);

		assertNotNull(formsStructureEntryLink);

		assertEquals(formsStructureEntryLink.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		FormsStructureEntryLink newFormsStructureEntryLink = addFormsStructureEntryLink();

		_persistence.remove(newFormsStructureEntryLink);

		FormsStructureEntryLink existingFormsStructureEntryLink = _persistence.fetchByPrimaryKey(newFormsStructureEntryLink.getPrimaryKey());

		assertNull(existingFormsStructureEntryLink);
	}

	public void testUpdateNew() throws Exception {
		addFormsStructureEntryLink();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		FormsStructureEntryLink newFormsStructureEntryLink = _persistence.create(pk);

		newFormsStructureEntryLink.setFormStructureId(randomString());
		newFormsStructureEntryLink.setClassName(randomString());
		newFormsStructureEntryLink.setClassPK(nextLong());

		_persistence.update(newFormsStructureEntryLink, false);

		FormsStructureEntryLink existingFormsStructureEntryLink = _persistence.findByPrimaryKey(newFormsStructureEntryLink.getPrimaryKey());

		assertEquals(existingFormsStructureEntryLink.getFormStructureLinkId(),
			newFormsStructureEntryLink.getFormStructureLinkId());
		assertEquals(existingFormsStructureEntryLink.getFormStructureId(),
			newFormsStructureEntryLink.getFormStructureId());
		assertEquals(existingFormsStructureEntryLink.getClassName(),
			newFormsStructureEntryLink.getClassName());
		assertEquals(existingFormsStructureEntryLink.getClassPK(),
			newFormsStructureEntryLink.getClassPK());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		FormsStructureEntryLink newFormsStructureEntryLink = addFormsStructureEntryLink();

		FormsStructureEntryLink existingFormsStructureEntryLink = _persistence.findByPrimaryKey(newFormsStructureEntryLink.getPrimaryKey());

		assertEquals(existingFormsStructureEntryLink, newFormsStructureEntryLink);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail(
				"Missing entity did not throw NoSuchStructureEntryLinkException");
		}
		catch (NoSuchStructureEntryLinkException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		FormsStructureEntryLink newFormsStructureEntryLink = addFormsStructureEntryLink();

		FormsStructureEntryLink existingFormsStructureEntryLink = _persistence.fetchByPrimaryKey(newFormsStructureEntryLink.getPrimaryKey());

		assertEquals(existingFormsStructureEntryLink, newFormsStructureEntryLink);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		FormsStructureEntryLink missingFormsStructureEntryLink = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingFormsStructureEntryLink);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		FormsStructureEntryLink newFormsStructureEntryLink = addFormsStructureEntryLink();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(FormsStructureEntryLink.class,
				FormsStructureEntryLink.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("formStructureLinkId",
				newFormsStructureEntryLink.getFormStructureLinkId()));

		List<FormsStructureEntryLink> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		FormsStructureEntryLink existingFormsStructureEntryLink = result.get(0);

		assertEquals(existingFormsStructureEntryLink, newFormsStructureEntryLink);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(FormsStructureEntryLink.class,
				FormsStructureEntryLink.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("formStructureLinkId",
				nextLong()));

		List<FormsStructureEntryLink> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected FormsStructureEntryLink addFormsStructureEntryLink()
		throws Exception {
		long pk = nextLong();

		FormsStructureEntryLink formsStructureEntryLink = _persistence.create(pk);

		formsStructureEntryLink.setFormStructureId(randomString());
		formsStructureEntryLink.setClassName(randomString());
		formsStructureEntryLink.setClassPK(nextLong());

		_persistence.update(formsStructureEntryLink, false);

		return formsStructureEntryLink;
	}

	private FormsStructureEntryLinkPersistence _persistence;
}