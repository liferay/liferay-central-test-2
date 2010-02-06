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
		throws com.liferay.portal.SystemException {
		return _membershipRequestLocalService.addMembershipRequest(membershipRequest);
	}

	public com.liferay.portal.model.MembershipRequest createMembershipRequest(
		long membershipRequestId) {
		return _membershipRequestLocalService.createMembershipRequest(membershipRequestId);
	}

	public void deleteMembershipRequest(long membershipRequestId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_membershipRequestLocalService.deleteMembershipRequest(membershipRequestId);
	}

	public void deleteMembershipRequest(
		com.liferay.portal.model.MembershipRequest membershipRequest)
		throws com.liferay.portal.SystemException {
		_membershipRequestLocalService.deleteMembershipRequest(membershipRequest);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _membershipRequestLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _membershipRequestLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	public com.liferay.portal.model.MembershipRequest getMembershipRequest(
		long membershipRequestId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _membershipRequestLocalService.getMembershipRequest(membershipRequestId);
	}

	public java.util.List<com.liferay.portal.model.MembershipRequest> getMembershipRequests(
		int start, int end) throws com.liferay.portal.SystemException {
		return _membershipRequestLocalService.getMembershipRequests(start, end);
	}

	public int getMembershipRequestsCount()
		throws com.liferay.portal.SystemException {
		return _membershipRequestLocalService.getMembershipRequestsCount();
	}

	public com.liferay.portal.model.MembershipRequest updateMembershipRequest(
		com.liferay.portal.model.MembershipRequest membershipRequest)
		throws com.liferay.portal.SystemException {
		return _membershipRequestLocalService.updateMembershipRequest(membershipRequest);
	}

	public com.liferay.portal.model.MembershipRequest updateMembershipRequest(
		com.liferay.portal.model.MembershipRequest membershipRequest,
		boolean merge) throws com.liferay.portal.SystemException {
		return _membershipRequestLocalService.updateMembershipRequest(membershipRequest,
			merge);
	}

	public com.liferay.portal.model.MembershipRequest addMembershipRequest(
		long userId, long groupId, java.lang.String comments)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _membershipRequestLocalService.addMembershipRequest(userId,
			groupId, comments);
	}

	public void deleteMembershipRequests(long groupId)
		throws com.liferay.portal.SystemException {
		_membershipRequestLocalService.deleteMembershipRequests(groupId);
	}

	public void deleteMembershipRequests(long groupId, int statusId)
		throws com.liferay.portal.SystemException {
		_membershipRequestLocalService.deleteMembershipRequests(groupId,
			statusId);
	}

	public java.util.List<com.liferay.portal.model.MembershipRequest> search(
		long groupId, int status, int start, int end)
		throws com.liferay.portal.SystemException {
		return _membershipRequestLocalService.search(groupId, status, start, end);
	}

	public int searchCount(long groupId, int status)
		throws com.liferay.portal.SystemException {
		return _membershipRequestLocalService.searchCount(groupId, status);
	}

	public void updateStatus(long replierUserId, long membershipRequestId,
		java.lang.String replyComments, int statusId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_membershipRequestLocalService.updateStatus(replierUserId,
			membershipRequestId, replyComments, statusId);
	}

	public MembershipRequestLocalService getWrappedMembershipRequestLocalService() {
		return _membershipRequestLocalService;
	}

	private MembershipRequestLocalService _membershipRequestLocalService;
}