/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.forms.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import com.liferay.portlet.forms.service.FormsStructureEntryServiceUtil;

import java.rmi.RemoteException;

/**
 * <p>
 * This class provides a SOAP utility for the
 * {@link com.liferay.portlet.forms.service.FormsStructureEntryServiceUtil} service utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it is difficult for SOAP to
 * support certain types.
 * </p>
 *
 * <p>
 * ServiceBuilder follows certain rules in translating the methods. For example,
 * if the method in the service utility returns a {@link java.util.List}, that
 * is translated to an array of {@link com.liferay.portlet.forms.model.FormsStructureEntrySoap}.
 * If the method in the service utility returns a
 * {@link com.liferay.portlet.forms.model.FormsStructureEntry}, that is translated to a
 * {@link com.liferay.portlet.forms.model.FormsStructureEntrySoap}. Methods that SOAP cannot
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
 * @see       FormsStructureEntryServiceHttp
 * @see       com.liferay.portlet.forms.model.FormsStructureEntrySoap
 * @see       com.liferay.portlet.forms.service.FormsStructureEntryServiceUtil
 * @generated
 */
public class FormsStructureEntryServiceSoap {
	public static com.liferay.portlet.forms.model.FormsStructureEntrySoap addEntry(
		long groupId, java.lang.String formStrucureId,
		boolean autoFormStrucureId, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.forms.model.FormsStructureEntry returnValue = FormsStructureEntryServiceUtil.addEntry(groupId,
					formStrucureId, autoFormStrucureId, name, description, xsd,
					serviceContext);

			return com.liferay.portlet.forms.model.FormsStructureEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteEntry(long groupId,
		java.lang.String formStructureId) throws RemoteException {
		try {
			FormsStructureEntryServiceUtil.deleteEntry(groupId, formStructureId);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.forms.model.FormsStructureEntrySoap fetchByG_F(
		long groupId, java.lang.String formStructureId)
		throws RemoteException {
		try {
			com.liferay.portlet.forms.model.FormsStructureEntry returnValue = FormsStructureEntryServiceUtil.fetchByG_F(groupId,
					formStructureId);

			return com.liferay.portlet.forms.model.FormsStructureEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.forms.model.FormsStructureEntrySoap getEntry(
		long groupId, java.lang.String formStructureId)
		throws RemoteException {
		try {
			com.liferay.portlet.forms.model.FormsStructureEntry returnValue = FormsStructureEntryServiceUtil.getEntry(groupId,
					formStructureId);

			return com.liferay.portlet.forms.model.FormsStructureEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portlet.forms.model.FormsStructureEntrySoap updateEntry(
		long groupId, java.lang.String formStructureId, java.lang.String name,
		java.lang.String description, java.lang.String xsd,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws RemoteException {
		try {
			com.liferay.portlet.forms.model.FormsStructureEntry returnValue = FormsStructureEntryServiceUtil.updateEntry(groupId,
					formStructureId, name, description, xsd, serviceContext);

			return com.liferay.portlet.forms.model.FormsStructureEntrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(FormsStructureEntryServiceSoap.class);
}