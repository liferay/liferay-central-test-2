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

package com.liferay.portlet.communities.util;

import com.liferay.portal.NoSuchUserException;
import com.liferay.portal.kernel.job.PublishToLiveEvent;
import com.liferay.portal.kernel.messaging.MessageListener;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionCheckerFactory;
import com.liferay.portal.security.permission.PermissionCheckerImpl;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.util.JSONUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="PublishToLiveEvent.java.html"><b><i>View Source</i></b></a>
 *
 * @author Bruno Farache
 *
 */
public class StagingMessageListener implements MessageListener {

	public void receive(String message) {
		try {
			PublishToLiveEvent event = (PublishToLiveEvent)JSONUtil.deserialize(
				message);

			PrincipalThreadLocal.setName(event.getUserId());

			User user = UserLocalServiceUtil.getUserById(event.getUserId());

			PermissionCheckerImpl _permissionChecker =
				PermissionCheckerFactory.create(user, false);

			PermissionThreadLocal.setPermissionChecker(_permissionChecker);

			if (event.getScope().equals("all-pages")) {
				StagingUtil.copyLayouts(
					event.getStagingGroupId(), event.getLiveGroupId(),
					event.isPrivateLayout(), event.getParameterMap());
			}
			else {
				StagingUtil.publishLayouts(
					event.getLayoutIdMap(), event.getStagingGroupId(),
					event.getLiveGroupId(), event.isPrivateLayout(),
					event.getParameterMap());
			}
		}
		catch(NoSuchUserException nsue) {
			_log.error("User that created the scheduled publishing doesn't " +
				"exist anymore");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Log _log = LogFactory.getLog(StagingMessageListener.class);

}