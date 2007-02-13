/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.tags.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.portlet.tags.service.TagsEntryServiceUtil;

import java.rmi.RemoteException;

/**
 * <a href="TagsEntryServiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TagsEntryServiceSoap {
	public static com.liferay.portlet.tags.model.TagsEntrySoap addEntry(
		java.lang.String name) throws RemoteException {
		try {
			com.liferay.portlet.tags.model.TagsEntry returnValue = TagsEntryServiceUtil.addEntry(name);

			return com.liferay.portlet.tags.model.TagsEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteEntry(long entryId) throws RemoteException {
		try {
			TagsEntryServiceUtil.deleteEntry(entryId);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.tags.model.TagsEntrySoap[] getEntries(
		java.lang.String className, java.lang.String classPK)
		throws RemoteException {
		try {
			java.util.List returnValue = TagsEntryServiceUtil.getEntries(className,
					classPK);

			return com.liferay.portlet.tags.model.TagsEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.tags.model.TagsEntrySoap[] search(
		java.lang.String companyId, java.lang.String name,
		java.lang.String[] properties) throws RemoteException {
		try {
			java.util.List returnValue = TagsEntryServiceUtil.search(companyId,
					name, properties);

			return com.liferay.portlet.tags.model.TagsEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.tags.model.TagsEntrySoap[] search(
		java.lang.String companyId, java.lang.String name,
		java.lang.String[] properties, int begin, int end)
		throws RemoteException {
		try {
			java.util.List returnValue = TagsEntryServiceUtil.search(companyId,
					name, properties, begin, end);

			return com.liferay.portlet.tags.model.TagsEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.String searchAutocomplete(
		java.lang.String companyId, java.lang.String name,
		java.lang.String[] properties, int begin, int end)
		throws RemoteException {
		try {
			java.lang.String returnValue = TagsEntryServiceUtil.searchAutocomplete(companyId,
					name, properties, begin, end);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static int searchCount(java.lang.String companyId,
		java.lang.String name, java.lang.String[] properties)
		throws RemoteException {
		try {
			int returnValue = TagsEntryServiceUtil.searchCount(companyId, name,
					properties);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.tags.model.TagsEntrySoap updateEntry(
		long entryId, java.lang.String name) throws RemoteException {
		try {
			com.liferay.portlet.tags.model.TagsEntry returnValue = TagsEntryServiceUtil.updateEntry(entryId,
					name);

			return com.liferay.portlet.tags.model.TagsEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.tags.model.TagsEntrySoap updateEntry(
		long entryId, java.lang.String name, java.lang.String[] properties)
		throws RemoteException {
		try {
			com.liferay.portlet.tags.model.TagsEntry returnValue = TagsEntryServiceUtil.updateEntry(entryId,
					name, properties);

			return com.liferay.portlet.tags.model.TagsEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(TagsEntryServiceSoap.class);
}