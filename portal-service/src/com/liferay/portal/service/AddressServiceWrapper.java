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
 * <a href="AddressServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link AddressService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AddressService
 * @generated
 */
public class AddressServiceWrapper implements AddressService {
	public AddressServiceWrapper(AddressService addressService) {
		_addressService = addressService;
	}

	public com.liferay.portal.model.Address addAddress(
		java.lang.String className, long classPK, java.lang.String street1,
		java.lang.String street2, java.lang.String street3,
		java.lang.String city, java.lang.String zip, long regionId,
		long countryId, int typeId, boolean mailing, boolean primary)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _addressService.addAddress(className, classPK, street1, street2,
			street3, city, zip, regionId, countryId, typeId, mailing, primary);
	}

	public void deleteAddress(long addressId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_addressService.deleteAddress(addressId);
	}

	public com.liferay.portal.model.Address getAddress(long addressId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _addressService.getAddress(addressId);
	}

	public java.util.List<com.liferay.portal.model.Address> getAddresses(
		java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _addressService.getAddresses(className, classPK);
	}

	public com.liferay.portal.model.Address updateAddress(long addressId,
		java.lang.String street1, java.lang.String street2,
		java.lang.String street3, java.lang.String city, java.lang.String zip,
		long regionId, long countryId, int typeId, boolean mailing,
		boolean primary)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _addressService.updateAddress(addressId, street1, street2,
			street3, city, zip, regionId, countryId, typeId, mailing, primary);
	}

	public AddressService getWrappedAddressService() {
		return _addressService;
	}

	private AddressService _addressService;
}