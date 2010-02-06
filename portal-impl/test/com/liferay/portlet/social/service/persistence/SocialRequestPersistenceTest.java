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

import com.liferay.portlet.social.NoSuchRequestException;
import com.liferay.portlet.social.model.SocialRequest;

import java.util.List;

/**
 * <a href="SocialRequestPersistenceTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class SocialRequestPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (SocialRequestPersistence)PortalBeanLocatorUtil.locate(SocialRequestPersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		SocialRequest socialRequest = _persistence.create(pk);

		assertNotNull(socialRequest);

		assertEquals(socialRequest.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		SocialRequest newSocialRequest = addSocialRequest();

		_persistence.remove(newSocialRequest);

		SocialRequest existingSocialRequest = _persistence.fetchByPrimaryKey(newSocialRequest.getPrimaryKey());

		assertNull(existingSocialRequest);
	}

	public void testUpdateNew() throws Exception {
		addSocialRequest();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		SocialRequest newSocialRequest = _persistence.create(pk);

		newSocialRequest.setUuid(randomString());
		newSocialRequest.setGroupId(nextLong());
		newSocialRequest.setCompanyId(nextLong());
		newSocialRequest.setUserId(nextLong());
		newSocialRequest.setCreateDate(nextLong());
		newSocialRequest.setModifiedDate(nextLong());
		newSocialRequest.setClassNameId(nextLong());
		newSocialRequest.setClassPK(nextLong());
		newSocialRequest.setType(nextInt());
		newSocialRequest.setExtraData(randomString());
		newSocialRequest.setReceiverUserId(nextLong());
		newSocialRequest.setStatus(nextInt());

		_persistence.update(newSocialRequest, false);

		SocialRequest existingSocialRequest = _persistence.findByPrimaryKey(newSocialRequest.getPrimaryKey());

		assertEquals(existingSocialRequest.getUuid(), newSocialRequest.getUuid());
		assertEquals(existingSocialRequest.getRequestId(),
			newSocialRequest.getRequestId());
		assertEquals(existingSocialRequest.getGroupId(),
			newSocialRequest.getGroupId());
		assertEquals(existingSocialRequest.getCompanyId(),
			newSocialRequest.getCompanyId());
		assertEquals(existingSocialRequest.getUserId(),
			newSocialRequest.getUserId());
		assertEquals(existingSocialRequest.getCreateDate(),
			newSocialRequest.getCreateDate());
		assertEquals(existingSocialRequest.getModifiedDate(),
			newSocialRequest.getModifiedDate());
		assertEquals(existingSocialRequest.getClassNameId(),
			newSocialRequest.getClassNameId());
		assertEquals(existingSocialRequest.getClassPK(),
			newSocialRequest.getClassPK());
		assertEquals(existingSocialRequest.getType(), newSocialRequest.getType());
		assertEquals(existingSocialRequest.getExtraData(),
			newSocialRequest.getExtraData());
		assertEquals(existingSocialRequest.getReceiverUserId(),
			newSocialRequest.getReceiverUserId());
		assertEquals(existingSocialRequest.getStatus(),
			newSocialRequest.getStatus());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		SocialRequest newSocialRequest = addSocialRequest();

		SocialRequest existingSocialRequest = _persistence.findByPrimaryKey(newSocialRequest.getPrimaryKey());

		assertEquals(existingSocialRequest, newSocialRequest);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchRequestException");
		}
		catch (NoSuchRequestException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		SocialRequest newSocialRequest = addSocialRequest();

		SocialRequest existingSocialRequest = _persistence.fetchByPrimaryKey(newSocialRequest.getPrimaryKey());

		assertEquals(existingSocialRequest, newSocialRequest);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		SocialRequest missingSocialRequest = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingSocialRequest);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		SocialRequest newSocialRequest = addSocialRequest();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialRequest.class,
				SocialRequest.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("requestId",
				newSocialRequest.getRequestId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		SocialRequest existingSocialRequest = (SocialRequest)result.get(0);

		assertEquals(existingSocialRequest, newSocialRequest);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(SocialRequest.class,
				SocialRequest.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("requestId", nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected SocialRequest addSocialRequest() throws Exception {
		long pk = nextLong();

		SocialRequest socialRequest = _persistence.create(pk);

		socialRequest.setUuid(randomString());
		socialRequest.setGroupId(nextLong());
		socialRequest.setCompanyId(nextLong());
		socialRequest.setUserId(nextLong());
		socialRequest.setCreateDate(nextLong());
		socialRequest.setModifiedDate(nextLong());
		socialRequest.setClassNameId(nextLong());
		socialRequest.setClassPK(nextLong());
		socialRequest.setType(nextInt());
		socialRequest.setExtraData(randomString());
		socialRequest.setReceiverUserId(nextLong());
		socialRequest.setStatus(nextInt());

		_persistence.update(socialRequest, false);

		return socialRequest;
	}

	private SocialRequestPersistence _persistence;
}