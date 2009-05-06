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

package com.liferay.portal.upgrade.v5_2_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.jdbc.SmartResultSet;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Permission;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PermissionLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * <a href="UpgradePortletPermissions.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 *
 */
public class UpgradePortletPermissions extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading portlet permissions");

		try {
			updatePortletPermissions(
				"33", "com.liferay.portlet.blogs",
				new String[] {"ADD_ENTRY"});

			updatePortletPermissions(
				"28", "com.liferay.portlet.bookmarks",
				new String[] {"ADD_FOLDER"});

			updatePortletPermissions(
				"8", "com.liferay.portlet.calendar",
				new String[] {"ADD_EVENT", "EXPORT_ALL_EVENTS"});

			updatePortletPermissions(
				"20", "com.liferay.portlet.documentlibrary",
				new String[] {"ADD_FOLDER"});

			updatePortletPermissions(
				"31", "com.liferay.portlet.imagegallery",
				new String[] {"ADD_FOLDER"});

			updatePortletPermissions(
				"15", "com.liferay.portlet.journal",
				new String[] {
					"ADD_ARTICLE", "ADD_FEED", "ADD_STRUCTURE", "ADD_TEMPLATE",
					"APPROVE_ARTICLE"
				});

			updatePortletPermissions(
				"19", "com.liferay.portlet.messageboards",
				new String[] {"ADD_CATEGORY", "BAN_USER"});

			updatePortletPermissions(
				"25", "com.liferay.portlet.polls",
				new String[] {"ADD_QUESTION"});

			updatePortletPermissions(
				"34", "com.liferay.portlet.shopping",
				new String[] {
					"ADD_CATEGORY", "MANAGE_COUPONS", "MANAGE_ORDERS"
				});

			updatePortletPermissions(
				"98", "com.liferay.portlet.softwarecatalog",
				new String[] {"ADD_FRAMEWORK_VERSION", "ADD_PRODUCT_ENTRY"});

			updatePortletPermissions(
				"99", "com.liferay.portlet.tags",
				new String[] {"ADD_ENTRY", "ADD_VOCABULARY"});

			updatePortletPermissions(
				"36", "com.liferay.portlet.wiki",
				new String[] {"ADD_NODE"});
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	protected long getPortletPermissionsCount(
			String actionId, long resourceId, String modelName)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBuilder sb = new StringBuilder();

			sb.append("select count(*) from Permission_ ");
			sb.append("inner join Resource_ on Resource_.resourceId = ");
			sb.append("Permission_.resourceId inner join ResourceCode on ");
			sb.append("ResourceCode.codeId = Resource_.codeId where ");
			sb.append("Permission_.actionId = ? and ");
			sb.append("Permission_.resourceId = ? and ResourceCode.name = ? ");
			sb.append("and ResourceCode.scope = ? ");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setString(1, actionId);
			ps.setLong(2, resourceId);
			ps.setString(3, modelName);
			ps.setInt(4, ResourceConstants.SCOPE_INDIVIDUAL);

			rs = ps.executeQuery();

			rs.next();

			return rs.getLong(1);
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updatePortletPermission(
			long permissionId, String actionId, String primKey,
			String modelName, int scope)
		throws Exception {

		long plid = GetterUtil.getLong(
			primKey.substring(
				0, primKey.indexOf(PortletConstants.LAYOUT_SEPARATOR)));

		Layout layout = LayoutLocalServiceUtil.getLayout(plid);

		Resource resource = ResourceLocalServiceUtil.addResource(
			layout.getCompanyId(), modelName, scope,
			String.valueOf(layout.getGroupId()));

		long portletPermissionCount = getPortletPermissionsCount(
			actionId, resource.getResourceId(), modelName);

		if (portletPermissionCount == 0) {
			Permission permission = PermissionLocalServiceUtil.getPermission(
				permissionId);

			permission.setResourceId(resource.getResourceId());

			PermissionLocalServiceUtil.updatePermission(permission);
		}
		else {
			runSQL(
				"delete from Permission_ where permissionId = " + permissionId);
		}
	}

	protected void updatePortletPermissions(
			String portletName, String modelName, String[] actionIds)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBuilder sb = new StringBuilder();

			sb.append("select Permission_.permissionId, ");
			sb.append("Permission_.actionId, Resource_.primKey, ");
			sb.append("ResourceCode.scope from Permission_ ");
			sb.append("inner join Resource_ on Resource_.resourceId = ");
			sb.append("Permission_.resourceId inner join ResourceCode on ");
			sb.append("ResourceCode.codeId = Resource_.codeId where (");

			for (int i = 0; i < actionIds.length; i++) {
				String actionId = actionIds[i];

				sb.append("Permission_.actionId = '");
				sb.append(actionId);
				sb.append("'");

				if (i < (actionIds.length - 1)) {
					sb.append(" or ");
				}
			}

			sb.append(") and ResourceCode.name = ? and ResourceCode.scope = ?");

			String sql = sb.toString();

			ps = con.prepareStatement(sql);

			ps.setString(1, portletName);
			ps.setInt(2, ResourceConstants.SCOPE_INDIVIDUAL);

			rs = ps.executeQuery();

			SmartResultSet srs = new SmartResultSet(rs);

			while (srs.next()) {
				long permissionId = srs.getLong("Permission_.permissionId");
				String actionId = srs.getString("Permission_.actionId");
				String primKey = srs.getString("Resource_.primKey");
				int scope = srs.getInt("ResourceCode.scope");

				try {
					updatePortletPermission(
						permissionId, actionId, primKey, modelName, scope);
				}
				catch (Exception e) {
					_log.error(
						"Unable to upgrade permission " + permissionId, e);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		UpgradePortletPermissions.class);

}