/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Permission;
import com.liferay.portal.model.Resource;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PermissionLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
				"15", "com.liferay.portlet.journal",
				new String[]{
					"ADD_ARTICLE", "ADD_FEED", "ADD_STRUCTURE", "ADD_TEMPLATE",
					"APPROVE_ARTICLE"
				});
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	protected int getPortletPermissionsCount(
			String actionId, long resourceId, String modelName)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select COUNT(*) as COUNT_VALUE FROM Permission_ p, " +
					"Resource_ r, ResourceCode rc WHERE actionId = ? " +
						"and r.resourceId = p.resourceId AND " +
							"r.resourceId = ? AND rc.codeId = r.codeId " +
								"and name = ? and scope = 4");

			ps.setString(1, actionId);
			ps.setLong(2, resourceId);
			ps.setString(3, modelName);

			rs = ps.executeQuery();

			rs.next();

			return rs.getInt("COUNT_VALUE");
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
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

			sb.append("SELECT * FROM Permission_ p, Resource_ r, ");
			sb.append("ResourceCode rc WHERE (");

			for (int i = 0; i < actionIds.length; i++) {
				String actionId = actionIds[i];

				sb.append("actionId = '");
				sb.append(actionId);
				sb.append("'");

				if (i < (actionIds.length - 1)) {
					sb.append(" OR ");
				}
			}

			sb.append(") AND p.resourceId = r.resourceId AND ");
			sb.append("r.codeId = rc.codeId AND rc.name = ? and rc.scope = 4");

			ps = con.prepareStatement(sb.toString());

			ps.setString(1, portletName);

			rs = ps.executeQuery();

			while (rs.next()) {
				long permissionId = rs.getLong("p.permissionId");
				String primKey = rs.getString("r.primKey");
				String actionId = rs.getString("p.actionId");
				long companyId = rs.getLong("rc.companyId");
				int scope = rs.getInt("rc.scope");

				long plid = GetterUtil.getLong(
					primKey.substring(0, primKey.indexOf("_LAYOUT_")));

				Layout layout = LayoutLocalServiceUtil.getLayout(plid);

				String resourcePrimKey = String.valueOf(
					layout.getGroupId());

				Resource resource = ResourceLocalServiceUtil.addResource(
					companyId, modelName, scope, resourcePrimKey);

				int portletPermissionCount = getPortletPermissionsCount(
					actionId, resource.getResourceId(), modelName);

				if (portletPermissionCount == 0) {
					Permission permission =
						PermissionLocalServiceUtil.getPermission(
							permissionId);

					permission.setResourceId(resource.getResourceId());

					PermissionLocalServiceUtil.updatePermission(permission);
				}
				else {
					PermissionLocalServiceUtil.deletePermission(
						permissionId);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static Log _log = LogFactory.getLog(
		UpgradePortletPermissions.class);

}