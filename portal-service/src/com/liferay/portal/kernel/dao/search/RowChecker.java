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

package com.liferay.portal.kernel.dao.search;

import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;

import javax.portlet.RenderResponse;

/**
 * @author Brian Wing Shun Chan
 */
public class RowChecker {

	public static final String ALIGN = "left";

	public static final String ALL_ROW_IDS = "allRowIds";

	public static final int COLSPAN = 1;

	public static final String CSS_CLASS = StringPool.BLANK;

	public static final String FORM_NAME = "fm";

	public static final String ROW_IDS = "rowIds";

	public static final String VALIGN = "middle";

	public RowChecker(RenderResponse renderResponse) {
		_renderResponse = renderResponse;

		_allRowIds = _renderResponse.getNamespace() + ALL_ROW_IDS;
		_formName = _renderResponse.getNamespace() + FORM_NAME;
		_rowIds = _renderResponse.getNamespace() + ROW_IDS;
	}

	public String getAlign() {
		return _align;
	}

	public String getAllRowsCheckBox() {
		if (Validator.isNull(_allRowIds)) {
			return StringPool.BLANK;
		}
		else {
			StringBuilder sb = new StringBuilder();

			sb.append("<input name=\"");
			sb.append(_allRowIds);
			sb.append("\" type=\"checkbox\" ");
			sb.append("onClick=\"Liferay.Util.checkAll(");
			sb.append("AUI().one(this).ancestor('");
			sb.append("table.taglib-search-iterator'), '");
			sb.append(_rowIds);
			sb.append("', this");
			sb.append(");\">");

			return sb.toString();
		}
	}

	public String getAllRowIds() {
		return _allRowIds;
	}

	public String getAllRowsId() {
		return getAllRowIds();
	}

	public int getColspan() {
		return _colspan;
	}

	public String getCssClass() {
		return _cssClass;
	}

	public String getFormName() {
		return _formName;
	}

	public String getRowCheckBox(boolean checked, String primaryKey) {
		StringBuilder sb = new StringBuilder();

		sb.append("<input ");

		if (checked) {
			sb.append("checked ");
		}

		sb.append("name=\"");
		sb.append(_rowIds);
		sb.append("\" type=\"checkbox\" value=\"");
		sb.append(primaryKey);
		sb.append("\" ");

		if (Validator.isNotNull(_allRowIds)) {
			sb.append("onClick=\"Liferay.Util.checkAllBox(");
			sb.append("AUI().one(this).ancestor('");
			sb.append("table.taglib-search-iterator'), '");
			sb.append(_rowIds);
			sb.append("', ");
			sb.append(_allRowIds);
			sb.append(");\"");
		}

		sb.append(">");

		return sb.toString();
	}

	public String getRowId() {
		return getRowIds();
	}

	public String getRowIds() {
		return _rowIds;
	}

	public String getValign() {
		return _valign;
	}

	public boolean isChecked(Object obj) {
		return false;
	}

	public void setAlign(String align) {
		_align = align;
	}

	public void setAllRowIds(String allRowIds) {
		_allRowIds = getNamespacedValue(allRowIds);
	}

	public void setColspan(int colspan) {
		_colspan = colspan;
	}

	public void setCssClass(String cssClass) {
		_cssClass = cssClass;
	}

	public void setFormName(String formName) {
		_formName = getNamespacedValue(formName);
	}

	public void setRowIds(String rowIds) {
		_rowIds = getNamespacedValue(rowIds);
	}

	public void setValign(String valign) {
		_valign = valign;
	}

	protected String getNamespacedValue(String value) {
		if (Validator.isNull(value)) {
			return StringPool.BLANK;
		}
		else {
			if (!value.startsWith(_renderResponse.getNamespace())) {
				value = _renderResponse.getNamespace() + value;
			}

			return value;
		}
	}

	private String _align = ALIGN;
	private String _allRowIds;
	private int _colspan = COLSPAN;
	private String _cssClass = CSS_CLASS;
	private String _formName;
	private RenderResponse _renderResponse;
	private String _rowIds;
	private String _valign = VALIGN;

}