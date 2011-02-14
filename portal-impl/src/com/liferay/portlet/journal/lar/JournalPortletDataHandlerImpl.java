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

package com.liferay.portlet.journal.lar;

import com.liferay.portal.NoSuchImageException;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.lar.BasePortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.Node;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.ImageUtil;
import com.liferay.portal.service.persistence.LayoutUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.lar.DLPortletDataHandlerImpl;
import com.liferay.portlet.documentlibrary.lar.FileEntryUtil;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;
import com.liferay.portlet.imagegallery.lar.IGPortletDataHandlerImpl;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.IGImageLocalServiceUtil;
import com.liferay.portlet.imagegallery.service.persistence.IGImageUtil;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.NoSuchStructureException;
import com.liferay.portlet.journal.NoSuchTemplateException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.JournalArticleImage;
import com.liferay.portlet.journal.model.JournalArticleResource;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalFeedLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalStructureLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalTemplateLocalServiceUtil;
import com.liferay.portlet.journal.service.persistence.JournalArticleImageUtil;
import com.liferay.portlet.journal.service.persistence.JournalArticleResourceUtil;
import com.liferay.portlet.journal.service.persistence.JournalArticleUtil;
import com.liferay.portlet.journal.service.persistence.JournalFeedUtil;
import com.liferay.portlet.journal.service.persistence.JournalStructureUtil;
import com.liferay.portlet.journal.service.persistence.JournalTemplateUtil;
import com.liferay.portlet.journal.util.comparator.ArticleIDComparator;
import com.liferay.portlet.journal.util.comparator.JournalStructureIDComparator;

import java.io.File;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.portlet.PortletPreferences;

/**
 * <p>
 * Provides the Journal portlet export and import functionality, which is to
 * clone all articles, structures, and templates associated with the layout's
 * group. Upon import, new instances of the corresponding articles, structures,
 * and templates are created or updated according to the DATA_MIRROW strategy
 * The author of the newly created objects are determined by the
 * JournalCreationStrategy class defined in <i>portal.properties</i>. That
 * strategy also allows the text of the journal article to be modified prior to
 * import.
 * </p>
 *
 * <p>
 * This <code>PortletDataHandler</code> differs from
 * <code>JournalContentPortletDataHandlerImpl</code> in that it exports all
 * articles owned by the group whether or not they are actually displayed in a
 * portlet in the layout set.
 * </p>
 *
 * @author Raymond Augé
 * @author Joel Kozikowski
 * @author Brian Wing Shun Chan
 * @author Bruno Farache
 * @author Karthik Sudarshan
 * @author Wesley Gong
 * @author Hugo Huijser
 * @see    com.liferay.portal.kernel.lar.PortletDataHandler
 * @see    com.liferay.portlet.journal.lar.JournalContentPortletDataHandlerImpl
 * @see    com.liferay.portlet.journal.lar.JournalCreationStrategy
 */
public class JournalPortletDataHandlerImpl extends BasePortletDataHandler {

	public static String exportReferencedContent(
			PortletDataContext portletDataContext, Element dlFoldersElement,
			Element dlFileEntriesElement, Element dlFileRanksElement,
			Element igFoldersElement, Element igImagesElement,
			Element entityElement, String content, boolean checkDateRange)
		throws Exception {

		content = exportDLFileEntries(
			portletDataContext, dlFoldersElement, dlFileEntriesElement,
			dlFileRanksElement, entityElement, content, checkDateRange);
		content = exportIGImages(
			portletDataContext, igFoldersElement, igImagesElement,
			entityElement, content, checkDateRange);
		content = exportLayoutFriendlyURLs(portletDataContext, content);
		content = exportLinksToLayout(portletDataContext, content);

		content = StringUtil.replace(
			content, StringPool.AMPERSAND_ENCODED, StringPool.AMPERSAND);

		return content;
	}

	public static String importReferencedContent(
			PortletDataContext portletDataContext, Element parentElement,
			String content)
		throws Exception {

		content = importDLFileEntries(
			portletDataContext, parentElement, content);
		content = importIGImages(portletDataContext, parentElement, content);

		Group group = GroupLocalServiceUtil.getGroup(
			portletDataContext.getScopeGroupId());

		content = StringUtil.replace(
			content, "@data_handler_group_friendly_url@",
			group.getFriendlyURL());

		content = importLinksToLayout(portletDataContext, content);

		return content;
	}

