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
import com.liferay.portal.kernel.util.BooleanWrapper;
import com.liferay.portal.kernel.util.DoubleWrapper;
import com.liferay.portal.kernel.util.FloatWrapper;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.NullWrapper;
import com.liferay.portal.kernel.util.ShortWrapper;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

import com.liferay.portlet.expando.service.ExpandoValueServiceUtil;

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
 * <code>com.liferay.portlet.expando.service.ExpandoValueServiceUtil</code> service
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
 * @see com.liferay.portlet.expando.service.ExpandoValueServiceUtil
 * @see com.liferay.portlet.expando.service.http.ExpandoValueServiceSoap
 *
 */
public class ExpandoValueServiceHttp {
	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		HttpPrincipal httpPrincipal, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		boolean data)
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

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new LongWrapper(classPK);

			Object paramObj4 = new BooleanWrapper(data);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"addValue",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

			return (com.liferay.portlet.expando.model.ExpandoValue)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		HttpPrincipal httpPrincipal, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		boolean[] data)
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

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new LongWrapper(classPK);

			Object paramObj4 = data;

			if (data == null) {
				paramObj4 = new NullWrapper("[Z");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"addValue",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

			return (com.liferay.portlet.expando.model.ExpandoValue)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		HttpPrincipal httpPrincipal, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		java.util.Date data)
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

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new LongWrapper(classPK);

			Object paramObj4 = data;

			if (data == null) {
				paramObj4 = new NullWrapper("java.util.Date");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"addValue",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

			return (com.liferay.portlet.expando.model.ExpandoValue)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		HttpPrincipal httpPrincipal, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		java.util.Date[] data)
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

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new LongWrapper(classPK);

			Object paramObj4 = data;

			if (data == null) {
				paramObj4 = new NullWrapper("[Ljava.util.Date;");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"addValue",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

			return (com.liferay.portlet.expando.model.ExpandoValue)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		HttpPrincipal httpPrincipal, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		double data)
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

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new LongWrapper(classPK);

			Object paramObj4 = new DoubleWrapper(data);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"addValue",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

			return (com.liferay.portlet.expando.model.ExpandoValue)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		HttpPrincipal httpPrincipal, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		double[] data)
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

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new LongWrapper(classPK);

			Object paramObj4 = data;

			if (data == null) {
				paramObj4 = new NullWrapper("[D");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"addValue",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

			return (com.liferay.portlet.expando.model.ExpandoValue)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		HttpPrincipal httpPrincipal, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		float data)
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

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new LongWrapper(classPK);

			Object paramObj4 = new FloatWrapper(data);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"addValue",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

			return (com.liferay.portlet.expando.model.ExpandoValue)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		HttpPrincipal httpPrincipal, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		float[] data)
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

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new LongWrapper(classPK);

			Object paramObj4 = data;

			if (data == null) {
				paramObj4 = new NullWrapper("[F");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"addValue",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

			return (com.liferay.portlet.expando.model.ExpandoValue)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		HttpPrincipal httpPrincipal, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		int data)
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

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new LongWrapper(classPK);

			Object paramObj4 = new IntegerWrapper(data);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"addValue",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

			return (com.liferay.portlet.expando.model.ExpandoValue)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		HttpPrincipal httpPrincipal, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		int[] data)
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

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new LongWrapper(classPK);

			Object paramObj4 = data;

			if (data == null) {
				paramObj4 = new NullWrapper("[I");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"addValue",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

			return (com.liferay.portlet.expando.model.ExpandoValue)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		HttpPrincipal httpPrincipal, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		long data)
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

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new LongWrapper(classPK);

			Object paramObj4 = new LongWrapper(data);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"addValue",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

			return (com.liferay.portlet.expando.model.ExpandoValue)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		HttpPrincipal httpPrincipal, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		long[] data)
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

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new LongWrapper(classPK);

			Object paramObj4 = data;

			if (data == null) {
				paramObj4 = new NullWrapper("[J");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"addValue",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

			return (com.liferay.portlet.expando.model.ExpandoValue)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		HttpPrincipal httpPrincipal, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		short data)
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

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new LongWrapper(classPK);

			Object paramObj4 = new ShortWrapper(data);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"addValue",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

			return (com.liferay.portlet.expando.model.ExpandoValue)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		HttpPrincipal httpPrincipal, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		short[] data)
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

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new LongWrapper(classPK);

			Object paramObj4 = data;

			if (data == null) {
				paramObj4 = new NullWrapper("[S");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"addValue",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

			return (com.liferay.portlet.expando.model.ExpandoValue)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		HttpPrincipal httpPrincipal, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		java.lang.String data)
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

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new LongWrapper(classPK);

			Object paramObj4 = data;

			if (data == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"addValue",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

			return (com.liferay.portlet.expando.model.ExpandoValue)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		HttpPrincipal httpPrincipal, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long classPK,
		java.lang.String[] data)
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

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new LongWrapper(classPK);

			Object paramObj4 = data;

			if (data == null) {
				paramObj4 = new NullWrapper("[Ljava.lang.String;");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"addValue",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

			return (com.liferay.portlet.expando.model.ExpandoValue)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValue addValue(
		HttpPrincipal httpPrincipal, long classNameId, long tableId,
		long columnId, long classPK, java.lang.String data)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(classNameId);

			Object paramObj1 = new LongWrapper(tableId);

			Object paramObj2 = new LongWrapper(columnId);

			Object paramObj3 = new LongWrapper(classPK);

			Object paramObj4 = data;

			if (data == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"addValue",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

			return (com.liferay.portlet.expando.model.ExpandoValue)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteColumnValues(HttpPrincipal httpPrincipal,
		long columnId) throws com.liferay.portal.SystemException {
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

				throw new com.liferay.portal.SystemException(e);
			}
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteRowValues(HttpPrincipal httpPrincipal, long rowId)
		throws com.liferay.portal.SystemException {
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

				throw new com.liferay.portal.SystemException(e);
			}
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteTableValues(HttpPrincipal httpPrincipal,
		long tableId) throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(tableId);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"deleteTableValues", new Object[] { paramObj0 });

			try {
				TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}
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

	public static void deleteValues(HttpPrincipal httpPrincipal,
		java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = className;

			if (className == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = new LongWrapper(classPK);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"deleteValues", new Object[] { paramObj0, paramObj1 });

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

	public static void deleteValues(HttpPrincipal httpPrincipal,
		long classNameId, long classPK)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(classNameId);

			Object paramObj1 = new LongWrapper(classPK);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"deleteValues", new Object[] { paramObj0, paramObj1 });

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

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getColumnValues(
		HttpPrincipal httpPrincipal, long columnId, int begin, int end)
		throws com.liferay.portal.SystemException {
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

				throw new com.liferay.portal.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.expando.model.ExpandoValue>)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getColumnValues(
		HttpPrincipal httpPrincipal, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, int begin,
		int end) throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = className;

			if (className == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = tableName;

			if (tableName == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new IntegerWrapper(begin);

			Object paramObj4 = new IntegerWrapper(end);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getColumnValues",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

			return (java.util.List<com.liferay.portlet.expando.model.ExpandoValue>)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getColumnValues(
		HttpPrincipal httpPrincipal, long classNameId,
		java.lang.String tableName, java.lang.String columnName, int begin,
		int end) throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(classNameId);

			Object paramObj1 = tableName;

			if (tableName == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new IntegerWrapper(begin);

			Object paramObj4 = new IntegerWrapper(end);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getColumnValues",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

			return (java.util.List<com.liferay.portlet.expando.model.ExpandoValue>)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getColumnValues(
		HttpPrincipal httpPrincipal, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, int begin,
		java.lang.String data, int end)
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

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new IntegerWrapper(begin);

			Object paramObj4 = data;

			if (data == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = new IntegerWrapper(end);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getColumnValues",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5
					});

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

			return (java.util.List<com.liferay.portlet.expando.model.ExpandoValue>)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getColumnValues(
		HttpPrincipal httpPrincipal, long classNameId,
		java.lang.String tableName, java.lang.String columnName, int begin,
		java.lang.String data, int end)
		throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(classNameId);

			Object paramObj1 = tableName;

			if (tableName == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new IntegerWrapper(begin);

			Object paramObj4 = data;

			if (data == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = new IntegerWrapper(end);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getColumnValues",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5
					});

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

			return (java.util.List<com.liferay.portlet.expando.model.ExpandoValue>)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getColumnValuesCount(HttpPrincipal httpPrincipal,
		long columnId) throws com.liferay.portal.SystemException {
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

				throw new com.liferay.portal.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getColumnValuesCount(HttpPrincipal httpPrincipal,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName) throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = className;

			if (className == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = tableName;

			if (tableName == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getColumnValuesCount",
					new Object[] { paramObj0, paramObj1, paramObj2 });

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

	public static int getColumnValuesCount(HttpPrincipal httpPrincipal,
		long classNameId, java.lang.String tableName,
		java.lang.String columnName) throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(classNameId);

			Object paramObj1 = tableName;

			if (tableName == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getColumnValuesCount",
					new Object[] { paramObj0, paramObj1, paramObj2 });

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

	public static int getColumnValuesCount(HttpPrincipal httpPrincipal,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, java.lang.String data)
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

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = data;

			if (data == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getColumnValuesCount",
					new Object[] { paramObj0, paramObj1, paramObj2, paramObj3 });

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

	public static int getColumnValuesCount(HttpPrincipal httpPrincipal,
		long classNameId, java.lang.String tableName,
		java.lang.String columnName, java.lang.String data)
		throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(classNameId);

			Object paramObj1 = tableName;

			if (tableName == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = data;

			if (data == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getColumnValuesCount",
					new Object[] { paramObj0, paramObj1, paramObj2, paramObj3 });

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

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getDefaultTableColumnValues(
		HttpPrincipal httpPrincipal, java.lang.String className,
		java.lang.String columnName, int begin, int end)
		throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = className;

			if (className == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = columnName;

			if (columnName == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = new IntegerWrapper(begin);

			Object paramObj3 = new IntegerWrapper(end);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getDefaultTableColumnValues",
					new Object[] { paramObj0, paramObj1, paramObj2, paramObj3 });

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

			return (java.util.List<com.liferay.portlet.expando.model.ExpandoValue>)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getDefaultTableColumnValues(
		HttpPrincipal httpPrincipal, long classNameId,
		java.lang.String columnName, int begin, int end)
		throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(classNameId);

			Object paramObj1 = columnName;

			if (columnName == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = new IntegerWrapper(begin);

			Object paramObj3 = new IntegerWrapper(end);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getDefaultTableColumnValues",
					new Object[] { paramObj0, paramObj1, paramObj2, paramObj3 });

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

			return (java.util.List<com.liferay.portlet.expando.model.ExpandoValue>)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getDefaultTableColumnValuesCount(
		HttpPrincipal httpPrincipal, java.lang.String className,
		java.lang.String columnName) throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = className;

			if (className == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = columnName;

			if (columnName == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getDefaultTableColumnValuesCount",
					new Object[] { paramObj0, paramObj1 });

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

	public static int getDefaultTableColumnValuesCount(
		HttpPrincipal httpPrincipal, long classNameId,
		java.lang.String columnName) throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(classNameId);

			Object paramObj1 = columnName;

			if (columnName == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getDefaultTableColumnValuesCount",
					new Object[] { paramObj0, paramObj1 });

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

	public static boolean getData(HttpPrincipal httpPrincipal,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, boolean defaultData)
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

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new LongWrapper(classPK);

			Object paramObj4 = new BooleanWrapper(defaultData);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getData",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean[] getData(HttpPrincipal httpPrincipal,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, boolean[] defaultData)
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

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new LongWrapper(classPK);

			Object paramObj4 = defaultData;

			if (defaultData == null) {
				paramObj4 = new NullWrapper("[Z");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getData",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

			return (boolean[])returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.Date getData(HttpPrincipal httpPrincipal,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.util.Date defaultData)
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

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new LongWrapper(classPK);

			Object paramObj4 = defaultData;

			if (defaultData == null) {
				paramObj4 = new NullWrapper("java.util.Date");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getData",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

			return (java.util.Date)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.Date[] getData(HttpPrincipal httpPrincipal,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.util.Date[] defaultData)
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

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new LongWrapper(classPK);

			Object paramObj4 = defaultData;

			if (defaultData == null) {
				paramObj4 = new NullWrapper("[Ljava.util.Date;");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getData",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

			return (java.util.Date[])returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static double getData(HttpPrincipal httpPrincipal,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, double defaultData)
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

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new LongWrapper(classPK);

			Object paramObj4 = new DoubleWrapper(defaultData);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getData",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

			return ((Double)returnObj).doubleValue();
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static double[] getData(HttpPrincipal httpPrincipal,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, double[] defaultData)
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

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new LongWrapper(classPK);

			Object paramObj4 = defaultData;

			if (defaultData == null) {
				paramObj4 = new NullWrapper("[D");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getData",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

			return (double[])returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static float getData(HttpPrincipal httpPrincipal,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, float defaultData)
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

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new LongWrapper(classPK);

			Object paramObj4 = new FloatWrapper(defaultData);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getData",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

			return ((Float)returnObj).floatValue();
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static float[] getData(HttpPrincipal httpPrincipal,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, float[] defaultData)
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

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new LongWrapper(classPK);

			Object paramObj4 = defaultData;

			if (defaultData == null) {
				paramObj4 = new NullWrapper("[F");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getData",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

			return (float[])returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getData(HttpPrincipal httpPrincipal,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, int defaultData)
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

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new LongWrapper(classPK);

			Object paramObj4 = new IntegerWrapper(defaultData);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getData",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

	public static int[] getData(HttpPrincipal httpPrincipal,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, int[] defaultData)
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

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new LongWrapper(classPK);

			Object paramObj4 = defaultData;

			if (defaultData == null) {
				paramObj4 = new NullWrapper("[I");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getData",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

			return (int[])returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static long getData(HttpPrincipal httpPrincipal,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, long defaultData)
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

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new LongWrapper(classPK);

			Object paramObj4 = new LongWrapper(defaultData);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getData",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

	public static long[] getData(HttpPrincipal httpPrincipal,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, long[] defaultData)
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

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new LongWrapper(classPK);

			Object paramObj4 = defaultData;

			if (defaultData == null) {
				paramObj4 = new NullWrapper("[J");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getData",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

			return (long[])returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static short getData(HttpPrincipal httpPrincipal,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, short defaultData)
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

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new LongWrapper(classPK);

			Object paramObj4 = new ShortWrapper(defaultData);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getData",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

			return ((Short)returnObj).shortValue();
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static short[] getData(HttpPrincipal httpPrincipal,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, short[] defaultData)
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

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new LongWrapper(classPK);

			Object paramObj4 = defaultData;

			if (defaultData == null) {
				paramObj4 = new NullWrapper("[S");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getData",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

			return (short[])returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.lang.String getData(HttpPrincipal httpPrincipal,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK, java.lang.String defaultData)
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

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new LongWrapper(classPK);

			Object paramObj4 = defaultData;

			if (defaultData == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getData",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

			return (java.lang.String)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.lang.String[] getData(HttpPrincipal httpPrincipal,
		java.lang.String className, java.lang.String tableName,
		java.lang.String columnName, long classPK,
		java.lang.String[] defaultData)
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

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new LongWrapper(classPK);

			Object paramObj4 = defaultData;

			if (defaultData == null) {
				paramObj4 = new NullWrapper("[Ljava.lang.String;");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getData",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

			return (java.lang.String[])returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getRowValues(
		HttpPrincipal httpPrincipal, long rowId)
		throws com.liferay.portal.SystemException {
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

				throw new com.liferay.portal.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.expando.model.ExpandoValue>)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getRowValues(
		HttpPrincipal httpPrincipal, long rowId, int begin, int end)
		throws com.liferay.portal.SystemException {
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

				throw new com.liferay.portal.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.expando.model.ExpandoValue>)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getRowValues(
		HttpPrincipal httpPrincipal, java.lang.String className,
		java.lang.String tableName, long classPK, int begin, int end)
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

			Object paramObj2 = new LongWrapper(classPK);

			Object paramObj3 = new IntegerWrapper(begin);

			Object paramObj4 = new IntegerWrapper(end);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getRowValues",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

			return (java.util.List<com.liferay.portlet.expando.model.ExpandoValue>)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.expando.model.ExpandoValue> getRowValues(
		HttpPrincipal httpPrincipal, long classNameId,
		java.lang.String tableName, long classPK, int begin, int end)
		throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(classNameId);

			Object paramObj1 = tableName;

			if (tableName == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = new LongWrapper(classPK);

			Object paramObj3 = new IntegerWrapper(begin);

			Object paramObj4 = new IntegerWrapper(end);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getRowValues",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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

			return (java.util.List<com.liferay.portlet.expando.model.ExpandoValue>)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getRowValuesCount(HttpPrincipal httpPrincipal, long rowId)
		throws com.liferay.portal.SystemException {
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

				throw new com.liferay.portal.SystemException(e);
			}

			return ((Integer)returnObj).intValue();
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getRowValuesCount(HttpPrincipal httpPrincipal,
		java.lang.String className, java.lang.String tableName, long classPK)
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

			Object paramObj2 = new LongWrapper(classPK);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getRowValuesCount",
					new Object[] { paramObj0, paramObj1, paramObj2 });

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

	public static int getRowValuesCount(HttpPrincipal httpPrincipal,
		long classNameId, java.lang.String tableName, long classPK)
		throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(classNameId);

			Object paramObj1 = tableName;

			if (tableName == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = new LongWrapper(classPK);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getRowValuesCount",
					new Object[] { paramObj0, paramObj1, paramObj2 });

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

	public static com.liferay.portlet.expando.model.ExpandoValue getValue(
		HttpPrincipal httpPrincipal, long valueId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(valueId);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getValue", new Object[] { paramObj0 });

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

			return (com.liferay.portlet.expando.model.ExpandoValue)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValue getValue(
		HttpPrincipal httpPrincipal, long columnId, long rowId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(columnId);

			Object paramObj1 = new LongWrapper(rowId);

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

			return (com.liferay.portlet.expando.model.ExpandoValue)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValue getValue(
		HttpPrincipal httpPrincipal, java.lang.String className,
		java.lang.String tableName, java.lang.String columnName, long rowId)
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

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new LongWrapper(rowId);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getValue",
					new Object[] { paramObj0, paramObj1, paramObj2, paramObj3 });

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

			return (com.liferay.portlet.expando.model.ExpandoValue)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.expando.model.ExpandoValue getValue(
		HttpPrincipal httpPrincipal, long classNameId,
		java.lang.String tableName, java.lang.String columnName, long rowId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(classNameId);

			Object paramObj1 = tableName;

			if (tableName == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = columnName;

			if (columnName == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new LongWrapper(rowId);

			MethodWrapper methodWrapper = new MethodWrapper(ExpandoValueServiceUtil.class.getName(),
					"getValue",
					new Object[] { paramObj0, paramObj1, paramObj2, paramObj3 });

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

			return (com.liferay.portlet.expando.model.ExpandoValue)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ExpandoValueServiceHttp.class);
}