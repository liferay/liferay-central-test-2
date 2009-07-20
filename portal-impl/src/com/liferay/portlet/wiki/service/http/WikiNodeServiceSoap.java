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

package com.liferay.portlet.wiki.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.portlet.wiki.service.WikiNodeServiceUtil;

import java.rmi.RemoteException;

public class WikiNodeServiceSoap {
	public static com.liferay.portlet.wiki.model.WikiNodeSoap addNode(
		java.lang.String name, java.lang.String description,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.wiki.model.WikiNode returnValue = WikiNodeServiceUtil.addNode(name,
					description, serviceContext);

			return com.liferay.portlet.wiki.model.WikiNodeSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteNode(long nodeId) throws RemoteException {
		try {
			WikiNodeServiceUtil.deleteNode(nodeId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.wiki.model.WikiNodeSoap getNode(
		long nodeId) throws RemoteException {
		try {
			com.liferay.portlet.wiki.model.WikiNode returnValue = WikiNodeServiceUtil.getNode(nodeId);

			return com.liferay.portlet.wiki.model.WikiNodeSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.wiki.model.WikiNodeSoap getNode(
		long groupId, java.lang.String name) throws RemoteException {
		try {
			com.liferay.portlet.wiki.model.WikiNode returnValue = WikiNodeServiceUtil.getNode(groupId,
					name);

			return com.liferay.portlet.wiki.model.WikiNodeSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void subscribeNode(long nodeId) throws RemoteException {
		try {
			WikiNodeServiceUtil.subscribeNode(nodeId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void unsubscribeNode(long nodeId) throws RemoteException {
		try {
			WikiNodeServiceUtil.unsubscribeNode(nodeId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.wiki.model.WikiNodeSoap updateNode(
		long nodeId, java.lang.String name, java.lang.String description)
		throws RemoteException {
		try {
			com.liferay.portlet.wiki.model.WikiNode returnValue = WikiNodeServiceUtil.updateNode(nodeId,
					name, description);

			return com.liferay.portlet.wiki.model.WikiNodeSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(WikiNodeServiceSoap.class);
}