/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.softwarecatalog.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.BooleanWrapper;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.NullWrapper;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

import com.liferay.portlet.softwarecatalog.service.SCLicenseServiceUtil;

/**
 * <a href="SCLicenseServiceHttp.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class SCLicenseServiceHttp {
	public static com.liferay.portlet.softwarecatalog.model.SCLicense addLicense(
		HttpPrincipal httpPrincipal, java.lang.String name,
		java.lang.String url, boolean openSource, boolean active,
		boolean recommended)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = name;

			if (name == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = url;

			if (url == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = new BooleanWrapper(openSource);
			Object paramObj3 = new BooleanWrapper(active);
			Object paramObj4 = new BooleanWrapper(recommended);
			MethodWrapper methodWrapper = new MethodWrapper(SCLicenseServiceUtil.class.getName(),
					"addLicense",
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

			return (com.liferay.portlet.softwarecatalog.model.SCLicense)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);
			throw se;
		}
	}

	public static void deleteLicense(HttpPrincipal httpPrincipal, long licenseId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(licenseId);
			MethodWrapper methodWrapper = new MethodWrapper(SCLicenseServiceUtil.class.getName(),
					"deleteLicense", new Object[] { paramObj0 });

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

	public static com.liferay.portlet.softwarecatalog.model.SCLicense getLicense(
		HttpPrincipal httpPrincipal, long licenseId)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(licenseId);
			MethodWrapper methodWrapper = new MethodWrapper(SCLicenseServiceUtil.class.getName(),
					"getLicense", new Object[] { paramObj0 });
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

			return (com.liferay.portlet.softwarecatalog.model.SCLicense)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);
			throw se;
		}
	}

	public static com.liferay.portlet.softwarecatalog.model.SCLicense updateLicense(
		HttpPrincipal httpPrincipal, long licenseId, java.lang.String name,
		java.lang.String url, boolean openSource, boolean active,
		boolean recommended)
		throws com.liferay.portal.SystemException, 
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(licenseId);
			Object paramObj1 = name;

			if (name == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = url;

			if (url == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new BooleanWrapper(openSource);
			Object paramObj4 = new BooleanWrapper(active);
			Object paramObj5 = new BooleanWrapper(recommended);
			MethodWrapper methodWrapper = new MethodWrapper(SCLicenseServiceUtil.class.getName(),
					"updateLicense",
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

			return (com.liferay.portlet.softwarecatalog.model.SCLicense)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);
			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(SCLicenseServiceHttp.class);
}