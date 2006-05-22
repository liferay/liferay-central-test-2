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

package com.liferay.portal.service.http;

import com.liferay.portal.service.spring.LayoutServiceUtil;
import com.liferay.portal.shared.util.StackTraceUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.rmi.RemoteException;

/**
 * <a href="LayoutServiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class LayoutServiceSoap {
	public static com.liferay.portal.model.LayoutModel addLayout(
		java.lang.String groupId, boolean privateLayout,
		java.lang.String parentLayoutId, java.lang.String name,
		java.lang.String type, boolean hidden, java.lang.String friendlyURL)
		throws RemoteException {
		try {
			com.liferay.portal.model.Layout returnValue = LayoutServiceUtil.addLayout(groupId,
					privateLayout, parentLayoutId, name, type, hidden,
					friendlyURL);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static void deleteLayout(java.lang.String layoutId,
		java.lang.String ownerId) throws RemoteException {
		try {
			LayoutServiceUtil.deleteLayout(layoutId, ownerId);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portal.model.LayoutModel[] getLayouts(
		java.lang.String companyId, java.lang.String portletId,
		java.lang.String prefsKey, java.lang.String prefsValue)
		throws RemoteException {
		try {
			java.util.List returnValue = LayoutServiceUtil.getLayouts(companyId,
					portletId, prefsKey, prefsValue);

			return (com.liferay.portal.model.Layout[])returnValue.toArray(new com.liferay.portal.model.Layout[0]);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static void setLayouts(java.lang.String ownerId,
		java.lang.String parentLayoutId, java.lang.String[] layoutIds)
		throws RemoteException {
		try {
			LayoutServiceUtil.setLayouts(ownerId, parentLayoutId, layoutIds);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portal.model.LayoutModel updateLayout(
		java.lang.String layoutId, java.lang.String ownerId,
		java.lang.String parentLayoutId, java.lang.String name,
		java.lang.String languageId, java.lang.String type, boolean hidden,
		java.lang.String friendlyURL) throws RemoteException {
		try {
			com.liferay.portal.model.Layout returnValue = LayoutServiceUtil.updateLayout(layoutId,
					ownerId, parentLayoutId, name, languageId, type, hidden,
					friendlyURL);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portal.model.LayoutModel updateLayout(
		java.lang.String layoutId, java.lang.String ownerId,
		java.lang.String typeSettings) throws RemoteException {
		try {
			com.liferay.portal.model.Layout returnValue = LayoutServiceUtil.updateLayout(layoutId,
					ownerId, typeSettings);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portal.model.LayoutModel updateLookAndFeel(
		java.lang.String layoutId, java.lang.String ownerId,
		java.lang.String themeId, java.lang.String colorSchemeId)
		throws RemoteException {
		try {
			com.liferay.portal.model.Layout returnValue = LayoutServiceUtil.updateLookAndFeel(layoutId,
					ownerId, themeId, colorSchemeId);

			return returnValue;
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	private static Log _log = LogFactory.getLog(LayoutServiceSoap.class);
}