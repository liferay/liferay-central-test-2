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

package com.liferay.portal.upgrade.v5_2_2;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.upgrade.UpgradeProcess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * <a href="UpgradeWebForm.java.html"><b><i>View Source</i></b></a>
 *
 * @author Julio Camarero
 */
public class UpgradeWebForm extends UpgradeProcess {

	protected void doUpgrade() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		long oldClassNameId = 0;
		long newClassNameId = 0;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select classNameId from ClassName_ where value = ?");

			ps.setString(1, _OLD_WEBFORM_CLASS_NAME);

			rs = ps.executeQuery();

			if (!rs.next()) {
				ps.close();

				return;
			}

			oldClassNameId = rs.getLong("classNameId");

			ps = con.prepareStatement(
				"select classNameId from ClassName_ where value = ?");

			ps.setString(1, _NEW_WEBFORM_CLASS_NAME);

			rs = ps.executeQuery();

			if (!rs.next()){
				ps = con.prepareStatement(
					"update ClassName_ set value = ? where value = ?");

				ps.setString(1, _NEW_WEBFORM_CLASS_NAME);
				ps.setString(2, _OLD_WEBFORM_CLASS_NAME);

				ps.executeUpdate();

				ps.close();

				return;
			}

			newClassNameId = rs.getLong("classNameId");

			ps = con.prepareStatement(
				"update ExpandoTable set classNameId = ? where " +
					"classNameId = ?");

			ps.setLong(1, newClassNameId);
			ps.setLong(2, oldClassNameId);

			ps.executeUpdate();

			ps = con.prepareStatement(
				"update ExpandoValue set classNameId = ? where " +
					"classNameId = ?");

			ps.setLong(1, newClassNameId);
			ps.setLong(2, oldClassNameId);

			ps.executeUpdate();

			ps = con.prepareStatement(
				"delete from ClassName_ where value = ?");

			ps.setString(1, _OLD_WEBFORM_CLASS_NAME);

			ps.executeUpdate();

			ps.close();
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final String _NEW_WEBFORM_CLASS_NAME =
		"com.liferay.webform.util.WebFormUtil";

	private static final String _OLD_WEBFORM_CLASS_NAME =
		"com.liferay.portlet.webform.util.WebFormUtil";

	private static Log _log = LogFactoryUtil.getLog(UpgradeWebForm.class);

}