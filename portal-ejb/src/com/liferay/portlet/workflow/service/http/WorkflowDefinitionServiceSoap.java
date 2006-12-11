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

package com.liferay.portlet.workflow.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StackTraceUtil;

import com.liferay.portlet.workflow.service.WorkflowDefinitionServiceUtil;

import java.rmi.RemoteException;

/**
 * <a href="WorkflowDefinitionServiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class WorkflowDefinitionServiceSoap {
	public static com.liferay.portlet.workflow.model.WorkflowDefinitionSoap addDefinition(
		java.lang.String xml, boolean addCommunityPermissions,
		boolean addGuestPermissions) throws RemoteException {
		try {
			com.liferay.portlet.workflow.model.WorkflowDefinition returnValue = WorkflowDefinitionServiceUtil.addDefinition(xml,
					addCommunityPermissions, addGuestPermissions);

			return com.liferay.portlet.workflow.model.WorkflowDefinitionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.workflow.model.WorkflowDefinitionSoap addDefinition(
		java.lang.String xml, java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions) throws RemoteException {
		try {
			com.liferay.portlet.workflow.model.WorkflowDefinition returnValue = WorkflowDefinitionServiceUtil.addDefinition(xml,
					communityPermissions, guestPermissions);

			return com.liferay.portlet.workflow.model.WorkflowDefinitionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.workflow.model.WorkflowDefinitionSoap addDefinition(
		java.lang.String xml, java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions) throws RemoteException {
		try {
			com.liferay.portlet.workflow.model.WorkflowDefinition returnValue = WorkflowDefinitionServiceUtil.addDefinition(xml,
					addCommunityPermissions, addGuestPermissions,
					communityPermissions, guestPermissions);

			return com.liferay.portlet.workflow.model.WorkflowDefinitionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	public static com.liferay.portlet.workflow.model.WorkflowDefinitionSoap getDefinition(
		long definitionId) throws RemoteException {
		try {
			com.liferay.portlet.workflow.model.WorkflowDefinition returnValue = WorkflowDefinitionServiceUtil.getDefinition(definitionId);

			return com.liferay.portlet.workflow.model.WorkflowDefinitionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			String stackTrace = StackTraceUtil.getStackTrace(e);
			_log.error(stackTrace);
			throw new RemoteException(stackTrace);
		}
	}

	private static Log _log = LogFactoryUtil.getLog(WorkflowDefinitionServiceSoap.class);
}