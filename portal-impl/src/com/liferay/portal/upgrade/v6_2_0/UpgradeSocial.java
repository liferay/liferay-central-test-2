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

package com.liferay.portal.upgrade.v6_2_0;

import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.LoggingTimer;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sergio Sanchez
 * @author Zsolt Berentey
 * @author Daniel Sanz
 */
public class UpgradeSocial extends UpgradeProcess {

	@Override
	protected void doUpgrade() throws Exception {
		updateJournalActivities();
		updateSOSocialActivities();

		updateActivities();
	}

	protected String generateExtraDataForActivity(
			ExtraDataGenerator extraDataGenerator, long companyId, long groupId,
			long userId, long classNameId, long classPK, int type,
			String extraData)
		throws Exception {

		if (extraDataGenerator == null) {
			return null;
		}

		String result = null;

		try (PreparedStatement ps = connection.prepareStatement(
				extraDataGenerator.getEntityQuery())) {

			extraDataGenerator.setEntityQueryParameters(
				ps, groupId, companyId, userId, classNameId, classPK, type,
				extraData);

			try (ResultSet rs = ps.executeQuery()) {
				JSONObject extraDataJSONObject = null;

				while (rs.next()) {
					extraDataJSONObject =
						extraDataGenerator.getExtraDataJSONObject(
							rs, extraData);
				}

				result = extraDataJSONObject.toString();
			}
		}

		return result;
	}

