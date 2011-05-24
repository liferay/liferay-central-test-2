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

import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class ContentPersistenceTest extends BasePersistenceTestCase {

	public void setUp() throws Exception {
		super.setUp();

		_persistence = (ContentPersistence)PortalBeanLocatorUtil.locate(
			ContentPersistence.class.getName());
	}

	public void testCountByC_P_R_P_V() throws Exception {
		Content content = createContent();

		int count = _persistence.countByC_P_R_P_V(
			content.getCompanyId(), content.getPortletId(),
			content.getRepositoryId(), content.getPath(), content.getVersion());

		assertEquals(0, count);

		_persistence.update(content);

		count = _persistence.countByC_P_R_P_V(
			content.getCompanyId(), content.getPortletId(),
			content.getRepositoryId(), content.getPath(), content.getVersion());

		assertEquals(1, count);

		_persistence.remove(content.getContentId());

		count = _persistence.countByC_P_R_P_V(
			content.getCompanyId(), content.getPortletId(),
			content.getRepositoryId(), content.getPath(), content.getVersion());

		assertEquals(0, count);
	}

	public void testFetchByC_P_R_P_V() throws Exception {
		Content content = addContent();

		Content existingContent = _persistence.fetchByC_P_R_P_V(
			content.getCompanyId(), content.getPortletId(),
			content.getRepositoryId(), content.getPath(),
			content.getVersion());

		assertTrue(existingContent.equals(content));

		_persistence.remove(existingContent.getContentId());

		Content missingContent = _persistence.fetchByC_P_R_P_V(
			content.getCompanyId(), content.getPortletId(),
			content.getRepositoryId(), content.getPath(), randomString());

		assertNull(missingContent);
	}

	public void testFetchByPrimaryKeyExisting() throws Exception {
		Content content = addContent();

		Content existingContent = _persistence.fetchByPrimaryKey(
			content.getContentId());

		assertTrue(existingContent.equals(content));
	}

	public void testFetchByPrimaryKeyMissing() throws Exception {
		long contentId = nextLong();

		Content missingContent = _persistence.fetchByPrimaryKey(contentId);

		assertNull(missingContent);
	}

	public void testFindByC_P_R_P() throws Exception {
		Content content1 = addContent();

		long contentId2 = content1.getContentId() + 1;

		String version2 = content1.getVersion().concat("-2");

		int size = (int)content1.getSize();

		Blob data = new OutputBlob(
			new ByteArrayInputStream(new byte[size]), size);

		Content content2 = new Content(
			contentId2, content1.getCompanyId(), content1.getPortletId(),
			content1.getGroupId(), content1.getRepositoryId(),
			content1.getPath(), version2, data, size);

		_persistence.update(content2);

		List<Content> existingContents2 = _persistence.findByC_P_R_P(
			content1.getCompanyId(), content1.getPortletId(),
			content1.getRepositoryId(), content1.getPath());

		assertTrue(existingContents2.size() == 1);

		Content existingContent2 = existingContents2.get(0);

		assertTrue(existingContent2.equals(content2));

		_persistence.remove(existingContent2.getContentId());

		List<Content> existingContents1 = _persistence.findByC_P_R_P(
			content1.getCompanyId(), content1.getPortletId(),
			content1.getRepositoryId(), content1.getPath());

		assertTrue(existingContents1.size() == 1);

		Content existingContent1 = existingContents1.get(0);

		assertTrue(existingContent1.equals(content1));

		_persistence.remove(existingContent1.getContentId());

		existingContents1 = _persistence.findByC_P_R_P(
			content1.getCompanyId(), content1.getPortletId(),
			content1.getRepositoryId(), content1.getPath());

		assertTrue(existingContents1.size() == 0);
	}

	public void testFindByC_P_R_P_V() throws Exception {
		Content content = addContent();

		Content existingContent = _persistence.findByC_P_R_P_V(
			content.getCompanyId(), content.getPortletId(),
			content.getRepositoryId(), content.getPath(), content.getVersion());

		assertTrue(existingContent.equals(content));

		_persistence.remove(existingContent.getContentId());

		try {
			_persistence.findByC_P_R_P_V(
				content.getCompanyId(), content.getPortletId(),
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
		long contentId = nextLong();

		try {
			_persistence.findByPrimaryKey(contentId);

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

	public void testRemoveByC_P_R_P_V() throws Exception {
		Content content = addContent();

		Content existingContent = _persistence.fetchByPrimaryKey(
			content.getContentId());

		assertNotNull(existingContent);

		boolean removed = _persistence.removeByC_P_R_P_V(
			existingContent.getCompanyId(), existingContent.getPortletId(),
			existingContent.getRepositoryId(), existingContent.getPath(),
			existingContent.getVersion());

		assertTrue(removed);

		existingContent = _persistence.fetchByPrimaryKey(
			content.getContentId());

		assertNull(existingContent);
	}

	public void testUpdateExisting() throws Exception {
		Content content = addContent();

		byte[] bytes = new byte[1024];

		Blob data = new OutputBlob(
			new ByteArrayInputStream(bytes), bytes.length);

		Content newContent = new Content(
			content.getContentId(), nextLong(), randomString(), nextLong(),
			nextLong(), randomString(), randomString(), data, data.length());

		_persistence.update(newContent);

		Content existingContent = _persistence.findByPrimaryKey(
			newContent.getContentId());

		assertTrue(existingContent.equals(newContent));
	}

	public void testUpdateNew() throws Exception {
		Content content = addContent();

		Blob data = content.getData();

		assertFalse(data.getClass().equals(OutputBlob.class));
		assertEquals(1024, data.length());
	}

	protected Content addContent() throws Exception {
		Content content = createContent();

		_persistence.update(content);

		// Reload for a fresh Blob

		content = _persistence.findByPrimaryKey(content.getContentId());

		return content;
	}

	protected Content createContent() throws Exception {
		long contentId = nextLong();

		byte[] bytes = new byte[1024];

		Blob data = new OutputBlob(
			new ByteArrayInputStream(bytes), bytes.length);

		Content content = new Content(
			contentId, nextLong(), randomString(), nextLong(), nextLong(),
			randomString(), randomString(), data, data.length());

		return content;
	}

	private ContentPersistence _persistence;

}