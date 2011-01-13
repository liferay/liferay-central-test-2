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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Address;

/**
 * The persistence interface for the address service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AddressPersistenceImpl
 * @see AddressUtil
 * @generated
 */
public interface AddressPersistence extends BasePersistence<Address> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link AddressUtil} to access the address persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the address in the entity cache if it is enabled.
	*
	* @param address the address to cache
	*/
	public void cacheResult(com.liferay.portal.model.Address address);

	/**
	* Caches the addresses in the entity cache if it is enabled.
	*
	* @param addresses the addresses to cache
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.Address> addresses);

	/**
	* Creates a new address with the primary key. Does not add the address to the database.
	*
	* @param addressId the primary key for the new address
	* @return the new address
	*/
	public com.liferay.portal.model.Address create(long addressId);

	/**
	* Removes the address with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param addressId the primary key of the address to remove
	* @return the address that was removed
	* @throws com.liferay.portal.NoSuchAddressException if a address with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Address remove(long addressId)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Address updateImpl(
		com.liferay.portal.model.Address address, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the address with the primary key or throws a {@link com.liferay.portal.NoSuchAddressException} if it could not be found.
	*
	* @param addressId the primary key of the address to find
	* @return the address
	* @throws com.liferay.portal.NoSuchAddressException if a address with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Address findByPrimaryKey(long addressId)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the address with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param addressId the primary key of the address to find
	* @return the address, or <code>null</code> if a address with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Address fetchByPrimaryKey(long addressId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the addresses where companyId = &#63;.
	*
	* @param companyId the company ID to search with
	* @return the matching addresses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Address> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the addresses where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param start the lower bound of the range of addresses to return
	* @param end the upper bound of the range of addresses to return (not inclusive)
	* @return the range of matching addresses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Address> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the addresses where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param start the lower bound of the range of addresses to return
	* @param end the upper bound of the range of addresses to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching addresses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Address> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first address in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching address
	* @throws com.liferay.portal.NoSuchAddressException if a matching address could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Address findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the last address in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching address
	* @throws com.liferay.portal.NoSuchAddressException if a matching address could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Address findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the addresses before and after the current address in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param addressId the primary key of the current address
	* @param companyId the company ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next address
	* @throws com.liferay.portal.NoSuchAddressException if a address with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Address[] findByCompanyId_PrevAndNext(
		long addressId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the addresses where userId = &#63;.
	*
	* @param userId the user ID to search with
	* @return the matching addresses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Address> findByUserId(
		long userId) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the addresses where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param start the lower bound of the range of addresses to return
	* @param end the upper bound of the range of addresses to return (not inclusive)
	* @return the range of matching addresses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Address> findByUserId(
		long userId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the addresses where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param start the lower bound of the range of addresses to return
	* @param end the upper bound of the range of addresses to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching addresses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Address> findByUserId(
		long userId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first address in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching address
	* @throws com.liferay.portal.NoSuchAddressException if a matching address could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Address findByUserId_First(long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the last address in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param userId the user ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching address
	* @throws com.liferay.portal.NoSuchAddressException if a matching address could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Address findByUserId_Last(long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the addresses before and after the current address in the ordered set where userId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param addressId the primary key of the current address
	* @param userId the user ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next address
	* @throws com.liferay.portal.NoSuchAddressException if a address with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Address[] findByUserId_PrevAndNext(
		long addressId, long userId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the addresses where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @return the matching addresses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Address> findByC_C(
		long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the addresses where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param start the lower bound of the range of addresses to return
	* @param end the upper bound of the range of addresses to return (not inclusive)
	* @return the range of matching addresses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Address> findByC_C(
		long companyId, long classNameId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the addresses where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param start the lower bound of the range of addresses to return
	* @param end the upper bound of the range of addresses to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching addresses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Address> findByC_C(
		long companyId, long classNameId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first address in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching address
	* @throws com.liferay.portal.NoSuchAddressException if a matching address could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Address findByC_C_First(long companyId,
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the last address in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching address
	* @throws com.liferay.portal.NoSuchAddressException if a matching address could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Address findByC_C_Last(long companyId,
		long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the addresses before and after the current address in the ordered set where companyId = &#63; and classNameId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param addressId the primary key of the current address
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next address
	* @throws com.liferay.portal.NoSuchAddressException if a address with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Address[] findByC_C_PrevAndNext(
		long addressId, long companyId, long classNameId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @return the matching addresses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Address> findByC_C_C(
		long companyId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @param start the lower bound of the range of addresses to return
	* @param end the upper bound of the range of addresses to return (not inclusive)
	* @return the range of matching addresses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Address> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @param start the lower bound of the range of addresses to return
	* @param end the upper bound of the range of addresses to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching addresses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Address> findByC_C_C(
		long companyId, long classNameId, long classPK, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first address in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching address
	* @throws com.liferay.portal.NoSuchAddressException if a matching address could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Address findByC_C_C_First(long companyId,
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the last address in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching address
	* @throws com.liferay.portal.NoSuchAddressException if a matching address could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Address findByC_C_C_Last(long companyId,
		long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the addresses before and after the current address in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param addressId the primary key of the current address
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next address
	* @throws com.liferay.portal.NoSuchAddressException if a address with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Address[] findByC_C_C_PrevAndNext(
		long addressId, long companyId, long classNameId, long classPK,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63; and mailing = &#63;.
	*
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @param mailing the mailing to search with
	* @return the matching addresses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Address> findByC_C_C_M(
		long companyId, long classNameId, long classPK, boolean mailing)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63; and mailing = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @param mailing the mailing to search with
	* @param start the lower bound of the range of addresses to return
	* @param end the upper bound of the range of addresses to return (not inclusive)
	* @return the range of matching addresses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Address> findByC_C_C_M(
		long companyId, long classNameId, long classPK, boolean mailing,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63; and mailing = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @param mailing the mailing to search with
	* @param start the lower bound of the range of addresses to return
	* @param end the upper bound of the range of addresses to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching addresses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Address> findByC_C_C_M(
		long companyId, long classNameId, long classPK, boolean mailing,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first address in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63; and mailing = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @param mailing the mailing to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching address
	* @throws com.liferay.portal.NoSuchAddressException if a matching address could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Address findByC_C_C_M_First(
		long companyId, long classNameId, long classPK, boolean mailing,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the last address in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63; and mailing = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @param mailing the mailing to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching address
	* @throws com.liferay.portal.NoSuchAddressException if a matching address could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Address findByC_C_C_M_Last(long companyId,
		long classNameId, long classPK, boolean mailing,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the addresses before and after the current address in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63; and mailing = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param addressId the primary key of the current address
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @param mailing the mailing to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next address
	* @throws com.liferay.portal.NoSuchAddressException if a address with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Address[] findByC_C_C_M_PrevAndNext(
		long addressId, long companyId, long classNameId, long classPK,
		boolean mailing,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63; and primary = &#63;.
	*
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @param primary the primary to search with
	* @return the matching addresses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Address> findByC_C_C_P(
		long companyId, long classNameId, long classPK, boolean primary)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63; and primary = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @param primary the primary to search with
	* @param start the lower bound of the range of addresses to return
	* @param end the upper bound of the range of addresses to return (not inclusive)
	* @return the range of matching addresses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Address> findByC_C_C_P(
		long companyId, long classNameId, long classPK, boolean primary,
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63; and primary = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @param primary the primary to search with
	* @param start the lower bound of the range of addresses to return
	* @param end the upper bound of the range of addresses to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching addresses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Address> findByC_C_C_P(
		long companyId, long classNameId, long classPK, boolean primary,
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first address in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63; and primary = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @param primary the primary to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching address
	* @throws com.liferay.portal.NoSuchAddressException if a matching address could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Address findByC_C_C_P_First(
		long companyId, long classNameId, long classPK, boolean primary,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the last address in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63; and primary = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @param primary the primary to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching address
	* @throws com.liferay.portal.NoSuchAddressException if a matching address could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Address findByC_C_C_P_Last(long companyId,
		long classNameId, long classPK, boolean primary,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the addresses before and after the current address in the ordered set where companyId = &#63; and classNameId = &#63; and classPK = &#63; and primary = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param addressId the primary key of the current address
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @param primary the primary to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next address
	* @throws com.liferay.portal.NoSuchAddressException if a address with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Address[] findByC_C_C_P_PrevAndNext(
		long addressId, long companyId, long classNameId, long classPK,
		boolean primary,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchAddressException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the addresses.
	*
	* @return the addresses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Address> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the addresses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of addresses to return
	* @param end the upper bound of the range of addresses to return (not inclusive)
	* @return the range of addresses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Address> findAll(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the addresses.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of addresses to return
	* @param end the upper bound of the range of addresses to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of addresses
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Address> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the addresses where companyId = &#63; from the database.
	*
	* @param companyId the company ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the addresses where userId = &#63; from the database.
	*
	* @param userId the user ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the addresses where companyId = &#63; and classNameId = &#63; from the database.
	*
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_C(long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	*
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_C_C(long companyId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63; and mailing = &#63; from the database.
	*
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @param mailing the mailing to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_C_C_M(long companyId, long classNameId, long classPK,
		boolean mailing)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63; and primary = &#63; from the database.
	*
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @param primary the primary to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_C_C_P(long companyId, long classNameId, long classPK,
		boolean primary)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the addresses from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the addresses where companyId = &#63;.
	*
	* @param companyId the company ID to search with
	* @return the number of matching addresses
	* @throws SystemException if a system exception occurred
	*/
	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the addresses where userId = &#63;.
	*
	* @param userId the user ID to search with
	* @return the number of matching addresses
	* @throws SystemException if a system exception occurred
	*/
	public int countByUserId(long userId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the addresses where companyId = &#63; and classNameId = &#63;.
	*
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @return the number of matching addresses
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_C(long companyId, long classNameId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63;.
	*
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @return the number of matching addresses
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_C_C(long companyId, long classNameId, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63; and mailing = &#63;.
	*
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @param mailing the mailing to search with
	* @return the number of matching addresses
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_C_C_M(long companyId, long classNameId, long classPK,
		boolean mailing)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the addresses where companyId = &#63; and classNameId = &#63; and classPK = &#63; and primary = &#63;.
	*
	* @param companyId the company ID to search with
	* @param classNameId the class name ID to search with
	* @param classPK the class p k to search with
	* @param primary the primary to search with
	* @return the number of matching addresses
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_C_C_P(long companyId, long classNameId, long classPK,
		boolean primary)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the addresses.
	*
	* @return the number of addresses
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	public Address remove(Address address) throws SystemException;
}