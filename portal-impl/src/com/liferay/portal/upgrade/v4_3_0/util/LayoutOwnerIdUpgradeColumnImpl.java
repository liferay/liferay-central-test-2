/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.upgrade.v4_3_0.util;

import com.liferay.portal.kernel.upgrade.StagnantRowException;
import com.liferay.portal.kernel.upgrade.util.BaseUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.kernel.upgrade.util.ValueMapper;
import com.liferay.portal.kernel.util.GetterUtil;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutOwnerIdUpgradeColumnImpl extends BaseUpgradeColumnImpl {

	public LayoutOwnerIdUpgradeColumnImpl(
		String name, UpgradeColumn upgradeColumn, ValueMapper groupIdMapper) {

		super(name);

		_name = name;
		_upgradeColumn = upgradeColumn;
		_groupIdMapper = groupIdMapper;
	}

	public Object getNewValue(Object oldValue) throws Exception {
		_groupId = null;
		_privateLayout = null;

		String ownerId = (String)_upgradeColumn.getOldValue();

		if (_name.equals("groupId")) {
			if (ownerId.startsWith("PUB.") || ownerId.startsWith("PRI.")) {
				Long groupId = new Long(GetterUtil.getLong(
					ownerId.substring(4, ownerId.length())));

				_groupId = (Long)_groupIdMapper.getNewValue(groupId);

				return _groupId;
			}
			else {
				throw new StagnantRowException(ownerId);
			}
		}
		else {
			if (ownerId.startsWith("PUB.")) {
				_privateLayout = Boolean.FALSE;

				return _privateLayout;
			}
			else if (ownerId.startsWith("PRI.")) {
				_privateLayout = Boolean.TRUE;

				return _privateLayout;
			}
			else {
				throw new StagnantRowException(ownerId);
			}
		}
	}

	public Long getGroupId() {
		return _groupId;
	}

	public Boolean isPrivateLayout() {
		return _privateLayout;
	}

	private String _name;
	private UpgradeColumn _upgradeColumn;
	private ValueMapper _groupIdMapper;
	private Long _groupId;
	private Boolean _privateLayout;

}