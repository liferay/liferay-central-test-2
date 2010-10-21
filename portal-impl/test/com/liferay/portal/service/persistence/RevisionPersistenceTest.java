/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchRevisionException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.Revision;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class RevisionPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (RevisionPersistence)PortalBeanLocatorUtil.locate(RevisionPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		Revision revision = _persistence.create(pk);

		assertNotNull(revision);

		assertEquals(revision.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		Revision newRevision = addRevision();

		_persistence.remove(newRevision);

		Revision existingRevision = _persistence.fetchByPrimaryKey(newRevision.getPrimaryKey());

		assertNull(existingRevision);
	}

	public void testUpdateNew() throws Exception {
		addRevision();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		Revision newRevision = _persistence.create(pk);

		newRevision.setGroupId(nextLong());
		newRevision.setCompanyId(nextLong());
		newRevision.setUserId(nextLong());
		newRevision.setUserName(randomString());
		newRevision.setCreateDate(nextDate());
		newRevision.setModifiedDate(nextDate());
		newRevision.setBranchId(nextLong());
		newRevision.setPlid(nextLong());
		newRevision.setParentRevisionId(nextLong());
		newRevision.setHead(randomBoolean());
		newRevision.setName(randomString());
		newRevision.setTitle(randomString());
		newRevision.setDescription(randomString());
		newRevision.setTypeSettings(randomString());
		newRevision.setIconImage(randomBoolean());
		newRevision.setIconImageId(nextLong());
		newRevision.setThemeId(randomString());
		newRevision.setColorSchemeId(randomString());
		newRevision.setWapThemeId(randomString());
		newRevision.setWapColorSchemeId(randomString());
		newRevision.setCss(randomString());
		newRevision.setStatus(nextInt());
		newRevision.setStatusByUserId(nextLong());
		newRevision.setStatusByUserName(randomString());
		newRevision.setStatusDate(nextDate());

		_persistence.update(newRevision, false);

		Revision existingRevision = _persistence.findByPrimaryKey(newRevision.getPrimaryKey());

		assertEquals(existingRevision.getRevisionId(),
			newRevision.getRevisionId());
		assertEquals(existingRevision.getGroupId(), newRevision.getGroupId());
		assertEquals(existingRevision.getCompanyId(), newRevision.getCompanyId());
		assertEquals(existingRevision.getUserId(), newRevision.getUserId());
		assertEquals(existingRevision.getUserName(), newRevision.getUserName());
		assertEquals(Time.getShortTimestamp(existingRevision.getCreateDate()),
			Time.getShortTimestamp(newRevision.getCreateDate()));
		assertEquals(Time.getShortTimestamp(existingRevision.getModifiedDate()),
			Time.getShortTimestamp(newRevision.getModifiedDate()));
		assertEquals(existingRevision.getBranchId(), newRevision.getBranchId());
		assertEquals(existingRevision.getPlid(), newRevision.getPlid());
		assertEquals(existingRevision.getParentRevisionId(),
			newRevision.getParentRevisionId());
		assertEquals(existingRevision.getHead(), newRevision.getHead());
		assertEquals(existingRevision.getName(), newRevision.getName());
		assertEquals(existingRevision.getTitle(), newRevision.getTitle());
		assertEquals(existingRevision.getDescription(),
			newRevision.getDescription());
		assertEquals(existingRevision.getTypeSettings(),
			newRevision.getTypeSettings());
		assertEquals(existingRevision.getIconImage(), newRevision.getIconImage());
		assertEquals(existingRevision.getIconImageId(),
			newRevision.getIconImageId());
		assertEquals(existingRevision.getThemeId(), newRevision.getThemeId());
		assertEquals(existingRevision.getColorSchemeId(),
			newRevision.getColorSchemeId());
		assertEquals(existingRevision.getWapThemeId(),
			newRevision.getWapThemeId());
		assertEquals(existingRevision.getWapColorSchemeId(),
			newRevision.getWapColorSchemeId());
		assertEquals(existingRevision.getCss(), newRevision.getCss());
		assertEquals(existingRevision.getStatus(), newRevision.getStatus());
		assertEquals(existingRevision.getStatusByUserId(),
			newRevision.getStatusByUserId());
		assertEquals(existingRevision.getStatusByUserName(),
			newRevision.getStatusByUserName());
		assertEquals(Time.getShortTimestamp(existingRevision.getStatusDate()),
			Time.getShortTimestamp(newRevision.getStatusDate()));
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		Revision newRevision = addRevision();

		Revision existingRevision = _persistence.findByPrimaryKey(newRevision.getPrimaryKey());

		assertEquals(existingRevision, newRevision);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchRevisionException");
		}
		catch (NoSuchRevisionException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		Revision newRevision = addRevision();

		Revision existingRevision = _persistence.fetchByPrimaryKey(newRevision.getPrimaryKey());

		assertEquals(existingRevision, newRevision);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		Revision missingRevision = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingRevision);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Revision newRevision = addRevision();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Revision.class,
				Revision.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("revisionId",
				newRevision.getRevisionId()));

		List<Revision> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Revision existingRevision = result.get(0);

		assertEquals(existingRevision, newRevision);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Revision.class,
				Revision.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("revisionId", nextLong()));

		List<Revision> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected Revision addRevision() throws Exception {
		long pk = nextLong();

		Revision revision = _persistence.create(pk);

		revision.setGroupId(nextLong());
		revision.setCompanyId(nextLong());
		revision.setUserId(nextLong());
		revision.setUserName(randomString());
		revision.setCreateDate(nextDate());
		revision.setModifiedDate(nextDate());
		revision.setBranchId(nextLong());
		revision.setPlid(nextLong());
		revision.setParentRevisionId(nextLong());
		revision.setHead(randomBoolean());
		revision.setName(randomString());
		revision.setTitle(randomString());
		revision.setDescription(randomString());
		revision.setTypeSettings(randomString());
		revision.setIconImage(randomBoolean());
		revision.setIconImageId(nextLong());
		revision.setThemeId(randomString());
		revision.setColorSchemeId(randomString());
		revision.setWapThemeId(randomString());
		revision.setWapColorSchemeId(randomString());
		revision.setCss(randomString());
		revision.setStatus(nextInt());
		revision.setStatusByUserId(nextLong());
		revision.setStatusByUserName(randomString());
		revision.setStatusDate(nextDate());

		_persistence.update(revision, false);

		return revision;
	}

	private RevisionPersistence _persistence;
}