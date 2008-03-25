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
import com.liferay.portal.service.ExpandoValueServiceUtil;

/**
 * <a href="ExpandoValueServiceHttp.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a HTTP utility for the
 * <code>com.liferay.portal.service.ExpandoValueServiceUtil</code> service
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
 * @see com.liferay.portal.service.ExpandoValueServiceUtil
 * @see com.liferay.portal.service.http.ExpandoValueServiceSoap
 *
 */
public class ExpandoValueServiceHttp {
	public static com.liferay.portal.model.ExpandoValue addValue(
		HttpPrincipal httpPrincipal, long classPK, long columnId,
		java.lang.String value)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(classPK);

			Object paramObj1 = new LongWrapper(columnId);

			Object paramObj2 = value;

			if (value == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"addValue", new Object[] { paramObj0, paramObj1, paramObj2 });

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

			return (com.liferay.portal.model.ExpandoValue)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.model.ExpandoValue getValue(
		HttpPrincipal httpPrincipal, long classPK, long columnId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(classPK);

			Object paramObj1 = new LongWrapper(columnId);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getValue", new Object[] { paramObj0, paramObj1 });

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

			return (com.liferay.portal.model.ExpandoValue)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteValue(HttpPrincipal httpPrincipal, long valueId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(valueId);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"deleteValue", new Object[] { paramObj0 });

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

	public static void deleteValue(HttpPrincipal httpPrincipal, long classPK,
		long columnId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(classPK);

			Object paramObj1 = new LongWrapper(columnId);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"deleteValue", new Object[] { paramObj0, paramObj1 });

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

	public static void deleteValues(HttpPrincipal httpPrincipal, long classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(classPK);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"deleteValues", new Object[] { paramObj0 });

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

	public static void deleteColumnValues(HttpPrincipal httpPrincipal,
		long columnId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(columnId);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"deleteColumnValues", new Object[] { paramObj0 });

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

	public static void deleteRowValues(HttpPrincipal httpPrincipal, long rowId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(rowId);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"deleteRowValues", new Object[] { paramObj0 });

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

	public static java.util.List<com.liferay.portal.model.ExpandoValue> getColumnValues(
		HttpPrincipal httpPrincipal, long columnId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(columnId);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getColumnValues", new Object[] { paramObj0 });

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

			return (java.util.List<com.liferay.portal.model.ExpandoValue>)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.model.ExpandoValue> getColumnValues(
		HttpPrincipal httpPrincipal, long columnId, int begin, int end)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(columnId);

			Object paramObj1 = new IntegerWrapper(begin);

			Object paramObj2 = new IntegerWrapper(end);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getColumnValues",
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

			return (java.util.List<com.liferay.portal.model.ExpandoValue>)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getColumnValuesCount(HttpPrincipal httpPrincipal,
		long columnId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(columnId);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getColumnValuesCount", new Object[] { paramObj0 });

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

	public static java.util.List<com.liferay.portal.model.ExpandoValue> getRowValues(
		HttpPrincipal httpPrincipal, long rowId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(rowId);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getRowValues", new Object[] { paramObj0 });

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

			return (java.util.List<com.liferay.portal.model.ExpandoValue>)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.model.ExpandoValue> getRowValues(
		HttpPrincipal httpPrincipal, long rowId, int begin, int end)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(rowId);

			Object paramObj1 = new IntegerWrapper(begin);

			Object paramObj2 = new IntegerWrapper(end);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getRowValues",
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

			return (java.util.List<com.liferay.portal.model.ExpandoValue>)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getRowValuesCount(HttpPrincipal httpPrincipal, long rowId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(rowId);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getRowValuesCount", new Object[] { paramObj0 });

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

	public static java.util.List<com.liferay.portal.model.ExpandoValue> getValues(
		HttpPrincipal httpPrincipal, long classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(classPK);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getValues", new Object[] { paramObj0 });

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

			return (java.util.List<com.liferay.portal.model.ExpandoValue>)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.model.ExpandoValue> getValues(
		HttpPrincipal httpPrincipal, long classPK, int begin, int end)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(classPK);

			Object paramObj1 = new IntegerWrapper(begin);

			Object paramObj2 = new IntegerWrapper(end);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getValues",
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

			return (java.util.List<com.liferay.portal.model.ExpandoValue>)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getValuesCount(HttpPrincipal httpPrincipal, long classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(classPK);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getValuesCount", new Object[] { paramObj0 });

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

	public static com.liferay.portal.model.ExpandoValue setValue(
		HttpPrincipal httpPrincipal, long classPK, long columnId,
		java.lang.String value)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(classPK);

			Object paramObj1 = new LongWrapper(columnId);

			Object paramObj2 = value;

			if (value == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"setValue", new Object[] { paramObj0, paramObj1, paramObj2 });

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

			return (com.liferay.portal.model.ExpandoValue)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static long setRowValues(HttpPrincipal httpPrincipal, long tableId,
		com.liferay.portal.model.ExpandoValue[] expandoValues)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(tableId);

			Object paramObj1 = expandoValues;

			if (expandoValues == null) {
				paramObj1 = new NullWrapper(
						"[Lcom.liferay.portal.model.ExpandoValue;");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"setRowValues", new Object[] { paramObj0, paramObj1 });

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

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static long setRowValues(HttpPrincipal httpPrincipal, long tableId,
		java.util.List<com.liferay.portal.model.ExpandoValue> expandoValues)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(tableId);

			Object paramObj1 = expandoValues;

			if (expandoValues == null) {
				paramObj1 = new NullWrapper("java.util.List");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"setRowValues", new Object[] { paramObj0, paramObj1 });

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

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static long setRowValues(HttpPrincipal httpPrincipal, long tableId,
		long rowId, com.liferay.portal.model.ExpandoValue[] expandoValues)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(tableId);

			Object paramObj1 = new LongWrapper(rowId);

			Object paramObj2 = expandoValues;

			if (expandoValues == null) {
				paramObj2 = new NullWrapper(
						"[Lcom.liferay.portal.model.ExpandoValue;");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"setRowValues",
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

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static long setRowValues(HttpPrincipal httpPrincipal, long tableId,
		long rowId,
		java.util.List<com.liferay.portal.model.ExpandoValue> expandoValues)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(tableId);

			Object paramObj1 = new LongWrapper(rowId);

			Object paramObj2 = expandoValues;

			if (expandoValues == null) {
				paramObj2 = new NullWrapper("java.util.List");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"setRowValues",
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

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ExpandoValueServiceHttp.class);
}