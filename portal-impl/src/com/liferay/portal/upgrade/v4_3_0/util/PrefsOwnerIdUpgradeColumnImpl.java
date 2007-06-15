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

package com.liferay.portal.upgrade.v4_3_0.util;

import com.liferay.portal.upgrade.util.BaseUpgradeColumnImpl;
import com.liferay.portal.upgrade.util.ValueMapper;
import com.liferay.portal.util.PortletKeys;
import com.liferay.util.GetterUtil;

import java.sql.Types;

/**
 * <a href="PrefsOwnerIdUpgradeColumnImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PrefsOwnerIdUpgradeColumnImpl extends BaseUpgradeColumnImpl {

	public PrefsOwnerIdUpgradeColumnImpl(
		ValueMapper groupIdMapper, ValueMapper userIdMapper) {

		super("ownerId");

		_oldColumnType = new Integer(Types.VARCHAR);
		_groupIdMapper = groupIdMapper;
		_userIdMapper = userIdMapper;
	}

	public Integer getOldColumnType(Integer defaultType) {
		return _oldColumnType;
	}

	public Integer getNewColumnType(Integer defaultType) {
		return getOldColumnType(defaultType);
	}

	public Object getNewValue(Object oldValue) throws Exception {
		_ownerTypeObj = null;
		_groupId = null;
		_privateLayout = null;

		String ownerId = (String)oldValue;

		int ownerType = 0;

		if (ownerId.startsWith("PUB.") || ownerId.startsWith("PRI.")) {
			if (ownerId.indexOf(".USER.") != -1) {
				ownerType = PortletKeys.PREFS_OWNER_TYPE_USER;
			}
			else {
				ownerId = ownerId.substring(4, ownerId.length());
				ownerType = PortletKeys.PREFS_OWNER_TYPE_LAYOUT;
				_groupId = new Long(GetterUtil.getLong(ownerId));
				_privateLayout = new Boolean(ownerId.startsWith("PRI."));
			}
		}
		else if (ownerId.startsWith("COMPANY.")) {
			ownerType = PortletKeys.PREFS_OWNER_TYPE_COMPANY;
		}
		else if (ownerId.startsWith("GROUP.")) {
			ownerId = ownerId.substring(6, ownerId.length());
			ownerType = PortletKeys.PREFS_OWNER_TYPE_GROUP;
		}
		else if (ownerId.startsWith("USER.")) {
			ownerType = PortletKeys.PREFS_OWNER_TYPE_USER;
		}
		else {
			ownerId = String.valueOf(_userIdMapper.getNewValue(ownerId));
			ownerType = PortletKeys.PREFS_OWNER_TYPE_USER;
		}

		_ownerTypeObj = new Integer(ownerType);

		return ownerId;
	}

	public Integer getOwnerType() {
		return _ownerTypeObj;
	}

	public Long getGroupId() {
		return _groupId;
	}

	public Boolean isPrivateLayout() {
		return _privateLayout;
	}

	private Integer _oldColumnType;
	private ValueMapper _groupIdMapper;
	private ValueMapper _userIdMapper;
	private Integer _ownerTypeObj;
	private Long _groupId;
	private Boolean _privateLayout;

}