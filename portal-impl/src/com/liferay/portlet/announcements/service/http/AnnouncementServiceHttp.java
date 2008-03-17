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

import com.liferay.portlet.announcements.service.AnnouncementServiceUtil;

/**
 * <a href="AnnouncementServiceHttp.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a HTTP utility for the
 * <code>com.liferay.portlet.announcements.service.AnnouncementServiceUtil</code> service
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
 * @see com.liferay.portlet.announcements.service.AnnouncementServiceUtil
 * @see com.liferay.portlet.announcements.service.http.AnnouncementServiceSoap
 *
 */
public class AnnouncementServiceHttp {
	public static com.liferay.portlet.announcements.model.Announcement addAnnouncement(
		HttpPrincipal httpPrincipal, long userId, long plid, long classNameId,
		long classPK, java.lang.String title, java.lang.String content,
		java.lang.String url, java.lang.String type, int displayMonth,
		int displayDay, int displayYear, int expirationMonth,
		int expirationDay, int expirationYear, int priority, boolean alert)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(userId);

			Object paramObj1 = new LongWrapper(plid);

			Object paramObj2 = new LongWrapper(classNameId);

			Object paramObj3 = new LongWrapper(classPK);

			Object paramObj4 = title;

			if (title == null) {
				paramObj4 = new NullWrapper("java.lang.String");
			}

			Object paramObj5 = content;

			if (content == null) {
				paramObj5 = new NullWrapper("java.lang.String");
			}

			Object paramObj6 = url;

			if (url == null) {
				paramObj6 = new NullWrapper("java.lang.String");
			}

			Object paramObj7 = type;

			if (type == null) {
				paramObj7 = new NullWrapper("java.lang.String");
			}

			Object paramObj8 = new IntegerWrapper(displayMonth);

			Object paramObj9 = new IntegerWrapper(displayDay);

			Object paramObj10 = new IntegerWrapper(displayYear);

			Object paramObj11 = new IntegerWrapper(expirationMonth);

			Object paramObj12 = new IntegerWrapper(expirationDay);

			Object paramObj13 = new IntegerWrapper(expirationYear);

			Object paramObj14 = new IntegerWrapper(priority);

			Object paramObj15 = new BooleanWrapper(alert);

			MethodWrapper methodWrapper = new MethodWrapper(AnnouncementServiceUtil.class.getName(),
					"addAnnouncement",
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
				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (com.liferay.portlet.announcements.model.Announcement)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteAnnouncement(HttpPrincipal httpPrincipal,
		long announcementId)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(announcementId);

			MethodWrapper methodWrapper = new MethodWrapper(AnnouncementServiceUtil.class.getName(),
					"deleteAnnouncement", new Object[] { paramObj0 });

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

	public static com.liferay.portlet.announcements.model.Announcement updateAnnouncement(
		HttpPrincipal httpPrincipal, long announcementId,
		java.lang.String title, java.lang.String content, java.lang.String url,
		java.lang.String type, int displayMonth, int displayDay,
		int displayYear, int expirationMonth, int expirationDay,
		int expirationYear, int priority)
		throws com.liferay.portal.SystemException,
			com.liferay.portal.PortalException {
		try {
			Object paramObj0 = new LongWrapper(announcementId);

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

			Object paramObj5 = new IntegerWrapper(displayMonth);

			Object paramObj6 = new IntegerWrapper(displayDay);

			Object paramObj7 = new IntegerWrapper(displayYear);

			Object paramObj8 = new IntegerWrapper(expirationMonth);

			Object paramObj9 = new IntegerWrapper(expirationDay);

			Object paramObj10 = new IntegerWrapper(expirationYear);

			Object paramObj11 = new IntegerWrapper(priority);

			MethodWrapper methodWrapper = new MethodWrapper(AnnouncementServiceUtil.class.getName(),
					"updateAnnouncement",
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
				if (e instanceof com.liferay.portal.SystemException) {
					throw (com.liferay.portal.SystemException)e;
				}

				if (e instanceof com.liferay.portal.PortalException) {
					throw (com.liferay.portal.PortalException)e;
				}

				throw new com.liferay.portal.SystemException(e);
			}

			return (com.liferay.portlet.announcements.model.Announcement)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(AnnouncementServiceHttp.class);
}