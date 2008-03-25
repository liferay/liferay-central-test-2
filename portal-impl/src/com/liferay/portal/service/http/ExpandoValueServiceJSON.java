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

package com.liferay.portal.service.http;

import com.liferay.portal.service.ExpandoValueServiceUtil;

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
 * <code>com.liferay.portal.service.ExpandoValueServiceUtil</code>
 * service utility. The static methods of this class calls the same methods of
 * the service utility. However, the signatures are different because it is
 * difficult for JSON to support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to a <code>org.json.JSONArray</code>. If the method in the
 * service utility returns a <code>com.liferay.portal.model.ExpandoValue</code>,
 * that is translated to a <code>org.json.JSONObject</code>. Methods that JSON
 * cannot safely use are skipped. The logic for the translation is encapsulated
 * in <code>com.liferay.portal.service.http.ExpandoValueJSONSerializer</code>.
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
 * @see com.liferay.portal.service.ExpandoValueServiceUtil
 * @see com.liferay.portal.service.http.ExpandoValueJSONSerializer
 *
 */
public class ExpandoValueServiceJSON {
	public static JSONObject addValue(long classPK, long columnId,
		java.lang.String value)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		com.liferay.portal.model.ExpandoValue returnValue = ExpandoValueServiceUtil.addValue(classPK,
				columnId, value);

		return ExpandoValueJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject getValue(long classPK, long columnId)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		com.liferay.portal.model.ExpandoValue returnValue = ExpandoValueServiceUtil.getValue(classPK,
				columnId);

		return ExpandoValueJSONSerializer.toJSONObject(returnValue);
	}

	public static void deleteValue(long valueId)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		ExpandoValueServiceUtil.deleteValue(valueId);
	}

	public static void deleteValue(long classPK, long columnId)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		ExpandoValueServiceUtil.deleteValue(classPK, columnId);
	}

	public static void deleteValues(long classPK)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		ExpandoValueServiceUtil.deleteValues(classPK);
	}

	public static void deleteColumnValues(long columnId)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		ExpandoValueServiceUtil.deleteColumnValues(columnId);
	}

	public static void deleteRowValues(long rowId)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		ExpandoValueServiceUtil.deleteRowValues(rowId);
	}

	public static JSONArray getColumnValues(long columnId)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		java.util.List<com.liferay.portal.model.ExpandoValue> returnValue = ExpandoValueServiceUtil.getColumnValues(columnId);

		return ExpandoValueJSONSerializer.toJSONArray(returnValue);
	}

	public static JSONArray getColumnValues(long columnId, int begin, int end)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		java.util.List<com.liferay.portal.model.ExpandoValue> returnValue = ExpandoValueServiceUtil.getColumnValues(columnId,
				begin, end);

		return ExpandoValueJSONSerializer.toJSONArray(returnValue);
	}

	public static int getColumnValuesCount(long columnId)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		int returnValue = ExpandoValueServiceUtil.getColumnValuesCount(columnId);

		return returnValue;
	}

	public static JSONArray getRowValues(long rowId)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		java.util.List<com.liferay.portal.model.ExpandoValue> returnValue = ExpandoValueServiceUtil.getRowValues(rowId);

		return ExpandoValueJSONSerializer.toJSONArray(returnValue);
	}

	public static JSONArray getRowValues(long rowId, int begin, int end)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		java.util.List<com.liferay.portal.model.ExpandoValue> returnValue = ExpandoValueServiceUtil.getRowValues(rowId,
				begin, end);

		return ExpandoValueJSONSerializer.toJSONArray(returnValue);
	}

	public static int getRowValuesCount(long rowId)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		int returnValue = ExpandoValueServiceUtil.getRowValuesCount(rowId);

		return returnValue;
	}

	public static JSONArray getValues(long classPK)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		java.util.List<com.liferay.portal.model.ExpandoValue> returnValue = ExpandoValueServiceUtil.getValues(classPK);

		return ExpandoValueJSONSerializer.toJSONArray(returnValue);
	}

	public static JSONArray getValues(long classPK, int begin, int end)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		java.util.List<com.liferay.portal.model.ExpandoValue> returnValue = ExpandoValueServiceUtil.getValues(classPK,
				begin, end);

		return ExpandoValueJSONSerializer.toJSONArray(returnValue);
	}

	public static int getValuesCount(long classPK)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		int returnValue = ExpandoValueServiceUtil.getValuesCount(classPK);

		return returnValue;
	}

	public static JSONObject setValue(long classPK, long columnId,
		java.lang.String value)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		com.liferay.portal.model.ExpandoValue returnValue = ExpandoValueServiceUtil.setValue(classPK,
				columnId, value);

		return ExpandoValueJSONSerializer.toJSONObject(returnValue);
	}

	public static long setRowValues(long tableId,
		java.util.List<com.liferay.portal.model.ExpandoValue> expandoValues)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		long returnValue = ExpandoValueServiceUtil.setRowValues(tableId,
				expandoValues);

		return returnValue;
	}

	public static long setRowValues(long tableId, long rowId,
		java.util.List<com.liferay.portal.model.ExpandoValue> expandoValues)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		long returnValue = ExpandoValueServiceUtil.setRowValues(tableId, rowId,
				expandoValues);

		return returnValue;
	}
}