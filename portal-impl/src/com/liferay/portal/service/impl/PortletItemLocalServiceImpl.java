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

package com.liferay.portal.service.impl;

import com.liferay.portal.NoSuchPortletItemException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.PortletItem;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.User;
import com.liferay.portal.service.base.PortletItemLocalServiceBaseImpl;
import com.liferay.portal.util.PortalUtil;

import java.util.Date;
import java.util.List;

/**
 * <a href="PortletItemLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class PortletItemLocalServiceImpl extends PortletItemLocalServiceBaseImpl {
	public PortletItem addPortletItem(
			long userId, long groupId, String portletId, String className,
			String name)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		long classNameId = PortalUtil.getClassNameId(className);
		Date now = new Date();

		long portletItemId = counterLocalService.increment();

		PortletItem portletItem = portletItemPersistence.create(
			portletItemId);

		portletItem.setCompanyId(user.getCompanyId());
		portletItem.setUserId(user.getUserId());
		portletItem.setUserName(user.getFullName());
		portletItem.setClassNameId(classNameId);
		portletItem.setGroupId(groupId);
		portletItem.setCreateDate(now);
		portletItem.setModifiedDate(now);
		portletItem.setPortletId(portletId);
		portletItem.setName(name);

		portletItemPersistence.update(portletItem);

		return portletItem;
	}

	public PortletItem getPortletItem(
			long portletItemId)
		throws PortalException, SystemException {
		return portletItemPersistence.findByPrimaryKey(portletItemId);
	}

	public PortletItem getPortletItem(
			long groupId, String portletId, String className, String name)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return portletItemPersistence.findByG_P_C_N(
			groupId, portletId, classNameId, name);
	}

	public List<PortletItem> getPortletItems(
			long groupId, String portletId, String className)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return portletItemPersistence.findByG_P_C(
			groupId, portletId, classNameId);
	}

	public PortletItem updatePortletItem(
			long userId, long groupId, String portletId, String className,
			String name)
		throws PortalException, SystemException {

		PortletItem portletItem = null;

		try {
			Date now = new Date();
			User user = userPersistence.findByPrimaryKey(userId);

			portletItem = getPortletItem(
				groupId, portletId, PortletPreferences.class.getName(),
				name);

			portletItem.setUserId(userId);
			portletItem.setUserName(user.getFullName());
			portletItem.setModifiedDate(now);

			portletItemPersistence.update(portletItem);

		}
		catch(NoSuchPortletItemException nsste) {
			portletItem = addPortletItem(
				userId, groupId, portletId, PortletPreferences.class.getName(),
				name);
		}

		return portletItem;
	}

}