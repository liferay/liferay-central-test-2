/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.documentlibrary.service.persistence;

import com.liferay.documentlibrary.NoSuchContentException;
import com.liferay.documentlibrary.model.Content;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.jdbc.OutputBlob;
import com.liferay.portal.service.persistence.BasePersistenceTestCase;

import java.io.ByteArrayInputStream;

import java.sql.Blob;

/**
 * @author Shuyang Zhou
 */
public class ContentPersistenceTest extends BasePersistenceTestCase {

	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ContentPersistence)PortalBeanLocatorUtil.locate(
			ContentPersistence.class.getName());
	}

	public void testCountByC_R_P_V() throws Exception {
		Content content = createContent();

		int count = _persistence.countByC_R_P_V(content.getCompanyId(),
			content.getRepositoryId(), content.getPath(), content.getVersion());

		assertEquals(0, count);

		_persistence.update(content);

		count = _persistence.countByC_R_P_V(content.getCompanyId(),
			content.getRepositoryId(), content.getPath(), content.getVersion());

		assertEquals(1, count);

		_persistence.remove(content.getContentId());

		count = _persistence.countByC_R_P_V(content.getCompanyId(),
			content.getRepositoryId(), content.getPath(), content.getVersion());

		assertEquals(0, count);
	}

	public void testFetchByC_R_P() throws Exception {
		Content content1 = addContent();

		int size = (int)content1.getSize();

		Blob blob = new OutputBlob(new ByteArrayInputStream(new byte[size]),
			size);

		long contentId2 = content1.getContentId() + 1;
		String version2 = content1.getVersion().concat("-2");

		Content content2 = new Content(contentId2, content1.getCompanyId(),
			content1.getPortletId(), content1.getGroupId(),
			content1.getRepositoryId(), content1.getPath(), version2, blob,
			size);

		_persistence.update(content2);

		Content existingContent2 = _persistence.fetchByC_R_P(
			content1.getCompanyId(), content1.getRepositoryId(),
			content1.getPath());

		assertTrue(existingContent2.equals(content2));

		_persistence.remove(existingContent2.getContentId());

		Content existingContent1 = _persistence.fetchByC_R_P(
			content1.getCompanyId(), content1.getRepositoryId(),
			content1.getPath());

		assertTrue(existingContent1.equals(content1));

		_persistence.remove(existingContent1.getContentId());

		Content missingContent = _persistence.fetchByC_R_P(
			content1.getCompanyId(), content1.getRepositoryId(),
			content1.getPath());

		assertNull(missingContent);
	}

	public void testFetchByC_R_P_V() throws Exception {
		Content content = addContent();

		Content existingContent = _persistence.fetchByC_R_P_V(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath(), content.getVersion());

		assertTrue(existingContent.equals(content));

		_persistence.remove(existingContent.getContentId());

		Content missingContent = _persistence.fetchByC_R_P_V(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath(), randomString());

		assertNull(missingContent);
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		Content content = addContent();

		Content existingContent = _persistence.fetchByPrimaryKey(
			content.getContentId());

		assertTrue(existingContent.equals(content));
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		Content missingContent = _persistence.fetchByPrimaryKey(pk);

		assertNull(missingContent);
	}

	public void testFindByC_R_P() throws Exception {
		Content content1 = addContent();

		int size = (int)content1.getSize();

		Blob blob = new OutputBlob(new ByteArrayInputStream(new byte[size]),
			size);

		long contentId2 = content1.getContentId() + 1;
		String version2 = content1.getVersion().concat("-2");

		Content content2 = new Content(contentId2, content1.getCompanyId(),
			content1.getPortletId(), content1.getGroupId(),
			content1.getRepositoryId(), content1.getPath(), version2, blob,
			size);

		_persistence.update(content2);

		Content existingContent2 = _persistence.findByC_R_P(
			content1.getCompanyId(), content1.getRepositoryId(),
			content1.getPath());

		assertTrue(existingContent2.equals(content2));

		_persistence.remove(existingContent2.getContentId());

		Content existingContent1 = _persistence.findByC_R_P(
			content1.getCompanyId(), content1.getRepositoryId(),
			content1.getPath());

		assertTrue(existingContent1.equals(content1));

		_persistence.remove(existingContent1.getContentId());

		try {
			_persistence.findByC_R_P(content1.getCompanyId(),
				content1.getRepositoryId(), content1.getPath());

			fail("Missing entity did not throw NoSuchContentException");
		}
		catch (NoSuchContentException nsce) {
		}
	}

	public void testFindByC_R_P_V() throws Exception {
		Content content = addContent();

		Content existingContent = _persistence.findByC_R_P_V(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath(), content.getVersion());

		assertTrue(existingContent.equals(content));

		_persistence.remove(existingContent.getContentId());

		try {
			_persistence.findByC_R_P_V(content.getCompanyId(),
				content.getRepositoryId(), content.getPath(), randomString());

			fail("Missing entity did not throw NoSuchContentException");
		}
		catch (NoSuchContentException nsce) {
		}
	}

	public void testFindByPrimaryKeyExisting() throws Exception {
		Content content = addContent();

		Content existingContent = _persistence.findByPrimaryKey(
			content.getContentId());

		assertTrue(existingContent.equals(content));
	}

	public void testFindByPrimaryKeyMissing() throws Exception {
		long pk = nextLong();

		try {
			_persistence.findByPrimaryKey(pk);

			fail("Missing entity did not throw NoSuchContentException");
		}
		catch (NoSuchContentException nsce) {
		}
	}

	public void testRemove() throws Exception {
		Content content = addContent();

		Content existingContent = _persistence.fetchByPrimaryKey(
			content.getContentId());

		assertNotNull(existingContent);

		assertTrue(existingContent.equals(content));

		_persistence.remove(content.getContentId());

		existingContent = _persistence.fetchByPrimaryKey(
			content.getContentId());

		assertNull(existingContent);
	}

	public void testRemoveByC_R_P_V() throws Exception {
		Content content = addContent();

		Content existingContent = _persistence.fetchByPrimaryKey(
			content.getContentId());

		assertNotNull(existingContent);

		boolean removed = _persistence.removeByC_R_P_V(
			existingContent.getCompanyId(), existingContent.getRepositoryId(),
			existingContent.getPath(), existingContent.getVersion());

		assertTrue(removed);

		existingContent = _persistence.fetchByPrimaryKey(
			content.getContentId());

		assertNull(existingContent);
	}

	public void testUpdateExisting() throws Exception {
		Content content = addContent();

		byte[] data = new byte[1024];

		Blob blob = new OutputBlob(new ByteArrayInputStream(data), data.length);

		Content newContent = new Content(content.getContentId(), nextLong(),
			randomString(), nextLong(), nextLong(), randomString(),
			randomString(), blob, blob.length());

		_persistence.update(newContent);

		Content existingContent = _persistence.findByPrimaryKey(
			newContent.getContentId());

		assertTrue(existingContent.equals(newContent));
	}

	public void testUpdateNew() throws Exception {
		Content content = addContent();
		Blob blob = content.getData();

		assertFalse(blob.getClass().equals(OutputBlob.class));
		assertEquals(1024, blob.length());
	}

	protected Content createContent() throws Exception {
		long pk = nextLong();

		byte[] data = new byte[1024];

		Blob blob = new OutputBlob(new ByteArrayInputStream(data), data.length);

		Content content = new Content(pk, nextLong(), randomString(),
			nextLong(), nextLong(), randomString(), randomString(), blob,
			blob.length());

		return content;
	}

	protected Content addContent() throws Exception {
		Content content = createContent();

		_persistence.update(content);

		// Reload for a fresh Blob
		content = _persistence.findByPrimaryKey(content.getContentId());

		return content;
	}

	private ContentPersistence _persistence;

}