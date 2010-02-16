/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;


/**
 * <a href="CountryServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link CountryService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       CountryService
 * @generated
 */
public class CountryServiceWrapper implements CountryService {
	public CountryServiceWrapper(CountryService countryService) {
		_countryService = countryService;
	}

	public com.liferay.portal.model.Country addCountry(java.lang.String name,
		java.lang.String a2, java.lang.String a3, java.lang.String number,
		java.lang.String idd, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _countryService.addCountry(name, a2, a3, number, idd, active);
	}

	public java.util.List<com.liferay.portal.model.Country> getCountries()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _countryService.getCountries();
	}

	public java.util.List<com.liferay.portal.model.Country> getCountries(
		boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _countryService.getCountries(active);
	}

	public com.liferay.portal.model.Country getCountry(long countryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _countryService.getCountry(countryId);
	}

	public com.liferay.portal.model.Country getCountryByA2(java.lang.String a2)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _countryService.getCountryByA2(a2);
	}

	public com.liferay.portal.model.Country getCountryByA3(java.lang.String a3)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _countryService.getCountryByA3(a3);
	}

	public com.liferay.portal.model.Country getCountryByName(
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _countryService.getCountryByName(name);
	}

	public CountryService getWrappedCountryService() {
		return _countryService;
	}

	private CountryService _countryService;
}