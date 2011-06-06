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

package com.liferay.portlet.documentlibrary.search;

import com.liferay.portal.kernel.dao.search.RowChecker;
import com.liferay.portal.kernel.portlet.LiferayPortletResponse;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Sergio González
 */
public class EntriesChecker extends RowChecker {

	public EntriesChecker(
		LiferayPortletResponse liferayPortletResponse) {

		super(liferayPortletResponse);
	}

	public String getAllRowsCheckBox() {
		return null;
	}

	public String getRowCheckBox(boolean checked, String primaryKey) {

		StringBuilder sb = new StringBuilder();

		sb.append("<input ");

		if (checked) {
			sb.append("checked ");
		}

		sb.append("name=\"");
		sb.append(getRowIds());
		sb.append("\" type=\"checkbox\" value=\"");
		sb.append(primaryKey);
		sb.append("\" ");

		if (Validator.isNotNull(getAllRowIds())) {
			sb.append("onClick=\"Liferay.Util.checkAllBox(");
			sb.append("AUI().one(this).ancestor('");
			sb.append("table.taglib-search-iterator'), '");
			sb.append(getRowIds());
			sb.append("', ");
			sb.append(getAllRowIds());
			sb.append("Checkbox); ");
		}

		sb.append(">");

		return sb.toString();
	}

}