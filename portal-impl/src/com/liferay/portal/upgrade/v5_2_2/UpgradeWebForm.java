/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

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
		long oldClassNameId = getClassNameId(_OLD_WEBFORM_CLASS_NAME);

		if (oldClassNameId == 0) {
			return;
		}

		long newClassNameId = getClassNameId(_NEW_WEBFORM_CLASS_NAME);

		if (newClassNameId == 0) {
			runSQL(
				"update ClassName_ set value = '" +
					_NEW_WEBFORM_CLASS_NAME + "' where value = '" +
						_OLD_WEBFORM_CLASS_NAME + "'");
		}
		else {
			runSQL(
				"update ExpandoTable set classNameId = '" + newClassNameId +
					"' where classNameId = '" + oldClassNameId + "'");

			runSQL(
				"update ExpandoValue set classNameId = '" + newClassNameId +
					"' where classNameId = '" + oldClassNameId + "'");

			runSQL(
				"delete from ClassName_ where value = '" +
					_OLD_WEBFORM_CLASS_NAME + "'");
		}
	}

	protected long getClassNameId(String className) throws Exception {
		long classNameId = 0;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select classNameId from ClassName_ where value = ?");

			ps.setString(1, className);

			rs = ps.executeQuery();

			if (rs.next()) {
				classNameId = rs.getLong("classNameId");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return classNameId;
	}

	private static final String _NEW_WEBFORM_CLASS_NAME =
		"com.liferay.webform.util.WebFormUtil";

	private static final String _OLD_WEBFORM_CLASS_NAME =
		"com.liferay.portlet.webform.util.WebFormUtil";

}