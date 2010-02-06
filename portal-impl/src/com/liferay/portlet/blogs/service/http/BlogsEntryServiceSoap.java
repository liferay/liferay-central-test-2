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

package com.liferay.portlet.blogs.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.portlet.blogs.service.BlogsEntryServiceUtil;

import java.rmi.RemoteException;

/**
 * <a href="BlogsEntryServiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.liferay.portlet.blogs.service.BlogsEntryServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portlet.blogs.model.BlogsEntrySoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portlet.blogs.model.BlogsEntry}, that is translated to a
 * {@link com.liferay.portlet.blogs.model.BlogsEntrySoap}. Methods that SOAP cannot
 * safely wire are skipped.
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
 * <b>tunnel.servlet.hosts.allowed</b> in portal.properties to configure
 * security.
 * </p>
 *
 * <p>
 * The SOAP utility is only generated for remote services.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       BlogsEntryServiceHttp
 * @see       com.liferay.portlet.blogs.model.BlogsEntrySoap
 * @see       com.liferay.portlet.blogs.service.BlogsEntryServiceUtil
 * @generated
 */
public class BlogsEntryServiceSoap {
	public static com.liferay.portlet.blogs.model.BlogsEntrySoap addEntry(
		java.lang.String title, java.lang.String content, int displayDateMonth,
		int displayDateDay, int displayDateYear, int displayDateHour,
		int displayDateMinute, boolean allowTrackbacks,
		java.lang.String[] trackbacks,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.blogs.model.BlogsEntry returnValue = BlogsEntryServiceUtil.addEntry(title,
					content, displayDateMonth, displayDateDay, displayDateYear,
					displayDateHour, displayDateMinute, allowTrackbacks,
					trackbacks, serviceContext);

			return com.liferay.portlet.blogs.model.BlogsEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteEntry(long entryId) throws RemoteException {
		try {
			BlogsEntryServiceUtil.deleteEntry(entryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.blogs.model.BlogsEntrySoap[] getCompanyEntries(
		long companyId, int status, int max) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> returnValue =
				BlogsEntryServiceUtil.getCompanyEntries(companyId, status, max);

			return com.liferay.portlet.blogs.model.BlogsEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.blogs.model.BlogsEntrySoap getEntry(
		long entryId) throws RemoteException {
		try {
			com.liferay.portlet.blogs.model.BlogsEntry returnValue = BlogsEntryServiceUtil.getEntry(entryId);

			return com.liferay.portlet.blogs.model.BlogsEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.blogs.model.BlogsEntrySoap getEntry(
		long groupId, java.lang.String urlTitle) throws RemoteException {
		try {
			com.liferay.portlet.blogs.model.BlogsEntry returnValue = BlogsEntryServiceUtil.getEntry(groupId,
					urlTitle);

			return com.liferay.portlet.blogs.model.BlogsEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.blogs.model.BlogsEntrySoap[] getGroupEntries(
		long groupId, int status, int max) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> returnValue =
				BlogsEntryServiceUtil.getGroupEntries(groupId, status, max);

			return com.liferay.portlet.blogs.model.BlogsEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.blogs.model.BlogsEntrySoap[] getOrganizationEntries(
		long organizationId, int status, int max) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.blogs.model.BlogsEntry> returnValue =
				BlogsEntryServiceUtil.getOrganizationEntries(organizationId,
					status, max);

			return com.liferay.portlet.blogs.model.BlogsEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.blogs.model.BlogsEntrySoap updateEntry(
		long entryId, java.lang.String title, java.lang.String content,
		int displayDateMonth, int displayDateDay, int displayDateYear,
		int displayDateHour, int displayDateMinute, boolean allowTrackbacks,
		java.lang.String[] trackbacks,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.blogs.model.BlogsEntry returnValue = BlogsEntryServiceUtil.updateEntry(entryId,
					title, content, displayDateMonth, displayDateDay,
					displayDateYear, displayDateHour, displayDateMinute,
					allowTrackbacks, trackbacks, serviceContext);

			return com.liferay.portlet.blogs.model.BlogsEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(BlogsEntryServiceSoap.class);
}