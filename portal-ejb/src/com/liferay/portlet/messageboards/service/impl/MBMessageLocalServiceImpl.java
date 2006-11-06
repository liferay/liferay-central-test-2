/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

import com.liferay.counter.service.spring.CounterLocalServiceUtil;
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
import com.liferay.portlet.messageboards.NoSuchThreadException;
import com.liferay.portlet.messageboards.RequiredMessageException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.service.jms.MBMessageProducer;
import com.liferay.portlet.messageboards.service.persistence.MBCategoryUtil;
import com.liferay.portlet.messageboards.service.persistence.MBDiscussionUtil;
import com.liferay.portlet.messageboards.service.persistence.MBMessageFinder;
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
import com.liferay.util.GetterUtil;
import com.liferay.util.Html;
import com.liferay.util.ObjectValuePair;
import com.liferay.util.RSSUtil;
import com.liferay.util.StringPool;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;

import de.nava.informa.core.ChannelIF;
import de.nava.informa.impl.basic.ChannelBuilder;

import java.io.IOException;

import java.net.URL;

import java.rmi.RemoteException;

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
		double priority = 0.0;
		PortletPreferences prefs = null;
		boolean addCommunityPermissions = true;
		boolean addGuestPermissions = true;

		MBCategoryLocalServiceUtil.getSystemCategory();

		return addMessage(
			userId, categoryId, threadId, parentMessageId, subject, body, files,
			anonymous, priority, prefs, addCommunityPermissions,
			addGuestPermissions);
	}

	public MBMessage addMessage(
			String userId, String categoryId, String subject, String body,
			List files, boolean anonymous, double priority,
			PortletPreferences prefs, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		String threadId = null;
		String parentMessageId = null;

		return addMessage(
			userId, categoryId, threadId, parentMessageId, subject, body, files,
			anonymous, priority, prefs, addCommunityPermissions,
			addGuestPermissions);
	}

	public MBMessage addMessage(
			String userId, String categoryId, String threadId,
			String parentMessageId, String subject, String body, List files,
			boolean anonymous, double priority, PortletPreferences prefs,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		long start = 0;

		if (_log.isDebugEnabled()) {
			start = System.currentTimeMillis();
		}

		// Message

		User user = UserUtil.findByPrimaryKey(userId);
		MBCategory category = MBCategoryUtil.findByPrimaryKey(categoryId);
		Date now = new Date();

		validate(subject, body);

		String messageId = Long.toString(CounterLocalServiceUtil.increment(
			MBMessage.class.getName()));

		start = logAddMessage(messageId, start, 1);

		MBMessage message = MBMessageUtil.create(
			new MBMessagePK(MBMessage.DEPRECATED_TOPIC_ID, messageId));

		message.setCompanyId(user.getCompanyId());
		message.setUserId(user.getUserId());
		message.setUserName(user.getFullName());
		message.setCreateDate(now);
		message.setModifiedDate(now);

		// Thread

		MBMessage parentMessage = MBMessageUtil.fetchByPrimaryKey(
			new MBMessagePK(message.getTopicId(), parentMessageId));

		if (parentMessage == null) {
			parentMessageId = MBMessage.DEFAULT_PARENT_MESSAGE_ID;
		}

		MBThread thread = null;

		if (threadId != null) {
			thread = MBThreadUtil.fetchByPrimaryKey(threadId);
		}

		if (thread == null ||
			parentMessageId.equals(MBMessage.DEFAULT_PARENT_MESSAGE_ID)) {

			threadId = Long.toString(CounterLocalServiceUtil.increment(
				MBThread.class.getName()));

			thread = MBThreadUtil.create(threadId);

			thread.setCategoryId(categoryId);
			thread.setTopicId(message.getTopicId());
			thread.setRootMessageId(messageId);
		}

		thread.setMessageCount(thread.getMessageCount() + 1);

		if (anonymous) {
			thread.setLastPostByUserId(null);
		}
		else {
			thread.setLastPostByUserId(userId);
		}

		thread.setLastPostDate(now);
		thread.setPriority(priority);

		start = logAddMessage(messageId, start, 2);

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
			catch (RemoteException re) {
				throw new SystemException(re);
			}
		}

		start = logAddMessage(messageId, start, 3);

		// Commit

		MBThreadUtil.update(thread);
		MBMessageUtil.update(message);

		start = logAddMessage(messageId, start, 4);

		// Resources

		if (!category.isDiscussion()) {
			addMessageResources(
				category, message, addCommunityPermissions,
				addGuestPermissions);
		}

		start = logAddMessage(messageId, start, 5);

		// Statistics

		if (!category.isDiscussion()) {
			MBStatsUserLocalServiceUtil.updateStatsUser(
				category.getGroupId(), userId);
		}

		start = logAddMessage(messageId, start, 6);

		// Subscriptions

		notifySubscribers(category, message, prefs, false);

		start = logAddMessage(messageId, start, 7);

		// Category

		category.setLastPostDate(now);

		MBCategoryUtil.update(category);

		start = logAddMessage(messageId, start, 8);

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

		start = logAddMessage(messageId, start, 9);

		return message;
	}

	public void addMessageResources(
			String categoryId, String messageId,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		addMessageResources(
			categoryId, null, messageId, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addMessageResources(
			String categoryId, String topicId, String messageId,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		MBCategory category = MBCategoryUtil.findByPrimaryKey(categoryId);

		if (topicId == null) {
			topicId = MBMessage.DEPRECATED_TOPIC_ID;
		}

		MBMessage message = MBMessageUtil.findByPrimaryKey(
			new MBMessagePK(topicId, messageId));

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
			catch (RemoteException re) {
				throw new SystemException(re);
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
			catch (RemoteException re) {
				throw new SystemException(re);
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

	public String getCategoryMessagesRSS(
			String categoryId, int begin, int end, double version, String url)
		throws PortalException, SystemException {

		MBCategory category = MBCategoryUtil.findByPrimaryKey(categoryId);

		List messages = MBMessageUtil.findByCategoryId(categoryId, begin, end);

		return exportToRSS(
			category.getName(), category.getDescription(), version, url,
			messages);
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
			String subject = classPK;
			String body = subject;

			message = MBMessageLocalServiceUtil.addDiscussionMessage(
				userId, subject, body);

			String discussionId = Long.toString(
				CounterLocalServiceUtil.increment(
					MBDiscussion.class.getName()));

			MBDiscussion discussion = MBDiscussionUtil.create(discussionId);

			discussion.setClassName(className);
			discussion.setClassPK(classPK);
			discussion.setThreadId(message.getThreadId());

			MBDiscussionUtil.update(discussion);
		}

		return getMessageDisplay(message, userId);
	}

	public List getGroupMessages(String groupId, int begin, int end)
		throws SystemException {

		return MBMessageFinder.findByGroupId(groupId, begin, end);
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

		Collections.sort(messages, comparator);

		return messages;
	}

	public int getThreadMessagesCount(String threadId) throws SystemException {
		return MBMessageUtil.countByThreadId(threadId);
	}

	public String getThreadMessagesRSS(
			String threadId, int begin, int end, double version, String url)
		throws PortalException, SystemException {

		List messages = MBMessageUtil.findByThreadId(threadId, begin, end);

		String name = StringPool.BLANK;
		String description = StringPool.BLANK;

		if (messages.size() > 0) {
			MBMessage message = (MBMessage)messages.get(0);

			name = message.getSubject();
			description = message.getSubject();
		}

		return exportToRSS(name, description, version, url, messages);
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
		double priority = 0.0;
		PortletPreferences prefs = null;

		return updateMessage(
			messageId, categoryId, subject, body, files, priority, prefs);
	}

	public MBMessage updateMessage(
			String messageId, String categoryId, String subject, String body,
			List files, double priority, PortletPreferences prefs)
		throws PortalException, SystemException {

		// Message

		MBMessage message = MBMessageUtil.findByPrimaryKey(
			new MBMessagePK(MBMessage.DEPRECATED_TOPIC_ID, messageId));

		MBCategory category = getCategory(message, categoryId);
		String oldCategoryId = message.getCategoryId();
		Date now = new Date();

		validate(subject, body);

		// File attachments

		if (files.size() > 0) {
			String companyId = message.getCompanyId();
			String portletId = Company.SYSTEM;
			String groupId = Group.DEFAULT_PARENT_GROUP_ID;
			String repositoryId = Company.SYSTEM;
			String dirName = message.getAttachmentsDir();

			try {
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
			catch (RemoteException re) {
				throw new SystemException(re);
			}
		}

		// Message

		message.setModifiedDate(now);
		message.setSubject(subject);
		message.setBody(body);
		message.setAttachments((files.size() > 0 ? true : false));

		MBMessageUtil.update(message);

		// Subscriptions

		notifySubscribers(category, message, prefs, true);

		// Thread

		MBThread thread = MBThreadUtil.findByPrimaryKey(message.getThreadId());

		thread.setPriority(priority);

		MBThreadUtil.update(thread);

		// Category

		category.setLastPostDate(now);

		MBCategoryUtil.update(category);

		if (!oldCategoryId.equals(category.getCategoryId())) {

			// Messages

			Iterator itr = MBMessageUtil.findByC_T(
				oldCategoryId, thread.getThreadId()).iterator();

			while (itr.hasNext()) {
				MBMessage curMessage = (MBMessage)itr.next();

				curMessage.setCategoryId(category.getCategoryId());

				MBMessageUtil.update(curMessage);

				// Lucene

				try {
					if (!category.isDiscussion()) {
						Indexer.updateMessage(
							curMessage.getCompanyId(), category.getGroupId(),
							category.getCategoryId(), curMessage.getThreadId(),
							curMessage.getMessageId(), curMessage.getSubject(),
							curMessage.getBody());
					}
				}
				catch (IOException ioe) {
					_log.error(ioe.getMessage());
				}
			}

			// Thread

			thread.setCategoryId(category.getCategoryId());

			MBThreadUtil.update(thread);
		}

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

	public MBMessage updateMessage(
			String messageId, Date createDate, Date modifiedDate)
		throws PortalException, SystemException {

		// Message

		MBMessage message = MBMessageUtil.findByPrimaryKey(
			new MBMessagePK(MBMessage.DEPRECATED_TOPIC_ID, messageId));

		message.setCreateDate(createDate);
		message.setModifiedDate(modifiedDate);

		MBMessageUtil.update(message);

		// Thread

		MBThread thread = MBThreadUtil.findByPrimaryKey(message.getThreadId());

		if (message.isAnonymous()) {
			thread.setLastPostByUserId(null);
		}
		else {
			thread.setLastPostByUserId(message.getUserId());
		}

		thread.setLastPostDate(modifiedDate);

		MBThreadUtil.update(thread);

		// Category

		MBCategory category = MBCategoryUtil.findByPrimaryKey(
			message.getCategoryId());

		category.setLastPostDate(modifiedDate);

		MBCategoryUtil.update(category);

		return message;
	}

	public MBMessage updateMessage(String messageId, String body)
		throws PortalException, SystemException {

		MBMessage message = MBMessageUtil.findByPrimaryKey(
			new MBMessagePK(MBMessage.DEPRECATED_TOPIC_ID, messageId));

		message.setBody(body);

		MBMessageUtil.update(message);

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

				builder.createItem(
					channel, message.getSubject(), firstLine,
					new URL(url + "&messageId=" + message.getMessageId()));
			}
			catch (IOException ioe) {
				throw new SystemException(ioe);
			}
		}

		return RSSUtil.export(channel, version);
	}

	protected MBCategory getCategory(MBMessage message, String categoryId)
		throws PortalException, SystemException {

		if (!message.getCategoryId().equals(categoryId)) {
			MBCategory oldCategory = MBCategoryUtil.findByPrimaryKey(
				message.getCategoryId());

			MBCategory newCategory = MBCategoryUtil.fetchByPrimaryKey(
				categoryId);

			if ((newCategory == null) ||
				(!oldCategory.getGroupId().equals(newCategory.getGroupId()))) {

				categoryId = message.getCategoryId();
			}
		}

		return MBCategoryUtil.findByPrimaryKey(categoryId);
	}

	protected long logAddMessage(String messageId, long start, int block) {
		if (!_log.isDebugEnabled()) {
			return 0;
		}

		int messageIdInt = GetterUtil.getInteger(messageId);

		if ((messageIdInt != 1) && ((messageIdInt % 10) != 0)) {
			return 0;
		}

		long end = System.currentTimeMillis();

		_log.debug(
			"Adding message block " + block + " for " + messageId + " takes " +
				(end - start) + " ms");

		return end;
	}

	protected void notifySubscribers(
			MBCategory category, MBMessage message, PortletPreferences prefs,
			boolean update)
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

			List categoryIds = new ArrayList();

			MBCategoryLocalServiceUtil.getSubcategoryIds(
				categoryIds, category.getGroupId(), category.getCategoryId());

			categoryIds.add(category.getCategoryId());

			String portletName = PortalUtil.getPortletTitle(
				PortletKeys.MESSAGE_BOARDS, user);

			String fromName = MBUtil.getEmailFromName(prefs);
			String fromAddress = MBUtil.getEmailFromAddress(prefs);
			String mailingListAddress = MBUtil.getMailingListAddress(
				message.getCategoryId(), company.getCompanyId());
			String replyToAddress = mailingListAddress;
			String messageId = MBUtil.getMailId(
				message.getMessageId(), company.getCompanyId());

			fromName = StringUtil.replace(
				fromName,
				new String[] {
					"[$COMPANY_ID$]",
					"[$MAILING_LIST_ADDRESS$]",
					"[$MESSAGE_USER_ADDRESS$]",
					"[$MESSAGE_USER_NAME$]",
					"[$PORTLET_NAME$]"
				},
				new String[] {
					company.getCompanyId(),
					mailingListAddress,
					user.getEmailAddress(),
					user.getFullName(),
					portletName
				});

			fromAddress = StringUtil.replace(
				fromAddress,
				new String[] {
					"[$COMPANY_ID$]",
					"[$MAILING_LIST_ADDRESS$]",
					"[$MESSAGE_USER_ADDRESS$]",
					"[$MESSAGE_USER_NAME$]",
					"[$PORTLET_NAME$]"
				},
				new String[] {
					company.getCompanyId(),
					mailingListAddress,
					user.getEmailAddress(),
					user.getFullName(),
					portletName
				});

			String subjectPrefix = null;
			String body = null;
			String signature = null;
			String inReplyTo = null;

			if (update) {
				subjectPrefix = MBUtil.getEmailMessageUpdatedSubjectPrefix(
					prefs);
				body = MBUtil.getEmailMessageUpdatedBody(prefs);
				signature = MBUtil.getEmailMessageUpdatedSignature(prefs);
				inReplyTo = MBUtil.getMailId(
					message.getParentMessageId(), company.getCompanyId());
			}
			else {
				subjectPrefix = MBUtil.getEmailMessageAddedSubjectPrefix(prefs);
				body = MBUtil.getEmailMessageAddedBody(prefs);
				signature = MBUtil.getEmailMessageAddedSignature(prefs);
			}

			if (Validator.isNotNull(signature)) {
				body +=  "\n--\n" + signature;
			}

			subjectPrefix = StringUtil.replace(
				subjectPrefix,
				new String[] {
					"[$CATEGORY_NAME$]",
					"[$COMPANY_ID$]",
					"[$FROM_ADDRESS$]",
					"[$FROM_NAME$]",
  					"[$MAILING_LIST_ADDRESS$]",
					"[$MESSAGE_BODY$]",
					"[$MESSAGE_SUBJECT$]",
					"[$MESSAGE_USER_ADDRESS$]",
					"[$MESSAGE_USER_NAME$]",
					"[$PORTAL_URL$]",
					"[$PORTLET_NAME$]"
				},
				new String[] {
					category.getName(),
					company.getCompanyId(),
					fromAddress,
					fromName,
					mailingListAddress,
					message.getBody(),
					message.getSubject(),
					user.getEmailAddress(),
					user.getFullName(),
					company.getPortalURL(),
					portletName
				});

			body = StringUtil.replace(
				body,
				new String[] {
					"[$CATEGORY_NAME$]",
					"[$COMPANY_ID$]",
					"[$FROM_ADDRESS$]",
					"[$FROM_NAME$]",
  					"[$MAILING_LIST_ADDRESS$]",
					"[$MESSAGE_BODY$]",
					"[$MESSAGE_SUBJECT$]",
					"[$MESSAGE_USER_ADDRESS$]",
					"[$MESSAGE_USER_NAME$]",
					"[$PORTAL_URL$]",
					"[$PORTLET_NAME$]"
				},
				new String[] {
					category.getName(),
					company.getCompanyId(),
					fromAddress,
					fromName,
					mailingListAddress,
					message.getBody(),
					message.getSubject(),
					user.getEmailAddress(),
					user.getFullName(),
					company.getPortalURL(),
					portletName
				});

			String subject = message.getSubject();

			if (subject.indexOf(subjectPrefix) == -1) {
				subject = subjectPrefix + subject;
			}

			MBMessageProducer.produce(
				new String[] {
					message.getCompanyId(), message.getUserId(),
					StringUtil.merge(categoryIds), message.getThreadId(),
					fromName, fromAddress, subject, body, replyToAddress,
					messageId, inReplyTo
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