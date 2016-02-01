/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.screens.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextThreadLocal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.screens.service.base.ScreensUserServiceBaseImpl;

/**
 * @author José Manuel Navarro
 */
public class ScreensUserServiceImpl extends ScreensUserServiceBaseImpl {

	@Override
	public User getCurrentUser() throws PortalException {
		return getUser();
	}

	@Override
	public boolean sendPasswordByEmailAddress(
			long companyId, String emailAddress)
		throws PortalException {

		User user = userLocalService.getUserByEmailAddress(
			companyId, emailAddress);

		return sendPassword(user);
	}

	@Override
	public boolean sendPasswordByScreenName(long companyId, String screenName)
		throws PortalException {

		User user = userLocalService.getUserByScreenName(companyId, screenName);

		return sendPassword(user);
	}

	@Override
	public boolean sendPasswordByUserId(long userId) throws PortalException {
		User user = userLocalService.getUserById(userId);

		return sendPassword(user);
	}

	protected void populateServiceContext(
			long companyId, ServiceContext serviceContext)
		throws PortalException {

		if (serviceContext.getPathMain() == null) {
			serviceContext.setPathMain(PortalUtil.getPathMain());
		}

		if (serviceContext.getPlid() == 0) {
			long plid = layoutLocalService.getDefaultPlid(
				serviceContext.getScopeGroupId(), false);

			serviceContext.setPlid(plid);
		}

		if (serviceContext.getScopeGroupId() == 0) {
			Group guestGroup = groupLocalService.getGroup(
				companyId, GroupConstants.GUEST);

			serviceContext.setScopeGroupId(guestGroup.getGroupId());
		}
	}

	protected boolean sendPassword(User user) throws PortalException {
		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		populateServiceContext(user.getCompanyId(), serviceContext);

		userLocalService.sendPassword(
			user.getCompanyId(), user.getEmailAddress(), null, null, null, null,
			serviceContext);

		Company company = companyPersistence.findByPrimaryKey(
			user.getCompanyId());

		return company.isSendPassword();
	}

}