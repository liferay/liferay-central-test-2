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

package com.liferay.portlet.softwarecatalog.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.softwarecatalog.NoSuchProductScreenshotException;
import com.liferay.portlet.softwarecatalog.model.SCProductScreenshot;

import java.util.List;

/**
 * <a href="SCProductScreenshotPersistenceTest.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class SCProductScreenshotPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (SCProductScreenshotPersistence)PortalBeanLocatorUtil.locate(SCProductScreenshotPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		SCProductScreenshot scProductScreenshot = _persistence.create(pk);

		assertNotNull(scProductScreenshot);

		assertEquals(scProductScreenshot.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		SCProductScreenshot newSCProductScreenshot = addSCProductScreenshot();

		_persistence.remove(newSCProductScreenshot);

		SCProductScreenshot existingSCProductScreenshot = _persistence.fetchByPrimaryKey(newSCProductScreenshot.getPrimaryKey());

		assertNull(existingSCProductScreenshot);
	}

	public void testUpdateNew() throws Exception {
		addSCProductScreenshot();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		SCProductScreenshot newSCProductScreenshot = _persistence.create(pk);

		newSCProductScreenshot.setCompanyId(nextLong());
		newSCProductScreenshot.setGroupId(nextLong());
		newSCProductScreenshot.setProductEntryId(nextLong());
		newSCProductScreenshot.setThumbnailId(nextLong());
		newSCProductScreenshot.setFullImageId(nextLong());
		newSCProductScreenshot.setPriority(nextInt());

		_persistence.update(newSCProductScreenshot, false);

		SCProductScreenshot existingSCProductScreenshot = _persistence.findByPrimaryKey(newSCProductScreenshot.getPrimaryKey());

		assertEquals(existingSCProductScreenshot.getProductScreenshotId(),
			newSCProductScreenshot.getProductScreenshotId());
		assertEquals(existingSCProductScreenshot.getCompanyId(),
			newSCProductScreenshot.getCompanyId());
		assertEquals(existingSCProductScreenshot.getGroupId(),
			newSCProductScreenshot.getGroupId());
		assertEquals(existingSCProductScreenshot.getProductEntryId(),
			newSCProductScreenshot.getProductEntryId());
		assertEquals(existingSCProductScreenshot.getThumbnailId(),
			newSCProductScreenshot.getThumbnailId());
		assertEquals(existingSCProductScreenshot.getFullImageId(),
			newSCProductScreenshot.getFullImageId());
		assertEquals(existingSCProductScreenshot.getPriority(),
			newSCProductScreenshot.getPriority());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		SCProductScreenshot newSCProductScreenshot = addSCProductScreenshot();

		SCProductScreenshot existingSCProductScreenshot = _persistence.findByPrimaryKey(newSCProductScreenshot.getPrimaryKey());

		assertEquals(existingSCProductScreenshot, newSCProductScreenshot);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail(
				"Missing entity did not throw NoSuchProductScreenshotException");
		}
		catch (NoSuchProductScreenshotException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		SCProductScreenshot newSCProductScreenshot = addSCProductScreenshot();

		SCProductScreenshot existingSCProductScreenshot = _persistence.fetchByPrimaryKey(newSCProductScreenshot.getPrimaryKey());

		assertEquals(existingSCProductScreenshot, newSCProductScreenshot);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		SCProductScreenshot missingSCProductScreenshot = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingSCProductScreenshot);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SCProductScreenshot newSCProductScreenshot = addSCProductScreenshot();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCProductScreenshot.class,
				SCProductScreenshot.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("productScreenshotId",
				newSCProductScreenshot.getProductScreenshotId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		SCProductScreenshot existingSCProductScreenshot = (SCProductScreenshot)result.get(0);

		assertEquals(existingSCProductScreenshot, newSCProductScreenshot);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCProductScreenshot.class,
				SCProductScreenshot.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("productScreenshotId",
				nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected SCProductScreenshot addSCProductScreenshot()
		throws Exception {
		long pk = nextLong();

		SCProductScreenshot scProductScreenshot = _persistence.create(pk);

		scProductScreenshot.setCompanyId(nextLong());
		scProductScreenshot.setGroupId(nextLong());
		scProductScreenshot.setProductEntryId(nextLong());
		scProductScreenshot.setThumbnailId(nextLong());
		scProductScreenshot.setFullImageId(nextLong());
		scProductScreenshot.setPriority(nextInt());

		_persistence.update(scProductScreenshot, false);

		return scProductScreenshot;
	}

	private SCProductScreenshotPersistence _persistence;
}