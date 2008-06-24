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
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionCheckerFactory;
import com.liferay.portal.security.permission.PermissionCheckerImpl;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portlet.communities.util.StagingUtil;
import com.liferay.util.JSONUtil;

import java.util.Date;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="LayoutsRemoteExporterMessageListener.java.html"><b><i>View Source
 * </i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class LayoutsRemoteExporterMessageListener implements MessageListener {

	public void receive(String message) {
		PermissionCheckerImpl permissionChecker = null;

		try {
			LayoutsPublisherRequest layoutsPublisherRequest =
				(LayoutsPublisherRequest)JSONUtil.deserialize(message);

			long userId = layoutsPublisherRequest.getUserId();
			long stagingGroupId = layoutsPublisherRequest.getStagingGroupId();
			long liveGroupId = layoutsPublisherRequest.getLiveGroupId();
			boolean privateLayout = layoutsPublisherRequest.isPrivateLayout();
			Map<Long, Boolean> layoutIdMap =
				layoutsPublisherRequest.getLayoutIdMap();
			boolean remotePrivateLayout =
				layoutsPublisherRequest.isRemotePrivateLayout();
			Map<String, String[]> parameterMap =
				layoutsPublisherRequest.getParameterMap();
			String remoteAddress = layoutsPublisherRequest.getRemoteAddress();
			int remotePort = layoutsPublisherRequest.getRemotePort();
			boolean secureConnection =
				layoutsPublisherRequest.isSecureConnection();
			Date startDate = layoutsPublisherRequest.getStartDate();
			Date endDate = layoutsPublisherRequest.getEndDate();

			PrincipalThreadLocal.setName(userId);

			User user = UserLocalServiceUtil.getUserById(userId);

			permissionChecker = PermissionCheckerFactory.create(user, false);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);

			StagingUtil.copyRemoteLayouts(
				stagingGroupId, privateLayout, layoutIdMap, parameterMap,
				remoteAddress, remotePort, secureConnection, liveGroupId,
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
		LogFactory.getLog(LayoutsRemoteExporterMessageListener.class);

}