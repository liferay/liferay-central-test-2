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

package com.liferay.portal.upgrade.v4_3_0.util;

import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.util.BaseUpgradeColumnImpl;
import com.liferay.portal.kernel.upgrade.util.UpgradeColumn;
import com.liferay.portal.model.ResourceCode;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.service.ResourceCodeLocalServiceUtil;

/**
 * @author Alexander Chow
 * @author Brian Wing Shun Chan
 */
public class ResourceCodeIdUpgradeColumnImpl extends BaseUpgradeColumnImpl {

	public ResourceCodeIdUpgradeColumnImpl(
		UpgradeColumn companyIdColumn, UpgradeColumn nameColumn,
		UpgradeColumn scopeColumn) {

		super("codeId");

		_companyIdColumn = companyIdColumn;
		_nameColumn = nameColumn;
		_scopeColumn = scopeColumn;
	}

	public Object getNewValue(Object oldValue) throws Exception {
		_scope = 0;

		Long companyId = (Long)_companyIdColumn.getOldValue();
		String name = (String)_nameColumn.getOldValue();
		String scope = (String)_scopeColumn.getOldValue();

		if (scope.equals("company")) {
			_scope = ResourceConstants.SCOPE_COMPANY;
		}
		else if (scope.equals("group")) {
			_scope = ResourceConstants.SCOPE_GROUP;
		}
		else if (scope.equals("groupTemplate")) {
			_scope = ResourceConstants.SCOPE_GROUP_TEMPLATE;
		}
		else if (scope.equals("individual")) {
			_scope = ResourceConstants.SCOPE_INDIVIDUAL;
		}
		else {
			throw new UpgradeException("Scope " + _scope + " is invalid");
		}

		ResourceCode resourceCode =
			ResourceCodeLocalServiceUtil.getResourceCode(
				companyId.longValue(), name, _scope);

		return new Long(resourceCode.getCodeId());
	}

	public int getScope() {
		return _scope;
	}

	private UpgradeColumn _companyIdColumn;
	private UpgradeColumn _nameColumn;
	private UpgradeColumn _scopeColumn;
	private int _scope;

}