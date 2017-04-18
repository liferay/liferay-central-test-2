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

package com.liferay.dynamic.data.mapping.form.evaluator.internal.functions;

import com.liferay.dynamic.data.mapping.expression.DDMExpressionFunction;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.RoleLocalService;
import com.liferay.portal.kernel.service.UserGroupRoleLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.PortalUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Leonardo Barros
 */
public class BelongsToRoleFunction implements DDMExpressionFunction {

	public BelongsToRoleFunction(
		HttpServletRequest request, long groupId, GroupLocalService groupLocalService, RoleLocalService roleLocalService, UserGroupRoleLocalService userGroupRoleLocalService, UserLocalService userLocalService) {

		_request = request;
		_groupId = groupId;
		_groupLocalService = groupLocalService;
		_roleLocalService = roleLocalService;
		_userGroupRoleLocalService = userGroupRoleLocalService;
		_userLocalService = userLocalService;
	}

	@Override
	public Object evaluate(Object... parameters) {
		if (parameters.length < 1) {
			throw new IllegalArgumentException(
				"At least one parameter is expected");
		}

		try {
			Company company = PortalUtil.getCompany(_request);
			User user = PortalUtil.getUser(_request);

			if (user == null) {
				return false;
			}

			for (Object parameter : parameters) {
				String roleName = String.valueOf(parameter);
				Role role = _roleLocalService.fetchRole(company.getCompanyId(), roleName);

				if (role == null) {
					return false;
				}

				boolean belongsTo = false;

				long userId = user.getUserId();

				if (role.getType() == RoleConstants.TYPE_REGULAR) {
					belongsTo = _userLocalService.hasRoleUser(
						company.getCompanyId(), roleName, userId, true);
				} else if (role.getType() == RoleConstants.TYPE_SITE){
					belongsTo = _userGroupRoleLocalService.hasUserGroupRole(userId, _groupId, roleName, true);
				} else if (role.getType() == RoleConstants.TYPE_ORGANIZATION) {
					long[] organizationIds = _groupLocalService.getOrganizationPrimaryKeys(_groupId);

					for(long organizationId : organizationIds) {
						Group group = _groupLocalService.getOrganizationGroup(company.getCompanyId(), organizationId);
						belongsTo = _userGroupRoleLocalService.hasUserGroupRole(userId, group.getGroupId(), roleName, true);

						if (belongsTo == true) {
							break;
						}
					}
				} else {
					return false;
				}

				if (belongsTo) {
					return true;
				}
			}
		}
		catch (PortalException pe) {
			if (_log.isDebugEnabled()) {
				_log.debug(pe);
			}
		}

		return false;
	}

	private static final Log _log = LogFactoryUtil.getLog(
		BelongsToRoleFunction.class);

	private long _groupId;
	private final HttpServletRequest _request;
	private final GroupLocalService _groupLocalService;
	private final RoleLocalService _roleLocalService;
	private final UserGroupRoleLocalService _userGroupRoleLocalService;
	private final UserLocalService _userLocalService;

}