/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.blogs.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.BooleanWrapper;
import com.liferay.portal.kernel.util.DoubleWrapper;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.NullWrapper;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

import com.liferay.portlet.blogs.service.BlogsEntryServiceUtil;

/**
 * <a href="BlogsEntryServiceHttp.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a HTTP utility for the
 * {@link com.liferay.portlet.blogs.service.BlogsEntryServiceUtil} service utility. The
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
 * @see       BlogsEntryServiceSoap
 * @see       com.liferay.portal.security.auth.HttpPrincipal
 * @see       com.liferay.portlet.blogs.service.BlogsEntryServiceUtil
 * @generated
 */
public class BlogsEntryServiceHttp {
	public static com.liferay.portlet.blogs.model.BlogsEntry addEntry(
		HttpPrincipal httpPrincipal, java.lang.String title,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		boolean allowPingbacks, boolean allowTrackbacks,
		java.lang.String[] trackbacks,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			Object paramObj0 = title;

			if (title == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = content;

			if (content == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = new IntegerWrapper(displayDateMonth);

			Object paramObj3 = new IntegerWrapper(displayDateDay);

			Object paramObj4 = new IntegerWrapper(displayDateYear);

			Object paramObj5 = new IntegerWrapper(displayDateHour);

			Object paramObj6 = new IntegerWrapper(displayDateMinute);

			Object paramObj7 = new BooleanWrapper(allowPingbacks);

			Object paramObj8 = new BooleanWrapper(allowTrackbacks);

			Object paramObj9 = trackbacks;

			if (trackbacks == null) {
				paramObj9 = new NullWrapper("[Ljava.lang.String;");
			}

			Object paramObj10 = serviceContext;

			if (serviceContext == null) {
				paramObj10 = new NullWrapper(
						"com.liferay.portal.service.ServiceContext");
			}

			MethodWrapper methodWrapper = new MethodWrapper(BlogsEntryServiceUtil.class.getName(),
					"addEntry",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9,
						paramObj10
					});

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.blogs.model.BlogsEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteEntry(HttpPrincipal httpPrincipal, long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			Object paramObj0 = new LongWrapper(entryId);

			MethodWrapper methodWrapper = new MethodWrapper(BlogsEntryServiceUtil.class.getName(),
					"deleteEntry", new Object[] { paramObj0 });

			try {
				TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getCompanyEntries(
		HttpPrincipal httpPrincipal, long companyId, int status, int max)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			Object paramObj0 = new LongWrapper(companyId);

			Object paramObj1 = new IntegerWrapper(status);

			Object paramObj2 = new IntegerWrapper(max);

			MethodWrapper methodWrapper = new MethodWrapper(BlogsEntryServiceUtil.class.getName(),
					"getCompanyEntries",
					new Object[] { paramObj0, paramObj1, paramObj2 });

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.blogs.model.BlogsEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.lang.String getCompanyEntriesRSS(
		HttpPrincipal httpPrincipal, long companyId, int status, int max,
		java.lang.String type, double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			Object paramObj0 = new LongWrapper(companyId);

			Object paramObj1 = new IntegerWrapper(status);

			Object paramObj2 = new IntegerWrapper(max);

			Object paramObj3 = type;

			if (type == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = new DoubleWrapper(version);

			Object paramObj5 = displayStyle;

			if (displayStyle == null) {
				paramObj5 = new NullWrapper("java.lang.String");
			}

			Object paramObj6 = feedURL;

			if (feedURL == null) {
				paramObj6 = new NullWrapper("java.lang.String");
			}

			Object paramObj7 = entryURL;

			if (entryURL == null) {
				paramObj7 = new NullWrapper("java.lang.String");
			}

			Object paramObj8 = themeDisplay;

			if (themeDisplay == null) {
				paramObj8 = new NullWrapper(
						"com.liferay.portal.theme.ThemeDisplay");
			}

			MethodWrapper methodWrapper = new MethodWrapper(BlogsEntryServiceUtil.class.getName(),
					"getCompanyEntriesRSS",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8
					});

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.lang.String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry getEntry(
		HttpPrincipal httpPrincipal, long entryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			Object paramObj0 = new LongWrapper(entryId);

			MethodWrapper methodWrapper = new MethodWrapper(BlogsEntryServiceUtil.class.getName(),
					"getEntry", new Object[] { paramObj0 });

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.blogs.model.BlogsEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry getEntry(
		HttpPrincipal httpPrincipal, long groupId, java.lang.String urlTitle)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			Object paramObj0 = new LongWrapper(groupId);

			Object paramObj1 = urlTitle;

			if (urlTitle == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(BlogsEntryServiceUtil.class.getName(),
					"getEntry", new Object[] { paramObj0, paramObj1 });

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.blogs.model.BlogsEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getGroupEntries(
		HttpPrincipal httpPrincipal, long groupId, int status, int max)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			Object paramObj0 = new LongWrapper(groupId);

			Object paramObj1 = new IntegerWrapper(status);

			Object paramObj2 = new IntegerWrapper(max);

			MethodWrapper methodWrapper = new MethodWrapper(BlogsEntryServiceUtil.class.getName(),
					"getGroupEntries",
					new Object[] { paramObj0, paramObj1, paramObj2 });

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.blogs.model.BlogsEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.lang.String getGroupEntriesRSS(
		HttpPrincipal httpPrincipal, long groupId, int status, int max,
		java.lang.String type, double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			Object paramObj0 = new LongWrapper(groupId);

			Object paramObj1 = new IntegerWrapper(status);

			Object paramObj2 = new IntegerWrapper(max);

			Object paramObj3 = type;

			if (type == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = new DoubleWrapper(version);

			Object paramObj5 = displayStyle;

			if (displayStyle == null) {
				paramObj5 = new NullWrapper("java.lang.String");
			}

			Object paramObj6 = feedURL;

			if (feedURL == null) {
				paramObj6 = new NullWrapper("java.lang.String");
			}

			Object paramObj7 = entryURL;

			if (entryURL == null) {
				paramObj7 = new NullWrapper("java.lang.String");
			}

			Object paramObj8 = themeDisplay;

			if (themeDisplay == null) {
				paramObj8 = new NullWrapper(
						"com.liferay.portal.theme.ThemeDisplay");
			}

			MethodWrapper methodWrapper = new MethodWrapper(BlogsEntryServiceUtil.class.getName(),
					"getGroupEntriesRSS",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8
					});

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.lang.String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> getOrganizationEntries(
		HttpPrincipal httpPrincipal, long organizationId, int status, int max)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			Object paramObj0 = new LongWrapper(organizationId);

			Object paramObj1 = new IntegerWrapper(status);

			Object paramObj2 = new IntegerWrapper(max);

			MethodWrapper methodWrapper = new MethodWrapper(BlogsEntryServiceUtil.class.getName(),
					"getOrganizationEntries",
					new Object[] { paramObj0, paramObj1, paramObj2 });

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.util.List<com.liferay.portlet.blogs.model.BlogsEntry>)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.lang.String getOrganizationEntriesRSS(
		HttpPrincipal httpPrincipal, long organizationId, int status, int max,
		java.lang.String type, double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String entryURL,
		com.liferay.portal.theme.ThemeDisplay themeDisplay)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			Object paramObj0 = new LongWrapper(organizationId);

			Object paramObj1 = new IntegerWrapper(status);

			Object paramObj2 = new IntegerWrapper(max);

			Object paramObj3 = type;

			if (type == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = new DoubleWrapper(version);

			Object paramObj5 = displayStyle;

			if (displayStyle == null) {
				paramObj5 = new NullWrapper("java.lang.String");
			}

			Object paramObj6 = feedURL;

			if (feedURL == null) {
				paramObj6 = new NullWrapper("java.lang.String");
			}

			Object paramObj7 = entryURL;

			if (entryURL == null) {
				paramObj7 = new NullWrapper("java.lang.String");
			}

			Object paramObj8 = themeDisplay;

			if (themeDisplay == null) {
				paramObj8 = new NullWrapper(
						"com.liferay.portal.theme.ThemeDisplay");
			}

			MethodWrapper methodWrapper = new MethodWrapper(BlogsEntryServiceUtil.class.getName(),
					"getOrganizationEntriesRSS",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8
					});

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (java.lang.String)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry updateEntry(
		HttpPrincipal httpPrincipal, long entryId, java.lang.String title,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		boolean allowPingbacks, boolean allowTrackbacks,
		java.lang.String[] trackbacks,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		try {
			Object paramObj0 = new LongWrapper(entryId);

			Object paramObj1 = title;

			if (title == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = content;

			if (content == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new IntegerWrapper(displayDateMonth);

			Object paramObj4 = new IntegerWrapper(displayDateDay);

			Object paramObj5 = new IntegerWrapper(displayDateYear);

			Object paramObj6 = new IntegerWrapper(displayDateHour);

			Object paramObj7 = new IntegerWrapper(displayDateMinute);

			Object paramObj8 = new BooleanWrapper(allowPingbacks);

			Object paramObj9 = new BooleanWrapper(allowTrackbacks);

			Object paramObj10 = trackbacks;

			if (trackbacks == null) {
				paramObj10 = new NullWrapper("[Ljava.lang.String;");
			}

			Object paramObj11 = serviceContext;

			if (serviceContext == null) {
				paramObj11 = new NullWrapper(
						"com.liferay.portal.service.ServiceContext");
			}

			MethodWrapper methodWrapper = new MethodWrapper(BlogsEntryServiceUtil.class.getName(),
					"updateEntry",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9,
						paramObj10, paramObj11
					});

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodWrapper);
			}
			catch (Exception e) {
				if (e instanceof com.liferay.portal.kernel.exception.PortalException) {
					throw (com.liferay.portal.kernel.exception.PortalException)e;
				}

				if (e instanceof com.liferay.portal.kernel.exception.SystemException) {
					throw (com.liferay.portal.kernel.exception.SystemException)e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(e);
			}

			return (com.liferay.portlet.blogs.model.BlogsEntry)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(BlogsEntryServiceHttp.class);
}