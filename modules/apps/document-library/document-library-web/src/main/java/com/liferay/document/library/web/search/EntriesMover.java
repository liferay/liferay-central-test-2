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

package com.liferay.document.library.web.search;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.dao.search.RowMover;
import com.liferay.portal.kernel.dao.search.RowMoverDropTarget;
import com.liferay.portal.kernel.portlet.LiferayPortletRequest;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.portlet.PortletProvider;
import com.liferay.portal.kernel.portlet.PortletProviderUtil;
import com.liferay.portlet.admin.util.PortalProductMenuApplicationType;
import com.liferay.portlet.trash.model.TrashEntry;
import com.liferay.portlet.trash.util.TrashUtil;

/**
 * @author Chema Balsas
 */
public class EntriesMover extends RowMover {

	public EntriesMover(long scopeGroupId) throws PortalException {
		RowMoverDropTarget moveToFolderDropTarget = new RowMoverDropTarget();

		moveToFolderDropTarget.setAction("move-to-folder");
		moveToFolderDropTarget.setActiveCssClass("active");
		moveToFolderDropTarget.setSelector("[data-folder=\"true\"]");

		addDropTarget(moveToFolderDropTarget);

		if (TrashUtil.isTrashEnabled(scopeGroupId)) {
			RowMoverDropTarget moveToTrashDropTarget = new RowMoverDropTarget();

			moveToTrashDropTarget.setAction("move-to-trash");
			moveToTrashDropTarget.setActiveCssClass("active");
			moveToTrashDropTarget.setContainer("body");
			moveToTrashDropTarget.setInfoCssClass("active");
			moveToTrashDropTarget.setSelector("#_" + PortletProviderUtil.getPortletId(PortalProductMenuApplicationType.ProductMenu.CLASS_NAME, PortletProvider.Action.VIEW) + "_portlet_" + PortletProviderUtil.getPortletId(TrashEntry.class.getName(), PortletProvider.Action.VIEW));

			addDropTarget(moveToTrashDropTarget);
		}
	}
}