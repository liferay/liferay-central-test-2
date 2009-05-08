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

import com.liferay.portlet.asset.NoSuchCategoryVocabularyException;
import com.liferay.portlet.asset.model.AssetCategoryVocabulary;

/**
 * <a href="AssetCategoryVocabularyPersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class AssetCategoryVocabularyPersistenceTest
	extends BasePersistenceTestCase {
	protected void setUp() throws Exception {
		super.setUp();

		_persistence = (AssetCategoryVocabularyPersistence)PortalBeanLocatorUtil.locate(AssetCategoryVocabularyPersistence.class.getName() +
				".impl");
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		AssetCategoryVocabulary assetCategoryVocabulary = _persistence.create(pk);

		assertNotNull(assetCategoryVocabulary);

		assertEquals(assetCategoryVocabulary.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		AssetCategoryVocabulary newAssetCategoryVocabulary = addAssetCategoryVocabulary();

		_persistence.remove(newAssetCategoryVocabulary);

		AssetCategoryVocabulary existingAssetCategoryVocabulary = _persistence.fetchByPrimaryKey(newAssetCategoryVocabulary.getPrimaryKey());

		assertNull(existingAssetCategoryVocabulary);
	}

	public void testUpdateNew() throws Exception {
		addAssetCategoryVocabulary();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		AssetCategoryVocabulary newAssetCategoryVocabulary = _persistence.create(pk);

		newAssetCategoryVocabulary.setGroupId(nextLong());
		newAssetCategoryVocabulary.setCompanyId(nextLong());
		newAssetCategoryVocabulary.setUserId(nextLong());
		newAssetCategoryVocabulary.setUserName(randomString());
		newAssetCategoryVocabulary.setCreateDate(nextDate());
		newAssetCategoryVocabulary.setModifiedDate(nextDate());
		newAssetCategoryVocabulary.setName(randomString());
		newAssetCategoryVocabulary.setDescription(randomString());

		_persistence.update(newAssetCategoryVocabulary, false);

		AssetCategoryVocabulary existingAssetCategoryVocabulary = _persistence.findByPrimaryKey(newAssetCategoryVocabulary.getPrimaryKey());

		assertEquals(existingAssetCategoryVocabulary.getCategoryVocabularyId(),
			newAssetCategoryVocabulary.getCategoryVocabularyId());
		assertEquals(existingAssetCategoryVocabulary.getGroupId(),
			newAssetCategoryVocabulary.getGroupId());
		assertEquals(existingAssetCategoryVocabulary.getCompanyId(),
			newAssetCategoryVocabulary.getCompanyId());
		assertEquals(existingAssetCategoryVocabulary.getUserId(),
			newAssetCategoryVocabulary.getUserId());
		assertEquals(existingAssetCategoryVocabulary.getUserName(),
			newAssetCategoryVocabulary.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingAssetCategoryVocabulary.getCreateDate()),
			Time.getShortTimestamp(newAssetCategoryVocabulary.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingAssetCategoryVocabulary.getModifiedDate()),
			Time.getShortTimestamp(newAssetCategoryVocabulary.getModifiedDate()));
		assertEquals(existingAssetCategoryVocabulary.getName(),
			newAssetCategoryVocabulary.getName());
		assertEquals(existingAssetCategoryVocabulary.getDescription(),
			newAssetCategoryVocabulary.getDescription());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		AssetCategoryVocabulary newAssetCategoryVocabulary = addAssetCategoryVocabulary();

		AssetCategoryVocabulary existingAssetCategoryVocabulary = _persistence.findByPrimaryKey(newAssetCategoryVocabulary.getPrimaryKey());

		assertEquals(existingAssetCategoryVocabulary, newAssetCategoryVocabulary);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail(
				"Missing entity did not throw NoSuchCategoryVocabularyException");
		}
		catch (NoSuchCategoryVocabularyException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		AssetCategoryVocabulary newAssetCategoryVocabulary = addAssetCategoryVocabulary();

		AssetCategoryVocabulary existingAssetCategoryVocabulary = _persistence.fetchByPrimaryKey(newAssetCategoryVocabulary.getPrimaryKey());

		assertEquals(existingAssetCategoryVocabulary, newAssetCategoryVocabulary);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		AssetCategoryVocabulary missingAssetCategoryVocabulary = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingAssetCategoryVocabulary);
	}

	protected AssetCategoryVocabulary addAssetCategoryVocabulary()
		throws Exception {
		long pk = nextLong();

		AssetCategoryVocabulary assetCategoryVocabulary = _persistence.create(pk);

		assetCategoryVocabulary.setGroupId(nextLong());
		assetCategoryVocabulary.setCompanyId(nextLong());
		assetCategoryVocabulary.setUserId(nextLong());
		assetCategoryVocabulary.setUserName(randomString());
		assetCategoryVocabulary.setCreateDate(nextDate());
		assetCategoryVocabulary.setModifiedDate(nextDate());
		assetCategoryVocabulary.setName(randomString());
		assetCategoryVocabulary.setDescription(randomString());

		_persistence.update(assetCategoryVocabulary, false);

		return assetCategoryVocabulary;
	}

	private AssetCategoryVocabularyPersistence _persistence;
}