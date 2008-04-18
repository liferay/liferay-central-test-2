/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.expando.service;

import com.liferay.portal.service.BaseServiceTestCase;
import com.liferay.portlet.expando.NoSuchTableException;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.model.ExpandoColumnConstants;
import com.liferay.portlet.expando.model.ExpandoRow;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.ExpandoValue;
import com.liferay.portlet.expando.model.impl.ExpandoValueImpl;
import com.liferay.util.dao.hibernate.QueryUtil;

import java.util.List;

/**
 * <a href="ExpandoLocalServiceTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class ExpandoLocalServiceTest extends BaseServiceTestCase {

	public void test1DeleteExpandoTable() throws Exception {
		try {
			ExpandoTable table = ExpandoTableServiceUtil.getTable(
					ExpandoLocalServiceTest.class.getName(), _TABLENAME);

			if (table != null) {
				ExpandoTableLocalServiceUtil.deleteExpandoTable(table.getTableId());
			}
		}
		catch (NoSuchTableException nste) {
		}
	}

	public void test2CreateExpandoTable() throws Exception {
		ExpandoTable table = ExpandoTableLocalServiceUtil.addTable(
			ExpandoLocalServiceTest.class.getName(), _TABLENAME);

		assertNotNull(table);
		assertEquals(table.getClassName(),
			ExpandoLocalServiceTest.class.getName());
		assertEquals(table.getName(), _TABLENAME);
	}

	public void test3CreateExpandoColumns() throws Exception {
		ExpandoTable table = ExpandoTableLocalServiceUtil.getTable(
			ExpandoLocalServiceTest.class.getName(), _TABLENAME);

		ExpandoColumn column1 = ExpandoColumnLocalServiceUtil.addColumn(
			table.getTableId(), _COLUMNNAME1, ExpandoColumnConstants.STRING);

		ExpandoColumn column2 = ExpandoColumnLocalServiceUtil.addColumn(
			table.getTableId(), _COLUMNNAME2, ExpandoColumnConstants.DOUBLE);

		assertNotNull(column1);
		assertEquals(column1.getName(), _COLUMNNAME1);
		assertEquals(column1.getType(), ExpandoColumnConstants.STRING);
		assertNotNull(column2);
		assertEquals(column2.getName(), _COLUMNNAME2);
		assertEquals(column2.getType(), ExpandoColumnConstants.DOUBLE);
	}

	public void test4CreateExpandoValuesForRow1() throws Exception {
		ExpandoValue value1 = new ExpandoValueImpl();

		value1.setClassName(ExpandoLocalServiceTest.class.getName());
		value1.setTableName(_TABLENAME);
		value1.setColumnName(_COLUMNNAME1);
		value1.setClassPK(_CLASSPK1);
		value1.setString(_VALUE1_COLUMN1);

		value1 = ExpandoValueLocalServiceUtil.addValue(value1);

		assertNotNull(value1);
		assertEquals(value1.getClassPK(), _CLASSPK1);
		assertEquals(value1.getString(), _VALUE1_COLUMN1);

		ExpandoValue value2 = new ExpandoValueImpl();

		value2.setClassName(ExpandoLocalServiceTest.class.getName());
		value2.setTableName(_TABLENAME);
		value2.setColumnName(_COLUMNNAME2);
		value2.setClassPK(_CLASSPK1);
		value2.setDouble(_VALUE1_COLUMN2);

		value2 = ExpandoValueLocalServiceUtil.addValue(value2);

		assertNotNull(value2);
		assertEquals(value2.getClassPK(), _CLASSPK1);
		assertEquals(value2.getDouble(), _VALUE1_COLUMN2);
	}

	public void test4CreateExpandoValuesForRow2() throws Exception {
		ExpandoValue value1 = new ExpandoValueImpl();

		value1.setClassName(ExpandoLocalServiceTest.class.getName());
		value1.setTableName(_TABLENAME);
		value1.setColumnName(_COLUMNNAME1);
		value1.setClassPK(_CLASSPK2);
		value1.setString(_VALUE2_COLUMN1);

		value1 = ExpandoValueLocalServiceUtil.addValue(value1);

		assertNotNull(value1);
		assertEquals(value1.getClassPK(), _CLASSPK2);
		assertEquals(value1.getString(), _VALUE2_COLUMN1);

		ExpandoValue value2 = new ExpandoValueImpl();

		value2.setClassName(ExpandoLocalServiceTest.class.getName());
		value2.setTableName(_TABLENAME);
		value2.setColumnName(_COLUMNNAME2);
		value2.setClassPK(_CLASSPK2);
		value2.setDouble(_VALUE2_COLUMN2);

		value2 = ExpandoValueLocalServiceUtil.addValue(value2);

		assertNotNull(value2);
		assertEquals(value2.getClassPK(), _CLASSPK2);
		assertEquals(value2.getDouble(), _VALUE2_COLUMN2);
	}

	public void test5ExpandoRowIteration() throws Exception {
		List<ExpandoRow> rows = ExpandoRowLocalServiceUtil.getRows(
			ExpandoLocalServiceTest.class.getName(), _TABLENAME,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		assertEquals(rows.size(), 2);

		for (ExpandoRow row : rows) {
			long classPK = row.getClassPK();

			List<ExpandoValue> values =
				ExpandoValueLocalServiceUtil.getRowValues(row.getRowId());

			assertEquals(values.size(), 2);
			assertEquals(values.get(0).getClassPK(), classPK);
			assertNotNull(values.get(0).getString());
			assertEquals(values.get(1).getClassPK(), classPK);
			assertTrue(values.get(1).getDouble() > 1.22e10);
		}
	}

	public void test6GetExpandoRowByClassPK() throws Exception {
		ExpandoRow row = ExpandoRowLocalServiceUtil.getRow(
			ExpandoLocalServiceTest.class.getName(), _TABLENAME, _CLASSPK1);

		assertNotNull(row);

		List<ExpandoValue> values =
			ExpandoValueLocalServiceUtil.getRowValues(row.getRowId());

		assertEquals(values.size(), 2);
		assertEquals(values.get(0).getClassPK(), _CLASSPK1);
		assertEquals(values.get(0).getString(), _VALUE1_COLUMN1);
		assertEquals(values.get(1).getClassPK(), _CLASSPK1);
		assertEquals(values.get(1).getDouble(), _VALUE1_COLUMN2);
	}

	public void test7GetExpandoValuesByClassPK() throws Exception {
		List<ExpandoValue> values =
			ExpandoValueLocalServiceUtil.getRowValues(
				ExpandoLocalServiceTest.class.getName(), _TABLENAME, _CLASSPK2,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS);

		assertEquals(values.size(), 2);
		assertEquals(values.get(0).getClassPK(), _CLASSPK2);
		assertEquals(values.get(0).getString(), _VALUE2_COLUMN1);
		assertEquals(values.get(1).getClassPK(), _CLASSPK2);
		assertEquals(values.get(1).getDouble(), _VALUE2_COLUMN2);
	}

	public void tes8DeleteExpandoTable() throws Exception {
		try {
			ExpandoTable table = ExpandoTableServiceUtil.getTable(
					ExpandoLocalServiceTest.class.getName(), _TABLENAME);

			if (table != null) {
				ExpandoTableLocalServiceUtil.deleteExpandoTable(table.getTableId());
			}
		}
		catch (NoSuchTableException nste) {
		}
	}

	private static final String _TABLENAME = "ExpandoLocalServiceTest";
	private static final String _COLUMNNAME1 = "column-1";
	private static final String _COLUMNNAME2 = "column-2";
	private static final long _CLASSPK1 = 1;
	private static final long _CLASSPK2 = 2;
	private static final String _VALUE1_COLUMN1 = "value-1";
	private static final double _VALUE1_COLUMN2 = 1.23e10;
	private static final String _VALUE2_COLUMN1 = "value-2";
	private static final double _VALUE2_COLUMN2 = 2.34e10;

}
