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
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.kernel.xml.Document;
import com.liferay.portal.kernel.xml.Element;
import com.liferay.portal.kernel.xml.SAXReaderUtil;
import com.liferay.portal.lar.PortletDataHandlerUtil;
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
import com.liferay.portlet.imagegallery.lar.IGPortletDataHandlerImpl;
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
 * @author Hugo Huijser
 * @see    com.liferay.portal.kernel.lar.PortletDataHandler
 * @see    com.liferay.portlet.journal.lar.JournalContentPortletDataHandlerImpl
 * @see    com.liferay.portlet.journal.lar.JournalCreationStrategy
 */
public class JournalPortletDataHandlerImpl extends BasePortletDataHandler {

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

			String content = article.getContent();

			content = PortletDataHandlerUtil.exportDLFileEntries(
				portletDataContext, dlFoldersElement, dlFileEntriesElement,
				dlFileRanksElement, articleElement, content, checkDateRange);
			content = PortletDataHandlerUtil.exportIGImages(
				portletDataContext, igFoldersElement, igImagesElement,
				articleElement, content, checkDateRange);
			content = PortletDataHandlerUtil.exportLayoutFriendlyURLs(
				portletDataContext, content);
			content = PortletDataHandlerUtil.exportLinksToLayout(
				portletDataContext, content);

			article.setContent(content);
		}

		portletDataContext.addZipEntry(path, article);
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

		structure.setUserUuid(structure.getUserUuid());

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

			String content = template.getXsl();

			content = PortletDataHandlerUtil.exportDLFileEntries(
				portletDataContext, dlFoldersElement, dlFileEntriesElement,
				dlFileRanksElement, templateElement, content, checkDateRange);
			content = PortletDataHandlerUtil.exportIGImages(
				portletDataContext, igFoldersElement, igImagesElement,
				templateElement, content, checkDateRange);
			content = PortletDataHandlerUtil.exportLayoutFriendlyURLs(
				portletDataContext, content);

			content = StringUtil.replace(
				content, StringPool.AMPERSAND_ENCODED, StringPool.AMPERSAND);

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

		content = PortletDataHandlerUtil.importDLFileEntries(
			portletDataContext, articleElement, content);
		content = PortletDataHandlerUtil.importIGImages(
			portletDataContext, articleElement, content);

		Group group = GroupLocalServiceUtil.getGroup(
			portletDataContext.getScopeGroupId());

		content = StringUtil.replace(
			content, "@data_handler_group_friendly_url@",
			group.getFriendlyURL());

		content = PortletDataHandlerUtil.importLinksToLayout(
			portletDataContext, content);

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

	protected static void importStructure(
			PortletDataContext portletDataContext,	Element structureElement)
		throws Exception {

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

		xsl = PortletDataHandlerUtil.importDLFileEntries(
			portletDataContext, templateElement, xsl);
		xsl = PortletDataHandlerUtil.importIGImages(
			portletDataContext, templateElement, xsl);

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
				portletDataContext.getScopeGroupId());

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

		Element dlFoldersElement = rootElement.element("dl-folders");

		List<Element> dlFolderElements = dlFoldersElement.elements("folder");

		for (Element dlFolderElement : dlFolderElements) {
			DLPortletDataHandlerImpl.importFolder(
				portletDataContext, dlFolderElement);
		}

		Element dlFileEntriesElement = rootElement.element("dl-file-entries");

		List<Element> dlFileEntryElements = dlFileEntriesElement.elements(
			"file-entry");

		for (Element dlFileEntryElement : dlFileEntryElements) {
			DLPortletDataHandlerImpl.importFileEntry(
				portletDataContext, dlFileEntryElement);
		}

		Element dlFileRanksElement = rootElement.element("dl-file-ranks");

		List<Element> dlFileRankElements = dlFileRanksElement.elements(
			"file-rank");

		for (Element dlFileRankElement : dlFileRankElements) {
			DLPortletDataHandlerImpl.importFileRank(
				portletDataContext, dlFileRankElement);
		}

		Element igFoldersElement = rootElement.element("ig-folders");

		List<Element> igFolderElements = igFoldersElement.elements("folder");

		for (Element igFolderElement : igFolderElements) {
			IGPortletDataHandlerImpl.importFolder(
				portletDataContext, igFolderElement);
		}

		Element igImagesElement = rootElement.element("ig-images");

		List<Element> igImageElements = igImagesElement.elements("image");

		for (Element igImageElement : igImageElements) {
			IGPortletDataHandlerImpl.importImage(
				portletDataContext, igImageElement);
		}

		Element structuresElement = rootElement.element("structures");

		List<Element> structureElements = structuresElement.elements(
			"structure");

		for (Element structureElement : structureElements) {
			importStructure(portletDataContext, structureElement);
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

}