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

package com.liferay.documentlibrary.service;

import com.liferay.documentlibrary.model.Content;
import com.liferay.documentlibrary.util.Hook;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.jdbc.OutputBlob;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.service.BaseServiceTestCase;

import java.io.ByteArrayInputStream;

import java.sql.Blob;

/**
 * @author Michael Chen
 */
public class ContentLocalServiceTest extends BaseServiceTestCase {

	public void setUp() throws Exception {
		super.setUp();

		_service = (ContentLocalService)PortalBeanLocatorUtil.locate(
			ContentLocalService.class.getName());
	}

	public void testAddContentWithByteArray() throws Exception {
		Content content = createContent();

		_service.addContent(
			content.getCompanyId(), content.getPortletId(),
			content.getGroupId(), content.getRepositoryId(), content.getPath(),
			content.getVersion(), new byte[1024]);

		boolean exists = _service.hasContent(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath(), content.getVersion());

		assertTrue(exists);

		Content content2 = _service.getContent(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath());

		Content content3 = _service.getContent(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath());

		assertEquals(content2, content3);
	}

	public void testAddContentWithInputStream() throws Exception {
		Content content = createContent();

		ByteArrayInputStream byteArrayInputStream =
			new ByteArrayInputStream(new byte[1024]);

		_service.addContent(
			content.getCompanyId(), content.getPortletId(),
			content.getGroupId(), content.getRepositoryId(), content.getPath(),
			content.getVersion(), byteArrayInputStream,
			byteArrayInputStream.available());

		boolean exists = _service.hasContent(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath(), content.getVersion());

		assertTrue(exists);

		Content content2 = _service.getContent(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath());

		Content content3 = _service.getContent(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath());

		assertEquals(content2, content3);
	}

	public void testGetContent() throws Exception {
		Content content = addContent();

		Content newContent = _service.getContent(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath());

		assertEquals(content, newContent);
	}

	public void testGetContentNames() throws Exception {
		Content content = createContent();

		content.setPath(content.getPath().concat(StringPool.SLASH));

		_service.addContent(
			content.getCompanyId(), content.getPortletId(),
			content.getGroupId(), content.getRepositoryId(),
			content.getPath(), content.getVersion(), new byte[1024]);

		String[] names = _service.getContentNames(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath());

		assertEquals(1, names.length);
		assertEquals(content.getPath(), names[0]);
	}

	public void testGetContentSize() throws Exception {
		Content content = addContent();

		long size = _service.getContentSize(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath());

		assertEquals(content.getSize(), size);
	}

	public void testGetContentWithVersion() throws Exception {
		Content content = addContent();

		Content newContent = _service.getContent(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath(), content.getVersion());

		assertEquals(content, newContent);
	}

	public void testHasContent() throws Exception {
		Content content = addContent();

		boolean exists = _service.hasContent(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath(), content.getVersion());

		assertTrue(exists);
	}

	public void testRemoveByC_P_R_P() throws Exception {
		Content content = addContent();

		boolean deleted = _service.removeByC_P_R_P(
			content.getCompanyId(), content.getPortletId(),
			content.getRepositoryId(), content.getPath());

		assertTrue(deleted);

		boolean exists = _service.hasContent(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath(), content.getVersion());

		assertFalse(exists);
	}

	public void testRemoveByC_R_P_V() throws Exception {
		Content content = addContent();

		boolean deleted = _service.removeByC_R_P_V(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath(), content.getVersion());

		assertTrue(deleted);

		boolean exists = _service.hasContent(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath(), content.getVersion());

		assertFalse(exists);
	}

	public void testRemoveByC_R_P() throws Exception {
		Content content = createContent();

		content.setPath(content.getPath().concat(StringPool.SLASH));

		_service.addContent(
			content.getCompanyId(), content.getPortletId(),
			content.getGroupId(), content.getRepositoryId(),
			content.getPath(), content.getVersion(), new byte[1024]);

		boolean exists = _service.hasContent(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath(), content.getVersion());

		assertTrue(exists);

		_service.removeByC_R_P(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath());

		boolean exists2 = _service.hasContent(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath(), content.getVersion());

		assertFalse(exists2);
	}

	public void testUpdateContentWithNewPath() throws Exception {
		Content content = addContent();

		String newPath = randomString();

		_service.updateContent(
			content.getCompanyId(), content.getPortletId(),
			content.getGroupId(), content.getRepositoryId(), content.getPath(),
			newPath);

		Content newContent = _service.getContent(
			content.getCompanyId(), content.getRepositoryId(), newPath);

		assertFalse(content.getPath().equals(newContent.getPath()));
		assertFalse(content.equals(newContent));
	}

	public void testUpdateContentWithNewRepositoryId() throws Exception {
		Content content = addContent();

		long newRepositoryId = nextLong();

		_service.updateContent(
			content.getCompanyId(), content.getPortletId(),
			content.getGroupId(), content.getRepositoryId(), newRepositoryId,
			content.getPath());

		Content newContent = _service.getContent(
			content.getCompanyId(), newRepositoryId, content.getPath());

		assertFalse(content.getRepositoryId() == newContent.getRepositoryId());
		assertFalse(content.equals(newContent));
	}

	protected Content addContent() throws Exception {
		Content content = createContent();

		_service.addContent(
			content.getCompanyId(), content.getPortletId(),
			content.getGroupId(), content.getRepositoryId(), content.getPath(),
			Hook.DEFAULT_VERSION, new byte[1024]);

		content = _service.getContent(
			content.getCompanyId(), content.getRepositoryId(),
			content.getPath());

		return content;
	}

	protected Content createContent() throws Exception {
		long contentId = nextLong();

		byte[] bytes = new byte[1024];

		Blob blob = new OutputBlob(
			new ByteArrayInputStream(bytes), bytes.length);

		Content content = new Content(
			contentId, nextLong(), randomString(), nextLong(), nextLong(),
			randomString(), Hook.DEFAULT_VERSION, blob, blob.length());

		return content;
	}

	private ContentLocalService _service;

}