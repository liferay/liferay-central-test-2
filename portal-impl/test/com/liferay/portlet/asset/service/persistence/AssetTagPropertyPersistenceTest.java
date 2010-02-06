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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.asset.NoSuchTagPropertyException;
import com.liferay.portlet.asset.model.AssetTagProperty;

import java.util.List;

/**
 * <a href="AssetTagPropertyPersistenceTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class AssetTagPropertyPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (AssetTagPropertyPersistence)PortalBeanLocatorUtil.locate(AssetTagPropertyPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		AssetTagProperty assetTagProperty = _persistence.create(pk);

		assertNotNull(assetTagProperty);

		assertEquals(assetTagProperty.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		AssetTagProperty newAssetTagProperty = addAssetTagProperty();

		_persistence.remove(newAssetTagProperty);

		AssetTagProperty existingAssetTagProperty = _persistence.fetchByPrimaryKey(newAssetTagProperty.getPrimaryKey());

		assertNull(existingAssetTagProperty);
	}

	public void testUpdateNew() throws Exception {
		addAssetTagProperty();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		AssetTagProperty newAssetTagProperty = _persistence.create(pk);

		newAssetTagProperty.setCompanyId(nextLong());
		newAssetTagProperty.setUserId(nextLong());
		newAssetTagProperty.setUserName(randomString());
		newAssetTagProperty.setCreateDate(nextDate());
		newAssetTagProperty.setModifiedDate(nextDate());
		newAssetTagProperty.setTagId(nextLong());
		newAssetTagProperty.setKey(randomString());
		newAssetTagProperty.setValue(randomString());

		_persistence.update(newAssetTagProperty, false);

		AssetTagProperty existingAssetTagProperty = _persistence.findByPrimaryKey(newAssetTagProperty.getPrimaryKey());

		assertEquals(existingAssetTagProperty.getTagPropertyId(),
			newAssetTagProperty.getTagPropertyId());
		assertEquals(existingAssetTagProperty.getCompanyId(),
			newAssetTagProperty.getCompanyId());
		assertEquals(existingAssetTagProperty.getUserId(),
			newAssetTagProperty.getUserId());
		assertEquals(existingAssetTagProperty.getUserName(),
			newAssetTagProperty.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingAssetTagProperty.getCreateDate()),
			Time.getShortTimestamp(newAssetTagProperty.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingAssetTagProperty.getModifiedDate()),
			Time.getShortTimestamp(newAssetTagProperty.getModifiedDate()));
		assertEquals(existingAssetTagProperty.getTagId(),
			newAssetTagProperty.getTagId());
		assertEquals(existingAssetTagProperty.getKey(),
			newAssetTagProperty.getKey());
		assertEquals(existingAssetTagProperty.getValue(),
			newAssetTagProperty.getValue());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetTagProperty newAssetTagProperty = addAssetTagProperty();

		AssetTagProperty existingAssetTagProperty = _persistence.findByPrimaryKey(newAssetTagProperty.getPrimaryKey());

		assertEquals(existingAssetTagProperty, newAssetTagProperty);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchTagPropertyException");
		}
		catch (NoSuchTagPropertyException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetTagProperty newAssetTagProperty = addAssetTagProperty();

		AssetTagProperty existingAssetTagProperty = _persistence.fetchByPrimaryKey(newAssetTagProperty.getPrimaryKey());

		assertEquals(existingAssetTagProperty, newAssetTagProperty);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		AssetTagProperty missingAssetTagProperty = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingAssetTagProperty);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		AssetTagProperty newAssetTagProperty = addAssetTagProperty();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetTagProperty.class,
				AssetTagProperty.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("tagPropertyId",
				newAssetTagProperty.getTagPropertyId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		AssetTagProperty existingAssetTagProperty = (AssetTagProperty)result.get(0);

		assertEquals(existingAssetTagProperty, newAssetTagProperty);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(AssetTagProperty.class,
				AssetTagProperty.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("tagPropertyId", nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected AssetTagProperty addAssetTagProperty() throws Exception {
		long pk = nextLong();

		AssetTagProperty assetTagProperty = _persistence.create(pk);

		assetTagProperty.setCompanyId(nextLong());
		assetTagProperty.setUserId(nextLong());
		assetTagProperty.setUserName(randomString());
		assetTagProperty.setCreateDate(nextDate());
		assetTagProperty.setModifiedDate(nextDate());
		assetTagProperty.setTagId(nextLong());
		assetTagProperty.setKey(randomString());
		assetTagProperty.setValue(randomString());

		_persistence.update(assetTagProperty, false);

		return assetTagProperty;
	}

	private AssetTagPropertyPersistence _persistence;
}