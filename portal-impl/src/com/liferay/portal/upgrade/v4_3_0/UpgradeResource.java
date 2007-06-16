/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Organization;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.UserGroup;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.upgrade.UpgradeException;
import com.liferay.portal.upgrade.UpgradeProcess;
import com.liferay.portal.upgrade.util.DefaultUpgradeTableImpl;
import com.liferay.portal.upgrade.util.TempUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.UpgradeColumn;
import com.liferay.portal.upgrade.util.UpgradeTable;
import com.liferay.portal.upgrade.v4_3_0.util.AvailableMappersUtil;
import com.liferay.portal.upgrade.v4_3_0.util.ClassPKContainer;
import com.liferay.portal.upgrade.v4_3_0.util.ResourceCodeIdUpgradeColumnImpl;
import com.liferay.portal.upgrade.v4_3_0.util.ResourcePrimKeyUpgradeColumnImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.blogs.model.BlogsCategory;
import com.liferay.portlet.blogs.model.BlogsEntry;
import com.liferay.portlet.bookmarks.model.BookmarksEntry;
import com.liferay.portlet.bookmarks.model.BookmarksFolder;
import com.liferay.portlet.calendar.model.CalEvent;
import com.liferay.portlet.wiki.model.WikiNode;
import com.liferay.portlet.wiki.model.WikiPage;
import com.liferay.util.ArrayUtil;
import com.liferay.util.CollectionFactory;

import java.sql.Types;

import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="UpgradeResource.java.html"><b><i>View Source</i></b></a>
 *
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 *
 */
public class UpgradeResource extends UpgradeProcess {

	public void upgrade() throws UpgradeException {
		_log.info("Upgrading");

		try {
			doUpgrade();
		}
		catch (Exception e) {
			throw new UpgradeException(e);
		}
	}

	protected Map getClassPKContainers() {
		Map classPKContainers = CollectionFactory.getHashMap();

		// BlogsCategory

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(BlogsCategory.class.getName())),
			new ClassPKContainer(
				AvailableMappersUtil.getBlogsCategoryIdMapper(), true));

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

		// Group

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(Group.class.getName())),
			new ClassPKContainer(
				AvailableMappersUtil.getGroupIdMapper(), true));

		// Layout

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(Layout.class.getName())),
			new ClassPKContainer(
				AvailableMappersUtil.getLayoutPlidMapper(), false));

		// Organization

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(Organization.class.getName())),
			new ClassPKContainer(
				AvailableMappersUtil.getOrganizationIdMapper(), true));

		// Role

		classPKContainers.put(
			new Long(PortalUtil.getClassNameId(Role.class.getName())),
			new ClassPKContainer(AvailableMappersUtil.getRoleIdMapper(), true));

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
		Object[][] resourceColumns2 =
			(Object[][])ResourceImpl.TABLE_COLUMNS.clone();
		Object[][] resourceColumns =
			new Object[resourceColumns1.length + resourceColumns2.length][];

		ArrayUtil.combine(
			resourceColumns1, resourceColumns2, resourceColumns);

		UpgradeColumn companyIdColumn = new TempUpgradeColumnImpl("companyId");

		UpgradeColumn nameColumn = new TempUpgradeColumnImpl("name");

		UpgradeColumn scopeColumn = new TempUpgradeColumnImpl("scope");

		ResourceCodeIdUpgradeColumnImpl codeIdColumn =
			new ResourceCodeIdUpgradeColumnImpl(
				companyIdColumn, nameColumn, scopeColumn);

		UpgradeColumn primKeyColumn = new ResourcePrimKeyUpgradeColumnImpl(
			nameColumn, codeIdColumn, AvailableMappersUtil.getGroupIdMapper(),
			getClassPKContainers());

		UpgradeTable upgradeTable = new DefaultUpgradeTableImpl(
			ResourceImpl.TABLE_NAME, resourceColumns, companyIdColumn,
			nameColumn, scopeColumn, codeIdColumn, primKeyColumn);

		upgradeTable.updateTable();

		// Schema

		runSQL(_UPGRADE_SCHEMA);
	}

	private static final String[] _UPGRADE_SCHEMA = {
		"alter table Resource_ drop companyId",
		"alter table Resource_ drop name",
		"alter table Resource_ drop scope"
	};

	private static Log _log = LogFactory.getLog(UpgradeResource.class);

}