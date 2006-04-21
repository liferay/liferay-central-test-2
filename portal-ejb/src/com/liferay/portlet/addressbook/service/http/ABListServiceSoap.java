/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet.addressbook.service.http;

import com.liferay.portal.shared.util.StackTraceUtil;

import com.liferay.portlet.addressbook.service.spring.ABListServiceUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.rmi.RemoteException;

/**
 * <a href="ABListServiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class ABListServiceSoap {
	public static com.liferay.portlet.addressbook.model.ABListModel addList(
		java.lang.String name) throws RemoteException {
		try {
			com.liferay.portlet.addressbook.model.ABList returnValue = ABListServiceUtil.addList(name);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static void deleteList(java.lang.String listId)
		throws RemoteException {
		try {
			ABListServiceUtil.deleteList(listId);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.addressbook.model.ABListModel[] getContacts(
		java.lang.String listId) throws RemoteException {
		try {
			java.util.List returnValue = ABListServiceUtil.getContacts(listId);

			return (com.liferay.portlet.addressbook.model.ABList[])returnValue.toArray(new com.liferay.portlet.addressbook.model.ABList[0]);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.addressbook.model.ABListModel getList(
		java.lang.String listId) throws RemoteException {
		try {
			com.liferay.portlet.addressbook.model.ABList returnValue = ABListServiceUtil.getList(listId);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.addressbook.model.ABListModel[] getLists()
		throws RemoteException {
		try {
			java.util.List returnValue = ABListServiceUtil.getLists();

			return (com.liferay.portlet.addressbook.model.ABList[])returnValue.toArray(new com.liferay.portlet.addressbook.model.ABList[0]);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static int getListsSize() throws RemoteException {
		try {
			int returnValue = ABListServiceUtil.getListsSize();

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static void setContacts(java.lang.String listId,
		java.util.List contacts) throws RemoteException {
		try {
			ABListServiceUtil.setContacts(listId, contacts);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.addressbook.model.ABListModel updateList(
		java.lang.String listId, java.lang.String name)
		throws RemoteException {
		try {
			com.liferay.portlet.addressbook.model.ABList returnValue = ABListServiceUtil.updateList(listId,
					name);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	private static Log _log = LogFactory.getLog(ABListServiceSoap.class);
}