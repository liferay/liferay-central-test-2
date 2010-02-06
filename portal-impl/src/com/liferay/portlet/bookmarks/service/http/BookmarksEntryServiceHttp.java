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

package com.liferay.portlet.bookmarks.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.NullWrapper;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

import com.liferay.portlet.bookmarks.service.BookmarksEntryServiceUtil;

/**
 * <a href="BookmarksEntryServiceHttp.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a HTTP utility for the
 * {@link com.liferay.portlet.bookmarks.service.BookmarksEntryServiceUtil} service utility. The
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
 * @see       BookmarksEntryServiceSoap
 * @see       com.liferay.portal.security.auth.HttpPrincipal
 * @see       com.liferay.portlet.bookmarks.service.BookmarksEntryServiceUtil
 * @generated
 */
public class BookmarksEntryServiceHttp {
	public static com.liferay.portlet.bookmarks.model.BookmarksEntry addEntry(
		HttpPrincipal httpPrincipal, long groupId, long folderId,
		java.lang.String name, java.lang.String url, java.lang.String comments,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(groupId);

			Object paramObj1 = new LongWrapper(folderId);

			Object paramObj2 = name;

			if (name == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = url;

			if (url == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = comments;

			if (comments == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = serviceContext;

			if (serviceContext == null) {
				paramObj5 = new NullWrapper(
						"com.liferay.portal.service.ServiceContext");
			}

			MethodWrapper methodWrapper = new MethodWrapper(BookmarksEntryServiceUtil.class.getName(),
					"addEntry",
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

			return (com.liferay.portlet.bookmarks.model.BookmarksEntry)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteEntry(HttpPrincipal httpPrincipal, long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(entryId);

			MethodWrapper methodWrapper = new MethodWrapper(BookmarksEntryServiceUtil.class.getName(),
					"deleteEntry", new Object[] { paramObj0 });

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

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry getEntry(
		HttpPrincipal httpPrincipal, long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(entryId);

			MethodWrapper methodWrapper = new MethodWrapper(BookmarksEntryServiceUtil.class.getName(),
					"getEntry", new Object[] { paramObj0 });

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

			return (com.liferay.portlet.bookmarks.model.BookmarksEntry)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry openEntry(
		HttpPrincipal httpPrincipal, long entryId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(entryId);

			MethodWrapper methodWrapper = new MethodWrapper(BookmarksEntryServiceUtil.class.getName(),
					"openEntry", new Object[] { paramObj0 });

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

			return (com.liferay.portlet.bookmarks.model.BookmarksEntry)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntry updateEntry(
		HttpPrincipal httpPrincipal, long entryId, long groupId, long folderId,
		java.lang.String name, java.lang.String url, java.lang.String comments,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(entryId);

			Object paramObj1 = new LongWrapper(groupId);

			Object paramObj2 = new LongWrapper(folderId);

			Object paramObj3 = name;

			if (name == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = url;

			if (url == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = comments;

			if (comments == null) {
				paramObj5 = new NullWrapper("java.lang.String");
			}

			Object paramObj6 = serviceContext;

			if (serviceContext == null) {
				paramObj6 = new NullWrapper(
						"com.liferay.portal.service.ServiceContext");
			}

			MethodWrapper methodWrapper = new MethodWrapper(BookmarksEntryServiceUtil.class.getName(),
					"updateEntry",
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

			return (com.liferay.portlet.bookmarks.model.BookmarksEntry)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(BookmarksEntryServiceHttp.class);
}