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

package com.liferay.portlet.expando.service.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.CompanyThreadLocal;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.expando.DuplicateTableNameException;
import com.liferay.portlet.expando.TableNameException;
import com.liferay.portlet.expando.model.ExpandoTable;
import com.liferay.portlet.expando.model.ExpandoTableConstants;
import com.liferay.portlet.expando.service.base.ExpandoTableLocalServiceBaseImpl;

import java.util.List;

/**
 * <a href="ExpandoTableLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 */
public class ExpandoTableLocalServiceImpl
	extends ExpandoTableLocalServiceBaseImpl {

	public ExpandoTable addDefaultTable(long classNameId)
		throws PortalException, SystemException {

		return addTable(classNameId, ExpandoTableConstants.DEFAULT_TABLE_NAME);
	}

	public ExpandoTable addDefaultTable(String className)
		throws PortalException, SystemException {

		return addTable(className, ExpandoTableConstants.DEFAULT_TABLE_NAME);
	}

	public ExpandoTable addTable(long classNameId, String name)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		validate(companyId, 0, classNameId, name);

		long tableId = counterLocalService.increment();

		ExpandoTable table = expandoTablePersistence.create(tableId);

		table.setCompanyId(companyId);
		table.setClassNameId(classNameId);
		table.setName(name);

		expandoTablePersistence.update(table, false);

		return table;
	}

	public ExpandoTable addTable(String className, String name)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return addTable(classNameId, name);
	}

	public void deleteTable(ExpandoTable table) throws SystemException {

		// Table

		expandoTablePersistence.remove(table);

		// Columns

		runSQL(
			"delete from ExpandoColumn where tableId = " + table.getTableId());

		expandoColumnPersistence.clearCache();

		// Rows

		runSQL("delete from ExpandoRow where tableId = " + table.getTableId());

		expandoRowPersistence.clearCache();

		// Values

		runSQL(
			"delete from ExpandoValue where tableId = " + table.getTableId());

		expandoValuePersistence.clearCache();
	}

	public void deleteTable(long tableId)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTablePersistence.findByPrimaryKey(tableId);

		deleteTable(table);
	}

	public void deleteTable(long classNameId, String name)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		ExpandoTable table = expandoTablePersistence.findByC_C_N(
			companyId, classNameId, name);

		deleteTable(table);
	}

	public void deleteTable(String className, String name)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		deleteTable(classNameId, name);
	}

	public void deleteTables(long classNameId) throws SystemException {
		long companyId = CompanyThreadLocal.getCompanyId();

		List<ExpandoTable> tables = expandoTablePersistence.findByC_C(
			companyId, classNameId);

		for (ExpandoTable table : tables) {
			deleteTable(table);
		}
	}

	public void deleteTables(String className) throws SystemException {
		long classNameId = PortalUtil.getClassNameId(className);

		deleteTables(classNameId);
	}

	public ExpandoTable getDefaultTable(long classNameId)
		throws PortalException, SystemException {

		return getTable(classNameId, ExpandoTableConstants.DEFAULT_TABLE_NAME);
	}

	public ExpandoTable getDefaultTable(String className)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getTable(classNameId, ExpandoTableConstants.DEFAULT_TABLE_NAME);
	}

	public ExpandoTable getTable(long tableId)
		throws PortalException, SystemException {

		return expandoTablePersistence.findByPrimaryKey(tableId);
	}

	public ExpandoTable getTable(long classNameId, String name)
		throws PortalException, SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoTablePersistence.findByC_C_N(
			companyId, classNameId, name);
	}

	public ExpandoTable getTable(String className, String name)
		throws PortalException, SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getTable(classNameId, name);
	}

	public List<ExpandoTable> getTables(long classNameId)
		throws SystemException {

		long companyId = CompanyThreadLocal.getCompanyId();

		return expandoTablePersistence.findByC_C(companyId, classNameId);
	}

	public List<ExpandoTable> getTables(String className)
		throws SystemException {

		long classNameId = PortalUtil.getClassNameId(className);

		return getTables(classNameId);
	}

	public ExpandoTable updateTable(long tableId, String name)
		throws PortalException, SystemException {

		ExpandoTable table = expandoTablePersistence.findByPrimaryKey(tableId);

		if (table.getName().equals(ExpandoTableConstants.DEFAULT_TABLE_NAME)) {
			throw new TableNameException(
				"Cannot rename " + ExpandoTableConstants.DEFAULT_TABLE_NAME);
		}

		validate(table.getCompanyId(), tableId, table.getClassNameId(), name);

		table.setName(name);

		return expandoTablePersistence.update(table, false);
	}

	protected void validate(
			long companyId, long tableId, long classNameId, String name)
		throws PortalException, SystemException {

		if (Validator.isNull(name)) {
			throw new TableNameException();
		}

		ExpandoTable table = expandoTablePersistence.fetchByC_C_N(
			companyId, classNameId, name);

		if ((table != null) && (table.getTableId() != tableId)) {
			throw new DuplicateTableNameException();
		}
	}

}