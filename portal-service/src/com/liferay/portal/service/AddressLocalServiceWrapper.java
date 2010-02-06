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
 * <a href="AddressLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link AddressLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AddressLocalService
 * @generated
 */
public class AddressLocalServiceWrapper implements AddressLocalService {
	public AddressLocalServiceWrapper(AddressLocalService addressLocalService) {
		_addressLocalService = addressLocalService;
	}

	public com.liferay.portal.model.Address addAddress(
		com.liferay.portal.model.Address address)
		throws com.liferay.portal.SystemException {
		return _addressLocalService.addAddress(address);
	}

	public com.liferay.portal.model.Address createAddress(long addressId) {
		return _addressLocalService.createAddress(addressId);
	}

	public void deleteAddress(long addressId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_addressLocalService.deleteAddress(addressId);
	}

	public void deleteAddress(com.liferay.portal.model.Address address)
		throws com.liferay.portal.SystemException {
		_addressLocalService.deleteAddress(address);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _addressLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _addressLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portal.model.Address getAddress(long addressId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _addressLocalService.getAddress(addressId);
	}

	public java.util.List<com.liferay.portal.model.Address> getAddresses(
		int start, int end) throws com.liferay.portal.SystemException {
		return _addressLocalService.getAddresses(start, end);
	}

	public int getAddressesCount() throws com.liferay.portal.SystemException {
		return _addressLocalService.getAddressesCount();
	}

	public com.liferay.portal.model.Address updateAddress(
		com.liferay.portal.model.Address address)
		throws com.liferay.portal.SystemException {
		return _addressLocalService.updateAddress(address);
	}

	public com.liferay.portal.model.Address updateAddress(
		com.liferay.portal.model.Address address, boolean merge)
		throws com.liferay.portal.SystemException {
		return _addressLocalService.updateAddress(address, merge);
	}

	public com.liferay.portal.model.Address addAddress(long userId,
		java.lang.String className, long classPK, java.lang.String street1,
		java.lang.String street2, java.lang.String street3,
		java.lang.String city, java.lang.String zip, long regionId,
		long countryId, int typeId, boolean mailing, boolean primary)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _addressLocalService.addAddress(userId, className, classPK,
			street1, street2, street3, city, zip, regionId, countryId, typeId,
			mailing, primary);
	}

	public void deleteAddresses(long companyId, java.lang.String className,
		long classPK) throws com.liferay.portal.SystemException {
		_addressLocalService.deleteAddresses(companyId, className, classPK);
	}

	public java.util.List<com.liferay.portal.model.Address> getAddresses()
		throws com.liferay.portal.SystemException {
		return _addressLocalService.getAddresses();
	}

	public java.util.List<com.liferay.portal.model.Address> getAddresses(
		long companyId, java.lang.String className, long classPK)
		throws com.liferay.portal.SystemException {
		return _addressLocalService.getAddresses(companyId, className, classPK);
	}

	public com.liferay.portal.model.Address updateAddress(long addressId,
		java.lang.String street1, java.lang.String street2,
		java.lang.String street3, java.lang.String city, java.lang.String zip,
		long regionId, long countryId, int typeId, boolean mailing,
		boolean primary)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _addressLocalService.updateAddress(addressId, street1, street2,
			street3, city, zip, regionId, countryId, typeId, mailing, primary);
	}

	public AddressLocalService getWrappedAddressLocalService() {
		return _addressLocalService;
	}

	private AddressLocalService _addressLocalService;
}