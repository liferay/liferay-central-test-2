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

package com.liferay.portlet.journal.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.RestrictionsFactoryUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.model.JournalArticle;

import java.util.List;

/**
 * <a href="JournalArticlePersistenceTest.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class JournalArticlePersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (JournalArticlePersistence)PortalBeanLocatorUtil.locate(JournalArticlePersistence.class.getName());
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		JournalArticle journalArticle = _persistence.create(pk);

		assertNotNull(journalArticle);

		assertEquals(journalArticle.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		JournalArticle newJournalArticle = addJournalArticle();

		_persistence.remove(newJournalArticle);

		JournalArticle existingJournalArticle = _persistence.fetchByPrimaryKey(newJournalArticle.getPrimaryKey());

		assertNull(existingJournalArticle);
	}

	public void testUpdateNew() throws Exception {
		addJournalArticle();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		JournalArticle newJournalArticle = _persistence.create(pk);

		newJournalArticle.setUuid(randomString());
		newJournalArticle.setResourcePrimKey(nextLong());
		newJournalArticle.setGroupId(nextLong());
		newJournalArticle.setCompanyId(nextLong());
		newJournalArticle.setUserId(nextLong());
		newJournalArticle.setUserName(randomString());
		newJournalArticle.setCreateDate(nextDate());
		newJournalArticle.setModifiedDate(nextDate());
		newJournalArticle.setArticleId(randomString());
		newJournalArticle.setVersion(nextDouble());
		newJournalArticle.setTitle(randomString());
		newJournalArticle.setUrlTitle(randomString());
		newJournalArticle.setDescription(randomString());
		newJournalArticle.setContent(randomString());
		newJournalArticle.setType(randomString());
		newJournalArticle.setStructureId(randomString());
		newJournalArticle.setTemplateId(randomString());
		newJournalArticle.setDisplayDate(nextDate());
		newJournalArticle.setStatus(nextInt());
		newJournalArticle.setStatusByUserId(nextLong());
		newJournalArticle.setStatusByUserName(randomString());
		newJournalArticle.setStatusDate(nextDate());
		newJournalArticle.setExpirationDate(nextDate());
		newJournalArticle.setReviewDate(nextDate());
		newJournalArticle.setIndexable(randomBoolean());
		newJournalArticle.setSmallImage(randomBoolean());
		newJournalArticle.setSmallImageId(nextLong());
		newJournalArticle.setSmallImageURL(randomString());

		_persistence.update(newJournalArticle, false);

		JournalArticle existingJournalArticle = _persistence.findByPrimaryKey(newJournalArticle.getPrimaryKey());

		assertEquals(existingJournalArticle.getUuid(),
			newJournalArticle.getUuid());
		assertEquals(existingJournalArticle.getId(), newJournalArticle.getId());
		assertEquals(existingJournalArticle.getResourcePrimKey(),
			newJournalArticle.getResourcePrimKey());
		assertEquals(existingJournalArticle.getGroupId(),
			newJournalArticle.getGroupId());
		assertEquals(existingJournalArticle.getCompanyId(),
			newJournalArticle.getCompanyId());
		assertEquals(existingJournalArticle.getUserId(),
			newJournalArticle.getUserId());
		assertEquals(existingJournalArticle.getUserName(),
			newJournalArticle.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingJournalArticle.getCreateDate()),
			Time.getShortTimestamp(newJournalArticle.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingJournalArticle.getModifiedDate()),
			Time.getShortTimestamp(newJournalArticle.getModifiedDate()));
		assertEquals(existingJournalArticle.getArticleId(),
			newJournalArticle.getArticleId());
		assertEquals(existingJournalArticle.getVersion(),
			newJournalArticle.getVersion());
		assertEquals(existingJournalArticle.getTitle(),
			newJournalArticle.getTitle());
		assertEquals(existingJournalArticle.getUrlTitle(),
			newJournalArticle.getUrlTitle());
		assertEquals(existingJournalArticle.getDescription(),
			newJournalArticle.getDescription());
		assertEquals(existingJournalArticle.getContent(),
			newJournalArticle.getContent());
		assertEquals(existingJournalArticle.getType(),
			newJournalArticle.getType());
		assertEquals(existingJournalArticle.getStructureId(),
			newJournalArticle.getStructureId());
		assertEquals(existingJournalArticle.getTemplateId(),
			newJournalArticle.getTemplateId());
		assertEquals(Time.getShortTimestamp(
				existingJournalArticle.getDisplayDate()),
			Time.getShortTimestamp(newJournalArticle.getDisplayDate()));
		assertEquals(existingJournalArticle.getStatus(),
			newJournalArticle.getStatus());
		assertEquals(existingJournalArticle.getStatusByUserId(),
			newJournalArticle.getStatusByUserId());
		assertEquals(existingJournalArticle.getStatusByUserName(),
			newJournalArticle.getStatusByUserName());
		assertEquals(Time.getShortTimestamp(
				existingJournalArticle.getStatusDate()),
			Time.getShortTimestamp(newJournalArticle.getStatusDate()));
		assertEquals(Time.getShortTimestamp(
				existingJournalArticle.getExpirationDate()),
			Time.getShortTimestamp(newJournalArticle.getExpirationDate()));
		assertEquals(Time.getShortTimestamp(
				existingJournalArticle.getReviewDate()),
			Time.getShortTimestamp(newJournalArticle.getReviewDate()));
		assertEquals(existingJournalArticle.getIndexable(),
			newJournalArticle.getIndexable());
		assertEquals(existingJournalArticle.getSmallImage(),
			newJournalArticle.getSmallImage());
		assertEquals(existingJournalArticle.getSmallImageId(),
			newJournalArticle.getSmallImageId());
		assertEquals(existingJournalArticle.getSmallImageURL(),
			newJournalArticle.getSmallImageURL());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		JournalArticle newJournalArticle = addJournalArticle();

		JournalArticle existingJournalArticle = _persistence.findByPrimaryKey(newJournalArticle.getPrimaryKey());

		assertEquals(existingJournalArticle, newJournalArticle);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchArticleException");
		}
		catch (NoSuchArticleException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		JournalArticle newJournalArticle = addJournalArticle();

		JournalArticle existingJournalArticle = _persistence.fetchByPrimaryKey(newJournalArticle.getPrimaryKey());

		assertEquals(existingJournalArticle, newJournalArticle);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		JournalArticle missingJournalArticle = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingJournalArticle);
	}

	public void testDynamicQueryByPrimaryKeyExisting()
		throws Exception {
		JournalArticle newJournalArticle = addJournalArticle();

		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalArticle.class,
				JournalArticle.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id",
				newJournalArticle.getId()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(1, result.size());

		JournalArticle existingJournalArticle = (JournalArticle)result.get(0);

		assertEquals(existingJournalArticle, newJournalArticle);
	}

	public void testDynamicQueryByPrimaryKeyMissing() throws Exception {
		DynamicQuery dynamicQuery = DynamicQueryFactoryUtil.forClass(JournalArticle.class,
				JournalArticle.class.getClassLoader());

		dynamicQuery.add(RestrictionsFactoryUtil.eq("id", nextLong()));

		List<Object> result = _persistence.findWithDynamicQuery(dynamicQuery);

		assertEquals(0, result.size());
	}

	protected JournalArticle addJournalArticle() throws Exception {
		long pk = nextLong();

		JournalArticle journalArticle = _persistence.create(pk);

		journalArticle.setUuid(randomString());
		journalArticle.setResourcePrimKey(nextLong());
		journalArticle.setGroupId(nextLong());
		journalArticle.setCompanyId(nextLong());
		journalArticle.setUserId(nextLong());
		journalArticle.setUserName(randomString());
		journalArticle.setCreateDate(nextDate());
		journalArticle.setModifiedDate(nextDate());
		journalArticle.setArticleId(randomString());
		journalArticle.setVersion(nextDouble());
		journalArticle.setTitle(randomString());
		journalArticle.setUrlTitle(randomString());
		journalArticle.setDescription(randomString());
		journalArticle.setContent(randomString());
		journalArticle.setType(randomString());
		journalArticle.setStructureId(randomString());
		journalArticle.setTemplateId(randomString());
		journalArticle.setDisplayDate(nextDate());
		journalArticle.setStatus(nextInt());
		journalArticle.setStatusByUserId(nextLong());
		journalArticle.setStatusByUserName(randomString());
		journalArticle.setStatusDate(nextDate());
		journalArticle.setExpirationDate(nextDate());
		journalArticle.setReviewDate(nextDate());
		journalArticle.setIndexable(randomBoolean());
		journalArticle.setSmallImage(randomBoolean());
		journalArticle.setSmallImageId(nextLong());
		journalArticle.setSmallImageURL(randomString());

		_persistence.update(journalArticle, false);

		return journalArticle;
	}

	private JournalArticlePersistence _persistence;
}