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

package com.liferay.portal.service.impl;

import com.liferay.portal.ExpandoTableNameException;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.ExpandoTable;
import com.liferay.portal.service.base.ExpandoTableLocalServiceBaseImpl;

import java.util.List;

/**
 * <a href="ExpandoTableLocalServiceImpl.java.html"><b><i>View Source</i></b>
 * </a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoTableLocalServiceImpl
	extends ExpandoTableLocalServiceBaseImpl {

	public ExpandoTable addTable(long classNameId, String name)
		throws PortalException, SystemException {

		validate(name);

		long tableId = counterLocalService.increment();

		ExpandoTable table = expandoTablePersistence.create(tableId);

		table.setClassNameId(classNameId);
		table.setName(name);

		expandoTablePersistence.update(table);

		return table;
	}

	public void deleteTable(long tableId)
		throws PortalException, SystemException {

		// ExpandoColumns

		expandoColumnLocalService.deleteTableColumns(tableId);

		// ExpandoTable

		expandoTablePersistence.remove(tableId);
	}

	public ExpandoTable getTable(long tableId)
		throws PortalException, SystemException {

		return expandoTablePersistence.findByPrimaryKey(tableId);
	}

	public ExpandoTable getTable(long classNameId, String name)
		throws PortalException, SystemException {

		return expandoTablePersistence.findByC_N(classNameId, name);
	}

	public List<ExpandoTable> getTables(long classNameId)
		throws PortalException, SystemException {

		return expandoTablePersistence.findByClassNameId(classNameId);
	}

	public ExpandoTable setTable(long classNameId, String name)
		throws PortalException, SystemException {

		ExpandoTable table =
			expandoTablePersistence.fetchByC_N(classNameId, name);

		if (table == null) {
			validate(name);

			long tableId = counterLocalService.increment();

			table = expandoTablePersistence.create(tableId);

			table.setClassNameId(classNameId);
			table.setName(name);

			expandoTablePersistence.update(table);
		}

		return table;
	}

	public ExpandoTable updateTableName(long tableId, String name)
		throws PortalException, SystemException {

		validate(name);

		ExpandoTable table =
			expandoTablePersistence.findByPrimaryKey(tableId);

		table.setName(name);

		return expandoTablePersistence.update(table);
	}

	protected void validate(String name)
		throws PortalException, SystemException {

		if (!Validator.isNull(name)) {
			throw new ExpandoTableNameException();
		}
	}

}