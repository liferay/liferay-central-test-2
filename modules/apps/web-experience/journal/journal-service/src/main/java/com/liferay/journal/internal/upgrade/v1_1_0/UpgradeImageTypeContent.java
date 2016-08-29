/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.journal.internal.upgrade.v1_1_0;

import com.liferay.document.library.kernel.exception.NoSuchFolderException;
import com.liferay.document.library.kernel.model.DLFolderConstants;
import com.liferay.document.library.kernel.util.DLUtil;
import com.liferay.journal.constants.JournalConstants;
import com.liferay.journal.model.JournalArticle;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Image;
import com.liferay.portal.kernel.model.Repository;
import com.liferay.portal.kernel.portletfilerepository.PortletFileRepositoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.service.ImageLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.kernel.xml.XPath;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

/**
 * @author Eudaldo Alonso
 */
public class UpgradeImageTypeContent extends UpgradeProcess {

	public UpgradeImageTypeContent(ImageLocalService imageLocalService) {
		_imageLocalService = imageLocalService;
	}

	protected String convertTypeImageElements(
			long userId, long groupId, String content, long resourcePrimKey)
		throws Exception {

		Document contentDocument = SAXReaderUtil.read(content);

		contentDocument = contentDocument.clone();

		XPath xPathSelector = SAXReaderUtil.createXPath(
			"//dynamic-element[@type='image']");

		List<Node> imageNodes = xPathSelector.selectNodes(contentDocument);

		for (Node imageNode : imageNodes) {
			Element imageEl = (Element)imageNode;

			List<Element> dynamicContentEls = imageEl.elements(
				"dynamic-content");

			for (Element dynamicContentEl : dynamicContentEls) {
				String id = dynamicContentEl.attributeValue("id");

				long folderId = getFolderId(userId, groupId, resourcePrimKey);

				FileEntry fileEntry = getFileEntry(groupId, folderId, id);

				String previewURL = StringPool.BLANK;

				if (fileEntry != null) {
					previewURL = DLUtil.getPreviewURL(
						fileEntry, fileEntry.getFileVersion(), null,
						StringPool.BLANK, false, true);
				}

				dynamicContentEl.addAttribute(
					"resourcePrimKey", String.valueOf(resourcePrimKey));
				dynamicContentEl.addAttribute("type", "journal");

				dynamicContentEl.clearContent();

				dynamicContentEl.addCDATA(previewURL);
			}
		}

		return contentDocument.formattedString();
	}

	protected void copyJournalArticleImagesToJournalRepository()
		throws Exception {

		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(
				"select articleId, articleImageId, groupId from " +
					"JournalArticleImage");
			ResultSet rs1 = ps1.executeQuery()) {

			while (rs1.next()) {
				String articleId = rs1.getString(1);
				long articleImageId = rs1.getLong(2);
				long groupId = rs1.getLong(3);

				try (PreparedStatement ps2 = connection.prepareStatement(
						"select resourcePrimKey, userId from JournalArticle " +
							"where groupId = ? and articleId = ?")) {

					ps2.setLong(1, groupId);
					ps2.setString(2, articleId);

					ResultSet rs2 = ps2.executeQuery();

					if (!rs2.next()) {
						continue;
					}

					long resourcePrimKey = rs2.getLong(1);
					long userId = rs2.getLong(2);

					long folderId = getFolderId(
						userId, groupId, resourcePrimKey);

					FileEntry fileEntry = getFileEntry(
						groupId, folderId, String.valueOf(articleImageId));

					if (fileEntry != null) {
						continue;
					}

					Image image = _imageLocalService.getImage(articleImageId);

					PortletFileRepositoryUtil.addPortletFileEntry(
						groupId, userId, JournalArticle.class.getName(),
						resourcePrimKey, JournalConstants.SERVICE_NAME,
						folderId, image.getTextObj(),
						String.valueOf(articleImageId), image.getType(), false);
				}
			}
		}
	}

	@Override
	protected void doUpgrade() throws Exception {
		copyJournalArticleImagesToJournalRepository();

		updateContentImages();
	}

	protected FileEntry getFileEntry(
		long groupId, long folderId, String fileName) {

		FileEntry fileEntry = null;

		try {
			fileEntry = PortletFileRepositoryUtil.getPortletFileEntry(
				groupId, folderId, fileName);
		}
		catch (PortalException pe) {
		}

		return fileEntry;
	}

	protected long getFolderId(long userId, long groupId, long resourcePrimKey)
		throws PortalException {

		long repositoryId = getRepositoryId(groupId);

		try {
			Folder folder = PortletFileRepositoryUtil.getPortletFolder(
				repositoryId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				String.valueOf(resourcePrimKey));

			return folder.getFolderId();
		}
		catch (NoSuchFolderException nsfe) {
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		Folder folder = PortletFileRepositoryUtil.addPortletFolder(
			userId, repositoryId, DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			String.valueOf(resourcePrimKey), serviceContext);

		return folder.getFolderId();
	}

	protected long getRepositoryId(long groupId) throws PortalException {
		Repository repository =
			PortletFileRepositoryUtil.fetchPortletRepository(
				groupId, JournalConstants.SERVICE_NAME);

		if (repository != null) {
			return repository.getRepositoryId();
		}

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);

		repository = PortletFileRepositoryUtil.addPortletRepository(
			groupId, JournalConstants.SERVICE_NAME, serviceContext);

		return repository.getRepositoryId();
	}

	protected void updateContentImages() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer();
			PreparedStatement ps1 = connection.prepareStatement(
				"select content, groupId, id_, resourcePrimKey, userId from " +
					"JournalArticle where content like ?")) {

			ps1.setString(1, "%type=\"image\"%");

			ResultSet rs1 = ps1.executeQuery();

			while (rs1.next()) {
				String content = rs1.getString(1);
				long groupId = rs1.getLong(2);
				long id = rs1.getLong(3);
				long resourcePrimKey = rs1.getLong(4);
				long userId = rs1.getLong(5);

				String newContent = convertTypeImageElements(
					userId, groupId, content, resourcePrimKey);

				try (PreparedStatement ps2 = connection.prepareStatement(
						"update JournalArticle set content = ? where id_ = " +
							"?")) {

					ps2.setString(1, newContent);
					ps2.setLong(2, id);

					ps2.executeUpdate();
				}
			}
		}
	}

	private final ImageLocalService _imageLocalService;

}