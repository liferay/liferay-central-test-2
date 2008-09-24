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

package com.liferay.portlet.journal.workflow;

import com.liferay.mail.service.MailService;
import com.liferay.mail.service.MailServiceFactory;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.search.SearchException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.CompanyPersistence;
import com.liferay.portal.service.persistence.CompanyUtil;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.persistence.JournalArticlePersistence;
import com.liferay.portlet.journal.service.persistence.JournalArticleUtil;
import com.liferay.portlet.journal.util.Indexer;
import com.liferay.portlet.journal.util.JournalUtil;
import com.liferay.portlet.tags.service.TagsEntryLocalService;
import com.liferay.portlet.tags.service.TagsEntryLocalServiceFactory;

import com.sun.saw.Workflow;
import com.sun.saw.WorkflowException;
import com.sun.saw.vo.ArchiveTaskVO;
import com.sun.saw.vo.AuditHistoryVO;
import com.sun.saw.vo.BusinessProcessInstanceVO;
import com.sun.saw.vo.BusinessProcessVO;
import com.sun.saw.vo.CheckinTaskVO;
import com.sun.saw.vo.CheckoutTaskVO;
import com.sun.saw.vo.CompleteTaskVO;
import com.sun.saw.vo.DeleteTaskVO;
import com.sun.saw.vo.EscalateTaskVO;
import com.sun.saw.vo.FilterTaskVO;
import com.sun.saw.vo.InvokeMethodVO;
import com.sun.saw.vo.ManageAttachmentsVO;
import com.sun.saw.vo.NotifyTaskVO;
import com.sun.saw.vo.OutputVO;
import com.sun.saw.vo.ReassignTaskVO;
import com.sun.saw.vo.RenewTaskVO;
import com.sun.saw.vo.RevokeTaskVO;
import com.sun.saw.vo.SaveTaskVO;
import com.sun.saw.vo.TaskVO;
import com.sun.saw.vo.TimeoutTaskVO;
import com.sun.saw.vo.TokenVO;
import com.sun.saw.vo.WorkflowTaskVO;
import com.sun.saw.vo.WorkflowUser;
import com.sun.saw.vo.XFormVO;

import java.io.IOException;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.internet.InternetAddress;

import javax.portlet.PortletPreferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="JournalWorkflowImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Prakash Reddy
 *
 */
public class JournalWorkflowImpl implements Workflow {

	public JournalWorkflowImpl() {

	}

	public JournalWorkflowImpl(Properties props) {

	}

	public OutputVO archiveTasks(ArchiveTaskVO archiveTaskVO)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public OutputVO checkinTasks(CheckinTaskVO checkinTaskVO)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public OutputVO checkoutTasks(CheckoutTaskVO checkoutTaskVO)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public OutputVO completeExpiredTask(CompleteTaskVO completeTaskVO)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public OutputVO completeTasks(CompleteTaskVO completeTaskVO)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public OutputVO countTasks(FilterTaskVO filterTaskVO)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public OutputVO deleteBusinessProcess(BusinessProcessVO businessProcessVO)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public OutputVO deleteBusinessProcessInstance(
			BusinessProcessInstanceVO businessProcessInstanceVO)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public OutputVO deleteTasks(DeleteTaskVO deleteTaskVO)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public OutputVO deployBusinessProcess(BusinessProcessVO businessProcessVO)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public OutputVO endBusinessProcess(BusinessProcessVO businessProcessVO)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public OutputVO escalateTasks(EscalateTaskVO escalateTaskVO)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public OutputVO getAllBusinessProcesses(BusinessProcessVO businessProcessVO)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public OutputVO getBusinessProcess(BusinessProcessVO businessProcessVO)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public OutputVO getBusinessProcessInstances(
			BusinessProcessInstanceVO businessProcessInstanceVO)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public OutputVO getTaskByTaskId(FilterTaskVO filterTaskVO)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public OutputVO getTaskStatuses() throws WorkflowException {
		throw new UnsupportedOperationException();
	}

