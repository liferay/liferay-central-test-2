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

package com.liferay.portlet.journal.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.search.Field;
import com.liferay.portal.kernel.search.Hits;
import com.liferay.portal.kernel.search.SearchEngineUtil;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.search.Sort;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.mirage.custom.MirageServiceFactory;
import com.liferay.portal.mirage.custom.WorkflowServiceImpl;
import com.liferay.portal.mirage.model.JournalArticleContent;
import com.liferay.portal.mirage.model.OptionalJournalArticleCriteria;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.search.lucene.LuceneUtil;
import com.liferay.portal.servlet.filters.layoutcache.LayoutCacheUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.NoSuchTemplateException;
import com.liferay.portlet.journal.StructureXsdException;
import com.liferay.portlet.journal.job.CheckArticleJob;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalArticleDisplay;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.model.impl.JournalArticleDisplayImpl;
import com.liferay.portlet.journal.model.impl.JournalArticleImpl;
import com.liferay.portlet.journal.service.base.JournalArticleLocalServiceBaseImpl;
import com.liferay.portlet.journal.util.Indexer;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.portlet.journalcontent.util.JournalContentUtil;
import com.liferay.util.LocalizationUtil;

import com.sun.portal.cms.mirage.exception.CMSException;
import com.sun.portal.cms.mirage.model.custom.Content;
import com.sun.portal.cms.mirage.model.custom.ContentType;
import com.sun.portal.cms.mirage.model.search.SearchCriteria;
import com.sun.portal.cms.mirage.model.search.SearchFieldValue;
import com.sun.portal.cms.mirage.service.custom.ContentService;
import com.sun.portal.cms.mirage.service.custom.WorkflowService;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.internet.InternetAddress;

import javax.portlet.PortletPreferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentFactory;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.XPath;
import org.dom4j.io.SAXReader;

