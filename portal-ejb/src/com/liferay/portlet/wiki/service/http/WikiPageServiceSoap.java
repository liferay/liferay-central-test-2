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

package com.liferay.portlet.wiki.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StackTraceUtil;

import com.liferay.portlet.wiki.service.WikiPageServiceUtil;

import java.rmi.RemoteException;

/**
 * <a href="WikiPageServiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class WikiPageServiceSoap {
	public static com.liferay.portlet.wiki.model.WikiPageSoap addPage(
		java.lang.String nodeId, java.lang.String title)
		throws RemoteException {
		try {
			com.liferay.portlet.wiki.model.WikiPage returnValue = WikiPageServiceUtil.addPage(nodeId,
					title);

			return com.liferay.portlet.wiki.model.WikiPageSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static void deletePage(java.lang.String nodeId,
		java.lang.String title) throws RemoteException {
		try {
			WikiPageServiceUtil.deletePage(nodeId, title);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.wiki.model.WikiPageSoap getPage(
		java.lang.String nodeId, java.lang.String title)
		throws RemoteException {
		try {
			com.liferay.portlet.wiki.model.WikiPage returnValue = WikiPageServiceUtil.getPage(nodeId,
					title);

			return com.liferay.portlet.wiki.model.WikiPageSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.wiki.model.WikiPageSoap getPage(
		java.lang.String nodeId, java.lang.String title, double version)
		throws RemoteException {
		try {
			com.liferay.portlet.wiki.model.WikiPage returnValue = WikiPageServiceUtil.getPage(nodeId,
					title, version);

			return com.liferay.portlet.wiki.model.WikiPageSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.wiki.model.WikiPageSoap revertPage(
		java.lang.String nodeId, java.lang.String title, double version)
		throws RemoteException {
		try {
			com.liferay.portlet.wiki.model.WikiPage returnValue = WikiPageServiceUtil.revertPage(nodeId,
					title, version);

			return com.liferay.portlet.wiki.model.WikiPageSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.wiki.model.WikiPageSoap updatePage(
		java.lang.String nodeId, java.lang.String title,
		java.lang.String content, java.lang.String format)
		throws RemoteException {
		try {
			com.liferay.portlet.wiki.model.WikiPage returnValue = WikiPageServiceUtil.updatePage(nodeId,
					title, content, format);

			return com.liferay.portlet.wiki.model.WikiPageSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(WikiPageServiceSoap.class);
}