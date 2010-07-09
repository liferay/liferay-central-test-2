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