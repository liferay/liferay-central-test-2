/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.BooleanWrapper;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.NullWrapper;
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.PhoneServiceUtil;
import com.liferay.portal.service.http.TunnelUtil;

/**
 * <a href="PhoneServiceHttp.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PhoneServiceHttp {
	public static com.liferay.portal.model.Phone addPhone(
		HttpPrincipal httpPrincipal, java.lang.String className,
		java.lang.String classPK, java.lang.String number,
		java.lang.String extension, java.lang.String typeId, boolean primary)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = className;

			if (className == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = classPK;

			if (classPK == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = number;

			if (number == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = extension;

			if (extension == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = typeId;

			if (typeId == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = new BooleanWrapper(primary);
			MethodWrapper methodWrapper = new MethodWrapper(PhoneServiceUtil.class.getName(),
					"addPhone",
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

				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (com.liferay.portal.model.Phone)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static void deletePhone(HttpPrincipal httpPrincipal, long phoneId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(phoneId);
			MethodWrapper methodWrapper = new MethodWrapper(PhoneServiceUtil.class.getName(),
					"deletePhone", new Object[] { paramObj0 });

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
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static com.liferay.portal.model.Phone getPhone(
		HttpPrincipal httpPrincipal, long phoneId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(phoneId);
			MethodWrapper methodWrapper = new MethodWrapper(PhoneServiceUtil.class.getName(),
					"getPhone", new Object[] { paramObj0 });
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

			return (com.liferay.portal.model.Phone)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static java.util.List getPhones(HttpPrincipal httpPrincipal,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = className;

			if (className == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = classPK;

			if (classPK == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(PhoneServiceUtil.class.getName(),
					"getPhones", new Object[] { paramObj0, paramObj1 });
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

			return (java.util.List)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static com.liferay.portal.model.Phone updatePhone(
		HttpPrincipal httpPrincipal, long phoneId, java.lang.String number,
		java.lang.String extension, java.lang.String typeId, boolean primary)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(phoneId);
			Object paramObj1 = number;

			if (number == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = extension;

			if (extension == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = typeId;

			if (typeId == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = new BooleanWrapper(primary);
			MethodWrapper methodWrapper = new MethodWrapper(PhoneServiceUtil.class.getName(),
					"updatePhone",
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

			return (com.liferay.portal.model.Phone)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PhoneServiceHttp.class);
}