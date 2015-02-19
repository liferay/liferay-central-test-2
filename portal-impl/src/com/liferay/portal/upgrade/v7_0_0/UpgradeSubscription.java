/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v7_0_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.model.Layout;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntryType;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructure;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalFolder;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.shopping.model.ShoppingOrder;
import com.liferay.portlet.softwarecatalog.model.SCProductEntry;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Eduardo Garcia
 * @author Roberto Díaz
 * @author Iván Zaera
 */
public class UpgradeSubscription extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateSubscriptionClassNames(
			Folder.class.getName(), DLFolder.class.getName());
		updateSubscriptionClassNames(
			JournalArticle.class.getName(), JournalFolder.class.getName());

		updateSubscriptionGroupIds();
	}

	protected long getGroupId(long classNameId, long classPK) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();
			
			String className = PortalUtil.getClassName(classNameId);

			String sql = _getGroupIdSQLs.get(className);

			ps = con.prepareStatement(sql);

			ps.setLong(1, classPK);

			rs = ps.executeQuery();

			if (rs.next()) {
				return rs.getLong("groupId");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return 0;
	}

	protected boolean hasGroup(long groupId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select count(*) from Group_ where groupId = ?");

			ps.setLong(1, groupId);

			rs = ps.executeQuery();

			if (rs.next()) {
				int count = rs.getInt(1);

				if (count > 0) {
					return true;
				}
			}

			return false;
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateSubscriptionClassNames(
			String oldClassName, String newClassName)
		throws Exception {

		StringBundler sb = new StringBundler(4);

		sb.append("update Subscription set classNameId = ");
		sb.append(PortalUtil.getClassNameId(newClassName));
		sb.append(" where classNameId = ");
		sb.append(PortalUtil.getClassNameId(oldClassName));

		runSQL(sb.toString());
	}

	protected void updateSubscriptionGroupId(
			long subscriptionId, long classNameId, long classPK)
		throws Exception {

		long groupId = getGroupId(classNameId, classPK);

		if ((groupId == 0) && hasGroup(classPK)) {
			groupId = classPK;
		}

		if (groupId != 0) {
			runSQL(
				"update Subscription set groupId = " + groupId + " where " +
					"subscriptionId = " + subscriptionId);
		}
	}

	protected void updateSubscriptionGroupIds() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getUpgradeOptimizedConnection();

			ps = con.prepareStatement(
				"select subscriptionId, classNameId, classPK from " +
					"Subscription");

			rs = ps.executeQuery();

			while (rs.next()) {
				long subscriptionId = rs.getLong("subscriptionId");
				long classNameId = rs.getLong("classNameId");
				long classPK = rs.getLong("classPK");

				updateSubscriptionGroupId(subscriptionId, classNameId, classPK);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	private static final Map<String, String> _getGroupIdSQLs = new HashMap<>();

	static {
		_getGroupIdSQLs.put(
			BlogsEntry.class.getName(),
			"select groupId from BlogsEntry where entryId = ?");
		_getGroupIdSQLs.put(
			DDMStructure.class.getName(),
			"select groupId from DDMStructure where structureId = ?");
		_getGroupIdSQLs.put(
			DLFileEntry.class.getName(),
			"select groupId from DLFileEntry where fileEntryId = ?");
		_getGroupIdSQLs.put(
			DLFileEntryType.class.getName(),
			"select groupId from DLFileEntryType where fileEntryTypeId = ?");
		_getGroupIdSQLs.put(
			DLFolder.class.getName(),
			"select groupId from DLFolder where folderId = ?");
		_getGroupIdSQLs.put(
			JournalFolder.class.getName(),
			"select groupId from JournalFolder where folderId = ?");
		_getGroupIdSQLs.put(
			Layout.class.getName(),
			"select groupId from Layout where plid = ?");
		_getGroupIdSQLs.put(
			MBCategory.class.getName(),
			"select groupId from MBCategory where categoryId = ?");
		_getGroupIdSQLs.put(
			MBThread.class.getName(),
			"select groupId from MBThread where threadId = ?");
		_getGroupIdSQLs.put(
			SCProductEntry.class.getName(),
			"select groupId from SCProductEntry where productEntryId = ?");
		_getGroupIdSQLs.put(
			ShoppingOrder.class.getName(),
			"select groupId from ShoppingOrder where orderId = ?");
		_getGroupIdSQLs.put(
			WorkflowInstance.class.getName(),
			"select groupId from WorkflowInstance where workflowInstanceId = " +
				"?");
		_getGroupIdSQLs.put(
			"com.liferay.bookmarks.model.BookmarksEntry",
			"select groupId from BookmarksEntry where entryId = ?");
		_getGroupIdSQLs.put(
			"com.liferay.bookmarks.model.BookmarksFolder",
			"select groupId from BookmarksFolder where folderId = ?");
		_getGroupIdSQLs.put(
			"com.liferay.portlet.wiki.model.WikiNode",
			"select groupId from WikiNode where nodeId = ?");
		_getGroupIdSQLs.put(			
			"com.liferay.portlet.wiki.model.WikiPage",
			"select groupId from WikiPage where resourcePrimKey = ?");
	}

}