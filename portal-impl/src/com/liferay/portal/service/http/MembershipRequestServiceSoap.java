/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.MembershipRequestServiceUtil;

import java.rmi.RemoteException;

public class MembershipRequestServiceSoap {
	public static com.liferay.portal.model.MembershipRequestSoap addMembershipRequest(
		long groupId, java.lang.String comments) throws RemoteException {
		try {
			com.liferay.portal.model.MembershipRequest returnValue = MembershipRequestServiceUtil.addMembershipRequest(groupId,
					comments);

			return com.liferay.portal.model.MembershipRequestSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteMembershipRequests(long groupId, int statusId)
		throws RemoteException {
		try {
			MembershipRequestServiceUtil.deleteMembershipRequests(groupId,
				statusId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.MembershipRequestSoap getMembershipRequest(
		long membershipRequestId) throws RemoteException {
		try {
			com.liferay.portal.model.MembershipRequest returnValue = MembershipRequestServiceUtil.getMembershipRequest(membershipRequestId);

			return com.liferay.portal.model.MembershipRequestSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void updateStatus(long membershipRequestId,
		java.lang.String reviewComments, int statusId)
		throws RemoteException {
		try {
			MembershipRequestServiceUtil.updateStatus(membershipRequestId,
				reviewComments, statusId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(MembershipRequestServiceSoap.class);
}