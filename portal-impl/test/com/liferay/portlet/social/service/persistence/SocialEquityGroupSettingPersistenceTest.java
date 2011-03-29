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

import com.liferay.portlet.social.NoSuchEquityGroupSettingException;
import com.liferay.portlet.social.model.SocialEquityGroupSetting;
import com.liferay.portlet.social.model.impl.SocialEquityGroupSettingModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class SocialEquityGroupSettingPersistenceTest
	extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (SocialEquityGroupSettingPersistence)PortalBeanLocatorUtil.locate(SocialEquityGroupSettingPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		SocialEquityGroupSetting socialEquityGroupSetting = _persistence.create(pk);

		assertNotNull(socialEquityGroupSetting);

		assertEquals(socialEquityGroupSetting.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		SocialEquityGroupSetting newSocialEquityGroupSetting = addSocialEquityGroupSetting();

		_persistence.remove(newSocialEquityGroupSetting);

		SocialEquityGroupSetting existingSocialEquityGroupSetting = _persistence.fetchByPrimaryKey(newSocialEquityGroupSetting.getPrimaryKey());

		assertNull(existingSocialEquityGroupSetting);
	}

	public void testUpdateNew() throws Exception {
		addSocialEquityGroupSetting();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		SocialEquityGroupSetting newSocialEquityGroupSetting = _persistence.create(pk);

		newSocialEquityGroupSetting.setGroupId(nextLong());
		newSocialEquityGroupSetting.setCompanyId(nextLong());
		newSocialEquityGroupSetting.setClassNameId(nextLong());
		newSocialEquityGroupSetting.setType(nextInt());
		newSocialEquityGroupSetting.setEnabled(randomBoolean());

		_persistence.update(newSocialEquityGroupSetting, false);

		SocialEquityGroupSetting existingSocialEquityGroupSetting = _persistence.findByPrimaryKey(newSocialEquityGroupSetting.getPrimaryKey());

		assertEquals(existingSocialEquityGroupSetting.getEquityGroupSettingId(),
			newSocialEquityGroupSetting.getEquityGroupSettingId());
		assertEquals(existingSocialEquityGroupSetting.getGroupId(),
			newSocialEquityGroupSetting.getGroupId());
		assertEquals(existingSocialEquityGroupSetting.getCompanyId(),
			newSocialEquityGroupSetting.getCompanyId());
		assertEquals(existingSocialEquityGroupSetting.getClassNameId(),
			newSocialEquityGroupSetting.getClassNameId());
		assertEquals(existingSocialEquityGroupSetting.getType(),
			newSocialEquityGroupSetting.getType());
		assertEquals(existingSocialEquityGroupSetting.getEnabled(),
			newSocialEquityGroupSetting.getEnabled());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		SocialEquityGroupSetting newSocialEquityGroupSetting = addSocialEquityGroupSetting();

		SocialEquityGroupSetting existingSocialEquityGroupSetting = _persistence.findByPrimaryKey(newSocialEquityGroupSetting.getPrimaryKey());

		assertEquals(existingSocialEquityGroupSetting,
			newSocialEquityGroupSetting);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail(
				"Missing entity did not throw NoSuchEquityGroupSettingException");
		}
		catch (NoSuchEquityGroupSettingException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		SocialEquityGroupSetting newSocialEquityGroupSetting = addSocialEquityGroupSetting();

		SocialEquityGroupSetting existingSocialEquityGroupSetting = _persistence.fetchByPrimaryKey(newSocialEquityGroupSetting.getPrimaryKey());

		assertEquals(existingSocialEquityGroupSetting,
			newSocialEquityGroupSetting);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		SocialEquityGroupSetting missingSocialEquityGroupSetting = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingSocialEquityGroupSetting);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SocialEquityGroupSetting newSocialEquityGroupSetting = addSocialEquityGroupSetting();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialEquityGroupSetting.class,
				SocialEquityGroupSetting.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("equityGroupSettingId",
				newSocialEquityGroupSetting.getEquityGroupSettingId()));

		List<SocialEquityGroupSetting> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		SocialEquityGroupSetting existingSocialEquityGroupSetting = result.get(0);

		assertEquals(existingSocialEquityGroupSetting,
			newSocialEquityGroupSetting);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialEquityGroupSetting.class,
				SocialEquityGroupSetting.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("equityGroupSettingId",
				nextLong()));

		List<SocialEquityGroupSetting> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		SocialEquityGroupSetting newSocialEquityGroupSetting = addSocialEquityGroupSetting();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialEquityGroupSetting.class,
				SocialEquityGroupSetting.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"equityGroupSettingId"));

		Object newEquityGroupSettingId = newSocialEquityGroupSetting.getEquityGroupSettingId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("equityGroupSettingId",
				new Object[] { newEquityGroupSettingId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingEquityGroupSettingId = result.get(0);

		assertEquals(existingEquityGroupSettingId, newEquityGroupSettingId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialEquityGroupSetting.class,
				SocialEquityGroupSetting.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property(
				"equityGroupSettingId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("equityGroupSettingId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		SocialEquityGroupSetting newSocialEquityGroupSetting = addSocialEquityGroupSetting();

		_persistence.clearCache();

		SocialEquityGroupSettingModelImpl existingSocialEquityGroupSettingModelImpl =
			(SocialEquityGroupSettingModelImpl)_persistence.findByPrimaryKey(newSocialEquityGroupSetting.getPrimaryKey());

		assertEquals(existingSocialEquityGroupSettingModelImpl.getGroupId(),
			existingSocialEquityGroupSettingModelImpl.getOriginalGroupId());
		assertEquals(existingSocialEquityGroupSettingModelImpl.getClassNameId(),
			existingSocialEquityGroupSettingModelImpl.getOriginalClassNameId());
		assertEquals(existingSocialEquityGroupSettingModelImpl.getType(),
			existingSocialEquityGroupSettingModelImpl.getOriginalType());
	}

	protected SocialEquityGroupSetting addSocialEquityGroupSetting()
		throws Exception {
		long pk = nextLong();

		SocialEquityGroupSetting socialEquityGroupSetting = _persistence.create(pk);

		socialEquityGroupSetting.setGroupId(nextLong());
		socialEquityGroupSetting.setCompanyId(nextLong());
		socialEquityGroupSetting.setClassNameId(nextLong());
		socialEquityGroupSetting.setType(nextInt());
		socialEquityGroupSetting.setEnabled(randomBoolean());

		_persistence.update(socialEquityGroupSetting, false);

		return socialEquityGroupSetting;
	}

	private SocialEquityGroupSettingPersistence _persistence;
}