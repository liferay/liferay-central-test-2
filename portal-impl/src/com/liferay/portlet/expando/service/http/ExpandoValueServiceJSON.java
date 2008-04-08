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

import com.liferay.portlet.expando.service.ExpandoValueServiceUtil;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * <a href="ExpandoValueServiceJSON.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a JSON utility for the
 * <code>com.liferay.portlet.expando.service.ExpandoValueServiceUtil</code>
 * service utility. The static methods of this class calls the same methods of
 * the service utility. However, the signatures are different because it is
 * difficult for JSON to support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to a <code>org.json.JSONArray</code>. If the method in the
 * service utility returns a <code>com.liferay.portlet.expando.model.ExpandoValue</code>,
 * that is translated to a <code>org.json.JSONObject</code>. Methods that JSON
 * cannot safely use are skipped. The logic for the translation is encapsulated
 * in <code>com.liferay.portlet.expando.service.http.ExpandoValueJSONSerializer</code>.
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
 * @see com.liferay.portlet.expando.service.ExpandoValueServiceUtil
 * @see com.liferay.portlet.expando.service.http.ExpandoValueJSONSerializer
 *
 */
public class ExpandoValueServiceJSON {
	public static JSONObject addValue(long columnId, long classPK, long rowId,
		java.lang.String data)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		com.liferay.portlet.expando.model.ExpandoValue returnValue = ExpandoValueServiceUtil.addValue(columnId,
				classPK, rowId, data);

		return ExpandoValueJSONSerializer.toJSONObject(returnValue);
	}

	public static void deleteColumnValues(long columnId)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException {
		ExpandoValueServiceUtil.deleteColumnValues(columnId);
	}

	public static void deleteRowValues(long rowId)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException {
		ExpandoValueServiceUtil.deleteRowValues(rowId);
	}

	public static void deleteTableValues(long tableId)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException {
		ExpandoValueServiceUtil.deleteTableValues(tableId);
	}

	public static void deleteValue(long valueId)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		ExpandoValueServiceUtil.deleteValue(valueId);
	}

	public static void deleteValues(java.lang.String className, long classPK)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		ExpandoValueServiceUtil.deleteValues(className, classPK);
	}

	public static void deleteValues(long classNameId, long classPK)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		ExpandoValueServiceUtil.deleteValues(classNameId, classPK);
	}

	public static JSONArray getColumnValues(long columnId, int begin, int end)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException {
		java.util.List<com.liferay.portlet.expando.model.ExpandoValue> returnValue =
			ExpandoValueServiceUtil.getColumnValues(columnId, begin, end);

		return ExpandoValueJSONSerializer.toJSONArray(returnValue);
	}

	public static JSONArray getColumnValues(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, int begin,
		int end)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException {
		java.util.List<com.liferay.portlet.expando.model.ExpandoValue> returnValue =
			ExpandoValueServiceUtil.getColumnValues(className, tableName,
				columnName, begin, end);

		return ExpandoValueJSONSerializer.toJSONArray(returnValue);
	}

	public static int getColumnValuesCount(long columnId)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException {
		int returnValue = ExpandoValueServiceUtil.getColumnValuesCount(columnId);

		return returnValue;
	}

	public static int getColumnValuesCount(java.lang.String className,
		java.lang.String tableName, java.lang.String columnName)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException {
		int returnValue = ExpandoValueServiceUtil.getColumnValuesCount(className,
				tableName, columnName);

		return returnValue;
	}

	public static JSONArray getDefaultTableColumnValues(
		java.lang.String className, java.lang.String columnName, int begin,
		int end)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException {
		java.util.List<com.liferay.portlet.expando.model.ExpandoValue> returnValue =
			ExpandoValueServiceUtil.getDefaultTableColumnValues(className,
				columnName, begin, end);

		return ExpandoValueJSONSerializer.toJSONArray(returnValue);
	}

	public static int getDefaultTableColumnValuesCount(
		java.lang.String className, java.lang.String columnName)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException {
		int returnValue = ExpandoValueServiceUtil.getDefaultTableColumnValuesCount(className,
				columnName);

		return returnValue;
	}

	public static JSONArray getRowValues(long rowId)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException {
		java.util.List<com.liferay.portlet.expando.model.ExpandoValue> returnValue =
			ExpandoValueServiceUtil.getRowValues(rowId);

		return ExpandoValueJSONSerializer.toJSONArray(returnValue);
	}

	public static JSONArray getRowValues(long rowId, int begin, int end)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException {
		java.util.List<com.liferay.portlet.expando.model.ExpandoValue> returnValue =
			ExpandoValueServiceUtil.getRowValues(rowId, begin, end);

		return ExpandoValueJSONSerializer.toJSONArray(returnValue);
	}

	public static int getRowValuesCount(long rowId)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException {
		int returnValue = ExpandoValueServiceUtil.getRowValuesCount(rowId);

		return returnValue;
	}

	public static JSONObject getValue(long valueId)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		com.liferay.portlet.expando.model.ExpandoValue returnValue = ExpandoValueServiceUtil.getValue(valueId);

		return ExpandoValueJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject getValue(long columnId, long rowId)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		com.liferay.portlet.expando.model.ExpandoValue returnValue = ExpandoValueServiceUtil.getValue(columnId,
				rowId);

		return ExpandoValueJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject getValue(java.lang.String className,
		java.lang.String tableName, java.lang.String name, long rowId)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		com.liferay.portlet.expando.model.ExpandoValue returnValue = ExpandoValueServiceUtil.getValue(className,
				tableName, name, rowId);

		return ExpandoValueJSONSerializer.toJSONObject(returnValue);
	}
}