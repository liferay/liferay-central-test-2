/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

import com.liferay.portal.NoSuchImageException;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ProjectionFactoryUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateManagerUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.OrderByComparatorFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.test.TransactionalTestRule;
import com.liferay.portal.test.runners.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.util.test.RandomTestUtil;

import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import org.junit.runner.RunWith;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @generated
 */
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class ImagePersistenceTest {
	@ClassRule
	public static TransactionalTestRule transactionalTestRule = new TransactionalTestRule(Propagation.REQUIRED);

	@BeforeClass
	public static void setupClass() throws TemplateException {
		try {
			DBUpgrader.upgrade();
		}
		catch (Exception e) {
			_log.error(e, e);
		}

		TemplateManagerUtil.init();
	}

	@After
	public void tearDown() throws Exception {
		Iterator<Image> iterator = _images.iterator();

		while (iterator.hasNext()) {
			_persistence.remove(iterator.next());

			iterator.remove();
		}
	}

	@Test
	public void testCreate() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Image image = _persistence.create(pk);

		Assert.assertNotNull(image);

		Assert.assertEquals(image.getPrimaryKey(), pk);
	}

	@Test
	public void testRemove() throws Exception {
		Image newImage = addImage();

		_persistence.remove(newImage);

		Image existingImage = _persistence.fetchByPrimaryKey(newImage.getPrimaryKey());

		Assert.assertNull(existingImage);
	}

	@Test
	public void testUpdateNew() throws Exception {
		addImage();
	}

	@Test
	public void testUpdateExisting() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Image newImage = _persistence.create(pk);

		newImage.setMvccVersion(RandomTestUtil.nextLong());

		newImage.setModifiedDate(RandomTestUtil.nextDate());

		newImage.setType(RandomTestUtil.randomString());

		newImage.setHeight(RandomTestUtil.nextInt());

		newImage.setWidth(RandomTestUtil.nextInt());

		newImage.setSize(RandomTestUtil.nextInt());

		_images.add(_persistence.update(newImage));

		Image existingImage = _persistence.findByPrimaryKey(newImage.getPrimaryKey());

		Assert.assertEquals(existingImage.getMvccVersion(),
			newImage.getMvccVersion());
		Assert.assertEquals(existingImage.getImageId(), newImage.getImageId());
		Assert.assertEquals(Time.getShortTimestamp(
				existingImage.getModifiedDate()),
			Time.getShortTimestamp(newImage.getModifiedDate()));
		Assert.assertEquals(existingImage.getType(), newImage.getType());
		Assert.assertEquals(existingImage.getHeight(), newImage.getHeight());
		Assert.assertEquals(existingImage.getWidth(), newImage.getWidth());
		Assert.assertEquals(existingImage.getSize(), newImage.getSize());
	}

	@Test
	public void testCountByLtSize() {
		try {
			_persistence.countByLtSize(RandomTestUtil.nextInt());

			_persistence.countByLtSize(0);
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	@Test
	public void testFindByPrimaryKeyExisting() throws Exception {
		Image newImage = addImage();

		Image existingImage = _persistence.findByPrimaryKey(newImage.getPrimaryKey());

		Assert.assertEquals(existingImage, newImage);
	}

	@Test
	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			Assert.fail("Missing entity did not throw NoSuchImageException");
		}
		catch (NoSuchImageException nsee) {
		}
	}

	@Test
	public void testFindAll() throws Exception {
		try {
			_persistence.findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				getOrderByComparator());
		}
		catch (Exception e) {
			Assert.fail(e.getMessage());
		}
	}

	protected OrderByComparator<Image> getOrderByComparator() {
		return OrderByComparatorFactoryUtil.create("Image", "mvccVersion",
			true, "imageId", true, "modifiedDate", true, "type", true,
			"height", true, "width", true, "size", true);
	}

	@Test
	public void testFetchByPrimaryKeyExisting() throws Exception {
		Image newImage = addImage();

		Image existingImage = _persistence.fetchByPrimaryKey(newImage.getPrimaryKey());

		Assert.assertEquals(existingImage, newImage);
	}

	@Test
	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Image missingImage = _persistence.fetchByPrimaryKey(pk);

		Assert.assertNull(missingImage);
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereAllPrimaryKeysExist()
		throws Exception {
		Image newImage1 = addImage();
		Image newImage2 = addImage();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newImage1.getPrimaryKey());
		primaryKeys.add(newImage2.getPrimaryKey());

		Map<Serializable, Image> images = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(2, images.size());
		Assert.assertEquals(newImage1, images.get(newImage1.getPrimaryKey()));
		Assert.assertEquals(newImage2, images.get(newImage2.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereNoPrimaryKeysExist()
		throws Exception {
		long pk1 = RandomTestUtil.nextLong();

		long pk2 = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(pk1);
		primaryKeys.add(pk2);

		Map<Serializable, Image> images = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(images.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithMultiplePrimaryKeysWhereSomePrimaryKeysExist()
		throws Exception {
		Image newImage = addImage();

		long pk = RandomTestUtil.nextLong();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newImage.getPrimaryKey());
		primaryKeys.add(pk);

		Map<Serializable, Image> images = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, images.size());
		Assert.assertEquals(newImage, images.get(newImage.getPrimaryKey()));
	}

	@Test
	public void testFetchByPrimaryKeysWithNoPrimaryKeys()
		throws Exception {
		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		Map<Serializable, Image> images = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertTrue(images.isEmpty());
	}

	@Test
	public void testFetchByPrimaryKeysWithOnePrimaryKey()
		throws Exception {
		Image newImage = addImage();

		Set<Serializable> primaryKeys = new HashSet<Serializable>();

		primaryKeys.add(newImage.getPrimaryKey());

		Map<Serializable, Image> images = _persistence.fetchByPrimaryKeys(primaryKeys);

		Assert.assertEquals(1, images.size());
		Assert.assertEquals(newImage, images.get(newImage.getPrimaryKey()));
	}

	@Test
	public void testActionableDynamicQuery() throws Exception {
		final IntegerWrapper count = new IntegerWrapper();

		ActionableDynamicQuery actionableDynamicQuery = ImageLocalServiceUtil.getActionableDynamicQuery();

		actionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod() {
				@Override
				public void performAction(Object object) {
					Image image = (Image)object;

					Assert.assertNotNull(image);

					count.increment();
				}
			});

		actionableDynamicQuery.performActions();

		Assert.assertEquals(count.getValue(), _persistence.countAll());
	}

	@Test
	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Image newImage = addImage();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Image.class,
				Image.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("imageId",
				newImage.getImageId()));

		List<Image> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Image existingImage = result.get(0);

		Assert.assertEquals(existingImage, newImage);
	}

	@Test
	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Image.class,
				Image.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("imageId",
				RandomTestUtil.nextLong()));

		List<Image> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	@Test
	public void testDynamicQueryByProjectionExisting()
		throws Exception {
		Image newImage = addImage();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Image.class,
				Image.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("imageId"));

		Object newImageId = newImage.getImageId();

		dynamicQuery.add(RestrictionsFactoryUtil.in("imageId",
				new Object[] { newImageId }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(1, result.size());

		Object existingImageId = result.get(0);

		Assert.assertEquals(existingImageId, newImageId);
	}

	@Test
	public void testDynamicQueryByProjectionMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Image.class,
				Image.class.getClassLoader());

		dynamicQuery.setProjection(ProjectionFactoryUtil.property("imageId"));

		dynamicQuery.add(RestrictionsFactoryUtil.in("imageId",
				new Object[] { RandomTestUtil.nextLong() }));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		Assert.assertEquals(0, result.size());
	}

	protected Image addImage() throws Exception {
		long pk = RandomTestUtil.nextLong();

		Image image = _persistence.create(pk);

		image.setMvccVersion(RandomTestUtil.nextLong());

		image.setModifiedDate(RandomTestUtil.nextDate());

		image.setType(RandomTestUtil.randomString());

		image.setHeight(RandomTestUtil.nextInt());

		image.setWidth(RandomTestUtil.nextInt());

		image.setSize(RandomTestUtil.nextInt());

		_images.add(_persistence.update(image));

		return image;
	}

	private static Log _log = LogFactoryUtil.getLog(ImagePersistenceTest.class);
	private List<Image> _images = new ArrayList<Image>();
	private ModelListener<Image>[] _modelListeners;
	private ImagePersistence _persistence = ImageUtil.getPersistence();
}