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
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.impl.PrincipalBean;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBMessageDisplay;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.impl.MBThreadImpl;
import com.liferay.portlet.messageboards.service.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageService;
import com.liferay.portlet.messageboards.service.MBThreadLocalServiceUtil;
import com.liferay.portlet.messageboards.service.permission.MBCategoryPermission;
import com.liferay.portlet.messageboards.service.permission.MBDiscussionPermission;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;

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
			long groupId, String className, String classPK, String threadId,
			String parentMessageId, String subject, String body)
		throws PortalException, SystemException {

		MBDiscussionPermission.check(
			getPermissionChecker(), groupId, className, classPK,
			ActionKeys.ADD_DISCUSSION);

		return MBMessageLocalServiceUtil.addDiscussionMessage(
			getUserId(), threadId, parentMessageId, subject, body);
	}

	public MBMessage addMessage(
			String categoryId, String subject, String body, List files,
			boolean anonymous, double priority, boolean addCommunityPermissions,
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
			getUserId(), categoryId, subject, body, files, anonymous, priority,
			null, addCommunityPermissions, addGuestPermissions);
	}

	public MBMessage addMessage(
			String categoryId, String subject, String body, List files,
			boolean anonymous, double priority, PortletPreferences prefs,
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
			getUserId(), categoryId, subject, body, files, anonymous, priority,
			prefs, addCommunityPermissions, addGuestPermissions);
	}

	public MBMessage addMessage(
			String categoryId, String threadId, String parentMessageId,
			String subject, String body, List files, boolean anonymous,
			double priority, boolean addCommunityPermissions,
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
			getUserId(), categoryId, threadId, parentMessageId, subject, body,
			files, anonymous, priority, null, addCommunityPermissions,
			addGuestPermissions);
	}

	public MBMessage addMessage(
			String categoryId, String threadId, String parentMessageId,
			String subject, String body, List files, boolean anonymous,
			double priority, PortletPreferences prefs,
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
			getUserId(), categoryId, threadId, parentMessageId, subject, body,
			files, anonymous, priority, prefs, addCommunityPermissions,
			addGuestPermissions);
	}

	public MBMessage addMessage(
			String categoryId, String subject, String body, List files,
			boolean anonymous, double priority, String[] communityPermissions,
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
			getUserId(), categoryId, subject, body, files, anonymous, priority,
			null, communityPermissions, guestPermissions);
	}

	public MBMessage addMessage(
			String categoryId, String subject, String body, List files,
			boolean anonymous, double priority, PortletPreferences prefs,
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
			getUserId(), categoryId, subject, body, files, anonymous, priority,
			prefs, communityPermissions, guestPermissions);
	}

	public MBMessage addMessage(
			String categoryId, String threadId, String parentMessageId,
			String subject, String body, List files, boolean anonymous,
			double priority, String[] communityPermissions,
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
			getUserId(), categoryId, threadId, parentMessageId, subject, body,
			files, anonymous, priority, null, communityPermissions,
			guestPermissions);
	}

	public MBMessage addMessage(
			String categoryId, String threadId, String parentMessageId,
			String subject, String body, List files, boolean anonymous,
			double priority, PortletPreferences prefs,
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
			getUserId(), categoryId, threadId, parentMessageId, subject, body,
			files, anonymous, priority, prefs, communityPermissions,
			guestPermissions);
	}

	public void deleteDiscussionMessage(
			long groupId, String className, String classPK, String messageId)
		throws PortalException, SystemException {

		MBDiscussionPermission.check(
			getPermissionChecker(), groupId, className, classPK,
			ActionKeys.DELETE_DISCUSSION);

		MBMessageLocalServiceUtil.deleteDiscussionMessage(messageId);
	}

	public void deleteMessage(String messageId)
		throws PortalException, SystemException {

		MBMessagePermission.check(
			getPermissionChecker(), messageId, ActionKeys.DELETE);

		MBMessageLocalServiceUtil.deleteMessage(messageId);
	}

	public MBMessage getMessage(String messageId)
		throws PortalException, SystemException {

		MBMessagePermission.check(
			getPermissionChecker(), messageId, ActionKeys.VIEW);

		return MBMessageLocalServiceUtil.getMessage(messageId);
	}

	public MBMessageDisplay getMessageDisplay(String messageId, String userId)
		throws PortalException, SystemException {

		MBMessagePermission.check(
			getPermissionChecker(), messageId, ActionKeys.VIEW);

		return MBMessageLocalServiceUtil.getMessageDisplay(messageId, userId);
	}

	public void subscribeMessage(String messageId)
		throws PortalException, SystemException {

		MBMessagePermission.check(
			getPermissionChecker(), messageId, ActionKeys.SUBSCRIBE);

		MBMessageLocalServiceUtil.subscribeMessage(getUserId(), messageId);
	}

	public void unsubscribeMessage(String messageId)
		throws PortalException, SystemException {

		MBMessagePermission.check(
			getPermissionChecker(), messageId, ActionKeys.SUBSCRIBE);

		MBMessageLocalServiceUtil.unsubscribeMessage(getUserId(), messageId);
	}

	public MBMessage updateDiscussionMessage(
			long groupId, String className, String classPK, String messageId,
			String subject, String body)
		throws PortalException, SystemException {

		MBDiscussionPermission.check(
			getPermissionChecker(), groupId, className, classPK,
			ActionKeys.UPDATE_DISCUSSION);

		return MBMessageLocalServiceUtil.updateDiscussionMessage(
			messageId, subject, body);
	}

	public MBMessage updateMessage(
			String messageId, String categoryId, String subject, String body,
			List files, double priority)
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
			messageId, categoryId, subject, body, files, priority, null);
	}

	public MBMessage updateMessage(
			String messageId, String categoryId, String subject, String body,
			List files, double priority, PortletPreferences prefs)
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
			messageId, categoryId, subject, body, files, priority, prefs);
	}

}