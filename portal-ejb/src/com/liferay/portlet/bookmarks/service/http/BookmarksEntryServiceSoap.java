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

package com.liferay.portlet.bookmarks.service.http;

import com.liferay.portal.kernel.util.StackTraceUtil;

import com.liferay.portlet.bookmarks.service.spring.BookmarksEntryServiceUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.rmi.RemoteException;

/**
 * <a href="BookmarksEntryServiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class BookmarksEntryServiceSoap {
	public static com.liferay.portlet.bookmarks.model.BookmarksEntryModel addEntry(
		java.lang.String folderId, java.lang.String name, java.lang.String url,
		java.lang.String comments, boolean addCommunityPermissions,
		boolean addGuestPermissions) throws RemoteException {
		try {
			com.liferay.portlet.bookmarks.model.BookmarksEntry returnValue = BookmarksEntryServiceUtil.addEntry(folderId,
					name, url, comments, addCommunityPermissions,
					addGuestPermissions);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntryModel addEntry(
		java.lang.String folderId, java.lang.String name, java.lang.String url,
		java.lang.String comments, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions) throws RemoteException {
		try {
			com.liferay.portlet.bookmarks.model.BookmarksEntry returnValue = BookmarksEntryServiceUtil.addEntry(folderId,
					name, url, comments, communityPermissions, guestPermissions);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static void deleteEntry(java.lang.String entryId)
		throws RemoteException {
		try {
			BookmarksEntryServiceUtil.deleteEntry(entryId);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntryModel getEntry(
		java.lang.String entryId) throws RemoteException {
		try {
			com.liferay.portlet.bookmarks.model.BookmarksEntry returnValue = BookmarksEntryServiceUtil.getEntry(entryId);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntryModel openEntry(
		java.lang.String entryId) throws RemoteException {
		try {
			com.liferay.portlet.bookmarks.model.BookmarksEntry returnValue = BookmarksEntryServiceUtil.openEntry(entryId);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.bookmarks.model.BookmarksEntryModel updateEntry(
		java.lang.String entryId, java.lang.String folderId,
		java.lang.String name, java.lang.String url, java.lang.String comments)
		throws RemoteException {
		try {
			com.liferay.portlet.bookmarks.model.BookmarksEntry returnValue = BookmarksEntryServiceUtil.updateEntry(entryId,
					folderId, name, url, comments);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	private static Log _log = LogFactory.getLog(BookmarksEntryServiceSoap.class);
}