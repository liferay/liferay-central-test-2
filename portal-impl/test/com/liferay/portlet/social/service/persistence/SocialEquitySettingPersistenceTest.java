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

import com.liferay.portlet.social.NoSuchEquitySettingException;
import com.liferay.portlet.social.model.SocialEquitySetting;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class SocialEquitySettingPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (SocialEquitySettingPersistence)PortalBeanLocatorUtil.locate(SocialEquitySettingPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		SocialEquitySetting socialEquitySetting = _persistence.create(pk);

		assertNotNull(socialEquitySetting);

		assertEquals(socialEquitySetting.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		SocialEquitySetting newSocialEquitySetting = addSocialEquitySetting();

		_persistence.remove(newSocialEquitySetting);

		SocialEquitySetting existingSocialEquitySetting = _persistence.fetchByPrimaryKey(newSocialEquitySetting.getPrimaryKey());

		assertNull(existingSocialEquitySetting);
	}

	public void testUpdateNew() throws Exception {
		addSocialEquitySetting();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		SocialEquitySetting newSocialEquitySetting = _persistence.create(pk);

		newSocialEquitySetting.setGroupId(nextLong());
		newSocialEquitySetting.setCompanyId(nextLong());
		newSocialEquitySetting.setClassNameId(nextLong());
		newSocialEquitySetting.setActionId(randomString());
		newSocialEquitySetting.setDailyLimit(nextInt());
		newSocialEquitySetting.setLifespan(nextInt());
		newSocialEquitySetting.setType(nextInt());
		newSocialEquitySetting.setUniqueEntry(randomBoolean());
		newSocialEquitySetting.setValue(nextInt());

		_persistence.update(newSocialEquitySetting, false);

		SocialEquitySetting existingSocialEquitySetting = _persistence.findByPrimaryKey(newSocialEquitySetting.getPrimaryKey());

		assertEquals(existingSocialEquitySetting.getEquitySettingId(),
			newSocialEquitySetting.getEquitySettingId());
		assertEquals(existingSocialEquitySetting.getGroupId(),
			newSocialEquitySetting.getGroupId());
		assertEquals(existingSocialEquitySetting.getCompanyId(),
			newSocialEquitySetting.getCompanyId());
		assertEquals(existingSocialEquitySetting.getClassNameId(),
			newSocialEquitySetting.getClassNameId());
		assertEquals(existingSocialEquitySetting.getActionId(),
			newSocialEquitySetting.getActionId());
		assertEquals(existingSocialEquitySetting.getDailyLimit(),
			newSocialEquitySetting.getDailyLimit());
		assertEquals(existingSocialEquitySetting.getLifespan(),
			newSocialEquitySetting.getLifespan());
		assertEquals(existingSocialEquitySetting.getType(),
			newSocialEquitySetting.getType());
		assertEquals(existingSocialEquitySetting.getUniqueEntry(),
			newSocialEquitySetting.getUniqueEntry());
		assertEquals(existingSocialEquitySetting.getValue(),
			newSocialEquitySetting.getValue());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		SocialEquitySetting newSocialEquitySetting = addSocialEquitySetting();

		SocialEquitySetting existingSocialEquitySetting = _persistence.findByPrimaryKey(newSocialEquitySetting.getPrimaryKey());

		assertEquals(existingSocialEquitySetting, newSocialEquitySetting);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchEquitySettingException");
		}
		catch (NoSuchEquitySettingException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		SocialEquitySetting newSocialEquitySetting = addSocialEquitySetting();

		SocialEquitySetting existingSocialEquitySetting = _persistence.fetchByPrimaryKey(newSocialEquitySetting.getPrimaryKey());

		assertEquals(existingSocialEquitySetting, newSocialEquitySetting);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		SocialEquitySetting missingSocialEquitySetting = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingSocialEquitySetting);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SocialEquitySetting newSocialEquitySetting = addSocialEquitySetting();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialEquitySetting.class,
				SocialEquitySetting.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("equitySettingId",
				newSocialEquitySetting.getEquitySettingId()));

		List<SocialEquitySetting> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		SocialEquitySetting existingSocialEquitySetting = result.get(0);

		assertEquals(existingSocialEquitySetting, newSocialEquitySetting);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialEquitySetting.class,
				SocialEquitySetting.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("equitySettingId",
				nextLong()));

		List<SocialEquitySetting> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		SocialEquitySetting newSocialEquitySetting = addSocialEquitySetting();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialEquitySetting.class,
				SocialEquitySetting.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"equitySettingId"));

		Object newEquitySettingId = newSocialEquitySetting.getEquitySettingId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("equitySettingId",
				new Object[] { newEquitySettingId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingEquitySettingId = result.get(0);

		assertEquals(existingEquitySettingId, newEquitySettingId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		SocialEquitySetting newSocialEquitySetting = addSocialEquitySetting();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialEquitySetting.class,
				SocialEquitySetting.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"equitySettingId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("equitySettingId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected SocialEquitySetting addSocialEquitySetting()
		throws Exception {
		long pk = nextLong();

		SocialEquitySetting socialEquitySetting = _persistence.create(pk);

		socialEquitySetting.setGroupId(nextLong());
		socialEquitySetting.setCompanyId(nextLong());
		socialEquitySetting.setClassNameId(nextLong());
		socialEquitySetting.setActionId(randomString());
		socialEquitySetting.setDailyLimit(nextInt());
		socialEquitySetting.setLifespan(nextInt());
		socialEquitySetting.setType(nextInt());
		socialEquitySetting.setUniqueEntry(randomBoolean());
		socialEquitySetting.setValue(nextInt());

		_persistence.update(socialEquitySetting, false);

		return socialEquitySetting;
	}

	private SocialEquitySettingPersistence _persistence;
}