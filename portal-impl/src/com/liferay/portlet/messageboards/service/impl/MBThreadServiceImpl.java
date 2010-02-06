/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.model.Lock;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.messageboards.LockedThreadException;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.impl.MBThreadModelImpl;
import com.liferay.portlet.messageboards.service.base.MBThreadServiceBaseImpl;
import com.liferay.portlet.messageboards.service.permission.MBCategoryPermission;
import com.liferay.portlet.messageboards.service.permission.MBMessagePermission;

import java.util.List;

/**
 * <a href="MBThreadServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Deepak Gothe
 * @author Mika Koivisto
 */
public class MBThreadServiceImpl extends MBThreadServiceBaseImpl {

	public void deleteThread(long threadId)
		throws PortalException, SystemException {

		if (lockLocalService.isLocked(
				MBThread.class.getName(), threadId)) {

			throw new LockedThreadException();
		}

		List<MBMessage> messages = mbMessagePersistence.findByThreadId(
			threadId);

		for (MBMessage message : messages) {
			MBMessagePermission.check(
				getPermissionChecker(), message.getMessageId(),
				ActionKeys.DELETE);
		}

		mbThreadLocalService.deleteThread(threadId);
	}

	public Lock lockThread(long threadId)
		throws PortalException, SystemException {

		MBThread thread = mbThreadLocalService.getThread(threadId);

		MBCategoryPermission.check(
			getPermissionChecker(), thread.getGroupId(), thread.getCategoryId(),
			ActionKeys.LOCK_THREAD);

		return lockLocalService.lock(
			getUserId(), MBThread.class.getName(), threadId,
			String.valueOf(threadId), false,
			MBThreadModelImpl.LOCK_EXPIRATION_TIME);
	}

	public MBThread moveThread(long categoryId, long threadId)
		throws PortalException, SystemException {

		MBThread thread = mbThreadLocalService.getThread(threadId);

		MBCategoryPermission.check(
			getPermissionChecker(), thread.getGroupId(), thread.getCategoryId(),
			ActionKeys.MOVE_THREAD);

		MBCategoryPermission.check(
			getPermissionChecker(), thread.getGroupId(), categoryId,
			ActionKeys.MOVE_THREAD);

		return mbThreadLocalService.moveThread(
			thread.getGroupId(), categoryId, threadId);
	}

	public MBThread splitThread(long messageId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		MBMessage message = mbMessageLocalService.getMessage(messageId);

		MBCategoryPermission.check(
			getPermissionChecker(), message.getGroupId(),
			message.getCategoryId(), ActionKeys.MOVE_THREAD);

		return mbThreadLocalService.splitThread(messageId, serviceContext);
	}

	public void unlockThread(long threadId)
		throws PortalException, SystemException {

		MBThread thread = mbThreadLocalService.getThread(threadId);

		MBCategoryPermission.check(
			getPermissionChecker(), thread.getGroupId(), thread.getCategoryId(),
			ActionKeys.LOCK_THREAD);

		lockLocalService.unlock(MBThread.class.getName(), threadId);
	}

}