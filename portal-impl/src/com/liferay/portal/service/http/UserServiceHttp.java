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
import com.liferay.portal.service.UserServiceUtil;

/**
 * <a href="UserServiceHttp.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a HTTP utility for the
 * {@link com.liferay.portal.service.UserServiceUtil} service utility. The
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
 * @see       UserServiceSoap
 * @see       com.liferay.portal.security.auth.HttpPrincipal
 * @see       com.liferay.portal.service.UserServiceUtil
 * @generated
 */
public class UserServiceHttp {
	public static void addGroupUsers(HttpPrincipal httpPrincipal, long groupId,
		long[] userIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(groupId);

			Object paramObj1 = userIds;

			if (userIds == null) {
				paramObj1 = new NullWrapper("[J");
			}

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"addGroupUsers", new Object[] { paramObj0, paramObj1 });

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

	public static void addOrganizationUsers(HttpPrincipal httpPrincipal,
		long organizationId, long[] userIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(organizationId);

			Object paramObj1 = userIds;

			if (userIds == null) {
				paramObj1 = new NullWrapper("[J");
			}

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"addOrganizationUsers",
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
			_log.error(se, se);

			throw se;
		}
	}

	public static void addPasswordPolicyUsers(HttpPrincipal httpPrincipal,
		long passwordPolicyId, long[] userIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(passwordPolicyId);

			Object paramObj1 = userIds;

			if (userIds == null) {
				paramObj1 = new NullWrapper("[J");
			}

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"addPasswordPolicyUsers",
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
			_log.error(se, se);

			throw se;
		}
	}

	public static void addRoleUsers(HttpPrincipal httpPrincipal, long roleId,
		long[] userIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(roleId);

			Object paramObj1 = userIds;

			if (userIds == null) {
				paramObj1 = new NullWrapper("[J");
			}

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"addRoleUsers", new Object[] { paramObj0, paramObj1 });

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

	public static void addUserGroupUsers(HttpPrincipal httpPrincipal,
		long userGroupId, long[] userIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(userGroupId);

			Object paramObj1 = userIds;

			if (userIds == null) {
				paramObj1 = new NullWrapper("[J");
			}

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"addUserGroupUsers", new Object[] { paramObj0, paramObj1 });

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

	public static com.liferay.portal.model.User addUser(
		HttpPrincipal httpPrincipal, long companyId, boolean autoPassword,
		java.lang.String password1, java.lang.String password2,
		boolean autoScreenName, java.lang.String screenName,
		java.lang.String emailAddress, java.lang.String openId,
		java.util.Locale locale, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName, int prefixId,
		int suffixId, boolean male, int birthdayMonth, int birthdayDay,
		int birthdayYear, java.lang.String jobTitle, long[] groupIds,
		long[] organizationIds, long[] roleIds, long[] userGroupIds,
		boolean sendEmail,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(companyId);

			Object paramObj1 = new BooleanWrapper(autoPassword);

			Object paramObj2 = password1;

			if (password1 == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = password2;

			if (password2 == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = new BooleanWrapper(autoScreenName);

			Object paramObj5 = screenName;

			if (screenName == null) {
				paramObj5 = new NullWrapper("java.lang.String");
			}

			Object paramObj6 = emailAddress;

			if (emailAddress == null) {
				paramObj6 = new NullWrapper("java.lang.String");
			}

			Object paramObj7 = openId;

			if (openId == null) {
				paramObj7 = new NullWrapper("java.lang.String");
			}

			Object paramObj8 = locale;

			if (locale == null) {
				paramObj8 = new NullWrapper("java.util.Locale");
			}

			Object paramObj9 = firstName;

			if (firstName == null) {
				paramObj9 = new NullWrapper("java.lang.String");
			}

			Object paramObj10 = middleName;

			if (middleName == null) {
				paramObj10 = new NullWrapper("java.lang.String");
			}

			Object paramObj11 = lastName;

			if (lastName == null) {
				paramObj11 = new NullWrapper("java.lang.String");
			}

			Object paramObj12 = new IntegerWrapper(prefixId);

			Object paramObj13 = new IntegerWrapper(suffixId);

			Object paramObj14 = new BooleanWrapper(male);

			Object paramObj15 = new IntegerWrapper(birthdayMonth);

			Object paramObj16 = new IntegerWrapper(birthdayDay);

			Object paramObj17 = new IntegerWrapper(birthdayYear);

			Object paramObj18 = jobTitle;

			if (jobTitle == null) {
				paramObj18 = new NullWrapper("java.lang.String");
			}

			Object paramObj19 = groupIds;

			if (groupIds == null) {
				paramObj19 = new NullWrapper("[J");
			}

			Object paramObj20 = organizationIds;

			if (organizationIds == null) {
				paramObj20 = new NullWrapper("[J");
			}

			Object paramObj21 = roleIds;

			if (roleIds == null) {
				paramObj21 = new NullWrapper("[J");
			}

			Object paramObj22 = userGroupIds;

			if (userGroupIds == null) {
				paramObj22 = new NullWrapper("[J");
			}

			Object paramObj23 = new BooleanWrapper(sendEmail);

			Object paramObj24 = serviceContext;

			if (serviceContext == null) {
				paramObj24 = new NullWrapper(
						"com.liferay.portal.service.ServiceContext");
			}

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"addUser",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9,
						paramObj10, paramObj11, paramObj12, paramObj13,
						paramObj14, paramObj15, paramObj16, paramObj17,
						paramObj18, paramObj19, paramObj20, paramObj21,
						paramObj22, paramObj23, paramObj24
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

			return (com.liferay.portal.model.User)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.model.User addUser(
		HttpPrincipal httpPrincipal, long companyId, boolean autoPassword,
		java.lang.String password1, java.lang.String password2,
		boolean autoScreenName, java.lang.String screenName,
		java.lang.String emailAddress, java.lang.String openId,
		java.util.Locale locale, java.lang.String firstName,
		java.lang.String middleName, java.lang.String lastName, int prefixId,
		int suffixId, boolean male, int birthdayMonth, int birthdayDay,
		int birthdayYear, java.lang.String jobTitle, long[] groupIds,
		long[] organizationIds, long[] roleIds, long[] userGroupIds,
		boolean sendEmail,
		java.util.List<com.liferay.portal.model.Address> addresses,
		java.util.List<com.liferay.portal.model.EmailAddress> emailAddresses,
		java.util.List<com.liferay.portal.model.Phone> phones,
		java.util.List<com.liferay.portal.model.Website> websites,
		java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> announcementsDelivers,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(companyId);

			Object paramObj1 = new BooleanWrapper(autoPassword);

			Object paramObj2 = password1;

			if (password1 == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = password2;

			if (password2 == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = new BooleanWrapper(autoScreenName);

			Object paramObj5 = screenName;

			if (screenName == null) {
				paramObj5 = new NullWrapper("java.lang.String");
			}

			Object paramObj6 = emailAddress;

			if (emailAddress == null) {
				paramObj6 = new NullWrapper("java.lang.String");
			}

			Object paramObj7 = openId;

			if (openId == null) {
				paramObj7 = new NullWrapper("java.lang.String");
			}

			Object paramObj8 = locale;

			if (locale == null) {
				paramObj8 = new NullWrapper("java.util.Locale");
			}

			Object paramObj9 = firstName;

			if (firstName == null) {
				paramObj9 = new NullWrapper("java.lang.String");
			}

			Object paramObj10 = middleName;

			if (middleName == null) {
				paramObj10 = new NullWrapper("java.lang.String");
			}

			Object paramObj11 = lastName;

			if (lastName == null) {
				paramObj11 = new NullWrapper("java.lang.String");
			}

			Object paramObj12 = new IntegerWrapper(prefixId);

			Object paramObj13 = new IntegerWrapper(suffixId);

			Object paramObj14 = new BooleanWrapper(male);

			Object paramObj15 = new IntegerWrapper(birthdayMonth);

			Object paramObj16 = new IntegerWrapper(birthdayDay);

			Object paramObj17 = new IntegerWrapper(birthdayYear);

			Object paramObj18 = jobTitle;

			if (jobTitle == null) {
				paramObj18 = new NullWrapper("java.lang.String");
			}

			Object paramObj19 = groupIds;

			if (groupIds == null) {
				paramObj19 = new NullWrapper("[J");
			}

			Object paramObj20 = organizationIds;

			if (organizationIds == null) {
				paramObj20 = new NullWrapper("[J");
			}

			Object paramObj21 = roleIds;

			if (roleIds == null) {
				paramObj21 = new NullWrapper("[J");
			}

			Object paramObj22 = userGroupIds;

			if (userGroupIds == null) {
				paramObj22 = new NullWrapper("[J");
			}

			Object paramObj23 = new BooleanWrapper(sendEmail);

			Object paramObj24 = addresses;

			if (addresses == null) {
				paramObj24 = new NullWrapper("java.util.List");
			}

			Object paramObj25 = emailAddresses;

			if (emailAddresses == null) {
				paramObj25 = new NullWrapper("java.util.List");
			}

			Object paramObj26 = phones;

			if (phones == null) {
				paramObj26 = new NullWrapper("java.util.List");
			}

			Object paramObj27 = websites;

			if (websites == null) {
				paramObj27 = new NullWrapper("java.util.List");
			}

			Object paramObj28 = announcementsDelivers;

			if (announcementsDelivers == null) {
				paramObj28 = new NullWrapper("java.util.List");
			}

			Object paramObj29 = serviceContext;

			if (serviceContext == null) {
				paramObj29 = new NullWrapper(
						"com.liferay.portal.service.ServiceContext");
			}

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"addUser",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9,
						paramObj10, paramObj11, paramObj12, paramObj13,
						paramObj14, paramObj15, paramObj16, paramObj17,
						paramObj18, paramObj19, paramObj20, paramObj21,
						paramObj22, paramObj23, paramObj24, paramObj25,
						paramObj26, paramObj27, paramObj28, paramObj29
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

			return (com.liferay.portal.model.User)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deletePortrait(HttpPrincipal httpPrincipal, long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(userId);

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"deletePortrait", new Object[] { paramObj0 });

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

	public static void deleteRoleUser(HttpPrincipal httpPrincipal, long roleId,
		long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(roleId);

			Object paramObj1 = new LongWrapper(userId);

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"deleteRoleUser", new Object[] { paramObj0, paramObj1 });

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

	public static void deleteUser(HttpPrincipal httpPrincipal, long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(userId);

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"deleteUser", new Object[] { paramObj0 });

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

	public static long getDefaultUserId(HttpPrincipal httpPrincipal,
		long companyId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(companyId);

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"getDefaultUserId", new Object[] { paramObj0 });

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

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static long[] getGroupUserIds(HttpPrincipal httpPrincipal,
		long groupId) throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(groupId);

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"getGroupUserIds", new Object[] { paramObj0 });

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

			return (long[])returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static long[] getOrganizationUserIds(HttpPrincipal httpPrincipal,
		long organizationId) throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(organizationId);

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"getOrganizationUserIds", new Object[] { paramObj0 });

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

			return (long[])returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static long[] getRoleUserIds(HttpPrincipal httpPrincipal, long roleId)
		throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(roleId);

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"getRoleUserIds", new Object[] { paramObj0 });

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

			return (long[])returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.model.User getUserByEmailAddress(
		HttpPrincipal httpPrincipal, long companyId,
		java.lang.String emailAddress)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(companyId);

			Object paramObj1 = emailAddress;

			if (emailAddress == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"getUserByEmailAddress",
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

				throw new com.liferay.portal.SystemException(e);
			}

			return (com.liferay.portal.model.User)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.model.User getUserById(
		HttpPrincipal httpPrincipal, long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(userId);

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"getUserById", new Object[] { paramObj0 });

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

			return (com.liferay.portal.model.User)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.model.User getUserByScreenName(
		HttpPrincipal httpPrincipal, long companyId, java.lang.String screenName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(companyId);

			Object paramObj1 = screenName;

			if (screenName == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"getUserByScreenName", new Object[] { paramObj0, paramObj1 });

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

			return (com.liferay.portal.model.User)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static long getUserIdByEmailAddress(HttpPrincipal httpPrincipal,
		long companyId, java.lang.String emailAddress)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(companyId);

			Object paramObj1 = emailAddress;

			if (emailAddress == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"getUserIdByEmailAddress",
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

				throw new com.liferay.portal.SystemException(e);
			}

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static long getUserIdByScreenName(HttpPrincipal httpPrincipal,
		long companyId, java.lang.String screenName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(companyId);

			Object paramObj1 = screenName;

			if (screenName == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"getUserIdByScreenName",
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

				throw new com.liferay.portal.SystemException(e);
			}

			return ((Long)returnObj).longValue();
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean hasGroupUser(HttpPrincipal httpPrincipal,
		long groupId, long userId) throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(groupId);

			Object paramObj1 = new LongWrapper(userId);

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"hasGroupUser", new Object[] { paramObj0, paramObj1 });

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

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean hasRoleUser(HttpPrincipal httpPrincipal, long roleId,
		long userId) throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(roleId);

			Object paramObj1 = new LongWrapper(userId);

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"hasRoleUser", new Object[] { paramObj0, paramObj1 });

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

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean hasRoleUser(HttpPrincipal httpPrincipal,
		long companyId, java.lang.String name, long userId, boolean inherited)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(companyId);

			Object paramObj1 = name;

			if (name == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = new LongWrapper(userId);

			Object paramObj3 = new BooleanWrapper(inherited);

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"hasRoleUser",
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

	public static void setRoleUsers(HttpPrincipal httpPrincipal, long roleId,
		long[] userIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(roleId);

			Object paramObj1 = userIds;

			if (userIds == null) {
				paramObj1 = new NullWrapper("[J");
			}

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"setRoleUsers", new Object[] { paramObj0, paramObj1 });

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

	public static void setUserGroupUsers(HttpPrincipal httpPrincipal,
		long userGroupId, long[] userIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(userGroupId);

			Object paramObj1 = userIds;

			if (userIds == null) {
				paramObj1 = new NullWrapper("[J");
			}

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"setUserGroupUsers", new Object[] { paramObj0, paramObj1 });

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

	public static void unsetGroupUsers(HttpPrincipal httpPrincipal,
		long groupId, long[] userIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(groupId);

			Object paramObj1 = userIds;

			if (userIds == null) {
				paramObj1 = new NullWrapper("[J");
			}

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"unsetGroupUsers", new Object[] { paramObj0, paramObj1 });

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

	public static void unsetOrganizationUsers(HttpPrincipal httpPrincipal,
		long organizationId, long[] userIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(organizationId);

			Object paramObj1 = userIds;

			if (userIds == null) {
				paramObj1 = new NullWrapper("[J");
			}

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"unsetOrganizationUsers",
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
			_log.error(se, se);

			throw se;
		}
	}

	public static void unsetPasswordPolicyUsers(HttpPrincipal httpPrincipal,
		long passwordPolicyId, long[] userIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(passwordPolicyId);

			Object paramObj1 = userIds;

			if (userIds == null) {
				paramObj1 = new NullWrapper("[J");
			}

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"unsetPasswordPolicyUsers",
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
			_log.error(se, se);

			throw se;
		}
	}

	public static void unsetRoleUsers(HttpPrincipal httpPrincipal, long roleId,
		long[] userIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(roleId);

			Object paramObj1 = userIds;

			if (userIds == null) {
				paramObj1 = new NullWrapper("[J");
			}

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"unsetRoleUsers", new Object[] { paramObj0, paramObj1 });

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

	public static void unsetUserGroupUsers(HttpPrincipal httpPrincipal,
		long userGroupId, long[] userIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(userGroupId);

			Object paramObj1 = userIds;

			if (userIds == null) {
				paramObj1 = new NullWrapper("[J");
			}

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"unsetUserGroupUsers", new Object[] { paramObj0, paramObj1 });

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

	public static com.liferay.portal.model.User updateActive(
		HttpPrincipal httpPrincipal, long userId, boolean active)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(userId);

			Object paramObj1 = new BooleanWrapper(active);

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"updateActive", new Object[] { paramObj0, paramObj1 });

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

			return (com.liferay.portal.model.User)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.model.User updateAgreedToTermsOfUse(
		HttpPrincipal httpPrincipal, long userId, boolean agreedToTermsOfUse)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(userId);

			Object paramObj1 = new BooleanWrapper(agreedToTermsOfUse);

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"updateAgreedToTermsOfUse",
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

				throw new com.liferay.portal.SystemException(e);
			}

			return (com.liferay.portal.model.User)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void updateEmailAddress(HttpPrincipal httpPrincipal,
		long userId, java.lang.String password, java.lang.String emailAddress1,
		java.lang.String emailAddress2)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(userId);

			Object paramObj1 = password;

			if (password == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = emailAddress1;

			if (emailAddress1 == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = emailAddress2;

			if (emailAddress2 == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"updateEmailAddress",
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

	public static com.liferay.portal.model.User updateLockout(
		HttpPrincipal httpPrincipal, long userId, boolean lockout)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(userId);

			Object paramObj1 = new BooleanWrapper(lockout);

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"updateLockout", new Object[] { paramObj0, paramObj1 });

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

			return (com.liferay.portal.model.User)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void updateOpenId(HttpPrincipal httpPrincipal, long userId,
		java.lang.String openId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(userId);

			Object paramObj1 = openId;

			if (openId == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"updateOpenId", new Object[] { paramObj0, paramObj1 });

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

	public static void updateOrganizations(HttpPrincipal httpPrincipal,
		long userId, long[] organizationIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(userId);

			Object paramObj1 = organizationIds;

			if (organizationIds == null) {
				paramObj1 = new NullWrapper("[J");
			}

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"updateOrganizations", new Object[] { paramObj0, paramObj1 });

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

	public static com.liferay.portal.model.User updatePassword(
		HttpPrincipal httpPrincipal, long userId, java.lang.String password1,
		java.lang.String password2, boolean passwordReset)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(userId);

			Object paramObj1 = password1;

			if (password1 == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = password2;

			if (password2 == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new BooleanWrapper(passwordReset);

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"updatePassword",
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

			return (com.liferay.portal.model.User)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void updatePortrait(HttpPrincipal httpPrincipal, long userId,
		byte[] bytes)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(userId);

			Object paramObj1 = bytes;

			if (bytes == null) {
				paramObj1 = new NullWrapper("[B");
			}

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"updatePortrait", new Object[] { paramObj0, paramObj1 });

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

	public static void updateReminderQuery(HttpPrincipal httpPrincipal,
		long userId, java.lang.String question, java.lang.String answer)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(userId);

			Object paramObj1 = question;

			if (question == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = answer;

			if (answer == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"updateReminderQuery",
					new Object[] { paramObj0, paramObj1, paramObj2 });

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

	public static void updateScreenName(HttpPrincipal httpPrincipal,
		long userId, java.lang.String screenName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(userId);

			Object paramObj1 = screenName;

			if (screenName == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"updateScreenName", new Object[] { paramObj0, paramObj1 });

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

	public static com.liferay.portal.model.User updateUser(
		HttpPrincipal httpPrincipal, long userId, java.lang.String oldPassword,
		java.lang.String newPassword1, java.lang.String newPassword2,
		boolean passwordReset, java.lang.String reminderQueryQuestion,
		java.lang.String reminderQueryAnswer, java.lang.String screenName,
		java.lang.String emailAddress, java.lang.String openId,
		java.lang.String languageId, java.lang.String timeZoneId,
		java.lang.String greeting, java.lang.String comments,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, int prefixId, int suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String smsSn, java.lang.String aimSn,
		java.lang.String facebookSn, java.lang.String icqSn,
		java.lang.String jabberSn, java.lang.String msnSn,
		java.lang.String mySpaceSn, java.lang.String skypeSn,
		java.lang.String twitterSn, java.lang.String ymSn,
		java.lang.String jobTitle, long[] groupIds, long[] organizationIds,
		long[] roleIds,
		java.util.List<com.liferay.portal.model.UserGroupRole> userGroupRoles,
		long[] userGroupIds,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(userId);

			Object paramObj1 = oldPassword;

			if (oldPassword == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = newPassword1;

			if (newPassword1 == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = newPassword2;

			if (newPassword2 == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = new BooleanWrapper(passwordReset);

			Object paramObj5 = reminderQueryQuestion;

			if (reminderQueryQuestion == null) {
				paramObj5 = new NullWrapper("java.lang.String");
			}

			Object paramObj6 = reminderQueryAnswer;

			if (reminderQueryAnswer == null) {
				paramObj6 = new NullWrapper("java.lang.String");
			}

			Object paramObj7 = screenName;

			if (screenName == null) {
				paramObj7 = new NullWrapper("java.lang.String");
			}

			Object paramObj8 = emailAddress;

			if (emailAddress == null) {
				paramObj8 = new NullWrapper("java.lang.String");
			}

			Object paramObj9 = openId;

			if (openId == null) {
				paramObj9 = new NullWrapper("java.lang.String");
			}

			Object paramObj10 = languageId;

			if (languageId == null) {
				paramObj10 = new NullWrapper("java.lang.String");
			}

			Object paramObj11 = timeZoneId;

			if (timeZoneId == null) {
				paramObj11 = new NullWrapper("java.lang.String");
			}

			Object paramObj12 = greeting;

			if (greeting == null) {
				paramObj12 = new NullWrapper("java.lang.String");
			}

			Object paramObj13 = comments;

			if (comments == null) {
				paramObj13 = new NullWrapper("java.lang.String");
			}

			Object paramObj14 = firstName;

			if (firstName == null) {
				paramObj14 = new NullWrapper("java.lang.String");
			}

			Object paramObj15 = middleName;

			if (middleName == null) {
				paramObj15 = new NullWrapper("java.lang.String");
			}

			Object paramObj16 = lastName;

			if (lastName == null) {
				paramObj16 = new NullWrapper("java.lang.String");
			}

			Object paramObj17 = new IntegerWrapper(prefixId);

			Object paramObj18 = new IntegerWrapper(suffixId);

			Object paramObj19 = new BooleanWrapper(male);

			Object paramObj20 = new IntegerWrapper(birthdayMonth);

			Object paramObj21 = new IntegerWrapper(birthdayDay);

			Object paramObj22 = new IntegerWrapper(birthdayYear);

			Object paramObj23 = smsSn;

			if (smsSn == null) {
				paramObj23 = new NullWrapper("java.lang.String");
			}

			Object paramObj24 = aimSn;

			if (aimSn == null) {
				paramObj24 = new NullWrapper("java.lang.String");
			}

			Object paramObj25 = facebookSn;

			if (facebookSn == null) {
				paramObj25 = new NullWrapper("java.lang.String");
			}

			Object paramObj26 = icqSn;

			if (icqSn == null) {
				paramObj26 = new NullWrapper("java.lang.String");
			}

			Object paramObj27 = jabberSn;

			if (jabberSn == null) {
				paramObj27 = new NullWrapper("java.lang.String");
			}

			Object paramObj28 = msnSn;

			if (msnSn == null) {
				paramObj28 = new NullWrapper("java.lang.String");
			}

			Object paramObj29 = mySpaceSn;

			if (mySpaceSn == null) {
				paramObj29 = new NullWrapper("java.lang.String");
			}

			Object paramObj30 = skypeSn;

			if (skypeSn == null) {
				paramObj30 = new NullWrapper("java.lang.String");
			}

			Object paramObj31 = twitterSn;

			if (twitterSn == null) {
				paramObj31 = new NullWrapper("java.lang.String");
			}

			Object paramObj32 = ymSn;

			if (ymSn == null) {
				paramObj32 = new NullWrapper("java.lang.String");
			}

			Object paramObj33 = jobTitle;

			if (jobTitle == null) {
				paramObj33 = new NullWrapper("java.lang.String");
			}

			Object paramObj34 = groupIds;

			if (groupIds == null) {
				paramObj34 = new NullWrapper("[J");
			}

			Object paramObj35 = organizationIds;

			if (organizationIds == null) {
				paramObj35 = new NullWrapper("[J");
			}

			Object paramObj36 = roleIds;

			if (roleIds == null) {
				paramObj36 = new NullWrapper("[J");
			}

			Object paramObj37 = userGroupRoles;

			if (userGroupRoles == null) {
				paramObj37 = new NullWrapper("java.util.List");
			}

			Object paramObj38 = userGroupIds;

			if (userGroupIds == null) {
				paramObj38 = new NullWrapper("[J");
			}

			Object paramObj39 = serviceContext;

			if (serviceContext == null) {
				paramObj39 = new NullWrapper(
						"com.liferay.portal.service.ServiceContext");
			}

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"updateUser",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9,
						paramObj10, paramObj11, paramObj12, paramObj13,
						paramObj14, paramObj15, paramObj16, paramObj17,
						paramObj18, paramObj19, paramObj20, paramObj21,
						paramObj22, paramObj23, paramObj24, paramObj25,
						paramObj26, paramObj27, paramObj28, paramObj29,
						paramObj30, paramObj31, paramObj32, paramObj33,
						paramObj34, paramObj35, paramObj36, paramObj37,
						paramObj38, paramObj39
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

			return (com.liferay.portal.model.User)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.model.User updateUser(
		HttpPrincipal httpPrincipal, long userId, java.lang.String oldPassword,
		java.lang.String newPassword1, java.lang.String newPassword2,
		boolean passwordReset, java.lang.String reminderQueryQuestion,
		java.lang.String reminderQueryAnswer, java.lang.String screenName,
		java.lang.String emailAddress, java.lang.String openId,
		java.lang.String languageId, java.lang.String timeZoneId,
		java.lang.String greeting, java.lang.String comments,
		java.lang.String firstName, java.lang.String middleName,
		java.lang.String lastName, int prefixId, int suffixId, boolean male,
		int birthdayMonth, int birthdayDay, int birthdayYear,
		java.lang.String smsSn, java.lang.String aimSn,
		java.lang.String facebookSn, java.lang.String icqSn,
		java.lang.String jabberSn, java.lang.String msnSn,
		java.lang.String mySpaceSn, java.lang.String skypeSn,
		java.lang.String twitterSn, java.lang.String ymSn,
		java.lang.String jobTitle, long[] groupIds, long[] organizationIds,
		long[] roleIds,
		java.util.List<com.liferay.portal.model.UserGroupRole> userGroupRoles,
		long[] userGroupIds,
		java.util.List<com.liferay.portal.model.Address> addresses,
		java.util.List<com.liferay.portal.model.EmailAddress> emailAddresses,
		java.util.List<com.liferay.portal.model.Phone> phones,
		java.util.List<com.liferay.portal.model.Website> websites,
		java.util.List<com.liferay.portlet.announcements.model.AnnouncementsDelivery> announcementsDelivers,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(userId);

			Object paramObj1 = oldPassword;

			if (oldPassword == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = newPassword1;

			if (newPassword1 == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = newPassword2;

			if (newPassword2 == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = new BooleanWrapper(passwordReset);

			Object paramObj5 = reminderQueryQuestion;

			if (reminderQueryQuestion == null) {
				paramObj5 = new NullWrapper("java.lang.String");
			}

			Object paramObj6 = reminderQueryAnswer;

			if (reminderQueryAnswer == null) {
				paramObj6 = new NullWrapper("java.lang.String");
			}

			Object paramObj7 = screenName;

			if (screenName == null) {
				paramObj7 = new NullWrapper("java.lang.String");
			}

			Object paramObj8 = emailAddress;

			if (emailAddress == null) {
				paramObj8 = new NullWrapper("java.lang.String");
			}

			Object paramObj9 = openId;

			if (openId == null) {
				paramObj9 = new NullWrapper("java.lang.String");
			}

			Object paramObj10 = languageId;

			if (languageId == null) {
				paramObj10 = new NullWrapper("java.lang.String");
			}

			Object paramObj11 = timeZoneId;

			if (timeZoneId == null) {
				paramObj11 = new NullWrapper("java.lang.String");
			}

			Object paramObj12 = greeting;

			if (greeting == null) {
				paramObj12 = new NullWrapper("java.lang.String");
			}

			Object paramObj13 = comments;

			if (comments == null) {
				paramObj13 = new NullWrapper("java.lang.String");
			}

			Object paramObj14 = firstName;

			if (firstName == null) {
				paramObj14 = new NullWrapper("java.lang.String");
			}

			Object paramObj15 = middleName;

			if (middleName == null) {
				paramObj15 = new NullWrapper("java.lang.String");
			}

			Object paramObj16 = lastName;

			if (lastName == null) {
				paramObj16 = new NullWrapper("java.lang.String");
			}

			Object paramObj17 = new IntegerWrapper(prefixId);

			Object paramObj18 = new IntegerWrapper(suffixId);

			Object paramObj19 = new BooleanWrapper(male);

			Object paramObj20 = new IntegerWrapper(birthdayMonth);

			Object paramObj21 = new IntegerWrapper(birthdayDay);

			Object paramObj22 = new IntegerWrapper(birthdayYear);

			Object paramObj23 = smsSn;

			if (smsSn == null) {
				paramObj23 = new NullWrapper("java.lang.String");
			}

			Object paramObj24 = aimSn;

			if (aimSn == null) {
				paramObj24 = new NullWrapper("java.lang.String");
			}

			Object paramObj25 = facebookSn;

			if (facebookSn == null) {
				paramObj25 = new NullWrapper("java.lang.String");
			}

			Object paramObj26 = icqSn;

			if (icqSn == null) {
				paramObj26 = new NullWrapper("java.lang.String");
			}

			Object paramObj27 = jabberSn;

			if (jabberSn == null) {
				paramObj27 = new NullWrapper("java.lang.String");
			}

			Object paramObj28 = msnSn;

			if (msnSn == null) {
				paramObj28 = new NullWrapper("java.lang.String");
			}

			Object paramObj29 = mySpaceSn;

			if (mySpaceSn == null) {
				paramObj29 = new NullWrapper("java.lang.String");
			}

			Object paramObj30 = skypeSn;

			if (skypeSn == null) {
				paramObj30 = new NullWrapper("java.lang.String");
			}

			Object paramObj31 = twitterSn;

			if (twitterSn == null) {
				paramObj31 = new NullWrapper("java.lang.String");
			}

			Object paramObj32 = ymSn;

			if (ymSn == null) {
				paramObj32 = new NullWrapper("java.lang.String");
			}

			Object paramObj33 = jobTitle;

			if (jobTitle == null) {
				paramObj33 = new NullWrapper("java.lang.String");
			}

			Object paramObj34 = groupIds;

			if (groupIds == null) {
				paramObj34 = new NullWrapper("[J");
			}

			Object paramObj35 = organizationIds;

			if (organizationIds == null) {
				paramObj35 = new NullWrapper("[J");
			}

			Object paramObj36 = roleIds;

			if (roleIds == null) {
				paramObj36 = new NullWrapper("[J");
			}

			Object paramObj37 = userGroupRoles;

			if (userGroupRoles == null) {
				paramObj37 = new NullWrapper("java.util.List");
			}

			Object paramObj38 = userGroupIds;

			if (userGroupIds == null) {
				paramObj38 = new NullWrapper("[J");
			}

			Object paramObj39 = addresses;

			if (addresses == null) {
				paramObj39 = new NullWrapper("java.util.List");
			}

			Object paramObj40 = emailAddresses;

			if (emailAddresses == null) {
				paramObj40 = new NullWrapper("java.util.List");
			}

			Object paramObj41 = phones;

			if (phones == null) {
				paramObj41 = new NullWrapper("java.util.List");
			}

			Object paramObj42 = websites;

			if (websites == null) {
				paramObj42 = new NullWrapper("java.util.List");
			}

			Object paramObj43 = announcementsDelivers;

			if (announcementsDelivers == null) {
				paramObj43 = new NullWrapper("java.util.List");
			}

			Object paramObj44 = serviceContext;

			if (serviceContext == null) {
				paramObj44 = new NullWrapper(
						"com.liferay.portal.service.ServiceContext");
			}

			MethodWrapper methodWrapper = new MethodWrapper(UserServiceUtil.class.getName(),
					"updateUser",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9,
						paramObj10, paramObj11, paramObj12, paramObj13,
						paramObj14, paramObj15, paramObj16, paramObj17,
						paramObj18, paramObj19, paramObj20, paramObj21,
						paramObj22, paramObj23, paramObj24, paramObj25,
						paramObj26, paramObj27, paramObj28, paramObj29,
						paramObj30, paramObj31, paramObj32, paramObj33,
						paramObj34, paramObj35, paramObj36, paramObj37,
						paramObj38, paramObj39, paramObj40, paramObj41,
						paramObj42, paramObj43, paramObj44
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

			return (com.liferay.portal.model.User)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(UserServiceHttp.class);
}