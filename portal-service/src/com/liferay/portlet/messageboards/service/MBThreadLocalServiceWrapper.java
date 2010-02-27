/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.service;


/**
 * <a href="MBThreadLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link MBThreadLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBThreadLocalService
 * @generated
 */
public class MBThreadLocalServiceWrapper implements MBThreadLocalService {
	public MBThreadLocalServiceWrapper(
		MBThreadLocalService mbThreadLocalService) {
		_mbThreadLocalService = mbThreadLocalService;
	}

	public com.liferay.portlet.messageboards.model.MBThread addMBThread(
		com.liferay.portlet.messageboards.model.MBThread mbThread)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.addMBThread(mbThread);
	}

	public com.liferay.portlet.messageboards.model.MBThread createMBThread(
		long threadId) {
		return _mbThreadLocalService.createMBThread(threadId);
	}

	public void deleteMBThread(long threadId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbThreadLocalService.deleteMBThread(threadId);
	}

	public void deleteMBThread(
		com.liferay.portlet.messageboards.model.MBThread mbThread)
		throws com.liferay.portal.kernel.exception.SystemException {
		_mbThreadLocalService.deleteMBThread(mbThread);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.messageboards.model.MBThread getMBThread(
		long threadId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getMBThread(threadId);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> getMBThreads(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getMBThreads(start, end);
	}

	public int getMBThreadsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getMBThreadsCount();
	}

	public com.liferay.portlet.messageboards.model.MBThread updateMBThread(
		com.liferay.portlet.messageboards.model.MBThread mbThread)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.updateMBThread(mbThread);
	}

	public com.liferay.portlet.messageboards.model.MBThread updateMBThread(
		com.liferay.portlet.messageboards.model.MBThread mbThread, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.updateMBThread(mbThread, merge);
	}

	public void deleteThread(long threadId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbThreadLocalService.deleteThread(threadId);
	}

	public void deleteThread(
		com.liferay.portlet.messageboards.model.MBThread thread)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbThreadLocalService.deleteThread(thread);
	}

	public void deleteThreads(long groupId, long categoryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_mbThreadLocalService.deleteThreads(groupId, categoryId);
	}

	public int getCategoryThreadsCount(long groupId, long categoryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getCategoryThreadsCount(groupId,
			categoryId, status);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> getGroupThreads(
		long groupId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getGroupThreads(groupId, status, start, end);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> getGroupThreads(
		long groupId, long userId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getGroupThreads(groupId, userId, status,
			start, end);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> getGroupThreads(
		long groupId, long userId, int status, boolean subscribed, int start,
		int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getGroupThreads(groupId, userId, status,
			subscribed, start, end);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> getGroupThreads(
		long groupId, long userId, int status, boolean subscribed,
		boolean includeAnonymous, int start, int end)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getGroupThreads(groupId, userId, status,
			subscribed, includeAnonymous, start, end);
	}

	public int getGroupThreadsCount(long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getGroupThreadsCount(groupId, status);
	}

	public int getGroupThreadsCount(long groupId, long userId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getGroupThreadsCount(groupId, userId,
			status);
	}

	public int getGroupThreadsCount(long groupId, long userId, int status,
		boolean subscribed)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getGroupThreadsCount(groupId, userId,
			status, subscribed);
	}

	public int getGroupThreadsCount(long groupId, long userId, int status,
		boolean subscribed, boolean includeAnonymous)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getGroupThreadsCount(groupId, userId,
			status, subscribed, includeAnonymous);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> getPriorityThreads(
		long categoryId, double priority)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getPriorityThreads(categoryId, priority);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> getPriorityThreads(
		long categoryId, double priority, boolean inherit)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getPriorityThreads(categoryId, priority,
			inherit);
	}

	public com.liferay.portlet.messageboards.model.MBThread getThread(
		long threadId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getThread(threadId);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBThread> getThreads(
		long groupId, long categoryId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getThreads(groupId, categoryId, status,
			start, end);
	}

	public int getThreadsCount(long groupId, long categoryId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.getThreadsCount(groupId, categoryId, status);
	}

	public com.liferay.portlet.messageboards.model.MBThread moveThread(
		long groupId, long categoryId, long threadId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.moveThread(groupId, categoryId, threadId);
	}

	public com.liferay.portlet.messageboards.model.MBThread splitThread(
		long messageId, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.splitThread(messageId, serviceContext);
	}

	public com.liferay.portlet.messageboards.model.MBThread updateThread(
		long threadId, int viewCount)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _mbThreadLocalService.updateThread(threadId, viewCount);
	}

	public MBThreadLocalService getWrappedMBThreadLocalService() {
		return _mbThreadLocalService;
	}

	private MBThreadLocalService _mbThreadLocalService;
}