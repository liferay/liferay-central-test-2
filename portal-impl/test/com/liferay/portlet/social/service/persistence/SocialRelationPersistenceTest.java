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

package com.liferay.portlet.social.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.social.NoSuchRelationException;
import com.liferay.portlet.social.model.SocialRelation;

import java.util.List;

/**
 * <a href="SocialRelationPersistenceTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class SocialRelationPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (SocialRelationPersistence)PortalBeanLocatorUtil.locate(SocialRelationPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		SocialRelation socialRelation = _persistence.create(pk);

		assertNotNull(socialRelation);

		assertEquals(socialRelation.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		SocialRelation newSocialRelation = addSocialRelation();

		_persistence.remove(newSocialRelation);

		SocialRelation existingSocialRelation = _persistence.fetchByPrimaryKey(newSocialRelation.getPrimaryKey());

		assertNull(existingSocialRelation);
	}

	public void testUpdateNew() throws Exception {
		addSocialRelation();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		SocialRelation newSocialRelation = _persistence.create(pk);

		newSocialRelation.setUuid(randomString());
		newSocialRelation.setCompanyId(nextLong());
		newSocialRelation.setCreateDate(nextLong());
		newSocialRelation.setUserId1(nextLong());
		newSocialRelation.setUserId2(nextLong());
		newSocialRelation.setType(nextInt());

		_persistence.update(newSocialRelation, false);

		SocialRelation existingSocialRelation = _persistence.findByPrimaryKey(newSocialRelation.getPrimaryKey());

		assertEquals(existingSocialRelation.getUuid(),
			newSocialRelation.getUuid());
		assertEquals(existingSocialRelation.getRelationId(),
			newSocialRelation.getRelationId());
		assertEquals(existingSocialRelation.getCompanyId(),
			newSocialRelation.getCompanyId());
		assertEquals(existingSocialRelation.getCreateDate(),
			newSocialRelation.getCreateDate());
		assertEquals(existingSocialRelation.getUserId1(),
			newSocialRelation.getUserId1());
		assertEquals(existingSocialRelation.getUserId2(),
			newSocialRelation.getUserId2());
		assertEquals(existingSocialRelation.getType(),
			newSocialRelation.getType());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		SocialRelation newSocialRelation = addSocialRelation();

		SocialRelation existingSocialRelation = _persistence.findByPrimaryKey(newSocialRelation.getPrimaryKey());

		assertEquals(existingSocialRelation, newSocialRelation);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchRelationException");
		}
		catch (NoSuchRelationException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		SocialRelation newSocialRelation = addSocialRelation();

		SocialRelation existingSocialRelation = _persistence.fetchByPrimaryKey(newSocialRelation.getPrimaryKey());

		assertEquals(existingSocialRelation, newSocialRelation);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		SocialRelation missingSocialRelation = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingSocialRelation);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SocialRelation newSocialRelation = addSocialRelation();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialRelation.class,
				SocialRelation.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("relationId",
				newSocialRelation.getRelationId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		SocialRelation existingSocialRelation = (SocialRelation)result.get(0);

		assertEquals(existingSocialRelation, newSocialRelation);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialRelation.class,
				SocialRelation.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("relationId", nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected SocialRelation addSocialRelation() throws Exception {
		long pk = nextLong();

		SocialRelation socialRelation = _persistence.create(pk);

		socialRelation.setUuid(randomString());
		socialRelation.setCompanyId(nextLong());
		socialRelation.setCreateDate(nextLong());
		socialRelation.setUserId1(nextLong());
		socialRelation.setUserId2(nextLong());
		socialRelation.setType(nextInt());

		_persistence.update(socialRelation, false);

		return socialRelation;
	}

	private SocialRelationPersistence _persistence;
}