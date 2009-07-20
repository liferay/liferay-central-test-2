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

package com.liferay.portal.service.http;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.service.CountryServiceUtil;

import java.rmi.RemoteException;

public class CountryServiceSoap {
	public static com.liferay.portal.model.CountrySoap addCountry(
		java.lang.String name, java.lang.String a2, java.lang.String a3,
		java.lang.String number, java.lang.String idd, boolean active)
		throws RemoteException {
		try {
			com.liferay.portal.model.Country returnValue = CountryServiceUtil.addCountry(name,
					a2, a3, number, idd, active);

			return com.liferay.portal.model.CountrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.CountrySoap[] getCountries()
		throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Country> returnValue = CountryServiceUtil.getCountries();

			return com.liferay.portal.model.CountrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.CountrySoap[] getCountries(
		boolean active) throws RemoteException {
		try {
			java.util.List<com.liferay.portal.model.Country> returnValue = CountryServiceUtil.getCountries(active);

			return com.liferay.portal.model.CountrySoap.toSoapModels(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.CountrySoap getCountry(
		long countryId) throws RemoteException {
		try {
			com.liferay.portal.model.Country returnValue = CountryServiceUtil.getCountry(countryId);

			return com.liferay.portal.model.CountrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.CountrySoap getCountryByA2(
		java.lang.String a2) throws RemoteException {
		try {
			com.liferay.portal.model.Country returnValue = CountryServiceUtil.getCountryByA2(a2);

			return com.liferay.portal.model.CountrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.CountrySoap getCountryByA3(
		java.lang.String a3) throws RemoteException {
		try {
			com.liferay.portal.model.Country returnValue = CountryServiceUtil.getCountryByA3(a3);

			return com.liferay.portal.model.CountrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	public static com.liferay.portal.model.CountrySoap getCountryByName(
		java.lang.String name) throws RemoteException {
		try {
			com.liferay.portal.model.Country returnValue = CountryServiceUtil.getCountryByName(name);

			return com.liferay.portal.model.CountrySoap.toSoapModel(returnValue);
		}
		catch (Exception e) {
			_log.error(e, e);

			throw new RemoteException(e.getMessage());
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CountryServiceSoap.class);
}