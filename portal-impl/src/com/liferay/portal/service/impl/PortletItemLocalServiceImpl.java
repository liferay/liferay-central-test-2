/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.PortletItemNameException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.Validator;
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
 */
public class PortletItemLocalServiceImpl
	extends PortletItemLocalServiceBaseImpl {

	public PortletItem addPortletItem(
			long userId, long groupId, String name, String portletId,
			String className)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		long classNameId = PortalUtil.getClassNameId(className);
		Date now = new Date();

		validate(name);

		long portletItemId = counterLocalService.increment();

		PortletItem portletItem = portletItemPersistence.create(
			portletItemId);

		portletItem.setGroupId(groupId);
		portletItem.setCompanyId(user.getCompanyId());
		portletItem.setUserId(user.getUserId());
		portletItem.setUserName(user.getFullName());
		portletItem.setCreateDate(now);
		portletItem.setModifiedDate(now);
		portletItem.setName(name);
		portletItem.setPortletId(portletId);
		portletItem.setClassNameId(classNameId);

		portletItemPersistence.update(portletItem, false);

		return portletItem;
	}

	public PortletItem getPortletItem(long portletItemId)
		throws PortalException, SystemException {

		return portletItemPersistence.findByPrimaryKey(portletItemId);
	}

	public PortletItem getPortletItem(
			long groupId, String name, String portletId, String className)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return portletItemPersistence.findByG_N_P_C(
			groupId, name, portletId, classNameId);
	}

	public List<PortletItem> getPortletItems(long groupId, String className)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return portletItemPersistence.findByG_C(groupId, classNameId);
	}

	public List<PortletItem> getPortletItems(
			long groupId, String portletId, String className)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return portletItemPersistence.findByG_P_C(
			groupId, portletId, classNameId);
	}

	public PortletItem updatePortletItem(
			long userId, long groupId, String name, String portletId,
			String className)
		throws PortalException, SystemException {

		PortletItem portletItem = null;

		try {
			User user = userPersistence.findByPrimaryKey(userId);

			portletItem = getPortletItem(
				groupId, name, portletId, PortletPreferences.class.getName());

			portletItem.setUserId(userId);
			portletItem.setUserName(user.getFullName());
			portletItem.setModifiedDate(new Date());

			portletItemPersistence.update(portletItem, false);
		}
		catch (NoSuchPortletItemException nsste) {
			portletItem = addPortletItem(
				userId, groupId, name, portletId,
				PortletPreferences.class.getName());
		}

		return portletItem;
	}

	protected void validate(String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new PortletItemNameException();
		}
	}

}