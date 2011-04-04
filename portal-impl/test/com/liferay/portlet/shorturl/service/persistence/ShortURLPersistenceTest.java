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

package com.liferay.portlet.shorturl.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.shorturl.NoSuchShortURLException;
import com.liferay.portlet.shorturl.model.ShortURL;
import com.liferay.portlet.shorturl.model.impl.ShortURLModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class ShortURLPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ShortURLPersistence)PortalBeanLocatorUtil.locate(ShortURLPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		ShortURL shortURL = _persistence.create(pk);

		assertNotNull(shortURL);

		assertEquals(shortURL.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		ShortURL newShortURL = addShortURL();

		_persistence.remove(newShortURL);

		ShortURL existingShortURL = _persistence.fetchByPrimaryKey(newShortURL.getPrimaryKey());

		assertNull(existingShortURL);
	}

	public void testUpdateNew() throws Exception {
		addShortURL();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		ShortURL newShortURL = _persistence.create(pk);

		newShortURL.setCreateDate(nextDate());
		newShortURL.setModifiedDate(nextDate());
		newShortURL.setOriginalURL(randomString());
		newShortURL.setHash(randomString());
		newShortURL.setDescriptor(randomString());

		_persistence.update(newShortURL, false);

		ShortURL existingShortURL = _persistence.findByPrimaryKey(newShortURL.getPrimaryKey());

		assertEquals(existingShortURL.getShortURLId(),
			newShortURL.getShortURLId());
		assertEquals(Time.getShortTimestamp(existingShortURL.getCreateDate()),
			Time.getShortTimestamp(newShortURL.getCreateDate()));
		assertEquals(Time.getShortTimestamp(existingShortURL.getModifiedDate()),
			Time.getShortTimestamp(newShortURL.getModifiedDate()));
		assertEquals(existingShortURL.getOriginalURL(),
			newShortURL.getOriginalURL());
		assertEquals(existingShortURL.getHash(), newShortURL.getHash());
		assertEquals(existingShortURL.getDescriptor(),
			newShortURL.getDescriptor());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		ShortURL newShortURL = addShortURL();

		ShortURL existingShortURL = _persistence.findByPrimaryKey(newShortURL.getPrimaryKey());

		assertEquals(existingShortURL, newShortURL);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchShortURLException");
		}
		catch (NoSuchShortURLException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		ShortURL newShortURL = addShortURL();

		ShortURL existingShortURL = _persistence.fetchByPrimaryKey(newShortURL.getPrimaryKey());

		assertEquals(existingShortURL, newShortURL);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		ShortURL missingShortURL = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingShortURL);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		ShortURL newShortURL = addShortURL();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShortURL.class,
				ShortURL.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("shortURLId",
				newShortURL.getShortURLId()));

		List<ShortURL> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		ShortURL existingShortURL = result.get(0);

		assertEquals(existingShortURL, newShortURL);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShortURL.class,
				ShortURL.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("shortURLId", nextLong()));

		List<ShortURL> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		ShortURL newShortURL = addShortURL();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShortURL.class,
				ShortURL.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("shortURLId"));

		Object newShortURLId = newShortURL.getShortURLId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("shortURLId",
				new Object[] { newShortURLId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingShortURLId = result.get(0);

		assertEquals(existingShortURLId, newShortURLId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(ShortURL.class,
				ShortURL.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("shortURLId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("shortURLId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		ShortURL newShortURL = addShortURL();

		_persistence.clearCache();

		ShortURLModelImpl existingShortURLModelImpl = (ShortURLModelImpl)_persistence.findByPrimaryKey(newShortURL.getPrimaryKey());

		assertTrue(Validator.equals(
				existingShortURLModelImpl.getOriginalURL(),
				existingShortURLModelImpl.getOriginalOriginalURL()));

		assertTrue(Validator.equals(existingShortURLModelImpl.getHash(),
				existingShortURLModelImpl.getOriginalHash()));
	}

	protected ShortURL addShortURL() throws Exception {
		long pk = nextLong();

		ShortURL shortURL = _persistence.create(pk);

		shortURL.setCreateDate(nextDate());
		shortURL.setModifiedDate(nextDate());
		shortURL.setOriginalURL(randomString());
		shortURL.setHash(randomString());
		shortURL.setDescriptor(randomString());

		_persistence.update(shortURL, false);

		return shortURL;
	}

	private ShortURLPersistence _persistence;
}