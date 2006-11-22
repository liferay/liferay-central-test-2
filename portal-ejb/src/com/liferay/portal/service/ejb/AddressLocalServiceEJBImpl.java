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

package com.liferay.portal.service.ejb;

import com.liferay.portal.service.spring.AddressLocalService;
import com.liferay.portal.service.spring.AddressLocalServiceFactory;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="AddressLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class AddressLocalServiceEJBImpl implements AddressLocalService,
	SessionBean {
	public com.liferay.portal.model.Address addAddress(
		java.lang.String userId, java.lang.String className,
		java.lang.String classPK, java.lang.String street1,
		java.lang.String street2, java.lang.String street3,
		java.lang.String city, java.lang.String zip, java.lang.String regionId,
		java.lang.String countryId, java.lang.String typeId, boolean mailing,
		boolean primary)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return AddressLocalServiceFactory.getTxImpl().addAddress(userId,
			className, classPK, street1, street2, street3, city, zip, regionId,
			countryId, typeId, mailing, primary);
	}

	public void deleteAddress(java.lang.String addressId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		AddressLocalServiceFactory.getTxImpl().deleteAddress(addressId);
	}

	public void deleteAddresses(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException {
		AddressLocalServiceFactory.getTxImpl().deleteAddresses(companyId,
			className, classPK);
	}

	public com.liferay.portal.model.Address getAddress(
		java.lang.String addressId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return AddressLocalServiceFactory.getTxImpl().getAddress(addressId);
	}

	public java.util.List getAddresses(java.lang.String companyId,
		java.lang.String className, java.lang.String classPK)
		throws com.liferay.portal.SystemException {
		return AddressLocalServiceFactory.getTxImpl().getAddresses(companyId,
			className, classPK);
	}

	public com.liferay.portal.model.Address updateAddress(
		java.lang.String addressId, java.lang.String street1,
		java.lang.String street2, java.lang.String street3,
		java.lang.String city, java.lang.String zip, java.lang.String regionId,
		java.lang.String countryId, java.lang.String typeId, boolean mailing,
		boolean primary)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return AddressLocalServiceFactory.getTxImpl().updateAddress(addressId,
			street1, street2, street3, city, zip, regionId, countryId, typeId,
			mailing, primary);
	}

	public void ejbCreate() throws CreateException {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public SessionContext getSessionContext() {
		return _sc;
	}

	public void setSessionContext(SessionContext sc) {
		_sc = sc;
	}

	private SessionContext _sc;
}