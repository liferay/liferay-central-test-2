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

import com.liferay.portal.kernel.util.BooleanWrapper;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.NullWrapper;
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.spring.OrganizationServiceUtil;
import com.liferay.portal.servlet.TunnelUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="OrganizationServiceHttp.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class OrganizationServiceHttp {
	public static void addGroupOrganizations(HttpPrincipal httpPrincipal,
		java.lang.String groupId, java.lang.String[] organizationIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = groupId;

			if (groupId == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = organizationIds;

			if (organizationIds == null) {
				paramObj1 = new NullWrapper("[Ljava.lang.String;");
			}

			MethodWrapper methodWrapper = new MethodWrapper(OrganizationServiceUtil.class.getName(),
					"addGroupOrganizations",
					new Object[] { paramObj0, paramObj1 });

			try {
				TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
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

	public static com.liferay.portal.model.Organization addOrganization(
		HttpPrincipal httpPrincipal, java.lang.String parentOrganizationId,
		java.lang.String name, java.lang.String regionId,
		java.lang.String countryId, java.lang.String statusId, boolean location)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = parentOrganizationId;

			if (parentOrganizationId == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = name;

			if (name == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = regionId;

			if (regionId == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = countryId;

			if (countryId == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = statusId;

			if (statusId == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = new BooleanWrapper(location);
			MethodWrapper methodWrapper = new MethodWrapper(OrganizationServiceUtil.class.getName(),
					"addOrganization",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5
					});
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

				throw new com.liferay.portal.SystemException(e);
			}

			return (com.liferay.portal.model.Organization)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static void deleteOrganization(HttpPrincipal httpPrincipal,
		java.lang.String organizationId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = organizationId;

			if (organizationId == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(OrganizationServiceUtil.class.getName(),
					"deleteOrganization", new Object[] { paramObj0 });

			try {
				TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
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

	public static com.liferay.portal.model.Organization getOrganization(
		HttpPrincipal httpPrincipal, java.lang.String organizationId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = organizationId;

			if (organizationId == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(OrganizationServiceUtil.class.getName(),
					"getOrganization", new Object[] { paramObj0 });
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

				throw new com.liferay.portal.SystemException(e);
			}

			return (com.liferay.portal.model.Organization)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static java.util.List getUserOrganizations(
		HttpPrincipal httpPrincipal, java.lang.String userId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = userId;

			if (userId == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(OrganizationServiceUtil.class.getName(),
					"getUserOrganizations", new Object[] { paramObj0 });
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

	public static void setGroupOrganizations(HttpPrincipal httpPrincipal,
		java.lang.String groupId, java.lang.String[] organizationIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = groupId;

			if (groupId == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = organizationIds;

			if (organizationIds == null) {
				paramObj1 = new NullWrapper("[Ljava.lang.String;");
			}

			MethodWrapper methodWrapper = new MethodWrapper(OrganizationServiceUtil.class.getName(),
					"setGroupOrganizations",
					new Object[] { paramObj0, paramObj1 });

			try {
				TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
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

	public static void unsetGroupOrganizations(HttpPrincipal httpPrincipal,
		java.lang.String groupId, java.lang.String[] organizationIds)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = groupId;

			if (groupId == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = organizationIds;

			if (organizationIds == null) {
				paramObj1 = new NullWrapper("[Ljava.lang.String;");
			}

			MethodWrapper methodWrapper = new MethodWrapper(OrganizationServiceUtil.class.getName(),
					"unsetGroupOrganizations",
					new Object[] { paramObj0, paramObj1 });

			try {
				TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
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

	public static com.liferay.portal.model.Organization updateOrganization(
		HttpPrincipal httpPrincipal, java.lang.String organizationId,
		java.lang.String parentOrganizationId, java.lang.String name,
		java.lang.String regionId, java.lang.String countryId,
		java.lang.String statusId, boolean location)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = organizationId;

			if (organizationId == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = parentOrganizationId;

			if (parentOrganizationId == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = name;

			if (name == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = regionId;

			if (regionId == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = countryId;

			if (countryId == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = statusId;

			if (statusId == null) {
				paramObj5 = new NullWrapper("java.lang.String");
			}

			Object paramObj6 = new BooleanWrapper(location);
			MethodWrapper methodWrapper = new MethodWrapper(OrganizationServiceUtil.class.getName(),
					"updateOrganization",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6
					});
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

				throw new com.liferay.portal.SystemException(e);
			}

			return (com.liferay.portal.model.Organization)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static com.liferay.portal.model.Organization updateOrganization(
		HttpPrincipal httpPrincipal, java.lang.String organizationId,
		java.lang.String comments)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = organizationId;

			if (organizationId == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = comments;

			if (comments == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(OrganizationServiceUtil.class.getName(),
					"updateOrganization", new Object[] { paramObj0, paramObj1 });
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

				throw new com.liferay.portal.SystemException(e);
			}

			return (com.liferay.portal.model.Organization)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	private static Log _log = LogFactory.getLog(OrganizationServiceHttp.class);
}