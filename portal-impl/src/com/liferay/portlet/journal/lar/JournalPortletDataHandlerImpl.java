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

package com.liferay.portlet.journal.lar;

import com.liferay.portal.NoSuchImageException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.lar.PortletDataContext;
import com.liferay.portal.kernel.lar.PortletDataException;
import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.lar.PortletDataHandlerBoolean;
import com.liferay.portal.kernel.lar.PortletDataHandlerControl;
import com.liferay.portal.kernel.lar.PortletDataHandlerKeys;
import com.liferay.portal.kernel.util.CalendarFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.StringMaker;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Image;
import com.liferay.portal.service.persistence.ImageUtil;
import com.liferay.portal.util.DocumentUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleImage;
import com.liferay.portlet.journal.model.JournalFeed;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.model.impl.JournalArticleImpl;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalFeedLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalStructureLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalTemplateLocalServiceUtil;
import com.liferay.portlet.journal.service.persistence.JournalArticleImageUtil;
import com.liferay.portlet.journal.service.persistence.JournalArticleUtil;
import com.liferay.portlet.journal.service.persistence.JournalFeedUtil;
import com.liferay.portlet.journal.service.persistence.JournalStructureUtil;
import com.liferay.portlet.journal.service.persistence.JournalTemplateUtil;
import com.liferay.util.MapUtil;
import com.liferay.util.xml.XMLFormatter;

import java.io.File;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.portlet.PortletPreferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * <a href="JournalPortletDataHandlerImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * <p>
 * Provides the Journal portlet export and import functionality, which is to
 * clone all articles, structures, and templates associated with the layout's
 * group. Upon import, new instances of the corresponding articles, structures,
 * and templates are created or updated according to the DATA_MIRROW strategy
 * The author of the newly created objects are determined by the
 * JournalCreationStrategy class defined in <i>portal.properties</i>. That
 * strategy also allows the text of the journal article to be modified prior
 * to import.
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
 *
 * @see com.liferay.portal.kernel.lar.PortletDataHandler
 * @see com.liferay.portlet.journal.lar.JournalContentPortletDataHandlerImpl
 * @see com.liferay.portlet.journal.lar.JournalCreationStrategy
 *
 */
public class JournalPortletDataHandlerImpl implements PortletDataHandler {

	public static void exportArticle(
			PortletDataContext context, Element articlesEl,
			JournalArticle article)
		throws PortalException, SystemException {

		if (!context.isWithinDateRange(article.getModifiedDate())) {
			return;
		}

		String path = getArticlePath(context, article);

		Element articleEl = articlesEl.addElement("article");

		articleEl.addAttribute("path", path);

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		if (article.isSmallImage()) {
			Image smallImage = ImageUtil.fetchByPrimaryKey(
				article.getSmallImageId());

			article.setSmallImageType(smallImage.getType());

			context.addZipEntry(
				getArticleSmallImagePath(context, article),
				smallImage.getTextObj());
		}

		if (context.getBooleanParameter(_NAMESPACE, "images")) {
			List<JournalArticleImage> articleImages =
				JournalArticleImageUtil.findByG_A_V(
					context.getGroupId(), article.getArticleId(),
					article.getVersion());

			for (JournalArticleImage articleImage : articleImages) {
				try {
					Image image = ImageUtil.findByPrimaryKey(
						articleImage.getArticleImageId());

					String imagePath = getArticleImagePath(
						context, article, articleImage, image);

					context.addZipEntry(imagePath, image.getTextObj());
				}
				catch (NoSuchImageException nsie) {
				}
			}
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
			context.addTagsEntries(
				JournalArticle.class, article.getResourcePrimKey());
		}

		article.setUserUuid(article.getUserUuid());
		article.setApprovedByUserUuid(article.getApprovedByUserUuid());

		context.addZipEntry(path, article);
	}

	protected void exportFeed(
			PortletDataContext context, Element feedsEl, JournalFeed feed)
		throws SystemException {

		if (!context.isWithinDateRange(feed.getModifiedDate())) {
			return;
		}

		String path = getFeedPath(context, feed);

		Element feedEl = feedsEl.addElement("feed");

		feedEl.addAttribute("path", path);

		if (context.isPathNotProcessed(path)) {
			feed.setUserUuid(feed.getUserUuid());

			context.addZipEntry(path, feed);
		}
	}