	public static void importReferencedData(
			PortletDataContext portletDataContext, Element entityElement)
		throws Exception {

		Element dlFoldersElement = entityElement.element("dl-folders");

		List<Element> dlFolderElements = Collections.emptyList();

		if (dlFoldersElement != null) {
			dlFolderElements = dlFoldersElement.elements("folder");
		}

		for (Element folderElement : dlFolderElements) {
			DLPortletDataHandlerImpl.importFolder(
				portletDataContext, folderElement);
		}

		Element dlFileEntriesElement = entityElement.element("dl-file-entries");

		List<Element> dlFileEntryElements = Collections.emptyList();

		if (dlFileEntriesElement != null) {
			dlFileEntryElements = dlFileEntriesElement.elements("file-entry");
		}

		for (Element fileEntryElement : dlFileEntryElements) {
			DLPortletDataHandlerImpl.importFileEntry(
				portletDataContext, fileEntryElement);
		}

		Element dlFileRanksElement = entityElement.element("dl-file-ranks");

		List<Element> dlFileRankElements = Collections.emptyList();

		if (dlFileRanksElement != null) {
			dlFileRankElements = dlFileRanksElement.elements("file-rank");
		}

		for (Element fileRankElement : dlFileRankElements) {
			DLPortletDataHandlerImpl.importFileRank(
				portletDataContext, fileRankElement);
		}

		Element igFoldersElement = entityElement.element("ig-folders");

		List<Element> igFolderElements = Collections.emptyList();

		if (igFoldersElement != null) {
			igFolderElements = igFoldersElement.elements("folder");
		}

		for (Element folderElement : igFolderElements) {
			IGPortletDataHandlerImpl.importFolder(
				portletDataContext, folderElement);
		}

		Element igImagesElement = entityElement.element("ig-images");

		List<Element> igImageElements = Collections.emptyList();

		if (igImagesElement != null) {
			igImageElements = igImagesElement.elements("image");
		}

		for (Element imageElement : igImageElements) {
			IGPortletDataHandlerImpl.importImage(
				portletDataContext, imageElement);
		}
	}

	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {
			_articles, _structuresTemplatesAndFeeds, _embeddedAssets, _images,
			_categories, _comments, _ratings, _tags
		};
	}

	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] {
			_articles, _structuresTemplatesAndFeeds, _images, _categories,
			_comments, _ratings, _tags
		};
	}

	public boolean isAlwaysExportable() {
		return _ALWAYS_EXPORTABLE;
	}

	public boolean isPublishToLiveByDefault() {
		return PropsValues.JOURNAL_PUBLISH_TO_LIVE_BY_DEFAULT;
	}

	protected static void exportArticle(
			PortletDataContext portletDataContext, Element articlesElement,
			Element structuresElement, Element templatesElement,
			Element dlFoldersElement, Element dlFileEntriesElement,
			Element dlFileRanksElement, Element igFoldersElement,
			Element igImagesElement, JournalArticle article,
			boolean checkDateRange)
		throws Exception {

		if (checkDateRange &&
			!portletDataContext.isWithinDateRange(article.getModifiedDate())) {

			return;
		}

		if ((article.getStatus() != WorkflowConstants.STATUS_APPROVED) &&
			(article.getStatus() != WorkflowConstants.STATUS_EXPIRED)) {

			return;
		}

		String path = getArticlePath(portletDataContext, article);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		// Clone this article to make sure changes to its content are never
		// persisted

		article = (JournalArticle)article.clone();

		Element articleElement = (Element)articlesElement.selectSingleNode(
			"//article[@path='".concat(path).concat("']"));

		if (articleElement == null) {
			articleElement = articlesElement.addElement("article");
		}

		articleElement.addAttribute("path", path);
		articleElement.addAttribute(
			"article-resource-uuid", article.getArticleResourceUuid());

		article.setUserUuid(article.getUserUuid());

		Group companyGroup = GroupLocalServiceUtil.getCompanyGroup(
			article.getCompanyId());

		if (Validator.isNotNull(article.getStructureId())) {
			JournalStructure structure = null;

			try {
				structure = JournalStructureLocalServiceUtil.getStructure(
					article.getGroupId(), article.getStructureId());
			}
			catch (NoSuchStructureException nsse) {
				structure = JournalStructureLocalServiceUtil.getStructure(
					companyGroup.getGroupId(), article.getStructureId());
			}

			articleElement.addAttribute("structure-uuid", structure.getUuid());

			exportStructure(portletDataContext, structuresElement, structure);
		}

		if (Validator.isNotNull(article.getTemplateId())) {
			JournalTemplate template = null;

			try {
				template = JournalTemplateLocalServiceUtil.getTemplate(
					portletDataContext.getScopeGroupId(),
					article.getTemplateId());
			}
			catch (NoSuchTemplateException nste) {
				template = JournalTemplateLocalServiceUtil.getTemplate(
					companyGroup.getGroupId(), article.getTemplateId());
			}

			articleElement.addAttribute("template-uuid", template.getUuid());

			exportTemplate(
				portletDataContext, templatesElement, dlFoldersElement,
				dlFileEntriesElement, dlFileRanksElement, igFoldersElement,
				igImagesElement, template, checkDateRange);
		}

		Image smallImage = ImageUtil.fetchByPrimaryKey(
			article.getSmallImageId());

		if (article.isSmallImage() && (smallImage != null)) {
			String smallImagePath = getArticleSmallImagePath(
				portletDataContext, article);

			articleElement.addAttribute("small-image-path", smallImagePath);

			article.setSmallImageType(smallImage.getType());

			portletDataContext.addZipEntry(
				smallImagePath, smallImage.getTextObj());
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "images")) {
			String imagePath = getArticleImagePath(portletDataContext, article);

			articleElement.addAttribute("image-path", imagePath);

			List<JournalArticleImage> articleImages =
				JournalArticleImageUtil.findByG_A_V(
					article.getGroupId(), article.getArticleId(),
					article.getVersion());

			for (JournalArticleImage articleImage : articleImages) {
				Image image = null;

				try {
					image = ImageUtil.findByPrimaryKey(
						articleImage.getArticleImageId());
				}
				catch (NoSuchImageException nsie) {
					continue;
				}

				String articleImagePath = getArticleImagePath(
					portletDataContext, article, articleImage, image);

				if (!portletDataContext.isPathNotProcessed(articleImagePath)) {
					continue;
				}

				portletDataContext.addZipEntry(
					articleImagePath, image.getTextObj());
			}
		}

		article.setStatusByUserUuid(article.getStatusByUserUuid());

		portletDataContext.addPermissions(
			JournalArticle.class, article.getResourcePrimKey());

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "categories")) {
			portletDataContext.addAssetCategories(
				JournalArticle.class, article.getResourcePrimKey());
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "comments")) {
			portletDataContext.addComments(
				JournalArticle.class, article.getResourcePrimKey());
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "ratings")) {
			portletDataContext.addRatingsEntries(
				JournalArticle.class, article.getResourcePrimKey());
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "tags")) {
			portletDataContext.addAssetTags(
				JournalArticle.class, article.getResourcePrimKey());
		}

		if (portletDataContext.getBooleanParameter(
				_NAMESPACE, "embedded-assets")) {

			String content = exportReferencedContent(
				portletDataContext, dlFoldersElement, dlFileEntriesElement,
				dlFileRanksElement, igFoldersElement, igImagesElement,
				articleElement, article.getContent(), checkDateRange);

			article.setContent(content);
		}

		portletDataContext.addZipEntry(path, article);
	}

	protected static String exportDLFileEntries(
			PortletDataContext portletDataContext, Element dlFoldersElement,
			Element dlFileEntriesElement, Element dlFileRanksElement,
			Element entityElement, String content, boolean checkDateRange)
		throws Exception {

		Group group = GroupLocalServiceUtil.getGroup(
			portletDataContext.getGroupId());

		if (group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		if (group.isStaged() && !group.isStagedRemotely() &&
			!group.isStagedPortlet(PortletKeys.DOCUMENT_LIBRARY)) {

			return content;
		}

		StringBuilder sb = new StringBuilder(content);

		int beginPos = content.length();
		int currentLocation = -1;

		while (true) {
			currentLocation = content.lastIndexOf(
				"/c/document_library/get_file?", beginPos);

			if (currentLocation == -1) {
				currentLocation = content.lastIndexOf("/documents/", beginPos);
			}

			if (currentLocation == -1) {
				return sb.toString();
			}

			beginPos = currentLocation;

			int endPos1 = content.indexOf(CharPool.APOSTROPHE, beginPos);
			int endPos2 = content.indexOf(CharPool.CLOSE_BRACKET, beginPos);
			int endPos3 = content.indexOf(
				CharPool.CLOSE_PARENTHESIS, beginPos);
			int endPos4 = content.indexOf(CharPool.LESS_THAN, beginPos);
			int endPos5 = content.indexOf(CharPool.QUOTE, beginPos);
			int endPos6 = content.indexOf(CharPool.SPACE, beginPos);

			int endPos = endPos1;

			if ((endPos == -1) || ((endPos2 != -1) && (endPos2 < endPos))) {
				endPos = endPos2;
			}

			if ((endPos == -1) || ((endPos3 != -1) && (endPos3 < endPos))) {
				endPos = endPos3;
			}

			if ((endPos == -1) || ((endPos4 != -1) && (endPos4 < endPos))) {
				endPos = endPos4;
			}

			if ((endPos == -1) || ((endPos5 != -1) && (endPos5 < endPos))) {
				endPos = endPos5;
			}

			if ((endPos == -1) || ((endPos6 != -1) && (endPos6 < endPos))) {
				endPos = endPos6;
			}

			if ((beginPos == -1) || (endPos == -1)) {
				break;
			}

			try {
				String oldParameters = content.substring(beginPos, endPos);

				while (oldParameters.contains(StringPool.AMPERSAND_ENCODED)) {
					oldParameters = oldParameters.replace(
						StringPool.AMPERSAND_ENCODED, StringPool.AMPERSAND);
				}

				Map<String, String> map = new HashMap<String, String>();

				if (oldParameters.startsWith("/documents/")) {
					String[] pathArray = oldParameters.split(StringPool.SLASH);

					map.put("groupId", pathArray[2]);

					if (pathArray.length == 4) {
						map.put("uuid", pathArray[3]);
					}
					else if (pathArray.length > 4) {
						map.put("folderId", pathArray[3]);
						map.put("name", pathArray[4]);
					}
				}
				else {
					oldParameters = oldParameters.substring(
						oldParameters.indexOf(CharPool.QUESTION) + 1);

					map = MapUtil.toLinkedHashMap(
						oldParameters.split(StringPool.AMPERSAND),
						StringPool.EQUAL);
				}

				FileEntry fileEntry = null;

				String uuid = map.get("uuid");

				if (uuid != null) {
					String groupIdString = map.get("groupId");

					long groupId = GetterUtil.getLong(groupIdString);

					if (groupIdString.equals("@group_id@")) {
						groupId = portletDataContext.getScopeGroupId();
					}

					fileEntry =
						DLAppLocalServiceUtil.getFileEntryByUuidAndRepositoryId(
							uuid, groupId);
				}
				else {
					String folderIdString = map.get("folderId");

					if (folderIdString != null) {
						long folderId = GetterUtil.getLong(folderIdString);
						String name = map.get("name");

						String groupIdString = map.get("groupId");

						long groupId = GetterUtil.getLong(groupIdString);

						if (groupIdString.equals("@group_id@")) {
							groupId = portletDataContext.getScopeGroupId();
						}

						fileEntry = DLAppLocalServiceUtil.getFileEntry(
							groupId, folderId, name);
					}
				}

				if (fileEntry == null) {
					beginPos--;

					continue;
				}

				String path = DLPortletDataHandlerImpl.getFileEntryPath(
					portletDataContext, fileEntry);

				Element dlReferenceElement = entityElement.addElement(
					"dl-reference");

				dlReferenceElement.addAttribute("path", path);

				DLPortletDataHandlerImpl.exportFileEntry(
					portletDataContext, dlFoldersElement, dlFileEntriesElement,
					dlFileRanksElement, fileEntry, checkDateRange);

				String dlReference = "[$dl-reference=" + path + "$]";

				sb.replace(beginPos, endPos, dlReference);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e);
				}
			}

			beginPos--;
		}

		return sb.toString();
	}

	protected static void exportFeed(
			PortletDataContext portletDataContext, Element feedsElement,
			JournalFeed feed)
		throws Exception {

		if (!portletDataContext.isWithinDateRange(feed.getModifiedDate())) {
			return;
		}

		String path = getFeedPath(portletDataContext, feed);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		feed = (JournalFeed)feed.clone();

		Element feedElement = feedsElement.addElement("feed");

		feedElement.addAttribute("path", path);

		feed.setUserUuid(feed.getUserUuid());

		Group group = GroupLocalServiceUtil.getGroup(
			portletDataContext.getScopeGroupId());

		String newGroupFriendlyURL = group.getFriendlyURL().substring(1);

		String[] friendlyUrlParts = StringUtil.split(
			feed.getTargetLayoutFriendlyUrl(), "/");

		String oldGroupFriendlyURL = friendlyUrlParts[2];

		if (newGroupFriendlyURL.equals(oldGroupFriendlyURL)) {
			feed.setTargetLayoutFriendlyUrl(
				StringUtil.replace(
					feed.getTargetLayoutFriendlyUrl(), newGroupFriendlyURL,
					"@data_handler_group_friendly_url@"));
		}

		portletDataContext.addPermissions(JournalFeed.class, feed.getId());

		portletDataContext.addZipEntry(path, feed);
	}

	protected static String exportIGImages(
			PortletDataContext portletDataContext, Element igFoldersElement,
			Element igImagesElement, Element entityElement, String content,
			boolean checkDateRange)
		throws Exception {

		Group group = GroupLocalServiceUtil.getGroup(
			portletDataContext.getGroupId());

		if (group.isStagingGroup()) {
			group = group.getLiveGroup();
		}

		if (group.isStaged() && !group.isStagedRemotely() &&
			!group.isStagedPortlet(PortletKeys.IMAGE_GALLERY)) {

			return content;
		}

		StringBuilder sb = new StringBuilder(content);

		int beginPos = content.length();

		while (true) {
			beginPos = content.lastIndexOf("/image/image_gallery?", beginPos);

			if (beginPos == -1) {
				return sb.toString();
			}

			int endPos1 = content.indexOf(CharPool.APOSTROPHE, beginPos);
			int endPos2 = content.indexOf(CharPool.CLOSE_BRACKET, beginPos);
			int endPos3 = content.indexOf(
				CharPool.CLOSE_PARENTHESIS, beginPos);
			int endPos4 = content.indexOf(CharPool.LESS_THAN, beginPos);
			int endPos5 = content.indexOf(CharPool.QUOTE, beginPos);
			int endPos6 = content.indexOf(CharPool.SPACE, beginPos);

			int endPos = endPos1;

			if ((endPos == -1) || ((endPos2 != -1) && (endPos2 < endPos))) {
				endPos = endPos2;
			}

			if ((endPos == -1) || ((endPos3 != -1) && (endPos3 < endPos))) {
				endPos = endPos3;
			}

			if ((endPos == -1) || ((endPos4 != -1) && (endPos4 < endPos))) {
				endPos = endPos4;
			}

			if ((endPos == -1) || ((endPos5 != -1) && (endPos5 < endPos))) {
				endPos = endPos5;
			}

			if ((endPos == -1) || ((endPos6 != -1) && (endPos6 < endPos))) {
				endPos = endPos6;
			}

			if ((beginPos == -1) || (endPos == -1)) {
				break;
			}

			try {
				String oldParameters = content.substring(beginPos, endPos);

				oldParameters = oldParameters.substring(
					oldParameters.indexOf(StringPool.QUESTION) + 1);

				while (oldParameters.contains(StringPool.AMPERSAND_ENCODED)) {
					oldParameters = oldParameters.replace(
						StringPool.AMPERSAND_ENCODED, StringPool.AMPERSAND);
				}

				Map<String, String> map = MapUtil.toLinkedHashMap(
					oldParameters.split(StringPool.AMPERSAND),
					StringPool.EQUAL);

				IGImage image = null;

				if (map.containsKey("uuid")) {
					String uuid = map.get("uuid");

					String groupIdString = map.get("groupId");

					long groupId = GetterUtil.getLong(groupIdString);

					if (groupIdString.equals("@group_id@")) {
						groupId = portletDataContext.getScopeGroupId();
					}

					image = IGImageLocalServiceUtil.getImageByUuidAndGroupId(
						uuid, groupId);
				}
				else if (map.containsKey("image_id") ||
						 map.containsKey("img_id") ||
						 map.containsKey("i_id")) {

					long imageId = GetterUtil.getLong(map.get("image_id"));

					if (imageId <= 0) {
						imageId = GetterUtil.getLong(map.get("img_id"));

						if (imageId <= 0) {
							imageId = GetterUtil.getLong(map.get("i_id"));
						}
					}

					try {
						image = IGImageLocalServiceUtil.getImageByLargeImageId(
							imageId);
					}
					catch (Exception e) {
						image = IGImageLocalServiceUtil.getImageBySmallImageId(
							imageId);
					}
				}

				if (image == null) {
					beginPos--;

					continue;
				}

				String path = IGPortletDataHandlerImpl.getImagePath(
					portletDataContext, image);

				Element igReferenceElement = entityElement.addElement(
					"ig-reference");

				igReferenceElement.addAttribute("path", path);

				IGPortletDataHandlerImpl.exportImage(
					portletDataContext, igFoldersElement, igImagesElement,
					image, checkDateRange);

				String igReference = "[$ig-reference=" + path + "$]";

				sb.replace(beginPos, endPos, igReference);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e);
				}
			}

			beginPos--;
		}

		return sb.toString();
	}

	protected static String exportLayoutFriendlyURLs(
		PortletDataContext portletDataContext, String content) {

		Group group = null;

		try {
			group = GroupLocalServiceUtil.getGroup(
				portletDataContext.getScopeGroupId());
		}
		catch (Exception e) {
			if (_log.isWarnEnabled()) {
				_log.warn(e);
			}

			return content;
		}

		StringBuilder sb = new StringBuilder(content);

		String friendlyURLPrivateGroupPath =
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_GROUP_SERVLET_MAPPING;
		String friendlyURLPrivateUserPath =
			PropsValues.LAYOUT_FRIENDLY_URL_PRIVATE_USER_SERVLET_MAPPING;
		String friendlyURLPublicPath =
			PropsValues.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING;

		String href = "href=";

		int beginPos = content.length();

		while (true) {
			int hrefLength = href.length();

			beginPos = content.lastIndexOf(href, beginPos);

			if (beginPos == -1) {
				break;
			}

			char c = content.charAt(beginPos + hrefLength);

			if ((c == CharPool.APOSTROPHE) || (c == CharPool.QUOTE)) {
				hrefLength++;
			}

			int endPos1 = content.indexOf(
				CharPool.APOSTROPHE, beginPos + hrefLength);
			int endPos2 = content.indexOf(
				CharPool.CLOSE_BRACKET, beginPos + hrefLength);
			int endPos3 = content.indexOf(
				CharPool.CLOSE_PARENTHESIS, beginPos + hrefLength);
			int endPos4 = content.indexOf(
				CharPool.LESS_THAN, beginPos + hrefLength);
			int endPos5 = content.indexOf(
				CharPool.QUOTE, beginPos + hrefLength);
			int endPos6 = content.indexOf(
				CharPool.SPACE, beginPos + hrefLength);

			int endPos = endPos1;

			if ((endPos == -1) || ((endPos2 != -1) && (endPos2 < endPos))) {
				endPos = endPos2;
			}

			if ((endPos == -1) || ((endPos3 != -1) && (endPos3 < endPos))) {
				endPos = endPos3;
			}

			if ((endPos == -1) || ((endPos4 != -1) && (endPos4 < endPos))) {
				endPos = endPos4;
			}

			if ((endPos == -1) || ((endPos5 != -1) && (endPos5 < endPos))) {
				endPos = endPos5;
			}

			if ((endPos == -1) || ((endPos6 != -1) && (endPos6 < endPos))) {
				endPos = endPos6;
			}

			if (endPos == -1) {
				beginPos--;

				continue;
			}

			String url = content.substring(beginPos + hrefLength, endPos);

			if (!url.startsWith(friendlyURLPrivateGroupPath) &&
				!url.startsWith(friendlyURLPrivateUserPath) &&
				!url.startsWith(friendlyURLPublicPath)) {

				beginPos--;

				continue;
			}

			int beginGroupPos = content.indexOf(
				CharPool.SLASH, beginPos + hrefLength + 1);

			if (beginGroupPos == -1) {
				beginPos--;

				continue;
			}

			int endGroupPos = content.indexOf(
				CharPool.SLASH, beginGroupPos + 1);

			if (endGroupPos == -1) {
				beginPos--;

				continue;
			}

			String groupFriendlyURL = content.substring(
				beginGroupPos, endGroupPos);

			if (groupFriendlyURL.equals(group.getFriendlyURL())) {
				sb.replace(
					beginGroupPos, endGroupPos,
					"@data_handler_group_friendly_url@");
			}

			beginPos--;
		}

		return sb.toString();
	}

	protected static String exportLinksToLayout(
			PortletDataContext portletDataContext, String content)
		throws Exception {

		List<String> oldLinksToLayout = new ArrayList<String>();
		List<String> newLinksToLayout = new ArrayList<String>();

		Matcher matcher = _exportLinksToLayoutPattern.matcher(content);

		while (matcher.find()) {
			long layoutId = GetterUtil.getLong(matcher.group(1));

			String type = matcher.group(2);

			boolean privateLayout = type.startsWith("private");

			try {
				Layout layout = LayoutLocalServiceUtil.getLayout(
					portletDataContext.getScopeGroupId(), privateLayout,
					layoutId);

				String oldLinkToLayout = matcher.group(0);

				String newLinkToLayout = StringUtil.replace(
					oldLinkToLayout, type,
					type.concat(StringPool.AT.concat(layout.getFriendlyURL())));

				oldLinksToLayout.add(oldLinkToLayout);
				newLinksToLayout.add(newLinkToLayout);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to get layout with id " + layoutId +
							" in group " + portletDataContext.getScopeGroupId(),
						e);
				}
			}
		}

		content = StringUtil.replace(
			content, ArrayUtil.toStringArray(oldLinksToLayout.toArray()),
			ArrayUtil.toStringArray(newLinksToLayout.toArray()));

		return content;
	}

	protected static void exportStructure(
			PortletDataContext portletDataContext, Element structuresElement,
			JournalStructure structure)
		throws Exception {

		String path = getStructurePath(portletDataContext, structure);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		Element structureElement = structuresElement.addElement("structure");

		structureElement.addAttribute("path", path);

		structureElement.addAttribute(
			"structure-id", String.valueOf(structure.getStructureId()));

		String parentStructureId = structure.getParentStructureId();

		if (Validator.isNotNull(parentStructureId)) {
			JournalStructure parentStructure =
				JournalStructureLocalServiceUtil.getStructure(
					structure.getGroupId(), parentStructureId);

			if (parentStructure != null) {
				structureElement.addAttribute(
					"parent-structure-uuid", parentStructure.getUuid());

				exportStructure(portletDataContext, structuresElement,
					parentStructure);
			}
		}

		portletDataContext.addPermissions(
			JournalStructure.class, structure.getId());

		portletDataContext.addZipEntry(path, structure);
	}

	protected static void exportTemplate(
			PortletDataContext portletDataContext, Element templatesElement,
			Element dlFoldersElement, Element dlFileEntriesElement,
			Element dlFileRanksElement, Element igFoldersElement,
			Element igImagesElement, JournalTemplate template,
			boolean checkDateRange)
		throws Exception {

		String path = getTemplatePath(portletDataContext, template);

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		// Clone this template to make sure changes to its content are never
		// persisted

		template = (JournalTemplate)template.clone();

		Element templateElement = templatesElement.addElement("template");

		templateElement.addAttribute("path", path);

		template.setUserUuid(template.getUserUuid());

		if (template.isSmallImage()) {
			String smallImagePath = getTemplateSmallImagePath(
				portletDataContext, template);

			templateElement.addAttribute("small-image-path", smallImagePath);

			Image smallImage = ImageUtil.fetchByPrimaryKey(
				template.getSmallImageId());

			template.setSmallImageType(smallImage.getType());

			portletDataContext.addZipEntry(
				smallImagePath, smallImage.getTextObj());
		}

		if (portletDataContext.getBooleanParameter(
				_NAMESPACE, "embedded-assets")) {

			String content = exportReferencedContent(
				portletDataContext, dlFoldersElement, dlFileEntriesElement,
				dlFileRanksElement, igFoldersElement, igImagesElement,
				templateElement, template.getXsl(), checkDateRange);

			template.setXsl(content);
		}

		portletDataContext.addPermissions(
			JournalTemplate.class, template.getId());

		portletDataContext.addZipEntry(path, template);
	}

	protected static String getArticleImagePath(
			PortletDataContext portletDataContext, JournalArticle article)
		throws Exception {

		StringBundler sb = new StringBundler(6);

		sb.append(portletDataContext.getPortletPath(PortletKeys.JOURNAL));
		sb.append("/articles/");
		sb.append(article.getArticleResourceUuid());
		sb.append(StringPool.SLASH);
		sb.append(article.getVersion());
		sb.append(StringPool.SLASH);

		return sb.toString();
	}

	protected static String getArticleImagePath(
			PortletDataContext portletDataContext, JournalArticle article,
			JournalArticleImage articleImage, Image image)
		throws Exception {

		StringBundler sb = new StringBundler(13);

		sb.append(portletDataContext.getPortletPath(PortletKeys.JOURNAL));
		sb.append("/articles/");
		sb.append(article.getArticleResourceUuid());
		sb.append(StringPool.SLASH);
		sb.append(article.getVersion());
		sb.append(StringPool.SLASH);
		sb.append(articleImage.getElInstanceId());
		sb.append(StringPool.UNDERLINE);
		sb.append(articleImage.getElName());

		if (Validator.isNotNull(articleImage.getLanguageId())) {
			sb.append(StringPool.UNDERLINE);
			sb.append(articleImage.getLanguageId());
		}

		sb.append(StringPool.PERIOD);
		sb.append(image.getType());

		return sb.toString();
	}

	protected static String getArticlePath(
			PortletDataContext portletDataContext, JournalArticle article)
		throws Exception {

		StringBundler sb = new StringBundler(8);

		sb.append(portletDataContext.getPortletPath(PortletKeys.JOURNAL));
		sb.append("/articles/");
		sb.append(article.getArticleResourceUuid());
		sb.append(StringPool.SLASH);
		sb.append(article.getVersion());
		sb.append(StringPool.SLASH);
		sb.append("article.xml");

		return sb.toString();
	}

	protected static String getArticleSmallImagePath(
			PortletDataContext portletDataContext, JournalArticle article)
		throws Exception {

		StringBundler sb = new StringBundler(6);

		sb.append(portletDataContext.getPortletPath(PortletKeys.JOURNAL));
		sb.append("/articles/");
		sb.append(article.getArticleResourceUuid());
		sb.append("/thumbnail");
		sb.append(StringPool.PERIOD);
		sb.append(article.getSmallImageType());

		return sb.toString();
	}

	protected static String getFeedPath(
		PortletDataContext portletDataContext, JournalFeed feed) {

		StringBundler sb = new StringBundler(4);

		sb.append(portletDataContext.getPortletPath(PortletKeys.JOURNAL));
		sb.append("/feeds/");
		sb.append(feed.getUuid());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getStructurePath(
		PortletDataContext portletDataContext, JournalStructure structure) {

		StringBundler sb = new StringBundler(4);

		sb.append(portletDataContext.getPortletPath(PortletKeys.JOURNAL));
		sb.append("/structures/");
		sb.append(structure.getUuid());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getTemplatePath(
		PortletDataContext portletDataContext, JournalTemplate template) {

		StringBundler sb = new StringBundler(4);

		sb.append(portletDataContext.getPortletPath(PortletKeys.JOURNAL));
		sb.append("/templates/");
		sb.append(template.getUuid());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getTemplateSmallImagePath(
			PortletDataContext portletDataContext, JournalTemplate template)
		throws Exception {

		StringBundler sb = new StringBundler(5);

		sb.append(portletDataContext.getPortletPath(PortletKeys.JOURNAL));
		sb.append("/templates/thumbnail-");
		sb.append(template.getUuid());
		sb.append(StringPool.PERIOD);
		sb.append(template.getSmallImageType());

		return sb.toString();
	}

	protected static void importArticle(
			PortletDataContext portletDataContext, Element articleElement)
		throws Exception {

		String path = articleElement.attributeValue("path");

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		JournalArticle article =
			(JournalArticle)portletDataContext.getZipEntryAsObject(path);

		long userId = portletDataContext.getUserId(article.getUserUuid());

		JournalCreationStrategy creationStrategy =
			JournalCreationStrategyFactory.getInstance();

		long authorId = creationStrategy.getAuthorUserId(
			portletDataContext, article);

		if (authorId != JournalCreationStrategy.USE_DEFAULT_USER_ID_STRATEGY) {
			userId = authorId;
		}

		User user = UserLocalServiceUtil.getUser(userId);

		String articleId = article.getArticleId();
		boolean autoArticleId = false;

		if ((Validator.isNumber(articleId)) ||
			(JournalArticleUtil.fetchByG_A_V(
				portletDataContext.getScopeGroupId(), articleId,
					JournalArticleConstants.DEFAULT_VERSION) != null)) {

			autoArticleId = true;
		}

		Map<String, String> articleIds =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				JournalArticle.class);

		String newArticleId = articleIds.get(articleId);

		if (Validator.isNotNull(newArticleId)) {

			// A sibling of a different version was already assigned a new
			// article id

			articleId = newArticleId;
			autoArticleId = false;
		}

		String content = article.getContent();

		content = importDLFileEntries(
			portletDataContext, articleElement, content);
		content = importIGImages(portletDataContext, articleElement, content);

		Group group = GroupLocalServiceUtil.getGroup(
			portletDataContext.getScopeGroupId());

		content = StringUtil.replace(
			content, "@data_handler_group_friendly_url@",
			group.getFriendlyURL());

		content = importLinksToLayout(portletDataContext, content);

		article.setContent(content);

		String newContent = creationStrategy.getTransformedContent(
			portletDataContext, article);

		if (newContent != JournalCreationStrategy.ARTICLE_CONTENT_UNCHANGED) {
			article.setContent(newContent);
		}

		Map<String, String> structureIds =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				JournalStructure.class);

		String parentStructureId = MapUtil.getString(
			structureIds, article.getStructureId(), article.getStructureId());

		Map<String, String> templateIds =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				JournalTemplate.class);

		String parentTemplateId = MapUtil.getString(
			templateIds, article.getTemplateId(), article.getTemplateId());

		Date displayDate = article.getDisplayDate();

		int displayDateMonth = 0;
		int displayDateDay = 0;
		int displayDateYear = 0;
		int displayDateHour = 0;
		int displayDateMinute = 0;

		if (displayDate != null) {
			Calendar displayCal = CalendarFactoryUtil.getCalendar(
				user.getTimeZone());

			displayCal.setTime(displayDate);

			displayDateMonth = displayCal.get(Calendar.MONTH);
			displayDateDay = displayCal.get(Calendar.DATE);
			displayDateYear = displayCal.get(Calendar.YEAR);
			displayDateHour = displayCal.get(Calendar.HOUR);
			displayDateMinute = displayCal.get(Calendar.MINUTE);

			if (displayCal.get(Calendar.AM_PM) == Calendar.PM) {
				displayDateHour += 12;
			}
		}

		Date expirationDate = article.getExpirationDate();

		int expirationDateMonth = 0;
		int expirationDateDay = 0;
		int expirationDateYear = 0;
		int expirationDateHour = 0;
		int expirationDateMinute = 0;
		boolean neverExpire = true;

		if (expirationDate != null) {
			Calendar expirationCal = CalendarFactoryUtil.getCalendar(
				user.getTimeZone());

			expirationCal.setTime(expirationDate);

			expirationDateMonth = expirationCal.get(Calendar.MONTH);
			expirationDateDay = expirationCal.get(Calendar.DATE);
			expirationDateYear = expirationCal.get(Calendar.YEAR);
			expirationDateHour = expirationCal.get(Calendar.HOUR);
			expirationDateMinute = expirationCal.get(Calendar.MINUTE);
			neverExpire = false;

			if (expirationCal.get(Calendar.AM_PM) == Calendar.PM) {
				expirationDateHour += 12;
			}
		}

		Date reviewDate = article.getReviewDate();

		int reviewDateMonth = 0;
		int reviewDateDay = 0;
		int reviewDateYear = 0;
		int reviewDateHour = 0;
		int reviewDateMinute = 0;
		boolean neverReview = true;

		if (reviewDate != null) {
			Calendar reviewCal = CalendarFactoryUtil.getCalendar(
				user.getTimeZone());

			reviewCal.setTime(reviewDate);

			reviewDateMonth = reviewCal.get(Calendar.MONTH);
			reviewDateDay = reviewCal.get(Calendar.DATE);
			reviewDateYear = reviewCal.get(Calendar.YEAR);
			reviewDateHour = reviewCal.get(Calendar.HOUR);
			reviewDateMinute = reviewCal.get(Calendar.MINUTE);
			neverReview = false;

			if (reviewCal.get(Calendar.AM_PM) == Calendar.PM) {
				reviewDateHour += 12;
			}
		}

		if (Validator.isNotNull(article.getStructureId())) {
			String structureUuid = articleElement.attributeValue(
				"structure-uuid");

			JournalStructure existingStructure =
				JournalStructureUtil.fetchByUUID_G(
					structureUuid, portletDataContext.getScopeGroupId());

			if (existingStructure == null) {
				String newStructureId = structureIds.get(
					article.getStructureId());

				if (Validator.isNotNull(newStructureId)) {
					existingStructure = JournalStructureUtil.fetchByG_S(
						portletDataContext.getScopeGroupId(),
						String.valueOf(newStructureId));
				}

				if (existingStructure == null) {
					if (_log.isWarnEnabled()) {
						StringBundler sb = new StringBundler();

						sb.append("Structure ");
						sb.append(article.getStructureId());
						sb.append(" is missing for article ");
						sb.append(article.getArticleId());
						sb.append(", skipping this article.");

						_log.warn(sb.toString());
					}

					return;
				}
			}

			parentStructureId = existingStructure.getStructureId();
		}

		if (Validator.isNotNull(article.getTemplateId())) {
			String templateUuid = articleElement.attributeValue(
				"template-uuid");

			JournalTemplate existingTemplate =
				JournalTemplateUtil.fetchByUUID_G(
					templateUuid, portletDataContext.getScopeGroupId());

			if (existingTemplate == null) {
				String newTemplateId = templateIds.get(article.getTemplateId());

				if (Validator.isNotNull(newTemplateId)) {
					existingTemplate = JournalTemplateUtil.fetchByG_T(
						portletDataContext.getScopeGroupId(), newTemplateId);
				}

				if (existingTemplate == null) {
					if (_log.isWarnEnabled()) {
						StringBundler sb = new StringBundler();

						sb.append("Template ");
						sb.append(article.getTemplateId());
						sb.append(" is missing for article ");
						sb.append(article.getArticleId());
						sb.append(", skipping this article.");

						_log.warn(sb.toString());
					}

					return;
				}
			}

			parentTemplateId = existingTemplate.getTemplateId();
		}

		File smallFile = null;

		String smallImagePath = articleElement.attributeValue(
			"small-image-path");

		if (article.isSmallImage() && Validator.isNotNull(smallImagePath)) {
			byte[] bytes = portletDataContext.getZipEntryAsByteArray(
				smallImagePath);

			smallFile = File.createTempFile(
				String.valueOf(article.getSmallImageId()),
				StringPool.PERIOD + article.getSmallImageType());

			FileUtil.write(smallFile, bytes);
		}

		Map<String, byte[]> images = new HashMap<String, byte[]>();

		String imagePath = articleElement.attributeValue("image-path");

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "images") &&
			Validator.isNotNull(imagePath)) {

			List<String> imageFiles = portletDataContext.getZipFolderEntries(
				imagePath);

			for (String imageFile : imageFiles) {
				String fileName = imageFile;

				if (fileName.contains(StringPool.SLASH)) {
					fileName = fileName.substring(
						fileName.lastIndexOf(CharPool.SLASH) + 1);
				}

				if (fileName.endsWith(".xml")) {
					continue;
				}

				int pos = fileName.lastIndexOf(CharPool.PERIOD);

				if (pos != -1) {
					fileName = fileName.substring(0, pos);
				}

				images.put(fileName, portletDataContext.getZipEntryAsByteArray(
					imageFile));
			}
		}

		String articleURL = null;

		long[] assetCategoryIds = null;
		String[] assetTagNames = null;

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "categories")) {
			assetCategoryIds = portletDataContext.getAssetCategoryIds(
				JournalArticle.class, article.getResourcePrimKey());
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "tags")) {
			assetTagNames = portletDataContext.getAssetTagNames(
				JournalArticle.class, article.getResourcePrimKey());
		}

		boolean addCommunityPermissions =
			creationStrategy.addCommunityPermissions(
				portletDataContext, article);
		boolean addGuestPermissions = creationStrategy.addGuestPermissions(
			portletDataContext, article);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(addCommunityPermissions);
		serviceContext.setAddGuestPermissions(addGuestPermissions);
		serviceContext.setAssetCategoryIds(assetCategoryIds);
		serviceContext.setAssetTagNames(assetTagNames);
		serviceContext.setAttribute("imported", Boolean.TRUE.toString());
		serviceContext.setCreateDate(article.getCreateDate());
		serviceContext.setModifiedDate(article.getModifiedDate());
		serviceContext.setScopeGroupId(portletDataContext.getScopeGroupId());

		if (article.getStatus() != WorkflowConstants.STATUS_APPROVED) {
			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);
		}

		JournalArticle importedArticle = null;

		String articleResourceUuid = articleElement.attributeValue(
			"article-resource-uuid");

		if (portletDataContext.isDataStrategyMirror()) {
			JournalArticleResource articleResource =
				JournalArticleResourceUtil.fetchByUUID_G(
					articleResourceUuid, portletDataContext.getScopeGroupId());

			serviceContext.setUuid(articleResourceUuid);

			JournalArticle existingArticle = null;

			if (articleResource != null) {
				try {
					existingArticle =
						JournalArticleLocalServiceUtil.getLatestArticle(
							articleResource.getResourcePrimKey(),
							WorkflowConstants.STATUS_ANY, false);
				}
				catch (NoSuchArticleException nsae) {
				}
			}

			if (existingArticle == null) {
				existingArticle = JournalArticleUtil.fetchByG_A_V(
					portletDataContext.getScopeGroupId(), newArticleId,
					article.getVersion());
			}

			if (existingArticle == null) {
				importedArticle = JournalArticleLocalServiceUtil.addArticle(
					userId, portletDataContext.getScopeGroupId(), articleId,
					autoArticleId, article.getVersion(), article.getTitle(),
					article.getDescription(), article.getContent(),
					article.getType(), parentStructureId, parentTemplateId,
					displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, expirationDateMonth,
					expirationDateDay, expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, reviewDateMonth,
					reviewDateDay, reviewDateYear, reviewDateHour,
					reviewDateMinute, neverReview, article.isIndexable(),
					article.isSmallImage(), article.getSmallImageURL(),
					smallFile, images, articleURL, serviceContext);
			}
			else {
				importedArticle = JournalArticleLocalServiceUtil.updateArticle(
					userId, existingArticle.getGroupId(),
					existingArticle.getArticleId(),
					existingArticle.getVersion(), article.getTitle(),
					article.getDescription(), article.getContent(),
					article.getType(), parentStructureId, parentTemplateId,
					displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, expirationDateMonth,
					expirationDateDay, expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, reviewDateMonth,
					reviewDateDay, reviewDateYear, reviewDateHour,
					reviewDateMinute, neverReview, article.isIndexable(),
					article.isSmallImage(), article.getSmallImageURL(),
					smallFile, images, articleURL, serviceContext);
			}
		}
		else {
			importedArticle = JournalArticleLocalServiceUtil.addArticle(
				userId, portletDataContext.getScopeGroupId(), articleId,
				autoArticleId, article.getVersion(), article.getTitle(),
				article.getDescription(), article.getContent(),
				article.getType(), parentStructureId, parentTemplateId,
				displayDateMonth, displayDateDay, displayDateYear,
				displayDateHour, displayDateMinute, expirationDateMonth,
				expirationDateDay, expirationDateYear, expirationDateHour,
				expirationDateMinute, neverExpire, reviewDateMonth,
				reviewDateDay, reviewDateYear, reviewDateHour, reviewDateMinute,
				neverReview, article.isIndexable(), article.isSmallImage(),
				article.getSmallImageURL(), smallFile, images, articleURL,
				serviceContext);
		}

		portletDataContext.importPermissions(
			JournalArticle.class, article.getResourcePrimKey(),
			importedArticle.getResourcePrimKey());

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "comments")) {
			portletDataContext.importComments(
				JournalArticle.class, article.getResourcePrimKey(),
				importedArticle.getResourcePrimKey(),
				portletDataContext.getScopeGroupId());
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "ratings")) {
			portletDataContext.importRatingsEntries(
				JournalArticle.class, article.getResourcePrimKey(),
				importedArticle.getResourcePrimKey());
		}

		articleIds.put(articleId, importedArticle.getArticleId());

		if (!articleId.equals(importedArticle.getArticleId())) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"An article with the ID " + articleId + " already " +
						"exists. The new generated ID is " +
							importedArticle.getArticleId());
			}
		}
	}

	protected static String importDLFileEntries(
			PortletDataContext portletDataContext, Element parentElement,
			String content)
		throws Exception {

		List<Element> dlReferenceElements = parentElement.elements(
			"dl-reference");

		for (Element dlReferenceElement : dlReferenceElements) {
			String dlReferencePath = dlReferenceElement.attributeValue("path");

			FileEntry fileEntry = null;

			try {
				fileEntry = (FileEntry)portletDataContext.getZipEntryAsObject(
					dlReferencePath);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e);
				}
			}

			if (fileEntry == null) {
				continue;
			}

			fileEntry = FileEntryUtil.fetchByUUID_R(
				fileEntry.getUuid(), portletDataContext.getGroupId());

			if (fileEntry == null) {
				continue;
			}

			String dlReference = "[$dl-reference=" + dlReferencePath + "$]";

			StringBundler sb = new StringBundler(6);

			sb.append("/documents/");
			sb.append(portletDataContext.getScopeGroupId());
			sb.append(StringPool.SLASH);
			sb.append(fileEntry.getFolderId());
			sb.append(StringPool.SLASH);
			sb.append(
				HttpUtil.encodeURL(HtmlUtil.unescape(fileEntry.getTitle())));

			content = StringUtil.replace(content, dlReference, sb.toString());
		}

		return content;
	}

	protected static void importFeed(
			PortletDataContext portletDataContext, Element feedElement)
		throws Exception {

		String path = feedElement.attributeValue("path");

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		JournalFeed feed = (JournalFeed)portletDataContext.getZipEntryAsObject(
			path);

		long userId = portletDataContext.getUserId(feed.getUserUuid());

		JournalCreationStrategy creationStrategy =
			JournalCreationStrategyFactory.getInstance();

		long authorId = creationStrategy.getAuthorUserId(
			portletDataContext, feed);

		if (authorId != JournalCreationStrategy.USE_DEFAULT_USER_ID_STRATEGY) {
			userId = authorId;
		}

		Group group = GroupLocalServiceUtil.getGroup(
			portletDataContext.getScopeGroupId());

		String newGroupFriendlyURL = group.getFriendlyURL().substring(1);

		String[] friendlyUrlParts = StringUtil.split(
			feed.getTargetLayoutFriendlyUrl(), "/");

		String oldGroupFriendlyURL = friendlyUrlParts[2];

		if (oldGroupFriendlyURL.equals("@data_handler_group_friendly_url@")) {
			feed.setTargetLayoutFriendlyUrl(
				StringUtil.replace(
					feed.getTargetLayoutFriendlyUrl(),
					"@data_handler_group_friendly_url@",
					newGroupFriendlyURL));
		}

		String feedId = feed.getFeedId();
		boolean autoFeedId = false;

		if ((Validator.isNumber(feedId)) ||
			(JournalFeedUtil.fetchByG_F(
				portletDataContext.getScopeGroupId(), feedId) != null)) {

			autoFeedId = true;
		}

		Map<String, String> structureIds =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				JournalStructure.class);

		String parentStructureId = MapUtil.getString(
			structureIds, feed.getStructureId(), feed.getStructureId());

		Map<String, String> templateIds =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				JournalTemplate.class);

		String parentTemplateId = MapUtil.getString(
			templateIds, feed.getTemplateId(), feed.getTemplateId());
		String parentRenderTemplateId = MapUtil.getString(
			templateIds, feed.getRendererTemplateId(),
			feed.getRendererTemplateId());

		boolean addCommunityPermissions =
			creationStrategy.addCommunityPermissions(portletDataContext, feed);
		boolean addGuestPermissions = creationStrategy.addGuestPermissions(
			portletDataContext, feed);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(addCommunityPermissions);
		serviceContext.setAddGuestPermissions(addGuestPermissions);
		serviceContext.setCreateDate(feed.getCreateDate());
		serviceContext.setModifiedDate(feed.getModifiedDate());

		JournalFeed existingFeed = null;

		if (portletDataContext.isDataStrategyMirror()) {
			existingFeed = JournalFeedUtil.fetchByUUID_G(
				feed.getUuid(), portletDataContext.getScopeGroupId());

			if (existingFeed == null) {
				serviceContext.setUuid(feed.getUuid());

				existingFeed = JournalFeedLocalServiceUtil.addFeed(
					userId, portletDataContext.getScopeGroupId(), feedId,
					autoFeedId, feed.getName(), feed.getDescription(),
					feed.getType(), parentStructureId, parentTemplateId,
					parentRenderTemplateId, feed.getDelta(),
					feed.getOrderByCol(), feed.getOrderByType(),
					feed.getTargetLayoutFriendlyUrl(),
					feed.getTargetPortletId(), feed.getContentField(),
					feed.getFeedType(), feed.getFeedVersion(),
					serviceContext);
			}
			else {
				existingFeed = JournalFeedLocalServiceUtil.updateFeed(
					existingFeed.getGroupId(), existingFeed.getFeedId(),
					feed.getName(), feed.getDescription(), feed.getType(),
					parentStructureId, parentTemplateId, parentRenderTemplateId,
					feed.getDelta(), feed.getOrderByCol(),
					feed.getOrderByType(), feed.getTargetLayoutFriendlyUrl(),
					feed.getTargetPortletId(), feed.getContentField(),
					feed.getFeedType(), feed.getFeedVersion(), serviceContext);
			}
		}
		else {
			existingFeed = JournalFeedLocalServiceUtil.addFeed(
				userId, portletDataContext.getScopeGroupId(), feedId,
				autoFeedId, feed.getName(), feed.getDescription(),
				feed.getType(), parentStructureId, parentTemplateId,
				parentRenderTemplateId, feed.getDelta(), feed.getOrderByCol(),
				feed.getOrderByType(), feed.getTargetLayoutFriendlyUrl(),
				feed.getTargetPortletId(), feed.getContentField(),
				feed.getFeedType(), feed.getFeedVersion(), serviceContext);
		}

		Map<String, String> feedIds =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				JournalFeed.class);

		feedIds.put(feedId, existingFeed.getFeedId());

		portletDataContext.importPermissions(
			JournalFeed.class, feed.getId(), existingFeed.getId());

		if (!feedId.equals(existingFeed.getFeedId())) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"A feed with the ID " + feedId + " already " +
						"exists. The new generated ID is " +
							existingFeed.getFeedId());
			}
		}
	}

	protected static String importIGImages(
			PortletDataContext portletDataContext, Element parentElement,
			String content)
		throws Exception {

		List<Element> igReferenceElements = parentElement.elements(
			"ig-reference");

		for (Element igReferenceElement : igReferenceElements) {
			String igReferencePath = igReferenceElement.attributeValue("path");

			IGImage image = null;

			try {
				image = (IGImage)portletDataContext.getZipEntryAsObject(
					igReferencePath);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e);
				}
			}

			if (image == null) {
				continue;
			}

			image = IGImageUtil.fetchByUUID_G(
				image.getUuid(), portletDataContext.getGroupId());

			if (image == null) {
				continue;
			}

			String igReference = "[$ig-reference=" + igReferencePath + "$]";

			StringBundler sb = new StringBundler(6);

			sb.append("/image/image_gallery?uuid=");
			sb.append(image.getUuid());
			sb.append("&groupId=");
			sb.append(portletDataContext.getScopeGroupId());
			sb.append("&t=");
			sb.append(System.currentTimeMillis());

			content = StringUtil.replace(content, igReference, sb.toString());
		}

		return content;
	}

	protected static String importLinksToLayout(
			PortletDataContext portletDataContext, String content)
		throws Exception {

		List<String> oldLinksToLayout = new ArrayList<String>();
		List<String> newLinksToLayout = new ArrayList<String>();

		Matcher matcher = _importLinksToLayoutPattern.matcher(content);

		while (matcher.find()) {
			String oldLayoutId = matcher.group(1);

			String newLayoutId = oldLayoutId;

			String type = matcher.group(2);

			boolean privateLayout = type.startsWith("private");

			String friendlyURL = matcher.group(3);

			try {
				Layout layout = LayoutUtil.fetchByG_P_F(
					portletDataContext.getScopeGroupId(), privateLayout,
					friendlyURL);

				newLayoutId = String.valueOf(layout.getLayoutId());
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(
						"Unable to get layout with friendly URL " +
							friendlyURL + " in group " +
								portletDataContext.getScopeGroupId(),
						e);
				}
			}

			String oldLinkToLayout = matcher.group(0);

			String newLinkToLayout = StringUtil.replace(
				oldLinkToLayout,
				new String[] {oldLayoutId, StringPool.AT.concat(friendlyURL)},
				new String[] {newLayoutId, StringPool.BLANK});

			oldLinksToLayout.add(oldLinkToLayout);
			newLinksToLayout.add(newLinkToLayout);
		}

		content = StringUtil.replace(
			content, ArrayUtil.toStringArray(oldLinksToLayout.toArray()),
			ArrayUtil.toStringArray(newLinksToLayout.toArray()));

		return content;
	}

	protected static void importStructure(
			PortletDataContext portletDataContext, Element structureElement,
			Element rootElement)
		throws Exception {

		long groupId = portletDataContext.getGroupId();

		String path = structureElement.attributeValue("path");

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		JournalStructure structure =
			(JournalStructure)portletDataContext.getZipEntryAsObject(path);

		long userId = portletDataContext.getUserId(structure.getUserUuid());

		JournalCreationStrategy creationStrategy =
			JournalCreationStrategyFactory.getInstance();

		long authorId = creationStrategy.getAuthorUserId(
			portletDataContext, structure);

		if (authorId != JournalCreationStrategy.USE_DEFAULT_USER_ID_STRATEGY) {
			userId = authorId;
		}

		String structureId = structure.getStructureId();
		boolean autoStructureId = false;

		if ((Validator.isNumber(structureId)) ||
			(JournalStructureUtil.fetchByG_S(
				portletDataContext.getScopeGroupId(), structureId) != null)) {

			autoStructureId = true;
		}

		Map<String, String> structureIds =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				JournalStructure.class);

		String parentStructureId = MapUtil.getString(
			structureIds, structure.getParentStructureId(),
			structure.getParentStructureId());

		Node parentStructureNode = rootElement.selectSingleNode(
			"./structures/structure[@structure-id='" + parentStructureId +
				"']");

		String parentStructureUuid = GetterUtil.getString(
			structureElement.attributeValue("parent-structure-uuid"));

		if (Validator.isNotNull(parentStructureId) &&
			(parentStructureNode != null)) {

			importStructure(portletDataContext, (Element)parentStructureNode,
				rootElement);

			parentStructureId = structureIds.get(parentStructureId);
		}
		else if (Validator.isNotNull(parentStructureUuid)) {
			JournalStructure parentStructure =
				JournalStructureLocalServiceUtil.
					getJournalStructureByUuidAndGroupId(
						parentStructureUuid, groupId);

			parentStructureId = parentStructure.getStructureId();
		}

		boolean addCommunityPermissions =
			creationStrategy.addCommunityPermissions(
				portletDataContext, structure);
		boolean addGuestPermissions = creationStrategy.addGuestPermissions(
			portletDataContext, structure);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(addCommunityPermissions);
		serviceContext.setAddGuestPermissions(addGuestPermissions);
		serviceContext.setCreateDate(structure.getCreateDate());
		serviceContext.setModifiedDate(structure.getModifiedDate());

		JournalStructure existingStructure = null;

		if (portletDataContext.isDataStrategyMirror()) {
			existingStructure = JournalStructureUtil.fetchByUUID_G(
				structure.getUuid(), portletDataContext.getScopeGroupId());

			if (existingStructure == null) {
				serviceContext.setUuid(structure.getUuid());

				existingStructure =
					JournalStructureLocalServiceUtil.addStructure(
						userId, portletDataContext.getScopeGroupId(),
						structureId, autoStructureId, parentStructureId,
						structure.getName(), structure.getDescription(),
						structure.getXsd(), serviceContext);
			}
			else {
				existingStructure =
					JournalStructureLocalServiceUtil.updateStructure(
						existingStructure.getGroupId(),
						existingStructure.getStructureId(), parentStructureId,
						structure.getName(), structure.getDescription(),
						structure.getXsd(), serviceContext);
			}
		}
		else {
			existingStructure = JournalStructureLocalServiceUtil.addStructure(
				userId, portletDataContext.getScopeGroupId(), structureId,
				autoStructureId, parentStructureId, structure.getName(),
				structure.getDescription(), structure.getXsd(),
				serviceContext);
		}

		structureIds.put(structureId, existingStructure.getStructureId());

		portletDataContext.importPermissions(
			JournalStructure.class, structure.getId(),
			existingStructure.getId());

		if (!structureId.equals(existingStructure.getStructureId())) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"A structure with the ID " + structureId + " already " +
						"exists. The new generated ID is " +
							existingStructure.getStructureId());
			}
		}
	}

	protected static void importTemplate(
			PortletDataContext portletDataContext, Element templateElement)
		throws Exception {

		String path = templateElement.attributeValue("path");

		if (!portletDataContext.isPathNotProcessed(path)) {
			return;
		}

		JournalTemplate template =
			(JournalTemplate)portletDataContext.getZipEntryAsObject(path);

		long userId = portletDataContext.getUserId(template.getUserUuid());

		JournalCreationStrategy creationStrategy =
			JournalCreationStrategyFactory.getInstance();

		long authorId = creationStrategy.getAuthorUserId(
			portletDataContext, template);

		if (authorId != JournalCreationStrategy.USE_DEFAULT_USER_ID_STRATEGY) {
			userId = authorId;
		}

		String templateId = template.getTemplateId();
		boolean autoTemplateId = false;

		if ((Validator.isNumber(templateId)) ||
			(JournalTemplateUtil.fetchByG_T(
				portletDataContext.getScopeGroupId(), templateId) != null)) {

			autoTemplateId = true;
		}

		Map<String, String> structureIds =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				JournalStructure.class);

		String parentStructureId = MapUtil.getString(
			structureIds, template.getStructureId(), template.getStructureId());

		String xsl = template.getXsl();

		xsl = importDLFileEntries(portletDataContext, templateElement, xsl);
		xsl = importIGImages(portletDataContext, templateElement, xsl);

		Group group = GroupLocalServiceUtil.getGroup(
			portletDataContext.getScopeGroupId());

		xsl = StringUtil.replace(
			xsl, "@data_handler_group_friendly_url@", group.getFriendlyURL());

		template.setXsl(xsl);

		boolean formatXsl = false;

		boolean addCommunityPermissions =
			creationStrategy.addCommunityPermissions(
				portletDataContext, template);
		boolean addGuestPermissions = creationStrategy.addGuestPermissions(
			portletDataContext, template);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(addCommunityPermissions);
		serviceContext.setAddGuestPermissions(addGuestPermissions);
		serviceContext.setCreateDate(template.getCreateDate());
		serviceContext.setModifiedDate(template.getModifiedDate());

		File smallFile = null;

		String smallImagePath = templateElement.attributeValue(
			"small-image-path");

		if (template.isSmallImage() && Validator.isNotNull(smallImagePath)) {
			if (smallImagePath.endsWith(StringPool.PERIOD)) {
				smallImagePath += template.getSmallImageType();
			}

			byte[] bytes = portletDataContext.getZipEntryAsByteArray(
				smallImagePath);

			if (bytes != null) {
				smallFile = File.createTempFile(
					String.valueOf(template.getSmallImageId()),
					StringPool.PERIOD + template.getSmallImageType());

				FileUtil.write(smallFile, bytes);
			}
		}

		JournalTemplate existingTemplate = null;

		if (portletDataContext.isDataStrategyMirror()) {
			existingTemplate = JournalTemplateUtil.fetchByUUID_G(
				template.getUuid(), portletDataContext.getScopeGroupId());

			if (existingTemplate == null) {
				serviceContext.setUuid(template.getUuid());

				existingTemplate = JournalTemplateLocalServiceUtil.addTemplate(
					userId, portletDataContext.getScopeGroupId(), templateId,
					autoTemplateId, parentStructureId, template.getName(),
					template.getDescription(), template.getXsl(), formatXsl,
					template.getLangType(), template.getCacheable(),
					template.isSmallImage(), template.getSmallImageURL(),
					smallFile, serviceContext);
			}
			else {
				existingTemplate =
					JournalTemplateLocalServiceUtil.updateTemplate(
						existingTemplate.getGroupId(),
						existingTemplate.getTemplateId(),
						existingTemplate.getStructureId(), template.getName(),
						template.getDescription(), template.getXsl(), formatXsl,
						template.getLangType(), template.getCacheable(),
						template.isSmallImage(), template.getSmallImageURL(),
						smallFile, serviceContext);
			}
		}
		else {
			existingTemplate = JournalTemplateLocalServiceUtil.addTemplate(
				userId, portletDataContext.getScopeGroupId(), templateId,
				autoTemplateId, parentStructureId, template.getName(),
				template.getDescription(), template.getXsl(), formatXsl,
				template.getLangType(), template.getCacheable(),
				template.isSmallImage(), template.getSmallImageURL(), smallFile,
				serviceContext);
		}

		Map<String, String> templateIds =
			(Map<String, String>)portletDataContext.getNewPrimaryKeysMap(
				JournalTemplate.class);

		templateIds.put(templateId, existingTemplate.getTemplateId());

		portletDataContext.importPermissions(
			JournalTemplate.class, template.getId(),
			existingTemplate.getId());

		if (!templateId.equals(existingTemplate.getTemplateId())) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"A template with the ID " + templateId + " already " +
						"exists. The new generated ID is " +
							existingTemplate.getTemplateId());
			}
		}
	}

	protected PortletPreferences doDeleteData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		if (!portletDataContext.addPrimaryKey(
				JournalPortletDataHandlerImpl.class, "deleteData")) {

			JournalArticleLocalServiceUtil.deleteArticles(
				portletDataContext.getScopeGroupId());

			JournalTemplateLocalServiceUtil.deleteTemplates(
				portletDataContext.getScopeGroupId());

			JournalStructureLocalServiceUtil.deleteStructures(
				portletDataContext.getScopeGroupId());
		}

		return portletPreferences;
	}

	protected String doExportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences)
		throws Exception {

		portletDataContext.addPermissions(
			"com.liferay.portlet.journal",
			portletDataContext.getScopeGroupId());

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("journal-data");

		rootElement.addAttribute(
			"group-id", String.valueOf(portletDataContext.getScopeGroupId()));

		Element structuresElement = rootElement.addElement("structures");

		List<JournalStructure> structures =
			JournalStructureUtil.findByGroupId(
				portletDataContext.getScopeGroupId(), QueryUtil.ALL_POS, 
				QueryUtil.ALL_POS, new JournalStructureIDComparator(true));

		for (JournalStructure structure : structures) {
			if (portletDataContext.isWithinDateRange(
					structure.getModifiedDate())) {

				exportStructure(
					portletDataContext, structuresElement, structure);
			}
		}

		Element templatesElement = rootElement.addElement("templates");
		Element dlFoldersElement = rootElement.addElement("dl-folders");
		Element dlFilesElement = rootElement.addElement("dl-file-entries");
		Element dlFileRanksElement = rootElement.addElement("dl-file-ranks");
		Element igFoldersElement = rootElement.addElement("ig-folders");
		Element igImagesElement = rootElement.addElement("ig-images");

		List<JournalTemplate> templates = JournalTemplateUtil.findByGroupId(
			portletDataContext.getScopeGroupId());

		for (JournalTemplate template : templates) {
			if (portletDataContext.isWithinDateRange(
					template.getModifiedDate())) {

				exportTemplate(
					portletDataContext, templatesElement, dlFoldersElement,
					dlFilesElement, dlFileRanksElement, igFoldersElement,
					igImagesElement, template, true);
			}
		}

		Element feedsElement = rootElement.addElement("feeds");

		List<JournalFeed> feeds = JournalFeedUtil.findByGroupId(
			portletDataContext.getScopeGroupId());

		for (JournalFeed feed : feeds) {
			if (portletDataContext.isWithinDateRange(feed.getModifiedDate())) {
				exportFeed(portletDataContext, feedsElement, feed);
			}
		}

		Element articlesElement = rootElement.addElement("articles");

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "articles")) {
			List<JournalArticle> articles = JournalArticleUtil.findByGroupId(
				portletDataContext.getScopeGroupId(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, new ArticleIDComparator(true));

			for (JournalArticle article : articles) {
				exportArticle(
					portletDataContext, articlesElement, structuresElement,
					templatesElement, dlFoldersElement, dlFilesElement,
					dlFileRanksElement, igFoldersElement, igImagesElement,
					article, true);
			}
		}

		return document.formattedString();
	}

	protected PortletPreferences doImportData(
			PortletDataContext portletDataContext, String portletId,
			PortletPreferences portletPreferences, String data)
		throws Exception {

		portletDataContext.importPermissions(
			"com.liferay.portlet.journal",
			portletDataContext.getSourceGroupId(),
			portletDataContext.getScopeGroupId());

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		importReferencedData(portletDataContext, rootElement);

		Element structuresElement = rootElement.element("structures");

		List<Element> structureElements = structuresElement.elements(
			"structure");

		for (Element structureElement : structureElements) {
			importStructure(portletDataContext, structureElement, rootElement);
		}

		Element templatesElement = rootElement.element("templates");

		List<Element> templateElements = templatesElement.elements("template");

		for (Element templateElement : templateElements) {
			importTemplate(portletDataContext, templateElement);
		}

		Element feedsElement = rootElement.element("feeds");

		List<Element> feedElements = feedsElement.elements("feed");

		for (Element feedElement : feedElements) {
			importFeed(portletDataContext, feedElement);
		}

		if (portletDataContext.getBooleanParameter(_NAMESPACE, "articles")) {
			Element articlesElement = rootElement.element("articles");

			List<Element> articleElements = articlesElement.elements("article");

			for (Element articleElement : articleElements) {
				importArticle(portletDataContext, articleElement);
			}
		}

		return portletPreferences;
	}

	private static final boolean _ALWAYS_EXPORTABLE = true;

	private static final String _NAMESPACE = "journal";

	private static Log _log = LogFactoryUtil.getLog(
		JournalPortletDataHandlerImpl.class);

	private static PortletDataHandlerBoolean _categories =
		new PortletDataHandlerBoolean(_NAMESPACE, "categories");

	private static PortletDataHandlerBoolean _comments =
		new PortletDataHandlerBoolean(_NAMESPACE, "comments");

	private static PortletDataHandlerBoolean _embeddedAssets =
		new PortletDataHandlerBoolean(_NAMESPACE, "embedded-assets");

	private static PortletDataHandlerBoolean _images =
		new PortletDataHandlerBoolean(_NAMESPACE, "images");

	private static PortletDataHandlerBoolean _ratings =
		new PortletDataHandlerBoolean(_NAMESPACE, "ratings");

	private static PortletDataHandlerBoolean
		_structuresTemplatesAndFeeds = new PortletDataHandlerBoolean(
			_NAMESPACE, "structures-templates-and-feeds", true, true);

	private static PortletDataHandlerBoolean _tags =
		new PortletDataHandlerBoolean(_NAMESPACE, "tags");

	private static PortletDataHandlerBoolean _articles =
		new PortletDataHandlerBoolean(_NAMESPACE, "articles", true, false,
		new PortletDataHandlerControl[] {_images, _comments, _ratings, _tags});

	private static Pattern _exportLinksToLayoutPattern = Pattern.compile(
		"\\[([0-9]+)@(public|private\\-[a-z]*)\\]");
	private static Pattern _importLinksToLayoutPattern = Pattern.compile(
		"\\[([0-9]+)@(public|private\\-[a-z]*)@([^\\]]*)\\]");

}