	public OutputVO getTaskTransitions(TaskVO taskVO) throws WorkflowException {
		throw new UnsupportedOperationException();
	}

	public OutputVO getTasks(FilterTaskVO filterTaskVO)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public OutputVO getUsersForReassign(WorkflowUser workflowUser)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public OutputVO getUsersGroupList(WorkflowUser workflowUser)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public OutputVO getWorkflowState(WorkflowTaskVO workflowTaskVO)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public OutputVO getXForm(XFormVO xFormVO) throws WorkflowException {
		throw new UnsupportedOperationException();
	}

	public OutputVO initialiseBusinessProcess(
			BusinessProcessVO businessProcessVO)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public OutputVO invokeMethod(InvokeMethodVO invokeMethodVO)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public OutputVO manageAttachments(ManageAttachmentsVO manageAttachmentsVO)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public OutputVO reassignTasks(ReassignTaskVO reassignTaskVO)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public OutputVO renewTask(RenewTaskVO renewTaskVO)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public OutputVO revokeTask(RevokeTaskVO revokeTaskVO)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public OutputVO saveTasks(SaveTaskVO saveTaskVO) throws WorkflowException {
		OutputVO outputVO = new OutputVO();

		Map<String, Object> customAttributes =
			saveTaskVO.getCustomAttributesMap();

		String action = (String) customAttributes.get("action");

		try {
			if ("approve".equals(action)) {
				long userId = (Long) customAttributes.get("userId");
				long groupId = (Long) customAttributes.get("groupId");
				String articleId = (String) customAttributes.get("articleId");
				double version = (Double) customAttributes.get("version");
				String articleURL = (String) customAttributes.get("articleURL");
				PortletPreferences prefs =
					(PortletPreferences) customAttributes.get("portletPrefs");

				JournalArticle article = _approveArticle(
						userId, groupId, articleId, version, articleURL, prefs);

				Map<String, Object> customResultsMap =
					new HashMap<String, Object>();
				customResultsMap.put("journalArticle", article);

				outputVO.setCustomResultsMap(customResultsMap);
			}
			else if ("expire".equals(action)) {
				String articleURL = (String) customAttributes.get("articleURL");
				JournalArticle article =
					(JournalArticle) customAttributes.get("article");
				PortletPreferences prefs =
					(PortletPreferences) customAttributes.get("portletPrefs");

				_expireArticle(article, articleURL, prefs);
			}

			return outputVO;
		}
		catch (PortalException pe) {
			throw new WorkflowException(pe.getMessage(), pe.getMessage(), pe);
		}
		catch (SystemException se) {
			throw new WorkflowException(se.getMessage(), se.getMessage(), se);
		}
	}

	public OutputVO sendTaskNotification(NotifyTaskVO notifyTaskVO)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public OutputVO setTaskState(WorkflowTaskVO workflowTaskVO)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public OutputVO setTaskTimeOut(TimeoutTaskVO timeoutTaskVO)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public OutputVO showAuditHistory(AuditHistoryVO auditHistoryVO)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public OutputVO signalBusinessProcessInstance(
			BusinessProcessInstanceVO businessProcessInstanceVO)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public OutputVO signalToken(TokenVO tokenVO) throws WorkflowException {
		throw new UnsupportedOperationException();
	}

	public OutputVO startBusinessProcess(BusinessProcessVO businessProcessVO)
		throws WorkflowException {

		throw new UnsupportedOperationException();
	}

