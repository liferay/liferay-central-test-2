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

import com.liferay.mail.service.MailService;
import com.liferay.mail.service.MailServiceFactory;
import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.mirage.model.JournalArticleContent;
import com.liferay.portal.model.Company;
import com.liferay.portal.service.persistence.CompanyPersistence;
import com.liferay.portal.service.persistence.CompanyUtil;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.journal.NoSuchArticleException;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.persistence.JournalArticlePersistence;
import com.liferay.portlet.journal.service.persistence.JournalArticleUtil;
import com.liferay.portlet.journal.util.Indexer;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.portlet.tags.service.TagsEntryLocalService;
import com.liferay.portlet.tags.service.TagsEntryLocalServiceFactory;

import com.sun.portal.cms.mirage.exception.CMSException;
import com.sun.portal.cms.mirage.model.custom.Content;
import com.sun.portal.cms.mirage.service.custom.WorkflowService;

import java.io.IOException;

import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.mail.internet.InternetAddress;

import javax.portlet.PortletPreferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="WorkflowServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Prakash Reddy
 * @suthor Joshna Reddy
 *
 */
public class WorkflowServiceImpl implements WorkflowService {

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
				journalArticlePersistence.findByG_A(groupId, articleId, 0, 1);
		}
		else {
			articles =
				journalArticlePersistence.findByG_A_A(
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

	public void submitContentToWorkflow(String contentUUID)
		throws CMSException {

		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void updateWorkflowCommentsAccepted(String contentUUID)
		throws CMSException {

		throw new UnsupportedOperationException("Not supported yet.");
	}

	public void updateWorkflowComplete(Content content)
		throws CMSException {

		JournalArticleContent articleContent = (JournalArticleContent) content;

		JournalArticle article = articleContent.getArticle();
		JournalArticleContent.CreationAttributes creationAttributes =
			articleContent.getCreationAttributes();
		try {
			com.liferay.portal.model.User user =
				userPersistence.findByPrimaryKey(article.getUserId());
			Date now = new Date();

			article =
				journalArticlePersistence.findByG_A_V(
					article.getGroupId(), article.getArticleId(),
					article.getVersion());

			article.setModifiedDate(now);
			article.setApproved(true);
			article.setApprovedByUserId(user.getUserId());
			article.setApprovedByUserName(user.getFullName());
			article.setApprovedDate(now);
			article.setExpired(false);

			if ((article.getExpirationDate() != null) &&
				(article.getExpirationDate().before(now))) {

				article.setExpirationDate(null);
			}

			journalArticlePersistence.update(article, false);
			articleContent.setArticle(article);
		}
		catch (SystemException se) {
			logger.log(Level.SEVERE, se.getMessage(), se);
			throw new CMSException(se.getMessage(), se);
		}
		catch (NoSuchUserException nsue) {
			logger.log(Level.SEVERE, nsue.getMessage(), nsue);
			throw new CMSException(nsue.getMessage(), nsue);
		}
		catch (NoSuchArticleException nsae) {
			logger.log(Level.SEVERE, nsae.getMessage(), nsae);
			throw new CMSException(nsae.getMessage(), nsae);
		}
		// Email

		try {
			sendEmail(
				article, creationAttributes.getArticleURL(),
				creationAttributes.getPrefs(), "granted");
		}
		catch (IOException ioe) {
			throw new CMSException(ioe.getMessage(), ioe);
		}
		catch (PortalException pe) {
			throw new CMSException(pe.getMessage(), pe);
		}
		catch (SystemException se) {
			throw new CMSException(se.getMessage(), se);
		}

		// Lucene

		try {
			if (article.isIndexable()) {
				String[] tagsEntries =
					tagsEntryLocalService.getEntryNames(
						JournalArticle.class.getName(),
						article.getResourcePrimKey());

				Indexer.updateArticle(
					article.getCompanyId(), article.getGroupId(),
					article.getArticleId(), article.getVersion(),
					article.getTitle(), article.getDescription(),
					article.getContent(), article.getType(),
					article.getDisplayDate(), tagsEntries);
			}
		}
		catch (SearchException se) {
			_log.error("Indexing " + article.getId(), se);
		}
		catch (SystemException se) {
			_log.error("Indexing " + article.getId(), se);
		}

	}

	public void updateWorkflowComplete(String contentUUID)
		throws CMSException {

	}

	public void updateWorkflowContentRejected(Content content)
		throws CMSException {

		JournalArticleContent articleContent = (JournalArticleContent) content;
		JournalArticle article = articleContent.getArticle();

		JournalArticleContent.CreationAttributes creationAttributes =
			articleContent.getCreationAttributes();

		// Email
		try {
			if ((creationAttributes.getPrefs() != null) &&
				!article.isApproved() &&
				isLatestVersion(
					article.getGroupId(), article.getArticleId(),
					article.getVersion())) {

				sendEmail(
					article, creationAttributes.getArticleURL(),
					creationAttributes.getPrefs(), "denied");

			}
		}
		catch (IOException ioe) {
			throw new CMSException(ioe.getMessage(), ioe);
		}
		catch (PortalException pe) {
			throw new CMSException(pe.getMessage(), pe);
		}
		catch (SystemException se) {
			throw new CMSException(se.getMessage(), se);
		}

		// Article

		article.setExpirationDate(new Date());

		article.setApproved(false);
		article.setExpired(true);
		try {
			journalArticlePersistence.update(article, false);

		}
		catch (SystemException se) {
			throw new CMSException(se.getMessage(), se);
		}
		articleContent.setArticle(article);
		// Lucene

		try {
			if (article.isIndexable()) {
				Indexer.deleteArticle(
					article.getCompanyId(), article.getArticleId());
			}
		}
		catch (SearchException se) {
			_log.error("Removing index " + article.getId(), se);
		}

	}

	public void updateWorkflowContentRejected(
			String contentUUID, String comments)
		throws CMSException {

	}

	public void updateWorkflowStatus(String contentUUID, String status)
		throws CMSException {

		throw new UnsupportedOperationException("Not supported yet.");
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

		com.liferay.portal.model.User user =
			userPersistence.findByPrimaryKey(article.getUserId());

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

	private static Logger logger =
		Logger.getLogger(WorkflowServiceImpl.class.getPackage().getName());

	private static Log _log = LogFactory.getLog(WorkflowServiceImpl.class);

	private TagsEntryLocalService tagsEntryLocalService =
		TagsEntryLocalServiceFactory.getImpl();

	private MailService mailService = MailServiceFactory.getImpl();

	private CompanyPersistence companyPersistence =
		CompanyUtil.getPersistence();

	private JournalArticlePersistence journalArticlePersistence =
		JournalArticleUtil.getPersistence();

	private UserPersistence userPersistence = UserUtil.getPersistence();

}