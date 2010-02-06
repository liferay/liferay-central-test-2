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

package com.liferay.portlet.announcements.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.BooleanWrapper;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.NullWrapper;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

import com.liferay.portlet.announcements.service.AnnouncementsEntryServiceUtil;

/**
 * <a href="AnnouncementsEntryServiceHttp.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a HTTP utility for the
 * {@link com.liferay.portlet.announcements.service.AnnouncementsEntryServiceUtil} service utility. The
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
 * @see       AnnouncementsEntryServiceSoap
 * @see       com.liferay.portal.security.auth.HttpPrincipal
 * @see       com.liferay.portlet.announcements.service.AnnouncementsEntryServiceUtil
 * @generated
 */
public class AnnouncementsEntryServiceHttp {
	public static com.liferay.portlet.announcements.model.AnnouncementsEntry addEntry(
		HttpPrincipal httpPrincipal, long plid, long classNameId, long classPK,
		java.lang.String title, java.lang.String content, java.lang.String url,
		java.lang.String type, int displayDateMonth, int displayDateDay,
		int displayDateYear, int displayDateHour, int displayDateMinute,
		int expirationDateMonth, int expirationDateDay, int expirationDateYear,
		int expirationDateHour, int expirationDateMinute, int priority,
		boolean alert)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(plid);

			Object paramObj1 = new LongWrapper(classNameId);

			Object paramObj2 = new LongWrapper(classPK);

			Object paramObj3 = title;

			if (title == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = content;

			if (content == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = url;

			if (url == null) {
				paramObj5 = new NullWrapper("java.lang.String");
			}

			Object paramObj6 = type;

			if (type == null) {
				paramObj6 = new NullWrapper("java.lang.String");
			}

			Object paramObj7 = new IntegerWrapper(displayDateMonth);

			Object paramObj8 = new IntegerWrapper(displayDateDay);

			Object paramObj9 = new IntegerWrapper(displayDateYear);

			Object paramObj10 = new IntegerWrapper(displayDateHour);

			Object paramObj11 = new IntegerWrapper(displayDateMinute);

			Object paramObj12 = new IntegerWrapper(expirationDateMonth);

			Object paramObj13 = new IntegerWrapper(expirationDateDay);

			Object paramObj14 = new IntegerWrapper(expirationDateYear);

			Object paramObj15 = new IntegerWrapper(expirationDateHour);

			Object paramObj16 = new IntegerWrapper(expirationDateMinute);

			Object paramObj17 = new IntegerWrapper(priority);

			Object paramObj18 = new BooleanWrapper(alert);

			MethodWrapper methodWrapper = new MethodWrapper(AnnouncementsEntryServiceUtil.class.getName(),
					"addEntry",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9,
						paramObj10, paramObj11, paramObj12, paramObj13,
						paramObj14, paramObj15, paramObj16, paramObj17,
						paramObj18
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

			return (com.liferay.portlet.announcements.model.AnnouncementsEntry)returnObj;
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

			MethodWrapper methodWrapper = new MethodWrapper(AnnouncementsEntryServiceUtil.class.getName(),
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

	public static com.liferay.portlet.announcements.model.AnnouncementsEntry updateEntry(
		HttpPrincipal httpPrincipal, long entryId, java.lang.String title,
		java.lang.String content, java.lang.String url, java.lang.String type,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, int expirationDateMonth,
		int expirationDateDay, int expirationDateYear, int expirationDateHour,
		int expirationDateMinute, int priority)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
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

			Object paramObj3 = url;

			if (url == null) {
				paramObj3 = new NullWrapper("java.lang.String");
			}

			Object paramObj4 = type;

			if (type == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = new IntegerWrapper(displayDateMonth);

			Object paramObj6 = new IntegerWrapper(displayDateDay);

			Object paramObj7 = new IntegerWrapper(displayDateYear);

			Object paramObj8 = new IntegerWrapper(displayDateHour);

			Object paramObj9 = new IntegerWrapper(displayDateMinute);

			Object paramObj10 = new IntegerWrapper(expirationDateMonth);

			Object paramObj11 = new IntegerWrapper(expirationDateDay);

			Object paramObj12 = new IntegerWrapper(expirationDateYear);

			Object paramObj13 = new IntegerWrapper(expirationDateHour);

			Object paramObj14 = new IntegerWrapper(expirationDateMinute);

			Object paramObj15 = new IntegerWrapper(priority);

			MethodWrapper methodWrapper = new MethodWrapper(AnnouncementsEntryServiceUtil.class.getName(),
					"updateEntry",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9,
						paramObj10, paramObj11, paramObj12, paramObj13,
						paramObj14, paramObj15
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

			return (com.liferay.portlet.announcements.model.AnnouncementsEntry)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(AnnouncementsEntryServiceHttp.class);
}