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
import com.liferay.portal.service.spring.PortletPreferencesServiceUtil;
import com.liferay.portal.servlet.TunnelUtil;
import com.liferay.portal.shared.util.MethodWrapper;
import com.liferay.portal.shared.util.NullWrapper;
import com.liferay.portal.shared.util.StackTraceUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="PortletPreferencesServiceHttp.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class PortletPreferencesServiceHttp {
	public static javax.portlet.PortletPreferences getDefaultPreferences(
		HttpPrincipal httpPrincipal, java.lang.String companyId,
		java.lang.String portletId)
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

			MethodWrapper methodWrapper = new MethodWrapper(PortletPreferencesServiceUtil.class.getName(),
					"getDefaultPreferences",
					new Object[] { paramObj0, paramObj1 });
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

			return (javax.portlet.PortletPreferences)returnObj;
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

	public static javax.portlet.PortletPreferences getPreferences(
		HttpPrincipal httpPrincipal, java.lang.String companyId,
		com.liferay.portal.service.persistence.PortletPreferencesPK pk)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = companyId;

			if (companyId == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = pk;

			if (pk == null) {
				paramObj1 = new NullWrapper(
						"com.liferay.portal.service.persistence.PortletPreferencesPK");
			}

			MethodWrapper methodWrapper = new MethodWrapper(PortletPreferencesServiceUtil.class.getName(),
					"getPreferences", new Object[] { paramObj0, paramObj1 });
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

			return (javax.portlet.PortletPreferences)returnObj;
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

	public static void updatePreferences(HttpPrincipal httpPrincipal,
		com.liferay.portal.service.persistence.PortletPreferencesPK pk,
		com.liferay.portlet.PortletPreferencesImpl prefs)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = pk;

			if (pk == null) {
				paramObj0 = new NullWrapper(
						"com.liferay.portal.service.persistence.PortletPreferencesPK");
			}

			Object paramObj1 = prefs;

			if (prefs == null) {
				paramObj1 = new NullWrapper(
						"com.liferay.portlet.PortletPreferencesImpl");
			}

			MethodWrapper methodWrapper = new MethodWrapper(PortletPreferencesServiceUtil.class.getName(),
					"updatePreferences", new Object[] { paramObj0, paramObj1 });
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

	private static Log _log = LogFactory.getLog(PortletPreferencesServiceHttp.class);
}