/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.documentlibrary.DuplicateFileException;
import com.liferay.documentlibrary.NoSuchDirectoryException;
import com.liferay.documentlibrary.service.DLServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.ModelHintsUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.CompanyImpl;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.SubscriptionLocalServiceUtil;
import com.liferay.portal.service.persistence.CompanyUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portlet.messageboards.MessageBodyException;
import com.liferay.portlet.messageboards.MessageSubjectException;
import com.liferay.portlet.messageboards.NoSuchDiscussionException;
import com.liferay.portlet.messageboards.NoSuchThreadException;
import com.liferay.portlet.messageboards.RequiredMessageException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.model.MBStatsUser;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.MBTreeWalker;
import com.liferay.portlet.messageboards.model.impl.MBMessageDisplayImpl;
import com.liferay.portlet.messageboards.model.impl.MBMessageImpl;
import com.liferay.portlet.messageboards.model.impl.MBThreadImpl;
import com.liferay.portlet.messageboards.model.impl.MBTreeWalkerImpl;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBStatsUserLocalServiceUtil;
import com.liferay.portlet.messageboards.service.base.MBMessageLocalServiceBaseImpl;
import com.liferay.portlet.messageboards.service.jms.MBMessageProducer;
import com.liferay.portlet.messageboards.service.persistence.MBCategoryUtil;
import com.liferay.portlet.messageboards.service.persistence.MBDiscussionUtil;
import com.liferay.portlet.messageboards.service.persistence.MBMessageFinder;
import com.liferay.portlet.messageboards.service.persistence.MBMessageFlagUtil;
import com.liferay.portlet.messageboards.service.persistence.MBMessageUtil;
import com.liferay.portlet.messageboards.service.persistence.MBStatsUserUtil;
import com.liferay.portlet.messageboards.service.persistence.MBThreadUtil;
import com.liferay.portlet.messageboards.util.Indexer;
import com.liferay.portlet.messageboards.util.MBUtil;
import com.liferay.portlet.messageboards.util.comparator.MessageThreadComparator;
import com.liferay.portlet.messageboards.util.comparator.ThreadLastPostDateComparator;
import com.liferay.portlet.tags.service.TagsAssetLocalServiceUtil;

import java.io.IOException;

import java.rmi.RemoteException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.portlet.PortletPreferences;

