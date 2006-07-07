/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.counter.service.spring.CounterServiceUtil;
import com.liferay.documentlibrary.DuplicateFileException;
import com.liferay.documentlibrary.NoSuchDirectoryException;
import com.liferay.documentlibrary.service.spring.DLServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.CompanyUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.service.spring.ResourceLocalServiceUtil;
import com.liferay.portal.service.spring.SubscriptionLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.messageboards.MessageBodyException;
import com.liferay.portlet.messageboards.MessageSubjectException;
import com.liferay.portlet.messageboards.NoSuchDiscussionException;
import com.liferay.portlet.messageboards.NoSuchMessageException;
import com.liferay.portlet.messageboards.NoSuchMessageFlagException;
import com.liferay.portlet.messageboards.NoSuchThreadException;
import com.liferay.portlet.messageboards.RequiredMessageException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.model.MBMessageFlag;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.jms.MBMessageProducer;
import com.liferay.portlet.messageboards.service.persistence.MBCategoryUtil;
import com.liferay.portlet.messageboards.service.persistence.MBDiscussionUtil;
import com.liferay.portlet.messageboards.service.persistence.MBMessageFinder;
import com.liferay.portlet.messageboards.service.persistence.MBMessageFlagPK;
import com.liferay.portlet.messageboards.service.persistence.MBMessageFlagUtil;
import com.liferay.portlet.messageboards.service.persistence.MBMessagePK;
import com.liferay.portlet.messageboards.service.persistence.MBMessageUtil;
import com.liferay.portlet.messageboards.service.persistence.MBThreadUtil;
import com.liferay.portlet.messageboards.service.spring.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.spring.MBMessageLocalService;
import com.liferay.portlet.messageboards.service.spring.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.spring.MBStatsUserLocalServiceUtil;
import com.liferay.portlet.messageboards.util.Indexer;
import com.liferay.portlet.messageboards.util.MBUtil;
import com.liferay.portlet.messageboards.util.TreeWalker;
import com.liferay.portlet.messageboards.util.comparator.MessageThreadComparator;
import com.liferay.portlet.messageboards.util.comparator.ThreadLastPostDateComparator;
import com.liferay.util.Html;
import com.liferay.util.ObjectValuePair;
import com.liferay.util.RSSUtil;
import com.liferay.util.StringPool;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;

import de.nava.informa.core.ChannelIF;
import de.nava.informa.core.ItemIF;
import de.nava.informa.impl.basic.ChannelBuilder;

import java.io.IOException;

