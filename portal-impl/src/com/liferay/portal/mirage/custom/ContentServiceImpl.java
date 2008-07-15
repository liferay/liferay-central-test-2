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

/**
 * The contents of this file are subject to the terms of the Common Development
 * and Distribution License (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at http://www.sun.com/cddl/cddl.html and
 * legal/CDDLv1.0.txt. See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL Header Notice in each file
 * and include the License file at legal/CDDLv1.0.txt.
 *
 * If applicable, add the following below the CDDL Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Copyright 2008 Sun Microsystems Inc. All rights reserved.
 */

package com.liferay.portal.mirage.custom;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.servlet.ImageServletTokenUtil;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.mirage.model.JournalArticleContent;
import com.liferay.portal.mirage.model.OptionalJournalArticleCriteria;
import com.liferay.portal.mirage.model.OptionalJournalStructureCriteria;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Image;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.service.ImageLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.persistence.CompanyUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.journal.ArticleContentException;
import com.liferay.portlet.journal.ArticleDisplayDateException;
import com.liferay.portlet.journal.ArticleExpirationDateException;
import com.liferay.portlet.journal.ArticleIdException;
import com.liferay.portlet.journal.ArticleReviewDateException;
import com.liferay.portlet.journal.ArticleSmallImageNameException;
import com.liferay.portlet.journal.ArticleSmallImageSizeException;
import com.liferay.portlet.journal.ArticleTitleException;
import com.liferay.portlet.journal.ArticleTypeException;
import com.liferay.portlet.journal.DuplicateArticleIdException;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.NoSuchArticleResourceException;
import com.liferay.portlet.journal.NoSuchTemplateException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.journal.model.impl.JournalArticleImpl;
import com.liferay.portlet.journal.service.JournalArticleImageLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleResourceLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalContentSearchLocalServiceUtil;
import com.liferay.portlet.journal.service.persistence.JournalArticleFinderUtil;
import com.liferay.portlet.journal.service.persistence.JournalArticleUtil;
import com.liferay.portlet.journal.service.persistence.JournalStructureUtil;
import com.liferay.portlet.journal.service.persistence.JournalTemplateUtil;
import com.liferay.portlet.journal.util.Indexer;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.ratings.service.RatingsStatsLocalServiceUtil;
import com.liferay.portlet.tags.service.TagsAssetLocalServiceUtil;
import com.liferay.util.MathUtil;

import com.sun.portal.cms.mirage.exception.CMSException;
import com.sun.portal.cms.mirage.exception.SearchNotSupportedForFieldException;
import com.sun.portal.cms.mirage.exception.TemplateNotFoundException;
import com.sun.portal.cms.mirage.model.core.User;
import com.sun.portal.cms.mirage.model.custom.Content;
import com.sun.portal.cms.mirage.model.custom.ContentType;
import com.sun.portal.cms.mirage.model.custom.OptionalCriteria;
import com.sun.portal.cms.mirage.model.custom.VersionableContent;
import com.sun.portal.cms.mirage.model.search.SearchCriteria;
import com.sun.portal.cms.mirage.model.search.SearchFieldValue;
import com.sun.portal.cms.mirage.service.custom.ContentService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.mail.internet.InternetAddress;

import javax.portlet.PortletPreferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * <a href="ContentServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Joshna Reddy
 *
 */
public class ContentServiceImpl implements ContentService {

	public void addArticleResources(
			JournalArticle article, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addResources(
			article.getCompanyId(), article.getGroupId(), article.getUserId(),
				JournalArticle.class.getName(), article.getResourcePrimKey(),
					false, addCommunityPermissions, addGuestPermissions);
	}

	public void addArticleResources(
			JournalArticle article, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addModelResources(
			article.getCompanyId(), article.getGroupId(), article.getUserId(),
				JournalArticle.class.getName(), article.getResourcePrimKey(),
					communityPermissions, guestPermissions);
	}

	public void addArticleResources(
			long groupId, String articleId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		JournalArticle article = getLatestArticle(groupId, articleId);

		addArticleResources(article, communityPermissions, guestPermissions);
	}

	public void checkinContent(Content arg0)
		throws CMSException, TemplateNotFoundException {

		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void checkinContent(Content content, String username)
		throws CMSException, TemplateNotFoundException {

	}

	public VersionableContent checkoutContent(String arg0, String arg1)
		throws CMSException {

		throw new UnsupportedOperationException("Not supported yet.");
	}

	public VersionableContent checkoutContent(
			String contentName, String contentTypeName, String username)
		throws CMSException {

		return null;
	}

	public int contentSearchCount(
			ContentType contentType, SearchCriteria searchCriteria)
		throws CMSException {

		int result = 0;
		List<SearchFieldValue> searchFields =
			searchCriteria.getSearchFieldValues();

		String finder = _findFieldValue(
							searchFields,
								OptionalJournalArticleCriteria.FINDER);
		try {
			if (OptionalJournalArticleCriteria.COUNT_BY_G_S.equals(finder)) {

				String groupIdField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.GROUP_ID);
				long groupId = Long.parseLong(groupIdField);

				String structureId = contentType.getUuid();
				result = _getStructureArticlesCount(groupId, structureId);

			}
		}
		catch (SystemException se) {
			throw new CMSException(se.getMessage(), se);
		}

		return result;
	}

