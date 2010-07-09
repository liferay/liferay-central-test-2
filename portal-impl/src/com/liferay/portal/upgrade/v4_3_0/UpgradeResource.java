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
import com.liferay.portal.kernel.upgrade.util.TempUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.UpgradeTable;
import com.liferay.portal.kernel.upgrade.util.UpgradeTableFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Location;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.upgrade.v4_3_0.util.AvailableMappersUtil;
import com.liferay.portal.upgrade.v4_3_0.util.ClassPKContainer;
import com.liferay.portal.upgrade.v4_3_0.util.MBMessageIdMapper;
import com.liferay.portal.upgrade.v4_3_0.util.ResourceCodeIdUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.ResourcePrimKeyUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.ResourceTable;
import com.liferay.portal.util.PortalUtil;
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
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;

import java.sql.Types;

import java.util.HashMap;
import java.util.Map;

/**
 * @author	   Alexander Chow
 * @author	   Brian Wing Shun Chan
 * @deprecated
 */
public class UpgradeResource extends UpgradeProcess {

	protected Map<Long, ClassPKContainer> getClassPKContainers() {
		Map<Long, ClassPKContainer> classPKContainers =
			new HashMap<Long, ClassPKContainer>();

		// BlogsEntry

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(BlogsEntry.class.getName())),
			new ClassPKContainer(
				AvailableMappersUtil.getBlogsEntryIdMapper(), true));

		// BookmarksEntry

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(BookmarksEntry.class.getName())),
			new ClassPKContainer(
				AvailableMappersUtil.getBookmarksEntryIdMapper(), true));

		// BookmarksFolder

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(
				BookmarksFolder.class.getName())),
			new ClassPKContainer(
				AvailableMappersUtil.getBookmarksFolderIdMapper(), true));

		// CalEvent

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(CalEvent.class.getName())),
			new ClassPKContainer(
				AvailableMappersUtil.getCalEventIdMapper(), true));

		// DLFileEntry

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(DLFileEntry.class.getName())),
			new ClassPKContainer(
				AvailableMappersUtil.getDLFileEntryIdMapper(), false));

		// DLFileShortcut

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(DLFileShortcut.class.getName())),
			new ClassPKContainer(
				AvailableMappersUtil.getDLFileShortcutIdMapper(), true));

		// DLFolder

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(DLFolder.class.getName())),
			new ClassPKContainer(
				AvailableMappersUtil.getDLFolderIdMapper(), true));

		// Group

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(Group.class.getName())),
			new ClassPKContainer(
				AvailableMappersUtil.getGroupIdMapper(), true));

		// IGFolder

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(IGFolder.class.getName())),
			new ClassPKContainer(
				AvailableMappersUtil.getIGFolderIdMapper(), true));

		// IGImage

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(IGImage.class.getName())),
			new ClassPKContainer(
				AvailableMappersUtil.getIGImageIdMapper(), false));

		// JournalArticle

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(JournalArticle.class.getName())),
			new ClassPKContainer(
				AvailableMappersUtil.getJournalArticleIdMapper(), false));

		// JournalStructure

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(
				JournalStructure.class.getName())),
			new ClassPKContainer(
				AvailableMappersUtil.getJournalStructureIdMapper(), false));

		// JournalTemplate

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(
				JournalTemplate.class.getName())),
			new ClassPKContainer(
				AvailableMappersUtil.getJournalTemplateIdMapper(), false));

		// Layout

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(Layout.class.getName())),
			new ClassPKContainer(
				AvailableMappersUtil.getLayoutPlidMapper(), false));

		// Location

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(Location.class.getName())),
			new ClassPKContainer(
				AvailableMappersUtil.getOrganizationIdMapper(), true));

		// MBCategory

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(MBCategory.class.getName())),
			new ClassPKContainer(
				AvailableMappersUtil.getMBCategoryIdMapper(), true));

		// MBMessage

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(MBMessage.class.getName())),
			new ClassPKContainer(
				new MBMessageIdMapper(
					AvailableMappersUtil.getMBMessageIdMapper()),
				false));

		// Organization

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(Organization.class.getName())),
			new ClassPKContainer(
				AvailableMappersUtil.getOrganizationIdMapper(), true));

		// PollsQuestion

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(PollsQuestion.class.getName())),
			new ClassPKContainer(
				AvailableMappersUtil.getPollsQuestionIdMapper(), true));

		// Role

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(Role.class.getName())),
			new ClassPKContainer(AvailableMappersUtil.getRoleIdMapper(), true));

		// ShoppingCategory

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(
				ShoppingCategory.class.getName())),
			new ClassPKContainer(
				AvailableMappersUtil.getShoppingCategoryIdMapper(), true));

		// ShoppingItem

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(ShoppingItem.class.getName())),
			new ClassPKContainer(
				AvailableMappersUtil.getShoppingItemIdMapper(), true));

		// User

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(User.class.getName())),
			new ClassPKContainer(
				AvailableMappersUtil.getUserIdMapper(), false));

		// UserGroup

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(UserGroup.class.getName())),
			new ClassPKContainer(
				AvailableMappersUtil.getUserGroupIdMapper(), true));

		// WikiNode

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(WikiNode.class.getName())),
			new ClassPKContainer(
				AvailableMappersUtil.getWikiNodeIdMapper(), true));

		// WikiPage

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(WikiPage.class.getName())),
			new ClassPKContainer(
				AvailableMappersUtil.getWikiPageIdMapper(), false));

		return classPKContainers;
	}

	protected void doUpgrade() throws Exception {

		// Resource

		Object[][] resourceColumns1 = {
			{"companyId", new Integer(Types.BIGINT)},
			{"name", new Integer(Types.VARCHAR)},
			{"scope", new Integer(Types.VARCHAR)}
		};
		Object[][] resourceColumns2 = ResourceTable.TABLE_COLUMNS.clone();

		Object[][] resourceColumns = ArrayUtil.append(
			resourceColumns1, resourceColumns2);

		UpgradeColumn companyIdColumn = new TempUpgradeColumnImpl("companyId");

		UpgradeColumn nameColumn = new TempUpgradeColumnImpl("name");

		UpgradeColumn scopeColumn = new TempUpgradeColumnImpl("scope");

		ResourceCodeIdUpgradeColumnImpl codeIdColumn =
			new ResourceCodeIdUpgradeColumnImpl(
				companyIdColumn, nameColumn, scopeColumn);

		UpgradeColumn primKeyColumn = new ResourcePrimKeyUpgradeColumnImpl(
			nameColumn, codeIdColumn, AvailableMappersUtil.getGroupIdMapper(),
			getClassPKContainers(), AvailableMappersUtil.getLayoutPlidMapper());

		UpgradeTable upgradeTable = UpgradeTableFactoryUtil.getUpgradeTable(
			ResourceTable.TABLE_NAME, resourceColumns, companyIdColumn,
			nameColumn, scopeColumn, codeIdColumn, primKeyColumn);

		String createSQL = ResourceTable.TABLE_SQL_CREATE;

		createSQL =
			createSQL.substring(0, createSQL.length() - 1) +
				",companyId VARCHAR(75) null, name VARCHAR(75) null, " +
					"scope VARCHAR(75) null)";

		upgradeTable.setCreateSQL(createSQL);

		upgradeTable.updateTable();

		// Schema

		runSQL(_UPGRADE_SCHEMA);
	}

	private static final String[] _UPGRADE_SCHEMA = {
		"alter table Resource_ drop column companyId",
		"alter table Resource_ drop column name",
		"alter table Resource_ drop column scope"
	};

}