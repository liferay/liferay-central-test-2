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

import com.liferay.portlet.expando.service.ExpandoTableServiceUtil;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * <a href="ExpandoTableServiceJSON.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a JSON utility for the
 * <code>com.liferay.portlet.expando.service.ExpandoTableServiceUtil</code>
 * service utility. The static methods of this class calls the same methods of
 * the service utility. However, the signatures are different because it is
 * difficult for JSON to support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to a <code>org.json.JSONArray</code>. If the method in the
 * service utility returns a <code>com.liferay.portlet.expando.model.ExpandoTable</code>,
 * that is translated to a <code>org.json.JSONObject</code>. Methods that JSON
 * cannot safely use are skipped. The logic for the translation is encapsulated
 * in <code>com.liferay.portlet.expando.service.http.ExpandoTableJSONSerializer</code>.
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
 * @see com.liferay.portlet.expando.service.ExpandoTableServiceUtil
 * @see com.liferay.portlet.expando.service.http.ExpandoTableJSONSerializer
 *
 */
public class ExpandoTableServiceJSON {
	public static JSONObject addDefaultTable(java.lang.String className)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		com.liferay.portlet.expando.model.ExpandoTable returnValue = ExpandoTableServiceUtil.addDefaultTable(className);

		return ExpandoTableJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject addDefaultTable(long classNameId)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		com.liferay.portlet.expando.model.ExpandoTable returnValue = ExpandoTableServiceUtil.addDefaultTable(classNameId);

		return ExpandoTableJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject addTable(java.lang.String className,
		java.lang.String name)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		com.liferay.portlet.expando.model.ExpandoTable returnValue = ExpandoTableServiceUtil.addTable(className,
				name);

		return ExpandoTableJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject addTable(long classNameId, java.lang.String name)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		com.liferay.portlet.expando.model.ExpandoTable returnValue = ExpandoTableServiceUtil.addTable(classNameId,
				name);

		return ExpandoTableJSONSerializer.toJSONObject(returnValue);
	}

	public static void deleteTable(long tableId)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		ExpandoTableServiceUtil.deleteTable(tableId);
	}

	public static void deleteTable(java.lang.String className,
		java.lang.String name)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		ExpandoTableServiceUtil.deleteTable(className, name);
	}

	public static void deleteTable(long classNameId, java.lang.String name)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		ExpandoTableServiceUtil.deleteTable(classNameId, name);
	}

	public static void deleteTables(java.lang.String className)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		ExpandoTableServiceUtil.deleteTables(className);
	}

	public static void deleteTables(long classNameId)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		ExpandoTableServiceUtil.deleteTables(classNameId);
	}

	public static JSONObject getDefaultTable(java.lang.String className)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		com.liferay.portlet.expando.model.ExpandoTable returnValue = ExpandoTableServiceUtil.getDefaultTable(className);

		return ExpandoTableJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject getDefaultTable(long classNameId)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		com.liferay.portlet.expando.model.ExpandoTable returnValue = ExpandoTableServiceUtil.getDefaultTable(classNameId);

		return ExpandoTableJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject getTable(long tableId)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		com.liferay.portlet.expando.model.ExpandoTable returnValue = ExpandoTableServiceUtil.getTable(tableId);

		return ExpandoTableJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject getTable(java.lang.String className,
		java.lang.String name)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		com.liferay.portlet.expando.model.ExpandoTable returnValue = ExpandoTableServiceUtil.getTable(className,
				name);

		return ExpandoTableJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONObject getTable(long classNameId, java.lang.String name)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		com.liferay.portlet.expando.model.ExpandoTable returnValue = ExpandoTableServiceUtil.getTable(classNameId,
				name);

		return ExpandoTableJSONSerializer.toJSONObject(returnValue);
	}

	public static JSONArray getTables(java.lang.String className)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		java.util.List<com.liferay.portlet.expando.model.ExpandoTable> returnValue =
			ExpandoTableServiceUtil.getTables(className);

		return ExpandoTableJSONSerializer.toJSONArray(returnValue);
	}

	public static JSONArray getTables(long classNameId)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		java.util.List<com.liferay.portlet.expando.model.ExpandoTable> returnValue =
			ExpandoTableServiceUtil.getTables(classNameId);

		return ExpandoTableJSONSerializer.toJSONArray(returnValue);
	}

	public static JSONObject updateTable(long tableId, java.lang.String name)
		throws java.rmi.RemoteException, com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		com.liferay.portlet.expando.model.ExpandoTable returnValue = ExpandoTableServiceUtil.updateTable(tableId,
				name);

		return ExpandoTableJSONSerializer.toJSONObject(returnValue);
	}
}