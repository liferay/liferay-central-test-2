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
 * <a href="MembershipRequestServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link MembershipRequestService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MembershipRequestService
 * @generated
 */
public class MembershipRequestServiceUtil {
	public static com.liferay.portal.model.MembershipRequest addMembershipRequest(
		long groupId, java.lang.String comments)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addMembershipRequest(groupId, comments);
	}

	public static void deleteMembershipRequests(long groupId, int statusId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteMembershipRequests(groupId, statusId);
	}

	public static com.liferay.portal.model.MembershipRequest getMembershipRequest(
		long membershipRequestId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getMembershipRequest(membershipRequestId);
	}

	public static void updateStatus(long membershipRequestId,
		java.lang.String reviewComments, int statusId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().updateStatus(membershipRequestId, reviewComments, statusId);
	}

	public static MembershipRequestService getService() {
		if (_service == null) {
			_service = (MembershipRequestService)PortalBeanLocatorUtil.locate(MembershipRequestService.class.getName());
		}

		return _service;
	}

	public void setService(MembershipRequestService service) {
		_service = service;
	}

	private static MembershipRequestService _service;
}