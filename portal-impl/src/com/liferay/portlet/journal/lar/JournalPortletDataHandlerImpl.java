/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.User;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.ImageUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.lar.DLPortletDataHandlerImpl;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalServiceUtil;
import com.liferay.portlet.documentlibrary.service.persistence.DLFileEntryUtil;
import com.liferay.portlet.imagegallery.lar.IGPortletDataHandlerImpl;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.imagegallery.service.IGImageLocalServiceUtil;
import com.liferay.portlet.imagegallery.service.persistence.IGImageUtil;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleConstants;
import com.liferay.portlet.journal.model.JournalArticleImage;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalFeedLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalStructureLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalTemplateLocalServiceUtil;
import com.liferay.portlet.journal.service.persistence.JournalArticleImageUtil;
import com.liferay.portlet.journal.service.persistence.JournalArticleUtil;
import com.liferay.portlet.journal.service.persistence.JournalFeedUtil;
import com.liferay.portlet.journal.service.persistence.JournalStructureUtil;
import com.liferay.portlet.journal.service.persistence.JournalTemplateUtil;
import com.liferay.portlet.journal.util.comparator.ArticleIDComparator;

import java.io.File;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * @see	   com.liferay.portal.kernel.lar.PortletDataHandler
 * @see	   com.liferay.portlet.journal.lar.JournalContentPortletDataHandlerImpl
 * @see	   com.liferay.portlet.journal.lar.JournalCreationStrategy
 */
public class JournalPortletDataHandlerImpl extends BasePortletDataHandler {

	protected static void exportArticle(
			PortletDataContext context, Element articlesElement,
			Element dlFoldersElement, Element dlFileEntriesElement,
			Element dlFileRanksElement, Element igFoldersElement,
			Element igImagesElement, JournalArticle article,
			boolean checkDateRange)
		throws Exception {

		if (checkDateRange &&
			!context.isWithinDateRange(article.getModifiedDate())) {

			return;
		}

		if (article.getStatus() != WorkflowConstants.STATUS_APPROVED) {
			return;
		}

		String path = getArticlePath(context, article);

		if (!context.isPathNotProcessed(path)) {
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

		article.setUserUuid(article.getUserUuid());

		if (Validator.isNotNull(article.getStructureId())) {
			JournalStructure structure =
				JournalStructureLocalServiceUtil.getStructure(
					context.getScopeGroupId(), article.getStructureId());

			articleElement.addAttribute("structure-uuid", structure.getUuid());
		}

		if (Validator.isNotNull(article.getTemplateId())) {
			JournalTemplate template =
				JournalTemplateLocalServiceUtil.getTemplate(
					context.getScopeGroupId(), article.getTemplateId());

			articleElement.addAttribute("template-uuid", template.getUuid());
		}

		Image smallImage = ImageUtil.fetchByPrimaryKey(
			article.getSmallImageId());

		if (article.isSmallImage() && (smallImage != null)) {
			String smallImagePath = getArticleSmallImagePath(context, article);

			articleElement.addAttribute("small-image-path", smallImagePath);

			article.setSmallImageType(smallImage.getType());

			context.addZipEntry(smallImagePath, smallImage.getTextObj());
		}

		if (context.getBooleanParameter(_NAMESPACE, "images")) {
			String imagePath = getArticleImagePath(context, article);

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
					context, article, articleImage, image);

				if (!context.isPathNotProcessed(articleImagePath)) {
					continue;
				}

				context.addZipEntry(articleImagePath, image.getTextObj());
			}
		}

		article.setStatusByUserUuid(article.getStatusByUserUuid());

		context.addPermissions(
			JournalArticle.class, article.getResourcePrimKey());

		if (context.getBooleanParameter(_NAMESPACE, "categories")) {
			context.addAssetCategories(
				JournalArticle.class, article.getResourcePrimKey());
		}

		if (context.getBooleanParameter(_NAMESPACE, "comments")) {
			context.addComments(
				JournalArticle.class, article.getResourcePrimKey());
		}

		if (context.getBooleanParameter(_NAMESPACE, "ratings")) {
			context.addRatingsEntries(
				JournalArticle.class, article.getResourcePrimKey());
		}

		if (context.getBooleanParameter(_NAMESPACE, "tags")) {
			context.addAssetTags(
				JournalArticle.class, article.getResourcePrimKey());
		}

		if (context.getBooleanParameter(_NAMESPACE, "embedded-assets")) {
			String content = article.getContent();

			content = exportDLFileEntries(
				context, dlFoldersElement, dlFileEntriesElement,
				dlFileRanksElement, articleElement, content);
			content = exportIGImages(
				context, igFoldersElement, igImagesElement, articleElement,
				content);
			content = exportLayoutFriendlyURLs(context, content);

			article.setContent(content);
		}

