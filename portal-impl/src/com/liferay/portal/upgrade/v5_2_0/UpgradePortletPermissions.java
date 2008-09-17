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
			updatePermissions(
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

	protected void updatePermissions(
			String portletName, String modelName, String[] actionIds)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			StringBuilder sb = new StringBuilder();

			sb.append("SELECT * FROM Permission_ P, Resource_ R, ");
			sb.append("ResourceCode RC WHERE (");

			for (int i = 0; i < actionIds.length; i++) {
				String actionId = actionIds[i];
				sb.append("actionId='");
				sb.append(actionId);
				sb.append("'");

				if (i < (actionIds.length - 1)) {
					sb.append(" OR ");
				}
			}

			sb.append(") AND P.resourceId=R.resourceId AND R.codeId=RC.codeId");
			sb.append(" AND RC.name=? and RC.scope=4");

			ps = con.prepareStatement(sb.toString());

			ps.setString(1, portletName);

			rs = ps.executeQuery();

			while (rs.next()) {
				long permissionId = rs.getLong("P.permissionId");
				String primKey = rs.getString("R.primKey");
				String actionId = rs.getString("P.actionId");
				long companyId = rs.getLong("RC.companyId");
				int scope = rs.getInt("RC.scope");

				try {
					long plid = GetterUtil.getLong(
						primKey.substring(0, primKey.indexOf("_LAYOUT_")));

					Layout layout = LayoutLocalServiceUtil.getLayout(plid);

					String resourcePrimKey = String.valueOf(
						layout.getGroupId());

					Resource resource = ResourceLocalServiceUtil.addResource(
						companyId, modelName, scope, resourcePrimKey);

					// Check if an equivalent permission already exists

					ps = con.prepareStatement(
						"select COUNT(*) as COUNT_VALUE FROM Permission_ P, " +
							"Resource_ R, ResourceCode RC WHERE actionId=? " +
								"and R.resourceId=P.resourceId AND " +
									"R.resourceId=? AND RC.codeId=R.codeId " +
										"and name=? and scope=4");

					ps.setString(1, actionId);
					ps.setLong(2, resource.getResourceId());
					ps.setString(3, modelName);

					ResultSet rs2 = ps.executeQuery();

					rs2.next();

					if (rs2.getInt("COUNT_VALUE") == 0) {
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

					rs2.close();
					ps.close();
				}
				catch (Exception e) {
					_log.error("Error upgrading permission " + permissionId, e);
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