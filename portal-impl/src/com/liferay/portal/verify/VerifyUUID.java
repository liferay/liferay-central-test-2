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

package com.liferay.portal.verify;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.MethodInvoker;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portlet.imagegallery.service.IGFolderLocalServiceUtil;
import com.liferay.portlet.imagegallery.service.IGImageLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalFeedLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalStructureLocalServiceUtil;
import com.liferay.portlet.journal.service.JournalTemplateLocalServiceUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * <a href="VerifyUUID.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class VerifyUUID extends VerifyProcess {

	protected void doVerify() throws Exception {
		for (String[] model : _MODELS) {
			verifyModel(model[0], model[1], model[2]);
		}
	}

	protected void verifyModel(
			String serviceClassName, String modelName, String pkColumnName)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select " + pkColumnName + " from " + modelName +
					" where uuid_ is null or uuid_ = ''");

			rs = ps.executeQuery();

			while (rs.next()) {
				long pk = rs.getLong(pkColumnName);

				verifyModel(serviceClassName, modelName, pk);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void verifyModel(
			String serviceClassName, String modelName, long pk)
		throws Exception {

		Object obj = MethodInvoker.invoke(
			new MethodWrapper(
				serviceClassName, "get" + modelName, new LongWrapper(pk)));

		MethodInvoker.invoke(
			new MethodWrapper(serviceClassName, "update" + modelName, obj));
	}

	private static final String[][] _MODELS = new String[][] {
		new String[] {
			IGFolderLocalServiceUtil.class.getName(),
			"IGFolder",
			"folderId"
		},
		new String[] {
			IGImageLocalServiceUtil.class.getName(),
			"IGImage",
			"imageId"
		},
		new String[] {
			JournalArticleLocalServiceUtil.class.getName(),
			"JournalArticle",
			"id_"
		},
		new String[] {
			JournalFeedLocalServiceUtil.class.getName(),
			"JournalFeed",
			"id_"
		},
		new String[] {
			JournalStructureLocalServiceUtil.class.getName(),
			"JournalStructure",
			"id_"
		},
		new String[] {
			JournalTemplateLocalServiceUtil.class.getName(),
			"JournalTemplate",
			"id_"
		}
	};

}