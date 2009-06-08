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

package com.liferay.portal.upgrade.v5_2_3;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.util.DefaultUpgradeTableImpl;
import com.liferay.portal.upgrade.util.UpgradeTable;
import com.liferay.portal.upgrade.v5_2_3.util.TagsPropertyValueUpgradeColumnImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.documentlibrary.model.DLFileEntry;

import java.sql.Types;

/**
 * <a href="UpgradeTags.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Samuel Kong
 *
 */
public class UpgradeTags extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			doUpgrade();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	protected void doUpgrade() throws Exception {
		if (isSupportsAlterColumnName()) {
			runSQL("alter_column_type TagsAsset title VARCHAR(255) null");
		}
		else {

			// TagsAsset

			UpgradeTable upgradeTable = new DefaultUpgradeTableImpl(
				_TAGS_ASSET_TABLE_NAME, _TAGS_ASSET_TABLE_COLUMNS);

			upgradeTable.setCreateSQL(_TAGS_ASSET_TABLE_SQL_CREATE);

			upgradeTable.updateTable();
		}

		updateAssetViewCount();

		// TagsProperty

		UpgradeTable upgradeTable = new DefaultUpgradeTableImpl(
			_TAGS_PROPERTY_TABLE_NAME, _TAGS_PROPERTY_TABLE_COLUMNS,
			new TagsPropertyValueUpgradeColumnImpl("value"));

		upgradeTable.setCreateSQL(_TAGS_PROPERTY_TABLE_SQL_CREATE);

		upgradeTable.updateTable();
	}

	protected void updateAssetViewCount() throws Exception {
		updateAssetViewCount(
			BookmarksEntry.class.getName(), "BookmarksEntry", "entryId",
			"visits");

		updateAssetViewCount(
			DLFileEntry.class.getName(), "DLFileEntry", "fileEntryId",
			"readCount");
	}

	protected void updateAssetViewCount(
			String className, String tableName, String columnClassPK,
			String columnViewCount)
		throws Exception {

		long classNameId = PortalUtil.getClassNameId(className);

		StringBuilder sb = new StringBuilder();

		if (isSupportsUpdateWithInnerJoin()) {
			sb.append("update TagsAsset inner join ");
			sb.append(tableName);
			sb.append(" on ");
			sb.append(tableName);
			sb.append(".");
			sb.append(columnClassPK);
			sb.append(" = TagsAsset.classPK set TagsAsset.viewCount = ");
			sb.append(tableName);
			sb.append(".");
			sb.append(columnViewCount);
			sb.append(" where TagsAsset.classNameId = ");
			sb.append(classNameId);
		}
		else {
			sb.append("update TagsAsset set viewCount = (select ");
			sb.append(tableName);
			sb.append(".");
			sb.append(columnViewCount);
			sb.append(" from ");
			sb.append(tableName);
			sb.append(" where TagsAsset.classPK = ");
			sb.append(tableName);
			sb.append(".");
			sb.append(columnClassPK);
			sb.append(") where TagsAsset.classNameId = ");
			sb.append(classNameId);
		}

		runSQL(sb.toString());
	}

	private static Log _log = LogFactoryUtil.getLog(UpgradeTags.class);

	private static final String _TAGS_ASSET_TABLE_NAME = "TagsAsset";
	private static final Object[][] _TAGS_ASSET_TABLE_COLUMNS = {
		{ "assetId", new Integer(Types.BIGINT) },

		{ "groupId", new Integer(Types.BIGINT) },

		{ "companyId", new Integer(Types.BIGINT) },

		{ "userId", new Integer(Types.BIGINT) },

		{ "userName", new Integer(Types.VARCHAR) },

		{ "createDate", new Integer(Types.TIMESTAMP) },

		{ "modifiedDate", new Integer(Types.TIMESTAMP) },

		{ "classNameId", new Integer(Types.BIGINT) },

		{ "classPK", new Integer(Types.BIGINT) },

		{ "visible", new Integer(Types.BOOLEAN) },

		{ "startDate", new Integer(Types.TIMESTAMP) },

		{ "endDate", new Integer(Types.TIMESTAMP) },

		{ "publishDate", new Integer(Types.TIMESTAMP) },

		{ "expirationDate", new Integer(Types.TIMESTAMP) },

		{ "mimeType", new Integer(Types.VARCHAR) },

		{ "title", new Integer(Types.VARCHAR) },

		{ "description", new Integer(Types.VARCHAR) },

		{ "summary", new Integer(Types.VARCHAR) },

		{ "url", new Integer(Types.VARCHAR) },

		{ "height", new Integer(Types.INTEGER) },

		{ "width", new Integer(Types.INTEGER) },

		{ "priority", new Integer(Types.DOUBLE) },

		{ "viewCount", new Integer(Types.INTEGER) }
	};
	private static final String _TAGS_ASSET_TABLE_SQL_CREATE = "create table TagsAsset (assetId LONG not null primary key,groupId LONG,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,classNameId LONG,classPK LONG,visible BOOLEAN,startDate DATE null,endDate DATE null,publishDate DATE null,expirationDate DATE null,mimeType VARCHAR(75) null,title VARCHAR(255) null,description STRING null,summary STRING null,url STRING null,height INTEGER,width INTEGER,priority DOUBLE,viewCount INTEGER)";

	private static final String _TAGS_PROPERTY_TABLE_NAME = "TagsProperty";
	private static final Object[][] _TAGS_PROPERTY_TABLE_COLUMNS = {
			{ "propertyId", new Integer(Types.BIGINT) },

			{ "companyId", new Integer(Types.BIGINT) },

			{ "userId", new Integer(Types.BIGINT) },

			{ "userName", new Integer(Types.VARCHAR) },

			{ "createDate", new Integer(Types.TIMESTAMP) },

			{ "modifiedDate", new Integer(Types.TIMESTAMP) },

			{ "entryId", new Integer(Types.BIGINT) },

			{ "key_", new Integer(Types.VARCHAR) },

			{ "value", new Integer(Types.VARCHAR) }
		};
	private static final String _TAGS_PROPERTY_TABLE_SQL_CREATE = "create table TagsProperty (propertyId LONG not null primary key,companyId LONG,userId LONG,userName VARCHAR(75) null,createDate DATE null,modifiedDate DATE null,entryId LONG,key_ VARCHAR(75) null,value VARCHAR(255) null)";

}