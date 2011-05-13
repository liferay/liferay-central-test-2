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

package com.liferay.portal.upgrade.v6_1_0;

import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.RoleConstants;

/**
 * @author Hugo Huijser
 */
public class UpgradeResourcePermission extends UpgradeProcess {

	protected void doUpgrade() throws Exception {
		StringBundler sb = new StringBundler(8);

		sb.append("update ResourcePermission inner join Role_ on ");
		sb.append("ResourcePermission.roleId = Role_.roleId set ");
		sb.append("ResourcePermission.scope = ");
		sb.append(ResourceConstants.SCOPE_GROUP_TEMPLATE);
		sb.append(", ResourcePermission.primKey = '");
		sb.append(String.valueOf(GroupConstants.DEFAULT_PARENT_GROUP_ID));
		sb.append("' where Role_.type_ = ");
		sb.append(RoleConstants.TYPE_PROVIDER);

		runSQL(sb.toString());
	}

}