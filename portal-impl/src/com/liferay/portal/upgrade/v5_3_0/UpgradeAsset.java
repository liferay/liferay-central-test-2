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

package com.liferay.portal.upgrade.v5_3_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.jdbc.SmartResultSet;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.model.ResourceCode;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.service.ResourceCodeLocalServiceUtil;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portlet.asset.model.AssetCategory;
import com.liferay.portlet.asset.model.AssetEntry;
import com.liferay.portlet.asset.model.AssetTag;
import com.liferay.portlet.asset.model.AssetVocabulary;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

/**
 * <a href="UpgradeAsset.java.html"><b><i>View Source</i></b></a>
 *
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 *
 */
public class UpgradeAsset extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			updateResourceCodes();
			updateAssetEntries();
			updateAssetCategories();
			updateAssetTags();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	protected void copyProperties(
			long categoryId, String tableName, String pkName)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select * from TagsProperty where entryId = ?");

			ps.setLong(1, categoryId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long propertyId = rs.getLong("propertyId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Timestamp createDate = rs.getTimestamp("createDate");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				String key = rs.getString("key_");
				String value = rs.getString("value");

				ps = con.prepareStatement(
					"insert into " + tableName + " (" + pkName + "," +
						" companyId, userId, userName, createDate, " +
							"modifiedDate, entryId, key_, value) values (?, " +
								"?, ?, ?, ?, ?, ?, ?, ?)");

				ps.setLong(1, propertyId);
				ps.setLong(2, companyId);
				ps.setLong(3, userId);
				ps.setString(4, userName);
				ps.setTimestamp(5, createDate);
				ps.setTimestamp(6, modifiedDate);
				ps.setLong(7, categoryId);
				ps.setString(8, key);
				ps.setString(9, value);

				ps.executeUpdate();

				ps.close();
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void copyAssociations(
			long tagsEntryId, String tableName, String pkName)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select * from TagsAssets_TagsEntries where entryId = ?");

			ps.setLong(1, tagsEntryId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long tagsAssetId = rs.getLong("assetId");

				ps = con.prepareStatement(
					"insert into " + tableName + " (entryId, " + pkName +
						") values (?, ?)");

				ps.setLong(1, tagsAssetId);
				ps.setLong(2, tagsEntryId);

				ps.executeUpdate();

				ps.close();
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void copyEntriesToCategories(long vocabularyId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select * from TagsEntry where vocabularyId = ?");

			ps.setLong(1, vocabularyId);

			rs = ps.executeQuery();

			SmartResultSet srs = new SmartResultSet(rs);

			while (srs.next()) {
				long entryId = srs.getLong("entryId");
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Timestamp createDate = rs.getTimestamp("createDate");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				long parentCategoryId = rs.getLong("parentEntryId");
				String name = rs.getString("name");

				String uuid = PortalUUIDUtil.generate();

				ps = con.prepareStatement(
					"insert into AssetCategory " +
						"(uuid_, categoryId, groupId, companyId, userId, " +
							"userName, createDate, modifiedDate, " +
								"parentCategoryId, name, vocabularyId) " +
									"values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");

				ps.setString(1, uuid);
				ps.setLong(2, entryId);
				ps.setLong(3, groupId);
				ps.setLong(4, companyId);
				ps.setLong(5, userId);
				ps.setString(6, userName);
				ps.setTimestamp(7, createDate);
				ps.setTimestamp(8, modifiedDate);
				ps.setLong(9, parentCategoryId);
				ps.setString(10, name);
				ps.setLong(11, vocabularyId);

				ps.executeUpdate();

				copyAssociations(
					entryId, "AssetEntries_AssetCategories", "categoryId");

				copyProperties(
					entryId, "AssetCategoryProperty", "categoryPropertyId");

				// Permissions

				String resourceName = AssetCategory.class.getName();

				ResourceLocalServiceUtil.addModelResources(
					companyId, groupId, 0, resourceName, null, null, null);

				updateCategoryResource(companyId, entryId);
			}

		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateAssetEntries() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement("select * from TagsAsset");

			rs = ps.executeQuery();

			SmartResultSet srs = new SmartResultSet(rs);

			while (srs.next()) {
				long assetId = srs.getLong("assetId");
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Timestamp createDate = rs.getTimestamp("createDate");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				long classNameId = rs.getLong("classNameId");
				long classPK = rs.getLong("classPK");
				boolean visible = rs.getBoolean("visible");
				Timestamp startDate = rs.getTimestamp("startDate");
				Timestamp endDate = rs.getTimestamp("endDate");
				Timestamp publishDate = rs.getTimestamp("publishDate");
				Timestamp expirationDate = rs.getTimestamp("expirationDate");
				String mimeType = rs.getString("mimeType");
				String title = rs.getString("title");
				String description = rs.getString("description");
				String summary = rs.getString("summary");
				String url = rs.getString("url");
				int height = rs.getInt("height");
				int width = rs.getInt("width");
				double priority = rs.getDouble("priority");
				int viewCount = rs.getInt("viewCount");

				ps = con.prepareStatement(
					"insert into AssetEntry " +
						"(entryId, groupId, companyId, userId, userName, " +
							"createDate, modifiedDate, classNameId, classPK, " +
								"visible, startDate, endDate, publishDate, " +
									"expirationDate, mimeType, title, " +
										"description, summary, url, height, " +
											"width, priority, viewCount) " +
												"values (?, ?, ?, ?, ?, ?, " +
													"?, ?, ?, ?, ?, ?, ?, ?, " +
														"?, ?, ?, ?, ?, ?, " +
															"?, ?, ?)");

				ps.setLong(1, assetId);
				ps.setLong(2, groupId);
				ps.setLong(3, companyId);
				ps.setLong(4, userId);
				ps.setString(5, userName);
				ps.setTimestamp(6, createDate);
				ps.setTimestamp(7, modifiedDate);
				ps.setLong(8, classNameId);
				ps.setLong(9, classPK);
				ps.setBoolean(10, visible);
				ps.setTimestamp(11, startDate);
				ps.setTimestamp(12, endDate);
				ps.setTimestamp(13, publishDate);
				ps.setTimestamp(14, expirationDate);
				ps.setString(15, mimeType);
				ps.setString(16, title);
				ps.setString(17, description);
				ps.setString(18, summary);
				ps.setString(19, url);
				ps.setInt(20, height);
				ps.setInt(21, width);
				ps.setDouble(22, priority);
				ps.setInt(23, viewCount);

				ps.executeUpdate();
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateAssetCategories() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select * from TagsVocabulary where folksonomy = false");

			rs = ps.executeQuery();

			SmartResultSet srs = new SmartResultSet(rs);

			while (srs.next()) {
				long vocabularyId = srs.getLong("vocabularyId");
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Timestamp createDate = rs.getTimestamp("createDate");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				String name = rs.getString("name");
				String description = rs.getString("description");

				String uuid = PortalUUIDUtil.generate();

				ps = con.prepareStatement(
					"insert into AssetVocabulary " +
						"(uuid_, vocabularyId, groupId, companyId, userId, " +
							"userName, createDate, modifiedDate, name, " +
								"description) values (?, ?, ?, ?, ?, ?, ?," +
									" ?, ?, ?)");

				ps.setString(1, uuid);
				ps.setLong(2, vocabularyId);
				ps.setLong(3, groupId);
				ps.setLong(4, companyId);
				ps.setLong(5, userId);
				ps.setString(6, userName);
				ps.setTimestamp(7, createDate);
				ps.setTimestamp(8, modifiedDate);
				ps.setString(9, name);
				ps.setString(10, description);

				ps.executeUpdate();

				copyEntriesToCategories(vocabularyId);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateAssetTags() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select TE.* from TagsEntry TE inner join TagsVocabulary TV " +
					"on TE.vocabularyId = TV.vocabularyId where " +
						"TV.folksonomy = true");

			rs = ps.executeQuery();

			SmartResultSet srs = new SmartResultSet(rs);

			while (srs.next()) {
				long entryId = srs.getLong("entryId");
				long groupId = rs.getLong("groupId");
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Timestamp createDate = rs.getTimestamp("createDate");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				String name = rs.getString("name");

				ps = con.prepareStatement(
					"insert into AssetTag (tagId, groupId, companyId, " +
						"userId, userName, createDate, modifiedDate, name) " +
							"values (?, ?, ?, ?, ?, ?, ?, ?)");

				ps.setLong(1, entryId);
				ps.setLong(2, groupId);
				ps.setLong(3, companyId);
				ps.setLong(4, userId);
				ps.setString(5, userName);
				ps.setTimestamp(6, createDate);
				ps.setTimestamp(7, modifiedDate);
				ps.setString(8, name);

				ps.executeUpdate();

				copyAssociations(
					entryId, "AssetEntries_AssetTags", "tagId");

				copyProperties(
					entryId, "AssetTagProperty", "tagPropertyId");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateCategoryResource(long companyId, long categoryId)
		throws Exception{

		String newName = AssetCategory.class.getName();
		String oldName = "com.liferay.tags.model.TagsEntry";

		ResourceCode newResourceCode =
			ResourceCodeLocalServiceUtil.getResourceCode(
				companyId, newName, ResourceConstants.SCOPE_INDIVIDUAL);
		ResourceCode oldResourceCode =
			ResourceCodeLocalServiceUtil.getResourceCode(
				companyId, oldName, ResourceConstants.SCOPE_INDIVIDUAL);

		long newCodeId = newResourceCode.getCodeId();
		long oldCodeId = oldResourceCode.getCodeId();

		// Alg. 1-5

		runSQL(
			"update Resource_ set codeId = '" + newCodeId + "' where " +
				"codeId = '" + oldCodeId +
					"' and primKey = '" + categoryId + "';");

		// Alg. 6

		runSQL(
			"update ResourcePermission set name = '" + newName + "' where " +
				"companyId = '" + companyId + "' and name = '" + oldName +
					"' and scope = '" + ResourceConstants.SCOPE_INDIVIDUAL +
						"' and primKey = '" + categoryId + "';");
	}

	protected void updateResourceCodes() throws Exception {
		updateResourceCodes(
			"com.liferay.portlet.tags", "com.liferay.portlet.asset"
		);
		updateResourceCodes(
			"com.liferay.portlet.tags.model.TagsEntry",
			AssetTag.class.getName()
		);
		updateResourceCodes(
			"com.liferay.portlet.tags.model.TagsAsset",
			AssetEntry.class.getName()
		);
		updateResourceCodes(
			"com.liferay.portlet.tags.model.TagsVocabulary",
			AssetVocabulary.class.getName()
		);
	}

	protected void updateResourceCodes(String oldCodeName, String newCodeName)
		throws Exception {

		// Alg. 1-5

		runSQL(
			"update ResourceCode set name = '" + newCodeName + "' where" +
				" name = '" + oldCodeName + "';");

		// Alg. 6

		runSQL(
			"update ResourceAction set name = '" + newCodeName + "' where" +
				" name = '" + oldCodeName + "';");

		runSQL(
			"update ResourcePermission set name = '" + newCodeName + "' where" +
				" name = '" + oldCodeName + "';");
	}

	private static Log _log = LogFactoryUtil.getLog(UpgradeAsset.class);

}