	public static void exportStructure(
			PortletDataContext context, Element structuresEl,
			JournalStructure structure)
		throws SystemException {

		if (!context.isWithinDateRange(structure.getModifiedDate())) {
			return;
		}

		String path = getStructurePath(context, structure);

		Element structureEl = structuresEl.addElement("structure");

		structureEl.addAttribute("path", path);

		if (context.isPathNotProcessed(path)) {
			structure.setUserUuid(structure.getUserUuid());

			context.addZipEntry(path, structure);
		}
	}

	public static void exportTemplate(
			PortletDataContext context, Element templatesEl,
			JournalTemplate template)
		throws PortalException, SystemException {

		if (!context.isWithinDateRange(template.getModifiedDate())) {
			return;
		}

		String path = getTemplatePath(context, template);

		Element templateEl = templatesEl.addElement("template");

		templateEl.addAttribute("path", path);

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		if (template.isSmallImage()) {
			Image smallImage = ImageUtil.fetchByPrimaryKey(
				template.getSmallImageId());

			template.setSmallImageType(smallImage.getType());

			context.addZipEntry(
				getTemplateSmallImagePath(context, template),
				smallImage.getTextObj());
		}

		template.setUserUuid(template.getUserUuid());

		context.addZipEntry(path, template);
	}

