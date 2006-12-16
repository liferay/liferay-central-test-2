/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.softwarerepository.service.impl;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.service.impl.PrincipalBean;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.softwarerepository.model.SRFrameworkVersion;
import com.liferay.portlet.softwarerepository.service.SRFrameworkVersionService;
import com.liferay.portlet.softwarerepository.service.persistence.SRFrameworkVersionUtil;

import java.util.Date;
import java.util.List;

/**
 * <a href="SRFrameworkVersionServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class SRFrameworkVersionServiceImpl extends PrincipalBean
	implements SRFrameworkVersionService {

	public SRFrameworkVersion addFrameworkVersion(
			String userId, String plid, String name, boolean active,
			int priority, String url)
		throws PortalException, SystemException {

		User user = UserUtil.findByPrimaryKey(userId);
		String groupId = PortalUtil.getPortletGroupId(plid);
		Date now = new Date();

		long frameworkVersionId = CounterLocalServiceUtil.increment(
			SRFrameworkVersion.class.getName());

		SRFrameworkVersion frameworkVersion =
			SRFrameworkVersionUtil.create(frameworkVersionId);

		frameworkVersion.setCompanyId(user.getCompanyId());
		frameworkVersion.setGroupId(groupId);
		frameworkVersion.setUserId(user.getUserId());
		frameworkVersion.setUserName(user.getFullName());
		frameworkVersion.setCreateDate(now);
		frameworkVersion.setModifiedDate(now);

		frameworkVersion.setName(name);
		frameworkVersion.setActive(active);
		frameworkVersion.setPriority(priority);
		frameworkVersion.setUrl(url);

		SRFrameworkVersionUtil.update(frameworkVersion);

		return frameworkVersion;
	}

	public void deleteFrameworkVersion(long frameworkVersionId)
		throws PortalException, SystemException {

		SRFrameworkVersion frameworkVersion =
			SRFrameworkVersionUtil.findByPrimaryKey(frameworkVersionId);

		SRFrameworkVersionUtil.remove(frameworkVersion.getFrameworkVersionId());
	}

	public SRFrameworkVersion getFrameworkVersion(long frameworkVersionId)
		throws PortalException, SystemException {

		return SRFrameworkVersionUtil.findByPrimaryKey(frameworkVersionId);
	}

	public List getFrameworkVersions(String groupId)
		throws SystemException {

		return SRFrameworkVersionUtil.findByGroupId(groupId);
	}

	public List getFrameworkVersions(String groupId, boolean active)
		throws SystemException {

		return SRFrameworkVersionUtil.findByG_A(groupId, active);
	}

	public int getFrameworkVersionsCount(String groupId)
		throws SystemException {

		return SRFrameworkVersionUtil.countByG_A(groupId, true) +
			SRFrameworkVersionUtil.countByG_A(groupId, false);
	}

	public int getFrameworkVersionsCount(String groupId, boolean active)
		throws SystemException {

		return SRFrameworkVersionUtil.countByG_A(groupId, active);
	}

	public SRFrameworkVersion updateFrameworkVersion(
		long frameworkVersionId, String name, boolean active, int priority,
		String url)
		throws PortalException, SystemException {

		SRFrameworkVersion frameworkVersion =
			SRFrameworkVersionUtil.findByPrimaryKey(frameworkVersionId);

		frameworkVersion.setName(name);
		frameworkVersion.setActive(active);
		frameworkVersion.setPriority(priority);
		frameworkVersion.setUrl(url);

		SRFrameworkVersionUtil.update(frameworkVersion);

		return frameworkVersion;
	}

}