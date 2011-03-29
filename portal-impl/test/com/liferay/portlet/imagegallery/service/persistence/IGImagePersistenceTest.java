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

package com.liferay.portlet.imagegallery.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;
import com.liferay.portal.util.PropsValues;

import com.liferay.portlet.imagegallery.NoSuchImageException;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.model.impl.IGImageModelImpl;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class IGImagePersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (IGImagePersistence)PortalBeanLocatorUtil.locate(IGImagePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		IGImage igImage = _persistence.create(pk);

		assertNotNull(igImage);

		assertEquals(igImage.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		IGImage newIGImage = addIGImage();

		_persistence.remove(newIGImage);

		IGImage existingIGImage = _persistence.fetchByPrimaryKey(newIGImage.getPrimaryKey());

		assertNull(existingIGImage);
	}

	public void testUpdateNew() throws Exception {
		addIGImage();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		IGImage newIGImage = _persistence.create(pk);

		newIGImage.setUuid(randomString());
		newIGImage.setGroupId(nextLong());
		newIGImage.setCompanyId(nextLong());
		newIGImage.setUserId(nextLong());
		newIGImage.setCreateDate(nextDate());
		newIGImage.setModifiedDate(nextDate());
		newIGImage.setFolderId(nextLong());
		newIGImage.setName(randomString());
		newIGImage.setDescription(randomString());
		newIGImage.setSmallImageId(nextLong());
		newIGImage.setLargeImageId(nextLong());
		newIGImage.setCustom1ImageId(nextLong());
		newIGImage.setCustom2ImageId(nextLong());

		_persistence.update(newIGImage, false);

		IGImage existingIGImage = _persistence.findByPrimaryKey(newIGImage.getPrimaryKey());

		assertEquals(existingIGImage.getUuid(), newIGImage.getUuid());
		assertEquals(existingIGImage.getImageId(), newIGImage.getImageId());
		assertEquals(existingIGImage.getGroupId(), newIGImage.getGroupId());
		assertEquals(existingIGImage.getCompanyId(), newIGImage.getCompanyId());
		assertEquals(existingIGImage.getUserId(), newIGImage.getUserId());
		assertEquals(Time.getShortTimestamp(existingIGImage.getCreateDate()),
			Time.getShortTimestamp(newIGImage.getCreateDate()));
		assertEquals(Time.getShortTimestamp(existingIGImage.getModifiedDate()),
			Time.getShortTimestamp(newIGImage.getModifiedDate()));
		assertEquals(existingIGImage.getFolderId(), newIGImage.getFolderId());
		assertEquals(existingIGImage.getName(), newIGImage.getName());
		assertEquals(existingIGImage.getDescription(),
			newIGImage.getDescription());
		assertEquals(existingIGImage.getSmallImageId(),
			newIGImage.getSmallImageId());
		assertEquals(existingIGImage.getLargeImageId(),
			newIGImage.getLargeImageId());
		assertEquals(existingIGImage.getCustom1ImageId(),
			newIGImage.getCustom1ImageId());
		assertEquals(existingIGImage.getCustom2ImageId(),
			newIGImage.getCustom2ImageId());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		IGImage newIGImage = addIGImage();

		IGImage existingIGImage = _persistence.findByPrimaryKey(newIGImage.getPrimaryKey());

		assertEquals(existingIGImage, newIGImage);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchImageException");
		}
		catch (NoSuchImageException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		IGImage newIGImage = addIGImage();

		IGImage existingIGImage = _persistence.fetchByPrimaryKey(newIGImage.getPrimaryKey());

		assertEquals(existingIGImage, newIGImage);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		IGImage missingIGImage = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingIGImage);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		IGImage newIGImage = addIGImage();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(IGImage.class,
				IGImage.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("imageId",
				newIGImage.getImageId()));

		List<IGImage> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		IGImage existingIGImage = result.get(0);

		assertEquals(existingIGImage, newIGImage);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(IGImage.class,
				IGImage.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("imageId", nextLong()));

		List<IGImage> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		IGImage newIGImage = addIGImage();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(IGImage.class,
				IGImage.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("imageId"));

		Object newImageId = newIGImage.getImageId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("imageId",
				new Object[] { newImageId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Object existingImageId = result.get(0);

		assertEquals(existingImageId, newImageId);
	}

	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(IGImage.class,
				IGImage.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("imageId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("imageId",
				new Object[] { nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	public void testResetOriginalValues() throws Exception {
		if (!PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			return;
		}

		IGImage newIGImage = addIGImage();

		_persistence.clearCache();

		IGImageModelImpl existingIGImageModelImpl = (IGImageModelImpl)_persistence.findByPrimaryKey(newIGImage.getPrimaryKey());

		assertTrue(Validator.equals(existingIGImageModelImpl.getUuid(),
				existingIGImageModelImpl.getOriginalUuid()));
		assertEquals(existingIGImageModelImpl.getGroupId(),
			existingIGImageModelImpl.getOriginalGroupId());

		assertEquals(existingIGImageModelImpl.getSmallImageId(),
			existingIGImageModelImpl.getOriginalSmallImageId());

		assertEquals(existingIGImageModelImpl.getLargeImageId(),
			existingIGImageModelImpl.getOriginalLargeImageId());

		assertEquals(existingIGImageModelImpl.getCustom1ImageId(),
			existingIGImageModelImpl.getOriginalCustom1ImageId());

		assertEquals(existingIGImageModelImpl.getCustom2ImageId(),
			existingIGImageModelImpl.getOriginalCustom2ImageId());
	}

	protected IGImage addIGImage() throws Exception {
		long pk = nextLong();

		IGImage igImage = _persistence.create(pk);

		igImage.setUuid(randomString());
		igImage.setGroupId(nextLong());
		igImage.setCompanyId(nextLong());
		igImage.setUserId(nextLong());
		igImage.setCreateDate(nextDate());
		igImage.setModifiedDate(nextDate());
		igImage.setFolderId(nextLong());
		igImage.setName(randomString());
		igImage.setDescription(randomString());
		igImage.setSmallImageId(nextLong());
		igImage.setLargeImageId(nextLong());
		igImage.setCustom1ImageId(nextLong());
		igImage.setCustom2ImageId(nextLong());

		_persistence.update(igImage, false);

		return igImage;
	}

	private IGImagePersistence _persistence;
}