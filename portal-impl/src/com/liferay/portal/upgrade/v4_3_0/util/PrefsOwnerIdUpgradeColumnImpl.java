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

import com.liferay.portal.kernel.upgrade.util.TempUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.ValueMapper;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.util.PortletKeys;

import java.sql.Types;

/**
 * @author Brian Wing Shun Chan
 */
public class PrefsOwnerIdUpgradeColumnImpl extends TempUpgradeColumnImpl {

	public PrefsOwnerIdUpgradeColumnImpl(
		ValueMapper companyIdMapper, ValueMapper groupIdMapper,
		ValueMapper userIdMapper) {

		super("ownerId", new Integer(Types.VARCHAR));

		_companyIdMapper = companyIdMapper;
		_groupIdMapper = groupIdMapper;
		_userIdMapper = userIdMapper;
	}

	public Integer getNewColumnType(Integer defaultType) {
		return new Integer(Types.BIGINT);
	}

	public Object getNewValue(Object oldValue) throws Exception {
		_ownerType = null;
		_oldGroupId = null;
		_newGroupId = null;
		_privateLayout = null;

		String ownerId = (String)oldValue;

		int ownerType = 0;

		if (ownerId.startsWith("PUB.") || ownerId.startsWith("PRI.")) {
			_privateLayout = Boolean.valueOf(ownerId.startsWith("PRI."));

			int pos = ownerId.indexOf(".USER.");

			if (pos != -1) {
				_oldGroupId = new Long(GetterUtil.getLong(
					ownerId.substring(4, pos)));
				_newGroupId = (Long)_groupIdMapper.getNewValue(_oldGroupId);

				ownerId = String.valueOf(_userIdMapper.getNewValue(
					ownerId.substring(pos + 6, ownerId.length())));
				ownerType = PortletKeys.PREFS_OWNER_TYPE_USER;
			}
			else {
				_oldGroupId = new Long(GetterUtil.getLong(
					ownerId.substring(4, ownerId.length())));
				_newGroupId = (Long)_groupIdMapper.getNewValue(_oldGroupId);

				ownerId = String.valueOf(PortletKeys.PREFS_OWNER_ID_DEFAULT);
				ownerType = PortletKeys.PREFS_OWNER_TYPE_LAYOUT;
			}
		}
		else if (ownerId.startsWith("COMPANY.")) {
			ownerId = String.valueOf(_companyIdMapper.getNewValue(
				ownerId.substring(8, ownerId.length())));
			ownerType = PortletKeys.PREFS_OWNER_TYPE_COMPANY;
		}
		else if (ownerId.startsWith("GROUP.")) {
			Long groupId = new Long(GetterUtil.getLong(
				ownerId.substring(6, ownerId.length())));

			groupId = (Long)_groupIdMapper.getNewValue(groupId);

			ownerId = String.valueOf(groupId);
			ownerType = PortletKeys.PREFS_OWNER_TYPE_GROUP;
		}
		else if (ownerId.startsWith("USER.")) {
			ownerId = String.valueOf(_userIdMapper.getNewValue(
				ownerId.substring(5, ownerId.length())));
			ownerType = PortletKeys.PREFS_OWNER_TYPE_USER;
		}
		else {
			ownerId = String.valueOf(_userIdMapper.getNewValue(ownerId));
			ownerType = PortletKeys.PREFS_OWNER_TYPE_USER;
		}

		_ownerType = new Integer(ownerType);

		return ownerId;
	}

	public Integer getOwnerType() {
		return _ownerType;
	}

	public Long getOldGroupId() {
		return _oldGroupId;
	}

	public Long getNewGroupId() {
		return _newGroupId;
	}

	public Boolean isPrivateLayout() {
		return _privateLayout;
	}

	private ValueMapper _companyIdMapper;
	private ValueMapper _groupIdMapper;
	private ValueMapper _userIdMapper;
	private Integer _ownerType;
	private Long _oldGroupId;
	private Long _newGroupId;
	private Boolean _privateLayout;

}