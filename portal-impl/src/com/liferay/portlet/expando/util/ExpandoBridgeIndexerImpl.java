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

package com.liferay.portlet.expando.util;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoTableConstants;
import com.liferay.portlet.expando.model.ExpandoValue;
import com.liferay.portlet.expando.model.impl.ExpandoValueImpl;
import com.liferay.portlet.expando.service.ExpandoColumnLocalServiceUtil;
import com.liferay.portlet.expando.service.ExpandoValueLocalServiceUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="ExpandoBridgeIndexerImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 */
public class ExpandoBridgeIndexerImpl implements ExpandoBridgeIndexer {

	public void addAttributes(Document doc, ExpandoBridge expandoBridge) {
		if (expandoBridge == null) {
			return;
		}

		try {
			doAddAttributes(doc, expandoBridge);
		}
		catch (SystemException se) {
			_log.error(se, se);
		}
	}

	public String encodeFieldName(String columnName) {
		StringBundler sb = new StringBundler(3);

		sb.append(_FIELD_NAMESPACE);
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(ExpandoTableConstants.DEFAULT_TABLE_NAME.toLowerCase());
		sb.append(StringPool.FORWARD_SLASH);
		sb.append(columnName);

		return sb.toString();
	}

	protected void doAddAttributes(Document doc, ExpandoBridge expandoBridge)
		throws SystemException {

		List<ExpandoColumn> expandoColumns =
			ExpandoColumnLocalServiceUtil.getDefaultTableColumns(
				expandoBridge.getCompanyId(), expandoBridge.getClassName());

		if ((expandoColumns == null) || expandoColumns.isEmpty()) {
			return;
		}

		List<ExpandoColumn> indexedColumns = new ArrayList<ExpandoColumn>();

		for (ExpandoColumn expandoColumn : expandoColumns) {
			UnicodeProperties properties =
				expandoColumn.getTypeSettingsProperties();

			boolean indexable = GetterUtil.getBoolean(
				properties.get(ExpandoBridgeIndexer.INDEXABLE));

			if (indexable) {
				indexedColumns.add(expandoColumn);
			}
		}

		if (indexedColumns.isEmpty()) {
			return;
		}

		List<ExpandoValue> expandoValues =
			ExpandoValueLocalServiceUtil.getRowValues(
				expandoBridge.getCompanyId(), expandoBridge.getClassName(),
				ExpandoTableConstants.DEFAULT_TABLE_NAME,
				expandoBridge.getClassPK(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		for (ExpandoColumn expandoColumn : indexedColumns) {
			try {
				String fieldName = encodeFieldName(expandoColumn.getName());

				boolean defaultValue = true;
				ExpandoValue curValue = new ExpandoValueImpl();

				curValue.setData(expandoColumn.getDefaultData());
				curValue.setColumnId(expandoColumn.getColumnId());

				for (ExpandoValue expandoValue : expandoValues) {
					if (expandoValue.getColumnId() ==
							expandoColumn.getColumnId()) {

						curValue = expandoValue;
						defaultValue = false;

						break;
					}
				}

				if (expandoColumn.getType() ==
							ExpandoColumnConstants.BOOLEAN) {

					doc.addKeyword(fieldName, curValue.getBoolean());
				}
				else if (expandoColumn.getType() ==
							ExpandoColumnConstants.BOOLEAN_ARRAY) {

					doc.addKeyword(
						fieldName,
						(!defaultValue ? curValue.getBooleanArray() :
							new boolean[0]));
				}
				else if (expandoColumn.getType() ==
							ExpandoColumnConstants.DATE) {

					doc.addDate(fieldName, curValue.getDate());
				}
				else if (expandoColumn.getType() ==
							ExpandoColumnConstants.DOUBLE) {

					doc.addKeyword(fieldName, curValue.getDouble());
				}
				else if (expandoColumn.getType() ==
							ExpandoColumnConstants.DOUBLE_ARRAY) {

					doc.addKeyword(
						fieldName,
						(!defaultValue ? curValue.getDoubleArray() :
							new double[0]));
				}
				else if (expandoColumn.getType() ==
							ExpandoColumnConstants.FLOAT) {

					doc.addKeyword(fieldName, curValue.getFloat());
				}
				else if (expandoColumn.getType() ==
							ExpandoColumnConstants.FLOAT_ARRAY) {

					doc.addKeyword(
						fieldName,
						(!defaultValue ?
							StringUtil.merge(curValue.getFloatArray()) :
							StringPool.BLANK));
				}
				else if (expandoColumn.getType() ==
							ExpandoColumnConstants.INTEGER) {

					doc.addKeyword(fieldName, curValue.getInteger());
				}
				else if (expandoColumn.getType() ==
							ExpandoColumnConstants.INTEGER_ARRAY) {

					doc.addKeyword(
						fieldName,
						(!defaultValue ? curValue.getIntegerArray() :
							new int[0]));
				}
				else if (expandoColumn.getType() ==
							ExpandoColumnConstants.LONG) {

					doc.addKeyword(fieldName, curValue.getLong());
				}
				else if (expandoColumn.getType() ==
							ExpandoColumnConstants.LONG_ARRAY) {

					doc.addKeyword(
						fieldName,
						(!defaultValue ? curValue.getLongArray() :
							new long[0]));
				}
				else if (expandoColumn.getType() ==
							ExpandoColumnConstants.SHORT) {

					doc.addKeyword(fieldName, curValue.getShort());
				}
				else if (expandoColumn.getType() ==
							ExpandoColumnConstants.SHORT_ARRAY) {

					doc.addKeyword(
						fieldName,
						(!defaultValue ? curValue.getShortArray() :
							new short[0]));
				}
				else if (expandoColumn.getType() ==
							ExpandoColumnConstants.STRING) {

					doc.addText(fieldName, curValue.getString());
				}
				else if (expandoColumn.getType() ==
							ExpandoColumnConstants.STRING_ARRAY) {

					doc.addKeyword(
						fieldName,
						(!defaultValue ? curValue.getStringArray() :
							new String[0]));
				}
			}
			catch (Exception e) {
				_log.error("Indexing " + expandoColumn.getName(), e);
			}
		}
	}

	protected static final String _FIELD_NAMESPACE = "expando";

	private static Log _log = LogFactoryUtil.getLog(
		ExpandoBridgeIndexerImpl.class);

}