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

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.security.permission.ActionKeys;
import com.liferay.portal.service.impl.PrincipalBean;
import com.liferay.portlet.messageboards.model.MBTopic;
import com.liferay.portlet.messageboards.service.permission.MBCategoryPermission;
import com.liferay.portlet.messageboards.service.permission.MBTopicPermission;
import com.liferay.portlet.messageboards.service.spring.MBTopicLocalServiceUtil;
import com.liferay.portlet.messageboards.service.spring.MBTopicService;

/**
 * <a href="MBTopicServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBTopicServiceImpl
	extends PrincipalBean implements MBTopicService {

	public MBTopic addTopic(
			String categoryId, String name, String description,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		MBCategoryPermission.check(
			getPermissionChecker(), categoryId, ActionKeys.ADD_TOPIC);

		return MBTopicLocalServiceUtil.addTopic(
			getUserId(), categoryId, name, description, addCommunityPermissions,
			addGuestPermissions);
	}

	public void deleteTopic(String topicId)
		throws PortalException, SystemException {

		MBTopicPermission.check(
			getPermissionChecker(), topicId, ActionKeys.DELETE);

		MBTopicLocalServiceUtil.deleteTopic(topicId);
	}

	public MBTopic getTopic(String topicId)
		throws PortalException, SystemException {

		MBTopicPermission.check(
			getPermissionChecker(), topicId, ActionKeys.VIEW);

		return MBTopicLocalServiceUtil.getTopic(topicId);
	}

	public MBTopic updateTopic(
			String topicId, String categoryId, String name, String description)
		throws PortalException, SystemException {

		MBTopicPermission.check(
			getPermissionChecker(), topicId, ActionKeys.UPDATE);

		return MBTopicLocalServiceUtil.updateTopic(
			topicId, categoryId, name, description);
	}

}