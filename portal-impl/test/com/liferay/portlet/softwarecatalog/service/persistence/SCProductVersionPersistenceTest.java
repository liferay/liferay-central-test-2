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

package com.liferay.portlet.softwarecatalog.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.softwarecatalog.NoSuchProductVersionException;
import com.liferay.portlet.softwarecatalog.model.SCProductVersion;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class SCProductVersionPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (SCProductVersionPersistence)PortalBeanLocatorUtil.locate(SCProductVersionPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		SCProductVersion scProductVersion = _persistence.create(pk);

		assertNotNull(scProductVersion);

		assertEquals(scProductVersion.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		SCProductVersion newSCProductVersion = addSCProductVersion();

		_persistence.remove(newSCProductVersion);

		SCProductVersion existingSCProductVersion = _persistence.fetchByPrimaryKey(newSCProductVersion.getPrimaryKey());

		assertNull(existingSCProductVersion);
	}

	public void testUpdateNew() throws Exception {
		addSCProductVersion();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		SCProductVersion newSCProductVersion = _persistence.create(pk);

		newSCProductVersion.setCompanyId(nextLong());
		newSCProductVersion.setUserId(nextLong());
		newSCProductVersion.setUserName(randomString());
		newSCProductVersion.setCreateDate(nextDate());
		newSCProductVersion.setModifiedDate(nextDate());
		newSCProductVersion.setProductEntryId(nextLong());
		newSCProductVersion.setVersion(randomString());
		newSCProductVersion.setChangeLog(randomString());
		newSCProductVersion.setDownloadPageURL(randomString());
		newSCProductVersion.setDirectDownloadURL(randomString());
		newSCProductVersion.setRepoStoreArtifact(randomBoolean());

		_persistence.update(newSCProductVersion, false);

		SCProductVersion existingSCProductVersion = _persistence.findByPrimaryKey(newSCProductVersion.getPrimaryKey());

		assertEquals(existingSCProductVersion.getProductVersionId(),
			newSCProductVersion.getProductVersionId());
		assertEquals(existingSCProductVersion.getCompanyId(),
			newSCProductVersion.getCompanyId());
		assertEquals(existingSCProductVersion.getUserId(),
			newSCProductVersion.getUserId());
		assertEquals(existingSCProductVersion.getUserName(),
			newSCProductVersion.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingSCProductVersion.getCreateDate()),
			Time.getShortTimestamp(newSCProductVersion.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingSCProductVersion.getModifiedDate()),
			Time.getShortTimestamp(newSCProductVersion.getModifiedDate()));
		assertEquals(existingSCProductVersion.getProductEntryId(),
			newSCProductVersion.getProductEntryId());
		assertEquals(existingSCProductVersion.getVersion(),
			newSCProductVersion.getVersion());
		assertEquals(existingSCProductVersion.getChangeLog(),
			newSCProductVersion.getChangeLog());
		assertEquals(existingSCProductVersion.getDownloadPageURL(),
			newSCProductVersion.getDownloadPageURL());
		assertEquals(existingSCProductVersion.getDirectDownloadURL(),
			newSCProductVersion.getDirectDownloadURL());
		assertEquals(existingSCProductVersion.getRepoStoreArtifact(),
			newSCProductVersion.getRepoStoreArtifact());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		SCProductVersion newSCProductVersion = addSCProductVersion();

		SCProductVersion existingSCProductVersion = _persistence.findByPrimaryKey(newSCProductVersion.getPrimaryKey());

		assertEquals(existingSCProductVersion, newSCProductVersion);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchProductVersionException");
		}
		catch (NoSuchProductVersionException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		SCProductVersion newSCProductVersion = addSCProductVersion();

		SCProductVersion existingSCProductVersion = _persistence.fetchByPrimaryKey(newSCProductVersion.getPrimaryKey());

		assertEquals(existingSCProductVersion, newSCProductVersion);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		SCProductVersion missingSCProductVersion = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingSCProductVersion);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SCProductVersion newSCProductVersion = addSCProductVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCProductVersion.class,
				SCProductVersion.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("productVersionId",
				newSCProductVersion.getProductVersionId()));

		List<SCProductVersion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		SCProductVersion existingSCProductVersion = result.get(0);

		assertEquals(existingSCProductVersion, newSCProductVersion);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCProductVersion.class,
				SCProductVersion.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("productVersionId",
				nextLong()));

		List<SCProductVersion> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected SCProductVersion addSCProductVersion() throws Exception {
		long pk = nextLong();

		SCProductVersion scProductVersion = _persistence.create(pk);

		scProductVersion.setCompanyId(nextLong());
		scProductVersion.setUserId(nextLong());
		scProductVersion.setUserName(randomString());
		scProductVersion.setCreateDate(nextDate());
		scProductVersion.setModifiedDate(nextDate());
		scProductVersion.setProductEntryId(nextLong());
		scProductVersion.setVersion(randomString());
		scProductVersion.setChangeLog(randomString());
		scProductVersion.setDownloadPageURL(randomString());
		scProductVersion.setDirectDownloadURL(randomString());
		scProductVersion.setRepoStoreArtifact(randomBoolean());

		_persistence.update(scProductVersion, false);

		return scProductVersion;
	}

	private SCProductVersionPersistence _persistence;
}