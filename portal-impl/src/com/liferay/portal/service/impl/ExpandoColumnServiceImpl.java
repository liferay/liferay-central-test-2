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

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.ExpandoColumn;
import com.liferay.portal.service.base.ExpandoColumnServiceBaseImpl;

import java.util.List;
import java.util.Properties;

/**
 * <a href="ExpandoColumnServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoColumnServiceImpl extends ExpandoColumnServiceBaseImpl {

	public ExpandoColumn addColumn(long classNameId, String name, int type)
		throws PortalException, SystemException {

		return expandoColumnLocalService.addColumn(
			classNameId, name, type, null);
	}

	public ExpandoColumn addColumn(
			long classNameId, String name, int type, Properties properties)
		throws PortalException, SystemException {

		return expandoColumnLocalService.addColumn(
				classNameId, name, type, properties);
	}

	public void addTableColumns(long tableId, long[] columnIds)
		throws PortalException, SystemException {

		expandoColumnLocalService.addTableColumns(tableId, columnIds);
	}

	public void addTableColumns(long tableId, List<ExpandoColumn> columns)
		throws PortalException, SystemException {

		expandoColumnLocalService.addTableColumns(tableId, columns);
	}

	public void deleteColumn(long columnId)
		throws PortalException, SystemException {

		expandoColumnLocalService.deleteColumn(columnId);
	}

	public void deleteTableColumns(long tableId)
		throws PortalException, SystemException {

		expandoColumnLocalService.deleteTableColumns(tableId);
	}

	public void deleteTableColumns(long tableId, long[] columnIds)
		throws PortalException, SystemException {

		expandoColumnLocalService.deleteTableColumns(tableId, columnIds);
	}

	public void deleteTableColumns(long tableId, List<ExpandoColumn> columns)
		throws PortalException, SystemException {

		expandoColumnLocalService.deleteTableColumns(tableId, columns);
	}

	public ExpandoColumn getColumn(long columnId)
		throws PortalException, SystemException {

		return expandoColumnLocalService.getColumn(columnId);
	}

	public ExpandoColumn getColumn(long classNameId, String name)
		throws PortalException, SystemException {

		return expandoColumnLocalService.getColumn(classNameId, name);
	}

	public List<ExpandoColumn> getColumns(long classNameId)
		throws PortalException, SystemException {

		return expandoColumnLocalService.getColumns(classNameId);
	}

	public List<ExpandoColumn> getColumns(long classNameId, int begin, int end)
		throws PortalException, SystemException {

		return expandoColumnLocalService.getColumns(classNameId, begin, end);
	}

	public int getColumnsCount(long classNameId)
		throws PortalException, SystemException {

		return expandoColumnLocalService.getColumnsCount(classNameId);
	}

	public List<ExpandoColumn> getTableColumns(long tableId)
		throws PortalException, SystemException {

		return expandoColumnLocalService.getTableColumns(tableId);
	}

	public List<ExpandoColumn> getTableColumns(long tableId, int begin, int end)
		throws PortalException, SystemException {

		return expandoColumnLocalService.getTableColumns(tableId, begin, end);
	}

	public int getTableColumnsCount(long tableId)
		throws PortalException, SystemException {

		return expandoColumnLocalService.getTableColumnsCount(tableId);
	}

	public ExpandoColumn setColumn(long classNameId, String name, int type)
		throws PortalException, SystemException {

		return expandoColumnLocalService.setColumn(
			classNameId, name, type, null);
	}

	public ExpandoColumn setColumn(
			long classNameId, String name, int type, Properties properties)
		throws PortalException, SystemException {

		return expandoColumnLocalService.setColumn(
			classNameId, name, type, properties);
	}

	public ExpandoColumn updateColumnType(long columnId, int type)
		throws PortalException, SystemException {

		return expandoColumnLocalService.updateColumnType(columnId, type);
	}

	public ExpandoColumn updateColumnName(long columnId, String name)
		throws PortalException, SystemException {

		return expandoColumnLocalService.updateColumnName(columnId, name);
	}

	public ExpandoColumn updateColumnProperties(
			long columnId, Properties properties)
		throws PortalException, SystemException {

		return expandoColumnLocalService.updateColumnProperties(
			columnId, properties);
	}

}