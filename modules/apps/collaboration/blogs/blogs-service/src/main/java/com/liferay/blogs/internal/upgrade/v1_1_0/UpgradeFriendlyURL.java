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

package com.liferay.blogs.internal.upgrade.v1_1_0;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.friendly.url.service.FriendlyURLEntryLocalService;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * @author Adolfo Pérez
 */
public class UpgradeFriendlyURL extends UpgradeProcess {

	public UpgradeFriendlyURL(
		FriendlyURLEntryLocalService friendlyURLEntryLocalService) {

		_friendlyURLEntryLocalService = friendlyURLEntryLocalService;
	}

	@Override
	protected void doUpgrade() throws Exception {
		try (PreparedStatement ps1 = connection.prepareStatement(
				"select groupId, entryId, urlTitle from BlogsEntry")) {

			ResultSet rs = ps1.executeQuery();

			while (rs.next()) {
				long groupId = rs.getLong(1);
				long classPK = rs.getLong(2);
				String urlTitle = rs.getString(3);

				_friendlyURLEntryLocalService.addFriendlyURLEntry(
					groupId, BlogsEntry.class, classPK, urlTitle,
					new ServiceContext());
			}
		}
	}

	private final FriendlyURLEntryLocalService _friendlyURLEntryLocalService;

}