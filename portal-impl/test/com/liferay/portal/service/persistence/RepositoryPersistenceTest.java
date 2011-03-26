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

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchRepositoryException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.Repository;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class RepositoryPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (RepositoryPersistence)PortalBeanLocatorUtil.locate(RepositoryPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		Repository repository = _persistence.create(pk);

		assertNotNull(repository);

		assertEquals(repository.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		Repository newRepository = addRepository();

		_persistence.remove(newRepository);

		Repository existingRepository = _persistence.fetchByPrimaryKey(newRepository.getPrimaryKey());

		assertNull(existingRepository);
	}

	public void testUpdateNew() throws Exception {
		addRepository();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		Repository newRepository = _persistence.create(pk);

		newRepository.setGroupId(nextLong());
		newRepository.setCompanyId(nextLong());
		newRepository.setCreateDate(nextDate());
		newRepository.setModifiedDate(nextDate());
		newRepository.setClassNameId(nextLong());
		newRepository.setName(randomString());
		newRepository.setDescription(randomString());
		newRepository.setPortletId(randomString());
		newRepository.setTypeSettings(randomString());
		newRepository.setDlFolderId(nextLong());

		_persistence.update(newRepository, false);

		Repository existingRepository = _persistence.findByPrimaryKey(newRepository.getPrimaryKey());

		assertEquals(existingRepository.getRepositoryId(),
			newRepository.getRepositoryId());
		assertEquals(existingRepository.getGroupId(), newRepository.getGroupId());
		assertEquals(existingRepository.getCompanyId(),
			newRepository.getCompanyId());
		assertEquals(Time.getShortTimestamp(existingRepository.getCreateDate()),
			Time.getShortTimestamp(newRepository.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingRepository.getModifiedDate()),
			Time.getShortTimestamp(newRepository.getModifiedDate()));
		assertEquals(existingRepository.getClassNameId(),
			newRepository.getClassNameId());
		assertEquals(existingRepository.getName(), newRepository.getName());
		assertEquals(existingRepository.getDescription(),
			newRepository.getDescription());
		assertEquals(existingRepository.getPortletId(),
			newRepository.getPortletId());
		assertEquals(existingRepository.getTypeSettings(),
			newRepository.getTypeSettings());
		assertEquals(existingRepository.getDlFolderId(),
			newRepository.getDlFolderId());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		Repository newRepository = addRepository();

		Repository existingRepository = _persistence.findByPrimaryKey(newRepository.getPrimaryKey());

		assertEquals(existingRepository, newRepository);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchRepositoryException");
		}
		catch (NoSuchRepositoryException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		Repository newRepository = addRepository();

		Repository existingRepository = _persistence.fetchByPrimaryKey(newRepository.getPrimaryKey());

		assertEquals(existingRepository, newRepository);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		Repository missingRepository = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingRepository);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Repository newRepository = addRepository();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Repository.class,
				Repository.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("repositoryId",
				newRepository.getRepositoryId()));

		List<Repository> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Repository existingRepository = result.get(0);

		assertEquals(existingRepository, newRepository);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Repository.class,
				Repository.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("repositoryId", nextLong()));

		List<Repository> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Repository newRepository = addRepository();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Repository.class,
				Repository.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"repositoryId"));

		Object newRepositoryId = newRepository.getRepositoryId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("repositoryId",
				new Object[] { newRepositoryId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingRepositoryId = result.get(0);

		assertEquals(existingRepositoryId, newRepositoryId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		Repository newRepository = addRepository();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Repository.class,
				Repository.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"repositoryId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("repositoryId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected Repository addRepository() throws Exception {
		long pk = nextLong();

		Repository repository = _persistence.create(pk);

		repository.setGroupId(nextLong());
		repository.setCompanyId(nextLong());
		repository.setCreateDate(nextDate());
		repository.setModifiedDate(nextDate());
		repository.setClassNameId(nextLong());
		repository.setName(randomString());
		repository.setDescription(randomString());
		repository.setPortletId(randomString());
		repository.setTypeSettings(randomString());
		repository.setDlFolderId(nextLong());

		_persistence.update(repository, false);

		return repository;
	}

	private RepositoryPersistence _persistence;
}