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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.NullWrapper;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

import com.liferay.portlet.expando.service.ExpandoColumnServiceUtil;

/**
 * <a href="ExpandoColumnServiceHttp.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a HTTP utility for the
 * <code>com.liferay.portlet.expando.service.ExpandoColumnServiceUtil</code> service
 * utility. The static methods of this class calls the same methods of the
 * service utility. However, the signatures are different because it requires an
 * additional <code>com.liferay.portal.security.auth.HttpPrincipal</code>
 * parameter.
 * </p>
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <code>tunnel.servlet.hosts.allowed</code> in
 * portal.properties to configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.security.auth.HttpPrincipal
 * @see com.liferay.portlet.expando.service.ExpandoColumnServiceUtil
 * @see com.liferay.portlet.expando.service.http.ExpandoColumnServiceSoap
 *
 */
public class ExpandoColumnServiceHttp {
	public static com.liferay.portlet.expando.model.ExpandoColumn addColumn(
		HttpPrincipal httpPrincipal, long tableId, java.lang.String name,
		int type)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(tableId);

			Object paramObj1 = name;

			if (name == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = new IntegerWrapper(type);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoColumnServiceUtil.class.getName(),
					"addColumn",
					new Object[] { paramObj0, paramObj1, paramObj2 });

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (com.liferay.portlet.expando.model.ExpandoColumn)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteColumn(HttpPrincipal httpPrincipal, long columnId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(columnId);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoColumnServiceUtil.class.getName(),
					"deleteColumn", new Object[] { paramObj0 });

			try {
				TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteColumns(HttpPrincipal httpPrincipal,
		java.lang.String className, java.lang.String tableName)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = className;

			if (className == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = tableName;

			if (tableName == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoColumnServiceUtil.class.getName(),
					"deleteColumns", new Object[] { paramObj0, paramObj1 });

			try {
				TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteColumns(HttpPrincipal httpPrincipal, long tableId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(tableId);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoColumnServiceUtil.class.getName(),
					"deleteColumns", new Object[] { paramObj0 });

			try {
				TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn getColumn(
		HttpPrincipal httpPrincipal, long columnId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(columnId);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoColumnServiceUtil.class.getName(),
					"getColumn", new Object[] { paramObj0 });

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (com.liferay.portlet.expando.model.ExpandoColumn)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn getColumn(
		HttpPrincipal httpPrincipal, long tableId, java.lang.String name)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(tableId);

			Object paramObj1 = name;

			if (name == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoColumnServiceUtil.class.getName(),
					"getColumn", new Object[] { paramObj0, paramObj1 });

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (com.liferay.portlet.expando.model.ExpandoColumn)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn getColumn(
		HttpPrincipal httpPrincipal, java.lang.String className,
		java.lang.String tableName, java.lang.String name)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = className;

			if (className == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = tableName;

			if (tableName == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = name;

			if (name == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoColumnServiceUtil.class.getName(),
					"getColumn",
					new Object[] { paramObj0, paramObj1, paramObj2 });

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (com.liferay.portlet.expando.model.ExpandoColumn)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getColumns(
		HttpPrincipal httpPrincipal, long tableId)
		throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(tableId);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoColumnServiceUtil.class.getName(),
					"getColumns", new Object[] { paramObj0 });

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.expando.model.ExpandoColumn>)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getColumns(
		HttpPrincipal httpPrincipal, java.lang.String className,
		java.lang.String tableName) throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = className;

			if (className == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = tableName;

			if (tableName == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoColumnServiceUtil.class.getName(),
					"getColumns", new Object[] { paramObj0, paramObj1 });

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.expando.model.ExpandoColumn>)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getColumnsCount(HttpPrincipal httpPrincipal, long tableId)
		throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(tableId);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoColumnServiceUtil.class.getName(),
					"getColumnsCount", new Object[] { paramObj0 });

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getColumnsCount(HttpPrincipal httpPrincipal,
		java.lang.String className, java.lang.String tableName)
		throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = className;

			if (className == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = tableName;

			if (tableName == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoColumnServiceUtil.class.getName(),
					"getColumnsCount", new Object[] { paramObj0, paramObj1 });

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn getDefaultTableColumn(
		HttpPrincipal httpPrincipal, java.lang.String className,
		java.lang.String name)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = className;

			if (className == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = name;

			if (name == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoColumnServiceUtil.class.getName(),
					"getDefaultTableColumn",
					new Object[] { paramObj0, paramObj1 });

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (com.liferay.portlet.expando.model.ExpandoColumn)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoColumn> getDefaultTableColumns(
		HttpPrincipal httpPrincipal, java.lang.String className)
		throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = className;

			if (className == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoColumnServiceUtil.class.getName(),
					"getDefaultTableColumns", new Object[] { paramObj0 });

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.expando.model.ExpandoColumn>)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getDefaultTableColumnsCount(HttpPrincipal httpPrincipal,
		java.lang.String className) throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = className;

			if (className == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoColumnServiceUtil.class.getName(),
					"getDefaultTableColumnsCount", new Object[] { paramObj0 });

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoColumn updateColumn(
		HttpPrincipal httpPrincipal, long columnId, java.lang.String name,
		int type)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(columnId);

			Object paramObj1 = name;

			if (name == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = new IntegerWrapper(type);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoColumnServiceUtil.class.getName(),
					"updateColumn",
					new Object[] { paramObj0, paramObj1, paramObj2 });

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (com.liferay.portlet.expando.model.ExpandoColumn)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ExpandoColumnServiceHttp.class);
}