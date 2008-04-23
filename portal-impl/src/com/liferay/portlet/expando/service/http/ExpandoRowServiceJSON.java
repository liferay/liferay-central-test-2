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

package com.liferay.portlet.expando.service.http;

import com.liferay.portlet.expando.service.ExpandoRowServiceUtil;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * <a href="ExpandoRowServiceJSON.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a JSON utility for the
 * <code>com.liferay.portlet.expando.service.ExpandoRowServiceUtil</code>
 * service utility. The static methods of this class calls the same methods of
 * the service utility. However, the signatures are different because it is
 * difficult for JSON to support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to a <code>org.json.JSONArray</code>. If the method in the
 * service utility returns a <code>com.liferay.portlet.expando.model.ExpandoRow</code>,
 * that is translated to a <code>org.json.JSONObject</code>. Methods that JSON
 * cannot safely use are skipped. The logic for the translation is encapsulated
 * in <code>com.liferay.portlet.expando.service.http.ExpandoRowJSONSerializer</code>.
 * </p>
 *
 * <p>
 * This allows you to call the the backend services directly from JavaScript.
 * See <code>portal-web/docroot/html/portlet/tags_admin/unpacked.js</code> for a
 * reference of how that portlet uses the generated JavaScript in
 * <code>portal-web/docroot/html/js/service.js</code> to call the backend
 * services directly from JavaScript.
 * </p>
 *
 * <p>
 * The JSON utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.expando.service.ExpandoRowServiceUtil
 * @see com.liferay.portlet.expando.service.http.ExpandoRowJSONSerializer
 *
 */
public class ExpandoRowServiceJSON {
	public static JSONObject addRow(long tableId, long classPK)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		com.liferay.portlet.expando.model.ExpandoRow returnValue = ExpandoRowServiceUtil.addRow(tableId,
				classPK);

		return ExpandoRowJSONSerializer.toJSONObject(returnValue);
	}

	public static void deleteRow(long rowId)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		ExpandoRowServiceUtil.deleteRow(rowId);
	}

	public static void deleteRow(long tableId, long classPK)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		ExpandoRowServiceUtil.deleteRow(tableId, classPK);
	}

	public static void deleteRow(java.lang.String className,
		java.lang.String tableName, long classPK)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		ExpandoRowServiceUtil.deleteRow(className, tableName, classPK);
	}

	public static void deleteRow(long classNameId, java.lang.String tableName,
		long classPK)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		ExpandoRowServiceUtil.deleteRow(classNameId, tableName, classPK);
	}

	public static JSONArray getDefaultTableRows(java.lang.String className,
		int begin, int end)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException {
		java.util.List<com.liferay.portlet.expando.model.ExpandoRow> returnValue =
			ExpandoRowServiceUtil.getDefaultTableRows(className, begin, end);

		return ExpandoRowJSONSerializer.toJSONArray(returnValue);
	}

	public static JSONArray getDefaultTableRows(long classNameId, int begin,
		int end)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException {
		java.util.List<com.liferay.portlet.expando.model.ExpandoRow> returnValue =
			ExpandoRowServiceUtil.getDefaultTableRows(classNameId, begin, end);

		return ExpandoRowJSONSerializer.toJSONArray(returnValue);
	}

	public static int getDefaultTableRowsCount(java.lang.String className)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException {
		int returnValue = ExpandoRowServiceUtil.getDefaultTableRowsCount(className);

		return returnValue;
	}

	public static int getDefaultTableRowsCount(long classNameId)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException {
		int returnValue = ExpandoRowServiceUtil.getDefaultTableRowsCount(classNameId);

		return returnValue;
	}

	public static JSONObject getRow(long rowId)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		com.liferay.portlet.expando.model.ExpandoRow returnValue = ExpandoRowServiceUtil.getRow(rowId);

		return ExpandoRowJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject getRow(long tableId, long classPK)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		com.liferay.portlet.expando.model.ExpandoRow returnValue = ExpandoRowServiceUtil.getRow(tableId,
				classPK);

		return ExpandoRowJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject getRow(java.lang.String className,
		java.lang.String tableName, long classPK)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		com.liferay.portlet.expando.model.ExpandoRow returnValue = ExpandoRowServiceUtil.getRow(className,
				tableName, classPK);

		return ExpandoRowJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject getRow(long classNameId,
		java.lang.String tableName, long classPK)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		com.liferay.portlet.expando.model.ExpandoRow returnValue = ExpandoRowServiceUtil.getRow(classNameId,
				tableName, classPK);

		return ExpandoRowJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONArray getRows(long tableId, int begin, int end)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException {
		java.util.List<com.liferay.portlet.expando.model.ExpandoRow> returnValue =
			ExpandoRowServiceUtil.getRows(tableId, begin, end);

		return ExpandoRowJSONSerializer.toJSONArray(returnValue);
	}

	public static JSONArray getRows(java.lang.String className,
		java.lang.String tableName, int begin, int end)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException {
		java.util.List<com.liferay.portlet.expando.model.ExpandoRow> returnValue =
			ExpandoRowServiceUtil.getRows(className, tableName, begin, end);

		return ExpandoRowJSONSerializer.toJSONArray(returnValue);
	}

	public static JSONArray getRows(long classNameId,
		java.lang.String tableName, int begin, int end)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException {
		java.util.List<com.liferay.portlet.expando.model.ExpandoRow> returnValue =
			ExpandoRowServiceUtil.getRows(classNameId, tableName, begin, end);

		return ExpandoRowJSONSerializer.toJSONArray(returnValue);
	}

	public static int getRowsCount(long tableId)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException {
		int returnValue = ExpandoRowServiceUtil.getRowsCount(tableId);

		return returnValue;
	}

	public static int getRowsCount(java.lang.String className,
		java.lang.String tableName)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException {
		int returnValue = ExpandoRowServiceUtil.getRowsCount(className,
				tableName);

		return returnValue;
	}

	public static int getRowsCount(long classNameId, java.lang.String tableName)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException {
		int returnValue = ExpandoRowServiceUtil.getRowsCount(classNameId,
				tableName);

		return returnValue;
	}
}