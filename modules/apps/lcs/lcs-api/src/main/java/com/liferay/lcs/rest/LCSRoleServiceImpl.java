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

package com.liferay.lcs.rest;

import com.liferay.jsonwebserviceclient.JSONWebServiceInvocationException;

import java.util.List;

/**
 * @author Igor Beslic
 */
public class LCSRoleServiceImpl
	extends BaseLCSServiceImpl implements LCSRoleService {

	@Override
	public boolean hasUserLCSAdministratorLCSRole(long lcsProjectId) {
		try {
			List<LCSRoleImpl> lcsRoles = doGetToList(
				LCSRoleImpl.class, _URL_LCS_ROLE, "lcsProjectId",
				String.valueOf(lcsProjectId), "lcsAdministrator", "true",
				"lcsEnvironmentManager", "false");

			if (lcsRoles.isEmpty()) {
				return false;
			}

			return true;
		}
		catch (JSONWebServiceInvocationException jsonwsie) {
			throw new RuntimeException(jsonwsie);
		}
	}

	private static final String _URL_LCS_ROLE =
		"/osb-lcs-portlet/lcs/jsonws/v1_4/LCSRole";

}