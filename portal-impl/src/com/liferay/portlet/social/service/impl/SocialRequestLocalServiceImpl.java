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

package com.liferay.portlet.social.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.social.NoSuchRequestException;
import com.liferay.portlet.social.RequestUserIdException;
import com.liferay.portlet.social.model.SocialRequest;
import com.liferay.portlet.social.model.SocialRequestConstants;
import com.liferay.portlet.social.service.base.SocialRequestLocalServiceBaseImpl;

import java.util.List;

/**
 * <a href="SocialRequestLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 */
public class SocialRequestLocalServiceImpl
	extends SocialRequestLocalServiceBaseImpl {

	public SocialRequest addRequest(
			long userId, long groupId, String className, long classPK,
			int type, String extraData, long receiverUserId)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		long classNameId = PortalUtil.getClassNameId(className);
		User receiverUser = userPersistence.findByPrimaryKey(receiverUserId);
		long now = System.currentTimeMillis();

		if ((userId == receiverUserId) || (user.isDefaultUser()) ||
			(receiverUser.isDefaultUser()) ||
			(user.getCompanyId() != receiverUser.getCompanyId())) {

			throw new RequestUserIdException();
		}

		try {
			socialRequestPersistence.removeByU_C_C_T_R(
				userId, classNameId, classPK, type, receiverUserId);
		}
		catch (NoSuchRequestException nsre) {
		}

		long requestId = counterLocalService.increment(
			SocialRequest.class.getName());

		SocialRequest request = socialRequestPersistence.create(requestId);

		request.setGroupId(groupId);
		request.setCompanyId(user.getCompanyId());
		request.setUserId(user.getUserId());
		request.setCreateDate(now);
		request.setModifiedDate(now);
		request.setClassNameId(classNameId);
		request.setClassPK(classPK);
		request.setType(type);
		request.setExtraData(extraData);
		request.setReceiverUserId(receiverUserId);
		request.setStatus(SocialRequestConstants.STATUS_PENDING);

		socialRequestPersistence.update(request, false);

		return request;
	}

	public void deleteReceiverUserRequests(long receiverUserId)
		throws SystemException {

		socialRequestPersistence.removeByReceiverUserId(receiverUserId);
	}

	public void deleteRequest(long requestId)
		throws PortalException, SystemException {

		socialRequestPersistence.remove(requestId);
	}

	public void deleteUserRequests(long userId) throws SystemException {
		socialRequestPersistence.removeByUserId(userId);
	}

	public List<SocialRequest> getReceiverUserRequests(
			long receiverUserId, int start, int end)
		throws SystemException {

		return socialRequestPersistence.findByReceiverUserId(
			receiverUserId, start, end);
	}

	public List<SocialRequest> getReceiverUserRequests(
			long receiverUserId, int status, int start, int end)
		throws SystemException {

		return socialRequestPersistence.findByR_S(
			receiverUserId, status, start, end);
	}

	public int getReceiverUserRequestsCount(long receiverUserId)
		throws SystemException {

		return socialRequestPersistence.countByReceiverUserId(receiverUserId);
	}

	public int getReceiverUserRequestsCount(long receiverUserId, int status)
		throws SystemException {

		return socialRequestPersistence.countByR_S(receiverUserId, status);
	}

	public List<SocialRequest> getUserRequests(long userId, int start, int end)
		throws SystemException {

		return socialRequestPersistence.findByUserId(userId, start, end);
	}

	public List<SocialRequest> getUserRequests(
			long userId, int status, int start, int end)
		throws SystemException {

		return socialRequestPersistence.findByU_S(userId, status, start, end);
	}

	public int getUserRequestsCount(long userId) throws SystemException {
		return socialRequestPersistence.countByUserId(userId);
	}

	public int getUserRequestsCount(long userId, int status)
		throws SystemException {

		return socialRequestPersistence.countByU_S(userId, status);
	}

	public boolean hasRequest(
			long userId, String className, long classPK, int type, int status)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		if (socialRequestPersistence.countByU_C_C_T_S(
				userId, classNameId, classPK, type, status) <= 0) {

			return false;
		}
		else {
			return true;
		}
	}

	public boolean hasRequest(
			long userId, String className, long classPK, int type,
			long receiverUserId, int status)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		SocialRequest socialRequest =
			socialRequestPersistence.fetchByU_C_C_T_R(
				userId, classNameId, classPK, type, receiverUserId);

		if ((socialRequest == null) || (socialRequest.getStatus() != status)) {
			return false;
		}
		else {
			return true;
		}
	}

	public SocialRequest updateRequest(
			long requestId, int status, ThemeDisplay themeDisplay)
		throws PortalException, SystemException {

		SocialRequest request = socialRequestPersistence.findByPrimaryKey(
			requestId);

		request.setModifiedDate(System.currentTimeMillis());
		request.setStatus(status);

		socialRequestPersistence.update(request, false);

		if (status == SocialRequestConstants.STATUS_CONFIRM) {
			socialRequestInterpreterLocalService.processConfirmation(
				request, themeDisplay);
		}
		else if (status == SocialRequestConstants.STATUS_IGNORE) {
			socialRequestInterpreterLocalService.processRejection(
				request, themeDisplay);
		}

		return request;
	}

}