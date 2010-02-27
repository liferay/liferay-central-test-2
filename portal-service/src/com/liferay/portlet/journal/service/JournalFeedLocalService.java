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

package com.liferay.portlet.journal.service;

import com.liferay.portal.kernel.annotation.Isolation;
import com.liferay.portal.kernel.annotation.Propagation;
import com.liferay.portal.kernel.annotation.Transactional;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;

/**
 * <a href="JournalFeedLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * {@link
 * com.liferay.portlet.journal.service.impl.JournalFeedLocalServiceImpl}}.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       JournalFeedLocalServiceUtil
 * @generated
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface JournalFeedLocalService {
	public com.liferay.portlet.journal.model.JournalFeed addJournalFeed(
		com.liferay.portlet.journal.model.JournalFeed journalFeed)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.journal.model.JournalFeed createJournalFeed(
		long id);

	public void deleteJournalFeed(long id)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void deleteJournalFeed(
		com.liferay.portlet.journal.model.JournalFeed journalFeed)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException;

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.journal.model.JournalFeed getJournalFeed(long id)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.journal.model.JournalFeed> getJournalFeeds(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getJournalFeedsCount()
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.journal.model.JournalFeed updateJournalFeed(
		com.liferay.portlet.journal.model.JournalFeed journalFeed)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.journal.model.JournalFeed updateJournalFeed(
		com.liferay.portlet.journal.model.JournalFeed journalFeed, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.journal.model.JournalFeed addFeed(long userId,
		long groupId, java.lang.String feedId, boolean autoFeedId,
		java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String structureId,
		java.lang.String templateId, java.lang.String rendererTemplateId,
		int delta, java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedType, double feedVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.journal.model.JournalFeed addFeed(
		java.lang.String uuid, long userId, long groupId,
		java.lang.String feedId, boolean autoFeedId, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		java.lang.String rendererTemplateId, int delta,
		java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedType, double feedVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void addFeedResources(long feedId, boolean addCommunityPermissions,
		boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void addFeedResources(
		com.liferay.portlet.journal.model.JournalFeed feed,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void addFeedResources(long feedId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void addFeedResources(
		com.liferay.portlet.journal.model.JournalFeed feed,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void deleteFeed(long feedId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void deleteFeed(long groupId, java.lang.String feedId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	public void deleteFeed(com.liferay.portlet.journal.model.JournalFeed feed)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.journal.model.JournalFeed getFeed(long feedId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.journal.model.JournalFeed getFeed(long groupId,
		java.lang.String feedId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.journal.model.JournalFeed> getFeeds()
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.journal.model.JournalFeed> getFeeds(
		long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.journal.model.JournalFeed> getFeeds(
		long groupId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getFeedsCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.journal.model.JournalFeed> search(
		long companyId, long groupId, java.lang.String keywords, int start,
		int end, com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.journal.model.JournalFeed> search(
		long companyId, long groupId, java.lang.String feedId,
		java.lang.String name, java.lang.String description,
		boolean andOperator, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long companyId, long groupId,
		java.lang.String keywords)
		throws com.liferay.portal.kernel.exception.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int searchCount(long companyId, long groupId,
		java.lang.String feedId, java.lang.String name,
		java.lang.String description, boolean andOperator)
		throws com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portlet.journal.model.JournalFeed updateFeed(
		long groupId, java.lang.String feedId, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		java.lang.String rendererTemplateId, int delta,
		java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedType, double feedVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException;
}