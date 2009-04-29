/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.convert;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.convert.util.PermissionView;
import com.liferay.portal.kernel.cache.CacheRegistry;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.ResourceCode;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.RoleConstants;
import com.liferay.portal.model.impl.RoleModelImpl;
import com.liferay.portal.security.permission.PermissionCacheUtil;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.service.ClassNameLocalServiceUtil;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.ResourceCodeLocalServiceUtil;
import com.liferay.portal.service.RoleLocalServiceUtil;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.tools.sql.DBUtil;
import com.liferay.portal.upgrade.util.Table;
import com.liferay.portal.util.MaintenanceUtil;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsKeys;
import com.liferay.portal.util.PropsValues;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.map.MultiValueMap;

/**
 * <a href="ConvertPermissionAlgorithm.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * This class converts all existing permissions from the legacy permissions
 * algorithm to the latest algorithm.
 * </p>
 *
 * @author Alexander Chow
 *
 */
public class ConvertPermissionAlgorithm extends ConvertProcess {

	public String getDescription() {
		return "convert-legacy-permission-algorithm";
	}

	public boolean isEnabled() {
		boolean enabled = false;

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM < 5) {
			enabled = true;
		}

		return enabled;
	}

	protected void doConvert() throws Exception {
		try {
			BatchSessionUtil.setEnabled(true);

			_convert();
		}
		catch (Exception e) {
			_log.fatal(e, e);
		}
		finally {
			CacheRegistry.clear();

			BatchSessionUtil.setEnabled(false);
		}
	}

	private void _convert() throws Exception {

		// Initialize

		_initialize();

		// Groups_Permissions

		_convertPermissions(
			RoleConstants.TYPE_COMMUNITY, "Groups_Permissions",
			new String[] {"groupId"}, "Groups_Roles",
			new Object[][] {
				{"groupId", Types.BIGINT}, {"roleId", Types.BIGINT}
			});

		// OrgGroupPermission

		_convertPermissions(
			RoleConstants.TYPE_ORGANIZATION, "OrgGroupPermission",
			new String[] {"organizationId", "groupId"}, "OrgGroupRole",
			new Object[][] {
				{"organizationId", Types.BIGINT}, {"groupId", Types.BIGINT},
				{"roleId", Types.BIGINT}
			});

		// Users_Permissions

		_convertPermissions(
			RoleConstants.TYPE_REGULAR, "Users_Permissions",
			new String[] {"userId"}, "Users_Roles",
			new Object[][] {
				{"userId", Types.BIGINT}, {"roleId", Types.BIGINT}
			});

		// Cleanup

		PermissionCacheUtil.clearCache();

		PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM = 5;

		MaintenanceUtil.appendStatus(
			"Please set " + PropsKeys.PERMISSIONS_USER_CHECK_ALGORITHM +
				" in your portal-ext.properties to use algorithm 5");
	}

	private String _convertGuestUsers(String legacyFile) throws Exception {
		BufferedReader legacyFileReader = new BufferedReader(
			new FileReader(legacyFile));

		BufferedWriter legacyFileUpdatedWriter = new BufferedWriter(
			new FileWriter(legacyFile + _UPDATED));
		BufferedWriter legacyFileExtRolesPermissionsWriter = new BufferedWriter(
			new FileWriter(legacyFile + _EXT_ROLES_PERMIMISSIONS));

		try {
			String line = null;

			while (Validator.isNotNull(line = legacyFileReader.readLine())) {
				String[] values = StringUtil.split(line);

				long companyId = PermissionView.getCompanyId(values);
				long permissionId = PermissionView.getPermissionId(values);
				int scope = PermissionView.getScopeId(values);
				long userId = PermissionView.getPrimaryKey(values);

				if ((scope == ResourceConstants.SCOPE_INDIVIDUAL) &&
					(_guestUsersSet.contains(userId))) {

					long roleId = _guestRolesMap.get(companyId).getRoleId();

					String key = roleId + "_" + permissionId;

					if (_rolesPermissions.contains(key)) {
						continue;
					}
					else {
						_rolesPermissions.add(key);
					}

					legacyFileExtRolesPermissionsWriter.write(
						roleId + "," + permissionId + "\n");
				}
				else {
					legacyFileUpdatedWriter.write(line + "\n");
				}
			}
		}
		finally {
			legacyFileReader.close();

			legacyFileUpdatedWriter.close();
			legacyFileExtRolesPermissionsWriter.close();
		}

		Table table = new Table(
			"Roles_Permissions",
			new Object[][] {
				{"roleId", Types.BIGINT}, {"permissionId", Types.BIGINT}
			});

		table.populateTable(legacyFile + _EXT_ROLES_PERMIMISSIONS);

		FileUtil.delete(legacyFile);
		FileUtil.delete(legacyFile + _EXT_ROLES_PERMIMISSIONS);

		return legacyFile + _UPDATED;
	}

	private void _convertPermissions(
			int type, String legacyName, String[] primKeys, String newName,
			Object[][] newColumns)
		throws Exception {

		MaintenanceUtil.appendStatus("Processing " + legacyName);

		Table legacyTable = new PermissionView(legacyName, primKeys);

		String legacyFile = legacyTable.generateTempFile();

		if (legacyFile == null) {
			return;
		}

		if (type == RoleConstants.TYPE_REGULAR) {
			legacyFile = _convertGuestUsers(legacyFile);

			MaintenanceUtil.appendStatus(
				"Converted guest users to guest roles");
		}

		_convertRoles(legacyFile, type, newName, newColumns);

		MaintenanceUtil.appendStatus("Converted roles for " + legacyName);

		DBUtil.getInstance().runSQL(legacyTable.getDeleteSQL());

		FileUtil.delete(legacyFile);
	}

	private void _convertRoles(
			String legacyFile, int type, String newName, Object[][] newColumns)
		throws Exception {

		BufferedReader legacyFileReader = new BufferedReader(
			new FileReader(legacyFile));

		BufferedWriter legacyFileExtRoleWriter = new BufferedWriter(
			new FileWriter(legacyFile + _EXT_ROLE));
		BufferedWriter legacyFileExtRolesPermissionsWriter = new BufferedWriter(
			new FileWriter(legacyFile + _EXT_ROLES_PERMIMISSIONS));
		BufferedWriter legacyFileExtOtherRolesWriter = new BufferedWriter(
			new FileWriter(legacyFile + _EXT_OTHER_ROLES));

		try {

			// Group by resource id

			MultiValueMap mvp = new MultiValueMap();

			String line = null;

			while ((line = legacyFileReader.readLine()) != null) {
				String[] values = StringUtil.split(line);

				long resourceId = PermissionView.getResourceId(values);

				mvp.put(resourceId, values);
			}

			// Assign role for each grouping

			for (Long key : (Set<Long>)mvp.keySet()) {
				List<String[]> valuesList = new ArrayList<String[]>(
					mvp.getCollection(key));

				String[] values = valuesList.get(0);

				long companyId = PermissionView.getCompanyId(values);
				long groupId = PermissionView.getPrimaryKey(values);
				String name = PermissionView.getNameId(values);
				int scope = PermissionView.getScopeId(values);

				// Group action ids and permission ids

				List<String> actionsIds = new ArrayList<String>();
				List<Long> permissionIds = new ArrayList<Long>();

				for (String[] curValues : valuesList) {
					String actionId = PermissionView.getActionId(curValues);
					long permissionId = PermissionView.getPermissionId(
						curValues);

					actionsIds.add(actionId);
					permissionIds.add(permissionId);
				}

				// Look for owner and system roles

				if ((type != RoleConstants.TYPE_ORGANIZATION) &&
					(scope == ResourceConstants.SCOPE_INDIVIDUAL)) {

					// Find default actions

					List<String> defaultActions = null;

					if (type == RoleConstants.TYPE_REGULAR) {
						if (name.contains(StringPool.PERIOD)) {
							defaultActions = ResourceActionsUtil.
								getModelResourceActions(name);
						}
						else {
							defaultActions = ResourceActionsUtil.
								getPortletResourceActions(name);
						}
					}
					else {
						if (name.contains(StringPool.PERIOD)) {
							defaultActions = ResourceActionsUtil.
								getModelResourceCommunityDefaultActions(name);
						}
						else {
							defaultActions = ResourceActionsUtil.
								getPortletResourceCommunityDefaultActions(name);
						}
					}

					// Resolve owner and system roles

					Role defaultRole = null;

					if (type == RoleConstants.TYPE_REGULAR) {
						Collections.sort(actionsIds);
						Collections.sort(defaultActions);

						if (defaultActions.equals(actionsIds)) {
							defaultRole = _ownerRolesMap.get(companyId);
						}
					}
					else {
						if (defaultActions.containsAll(actionsIds)) {
							Role[] defaultRoles = _defaultRolesMap.get(
								companyId);

							Group group = _groupsMap.get(groupId);

							if (group.isCommunity()) {
								defaultRole = defaultRoles[0];
							}
							else if (group.isOrganization()) {
								defaultRole = defaultRoles[1];
							}
							else if (group.isUser() || group.isUserGroup()) {
								defaultRole = defaultRoles[2];
							}
						}
					}

					if (defaultRole != null) {
						long roleId = defaultRole.getRoleId();

						for (Long permissionId : permissionIds) {
							String curKey = roleId + "_" + permissionId;

							if (_rolesPermissions.contains(curKey)) {
								continue;
							}
							else {
								_rolesPermissions.add(curKey);
							}

							legacyFileExtRolesPermissionsWriter.write(
								roleId + "," + permissionId + ",\n");
						}

						continue;
					}
				}

				// Role_

				long roleId = CounterLocalServiceUtil.increment();

				String roleName = StringUtil.upperCaseFirstLetter(
					RoleConstants.getTypeLabel(type));

				roleName += " " + Long.toHexString(roleId);

				String[] roleColumns = new String[] {
					String.valueOf(roleId), String.valueOf(companyId),
					String.valueOf(
						ClassNameLocalServiceUtil.getClassNameId(Role.class)),
					String.valueOf(roleId), roleName, StringPool.BLANK,
					"Autogenerated role from portal upgrade",
					String.valueOf(type), "lfr-permission-algorithm-5"
				};

				for (int i = 0; i < roleColumns.length; i++) {
					legacyFileExtRoleWriter.write(
						roleColumns[i] + StringPool.COMMA);

					if (i == (roleColumns.length - 1)) {
						legacyFileExtRoleWriter.write(StringPool.NEW_LINE);
					}
				}

				// Roles_Permissions

				for (Long permissionId : permissionIds) {
					String curKey = roleId + "_" + permissionId;

					if (_rolesPermissions.contains(curKey)) {
						continue;
					}
					else {
						_rolesPermissions.add(curKey);
					}

					legacyFileExtRolesPermissionsWriter.write(
						roleId + "," + permissionId + ",\n");
				}

				// Others_Roles

				for (int i = 0; i < newColumns.length - 1; i++) {
					legacyFileExtOtherRolesWriter.write(
						values[i] + StringPool.COMMA);
				}

				legacyFileExtOtherRolesWriter.write(roleId + ",\n");
			}
		}
		finally {
			legacyFileReader.close();

			legacyFileExtRoleWriter.close();
			legacyFileExtRolesPermissionsWriter.close();
			legacyFileExtOtherRolesWriter.close();
		}

		// Role_

		Table roleTable = new Table(
			RoleModelImpl.TABLE_NAME, RoleModelImpl.TABLE_COLUMNS);

		roleTable.populateTable(legacyFile + _EXT_ROLE);

		// Roles_Permissions

		Table rolesPermissionsTable = new Table(
			"Roles_Permissions",
			new Object[][] {
				{"roleId", Types.BIGINT}, {"permissionId", Types.BIGINT}
			});

		rolesPermissionsTable.populateTable(
			legacyFile + _EXT_ROLES_PERMIMISSIONS);

		// Others_Roles

		Table othersRolesTable = new Table(newName, newColumns);

		othersRolesTable.populateTable(legacyFile + _EXT_OTHER_ROLES);

		// Cleanup

		FileUtil.delete(legacyFile + _EXT_ROLE);
		FileUtil.delete(legacyFile + _EXT_ROLES_PERMIMISSIONS);
		FileUtil.delete(legacyFile + _EXT_OTHER_ROLES);
	}

	private void _initialize() throws Exception {

		// System roles and default users

		long[] companyIds = PortalInstances.getCompanyIds();

		for (long companyId : companyIds) {
			_defaultRolesMap.put(
				companyId,
				new Role[] {
						RoleLocalServiceUtil.getRole(
							companyId, RoleConstants.COMMUNITY_MEMBER),
						RoleLocalServiceUtil.getRole(
							companyId, RoleConstants.ORGANIZATION_MEMBER),
						RoleLocalServiceUtil.getRole(
							companyId, RoleConstants.POWER_USER),
					}
				);

			Role guestRole = RoleLocalServiceUtil.getRole(
				companyId, RoleConstants.GUEST);

			_guestRolesMap.put(companyId, guestRole);

			Role ownerRole = RoleLocalServiceUtil.getRole(
				companyId, RoleConstants.OWNER);

			_ownerRolesMap.put(companyId, ownerRole);

			long defaultUserId = UserLocalServiceUtil.getDefaultUserId(
				companyId);

			_guestUsersSet.add(defaultUserId);
		}

		// Roles_Permissions

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement("SELECT * FROM Roles_Permissions");

			rs = ps.executeQuery();

			while (rs.next()) {
				long roleId = rs.getLong("roleId");
				long permissionId = rs.getLong("permissionId");

				_rolesPermissions.add(roleId + "_" + permissionId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		// Groups

		List<Group> groups = GroupLocalServiceUtil.getGroups(
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (Group group : groups) {
			_groupsMap.put(group.getGroupId(), group);
		}

		// Resource actions for unknown portlets

		List<ResourceCode> resourceCodes =
			ResourceCodeLocalServiceUtil.getResourceCodes(
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		for (ResourceCode resourceCode : resourceCodes) {
			String name = resourceCode.getName();

			if (!name.contains(StringPool.PERIOD)) {
				ResourceActionsUtil.getPortletResourceActions(name);
			}
		}
	}

	private static final String _EXT_OTHER_ROLES = ".others_roles";

	private static final String _EXT_ROLE = ".role";

	private static final String _EXT_ROLES_PERMIMISSIONS = ".roles_permissions";

	private static final String _UPDATED = ".updated";

	private static final Log _log =
		LogFactoryUtil.getLog(ConvertPermissionAlgorithm.class);

	private Map<Long, Role[]> _defaultRolesMap = new HashMap<Long, Role[]>();
	private Map<Long, Group> _groupsMap = new HashMap<Long, Group>();
	private Map<Long, Role> _guestRolesMap = new HashMap<Long, Role>();
	private Set<Long> _guestUsersSet = new HashSet<Long>();
	private Map<Long, Role> _ownerRolesMap = new HashMap<Long, Role>();
	private Set<String> _rolesPermissions = new HashSet<String>();

}