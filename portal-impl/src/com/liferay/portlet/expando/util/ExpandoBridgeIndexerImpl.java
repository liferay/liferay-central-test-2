/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.expando.util;

import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.search.Document;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portlet.expando.model.ExpandoBridge;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoTableConstants;
import com.liferay.portlet.expando.model.ExpandoValue;
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

	protected void doAddAttributes(Document doc, ExpandoBridge expandoBridge)
		throws SystemException {

		List<ExpandoColumn> expandoColumns =
			ExpandoColumnLocalServiceUtil.getDefaultTableColumns(
				expandoBridge.getClassName());

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
				expandoBridge.getClassName(),
				ExpandoTableConstants.DEFAULT_TABLE_NAME,
				expandoBridge.getClassPK(), QueryUtil.ALL_POS,
				QueryUtil.ALL_POS);

		for (ExpandoColumn expandoColumn : indexedColumns) {
			try {
				String value = expandoColumn.getDefaultData();

				for (ExpandoValue expandoValue : expandoValues) {
					if (expandoValue.getColumnId() ==
							expandoColumn.getColumnId()) {

						value = expandoValue.getData();

						break;
					}
				}

				doc.addText(expandoColumn.getName(), value);
			}
			catch (Exception e) {
				_log.error("Indexing " + expandoColumn.getName(), e);
			}
		}
	}

	private static Log _log = LogFactoryUtil.getLog(
		ExpandoBridgeIndexerImpl.class);

}