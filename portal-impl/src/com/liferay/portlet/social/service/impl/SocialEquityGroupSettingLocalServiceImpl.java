/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.social.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Group;
import com.liferay.portlet.social.NoSuchEquityGroupSettingException;
import com.liferay.portlet.social.model.SocialEquityGroupSetting;
import com.liferay.portlet.social.service.base.SocialEquityGroupSettingLocalServiceBaseImpl;

/**
 * The implementation of the social equity group setting local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the {@link com.liferay.portlet.social.service.SocialEquityGroupSettingLocalService} interface.
 * </p>
 *
 * <p>
 * Never reference this interface directly. Always use {@link com.liferay.portlet.social.service.SocialEquityGroupSettingLocalServiceUtil} to access the social equity group setting local service.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Zsolt Berentey
 * @see com.liferay.portlet.social.service.base.SocialEquityGroupSettingLocalServiceBaseImpl
 * @see com.liferay.portlet.social.service.SocialEquityGroupSettingLocalServiceUtil
 */
public class SocialEquityGroupSettingLocalServiceImpl
	extends SocialEquityGroupSettingLocalServiceBaseImpl {

	public boolean isSocialEquityEnabled(
			long groupId, String className, int type)
		throws SystemException {

		try {
			SocialEquityGroupSetting socialEquityGroupSetting =
				socialEquityGroupSettingPersistence.findByG_C_T(
					groupId, className, type);

			return socialEquityGroupSetting.getEnabled();
		}
		catch (NoSuchEquityGroupSettingException e) {
			if (Group.class.getName().equals(className)) {
				return false;
			}

			return true;
		}
	}

	public void updateSetting(
			long groupId, String className, int type, boolean value)
		throws PortalException, SystemException {

		SocialEquityGroupSetting socialEquityGroupSetting = null;

		try {
			socialEquityGroupSetting =
				socialEquityGroupSettingPersistence.findByG_C_T(
					groupId, className, type);

			socialEquityGroupSetting.setEnabled(value);

		}
		catch (NoSuchEquityGroupSettingException e) {
			socialEquityGroupSetting = createSocialEquityGroupSetting(
				counterLocalService.increment());

			Group group = groupLocalService.getGroup(groupId);

			socialEquityGroupSetting.setCompanyId(group.getCompanyId());
			socialEquityGroupSetting.setGroupId(groupId);
			socialEquityGroupSetting.setClassName(className);
			socialEquityGroupSetting.setType(type);
			socialEquityGroupSetting.setEnabled(value);
		}

		socialEquityGroupSettingPersistence.update(
			socialEquityGroupSetting, false);
	}

}