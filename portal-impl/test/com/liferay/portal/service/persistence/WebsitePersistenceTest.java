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

import com.liferay.portal.NoSuchWebsiteException;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.Website;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.util.List;

/**
 * <a href="WebsitePersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class WebsitePersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (WebsitePersistence)PortalBeanLocatorUtil.locate(WebsitePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		Website website = _persistence.create(pk);

		assertNotNull(website);

		assertEquals(website.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		Website newWebsite = addWebsite();

		_persistence.remove(newWebsite);

		Website existingWebsite = _persistence.fetchByPrimaryKey(newWebsite.getPrimaryKey());

		assertNull(existingWebsite);
	}

	public void testUpdateNew() throws Exception {
		addWebsite();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		Website newWebsite = _persistence.create(pk);

		newWebsite.setCompanyId(nextLong());
		newWebsite.setUserId(nextLong());
		newWebsite.setUserName(randomString());
		newWebsite.setCreateDate(nextDate());
		newWebsite.setModifiedDate(nextDate());
		newWebsite.setClassNameId(nextLong());
		newWebsite.setClassPK(nextLong());
		newWebsite.setUrl(randomString());
		newWebsite.setTypeId(nextInt());
		newWebsite.setPrimary(randomBoolean());

		_persistence.update(newWebsite, false);

		Website existingWebsite = _persistence.findByPrimaryKey(newWebsite.getPrimaryKey());

		assertEquals(existingWebsite.getWebsiteId(), newWebsite.getWebsiteId());
		assertEquals(existingWebsite.getCompanyId(), newWebsite.getCompanyId());
		assertEquals(existingWebsite.getUserId(), newWebsite.getUserId());
		assertEquals(existingWebsite.getUserName(), newWebsite.getUserName());
		assertEquals(Time.getShortTimestamp(existingWebsite.getCreateDate()),
			Time.getShortTimestamp(newWebsite.getCreateDate()));
		assertEquals(Time.getShortTimestamp(existingWebsite.getModifiedDate()),
			Time.getShortTimestamp(newWebsite.getModifiedDate()));
		assertEquals(existingWebsite.getClassNameId(),
			newWebsite.getClassNameId());
		assertEquals(existingWebsite.getClassPK(), newWebsite.getClassPK());
		assertEquals(existingWebsite.getUrl(), newWebsite.getUrl());
		assertEquals(existingWebsite.getTypeId(), newWebsite.getTypeId());
		assertEquals(existingWebsite.getPrimary(), newWebsite.getPrimary());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		Website newWebsite = addWebsite();

		Website existingWebsite = _persistence.findByPrimaryKey(newWebsite.getPrimaryKey());

		assertEquals(existingWebsite, newWebsite);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchWebsiteException");
		}
		catch (NoSuchWebsiteException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		Website newWebsite = addWebsite();

		Website existingWebsite = _persistence.fetchByPrimaryKey(newWebsite.getPrimaryKey());

		assertEquals(existingWebsite, newWebsite);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		Website missingWebsite = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingWebsite);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		Website newWebsite = addWebsite();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Website.class,
				Website.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("websiteId",
				newWebsite.getWebsiteId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		Website existingWebsite = (Website)result.get(0);

		assertEquals(existingWebsite, newWebsite);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(Website.class,
				Website.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("websiteId", nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected Website addWebsite() throws Exception {
		long pk = nextLong();

		Website website = _persistence.create(pk);

		website.setCompanyId(nextLong());
		website.setUserId(nextLong());
		website.setUserName(randomString());
		website.setCreateDate(nextDate());
		website.setModifiedDate(nextDate());
		website.setClassNameId(nextLong());
		website.setClassPK(nextLong());
		website.setUrl(randomString());
		website.setTypeId(nextInt());
		website.setPrimary(randomBoolean());

		_persistence.update(website, false);

		return website;
	}

	private WebsitePersistence _persistence;
}