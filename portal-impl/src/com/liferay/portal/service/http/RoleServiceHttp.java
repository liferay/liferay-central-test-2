/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.NullWrapper;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.RoleServiceUtil;

/**
 * <a href="RoleServiceHttp.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a HTTP utility for the
 * {@link com.liferay.portal.service.RoleServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * {@link com.liferay.portal.security.auth.HttpPrincipal} parameter.
 * </p>
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       RoleServiceSoap
 * @see       com.liferay.portal.security.auth.HttpPrincipal
 * @see       com.liferay.portal.service.RoleServiceUtil
 * @generated
 */
public class RoleServiceHttp {
	public static com.liferay.portal.model.Role addRole(
		HttpPrincipal httpPrincipal, java.lang.String name,
		java.util.Map<java.util.Locale, String> titleMap,
		java.lang.String description, int type)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = name;

			if (name == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = titleMap;

			if (titleMap == null) {
				paramObj1 = new NullWrapper("java.util.Map");
			}

			Object paramObj2 = description;

			if (description == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new IntegerWrapper(type);

			MethodWrapper methodWrapper = new MethodWrapper(RoleServiceUtil.class.getName(),
					"addRole",
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

				throw new com.liferay.portal.SystemException(e);
			}

			return (com.liferay.portal.model.Role)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void addUserRoles(HttpPrincipal httpPrincipal, long userId,
		long[] roleIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(userId);

			Object paramObj1 = roleIds;

			if (roleIds == null) {
				paramObj1 = new NullWrapper("[J");
			}

			MethodWrapper methodWrapper = new MethodWrapper(RoleServiceUtil.class.getName(),
					"addUserRoles", new Object[] { paramObj0, paramObj1 });

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

	public static void deleteRole(HttpPrincipal httpPrincipal, long roleId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(roleId);

			MethodWrapper methodWrapper = new MethodWrapper(RoleServiceUtil.class.getName(),
					"deleteRole", new Object[] { paramObj0 });

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

	public static com.liferay.portal.model.Role getGroupRole(
		HttpPrincipal httpPrincipal, long companyId, long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(companyId);

			Object paramObj1 = new LongWrapper(groupId);

			MethodWrapper methodWrapper = new MethodWrapper(RoleServiceUtil.class.getName(),
					"getGroupRole", new Object[] { paramObj0, paramObj1 });

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

			return (com.liferay.portal.model.Role)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.model.Role> getGroupRoles(
		HttpPrincipal httpPrincipal, long groupId)
		throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(groupId);

			MethodWrapper methodWrapper = new MethodWrapper(RoleServiceUtil.class.getName(),
					"getGroupRoles", new Object[] { paramObj0 });

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

			return (java.util.List<com.liferay.portal.model.Role>)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.model.Role getRole(
		HttpPrincipal httpPrincipal, long roleId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(roleId);

			MethodWrapper methodWrapper = new MethodWrapper(RoleServiceUtil.class.getName(),
					"getRole", new Object[] { paramObj0 });

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

			return (com.liferay.portal.model.Role)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.model.Role getRole(
		HttpPrincipal httpPrincipal, long companyId, java.lang.String name)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(companyId);

			Object paramObj1 = name;

			if (name == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(RoleServiceUtil.class.getName(),
					"getRole", new Object[] { paramObj0, paramObj1 });

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

			return (com.liferay.portal.model.Role)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.model.Role> getUserGroupGroupRoles(
		HttpPrincipal httpPrincipal, long userId, long groupId)
		throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(userId);

			Object paramObj1 = new LongWrapper(groupId);

			MethodWrapper methodWrapper = new MethodWrapper(RoleServiceUtil.class.getName(),
					"getUserGroupGroupRoles",
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

			return (java.util.List<com.liferay.portal.model.Role>)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.model.Role> getUserGroupRoles(
		HttpPrincipal httpPrincipal, long userId, long groupId)
		throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(userId);

			Object paramObj1 = new LongWrapper(groupId);

			MethodWrapper methodWrapper = new MethodWrapper(RoleServiceUtil.class.getName(),
					"getUserGroupRoles", new Object[] { paramObj0, paramObj1 });

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

			return (java.util.List<com.liferay.portal.model.Role>)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.model.Role> getUserRelatedRoles(
		HttpPrincipal httpPrincipal, long userId,
		java.util.List<com.liferay.portal.model.Group> groups)
		throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(userId);

			Object paramObj1 = groups;

			if (groups == null) {
				paramObj1 = new NullWrapper("java.util.List");
			}

			MethodWrapper methodWrapper = new MethodWrapper(RoleServiceUtil.class.getName(),
					"getUserRelatedRoles", new Object[] { paramObj0, paramObj1 });

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

			return (java.util.List<com.liferay.portal.model.Role>)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.model.Role> getUserRoles(
		HttpPrincipal httpPrincipal, long userId)
		throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(userId);

			MethodWrapper methodWrapper = new MethodWrapper(RoleServiceUtil.class.getName(),
					"getUserRoles", new Object[] { paramObj0 });

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

			return (java.util.List<com.liferay.portal.model.Role>)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean hasUserRole(HttpPrincipal httpPrincipal, long userId,
		long companyId, java.lang.String name, boolean inherited)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(userId);

			Object paramObj1 = new LongWrapper(companyId);

			Object paramObj2 = name;

			if (name == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new BooleanWrapper(inherited);

			MethodWrapper methodWrapper = new MethodWrapper(RoleServiceUtil.class.getName(),
					"hasUserRole",
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

				throw new com.liferay.portal.SystemException(e);
			}

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean hasUserRoles(HttpPrincipal httpPrincipal,
		long userId, long companyId, java.lang.String[] names, boolean inherited)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(userId);

			Object paramObj1 = new LongWrapper(companyId);

			Object paramObj2 = names;

			if (names == null) {
				paramObj2 = new NullWrapper("[Ljava.lang.String;");
			}

			Object paramObj3 = new BooleanWrapper(inherited);

			MethodWrapper methodWrapper = new MethodWrapper(RoleServiceUtil.class.getName(),
					"hasUserRoles",
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

				throw new com.liferay.portal.SystemException(e);
			}

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void unsetUserRoles(HttpPrincipal httpPrincipal, long userId,
		long[] roleIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(userId);

			Object paramObj1 = roleIds;

			if (roleIds == null) {
				paramObj1 = new NullWrapper("[J");
			}

			MethodWrapper methodWrapper = new MethodWrapper(RoleServiceUtil.class.getName(),
					"unsetUserRoles", new Object[] { paramObj0, paramObj1 });

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

	public static com.liferay.portal.model.Role updateRole(
		HttpPrincipal httpPrincipal, long roleId, java.lang.String name,
		java.util.Map<java.util.Locale, String> titleMap,
		java.lang.String description, java.lang.String subtype)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(roleId);

			Object paramObj1 = name;

			if (name == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = titleMap;

			if (titleMap == null) {
				paramObj2 = new NullWrapper("java.util.Map");
			}

			Object paramObj3 = description;

			if (description == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = subtype;

			if (subtype == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(RoleServiceUtil.class.getName(),
					"updateRole",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4
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

			return (com.liferay.portal.model.Role)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(RoleServiceHttp.class);
}