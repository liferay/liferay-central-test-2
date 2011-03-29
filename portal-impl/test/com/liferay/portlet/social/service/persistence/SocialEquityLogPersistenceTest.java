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
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.social.NoSuchEquityLogException;
import com.liferay.portlet.social.model.SocialEquityLog;
import com.liferay.portlet.social.model.impl.SocialEquityLogModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class SocialEquityLogPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (SocialEquityLogPersistence)PortalBeanLocatorUtil.locate(SocialEquityLogPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		SocialEquityLog socialEquityLog = _persistence.create(pk);

		assertNotNull(socialEquityLog);

		assertEquals(socialEquityLog.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		SocialEquityLog newSocialEquityLog = addSocialEquityLog();

		_persistence.remove(newSocialEquityLog);

		SocialEquityLog existingSocialEquityLog = _persistence.fetchByPrimaryKey(newSocialEquityLog.getPrimaryKey());

		assertNull(existingSocialEquityLog);
	}

	public void testUpdateNew() throws Exception {
		addSocialEquityLog();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		SocialEquityLog newSocialEquityLog = _persistence.create(pk);

		newSocialEquityLog.setGroupId(nextLong());
		newSocialEquityLog.setCompanyId(nextLong());
		newSocialEquityLog.setUserId(nextLong());
		newSocialEquityLog.setAssetEntryId(nextLong());
		newSocialEquityLog.setActionId(randomString());
		newSocialEquityLog.setActionDate(nextInt());
		newSocialEquityLog.setActive(randomBoolean());
		newSocialEquityLog.setExpiration(nextInt());
		newSocialEquityLog.setType(nextInt());
		newSocialEquityLog.setValue(nextInt());
		newSocialEquityLog.setExtraData(randomString());

		_persistence.update(newSocialEquityLog, false);

		SocialEquityLog existingSocialEquityLog = _persistence.findByPrimaryKey(newSocialEquityLog.getPrimaryKey());

		assertEquals(existingSocialEquityLog.getEquityLogId(),
			newSocialEquityLog.getEquityLogId());
		assertEquals(existingSocialEquityLog.getGroupId(),
			newSocialEquityLog.getGroupId());
		assertEquals(existingSocialEquityLog.getCompanyId(),
			newSocialEquityLog.getCompanyId());
		assertEquals(existingSocialEquityLog.getUserId(),
			newSocialEquityLog.getUserId());
		assertEquals(existingSocialEquityLog.getAssetEntryId(),
			newSocialEquityLog.getAssetEntryId());
		assertEquals(existingSocialEquityLog.getActionId(),
			newSocialEquityLog.getActionId());
		assertEquals(existingSocialEquityLog.getActionDate(),
			newSocialEquityLog.getActionDate());
		assertEquals(existingSocialEquityLog.getActive(),
			newSocialEquityLog.getActive());
		assertEquals(existingSocialEquityLog.getExpiration(),
			newSocialEquityLog.getExpiration());
		assertEquals(existingSocialEquityLog.getType(),
			newSocialEquityLog.getType());
		assertEquals(existingSocialEquityLog.getValue(),
			newSocialEquityLog.getValue());
		assertEquals(existingSocialEquityLog.getExtraData(),
			newSocialEquityLog.getExtraData());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		SocialEquityLog newSocialEquityLog = addSocialEquityLog();

		SocialEquityLog existingSocialEquityLog = _persistence.findByPrimaryKey(newSocialEquityLog.getPrimaryKey());

		assertEquals(existingSocialEquityLog, newSocialEquityLog);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchEquityLogException");
		}
		catch (NoSuchEquityLogException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		SocialEquityLog newSocialEquityLog = addSocialEquityLog();

		SocialEquityLog existingSocialEquityLog = _persistence.fetchByPrimaryKey(newSocialEquityLog.getPrimaryKey());

		assertEquals(existingSocialEquityLog, newSocialEquityLog);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		SocialEquityLog missingSocialEquityLog = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingSocialEquityLog);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SocialEquityLog newSocialEquityLog = addSocialEquityLog();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialEquityLog.class,
				SocialEquityLog.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("equityLogId",
				newSocialEquityLog.getEquityLogId()));

		List<SocialEquityLog> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		SocialEquityLog existingSocialEquityLog = result.get(0);

		assertEquals(existingSocialEquityLog, newSocialEquityLog);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialEquityLog.class,
				SocialEquityLog.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("equityLogId", nextLong()));

		List<SocialEquityLog> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		SocialEquityLog newSocialEquityLog = addSocialEquityLog();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialEquityLog.class,
				SocialEquityLog.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("equityLogId"));

		Object newEquityLogId = newSocialEquityLog.getEquityLogId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("equityLogId",
				new Object[] { newEquityLogId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingEquityLogId = result.get(0);

		assertEquals(existingEquityLogId, newEquityLogId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialEquityLog.class,
				SocialEquityLog.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("equityLogId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("equityLogId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		SocialEquityLog newSocialEquityLog = addSocialEquityLog();

		_persistence.clearCache();

		SocialEquityLogModelImpl existingSocialEquityLogModelImpl = (SocialEquityLogModelImpl)_persistence.findByPrimaryKey(newSocialEquityLog.getPrimaryKey());

		assertEquals(existingSocialEquityLogModelImpl.getUserId(),
			existingSocialEquityLogModelImpl.getOriginalUserId());
		assertEquals(existingSocialEquityLogModelImpl.getAssetEntryId(),
			existingSocialEquityLogModelImpl.getOriginalAssetEntryId());
		assertTrue(Validator.equals(
				existingSocialEquityLogModelImpl.getActionId(),
				existingSocialEquityLogModelImpl.getOriginalActionId()));
		assertEquals(existingSocialEquityLogModelImpl.getActionDate(),
			existingSocialEquityLogModelImpl.getOriginalActionDate());
		assertEquals(existingSocialEquityLogModelImpl.getActive(),
			existingSocialEquityLogModelImpl.getOriginalActive());
		assertEquals(existingSocialEquityLogModelImpl.getType(),
			existingSocialEquityLogModelImpl.getOriginalType());
		assertTrue(Validator.equals(
				existingSocialEquityLogModelImpl.getExtraData(),
				existingSocialEquityLogModelImpl.getOriginalExtraData()));
	}

	protected SocialEquityLog addSocialEquityLog() throws Exception {
		long pk = nextLong();

		SocialEquityLog socialEquityLog = _persistence.create(pk);

		socialEquityLog.setGroupId(nextLong());
		socialEquityLog.setCompanyId(nextLong());
		socialEquityLog.setUserId(nextLong());
		socialEquityLog.setAssetEntryId(nextLong());
		socialEquityLog.setActionId(randomString());
		socialEquityLog.setActionDate(nextInt());
		socialEquityLog.setActive(randomBoolean());
		socialEquityLog.setExpiration(nextInt());
		socialEquityLog.setType(nextInt());
		socialEquityLog.setValue(nextInt());
		socialEquityLog.setExtraData(randomString());

		_persistence.update(socialEquityLog, false);

		return socialEquityLog;
	}

	private SocialEquityLogPersistence _persistence;
}