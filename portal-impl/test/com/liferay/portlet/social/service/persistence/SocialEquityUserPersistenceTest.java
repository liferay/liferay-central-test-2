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

package com.liferay.portlet.social.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.social.NoSuchEquityUserException;
import com.liferay.portlet.social.model.SocialEquityUser;
import com.liferay.portlet.social.model.impl.SocialEquityUserModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class SocialEquityUserPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (SocialEquityUserPersistence)PortalBeanLocatorUtil.locate(SocialEquityUserPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		SocialEquityUser socialEquityUser = _persistence.create(pk);

		assertNotNull(socialEquityUser);

		assertEquals(socialEquityUser.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		SocialEquityUser newSocialEquityUser = addSocialEquityUser();

		_persistence.remove(newSocialEquityUser);

		SocialEquityUser existingSocialEquityUser = _persistence.fetchByPrimaryKey(newSocialEquityUser.getPrimaryKey());

		assertNull(existingSocialEquityUser);
	}

	public void testUpdateNew() throws Exception {
		addSocialEquityUser();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		SocialEquityUser newSocialEquityUser = _persistence.create(pk);

		newSocialEquityUser.setGroupId(nextLong());
		newSocialEquityUser.setCompanyId(nextLong());
		newSocialEquityUser.setUserId(nextLong());
		newSocialEquityUser.setContributionK(nextDouble());
		newSocialEquityUser.setContributionB(nextDouble());
		newSocialEquityUser.setParticipationK(nextDouble());
		newSocialEquityUser.setParticipationB(nextDouble());
		newSocialEquityUser.setRank(nextInt());

		_persistence.update(newSocialEquityUser, false);

		SocialEquityUser existingSocialEquityUser = _persistence.findByPrimaryKey(newSocialEquityUser.getPrimaryKey());

		assertEquals(existingSocialEquityUser.getEquityUserId(),
			newSocialEquityUser.getEquityUserId());
		assertEquals(existingSocialEquityUser.getGroupId(),
			newSocialEquityUser.getGroupId());
		assertEquals(existingSocialEquityUser.getCompanyId(),
			newSocialEquityUser.getCompanyId());
		assertEquals(existingSocialEquityUser.getUserId(),
			newSocialEquityUser.getUserId());
		assertEquals(existingSocialEquityUser.getContributionK(),
			newSocialEquityUser.getContributionK());
		assertEquals(existingSocialEquityUser.getContributionB(),
			newSocialEquityUser.getContributionB());
		assertEquals(existingSocialEquityUser.getParticipationK(),
			newSocialEquityUser.getParticipationK());
		assertEquals(existingSocialEquityUser.getParticipationB(),
			newSocialEquityUser.getParticipationB());
		assertEquals(existingSocialEquityUser.getRank(),
			newSocialEquityUser.getRank());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		SocialEquityUser newSocialEquityUser = addSocialEquityUser();

		SocialEquityUser existingSocialEquityUser = _persistence.findByPrimaryKey(newSocialEquityUser.getPrimaryKey());

		assertEquals(existingSocialEquityUser, newSocialEquityUser);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchEquityUserException");
		}
		catch (NoSuchEquityUserException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		SocialEquityUser newSocialEquityUser = addSocialEquityUser();

		SocialEquityUser existingSocialEquityUser = _persistence.fetchByPrimaryKey(newSocialEquityUser.getPrimaryKey());

		assertEquals(existingSocialEquityUser, newSocialEquityUser);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		SocialEquityUser missingSocialEquityUser = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingSocialEquityUser);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SocialEquityUser newSocialEquityUser = addSocialEquityUser();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialEquityUser.class,
				SocialEquityUser.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("equityUserId",
				newSocialEquityUser.getEquityUserId()));

		List<SocialEquityUser> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		SocialEquityUser existingSocialEquityUser = result.get(0);

		assertEquals(existingSocialEquityUser, newSocialEquityUser);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialEquityUser.class,
				SocialEquityUser.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("equityUserId", nextLong()));

		List<SocialEquityUser> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		SocialEquityUser newSocialEquityUser = addSocialEquityUser();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialEquityUser.class,
				SocialEquityUser.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"equityUserId"));

		Object newEquityUserId = newSocialEquityUser.getEquityUserId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("equityUserId",
				new Object[] { newEquityUserId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingEquityUserId = result.get(0);

		assertEquals(existingEquityUserId, newEquityUserId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialEquityUser.class,
				SocialEquityUser.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"equityUserId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("equityUserId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		SocialEquityUser newSocialEquityUser = addSocialEquityUser();

		_persistence.clearCache();

		SocialEquityUserModelImpl existingSocialEquityUserModelImpl = (SocialEquityUserModelImpl)_persistence.findByPrimaryKey(newSocialEquityUser.getPrimaryKey());

		assertEquals(existingSocialEquityUserModelImpl.getGroupId(),
			existingSocialEquityUserModelImpl.getOriginalGroupId());
		assertEquals(existingSocialEquityUserModelImpl.getUserId(),
			existingSocialEquityUserModelImpl.getOriginalUserId());
	}

	protected SocialEquityUser addSocialEquityUser() throws Exception {
		long pk = nextLong();

		SocialEquityUser socialEquityUser = _persistence.create(pk);

		socialEquityUser.setGroupId(nextLong());
		socialEquityUser.setCompanyId(nextLong());
		socialEquityUser.setUserId(nextLong());
		socialEquityUser.setContributionK(nextDouble());
		socialEquityUser.setContributionB(nextDouble());
		socialEquityUser.setParticipationK(nextDouble());
		socialEquityUser.setParticipationB(nextDouble());
		socialEquityUser.setRank(nextInt());

		_persistence.update(socialEquityUser, false);

		return socialEquityUser;
	}

	private SocialEquityUserPersistence _persistence;
}