	protected Map<Long, String> getExtraDataMap(
			ExtraDataGenerator extraDataGenerator)
		throws Exception {

		Map<Long, String> extraDataMap = new HashMap<>();

		StringBundler sb = new StringBundler(4);

		sb.append("select activityId, groupId, companyId, userId, ");
		sb.append("classNameId, classPK, type_, extraData ");
		sb.append("from SocialActivity where ");
		sb.append(extraDataGenerator.getActivityQueryWhereClause());

		try (PreparedStatement ps = connection.prepareStatement(
				sb.toString())) {

			extraDataGenerator.setActivityQueryParameters(ps);

			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					long activityId = rs.getLong("activityId");
					long classNameId = rs.getLong("classNameId");
					long classPK = rs.getLong("classPK");
					long companyId = rs.getLong("companyId");
					String extraData = rs.getString("extraData");
					long groupId = rs.getLong("groupId");
					int type = rs.getInt("type_");
					long userId = rs.getLong("userId");

					String newExtraData = generateExtraDataForActivity(
						extraDataGenerator, groupId, companyId, userId,
						classNameId, classPK, type, extraData);

					if (newExtraData != null) {
						extraDataMap.put(activityId, newExtraData);
					}
				}
			}
		}

		return extraDataMap;
	}

	protected void updateActivities() throws Exception {
		populateExtraDataGeneratorMap();

		for (ExtraDataGenerator extraDataGenerator : _extraDataGenerators) {
			updateActivities(extraDataGenerator);
		}
	}

	protected void updateActivities(ExtraDataGenerator extraDataGenerator)
		throws Exception {

		Map<Long, String> extraDataMap = getExtraDataMap(extraDataGenerator);

		String updateActivityQuery =
			"update SocialActivity set extraData = ? where activityId = ?";

		for (Map.Entry<Long, String> entry : extraDataMap.entrySet()) {
			long activityId = entry.getKey();
			String extraData = entry.getValue();

			try (PreparedStatement ps = connection.prepareStatement(
					updateActivityQuery)) {

				ps.setString(1, extraData);
				ps.setLong(2, activityId);

				ps.executeUpdate();
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn("Unable to update activity " + activityId, e);
				}
			}
		}
	}

	protected void updateJournalActivities() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			long classNameId = PortalUtil.getClassNameId(
				"com.liferay.portlet.journal.model.JournalArticle");

			String[] tableNames = {"SocialActivity", "SocialActivityCounter"};

			for (String tableName : tableNames) {
				StringBundler sb = new StringBundler(7);

				sb.append("update ");
				sb.append(tableName);
				sb.append(" set classPK = (select resourcePrimKey ");
				sb.append("from JournalArticle where id_ = ");
				sb.append(tableName);
				sb.append(".classPK) where classNameId = ");
				sb.append(classNameId);

				runSQL(sb.toString());
			}
		}
	}

	protected void updateSOSocialActivities() throws Exception {
		try (LoggingTimer loggingTimer = new LoggingTimer()) {
			if (!hasTable("SO_SocialActivity")) {
				return;
			}

			try (PreparedStatement ps = connection.prepareStatement(
					"select activityId, activitySetId from SO_SocialActivity");
				ResultSet rs = ps.executeQuery()) {

				while (rs.next()) {
					long activityId = rs.getLong("activityId");
					long activitySetId = rs.getLong("activitySetId");

					StringBundler sb = new StringBundler(4);

					sb.append("update SocialActivity set activitySetId = ");
					sb.append(activitySetId);
					sb.append(" where activityId = ");
					sb.append(activityId);

					runSQL(sb.toString());
				}
			}

			runSQL("drop table SO_SocialActivity");
		}
	}

	protected interface ExtraDataGenerator {

		public String getActivityClassName();

		public String getActivityQueryWhereClause();

		public String getEntityQuery();

		public JSONObject getExtraDataJSONObject(
				ResultSet entityResultSet, String extraData)
			throws SQLException;

		public void setActivityQueryParameters(PreparedStatement ps)
			throws SQLException;

		public void setEntityQueryParameters(
				PreparedStatement ps, long companyId, long groupId, long userId,
				long classNameId, long classPK, int type, String extraData)
			throws SQLException;

	}

	private void populateExtraDataGeneratorMap() {
		_extraDataGenerators.add(new AddAssetCommentExtraDataGenerator());
		_extraDataGenerators.add(new AddMessageExtraDataGenerator());
		_extraDataGenerators.add(new BlogsEntryExtraDataGenerator());
		_extraDataGenerators.add(new BookmarksEntryExtraDataGenerator());
		_extraDataGenerators.add(new DLFileEntryExtraDataGenerator());
		_extraDataGenerators.add(new KBArticleExtraDataGenerator());
		_extraDataGenerators.add(new KBCommentExtraDataGenerator());
		_extraDataGenerators.add(new KBTemplateExtraDataGenerator());
		_extraDataGenerators.add(new WikiPageExtraDataGenerator());
	}

	private static final Log _log = LogFactoryUtil.getLog(UpgradeSocial.class);

	private static final List<ExtraDataGenerator> _extraDataGenerators =
		new ArrayList<>();

	private class AddAssetCommentExtraDataGenerator
		implements ExtraDataGenerator {

		@Override
		public String getActivityClassName() {
			return StringPool.BLANK;
		}

		@Override
		public String getActivityQueryWhereClause() {
			return "type_ = ?";
		}

		@Override
		public String getEntityQuery() {
			return "select subject from MBMessage where messageId = ?";
		}

		@Override
		public JSONObject getExtraDataJSONObject(
				ResultSet entityResultSet, String extraData)
			throws SQLException {

			long messageId = 0;

			try {
				JSONObject extraDataJsonObject =
					JSONFactoryUtil.createJSONObject(extraData);

				messageId = extraDataJsonObject.getLong("messageId");
			}
			catch (JSONException jsone) {
			}

			JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

			extraDataJSONObject.put(
				"title", entityResultSet.getString("subject"));

			extraDataJSONObject.put("messageId", messageId);

			return extraDataJSONObject;
		}

		@Override
		public void setActivityQueryParameters(PreparedStatement ps)
			throws SQLException {

			ps.setInt(1, _TYPE_ADD_COMMENT);
		}

		@Override
		public void setEntityQueryParameters(
				PreparedStatement ps, long companyId, long groupId, long userId,
				long classNameId, long classPK, int type, String extraData)
			throws SQLException {

			long messageId = 0;

			try {
				JSONObject extraDataJSONObject =
					JSONFactoryUtil.createJSONObject(extraData);

				messageId = extraDataJSONObject.getLong("messageId");
			}
			catch (JSONException jsone) {
			}

			ps.setLong(1, messageId);
		}

		private static final int _TYPE_ADD_COMMENT = 10005;

	};

	private class AddMessageExtraDataGenerator implements ExtraDataGenerator {

		@Override
		public String getActivityClassName() {
			return "com.liferay.portlet.messageboards.model.MBMessage";
		}

		@Override
		public String getActivityQueryWhereClause() {
			return "classNameId = ? and (type_ = ? or type_ = ?)";
		}

		@Override
		public String getEntityQuery() {
			return "select subject from MBMessage where messageId = ?";
		}

		@Override
		public JSONObject getExtraDataJSONObject(
				ResultSet entityResultSet, String extraData)
			throws SQLException {

			JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

			extraDataJSONObject.put(
				"title", entityResultSet.getString("subject"));

			return extraDataJSONObject;
		}

		@Override
		public void setActivityQueryParameters(PreparedStatement ps)
			throws SQLException {

			ps.setLong(1, PortalUtil.getClassNameId(getActivityClassName()));
			ps.setInt(2, _ADD_MESSAGE);
			ps.setInt(3, _REPLY_MESSAGE);
		}

		@Override
		public void setEntityQueryParameters(
				PreparedStatement ps, long companyId, long groupId, long userId,
				long classNameId, long classPK, int type, String extraData)
			throws SQLException {

			ps.setLong(1, classPK);
		}

		private static final int _ADD_MESSAGE = 1;

		private static final int _REPLY_MESSAGE = 2;

	};

	private class BlogsEntryExtraDataGenerator implements ExtraDataGenerator {

		@Override
		public String getActivityClassName() {
			return "com.liferay.portlet.blogs.model.BlogsEntry";
		}

		@Override
		public String getActivityQueryWhereClause() {
			return "classNameId = ? and (type_ = ? or type_ = ?)";
		}

		@Override
		public String getEntityQuery() {
			return "select title from BlogsEntry where entryId = ?";
		}

		@Override
		public JSONObject getExtraDataJSONObject(
				ResultSet entityResultSet, String extraData)
			throws SQLException {

			JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

			extraDataJSONObject.put(
				"title", entityResultSet.getString("title"));

			return extraDataJSONObject;
		}

		@Override
		public void setActivityQueryParameters(PreparedStatement ps)
			throws SQLException {

			ps.setLong(1, PortalUtil.getClassNameId(getActivityClassName()));
			ps.setInt(2, _ADD_ENTRY);
			ps.setInt(3, _UPDATE_ENTRY);
		}

		@Override
		public void setEntityQueryParameters(
				PreparedStatement ps, long companyId, long groupId, long userId,
				long classNameId, long classPK, int type, String extraData)
			throws SQLException {

			ps.setLong(1, classPK);
		}

		private static final int _ADD_ENTRY = 2;

		private static final int _UPDATE_ENTRY = 3;

	};

	private class BookmarksEntryExtraDataGenerator
		implements ExtraDataGenerator {

		@Override
		public String getActivityClassName() {
			return "com.liferay.portlet.bookmarks.model.BookmarksEntry";
		}

		@Override
		public String getActivityQueryWhereClause() {
			return "classNameId = ? and (type_ = ? or type_ = ?)";
		}

		@Override
		public String getEntityQuery() {
			return "select name from BookmarksEntry where entryId = ?";
		}

		@Override
		public JSONObject getExtraDataJSONObject(
				ResultSet entityResultSet, String extraData)
			throws SQLException {

			JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

			extraDataJSONObject.put("title", entityResultSet.getString("name"));

			return extraDataJSONObject;
		}

		@Override
		public void setActivityQueryParameters(PreparedStatement ps)
			throws SQLException {

			ps.setLong(1, PortalUtil.getClassNameId(getActivityClassName()));
			ps.setInt(2, _ADD_ENTRY);
			ps.setInt(3, _UPDATE_ENTRY);
		}

		@Override
		public void setEntityQueryParameters(
				PreparedStatement ps, long companyId, long groupId, long userId,
				long classNameId, long classPK, int type, String extraData)
			throws SQLException {

			ps.setLong(1, classPK);
		}

		private static final int _ADD_ENTRY = 1;

		private static final int _UPDATE_ENTRY = 2;

	};

	private class DLFileEntryExtraDataGenerator implements ExtraDataGenerator {

		@Override
		public String getActivityClassName() {
			return "com.liferay.portlet.documentlibrary.model.DLFileEntry";
		}

		@Override
		public String getActivityQueryWhereClause() {
			return "classNameId = ?";
		}

		@Override
		public String getEntityQuery() {
			return "select title from DLFileEntry where companyId = ? " +
				"and groupId = ? and fileEntryId = ?";
		}

		@Override
		public JSONObject getExtraDataJSONObject(
				ResultSet entityResultSet, String extraData)
			throws SQLException {

			JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

			extraDataJSONObject.put(
				"title", entityResultSet.getString("title"));

			return extraDataJSONObject;
		}

		@Override
		public void setActivityQueryParameters(PreparedStatement ps)
			throws SQLException {

			ps.setLong(1, PortalUtil.getClassNameId(getActivityClassName()));
		}

		@Override
		public void setEntityQueryParameters(
				PreparedStatement ps, long companyId, long groupId, long userId,
				long classNameId, long classPK, int type, String extraData)
			throws SQLException {

			ps.setLong(1, companyId);
			ps.setLong(2, groupId);
			ps.setLong(3, classPK);
		}

	};

	private class KBArticleExtraDataGenerator implements ExtraDataGenerator {

		@Override
		public String getActivityClassName() {
			return "com.liferay.knowledgebase.model.KBArticle";
		}

		@Override
		public String getActivityQueryWhereClause() {
			return "classNameId = ? and (type_ = ? or type_ = ? or type_ = ?)";
		}

		@Override
		public String getEntityQuery() {
			return "select title from KBArticle where resourcePrimKey = ?";
		}

		@Override
		public JSONObject getExtraDataJSONObject(
				ResultSet entityResultSet, String extraData)
			throws SQLException {

			JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

			extraDataJSONObject.put(
				"title", entityResultSet.getString("title"));

			return extraDataJSONObject;
		}

		@Override
		public void setActivityQueryParameters(PreparedStatement ps)
			throws SQLException {

			ps.setLong(1, PortalUtil.getClassNameId(getActivityClassName()));
			ps.setInt(2, _ADD_KB_ARTICLE);
			ps.setInt(3, _UPDATE_KB_ARTICLE);
			ps.setInt(4, _MOVE_KB_ARTICLE);
		}

		@Override
		public void setEntityQueryParameters(
				PreparedStatement ps, long companyId, long groupId, long userId,
				long classNameId, long classPK, int type, String extraData)
			throws SQLException {

			ps.setLong(1, classPK);
		}

		private static final int _ADD_KB_ARTICLE = 1;

		private static final int _MOVE_KB_ARTICLE = 7;

		private static final int _UPDATE_KB_ARTICLE = 3;

	};

	private class KBCommentExtraDataGenerator implements ExtraDataGenerator {

		@Override
		public String getActivityClassName() {
			return "com.liferay.knowledgebase.model.KBComment";
		}

		@Override
		public String getActivityQueryWhereClause() {
			return "classNameId = ? and (type_ = ? or type_ = ?)";
		}

		@Override
		public String getEntityQuery() {
			return "select classNameId, classPK from KBComment where " +
				"kbCommentId = ?";
		}

		@Override
		public JSONObject getExtraDataJSONObject(
				ResultSet entityResultSet, String extraData)
			throws SQLException {

			JSONObject extraDataJSONObject = null;

			long classnameId = entityResultSet.getLong("classNameId");
			long classpk = entityResultSet.getLong("classPK");

			ExtraDataGenerator extraDataGenerator = null;

			if (classnameId == PortalUtil.getClassNameId(
					_kbArticleExtraDataGenerator.getActivityClassName())) {

				extraDataGenerator = _kbArticleExtraDataGenerator;
			}
			else if (classnameId == PortalUtil.getClassNameId(
						_kbTemplateExtraDataGenerator.getActivityClassName(
							))) {

				extraDataGenerator = _kbTemplateExtraDataGenerator;
			}

			if (extraDataGenerator != null) {
				try (PreparedStatement ps = connection.prepareStatement(
						extraDataGenerator.getEntityQuery())) {

					ps.setLong(1, classpk);

					try (ResultSet rs = ps.executeQuery()) {
						while (rs.next()) {
							extraDataJSONObject =
								extraDataGenerator.getExtraDataJSONObject(
									rs, StringPool.BLANK);
						}
					}
				}
			}

			return extraDataJSONObject;
		}

		@Override
		public void setActivityQueryParameters(PreparedStatement ps)
			throws SQLException {

			ps.setLong(1, PortalUtil.getClassNameId(getActivityClassName()));
			ps.setInt(2, _ADD_KB_COMMENT);
			ps.setInt(3, _UPDATE_KB_COMMENT);
		}

		@Override
		public void setEntityQueryParameters(
				PreparedStatement ps, long companyId, long groupId, long userId,
				long classNameId, long classPK, int type, String extraData)
			throws SQLException {

			ps.setLong(1, classPK);
		}

		private static final int _ADD_KB_COMMENT = 5;

		private static final int _UPDATE_KB_COMMENT = 6;

		private final KBArticleExtraDataGenerator
			_kbArticleExtraDataGenerator = new KBArticleExtraDataGenerator();
		private final KBTemplateExtraDataGenerator
			_kbTemplateExtraDataGenerator = new KBTemplateExtraDataGenerator();

	};

	private class KBTemplateExtraDataGenerator implements ExtraDataGenerator {

		@Override
		public String getActivityClassName() {
			return "com.liferay.knowledgebase.model.KBTemplate";
		}

		@Override
		public String getActivityQueryWhereClause() {
			return "classNameId = ? and (type_ = ? or type_ = ?)";
		}

		@Override
		public String getEntityQuery() {
			return "select title from KBTemplate where kbTemplateId = ?";
		}

		@Override
		public JSONObject getExtraDataJSONObject(
				ResultSet entityResultSet, String extraData)
			throws SQLException {

			JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

			extraDataJSONObject.put(
				"title", entityResultSet.getString("title"));

			return extraDataJSONObject;
		}

		@Override
		public void setActivityQueryParameters(PreparedStatement ps)
			throws SQLException {

			ps.setLong(1, PortalUtil.getClassNameId(getActivityClassName()));
			ps.setInt(2, _ADD_KB_TEMPLATE);
			ps.setInt(3, _UPDATE_KB_TEMPLATE);
		}

		@Override
		public void setEntityQueryParameters(
				PreparedStatement ps, long companyId, long groupId, long userId,
				long classNameId, long classPK, int type, String extraData)
			throws SQLException {

			ps.setLong(1, classPK);
		}

		private static final int _ADD_KB_TEMPLATE = 2;

		private static final int _UPDATE_KB_TEMPLATE = 4;

	};

	private class WikiPageExtraDataGenerator implements ExtraDataGenerator {

		@Override
		public String getActivityClassName() {
			return "com.liferay.portlet.wiki.model.WikiPage";
		}

		@Override
		public String getActivityQueryWhereClause() {
			return "classNameId = ? and (type_ = ? or type_ = ?)";
		}

		@Override
		public String getEntityQuery() {
			return "select title, version from WikiPage where " +
				"companyId = ? and groupId = ? and resourcePrimKey = ? " +
					"and head = ?";
		}

		@Override
		public JSONObject getExtraDataJSONObject(
				ResultSet entityResultSet, String extraData)
			throws SQLException {

			JSONObject extraDataJSONObject = JSONFactoryUtil.createJSONObject();

			extraDataJSONObject.put(
				"title", entityResultSet.getString("title"));
			extraDataJSONObject.put(
				"version", entityResultSet.getDouble("version"));

			return extraDataJSONObject;
		}

		@Override
		public void setActivityQueryParameters(PreparedStatement ps)
			throws SQLException {

			ps.setLong(1, PortalUtil.getClassNameId(getActivityClassName()));
			ps.setInt(2, _ADD_PAGE);
			ps.setInt(3, _UPDATE_PAGE);
		}

		@Override
		public void setEntityQueryParameters(
				PreparedStatement ps, long companyId, long groupId, long userId,
				long classNameId, long classPK, int type, String extraData)
			throws SQLException {

			ps.setLong(1, companyId);
			ps.setLong(2, groupId);
			ps.setLong(3, classPK);
			ps.setBoolean(4, true);
		}

		private static final int _ADD_PAGE = 1;

		private static final int _UPDATE_PAGE = 2;

	};

}