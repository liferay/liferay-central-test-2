/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.service.ResourcePermissionServiceUtil;

/**
 * <a href="ResourcePermissionServiceHttp.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a HTTP utility for the
 * <code>com.liferay.portal.service.ResourcePermissionServiceUtil</code> service
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
 * @see com.liferay.portal.service.ResourcePermissionServiceUtil
 * @see com.liferay.portal.service.http.ResourcePermissionServiceSoap
 *
 */
public class ResourcePermissionServiceHttp {
	public static void setResourcePermission(HttpPrincipal httpPrincipal,
		long roleId, long groupId, java.lang.String name, int scope,
		java.lang.String primKey, java.lang.String actionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(roleId);

			Object paramObj1 = new LongWrapper(groupId);

			Object paramObj2 = name;

			if (name == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new IntegerWrapper(scope);

			Object paramObj4 = primKey;

			if (primKey == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = actionId;

			if (actionId == null) {
				paramObj5 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ResourcePermissionServiceUtil.class.getName(),
					"setResourcePermission",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5
					});

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
			_log.error(se, se);

			throw se;
		}
	}

	public static void setResourcePermissions(HttpPrincipal httpPrincipal,
		long roleId, long groupId, java.lang.String[] actionIds, long resourceId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(roleId);

			Object paramObj1 = new LongWrapper(groupId);

			Object paramObj2 = actionIds;

			if (actionIds == null) {
				paramObj2 = new NullWrapper("[Ljava.lang.String;");
			}

			Object paramObj3 = new LongWrapper(resourceId);

			MethodWrapper methodWrapper = new MethodWrapper(ResourcePermissionServiceUtil.class.getName(),
					"setResourcePermissions",
					new Object[] { paramObj0, paramObj1, paramObj2, paramObj3 });

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
			_log.error(se, se);

			throw se;
		}
	}

	public static void unsetResourcePermission(HttpPrincipal httpPrincipal,
		long roleId, long groupId, long resourceId, java.lang.String actionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(roleId);

			Object paramObj1 = new LongWrapper(groupId);

			Object paramObj2 = new LongWrapper(resourceId);

			Object paramObj3 = actionId;

			if (actionId == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ResourcePermissionServiceUtil.class.getName(),
					"unsetResourcePermission",
					new Object[] { paramObj0, paramObj1, paramObj2, paramObj3 });

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
			_log.error(se, se);

			throw se;
		}
	}

	public static void unsetResourcePermissions(HttpPrincipal httpPrincipal,
		long roleId, long groupId, java.lang.String name, int scope,
		java.lang.String actionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(roleId);

			Object paramObj1 = new LongWrapper(groupId);

			Object paramObj2 = name;

			if (name == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new IntegerWrapper(scope);

			Object paramObj4 = actionId;

			if (actionId == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(ResourcePermissionServiceUtil.class.getName(),
					"unsetResourcePermissions",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
					});

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
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(ResourcePermissionServiceHttp.class);
}