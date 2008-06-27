/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.kernel.util.Time;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionCheckerFactory;
import com.liferay.portal.security.permission.PermissionCheckerImpl;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.communities.util.StagingUtil;
import com.liferay.util.JSONUtil;
import com.liferay.util.MapUtil;

import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="LayoutsRemotePublisherMessageListener.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class LayoutsRemotePublisherMessageListener implements MessageListener {

	public void receive(String message) {
		PermissionCheckerImpl permissionChecker = null;

		try {
			LayoutsRemotePublisherRequest publisherRequest =
				(LayoutsRemotePublisherRequest)JSONUtil.deserialize(message);

			long userId = publisherRequest.getUserId();
			long sourceGroupId = publisherRequest.getSourceGroupId();
			boolean privateLayout = publisherRequest.isPrivateLayout();
			Map<Long, Boolean> layoutIdMap = publisherRequest.getLayoutIdMap();
			Map<String, String[]> parameterMap =
				publisherRequest.getParameterMap();
			String remoteAddress = publisherRequest.getRemoteAddress();
			int remotePort = publisherRequest.getRemotePort();
			boolean secureConnection = publisherRequest.isSecureConnection();
			long remoteGroupId = publisherRequest.getRemoteGroupId();
			boolean remotePrivateLayout =
				publisherRequest.isRemotePrivateLayout();
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

			permissionChecker = PermissionCheckerFactory.create(user, false);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			StagingUtil.copyRemoteLayouts(
				sourceGroupId, privateLayout, layoutIdMap, parameterMap,
				remoteAddress, remotePort, secureConnection, remoteGroupId,
				remotePrivateLayout, parameterMap, startDate, endDate);
		}
		catch (Exception e) {
			_log.error(e, e);
		}
		finally {
			try {
				PermissionCheckerFactory.recycle(permissionChecker);
			}
			catch (Exception e) {
			}
		}
	}

	private static Log _log =
		LogFactory.getLog(LayoutsRemotePublisherMessageListener.class);

}