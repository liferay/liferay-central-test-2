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

package com.liferay.portlet.tags.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.tags.NoSuchVocabularyException;
import com.liferay.portlet.tags.model.TagsVocabulary;

/**
 * <a href="TagsVocabularyPersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TagsVocabularyPersistenceTest extends BasePersistenceTestCase {
	public void setUp() throws Exception {
		super.setUp();

		_persistence = (TagsVocabularyPersistence)PortalBeanLocatorUtil.locate(TagsVocabularyPersistence.class.getName() +
				".impl");
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		TagsVocabulary tagsVocabulary = _persistence.create(pk);

		assertNotNull(tagsVocabulary);

		assertEquals(tagsVocabulary.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		TagsVocabulary newTagsVocabulary = addTagsVocabulary();

		_persistence.remove(newTagsVocabulary);

		TagsVocabulary existingTagsVocabulary = _persistence.fetchByPrimaryKey(newTagsVocabulary.getPrimaryKey());

		assertNull(existingTagsVocabulary);
	}

	public void testUpdateNew() throws Exception {
		addTagsVocabulary();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		TagsVocabulary newTagsVocabulary = _persistence.create(pk);

		newTagsVocabulary.setGroupId(nextLong());
		newTagsVocabulary.setCompanyId(nextLong());
		newTagsVocabulary.setUserId(nextLong());
		newTagsVocabulary.setUserName(randomString());
		newTagsVocabulary.setCreateDate(nextDate());
		newTagsVocabulary.setModifiedDate(nextDate());
		newTagsVocabulary.setName(randomString());
		newTagsVocabulary.setDescription(randomString());
		newTagsVocabulary.setFolksonomy(randomBoolean());

		_persistence.update(newTagsVocabulary, false);

		TagsVocabulary existingTagsVocabulary = _persistence.findByPrimaryKey(newTagsVocabulary.getPrimaryKey());

		assertEquals(existingTagsVocabulary.getVocabularyId(),
			newTagsVocabulary.getVocabularyId());
		assertEquals(existingTagsVocabulary.getGroupId(),
			newTagsVocabulary.getGroupId());
		assertEquals(existingTagsVocabulary.getCompanyId(),
			newTagsVocabulary.getCompanyId());
		assertEquals(existingTagsVocabulary.getUserId(),
			newTagsVocabulary.getUserId());
		assertEquals(existingTagsVocabulary.getUserName(),
			newTagsVocabulary.getUserName());
		assertEquals(Time.getShortTimestamp(
				existingTagsVocabulary.getCreateDate()),
			Time.getShortTimestamp(newTagsVocabulary.getCreateDate()));
		assertEquals(Time.getShortTimestamp(
				existingTagsVocabulary.getModifiedDate()),
			Time.getShortTimestamp(newTagsVocabulary.getModifiedDate()));
		assertEquals(existingTagsVocabulary.getName(),
			newTagsVocabulary.getName());
		assertEquals(existingTagsVocabulary.getDescription(),
			newTagsVocabulary.getDescription());
		assertEquals(existingTagsVocabulary.getFolksonomy(),
			newTagsVocabulary.getFolksonomy());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		TagsVocabulary newTagsVocabulary = addTagsVocabulary();

		TagsVocabulary existingTagsVocabulary = _persistence.findByPrimaryKey(newTagsVocabulary.getPrimaryKey());

		assertEquals(existingTagsVocabulary, newTagsVocabulary);
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchVocabularyException");
		}
		catch (NoSuchVocabularyException nsee) {
		}
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		TagsVocabulary newTagsVocabulary = addTagsVocabulary();

		TagsVocabulary existingTagsVocabulary = _persistence.fetchByPrimaryKey(newTagsVocabulary.getPrimaryKey());

		assertEquals(existingTagsVocabulary, newTagsVocabulary);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		TagsVocabulary missingTagsVocabulary = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingTagsVocabulary);
	}

	protected TagsVocabulary addTagsVocabulary() throws Exception {
		long pk = nextLong();

		TagsVocabulary tagsVocabulary = _persistence.create(pk);

		tagsVocabulary.setGroupId(nextLong());
		tagsVocabulary.setCompanyId(nextLong());
		tagsVocabulary.setUserId(nextLong());
		tagsVocabulary.setUserName(randomString());
		tagsVocabulary.setCreateDate(nextDate());
		tagsVocabulary.setModifiedDate(nextDate());
		tagsVocabulary.setName(randomString());
		tagsVocabulary.setDescription(randomString());
		tagsVocabulary.setFolksonomy(randomBoolean());

		_persistence.update(tagsVocabulary, false);

		return tagsVocabulary;
	}

	private TagsVocabularyPersistence _persistence;
}