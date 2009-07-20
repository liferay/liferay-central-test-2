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

package com.liferay.portlet.journal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.portlet.journal.service.JournalFeedServiceUtil;

import java.rmi.RemoteException;

public class JournalFeedServiceSoap {
	public static com.liferay.portlet.journal.model.JournalFeedSoap addFeed(
		long groupId, java.lang.String feedId, boolean autoFeedId,
		java.lang.String name, java.lang.String description,
		java.lang.String type, java.lang.String structureId,
		java.lang.String templateId, java.lang.String rendererTemplateId,
		int delta, java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedType, double feedVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.journal.model.JournalFeed returnValue = JournalFeedServiceUtil.addFeed(groupId,
					feedId, autoFeedId, name, description, type, structureId,
					templateId, rendererTemplateId, delta, orderByCol,
					orderByType, targetLayoutFriendlyUrl, targetPortletId,
					contentField, feedType, feedVersion, serviceContext);

			return com.liferay.portlet.journal.model.JournalFeedSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFeed(long groupId, long feedId)
		throws RemoteException {
		try {
			JournalFeedServiceUtil.deleteFeed(groupId, feedId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteFeed(long groupId, java.lang.String feedId)
		throws RemoteException {
		try {
			JournalFeedServiceUtil.deleteFeed(groupId, feedId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.journal.model.JournalFeedSoap getFeed(
		long groupId, long feedId) throws RemoteException {
		try {
			com.liferay.portlet.journal.model.JournalFeed returnValue = JournalFeedServiceUtil.getFeed(groupId,
					feedId);

			return com.liferay.portlet.journal.model.JournalFeedSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.journal.model.JournalFeedSoap getFeed(
		long groupId, java.lang.String feedId) throws RemoteException {
		try {
			com.liferay.portlet.journal.model.JournalFeed returnValue = JournalFeedServiceUtil.getFeed(groupId,
					feedId);

			return com.liferay.portlet.journal.model.JournalFeedSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.journal.model.JournalFeedSoap updateFeed(
		long groupId, java.lang.String feedId, java.lang.String name,
		java.lang.String description, java.lang.String type,
		java.lang.String structureId, java.lang.String templateId,
		java.lang.String rendererTemplateId, int delta,
		java.lang.String orderByCol, java.lang.String orderByType,
		java.lang.String targetLayoutFriendlyUrl,
		java.lang.String targetPortletId, java.lang.String contentField,
		java.lang.String feedType, double feedVersion,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.journal.model.JournalFeed returnValue = JournalFeedServiceUtil.updateFeed(groupId,
					feedId, name, description, type, structureId, templateId,
					rendererTemplateId, delta, orderByCol, orderByType,
					targetLayoutFriendlyUrl, targetPortletId, contentField,
					feedType, feedVersion, serviceContext);

			return com.liferay.portlet.journal.model.JournalFeedSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(JournalFeedServiceSoap.class);
}