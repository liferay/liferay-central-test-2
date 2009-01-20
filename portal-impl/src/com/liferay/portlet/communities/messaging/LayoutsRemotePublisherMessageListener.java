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

package com.liferay.portlet.communities.messaging;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Message;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.communities.util.StagingUtil;
import com.liferay.util.MapUtil;

import java.util.Date;
import java.util.Map;

/**
 * <a href="LayoutsRemotePublisherMessageListener.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class LayoutsRemotePublisherMessageListener implements MessageListener {

	public void receive(Message message) {
		try {
			doReceive(message);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
	}

	protected void doReceive(Message message) throws Exception {
		LayoutsRemotePublisherRequest publisherRequest =
			(LayoutsRemotePublisherRequest)JSONFactoryUtil.deserialize(
				(String)message.getPayload());

		long userId = publisherRequest.getUserId();
		long sourceGroupId = publisherRequest.getSourceGroupId();
		boolean privateLayout = publisherRequest.isPrivateLayout();
		Map<Long, Boolean> layoutIdMap = publisherRequest.getLayoutIdMap();
		Map<String, String[]> parameterMap = publisherRequest.getParameterMap();
		String remoteAddress = publisherRequest.getRemoteAddress();
		int remotePort = publisherRequest.getRemotePort();
		boolean secureConnection = publisherRequest.isSecureConnection();
		long remoteGroupId = publisherRequest.getRemoteGroupId();
		boolean remotePrivateLayout = publisherRequest.isRemotePrivateLayout();
		Date startDate = publisherRequest.getStartDate();
		Date endDate = publisherRequest.getEndDate();

		String range = MapUtil.getString(parameterMap, "range");

		if (range.equals("last")) {
			int last = MapUtil.getInteger(parameterMap, "last");

			if (last > 0) {
				Date scheduledFireTime =
					publisherRequest.getScheduledFireTime();

				startDate = new Date(
					scheduledFireTime.getTime() - (last * Time.HOUR));

				endDate = scheduledFireTime;
			}
		}

		PrincipalThreadLocal.setName(userId);

		User user = UserLocalServiceUtil.getUserById(userId);

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user, false);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);

		StagingUtil.copyRemoteLayouts(
			sourceGroupId, privateLayout, layoutIdMap, parameterMap,
			remoteAddress, remotePort, secureConnection, remoteGroupId,
			remotePrivateLayout, parameterMap, startDate, endDate);
	}

	private static Log _log =
		LogFactoryUtil.getLog(LayoutsRemotePublisherMessageListener.class);

}