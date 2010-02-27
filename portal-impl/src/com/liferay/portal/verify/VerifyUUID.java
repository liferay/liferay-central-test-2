/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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