/**
 * <a href="JournalArticleLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class JournalArticleLocalServiceImpl
	extends JournalArticleLocalServiceBaseImpl {

	public JournalArticle addArticle(
			long userId, String articleId, boolean autoArticleId, long plid,
			String title, String description, String content, String type,
			String structureId, String templateId, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay,int expirationDateYear,
			int expirationDateHour,int expirationDateMinute,
			boolean neverExpire, int reviewDateMonth,int reviewDateDay,
			int reviewDateYear, int reviewDateHour,int reviewDateMinute,
			boolean neverReview, boolean indexable,boolean smallImage,
			String smallImageURL, File smallFile,Map<String, byte[]> images,
			String articleURL,PortletPreferences prefs, String[] tagsEntries,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		double version = JournalArticleImpl.DEFAULT_VERSION;

		return addArticle(
			userId, articleId, autoArticleId, plid, version, title,
			description, content, type, structureId, templateId,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear,
			reviewDateHour, reviewDateMinute, neverReview, indexable,
			smallImage, smallImageURL, smallFile, images, articleURL, prefs,
			tagsEntries, addCommunityPermissions, addGuestPermissions);
	}

	public JournalArticle addArticle(
			long userId, String articleId, boolean autoArticleId, long plid,
			double version, String title, String description, String content,
			String type, String structureId, String templateId,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, int reviewDateMonth,
			int reviewDateDay, int reviewDateYear, int reviewDateHour,
			int reviewDateMinute, boolean neverReview, boolean indexable,
			boolean smallImage, String smallImageURL, File smallFile,
			Map<String, byte[]> images, String articleURL,
			PortletPreferences prefs, String[] tagsEntries,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addArticle(
			null, userId, articleId, autoArticleId, plid, version, title,
			description, content, type, structureId, templateId,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear,
			reviewDateHour, reviewDateMinute, neverReview, indexable,
			smallImage, smallImageURL, smallFile, images, articleURL, prefs,
			tagsEntries, Boolean.valueOf(addCommunityPermissions),
			Boolean.valueOf(addGuestPermissions), null, null);
	}

	public JournalArticle addArticle(
			String uuid, long userId, String articleId, boolean autoArticleId,
			long plid, double version, String title, String description,
			String content, String type, String structureId, String templateId,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute,
			int expirationDateMonth,int expirationDateDay,
			int expirationDateYear, int expirationDateHour,
			int expirationDateMinute, boolean neverExpire, int reviewDateMonth,
			int reviewDateDay, int reviewDateYear, int reviewDateHour,
			int reviewDateMinute, boolean neverReview, boolean indexable,
			boolean smallImage, String smallImageURL, File smallFile,
			Map<String, byte[]> images, String articleURL,
			PortletPreferences prefs, String[] tagsEntries,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addArticle(
			uuid, userId, articleId, autoArticleId, plid, version, title,
			description, content, type, structureId, templateId,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear,
			reviewDateHour, reviewDateMinute, neverReview, indexable,
			smallImage, smallImageURL, smallFile, images, articleURL, prefs,
			tagsEntries, Boolean.valueOf(addCommunityPermissions),
			Boolean.valueOf(addGuestPermissions), null, null);
	}

	public JournalArticle addArticle(
			long userId, String articleId, boolean autoArticleId, long plid,
			String title, String description, String content, String type,
			String structureId, String templateId, int displayDateMonth,
			int displayDateDay, int displayDateYear, int displayDateHour,
			int displayDateMinute, int expirationDateMonth,
			int expirationDateDay,	int expirationDateYear,
			int expirationDateHour,	int expirationDateMinute,
			boolean neverExpire, int reviewDateMonth, int reviewDateDay,
			int reviewDateYear, int reviewDateHour,
			int reviewDateMinute, boolean neverReview, boolean indexable,
			boolean smallImage, String smallImageURL, File smallFile,
			Map<String, byte[]> images, String articleURL,
			PortletPreferences prefs, String[] tagsEntries,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		double version = JournalArticleImpl.DEFAULT_VERSION;

		return addArticle(
			null, userId, articleId, autoArticleId, plid, version, title,
			description, content, type, structureId, templateId,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear,
			reviewDateHour, reviewDateMinute, neverReview, indexable,
			smallImage, smallImageURL, smallFile, images, articleURL, prefs,
			tagsEntries, null, null, communityPermissions, guestPermissions);
	}

	public JournalArticle addArticle(
			String uuid, long userId, String articleId, boolean autoArticleId,
			long plid, double version, String title, String description,
			String content, String type, String structureId, String templateId,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, int reviewDateMonth, int reviewDateDay,
			int reviewDateYear, int reviewDateHour,	int reviewDateMinute,
			boolean neverReview, boolean indexable,	boolean smallImage,
			String smallImageURL, File smallFile, Map<String, byte[]> images,
			String articleURL, PortletPreferences prefs, String[] tagsEntries,
			Boolean addCommunityPermissions, Boolean addGuestPermissions,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		long groupId = PortalUtil.getPortletGroupId(plid);

		return addArticleToGroup(
			uuid, userId, articleId, autoArticleId, groupId, version, title,
			description, content, type, structureId, templateId,
			displayDateMonth, displayDateDay, displayDateYear, displayDateHour,
			displayDateMinute, expirationDateMonth, expirationDateDay,
			expirationDateYear, expirationDateHour, expirationDateMinute,
			neverExpire, reviewDateMonth, reviewDateDay, reviewDateYear,
			reviewDateHour, reviewDateMinute, neverReview, indexable,
			smallImage, smallImageURL, smallFile, images, articleURL, prefs,
			tagsEntries, addCommunityPermissions, addGuestPermissions,
			communityPermissions, guestPermissions);
	}

	public JournalArticle addArticleToGroup(
			String uuid, long userId, String articleId, boolean autoArticleId,
			long groupId, double version, String title, String description,
			String content, String type, String structureId, String templateId,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour, int expirationDateMinute,
			boolean neverExpire, int reviewDateMonth, int reviewDateDay,
			int reviewDateYear, int reviewDateHour, int reviewDateMinute,
			boolean neverReview, boolean indexable, boolean smallImage,
			String smallImageURL, File smallFile, Map<String, byte[]> images,
			String articleURL, PortletPreferences prefs, String[] tagsEntries,
			Boolean addCommunityPermissions, Boolean addGuestPermissions,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		// Article

		JournalArticle article = new JournalArticleImpl();
		article.setUuid(uuid);
		article.setUserId(userId);
		article.setGroupId(groupId);
		article.setArticleId(articleId);
		article.setVersion(version);
		article.setTitle(title);
		article.setDescription(description);
		article.setContent(content);
		article.setType(type);
		article.setStructureId(structureId);
		article.setTemplateId(templateId);
		article.setApproved(false);
		article.setIndexable(indexable);
		article.setSmallImage(smallImage);
		article.setSmallImageId(counterLocalService.increment());
		article.setSmallImageURL(smallImageURL);

		/* Create a Mirage Content object using the JournalArticle */
		JournalArticleContent articleContent =
			new JournalArticleContent(
				article, communityPermissions, guestPermissions);

		JournalArticleContent.CreationAttributes creationAttributes =
		articleContent.new CreationAttributes();
		creationAttributes.setAutoArticleId(autoArticleId);
		creationAttributes.setAddCommunityPermissions(addCommunityPermissions);
		creationAttributes.setAddGuestPermissions(addGuestPermissions);
		creationAttributes.setTagsEntries(tagsEntries);
		creationAttributes.setArticleURL(articleURL);
		creationAttributes.setPrefs(prefs);
		creationAttributes.setDisplayDateDay(displayDateDay);
		creationAttributes.setDisplayDateHour(displayDateHour);
		creationAttributes.setDisplayDateMinute(displayDateMinute);
		creationAttributes.setDisplayDateMonth(displayDateMonth);
		creationAttributes.setDisplayDateYear(displayDateYear);
		creationAttributes.setExpirationDateDay(expirationDateDay);
		creationAttributes.setExpirationDateHour(expirationDateHour);
		creationAttributes.setExpirationDateMinute(expirationDateMinute);
		creationAttributes.setExpirationDateMonth(expirationDateMonth);
		creationAttributes.setExpirationDateYear(expirationDateYear);
		creationAttributes.setNeverExpire(neverExpire);
		creationAttributes.setReviewDateDay(reviewDateDay);
		creationAttributes.setReviewDateHour(reviewDateHour);
		creationAttributes.setReviewDateMinute(reviewDateMinute);
		creationAttributes.setReviewDateMonth(reviewDateMonth);
		creationAttributes.setReviewDateYear(reviewDateYear);
		creationAttributes.setNeverReview(neverReview);
		creationAttributes.setSmallFile(smallFile);
		creationAttributes.setImages(images);

		articleContent.setCreationAttributes(creationAttributes);
		/* Get the Project Mirage service */
		ContentService contentService =
						MirageServiceFactory.getContentService();
		try {
			contentService.createContent(articleContent);
		}
		catch (CMSException ce) {
			_throwException(ce);
		}
		article = articleContent.getArticle();

		return article;

	}

	public void addArticleResources(
			long groupId, String articleId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		JournalArticle article = getLatestArticle(groupId, articleId);

		addArticleResources(
			article, addCommunityPermissions, addGuestPermissions);
	}

	public void addArticleResources(
			JournalArticle article, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addResources(
			article.getCompanyId(), article.getGroupId(), article.getUserId(),
			JournalArticle.class.getName(), article.getResourcePrimKey(),
			false, addCommunityPermissions, addGuestPermissions);
	}

	public void addArticleResources(
			long groupId, String articleId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		JournalArticle article = getLatestArticle(groupId, articleId);

		addArticleResources(article, communityPermissions, guestPermissions);
	}

	public void addArticleResources(
			JournalArticle article, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		resourceLocalService.addModelResources(
			article.getCompanyId(), article.getGroupId(), article.getUserId(),
			JournalArticle.class.getName(), article.getResourcePrimKey(),
			communityPermissions, guestPermissions);
	}

	public JournalArticle approveArticle(
			long userId, long groupId, String articleId, double version,
			String articleURL, PortletPreferences prefs)
		throws PortalException, SystemException {

		// Article

		JournalArticle journalArticle = new JournalArticleImpl();
		journalArticle.setUserId(userId);
		journalArticle.setGroupId(groupId);
		journalArticle.setArticleId(articleId);
		journalArticle.setVersion(version);

		/* Create a Mirage Content object using the JournalArticle */
		JournalArticleContent articleContent =
			new JournalArticleContent(journalArticle);

		JournalArticleContent.CreationAttributes creationAttributes =
			articleContent.new CreationAttributes();

		creationAttributes.setArticleURL(articleURL);
		creationAttributes.setPrefs(prefs);
		articleContent.setCreationAttributes(creationAttributes);

		WorkflowService workflowService = new WorkflowServiceImpl();
		try {
			workflowService.updateWorkflowComplete(articleContent);
		}
		catch (CMSException ce) {
			_throwException(ce);
		}
		journalArticle = articleContent.getArticle();

		return journalArticle;
	}

	public JournalArticle checkArticleResourcePrimKey(
			long groupId, String articleId, double version)
		throws PortalException, SystemException {

		JournalArticle article = getArticle(groupId, articleId, version);

		if (article.getResourcePrimKey() > 0) {
			return article;
		}

		long resourcePrimKey =
			journalArticleResourceLocalService.getArticleResourcePrimKey(
				groupId, articleId);

		article.setResourcePrimKey(resourcePrimKey);
		JournalArticleContent articleContent =
			new JournalArticleContent(article);
		ContentService contentService =
							MirageServiceFactory.getContentService();
		try {
			contentService.updateContent(articleContent, null);
		}
		catch (CMSException ce) {
			_throwException(ce);
		}

		return article;
	}

	public void checkArticles()
		throws PortalException, SystemException {

		try {
			Date now = new Date();
			Date expirationDateGT =
				new Date(now.getTime() - CheckArticleJob.INTERVAL);
			List<JournalArticle> expireArticles =
				_getArticlesByExpirationDate(now, expirationDateGT);

			if (_log.isDebugEnabled()) {
				_log.debug("Expiring " + expireArticles.size() + " articles");
			}

			Set<Long> companyIds = new HashSet<Long>();
			JournalArticleContent articleContent = new JournalArticleContent();
			ContentService contentService =
								MirageServiceFactory.getContentService();
			for (JournalArticle article : expireArticles) {
				article.setApproved(false);
				article.setExpired(true);

				articleContent.setArticle(article);
				contentService.updateContent(articleContent, null);

				try {
					if (article.isIndexable()) {
						Indexer.deleteArticle(
							article.getCompanyId(), article.getArticleId());
					}
				}
				catch (SearchException se) {
					_log.error("Removing index " + article.getId(), se);
				}

				JournalContentUtil.clearCache(
					article.getGroupId(), article.getArticleId(),
					article.getTemplateId());

				companyIds.add(article.getCompanyId());
			}

			for (long companyId : companyIds) {
				LayoutCacheUtil.clearCache(companyId);
			}
			Date reviewDateGT =
				new Date(now.getTime() - CheckArticleJob.INTERVAL);
			List<JournalArticle> reviewArticles =
				_getArticleByReviewDate(now, reviewDateGT);
			if (_log.isDebugEnabled()) {
				_log.debug("Sending review notifications for " +
					reviewArticles.size() + " articles");
			}

			for (JournalArticle article : reviewArticles) {
				String articleURL = StringPool.BLANK;

				long ownerId = article.getGroupId();
				int ownerType = PortletKeys.PREFS_OWNER_TYPE_GROUP;
				long plid = PortletKeys.PREFS_PLID_SHARED;
				String portletId = PortletKeys.JOURNAL;

				PortletPreferences prefs =
					portletPreferencesLocalService.getPreferences(
						article.getCompanyId(), ownerId, ownerType, plid,
						portletId);

				try {
					sendEmail(article, articleURL, prefs, "review");
				}
				catch (IOException ioe) {
					throw new SystemException(ioe);
				}
			}
		}
		catch (CMSException ex) {
			_throwException(ex);
		}
	}

	public void checkNewLine(long groupId, String articleId, double version)
		throws PortalException, SystemException {

		JournalArticle article = getArticle(groupId, articleId, version);

		String content = GetterUtil.getString(article.getContent());

		if (content.indexOf("\\n") != -1) {
			content = StringUtil.replace(content, new String[] {
				"\\n", "\\r"
			}, new String[] {
				"\n", "\r"
			});

			article.setContent(content);

			JournalArticleContent articleContent =
				new JournalArticleContent(article);
			ContentService contentService =
								MirageServiceFactory.getContentService();
			try {
				contentService.updateContent(articleContent, null);
			}
			catch (CMSException ce) {
				_throwException(ce);
			}
		}
	}

	public void checkStructure(long groupId, String articleId, double version)
		throws PortalException, SystemException {

		JournalArticle article = getArticle(groupId, articleId, version);

		if (Validator.isNull(article.getStructureId())) {
			return;
		}

		try {
			checkStructure(article);
		}
		catch (DocumentException de) {
			_log.error(de, de);
		}
	}

	public void deleteArticle(
			long groupId, String articleId, double version, String articleURL,
			PortletPreferences prefs)
		throws PortalException, SystemException {

		JournalArticle article = new JournalArticleImpl();
		article.setGroupId(groupId);
		article.setArticleId(articleId);
		article.setVersion(version);

		JournalArticleContent articleContent =
			new JournalArticleContent(article);

		ContentService contentService =
							MirageServiceFactory.getContentService();
		OptionalJournalArticleCriteria criteria =
			new OptionalJournalArticleCriteria(
				OptionalJournalArticleCriteria.FIND_BY_G_A_V);
		try {
			articleContent =
				(JournalArticleContent) contentService.getContent(
					articleContent, criteria);
		}
		catch (CMSException ce) {
			_throwException(ce);
		}
		article = articleContent.getArticle();
		deleteArticle(article, articleURL, prefs);
	}

	public void deleteArticle(
			JournalArticle article, String articleURL, PortletPreferences prefs)
		throws PortalException, SystemException {

		JournalArticleContent articleContent =
			new JournalArticleContent(article);
		JournalArticleContent.CreationAttributes creationAttributes =
			articleContent.new CreationAttributes();
		creationAttributes.setArticleURL(articleURL);
		creationAttributes.setPrefs(prefs);
		articleContent.setCreationAttributes(creationAttributes);
		ContentService contentService =
							MirageServiceFactory.getContentService();
		try {
			contentService.deleteContent(articleContent);
		}
		catch (CMSException ce) {
			_throwException(ce);
		}

	}

	public void deleteArticles(long groupId)
		throws PortalException, SystemException {

		List<JournalArticle> articles = getArticles(groupId);
		for (JournalArticle article : articles) {

			deleteArticle(article, null, null);
		}
	}

	public void expireArticle(
			long groupId, String articleId, double version, String articleURL,
			PortletPreferences prefs)
		throws PortalException, SystemException {

		JournalArticle article = getArticle(groupId, articleId, version);

		expireArticle(article, articleURL, prefs);
	}

	public void expireArticle(
			JournalArticle article, String articleURL, PortletPreferences prefs)
		throws PortalException, SystemException {

		/* Create a Mirage Content object using the JournalArticle */
		JournalArticleContent articleContent =
			new JournalArticleContent(article);

		JournalArticleContent.CreationAttributes creationAttributes =
			articleContent.new CreationAttributes();

		creationAttributes.setArticleURL(articleURL);
		creationAttributes.setPrefs(prefs);
		articleContent.setCreationAttributes(creationAttributes);

		WorkflowService workflowService = new WorkflowServiceImpl();
		try {
			workflowService.updateWorkflowContentRejected(articleContent);
		}
		catch (CMSException ce) {
			_throwException(ce);
		}

	}

	public JournalArticle getArticle(long id)
		throws PortalException, SystemException {

		JournalArticle journalArticle = new JournalArticleImpl();
		journalArticle.setId(id);
		JournalArticleContent articleContent =
			new JournalArticleContent(journalArticle);
		OptionalJournalArticleCriteria criteria =
			new OptionalJournalArticleCriteria(
				OptionalJournalArticleCriteria.FIND_BY_PRIMARY_KEY);
		ContentService contentService =
							MirageServiceFactory.getContentService();
		try {
			articleContent =
				(JournalArticleContent) contentService.getContent(
					articleContent, criteria);
		}
		catch (CMSException ex) {
			_throwException(ex);
		}
		return articleContent.getArticle();

	}

	public JournalArticle getArticle(long groupId, String articleId)
		throws PortalException, SystemException {

		// Get the latest article that is approved, if none are approved, get
		// the latest unapproved article

		try {
			return getLatestArticle(groupId, articleId, Boolean.TRUE);
		}
		catch (NoSuchArticleException nsae) {
			return getLatestArticle(groupId, articleId, Boolean.FALSE);
		}
	}

	public JournalArticle getArticle(
			long groupId, String articleId, double version)
		throws PortalException, SystemException {

		JournalArticle journalArticle = new JournalArticleImpl();
		journalArticle.setGroupId(groupId);
		journalArticle.setArticleId(articleId);
		journalArticle.setVersion(version);
		JournalArticleContent articleContent =
			new JournalArticleContent(journalArticle);
		OptionalJournalArticleCriteria criteria =
			new OptionalJournalArticleCriteria(
				OptionalJournalArticleCriteria.FIND_BY_G_A_V);
		ContentService contentService =
							MirageServiceFactory.getContentService();
		try {
			articleContent =
				(JournalArticleContent) contentService.getContent(
					articleContent, criteria);
		}
		catch (CMSException ex) {
			_throwException(ex);
		}
		return articleContent.getArticle();

	}

	public String getArticleContent(
			long groupId, String articleId, String languageId,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getArticleContent(
			groupId, articleId, null, languageId, themeDisplay);
	}

	public String getArticleContent(
			long groupId, String articleId, String templateId,
			String languageId,ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		JournalArticleDisplay articleDisplay =
			getArticleDisplay(
				groupId, articleId, templateId, languageId, themeDisplay);

		return articleDisplay.getContent();
	}

	public String getArticleContent(
			long groupId, String articleId, double version, String languageId,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getArticleContent(
			groupId, articleId, version, null, languageId, themeDisplay);
	}

	public String getArticleContent(
			long groupId, String articleId, double version, String templateId,
			String languageId, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		JournalArticleDisplay articleDisplay =
			getArticleDisplay(
				groupId, articleId, version, templateId, languageId,
				themeDisplay);

		if (articleDisplay == null) {
			return StringPool.BLANK;
		}
		else {
			return articleDisplay.getContent();
		}
	}

	public JournalArticleDisplay getArticleDisplay(
			long groupId, String articleId, String languageId,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getArticleDisplay(
			groupId, articleId, null, languageId, themeDisplay);
	}

	public JournalArticleDisplay getArticleDisplay(
			long groupId, String articleId, String languageId, int page,
			String xmlRequest, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getArticleDisplay(
			groupId, articleId, null, languageId, page, xmlRequest,
			themeDisplay);
	}

	public JournalArticleDisplay getArticleDisplay(
			long groupId, String articleId, String templateId,
			String languageId, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		JournalArticle article = getDisplayArticle(groupId, articleId);

		return getArticleDisplay(
			groupId, articleId, article.getVersion(), templateId, languageId,
			themeDisplay);
	}

	public JournalArticleDisplay getArticleDisplay(
			long groupId, String articleId, String templateId,
			String languageId, int page, String xmlRequest,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		JournalArticle article = getDisplayArticle(groupId, articleId);

		return getArticleDisplay(
			groupId, articleId, article.getVersion(), templateId, languageId,
			page, xmlRequest, themeDisplay);
	}

	public JournalArticleDisplay getArticleDisplay(
			long groupId, String articleId, double version, String templateId,
			String languageId, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		return getArticleDisplay(
			groupId, articleId, version, templateId, languageId, 1, null,
			themeDisplay);
	}

	public JournalArticleDisplay getArticleDisplay(
			long groupId, String articleId, double version, String templateId,
			String languageId, int page, String xmlRequest,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		String content = null;

		Date now = new Date();

		JournalArticle article = getArticle(groupId, articleId, version);

		if (article.isExpired()) {
			Date expirationDate = article.getExpirationDate();

			if ((expirationDate != null) && expirationDate.before(now)) {
				return null;
			}
		}

		if (article.getDisplayDate().after(now)) {
			return null;
		}

		if (page < 1) {
			page = 1;
		}

		int numberOfPages = 1;
		boolean paginate = false;
		boolean pageFlow = false;

		boolean cacheable = true;

		Map<String, String> tokens =
			JournalUtil.getTokens(groupId, themeDisplay);

		tokens.put(
			"article_resource_pk",
			String.valueOf(article.getResourcePrimKey()));

		String xml = article.getContent();

		try {
			Document doc = null;

			Element root = null;

			if (article.isTemplateDriven()) {
				SAXReader reader = new SAXReader();

				doc = reader.read(new StringReader(xml));

				root = doc.getRootElement();

				Document request = null;

				if (Validator.isNotNull(xmlRequest)) {
					request = reader.read(new StringReader(xmlRequest));
				}

				List<Element> pages = root.elements("page");

				if (pages.size() > 0) {
					pageFlow = true;

					String targetPage =
						request.valueOf(
						"/request/parameters/parameter[name='targetPage']/"
							+ "value");

					Element pageEl = null;

					if (Validator.isNotNull(targetPage)) {
						XPath xpathSelector =
							DocumentHelper.createXPath("/root/page[@id = '" +
								targetPage + "']");

						pageEl = (Element) xpathSelector.selectSingleNode(doc);
					}

					DocumentFactory docFactory = DocumentFactory.getInstance();

					if (pageEl != null) {
						doc = docFactory.createDocument(pageEl);

						root = doc.getRootElement();

						numberOfPages = pages.size();
					}
					else {
						if (page > pages.size()) {
							page = 1;
						}

						pageEl = pages.get(page - 1);

						doc = docFactory.createDocument(pageEl);

						root = doc.getRootElement();

						numberOfPages = pages.size();
						paginate = true;
					}
				}

				if (request != null) {
					root.add(request.getRootElement().createCopy());
				}

				JournalUtil.addAllReservedEls(root, tokens, article);

				xml = JournalUtil.formatXML(doc);
			}
		}
		catch (DocumentException de) {
			throw new SystemException(de);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}

		try {
			if (_log.isDebugEnabled()) {
				_log.debug("Transforming " + articleId + " " + version + " " +
					languageId);
			}

			String script = null;
			String langType = null;

			if (article.isTemplateDriven()) {

				// Try with specified template first. If a template is not
				// specified, use the default one. If the specified template
				// does not exit, use the default one. If the default one does
				// not exist, throw an exception.

				String defaultTemplateId = article.getTemplateId();

				if (Validator.isNull(templateId)) {
					templateId = defaultTemplateId;
				}

				JournalTemplate template = null;

				try {
					template =
						journalTemplateLocalService.getTemplate(
							groupId, templateId);
				}
				catch (NoSuchTemplateException nste) {
					if (!defaultTemplateId.equals(templateId)) {
						template =
							journalTemplateLocalService.getTemplate(
								groupId, defaultTemplateId);
					}
					else {
						throw nste;
					}
				}

				script = template.getXsl();
				langType = template.getLangType();
				cacheable = template.isCacheable();
			}

			content =
				JournalUtil.transform(
					tokens, languageId, xml, script, langType);

			if (!pageFlow) {
				String[] pieces = StringUtil.split(content, _TOKEN_PAGE_BREAK);

				if (pieces.length > 1) {
					if (page > pieces.length) {
						page = 1;
					}

					content = pieces[page - 1];
					numberOfPages = pieces.length;
					paginate = true;
				}
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}

		return new JournalArticleDisplayImpl(
			article.getId(), article.getResourcePrimKey(),
			article.getGroupId(), article.getUserId(), article.getArticleId(),
			article.getVersion(), article.getTitle(), article.getDescription(),
			article.getAvailableLocales(), content, article.getType(),
			article.getStructureId(), templateId, article.isSmallImage(),
			article.getSmallImageId(), article.getSmallImageURL(),
			numberOfPages, page, paginate, cacheable);
	}

	public List<JournalArticle> getArticles()
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();
		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.FINDER,
			OptionalJournalArticleCriteria.FIND_ALL);

		criteria.setSearchFieldValues(searchFields);

		ContentService contentService =
							MirageServiceFactory.getContentService();
		try {
			List<Content> contents = contentService.searchContents(criteria);
			return _getArticlesFromContents(contents);
		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}

	}

	public List<JournalArticle> getArticles(long groupId)
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();
		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.FINDER,
			OptionalJournalArticleCriteria.FIND_BY_GROUP);

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.GROUP_ID,
			String.valueOf(groupId));

		criteria.setSearchFieldValues(searchFields);

		ContentService contentService =
							MirageServiceFactory.getContentService();
		try {
			List<Content> contents = contentService.searchContents(criteria);

			return _getArticlesFromContents(contents);
		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}

	}

	public List<JournalArticle> getArticles(long groupId, int start, int end)
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();
		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.FINDER,
			OptionalJournalArticleCriteria.FIND_BY_GROUP_LIMIT);

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.GROUP_ID,
			String.valueOf(groupId));

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.RANGE_START,
			String.valueOf(start));

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.RANGE_END,
			String.valueOf(end));

		criteria.setSearchFieldValues(searchFields);

		ContentService contentService =
							MirageServiceFactory.getContentService();
		try {
			List<Content> contents = contentService.searchContents(criteria);

			return _getArticlesFromContents(contents);
		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}

	}

	public List<JournalArticle> getArticles(
			long groupId, int start, int end, OrderByComparator obc)
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();
		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.FINDER,
			OptionalJournalArticleCriteria.FIND_BY_GROUP_AND_ORDER);

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.GROUP_ID,
			String.valueOf(groupId));

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.RANGE_START,
			String.valueOf(start));

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.RANGE_END,
			String.valueOf(end));

		criteria.setSearchFieldValues(searchFields);
		criteria.setOrderByComparator(obc);

		ContentService contentService =
							MirageServiceFactory.getContentService();
		try {
			List<Content> contents = contentService.searchContents(criteria);

			return _getArticlesFromContents(contents);
		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}

	}

	public List<JournalArticle> getArticles(long groupId, String articleId)
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();
		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.FINDER,
			OptionalJournalArticleCriteria.FIND_BY_G_A);

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.GROUP_ID,
			String.valueOf(groupId));

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.ARTICLE_ID, articleId);
		criteria.setSearchFieldValues(searchFields);

		ContentService contentService =
							MirageServiceFactory.getContentService();
		try {
			List<Content> contents = contentService.searchContents(criteria);

			return _getArticlesFromContents(contents);
		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}

	}

	public List<JournalArticle> getArticlesBySmallImageId(long smallImageId)
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();
		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.FINDER,
			OptionalJournalArticleCriteria.FIND_BY_SMALL_IMAGE_ID);

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.SMALL_IMAGE_ID,
			String.valueOf(smallImageId));

		criteria.setSearchFieldValues(searchFields);

		ContentService contentService =
							MirageServiceFactory.getContentService();
		try {
			List<Content> contents = contentService.searchContents(criteria);

			return _getArticlesFromContents(contents);
		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}

	}

	public int getArticlesCount(long groupId)
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();
		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();
		String typeId = String.valueOf(groupId);
		ContentType contentType = new ContentType();
		contentType.setUuid(typeId);

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.FINDER,
			OptionalJournalArticleCriteria.FIND_BY_GROUP);

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.GROUP_ID,
			String.valueOf(groupId));

		criteria.setSearchFieldValues(searchFields);

		ContentService contentService =
							MirageServiceFactory.getContentService();
		try {

			return contentService.contentSearchCount(criteria);

		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}

	}

	public JournalArticle getDisplayArticle(long groupId, String articleId)
		throws PortalException, SystemException {

		SearchCriteria criteria = new SearchCriteria();
		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();
		List<JournalArticle> articles = null;

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.FINDER,
			OptionalJournalArticleCriteria.FIND_BY_G_A_A);

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.GROUP_ID,
			String.valueOf(groupId));

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.ARTICLE_ID, articleId);
		criteria.setSearchFieldValues(searchFields);

		criteria.setSearchFieldValues(searchFields);

		ContentService contentService =
							MirageServiceFactory.getContentService();
		try {
			List<Content> contents = contentService.searchContents(criteria);

			articles = _getArticlesFromContents(contents);
		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}

		if (articles.size() == 0) {
			throw new NoSuchArticleException();
		}

		Date now = new Date();

		for (int i = 0; i < articles.size(); i++) {
			JournalArticle article = articles.get(i);

			Date expirationDate = article.getExpirationDate();

			if (article.getDisplayDate().before(now) &&
				((expirationDate == null) || expirationDate.after(now))) {

				return article;
			}
		}

		return articles.get(0);
	}

	public JournalArticle getLatestArticle(long groupId, String articleId)
		throws PortalException, SystemException {

		return getLatestArticle(groupId, articleId, null);
	}

	public JournalArticle getLatestArticle(
			long groupId, String articleId, Boolean approved)
		throws PortalException, SystemException {

		SearchCriteria criteria = new SearchCriteria();
		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();
		List<JournalArticle> articles = null;

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.FINDER,
			OptionalJournalArticleCriteria.FIND_LATEST);

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.GROUP_ID,
			String.valueOf(groupId));

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.ARTICLE_ID, articleId);
		if (approved == null) {
			_addSearchField(
				searchFields, OptionalJournalArticleCriteria.APPROVED, null);
		}
		else {
			_addSearchField(
				searchFields, OptionalJournalArticleCriteria.APPROVED,
				String.valueOf(approved.booleanValue()));
		}
		criteria.setSearchFieldValues(searchFields);

		ContentService contentService =
							MirageServiceFactory.getContentService();
		try {
			List<Content> contents = contentService.searchContents(criteria);

			articles = _getArticlesFromContents(contents);
		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}

		if (articles.size() == 0) {
			throw new NoSuchArticleException();
		}

		return articles.get(0);

	}

	public double getLatestVersion(long groupId, String articleId)
		throws PortalException, SystemException {

		JournalArticle article = getLatestArticle(groupId, articleId);

		return article.getVersion();
	}

	public double getLatestVersion(
			long groupId, String articleId, Boolean approved)
		throws PortalException, SystemException {

		JournalArticle article = getLatestArticle(groupId, articleId, approved);

		return article.getVersion();
	}

	public List<JournalArticle> getStructureArticles(
			long groupId, String structureId)
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();
		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();
		List<JournalArticle> articles = null;
		ContentType contentType = new ContentType();
		contentType.setUuid(structureId);

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.FINDER,
			OptionalJournalArticleCriteria.FIND_BY_G_S);

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.GROUP_ID,
			String.valueOf(groupId));

		criteria.setSearchFieldValues(searchFields);

		ContentService contentService =
							MirageServiceFactory.getContentService();
		try {
			List<Content> contents =
				contentService.searchContentsByType(contentType, criteria);

			articles = _getArticlesFromContents(contents);
		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}

		return articles;

	}

	public List<JournalArticle> getStructureArticles(
			long groupId, String structureId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();
		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();
		List<JournalArticle> articles = null;
		ContentType contentType = new ContentType();
		contentType.setUuid(structureId);

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.FINDER,
			OptionalJournalArticleCriteria.FIND_BY_G_S_ORDER);

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.GROUP_ID,
			String.valueOf(groupId));

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.RANGE_START,
			String.valueOf(start));

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.RANGE_END,
			String.valueOf(end));

		criteria.setSearchFieldValues(searchFields);
		criteria.setOrderByComparator(obc);

		ContentService contentService =
							MirageServiceFactory.getContentService();
		try {
			List<Content> contents =
				contentService.searchContentsByType(contentType, criteria);

			articles = _getArticlesFromContents(contents);
		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}
		return articles;

	}

	public int getStructureArticlesCount(long groupId, String structureId)
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();
		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();
		ContentType contentType = new ContentType();
		contentType.setUuid(structureId);

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.FINDER,
			OptionalJournalArticleCriteria.COUNT_BY_G_S);

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.GROUP_ID,
			String.valueOf(groupId));

		criteria.setSearchFieldValues(searchFields);

		ContentService contentService =
							MirageServiceFactory.getContentService();
		try {

			return contentService.contentSearchCount(contentType, criteria);

		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}

	}

	public List<JournalArticle> getTemplateArticles(
			long groupId, String templateId)
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();
		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();
		List<JournalArticle> articles = null;
		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.FINDER,
			OptionalJournalArticleCriteria.FIND_BY_G_T);

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.GROUP_ID,
			String.valueOf(groupId));

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.TEMPLATE_ID,
			templateId);

		criteria.setSearchFieldValues(searchFields);

		ContentService contentService =
							MirageServiceFactory.getContentService();
		try {
			List<Content> contents = contentService.searchContents(criteria);

			articles = _getArticlesFromContents(contents);
		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}
		return articles;

	}

	public List<JournalArticle> getTemplateArticles(
			long groupId, String templateId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();
		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();
		List<JournalArticle> articles = null;

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.FINDER,
			OptionalJournalArticleCriteria.FIND_BY_G_T_ORDER);

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.GROUP_ID,
			String.valueOf(groupId));

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.TEMPLATE_ID,
			templateId);

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.RANGE_START,
			String.valueOf(start));

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.RANGE_END,
			String.valueOf(end));

		criteria.setSearchFieldValues(searchFields);
		criteria.setOrderByComparator(obc);

		ContentService contentService =
							MirageServiceFactory.getContentService();
		try {
			List<Content> contents = contentService.searchContents(criteria);

			articles = _getArticlesFromContents(contents);
		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}
		return articles;

	}

	public int getTemplateArticlesCount(long groupId, String templateId)
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();
		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.FINDER,
			OptionalJournalArticleCriteria.COUNT_BY_G_S);

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.GROUP_ID,
			String.valueOf(groupId));

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.TEMPLATE_ID,
			templateId);

		criteria.setSearchFieldValues(searchFields);

		ContentService contentService =
							MirageServiceFactory.getContentService();
		try {

			return contentService.contentSearchCount(criteria);

		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}

	}

	public boolean hasArticle(long groupId, String articleId)
		throws SystemException {

		try {
			getArticle(groupId, articleId);

			return true;
		}
		catch (PortalException pe) {
			return false;
		}
	}

	public boolean isLatestVersion(
			long groupId, String articleId, double version)
		throws PortalException, SystemException {

		if (getLatestVersion(groupId, articleId) == version) {
			return true;
		}
		else {
			return false;
		}
	}

	public boolean isLatestVersion(
			long groupId, String articleId, double version, Boolean active)
		throws PortalException, SystemException {

		if (getLatestVersion(groupId, articleId, active) == version) {
			return true;
		}
		else {
			return false;
		}
	}

	public void reIndex(String[] ids)
		throws SystemException {

		if (SearchEngineUtil.isIndexReadOnly()) {
			return;
		}

		long companyId = GetterUtil.getLong(ids[0]);

		try {
			for (JournalArticle article : _getArticlesByCompanyId(companyId)) {

				if (article.isApproved() && article.isIndexable()) {
					long resourcePrimKey = article.getResourcePrimKey();
					long groupId = article.getGroupId();
					String articleId = article.getArticleId();
					double version = article.getVersion();
					String title = article.getTitle();
					String description = article.getDescription();
					String content = article.getContent();
					String type = article.getType();
					Date displayDate = article.getDisplayDate();

					String[] tagsEntries =
						tagsEntryLocalService.getEntryNames(
							JournalArticle.class.getName(), resourcePrimKey);

					try {
						com.liferay.portal.kernel.search.Document doc =
							Indexer.getArticleDocument(
								companyId, groupId, articleId, version, title,
								description, content, type, displayDate,
								tagsEntries);

						SearchEngineUtil.addDocument(companyId, doc);
					}
					catch (Exception e1) {
						_log.error("Reindexing " + article.getId(), e1);
					}
				}
			}
		}
		catch (SystemException se) {
			throw se;
		}
		catch (Exception e2) {
			throw new SystemException(e2);
		}
	}

	public JournalArticle removeArticleLocale(
			long groupId, String articleId, double version, String languageId)
		throws PortalException, SystemException {

		JournalArticle article = getArticle(groupId, articleId, version);

		String content = article.getContent();

		if (article.isTemplateDriven()) {
			content = JournalUtil.removeArticleLocale(content, languageId);
		}
		else {
			content =
				LocalizationUtil.removeLocalization(
					content, "static-content", languageId, true);
		}

		article.setContent(content);
		JournalArticleContent articleContent =
			new JournalArticleContent(article);
		ContentService contentService =
							MirageServiceFactory.getContentService();
		try {
			contentService.updateContent(articleContent, null);
		}
		catch (CMSException ce) {
			_throwException(ce);
		}
		article = articleContent.getArticle();

		return article;
	}

	public Hits search(
			long companyId, long groupId, String keywords, int start, int end)
		throws SystemException {

		return search(companyId, groupId, keywords, "displayDate", start, end);
	}

	public Hits search(
			long companyId, long groupId, String keywords, String sortField,
			int start, int end)
		throws SystemException {

		try {
			BooleanQuery contextQuery = new BooleanQuery();

			LuceneUtil.addRequiredTerm(
				contextQuery, Field.PORTLET_ID, Indexer.PORTLET_ID);

			if (groupId > 0) {
				LuceneUtil.addRequiredTerm(
					contextQuery, Field.GROUP_ID, groupId);
			}

			BooleanQuery searchQuery = new BooleanQuery();

			if (Validator.isNotNull(keywords)) {
				LuceneUtil.addTerm(searchQuery, Field.TITLE, keywords);
				LuceneUtil.addTerm(searchQuery, Field.CONTENT, keywords);
				LuceneUtil.addTerm(searchQuery, Field.DESCRIPTION, keywords);
				LuceneUtil.addTerm(searchQuery, Field.TAGS_ENTRIES, keywords);
			}

			BooleanQuery fullQuery = new BooleanQuery();

			fullQuery.add(contextQuery, BooleanClause.Occur.MUST);

			if (searchQuery.clauses().size() > 0) {
				fullQuery.add(searchQuery, BooleanClause.Occur.MUST);
			}

			Sort sort = new Sort(sortField, true);

			return SearchEngineUtil.search(
				companyId, fullQuery.toString(), sort, start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	public List<JournalArticle> search(
			long companyId, long groupId, String keywords, Double version,
			String type, String structureId, String templateId,
			Date displayDateGT,Date displayDateLT, Boolean approved,
			Boolean expired, Date reviewDate,
			int start, int end, OrderByComparator obc)
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();
		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();

		Map<String, String> fields = new HashMap<String, String>();
		fields.put(
			OptionalJournalArticleCriteria.FINDER,
			OptionalJournalArticleCriteria.FIND_BY_KEYWORDS);
		fields.put(
			OptionalJournalArticleCriteria.GROUP_ID, String.valueOf(groupId));
		fields.put(
			OptionalJournalArticleCriteria.COMPANY_ID,
			String.valueOf(companyId));
		fields.put(OptionalJournalArticleCriteria.KEYWORDS, keywords);
		if (version == null) {
			fields.put(OptionalJournalArticleCriteria.VERSION, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.VERSION,
				String.valueOf(version));
		}
		fields.put(OptionalJournalArticleCriteria.TYPE, type);
		if (structureId == null) {
			fields.put(OptionalJournalArticleCriteria.STRUCTURE_ID, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.STRUCTURE_ID,
				String.valueOf(structureId));
		}
		if (templateId == null) {
			fields.put(OptionalJournalArticleCriteria.TEMPLATE_ID, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.TEMPLATE_ID,
				String.valueOf(templateId));
		}
		if ((displayDateGT == null)) {
			fields.put(OptionalJournalArticleCriteria.DISPLAY_DATE_GT, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.DISPLAY_DATE_GT,
				String.valueOf(displayDateGT.getTime()));
		}
		if ((displayDateLT == null)) {
			fields.put(OptionalJournalArticleCriteria.DISPLAY_DATE_LT, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.DISPLAY_DATE_LT,
				String.valueOf(displayDateLT.getTime()));
		}
		if (approved == null) {
			fields.put(OptionalJournalArticleCriteria.APPROVED, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.APPROVED, approved.toString());
		}
		if (expired == null) {
			fields.put(OptionalJournalArticleCriteria.EXPIRED, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.EXPIRED, expired.toString());
		}
		if ((reviewDate == null)) {
			fields.put(OptionalJournalArticleCriteria.REVIEW_DATE, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.REVIEW_DATE,
				String.valueOf(reviewDate.getTime()));
		}
		fields.put(
			OptionalJournalArticleCriteria.RANGE_START, String.valueOf(start));
		fields.put(
			OptionalJournalArticleCriteria.RANGE_END, String.valueOf(end));

		_addSearchFields(searchFields, fields);
		criteria.setSearchFieldValues(searchFields);
		criteria.setOrderByComparator(obc);

		ContentService contentService =
							MirageServiceFactory.getContentService();
		try {
			List<Content> contents = contentService.searchContents(criteria);

			return _getArticlesFromContents(contents);
		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}

	}

	public List<JournalArticle> search(
			long companyId, long groupId, String articleId, Double version,
			String title, String description, String content, String type,
			String structureId, String templateId, Date displayDateGT,
			Date displayDateLT, Boolean approved, Boolean expired,
			Date reviewDate,boolean andOperator, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();
		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();
		String typeId = String.valueOf(groupId);
		ContentType contentType = new ContentType();
		contentType.setUuid(typeId);

		Map<String, String> fields = new HashMap<String, String>();
		fields.put(
			OptionalJournalArticleCriteria.FINDER,
			OptionalJournalArticleCriteria.FIND_BY_C_G_A_V_T_D_C_T_S_T_D_A_E_R);
		fields.put(
			OptionalJournalArticleCriteria.GROUP_ID, String.valueOf(groupId));
		fields.put(
			OptionalJournalArticleCriteria.COMPANY_ID,
			String.valueOf(companyId));
		fields.put(OptionalJournalArticleCriteria.ARTICLE_ID, articleId);
		if (version == null) {
			fields.put(OptionalJournalArticleCriteria.VERSION, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.VERSION,
												String.valueOf(version));
		}
		fields.put(OptionalJournalArticleCriteria.TITLE, title);
		fields.put(OptionalJournalArticleCriteria.DESCRIPTION, description);
		fields.put(OptionalJournalArticleCriteria.CONTENT, content);
		fields.put(OptionalJournalArticleCriteria.TYPE, type);
		if (structureId == null) {
			fields.put(OptionalJournalArticleCriteria.STRUCTURE_ID, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.STRUCTURE_ID,
				String.valueOf(structureId));
		}
		if (templateId == null) {
			fields.put(OptionalJournalArticleCriteria.TEMPLATE_ID, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.TEMPLATE_ID,
				String.valueOf(templateId));
		}
		if ((displayDateGT == null)) {
			fields.put(OptionalJournalArticleCriteria.DISPLAY_DATE_GT, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.DISPLAY_DATE_GT,
				String.valueOf(displayDateGT.getTime()));
		}
		if ((displayDateLT == null)) {
			fields.put(OptionalJournalArticleCriteria.DISPLAY_DATE_LT, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.DISPLAY_DATE_LT,
				String.valueOf(displayDateLT.getTime()));
		}
		if (approved == null) {
			fields.put(OptionalJournalArticleCriteria.APPROVED, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.APPROVED, approved.toString());
		}
		if (expired == null) {
			fields.put(OptionalJournalArticleCriteria.EXPIRED, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.EXPIRED, expired.toString());
		}
		fields.put(
			OptionalJournalArticleCriteria.AND_OPERATOR,
			String.valueOf(andOperator));
		if ((reviewDate == null)) {
			fields.put(OptionalJournalArticleCriteria.REVIEW_DATE, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.REVIEW_DATE,
				String.valueOf(reviewDate.getTime()));
		}
		fields.put(
			OptionalJournalArticleCriteria.RANGE_START, String.valueOf(start));
		fields.put(
			OptionalJournalArticleCriteria.RANGE_END, String.valueOf(end));

		_addSearchFields(searchFields, fields);
		criteria.setSearchFieldValues(searchFields);
		criteria.setOrderByComparator(obc);

		ContentService contentService =
							MirageServiceFactory.getContentService();
		try {
			List<Content> contents = contentService.searchContents(criteria);

			return _getArticlesFromContents(contents);
		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}

		//
	}

	public List<JournalArticle> search(
			long companyId, long groupId, String articleId, Double version,
			String title, String description, String content, String type,
			String[] structureIds, String[] templateIds, Date displayDateGT,
			Date displayDateLT, Boolean approved, Boolean expired,
			Date reviewDate,boolean andOperator, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();
		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();
		String typeId = String.valueOf(groupId);
		ContentType contentType = new ContentType();
		contentType.setUuid(typeId);

		Map<String, String> fields = new HashMap<String, String>();
		fields.put(
			OptionalJournalArticleCriteria.FINDER,
			OptionalJournalArticleCriteria.FIND_BY_STRUCT_AND_TEMPLATE_IDS);
		fields.put(
			OptionalJournalArticleCriteria.GROUP_ID, String.valueOf(groupId));
		fields.put(
			OptionalJournalArticleCriteria.COMPANY_ID,
			String.valueOf(companyId));
		fields.put(OptionalJournalArticleCriteria.ARTICLE_ID, articleId);
		if (version == null) {
			fields.put(OptionalJournalArticleCriteria.VERSION, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.VERSION,
												String.valueOf(version));
		}
		fields.put(OptionalJournalArticleCriteria.TITLE, title);
		fields.put(OptionalJournalArticleCriteria.DESCRIPTION, description);
		fields.put(OptionalJournalArticleCriteria.CONTENT, content);
		fields.put(OptionalJournalArticleCriteria.TYPE, type);
		if ((displayDateGT == null)) {
			fields.put(OptionalJournalArticleCriteria.DISPLAY_DATE_GT, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.DISPLAY_DATE_GT,
				String.valueOf(displayDateGT.getTime()));
		}
		if ((displayDateLT == null)) {
			fields.put(OptionalJournalArticleCriteria.DISPLAY_DATE_LT, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.DISPLAY_DATE_LT,
				String.valueOf(displayDateLT.getTime()));
		}
		if (approved == null) {
			fields.put(OptionalJournalArticleCriteria.APPROVED, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.APPROVED, approved.toString());
		}
		if (expired == null) {
			fields.put(OptionalJournalArticleCriteria.EXPIRED, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.EXPIRED, expired.toString());
		}
		fields.put(
			OptionalJournalArticleCriteria.AND_OPERATOR,
			String.valueOf(andOperator));
		if ((reviewDate == null)) {
			fields.put(OptionalJournalArticleCriteria.REVIEW_DATE, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.REVIEW_DATE,
				String.valueOf(reviewDate.getTime()));
		}
		fields.put(
			OptionalJournalArticleCriteria.RANGE_START, String.valueOf(start));
		fields.put(
			OptionalJournalArticleCriteria.RANGE_END, String.valueOf(end));

		_addSearchFields(searchFields, fields);
		_addSearchFieldAndValues(
			searchFields, OptionalJournalArticleCriteria.STRUCTURE_IDS,
			structureIds);
		_addSearchFieldAndValues(
			searchFields, OptionalJournalArticleCriteria.TEMPLATE_IDS,
			templateIds);

		criteria.setSearchFieldValues(searchFields);
		criteria.setOrderByComparator(obc);

		ContentService contentService =
							MirageServiceFactory.getContentService();
		try {
			List<Content> contents = contentService.searchContents(criteria);

			return _getArticlesFromContents(contents);
		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}

	}

	public int searchCount(
			long companyId, long groupId, String keywords, Double version,
			String type, String structureId, String templateId,
			Date displayDateGT,Date displayDateLT, Boolean approved,
			Boolean expired, Date reviewDate)
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();
		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();
		String typeId = String.valueOf(groupId);
		ContentType contentType = new ContentType();
		contentType.setUuid(typeId);

		Map<String, String> fields = new HashMap<String, String>();
		fields.put(
			OptionalJournalArticleCriteria.FINDER,
			OptionalJournalArticleCriteria.COUNT_BY_KEYWORDS);
		fields.put(
			OptionalJournalArticleCriteria.GROUP_ID, String.valueOf(groupId));
		fields.put(
			OptionalJournalArticleCriteria.COMPANY_ID,
			String.valueOf(companyId));
		fields.put(OptionalJournalArticleCriteria.KEYWORDS, keywords);
		if (version == null) {
			fields.put(OptionalJournalArticleCriteria.VERSION, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.VERSION,
												String.valueOf(version));
		}
		fields.put(OptionalJournalArticleCriteria.TYPE, type);
		if (structureId == null) {
			fields.put(OptionalJournalArticleCriteria.STRUCTURE_ID, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.STRUCTURE_ID,
				String.valueOf(structureId));
		}
		if (templateId == null) {
			fields.put(OptionalJournalArticleCriteria.TEMPLATE_ID, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.TEMPLATE_ID,
				String.valueOf(templateId));
		}
		if ((displayDateGT == null)) {
			fields.put(OptionalJournalArticleCriteria.DISPLAY_DATE_GT, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.DISPLAY_DATE_GT,
				String.valueOf(displayDateGT.getTime()));
		}
		if ((displayDateLT == null)) {
			fields.put(OptionalJournalArticleCriteria.DISPLAY_DATE_LT, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.DISPLAY_DATE_LT,
				String.valueOf(displayDateLT.getTime()));
		}
		if (approved == null) {
			fields.put(OptionalJournalArticleCriteria.APPROVED, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.APPROVED, approved.toString());
		}
		if (expired == null) {
			fields.put(OptionalJournalArticleCriteria.EXPIRED, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.EXPIRED, expired.toString());
		}
		if ((reviewDate == null)) {
			fields.put(OptionalJournalArticleCriteria.REVIEW_DATE, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.REVIEW_DATE,
				String.valueOf(reviewDate.getTime()));
		}

		_addSearchFields(searchFields, fields);
		criteria.setSearchFieldValues(searchFields);

		ContentService contentService =
							MirageServiceFactory.getContentService();
		try {

			return contentService.contentSearchCount(contentType, criteria);

		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}

	}

	public int searchCount(
			long companyId, long groupId, String articleId, Double version,
			String title, String description, String content, String type,
			String structureId, String templateId, Date displayDateGT,
			Date displayDateLT, Boolean approved, Boolean expired,
			Date reviewDate,boolean andOperator)
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();
		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();
		String typeId = String.valueOf(groupId);
		ContentType contentType = new ContentType();
		contentType.setUuid(typeId);

		Map<String, String> fields = new HashMap<String, String>();
		fields.put(
			OptionalJournalArticleCriteria.FINDER,
			OptionalJournalArticleCriteria.
				COUNT_BY_C_G_A_V_T_D_C_T_S_T_D_A_E_R);
		fields.put(
			OptionalJournalArticleCriteria.GROUP_ID, String.valueOf(groupId));
		fields.put(
			OptionalJournalArticleCriteria.COMPANY_ID,
			String.valueOf(companyId));
		fields.put(OptionalJournalArticleCriteria.ARTICLE_ID, articleId);
		if (version == null) {
			fields.put(OptionalJournalArticleCriteria.VERSION, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.VERSION,
												String.valueOf(version));
		}
		fields.put(OptionalJournalArticleCriteria.TITLE, title);
		fields.put(OptionalJournalArticleCriteria.DESCRIPTION, description);
		fields.put(OptionalJournalArticleCriteria.CONTENT, content);
		fields.put(OptionalJournalArticleCriteria.TYPE, type);
		if (structureId == null) {
			fields.put(OptionalJournalArticleCriteria.STRUCTURE_ID, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.STRUCTURE_ID,
				String.valueOf(structureId));
		}
		if (templateId == null) {
			fields.put(OptionalJournalArticleCriteria.TEMPLATE_ID, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.TEMPLATE_ID,
				String.valueOf(templateId));
		}
		if ((displayDateGT == null)) {
			fields.put(OptionalJournalArticleCriteria.DISPLAY_DATE_GT, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.DISPLAY_DATE_GT,
				String.valueOf(displayDateGT.getTime()));
		}
		if ((displayDateLT == null)) {
			fields.put(OptionalJournalArticleCriteria.DISPLAY_DATE_LT, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.DISPLAY_DATE_LT,
				String.valueOf(displayDateLT.getTime()));
		}
		if (approved == null) {
			fields.put(OptionalJournalArticleCriteria.APPROVED, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.APPROVED, approved.toString());
		}
		if (expired == null) {
			fields.put(OptionalJournalArticleCriteria.EXPIRED, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.EXPIRED, expired.toString());
		}
		fields.put(
			OptionalJournalArticleCriteria.AND_OPERATOR,
			String.valueOf(andOperator));
		if ((reviewDate == null)) {
			fields.put(OptionalJournalArticleCriteria.REVIEW_DATE, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.REVIEW_DATE,
				String.valueOf(reviewDate.getTime()));
		}

		_addSearchFields(searchFields, fields);
		criteria.setSearchFieldValues(searchFields);

		ContentService contentService =
							MirageServiceFactory.getContentService();
		try {

			return contentService.contentSearchCount(contentType, criteria);

		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}

	}

	public int searchCount(
			long companyId, long groupId, String articleId, Double version,
			String title, String description, String content, String type,
			String[] structureIds, String[] templateIds, Date displayDateGT,
			Date displayDateLT, Boolean approved, Boolean expired,
			Date reviewDate,boolean andOperator)
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();
		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();
		String typeId = String.valueOf(groupId);
		ContentType contentType = new ContentType();
		contentType.setUuid(typeId);

		Map<String, String> fields = new HashMap<String, String>();
		fields.put(
			OptionalJournalArticleCriteria.FINDER,
			OptionalJournalArticleCriteria.COUNT_BY_STRUCT_AND_TEMPLATE_IDS);
		fields.put(
			OptionalJournalArticleCriteria.GROUP_ID, String.valueOf(groupId));
		fields.put(
			OptionalJournalArticleCriteria.COMPANY_ID,
			String.valueOf(companyId));
		fields.put(OptionalJournalArticleCriteria.ARTICLE_ID, articleId);
		if (version == null) {
			fields.put(OptionalJournalArticleCriteria.VERSION, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.VERSION,
												String.valueOf(version));
		}
		fields.put(OptionalJournalArticleCriteria.TITLE, title);
		fields.put(OptionalJournalArticleCriteria.DESCRIPTION, description);
		fields.put(OptionalJournalArticleCriteria.CONTENT, content);
		fields.put(OptionalJournalArticleCriteria.TYPE, type);
		if ((displayDateGT == null)) {
			fields.put(OptionalJournalArticleCriteria.DISPLAY_DATE_GT, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.DISPLAY_DATE_GT,
				String.valueOf(displayDateGT.getTime()));
		}
		if ((displayDateLT == null)) {
			fields.put(OptionalJournalArticleCriteria.DISPLAY_DATE_LT, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.DISPLAY_DATE_LT,
				String.valueOf(displayDateLT.getTime()));
		}
		if (approved == null) {
			fields.put(OptionalJournalArticleCriteria.APPROVED, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.APPROVED, approved.toString());
		}
		if (expired == null) {
			fields.put(OptionalJournalArticleCriteria.EXPIRED, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.EXPIRED, expired.toString());
		}
		fields.put(
			OptionalJournalArticleCriteria.AND_OPERATOR,
			String.valueOf(andOperator));
		if ((reviewDate == null)) {
			fields.put(OptionalJournalArticleCriteria.REVIEW_DATE, null);
		}
		else {
			fields.put(
				OptionalJournalArticleCriteria.REVIEW_DATE,
				String.valueOf(reviewDate.getTime()));
		}

		_addSearchFields(searchFields, fields);
		_addSearchFieldAndValues(
			searchFields, OptionalJournalArticleCriteria.STRUCTURE_IDS,
			structureIds);
		_addSearchFieldAndValues(
			searchFields, OptionalJournalArticleCriteria.TEMPLATE_IDS,
			templateIds);

		criteria.setSearchFieldValues(searchFields);

		ContentService contentService =
							MirageServiceFactory.getContentService();
		try {

			return contentService.contentSearchCount(contentType, criteria);
		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}

	}

	public JournalArticle updateArticle(
			long userId, long groupId, String articleId, double version,
			boolean incrementVersion, String title, String description,
			String content, String type, String structureId, String templateId,
			int displayDateMonth, int displayDateDay, int displayDateYear,
			int displayDateHour, int displayDateMinute, int expirationDateMonth,
			int expirationDateDay, int expirationDateYear,
			int expirationDateHour,int expirationDateMinute,
			boolean neverExpire, int reviewDateMonth,int reviewDateDay,
			int reviewDateYear, int reviewDateHour,int reviewDateMinute,
			boolean neverReview, boolean indexable,boolean smallImage,
			String smallImageURL, File smallFile,Map<String, byte[]> images,
			String articleURL,PortletPreferences prefs, String[] tagsEntries)
		throws PortalException, SystemException {

		// Article

		JournalArticle article = new JournalArticleImpl();

		article.setUserId(userId);
		article.setGroupId(groupId);
		article.setArticleId(articleId);
		article.setVersion(version);
		article.setTitle(title);
		article.setDescription(description);
		article.setContent(content);
		article.setType(type);
		article.setStructureId(structureId);
		article.setTemplateId(templateId);
		article.setApproved(false);
		article.setIndexable(indexable);
		article.setSmallImage(smallImage);
		article.setSmallImageId(counterLocalService.increment());
		article.setSmallImageURL(smallImageURL);

		/* Create a Mirage Content object using the JournalArticle */
		JournalArticleContent articleContent =
			new JournalArticleContent(article);

		JournalArticleContent.CreationAttributes creationAttributes =
										articleContent.new CreationAttributes();

		creationAttributes.setArticleURL(articleURL);
		creationAttributes.setPrefs(prefs);
		creationAttributes.setTagsEntries(tagsEntries);
		creationAttributes.setDisplayDateDay(displayDateDay);
		creationAttributes.setDisplayDateHour(displayDateHour);
		creationAttributes.setDisplayDateMinute(displayDateMinute);
		creationAttributes.setDisplayDateMonth(displayDateMonth);
		creationAttributes.setDisplayDateYear(displayDateYear);
		creationAttributes.setExpirationDateDay(expirationDateDay);
		creationAttributes.setExpirationDateHour(expirationDateHour);
		creationAttributes.setExpirationDateMinute(expirationDateMinute);
		creationAttributes.setExpirationDateMonth(expirationDateMonth);
		creationAttributes.setExpirationDateYear(expirationDateYear);
		creationAttributes.setNeverExpire(neverExpire);
		creationAttributes.setReviewDateDay(reviewDateDay);
		creationAttributes.setReviewDateHour(reviewDateHour);
		creationAttributes.setReviewDateMinute(reviewDateMinute);
		creationAttributes.setReviewDateMonth(reviewDateMonth);
		creationAttributes.setReviewDateYear(reviewDateYear);
		creationAttributes.setNeverReview(neverReview);
		creationAttributes.setIncrementVersion(incrementVersion);
		creationAttributes.setSmallFile(smallFile);
		creationAttributes.setImages(images);

		articleContent.setCreationAttributes(creationAttributes);
		/* Get the Project Mirage service */
		ContentService contentService =
							MirageServiceFactory.getContentService();
		try {
			contentService.updateContent(articleContent);
		}
		catch (CMSException ce) {
			_throwException(ce);
		}
		article = articleContent.getArticle();

		return article;
	}

	public JournalArticle updateContent(
			long groupId, String articleId, double version, String content)
		throws PortalException, SystemException {

		JournalArticle article = getArticle(groupId, articleId, version);

		article.setContent(content);
		JournalArticleContent articleContent =
			new JournalArticleContent(article);
		ContentService contentService =
							MirageServiceFactory.getContentService();
		try {
			contentService.updateContent(articleContent, null);
		}
		catch (CMSException ce) {
			_throwException(ce);
		}
		article = articleContent.getArticle();
		return article;
	}

	public void updateTagsAsset(
			long userId, JournalArticle article, String[] tagsEntries)
		throws PortalException, SystemException {

		// Get the earliest display date and latest expiration date among
		// all article versions

		Date[] dateInterval =
			getDateInterval(
				article.getGroupId(), article.getArticleId(),
				article.getDisplayDate(), article.getExpirationDate());

		Date displayDate = dateInterval[0];
		Date expirationDate = dateInterval[1];

		tagsAssetLocalService.updateAsset(
			userId, article.getGroupId(), JournalArticle.class.getName(),
			article.getResourcePrimKey(), tagsEntries, null, null, displayDate,
			expirationDate, ContentTypes.TEXT_HTML, article.getTitle(),
			article.getDescription(), null, null, 0, 0, null, false);
	}

	protected void checkStructure(JournalArticle article)
		throws DocumentException, PortalException, SystemException {

		JournalStructure structure =
			journalStructureLocalService.getStructure(
				article.getGroupId(), article.getStructureId());

		String content = GetterUtil.getString(article.getContent());

		SAXReader reader = new SAXReader();

		Document contentDoc = reader.read(new StringReader(content));
		Document xsdDoc = reader.read(new StringReader(structure.getXsd()));

		try {
			checkStructure(contentDoc, xsdDoc.getRootElement());
		}
		catch (StructureXsdException sxsde) {
			long groupId = article.getGroupId();
			String articleId = article.getArticleId();
			double version = article.getVersion();

			_log.error("Article {groupId=" + groupId + ", articleId=" +
				articleId + ", version=" + version +
				"} has content that does not match its structure: " +
				sxsde.getMessage());
		}
	}

	protected void checkStructure(Document contentDoc, Element root)
		throws PortalException {

		for (Element el : (List<Element>) root.elements()) {
			checkStructureField(el, contentDoc);

			checkStructure(contentDoc, el);
		}
	}

	protected void checkStructureField(Element el, Document contentDoc)
		throws PortalException {

		StringBuilder elPath = new StringBuilder();

		elPath.append(el.attributeValue("name"));

		Element elParent = el.getParent();

		for (;;) {
			if ((elParent == null) || (elParent.getName().equals("root"))) {

				break;
			}

			elPath.insert(0, elParent.attributeValue("name")
												+ StringPool.COMMA);

			elParent = elParent.getParent();
		}

		String[] elPathNames = StringUtil.split(elPath.toString());

		Element contentEl = contentDoc.getRootElement();

		for (int i = 0; i < elPathNames.length; i++) {
			boolean foundEl = false;

			for (Element tempEl : (List<Element>) contentEl.elements()) {
				if (elPathNames[i].equals(tempEl.attributeValue(
					"name", StringPool.BLANK))) {

					contentEl = tempEl;
					foundEl = true;

					break;
				}
			}

			if (!foundEl) {
				String elType =
					contentEl.attributeValue("type", StringPool.BLANK);

				if (!elType.equals("list") && !elType.equals("multi-list")) {
					throw new StructureXsdException(elPath.toString());
				}

				break;
			}
		}
	}

	protected Date[] getDateInterval(
		long groupId, String articleId, Date earliestDisplayDate,
		Date latestExpirationDate)
		throws SystemException {

		Date[] dateInterval = new Date[2];

		List<JournalArticle> articles = _getArticlesByG_A_A(groupId, articleId);

		boolean expiringArticle = true;

		if (latestExpirationDate == null) {
			expiringArticle = false;
		}

		for (JournalArticle article : articles) {
			if ((earliestDisplayDate == null) ||
				((article.getDisplayDate() != null) &&
						earliestDisplayDate.after(article.getDisplayDate()))) {

				earliestDisplayDate = article.getDisplayDate();
			}

			if (expiringArticle &&
				((latestExpirationDate == null) ||
						((article.getExpirationDate() != null) &&
								latestExpirationDate.before(
										article.getExpirationDate())))) {

				latestExpirationDate = article.getExpirationDate();
			}

			if (expiringArticle && (article.getExpirationDate() == null)) {
				latestExpirationDate = null;
				expiringArticle = false;
			}
		}

		dateInterval[0] = earliestDisplayDate;
		dateInterval[1] = latestExpirationDate;

		return dateInterval;
	}

	protected void sendEmail(
			JournalArticle article, String articleURL, PortletPreferences prefs,
			String emailType)
		throws IOException, PortalException, SystemException {

		if (prefs == null) {
			return;
		}
		else if (emailType.equals("denied") &&
			JournalUtil.getEmailArticleApprovalDeniedEnabled(prefs)) {
		}
		else if (emailType.equals("granted") &&
			JournalUtil.getEmailArticleApprovalGrantedEnabled(prefs)) {
		}
		else if (emailType.equals("requested") &&
			JournalUtil.getEmailArticleApprovalRequestedEnabled(prefs)) {
		}
		else if (emailType.equals("review") &&
			JournalUtil.getEmailArticleReviewEnabled(prefs)) {
		}
		else {
			return;
		}

		Company company =
			companyPersistence.findByPrimaryKey(article.getCompanyId());

		User user = userPersistence.findByPrimaryKey(article.getUserId());

		articleURL +=
			"&groupId=" + article.getGroupId() + "&articleId=" +
				article.getArticleId() + "&version=" + article.getVersion();

		String portletName =
			PortalUtil.getPortletTitle(PortletKeys.JOURNAL, user);

		String fromName = JournalUtil.getEmailFromName(prefs);
		String fromAddress = JournalUtil.getEmailFromAddress(prefs);

		String toName = user.getFullName();
		String toAddress = user.getEmailAddress();

		if (emailType.equals("requested") || emailType.equals("review")) {

			String tempToName = fromName;
			String tempToAddress = fromAddress;

			fromName = toName;
			fromAddress = toAddress;

			toName = tempToName;
			toAddress = tempToAddress;
		}

		String subject = null;
		String body = null;

		if (emailType.equals("denied")) {
			subject = JournalUtil.getEmailArticleApprovalDeniedSubject(prefs);
			body = JournalUtil.getEmailArticleApprovalDeniedBody(prefs);
		}
		else if (emailType.equals("granted")) {
			subject = JournalUtil.getEmailArticleApprovalGrantedSubject(prefs);
			body = JournalUtil.getEmailArticleApprovalGrantedBody(prefs);
		}
		else if (emailType.equals("requested")) {
			subject =
				JournalUtil.getEmailArticleApprovalRequestedSubject(prefs);
			body = JournalUtil.getEmailArticleApprovalRequestedBody(prefs);
		}
		else if (emailType.equals("review")) {
			subject = JournalUtil.getEmailArticleReviewSubject(prefs);
			body = JournalUtil.getEmailArticleReviewBody(prefs);
		}

		subject =
			StringUtil.replace(subject, new String[] {
				"[$ARTICLE_ID$]", "[$ARTICLE_TITLE$]", "[$ARTICLE_URL$]",
				"[$ARTICLE_VERSION$]", "[$FROM_ADDRESS$]", "[$FROM_NAME$]",
				"[$PORTAL_URL$]", "[$PORTLET_NAME$]", "[$TO_ADDRESS$]",
				"[$TO_NAME$]"
			}, new String[] {
				article.getArticleId(), article.getTitle(), articleURL,
				String.valueOf(article.getVersion()), fromAddress, fromName,
				company.getVirtualHost(), portletName, toAddress, toName

				,
			});

		body =
			StringUtil.replace(body, new String[] {
				"[$ARTICLE_ID$]", "[$ARTICLE_TITLE$]", "[$ARTICLE_URL$]",
				"[$ARTICLE_VERSION$]", "[$FROM_ADDRESS$]", "[$FROM_NAME$]",
				"[$PORTAL_URL$]", "[$PORTLET_NAME$]", "[$TO_ADDRESS$]",
				"[$TO_NAME$]"
			}, new String[] {
				article.getArticleId(), article.getTitle(), articleURL,
				String.valueOf(article.getVersion()), fromAddress, fromName,
				company.getVirtualHost(), portletName, toAddress, toName

				,

			});

		InternetAddress from = new InternetAddress(fromAddress, fromName);

		InternetAddress to = new InternetAddress(toAddress, toName);

		MailMessage message = new MailMessage(from, to, subject, body, true);

		mailService.sendEmail(message);
	}

	private void _addSearchField(
		List<SearchFieldValue> fieldList, String fieldName, String fieldValue) {

		SearchFieldValue searchField = new SearchFieldValue();
		searchField.setFieldName(fieldName);
		searchField.setFieldValues(new String[] {
			fieldValue
		});
		fieldList.add(searchField);
	}

	private void _addSearchFieldAndValues(
			List<SearchFieldValue> fieldList, String fieldName,
			String[] fieldValues) {

		SearchFieldValue searchField = new SearchFieldValue();
		searchField.setFieldName(fieldName);
		searchField.setFieldValues(fieldValues);
		fieldList.add(searchField);
	}

	private void _addSearchFields(
			List<SearchFieldValue> fieldList,
			Map<String, String> nameValues) {

		Iterator<String> iter = nameValues.keySet().iterator();
		while (iter.hasNext()) {
			String name = iter.next();
			String value = nameValues.get(name);
			_addSearchField(fieldList, name, value);
		}
	}

	private List<JournalArticle> _getArticlesByG_A_A(
			long groupId, String articleId)
		throws SystemException {

		SearchCriteria criteria = new SearchCriteria();
		List<SearchFieldValue> searchFields = new ArrayList<SearchFieldValue>();
		List<JournalArticle> articles = null;

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.FINDER,
			OptionalJournalArticleCriteria.FIND_BY_G_A_A);

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.GROUP_ID,
			String.valueOf(groupId));

		_addSearchField(
			searchFields, OptionalJournalArticleCriteria.ARTICLE_ID, articleId);
		criteria.setSearchFieldValues(searchFields);

		criteria.setSearchFieldValues(searchFields);

		ContentService contentService =
							MirageServiceFactory.getContentService();
		try {
			List<Content> contents = contentService.searchContents(criteria);

			articles = _getArticlesFromContents(contents);
		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}
		return articles;

	}

	private List<JournalArticle> _getArticlesFromContents(
			List<Content> contents) {

		List<JournalArticle> articles = new ArrayList<JournalArticle>();
		for (Content content : contents) {
			articles.add(((JournalArticleContent) content).getArticle());
		}

		return articles;
	}

	private void _throwException(CMSException ex)
		throws PortalException, SystemException {

		Throwable cause = ex.getCause();
		if (cause != null) {
			if (cause instanceof PortalException) {
				throw (PortalException) cause;
			}
			else if (cause instanceof SystemException) {
				throw (SystemException) cause;
			}
		}
	}

	private List<JournalArticle> _getArticleByReviewDate(
			Date now, Date reviewDateGT)
		throws SystemException {

		try {
			SearchCriteria reviewCriteria = new SearchCriteria();
			List<SearchFieldValue> searchFields =
				new ArrayList<SearchFieldValue>();

			Map<String, String> fields = new HashMap<String, String>();
			fields.put(
				OptionalJournalArticleCriteria.FINDER,
				OptionalJournalArticleCriteria.FIND_BY_REVIEW_DATE);
			if (now == null) {
				fields.put(OptionalJournalArticleCriteria.REVIEW_DATE_LT, null);
			}
			else {
				fields.put(
					OptionalJournalArticleCriteria.REVIEW_DATE_LT,
					String.valueOf(now.getTime()));
			}
			if (reviewDateGT == null) {
				fields.put(OptionalJournalArticleCriteria.REVIEW_DATE_GT, null);
			}
			else {
				fields.put(
					OptionalJournalArticleCriteria.REVIEW_DATE_GT,
					String.valueOf(reviewDateGT.getTime()));
			}

			_addSearchFields(searchFields, fields);
			reviewCriteria.setSearchFieldValues(searchFields);

			ContentService contentService =
								MirageServiceFactory.getContentService();
			List<Content> contents =
				contentService.searchContents(reviewCriteria);
			List<JournalArticle> articles = _getArticlesFromContents(contents);
			return articles;
		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}

	}

	private List<JournalArticle> _getArticlesByCompanyId(long companyId)
		throws SystemException {

		try {
			SearchCriteria criteria = new SearchCriteria();
			List<SearchFieldValue> searchFields =
				new ArrayList<SearchFieldValue>();

			Map<String, String> fields = new HashMap<String, String>();
			fields.put(
				OptionalJournalArticleCriteria.FINDER,
				OptionalJournalArticleCriteria.FIND_BY_COMPANY_ID);
			fields.put(
				OptionalJournalArticleCriteria.COMPANY_ID,
				String.valueOf(companyId));

			_addSearchFields(searchFields, fields);
			criteria.setSearchFieldValues(searchFields);

			ContentService contentService =
								MirageServiceFactory.getContentService();
			List<Content> contents = contentService.searchContents(criteria);
			List<JournalArticle> articles = _getArticlesFromContents(contents);
			return articles;
		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}
	}

	private List<JournalArticle> _getArticlesByExpirationDate(
			Date now, Date expirationDateGT)
		throws SystemException {

		try {

			SearchCriteria expireCriteria = new SearchCriteria();
			List<SearchFieldValue> searchFields =
				new ArrayList<SearchFieldValue>();

			Map<String, String> fields = new HashMap<String, String>();
			fields.put(
				OptionalJournalArticleCriteria.FINDER,
				OptionalJournalArticleCriteria.FIND_BY_EXPIRATION_DATE);
			if (now == null) {
				fields.put(
					OptionalJournalArticleCriteria.EXPIRATION_DATE_LT, null);
			}
			else {
				fields.put(
					OptionalJournalArticleCriteria.EXPIRATION_DATE_LT,
					String.valueOf(now.getTime()));
			}
			if (expirationDateGT == null) {
				fields.put(
					OptionalJournalArticleCriteria.EXPIRATION_DATE_GT, null);
			}
			else {
				fields.put(
					OptionalJournalArticleCriteria.EXPIRATION_DATE_GT,
					String.valueOf(expirationDateGT.getTime()));
			}
			fields.put(
				OptionalJournalArticleCriteria.EXPIRED, String.valueOf(false));
			_addSearchFields(searchFields, fields);
			expireCriteria.setSearchFieldValues(searchFields);

			ContentService contentService =
								MirageServiceFactory.getContentService();
			List<Content> contents =
				contentService.searchContents(expireCriteria);
			List<JournalArticle> articles = _getArticlesFromContents(contents);
			return articles;
		}
		catch (CMSException ex) {
			throw (SystemException) ex.getCause();
		}

	}

	private static final String _TOKEN_PAGE_BREAK =
		PropsUtil.get(PropsKeys.JOURNAL_ARTICLE_TOKEN_PAGE_BREAK);
	private static Log _log =
		LogFactory.getLog(JournalArticleLocalServiceImpl.class);

}