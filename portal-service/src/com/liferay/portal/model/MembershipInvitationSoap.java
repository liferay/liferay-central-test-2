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

package com.liferay.portal.model;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <a href="MembershipInvitationSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is used by
 * <code>com.liferay.portal.service.http.MembershipInvitationServiceSoap</code>.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.http.MembershipInvitationServiceSoap
 *
 */
public class MembershipInvitationSoap implements Serializable {
	public static MembershipInvitationSoap toSoapModel(
		MembershipInvitation model) {
		MembershipInvitationSoap soapModel = new MembershipInvitationSoap();

		soapModel.setMembershipRequestId(model.getMembershipRequestId());
		soapModel.setCompanyId(model.getCompanyId());
		soapModel.setUserId(model.getUserId());
		soapModel.setCreateDate(model.getCreateDate());
		soapModel.setAcceptedDate(model.getAcceptedDate());
		soapModel.setDeclinedDate(model.getDeclinedDate());
		soapModel.setInvitedUserId(model.getInvitedUserId());
		soapModel.setGroupId(model.getGroupId());
		soapModel.setKey(model.getKey());

		return soapModel;
	}

	public static MembershipInvitationSoap[] toSoapModels(
		List<MembershipInvitation> models) {
		List<MembershipInvitationSoap> soapModels = new ArrayList<MembershipInvitationSoap>(models.size());

		for (MembershipInvitation model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new MembershipInvitationSoap[soapModels.size()]);
	}

	public MembershipInvitationSoap() {
	}

	public long getPrimaryKey() {
		return _membershipRequestId;
	}

	public void setPrimaryKey(long pk) {
		setMembershipRequestId(pk);
	}

	public long getMembershipRequestId() {
		return _membershipRequestId;
	}

	public void setMembershipRequestId(long membershipRequestId) {
		_membershipRequestId = membershipRequestId;
	}

	public long getCompanyId() {
		return _companyId;
	}

	public void setCompanyId(long companyId) {
		_companyId = companyId;
	}

	public long getUserId() {
		return _userId;
	}

	public void setUserId(long userId) {
		_userId = userId;
	}

	public Date getCreateDate() {
		return _createDate;
	}

	public void setCreateDate(Date createDate) {
		_createDate = createDate;
	}

	public Date getAcceptedDate() {
		return _acceptedDate;
	}

	public void setAcceptedDate(Date acceptedDate) {
		_acceptedDate = acceptedDate;
	}

	public Date getDeclinedDate() {
		return _declinedDate;
	}

	public void setDeclinedDate(Date declinedDate) {
		_declinedDate = declinedDate;
	}

	public long getInvitedUserId() {
		return _invitedUserId;
	}

	public void setInvitedUserId(long invitedUserId) {
		_invitedUserId = invitedUserId;
	}

	public long getGroupId() {
		return _groupId;
	}

	public void setGroupId(long groupId) {
		_groupId = groupId;
	}

	public String getKey() {
		return _key;
	}

	public void setKey(String key) {
		_key = key;
	}

	private long _membershipRequestId;
	private long _companyId;
	private long _userId;
	private Date _createDate;
	private Date _acceptedDate;
	private Date _declinedDate;
	private long _invitedUserId;
	private long _groupId;
	private String _key;
}