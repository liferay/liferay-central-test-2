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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.User;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.impl.PrincipalBean;
import com.liferay.portal.util.Constants;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.impl.MBThreadImpl;
import com.liferay.portlet.messageboards.service.MBCategoryLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageService;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.messageboards.service.permission.MBCategoryPermission;
import com.liferay.portlet.messageboards.service.permission.MBDiscussionPermission;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;
import com.liferay.portlet.messageboards.util.MBUtil;
import com.liferay.portlet.messageboards.util.comparator.MessageCreateDateComparator;
import com.liferay.util.Html;
import com.liferay.util.RSSUtil;
import com.liferay.util.StringUtil;

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
public class MBMessageServiceImpl
	extends PrincipalBean implements MBMessageService {

	public MBMessage addDiscussionMessage(
			long groupId, String className, long classPK, long threadId,
			long parentMessageId, String subject, String body)
		throws PortalException, SystemException {

		MBDiscussionPermission.check(
			getPermissionChecker(), groupId, className, classPK,
			ActionKeys.ADD_DISCUSSION);

		return MBMessageLocalServiceUtil.addDiscussionMessage(
			getUserId(), threadId, parentMessageId, subject, body);
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

		return MBMessageLocalServiceUtil.addMessage(
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

		return MBMessageLocalServiceUtil.addMessage(
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

		return MBMessageLocalServiceUtil.addMessage(
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

		return MBMessageLocalServiceUtil.addMessage(
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

		return MBMessageLocalServiceUtil.addMessage(
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

		return MBMessageLocalServiceUtil.addMessage(
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

		return MBMessageLocalServiceUtil.addMessage(
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

		return MBMessageLocalServiceUtil.addMessage(
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

		MBMessageLocalServiceUtil.deleteDiscussionMessage(messageId);
	}

	public void deleteMessage(long messageId)
		throws PortalException, SystemException {

		MBMessagePermission.check(
			getPermissionChecker(), messageId, ActionKeys.DELETE);

		MBMessageLocalServiceUtil.deleteMessage(messageId);
	}

	public String getCategoryMessagesRSS(
			long categoryId, int max, String type, double version,
			String feedURL, String entryURL, PortletPreferences prefs)
		throws PortalException, SystemException {

		MBCategory category = MBCategoryLocalServiceUtil.getCategory(
			categoryId);

		String name = category.getName();
		String description = category.getDescription();

		List messages = MBMessageLocalServiceUtil.getCategoryMessages(
			categoryId, 0, max, new MessageCreateDateComparator(false));

		Iterator itr = messages.iterator();

		while (itr.hasNext()) {
			MBMessage message = (MBMessage)itr.next();

			if (!MBMessagePermission.contains(
					getPermissionChecker(), message, ActionKeys.VIEW)) {

				itr.remove();
			}
		}

		return exportToRSS(
			name, description, type, version, feedURL, entryURL, messages, prefs);
	}

	public MBMessage getMessage(long messageId)
		throws PortalException, SystemException {

		MBMessagePermission.check(
			getPermissionChecker(), messageId, ActionKeys.VIEW);

		return MBMessageLocalServiceUtil.getMessage(messageId);
	}

	public MBMessageDisplay getMessageDisplay(long messageId)
		throws PortalException, SystemException {

		MBMessagePermission.check(
			getPermissionChecker(), messageId, ActionKeys.VIEW);

		return MBMessageLocalServiceUtil.getMessageDisplay(messageId);
	}

	public String getThreadMessagesRSS(
			long threadId, int max, String type, double version,
			String feedURL, String entryURL, PortletPreferences prefs)
		throws PortalException, SystemException {

		String name = StringPool.BLANK;
		String description = StringPool.BLANK;

		List messages = new ArrayList();

		Iterator itr = MBMessageLocalServiceUtil.getThreadMessages(
			threadId, new MessageCreateDateComparator(false)).iterator();

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
			name, description, type, version, feedURL, entryURL, messages, prefs);
	}

	public void subscribeMessage(long messageId)
		throws PortalException, SystemException {

		MBMessagePermission.check(
			getPermissionChecker(), messageId, ActionKeys.SUBSCRIBE);

		MBMessageLocalServiceUtil.subscribeMessage(getUserId(), messageId);
	}

	public void unsubscribeMessage(long messageId)
		throws PortalException, SystemException {

		MBMessagePermission.check(
			getPermissionChecker(), messageId, ActionKeys.SUBSCRIBE);

		MBMessageLocalServiceUtil.unsubscribeMessage(getUserId(), messageId);
	}

	public MBMessage updateDiscussionMessage(
			long groupId, String className, long classPK, long messageId,
			String subject, String body)
		throws PortalException, SystemException {

		MBDiscussionPermission.check(
			getPermissionChecker(), groupId, className, classPK,
			ActionKeys.UPDATE_DISCUSSION);

		return MBMessageLocalServiceUtil.updateDiscussionMessage(
			messageId, subject, body);
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

			MBMessage message = MBMessageLocalServiceUtil.getMessage(messageId);

			MBThread thread = MBThreadLocalServiceUtil.getThread(
				message.getThreadId());

			priority = thread.getPriority();
		}

		return MBMessageLocalServiceUtil.updateMessage(
			messageId, categoryId, subject, body, files, priority, tagsEntries,
			null);
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

			MBMessage message = MBMessageLocalServiceUtil.getMessage(messageId);

			MBThread thread = MBThreadLocalServiceUtil.getThread(
				message.getThreadId());

			priority = thread.getPriority();
		}

		return MBMessageLocalServiceUtil.updateMessage(
			messageId, categoryId, subject, body, files, priority, tagsEntries,
			prefs);
	}

	protected String exportToRSS(
			String name, String description, String type, double version,
			String feedURL, String entryURL, List messages, PortletPreferences prefs)
		throws SystemException {

		SyndFeed syndFeed = new SyndFeedImpl();

		syndFeed.setFeedType(type + "_" + version);

		syndFeed.setTitle(name);
		syndFeed.setLink(feedURL);
		syndFeed.setDescription(description);

		List entries = new ArrayList();

		syndFeed.setEntries(entries);

		Iterator itr = messages.iterator();

		while (itr.hasNext()) {
			MBMessage message = (MBMessage)itr.next();

			String firstLine = StringUtil.shorten(
				Html.stripHtml(message.getBody()), 80, StringPool.BLANK);

			SyndEntry syndEntry = new SyndEntryImpl();

			if (!message.isAnonymous()) {
				String userName = message.getUserName();

				try {
					User user = UserLocalServiceUtil.getUserById(
						message.getUserId());

					userName = (MBUtil.getShowFullName(prefs) ?  user.getFullName() : user.getScreenName());
				}
				catch (Exception e) {
				}

				syndEntry.setAuthor(userName);
			}

			syndEntry.setTitle(message.getSubject());
			syndEntry.setLink(
				entryURL + "&messageId=" + message.getMessageId());
			syndEntry.setPublishedDate(message.getCreateDate());

			SyndContent syndContent = new SyndContentImpl();

			syndContent.setType(Constants.TEXT_PLAIN);
			syndContent.setValue(firstLine);

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

}