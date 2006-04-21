/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet.messageboards.service.permission;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBTopic;
import com.liferay.portlet.messageboards.service.spring.MBTopicLocalServiceUtil;

/**
 * <a href="MBTopicPermission.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBTopicPermission {

	public static void check(
			PermissionChecker permissionChecker, String topicId,
			String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, topicId, actionId)) {
			throw new PrincipalException();
		}
	}

	public static void check(
			PermissionChecker permissionChecker, MBTopic topic, String actionId)
		throws PortalException, SystemException {

		if (!contains(permissionChecker, topic, actionId)) {
			throw new PrincipalException();
		}
	}

	public static boolean contains(
			PermissionChecker permissionChecker, String topicId,
			String actionId)
		throws PortalException, SystemException {

		MBTopic topic = MBTopicLocalServiceUtil.getTopic(topicId);

		return contains(permissionChecker, topic, actionId);
	}

	public static boolean contains(
			PermissionChecker permissionChecker, MBTopic topic, String actionId)
		throws PortalException, SystemException {

		MBCategory category = topic.getCategory();

		return permissionChecker.hasPermission(
			category.getGroupId(), MBTopic.class.getName(),
			topic.getPrimaryKey().toString(), actionId);
	}

}