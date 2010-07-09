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

package com.liferay.portal.upgrade.v5_2_0;

import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.dao.jdbc.SmartResultSet;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portlet.asset.NoSuchTagException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jorge Ferrer
 * @author Brian Wing Shun Chan
 */
public class UpgradeTags extends UpgradeProcess {

	protected void addEntry(
			long entryId, long groupId, long companyId, long userId,
			String userName, Timestamp createDate, Timestamp modifiedDate,
			long parentEntryId, String name, long vocabularyId)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"insert into TagsEntry (entryId, groupId, companyId, userId, " +
					"userName, createDate, modifiedDate, parentEntryId, " +
						"name, vocabularyId) values (?, ?, ?, ?, ?, ?, ?, ?, " +
							"?, ?)");

			ps.setLong(1, entryId);
			ps.setLong(2, groupId);
			ps.setLong(3, companyId);
			ps.setLong(4, userId);
			ps.setString(5, userName);
			ps.setTimestamp(6, createDate);
			ps.setTimestamp(7, modifiedDate);
			ps.setLong(8, parentEntryId);
			ps.setString(9, name);
			ps.setLong(10, vocabularyId);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected void addProperty(
			long propertyId, long companyId, long userId, String userName,
			Timestamp createDate, Timestamp modifiedDate, long entryId,
			String key, String value)
		throws Exception {

		Connection con = null;
		PreparedStatement ps = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"insert into TagsProperty (propertyId, companyId, userId, " +
					"userName, createDate, modifiedDate, entryId, key_, " +
						"value) values (?, ?, ?, ?, ?, ?, ?, ?, ?)");

			ps.setLong(1, propertyId);
			ps.setLong(2, companyId);
			ps.setLong(3, userId);
			ps.setString(4, userName);
			ps.setTimestamp(5, createDate);
			ps.setTimestamp(6, modifiedDate);
			ps.setLong(7, entryId);
			ps.setString(8, key);
			ps.setString(9, value);

			ps.executeUpdate();
		}
		finally {
			DataAccess.cleanUp(con, ps);
		}
	}

	protected long copyEntry(long groupId, long entryId) throws Exception {
		String key = groupId + StringPool.UNDERLINE + entryId;

		Long newEntryId = _entryIdsMap.get(key);

		if (newEntryId != null) {
			return newEntryId.longValue();
		}

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select * from TagsEntry where entryId = ?",
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);

			ps.setLong(1, entryId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Timestamp createDate = rs.getTimestamp("createDate");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				long parentEntryId = rs.getLong("parentEntryId");
				String name = rs.getString("name");
				long vocabularyId = rs.getLong("vocabularyId");

				newEntryId = increment();

				addEntry(
					newEntryId, groupId, companyId, userId, userName,
					createDate, modifiedDate, parentEntryId, name,
					vocabularyId);

				copyProperties(entryId, newEntryId);

				_entryIdsMap.put(key, newEntryId);

				return newEntryId;
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		throw new NoSuchTagException(
			"No AssetTag exists with the primary key " + entryId);
	}

	public void copyProperties(long entryId, long newEntryId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select * from TagsProperty where entryId = ?",
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);

			ps.setLong(1, entryId);

			rs = ps.executeQuery();

			while (rs.next()) {
				long companyId = rs.getLong("companyId");
				long userId = rs.getLong("userId");
				String userName = rs.getString("userName");
				Timestamp createDate = rs.getTimestamp("createDate");
				Timestamp modifiedDate = rs.getTimestamp("modifiedDate");
				String key = rs.getString("key_");
				String value = rs.getString("value");

				long newPropertyId = increment();

				addProperty(
					newPropertyId, companyId, userId, userName, createDate,
					modifiedDate, newEntryId, key, value);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void deleteEntries() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select entryId from TagsEntry where groupId = 0");

			rs = ps.executeQuery();

			while (rs.next()) {
				long entryId = rs.getLong("entryId");

				runSQL(
					"delete from TagsAssets_TagsEntries where entryId = " +
						entryId);

				runSQL("delete from TagsProperty where entryId = " + entryId);
			}

			runSQL("delete from TagsEntry where groupId = 0");
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void doUpgrade() throws Exception {
		updateGroupIds();
		updateAssets();
	}

	protected void updateAssets() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select resourcePrimKey from JournalArticle where approved " +
					"= ?");

			ps.setBoolean(1, false);

			rs = ps.executeQuery();

			while (rs.next()) {
				long resourcePrimKey = rs.getLong("resourcePrimKey");

				runSQL(
					"update TagsAsset set visible = FALSE where classPK = " +
						resourcePrimKey);
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}
	}

	protected void updateGroupIds() throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			con = DataAccess.getConnection();

			ps = con.prepareStatement(
				"select TA.assetId, TA.groupId, TA_TE.entryId from " +
					"TagsAssets_TagsEntries TA_TE inner join TagsAsset TA on " +
						"TA.assetId = TA_TE.assetId",
				ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);

			rs = ps.executeQuery();

			SmartResultSet srs = new SmartResultSet(rs);

			while (srs.next()) {
				long assetId = srs.getLong("TA.assetId");
				long groupId = srs.getLong("TA.groupId");
				long entryId = srs.getLong("TA_TE.entryId");

				long newEntryId = copyEntry(groupId, entryId);

				runSQL(
					"insert into TagsAssets_TagsEntries (assetId, entryId) " +
						"values (" + assetId + ", " + newEntryId + ")");
			}
		}
		finally {
			DataAccess.cleanUp(con, ps, rs);
		}

		deleteEntries();
	}

	private Map<String, Long> _entryIdsMap = new HashMap<String, Long>();

}