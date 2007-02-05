/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.softwarerepository.model.SRFrameworkVersion;
import com.liferay.portlet.softwarerepository.service.base.SRFrameworkVersionLocalServiceBaseImpl;
import com.liferay.portlet.softwarerepository.service.persistence.SRFrameworkVersionUtil;
import com.liferay.portlet.softwarerepository.service.persistence.SRProductVersionUtil;

import java.util.Date;
import java.util.List;

/**
 * <a href="SRFrameworkVersionLocalServiceImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 *
 */
public class SRFrameworkVersionLocalServiceImpl
	extends SRFrameworkVersionLocalServiceBaseImpl {

	public SRFrameworkVersion addFrameworkVersion(
			String userId, String plid, String name, String url, boolean active,
			int priority, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addFrameworkVersion(
			userId, plid, name, url, active, priority,
			new Boolean(addCommunityPermissions),
			new Boolean(addGuestPermissions), null, null);
	}

	public SRFrameworkVersion addFrameworkVersion(
			String userId, String plid, String name, String url, boolean active,
			int priority, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		return addFrameworkVersion(
			userId, plid, name, url, active, priority, null, null,
			communityPermissions, guestPermissions);
	}

	public SRFrameworkVersion addFrameworkVersion(
			String userId, String plid, String name, String url, boolean active,
			int priority, Boolean addCommunityPermissions,
			Boolean addGuestPermissions, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		// Framework version

		User user = UserUtil.findByPrimaryKey(userId);
		long groupId = PortalUtil.getPortletGroupId(plid);
		Date now = new Date();

		long frameworkVersionId = CounterLocalServiceUtil.increment(
			SRFrameworkVersion.class.getName());

		SRFrameworkVersion frameworkVersion =
			SRFrameworkVersionUtil.create(frameworkVersionId);

		frameworkVersion.setGroupId(groupId);
		frameworkVersion.setCompanyId(user.getCompanyId());
		frameworkVersion.setUserId(user.getUserId());
		frameworkVersion.setUserName(user.getFullName());
		frameworkVersion.setCreateDate(now);
		frameworkVersion.setModifiedDate(now);
		frameworkVersion.setName(name);
		frameworkVersion.setUrl(url);
		frameworkVersion.setActive(active);
		frameworkVersion.setPriority(priority);

		SRFrameworkVersionUtil.update(frameworkVersion);

		// Resources

		if ((addCommunityPermissions != null) &&
			(addGuestPermissions != null)) {

			addFrameworkVersionResources(
				frameworkVersion, addCommunityPermissions.booleanValue(),
				addGuestPermissions.booleanValue());
		}
		else {
			addFrameworkVersionResources(
				frameworkVersion, communityPermissions, guestPermissions);
		}

		return frameworkVersion;
	}

	public void addFrameworkVersionResources(
			long frameworkVersionId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		SRFrameworkVersion frameworkVersion =
			SRFrameworkVersionUtil.findByPrimaryKey(frameworkVersionId);

		addFrameworkVersionResources(
			frameworkVersion, addCommunityPermissions, addGuestPermissions);
	}

	public void addFrameworkVersionResources(
			SRFrameworkVersion frameworkVersion,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addResources(
			frameworkVersion.getCompanyId(), frameworkVersion.getGroupId(),
			frameworkVersion.getUserId(), SRFrameworkVersion.class.getName(),
			frameworkVersion.getPrimaryKey(), false, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addFrameworkVersionResources(
			long frameworkVersionId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		SRFrameworkVersion frameworkVersion =
			SRFrameworkVersionUtil.findByPrimaryKey(frameworkVersionId);

		addFrameworkVersionResources(
			frameworkVersion, communityPermissions, guestPermissions);
	}

	public void addFrameworkVersionResources(
			SRFrameworkVersion frameworkVersion, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addModelResources(
			frameworkVersion.getCompanyId(), frameworkVersion.getGroupId(),
			frameworkVersion.getUserId(), SRFrameworkVersion.class.getName(),
			frameworkVersion.getPrimaryKey(), communityPermissions,
			guestPermissions);
	}

	public void deleteFrameworkVersion(long frameworkVersionId)
		throws PortalException, SystemException {

		SRFrameworkVersionUtil.remove(frameworkVersionId);
	}

	public SRFrameworkVersion getFrameworkVersion(long frameworkVersionId)
		throws PortalException, SystemException {

		return SRFrameworkVersionUtil.findByPrimaryKey(frameworkVersionId);
	}

	public List getFrameworkVersions(long groupId, int begin, int end)
		throws SystemException {

		return SRFrameworkVersionUtil.findByGroupId(groupId, begin, end);
	}

	public List getFrameworkVersions(
			long groupId, boolean active, int begin, int end)
		throws SystemException {

		return SRFrameworkVersionUtil.findByG_A(groupId, active, begin, end);
	}

	public int getFrameworkVersionsCount(long groupId)
		throws SystemException {

		return SRFrameworkVersionUtil.countByGroupId(groupId);
	}

	public int getFrameworkVersionsCount(long groupId, boolean active)
		throws SystemException {

		return SRFrameworkVersionUtil.countByG_A(groupId, active);
	}

	public List getProductVersionFrameworkVersions(long productVersionId)
		throws PortalException, SystemException {

		return SRProductVersionUtil.getSRFrameworkVersions(productVersionId);
	}

	public SRFrameworkVersion updateFrameworkVersion(
			long frameworkVersionId, String name, String url, boolean active,
			int priority)
		throws PortalException, SystemException {

		SRFrameworkVersion frameworkVersion =
			SRFrameworkVersionUtil.findByPrimaryKey(frameworkVersionId);

		frameworkVersion.setName(name);
		frameworkVersion.setUrl(url);
		frameworkVersion.setActive(active);
		frameworkVersion.setPriority(priority);

		SRFrameworkVersionUtil.update(frameworkVersion);

		return frameworkVersion;
	}

}