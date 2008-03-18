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

import com.liferay.portlet.announcements.service.AnnouncementEntryServiceUtil;

import java.rmi.RemoteException;

/**
 * <a href="AnnouncementEntryServiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a SOAP utility for the
 * <code>com.liferay.portlet.announcements.service.AnnouncementEntryServiceUtil</code> service
 * utility. The static methods of this class calls the same methods of the
 * service utility. However, the signatures are different because it is
 * difficult for SOAP to support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a <code>java.util.List</code>,
 * that is translated to an array of
 * <code>com.liferay.portlet.announcements.model.AnnouncementEntrySoap</code>. If the method in the
 * service utility returns a <code>com.liferay.portlet.announcements.model.AnnouncementEntry</code>,
 * that is translated to a <code>com.liferay.portlet.announcements.model.AnnouncementEntrySoap</code>.
 * Methods that SOAP cannot safely wire are skipped.
 * </p>
 *
 * <p>
 * The benefits of using the SOAP utility is that it is cross platform
 * compatible. SOAP allows different languages like Java, .NET, C++, PHP, and
 * even Perl, to call the generated services. One drawback of SOAP is that it is
 * slow because it needs to serialize all calls into a text format (XML).
 * </p>
 *
 * <p>
 * You can see a list of services at
 * http://localhost:8080/tunnel-web/secure/axis. Set the property
 * <code>tunnel.servlet.hosts.allowed</code> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.announcements.service.AnnouncementEntryServiceUtil
 * @see com.liferay.portlet.announcements.service.http.AnnouncementEntryServiceHttp
 * @see com.liferay.portlet.announcements.service.model.AnnouncementEntrySoap
 *
 */
public class AnnouncementEntryServiceSoap {
	public static com.liferay.portlet.announcements.model.AnnouncementEntrySoap addEntry(
		long userId, long plid, long classNameId, long classPK,
		java.lang.String title, java.lang.String content, java.lang.String url,
		java.lang.String type, int displayMonth, int displayDay,
		int displayYear, int expirationMonth, int expirationDay,
		int expirationYear, int priority, boolean alert)
		throws RemoteException {
		try {
			com.liferay.portlet.announcements.model.AnnouncementEntry returnValue =
				AnnouncementEntryServiceUtil.addEntry(userId, plid,
					classNameId, classPK, title, content, url, type,
					displayMonth, displayDay, displayYear, expirationMonth,
					expirationDay, expirationYear, priority, alert);

			return com.liferay.portlet.announcements.model.AnnouncementEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteEntry(long entryId) throws RemoteException {
		try {
			AnnouncementEntryServiceUtil.deleteEntry(entryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.announcements.model.AnnouncementEntrySoap updateEntry(
		long entryId, java.lang.String title, java.lang.String content,
		java.lang.String url, java.lang.String type, int displayMonth,
		int displayDay, int displayYear, int expirationMonth,
		int expirationDay, int expirationYear, int priority)
		throws RemoteException {
		try {
			com.liferay.portlet.announcements.model.AnnouncementEntry returnValue =
				AnnouncementEntryServiceUtil.updateEntry(entryId, title,
					content, url, type, displayMonth, displayDay, displayYear,
					expirationMonth, expirationDay, expirationYear, priority);

			return com.liferay.portlet.announcements.model.AnnouncementEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(AnnouncementEntryServiceSoap.class);
}