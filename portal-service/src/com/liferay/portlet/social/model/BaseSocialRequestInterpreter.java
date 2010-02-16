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

package com.liferay.portlet.social.model;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.User;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portlet.social.service.persistence.SocialRequestUtil;

import java.util.List;

/**
 * <a href="BaseSocialRequestInterpreter.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 * @author Amos Fong
 */
public abstract class BaseSocialRequestInterpreter
	implements SocialRequestInterpreter {

	public String getUserName(long userId, ThemeDisplay themeDisplay) {
		try {
			if (userId <= 0) {
				return StringPool.BLANK;
			}

			User user = UserLocalServiceUtil.getUserById(userId);

			if (user.getUserId() == themeDisplay.getUserId()) {
				return user.getFirstName();
			}

			String userName = user.getFullName();

			Group group = user.getGroup();

			if (group.getGroupId() == themeDisplay.getScopeGroupId()) {
				return userName;
			}

			String userDisplayURL = user.getDisplayURL(themeDisplay);

			userName =
				"<a href=\"" + userDisplayURL + "\">" + userName + "</a>";

			return userName;
		}
		catch (Exception e) {
			return StringPool.BLANK;
		}
	}

	public SocialRequestFeedEntry interpret(
		SocialRequest request, ThemeDisplay themeDisplay) {

		try {
			return doInterpret(request, themeDisplay);
		}
		catch (Exception e) {
			_log.error("Unable to interpret request", e);
		}

		return null;
	}

	public boolean processConfirmation(
		SocialRequest request, ThemeDisplay themeDisplay) {

		try {
			return doProcessConfirmation(request, themeDisplay);
		}
		catch (Exception e) {
			_log.error("Unable to process confirmation", e);
		}

		return false;
	}

	public void processDuplicateRequestsFromUser(
			SocialRequest request, int oldStatus)
		throws SystemException {

		List<SocialRequest> requests = SocialRequestUtil.findByU_C_C_T_S(
			request.getUserId(), request.getClassNameId(), request.getClassPK(),
			request.getType(), oldStatus);

		int newStatus = request.getStatus();

		for (SocialRequest curRequest : requests) {
			curRequest.setStatus(newStatus);

			SocialRequestUtil.update(curRequest, false);
		}
	}

	public void processDuplicateRequestsToUser(
			SocialRequest request, int oldStatus)
		throws SystemException {

		List<SocialRequest> requests = SocialRequestUtil.findByC_C_T_R_S(
			request.getClassNameId(), request.getClassPK(), request.getType(),
			request.getReceiverUserId(), oldStatus);

		int newStatus = request.getStatus();

		for (SocialRequest curRequest : requests) {
			curRequest.setStatus(newStatus);

			SocialRequestUtil.update(curRequest, false);
		}
	}

	public boolean processRejection(
		SocialRequest request, ThemeDisplay themeDisplay) {

		try {
			return doProcessRejection(request, themeDisplay);
		}
		catch (Exception e) {
			_log.error("Unable to process rejection", e);
		}

		return false;
	}

	protected abstract SocialRequestFeedEntry doInterpret(
			SocialRequest request, ThemeDisplay themeDisplay)
		throws Exception;

	protected abstract boolean doProcessConfirmation(
			SocialRequest request, ThemeDisplay themeDisplay)
		throws Exception;

	protected boolean doProcessRejection(
			SocialRequest request, ThemeDisplay themeDisplay)
		throws Exception {

		return true;
	}

	private static Log _log = LogFactoryUtil.getLog(
		BaseSocialRequestInterpreter.class);

}