	public static void importArticle(
			PortletDataContext context, Map<String, String> structureIds,
			Map<String, String> templateIds, Map<String, String> articleIds,
			Element articleEl)
		throws Exception {

		String path = articleEl.attributeValue("path");

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		JournalArticle article = (JournalArticle)context.getZipEntryAsObject(
			path);

		long userId = context.getUserId(article.getUserUuid());
		long plid = context.getPlid();

		String articleId = article.getArticleId();
		boolean autoArticleId = false;

		if ((Validator.isNumber(articleId)) ||
			(JournalArticleUtil.fetchByG_A_V(
				context.getGroupId(), articleId,
					JournalArticleImpl.DEFAULT_VERSION) != null)) {

			autoArticleId = true;
		}

		String newArticleId = articleIds.get(articleId);

		if (Validator.isNotNull(newArticleId)) {

			// A sibling of a different version was already assigned a new
			// article id

			articleId = newArticleId;
			autoArticleId = false;
		}

		boolean incrementVersion = false;

		String parentStructureId = MapUtil.getString(
			structureIds, article.getStructureId(), article.getStructureId());
		String parentTemplateId = MapUtil.getString(
			templateIds, article.getTemplateId(), article.getTemplateId());

		Date displayDate = article.getDisplayDate();

		int displayDateMonth = 0;
		int displayDateDay = 0;
		int displayDateYear = 0;
		int displayDateHour = 0;
		int displayDateMinute = 0;

		if (displayDate != null) {
			Calendar displayCal = CalendarFactoryUtil.getCalendar();

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
			Calendar expirationCal = CalendarFactoryUtil.getCalendar();

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
			Calendar reviewCal = CalendarFactoryUtil.getCalendar();

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

		File smallFile = null;

		if (article.isSmallImage()) {
			byte[] bytes = context.getZipEntryAsByteArray(
				getArticleSmallImagePath(context, article));

			smallFile = File.createTempFile(
				String.valueOf(article.getSmallImageId()),
				StringPool.PERIOD + article.getSmallImageType());

			FileUtil.write(smallFile, bytes);
		}

		Map<String, byte[]> images = new HashMap<String, byte[]>();

		if (context.getBooleanParameter(_NAMESPACE, "images")) {
			List<ObjectValuePair<String, byte[]>> imageFiles =
				context.getZipFolderEntries(
					getArticleImagePath(context, article));

			if (imageFiles != null) {
				for (ObjectValuePair<String, byte[]> imageFile : imageFiles) {
					String fileName = imageFile.getKey();

					if (!fileName.endsWith(".xml")) {
						int pos = fileName.lastIndexOf(StringPool.PERIOD);

						if (pos != -1) {
							fileName = fileName.substring(0, pos);
						}

						images.put(fileName, imageFile.getValue());
					}
				}
			}
		}

		String articleURL = null;

		PortletPreferences prefs = null;

		String[] tagsEntries = null;

		if (context.getBooleanParameter(_NAMESPACE, "tags")) {
			tagsEntries = context.getTagsEntries(
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

		JournalArticle existingArticle = null;

		if (context.getDataStrategy().equals(
				PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {

			existingArticle = JournalArticleUtil.fetchByUUID_G(
				article.getUuid(), context.getGroupId());

			if (existingArticle == null) {
				existingArticle = JournalArticleLocalServiceUtil.addArticle(
					article.getUuid(), userId, articleId, autoArticleId,
					plid, article.getVersion(), article.getTitle(),
					article.getDescription(), article.getContent(),
					article.getType(), parentStructureId, parentTemplateId,
					displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, expirationDateMonth,
					expirationDateDay, expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, reviewDateMonth,
					reviewDateDay, reviewDateYear, reviewDateHour,
					reviewDateMinute, neverReview, article.getIndexable(),
					article.getSmallImage(), article.getSmallImageURL(),
					smallFile, images, articleURL, prefs, tagsEntries,
					addCommunityPermissions, addGuestPermissions);
			}
			else {
				existingArticle = JournalArticleLocalServiceUtil.updateArticle(
					userId, existingArticle.getGroupId(),
					existingArticle.getArticleId(),
					existingArticle.getVersion(), incrementVersion,
					article.getTitle(), article.getDescription(),
					article.getContent(), article.getType(),
					existingArticle.getStructureId(),
					existingArticle.getTemplateId(), displayDateMonth,
					displayDateDay, displayDateYear, displayDateHour,
					displayDateMinute, expirationDateMonth, expirationDateDay,
					expirationDateYear, expirationDateHour,
					expirationDateMinute, neverExpire, reviewDateMonth,
					reviewDateDay, reviewDateYear, reviewDateHour,
					reviewDateMinute, neverReview, article.getIndexable(),
					article.getSmallImage(), article.getSmallImageURL(),
					smallFile, images, articleURL, prefs, tagsEntries);
			}
		}
		else {
			existingArticle = JournalArticleLocalServiceUtil.addArticle(
				userId, articleId, autoArticleId, plid, article.getVersion(),
				article.getTitle(), article.getDescription(),
				article.getContent(), article.getType(), parentStructureId,
				parentTemplateId, displayDateMonth, displayDateDay,
				displayDateYear, displayDateHour, displayDateMinute,
				expirationDateMonth, expirationDateDay, expirationDateYear,
				expirationDateHour, expirationDateMinute, neverExpire,
				reviewDateMonth, reviewDateDay, reviewDateYear, reviewDateHour,
				reviewDateMinute, neverReview, article.getIndexable(),
				article.getSmallImage(), article.getSmallImageURL(), smallFile,
				images, articleURL, prefs, tagsEntries, addCommunityPermissions,
				addGuestPermissions);
		}

		long strategyApprovalUserId = creationStrategy.getApprovalUserId(
			context, article);

		if ((strategyApprovalUserId !=
				JournalCreationStrategy.USE_DEFAULT_USER_ID_STRATEGY) ||
			(article.isApproved() && !existingArticle.isApproved())) {

			long approvedByUserId = strategyApprovalUserId;

			if (approvedByUserId == 0) {
				approvedByUserId = context.getUserId(
					article.getApprovedByUserUuid());
			}

			JournalArticleLocalServiceUtil.approveArticle(
				approvedByUserId, context.getGroupId(),
				existingArticle.getArticleId(), existingArticle.getVersion(),
				articleURL, prefs);
		}

		if (context.getBooleanParameter(_NAMESPACE, "comments")) {
			context.importComments(
				JournalArticle.class, article.getResourcePrimKey(),
				existingArticle.getResourcePrimKey(), context.getGroupId());
		}

		if (context.getBooleanParameter(_NAMESPACE, "ratings")) {
			context.importRatingsEntries(
				JournalArticle.class, article.getResourcePrimKey(),
				existingArticle.getResourcePrimKey());
		}

		articleIds.put(articleId, existingArticle.getArticleId());

		if (!articleId.equals(existingArticle.getArticleId())) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"An article with the ID " + articleId + " already " +
						"exists. The new generated ID is " +
							existingArticle.getArticleId());
			}
		}
	}

	public static void importFeed(
			PortletDataContext context, Map<String, String> structureIds,
			Map<String, String> templateIds, Map<String, String> feedIds,
			Element feedEl)
		throws Exception {

		String path = feedEl.attributeValue("path");

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		JournalFeed feed = (JournalFeed)context.getZipEntryAsObject(path);

		long userId = context.getUserId(feed.getUserUuid());
		long plid = context.getPlid();

		String feedId = feed.getFeedId();
		boolean autoFeedId = false;

		if ((Validator.isNumber(feedId)) ||
			(JournalFeedUtil.fetchByG_F(
				context.getGroupId(), feedId) != null)) {

			autoFeedId = true;
		}

		String parentStructureId = MapUtil.getString(
			structureIds, feed.getStructureId(), feed.getStructureId());
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

		JournalFeed existingFeed = null;

		if (context.getDataStrategy().equals(
				PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {

			existingFeed = JournalFeedUtil.fetchByUUID_G(
				feed.getUuid(), context.getGroupId());

			if (existingFeed == null) {
				existingFeed =
					JournalFeedLocalServiceUtil.addFeed(
						feed.getUuid(), userId, plid, feedId, autoFeedId,
						feed.getName(), feed.getDescription(), feed.getType(),
						parentStructureId, parentTemplateId,
						parentRenderTemplateId, feed.getDelta(),
						feed.getOrderByCol(), feed.getOrderByType(),
						feed.getTargetLayoutFriendlyUrl(),
						feed.getTargetPortletId(), feed.getContentField(),
						feed.getFeedType(), feed.getFeedVersion(),
						addCommunityPermissions, addGuestPermissions);
			}
			else {
				existingFeed =
					JournalFeedLocalServiceUtil.updateFeed(
						existingFeed.getGroupId(), existingFeed.getFeedId(),
						feed.getName(), feed.getDescription(), feed.getType(),
						parentStructureId, parentTemplateId,
						parentRenderTemplateId, feed.getDelta(),
						feed.getOrderByCol(), feed.getOrderByType(),
						feed.getTargetLayoutFriendlyUrl(),
						feed.getTargetPortletId(), feed.getContentField(),
						feed.getFeedType(), feed.getFeedVersion());
			}
		}
		else {
			existingFeed =
				JournalFeedLocalServiceUtil.addFeed(
					userId, plid, feedId, autoFeedId, feed.getName(),
					feed.getDescription(), feed.getType(), parentStructureId,
					parentTemplateId, parentRenderTemplateId, feed.getDelta(),
					feed.getOrderByCol(), feed.getOrderByType(),
					feed.getTargetLayoutFriendlyUrl(),
					feed.getTargetPortletId(), feed.getContentField(),
					feed.getFeedType(), feed.getFeedVersion(),
					addCommunityPermissions, addGuestPermissions);
		}

		feedIds.put(feedId, existingFeed.getFeedId());

		if (!feedId.equals(existingFeed.getStructureId())) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"A feed with the ID " + feedId + " already " +
						"exists. The new generated ID is " +
							existingFeed.getFeedId());
			}
		}
	}

	public static void importStructure(
			PortletDataContext context, Map<String, String> structureIds,
			Element structureEl)
		throws Exception {

		String path = structureEl.attributeValue("path");

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		JournalStructure structure =
			(JournalStructure)context.getZipEntryAsObject(path);

		long userId = context.getUserId(structure.getUserUuid());
		long plid = context.getPlid();

		String structureId = structure.getStructureId();
		boolean autoStructureId = false;

		if ((Validator.isNumber(structureId)) ||
			(JournalStructureUtil.fetchByG_S(
				context.getGroupId(), structureId) != null)) {

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

		JournalStructure existingStructure = null;

		if (context.getDataStrategy().equals(
				PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {

			existingStructure = JournalStructureUtil.fetchByUUID_G(
				structure.getUuid(), context.getGroupId());

			if (existingStructure == null) {
				existingStructure =
					JournalStructureLocalServiceUtil.addStructure(
						structure.getUuid(), userId, structureId,
						autoStructureId, plid, structure.getName(),
						structure.getDescription(), structure.getXsd(),
						addCommunityPermissions, addGuestPermissions);
			}
			else {
				existingStructure =
					JournalStructureLocalServiceUtil.updateStructure(
						existingStructure.getGroupId(),
						existingStructure.getStructureId(), structure.getName(),
						structure.getDescription(), structure.getXsd());
			}
		}
		else {
			existingStructure = JournalStructureLocalServiceUtil.addStructure(
				userId, structureId, autoStructureId, plid, structure.getName(),
				structure.getDescription(), structure.getXsd(),
				addCommunityPermissions, addGuestPermissions);
		}

		structureIds.put(structureId, existingStructure.getStructureId());

		if (!structureId.equals(existingStructure.getStructureId())) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"A structure with the ID " + structureId + " already " +
						"exists. The new generated ID is " +
							existingStructure.getStructureId());
			}
		}
	}

	public static void importTemplate(
			PortletDataContext context, Map<String, String> structureIds,
			Map<String, String> templateIds, Element templateEl)
		throws Exception {

		String path = templateEl.attributeValue("path");

		if (!context.isPathNotProcessed(path)) {
			return;
		}

		JournalTemplate template = (JournalTemplate)context.getZipEntryAsObject(
			path);

		long userId = context.getUserId(template.getUserUuid());
		long plid = context.getPlid();

		String templateId = template.getTemplateId();
		boolean autoTemplateId = false;

		if ((Validator.isNumber(templateId)) ||
			(JournalTemplateUtil.fetchByG_T(
				context.getGroupId(), templateId) != null)) {

			autoTemplateId = true;
		}

		String parentStructureId = MapUtil.getString(
			structureIds, template.getStructureId(), template.getStructureId());

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

		File smallFile = null;

		if (template.isSmallImage()) {
			byte[] bytes = context.getZipEntryAsByteArray(
				getTemplateSmallImagePath(context, template));

			smallFile = File.createTempFile(
				String.valueOf(template.getSmallImageId()),
				StringPool.PERIOD + template.getSmallImageType());

			FileUtil.write(smallFile, bytes);
		}

		JournalTemplate existingTemplate = null;

		if (context.getDataStrategy().equals(
				PortletDataHandlerKeys.DATA_STRATEGY_MIRROR)) {

			existingTemplate = JournalTemplateUtil.fetchByUUID_G(
				template.getUuid(), context.getGroupId());

			if (existingTemplate == null) {
				existingTemplate = JournalTemplateLocalServiceUtil.addTemplate(
					template.getUuid(), userId, templateId, autoTemplateId,
					plid, parentStructureId, template.getName(),
					template.getDescription(), template.getXsl(), formatXsl,
					template.getLangType(), template.getCacheable(),
					template.isSmallImage(), template.getSmallImageURL(),
					smallFile, addCommunityPermissions, addGuestPermissions);
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
						smallFile);
			}
		}
		else {
			existingTemplate = JournalTemplateLocalServiceUtil.addTemplate(
				userId, templateId, autoTemplateId, plid, parentStructureId,
				template.getName(), template.getDescription(),
				template.getXsl(), formatXsl, template.getLangType(),
				template.getCacheable(), template.isSmallImage(),
				template.getSmallImageURL(), smallFile, addCommunityPermissions,
				addGuestPermissions);
		}

		templateIds.put(templateId, existingTemplate.getTemplateId());

		if (!templateId.equals(existingTemplate.getTemplateId())) {
			if (_log.isWarnEnabled()) {
				_log.warn(
					"A template with the ID " + templateId + " already " +
						"exists. The new generated ID is " +
							existingTemplate.getTemplateId());
			}
		}
	}