		context.addZipEntry(path, article);
	}

	protected static String exportDLFileEntries(
		PortletDataContext context, Element foldersElement,
		Element fileEntriesElement, Element fileRanksElement,
		Element entityElement, String content) {

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

			int endPos1 = content.indexOf(StringPool.APOSTROPHE, beginPos);
			int endPos2 = content.indexOf(StringPool.CLOSE_BRACKET, beginPos);
			int endPos3 = content.indexOf(
				StringPool.CLOSE_PARENTHESIS, beginPos);
			int endPos4 = content.indexOf(StringPool.LESS_THAN, beginPos);
			int endPos5 = content.indexOf(StringPool.QUOTE, beginPos);
			int endPos6 = content.indexOf(StringPool.SPACE, beginPos);

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
						oldParameters.indexOf(StringPool.QUESTION) + 1);

					map = MapUtil.toLinkedHashMap(
						oldParameters.split(StringPool.AMPERSAND),
						StringPool.EQUAL);
				}

				DLFileEntry fileEntry = null;

				if (map.containsKey("uuid")) {
					String uuid = map.get("uuid");

					String groupIdString = map.get("groupId");

					long groupId = GetterUtil.getLong(groupIdString);

					if (groupIdString.equals("@group_id@")) {
						groupId = context.getScopeGroupId();
					}

					fileEntry = DLFileEntryLocalServiceUtil.
						getFileEntryByUuidAndGroupId(uuid, groupId);
				}
				else if (map.containsKey("folderId")) {
					long folderId = GetterUtil.getLong(map.get("folderId"));
					String name = map.get("name");

					String groupIdString = map.get("groupId");

					long groupId = GetterUtil.getLong(groupIdString);

					if (groupIdString.equals("@group_id@")) {
						groupId = context.getScopeGroupId();
					}

					fileEntry = DLFileEntryLocalServiceUtil.getFileEntryByTitle(
						groupId, folderId, name);
				}

				if (fileEntry == null) {
					beginPos--;

					continue;
				}

				String path = DLPortletDataHandlerImpl.getFileEntryPath(
					context, fileEntry);

				Element dlReferenceElement = entityElement.addElement(
					"dl-reference");

				dlReferenceElement.addAttribute("path", path);

				DLPortletDataHandlerImpl.exportFileEntry(
					context, foldersElement, fileEntriesElement,
					fileRanksElement, fileEntry);

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
			PortletDataContext context, Element feedsElement, JournalFeed feed)
		throws Exception {

		if (!context.isWithinDateRange(feed.getModifiedDate())) {
			return;
		}

		String path = getFeedPath(context, feed);

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		Element feedElement = feedsElement.addElement("feed");

		feedElement.addAttribute("path", path);

		feed.setUserUuid(feed.getUserUuid());

		context.addPermissions(JournalFeed.class, feed.getId());

		context.addZipEntry(path, feed);
	}

	protected static String exportIGImages(
		PortletDataContext context, Element foldersElement,
		Element imagesElement, Element entityElement, String content) {

		StringBuilder sb = new StringBuilder(content);

		int beginPos = content.length();

		while (true) {
			beginPos = content.lastIndexOf("/image/image_gallery?", beginPos);

			if (beginPos == -1) {
				return sb.toString();
			}

			int endPos1 = content.indexOf(StringPool.APOSTROPHE, beginPos);
			int endPos2 = content.indexOf(StringPool.CLOSE_BRACKET, beginPos);
			int endPos3 = content.indexOf(
				StringPool.CLOSE_PARENTHESIS, beginPos);
			int endPos4 = content.indexOf(StringPool.LESS_THAN, beginPos);
			int endPos5 = content.indexOf(StringPool.QUOTE, beginPos);
			int endPos6 = content.indexOf(StringPool.SPACE, beginPos);

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
						groupId = context.getScopeGroupId();
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
					context, image);

				Element igReferenceElement = entityElement.addElement(
					"ig-reference");

				igReferenceElement.addAttribute("path", path);

				IGPortletDataHandlerImpl.exportImage(
					context, foldersElement, imagesElement, image);

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
		PortletDataContext context, String content) {

		Group group = null;

		try {
			group = GroupLocalServiceUtil.getGroup(context.getScopeGroupId());
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
				StringPool.APOSTROPHE, beginPos + hrefLength);
			int endPos2 = content.indexOf(
				StringPool.CLOSE_BRACKET, beginPos + hrefLength);
			int endPos3 = content.indexOf(
				StringPool.CLOSE_PARENTHESIS, beginPos + hrefLength);
			int endPos4 = content.indexOf(
				StringPool.LESS_THAN, beginPos + hrefLength);
			int endPos5 = content.indexOf(
				StringPool.QUOTE, beginPos + hrefLength);
			int endPos6 = content.indexOf(
				StringPool.SPACE, beginPos + hrefLength);

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
				StringPool.SLASH, beginPos + hrefLength + 1);

			if (beginGroupPos == -1) {
				beginPos--;

				continue;
			}

			int endGroupPos = content.indexOf(
				StringPool.SLASH, beginGroupPos + 1);

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

	protected static void exportStructure(
			PortletDataContext context, Element structuresElement,
			JournalStructure structure)
		throws Exception {

		String path = getStructurePath(context, structure);

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		Element structureElement = structuresElement.addElement("structure");

		structureElement.addAttribute("path", path);

		structure.setUserUuid(structure.getUserUuid());

		context.addPermissions(JournalStructure.class, structure.getId());

		context.addZipEntry(path, structure);
	}

	protected static void exportTemplate(
			PortletDataContext context, Element templatesElement,
			Element dlFoldersElement, Element dlFileEntriesElement,
			Element dlFileRanksElement, Element igFoldersElement,
			Element igImagesElement, JournalTemplate template)
		throws Exception {

		String path = getTemplatePath(context, template);

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		// Clone this template to make sure changes to its content are never
		// persisted

		template = (JournalTemplate)template.clone();

		Element templateElement = templatesElement.addElement("template");

		templateElement.addAttribute("path", path);

		if (template.isSmallImage()) {
			String smallImagePath = getTemplateSmallImagePath(
				context, template);

			templateElement.addAttribute("small-image-path", smallImagePath);

			Image smallImage = ImageUtil.fetchByPrimaryKey(
				template.getSmallImageId());

			template.setSmallImageType(smallImage.getType());

			context.addZipEntry(smallImagePath, smallImage.getTextObj());
		}

		if (context.getBooleanParameter(_NAMESPACE, "embedded-assets")) {
			String content = template.getXsl();

			content = exportDLFileEntries(
				context, dlFoldersElement, dlFileEntriesElement,
				dlFileRanksElement, templateElement, content);
			content = exportIGImages(
				context, igFoldersElement, igImagesElement, templateElement,
				content);
			content = exportLayoutFriendlyURLs(context, content);

			content = StringUtil.replace(
				content, StringPool.AMPERSAND_ENCODED, StringPool.AMPERSAND);

			template.setXsl(content);
		}

		template.setUserUuid(template.getUserUuid());

		context.addPermissions(JournalTemplate.class, template.getId());

		context.addZipEntry(path, template);
	}

	protected static String getArticleImagePath(
		PortletDataContext context, JournalArticle article) {

		StringBundler sb = new StringBundler(6);

		sb.append(context.getPortletPath(PortletKeys.JOURNAL));
		sb.append("/articles/");
		sb.append(article.getUuid());
		sb.append(StringPool.SLASH);
		sb.append(article.getVersion());
		sb.append(StringPool.SLASH);

		return sb.toString();
	}

	protected static String getArticleImagePath(
		PortletDataContext context, JournalArticle article,
		JournalArticleImage articleImage, Image image) {

		StringBundler sb = new StringBundler(13);

		sb.append(context.getPortletPath(PortletKeys.JOURNAL));
		sb.append("/articles/");
		sb.append(article.getUuid());
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
		PortletDataContext context, JournalArticle article) {

		StringBundler sb = new StringBundler(8);

		sb.append(context.getPortletPath(PortletKeys.JOURNAL));
		sb.append("/articles/");
		sb.append(article.getUuid());
		sb.append(StringPool.SLASH);
		sb.append(article.getVersion());
		sb.append(StringPool.SLASH);
		sb.append("article.xml");

		return sb.toString();
	}

	protected static String getArticleSmallImagePath(
			PortletDataContext context, JournalArticle article)
		throws Exception {

		StringBundler sb = new StringBundler(6);

		sb.append(context.getPortletPath(PortletKeys.JOURNAL));
		sb.append("/articles/");
		sb.append(article.getUuid());
		sb.append("/thumbnail");
		sb.append(StringPool.PERIOD);
		sb.append(article.getSmallImageType());

		return sb.toString();
	}

	protected static String getFeedPath(
		PortletDataContext context, JournalFeed feed) {

		StringBundler sb = new StringBundler(4);

		sb.append(context.getPortletPath(PortletKeys.JOURNAL));
		sb.append("/feeds/");
		sb.append(feed.getUuid());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getStructurePath(
		PortletDataContext context, JournalStructure structure) {

		StringBundler sb = new StringBundler(4);

		sb.append(context.getPortletPath(PortletKeys.JOURNAL));
		sb.append("/structures/");
		sb.append(structure.getUuid());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getTemplatePath(
		PortletDataContext context, JournalTemplate template) {

		StringBundler sb = new StringBundler(4);

		sb.append(context.getPortletPath(PortletKeys.JOURNAL));
		sb.append("/templates/");
		sb.append(template.getUuid());
		sb.append(".xml");

		return sb.toString();
	}

	protected static String getTemplateSmallImagePath(
			PortletDataContext context, JournalTemplate template)
		throws Exception {

		StringBundler sb = new StringBundler(5);

		sb.append(context.getPortletPath(PortletKeys.JOURNAL));
		sb.append("/templates/thumbnail-");
		sb.append(template.getUuid());
		sb.append(StringPool.PERIOD);
		sb.append(template.getSmallImageType());

		return sb.toString();
	}

	protected static void importArticle(
			PortletDataContext context, Element articleElement)
		throws Exception {

		String path = articleElement.attributeValue("path");

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		JournalArticle article = (JournalArticle)context.getZipEntryAsObject(
			path);

		long userId = context.getUserId(article.getUserUuid());

		User user = UserLocalServiceUtil.getUser(userId);

		String articleId = article.getArticleId();
		boolean autoArticleId = false;

		if ((Validator.isNumber(articleId)) ||
			(JournalArticleUtil.fetchByG_A_V(
				context.getScopeGroupId(), articleId,
					JournalArticleConstants.DEFAULT_VERSION) != null)) {

			autoArticleId = true;
		}

		Map<String, String> articleIds =
			(Map<String, String>)context.getNewPrimaryKeysMap(
				JournalArticle.class);

		String newArticleId = articleIds.get(articleId);

		if (Validator.isNotNull(newArticleId)) {

			// A sibling of a different version was already assigned a new
			// article id

			articleId = newArticleId;
			autoArticleId = false;
		}

		String content = article.getContent();

		content = importDLFileEntries(context, articleElement, content);
		content = importIGImages(context, articleElement, content);

		Group group = GroupLocalServiceUtil.getGroup(context.getScopeGroupId());

		content = StringUtil.replace(
			content, "@data_handler_group_friendly_url@",
			group.getFriendlyURL());

		article.setContent(content);

		Map<String, String> structureIds =
			(Map<String, String>)context.getNewPrimaryKeysMap(
				JournalStructure.class);

		String parentStructureId = MapUtil.getString(
			structureIds, article.getStructureId(), article.getStructureId());

		Map<String, String> templateIds =
			(Map<String, String>)context.getNewPrimaryKeysMap(
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
					structureUuid, context.getScopeGroupId());

			if (existingStructure == null) {
				String newStructureId = structureIds.get(
					article.getStructureId());

				if (Validator.isNotNull(newStructureId)) {
					existingStructure = JournalStructureUtil.fetchByG_S(
						context.getScopeGroupId(),
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
					templateUuid, context.getScopeGroupId());

			if (existingTemplate == null) {
				String newTemplateId = templateIds.get(article.getTemplateId());

				if (Validator.isNotNull(newTemplateId)) {
					existingTemplate = JournalTemplateUtil.fetchByG_T(
						context.getScopeGroupId(), newTemplateId);
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
			byte[] bytes = context.getZipEntryAsByteArray(smallImagePath);

			smallFile = File.createTempFile(
				String.valueOf(article.getSmallImageId()),
				StringPool.PERIOD + article.getSmallImageType());

			FileUtil.write(smallFile, bytes);
		}

		Map<String, byte[]> images = new HashMap<String, byte[]>();

		String imagePath = articleElement.attributeValue("image-path");

		if (context.getBooleanParameter(_NAMESPACE, "images") &&
			Validator.isNotNull(imagePath)) {

			List<String> imageFiles = context.getZipFolderEntries(imagePath);

			for (String imageFile : imageFiles) {
				String fileName = imageFile;

				if (fileName.contains(StringPool.SLASH)) {
					fileName = fileName.substring(
						fileName.lastIndexOf(StringPool.SLASH) + 1);
				}

				if (fileName.endsWith(".xml")) {
					continue;
				}

				int pos = fileName.lastIndexOf(StringPool.PERIOD);

				if (pos != -1) {
					fileName = fileName.substring(0, pos);
				}

				images.put(fileName, context.getZipEntryAsByteArray(imageFile));
			}
		}

		String articleURL = null;

		long[] assetCategoryIds = null;
		String[] assetTagNames = null;

		if (context.getBooleanParameter(_NAMESPACE, "categories")) {
			assetCategoryIds = context.getAssetCategoryIds(
				JournalArticle.class, article.getResourcePrimKey());
		}

		if (context.getBooleanParameter(_NAMESPACE, "tags")) {
			assetTagNames = context.getAssetTagNames(
				JournalArticle.class, article.getResourcePrimKey());
		}

		JournalCreationStrategy creationStrategy =
			JournalCreationStrategyFactory.getInstance();

		long authorId = creationStrategy.getAuthorUserId(context, article);

		if (authorId != JournalCreationStrategy.USE_DEFAULT_USER_ID_STRATEGY) {
			userId = authorId;
		}

		String newContent = creationStrategy.getTransformedContent(
			context, article);

		if (newContent != JournalCreationStrategy.ARTICLE_CONTENT_UNCHANGED) {
			article.setContent(newContent);
		}

		boolean addCommunityPermissions =
			creationStrategy.addCommunityPermissions(context, article);
		boolean addGuestPermissions = creationStrategy.addGuestPermissions(
			context, article);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(addCommunityPermissions);
		serviceContext.setAddGuestPermissions(addGuestPermissions);
		serviceContext.setAssetCategoryIds(assetCategoryIds);
		serviceContext.setAssetTagNames(assetTagNames);
		serviceContext.setAttribute("imported", Boolean.TRUE.toString());
		serviceContext.setCreateDate(article.getCreateDate());
		serviceContext.setModifiedDate(article.getModifiedDate());
		serviceContext.setScopeGroupId(context.getScopeGroupId());

		if (article.getStatus() != WorkflowConstants.STATUS_APPROVED) {
			serviceContext.setWorkflowAction(
				WorkflowConstants.ACTION_SAVE_DRAFT);
		}

		JournalArticle importedArticle = null;

		if (context.isDataStrategyMirror()) {
			JournalArticle existingArticle = JournalArticleUtil.fetchByUUID_G(
				article.getUuid(), context.getScopeGroupId());

			if (existingArticle == null) {
				existingArticle = JournalArticleUtil.fetchByG_A_V(
					context.getScopeGroupId(), newArticleId,
					article.getVersion());
			}

			if (existingArticle == null) {
				serviceContext.setUuid(article.getUuid());

				importedArticle = JournalArticleLocalServiceUtil.addArticle(
					userId, context.getScopeGroupId(), articleId, autoArticleId,
					article.getVersion(), article.getTitle(),
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
					existingArticle.getArticleId(), article.getVersion(),
					article.getTitle(), article.getDescription(),
					article.getContent(), article.getType(), parentStructureId,
					parentTemplateId, displayDateMonth, displayDateDay,
					displayDateYear, displayDateHour, displayDateMinute,
					expirationDateMonth, expirationDateDay, expirationDateYear,
					expirationDateHour, expirationDateMinute, neverExpire,
					reviewDateMonth, reviewDateDay, reviewDateYear,
					reviewDateHour, reviewDateMinute, neverReview,
					article.isIndexable(), article.isSmallImage(),
					article.getSmallImageURL(), smallFile, images, articleURL,
					serviceContext);
			}
		}
		else {
			importedArticle = JournalArticleLocalServiceUtil.addArticle(
				userId, context.getScopeGroupId(), articleId, autoArticleId,
				article.getVersion(), article.getTitle(),
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

		context.importPermissions(
			JournalArticle.class, article.getResourcePrimKey(),
			importedArticle.getResourcePrimKey());

		if (context.getBooleanParameter(_NAMESPACE, "comments")) {
			context.importComments(
				JournalArticle.class, article.getResourcePrimKey(),
				importedArticle.getResourcePrimKey(),
				context.getScopeGroupId());
		}

		if (context.getBooleanParameter(_NAMESPACE, "ratings")) {
			context.importRatingsEntries(
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
			PortletDataContext context, Element parentElement, String content)
		throws Exception {

		Map<Long, Long> fileEntryPKs =
			(Map<Long, Long>)context.getNewPrimaryKeysMap(DLFileEntry.class);

		List<Element> dlReferenceElements = parentElement.elements(
			"dl-reference");

		for (Element dlReferenceElement : dlReferenceElements) {
			String dlReferencePath = dlReferenceElement.attributeValue("path");

			DLFileEntry fileEntry = (DLFileEntry)context.getZipEntryAsObject(
				dlReferencePath);

			if (fileEntry == null) {
				continue;
			}

			long fileEntryId = MapUtil.getLong(
				fileEntryPKs, fileEntry.getFileEntryId(),
				fileEntry.getFileEntryId());

			fileEntry = DLFileEntryUtil.fetchByPrimaryKey(fileEntryId);

			if (fileEntry == null) {
				continue;
			}

			String dlReference = "[$dl-reference=" + dlReferencePath + "$]";

			StringBundler sb = new StringBundler(6);

			sb.append("/documents/");
			sb.append(context.getScopeGroupId());
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
			PortletDataContext context, Element feedElement)
		throws Exception {

		String path = feedElement.attributeValue("path");

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		JournalFeed feed = (JournalFeed)context.getZipEntryAsObject(path);

		long userId = context.getUserId(feed.getUserUuid());

		String feedId = feed.getFeedId();
		boolean autoFeedId = false;

		if ((Validator.isNumber(feedId)) ||
			(JournalFeedUtil.fetchByG_F(
				context.getScopeGroupId(), feedId) != null)) {

			autoFeedId = true;
		}

		Map<String, String> structureIds =
			(Map<String, String>)context.getNewPrimaryKeysMap(
				JournalStructure.class);

		String parentStructureId = MapUtil.getString(
			structureIds, feed.getStructureId(), feed.getStructureId());

		Map<String, String> templateIds =
			(Map<String, String>)context.getNewPrimaryKeysMap(
				JournalTemplate.class);

		String parentTemplateId = MapUtil.getString(
			templateIds, feed.getTemplateId(), feed.getTemplateId());
		String parentRenderTemplateId = MapUtil.getString(
			templateIds, feed.getRendererTemplateId(),
			feed.getRendererTemplateId());

		JournalCreationStrategy creationStrategy =
			JournalCreationStrategyFactory.getInstance();

		long authorId = creationStrategy.getAuthorUserId(context, feed);

		if (authorId != JournalCreationStrategy.USE_DEFAULT_USER_ID_STRATEGY) {
			userId = authorId;
		}

		boolean addCommunityPermissions =
			creationStrategy.addCommunityPermissions(context, feed);
		boolean addGuestPermissions = creationStrategy.addGuestPermissions(
			context, feed);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(addCommunityPermissions);
		serviceContext.setAddGuestPermissions(addGuestPermissions);
		serviceContext.setCreateDate(feed.getCreateDate());
		serviceContext.setModifiedDate(feed.getModifiedDate());

		JournalFeed existingFeed = null;

		if (context.isDataStrategyMirror()) {
			existingFeed = JournalFeedUtil.fetchByUUID_G(
				feed.getUuid(), context.getScopeGroupId());

			if (existingFeed == null) {
				serviceContext.setUuid(feed.getUuid());

				existingFeed = JournalFeedLocalServiceUtil.addFeed(
					userId, context.getScopeGroupId(), feedId, autoFeedId,
					feed.getName(), feed.getDescription(), feed.getType(),
					parentStructureId, parentTemplateId, parentRenderTemplateId,
					feed.getDelta(), feed.getOrderByCol(),
					feed.getOrderByType(), feed.getTargetLayoutFriendlyUrl(),
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
				userId, context.getScopeGroupId(), feedId, autoFeedId,
				feed.getName(), feed.getDescription(), feed.getType(),
				parentStructureId, parentTemplateId, parentRenderTemplateId,
				feed.getDelta(), feed.getOrderByCol(), feed.getOrderByType(),
				feed.getTargetLayoutFriendlyUrl(), feed.getTargetPortletId(),
				feed.getContentField(), feed.getFeedType(),
				feed.getFeedVersion(), serviceContext);
		}

		Map<String, String> feedIds =
			(Map<String, String>)context.getNewPrimaryKeysMap(
				JournalFeed.class);

		feedIds.put(feedId, existingFeed.getFeedId());

		context.importPermissions(
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
			PortletDataContext context, Element parentElement, String content)
		throws Exception {

		Map<Long, Long> imagePKs =
			(Map<Long, Long>)context.getNewPrimaryKeysMap(IGImage.class);

		List<Element> igReferenceElements = parentElement.elements(
			"ig-reference");

		for (Element igReferenceElement : igReferenceElements) {
			String igReferencePath = igReferenceElement.attributeValue("path");

			IGImage image = (IGImage)context.getZipEntryAsObject(
				igReferencePath);

			if (image == null) {
				continue;
			}

			long imageId = MapUtil.getLong(
				imagePKs, image.getImageId(), image.getImageId());

			image = IGImageUtil.fetchByPrimaryKey(imageId);

			if (image == null) {
				continue;
			}

			String igReference = "[$ig-reference=" + igReferencePath + "$]";

			StringBundler sb = new StringBundler(6);

			sb.append("/image/image_gallery?uuid=");
			sb.append(image.getUuid());
			sb.append("&groupId=");
			sb.append(context.getScopeGroupId());
			sb.append("&t=");
			sb.append(System.currentTimeMillis());

			content = StringUtil.replace(content, igReference, sb.toString());
		}

		return content;
	}

	protected static void importStructure(
			PortletDataContext context,	Element structureElement)
		throws Exception {

		String path = structureElement.attributeValue("path");

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		JournalStructure structure =
			(JournalStructure)context.getZipEntryAsObject(path);

		long userId = context.getUserId(structure.getUserUuid());

		String structureId = structure.getStructureId();
		boolean autoStructureId = false;

		if ((Validator.isNumber(structureId)) ||
			(JournalStructureUtil.fetchByG_S(
				context.getScopeGroupId(), structureId) != null)) {

			autoStructureId = true;
		}

		JournalCreationStrategy creationStrategy =
			JournalCreationStrategyFactory.getInstance();

		long authorId = creationStrategy.getAuthorUserId(context, structure);

		if (authorId != JournalCreationStrategy.USE_DEFAULT_USER_ID_STRATEGY) {
			userId = authorId;
		}

		boolean addCommunityPermissions =
			creationStrategy.addCommunityPermissions(context, structure);
		boolean addGuestPermissions = creationStrategy.addGuestPermissions(
			context, structure);

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddCommunityPermissions(addCommunityPermissions);
		serviceContext.setAddGuestPermissions(addGuestPermissions);
		serviceContext.setCreateDate(structure.getCreateDate());
		serviceContext.setModifiedDate(structure.getModifiedDate());

		JournalStructure existingStructure = null;

		if (context.isDataStrategyMirror()) {
			existingStructure = JournalStructureUtil.fetchByUUID_G(
				structure.getUuid(), context.getScopeGroupId());

			if (existingStructure == null) {
				serviceContext.setUuid(structure.getUuid());

				existingStructure =
					JournalStructureLocalServiceUtil.addStructure(
						userId, context.getScopeGroupId(), structureId,
						autoStructureId, structure.getParentStructureId(),
						structure.getName(), structure.getDescription(),
						structure.getXsd(), serviceContext);
			}
			else {
				existingStructure =
					JournalStructureLocalServiceUtil.updateStructure(
						existingStructure.getGroupId(),
						existingStructure.getStructureId(),
						structure.getParentStructureId(), structure.getName(),
						structure.getDescription(), structure.getXsd(),
						serviceContext);
			}
		}
		else {
			existingStructure = JournalStructureLocalServiceUtil.addStructure(
				userId, context.getScopeGroupId(), structureId, autoStructureId,
				structure.getParentStructureId(), structure.getName(),
				structure.getDescription(), structure.getXsd(), serviceContext);
		}

		Map<String, String> structureIds =
			(Map<String, String>)context.getNewPrimaryKeysMap(
				JournalStructure.class);

		structureIds.put(structureId, existingStructure.getStructureId());

		context.importPermissions(
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
			PortletDataContext context, Element templateElement)
		throws Exception {

		String path = templateElement.attributeValue("path");

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		JournalTemplate template = (JournalTemplate)context.getZipEntryAsObject(
			path);

		long userId = context.getUserId(template.getUserUuid());

		String templateId = template.getTemplateId();
		boolean autoTemplateId = false;

		if ((Validator.isNumber(templateId)) ||
			(JournalTemplateUtil.fetchByG_T(
				context.getScopeGroupId(), templateId) != null)) {

			autoTemplateId = true;
		}

		Map<String, String> structureIds =
			(Map<String, String>)context.getNewPrimaryKeysMap(
				JournalStructure.class);

		String parentStructureId = MapUtil.getString(
			structureIds, template.getStructureId(), template.getStructureId());

		String xsl = template.getXsl();

		xsl = importDLFileEntries(context, templateElement, xsl);
		xsl = importIGImages(context, templateElement, xsl);

		Group group = GroupLocalServiceUtil.getGroup(context.getScopeGroupId());

		xsl = StringUtil.replace(
			xsl, "@data_handler_group_friendly_url@", group.getFriendlyURL());

		template.setXsl(xsl);

		boolean formatXsl = false;

		JournalCreationStrategy creationStrategy =
			JournalCreationStrategyFactory.getInstance();

		long authorId = creationStrategy.getAuthorUserId(context, template);

		if (authorId != JournalCreationStrategy.USE_DEFAULT_USER_ID_STRATEGY) {
			userId = authorId;
		}

		boolean addCommunityPermissions =
			creationStrategy.addCommunityPermissions(context, template);
		boolean addGuestPermissions = creationStrategy.addGuestPermissions(
			context, template);

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

			byte[] bytes = context.getZipEntryAsByteArray(smallImagePath);

			if (bytes != null) {
				smallFile = File.createTempFile(
					String.valueOf(template.getSmallImageId()),
					StringPool.PERIOD + template.getSmallImageType());

				FileUtil.write(smallFile, bytes);
			}
		}

		JournalTemplate existingTemplate = null;

		if (context.isDataStrategyMirror()) {
			existingTemplate = JournalTemplateUtil.fetchByUUID_G(
				template.getUuid(), context.getScopeGroupId());

			if (existingTemplate == null) {
				serviceContext.setUuid(template.getUuid());

				existingTemplate = JournalTemplateLocalServiceUtil.addTemplate(
					userId, context.getScopeGroupId(), templateId,
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
				userId, context.getScopeGroupId(), templateId, autoTemplateId,
				parentStructureId, template.getName(),
				template.getDescription(), template.getXsl(), formatXsl,
				template.getLangType(), template.getCacheable(),
				template.isSmallImage(), template.getSmallImageURL(), smallFile,
				serviceContext);
		}

		Map<String, String> templateIds =
			(Map<String, String>)context.getNewPrimaryKeysMap(
				JournalTemplate.class);

		templateIds.put(templateId, existingTemplate.getTemplateId());

		context.importPermissions(
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

	protected PortletPreferences doDeleteData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws Exception {

		if (!context.addPrimaryKey(
				JournalPortletDataHandlerImpl.class, "deleteData")) {

			JournalArticleLocalServiceUtil.deleteArticles(
				context.getScopeGroupId());

			JournalTemplateLocalServiceUtil.deleteTemplates(
				context.getScopeGroupId());

			JournalStructureLocalServiceUtil.deleteStructures(
				context.getScopeGroupId());
		}

		return preferences;
	}

	protected String doExportData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences)
		throws Exception {

		context.addPermissions(
			"com.liferay.portlet.journal", context.getScopeGroupId());

		Document document = SAXReaderUtil.createDocument();

		Element rootElement = document.addElement("journal-data");

		rootElement.addAttribute(
			"group-id", String.valueOf(context.getScopeGroupId()));

		Element structuresElement = rootElement.addElement("structures");

		List<JournalStructure> structures =
			JournalStructureUtil.findByGroupId(context.getScopeGroupId());

		for (JournalStructure structure : structures) {
			if (context.isWithinDateRange(structure.getModifiedDate())) {
				exportStructure(context, structuresElement, structure);
			}
		}

		Element templatesElement = rootElement.addElement("templates");
		Element dlFoldersElement = rootElement.addElement("dl-folders");
		Element dlFilesElement = rootElement.addElement("dl-file-entries");
		Element dlFileRanksElement = rootElement.addElement("dl-file-ranks");
		Element igFoldersElement = rootElement.addElement("ig-folders");
		Element igImagesElement = rootElement.addElement("ig-images");

		List<JournalTemplate> templates = JournalTemplateUtil.findByGroupId(
			context.getScopeGroupId());

		for (JournalTemplate template : templates) {
			if (context.isWithinDateRange(template.getModifiedDate())) {
				exportTemplate(
					context, templatesElement, dlFoldersElement,
					dlFilesElement, dlFileRanksElement, igFoldersElement,
					igImagesElement, template);
			}
		}

		Element feedsElement = rootElement.addElement("feeds");

		List<JournalFeed> feeds = JournalFeedUtil.findByGroupId(
			context.getScopeGroupId());

		for (JournalFeed feed : feeds) {
			if (context.isWithinDateRange(feed.getModifiedDate())) {
				exportFeed(context, feedsElement, feed);
			}
		}

		Element articlesElement = rootElement.addElement("articles");

		if (context.getBooleanParameter(_NAMESPACE, "articles")) {
			List<JournalArticle> articles = JournalArticleUtil.findByG_ST(
				context.getScopeGroupId(), WorkflowConstants.STATUS_APPROVED,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				new ArticleIDComparator(true));

			for (JournalArticle article : articles) {
				exportArticle(
					context, articlesElement, dlFoldersElement, dlFilesElement,
					dlFileRanksElement, igFoldersElement, igImagesElement,
					article, true);
			}
		}

		return document.formattedString();
	}

	protected PortletPreferences doImportData(
			PortletDataContext context, String portletId,
			PortletPreferences preferences, String data)
		throws Exception {

		context.importPermissions(
			"com.liferay.portlet.journal", context.getSourceGroupId(),
			context.getScopeGroupId());

		Document document = SAXReaderUtil.read(data);

		Element rootElement = document.getRootElement();

		Element dlFoldersElement = rootElement.element("dl-folders");

		List<Element> dlFolderElements = dlFoldersElement.elements("folder");

		for (Element dlFolderElement : dlFolderElements) {
			DLPortletDataHandlerImpl.importFolder(context, dlFolderElement);
		}

		Element dlFileEntriesElement = rootElement.element("dl-file-entries");

		List<Element> dlFileEntryElements = dlFileEntriesElement.elements(
			"file-entry");

		for (Element dlFileEntryElement : dlFileEntryElements) {
			DLPortletDataHandlerImpl.importFileEntry(
				context, dlFileEntryElement);
		}

		Element dlFileRanksElement = rootElement.element("dl-file-ranks");

		List<Element> dlFileRankElements = dlFileRanksElement.elements(
			"file-rank");

		for (Element dlFileRankElement : dlFileRankElements) {
			DLPortletDataHandlerImpl.importFileRank(context, dlFileRankElement);
		}

		Element igFoldersElement = rootElement.element("ig-folders");

		List<Element> igFolderElements = igFoldersElement.elements("folder");

		for (Element igFolderElement : igFolderElements) {
			IGPortletDataHandlerImpl.importFolder(context, igFolderElement);
		}

		Element igImagesElement = rootElement.element("ig-images");

		List<Element> igImageElements = igImagesElement.elements("image");

		for (Element igImageElement : igImageElements) {
			IGPortletDataHandlerImpl.importImage(context, igImageElement);
		}

		Element structuresElement = rootElement.element("structures");

		List<Element> structureElements = structuresElement.elements(
			"structure");

		for (Element structureElement : structureElements) {
			importStructure(context, structureElement);
		}

		Element templatesElement = rootElement.element("templates");

		List<Element> templateElements = templatesElement.elements("template");

		for (Element templateElement : templateElements) {
			importTemplate(context, templateElement);
		}

		Element feedsElement = rootElement.element("feeds");

		List<Element> feedElements = feedsElement.elements("feed");

		for (Element feedElement : feedElements) {
			importFeed(context, feedElement);
		}

		if (context.getBooleanParameter(_NAMESPACE, "articles")) {
			Element articlesElement = rootElement.element("articles");

			List<Element> articleElements = articlesElement.elements("article");

			for (Element articleElement : articleElements) {
				importArticle(context, articleElement);
			}
		}

		return preferences;
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

}