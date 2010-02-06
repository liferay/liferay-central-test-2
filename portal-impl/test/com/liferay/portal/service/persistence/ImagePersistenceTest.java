/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.liferay.portal.service.persistence;

import com.liferay.portal.NoSuchImageException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * <a href="ImagePersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class ImagePersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ImagePersistence)PortalBeanLocatorUtil.locate(ImagePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		Image image = _persistence.create(pk);

		assertNotNull(image);

		assertEquals(image.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		Image newImage = addImage();

		_persistence.remove(newImage);

		Image existingImage = _persistence.fetchByPrimaryKey(newImage.getPrimaryKey());

		assertNull(existingImage);
	}

	public void testUpdateNew() throws Exception {
		addImage();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		Image newImage = _persistence.create(pk);

		newImage.setModifiedDate(nextDate());
		newImage.setText(randomString());
		newImage.setType(randomString());
		newImage.setHeight(nextInt());
		newImage.setWidth(nextInt());
		newImage.setSize(nextInt());

		_persistence.update(newImage, false);

		Image existingImage = _persistence.findByPrimaryKey(newImage.getPrimaryKey());

		assertEquals(existingImage.getImageId(), newImage.getImageId());
		assertEquals(Time.getShortTimestamp(existingImage.getModifiedDate()),
			Time.getShortTimestamp(newImage.getModifiedDate()));
		assertEquals(existingImage.getText(), newImage.getText());
		assertEquals(existingImage.getType(), newImage.getType());
		assertEquals(existingImage.getHeight(), newImage.getHeight());
		assertEquals(existingImage.getWidth(), newImage.getWidth());
		assertEquals(existingImage.getSize(), newImage.getSize());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		Image newImage = addImage();

		Image existingImage = _persistence.findByPrimaryKey(newImage.getPrimaryKey());

		assertEquals(existingImage, newImage);
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
		Image newImage = addImage();

		Image existingImage = _persistence.fetchByPrimaryKey(newImage.getPrimaryKey());

		assertEquals(existingImage, newImage);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		Image missingImage = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingImage);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Image newImage = addImage();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Image.class,
				Image.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("imageId",
				newImage.getImageId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Image existingImage = (Image)result.get(0);

		assertEquals(existingImage, newImage);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Image.class,
				Image.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("imageId", nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected Image addImage() throws Exception {
		long pk = nextLong();

		Image image = _persistence.create(pk);

		image.setModifiedDate(nextDate());
		image.setText(randomString());
		image.setType(randomString());
		image.setHeight(nextInt());
		image.setWidth(nextInt());
		image.setSize(nextInt());

		_persistence.update(image, false);

		return image;
	}

	private ImagePersistence _persistence;
}