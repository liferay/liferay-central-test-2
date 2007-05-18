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

package com.liferay.portlet.softwarecatalog.service.impl;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.softwarecatalog.FrameworkVersionNameException;
import com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion;
import com.liferay.portlet.softwarecatalog.service.base.SCFrameworkVersionLocalServiceBaseImpl;
import com.liferay.portlet.softwarecatalog.service.persistence.SCFrameworkVersionUtil;
import com.liferay.portlet.softwarecatalog.service.persistence.SCProductVersionUtil;
import com.liferay.util.Validator;

import java.util.Date;
import java.util.List;

/**
 * <a href="SCFrameworkVersionLocalServiceImpl.java.html"><b><i>View Source</i>
 * </b></a>
 *
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 *
 */
public class SCFrameworkVersionLocalServiceImpl
	extends SCFrameworkVersionLocalServiceBaseImpl {

	public SCFrameworkVersion addFrameworkVersion(
			long userId, long plid, String name, String url, boolean active,
			int priority, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		return addFrameworkVersion(
			userId, plid, name, url, active, priority,
			new Boolean(addCommunityPermissions),
			new Boolean(addGuestPermissions), null, null);
	}

	public SCFrameworkVersion addFrameworkVersion(
			long userId, long plid, String name, String url, boolean active,
			int priority, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		return addFrameworkVersion(
			userId, plid, name, url, active, priority, null, null,
			communityPermissions, guestPermissions);
	}

	public SCFrameworkVersion addFrameworkVersion(
			long userId, long plid, String name, String url, boolean active,
			int priority, Boolean addCommunityPermissions,
			Boolean addGuestPermissions, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		// Framework version

		User user = UserUtil.findByPrimaryKey(userId);
		long groupId = PortalUtil.getPortletGroupId(plid);
		Date now = new Date();

		validate(name);

		long frameworkVersionId = CounterLocalServiceUtil.increment();

		SCFrameworkVersion frameworkVersion = SCFrameworkVersionUtil.create(
			frameworkVersionId);

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

		SCFrameworkVersionUtil.update(frameworkVersion);

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

		SCFrameworkVersion frameworkVersion =
			SCFrameworkVersionUtil.findByPrimaryKey(frameworkVersionId);

		addFrameworkVersionResources(
			frameworkVersion, addCommunityPermissions, addGuestPermissions);
	}

	public void addFrameworkVersionResources(
			SCFrameworkVersion frameworkVersion,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addResources(
			frameworkVersion.getCompanyId(), frameworkVersion.getGroupId(),
			frameworkVersion.getUserId(), SCFrameworkVersion.class.getName(),
			frameworkVersion.getFrameworkVersionId(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addFrameworkVersionResources(
			long frameworkVersionId, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		SCFrameworkVersion frameworkVersion =
			SCFrameworkVersionUtil.findByPrimaryKey(frameworkVersionId);

		addFrameworkVersionResources(
			frameworkVersion, communityPermissions, guestPermissions);
	}

	public void addFrameworkVersionResources(
			SCFrameworkVersion frameworkVersion, String[] communityPermissions,
			String[] guestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addModelResources(
			frameworkVersion.getCompanyId(), frameworkVersion.getGroupId(),
			frameworkVersion.getUserId(), SCFrameworkVersion.class.getName(),
			frameworkVersion.getFrameworkVersionId(), communityPermissions,
			guestPermissions);
	}

	public void deleteFrameworkVersion(long frameworkVersionId)
		throws PortalException, SystemException {

		SCFrameworkVersionUtil.remove(frameworkVersionId);
	}

	public SCFrameworkVersion getFrameworkVersion(long frameworkVersionId)
		throws PortalException, SystemException {

		return SCFrameworkVersionUtil.findByPrimaryKey(frameworkVersionId);
	}

	public List getFrameworkVersions(long groupId, int begin, int end)
		throws SystemException {

		return SCFrameworkVersionUtil.findByGroupId(groupId, begin, end);
	}

	public List getFrameworkVersions(long groupId, boolean active)
		throws SystemException {

		return SCFrameworkVersionUtil.findByG_A(groupId, active);
	}

	public List getFrameworkVersions(
			long groupId, boolean active, int begin, int end)
		throws SystemException {

		return SCFrameworkVersionUtil.findByG_A(groupId, active, begin, end);
	}

	public int getFrameworkVersionsCount(long groupId)
		throws SystemException {

		return SCFrameworkVersionUtil.countByGroupId(groupId);
	}

	public int getFrameworkVersionsCount(long groupId, boolean active)
		throws SystemException {

		return SCFrameworkVersionUtil.countByG_A(groupId, active);
	}

	public List getProductVersionFrameworkVersions(long productVersionId)
		throws PortalException, SystemException {

		return SCProductVersionUtil.getSCFrameworkVersions(productVersionId);
	}

	public SCFrameworkVersion updateFrameworkVersion(
			long frameworkVersionId, String name, String url, boolean active,
			int priority)
		throws PortalException, SystemException {

		validate(name);

		SCFrameworkVersion frameworkVersion =
			SCFrameworkVersionUtil.findByPrimaryKey(frameworkVersionId);

		frameworkVersion.setName(name);
		frameworkVersion.setUrl(url);
		frameworkVersion.setActive(active);
		frameworkVersion.setPriority(priority);

		SCFrameworkVersionUtil.update(frameworkVersion);

		return frameworkVersion;
	}

	protected void validate(String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new FrameworkVersionNameException();
		}
	}

}