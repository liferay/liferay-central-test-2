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
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().addMembershipRequest(groupId, comments);
	}

	public static void deleteMembershipRequests(long groupId, int statusId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteMembershipRequests(groupId, statusId);
	}

	public static com.liferay.portal.model.MembershipRequest getMembershipRequest(
		long membershipRequestId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getMembershipRequest(membershipRequestId);
	}

	public static void updateStatus(long membershipRequestId,
		java.lang.String reviewComments, int statusId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
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