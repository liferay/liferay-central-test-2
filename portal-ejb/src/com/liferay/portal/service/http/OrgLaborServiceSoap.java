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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.OrgLaborServiceUtil;

import java.rmi.RemoteException;

/**
 * <a href="OrgLaborServiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class OrgLaborServiceSoap {
	public static com.liferay.portal.model.OrgLaborSoap addOrgLabor(
		java.lang.String organizationId, int typeId, int sunOpen, int sunClose,
		int monOpen, int monClose, int tueOpen, int tueClose, int wedOpen,
		int wedClose, int thuOpen, int thuClose, int friOpen, int friClose,
		int satOpen, int satClose) throws RemoteException {
		try {
			com.liferay.portal.model.OrgLabor returnValue = OrgLaborServiceUtil.addOrgLabor(organizationId,
					typeId, sunOpen, sunClose, monOpen, monClose, tueOpen,
					tueClose, wedOpen, wedClose, thuOpen, thuClose, friOpen,
					friClose, satOpen, satClose);

			return com.liferay.portal.model.OrgLaborSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static void deleteOrgLabor(java.lang.String orgLaborId)
		throws RemoteException {
		try {
			OrgLaborServiceUtil.deleteOrgLabor(orgLaborId);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.OrgLaborSoap getOrgLabor(
		java.lang.String orgLaborId) throws RemoteException {
		try {
			com.liferay.portal.model.OrgLabor returnValue = OrgLaborServiceUtil.getOrgLabor(orgLaborId);

			return com.liferay.portal.model.OrgLaborSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.OrgLaborSoap[] getOrgLabors(
		java.lang.String organizationId) throws RemoteException {
		try {
			java.util.List returnValue = OrgLaborServiceUtil.getOrgLabors(organizationId);

			return com.liferay.portal.model.OrgLaborSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.OrgLaborSoap updateOrgLabor(
		java.lang.String orgLaborId, int sunOpen, int sunClose, int monOpen,
		int monClose, int tueOpen, int tueClose, int wedOpen, int wedClose,
		int thuOpen, int thuClose, int friOpen, int friClose, int satOpen,
		int satClose) throws RemoteException {
		try {
			com.liferay.portal.model.OrgLabor returnValue = OrgLaborServiceUtil.updateOrgLabor(orgLaborId,
					sunOpen, sunClose, monOpen, monClose, tueOpen, tueClose,
					wedOpen, wedClose, thuOpen, thuClose, friOpen, friClose,
					satOpen, satClose);

			return com.liferay.portal.model.OrgLaborSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(OrgLaborServiceSoap.class);
}