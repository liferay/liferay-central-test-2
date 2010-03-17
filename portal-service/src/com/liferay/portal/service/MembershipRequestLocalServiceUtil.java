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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="MembershipRequestLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link MembershipRequestLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MembershipRequestLocalService
 * @generated
 */
public class MembershipRequestLocalServiceUtil {
	public static com.liferay.portal.model.MembershipRequest addMembershipRequest(
		com.liferay.portal.model.MembershipRequest membershipRequest)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addMembershipRequest(membershipRequest);
	}

	public static com.liferay.portal.model.MembershipRequest createMembershipRequest(
		long membershipRequestId) {
		return getService().createMembershipRequest(membershipRequestId);
	}

	public static void deleteMembershipRequest(long membershipRequestId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteMembershipRequest(membershipRequestId);
	}

	public static void deleteMembershipRequest(
		com.liferay.portal.model.MembershipRequest membershipRequest)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteMembershipRequest(membershipRequest);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	public static int dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portal.model.MembershipRequest getMembershipRequest(
		long membershipRequestId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getMembershipRequest(membershipRequestId);
	}

	public static java.util.List<com.liferay.portal.model.MembershipRequest> getMembershipRequests(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getMembershipRequests(start, end);
	}

	public static int getMembershipRequestsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getMembershipRequestsCount();
	}

	public static com.liferay.portal.model.MembershipRequest updateMembershipRequest(
		com.liferay.portal.model.MembershipRequest membershipRequest)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateMembershipRequest(membershipRequest);
	}

	public static com.liferay.portal.model.MembershipRequest updateMembershipRequest(
		com.liferay.portal.model.MembershipRequest membershipRequest,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateMembershipRequest(membershipRequest, merge);
	}

	public static com.liferay.portal.model.MembershipRequest addMembershipRequest(
		long userId, long groupId, java.lang.String comments)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addMembershipRequest(userId, groupId, comments);
	}

	public static void deleteMembershipRequests(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteMembershipRequests(groupId);
	}

	public static void deleteMembershipRequests(long groupId, int statusId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteMembershipRequests(groupId, statusId);
	}

	public static java.util.List<com.liferay.portal.model.MembershipRequest> search(
		long groupId, int status, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().search(groupId, status, start, end);
	}

	public static int searchCount(long groupId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().searchCount(groupId, status);
	}

	public static void updateStatus(long replierUserId,
		long membershipRequestId, java.lang.String replyComments, int statusId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateStatus(replierUserId, membershipRequestId, replyComments,
			statusId);
	}

	public static MembershipRequestLocalService getService() {
		if (_service == null) {
			_service = (MembershipRequestLocalService)PortalBeanLocatorUtil.locate(MembershipRequestLocalService.class.getName());
		}

		return _service;
	}

	public void setService(MembershipRequestLocalService service) {
		_service = service;
	}

	private static MembershipRequestLocalService _service;
}