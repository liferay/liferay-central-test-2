/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portlet.messageboards.model.MBDiscussion;
import com.liferay.portlet.messageboards.service.base.MBDiscussionLocalServiceBaseImpl;

import java.util.Date;

/**
 * @author Brian Wing Shun Chan
 */
public class MBDiscussionLocalServiceImpl
	extends MBDiscussionLocalServiceBaseImpl {

	@Override
	public MBDiscussion addDiscussion(
			long userId, long groupId, long classNameId, long classPK,
			long threadId, ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		long discussionId = counterLocalService.increment();

		MBDiscussion discussion = mbDiscussionPersistence.create(discussionId);

		discussion.setUuid(serviceContext.getUuid());
		discussion.setGroupId(groupId);
		discussion.setCompanyId(serviceContext.getCompanyId());
		discussion.setUserId(userId);
		discussion.setUserName(user.getFullName());
		discussion.setCreateDate(serviceContext.getCreateDate(now));
		discussion.setModifiedDate(serviceContext.getModifiedDate(now));
		discussion.setClassNameId(classNameId);
		discussion.setClassPK(classPK);
		discussion.setThreadId(threadId);

		mbDiscussionPersistence.update(discussion);

		return discussion;
	}

	/**
	 * @deprecated As of 7.0.0, replaced by {@link #addDiscussion(long, long,
	 *             long, long, long, ServiceContext)}
	 */
	@Deprecated
	@Override
	public MBDiscussion addDiscussion(
			long userId, long classNameId, long classPK, long threadId,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return addDiscussion(
			userId, serviceContext.getScopeGroupId(), classNameId, classPK,
			threadId, serviceContext);
	}

	@Override
	public MBDiscussion fetchDiscussion(long discussionId)
		throws SystemException {

		return mbDiscussionPersistence.fetchByPrimaryKey(discussionId);
	}

	@Override
	public MBDiscussion fetchDiscussion(String className, long classPK)
		throws SystemException {

		long classNameId = classNameLocalService.getClassNameId(className);

		return mbDiscussionPersistence.fetchByC_C(classNameId, classPK);
	}

	@Override
	public MBDiscussion getDiscussion(long discussionId)
		throws PortalException, SystemException {

		return mbDiscussionPersistence.findByPrimaryKey(discussionId);
	}

	@Override
	public MBDiscussion getDiscussion(String className, long classPK)
		throws PortalException, SystemException {

		long classNameId = classNameLocalService.getClassNameId(className);

		return mbDiscussionPersistence.findByC_C(classNameId, classPK);
	}

	@Override
	public MBDiscussion getThreadDiscussion(long threadId)
		throws PortalException, SystemException {

		return mbDiscussionPersistence.findByThreadId(threadId);
	}

	public void subscribeDiscussion(
			long userId, long groupId, String className, long classPK)
		throws PortalException, SystemException {

		subscriptionLocalService.addSubscription(
			userId, groupId, className, classPK);
	}

	public void unsubscribeDiscussion(
			long userId, String className, long classPK)
		throws PortalException, SystemException {

		subscriptionLocalService.deleteSubscription(userId, className, classPK);
	}

}