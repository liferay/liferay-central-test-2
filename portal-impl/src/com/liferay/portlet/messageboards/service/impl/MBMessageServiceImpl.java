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

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.security.permission.ActionKeys;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Company;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.impl.MBThreadImpl;
import com.liferay.portlet.messageboards.service.base.MBMessageServiceBaseImpl;
import com.liferay.portlet.messageboards.service.permission.MBCategoryPermission;
import com.liferay.portlet.messageboards.service.permission.MBDiscussionPermission;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;
import com.liferay.portlet.messageboards.util.MBUtil;
import com.liferay.portlet.messageboards.util.comparator.MessageCreateDateComparator;
import com.liferay.util.RSSUtil;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndContentImpl;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import com.sun.syndication.io.FeedException;

import java.io.IOException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * <a href="MBMessageServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class MBMessageServiceImpl extends MBMessageServiceBaseImpl {

	public MBMessage addDiscussionMessage(
			long groupId, String className, long classPK, long threadId,
			long parentMessageId, String subject, String body,
			ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		MBDiscussionPermission.check(
			getPermissionChecker(), groupId, className, classPK,
			ActionKeys.ADD_DISCUSSION);

		return mbMessageLocalService.addDiscussionMessage(
			getUserId(), groupId, className, classPK, threadId, parentMessageId,
			subject, body, themeDisplay);
	}

	public MBMessage addMessage(
			long categoryId, String subject, String body, List files,
			boolean anonymous, double priority, String[] tagsEntries,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		MBCategoryPermission.check(
			getPermissionChecker(), categoryId, ActionKeys.ADD_MESSAGE);

		if (!MBCategoryPermission.contains(
				getPermissionChecker(), categoryId, ActionKeys.ADD_FILE)) {

			files.clear();
		}

		if (!MBCategoryPermission.contains(
				getPermissionChecker(), categoryId,
				ActionKeys.UPDATE_THREAD_PRIORITY)) {

			priority = MBThreadImpl.PRIORITY_NOT_GIVEN;
		}

		return mbMessageLocalService.addMessage(
			getGuestOrUserId(), categoryId, subject, body, files, anonymous,
			priority, tagsEntries, null, addCommunityPermissions,
			addGuestPermissions);
	}

	public MBMessage addMessage(
			long categoryId, String subject, String body, List files,
			boolean anonymous, double priority, String[] tagsEntries,
			PortletPreferences prefs, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		MBCategoryPermission.check(
			getPermissionChecker(), categoryId, ActionKeys.ADD_MESSAGE);

		if (!MBCategoryPermission.contains(
				getPermissionChecker(), categoryId, ActionKeys.ADD_FILE)) {

			files.clear();
		}

		if (!MBCategoryPermission.contains(
				getPermissionChecker(), categoryId,
				ActionKeys.UPDATE_THREAD_PRIORITY)) {

			priority = MBThreadImpl.PRIORITY_NOT_GIVEN;
		}

		return mbMessageLocalService.addMessage(
			getGuestOrUserId(), categoryId, subject, body, files, anonymous,
			priority, tagsEntries, prefs, addCommunityPermissions,
			addGuestPermissions);
	}

	public MBMessage addMessage(
			long categoryId, long threadId, long parentMessageId,
			String subject, String body, List files, boolean anonymous,
			double priority, String[] tagsEntries,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		MBCategoryPermission.check(
			getPermissionChecker(), categoryId, ActionKeys.ADD_MESSAGE);

		if (!MBCategoryPermission.contains(
				getPermissionChecker(), categoryId, ActionKeys.ADD_FILE)) {

			files.clear();
		}

		if (!MBCategoryPermission.contains(
				getPermissionChecker(), categoryId,
				ActionKeys.UPDATE_THREAD_PRIORITY)) {

			priority = MBThreadImpl.PRIORITY_NOT_GIVEN;
		}

		return mbMessageLocalService.addMessage(
			getGuestOrUserId(), categoryId, threadId, parentMessageId, subject,
			body, files, anonymous, priority, tagsEntries, null,
			addCommunityPermissions, addGuestPermissions);
	}

	public MBMessage addMessage(
			long categoryId, long threadId, long parentMessageId,
			String subject, String body, List files, boolean anonymous,
			double priority, String[] tagsEntries, PortletPreferences prefs,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		MBCategoryPermission.check(
			getPermissionChecker(), categoryId, ActionKeys.ADD_MESSAGE);

		if (!MBCategoryPermission.contains(
				getPermissionChecker(), categoryId, ActionKeys.ADD_FILE)) {

			files.clear();
		}

		if (!MBCategoryPermission.contains(
				getPermissionChecker(), categoryId,
				ActionKeys.UPDATE_THREAD_PRIORITY)) {

			priority = MBThreadImpl.PRIORITY_NOT_GIVEN;
		}

		return mbMessageLocalService.addMessage(
			getGuestOrUserId(), categoryId, threadId, parentMessageId, subject,
			body, files, anonymous, priority, tagsEntries, prefs,
			addCommunityPermissions, addGuestPermissions);
	}

	public MBMessage addMessage(
			long categoryId, String subject, String body, List files,
			boolean anonymous, double priority, String[] tagsEntries,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		MBCategoryPermission.check(
			getPermissionChecker(), categoryId, ActionKeys.ADD_MESSAGE);

		if (!MBCategoryPermission.contains(
				getPermissionChecker(), categoryId, ActionKeys.ADD_FILE)) {

			files.clear();
		}

		if (!MBCategoryPermission.contains(
				getPermissionChecker(), categoryId,
				ActionKeys.UPDATE_THREAD_PRIORITY)) {

			priority = MBThreadImpl.PRIORITY_NOT_GIVEN;
		}

		return mbMessageLocalService.addMessage(
			getGuestOrUserId(), categoryId, subject, body, files, anonymous,
			priority, tagsEntries, null, communityPermissions,
			guestPermissions);
	}

	public MBMessage addMessage(
			long categoryId, String subject, String body, List files,
			boolean anonymous, double priority, String[] tagsEntries,
			PortletPreferences prefs, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		MBCategoryPermission.check(
			getPermissionChecker(), categoryId, ActionKeys.ADD_MESSAGE);

		if (!MBCategoryPermission.contains(
				getPermissionChecker(), categoryId, ActionKeys.ADD_FILE)) {

			files.clear();
		}

		if (!MBCategoryPermission.contains(
				getPermissionChecker(), categoryId,
				ActionKeys.UPDATE_THREAD_PRIORITY)) {

			priority = MBThreadImpl.PRIORITY_NOT_GIVEN;
		}

		return mbMessageLocalService.addMessage(
			getGuestOrUserId(), categoryId, subject, body, files, anonymous,
			priority, tagsEntries, prefs, communityPermissions,
			guestPermissions);
	}

	public MBMessage addMessage(
			long categoryId, long threadId, long parentMessageId,
			String subject, String body, List files, boolean anonymous,
			double priority, String[] tagsEntries,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		MBCategoryPermission.check(
			getPermissionChecker(), categoryId, ActionKeys.ADD_MESSAGE);

		if (!MBCategoryPermission.contains(
				getPermissionChecker(), categoryId, ActionKeys.ADD_FILE)) {

			files.clear();
		}

		if (!MBCategoryPermission.contains(
				getPermissionChecker(), categoryId,
				ActionKeys.UPDATE_THREAD_PRIORITY)) {

			priority = MBThreadImpl.PRIORITY_NOT_GIVEN;
		}

		return mbMessageLocalService.addMessage(
			getGuestOrUserId(), categoryId, threadId, parentMessageId, subject,
			body, files, anonymous, priority, tagsEntries, null,
			communityPermissions, guestPermissions);
	}

	public MBMessage addMessage(
			long categoryId, long threadId, long parentMessageId,
			String subject, String body, List files, boolean anonymous,
			double priority, String[] tagsEntries, PortletPreferences prefs,
			String[] communityPermissions, String[] guestPermissions)
		throws PortalException, SystemException {

		MBCategoryPermission.check(
			getPermissionChecker(), categoryId, ActionKeys.ADD_MESSAGE);

		if (!MBCategoryPermission.contains(
				getPermissionChecker(), categoryId, ActionKeys.ADD_FILE)) {

			files.clear();
		}

		if (!MBCategoryPermission.contains(
				getPermissionChecker(), categoryId,
				ActionKeys.UPDATE_THREAD_PRIORITY)) {

			priority = MBThreadImpl.PRIORITY_NOT_GIVEN;
		}

		return mbMessageLocalService.addMessage(
			getGuestOrUserId(), categoryId, threadId, parentMessageId, subject,
			body, files, anonymous, priority, tagsEntries, prefs,
			communityPermissions, guestPermissions);
	}

	public void deleteDiscussionMessage(
			long groupId, String className, long classPK, long messageId)
		throws PortalException, SystemException {

		MBDiscussionPermission.check(
			getPermissionChecker(), groupId, className, classPK,
			ActionKeys.DELETE_DISCUSSION);

		mbMessageLocalService.deleteDiscussionMessage(messageId);
	}

	public void deleteMessage(long messageId)
		throws PortalException, SystemException {

		MBMessagePermission.check(
			getPermissionChecker(), messageId, ActionKeys.DELETE);

		mbMessageLocalService.deleteMessage(messageId);
	}

	public List getCategoryMessages(long categoryId, int begin, int end)
		throws PortalException, SystemException {

		List messages = new ArrayList();

		Iterator itr = mbMessageLocalService.getCategoryMessages(
			categoryId, begin, end).iterator();

		while (itr.hasNext()) {
			MBMessage message = (MBMessage)itr.next();

			if (MBMessagePermission.contains(
					getPermissionChecker(), message, ActionKeys.VIEW)) {

				messages.add(message);
			}
		}

		return messages;
	}

	public int getCategoryMessagesCount(long categoryId)
		throws PortalException, SystemException {

		return mbMessageLocalService.getCategoryMessagesCount(categoryId);
	}

	public String getCategoryMessagesRSS(
			long categoryId, int max, String type, double version,
			String feedURL, String entryURL, PortletPreferences prefs)
		throws PortalException, SystemException {

		MBCategory category = mbCategoryLocalService.getCategory(
			categoryId);

		String name = category.getName();
		String description = category.getDescription();

		List messages = new ArrayList();

		MessageCreateDateComparator comparator =
			new MessageCreateDateComparator(false, false);

		Iterator itr = mbMessageLocalService.getCategoryMessages(
			categoryId, 0, _MAX_END, comparator).iterator();

		while (itr.hasNext() && (messages.size() < max)) {
			MBMessage message = (MBMessage)itr.next();

			if (MBMessagePermission.contains(
					getPermissionChecker(), message, ActionKeys.VIEW)) {

				messages.add(message);
			}
		}

		return exportToRSS(
			name, description, type, version, feedURL, entryURL, messages,
			prefs);
	}

	public String getCompanyMessagesRSS(
			long companyId, int max, String type, double version,
			String feedURL, String entryURL, PortletPreferences prefs)
		throws PortalException, SystemException {

		Company company = companyPersistence.findByPrimaryKey(companyId);

		String name = company.getName();
		String description = company.getName();

		List messages = new ArrayList();

		MessageCreateDateComparator comparator =
			new MessageCreateDateComparator(false, false);

		Iterator itr = mbMessageLocalService.getCompanyMessages(
			companyId, 0, _MAX_END, comparator).iterator();

		while (itr.hasNext() && (messages.size() < max)) {
			MBMessage message = (MBMessage)itr.next();

			if (MBMessagePermission.contains(
					getPermissionChecker(), message, ActionKeys.VIEW)) {

				messages.add(message);
			}
		}

		return exportToRSS(
			name, description, type, version, feedURL, entryURL, messages,
			prefs);
	}

	public String getGroupMessagesRSS(
			long groupId, int max, String type, double version,
			String feedURL, String entryURL, PortletPreferences prefs)
		throws PortalException, SystemException {

		String name = StringPool.BLANK;
		String description = StringPool.BLANK;

		List messages = new ArrayList();

		MessageCreateDateComparator comparator =
			new MessageCreateDateComparator(false, true);

		Iterator itr = mbMessageLocalService.getGroupMessages(
			groupId, 0, _MAX_END, comparator).iterator();

		while (itr.hasNext() && (messages.size() < max)) {
			MBMessage message = (MBMessage)itr.next();

			if (MBMessagePermission.contains(
					getPermissionChecker(), message, ActionKeys.VIEW)) {

				messages.add(message);
			}
		}

		if (messages.size() > 0) {
			MBMessage message = (MBMessage)messages.get(messages.size() - 1);

			name = message.getSubject();
			description = message.getSubject();
		}

		return exportToRSS(
			name, description, type, version, feedURL, entryURL, messages,
			prefs);
	}

	public MBMessage getMessage(long messageId)
		throws PortalException, SystemException {

		MBMessagePermission.check(
			getPermissionChecker(), messageId, ActionKeys.VIEW);

		return mbMessageLocalService.getMessage(messageId);
	}

	public MBMessageDisplay getMessageDisplay(long messageId)
		throws PortalException, SystemException {

		MBMessagePermission.check(
			getPermissionChecker(), messageId, ActionKeys.VIEW);

		return mbMessageLocalService.getMessageDisplay(messageId);
	}

	public String getThreadMessagesRSS(
			long threadId, int max, String type, double version,
			String feedURL, String entryURL, PortletPreferences prefs)
		throws PortalException, SystemException {

		String name = StringPool.BLANK;
		String description = StringPool.BLANK;

		List messages = new ArrayList();

		MessageCreateDateComparator comparator =
			new MessageCreateDateComparator(false, true);

		Iterator itr = mbMessageLocalService.getThreadMessages(
			threadId, comparator).iterator();

		while (itr.hasNext() && (messages.size() < max)) {
			MBMessage message = (MBMessage)itr.next();

			if (MBMessagePermission.contains(
					getPermissionChecker(), message, ActionKeys.VIEW)) {

				messages.add(message);
			}
		}

		if (messages.size() > 0) {
			MBMessage message = (MBMessage)messages.get(messages.size() - 1);

			name = message.getSubject();
			description = message.getSubject();
		}

		return exportToRSS(
			name, description, type, version, feedURL, entryURL, messages,
			prefs);
	}

	public void subscribeMessage(long messageId)
		throws PortalException, SystemException {

		MBMessagePermission.check(
			getPermissionChecker(), messageId, ActionKeys.SUBSCRIBE);

		mbMessageLocalService.subscribeMessage(getUserId(), messageId);
	}

	public void unsubscribeMessage(long messageId)
		throws PortalException, SystemException {

		MBMessagePermission.check(
			getPermissionChecker(), messageId, ActionKeys.SUBSCRIBE);

		mbMessageLocalService.unsubscribeMessage(getUserId(), messageId);
	}

	public MBMessage updateDiscussionMessage(
			long groupId, String className, long classPK, long messageId,
			String subject, String body)
		throws PortalException, SystemException {

		MBDiscussionPermission.check(
			getPermissionChecker(), groupId, className, classPK,
			ActionKeys.UPDATE_DISCUSSION);

		return mbMessageLocalService.updateDiscussionMessage(
			getUserId(), messageId, subject, body);
	}

	public MBMessage updateMessage(
			long messageId, long categoryId, String subject, String body,
			List files, double priority, String[] tagsEntries)
		throws PortalException, SystemException {

		MBMessagePermission.check(
			getPermissionChecker(), messageId, ActionKeys.UPDATE);

		if (!MBCategoryPermission.contains(
				getPermissionChecker(), categoryId, ActionKeys.ADD_FILE)) {

			files.clear();
		}

		if (!MBCategoryPermission.contains(
				getPermissionChecker(), categoryId,
				ActionKeys.UPDATE_THREAD_PRIORITY)) {

			MBMessage message = mbMessageLocalService.getMessage(messageId);

			MBThread thread = mbThreadLocalService.getThread(
				message.getThreadId());

			priority = thread.getPriority();
		}

		return mbMessageLocalService.updateMessage(
			getUserId(), messageId, categoryId, subject, body, files, priority,
			tagsEntries, null);
	}

	public MBMessage updateMessage(
			long messageId, long categoryId, String subject, String body,
			List files, double priority, String[] tagsEntries,
			PortletPreferences prefs)
		throws PortalException, SystemException {

		MBMessagePermission.check(
			getPermissionChecker(), messageId, ActionKeys.UPDATE);

		if (!MBCategoryPermission.contains(
				getPermissionChecker(), categoryId, ActionKeys.ADD_FILE)) {

			files.clear();
		}

		if (!MBCategoryPermission.contains(
				getPermissionChecker(), categoryId,
				ActionKeys.UPDATE_THREAD_PRIORITY)) {

			MBMessage message = mbMessageLocalService.getMessage(messageId);

			MBThread thread = mbThreadLocalService.getThread(
				message.getThreadId());

			priority = thread.getPriority();
		}

		return mbMessageLocalService.updateMessage(
			getUserId(), messageId, categoryId, subject, body, files, priority,
			tagsEntries, prefs);
	}

	protected String exportToRSS(
			String name, String description, String type, double version,
			String feedURL, String entryURL, List messages,
			PortletPreferences prefs)
		throws SystemException {

		SyndFeed syndFeed = new SyndFeedImpl();

		syndFeed.setFeedType(type + "_" + version);

		syndFeed.setTitle(name);
		syndFeed.setLink(feedURL);
		syndFeed.setDescription(description);

		List entries = new ArrayList();

		syndFeed.setEntries(entries);

		int rssContentLength = MBUtil.getRSSContentLength(prefs);

		Iterator itr = messages.iterator();

		while (itr.hasNext()) {
			MBMessage message = (MBMessage)itr.next();

			SyndEntry syndEntry = new SyndEntryImpl();

			if (!message.isAnonymous()) {
				String userName = MBUtil.getUserName(
					message.getUserId(), message.getUserName(), prefs);

				syndEntry.setAuthor(userName);
			}

			syndEntry.setTitle(message.getSubject());
			syndEntry.setLink(
				entryURL + "&messageId=" + message.getMessageId());
			syndEntry.setPublishedDate(message.getCreateDate());

			SyndContent syndContent = new SyndContentImpl();

			syndContent.setType(ContentTypes.TEXT_HTML);
			syndContent.setValue(message.getBody());

			syndEntry.setDescription(syndContent);

			entries.add(syndEntry);
		}

		try {
			return RSSUtil.export(syndFeed);
		}
		catch (FeedException fe) {
			throw new SystemException(fe);
		}
		catch (IOException ioe) {
			throw new SystemException(ioe);
		}
	}

	private static final int _MAX_END = 200;

}