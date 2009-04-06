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
import java.util.Collection;
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
 * algorithm to the new, role-based one.  Do not run this unless you want to do
 * this.
 * </p>
 *
 * @author Alexander Chow
 *
 */
public class ConvertPermissionAlgorithm extends ConvertProcess {

	public boolean isEnabled() throws ConvertException {
		boolean enabled = false;

		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM < 5) {
			enabled = true;
		}

		return enabled;
	}

	public String getDescription() {
		return "convert-legacy-permission-algorithm";
	}

	protected void doConvert() throws Exception {
		try {
			BatchSessionUtil.setEnabled(true);

			_initialize();

			// Users_Permissions

			_convertPermissions(
				RoleConstants.TYPE_REGULAR,
				"Users_Permissions",
				new String[] { "userId" },
				"Users_Roles",
				new Object[][] {
					{ "userId", Types.BIGINT },
					{ "roleId", Types.BIGINT }
				}
			);

			// Groups_Permissions

			_convertPermissions(
					RoleConstants.TYPE_COMMUNITY,
					"Groups_Permissions",
					new String[] { "groupId" },
					"Groups_Roles",
					new Object[][] {
						{ "groupId", Types.BIGINT },
						{ "roleId", Types.BIGINT }
					}
				);

			// OrgGroupPermission

			_convertPermissions(
					RoleConstants.TYPE_ORGANIZATION,
					"OrgGroupPermission",
					new String[] { "organizationId", "groupId" },
					"OrgGroupRole",
					new Object[][] {
						{ "organizationId", Types.BIGINT },
						{ "groupId", Types.BIGINT },
						{ "roleId", Types.BIGINT }
					}
				);

			// Cleanup

			PermissionCacheUtil.clearCache();

			_log.info(
				"Please set " + PropsKeys.PERMISSIONS_USER_CHECK_ALGORITHM +
					" in your portal-ext.properties to use algorithm 5");

			PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM = 5;
		}
		catch (Exception e) {
			_log.fatal(e);
		}
		finally {
			CacheRegistry.clear();

			BatchSessionUtil.setEnabled(false);
		}
	}

	private void _convertPermissions(
			int type, String legacyName, String[] primKeys, String newName,
			Object[][] newColumns)
		throws Exception {

		MaintenanceUtil.appendStatus("Processing " + legacyName);

		Table legacyTable = new PermissionView(legacyName, primKeys);

		String legacyFile = legacyTable.generateTempFile();

		if (legacyFile != null) {
			if (type == RoleConstants.TYPE_REGULAR) {
				legacyFile = _convertGuestUsers(legacyFile);

				MaintenanceUtil.appendStatus(
					"Converted guest users to guest roles");
			}

			_convertRoles(legacyFile, type, newName, newColumns);

			MaintenanceUtil.appendStatus("Converted roles for " + legacyName);

			// Cleanup

			DBUtil.getInstance().runSQL(legacyTable.getDeleteSQL());

			FileUtil.delete(legacyFile);
		}
	}

	private String _convertGuestUsers(String legacyFile) throws Exception {
		BufferedReader br = new BufferedReader(new FileReader(legacyFile));
		BufferedWriter bw1 =
			new BufferedWriter(new FileWriter(legacyFile + _UPDATED));
		BufferedWriter bw2 =
			new BufferedWriter(new FileWriter(legacyFile + _ROLE_PERM_MAP_EXT));

		try {
			String line = null;

			while (Validator.isNotNull(line = br.readLine())) {
				String[] values = StringUtil.split(line);

				long userId = PermissionView.getPrimaryKey(values);
				long permissionId = PermissionView.getPermissionId(values);
				long companyId = PermissionView.getCompanyId(values);
				int scope = PermissionView.getScopeId(values);

				if (scope == ResourceConstants.SCOPE_INDIVIDUAL &&
					_guestUsersSet.contains(userId)) {

					long roleId = _guestRolesMap.get(companyId).getRoleId();

					String key = roleId + "_" + permissionId;

					if (_rolesPermissions.contains(key)) {
						continue;
					}
					else {
						_rolesPermissions.add(key);
					}

					bw2.write(roleId + "," + permissionId + "\n");
				}
				else {
					bw1.write(line + "\n");
				}
			}
		}
		finally {
			br.close();
			bw1.close();
			bw2.close();
		}

		Table table = new Table(
				"Roles_Permissions",
				new Object[][] {
					{ "roleId", Types.BIGINT },
					{ "permissionId", Types.BIGINT }
				});

		table.populateTable(legacyFile + _ROLE_PERM_MAP_EXT);

		FileUtil.delete(legacyFile);
		FileUtil.delete(legacyFile + _ROLE_PERM_MAP_EXT);

		return legacyFile + _UPDATED;
	}

	private void _convertRoles(
			String legacyFile, int type, String newName,
			Object[][] newColumns)
		throws Exception {

		BufferedReader br = new BufferedReader(new FileReader(legacyFile));
		BufferedWriter rolePermMapBuff = new BufferedWriter(
			new FileWriter(legacyFile + _ROLE_PERM_MAP_EXT));
		BufferedWriter roleBuff = new BufferedWriter(
			new FileWriter(legacyFile + _ROLE_EXT));
		BufferedWriter otherRoleMapBuff = new BufferedWriter(
			new FileWriter(legacyFile + _OTHER_ROLE_MAP_EXT));

		try {

			// Group by resourceId

			MultiValueMap mvp = new MultiValueMap();

			String line;

			while ((line = br.readLine()) != null) {
				String[] values = StringUtil.split(line);

				long resourceId = PermissionView.getResourceId(values);

				mvp.put(resourceId, values);
			}

			// Assign role for each grouping

			for (Long key : (Set<Long>)mvp.keySet()) {
				List<String[]> valuesList = new ArrayList<String[]>(
					(Collection<String[]>)mvp.getCollection(key));

				String[] values = valuesList.get(0);

				long groupId = PermissionView.getPrimaryKey(values);
				long companyId = PermissionView.getCompanyId(values);
				String name = PermissionView.getNameId(values);
				int scope = PermissionView.getScopeId(values);

				// Group actions and permissionIds

				List<String> actionsIds = new ArrayList<String>();
				List<Long> permissionIds = new ArrayList<Long>();

				for (String[] values2 : valuesList) {
					actionsIds.add(PermissionView.getActionId(values2));
					permissionIds.add(PermissionView.getPermissionId(values2));
				}

				// Look for owner and system roles

				if (type != RoleConstants.TYPE_ORGANIZATION &&
					scope == ResourceConstants.SCOPE_INDIVIDUAL) {

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
							Role[] defaultRoles =
								_defaultRolesMap.get(companyId);

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
							String key2 = roleId + "_" + permissionId;

							if (_rolesPermissions.contains(key2)) {
								continue;
							}
							else {
								_rolesPermissions.add(key2);
							}

							rolePermMapBuff.write(
								roleId + "," + permissionId + ",\n");
						}

						continue;
					}
				}

				// Role_

				long roleId = CounterLocalServiceUtil.increment();
				String roleName =
					StringUtil.upperCaseFirstLetter(RoleConstants.getTypeLabel(type)) +
						" " + Long.toHexString(roleId);

				String[] roleColumns = new String[] {
					Long.toString(roleId),
					Long.toString(companyId),
					Long.toString(ClassNameLocalServiceUtil.getClassNameId(Role.class)),
					Long.toString(roleId),
					roleName,
					StringPool.BLANK,
					"Autogenerated role from portal upgrade",
					Integer.toString(type),
					StringPool.BLANK
				};

				for (int i = 0; i < roleColumns.length; i++) {
					roleBuff.write(roleColumns[i] + StringPool.COMMA);

					if (i == (roleColumns.length - 1)) {
						roleBuff.write(StringPool.NEW_LINE);
					}
				}

				// Roles_Permissions

				for (Long permissionId : permissionIds) {
					String key2 = roleId + "_" + permissionId;

					if (_rolesPermissions.contains(key2)) {
						continue;
					}
					else {
						_rolesPermissions.add(key2);
					}

					rolePermMapBuff.write(
						roleId + "," + permissionId + ",\n");
				}

				// Others_Roles

				for (int i = 0; i < newColumns.length - 1; i++) {
					otherRoleMapBuff.write(values[i] + StringPool.COMMA);
				}

				otherRoleMapBuff.write(roleId + ",\n");
			}
		}
		finally {
			br.close();
			roleBuff.close();
			rolePermMapBuff.close();
			otherRoleMapBuff.close();
		}

		// Role_

		Table roleTable =
			new Table(RoleModelImpl.TABLE_NAME, RoleModelImpl.TABLE_COLUMNS);

		roleTable.populateTable(legacyFile + _ROLE_EXT);

		// Roles_Permissions

		Table rolesPermissionsTable = new Table(
			"Roles_Permissions",
			new Object[][] {
				{ "roleId", Types.BIGINT },
				{ "permissionId", Types.BIGINT }
			});

		rolesPermissionsTable.populateTable(legacyFile + _ROLE_PERM_MAP_EXT);

		// Others_Roles

		Table othersRolesTable = new Table(newName, newColumns);

		othersRolesTable.populateTable(legacyFile + _OTHER_ROLE_MAP_EXT);

		// Cleanup

		FileUtil.delete(legacyFile + _ROLE_EXT);
		FileUtil.delete(legacyFile + _ROLE_PERM_MAP_EXT);
		FileUtil.delete(legacyFile + _OTHER_ROLE_MAP_EXT);
	}

	private void _initialize() throws Exception {

		// System Roles and Users

		long[] companyIds = PortalInstances.getCompanyIds();

		_defaultRolesMap = new HashMap<Long, Role[]>();
		_ownerRolesMap = new HashMap<Long, Role>();
		_guestRolesMap = new HashMap<Long, Role>();
		_guestUsersSet = new HashSet<Long>();
		_rolesPermissions = new HashSet<String>();

		for (long companyId : companyIds) {
			_defaultRolesMap.put(
				companyId, new Role[] {
					RoleLocalServiceUtil.getRole(
							companyId, RoleConstants.COMMUNITY_MEMBER),
					RoleLocalServiceUtil.getRole(
						companyId, RoleConstants.ORGANIZATION_MEMBER),
					RoleLocalServiceUtil.getRole(
						companyId, RoleConstants.POWER_USER),
					}
				);

			Role ownerRole = RoleLocalServiceUtil.getRole(
				companyId, RoleConstants.OWNER);

			_ownerRolesMap.put(companyId, ownerRole);

			Role guestRole = RoleLocalServiceUtil.getRole(
				companyId, RoleConstants.GUEST);

			_guestRolesMap.put(companyId, guestRole);

			_guestUsersSet.add(
				UserLocalServiceUtil.getDefaultUserId(companyId));
		}

		// Roles_Permissions for GUEST

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement("SELECT * FROM Roles_Permissions ");

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

		_groupsMap = new HashMap<Long, Group>(groups.size());

		for (Group group : groups) {
			_groupsMap.put(group.getGroupId(), group);
		}

		// Cache ResourceActions for unknown portlets

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

	private Map<Long, Role[]> _defaultRolesMap;

	private Map<Long, Role> _ownerRolesMap;

	private Map<Long, Group> _groupsMap;

	private Map<Long, Role> _guestRolesMap;

	private Set<Long> _guestUsersSet;

	private Set<String> _rolesPermissions;

	private static final String _UPDATED = ".updated";

	private static final String _ROLE_EXT = ".role";

	private static final String _ROLE_PERM_MAP_EXT = ".roles_permissions";

	private static final String _OTHER_ROLE_MAP_EXT = ".others_roles";

	private static final Log _log =
		LogFactoryUtil.getLog(ConvertPermissionAlgorithm.class);

}