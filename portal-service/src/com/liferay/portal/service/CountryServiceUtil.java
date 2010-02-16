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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="CountryServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link CountryService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       CountryService
 * @generated
 */
public class CountryServiceUtil {
	public static com.liferay.portal.model.Country addCountry(
		java.lang.String name, java.lang.String a2, java.lang.String a3,
		java.lang.String number, java.lang.String idd, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addCountry(name, a2, a3, number, idd, active);
	}

	public static java.util.List<com.liferay.portal.model.Country> getCountries()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCountries();
	}

	public static java.util.List<com.liferay.portal.model.Country> getCountries(
		boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getCountries(active);
	}

	public static com.liferay.portal.model.Country getCountry(long countryId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCountry(countryId);
	}

	public static com.liferay.portal.model.Country getCountryByA2(
		java.lang.String a2)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCountryByA2(a2);
	}

	public static com.liferay.portal.model.Country getCountryByA3(
		java.lang.String a3)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCountryByA3(a3);
	}

	public static com.liferay.portal.model.Country getCountryByName(
		java.lang.String name)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getCountryByName(name);
	}

	public static CountryService getService() {
		if (_service == null) {
			_service = (CountryService)PortalBeanLocatorUtil.locate(CountryService.class.getName());
		}

		return _service;
	}

	public void setService(CountryService service) {
		_service = service;
	}

	private static CountryService _service;
}