import org.apache.commons.lang.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="MBMessageLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MBMessageLocalServiceImpl extends MBMessageLocalServiceBaseImpl {

	public MBMessage addDiscussionMessage(
			long userId, String subject, String body)
		throws PortalException, SystemException {

		long threadId = 0;
		long parentMessageId = 0;

		return addDiscussionMessage(
			userId, threadId, parentMessageId, subject, body);
	}

	public MBMessage addDiscussionMessage(
			long userId, long threadId, long parentMessageId, String subject,
			String body)
		throws PortalException, SystemException {

		long categoryId = CompanyImpl.SYSTEM;
		List files = new ArrayList();
		boolean anonymous = false;
		double priority = 0.0;
		String[] tagsEntries = null;
		PortletPreferences prefs = null;
		boolean addCommunityPermissions = true;
		boolean addGuestPermissions = true;

		MBCategoryLocalServiceUtil.getSystemCategory();

		return addMessage(
			userId, categoryId, threadId, parentMessageId, subject, body, files,
			anonymous, priority, tagsEntries, prefs, addCommunityPermissions,
			addGuestPermissions);
	}

	public MBMessage addMessage(
			long userId, long categoryId, String subject, String body,
			List files, boolean anonymous, double priority,
			String[] tagsEntries, PortletPreferences prefs,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addMessage(
			userId, categoryId, subject, body, files, anonymous, priority,
			tagsEntries, prefs, new Boolean(addCommunityPermissions),
			new Boolean(addGuestPermissions), null, null);
	}

	public MBMessage addMessage(
			long userId, long categoryId, String subject, String body,
			List files, boolean anonymous, double priority,
			String[] tagsEntries, PortletPreferences prefs,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		return addMessage(
			userId, categoryId, subject, body, files, anonymous, priority,
			tagsEntries, prefs, null, null, communityPermissions,
			guestPermissions);
	}

	public MBMessage addMessage(
			long userId, long categoryId, String subject, String body,
			List files, boolean anonymous, double priority,
			String[] tagsEntries, PortletPreferences prefs,
			Boolean addCommunityPermissions, Boolean addGuestPermissions,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		long threadId = 0;
		long parentMessageId = 0;

		return addMessage(
			userId, categoryId, threadId, parentMessageId, subject, body, files,
			anonymous, priority, tagsEntries, prefs, addCommunityPermissions,
			addGuestPermissions, communityPermissions, guestPermissions);
	}

	public MBMessage addMessage(
			long userId, long categoryId, long threadId, long parentMessageId,
			String subject, String body, List files, boolean anonymous,
			double priority, String[] tagsEntries, PortletPreferences prefs,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addMessage(
			userId, categoryId, threadId, parentMessageId, subject, body, files,
			anonymous, priority, tagsEntries, prefs,
			new Boolean(addCommunityPermissions),
			new Boolean(addGuestPermissions), null, null);
	}

	public MBMessage addMessage(
			long userId, long categoryId, long threadId, long parentMessageId,
			String subject, String body, List files, boolean anonymous,
			double priority, String[] tagsEntries, PortletPreferences prefs,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		return addMessage(
			userId, categoryId, threadId, parentMessageId, subject, body, files,
			anonymous, priority, tagsEntries, prefs, null, null,
			communityPermissions, guestPermissions);
	}

	public MBMessage addMessage(
			long userId, long categoryId, long threadId, long parentMessageId,
			String subject, String body, List files, boolean anonymous,
			double priority, String[] tagsEntries, PortletPreferences prefs,
			Boolean addCommunityPermissions, Boolean addGuestPermissions,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		StopWatch stopWatch = null;

		if (_log.isDebugEnabled()) {
			stopWatch = new StopWatch();

			stopWatch.start();
		}

		// Message

		User user = UserUtil.findByPrimaryKey(userId);
		MBCategory category = MBCategoryUtil.findByPrimaryKey(categoryId);
		subject = ModelHintsUtil.trimString(
			MBMessage.class.getName(), "subject", subject);
		anonymous = user.isDefaultUser() ? true : anonymous;
		Date now = new Date();

		validate(subject, body);

		long messageId = CounterLocalServiceUtil.increment();

		logAddMessage(messageId, stopWatch, 1);

		MBMessage message = MBMessageUtil.create(messageId);

		message.setCompanyId(user.getCompanyId());
		message.setUserId(user.getUserId());
		message.setUserName(user.getFullName());
		message.setCreateDate(now);
		message.setModifiedDate(now);

		// Thread

		MBMessage parentMessage = MBMessageUtil.fetchByPrimaryKey(
			parentMessageId);

		if (parentMessage == null) {
			parentMessageId = MBMessageImpl.DEFAULT_PARENT_MESSAGE_ID;
		}

		MBThread thread = null;

		if (threadId > 0) {
			thread = MBThreadUtil.fetchByPrimaryKey(threadId);
		}

		if ((thread == null) ||
			(parentMessageId == MBMessageImpl.DEFAULT_PARENT_MESSAGE_ID)) {

			threadId = CounterLocalServiceUtil.increment();

			thread = MBThreadUtil.create(threadId);

			thread.setCategoryId(categoryId);
			thread.setRootMessageId(messageId);
		}

		thread.setMessageCount(thread.getMessageCount() + 1);

		if (anonymous) {
			thread.setLastPostByUserId(0);
		}
		else {
			thread.setLastPostByUserId(userId);
		}

		thread.setLastPostDate(now);

		if (priority != MBThreadImpl.PRIORITY_NOT_GIVEN) {
			thread.setPriority(priority);
		}

		logAddMessage(messageId, stopWatch, 2);

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
			long companyId = message.getCompanyId();
			String portletId = CompanyImpl.SYSTEM_STRING;
			long groupId = GroupImpl.DEFAULT_PARENT_GROUP_ID;
			long repositoryId = CompanyImpl.SYSTEM;
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

		logAddMessage(messageId, stopWatch, 3);

		// Commit

		MBThreadUtil.update(thread);
		MBMessageUtil.update(message);

		logAddMessage(messageId, stopWatch, 4);

		// Resources

		if (!category.isDiscussion()) {
			if (user.isDefaultUser()) {
				addMessageResources(category, message, true, true);
			}
			else if ((addCommunityPermissions != null) &&
					 (addGuestPermissions != null)) {

				addMessageResources(
					category, message, addCommunityPermissions.booleanValue(),
					addGuestPermissions.booleanValue());
			}
			else {
				addMessageResources(
					category, message, communityPermissions, guestPermissions);
			}
		}

		logAddMessage(messageId, stopWatch, 5);

		// Statistics

		if (!category.isDiscussion()) {
			MBStatsUserLocalServiceUtil.updateStatsUser(
				category.getGroupId(), userId);
		}

		logAddMessage(messageId, stopWatch, 6);

		// Subscriptions

		notifySubscribers(category, message, prefs, false);

		logAddMessage(messageId, stopWatch, 7);

		// Category

		category.setLastPostDate(now);

		MBCategoryUtil.update(category);

		logAddMessage(messageId, stopWatch, 8);

		// Tags

		updateAsset(message, tagsEntries);

		logAddMessage(messageId, stopWatch, 9);

		// Testing roll back

		/*if (true) {
			throw new SystemException("Testing roll back");
		}*/

		// Lucene

		try {
			if (!category.isDiscussion()) {
				Indexer.addMessage(
					message.getCompanyId(), category.getGroupId(),
					user.getFullName(), category.getCategoryId(), threadId,
					messageId, subject, body);
			}
		}
		catch (IOException ioe) {
			_log.error("Indexing " + messageId, ioe);
		}

		logAddMessage(messageId, stopWatch, 9);

		return message;
	}

	public void addMessageResources(
			long categoryId, long messageId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		addMessageResources(
			categoryId, null, messageId, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addMessageResources(
			long categoryId, String topicId, long messageId,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		MBCategory category = MBCategoryUtil.findByPrimaryKey(categoryId);
		MBMessage message = MBMessageUtil.findByPrimaryKey(messageId);

		addMessageResources(
			category, message, addCommunityPermissions, addGuestPermissions);
	}

	public void addMessageResources(
			MBCategory category, MBMessage message,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addResources(
			message.getCompanyId(), category.getGroupId(), message.getUserId(),
			MBMessage.class.getName(), message.getMessageId(),
			false, addCommunityPermissions, addGuestPermissions);
	}

	public void addMessageResources(
			long categoryId, long messageId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		addMessageResources(
			categoryId, null, messageId, communityPermissions,
			guestPermissions);
	}

	public void addMessageResources(
			long categoryId, String topicId, long messageId,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		MBCategory category = MBCategoryUtil.findByPrimaryKey(categoryId);
		MBMessage message = MBMessageUtil.findByPrimaryKey(messageId);

		addMessageResources(
			category, message, communityPermissions, guestPermissions);
	}

	public void addMessageResources(
			MBCategory category, MBMessage message,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addModelResources(
			message.getCompanyId(), category.getGroupId(), message.getUserId(),
			MBMessage.class.getName(), message.getMessageId(),
			communityPermissions, guestPermissions);
	}

	public void deleteDiscussionMessage(long messageId)
		throws PortalException, SystemException {

		deleteMessage(messageId);
	}

	public void deleteDiscussionMessages(String className, long classPK)
		throws PortalException, SystemException {

		try {
			long classNameId = PortalUtil.getClassNameId(className);

			MBDiscussion discussion =
				MBDiscussionUtil.findByC_C(classNameId, classPK);

			List messages = MBMessageUtil.findByT_P(
				discussion.getThreadId(),
				MBMessageImpl.DEFAULT_PARENT_MESSAGE_ID);

			MBMessage message = (MBMessage)messages.get(0);

			deleteMessage(message.getMessageId());

			MBDiscussionUtil.remove(discussion.getDiscussionId());
		}
		catch (NoSuchDiscussionException nsde) {
		}
	}

	public void deleteMessage(long messageId)
		throws PortalException, SystemException {

		MBMessage message = MBMessageUtil.findByPrimaryKey(messageId);

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
			_log.error("Deleting index " + message.getMessageId(), ioe);
		}

		// File attachments

		if (message.isAttachments()) {
			long companyId = message.getCompanyId();
			String portletId = CompanyImpl.SYSTEM_STRING;
			long repositoryId = CompanyImpl.SYSTEM;
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

			long companyId = message.getCompanyId();
			String portletId = CompanyImpl.SYSTEM_STRING;
			long repositoryId = CompanyImpl.SYSTEM;
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

			if (thread.getRootMessageId() == message.getMessageId()) {
				List childrenMessages = MBMessageUtil.findByT_P(
					message.getThreadId(), message.getMessageId());

				if (childrenMessages.size() > 1) {
					throw new RequiredMessageException();
				}
				else if (childrenMessages.size() == 1) {
					MBMessage childMessage = (MBMessage)childrenMessages.get(0);

					childMessage.setParentMessageId(
						MBMessageImpl.DEFAULT_PARENT_MESSAGE_ID);

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

		// Tags

		TagsAssetLocalServiceUtil.deleteAsset(
			MBMessage.class.getName(), message.getMessageId());

		// Message flags

		MBMessageFlagUtil.removeByMessageId(message.getMessageId());

		// Resources

		if (!message.isDiscussion()) {
			ResourceLocalServiceUtil.deleteResource(
				message.getCompanyId(), MBMessage.class.getName(),
				ResourceImpl.SCOPE_INDIVIDUAL, message.getMessageId());
		}

		// Message

		MBMessageUtil.remove(message.getPrimaryKey());
	}

	public List getCategoryMessages(long categoryId, int begin, int end)
		throws SystemException {

		return MBMessageUtil.findByCategoryId(categoryId, begin, end);
	}

	public List getCategoryMessages(
			long categoryId, int begin, int end, OrderByComparator obc)
		throws SystemException {

		return MBMessageUtil.findByCategoryId(categoryId, begin, end, obc);
	}

	public int getCategoryMessagesCount(long categoryId)
		throws SystemException {

		return MBMessageUtil.countByCategoryId(categoryId);
	}

	public int getCategoriesMessagesCount(List categoryIds)
		throws SystemException {

		return MBMessageFinder.countByCategoryIds(categoryIds);
	}

	public MBMessageDisplay getDiscussionMessageDisplay(
			long userId, String className, long classPK)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		MBMessage message = null;

		try {
			MBDiscussion discussion = MBDiscussionUtil.findByC_C(
				classNameId, classPK);

			List messages = MBMessageUtil.findByT_P(
				discussion.getThreadId(),
				MBMessageImpl.DEFAULT_PARENT_MESSAGE_ID);

			message = (MBMessage)messages.get(0);
		}
		catch (NoSuchDiscussionException nsde) {
			String subject = String.valueOf(classPK);
			String body = subject;

			message = MBMessageLocalServiceUtil.addDiscussionMessage(
				userId, subject, body);

			long discussionId = CounterLocalServiceUtil.increment();

			MBDiscussion discussion = MBDiscussionUtil.create(discussionId);

			discussion.setClassNameId(classNameId);
			discussion.setClassPK(classPK);
			discussion.setThreadId(message.getThreadId());

			MBDiscussionUtil.update(discussion);
		}

		return getMessageDisplay(message);
	}

	public List getGroupMessages(long groupId, int begin, int end)
		throws SystemException {

		return MBMessageFinder.findByGroupId(groupId, begin, end);
	}

	public int getGroupMessagesCount(long groupId) throws SystemException {
		return MBMessageFinder.countByGroupId(groupId);
	}

	public MBMessage getMessage(long messageId)
		throws PortalException, SystemException {

		return MBMessageUtil.findByPrimaryKey(messageId);
	}

	public MBMessageDisplay getMessageDisplay(long messageId)
		throws PortalException, SystemException {

		MBMessage message = getMessage(messageId);

		return getMessageDisplay(message);
	}

	public MBMessageDisplay getMessageDisplay(MBMessage message)
		throws PortalException, SystemException {

		MBCategory category = MBCategoryUtil.findByPrimaryKey(
			message.getCategoryId());

		MBMessage parentMessage = null;

		if (message.isReply()) {
			parentMessage = MBMessageUtil.findByPrimaryKey(
				message.getParentMessageId());
		}

		MBThread thread = MBThreadUtil.findByPrimaryKey(message.getThreadId());

		thread.setViewCount(thread.getViewCount() + 1);

		MBThreadUtil.update(thread);

		MBTreeWalker treeWalker = new MBTreeWalkerImpl(message);

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

		return new MBMessageDisplayImpl(
			message, parentMessage, category, thread, treeWalker,
			previousThread, nextThread, firstThread, lastThread);
	}

	public List getThreadMessages(long threadId) throws SystemException {
		return getThreadMessages(threadId, new MessageThreadComparator());
	}

	public List getThreadMessages(long threadId, Comparator comparator)
		throws SystemException {

		List messages = MBMessageUtil.findByThreadId(threadId);

		Collections.sort(messages, comparator);

		return messages;
	}

	public int getThreadMessagesCount(long threadId) throws SystemException {
		return MBMessageUtil.countByThreadId(threadId);
	}

	public void subscribeMessage(long userId, long messageId)
		throws PortalException, SystemException {

		MBMessage message = MBMessageUtil.findByPrimaryKey(messageId);

		SubscriptionLocalServiceUtil.addSubscription(
			userId, MBThread.class.getName(), message.getThreadId());
	}

	public void unsubscribeMessage(long userId, long messageId)
		throws PortalException, SystemException {

		MBMessage message = MBMessageUtil.findByPrimaryKey(messageId);

		SubscriptionLocalServiceUtil.deleteSubscription(
			userId, MBThread.class.getName(), message.getThreadId());
	}

	public MBMessage updateDiscussionMessage(
			long messageId, String subject, String body)
		throws PortalException, SystemException {

		long categoryId = CompanyImpl.SYSTEM;
		List files = new ArrayList();
		double priority = 0.0;
		String[] tagsEntries = null;
		PortletPreferences prefs = null;

		return updateMessage(
			messageId, categoryId, subject, body, files, priority, tagsEntries,
			prefs);
	}

	public MBMessage updateMessage(
			long messageId, long categoryId, String subject, String body,
			List files, double priority, String[] tagsEntries,
			PortletPreferences prefs)
		throws PortalException, SystemException {

		// Message

		MBMessage message = MBMessageUtil.findByPrimaryKey(messageId);

		MBCategory category = getCategory(message, categoryId);
		long oldCategoryId = message.getCategoryId();
		subject = ModelHintsUtil.trimString(
			MBMessage.class.getName(), "subject", subject);
		Date now = new Date();

		validate(subject, body);

		// File attachments

		if (files.size() > 0) {
			long companyId = message.getCompanyId();
			String portletId = CompanyImpl.SYSTEM_STRING;
			long groupId = GroupImpl.DEFAULT_PARENT_GROUP_ID;
			long repositoryId = CompanyImpl.SYSTEM;
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

		if (priority != MBThreadImpl.PRIORITY_NOT_GIVEN) {
			thread.setPriority(priority);
		}

		MBThreadUtil.update(thread);

		// Category

		category.setLastPostDate(now);

		MBCategoryUtil.update(category);

		if (oldCategoryId != category.getCategoryId()) {

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
							curMessage.getUserName(), category.getCategoryId(),
							curMessage.getThreadId(), curMessage.getMessageId(),
							curMessage.getSubject(), curMessage.getBody());
					}
				}
				catch (IOException ioe) {
					_log.error("Indexing " + messageId, ioe);
				}
			}

			// Thread

			thread.setCategoryId(category.getCategoryId());

			MBThreadUtil.update(thread);
		}

		// Tags

		updateAsset(message, tagsEntries);

		// Lucene

		try {
			if (!category.isDiscussion()) {
				Indexer.updateMessage(
					message.getCompanyId(), category.getGroupId(),
					message.getUserName(), category.getCategoryId(),
					message.getThreadId(), messageId, subject, body);
			}
		}
		catch (IOException ioe) {
			_log.error("Indexing " + messageId, ioe);
		}

		return message;
	}

	public MBMessage updateMessage(
			long messageId, Date createDate, Date modifiedDate)
		throws PortalException, SystemException {

		// Message

		MBMessage message = MBMessageUtil.findByPrimaryKey(messageId);

		message.setCreateDate(createDate);
		message.setModifiedDate(modifiedDate);

		MBMessageUtil.update(message);

		// Thread

		MBThread thread = MBThreadUtil.findByPrimaryKey(message.getThreadId());

		if (message.isAnonymous()) {
			thread.setLastPostByUserId(0);
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

		// Statistics

		MBStatsUser statsUser = MBStatsUserUtil.fetchByG_U(
			category.getGroupId(), message.getUserId());

		if (statsUser != null) {
			statsUser.setLastPostDate(modifiedDate);

			MBStatsUserUtil.update(statsUser);
		}

		return message;
	}

	public MBMessage updateMessage(long messageId, String body)
		throws PortalException, SystemException {

		MBMessage message = MBMessageUtil.findByPrimaryKey(messageId);

		message.setBody(body);

		MBMessageUtil.update(message);

		return message;
	}

	protected MBCategory getCategory(MBMessage message, long categoryId)
		throws PortalException, SystemException {

		if (message.getCategoryId() != categoryId) {
			MBCategory oldCategory = MBCategoryUtil.findByPrimaryKey(
				message.getCategoryId());

			MBCategory newCategory = MBCategoryUtil.fetchByPrimaryKey(
				categoryId);

			if ((newCategory == null) ||
				(oldCategory.getGroupId() != newCategory.getGroupId())) {

				categoryId = message.getCategoryId();
			}
		}

		return MBCategoryUtil.findByPrimaryKey(categoryId);
	}

	protected void logAddMessage(
		long messageId, StopWatch stopWatch, int block) {

		if (_log.isDebugEnabled()) {
			if ((messageId != 1) && ((messageId % 10) != 0)) {
				return;
			}

			_log.debug(
				"Adding message block " + block + " for " + messageId +
					" takes " + stopWatch.getTime() + " ms");
		}
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

			categoryIds.add(new Long(category.getCategoryId()));

			String portletName = PortalUtil.getPortletTitle(
				PortletKeys.MESSAGE_BOARDS, user);

			String fromName = MBUtil.getEmailFromName(prefs);
			String fromAddress = MBUtil.getEmailFromAddress(prefs);

			String mailingListAddress = StringPool.BLANK;

			if (GetterUtil.getBoolean(PropsUtil.get(
					PropsUtil.SMTP_SERVER_ENABLED))) {

				mailingListAddress = MBUtil.getMailingListAddress(
					message.getCategoryId(), company.getMx());
			}

			String replyToAddress = mailingListAddress;
			String messageId = MBUtil.getMailId(
				message.getMessageId(), company.getMx());

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
					String.valueOf(company.getCompanyId()),
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
					String.valueOf(company.getCompanyId()),
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
					message.getParentMessageId(), company.getMx());
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
					"[$MESSAGE_ID$]",
					"[$MESSAGE_SUBJECT$]",
					"[$MESSAGE_USER_ADDRESS$]",
					"[$MESSAGE_USER_NAME$]",
					"[$PORTAL_URL$]",
					"[$PORTLET_NAME$]"
				},
				new String[] {
					category.getName(),
					String.valueOf(company.getCompanyId()),
					fromAddress,
					fromName,
					mailingListAddress,
					message.getBody(),
					String.valueOf(message.getMessageId()),
					message.getSubject(),
					user.getEmailAddress(),
					user.getFullName(),
					company.getVirtualHost(),
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
					"[$MESSAGE_ID$]",
					"[$MESSAGE_SUBJECT$]",
					"[$MESSAGE_USER_ADDRESS$]",
					"[$MESSAGE_USER_NAME$]",
					"[$PORTAL_URL$]",
					"[$PORTLET_NAME$]"
				},
				new String[] {
					category.getName(),
					String.valueOf(company.getCompanyId()),
					fromAddress,
					fromName,
					mailingListAddress,
					message.getBody(),
					String.valueOf(message.getMessageId()),
					message.getSubject(),
					user.getEmailAddress(),
					user.getFullName(),
					company.getVirtualHost(),
					portletName
				});

			String subject = message.getSubject();

			if (subject.indexOf(subjectPrefix) == -1) {
				subject = subjectPrefix + subject;
			}

			MBMessageProducer.produce(
				new String[] {
					String.valueOf(message.getCompanyId()),
					String.valueOf(message.getUserId()),
					StringUtil.merge(categoryIds),
					String.valueOf(message.getThreadId()),
					fromName, fromAddress, subject, body, replyToAddress,
					messageId, inReplyTo
				});
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	protected void updateAsset(MBMessage message, String[] tagsEntries)
		throws PortalException, SystemException {

		if (tagsEntries == null) {
			return;
		}

		TagsAssetLocalServiceUtil.updateAsset(
			message.getUserId(), MBMessage.class.getName(),
			message.getMessageId(), tagsEntries, null, null, null, null,
			ContentTypes.TEXT_HTML, message.getSubject(), message.getSubject(),
			message.getSubject(), null, 0, 0);
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