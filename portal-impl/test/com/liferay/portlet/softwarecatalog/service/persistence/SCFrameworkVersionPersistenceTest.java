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
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.softwarecatalog.NoSuchFrameworkVersionException;
import com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion;

import java.util.List;

/**
 * <a href="SCFrameworkVersionPersistenceTest.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class SCFrameworkVersionPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (SCFrameworkVersionPersistence)PortalBeanLocatorUtil.locate(SCFrameworkVersionPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		SCFrameworkVersion scFrameworkVersion = _persistence.create(pk);

		assertNotNull(scFrameworkVersion);

		assertEquals(scFrameworkVersion.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		SCFrameworkVersion newSCFrameworkVersion = addSCFrameworkVersion();

		_persistence.remove(newSCFrameworkVersion);

		SCFrameworkVersion existingSCFrameworkVersion = _persistence.fetchByPrimaryKey(newSCFrameworkVersion.getPrimaryKey());

		assertNull(existingSCFrameworkVersion);
	}

	public void testUpdateNew() throws Exception {
		addSCFrameworkVersion();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		SCFrameworkVersion newSCFrameworkVersion = _persistence.create(pk);

		newSCFrameworkVersion.setGroupId(nextLong());
		newSCFrameworkVersion.setCompanyId(nextLong());
		newSCFrameworkVersion.setUserId(nextLong());
		newSCFrameworkVersion.setUserName(randomString());
		newSCFrameworkVersion.setCreateDate(nextDate());
		newSCFrameworkVersion.setModifiedDate(nextDate());
		newSCFrameworkVersion.setName(randomString());
		newSCFrameworkVersion.setUrl(randomString());
		newSCFrameworkVersion.setActive(randomBoolean());
		newSCFrameworkVersion.setPriority(nextInt());

		_persistence.update(newSCFrameworkVersion, false);

		SCFrameworkVersion existingSCFrameworkVersion = _persistence.findByPrimaryKey(newSCFrameworkVersion.getPrimaryKey());

		assertEquals(existingSCFrameworkVersion.getFrameworkVersionId(),
			newSCFrameworkVersion.getFrameworkVersionId());
		assertEquals(existingSCFrameworkVersion.getGroupId(),
			newSCFrameworkVersion.getGroupId());
		assertEquals(existingSCFrameworkVersion.getCompanyId(),
			newSCFrameworkVersion.getCompanyId());
		assertEquals(existingSCFrameworkVersion.getUserId(),
			newSCFrameworkVersion.getUserId());
		assertEquals(existingSCFrameworkVersion.getUserName(),
			newSCFrameworkVersion.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingSCFrameworkVersion.getCreateDate()),
			Time.getShortTimestamp(newSCFrameworkVersion.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingSCFrameworkVersion.getModifiedDate()),
			Time.getShortTimestamp(newSCFrameworkVersion.getModifiedDate()));
		assertEquals(existingSCFrameworkVersion.getName(),
			newSCFrameworkVersion.getName());
		assertEquals(existingSCFrameworkVersion.getUrl(),
			newSCFrameworkVersion.getUrl());
		assertEquals(existingSCFrameworkVersion.getActive(),
			newSCFrameworkVersion.getActive());
		assertEquals(existingSCFrameworkVersion.getPriority(),
			newSCFrameworkVersion.getPriority());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		SCFrameworkVersion newSCFrameworkVersion = addSCFrameworkVersion();

		SCFrameworkVersion existingSCFrameworkVersion = _persistence.findByPrimaryKey(newSCFrameworkVersion.getPrimaryKey());

		assertEquals(existingSCFrameworkVersion, newSCFrameworkVersion);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchFrameworkVersionException");
		}
		catch (NoSuchFrameworkVersionException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		SCFrameworkVersion newSCFrameworkVersion = addSCFrameworkVersion();

		SCFrameworkVersion existingSCFrameworkVersion = _persistence.fetchByPrimaryKey(newSCFrameworkVersion.getPrimaryKey());

		assertEquals(existingSCFrameworkVersion, newSCFrameworkVersion);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		SCFrameworkVersion missingSCFrameworkVersion = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingSCFrameworkVersion);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SCFrameworkVersion newSCFrameworkVersion = addSCFrameworkVersion();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCFrameworkVersion.class,
				SCFrameworkVersion.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("frameworkVersionId",
				newSCFrameworkVersion.getFrameworkVersionId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		SCFrameworkVersion existingSCFrameworkVersion = (SCFrameworkVersion)result.get(0);

		assertEquals(existingSCFrameworkVersion, newSCFrameworkVersion);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SCFrameworkVersion.class,
				SCFrameworkVersion.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("frameworkVersionId",
				nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected SCFrameworkVersion addSCFrameworkVersion()
		throws Exception {
		long pk = nextLong();

		SCFrameworkVersion scFrameworkVersion = _persistence.create(pk);

		scFrameworkVersion.setGroupId(nextLong());
		scFrameworkVersion.setCompanyId(nextLong());
		scFrameworkVersion.setUserId(nextLong());
		scFrameworkVersion.setUserName(randomString());
		scFrameworkVersion.setCreateDate(nextDate());
		scFrameworkVersion.setModifiedDate(nextDate());
		scFrameworkVersion.setName(randomString());
		scFrameworkVersion.setUrl(randomString());
		scFrameworkVersion.setActive(randomBoolean());
		scFrameworkVersion.setPriority(nextInt());

		_persistence.update(scFrameworkVersion, false);

		return scFrameworkVersion;
	}

	private SCFrameworkVersionPersistence _persistence;
}