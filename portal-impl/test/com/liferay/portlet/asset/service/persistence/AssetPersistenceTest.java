/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.asset.NoSuchAssetException;
import com.liferay.portlet.asset.model.Asset;

/**
 * <a href="AssetPersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AssetPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (AssetPersistence)PortalBeanLocatorUtil.locate(AssetPersistence.class.getName() +
				".impl");
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		Asset asset = _persistence.create(pk);

		assertNotNull(asset);

		assertEquals(asset.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		Asset newAsset = addAsset();

		_persistence.remove(newAsset);

		Asset existingAsset = _persistence.fetchByPrimaryKey(newAsset.getPrimaryKey());

		assertNull(existingAsset);
	}

	public void testUpdateNew() throws Exception {
		addAsset();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		Asset newAsset = _persistence.create(pk);

		newAsset.setGroupId(nextLong());
		newAsset.setCompanyId(nextLong());
		newAsset.setUserId(nextLong());
		newAsset.setUserName(randomString());
		newAsset.setCreateDate(nextDate());
		newAsset.setModifiedDate(nextDate());
		newAsset.setClassNameId(nextLong());
		newAsset.setClassPK(nextLong());
		newAsset.setVisible(randomBoolean());
		newAsset.setStartDate(nextDate());
		newAsset.setEndDate(nextDate());
		newAsset.setPublishDate(nextDate());
		newAsset.setExpirationDate(nextDate());
		newAsset.setMimeType(randomString());
		newAsset.setTitle(randomString());
		newAsset.setDescription(randomString());
		newAsset.setSummary(randomString());
		newAsset.setUrl(randomString());
		newAsset.setHeight(nextInt());
		newAsset.setWidth(nextInt());
		newAsset.setPriority(nextDouble());
		newAsset.setViewCount(nextInt());

		_persistence.update(newAsset, false);

		Asset existingAsset = _persistence.findByPrimaryKey(newAsset.getPrimaryKey());

		assertEquals(existingAsset.getAssetId(), newAsset.getAssetId());
		assertEquals(existingAsset.getGroupId(), newAsset.getGroupId());
		assertEquals(existingAsset.getCompanyId(), newAsset.getCompanyId());
		assertEquals(existingAsset.getUserId(), newAsset.getUserId());
		assertEquals(existingAsset.getUserName(), newAsset.getUserName());
		assertEquals(Time.getShortTimestamp(existingAsset.getCreateDate()),
			Time.getShortTimestamp(newAsset.getCreateDate()));
		assertEquals(Time.getShortTimestamp(existingAsset.getModifiedDate()),
			Time.getShortTimestamp(newAsset.getModifiedDate()));
		assertEquals(existingAsset.getClassNameId(), newAsset.getClassNameId());
		assertEquals(existingAsset.getClassPK(), newAsset.getClassPK());
		assertEquals(existingAsset.getVisible(), newAsset.getVisible());
		assertEquals(Time.getShortTimestamp(existingAsset.getStartDate()),
			Time.getShortTimestamp(newAsset.getStartDate()));
		assertEquals(Time.getShortTimestamp(existingAsset.getEndDate()),
			Time.getShortTimestamp(newAsset.getEndDate()));
		assertEquals(Time.getShortTimestamp(existingAsset.getPublishDate()),
			Time.getShortTimestamp(newAsset.getPublishDate()));
		assertEquals(Time.getShortTimestamp(existingAsset.getExpirationDate()),
			Time.getShortTimestamp(newAsset.getExpirationDate()));
		assertEquals(existingAsset.getMimeType(), newAsset.getMimeType());
		assertEquals(existingAsset.getTitle(), newAsset.getTitle());
		assertEquals(existingAsset.getDescription(), newAsset.getDescription());
		assertEquals(existingAsset.getSummary(), newAsset.getSummary());
		assertEquals(existingAsset.getUrl(), newAsset.getUrl());
		assertEquals(existingAsset.getHeight(), newAsset.getHeight());
		assertEquals(existingAsset.getWidth(), newAsset.getWidth());
		assertEquals(existingAsset.getPriority(), newAsset.getPriority());
		assertEquals(existingAsset.getViewCount(), newAsset.getViewCount());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		Asset newAsset = addAsset();

		Asset existingAsset = _persistence.findByPrimaryKey(newAsset.getPrimaryKey());

		assertEquals(existingAsset, newAsset);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchAssetException");
		}
		catch (NoSuchAssetException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		Asset newAsset = addAsset();

		Asset existingAsset = _persistence.fetchByPrimaryKey(newAsset.getPrimaryKey());

		assertEquals(existingAsset, newAsset);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		Asset missingAsset = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingAsset);
	}

	protected Asset addAsset() throws Exception {
		long pk = nextLong();

		Asset asset = _persistence.create(pk);

		asset.setGroupId(nextLong());
		asset.setCompanyId(nextLong());
		asset.setUserId(nextLong());
		asset.setUserName(randomString());
		asset.setCreateDate(nextDate());
		asset.setModifiedDate(nextDate());
		asset.setClassNameId(nextLong());
		asset.setClassPK(nextLong());
		asset.setVisible(randomBoolean());
		asset.setStartDate(nextDate());
		asset.setEndDate(nextDate());
		asset.setPublishDate(nextDate());
		asset.setExpirationDate(nextDate());
		asset.setMimeType(randomString());
		asset.setTitle(randomString());
		asset.setDescription(randomString());
		asset.setSummary(randomString());
		asset.setUrl(randomString());
		asset.setHeight(nextInt());
		asset.setWidth(nextInt());
		asset.setPriority(nextDouble());
		asset.setViewCount(nextInt());

		_persistence.update(asset, false);

		return asset;
	}

	private AssetPersistence _persistence;
}