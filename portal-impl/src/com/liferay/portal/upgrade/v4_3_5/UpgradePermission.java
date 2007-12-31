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

package com.liferay.portal.upgrade.v4_3_5;

import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.impl.GroupImpl;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.spring.hibernate.HibernateUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;
import com.liferay.portlet.documentlibrary.model.DLFileShortcut;
import com.liferay.portlet.documentlibrary.model.DLFolder;
import com.liferay.portlet.imagegallery.model.IGFolder;
import com.liferay.portlet.imagegallery.model.IGImage;
import com.liferay.portlet.journal.model.JournalArticle;
import com.liferay.portlet.journal.model.JournalStructure;
import com.liferay.portlet.journal.model.JournalTemplate;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.shopping.model.ShoppingCategory;
import com.liferay.portlet.shopping.model.ShoppingItem;
import com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion;
import com.liferay.portlet.softwarecatalog.model.SCProductEntry;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.util.dao.DataAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradePermission.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class UpgradePermission extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			doUpgrade();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	protected void copyPermissions(long defaultUserId, long guestGroupId)
		throws Exception {

		if ((defaultUserId == 0) || (guestGroupId == 0)) {
			return;
		}

		runSQL("delete from Users_Permissions where userId = " + defaultUserId);

		runSQL(
			"insert into Users_Permissions (userId, permissionId) select " +
				defaultUserId + ", Groups_Permissions.permissionId from " +
					"Groups_Permissions where groupId = " + guestGroupId);

		deleteStalePortletPermissions(guestGroupId);

		deleteStalePermissions(
			Layout.class.getName(), "Layout", "plid", guestGroupId);

		deleteStalePermissions(
			BlogsEntry.class.getName(), "BlogsEntry", "entryId", guestGroupId);

		deleteStalePermissions(
			BookmarksFolder.class.getName(), "BookmarksFolder", "folderId",
			guestGroupId);
		deleteStalePermissions(
			BookmarksEntry.class.getName(), "BookmarksEntry", "entryId",
			"BookmarksFolder", "folderId", guestGroupId);

		deleteStalePermissions(
			CalEvent.class.getName(), "CalEvent", "eventId", guestGroupId);

		deleteStalePermissions(
			DLFolder.class.getName(), "DLFolder", "folderId", guestGroupId);
		deleteStalePermissions(
			DLFileEntry.class.getName(), "DLFileEntry", "fileEntryId",
			"DLFolder", "folderId", guestGroupId);
		deleteStalePermissions(
			DLFileShortcut.class.getName(), "DLFileShortcut", "fileShortcutId",
			"DLFolder", "folderId", guestGroupId);

		deleteStalePermissions(
			IGFolder.class.getName(), "IGFolder", "folderId", guestGroupId);
		deleteStalePermissions(
			IGImage.class.getName(), "IGImage", "imageId", "IGFolder",
			"folderId", guestGroupId);

		deleteStalePermissions(
			JournalArticle.class.getName(), "JournalArticle", "resourcePrimKey",
			guestGroupId);
		deleteStalePermissions(
			JournalStructure.class.getName(), "JournalStructure", "id_",
			guestGroupId);
		deleteStalePermissions(
			JournalTemplate.class.getName(), "JournalTemplate", "id_",
			guestGroupId);

		deleteStalePermissions(
			MBCategory.class.getName(), "MBCategory", "categoryId",
			guestGroupId);
		deleteStalePermissions(
			MBMessage.class.getName(), "MBMessage", "messageId", "MBCategory",
			"categoryId", guestGroupId);

		deleteStalePermissions(
			PollsQuestion.class.getName(), "PollsQuestion", "questionId",
			guestGroupId);

		deleteStalePermissions(
			SCFrameworkVersion.class.getName(), "SCFrameworkVersion",
			"frameworkVersionId", guestGroupId);
		deleteStalePermissions(
			SCProductEntry.class.getName(), "SCProductEntry", "productEntryId",
			guestGroupId);

		deleteStalePermissions(
			ShoppingCategory.class.getName(), "ShoppingCategory", "categoryId",
			guestGroupId);
		deleteStalePermissions(
			ShoppingItem.class.getName(), "ShoppingItem", "itemId",
			"ShoppingCategory", "categoryId", guestGroupId);

		deleteStalePermissions(
			WikiNode.class.getName(), "WikiNode", "nodeId", guestGroupId);
		deleteStalePermissions(
			WikiPage.class.getName(), "WikiPage", "resourcePrimKey", "WikiNode",
			"nodeId", guestGroupId);
	}

	protected void deleteStalePermissions(
			String className, String tableName, String tablePKCol,
			long guestGroupId)
		throws Exception {

		String sql = getStalePermissionsSQL(
			className, tableName, tablePKCol, guestGroupId);

		deleteStalePermissions(sql, guestGroupId);
	}

	protected void deleteStalePermissions(
			String className, String tableName1, String tablePKCol1,
			String tableName2, String tablePKCol2, long guestGroupId)
		throws Exception {

		String sql = getStalePermissionsSQL(
			className, tableName1, tablePKCol1, tableName2, tablePKCol2,
			guestGroupId);

		deleteStalePermissions(sql, guestGroupId);
	}

	protected void deleteStalePermissions(String sql, long guestGroupId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = HibernateUtil.getConnection();

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				long permissionId = rs.getLong("permissionId");

				runSQL(
					"delete from Groups_Permissions where groupId = " +
						guestGroupId + " and permissionId = " + permissionId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void deleteStalePortletPermissions(long guestGroupId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = HibernateUtil.getConnection();

			Iterator itr = getPlidsWithStalePermissions(
				guestGroupId).iterator();

			while (itr.hasNext()) {
				Long plid = (Long)itr.next();

				ps = con.prepareStatement(
					"select primKey from Resource_ where primKey like ?");

				ps.setString(1, plid + PortletImpl.LAYOUT_SEPARATOR + "%");

				rs = ps.executeQuery();

				while (rs.next()) {
					String primKey = rs.getString("primKey");

					String sql = getStalePortletPermissionsSQL(
						primKey, guestGroupId);

					deleteStalePermissions(sql, guestGroupId);
				}
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void doUpgrade() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = HibernateUtil.getConnection();

			ps = con.prepareStatement(_GET_COMPANY_IDS);

			rs = ps.executeQuery();

			while (rs.next()) {
				long companyId = rs.getLong("companyId");

				long defaultUserId = getDefaultUserId(companyId);
				long guestGroupId = getGuestGroupId(companyId);

				copyPermissions(defaultUserId, guestGroupId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected long getDefaultUserId(long companyId) throws Exception {
		long userId = 0;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = HibernateUtil.getConnection();

			ps = con.prepareStatement(_GET_DEFAULT_USER_ID);

			ps.setLong(1, companyId);
			ps.setBoolean(2, true);

			rs = ps.executeQuery();

			while (rs.next()) {
				userId = rs.getLong("userId");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return userId;
	}

	protected long getGuestGroupId(long companyId) throws Exception {
		long groupId = 0;

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = HibernateUtil.getConnection();

			ps = con.prepareStatement(_GET_GUEST_GROUP_ID);

			ps.setLong(1, companyId);
			ps.setString(2, GroupImpl.GUEST);

			rs = ps.executeQuery();

			while (rs.next()) {
				groupId = rs.getLong("groupId");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return groupId;
	}

	protected List getPlidsWithStalePermissions(long guestGroupId)
		throws Exception {

		List plids = new ArrayList();

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = HibernateUtil.getConnection();

			String sql = getStalePermissionsSQL(
				Layout.class.getName(), "Layout", "plid", guestGroupId);

			sql = StringUtil.replace(
				sql,
				"select Groups_Permissions.permissionId ",
				"select Layout.plid ");

			ps = con.prepareStatement(sql);

			rs = ps.executeQuery();

			while (rs.next()) {
				long plid = rs.getLong("plid");

				plids.add(new Long(plid));
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		return plids;
	}

	protected String getStalePermissionsSQL(
			String className, String tableName, String tablePKCol,
			long guestGroupId)
		throws Exception {

		String sql =
			"select Groups_Permissions.permissionId from Groups_Permissions " +
			"inner join Permission_ on Permission_.permissionId = " +
				"Groups_Permissions.permissionId " +
			"inner join Resource_ on Resource_.resourceId = " +
				"Permission_.resourceId " +
			"inner join ResourceCode on ResourceCode.codeId = " +
				"Resource_.codeId and ResourceCode.name = '" + className +
					"' " +
			"inner join " + tableName + " on " + tableName + "." + tablePKCol +
				" = Resource_.primKey " +
			"where Groups_Permissions.groupId = " + guestGroupId + " and " +
				tableName + ".groupId != " + guestGroupId;

		return sql;
	}

	protected String getStalePermissionsSQL(
			String className, String tableName1, String tablePKCol1,
			String tableName2, String tablePKCol2, long guestGroupId)
		throws Exception {

		String sql =
			"select Groups_Permissions.permissionId from Groups_Permissions " +
			"inner join Permission_ on Permission_.permissionId = " +
				"Groups_Permissions.permissionId " +
			"inner join Resource_ on Resource_.resourceId = " +
				"Permission_.resourceId " +
			"inner join ResourceCode on ResourceCode.codeId = " +
				"Resource_.codeId and ResourceCode.name = '" + className +
					"' " +
			"inner join " + tableName1 + " on " + tableName1 + "." +
				tablePKCol1 + " = Resource_.primKey " +
			"inner join " + tableName2 + " on " + tableName2 + "." +
				tablePKCol2 + " = " + tableName1 + "." + tablePKCol2 + " " +
			"where Groups_Permissions.groupId = " + guestGroupId + " and " +
				tableName2 + ".groupId != " + guestGroupId;

		return sql;
	}

	protected String getStalePortletPermissionsSQL(
			String primKey, long guestGroupId)
		throws Exception {

		String sql =
			"select Groups_Permissions.permissionId from Groups_Permissions " +
			"inner join Permission_ on Permission_.permissionId = " +
				"Groups_Permissions.permissionId " +
			"inner join Resource_ on Resource_.resourceId = " +
				"Permission_.resourceId " +
			"inner join ResourceCode on ResourceCode.codeId = " +
				"Resource_.codeId and Resource_.primKey = '" + primKey + "' " +
			"where Groups_Permissions.groupId = " + guestGroupId;

		return sql;
	}

	private static final String _GET_COMPANY_IDS =
		"select companyId from Company";

	private static final String _GET_DEFAULT_USER_ID =
		"select userId from User_ where companyId = ? and defaultUser = ?";

	private static final String _GET_GUEST_GROUP_ID =
		"select groupId from Group_ where companyId = ? and name = ?";

	private static Log _log = LogFactory.getLog(UpgradePermission.class);

}