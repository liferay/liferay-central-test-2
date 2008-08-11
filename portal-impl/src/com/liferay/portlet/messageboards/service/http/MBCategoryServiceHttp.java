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

package com.liferay.portlet.messageboards.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.BooleanWrapper;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.NullWrapper;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

import com.liferay.portlet.messageboards.service.MBCategoryServiceUtil;

/**
 * <a href="MBCategoryServiceHttp.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a HTTP utility for the
 * <code>com.liferay.portlet.messageboards.service.MBCategoryServiceUtil</code> service
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
 * @see com.liferay.portlet.messageboards.service.MBCategoryServiceUtil
 * @see com.liferay.portlet.messageboards.service.http.MBCategoryServiceSoap
 *
 */
public class MBCategoryServiceHttp {
	public static com.liferay.portlet.messageboards.model.MBCategory addCategory(
		HttpPrincipal httpPrincipal, long plid, long parentCategoryId,
		java.lang.String name, java.lang.String description,
		java.lang.String mailingListAddress, java.lang.String mailAddress,
		java.lang.String mailInProtocol, java.lang.String mailInServerName,
		java.lang.Boolean mailInUseSSL, java.lang.Integer mailInServerPort,
		java.lang.String mailInUserName, java.lang.String mailInPassword,
		java.lang.Integer mailInReadInterval,
		java.lang.Boolean mailOutConfigured,
		java.lang.String mailOutServerName, java.lang.Boolean mailOutUseSSL,
		java.lang.Integer mailOutServerPort, java.lang.String mailOutUserName,
		java.lang.String mailOutPassword,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(plid);

			Object paramObj1 = new LongWrapper(parentCategoryId);

			Object paramObj2 = name;

			if (name == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = description;

			if (description == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = mailingListAddress;

			if (mailingListAddress == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = mailAddress;

			if (mailAddress == null) {
				paramObj5 = new NullWrapper("java.lang.String");
			}

			Object paramObj6 = mailInProtocol;

			if (mailInProtocol == null) {
				paramObj6 = new NullWrapper("java.lang.String");
			}

			Object paramObj7 = mailInServerName;

			if (mailInServerName == null) {
				paramObj7 = new NullWrapper("java.lang.String");
			}

			Object paramObj8 = mailInUseSSL;

			if (mailInUseSSL == null) {
				paramObj8 = new NullWrapper("java.lang.Boolean");
			}

			Object paramObj9 = mailInServerPort;

			if (mailInServerPort == null) {
				paramObj9 = new NullWrapper("java.lang.Integer");
			}

			Object paramObj10 = mailInUserName;

			if (mailInUserName == null) {
				paramObj10 = new NullWrapper("java.lang.String");
			}

			Object paramObj11 = mailInPassword;

			if (mailInPassword == null) {
				paramObj11 = new NullWrapper("java.lang.String");
			}

			Object paramObj12 = mailInReadInterval;

			if (mailInReadInterval == null) {
				paramObj12 = new NullWrapper("java.lang.Integer");
			}

			Object paramObj13 = mailOutConfigured;

			if (mailOutConfigured == null) {
				paramObj13 = new NullWrapper("java.lang.Boolean");
			}

			Object paramObj14 = mailOutServerName;

			if (mailOutServerName == null) {
				paramObj14 = new NullWrapper("java.lang.String");
			}

			Object paramObj15 = mailOutUseSSL;

			if (mailOutUseSSL == null) {
				paramObj15 = new NullWrapper("java.lang.Boolean");
			}

			Object paramObj16 = mailOutServerPort;

			if (mailOutServerPort == null) {
				paramObj16 = new NullWrapper("java.lang.Integer");
			}

			Object paramObj17 = mailOutUserName;

			if (mailOutUserName == null) {
				paramObj17 = new NullWrapper("java.lang.String");
			}

			Object paramObj18 = mailOutPassword;

			if (mailOutPassword == null) {
				paramObj18 = new NullWrapper("java.lang.String");
			}

			Object paramObj19 = communityPermissions;

			if (communityPermissions == null) {
				paramObj19 = new NullWrapper("[Ljava.lang.String;");
			}

			Object paramObj20 = guestPermissions;

			if (guestPermissions == null) {
				paramObj20 = new NullWrapper("[Ljava.lang.String;");
			}

			MethodWrapper methodWrapper = new MethodWrapper(MBCategoryServiceUtil.class.getName(),
					"addCategory",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9,
						paramObj10, paramObj11, paramObj12, paramObj13,
						paramObj14, paramObj15, paramObj16, paramObj17,
						paramObj18, paramObj19, paramObj20
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

			return (com.liferay.portlet.messageboards.model.MBCategory)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.messageboards.model.MBCategory addCategory(
		HttpPrincipal httpPrincipal, long plid, long parentCategoryId,
		java.lang.String name, java.lang.String description,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(plid);

			Object paramObj1 = new LongWrapper(parentCategoryId);

			Object paramObj2 = name;

			if (name == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = description;

			if (description == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = new BooleanWrapper(addCommunityPermissions);

			Object paramObj5 = new BooleanWrapper(addGuestPermissions);

			MethodWrapper methodWrapper = new MethodWrapper(MBCategoryServiceUtil.class.getName(),
					"addCategory",
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

			return (com.liferay.portlet.messageboards.model.MBCategory)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.messageboards.model.MBCategory addCategory(
		HttpPrincipal httpPrincipal, long plid, long parentCategoryId,
		java.lang.String name, java.lang.String description,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(plid);

			Object paramObj1 = new LongWrapper(parentCategoryId);

			Object paramObj2 = name;

			if (name == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = description;

			if (description == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = communityPermissions;

			if (communityPermissions == null) {
				paramObj4 = new NullWrapper("[Ljava.lang.String;");
			}

			Object paramObj5 = guestPermissions;

			if (guestPermissions == null) {
				paramObj5 = new NullWrapper("[Ljava.lang.String;");
			}

			MethodWrapper methodWrapper = new MethodWrapper(MBCategoryServiceUtil.class.getName(),
					"addCategory",
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

			return (com.liferay.portlet.messageboards.model.MBCategory)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteCategory(HttpPrincipal httpPrincipal,
		long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(categoryId);

			MethodWrapper methodWrapper = new MethodWrapper(MBCategoryServiceUtil.class.getName(),
					"deleteCategory", new Object[] { paramObj0 });

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

	public static com.liferay.portlet.messageboards.model.MBCategory getCategory(
		HttpPrincipal httpPrincipal, long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(categoryId);

			MethodWrapper methodWrapper = new MethodWrapper(MBCategoryServiceUtil.class.getName(),
					"getCategory", new Object[] { paramObj0 });

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

			return (com.liferay.portlet.messageboards.model.MBCategory)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.messageboards.model.MBCategory> getCategories(
		HttpPrincipal httpPrincipal, long groupId, long parentCategoryId,
		int start, int end)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(groupId);

			Object paramObj1 = new LongWrapper(parentCategoryId);

			Object paramObj2 = new IntegerWrapper(start);

			Object paramObj3 = new IntegerWrapper(end);

			MethodWrapper methodWrapper = new MethodWrapper(MBCategoryServiceUtil.class.getName(),
					"getCategories",
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

			return (java.util.List<com.liferay.portlet.messageboards.model.MBCategory>)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static int getCategoriesCount(HttpPrincipal httpPrincipal,
		long groupId, long parentCategoryId)
		throws com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(groupId);

			Object paramObj1 = new LongWrapper(parentCategoryId);

			MethodWrapper methodWrapper = new MethodWrapper(MBCategoryServiceUtil.class.getName(),
					"getCategoriesCount", new Object[] { paramObj0, paramObj1 });

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

	public static void subscribeCategory(HttpPrincipal httpPrincipal,
		long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(categoryId);

			MethodWrapper methodWrapper = new MethodWrapper(MBCategoryServiceUtil.class.getName(),
					"subscribeCategory", new Object[] { paramObj0 });

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

	public static void unsubscribeCategory(HttpPrincipal httpPrincipal,
		long categoryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(categoryId);

			MethodWrapper methodWrapper = new MethodWrapper(MBCategoryServiceUtil.class.getName(),
					"unsubscribeCategory", new Object[] { paramObj0 });

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

	public static com.liferay.portlet.messageboards.model.MBCategory updateCategory(
		HttpPrincipal httpPrincipal, long categoryId, long parentCategoryId,
		java.lang.String name, java.lang.String description,
		boolean mergeWithParentCategory, java.lang.String mailingListAddress,
		java.lang.String mailAddress, java.lang.String mailInProtocol,
		java.lang.String mailInServerName, java.lang.Boolean mailInUseSSL,
		java.lang.Integer mailInServerPort, java.lang.String mailInUserName,
		java.lang.String mailInPassword, java.lang.Integer mailInReadInterval,
		java.lang.Boolean mailOutConfigured,
		java.lang.String mailOutServerName, java.lang.Boolean mailOutUseSSL,
		java.lang.Integer mailOutServerPort, java.lang.String mailOutUserName,
		java.lang.String mailOutPassword)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(categoryId);

			Object paramObj1 = new LongWrapper(parentCategoryId);

			Object paramObj2 = name;

			if (name == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = description;

			if (description == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = new BooleanWrapper(mergeWithParentCategory);

			Object paramObj5 = mailingListAddress;

			if (mailingListAddress == null) {
				paramObj5 = new NullWrapper("java.lang.String");
			}

			Object paramObj6 = mailAddress;

			if (mailAddress == null) {
				paramObj6 = new NullWrapper("java.lang.String");
			}

			Object paramObj7 = mailInProtocol;

			if (mailInProtocol == null) {
				paramObj7 = new NullWrapper("java.lang.String");
			}

			Object paramObj8 = mailInServerName;

			if (mailInServerName == null) {
				paramObj8 = new NullWrapper("java.lang.String");
			}

			Object paramObj9 = mailInUseSSL;

			if (mailInUseSSL == null) {
				paramObj9 = new NullWrapper("java.lang.Boolean");
			}

			Object paramObj10 = mailInServerPort;

			if (mailInServerPort == null) {
				paramObj10 = new NullWrapper("java.lang.Integer");
			}

			Object paramObj11 = mailInUserName;

			if (mailInUserName == null) {
				paramObj11 = new NullWrapper("java.lang.String");
			}

			Object paramObj12 = mailInPassword;

			if (mailInPassword == null) {
				paramObj12 = new NullWrapper("java.lang.String");
			}

			Object paramObj13 = mailInReadInterval;

			if (mailInReadInterval == null) {
				paramObj13 = new NullWrapper("java.lang.Integer");
			}

			Object paramObj14 = mailOutConfigured;

			if (mailOutConfigured == null) {
				paramObj14 = new NullWrapper("java.lang.Boolean");
			}

			Object paramObj15 = mailOutServerName;

			if (mailOutServerName == null) {
				paramObj15 = new NullWrapper("java.lang.String");
			}

			Object paramObj16 = mailOutUseSSL;

			if (mailOutUseSSL == null) {
				paramObj16 = new NullWrapper("java.lang.Boolean");
			}

			Object paramObj17 = mailOutServerPort;

			if (mailOutServerPort == null) {
				paramObj17 = new NullWrapper("java.lang.Integer");
			}

			Object paramObj18 = mailOutUserName;

			if (mailOutUserName == null) {
				paramObj18 = new NullWrapper("java.lang.String");
			}

			Object paramObj19 = mailOutPassword;

			if (mailOutPassword == null) {
				paramObj19 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(MBCategoryServiceUtil.class.getName(),
					"updateCategory",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9,
						paramObj10, paramObj11, paramObj12, paramObj13,
						paramObj14, paramObj15, paramObj16, paramObj17,
						paramObj18, paramObj19
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

			return (com.liferay.portlet.messageboards.model.MBCategory)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(MBCategoryServiceHttp.class);
}