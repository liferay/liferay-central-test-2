/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.spring.PortletServiceUtil;
import com.liferay.portal.servlet.TunnelUtil;
import com.liferay.portal.shared.util.BooleanWrapper;
import com.liferay.portal.shared.util.MethodWrapper;
import com.liferay.portal.shared.util.NullWrapper;
import com.liferay.portal.shared.util.StackTraceUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="PortletServiceHttp.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PortletServiceHttp {
	public static com.liferay.portal.model.Portlet updatePortlet(
		HttpPrincipal httpPrincipal, java.lang.String companyId,
		java.lang.String portletId, java.lang.String roles, boolean active)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = companyId;

			if (companyId == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = portletId;

			if (portletId == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = roles;

			if (roles == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new BooleanWrapper(active);
			MethodWrapper methodWrapper = new MethodWrapper(PortletServiceUtil.class.getName(),
					"updatePortlet",
					new Object[] { paramObj0, paramObj1, paramObj2, paramObj3 });
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw e;
			}

			return (com.liferay.portal.model.Portlet)returnObj;
		}
		catch (com.liferay.portal.PortalException pe) {
			_log.error(StackTraceUtil.getStackTrace(pe));
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(StackTraceUtil.getStackTrace(se));
			throw se;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new com.liferay.portal.SystemException(stackTrace);
		}
	}

	public static com.liferay.portal.model.PortletCategory getEARDisplay(
		HttpPrincipal httpPrincipal, java.lang.String xml)
		throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = xml;

			if (xml == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(PortletServiceUtil.class.getName(),
					"getEARDisplay", new Object[] { paramObj0 });
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof org.dom4j.DocumentException) {
					throw (org.dom4j.DocumentException)e;
				}

				if (e instanceof java.io.IOException) {
					throw (java.io.IOException)e;
				}

				throw e;
			}

			return (com.liferay.portal.model.PortletCategory)returnObj;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new com.liferay.portal.SystemException(stackTrace);
		}
	}

	public static com.liferay.portal.model.PortletCategory getWARDisplay(
		HttpPrincipal httpPrincipal, java.lang.String servletContextName,
		java.lang.String xml) throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = servletContextName;

			if (servletContextName == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = xml;

			if (xml == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(PortletServiceUtil.class.getName(),
					"getWARDisplay", new Object[] { paramObj0, paramObj1 });
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof org.dom4j.DocumentException) {
					throw (org.dom4j.DocumentException)e;
				}

				if (e instanceof java.io.IOException) {
					throw (java.io.IOException)e;
				}

				throw e;
			}

			return (com.liferay.portal.model.PortletCategory)returnObj;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new com.liferay.portal.SystemException(stackTrace);
		}
	}

	public static com.liferay.portal.model.Portlet getPortletById(
		HttpPrincipal httpPrincipal, java.lang.String companyId,
		java.lang.String portletId) throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = companyId;

			if (companyId == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = portletId;

			if (portletId == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(PortletServiceUtil.class.getName(),
					"getPortletById", new Object[] { paramObj0, paramObj1 });
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw e;
			}

			return (com.liferay.portal.model.Portlet)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(StackTraceUtil.getStackTrace(se));
			throw se;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new com.liferay.portal.SystemException(stackTrace);
		}
	}

	public static com.liferay.portal.model.Portlet getPortletByStrutsPath(
		HttpPrincipal httpPrincipal, java.lang.String companyId,
		java.lang.String strutsPath) throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = companyId;

			if (companyId == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = strutsPath;

			if (strutsPath == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(PortletServiceUtil.class.getName(),
					"getPortletByStrutsPath",
					new Object[] { paramObj0, paramObj1 });
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw e;
			}

			return (com.liferay.portal.model.Portlet)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(StackTraceUtil.getStackTrace(se));
			throw se;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new com.liferay.portal.SystemException(stackTrace);
		}
	}

	public static java.util.List getPortlets(HttpPrincipal httpPrincipal,
		java.lang.String companyId) throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = companyId;

			if (companyId == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(PortletServiceUtil.class.getName(),
					"getPortlets", new Object[] { paramObj0 });
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw e;
			}

			return (java.util.List)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(StackTraceUtil.getStackTrace(se));
			throw se;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new com.liferay.portal.SystemException(stackTrace);
		}
	}

	public static void initEAR(HttpPrincipal httpPrincipal,
		java.lang.String[] xmls) throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = xmls;

			if (xmls == null) {
				paramObj0 = new NullWrapper("[Ljava.lang.String;");
			}

			MethodWrapper methodWrapper = new MethodWrapper(PortletServiceUtil.class.getName(),
					"initEAR", new Object[] { paramObj0 });
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				throw e;
			}
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new com.liferay.portal.SystemException(stackTrace);
		}
	}

	public static java.util.List initWAR(HttpPrincipal httpPrincipal,
		java.lang.String servletContextName, java.lang.String[] xmls)
		throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = servletContextName;

			if (servletContextName == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = xmls;

			if (xmls == null) {
				paramObj1 = new NullWrapper("[Ljava.lang.String;");
			}

			MethodWrapper methodWrapper = new MethodWrapper(PortletServiceUtil.class.getName(),
					"initWAR", new Object[] { paramObj0, paramObj1 });
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				throw e;
			}

			return (java.util.List)returnObj;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new com.liferay.portal.SystemException(stackTrace);
		}
	}

	public static com.liferay.portal.model.Portlet updatePortlet(
		HttpPrincipal httpPrincipal, java.lang.String portletId,
		boolean narrow, java.lang.String roles, boolean active)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = portletId;

			if (portletId == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = new BooleanWrapper(narrow);
			Object paramObj2 = roles;

			if (roles == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new BooleanWrapper(active);
			MethodWrapper methodWrapper = new MethodWrapper(PortletServiceUtil.class.getName(),
					"updatePortlet",
					new Object[] { paramObj0, paramObj1, paramObj2, paramObj3 });
			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw e;
			}

			return (com.liferay.portal.model.Portlet)returnObj;
		}
		catch (com.liferay.portal.PortalException pe) {
			_log.error(StackTraceUtil.getStackTrace(pe));
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(StackTraceUtil.getStackTrace(se));
			throw se;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new com.liferay.portal.SystemException(stackTrace);
		}
	}

	private static Log _log = LogFactory.getLog(PortletServiceHttp.class);
}