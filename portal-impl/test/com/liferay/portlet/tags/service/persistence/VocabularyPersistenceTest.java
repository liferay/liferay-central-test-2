/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import com.liferay.portlet.tags.NoSuchVocabularyException;
import com.liferay.portlet.tags.model.Vocabulary;

/**
 * <a href="VocabularyPersistenceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class VocabularyPersistenceTest extends BasePersistenceTestCase {
	protected void setUp() throws Exception {
		super.setUp();

		_persistence = (VocabularyPersistence)com.liferay.portal.kernel.bean.PortalBeanLocatorUtil.locate(_TX_IMPL);
	}

	public void testCreate() throws Exception {
		long pk = nextLong();

		Vocabulary vocabulary = _persistence.create(pk);

		assertNotNull(vocabulary);

		assertEquals(vocabulary.getPrimaryKey(), pk);
	}

	public void testRemove() throws Exception {
		Vocabulary newVocabulary = addVocabulary();

		_persistence.remove(newVocabulary);

		Vocabulary existingVocabulary = _persistence.fetchByPrimaryKey(newVocabulary.getPrimaryKey());

		assertNull(existingVocabulary);
	}

	public void testUpdateNew() throws Exception {
		addVocabulary();
	}

	public void testUpdateExisting() throws Exception {
		long pk = nextLong();

		Vocabulary newVocabulary = _persistence.create(pk);

		newVocabulary.setGroupId(nextLong());
		newVocabulary.setCompanyId(nextLong());
		newVocabulary.setUserId(nextLong());
		newVocabulary.setUserName(randomString());
		newVocabulary.setCreateDate(nextDate());
		newVocabulary.setModifiedDate(nextDate());
		newVocabulary.setName(randomString());
		newVocabulary.setDescription(randomString());
		newVocabulary.setFolksonomy(randomBoolean());

		_persistence.update(newVocabulary, false);

		Vocabulary existingVocabulary = _persistence.findByPrimaryKey(newVocabulary.getPrimaryKey());

		assertEquals(existingVocabulary.getVocabularyId(),
			newVocabulary.getVocabularyId());
		assertEquals(existingVocabulary.getGroupId(), newVocabulary.getGroupId());
		assertEquals(existingVocabulary.getCompanyId(),
			newVocabulary.getCompanyId());
		assertEquals(existingVocabulary.getUserId(), newVocabulary.getUserId());
		assertEquals(existingVocabulary.getUserName(),
			newVocabulary.getUserName());
		assertEquals(existingVocabulary.getCreateDate(),
			newVocabulary.getCreateDate());
		assertEquals(existingVocabulary.getModifiedDate(),
			newVocabulary.getModifiedDate());
		assertEquals(existingVocabulary.getName(), newVocabulary.getName());
		assertEquals(existingVocabulary.getDescription(),
			newVocabulary.getDescription());
		assertEquals(existingVocabulary.getFolksonomy(),
			newVocabulary.getFolksonomy());
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		Vocabulary newVocabulary = addVocabulary();

		Vocabulary existingVocabulary = _persistence.findByPrimaryKey(newVocabulary.getPrimaryKey());

		assertEquals(existingVocabulary, newVocabulary);
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
		Vocabulary newVocabulary = addVocabulary();

		Vocabulary existingVocabulary = _persistence.fetchByPrimaryKey(newVocabulary.getPrimaryKey());

		assertEquals(existingVocabulary, newVocabulary);
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		Vocabulary missingVocabulary = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingVocabulary);
	}

	protected Vocabulary addVocabulary() throws Exception {
		long pk = nextLong();

		Vocabulary vocabulary = _persistence.create(pk);

		vocabulary.setGroupId(nextLong());
		vocabulary.setCompanyId(nextLong());
		vocabulary.setUserId(nextLong());
		vocabulary.setUserName(randomString());
		vocabulary.setCreateDate(nextDate());
		vocabulary.setModifiedDate(nextDate());
		vocabulary.setName(randomString());
		vocabulary.setDescription(randomString());
		vocabulary.setFolksonomy(randomBoolean());

		_persistence.update(vocabulary, false);

		return vocabulary;
	}

	private static final String _TX_IMPL = VocabularyPersistence.class.getName() +
		".transaction";
	private VocabularyPersistence _persistence;
}