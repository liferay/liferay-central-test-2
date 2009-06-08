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

package com.liferay.portal.upgrade.v4_4_0;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.util.DefaultUpgradeTableImpl;
import com.liferay.portal.upgrade.util.TempUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.UpgradeColumn;
import com.liferay.portal.upgrade.util.UpgradeTable;
import com.liferay.portal.upgrade.v4_4_0.util.TagsAssetGroupIdUpgradeColumnImpl;

import java.sql.Types;

/**
 * <a href="UpgradeTags.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
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

		// TagsAsset

		UpgradeColumn classNameIdColumn =
			new TempUpgradeColumnImpl("classNameId");

		UpgradeColumn classPKColumn = new TempUpgradeColumnImpl("classPK");

		UpgradeColumn groupIdColumn = new TagsAssetGroupIdUpgradeColumnImpl(
			classNameIdColumn, classPKColumn);

		UpgradeTable upgradeTable = new DefaultUpgradeTableImpl(
			_TAGS_ASSET_TABLE_NAME, _TAGS_ASSET_TABLE_COLUMNS,
			classNameIdColumn, classPKColumn, groupIdColumn);

		upgradeTable.updateTable();
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
}