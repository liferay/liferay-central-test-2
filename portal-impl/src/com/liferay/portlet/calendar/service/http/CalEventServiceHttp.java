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

package com.liferay.portlet.calendar.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.BooleanWrapper;
import com.liferay.portal.kernel.util.IntegerWrapper;
import com.liferay.portal.kernel.util.LongWrapper;
import com.liferay.portal.kernel.util.MethodWrapper;
import com.liferay.portal.kernel.util.NullWrapper;
import com.liferay.portal.security.auth.HttpPrincipal;
import com.liferay.portal.service.http.TunnelUtil;

import com.liferay.portlet.calendar.service.CalEventServiceUtil;

/**
 * <a href="CalEventServiceHttp.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a HTTP utility for the
 * {@link com.liferay.portlet.calendar.service.CalEventServiceUtil} service utility. The
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
 * @see       CalEventServiceSoap
 * @see       com.liferay.portal.security.auth.HttpPrincipal
 * @see       com.liferay.portlet.calendar.service.CalEventServiceUtil
 * @generated
 */
public class CalEventServiceHttp {
	public static com.liferay.portlet.calendar.model.CalEvent addEvent(
		HttpPrincipal httpPrincipal, java.lang.String title,
		java.lang.String description, int startDateMonth, int startDateDay,
		int startDateYear, int startDateHour, int startDateMinute,
		int endDateMonth, int endDateDay, int endDateYear, int durationHour,
		int durationMinute, boolean allDay, boolean timeZoneSensitive,
		java.lang.String type, boolean repeating,
		com.liferay.portal.kernel.cal.TZSRecurrence recurrence, int remindBy,
		int firstReminder, int secondReminder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = title;

			if (title == null) {
				paramObj0 = new NullWrapper("java.lang.String");
			}

			Object paramObj1 = description;

			if (description == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = new IntegerWrapper(startDateMonth);

			Object paramObj3 = new IntegerWrapper(startDateDay);

			Object paramObj4 = new IntegerWrapper(startDateYear);

			Object paramObj5 = new IntegerWrapper(startDateHour);

			Object paramObj6 = new IntegerWrapper(startDateMinute);

			Object paramObj7 = new IntegerWrapper(endDateMonth);

			Object paramObj8 = new IntegerWrapper(endDateDay);

			Object paramObj9 = new IntegerWrapper(endDateYear);

			Object paramObj10 = new IntegerWrapper(durationHour);

			Object paramObj11 = new IntegerWrapper(durationMinute);

			Object paramObj12 = new BooleanWrapper(allDay);

			Object paramObj13 = new BooleanWrapper(timeZoneSensitive);

			Object paramObj14 = type;

			if (type == null) {
				paramObj14 = new NullWrapper("java.lang.String");
			}

			Object paramObj15 = new BooleanWrapper(repeating);

			Object paramObj16 = recurrence;

			if (recurrence == null) {
				paramObj16 = new NullWrapper(
						"com.liferay.portal.kernel.cal.TZSRecurrence");
			}

			Object paramObj17 = new IntegerWrapper(remindBy);

			Object paramObj18 = new IntegerWrapper(firstReminder);

			Object paramObj19 = new IntegerWrapper(secondReminder);

			Object paramObj20 = serviceContext;

			if (serviceContext == null) {
				paramObj20 = new NullWrapper(
						"com.liferay.portal.service.ServiceContext");
			}

			MethodWrapper methodWrapper = new MethodWrapper(CalEventServiceUtil.class.getName(),
					"addEvent",
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

			return (com.liferay.portlet.calendar.model.CalEvent)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void deleteEvent(HttpPrincipal httpPrincipal, long eventId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(eventId);

			MethodWrapper methodWrapper = new MethodWrapper(CalEventServiceUtil.class.getName(),
					"deleteEvent", new Object[] { paramObj0 });

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

	public static java.io.File exportEvent(HttpPrincipal httpPrincipal,
		long eventId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(eventId);

			MethodWrapper methodWrapper = new MethodWrapper(CalEventServiceUtil.class.getName(),
					"exportEvent", new Object[] { paramObj0 });

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

			return (java.io.File)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.io.File exportGroupEvents(HttpPrincipal httpPrincipal,
		long groupId, java.lang.String fileName)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(groupId);

			Object paramObj1 = fileName;

			if (fileName == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			MethodWrapper methodWrapper = new MethodWrapper(CalEventServiceUtil.class.getName(),
					"exportGroupEvents", new Object[] { paramObj0, paramObj1 });

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

			return (java.io.File)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portlet.calendar.model.CalEvent getEvent(
		HttpPrincipal httpPrincipal, long eventId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(eventId);

			MethodWrapper methodWrapper = new MethodWrapper(CalEventServiceUtil.class.getName(),
					"getEvent", new Object[] { paramObj0 });

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

			return (com.liferay.portlet.calendar.model.CalEvent)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void importICal4j(HttpPrincipal httpPrincipal, long groupId,
		java.io.File file)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(groupId);

			Object paramObj1 = file;

			if (file == null) {
				paramObj1 = new NullWrapper("java.io.File");
			}

			MethodWrapper methodWrapper = new MethodWrapper(CalEventServiceUtil.class.getName(),
					"importICal4j", new Object[] { paramObj0, paramObj1 });

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

	public static com.liferay.portlet.calendar.model.CalEvent updateEvent(
		HttpPrincipal httpPrincipal, long eventId, java.lang.String title,
		java.lang.String description, int startDateMonth, int startDateDay,
		int startDateYear, int startDateHour, int startDateMinute,
		int endDateMonth, int endDateDay, int endDateYear, int durationHour,
		int durationMinute, boolean allDay, boolean timeZoneSensitive,
		java.lang.String type, boolean repeating,
		com.liferay.portal.kernel.cal.TZSRecurrence recurrence, int remindBy,
		int firstReminder, int secondReminder,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		try {
			Object paramObj0 = new LongWrapper(eventId);

			Object paramObj1 = title;

			if (title == null) {
				paramObj1 = new NullWrapper("java.lang.String");
			}

			Object paramObj2 = description;

			if (description == null) {
				paramObj2 = new NullWrapper("java.lang.String");
			}

			Object paramObj3 = new IntegerWrapper(startDateMonth);

			Object paramObj4 = new IntegerWrapper(startDateDay);

			Object paramObj5 = new IntegerWrapper(startDateYear);

			Object paramObj6 = new IntegerWrapper(startDateHour);

			Object paramObj7 = new IntegerWrapper(startDateMinute);

			Object paramObj8 = new IntegerWrapper(endDateMonth);

			Object paramObj9 = new IntegerWrapper(endDateDay);

			Object paramObj10 = new IntegerWrapper(endDateYear);

			Object paramObj11 = new IntegerWrapper(durationHour);

			Object paramObj12 = new IntegerWrapper(durationMinute);

			Object paramObj13 = new BooleanWrapper(allDay);

			Object paramObj14 = new BooleanWrapper(timeZoneSensitive);

			Object paramObj15 = type;

			if (type == null) {
				paramObj15 = new NullWrapper("java.lang.String");
			}

			Object paramObj16 = new BooleanWrapper(repeating);

			Object paramObj17 = recurrence;

			if (recurrence == null) {
				paramObj17 = new NullWrapper(
						"com.liferay.portal.kernel.cal.TZSRecurrence");
			}

			Object paramObj18 = new IntegerWrapper(remindBy);

			Object paramObj19 = new IntegerWrapper(firstReminder);

			Object paramObj20 = new IntegerWrapper(secondReminder);

			Object paramObj21 = serviceContext;

			if (serviceContext == null) {
				paramObj21 = new NullWrapper(
						"com.liferay.portal.service.ServiceContext");
			}

			MethodWrapper methodWrapper = new MethodWrapper(CalEventServiceUtil.class.getName(),
					"updateEvent",
					new Object[] {
						paramObj0, paramObj1, paramObj2, paramObj3, paramObj4,
						paramObj5, paramObj6, paramObj7, paramObj8, paramObj9,
						paramObj10, paramObj11, paramObj12, paramObj13,
						paramObj14, paramObj15, paramObj16, paramObj17,
						paramObj18, paramObj19, paramObj20, paramObj21
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

			return (com.liferay.portlet.calendar.model.CalEvent)returnObj;
		}
		catch (com.liferay.portal.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CalEventServiceHttp.class);
}