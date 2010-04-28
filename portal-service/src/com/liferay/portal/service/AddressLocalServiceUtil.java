/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="AddressLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link AddressLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       AddressLocalService
 * @generated
 */
public class AddressLocalServiceUtil {
	public static com.liferay.portal.model.Address addAddress(
		com.liferay.portal.model.Address address)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addAddress(address);
	}

	public static com.liferay.portal.model.Address createAddress(long addressId) {
		return getService().createAddress(addressId);
	}

	public static void deleteAddress(long addressId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAddress(addressId);
	}

	public static void deleteAddress(com.liferay.portal.model.Address address)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAddress(address);
	}

	public static java.util.List<com.liferay.portal.model.Address> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<com.liferay.portal.model.Address> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static java.util.List<com.liferay.portal.model.Address> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portal.model.Address getAddress(long addressId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getAddress(addressId);
	}

	public static java.util.List<com.liferay.portal.model.Address> getAddresses(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAddresses(start, end);
	}

	public static int getAddressesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAddressesCount();
	}

	public static com.liferay.portal.model.Address updateAddress(
		com.liferay.portal.model.Address address)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateAddress(address);
	}

	public static com.liferay.portal.model.Address updateAddress(
		com.liferay.portal.model.Address address, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateAddress(address, merge);
	}

	public static com.liferay.portal.model.Address addAddress(long userId,
		java.lang.String className, long classPK, java.lang.String street1,
		java.lang.String street2, java.lang.String street3,
		java.lang.String city, java.lang.String zip, long regionId,
		long countryId, int typeId, boolean mailing, boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addAddress(userId, className, classPK, street1, street2,
			street3, city, zip, regionId, countryId, typeId, mailing, primary);
	}

	public static void deleteAddresses(long companyId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteAddresses(companyId, className, classPK);
	}

	public static java.util.List<com.liferay.portal.model.Address> getAddresses()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAddresses();
	}

	public static java.util.List<com.liferay.portal.model.Address> getAddresses(
		long companyId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getAddresses(companyId, className, classPK);
	}

	public static com.liferay.portal.model.Address updateAddress(
		long addressId, java.lang.String street1, java.lang.String street2,
		java.lang.String street3, java.lang.String city, java.lang.String zip,
		long regionId, long countryId, int typeId, boolean mailing,
		boolean primary)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateAddress(addressId, street1, street2, street3, city,
			zip, regionId, countryId, typeId, mailing, primary);
	}

	public static AddressLocalService getService() {
		if (_service == null) {
			_service = (AddressLocalService)PortalBeanLocatorUtil.locate(AddressLocalService.class.getName());
		}

		return _service;
	}

	public void setService(AddressLocalService service) {
		_service = service;
	}

	private static AddressLocalService _service;
}