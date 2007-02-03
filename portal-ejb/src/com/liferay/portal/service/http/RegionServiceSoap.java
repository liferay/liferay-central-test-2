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
import com.liferay.portal.service.RegionServiceUtil;

import java.rmi.RemoteException;

/**
 * <a href="RegionServiceSoap.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class RegionServiceSoap {
	public static com.liferay.portal.model.RegionSoap[] getRegions()
		throws RemoteException {
		try {
			java.util.List returnValue = RegionServiceUtil.getRegions();

			return com.liferay.portal.model.RegionSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.RegionSoap[] getRegions(
		java.lang.String countryId) throws RemoteException {
		try {
			java.util.List returnValue = RegionServiceUtil.getRegions(countryId);

			return com.liferay.portal.model.RegionSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.RegionSoap[] getRegions(
		boolean active) throws RemoteException {
		try {
			java.util.List returnValue = RegionServiceUtil.getRegions(active);

			return com.liferay.portal.model.RegionSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.RegionSoap[] getRegions(
		java.lang.String countryId, boolean active) throws RemoteException {
		try {
			java.util.List returnValue = RegionServiceUtil.getRegions(countryId,
					active);

			return com.liferay.portal.model.RegionSoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.RegionSoap getRegion(
		java.lang.String regionId) throws RemoteException {
		try {
			com.liferay.portal.model.Region returnValue = RegionServiceUtil.getRegion(regionId);

			return com.liferay.portal.model.RegionSoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);
			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(RegionServiceSoap.class);
}