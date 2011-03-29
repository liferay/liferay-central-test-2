/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.verify;

import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.PermissionLocalServiceUtil;
import com.liferay.portal.service.ResourceActionLocalServiceUtil;
import com.liferay.portal.util.PropsValues;

import java.util.List;

/**
 * @author Tobias Kaefer
 * @author Douglas Wong
 */
public class VerifyPermission extends VerifyProcess {

	protected void doVerify() throws Exception {
		if ((PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM != 5) &&
			(PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM != 6)) {

			return;
		}

		List<String> modelNames = ResourceActionsUtil.getModelNames();

		for (String modelName : modelNames) {
			List<String> actionIds =
				ResourceActionsUtil.getModelResourceActions(modelName);

			if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 5) {
				PermissionLocalServiceUtil.checkPermissions(
					modelName, actionIds);
			}
			else if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM == 6) {
				ResourceActionLocalServiceUtil.checkResourceActions(
					modelName, actionIds, true);
			}
		}
	}

}