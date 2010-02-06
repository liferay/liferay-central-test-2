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

package com.liferay.portal.upgrade.v4_3_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.upgrade.util.DefaultPKMapper;
import com.liferay.portal.kernel.upgrade.util.SwapUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.TempUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.kernel.upgrade.util.ValueMapper;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.upgrade.util.PKUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.AvailableMappersUtil;
import com.liferay.portal.upgrade.v4_3_0.util.JournalArticleContentUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.JournalArticlePKUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.JournalArticleResourcePrimKeyUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.JournalArticleTable;
import com.liferay.portal.upgrade.v4_3_0.util.JournalStructurePKUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.JournalStructureTable;
import com.liferay.portal.upgrade.v4_3_0.util.JournalStructureXSDUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.JournalTemplatePKUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.JournalTemplateSmallImageIdUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.JournalTemplateTable;
import com.liferay.portal.upgrade.v4_3_0.util.JournalTemplateXSLUpgradeColumnImpl;
import com.liferay.portal.util.PropsUtil;

import java.sql.Types;

/**
 * <a href="UpgradeJournal.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class UpgradeJournal extends UpgradeProcess {

	protected void doUpgrade() throws Exception {

		// JournalArticle

		UpgradeColumn upgradeCompanyIdColumn = new SwapUpgradeColumnImpl(
			"companyId", new Integer(Types.VARCHAR),
			AvailableMappersUtil.getCompanyIdMapper());

		UpgradeColumn upgradeGroupIdColumn = new SwapUpgradeColumnImpl(
			"groupId", AvailableMappersUtil.getGroupIdMapper());

		UpgradeColumn upgradeUserIdColumn = new SwapUpgradeColumnImpl(
			"userId", new Integer(Types.VARCHAR),
			AvailableMappersUtil.getUserIdMapper());

		UpgradeColumn upgradeApprovedByUserIdColumn = new SwapUpgradeColumnImpl(
			"approvedByUserId", new Integer(Types.VARCHAR),
			AvailableMappersUtil.getUserIdMapper());

		JournalArticlePKUpgradeColumnImpl upgradeArticlePKColumn =
			new JournalArticlePKUpgradeColumnImpl(
				upgradeCompanyIdColumn, upgradeGroupIdColumn);

		UpgradeColumn upgradeArticleResourcePrimKeyColumn =
			new JournalArticleResourcePrimKeyUpgradeColumnImpl(
				upgradeArticlePKColumn);

		UpgradeColumn upgradeArticleIdColumn =
			new TempUpgradeColumnImpl("articleId");

		UpgradeColumn upgradeVersionColumn =
			new TempUpgradeColumnImpl("version", new Integer(Types.DOUBLE));

		UpgradeColumn upgradeStructureIdColumn =
			new TempUpgradeColumnImpl("structureId");

		UpgradeColumn upgradeContentColumn =
			new JournalArticleContentUpgradeColumnImpl(
				upgradeCompanyIdColumn, upgradeGroupIdColumn,
				upgradeArticleIdColumn, upgradeVersionColumn,
				upgradeStructureIdColumn,
				AvailableMappersUtil.getImageIdMapper());

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			JournalArticleTable.TABLE_NAME, JournalArticleTable.TABLE_COLUMNS,
			upgradeCompanyIdColumn, upgradeGroupIdColumn, upgradeUserIdColumn,
			upgradeApprovedByUserIdColumn, upgradeArticlePKColumn,
			upgradeArticleResourcePrimKeyColumn, upgradeArticleIdColumn,
			upgradeVersionColumn, upgradeStructureIdColumn,
			upgradeContentColumn);

		upgradeTable.setCreateSQL(JournalArticleTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();

		ValueMapper articleIdMapper = new DefaultPKMapper(
			upgradeArticlePKColumn.getValueMapper());

		AvailableMappersUtil.setJournalArticleIdMapper(articleIdMapper);

		// JournalStructure

		PKUpgradeColumnImpl upgradeStructurePKColumn =
			new JournalStructurePKUpgradeColumnImpl(
				upgradeCompanyIdColumn, upgradeGroupIdColumn);

		UpgradeColumn upgradeXSDColumn =
			new JournalStructureXSDUpgradeColumnImpl();

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			JournalStructureTable.TABLE_NAME,
			JournalStructureTable.TABLE_COLUMNS, upgradeCompanyIdColumn,
			upgradeGroupIdColumn, upgradeStructurePKColumn, upgradeUserIdColumn,
			upgradeXSDColumn);

		upgradeTable.setCreateSQL(JournalStructureTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();

		ValueMapper structureIdMapper = new DefaultPKMapper(
			upgradeStructurePKColumn.getValueMapper());

		AvailableMappersUtil.setJournalStructureIdMapper(structureIdMapper);

		// JournalTemplate

		PKUpgradeColumnImpl upgradeTemplatePKColumn =
			new JournalTemplatePKUpgradeColumnImpl(
				upgradeCompanyIdColumn, upgradeGroupIdColumn);

		UpgradeColumn upgradeTemplateIdColumn =
			new TempUpgradeColumnImpl("templateId");

		UpgradeColumn upgradeXSLColumn =
			new JournalTemplateXSLUpgradeColumnImpl(upgradeTemplateIdColumn);

		UpgradeColumn upgradeSmallImageIdColumn =
			new JournalTemplateSmallImageIdUpgradeColumnImpl(
				upgradeCompanyIdColumn, upgradeGroupIdColumn,
				upgradeTemplatePKColumn,
				AvailableMappersUtil.getImageIdMapper());

		upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			JournalTemplateTable.TABLE_NAME, JournalTemplateTable.TABLE_COLUMNS,
			upgradeCompanyIdColumn, upgradeGroupIdColumn,
			upgradeTemplatePKColumn, upgradeUserIdColumn,
			upgradeTemplateIdColumn, upgradeXSLColumn,
			upgradeSmallImageIdColumn);

		upgradeTable.setCreateSQL(JournalTemplateTable.TABLE_SQL_CREATE);

		upgradeTable.updateTable();

		ValueMapper templateIdMapper = new DefaultPKMapper(
			upgradeTemplatePKColumn.getValueMapper());

		AvailableMappersUtil.setJournalTemplateIdMapper(templateIdMapper);

		// JournalContentSearch

		PropsUtil.set(PropsKeys.JOURNAL_SYNC_CONTENT_SEARCH_ON_STARTUP, "true");
	}

}