import java.net.URL;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.portlet.PortletPreferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="MBMessageLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBMessageLocalServiceImpl implements MBMessageLocalService {

	public MBMessage addDiscussionMessage(
			String userId, String subject, String body)
		throws PortalException, SystemException {

		String threadId = null;
		String parentMessageId = null;

		return addDiscussionMessage(
			userId, threadId, parentMessageId, subject, body);
	}

	public MBMessage addDiscussionMessage(
			String userId, String threadId, String parentMessageId,
			String subject, String body)
		throws PortalException, SystemException {

		String categoryId = Company.SYSTEM;
		List files = new ArrayList();
		boolean anonymous = false;
		PortletPreferences prefs = null;
		boolean addCommunityPermissions = true;
		boolean addGuestPermissions = true;

		MBCategoryLocalServiceUtil.getSystemCategory();

		return addMessage(
			userId, categoryId, threadId, parentMessageId, subject, body, files,
			anonymous, prefs, addCommunityPermissions, addGuestPermissions);
	}

	public MBMessage addMessage(
			String userId, String categoryId, String subject, String body,
			List files, boolean anonymous, PortletPreferences prefs,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		String threadId = null;
		String parentMessageId = null;

		return addMessage(
			userId, categoryId, threadId, parentMessageId, subject, body, files,
			anonymous, prefs, addCommunityPermissions, addGuestPermissions);
	}

	public MBMessage addMessage(
			String userId, String categoryId, String threadId,
			String parentMessageId, String subject, String body, List files,
			boolean anonymous, PortletPreferences prefs,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		// Message

		User user = UserUtil.findByPrimaryKey(userId);
		MBCategory category = MBCategoryUtil.findByPrimaryKey(categoryId);
		Date now = new Date();

		validate(subject, body);

		String rootMessageId = Long.toString(CounterServiceUtil.increment(
			MBMessage.class.getName()));

		String messageId = rootMessageId;

		MBMessage message = MBMessageUtil.create(
			new MBMessagePK(MBMessage.DEPRECATED_TOPIC_ID, messageId));

		message.setCompanyId(user.getCompanyId());
		message.setUserId(user.getUserId());
		message.setUserName(user.getFullName());
		message.setCreateDate(now);
		message.setModifiedDate(now);

		// Thread

		try {
			MBMessageUtil.findByPrimaryKey(
				new MBMessagePK(message.getTopicId(), parentMessageId));
		}
		catch (NoSuchMessageException nsme) {
			parentMessageId = MBMessage.DEFAULT_PARENT_MESSAGE_ID;
		}

		MBThread thread = null;

		if (threadId != null) {
			try {
				thread = MBThreadUtil.findByPrimaryKey(threadId);
			}
			catch (NoSuchThreadException nste) {
			}
		}

		if (thread == null ||
			parentMessageId.equals(MBMessage.DEFAULT_PARENT_MESSAGE_ID)) {

			threadId = Long.toString(CounterServiceUtil.increment(
				MBThread.class.getName()));

			thread = MBThreadUtil.create(threadId);

			thread.setCategoryId(categoryId);
			thread.setTopicId(message.getTopicId());
			thread.setRootMessageId(rootMessageId);
		}

		thread.setMessageCount(thread.getMessageCount() + 1);
		thread.setLastPostDate(now);

		// Message

		message.setCategoryId(categoryId);
		message.setThreadId(threadId);
		message.setParentMessageId(parentMessageId);
		message.setSubject(subject);
		message.setBody(body);
		message.setAttachments((files.size() > 0 ? true : false));
		message.setAnonymous(anonymous);

		// File attachments

		if (files.size() > 0) {
			String companyId = message.getCompanyId();
			String portletId = Company.SYSTEM;
			String groupId = Group.DEFAULT_PARENT_GROUP_ID;
			String repositoryId = Company.SYSTEM;
			String dirName = message.getAttachmentsDir();

			try {
				DLServiceUtil.deleteDirectory(
					companyId, portletId, repositoryId, dirName);
			}
			catch (NoSuchDirectoryException nsde) {
			}

			DLServiceUtil.addDirectory(companyId, repositoryId, dirName);

			for (int i = 0; i < files.size(); i++) {
				ObjectValuePair ovp = (ObjectValuePair)files.get(i);

				String fileName = (String)ovp.getKey();
				byte[] byteArray = (byte[])ovp.getValue();

				try {
					DLServiceUtil.addFile(
						companyId, portletId, groupId, repositoryId,
						dirName + "/" + fileName, byteArray);
				}
				catch (DuplicateFileException dfe) {
				}
			}
		}

		// Commit

		MBThreadUtil.update(thread);
		MBMessageUtil.update(message);

		// Resources

		if (!category.isDiscussion()) {
			addMessageResources(
				category, message, addCommunityPermissions,
				addGuestPermissions);
		}

		// Statistics

		if (!category.isDiscussion()) {
			MBStatsUserLocalServiceUtil.updateStatsUser(
				category.getGroupId(), userId);
		}

		// Subscriptions

		notifySubscribers(message, prefs, false);

		// Category

		category.setLastPostDate(now);

		MBCategoryUtil.update(category);

		// Testing roll back

		/*if (true) {
			throw new SystemException("Testing roll back");
		}*/

		// Lucene

		try {
			if (!category.isDiscussion()) {
				Indexer.addMessage(
					message.getCompanyId(), category.getGroupId(),
					category.getCategoryId(), threadId, messageId, subject,
					body);
			}
		}
		catch (IOException ioe) {
			_log.error(ioe.getMessage());
		}

		return message;
	}

	public void addMessageResources(
			String categoryId, String messageId,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		MBCategory category = MBCategoryUtil.findByPrimaryKey(categoryId);
		MBMessage message = MBMessageUtil.findByPrimaryKey(
			new MBMessagePK(MBMessage.DEPRECATED_TOPIC_ID, messageId));

		addMessageResources(
			category, message, addCommunityPermissions, addGuestPermissions);
	}

	public void addMessageResources(
			MBCategory category, MBMessage message,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addResources(
			message.getCompanyId(), category.getGroupId(), message.getUserId(),
			MBMessage.class.getName(), message.getPrimaryKey().toString(),
			false, addCommunityPermissions, addGuestPermissions);
	}

	public void deleteDiscussionMessage(String messageId)
		throws PortalException, SystemException {

		deleteMessage(messageId);
	}

	public void deleteDiscussionMessages(String className, String classPK)
		throws PortalException, SystemException {

		try {
			MBDiscussion discussion =
				MBDiscussionUtil.findByC_C(className, classPK);

			List messages = MBMessageUtil.findByT_P(
				discussion.getThreadId(), MBMessage.DEFAULT_PARENT_MESSAGE_ID);

			MBMessage message = (MBMessage)messages.get(0);

			deleteMessage(message.getMessageId());

			MBDiscussionUtil.remove(discussion.getDiscussionId());
		}
		catch (NoSuchDiscussionException nsde) {
		}
	}

	public void deleteMessage(String messageId)
		throws PortalException, SystemException {

		MBMessage message = MBMessageUtil.findByPrimaryKey(
			new MBMessagePK(MBMessage.DEPRECATED_TOPIC_ID, messageId));

		deleteMessage(message);
	}

	public void deleteMessage(MBMessage message)
		throws PortalException, SystemException {

		// Lucene

		try {
			Indexer.deleteMessage(
				message.getCompanyId(), message.getMessageId());
		}
		catch (IOException ioe) {
			_log.error(ioe.getMessage());
		}

		// File attachments

		if (message.isAttachments()) {
			String companyId = message.getCompanyId();
			String portletId = Company.SYSTEM;
			String repositoryId = Company.SYSTEM;
			String dirName = message.getAttachmentsDir();

			try {
				DLServiceUtil.deleteDirectory(
					companyId, portletId, repositoryId, dirName);
			}
			catch (NoSuchDirectoryException nsde) {
			}
		}

		// Thread

		int count = MBMessageUtil.countByThreadId(message.getThreadId());

		if (count == 1) {

			// File attachments

			String companyId = message.getCompanyId();
			String portletId = Company.SYSTEM;
			String repositoryId = Company.SYSTEM;
			String dirName = message.getThreadAttachmentsDir();

			try {
				DLServiceUtil.deleteDirectory(
					companyId, portletId, repositoryId, dirName);
			}
			catch (NoSuchDirectoryException nsde) {
			}

			// Subscriptions

			SubscriptionLocalServiceUtil.deleteSubscriptions(
				message.getCompanyId(), MBThread.class.getName(),
				message.getThreadId());

			// Thread

			MBThreadUtil.remove(message.getThreadId());
		}
		else if (count > 1) {
			MBThread thread = MBThreadUtil.findByPrimaryKey(
				message.getThreadId());

			// Message is a root message

			if (thread.getRootMessageId().equals(message.getMessageId())) {
				List childrenMessages = MBMessageUtil.findByT_P(
					message.getThreadId(), message.getMessageId());

				if (childrenMessages.size() > 1) {
					throw new RequiredMessageException();
				}
				else if (childrenMessages.size() == 1) {
					MBMessage childMessage = (MBMessage)childrenMessages.get(0);

					childMessage.setParentMessageId(
						MBMessage.DEFAULT_PARENT_MESSAGE_ID);

					MBMessageUtil.update(childMessage);

					thread.setRootMessageId(childMessage.getMessageId());

					MBThreadUtil.update(thread);
				}
			}

			// Message is a child message

			else {
				List childrenMessages = MBMessageUtil.findByT_P(
					message.getThreadId(), message.getMessageId());

				// Message has children messages

				if (childrenMessages.size() > 0) {
					Iterator itr = childrenMessages.iterator();

					while (itr.hasNext()) {
						MBMessage childMessage = (MBMessage)itr.next();

						childMessage.setParentMessageId(
							message.getParentMessageId());

						MBMessageUtil.update(childMessage);
					}
				}
			}

			// Thread

			thread.setMessageCount(count - 1);

			MBThreadUtil.update(thread);
		}

		// Message flags

		MBMessageFlagUtil.removeByT_M(
			message.getTopicId(), message.getMessageId());

		// Resources

		if (!message.isDiscussion()) {
			ResourceLocalServiceUtil.deleteResource(
				message.getCompanyId(), MBMessage.class.getName(),
				Resource.TYPE_CLASS, Resource.SCOPE_INDIVIDUAL,
				message.getPrimaryKey().toString());
		}

		// Message

		MBMessageUtil.remove(message.getPrimaryKey());
	}

	public List getCategoryMessages(String categoryId, int begin, int end)
		throws SystemException {

		return MBMessageUtil.findByCategoryId(categoryId, begin, end);
	}

	public int getCategoryMessagesCount(String categoryId)
		throws SystemException {

		return MBMessageUtil.countByCategoryId(categoryId);
	}

	public int getCategoriesMessagesCount(List categoryIds)
		throws SystemException {

		return MBMessageFinder.countByCategoryIds(categoryIds);
	}

	public String getCategoryMessagesCountRSS(
			String categoryId, int begin, int end, double version, String url)
		throws PortalException, SystemException {

		MBCategory category = MBCategoryUtil.findByPrimaryKey(categoryId);

		List messages = MBMessageUtil.findByCategoryId(categoryId, begin, end);

		return exportToRSS(
			category.getName(), category.getName(), version, url, messages);
	}

	public MBMessageDisplay getDiscussionMessageDisplay(
			String userId, String className, String classPK)
		throws PortalException, SystemException {

		MBMessage message = null;

		try {
			MBDiscussion discussion = MBDiscussionUtil.findByC_C(
				className, classPK);

			List messages = MBMessageUtil.findByT_P(
				discussion.getThreadId(), MBMessage.DEFAULT_PARENT_MESSAGE_ID);

			message = (MBMessage)messages.get(0);
		}
		catch (NoSuchDiscussionException nsde) {
			message = MBMessageLocalServiceUtil.addDiscussionMessage(
				userId, className + "_" + classPK, className + "_" + classPK);

			String discussionId = Long.toString(CounterServiceUtil.increment(
				MBDiscussion.class.getName()));

			MBDiscussion discussion = MBDiscussionUtil.create(discussionId);

			discussion.setClassName(className);
			discussion.setClassPK(classPK);
			discussion.setThreadId(message.getThreadId());

			MBDiscussionUtil.update(discussion);
		}

		return getMessageDisplay(message, userId);
	}

	public int getGroupMessagesCount(String groupId) throws SystemException {
		return MBMessageFinder.countByGroupId(groupId);
	}

	public MBMessage getMessage(String messageId)
		throws PortalException, SystemException {

		return MBMessageUtil.findByPrimaryKey(
			new MBMessagePK(MBMessage.DEPRECATED_TOPIC_ID, messageId));
	}

	public MBMessageDisplay getMessageDisplay(String messageId, String userId)
		throws PortalException, SystemException {

		MBMessage message = getMessage(messageId);

		return getMessageDisplay(message, userId);
	}

	public MBMessageDisplay getMessageDisplay(MBMessage message, String userId)
		throws PortalException, SystemException {

		MBCategory category = MBCategoryUtil.findByPrimaryKey(
			message.getCategoryId());

		// Message flags are now tracked by TreeWalker

		/*if (userId != null) {
			MBMessageFlagPK flagPK = new MBMessageFlagPK(
				message.getTopicId(), message.getMessageId(), userId);

			try {
				MBMessageFlagUtil.findByPrimaryKey(flagPK);
			}
			catch (NoSuchMessageFlagException nsmfe) {
				MBMessageFlag messageFlag = MBMessageFlagUtil.create(flagPK);

				messageFlag.setFlag(MBMessageFlag.READ_FLAG);

				MBMessageFlagUtil.update(messageFlag);
			}
		}*/

		MBMessage parentMessage = null;

		if (message.isReply()) {
			parentMessage = MBMessageUtil.findByPrimaryKey(new MBMessagePK(
				message.getTopicId(), message.getParentMessageId()));
		}

		MBThread thread = MBThreadUtil.findByPrimaryKey(message.getThreadId());

		thread.setViewCount(thread.getViewCount() + 1);

		MBThreadUtil.update(thread);

		TreeWalker treeWalker = new TreeWalker(message, userId);

		ThreadLastPostDateComparator comparator =
			new ThreadLastPostDateComparator(false);

		MBThread[] prevAndNextThreads =
			MBThreadUtil.findByCategoryId_PrevAndNext(
				message.getThreadId(), message.getCategoryId(), comparator);

		MBThread previousThread = prevAndNextThreads[0];
		MBThread nextThread = prevAndNextThreads[2];

		MBThread firstThread = null;

		try {
			firstThread = MBThreadUtil.findByCategoryId_First(
				message.getCategoryId(), comparator);
		}
		catch (NoSuchThreadException nste) {
		}

		MBThread lastThread = null;

		try {
			lastThread = MBThreadUtil.findByCategoryId_Last(
				message.getCategoryId(), comparator);
		}
		catch (NoSuchThreadException nste) {
		}

		return new MBMessageDisplay(
			message, parentMessage, category, thread, treeWalker,
			previousThread, nextThread, firstThread, lastThread, userId);
	}

	public List getThreadMessages(String threadId, String userId)
		throws SystemException {

		return getThreadMessages(
			threadId, userId, new MessageThreadComparator());
	}

	public List getThreadMessages(
			String threadId, String userId, Comparator comparator)
		throws SystemException {

		List messages = MBMessageUtil.findByThreadId(threadId);

		if (userId != null) {
			Iterator itr = messages.iterator();

			while (itr.hasNext()) {
				MBMessage message = (MBMessage)itr.next();

				MBMessageFlagPK flagPK = new MBMessageFlagPK(
					message.getTopicId(), message.getMessageId(), userId);

				try {
					MBMessageFlagUtil.findByPrimaryKey(flagPK);
				}
				catch (NoSuchMessageFlagException nsmfe) {
					MBMessageFlag messageFlag =
						MBMessageFlagUtil.create(flagPK);

					messageFlag.setFlag(MBMessageFlag.READ_FLAG);

					MBMessageFlagUtil.update(messageFlag);
				}
			}
		}

		Collections.sort(messages, comparator);

		return messages;
	}

	public int getThreadMessagesCount(String threadId) throws SystemException {
		return MBMessageUtil.countByThreadId(threadId);
	}

	public void subscribeMessage(String userId, String messageId)
		throws PortalException, SystemException {

		MBMessage message = MBMessageUtil.findByPrimaryKey(
			new MBMessagePK(MBMessage.DEPRECATED_TOPIC_ID, messageId));

		SubscriptionLocalServiceUtil.addSubscription(
			userId, MBThread.class.getName(), message.getThreadId());
	}

	public void unsubscribeMessage(String userId, String messageId)
		throws PortalException, SystemException {

		MBMessage message = MBMessageUtil.findByPrimaryKey(
			new MBMessagePK(MBMessage.DEPRECATED_TOPIC_ID, messageId));

		SubscriptionLocalServiceUtil.deleteSubscription(
			userId, MBThread.class.getName(), message.getThreadId());
	}

	public MBMessage updateDiscussionMessage(
			String messageId, String subject, String body)
		throws PortalException, SystemException {

		String categoryId = Company.SYSTEM;
		List files = new ArrayList();
		PortletPreferences prefs = null;

		return updateMessage(
			messageId, categoryId, subject, body, files, prefs);
	}

	public MBMessage updateMessage(
			String messageId, String categoryId, String subject, String body,
			List files, PortletPreferences prefs)
		throws PortalException, SystemException {

		// Message

		MBCategory category = MBCategoryUtil.findByPrimaryKey(categoryId);
		Date now = new Date();

		validate(subject, body);

		MBMessage message = MBMessageUtil.findByPrimaryKey(
			new MBMessagePK(MBMessage.DEPRECATED_TOPIC_ID, messageId));

		// File attachments

		if (files.size() > 0) {
			String companyId = message.getCompanyId();
			String portletId = Company.SYSTEM;
			String groupId = Group.DEFAULT_PARENT_GROUP_ID;
			String repositoryId = Company.SYSTEM;
			String dirName = message.getAttachmentsDir();

			try {
				DLServiceUtil.deleteDirectory(
					companyId, portletId, repositoryId, dirName);
			}
			catch (NoSuchDirectoryException nsde) {
			}

			DLServiceUtil.addDirectory(companyId, repositoryId, dirName);

			for (int i = 0; i < files.size(); i++) {
				ObjectValuePair ovp = (ObjectValuePair)files.get(i);

				String fileName = (String)ovp.getKey();
				byte[] byteArray = (byte[])ovp.getValue();

				try {
					DLServiceUtil.addFile(
						companyId, portletId, groupId, repositoryId,
						dirName + "/" + fileName, byteArray);
				}
				catch (DuplicateFileException dfe) {
				}
			}
		}

		// Message

		message.setModifiedDate(now);
		message.setSubject(subject);
		message.setBody(body);
		message.setAttachments((files.size() > 0 ? true : false));

		MBMessageUtil.update(message);

		// Subscriptions

		notifySubscribers(message, prefs, true);

		// Category

		category.setLastPostDate(now);

		MBCategoryUtil.update(category);

		// Lucene

		try {
			if (!category.isDiscussion()) {
				Indexer.updateMessage(
					message.getCompanyId(), category.getGroupId(),
					category.getCategoryId(), message.getThreadId(), messageId,
					subject, body);
			}
		}
		catch (IOException ioe) {
			_log.error(ioe.getMessage());
		}

		return message;
	}

	protected String exportToRSS(
			String name, String description, double version, String url,
			List messages)
		throws SystemException {

		ChannelBuilder builder = new ChannelBuilder();

		ChannelIF channel = builder.createChannel(name);

		channel.setDescription(description);

		Iterator itr = messages.iterator();

		while (itr.hasNext()) {
			MBMessage message = (MBMessage)itr.next();

			try {
				String firstLine =
					StringUtil.shorten(
						Html.stripHtml(message.getBody()), 80,
						StringPool.BLANK);

				ItemIF item = builder.createItem(
					channel, message.getSubject(), firstLine,
					new URL(url + "&messageId=" + message.getMessageId()));
			}
			catch (IOException ioe) {
				throw new SystemException(ioe);
			}
		}

		return RSSUtil.export(channel, version);
	}

	protected void notifySubscribers(
			MBMessage message, PortletPreferences prefs, boolean update)
		throws PortalException, SystemException {

		try {
			if (prefs == null) {
				return;
			}
			else if (!update && MBUtil.getEmailMessageAddedEnabled(prefs)) {
			}
			else if (update && MBUtil.getEmailMessageUpdatedEnabled(prefs)) {
			}
			else {
				return;
			}

			Company company = CompanyUtil.findByPrimaryKey(
				message.getCompanyId());

			User user = UserUtil.findByPrimaryKey(message.getUserId());

			String portletName = PortalUtil.getPortletTitle(
				PortletKeys.MESSAGE_BOARDS, user);

			String fromName = MBUtil.getEmailFromName(prefs);
			String fromAddress = MBUtil.getEmailFromAddress(prefs);

			String subject = null;
			String body = null;

			if (update) {
				subject = MBUtil.getEmailMessageUpdatedSubject(prefs);
				body = MBUtil.getEmailMessageUpdatedBody(prefs);
			}
			else {
				subject = MBUtil.getEmailMessageAddedSubject(prefs);
				body = MBUtil.getEmailMessageAddedBody(prefs);
			}

			subject = StringUtil.replace(
				subject,
				new String[] {
					"[$FROM_ADDRESS$]",
					"[$FROM_NAME$]",
					"[$MESSAGE_BODY$]",
					"[$MESSAGE_SUBJECT$]",
					"[$MESSAGE_USER_NAME$]",
					"[$PORTAL_URL$]",
					"[$PORTLET_NAME$]"
				},
				new String[] {
					fromAddress,
					fromName,
					message.getBody(),
					message.getSubject(),
					user.getFullName(),
					company.getPortalURL(),
					portletName
				});

			body = StringUtil.replace(
				body,
				new String[] {
					"[$FROM_ADDRESS$]",
					"[$FROM_NAME$]",
					"[$MESSAGE_BODY$]",
					"[$MESSAGE_SUBJECT$]",
					"[$MESSAGE_USER_NAME$]",
					"[$PORTAL_URL$]",
					"[$PORTLET_NAME$]"
				},
				new String[] {
					fromAddress,
					fromName,
					message.getBody(),
					message.getSubject(),
					user.getFullName(),
					company.getPortalURL(),
					portletName
				});

			MBMessageProducer.produce(
				new String[] {
					message.getCompanyId(), message.getThreadId(), fromName,
					fromAddress, subject, body
				});
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	protected void validate(String subject, String body)
		throws PortalException {

		if (Validator.isNull(subject)) {
			throw new MessageSubjectException();
		}

		if (Validator.isNull(body)) {
			throw new MessageBodyException();
		}
	}

	private static Log _log =
		LogFactory.getLog(MBMessageLocalServiceImpl.class);

}