	public PortletPreferences deleteData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs)
		throws PortletDataException {

		try {
			if (!context.addPrimaryKey(
					JournalPortletDataHandlerImpl.class, "deleteData")) {

				JournalArticleLocalServiceUtil.deleteArticles(
					context.getGroupId());

				JournalTemplateLocalServiceUtil.deleteTemplates(
					context.getGroupId());

				JournalStructureLocalServiceUtil.deleteStructures(
					context.getGroupId());
			}

			return prefs;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public String exportData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs)
		throws PortletDataException {

		try {
			Document doc = DocumentHelper.createDocument();

			Element root = doc.addElement("journal-data");

			root.addAttribute("group-id", String.valueOf(context.getGroupId()));

			Element structuresEl = root.addElement("structures");

			List<JournalStructure> structures =
				JournalStructureUtil.findByGroupId(context.getGroupId());

			for (JournalStructure structure : structures) {
				exportStructure(context, structuresEl, structure);
			}

			Element templatesEl = root.addElement("templates");

			List<JournalTemplate> templates = JournalTemplateUtil.findByGroupId(
				context.getGroupId());

			for (JournalTemplate template : templates) {
				exportTemplate(context, templatesEl, template);
			}

			Element feedsEl = root.addElement("feeds");

			List<JournalFeed> feeds = JournalFeedUtil.findByGroupId(
				context.getGroupId());

			for (JournalFeed feed : feeds) {
				if (context.isWithinDateRange(feed.getModifiedDate())) {
					exportFeed(context, feedsEl, feed);
				}
			}

			boolean exportArticles = context.getBooleanParameter(_NAMESPACE, "articles");

			Element articlesEl = root.addElement("articles");

			if (exportArticles) {
				List<JournalArticle> articles =
					JournalArticleUtil.findByGroupId(context.getGroupId());

				for (JournalArticle article : articles) {
					if (context.isWithinDateRange(article.getModifiedDate())) {
						exportArticle(context, articlesEl, article);
					}
				}
			}

			return XMLFormatter.toString(doc);
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public PortletDataHandlerControl[] getExportControls() {
		return new PortletDataHandlerControl[] {
			_articles, _structuresTemplatesAndFeeds
		};
	}

	public PortletDataHandlerControl[] getImportControls() {
		return new PortletDataHandlerControl[] {
			_articles, _structuresTemplatesAndFeeds
		};
	}

	public PortletPreferences importData(
			PortletDataContext context, String portletId,
			PortletPreferences prefs, String data)
		throws PortletDataException {

		try {
			Document doc = DocumentUtil.readDocumentFromXML(data);

			Element root = doc.getRootElement();

			List<Element> structureEls = root.element("structures").elements(
				"structure");

			Map<String, String> structureIds = context.getNewPrimaryKeysMap(
				JournalStructure.class);

			for (Element structureEl : structureEls) {
				importStructure(context, structureIds, structureEl);
			}

			List<Element> templateEls = root.element("templates").elements(
				"template");

			Map<String, String> templateIds = context.getNewPrimaryKeysMap(
				JournalTemplate.class);

			for (Element templateEl : templateEls) {
				importTemplate(context, structureIds, templateIds, templateEl);
			}

			List<Element> feedEls = root.element("feeds").elements("feed");

			Map<String, String> feedIds = context.getNewPrimaryKeysMap(
				JournalFeed.class);

			for (Element feedEl : feedEls) {
				importFeed(
					context, structureIds, templateIds, feedIds, feedEl);
			}

			if (context.getBooleanParameter(_NAMESPACE, "articles")) {
				List<Element> articleEls = root.element("articles").elements(
					"article");

				Map<String, String> articleIds = context.getNewPrimaryKeysMap(
					JournalArticle.class);

				for (Element articleEl : articleEls) {
					importArticle(
						context, structureIds, templateIds, articleIds,
						articleEl);
				}
			}

			return prefs;
		}
		catch (Exception e) {
			throw new PortletDataException(e);
		}
	}

	public boolean isPublishToLiveByDefault() {
		return true;
	}

	protected static String getArticlePath(
		PortletDataContext context, JournalArticle article) {

		StringMaker sm = new StringMaker();

		sm.append(context.getPortletPath(PortletKeys.JOURNAL));
		sm.append("/articles/");
		sm.append(article.getArticleId());
		sm.append(StringPool.SLASH);
		sm.append(article.getVersion());
		sm.append(StringPool.SLASH);
		sm.append(article.getArticleId());
		sm.append(".xml");

		return sm.toString();
	}

	protected static String getArticleImagePath(
		PortletDataContext context, JournalArticle article) {

		StringMaker sm = new StringMaker();

		sm.append(context.getPortletPath(PortletKeys.JOURNAL));
		sm.append("/articles/");
		sm.append(article.getArticleId());
		sm.append(StringPool.SLASH);
		sm.append(article.getVersion());
		sm.append(StringPool.SLASH);

		return sm.toString();
	}

	protected static String getArticleImagePath(
		PortletDataContext context, JournalArticle article,
		JournalArticleImage articleImage, Image image) {

		StringMaker sm = new StringMaker();

		sm.append(context.getPortletPath(PortletKeys.JOURNAL));
		sm.append("/articles/");
		sm.append(article.getArticleId());
		sm.append(StringPool.SLASH);
		sm.append(article.getVersion());
		sm.append(StringPool.SLASH);
		sm.append(articleImage.getElName());
		sm.append(articleImage.getLanguageId());
		sm.append(StringPool.PERIOD);
		sm.append(image.getType());

		return sm.toString();
	}

	protected static String getFeedPath(
		PortletDataContext context, JournalFeed feed) {

		StringMaker sm = new StringMaker();

		sm.append(context.getPortletPath(PortletKeys.JOURNAL));
		sm.append("/feeds/");
		sm.append(feed.getFeedId());
		sm.append(".xml");

		return sm.toString();
	}

	protected static String getTemplatePath(
		PortletDataContext context, JournalTemplate template) {

		StringMaker sm = new StringMaker();

		sm.append(context.getPortletPath(PortletKeys.JOURNAL));
		sm.append("/templates/");
		sm.append(template.getTemplateId());
		sm.append(".xml");

		return sm.toString();
	}

	protected static String getArticleSmallImagePath(
			PortletDataContext context, JournalArticle article)
		throws PortalException, SystemException {

		StringMaker sm = new StringMaker();

		sm.append(context.getPortletPath(PortletKeys.JOURNAL));
		sm.append("/articles/thumbnail.");
		sm.append(article.getSmallImageType());

		return sm.toString();
	}

	protected static String getTemplateSmallImagePath(
			PortletDataContext context, JournalTemplate template)
		throws PortalException, SystemException {

		StringMaker sm = new StringMaker();

		sm.append(context.getPortletPath(PortletKeys.JOURNAL));
		sm.append("/templates/thumbnail.");
		sm.append(template.getSmallImageType());

		return sm.toString();
	}

	protected static String getStructurePath(
		PortletDataContext context, JournalStructure structure) {

		StringMaker sm = new StringMaker();

		sm.append(context.getPortletPath(PortletKeys.JOURNAL));
		sm.append("/structures/");
		sm.append(structure.getStructureId());

		return sm.toString();
	}

	private static final String _NAMESPACE = "journal";

	private static final PortletDataHandlerBoolean _images =
		new PortletDataHandlerBoolean(_NAMESPACE, "images");

	private static final PortletDataHandlerBoolean _comments =
		new PortletDataHandlerBoolean(_NAMESPACE, "comments");

	private static final PortletDataHandlerBoolean _ratings =
		new PortletDataHandlerBoolean(_NAMESPACE, "ratings");

	private static final PortletDataHandlerBoolean _tags =
		new PortletDataHandlerBoolean(_NAMESPACE, "tags");

	private static final PortletDataHandlerBoolean _articles =
		new PortletDataHandlerBoolean(_NAMESPACE, "articles", true, false,
		new PortletDataHandlerControl[]{_images, _comments, _ratings, _tags});

	private static final PortletDataHandlerBoolean
		_structuresTemplatesAndFeeds = new PortletDataHandlerBoolean(
			_NAMESPACE, "structures-templates-and-feeds", true, true);

	private static Log _log =
		LogFactory.getLog(JournalPortletDataHandlerImpl.class);

}