	public OutputVO unArchiveTasks(ArchiveTaskVO archiveTaskVO)
		throws WorkflowException {

		throw new UnsupportedOperationException();
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

		Company company = companyPersistence.findByPrimaryKey(
			article.getCompanyId());

		User user = userPersistence.findByPrimaryKey(article.getUserId());

		articleURL +=
			"&groupId=" + article.getGroupId() + "&articleId=" +
				article.getArticleId() + "&version=" + article.getVersion();

		String portletName = PortalUtil.getPortletTitle(
			PortletKeys.JOURNAL, user);

		String fromName = JournalUtil.getEmailFromName(prefs);
		String fromAddress = JournalUtil.getEmailFromAddress(prefs);

		String toName = user.getFullName();
		String toAddress = user.getEmailAddress();

		if (emailType.equals("requested") ||
			emailType.equals("review")) {

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
			subject =
				JournalUtil.getEmailArticleApprovalDeniedSubject(prefs);
			body = JournalUtil.getEmailArticleApprovalDeniedBody(prefs);
		}
		else if (emailType.equals("granted")) {
			subject =
				JournalUtil.getEmailArticleApprovalGrantedSubject(prefs);
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

		subject = StringUtil.replace(
			subject,
			new String[] {
				"[$ARTICLE_ID$]",
				"[$ARTICLE_TITLE$]",
				"[$ARTICLE_URL$]",
				"[$ARTICLE_VERSION$]",
				"[$FROM_ADDRESS$]",
				"[$FROM_NAME$]",
				"[$PORTAL_URL$]",
				"[$PORTLET_NAME$]",
				"[$TO_ADDRESS$]",
				"[$TO_NAME$]"
			},
			new String[] {
				article.getArticleId(),
				article.getTitle(),
				articleURL,
				String.valueOf(article.getVersion()),
				fromAddress,
				fromName,
				company.getVirtualHost(),
				portletName,
				toAddress,
				toName,
			});

		body = StringUtil.replace(
			body,
			new String[] {
				"[$ARTICLE_ID$]",
				"[$ARTICLE_TITLE$]",
				"[$ARTICLE_URL$]",
				"[$ARTICLE_VERSION$]",
				"[$FROM_ADDRESS$]",
				"[$FROM_NAME$]",
				"[$PORTAL_URL$]",
				"[$PORTLET_NAME$]",
				"[$TO_ADDRESS$]",
				"[$TO_NAME$]"
			},
			new String[] {
				article.getArticleId(),
				article.getTitle(),
				articleURL,
				String.valueOf(article.getVersion()),
				fromAddress,
				fromName,
				company.getVirtualHost(),
				portletName,
				toAddress,
				toName,
			});

		InternetAddress from = new InternetAddress(fromAddress, fromName);

		InternetAddress to = new InternetAddress(toAddress, toName);

		MailMessage message = new MailMessage(from, to, subject, body, true);

		mailService.sendEmail(message);
	}

	private JournalArticle _approveArticle(
			long userId, long groupId, String articleId, double version,
			String articleURL, PortletPreferences prefs)
		throws PortalException, SystemException {

		// Article

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		JournalArticle article = journalArticlePersistence.findByG_A_V(
			groupId, articleId, version);

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

		// Email

		try {
			sendEmail(article, articleURL, prefs, "granted");
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}

		// Lucene

		try {
			if (article.isIndexable()) {
				String[] tagsEntries = tagsEntryLocalService.getEntryNames(
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

		return article;
	}

	private void _expireArticle(
			JournalArticle article, String articleURL, PortletPreferences prefs)
		throws PortalException, SystemException {

		// Email

		if ((prefs != null) && !article.isApproved() &&
			JournalArticleLocalServiceUtil.isLatestVersion(
				article.getGroupId(), article.getArticleId(),
				article.getVersion())) {

			try {
				sendEmail(article, articleURL, prefs, "denied");
			}
			catch (IOException ioe) {
				throw new SystemException(ioe);
			}
		}

		// Article

		article.setExpirationDate(new Date());

		article.setApproved(false);
		article.setExpired(true);

		journalArticlePersistence.update(article, false);

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

	protected CompanyPersistence companyPersistence =
		CompanyUtil.getPersistence();

	protected JournalArticlePersistence journalArticlePersistence =
		JournalArticleUtil.getPersistence();

	protected MailService mailService = MailServiceFactory.getImpl();

	protected TagsEntryLocalService tagsEntryLocalService =
		TagsEntryLocalServiceFactory.getImpl();

	protected UserPersistence userPersistence = UserUtil.getPersistence();

	private static Log _log = LogFactory.getLog(JournalWorkflowImpl.class);

}