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

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.impl.PrincipalBean;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.permission.MBCategoryPermission;
import com.liferay.portlet.messageboards.service.permission.MBDiscussionPermission;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;
import com.liferay.portlet.messageboards.service.spring.MBMessageLocalServiceUtil;
import com.liferay.portlet.messageboards.service.spring.MBMessageService;

import java.util.List;

import javax.portlet.PortletPreferences;

/**
 * <a href="MBMessageServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBMessageServiceImpl
	extends PrincipalBean implements MBMessageService {

	public MBMessage addDiscussionMessage(
			String groupId, String className, String classPK, String threadId,
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
			boolean anonymous, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		MBCategoryPermission.check(
			getPermissionChecker(), categoryId, ActionKeys.ADD_MESSAGE);

		return MBMessageLocalServiceUtil.addMessage(
			getUserId(), categoryId, subject, body, files, anonymous, null,
			addCommunityPermissions, addGuestPermissions);
	}

	public MBMessage addMessage(
			String categoryId, String subject, String body, List files,
			boolean anonymous, PortletPreferences prefs,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		MBCategoryPermission.check(
			getPermissionChecker(), categoryId, ActionKeys.ADD_MESSAGE);

		return MBMessageLocalServiceUtil.addMessage(
			getUserId(), categoryId, subject, body, files, anonymous, prefs,
			addCommunityPermissions, addGuestPermissions);
	}

	public MBMessage addMessage(
			String categoryId, String threadId, String parentMessageId,
			String subject, String body, List files, boolean anonymous,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		MBCategoryPermission.check(
			getPermissionChecker(), categoryId, ActionKeys.ADD_MESSAGE);

		return MBMessageLocalServiceUtil.addMessage(
			getUserId(), categoryId, threadId, parentMessageId, subject, body,
			files, anonymous, null, addCommunityPermissions,
			addGuestPermissions);
	}

	public MBMessage addMessage(
			String categoryId, String threadId, String parentMessageId,
			String subject, String body, List files, boolean anonymous,
			PortletPreferences prefs, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		MBCategoryPermission.check(
			getPermissionChecker(), categoryId, ActionKeys.ADD_MESSAGE);

		return MBMessageLocalServiceUtil.addMessage(
			getUserId(), categoryId, threadId, parentMessageId, subject, body,
			files, anonymous, prefs, addCommunityPermissions,
			addGuestPermissions);
	}

	public void deleteDiscussionMessage(
			String groupId, String className, String classPK, String topicId,
			String messageId)
		throws PortalException, SystemException {

		MBDiscussionPermission.check(
			getPermissionChecker(), groupId, className, classPK,
			ActionKeys.DELETE_DISCUSSION);

		MBMessageLocalServiceUtil.deleteDiscussionMessage(topicId, messageId);
	}

	public void deleteMessage(String topicId, String messageId)
		throws PortalException, SystemException {

		MBMessagePermission.check(
			getPermissionChecker(), topicId, messageId, ActionKeys.DELETE);

		MBMessageLocalServiceUtil.deleteMessage(topicId, messageId);
	}

	public MBMessage getMessage(String topicId, String messageId)
		throws PortalException, SystemException {

		MBMessagePermission.check(
			getPermissionChecker(), topicId, messageId, ActionKeys.VIEW);

		return MBMessageLocalServiceUtil.getMessage(topicId, messageId);
	}

	public void subscribeMessage(String topicId, String messageId)
		throws PortalException, SystemException {

		MBMessagePermission.check(
			getPermissionChecker(), topicId, messageId, ActionKeys.SUBSCRIBE);

		MBMessageLocalServiceUtil.subscribeMessage(
			getUserId(), topicId, messageId);
	}

	public void unsubscribeMessage(String topicId, String messageId)
		throws PortalException, SystemException {

		MBMessagePermission.check(
			getPermissionChecker(), topicId, messageId, ActionKeys.SUBSCRIBE);

		MBMessageLocalServiceUtil.unsubscribeMessage(
			getUserId(), topicId, messageId);
	}

	public MBMessage updateDiscussionMessage(
			String groupId, String className, String classPK, String topicId,
			String messageId, String subject, String body)
		throws PortalException, SystemException {

		MBDiscussionPermission.check(
			getPermissionChecker(), groupId, className, classPK,
			ActionKeys.UPDATE_DISCUSSION);

		return MBMessageLocalServiceUtil.updateDiscussionMessage(
			topicId, messageId, subject, body);
	}

	public MBMessage updateMessage(
			String topicId, String messageId, String categoryId, String subject,
			String body, List files)
		throws PortalException, SystemException {

		MBMessagePermission.check(
			getPermissionChecker(), topicId, messageId, ActionKeys.UPDATE);

		return MBMessageLocalServiceUtil.updateMessage(
			topicId, messageId, categoryId, subject, body, files, null);
	}

	public MBMessage updateMessage(
			String topicId, String messageId, String categoryId, String subject,
			String body, List files, PortletPreferences prefs)
		throws PortalException, SystemException {

		MBMessagePermission.check(
			getPermissionChecker(), topicId, messageId, ActionKeys.UPDATE);

		return MBMessageLocalServiceUtil.updateMessage(
			topicId, messageId, categoryId, subject, body, files, prefs);
	}

}