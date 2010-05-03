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

package com.liferay.portal.service;


/**
 * <a href="MembershipRequestLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link MembershipRequestLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MembershipRequestLocalService
 * @generated
 */
public class MembershipRequestLocalServiceWrapper
	implements MembershipRequestLocalService {
	public MembershipRequestLocalServiceWrapper(
		MembershipRequestLocalService membershipRequestLocalService) {
		_membershipRequestLocalService = membershipRequestLocalService;
	}

	public com.liferay.portal.model.MembershipRequest addMembershipRequest(
		com.liferay.portal.model.MembershipRequest membershipRequest)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _membershipRequestLocalService.addMembershipRequest(membershipRequest);
	}

	public com.liferay.portal.model.MembershipRequest createMembershipRequest(
		long membershipRequestId) {
		return _membershipRequestLocalService.createMembershipRequest(membershipRequestId);
	}

	public void deleteMembershipRequest(long membershipRequestId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_membershipRequestLocalService.deleteMembershipRequest(membershipRequestId);
	}

	public void deleteMembershipRequest(
		com.liferay.portal.model.MembershipRequest membershipRequest)
		throws com.liferay.portal.kernel.exception.SystemException {
		_membershipRequestLocalService.deleteMembershipRequest(membershipRequest);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _membershipRequestLocalService.dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _membershipRequestLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _membershipRequestLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _membershipRequestLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portal.model.MembershipRequest getMembershipRequest(
		long membershipRequestId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _membershipRequestLocalService.getMembershipRequest(membershipRequestId);
	}

	public java.util.List<com.liferay.portal.model.MembershipRequest> getMembershipRequests(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _membershipRequestLocalService.getMembershipRequests(start, end);
	}

	public int getMembershipRequestsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _membershipRequestLocalService.getMembershipRequestsCount();
	}

	public com.liferay.portal.model.MembershipRequest updateMembershipRequest(
		com.liferay.portal.model.MembershipRequest membershipRequest)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _membershipRequestLocalService.updateMembershipRequest(membershipRequest);
	}

	public com.liferay.portal.model.MembershipRequest updateMembershipRequest(
		com.liferay.portal.model.MembershipRequest membershipRequest,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _membershipRequestLocalService.updateMembershipRequest(membershipRequest,
			merge);
	}

	public com.liferay.portal.model.MembershipRequest addMembershipRequest(
		long userId, long groupId, java.lang.String comments)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _membershipRequestLocalService.addMembershipRequest(userId,
			groupId, comments);
	}

	public void deleteMembershipRequests(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_membershipRequestLocalService.deleteMembershipRequests(groupId);
	}

	public void deleteMembershipRequests(long groupId, int statusId)
		throws com.liferay.portal.kernel.exception.SystemException {
		_membershipRequestLocalService.deleteMembershipRequests(groupId,
			statusId);
	}

	public java.util.List<com.liferay.portal.model.MembershipRequest> search(
		long groupId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _membershipRequestLocalService.search(groupId, status, start, end);
	}

	public int searchCount(long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _membershipRequestLocalService.searchCount(groupId, status);
	}

	public void updateStatus(long replierUserId, long membershipRequestId,
		java.lang.String replyComments, int statusId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_membershipRequestLocalService.updateStatus(replierUserId,
			membershipRequestId, replyComments, statusId);
	}

	public MembershipRequestLocalService getWrappedMembershipRequestLocalService() {
		return _membershipRequestLocalService;
	}

	private MembershipRequestLocalService _membershipRequestLocalService;
}