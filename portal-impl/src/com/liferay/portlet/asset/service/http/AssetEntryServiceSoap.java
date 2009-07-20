/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.asset.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.portlet.asset.service.AssetEntryServiceUtil;

import java.rmi.RemoteException;

public class AssetEntryServiceSoap {
	public static void deleteEntry(long entryId) throws RemoteException {
		try {
			AssetEntryServiceUtil.deleteEntry(entryId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetEntrySoap[] getCompanyEntries(
		long companyId, int start, int end) throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.asset.model.AssetEntry> returnValue =
				AssetEntryServiceUtil.getCompanyEntries(companyId, start, end);

			return com.liferay.portlet.asset.model.AssetEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getCompanyEntriesCount(long companyId)
		throws RemoteException {
		try {
			int returnValue = AssetEntryServiceUtil.getCompanyEntriesCount(companyId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.String getCompanyEntriesRSS(long companyId,
		int max, java.lang.String type, double version,
		java.lang.String displayStyle, java.lang.String feedURL,
		java.lang.String tagURL) throws RemoteException {
		try {
			java.lang.String returnValue = AssetEntryServiceUtil.getCompanyEntriesRSS(companyId,
					max, type, version, displayStyle, feedURL, tagURL);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetEntryDisplay[] getCompanyEntryDisplays(
		long companyId, int start, int end, java.lang.String languageId)
		throws RemoteException {
		try {
			com.liferay.portlet.asset.model.AssetEntryDisplay[] returnValue = AssetEntryServiceUtil.getCompanyEntryDisplays(companyId,
					start, end, languageId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetEntrySoap[] getEntries(
		com.liferay.portlet.asset.service.persistence.AssetEntryQuery entryQuery)
		throws RemoteException {
		try {
			java.util.List<com.liferay.portlet.asset.model.AssetEntry> returnValue =
				AssetEntryServiceUtil.getEntries(entryQuery);

			return com.liferay.portlet.asset.model.AssetEntrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int getEntriesCount(
		com.liferay.portlet.asset.service.persistence.AssetEntryQuery entryQuery)
		throws RemoteException {
		try {
			int returnValue = AssetEntryServiceUtil.getEntriesCount(entryQuery);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static java.lang.String getEntriesRSS(
		com.liferay.portlet.asset.service.persistence.AssetEntryQuery entryQuery,
		java.lang.String type, double version, java.lang.String displayStyle,
		java.lang.String feedURL, java.lang.String tagURL)
		throws RemoteException {
		try {
			java.lang.String returnValue = AssetEntryServiceUtil.getEntriesRSS(entryQuery,
					type, version, displayStyle, feedURL, tagURL);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetEntrySoap getEntry(
		long entryId) throws RemoteException {
		try {
			com.liferay.portlet.asset.model.AssetEntry returnValue = AssetEntryServiceUtil.getEntry(entryId);

			return com.liferay.portlet.asset.model.AssetEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetEntryType[] getEntryTypes(
		java.lang.String languageId) throws RemoteException {
		try {
			com.liferay.portlet.asset.model.AssetEntryType[] returnValue = AssetEntryServiceUtil.getEntryTypes(languageId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetEntrySoap incrementViewCounter(
		java.lang.String className, long classPK) throws RemoteException {
		try {
			com.liferay.portlet.asset.model.AssetEntry returnValue = AssetEntryServiceUtil.incrementViewCounter(className,
					classPK);

			return com.liferay.portlet.asset.model.AssetEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetEntryDisplay[] searchEntryDisplays(
		long companyId, java.lang.String portletId, java.lang.String keywords,
		java.lang.String languageId, int start, int end)
		throws RemoteException {
		try {
			com.liferay.portlet.asset.model.AssetEntryDisplay[] returnValue = AssetEntryServiceUtil.searchEntryDisplays(companyId,
					portletId, keywords, languageId, start, end);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static int searchEntryDisplaysCount(long companyId,
		java.lang.String portletId, java.lang.String keywords,
		java.lang.String languageId) throws RemoteException {
		try {
			int returnValue = AssetEntryServiceUtil.searchEntryDisplaysCount(companyId,
					portletId, keywords, languageId);

			return returnValue;
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.asset.model.AssetEntrySoap updateEntry(
		long groupId, java.lang.String className, long classPK,
		long[] categoryIds, java.lang.String[] tagNames, boolean visible,
		java.util.Date startDate, java.util.Date endDate,
		java.util.Date publishDate, java.util.Date expirationDate,
		java.lang.String mimeType, java.lang.String title,
		java.lang.String description, java.lang.String summary,
		java.lang.String url, int height, int width,
		java.lang.Integer priority, boolean sync) throws RemoteException {
		try {
			com.liferay.portlet.asset.model.AssetEntry returnValue = AssetEntryServiceUtil.updateEntry(groupId,
					className, classPK, categoryIds, tagNames, visible,
					startDate, endDate, publishDate, expirationDate, mimeType,
					title, description, summary, url, height, width, priority,
					sync);

			return com.liferay.portlet.asset.model.AssetEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(AssetEntryServiceSoap.class);
}