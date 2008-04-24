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

package com.liferay.portlet.expando.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portlet.expando.model.ExpandoColumn;
import com.liferay.portlet.expando.service.base.ExpandoColumnServiceBaseImpl;

import java.util.List;

/**
 * <a href="ExpandoColumnServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 * @author Brian Wing Shun Chan
 *
 */
public class ExpandoColumnServiceImpl extends ExpandoColumnServiceBaseImpl {

	public ExpandoColumn addColumn(long tableId, String name, int type)
		throws PortalException, SystemException {

		return expandoColumnLocalService.addColumn(tableId, name, type);
	}

	public void deleteColumn(long columnId)
		throws PortalException, SystemException {

		expandoColumnLocalService.deleteColumn(columnId);
	}

	public void deleteColumn(long tableId, String name)
		throws PortalException, SystemException {

		expandoColumnLocalService.deleteColumn(tableId, name);
	}

	public void deleteColumn(String className, String tableName, String name)
		throws PortalException, SystemException {

		expandoColumnLocalService.deleteColumn(className, tableName, name);
	}

	public void deleteColumn(long classNameId, String tableName, String name)
		throws PortalException, SystemException {

		expandoColumnLocalService.deleteColumn(classNameId, tableName, name);
	}

	public void deleteColumns(long tableId)
		throws PortalException, SystemException {

		expandoColumnLocalService.deleteColumns(tableId);
	}

	public void deleteColumns(String className, String tableName)
		throws PortalException, SystemException {

		expandoColumnLocalService.deleteColumns(className, tableName);
	}

	public void deleteColumns(long classNameId, String tableName)
		throws PortalException, SystemException {

		expandoColumnLocalService.deleteColumns(classNameId, tableName);
	}

	public ExpandoColumn getColumn(long columnId)
		throws PortalException, SystemException {

		return expandoColumnLocalService.getColumn(columnId);
	}

	public ExpandoColumn getColumn(long tableId, String name)
		throws PortalException, SystemException {

		return expandoColumnLocalService.getColumn(tableId, name);
	}

	public ExpandoColumn getColumn(
			String className, String tableName, String name)
		throws PortalException, SystemException {

		return expandoColumnLocalService.getColumn(className, tableName, name);
	}

	public ExpandoColumn getColumn(
			long classNameId, String tableName, String name)
		throws PortalException, SystemException {

		return expandoColumnLocalService.getColumn(
			classNameId, tableName, name);
	}

	public List<ExpandoColumn> getColumns(long tableId)
		throws SystemException {

		return expandoColumnLocalService.getColumns(tableId);
	}

	public List<ExpandoColumn> getColumns(String className, String tableName)
		throws SystemException {

		return expandoColumnLocalService.getColumns(className, tableName);
	}

	public List<ExpandoColumn> getColumns(long classNameId, String tableName)
		throws SystemException {

		return expandoColumnLocalService.getColumns(classNameId, tableName);
	}

	public int getColumnsCount(long tableId) throws SystemException {
		return expandoColumnLocalService.getColumnsCount(tableId);
	}

	public int getColumnsCount(String className, String tableName)
		throws SystemException {

		return expandoColumnLocalService.getColumnsCount(className, tableName);
	}

	public int getColumnsCount(long classNameId, String tableName)
		throws SystemException {

		return expandoColumnLocalService.getColumnsCount(
			classNameId, tableName);
	}

	public ExpandoColumn getDefaultTableColumn(String className, String name)
		throws PortalException, SystemException {

		return expandoColumnLocalService.getDefaultTableColumn(className, name);
	}

	public ExpandoColumn getDefaultTableColumn(long classNameId, String name)
		throws PortalException, SystemException {

		return expandoColumnLocalService.getDefaultTableColumn(
			classNameId, name);
	}

	public List<ExpandoColumn> getDefaultTableColumns(String className)
		throws SystemException {

		return expandoColumnLocalService.getDefaultTableColumns(className);
	}

	public List<ExpandoColumn> getDefaultTableColumns(long classNameId)
		throws SystemException {

		return expandoColumnLocalService.getDefaultTableColumns(classNameId);
	}

	public int getDefaultTableColumnsCount(String className)
		throws SystemException {

		return expandoColumnLocalService.getDefaultTableColumnsCount(className);
	}

	public int getDefaultTableColumnsCount(long classNameId)
		throws SystemException {

		return expandoColumnLocalService.getDefaultTableColumnsCount(
			classNameId);
	}

	public ExpandoColumn updateColumn(long columnId, String name, int type)
		throws PortalException, SystemException {

		return expandoColumnLocalService.updateColumn(columnId, name, type);
	}

}