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

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.NullWrapper;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.ExpandoTableRowServiceUtil;

/**
 * <a href="ExpandoTableRowServiceHttp.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a HTTP utility for the
 * <code>com.liferay.portal.service.ExpandoTableRowServiceUtil</code> service
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
 * @see com.liferay.portal.service.ExpandoTableRowServiceUtil
 * @see com.liferay.portal.service.http.ExpandoTableRowServiceSoap
 *
 */
public class ExpandoTableRowServiceHttp {
	public static com.liferay.portal.model.ExpandoTableRow addRow(
		HttpPrincipal httpPrincipal, long tableId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(tableId);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoTableRowServiceUtil.class.getName(),
					"addRow", new Object[] { paramObj0 });

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

			return (com.liferay.portal.model.ExpandoTableRow)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteRow(HttpPrincipal httpPrincipal, long rowId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(rowId);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoTableRowServiceUtil.class.getName(),
					"deleteRow", new Object[] { paramObj0 });

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

	public static void deleteRows(HttpPrincipal httpPrincipal, long tableId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(tableId);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoTableRowServiceUtil.class.getName(),
					"deleteRows", new Object[] { paramObj0 });

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

	public static void deleteRows(HttpPrincipal httpPrincipal, long tableId,
		long[] rowIds)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(tableId);

			Object paramObj1 = rowIds;

			if (rowIds == null) {
				paramObj1 = new NullWrapper("[J");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoTableRowServiceUtil.class.getName(),
					"deleteRows", new Object[] { paramObj0, paramObj1 });

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

	public static com.liferay.portal.model.ExpandoTableRow getRow(
		HttpPrincipal httpPrincipal, long rowId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(rowId);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoTableRowServiceUtil.class.getName(),
					"getRow", new Object[] { paramObj0 });

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

			return (com.liferay.portal.model.ExpandoTableRow)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.model.ExpandoTableRow> getRows(
		HttpPrincipal httpPrincipal, long tableId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(tableId);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoTableRowServiceUtil.class.getName(),
					"getRows", new Object[] { paramObj0 });

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

			return (java.util.List<com.liferay.portal.model.ExpandoTableRow>)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.model.ExpandoTableRow> getRows(
		HttpPrincipal httpPrincipal, long tableId, int begin, int end)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(tableId);

			Object paramObj1 = new IntegerWrapper(begin);

			Object paramObj2 = new IntegerWrapper(end);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoTableRowServiceUtil.class.getName(),
					"getRows", new Object[] { paramObj0, paramObj1, paramObj2 });

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

			return (java.util.List<com.liferay.portal.model.ExpandoTableRow>)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getRowsCount(HttpPrincipal httpPrincipal, long tableId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(tableId);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoTableRowServiceUtil.class.getName(),
					"getRowsCount", new Object[] { paramObj0 });

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

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.model.ExpandoTableRow setRow(
		HttpPrincipal httpPrincipal, long tableId, long rowId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(tableId);

			Object paramObj1 = new LongWrapper(rowId);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoTableRowServiceUtil.class.getName(),
					"setRow", new Object[] { paramObj0, paramObj1 });

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

			return (com.liferay.portal.model.ExpandoTableRow)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ExpandoTableRowServiceHttp.class);
}