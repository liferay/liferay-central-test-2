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

package com.liferay.portal.verify;

import com.liferay.portal.NoSuchResourcePermissionException;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.model.Contact;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.ResourcePermission;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.service.ContactLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.service.ResourcePermissionLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.verify.model.resourced.VerifiableResourcedModel;
import com.liferay.registry.collections.ServiceTrackerCollections;
import com.liferay.registry.collections.ServiceTrackerList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;

/**
 * @author Raymond Augé
 * @author James Lefeu
 */
public class VerifyResourcePermissions extends VerifyProcess {

	@Override
	protected void doVerify() throws Exception {
		long[] companyIds = PortalInstances.getCompanyIdsBySQL();

		for (long companyId : companyIds) {
			Role role = RoleLocalServiceUtil.getRole(
				companyId, RoleConstants.OWNER);

			for (VerifiableResourcedModel verifiableResourcedModel :
					_verifiableResourcedModels) {

				verifyResourcedModel(
					role, verifiableResourcedModel.getModelName(),
					verifiableResourcedModel.getTableName(),
					verifiableResourcedModel.getPrimaryKeyColumnName());
			}

			verifyLayout(role);
		}
	}

	protected void verifyLayout(Role role) throws Exception {
		List<Layout> layouts = LayoutLocalServiceUtil.getNoPermissionLayouts(
			role.getRoleId());

		int total = layouts.size();

		for (int i = 0; i < total; i++) {
			Layout layout = layouts.get(i);

			verifyResourcedModel(
				role.getCompanyId(), Layout.class.getName(), layout.getPlid(),
				role, 0, i, total);
		}
	}

	protected void verifyResourcedModel(
			long companyId, String modelName, long primKey, Role role,
			long ownerId, int cur, int total)
		throws Exception {

		if (_log.isInfoEnabled() && ((cur % 100) == 0)) {
			_log.info(
				"Processed " + cur + " of " + total + " resource permissions " +
					"for company = " + companyId + " and model " + modelName);
		}

		ResourcePermission resourcePermission = null;

		try {
			resourcePermission =
				ResourcePermissionLocalServiceUtil.getResourcePermission(
					companyId, modelName, ResourceConstants.SCOPE_INDIVIDUAL,
					String.valueOf(primKey), role.getRoleId());
		}
		catch (NoSuchResourcePermissionException nsrpe) {
			if (_log.isDebugEnabled()) {
				_log.debug(
					"No resource found for {" + companyId + ", " + modelName +
						", " + ResourceConstants.SCOPE_INDIVIDUAL + ", " +
							primKey + ", " + role.getRoleId() + "}");
			}

			ResourceLocalServiceUtil.addResources(
				companyId, 0, ownerId, modelName, String.valueOf(primKey),
				false, false, false);
		}

		if (resourcePermission == null) {
			try {
				resourcePermission =
					ResourcePermissionLocalServiceUtil.getResourcePermission(
						companyId, modelName,
						ResourceConstants.SCOPE_INDIVIDUAL,
						String.valueOf(primKey), role.getRoleId());
			}
			catch (NoSuchResourcePermissionException nsrpe) {
				return;
			}
		}

		if (modelName.equals(User.class.getName())) {
			User user = UserLocalServiceUtil.fetchUserById(ownerId);

			if (user != null) {
				Contact contact = ContactLocalServiceUtil.fetchContact(
					user.getContactId());

				if (contact != null) {
					ownerId = contact.getUserId();
				}
			}
		}

		if (ownerId != resourcePermission.getOwnerId()) {
			resourcePermission.setOwnerId(ownerId);

			ResourcePermissionLocalServiceUtil.updateResourcePermission(
				resourcePermission);
		}
	}

	protected void verifyResourcedModel(
			Role role, String modelName, String tableName,
			String primaryKeyColumnName)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		int total = 0;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select count(*) from " + tableName + " where companyId = " +
					role.getCompanyId());

			rs = ps.executeQuery();

			if (rs.next()) {
				total = rs.getInt(1);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select " + primaryKeyColumnName + ", userId from " +
					tableName + " where companyId = " + role.getCompanyId());

			rs = ps.executeQuery();

			for (int i = 0; rs.next(); i++) {
				long primKey = rs.getLong(primaryKeyColumnName);
				long userId = rs.getLong("userId");

				verifyResourcedModel(
					role.getCompanyId(), modelName, primKey, role, userId, i,
					total);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		VerifyResourcePermissions.class);

	private ServiceTrackerList<VerifiableResourcedModel>
		_verifiableResourcedModels = ServiceTrackerCollections.list(
			VerifiableResourcedModel.class);

}