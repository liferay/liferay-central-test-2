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

package com.liferay.portlet.blogs.service.http;

import com.liferay.portal.kernel.util.BooleanWrapper;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.NullWrapper;
import com.liferay.portal.kernel.util.StackTraceUtil;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.servlet.TunnelUtil;

import com.liferay.portlet.blogs.service.spring.BlogsEntryServiceUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * <a href="BlogsEntryServiceHttp.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class BlogsEntryServiceHttp {
	public static com.liferay.portlet.blogs.model.BlogsEntry addEntry(
		HttpPrincipal httpPrincipal, java.lang.String plid,
		java.lang.String categoryId, java.lang.String title,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = plid;

			if (plid == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = categoryId;

			if (categoryId == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = title;

			if (title == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = content;

			if (content == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = new IntegerWrapper(displayDateMonth);
			Object paramObj5 = new IntegerWrapper(displayDateDay);
			Object paramObj6 = new IntegerWrapper(displayDateYear);
			Object paramObj7 = new IntegerWrapper(displayDateHour);
			Object paramObj8 = new IntegerWrapper(displayDateMinute);
			Object paramObj9 = new BooleanWrapper(addCommunityPermissions);
			Object paramObj10 = new BooleanWrapper(addGuestPermissions);
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
				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (com.liferay.portlet.blogs.model.BlogsEntry)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static void deleteEntry(HttpPrincipal httpPrincipal,
		java.lang.String entryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = entryId;

			if (entryId == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(BlogsEntryServiceUtil.class.getName(),
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
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry getEntry(
		HttpPrincipal httpPrincipal, java.lang.String entryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = entryId;

			if (entryId == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(BlogsEntryServiceUtil.class.getName(),
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

			return (com.liferay.portlet.blogs.model.BlogsEntry)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	public static com.liferay.portlet.blogs.model.BlogsEntry updateEntry(
		HttpPrincipal httpPrincipal, java.lang.String entryId,
		java.lang.String categoryId, java.lang.String title,
		java.lang.String content, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = entryId;

			if (entryId == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = categoryId;

			if (categoryId == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = title;

			if (title == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = content;

			if (content == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = new IntegerWrapper(displayDateMonth);
			Object paramObj5 = new IntegerWrapper(displayDateDay);
			Object paramObj6 = new IntegerWrapper(displayDateYear);
			Object paramObj7 = new IntegerWrapper(displayDateHour);
			Object paramObj8 = new IntegerWrapper(displayDateMinute);
			MethodWrapper methodWrapper = new MethodWrapper(BlogsEntryServiceUtil.class.getName(),
					"updateEntry",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8
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

			return (com.liferay.portlet.blogs.model.BlogsEntry)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			String stackTrace = StackTraceUtil.getStackTrace(se);
			_log.error(stackTrace);
			throw se;
		}
	}

	private static Log _log = LogFactory.getLog(BlogsEntryServiceHttp.class);
}