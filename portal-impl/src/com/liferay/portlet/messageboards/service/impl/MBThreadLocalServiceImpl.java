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

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.documentlibrary.DuplicateDirectoryException;
import com.liferay.documentlibrary.NoSuchDirectoryException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.impl.MBThreadImpl;
import com.liferay.portlet.messageboards.service.base.MBThreadLocalServiceBaseImpl;
import com.liferay.portlet.messageboards.util.Indexer;

import java.io.IOException;

import java.rmi.RemoteException;

import java.util.List;

import javax.portlet.PortletPreferences;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.queryParser.ParseException;

/**
 * <a href="MBThreadLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MBThreadLocalServiceImpl extends MBThreadLocalServiceBaseImpl {

	public void deleteThread(long threadId)
		throws PortalException, SystemException {

		MBThread thread = mbThreadPersistence.findByPrimaryKey(threadId);

		deleteThread(thread);
	}

	public void deleteThread(MBThread thread)
		throws PortalException, SystemException {

		MBMessage rootMessage = mbMessagePersistence.findByPrimaryKey(
			thread.getRootMessageId());

		// Lucene

		try {
			Indexer.deleteMessages(
				rootMessage.getCompanyId(), thread.getThreadId());
		}
		catch (IOException ioe) {
			_log.error("Deleting index " + thread.getThreadId(), ioe);
		}
		catch (ParseException pe) {
			_log.error("Deleting index " + thread.getThreadId(), pe);
		}

		// Attachments

		long companyId = rootMessage.getCompanyId();
		String portletId = CompanyConstants.SYSTEM_STRING;
		long repositoryId = CompanyConstants.SYSTEM;
		String dirName = thread.getAttachmentsDir();

		try {
			dlService.deleteDirectory(
				companyId, portletId, repositoryId, dirName);
		}
		catch (NoSuchDirectoryException nsde) {
		}
		catch (RemoteException re) {
			throw new SystemException(re);
		}

		// Messages

		List<MBMessage> messages = mbMessagePersistence.findByThreadId(
			thread.getThreadId());

		for (MBMessage message : messages) {

			// Tags

			tagsAssetLocalService.deleteAsset(
				MBMessage.class.getName(), message.getMessageId());

			// Social

			socialActivityLocalService.deleteActivities(
				MBMessage.class.getName(), message.getMessageId());

			// Message flags

			mbMessageFlagPersistence.removeByMessageId(message.getMessageId());

			// Resources

			if (!message.isDiscussion()) {
				resourceLocalService.deleteResource(
					message.getCompanyId(), MBMessage.class.getName(),
					ResourceConstants.SCOPE_INDIVIDUAL, message.getMessageId());
			}

			// Message

			mbMessagePersistence.remove(message.getMessageId());
		}

		// Thread

		mbThreadPersistence.remove(thread.getThreadId());
	}

	public void deleteThreads(long categoryId)
		throws PortalException, SystemException {

		List<MBThread> threads = mbThreadPersistence.findByCategoryId(
			categoryId);

		for (MBThread thread : threads) {
			deleteThread(thread);
		}
	}

	public int getCategoriesThreadsCount(List<Long> categoryIds)
		throws SystemException {

		return mbThreadFinder.countByCategoryIds(categoryIds);
	}

	public List<MBThread> getGroupThreads(long groupId, int begin, int end)
		throws SystemException {

		return mbThreadFinder.findByGroupId(groupId, begin, end);
	}

	public List<MBThread> getGroupThreads(
			long groupId, long userId, int begin, int end)
		throws SystemException {

		return getGroupThreads(groupId, userId, false, begin, end);
	}

	public List<MBThread> getGroupThreads(
			long groupId, long userId, boolean subscribed, int begin, int end)
		throws SystemException {

		if (userId <= 0) {
			return mbThreadFinder.findByGroupId(groupId, begin, end);
		}
		else {
			if (subscribed) {
				return mbThreadFinder.findByS_G_U(groupId, userId, begin, end);
			}
			else {
				return mbThreadFinder.findByG_U(groupId, userId, begin, end);
			}
		}
	}

	public int getGroupThreadsCount(long groupId) throws SystemException {
		return mbThreadFinder.countByGroupId(groupId);
	}

	public int getGroupThreadsCount(long groupId, long userId)
		throws SystemException {

		return getGroupThreadsCount(groupId, userId, false);
	}

	public int getGroupThreadsCount(
			long groupId, long userId, boolean subscribed)
		throws SystemException {

		if (userId <= 0) {
			return mbThreadFinder.countByGroupId(groupId);
		}
		else {
			if (subscribed) {
				return mbThreadFinder.countByS_G_U(groupId, userId);
			}
			else {
				return mbThreadFinder.countByG_U(groupId, userId);
			}
		}
	}

	public MBThread getThread(long threadId)
		throws PortalException, SystemException {

		return mbThreadPersistence.findByPrimaryKey(threadId);
	}

	public List<MBThread> getThreads(long categoryId, int begin, int end)
		throws SystemException {

		return mbThreadPersistence.findByCategoryId(categoryId, begin, end);
	}

	public int getThreadsCount(long categoryId) throws SystemException {
		return mbThreadPersistence.countByCategoryId(categoryId);
	}

	public boolean hasReadThread(long userId, long threadId)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);

		if (user.isDefaultUser()) {
			return true;
		}

		int total = mbMessagePersistence.countByThreadId(threadId);
		int read = mbMessageFlagFinder.countByU_T(userId, threadId);

		if (total != read) {
			return false;
		}
		else {
			return true;
		}
	}

	public MBThread moveThread(long categoryId, long threadId)
		throws PortalException, SystemException {

		MBThread thread = mbThreadPersistence.findByPrimaryKey(
			threadId);

		long oldCategoryId = thread.getCategoryId();

		MBCategory category = mbCategoryPersistence.findByPrimaryKey(
			categoryId);

		// Messages

		List<MBMessage> messages = mbMessagePersistence.findByC_T(
			oldCategoryId, thread.getThreadId());

		for (MBMessage message : messages) {
			message.setCategoryId(category.getCategoryId());

			mbMessagePersistence.update(message, false);

			// Lucene

			try {
				if (!category.isDiscussion()) {
					Indexer.updateMessage(
						message.getCompanyId(), category.getGroupId(),
						message.getUserName(), category.getCategoryId(),
						message.getThreadId(), message.getMessageId(),
						message.getSubject(), message.getBody(),
						message.getTagsEntries());
				}
			}
			catch (IOException ioe) {
				_log.error("Indexing " + message.getMessageId(), ioe);
			}
		}

		// Thread

		thread.setCategoryId(category.getCategoryId());

		mbThreadPersistence.update(thread, false);

		return thread;
	}

	public MBThread splitThread(
			long messageId, PortletPreferences prefs, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		MBMessage message = mbMessagePersistence.findByPrimaryKey(messageId);

		MBCategory category = message.getCategory();
		long oldThreadId = message.getThreadId();
		String oldAttachmentsDir = message.getAttachmentsDir();

		// Create new thread

		MBThread thread = addThread(message.getCategoryId(), message);

		// Update message

		message.setThreadId(thread.getThreadId());
		message.setParentMessageId(0);
		message.setAttachmentsDir(null);

		mbMessagePersistence.update(message, false);

		// Attachments

		moveAttachmentsFromOldThread(message, oldAttachmentsDir);

		// Lucene

		try {
			if (!category.isDiscussion()) {
				Indexer.updateMessage(
					message.getCompanyId(), category.getGroupId(),
					message.getUserName(), category.getCategoryId(),
					message.getThreadId(), message.getMessageId(),
					message.getSubject(), message.getBody(),
					message.getTagsEntries());
			}
		}
		catch (IOException ioe) {
			_log.error("Indexing " + message.getMessageId(), ioe);
		}

		// Update children

		int messagesMoved = 1;

		messagesMoved += moveChildrenMessages(
			message, category, oldThreadId);

		// Update new thread

		thread.setMessageCount(messagesMoved);

		mbThreadPersistence.update(thread, false);

		// Update old thread

		MBThread oldThread = mbThreadPersistence.findByPrimaryKey(oldThreadId);

		oldThread.setMessageCount(oldThread.getMessageCount() - messagesMoved);

		mbThreadPersistence.update(oldThread, false);

		return thread;
	}

	public MBThread updateThread(long threadId, int viewCount)
		throws PortalException, SystemException {

		MBThread thread = mbThreadPersistence.findByPrimaryKey(threadId);

		thread.setViewCount(viewCount);

		mbThreadPersistence.update(thread, false);

		return thread;
	}

	protected MBThread addThread(long categoryId, MBMessage message)
		throws SystemException, PortalException {

		long threadId = counterLocalService.increment();

		MBThread thread = mbThreadPersistence.create(threadId);

		thread.setCategoryId(categoryId);
		thread.setRootMessageId(message.getMessageId());

		thread.setMessageCount(thread.getMessageCount() + 1);

		if (message.isAnonymous()) {
			thread.setLastPostByUserId(0);
		}
		else {
			thread.setLastPostByUserId(message.getUserId());
		}

		thread.setLastPostDate(message.getCreateDate());

		if (message.getPriority() != MBThreadImpl.PRIORITY_NOT_GIVEN) {
			thread.setPriority(message.getPriority());
		}

		mbThreadPersistence.update(thread, false);

		return thread;
	}

	protected void moveAttachmentsFromOldThread(
			MBMessage message, String oldAttachmentsDir)
		throws PortalException, SystemException {

		long companyId = message.getCompanyId();
		String portletId = CompanyConstants.SYSTEM_STRING;
		long groupId = GroupImpl.DEFAULT_PARENT_GROUP_ID;
		long repositoryId = CompanyConstants.SYSTEM;
		String newAttachmentsDir = message.getAttachmentsDir();

		try {
			try {
				dlService.addDirectory(
					companyId, repositoryId, newAttachmentsDir);
			}
			catch (DuplicateDirectoryException dde) {
			}

			String[] fileNames = dlService.getFileNames(
				companyId, repositoryId, oldAttachmentsDir);

			for (String fileName : fileNames) {
				String name = StringUtil.extractLast(
					fileName, StringPool.SLASH);
				byte[] fileBytes = dlService.getFile(
					companyId, repositoryId, fileName);

				dlService.addFile(
					companyId, portletId, groupId, repositoryId,
					newAttachmentsDir + "/" + name, StringPool.BLANK,
					new String[0], fileBytes);

				dlService.deleteFile(
					companyId, portletId, repositoryId, fileName);
			}

			try {
				dlService.deleteDirectory(
					companyId, portletId, repositoryId, oldAttachmentsDir);
			}
			catch (NoSuchDirectoryException nsde) {
			}
		}
		catch (RemoteException re) {
			throw new SystemException(re);
		}
	}

	protected int moveChildrenMessages(
			MBMessage parentMessage, MBCategory category, long oldThreadId)
		throws SystemException, PortalException {

		int messagesMoved = 0;

		List<MBMessage> messages = mbMessagePersistence.findByT_P(
			oldThreadId, parentMessage.getMessageId());

		for (MBMessage message : messages) {
			String oldAttachmentsDir = message.getAttachmentsDir();

			message.setCategoryId(parentMessage.getCategoryId());
			message.setThreadId(parentMessage.getThreadId());
			message.setAttachmentsDir(null);

			mbMessagePersistence.update(message, false);

			moveAttachmentsFromOldThread(message, oldAttachmentsDir);

			try {
				if (!category.isDiscussion()) {
					Indexer.updateMessage(
						message.getCompanyId(), category.getGroupId(),
						message.getUserName(), category.getCategoryId(),
						message.getThreadId(), message.getMessageId(),
						message.getSubject(), message.getBody(),
						message.getTagsEntries());
				}
			}
			catch (IOException ioe) {
				_log.error("Indexing " + message.getMessageId(), ioe);
			}

			messagesMoved++;

			messagesMoved += moveChildrenMessages(
				message, category, oldThreadId);
		}

		return messagesMoved;
	}

	private static Log _log = LogFactory.getLog(MBThreadLocalServiceImpl.class);

}