	public int contentSearchCount(SearchCriteria searchCriteria)
		throws CMSException {

		int result = 0;
		List<SearchFieldValue> searchFields =
			searchCriteria.getSearchFieldValues();

		try {
			String finder =
				_findFieldValue(
					searchFields, OptionalJournalArticleCriteria.FINDER);

			if (OptionalJournalArticleCriteria.FIND_BY_GROUP.equals(finder)) {

				String groupIdField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.GROUP_ID);
				long groupId = Long.parseLong(groupIdField);
				result = _getArticlesCount(groupId);

			}
			else if (OptionalJournalArticleCriteria.COUNT_BY_KEYWORDS.equals(
					finder)) {

				String groupIdField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.GROUP_ID);
				long groupId = Long.parseLong(groupIdField);
				long companyId =
					Long.parseLong(_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.COMPANY_ID));
				String keywords =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.KEYWORDS);
				String versionField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.VERSION);
				Double version = null;

				if (versionField != null) {
					version = Double.parseDouble(versionField);
				}

				String type =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.TYPE);
				String structureId =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.STRUCTURE_ID);
				String templateId =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.TEMPLATE_ID);

				String displayDateGTField =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.DISPLAY_DATE_GT);
				Date displayDateGT = null;

				if (displayDateGTField != null) {
					displayDateGT =
						new Date(Long.parseLong(displayDateGTField));
				}

				String displayDateLTField =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.DISPLAY_DATE_LT);
				Date displayDateLT = null;

				if (displayDateLTField != null) {
					displayDateLT =
						new Date(Long.parseLong(displayDateLTField));
				}

				String approvedField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.APPROVED);
				Boolean approved = null;

				if (approvedField != null) {
					approved = Boolean.parseBoolean(approvedField);
				}

				String expiredField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.EXPIRED);
				Boolean expired = null;

				if (expiredField != null) {
					expired = Boolean.parseBoolean(expiredField);
				}

				String reviewDateField =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.REVIEW_DATE);
				Date reviewDate = null;

				if (reviewDateField != null) {
					reviewDate = new Date(Long.parseLong(reviewDateField));
				}

				result = _getArticlesCount(
							companyId, groupId, keywords, version, type,
								structureId, templateId, displayDateGT,
									displayDateLT, approved, expired,
										reviewDate);
			}
			else if (
				OptionalJournalArticleCriteria
						.COUNT_BY_C_G_A_V_T_D_C_T_S_T_D_A_E_R.equals(finder)) {

				String groupIdField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.GROUP_ID);
				long groupId = Long.parseLong(groupIdField);
				long companyId =
					Long.parseLong(_findFieldValue(
						searchFields,
								OptionalJournalArticleCriteria.COMPANY_ID));
				String articleId =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.ARTICLE_ID);

				String versionField =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.VERSION);
				Double version = null;

				if (versionField != null) {
					version = Double.parseDouble(versionField);
				}

				String title =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.TITLE);
				String description =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.DESCRIPTION);
				String content =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.CONTENT);
				String type =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.TYPE);
				String structureId =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.STRUCTURE_ID);
				String templateId =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.TEMPLATE_ID);

				String displayDateGTField =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.DISPLAY_DATE_GT);
				Date displayDateGT = null;

				if (displayDateGTField != null) {
					displayDateGT =
						new Date(Long.parseLong(displayDateGTField));
				}

				String displayDateLTField =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.DISPLAY_DATE_LT);
				Date displayDateLT = null;

				if (displayDateLTField != null) {
					displayDateLT =
						new Date(Long.parseLong(displayDateLTField));
				}

				String approvedField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.APPROVED);
				Boolean approved = null;

				if (approvedField != null) {
					approved = Boolean.parseBoolean(approvedField);
				}

				String expiredField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.EXPIRED);
				Boolean expired = null;

				if (expiredField != null) {
					expired = Boolean.parseBoolean(expiredField);
				}

				String reviewDateField =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.REVIEW_DATE);
				Date reviewDate = null;

				if (reviewDateField != null) {
					reviewDate = new Date(Long.parseLong(reviewDateField));
				}

				boolean andOperator =
					Boolean.parseBoolean(_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.AND_OPERATOR));

				result =
					_getArticlesCount(
						companyId, groupId, articleId, version, title,
							description, content, type, structureId, templateId,
								displayDateGT, displayDateLT, approved, expired,
									reviewDate, andOperator);

			}
			else if (OptionalJournalArticleCriteria
							.COUNT_BY_STRUCT_AND_TEMPLATE_IDS.equals(finder)) {
				String groupIdField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.GROUP_ID);
				long groupId = Long.parseLong(groupIdField);
				long companyId =
					Long.parseLong(_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.COMPANY_ID));
				String articleId =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.ARTICLE_ID);

				String versionField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.VERSION);
				Double version = null;

				if (versionField != null) {
					version = Double.parseDouble(versionField);
				}

				String title =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.TITLE);
				String description =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.DESCRIPTION);
				String content =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.CONTENT);
				String type =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.TYPE);
				String[] structureIds =
					_findFieldValues(
						searchFields,
							OptionalJournalArticleCriteria.STRUCTURE_IDS);
				String[] templateIds =
					_findFieldValues(
						searchFields,
							OptionalJournalArticleCriteria.TEMPLATE_IDS);

				String displayDateGTField =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.DISPLAY_DATE_GT);
				Date displayDateGT = null;

				if (displayDateGTField != null) {
					displayDateGT =
						new Date(Long.parseLong(displayDateGTField));
				}

				String displayDateLTField =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.DISPLAY_DATE_LT);
				Date displayDateLT = null;

				if (displayDateLTField != null) {
					displayDateLT =
						new Date(Long.parseLong(displayDateLTField));
				}

				String approvedField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.APPROVED);
				Boolean approved = null;

				if (approvedField != null) {
					approved = Boolean.parseBoolean(approvedField);
				}

				String expiredField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.EXPIRED);
				Boolean expired = null;

				if (expiredField != null) {
					expired = Boolean.parseBoolean(expiredField);
				}

				String reviewDateField =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.REVIEW_DATE);
				Date reviewDate = null;

				if (reviewDateField != null) {
					reviewDate = new Date(Long.parseLong(reviewDateField));
				}

				boolean andOperator =
					Boolean.parseBoolean(_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.AND_OPERATOR));

				result =
					_getArticlesCount(
						companyId, groupId, articleId, version, title,
							description, content, type, structureIds,
								templateIds, displayDateGT, displayDateLT,
									approved, expired, reviewDate, andOperator);
			}
			else if (OptionalJournalArticleCriteria
							.COUNT_BY_G_T.equals(finder)) {

				String groupIdField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.GROUP_ID);
				long groupId = Long.parseLong(groupIdField);

				String templateId =
					_findFieldValue(
						searchFields,
						OptionalJournalArticleCriteria.TEMPLATE_ID);
				result = _getTemplateArticlesCount(groupId, templateId);

			}
		}
		catch (SystemException ex) {
			throw new CMSException(ex.getMessage(), ex);
		}
		return result;
	}

	public void createContent(Content content)
		throws CMSException, TemplateNotFoundException {

		JournalArticleContent articleContent = (JournalArticleContent) content;

		JournalArticle article = articleContent.getArticle();
		JournalArticleContent.CreationAttributes creationAttributes =
			articleContent.getCreationAttributes();

		try {

			com.liferay.portal.model.User user =
				UserUtil.findByPrimaryKey(article.getUserId());
			String articleId = article.getArticleId().trim().toUpperCase();

			Date displayDate =
				PortalUtil.getDate(
					creationAttributes.getDisplayDateMonth(),
					creationAttributes.getDisplayDateDay(),
					creationAttributes.getDisplayDateYear(),
					creationAttributes.getDisplayDateHour(),
					creationAttributes.getDisplayDateMinute(),
					user.getTimeZone(),
					new ArticleDisplayDateException());

			Date expirationDate = null;

			if (!creationAttributes.isNeverExpire()) {
				expirationDate =
					PortalUtil.getDate(
						creationAttributes.getExpirationDateMonth(),
						creationAttributes.getExpirationDateDay(),
						creationAttributes.getExpirationDateYear(),
						creationAttributes.getExpirationDateHour(),
						creationAttributes.getExpirationDateMinute(),
						user.getTimeZone(),
						new ArticleExpirationDateException());
			}

			Date reviewDate = null;

			if (!creationAttributes.isNeverReview()) {
				reviewDate =
					PortalUtil.getDate(
						creationAttributes.getReviewDateMonth(),
						creationAttributes.getReviewDateDay(),
						creationAttributes.getReviewDateYear(),
						creationAttributes.getReviewDateYear(),
						creationAttributes.getReviewDateMinute(),
						user.getTimeZone(),
						new ArticleReviewDateException());
			}

			byte[] smallBytes = null;

			try {
				smallBytes =
					FileUtil.getBytes(creationAttributes.getSmallFile());
			}
			catch (IOException ioe) {

			}

			Date now = new Date();

			validate(
				article.getGroupId(), articleId,
					creationAttributes.isAutoArticleId(), article.getVersion(),
						article.getTitle(), article.getContent(),
							article.getType(), article.getStructureId(),
								article.getTemplateId(),
									article.getSmallImage(),
										article.getSmallImageURL(),
											creationAttributes.getSmallFile(),
												smallBytes);

			if (creationAttributes.isAutoArticleId()) {
				articleId = String.valueOf(CounterLocalServiceUtil.increment());
			}

			long id = CounterLocalServiceUtil.increment();

			long resourcePrimKey =
				JournalArticleResourceLocalServiceUtil.
					getArticleResourcePrimKey(
						article.getGroupId(), articleId);

			JournalArticle newArticle = JournalArticleUtil.create(id);

			String journalContent =
				format(
					article.getGroupId(), articleId, article.getVersion(),
						false, article.getContent(), article.getStructureId(),
							creationAttributes.getImages());

			newArticle.setUuid(article.getUuid());
			newArticle.setResourcePrimKey(resourcePrimKey);
			newArticle.setGroupId(article.getGroupId());
			newArticle.setCompanyId(user.getCompanyId());
			newArticle.setUserId(user.getUserId());
			newArticle.setUserName(user.getFullName());
			newArticle.setCreateDate(now);
			newArticle.setModifiedDate(now);
			newArticle.setArticleId(articleId);
			newArticle.setVersion(article.getVersion());
			newArticle.setTitle(article.getTitle());
			newArticle.setDescription(article.getDescription());
			newArticle.setContent(journalContent);
			newArticle.setType(article.getType());
			newArticle.setStructureId(article.getStructureId());
			newArticle.setTemplateId(article.getTemplateId());
			newArticle.setDisplayDate(displayDate);
			newArticle.setApproved(false);

			if ((expirationDate == null) || expirationDate.after(now)) {
				newArticle.setExpired(false);
			}
			else {
				newArticle.setExpired(true);
			}

			newArticle.setExpirationDate(expirationDate);
			newArticle.setReviewDate(reviewDate);
			newArticle.setIndexable(article.isIndexable());
			newArticle.setSmallImage(article.getSmallImage());
			newArticle.setSmallImageId(CounterLocalServiceUtil.increment());
			newArticle.setSmallImageURL(article.getSmallImageURL());

			JournalArticleUtil.update(newArticle, false);
			articleContent.setArticle(newArticle);

			// Small image

			saveImages(
				article.getSmallImage(), article.getSmallImageId(),
					creationAttributes.getSmallFile(), smallBytes);
			// Resources

			if ((creationAttributes.getAddCommunityPermissions() != null) &&
				(creationAttributes.getAddGuestPermissions() != null)) {

				Boolean addCommunityPermissions =
					creationAttributes.getAddCommunityPermissions();
				Boolean addGuestPermissions =
					creationAttributes.getAddGuestPermissions();
				addArticleResources(
					newArticle, addCommunityPermissions.booleanValue(),
						addGuestPermissions.booleanValue());
			}
			else {
				addArticleResources(
					newArticle, articleContent.getCommunityPermissions(),
						articleContent.getGuestPermissions());
			}

			// Tags

			updateTagsAsset(
				article.getUserId(), newArticle,
					creationAttributes.getTagsEntries());

			//Email

			try {
				sendEmail(
					article, creationAttributes.getArticleURL(),
						creationAttributes.getPrefs(), "requested");
			}
			catch (IOException ioe) {
				throw new SystemException(ioe);
			}

		}
		catch (PortalException ex) {
			_log.error(ex.getMessage(), ex);
		}
		catch (SystemException ex) {
			_log.error(ex.getMessage(), ex);
		}
	}

	public void createContent(Content content, String username)
		throws CMSException, TemplateNotFoundException {

	}

	public void deleteContent(Content content)
		throws CMSException {

		JournalArticleContent articleContent = (JournalArticleContent) content;
		JournalArticle article = articleContent.getArticle();
		JournalArticleContent.CreationAttributes creationAttributes =
			articleContent.getCreationAttributes();

		// Lucene

		try {
			if (article.isApproved() && article.isIndexable()) {
				Indexer.deleteArticle(
					article.getCompanyId(), article.getArticleId());
			}
		}
		catch (SearchException se) {
			_log.error("Deleting index " + article.getPrimaryKey(), se);
		}

		// Email

		try {

			if ((creationAttributes.getPrefs() != null) &&
					!article.isApproved() &&
						isLatestVersion(
							article.getGroupId(), article.getArticleId(),
								article.getVersion())) {

				try {
					sendEmail(
						article, creationAttributes.getArticleURL(),
							creationAttributes.getPrefs(), "denied");
				}
				catch (IOException ie) {
					throw new CMSException(ie.getMessage(), ie);
				}
			}

			// Tags

			TagsAssetLocalServiceUtil.deleteAsset(
				JournalArticle.class.getName(), article.getResourcePrimKey());

			// Ratings

			RatingsStatsLocalServiceUtil.deleteStats(
				JournalArticle.class.getName(), article.getResourcePrimKey());

			// Message boards

			MBMessageLocalServiceUtil.deleteDiscussionMessages(
				JournalArticle.class.getName(), article.getResourcePrimKey());

			// Content searches

			JournalContentSearchLocalServiceUtil.deleteArticleContentSearches(
				article.getGroupId(), article.getArticleId());

			// Images

			JournalArticleImageLocalServiceUtil.deleteImages(
				article.getGroupId(), article.getArticleId(),
					article.getVersion());

			// Small image

			ImageLocalServiceUtil.deleteImage(article.getSmallImageId());

			// Resources

			if (JournalArticleUtil.countByG_A(
					article.getGroupId(), article.getArticleId()) == 1) {

				ResourceLocalServiceUtil.deleteResource(
					article.getCompanyId(), JournalArticle.class.getName(),
						ResourceConstants.SCOPE_INDIVIDUAL,
							article.getResourcePrimKey());
			}

			// Resource

			if (JournalArticleUtil.countByG_A(
					article.getGroupId(), article.getArticleId()) == 1) {

				try {
					JournalArticleResourceLocalServiceUtil.
						deleteArticleResource(
							article.getGroupId(), article.getArticleId());
				}
				catch (NoSuchArticleResourceException nsare) {
					throw new CMSException(nsare.getMessage(), nsare);
				}
			}

			// Article

			JournalArticleUtil.remove(article.getPrimaryKey());
		}
		catch (NoSuchArticleException nsae) {
			_log.error(nsae.getMessage(), nsae);
			throw new CMSException(nsae.getMessage(), nsae);
		}
		catch (SystemException se) {
			_log.error(se.getMessage(), se);
			throw new CMSException(se.getMessage(), se);
		}
		catch (PortalException pe) {
			_log.error(pe.getMessage(), pe);
			throw new CMSException(pe.getMessage(), pe);
		}
	}

	public void deleteContent(String contentName, String contentTypeName)
		throws CMSException {

	}

	public Content getContent(
			Content content,OptionalCriteria optionalCriteria)
		throws CMSException {

		JournalArticleContent articleContent = (JournalArticleContent) content;
		JournalArticle article = articleContent.getArticle();
		JournalArticle newArticle = null;
		try {
			OptionalJournalArticleCriteria criteria =
				(OptionalJournalArticleCriteria) optionalCriteria;
			String finder =
				criteria.getOptions().get(
					OptionalJournalArticleCriteria.FINDER);

			if (finder.equals(OptionalJournalArticleCriteria.FIND_BY_G_A_V)) {
				newArticle =
					JournalArticleUtil.findByG_A_V(
						article.getGroupId(), article.getArticleId(),
							article.getVersion());
			}
			else if (finder.equals(
						OptionalJournalArticleCriteria.FIND_BY_PRIMARY_KEY)) {
				newArticle =
					JournalArticleUtil.findByPrimaryKey(
						article.getId());
			}

			articleContent.setArticle(newArticle);
		}
		catch (PortalException ex) {
			_log.error(ex.getMessage(), ex);
			throw new CMSException(ex.getMessage(), ex);
		}
		catch (SystemException ex) {
			_log.error(ex.getMessage(), ex);
			throw new CMSException(ex.getMessage(), ex);
		}
		return articleContent;
	}

	public Content getContentByNameAndType(String arg0, String arg1)
		throws CMSException {

		throw new UnsupportedOperationException("Not supported yet.");
	}

	public Content getContentByNameAndType(
			String contentName, String contentTypeName, String username)
		throws CMSException {

		return null;
	}

	public Content getContentByNameTypeNameAndCategory(
			String arg0, String arg1, String arg2)
		throws CMSException {

		throw new UnsupportedOperationException("Not supported yet.");
	}

	public Content getContentByUUID(String arg0)
		throws CMSException {

		throw new UnsupportedOperationException("Not supported yet.");
	}

	public Content getContentByUUID(String uuid, User user)
		throws CMSException {

		return null;
	}

	public VersionableContent getContentByVersion(
			String arg0, String arg1, String arg2)
		throws CMSException {

		throw new UnsupportedOperationException("Not supported yet.");
	}

	public VersionableContent getContentByVersion(
			String contentName, String contentTypeName, String versionName,
			User user)
		throws CMSException {

		return null;
	}

	public List<String> getContentNamesByType(String contentTypeName)
		throws CMSException {

		return null;
	}

	public List<Content> getContentsByType(String arg0)
		throws CMSException {

		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List<Content> getContentsByType(
			String contentTypeUUID, String username)
		throws CMSException {

		return null;
	}

	public String getContentURL(String appURL, String UUID) {

		return null;
	}

	public JournalArticle getLatestArticle(long groupId, String articleId)
		throws PortalException, SystemException {

		return getLatestArticle(groupId, articleId, null);
	}

	public JournalArticle getLatestArticle(
			long groupId, String articleId, Boolean approved)
		throws PortalException, SystemException {

		List<JournalArticle> articles = null;

		if (approved == null) {
			articles =
				JournalArticleUtil.findByG_A(groupId, articleId, 0, 1);
		}
		else {
			articles =
				JournalArticleUtil.findByG_A_A(
					groupId, articleId, approved.booleanValue(), 0, 1);
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

	public List<String> getVersionNames(String arg0, String arg1)
		throws CMSException {

		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List<String> getVersionNames(
			String contentName, String contentTypeName, User user)
		throws CMSException {

		return null;
	}

	public List<VersionableContent> getVersions(String arg0, String arg1)
		throws CMSException {

		throw new UnsupportedOperationException("Not supported yet.");
	}

	public List<VersionableContent> getVersions(
			String contentName, String contentTypeName, User user)
		throws CMSException {

		return null;
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

	public List<Content> searchContents(SearchCriteria searchCriteria)
		throws CMSException {

		List<SearchFieldValue> searchFields =
			searchCriteria.getSearchFieldValues();
		List<Content> contents = new ArrayList<Content>();

		try {
			List<JournalArticle> articles = null;
			String finder = _findFieldValue(
								searchCriteria.getSearchFieldValues(),
									OptionalJournalArticleCriteria.FINDER);

			if (OptionalJournalStructureCriteria.FIND_BY_GROUP.equals(finder)) {

				String groupIdField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.GROUP_ID);
				long groupId = Long.parseLong(groupIdField);
				articles = _getArticles(groupId);

			}
			else if (OptionalJournalArticleCriteria.FIND_ALL.equals(finder)) {
				articles = _getArticles();
			}
			else if (OptionalJournalArticleCriteria
							.FIND_BY_SMALL_IMAGE_ID.equals(finder)) {

				long smallImageId =
						Long.parseLong(
								OptionalJournalArticleCriteria.SMALL_IMAGE_ID);
				articles = _getArticlesByImageId(smallImageId);
			}
			else if (OptionalJournalArticleCriteria
							.FIND_BY_GROUP_LIMIT.equals(finder)) {

				String groupIdField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.GROUP_ID);
				long groupId = Long.parseLong(groupIdField);
				String start =
					_findFieldValue(
						searchFields,
						OptionalJournalArticleCriteria.RANGE_START);
				String end =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.RANGE_END);
				articles =
					_getArticles(
						groupId, Integer.parseInt(start),
							Integer.parseInt(end));

			}
			else if (OptionalJournalArticleCriteria
							.FIND_BY_GROUP_AND_ORDER.equals(finder)) {
				String groupIdField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.GROUP_ID);
				long groupId = Long.parseLong(groupIdField);
				OrderByComparator obc =
					(OrderByComparator) searchCriteria.getOrderByComparator();
				String start =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.RANGE_START);
				String end =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.RANGE_END);
				articles =
					_searchArticles(
						groupId, Integer.parseInt(start),
							Integer.parseInt(end), obc);
			}
			else if (OptionalJournalArticleCriteria
								.FIND_BY_G_A.equals(finder)) {
				String groupIdField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.GROUP_ID);
				long groupId = Long.parseLong(groupIdField);

				String articleId =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.ARTICLE_ID);

				articles = _getArticles(groupId, articleId);
			}
			else if (OptionalJournalArticleCriteria
								.FIND_BY_G_A_A.equals(finder)) {
				String groupIdField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.GROUP_ID);
				long groupId = Long.parseLong(groupIdField);

				String articleId =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.ARTICLE_ID);

				articles = _getArticles(groupId, articleId, true);
			}
			else if (OptionalJournalArticleCriteria
								.FIND_LATEST.equals(finder)) {
				String groupIdField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.GROUP_ID);
				long groupId = Long.parseLong(groupIdField);

				String articleId =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.ARTICLE_ID);

				String approved =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.APPROVED);

				if (approved == null) {
					articles = _getArticles(groupId, articleId, 0, 1);
				}
				else {
					articles =
						_getArticles(
							groupId, articleId,
								Boolean.valueOf(approved), 0, 1);
				}
			}
			else if (OptionalJournalArticleCriteria
								.FIND_BY_KEYWORDS.equals(finder)) {
				String groupIdField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.GROUP_ID);
				long groupId = Long.parseLong(groupIdField);
				long companyId =
					Long.parseLong(
						_findFieldValue(
							searchFields,
								OptionalJournalArticleCriteria.COMPANY_ID));
				String keywords =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.KEYWORDS);
				String versionField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.VERSION);
				Double version = null;

				if (versionField != null) {
					version = Double.parseDouble(versionField);
				}

				String type =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.TYPE);
				String structureId =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.STRUCTURE_ID);
				String templateId =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.TEMPLATE_ID);

				String displayDateGTField =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.DISPLAY_DATE_GT);
				Date displayDateGT = null;

				if (displayDateGTField != null) {
					displayDateGT =
						new Date(Long.parseLong(displayDateGTField));
				}

				String displayDateLTField =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.DISPLAY_DATE_LT);
				Date displayDateLT = null;

				if (displayDateLTField != null) {
					displayDateLT =
						new Date(Long.parseLong(displayDateLTField));
				}

				String approvedField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.APPROVED);
				Boolean approved = null;

				if (approvedField != null) {
					approved = Boolean.parseBoolean(approvedField);
				}

				String expiredField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.EXPIRED);
				Boolean expired = null;

				if (expiredField != null) {
					expired = Boolean.parseBoolean(expiredField);
				}

				String reviewDateField =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.REVIEW_DATE);
				Date reviewDate = null;

				if (reviewDateField != null) {
					reviewDate = new Date(Long.parseLong(reviewDateField));
				}

				int start =
					Integer.parseInt(_findFieldValue(
						searchFields,
						OptionalJournalArticleCriteria.RANGE_START));
				int end =
					Integer.parseInt(
						_findFieldValue(
							searchFields,
								OptionalJournalArticleCriteria.RANGE_END));
				OrderByComparator obc =
					(OrderByComparator) searchCriteria.getOrderByComparator();
				articles =
					_getArticles(
						companyId, groupId, keywords, version, type,
							structureId, templateId, displayDateGT,
								displayDateLT, approved, expired, reviewDate,
									start, end, obc);
			}
			else if (OptionalJournalArticleCriteria
						.FIND_BY_C_G_A_V_T_D_C_T_S_T_D_A_E_R.equals(finder)) {

				String groupIdField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.GROUP_ID);
				long groupId = Long.parseLong(groupIdField);
				long companyId =
					Long.parseLong(
						_findFieldValue(
							searchFields,
								OptionalJournalArticleCriteria.COMPANY_ID));
				String articleId =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.ARTICLE_ID);

				String versionField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.VERSION);
				Double version = null;

				if (versionField != null) {
					version = Double.parseDouble(versionField);
				}

				String title =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.TITLE);
				String description =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.DESCRIPTION);
				String content =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.CONTENT);
				String type =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.TYPE);
				String structureId =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.STRUCTURE_ID);
				String templateId =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.TEMPLATE_ID);

				String displayDateGTField =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.DISPLAY_DATE_GT);
				Date displayDateGT = null;

				if (displayDateGTField != null) {
					displayDateGT =
						new Date(Long.parseLong(displayDateGTField));
				}

				String displayDateLTField =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.DISPLAY_DATE_LT);
				Date displayDateLT = null;

				if (displayDateLTField != null) {
					displayDateLT =
						new Date(Long.parseLong(displayDateLTField));
				}

				String approvedField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.APPROVED);
				Boolean approved = null;

				if (approvedField != null) {
					approved = Boolean.parseBoolean(approvedField);
				}

				String expiredField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.EXPIRED);
				Boolean expired = null;

				if (expiredField != null) {
					expired = Boolean.parseBoolean(expiredField);
				}

				String reviewDateField =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.REVIEW_DATE);
				Date reviewDate = null;

				if (reviewDateField != null) {
					reviewDate = new Date(Long.parseLong(reviewDateField));
				}

				boolean andOperator =
					Boolean.parseBoolean(_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.AND_OPERATOR));
				int start =
					Integer.parseInt(_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.RANGE_START));
				int end =
					Integer.parseInt(
						_findFieldValue(
							searchFields,
								OptionalJournalArticleCriteria.RANGE_END));
				OrderByComparator obc =
					(OrderByComparator) searchCriteria.getOrderByComparator();
				articles =
					_getArticles(
						companyId, groupId, articleId, version, title,
							description, content, type, structureId, templateId,
								displayDateGT, displayDateLT, approved, expired,
									reviewDate, andOperator, start, end, obc);
			}
			else if (OptionalJournalArticleCriteria
						.FIND_BY_STRUCT_AND_TEMPLATE_IDS.equals(finder)) {
				String groupIdField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.GROUP_ID);
				long groupId = Long.parseLong(groupIdField);
				long companyId =
					Long.parseLong(
						_findFieldValue(
							searchFields,
								OptionalJournalArticleCriteria.COMPANY_ID));
				String articleId =
					_findFieldValue(
						searchFields,
								OptionalJournalArticleCriteria.ARTICLE_ID);

				String versionField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.VERSION);
				Double version = null;

				if (versionField != null) {
					version = Double.parseDouble(versionField);
				}

				String title =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.TITLE);
				String description =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.DESCRIPTION);
				String content =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.CONTENT);
				String type =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.TYPE);
				String[] structureIds =
					_findFieldValues(
						searchFields,
							OptionalJournalArticleCriteria.STRUCTURE_IDS);
				String[] templateIds =
					_findFieldValues(
						searchFields,
							OptionalJournalArticleCriteria.TEMPLATE_IDS);

				String displayDateGTField =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.DISPLAY_DATE_GT);
				Date displayDateGT = null;

				if (displayDateGTField != null) {
					displayDateGT =
						new Date(Long.parseLong(displayDateGTField));
				}

				String displayDateLTField =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.DISPLAY_DATE_LT);
				Date displayDateLT = null;

				if (displayDateLTField != null) {
					displayDateLT =
						new Date(Long.parseLong(displayDateLTField));
				}

				String approvedField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.APPROVED);
				Boolean approved = null;

				if (approvedField != null) {
					approved = Boolean.parseBoolean(approvedField);
				}

				String expiredField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.EXPIRED);
				Boolean expired = null;

				if (expiredField != null) {
					expired = Boolean.parseBoolean(expiredField);
				}

				String reviewDateField =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.REVIEW_DATE);
				Date reviewDate = null;

				if (reviewDateField != null) {
					reviewDate = new Date(Long.parseLong(reviewDateField));
				}

				boolean andOperator =
					Boolean.parseBoolean(
						_findFieldValue(
							searchFields,
								OptionalJournalArticleCriteria.AND_OPERATOR));
				int start =
					Integer.parseInt(
						_findFieldValue(
							searchFields,
								OptionalJournalArticleCriteria.RANGE_START));
				int end =
					Integer.parseInt(
						_findFieldValue(
							searchFields,
								OptionalJournalArticleCriteria.RANGE_END));
				OrderByComparator obc =
					(OrderByComparator) searchCriteria.getOrderByComparator();
				articles =
					_getArticles(
						companyId, groupId, articleId, version, title,
							description, content, type, structureIds,
								templateIds, displayDateGT, displayDateLT,
									approved, expired, reviewDate, andOperator,
										start, end, obc);
			}
			else if (OptionalJournalArticleCriteria
								.FIND_BY_G_T.equals(finder)) {
				String groupIdField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.GROUP_ID);
				long groupId = Long.parseLong(groupIdField);
				String templateId =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.TEMPLATE_ID);
				articles = _getTemplateArticles(groupId, templateId);
			}
			else if (OptionalJournalArticleCriteria
								.FIND_BY_G_T_ORDER.equals(finder)) {

				String groupIdField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.GROUP_ID);
				long groupId = Long.parseLong(groupIdField);

				String templateId =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.TEMPLATE_ID);

				OrderByComparator obc =
					(OrderByComparator) searchCriteria.getOrderByComparator();
				int start =
					Integer.parseInt(
						_findFieldValue(
							searchFields,
								OptionalJournalArticleCriteria.RANGE_START));
				int end =
					Integer.parseInt(
						_findFieldValue(
							searchFields,
								OptionalJournalArticleCriteria.RANGE_END));
				articles =
					_getTemplateArticles(groupId, templateId, start, end, obc);

			}
			else if (OptionalJournalArticleCriteria
								.FIND_BY_EXPIRATION_DATE.equals(finder)) {
				String expirationDateGTField =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.EXPIRATION_DATE_GT);
				Date expirationDateGT = null;

				if (expirationDateGTField != null) {
					expirationDateGT =
						new Date(Long.parseLong(expirationDateGTField));
				}

				String expirationDateLTField =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.EXPIRATION_DATE_LT);
				Date expirationDateLT = null;

				if (expirationDateLTField != null) {
					expirationDateLT =
						new Date(Long.parseLong(expirationDateLTField));
				}

				String expiredField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.EXPIRED);
				boolean expired = false;

				if (expiredField != null) {
					expired = Boolean.parseBoolean(expiredField);
				}

				articles =
					_getTemplateArticles(
						expired, expirationDateLT, expirationDateGT);

			}
			else if (OptionalJournalArticleCriteria
								.FIND_BY_REVIEW_DATE.equals(finder)) {
				String reviewDateGTField =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.REVIEW_DATE_GT);
				Date reviewDateGT = null;

				if (reviewDateGTField != null) {
					reviewDateGT = new Date(Long.parseLong(reviewDateGTField));
				}

				String reviewDateLTField =
					_findFieldValue(
						searchFields,
							OptionalJournalArticleCriteria.REVIEW_DATE_LT);
				Date reviewDateLT = null;

				if (reviewDateLTField != null) {
					reviewDateLT = new Date(Long.parseLong(reviewDateLTField));
				}

				articles = _getTemplateArticles(reviewDateLT, reviewDateGT);
			}

			_populateContents(contents, articles);
		}
		catch (SystemException ex) {
			throw new CMSException(ex.getMessage(), ex);
		}

		return contents;
	}

	public List<Content> searchContentsByType(
		ContentType contentType, SearchCriteria searchCriteria)
		throws CMSException, SearchNotSupportedForFieldException {

		List<Content> contents = new ArrayList<Content>();
		List<SearchFieldValue> searchFields =
			searchCriteria.getSearchFieldValues();

		try {
			List<JournalArticle> articles = null;
			String finder =
				_findFieldValue(
					searchFields, OptionalJournalArticleCriteria.FINDER);
			if (OptionalJournalArticleCriteria.FIND_BY_G_S.equals(finder)) {

				String groupIdField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.GROUP_ID);
				long groupId = Long.parseLong(groupIdField);
				String structureId = contentType.getUuid();

				articles = _getArticlesByStructure(groupId, structureId);

			}
			else if (OptionalJournalArticleCriteria
								.FIND_BY_G_S_ORDER.equals(finder)) {

				String groupIdField =
					_findFieldValue(
						searchFields, OptionalJournalArticleCriteria.GROUP_ID);
				long groupId = Long.parseLong(groupIdField);

				String structureId = contentType.getUuid();

				OrderByComparator obc =
					(OrderByComparator) searchCriteria.getOrderByComparator();
				int start =
					Integer.parseInt(
						_findFieldValue(
							searchFields,
								OptionalJournalArticleCriteria.RANGE_START));
				int end =
					Integer.parseInt(
						_findFieldValue(
							searchFields,
								OptionalJournalArticleCriteria.RANGE_END));
				articles =
					_getArticlesByStructure(
						groupId, structureId, start, end, obc);

			}

			_populateContents(contents, articles);

		}
		catch (SystemException ex) {
			throw new CMSException(ex.getMessage(), ex);
		}

		return contents;
	}

	public void unCheckoutContent(String arg0, String arg1)
		throws CMSException {

		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void unCheckoutContent(
			String contentName, String contentTypeName, String user)
		throws CMSException {

	}

	public void updateContent(Content mi_content)
		throws CMSException, TemplateNotFoundException {

		JournalArticleContent articleContent =
			(JournalArticleContent) mi_content;

		JournalArticle article = articleContent.getArticle();
		JournalArticleContent.CreationAttributes creationAttributes =
			articleContent.getCreationAttributes();
		try {
			com.liferay.portal.model.User user =
				UserUtil.findByPrimaryKey(article.getUserId());
			String articleId = article.getArticleId().trim().toUpperCase();

			Date displayDate =
				PortalUtil.getDate(
					creationAttributes.getDisplayDateMonth(),
					creationAttributes.getDisplayDateDay(),
					creationAttributes.getDisplayDateYear(),
					creationAttributes.getDisplayDateHour(),
					creationAttributes.getDisplayDateMinute(),
					user.getTimeZone(), new ArticleDisplayDateException());

			Date expirationDate = null;

			if (!creationAttributes.isNeverExpire()) {
				expirationDate =
					PortalUtil.getDate(
						creationAttributes.getExpirationDateMonth(),
						creationAttributes.getExpirationDateDay(),
						creationAttributes.getExpirationDateYear(),
						creationAttributes.getExpirationDateHour(),
						creationAttributes.getExpirationDateMinute(),
						user.getTimeZone(),
						new ArticleExpirationDateException());
			}

			Date reviewDate = null;

			if (!creationAttributes.isNeverReview()) {
				reviewDate =
					PortalUtil.getDate(
						creationAttributes.getReviewDateMonth(),
						creationAttributes.getReviewDateDay(),
						creationAttributes.getReviewDateYear(),
						creationAttributes.getReviewDateHour(),
						creationAttributes.getReviewDateMinute(),
						user.getTimeZone(), new ArticleReviewDateException());
			}

			byte[] smallBytes = null;

			try {
				smallBytes =
					FileUtil.getBytes(creationAttributes.getSmallFile());
			}
			catch (IOException ioe) {
				throw new CMSException(ioe.getMessage(), ioe);
			}

			Date now = new Date();

			validate(
				article.getGroupId(), article.getTitle(), article.getContent(),
					article.getType(), article.getStructureId(),
						article.getTemplateId(), article.getSmallImage(),
							article.getSmallImageURL(),
								creationAttributes.getSmallFile(), smallBytes);

			JournalArticle oldArticle =
				JournalArticleUtil.findByG_A_V(
					article.getGroupId(), articleId, article.getVersion());

			JournalArticle newArticle = null;

			if (creationAttributes.isIncrementVersion()) {
				double latestVersion = getLatestVersion(
											article.getGroupId(), articleId);

				long id = CounterLocalServiceUtil.increment();

				newArticle = JournalArticleUtil.create(id);

				newArticle.setResourcePrimKey(oldArticle.getResourcePrimKey());
				newArticle.setGroupId(oldArticle.getGroupId());
				newArticle.setCompanyId(user.getCompanyId());
				newArticle.setUserId(user.getUserId());
				newArticle.setUserName(user.getFullName());
				newArticle.setCreateDate(now);
				newArticle.setArticleId(articleId);
				newArticle.setVersion(MathUtil.format(
										latestVersion + 0.1, 1, 1));
				newArticle.setSmallImageId(oldArticle.getSmallImageId());
			}
			else {
				newArticle = oldArticle;
			}

			String content =
				format(
					article.getGroupId(), articleId, article.getVersion(),
						creationAttributes.isIncrementVersion(),
							article.getContent(), article.getStructureId(),
								creationAttributes.getImages());

			boolean approved = oldArticle.isApproved();

			if (creationAttributes.isIncrementVersion()) {
				approved = false;
			}

			newArticle.setModifiedDate(now);
			newArticle.setTitle(article.getTitle());
			newArticle.setDescription(article.getDescription());
			newArticle.setContent(content);
			newArticle.setType(article.getType());
			newArticle.setStructureId(article.getStructureId());
			newArticle.setTemplateId(article.getTemplateId());
			newArticle.setDisplayDate(displayDate);
			newArticle.setApproved(approved);

			if ((expirationDate == null) || expirationDate.after(now)) {
				article.setExpired(false);
			}
			else {
				article.setExpired(true);
			}

			newArticle.setExpirationDate(expirationDate);
			newArticle.setReviewDate(reviewDate);
			newArticle.setIndexable(article.getIndexable());
			newArticle.setSmallImage(article.getSmallImage());

			if (article.getSmallImageId() == 0) {
				article.setSmallImageId(CounterLocalServiceUtil.increment());
			}

			newArticle.setSmallImageURL(article.getSmallImageURL());

			JournalArticleUtil.update(newArticle, false);
			articleContent.setArticle(newArticle);

			// Small image

			saveImages(
				article.getSmallImage(), article.getSmallImageId(),
					creationAttributes.getSmallFile(), smallBytes);

			// Tags

			updateTagsAsset(
				article.getUserId(), newArticle,
					creationAttributes.getTagsEntries());

			// Email

			if (creationAttributes.isIncrementVersion()) {
				try {
					sendEmail(
						article, creationAttributes.getArticleURL(),
							creationAttributes.getPrefs(), "requested");
				}
				catch (IOException ioe) {
					throw new SystemException(ioe);
				}
			}

			// Lucene

			try {
				if (newArticle.isIndexable()) {
					if (newArticle.isApproved()) {
						Indexer.updateArticle(
							newArticle.getCompanyId(), newArticle.getGroupId(),
							newArticle.getArticleId(), newArticle.getVersion(),
							newArticle.getTitle(), newArticle.getDescription(),
							newArticle.getContent(), newArticle.getType(),
							newArticle.getDisplayDate(),
							creationAttributes.getTagsEntries());
					}
					else {
						Indexer.deleteArticle(
							article.getCompanyId(), article.getArticleId());
					}
				}
			}
			catch (SearchException se) {
				_log.error("Indexing " + article.getPrimaryKey(), se);
			}
		}
		catch (PortalException ex) {
			_log.error(ex.getMessage(), ex);

		}
		catch (SystemException ex) {
			_log.error(ex.getMessage(), ex);

		}

	}

	public void updateContent(
			Content content, OptionalCriteria optionalCriteria)
		throws CMSException, TemplateNotFoundException {

		JournalArticleContent articleContent = (JournalArticleContent) content;

		JournalArticle article = articleContent.getArticle();
		try {
			JournalArticleUtil.update(article, false);
			articleContent.setArticle(article);
		}
		catch (SystemException se) {
			throw new CMSException(se.getMessage(), se);
		}
	}

	public void updateContent(Content content, String username)
		throws CMSException, TemplateNotFoundException {

	}

	public void updateFileField(String arg0, String arg1, InputStream arg2)
		throws CMSException {

		throw new UnsupportedOperationException("Not supported yet.");
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

		TagsAssetLocalServiceUtil.updateAsset(
			userId, article.getGroupId(), JournalArticle.class.getName(),
				article.getResourcePrimKey(), tagsEntries, null, null,
					displayDate, expirationDate, ContentTypes.TEXT_HTML,
						article.getTitle(), article.getDescription(), null,
							null, 0, 0, null, false);
	}

	protected void format(
			long groupId, String articleId, double version,
			boolean incrementVersion, Element root, Map<String, byte[]> images)
		throws PortalException, SystemException {

		for (Element el : (List<Element>) root.elements()) {
			String elName = el.attributeValue("name", StringPool.BLANK);
			String elType = el.attributeValue("type", StringPool.BLANK);

			if (elType.equals("image")) {
				formatImage(
					groupId, articleId, version, incrementVersion, el, elName,
					images);
			}

			/*else if (elType.equals("text_area")) {
			Element dynamicContent = el.element("dynamic-content");
			String text = dynamicContent.getText();
			// LEP-1594
			try {
			text = ParserUtils.trimTags(
			text, new String[] {"script"}, false, true);
			}
			catch (ParserException pe) {
			text = pe.getLocalizedMessage();
			}
			catch (UnsupportedEncodingException uee) {
			text = uee.getLocalizedMessage();
			}
			dynamicContent.setText(text);
			}*/

			format(groupId, articleId, version, incrementVersion, el, images);
		}
	}

	protected String format(
			long groupId, String articleId, double version,
			boolean incrementVersion, String content, String structureId,
			Map<String, byte[]> images)
		throws PortalException, SystemException {

		if (Validator.isNotNull(structureId)) {
			SAXReader reader = new SAXReader();

			Document doc = null;

			try {
				doc = reader.read(new StringReader(content));

				Element root = doc.getRootElement();

				format(
					groupId, articleId, version, incrementVersion,
					root, images);

				content = JournalUtil.formatXML(doc);
			}
			catch (DocumentException de) {
				_log.error(de);
			}
			catch (IOException ioe) {
				_log.error(ioe);
			}
		}

		content = HtmlUtil.replaceMsWordCharacters(content);

		return content;
	}

	protected void formatImage(
			long groupId, String articleId, double version,
			boolean incrementVersion, Element el, String elName,
			Map<String, byte[]> images)
		throws PortalException, SystemException {

		List<Element> imageContents = el.elements("dynamic-content");

		for (Element dynamicContent : imageContents) {
			String elLanguage =
				dynamicContent.attributeValue("language-id", StringPool.BLANK);

			if (!elLanguage.equals(StringPool.BLANK)) {
				elLanguage = "_" + elLanguage;
			}

			long imageId =
				JournalArticleImageLocalServiceUtil.getArticleImageId(
					groupId, articleId, version, elName, elLanguage);

			double oldVersion = MathUtil.format(version - 0.1, 1, 1);

			long oldImageId = 0;

			if ((oldVersion >= 1) && incrementVersion) {
				oldImageId =
					JournalArticleImageLocalServiceUtil.getArticleImageId(
						groupId, articleId, oldVersion, elName, elLanguage);
			}

			String elContent =
				"/image/journal/article?img_id=" + imageId + "&t=" +
					ImageServletTokenUtil.getToken(imageId);

			if (dynamicContent.getText().equals("delete")) {
				dynamicContent.setText(StringPool.BLANK);

				ImageLocalServiceUtil.deleteImage(imageId);

				String defaultElLanguage = "";

				if (!Validator.isNotNull(elLanguage)) {
					defaultElLanguage =
						"_" + LocaleUtil.toLanguageId(LocaleUtil.getDefault());
				}

				long defaultImageId =
					JournalArticleImageLocalServiceUtil.getArticleImageId(
						groupId, articleId, version, elName, defaultElLanguage);

				ImageLocalServiceUtil.deleteImage(defaultImageId);

				continue;
			}

			byte[] bytes = images.get(elName + elLanguage);

			if (bytes != null && (bytes.length > 0)) {
				dynamicContent.setText(elContent);
				dynamicContent.addAttribute("id", String.valueOf(imageId));

				ImageLocalServiceUtil.updateImage(imageId, bytes);

				continue;
			}

			if ((version > JournalArticleImpl.DEFAULT_VERSION) &&
				(incrementVersion)) {

				Image oldImage = null;

				if (oldImageId > 0) {
					oldImage = ImageLocalServiceUtil.getImage(oldImageId);
				}

				if (oldImage != null) {
					dynamicContent.setText(elContent);
					dynamicContent.addAttribute("id", String.valueOf(imageId));

					bytes = oldImage.getTextObj();

					ImageLocalServiceUtil.updateImage(imageId, bytes);
				}

				continue;
			}

			Image image = ImageLocalServiceUtil.getImage(imageId);

			if (image != null) {
				dynamicContent.setText(elContent);
				dynamicContent.addAttribute("id", String.valueOf(imageId));

				continue;
			}

			long contentImageId =
				GetterUtil.getLong(HttpUtil.getParameter(
					dynamicContent.getText(), "img_id"));

			if (contentImageId <= 0) {
				contentImageId =
					GetterUtil.getLong(HttpUtil.getParameter(
						dynamicContent.getText(), "img_id", false));
			}

			if (contentImageId > 0) {
				image = ImageLocalServiceUtil.getImage(contentImageId);

				if (image != null) {
					dynamicContent.addAttribute(
						"id", String.valueOf(contentImageId));

					continue;
				}
			}

			String defaultElLanguage = "";

			if (!Validator.isNotNull(elLanguage)) {
				defaultElLanguage =
					"_" + LocaleUtil.toLanguageId(LocaleUtil.getDefault());
			}

			long defaultImageId =
				JournalArticleImageLocalServiceUtil.getArticleImageId(
					groupId, articleId, version, elName, defaultElLanguage);

			Image defaultImage = ImageLocalServiceUtil.getImage(defaultImageId);

			if (defaultImage != null) {
				dynamicContent.setText(elContent);
				dynamicContent.addAttribute(
					"id", String.valueOf(defaultImageId));

				bytes = defaultImage.getTextObj();

				ImageLocalServiceUtil.updateImage(defaultImageId, bytes);

				continue;
			}

			dynamicContent.setText(StringPool.BLANK);
		}
	}

	protected Date[] getDateInterval(
			long groupId, String articleId, Date earliestDisplayDate,
			Date latestExpirationDate)
		throws SystemException {

		Date[] dateInterval = new Date[2];

		List<JournalArticle> articles =
			JournalArticleUtil.findByG_A_A(groupId, articleId, true);

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

	protected void saveImages(
			boolean smallImage, long smallImageId, File smallFile,
			byte[] smallBytes)
		throws PortalException, SystemException {

		if (smallImage) {
			if ((smallFile != null) && (smallBytes != null)) {
				ImageLocalServiceUtil.updateImage(smallImageId, smallBytes);
			}
		}
		else {
			ImageLocalServiceUtil.deleteImage(smallImageId);
		}
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
			CompanyUtil.findByPrimaryKey(article.getCompanyId());

		com.liferay.portal.model.User user =
			UserUtil.findByPrimaryKey(article.getUserId());

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

		MailServiceUtil.sendEmail(message);
	}

	protected void validate(
			long groupId, String articleId, boolean autoArticleId,
			double version,String title, String content, String type,
			String structureId,String templateId, boolean smallImage,
			String smallImageURL,File smallFile, byte[] smallBytes)
		throws PortalException, SystemException {

		if (!autoArticleId) {
			if ((Validator.isNull(articleId)) ||
				(articleId.indexOf(StringPool.SPACE) != -1)) {

				throw new ArticleIdException();
			}

			try {
				JournalArticleUtil.findByG_A_V(
					groupId, articleId, version);

				throw new DuplicateArticleIdException();
			}
			catch (NoSuchArticleException nste) {
			}
		}

		validate(
			groupId, title, content, type, structureId, templateId, smallImage,
				smallImageURL, smallFile, smallBytes);
	}

	protected void validate(
			long groupId, String title, String content, String type,
			String structureId, String templateId, boolean smallImage,
			String smallImageURL, File smallFile, byte[] smallBytes)
		throws PortalException, SystemException {

		if (Validator.isNull(title)) {
			throw new ArticleTitleException();
		}
		else if (Validator.isNull(content)) {
			throw new ArticleContentException();
		}
		else if (Validator.isNull(type)) {
			throw new ArticleTypeException();
		}

		if (Validator.isNotNull(structureId)) {
			JournalStructureUtil.findByG_S(groupId, structureId);

			JournalTemplate template =
				JournalTemplateUtil.findByG_T(groupId, templateId);

			if (!template.getStructureId().equals(structureId)) {
				throw new NoSuchTemplateException();
			}
		}

		String[] imageExtensions =
			PropsUtil.getArray(PropsKeys.JOURNAL_IMAGE_EXTENSIONS);

		if (smallImage && Validator.isNull(smallImageURL) &&
			smallFile != null && smallBytes != null) {

			String smallImageName = smallFile.getName();

			if (smallImageName != null) {
				boolean validSmallImageExtension = false;

				for (int i = 0; i < imageExtensions.length; i++) {
					if (StringPool.STAR.equals(imageExtensions[i]) ||
							StringUtil.endsWith(
								smallImageName, imageExtensions[i])) {

						validSmallImageExtension = true;

						break;
					}
				}

				if (!validSmallImageExtension) {
					throw new ArticleSmallImageNameException(smallImageName);
				}
			}

			long smallImageMaxSize =
				GetterUtil.getLong(
					PropsUtil.get(PropsKeys.JOURNAL_IMAGE_SMALL_MAX_SIZE));

			if ((smallImageMaxSize > 0) &&
					((smallBytes == null) ||
						(smallBytes.length > smallImageMaxSize))) {

				throw new ArticleSmallImageSizeException();
			}
		}
	}

	private String _findFieldValue(
		List<SearchFieldValue> fieldValues, String fieldName) {

		String result = null;
		for (SearchFieldValue searchField : fieldValues) {
			if (searchField.getFieldName().equals(fieldName)) {
				result = searchField.getFieldValue();
				break;
			}
		}
		return result;
	}

	private String[] _findFieldValues(
		List<SearchFieldValue> fieldValues, String fieldName) {

		String[] result = null;
		for (SearchFieldValue searchField : fieldValues) {
			if (searchField.getFieldName().equals(fieldName)) {
				result = searchField.getFieldValues();
				break;
			}
		}
		return result;
	}

	private List<JournalArticle> _getArticles()
		throws SystemException {

		return JournalArticleUtil.findAll();
	}

	private List<JournalArticle> _getArticles(long groupId)
		throws SystemException {

		return JournalArticleUtil.findByGroupId(groupId);
	}

	private List<JournalArticle> _getArticles(long groupId, int start, int end)
		throws SystemException {

		return JournalArticleUtil.findByGroupId(groupId, start, end);
	}

	private List<JournalArticle> _getArticles(
			long companyId, long groupId, String keywords, Double version,
			String type, String structureId, String templateId,
			Date displayDateGT,Date displayDateLT, Boolean approved,
			Boolean expired, Date reviewDate,
			int start, int end, OrderByComparator obc)
		throws SystemException {

		return JournalArticleFinderUtil.findByKeywords(
			companyId, groupId, keywords, version, type, structureId,
				templateId, displayDateGT, displayDateLT, approved, expired,
					reviewDate, start, end, obc);
	}

	private List<JournalArticle> _getArticles(
			long companyId, long groupId, String articleId, Double version,
			String title, String description, String content, String type,
			String structureId, String templateId, Date displayDateGT,
			Date displayDateLT, Boolean approved, Boolean expired,
			Date reviewDate,boolean andOperator, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return JournalArticleFinderUtil.findByC_G_A_V_T_D_C_T_S_T_D_A_E_R(
			companyId, groupId, articleId, version, title, description,
				content, type, structureId, templateId, displayDateGT,
					displayDateLT, approved, expired, reviewDate, andOperator,
						start, end, obc);
	}

	private List<JournalArticle> _getArticles(
			long companyId, long groupId, String articleId, Double version,
			String title, String description, String content, String type,
			String[] structureIds, String[] templateIds, Date displayDateGT,
			Date displayDateLT, Boolean approved, Boolean expired,
			Date reviewDate,boolean andOperator, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return JournalArticleFinderUtil.findByC_G_A_V_T_D_C_T_S_T_D_A_E_R(
			companyId, groupId, articleId, version, title, description,
				content, type, structureIds, templateIds, displayDateGT,
					displayDateLT, approved, expired, reviewDate, andOperator,
					start, end, obc);
	}

	private List<JournalArticle> _getArticles(long groupId, String articleId)
		throws SystemException {

		return JournalArticleUtil.findByG_A(groupId, articleId);
	}

	private List<JournalArticle> _getArticles(
			long groupId, String articleId, boolean approved)
		throws SystemException {

		return JournalArticleUtil.findByG_A_A(
			groupId, articleId, approved);
	}

	private List<JournalArticle> _getArticles(
			long groupId, String articleId, Boolean approved,
			int start, int end)
		throws SystemException {

		return JournalArticleUtil.findByG_A_A(
			groupId, articleId, approved.booleanValue(), 0, 1);
	}

	private List<JournalArticle> _getArticles(
			long groupId, String articleId, int start, int end)
		throws SystemException {

		return JournalArticleUtil.findByG_A(
			groupId, articleId, start, end);
	}

	private List<JournalArticle> _getArticlesByImageId(long smallImageId)
		throws SystemException {

		return JournalArticleUtil.findBySmallImageId(smallImageId);
	}

	private List<JournalArticle> _getArticlesByStructure(
			long groupId, String structureId)
		throws SystemException {

		return JournalArticleUtil.findByG_S(groupId, structureId);
	}

	private List<JournalArticle> _getArticlesByStructure(
			long groupId, String structureId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return JournalArticleUtil.findByG_S(
			groupId, structureId, start, end, obc);
	}

	private int _getArticlesCount(long groupId)
		throws SystemException {

		return JournalArticleUtil.countByGroupId(groupId);
	}

	private int _getArticlesCount(
			long companyId, long groupId, String keywords, Double version,
			String type, String structureId, String templateId,
			Date displayDateGT,Date displayDateLT, Boolean approved,
			Boolean expired, Date reviewDate)
		throws SystemException {

		return JournalArticleFinderUtil.countByKeywords(
			companyId, groupId, keywords, version, type, structureId,
				templateId, displayDateGT, displayDateLT, approved, expired,
					reviewDate);

	}

	private int _getArticlesCount(
			long companyId, long groupId, String articleId, Double version,
			String title, String description, String content, String type,
			String structureId, String templateId, Date displayDateGT,
			Date displayDateLT, Boolean approved, Boolean expired,
			Date reviewDate,boolean andOperator)
		throws SystemException {

		return JournalArticleFinderUtil.countByC_G_A_V_T_D_C_T_S_T_D_A_E_R(
			companyId, groupId, articleId, version, title, description,
				content, type, structureId, templateId, displayDateGT,
					displayDateLT, approved, expired, reviewDate, andOperator);
	}

	private int _getArticlesCount(
			long companyId, long groupId, String articleId, Double version,
			String title, String description, String content, String type,
			String[] structureIds, String[] templateIds, Date displayDateGT,
			Date displayDateLT, Boolean approved, Boolean expired,
			Date reviewDate, boolean andOperator)
		throws SystemException {

		return JournalArticleFinderUtil.countByC_G_A_V_T_D_C_T_S_T_D_A_E_R(
			companyId, groupId, articleId, version, title, description,
				content, type, structureIds, templateIds, displayDateGT,
					displayDateLT, approved, expired, reviewDate, andOperator);
	}

	private int _getStructureArticlesCount(long groupId, String structureId)
		throws SystemException {

		return JournalArticleUtil.countByG_S(groupId, structureId);
	}

	private List<JournalArticle> _getTemplateArticles(
			boolean expired, Date expirationDateLT, Date expirationDateGT)
		throws SystemException {

		return JournalArticleFinderUtil.findByExpirationDate(
			expired, expirationDateLT, expirationDateGT);
	}

	private List<JournalArticle> _getTemplateArticles(
			Date reviewDateLT, Date reviewDateGT)
		throws SystemException {

		return JournalArticleFinderUtil.findByReviewDate(
					reviewDateLT, reviewDateGT);
	}

	private List<JournalArticle> _getTemplateArticles(
			long groupId, String templateId)
		throws SystemException {

		return JournalArticleUtil.findByG_T(groupId, templateId);
	}

	private List<JournalArticle> _getTemplateArticles(
			long groupId, String templateId, int start, int end,
			OrderByComparator obc)
		throws SystemException {

		return JournalArticleUtil.findByG_T(
			groupId, templateId, start, end, obc);
	}

	private int _getTemplateArticlesCount(long groupId, String templateId)
		throws SystemException {

		return JournalArticleUtil.countByG_T(groupId, templateId);
	}

	private void _populateContents(
			List<Content> contents, List<JournalArticle> articles) {

		if (articles == null) {
			return;
		}

		for (JournalArticle article : articles) {
			contents.add(new JournalArticleContent(article));
		}
	}

	private List<JournalArticle> _searchArticles(
			long groupId, int start, int end, OrderByComparator obc)
		throws SystemException {

		return JournalArticleUtil.findByGroupId(
					groupId, start, end, obc);
	}

	private static final Log _log = LogFactory.getLog(ContentServiceImpl.class);

}