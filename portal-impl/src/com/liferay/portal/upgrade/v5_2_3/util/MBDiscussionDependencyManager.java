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

package com.liferay.portal.upgrade.v5_2_3.util;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * <a href="MBDiscussionDependencyManager.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 * @author Amos Fong
 */
public class MBDiscussionDependencyManager extends DependencyManager {

	public void update(
			long oldPrimaryKeyValue, Object[] oldColumnValues,
			Object[] oldExtraColumnValues, long newPrimaryKeyValue,
			Object[] newColumnValues, Object[] newExtraColumnValues)
		throws Exception {

		long threadId = 0;

		for (int i = 0; i < columns.length; i++) {
			if (columns[i][0].equals("threadId")) {
				threadId = (Long)newColumnValues[i];
			}
		}

		if ((threadId == 0) && (extraColumns != null)) {
			for (int i = 0; i < extraColumns.length; i++) {
				if (extraColumns[i][0].equals("threadId")) {
					threadId = (Long)newExtraColumnValues[i];
				}
			}
		}

		if (isDuplicateThread(threadId)) {
			deleteDuplicateData("MBMessage", "threadId", threadId);
			deleteDuplicateData("MBMessageFlag", "threadId", threadId);
			deleteDuplicateData("MBThread", "threadId", threadId);
		}
	}

	protected boolean isDuplicateThread(long threadId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select count(*) from MBDiscussion where threadId = ?");

			ps.setLong(1, threadId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long count = rs.getLong(1);

				if (count > 0) {
					return false;
				}
			}

			return true;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

}