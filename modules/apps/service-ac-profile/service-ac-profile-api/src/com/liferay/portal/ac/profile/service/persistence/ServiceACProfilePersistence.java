/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.ac.profile.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.ac.profile.model.ServiceACProfile;
import com.liferay.portal.service.persistence.BasePersistence;

/**
 * The persistence interface for the service a c profile service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.portal.ac.profile.service.persistence.impl.ServiceACProfilePersistenceImpl
 * @see ServiceACProfileUtil
 * @generated
 */
@ProviderType
public interface ServiceACProfilePersistence extends BasePersistence<ServiceACProfile> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link ServiceACProfileUtil} to access the service a c profile persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Returns all the service a c profiles where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the matching service a c profiles
	*/
	public java.util.List<com.liferay.portal.ac.profile.model.ServiceACProfile> findByUuid(
		java.lang.String uuid);

	/**
	* Returns a range of all the service a c profiles where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.ac.profile.model.impl.ServiceACProfileModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of service a c profiles
	* @param end the upper bound of the range of service a c profiles (not inclusive)
	* @return the range of matching service a c profiles
	*/
	public java.util.List<com.liferay.portal.ac.profile.model.ServiceACProfile> findByUuid(
		java.lang.String uuid, int start, int end);

	/**
	* Returns an ordered range of all the service a c profiles where uuid = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.ac.profile.model.impl.ServiceACProfileModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param start the lower bound of the range of service a c profiles
	* @param end the upper bound of the range of service a c profiles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching service a c profiles
	*/
	public java.util.List<com.liferay.portal.ac.profile.model.ServiceACProfile> findByUuid(
		java.lang.String uuid, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator);

	/**
	* Returns the first service a c profile in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching service a c profile
	* @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a matching service a c profile could not be found
	*/
	public com.liferay.portal.ac.profile.model.ServiceACProfile findByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator)
		throws com.liferay.portal.ac.profile.exception.NoSuchServiceACProfileException;

	/**
	* Returns the first service a c profile in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching service a c profile, or <code>null</code> if a matching service a c profile could not be found
	*/
	public com.liferay.portal.ac.profile.model.ServiceACProfile fetchByUuid_First(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator);

	/**
	* Returns the last service a c profile in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching service a c profile
	* @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a matching service a c profile could not be found
	*/
	public com.liferay.portal.ac.profile.model.ServiceACProfile findByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator)
		throws com.liferay.portal.ac.profile.exception.NoSuchServiceACProfileException;

	/**
	* Returns the last service a c profile in the ordered set where uuid = &#63;.
	*
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching service a c profile, or <code>null</code> if a matching service a c profile could not be found
	*/
	public com.liferay.portal.ac.profile.model.ServiceACProfile fetchByUuid_Last(
		java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator);

	/**
	* Returns the service a c profiles before and after the current service a c profile in the ordered set where uuid = &#63;.
	*
	* @param serviceACProfileId the primary key of the current service a c profile
	* @param uuid the uuid
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next service a c profile
	* @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a service a c profile with the primary key could not be found
	*/
	public com.liferay.portal.ac.profile.model.ServiceACProfile[] findByUuid_PrevAndNext(
		long serviceACProfileId, java.lang.String uuid,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator)
		throws com.liferay.portal.ac.profile.exception.NoSuchServiceACProfileException;

	/**
	* Removes all the service a c profiles where uuid = &#63; from the database.
	*
	* @param uuid the uuid
	*/
	public void removeByUuid(java.lang.String uuid);

	/**
	* Returns the number of service a c profiles where uuid = &#63;.
	*
	* @param uuid the uuid
	* @return the number of matching service a c profiles
	*/
	public int countByUuid(java.lang.String uuid);

	/**
	* Returns all the service a c profiles where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the matching service a c profiles
	*/
	public java.util.List<com.liferay.portal.ac.profile.model.ServiceACProfile> findByUuid_C(
		java.lang.String uuid, long companyId);

	/**
	* Returns a range of all the service a c profiles where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.ac.profile.model.impl.ServiceACProfileModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of service a c profiles
	* @param end the upper bound of the range of service a c profiles (not inclusive)
	* @return the range of matching service a c profiles
	*/
	public java.util.List<com.liferay.portal.ac.profile.model.ServiceACProfile> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end);

	/**
	* Returns an ordered range of all the service a c profiles where uuid = &#63; and companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.ac.profile.model.impl.ServiceACProfileModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param start the lower bound of the range of service a c profiles
	* @param end the upper bound of the range of service a c profiles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching service a c profiles
	*/
	public java.util.List<com.liferay.portal.ac.profile.model.ServiceACProfile> findByUuid_C(
		java.lang.String uuid, long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator);

	/**
	* Returns the first service a c profile in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching service a c profile
	* @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a matching service a c profile could not be found
	*/
	public com.liferay.portal.ac.profile.model.ServiceACProfile findByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator)
		throws com.liferay.portal.ac.profile.exception.NoSuchServiceACProfileException;

	/**
	* Returns the first service a c profile in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching service a c profile, or <code>null</code> if a matching service a c profile could not be found
	*/
	public com.liferay.portal.ac.profile.model.ServiceACProfile fetchByUuid_C_First(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator);

	/**
	* Returns the last service a c profile in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching service a c profile
	* @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a matching service a c profile could not be found
	*/
	public com.liferay.portal.ac.profile.model.ServiceACProfile findByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator)
		throws com.liferay.portal.ac.profile.exception.NoSuchServiceACProfileException;

	/**
	* Returns the last service a c profile in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching service a c profile, or <code>null</code> if a matching service a c profile could not be found
	*/
	public com.liferay.portal.ac.profile.model.ServiceACProfile fetchByUuid_C_Last(
		java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator);

	/**
	* Returns the service a c profiles before and after the current service a c profile in the ordered set where uuid = &#63; and companyId = &#63;.
	*
	* @param serviceACProfileId the primary key of the current service a c profile
	* @param uuid the uuid
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next service a c profile
	* @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a service a c profile with the primary key could not be found
	*/
	public com.liferay.portal.ac.profile.model.ServiceACProfile[] findByUuid_C_PrevAndNext(
		long serviceACProfileId, java.lang.String uuid, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator)
		throws com.liferay.portal.ac.profile.exception.NoSuchServiceACProfileException;

	/**
	* Removes all the service a c profiles where uuid = &#63; and companyId = &#63; from the database.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	*/
	public void removeByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns the number of service a c profiles where uuid = &#63; and companyId = &#63;.
	*
	* @param uuid the uuid
	* @param companyId the company ID
	* @return the number of matching service a c profiles
	*/
	public int countByUuid_C(java.lang.String uuid, long companyId);

	/**
	* Returns all the service a c profiles where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching service a c profiles
	*/
	public java.util.List<com.liferay.portal.ac.profile.model.ServiceACProfile> findByCompanyId(
		long companyId);

	/**
	* Returns a range of all the service a c profiles where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.ac.profile.model.impl.ServiceACProfileModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of service a c profiles
	* @param end the upper bound of the range of service a c profiles (not inclusive)
	* @return the range of matching service a c profiles
	*/
	public java.util.List<com.liferay.portal.ac.profile.model.ServiceACProfile> findByCompanyId(
		long companyId, int start, int end);

	/**
	* Returns an ordered range of all the service a c profiles where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.ac.profile.model.impl.ServiceACProfileModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of service a c profiles
	* @param end the upper bound of the range of service a c profiles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching service a c profiles
	*/
	public java.util.List<com.liferay.portal.ac.profile.model.ServiceACProfile> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator);

	/**
	* Returns the first service a c profile in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching service a c profile
	* @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a matching service a c profile could not be found
	*/
	public com.liferay.portal.ac.profile.model.ServiceACProfile findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator)
		throws com.liferay.portal.ac.profile.exception.NoSuchServiceACProfileException;

	/**
	* Returns the first service a c profile in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching service a c profile, or <code>null</code> if a matching service a c profile could not be found
	*/
	public com.liferay.portal.ac.profile.model.ServiceACProfile fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator);

	/**
	* Returns the last service a c profile in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching service a c profile
	* @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a matching service a c profile could not be found
	*/
	public com.liferay.portal.ac.profile.model.ServiceACProfile findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator)
		throws com.liferay.portal.ac.profile.exception.NoSuchServiceACProfileException;

	/**
	* Returns the last service a c profile in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching service a c profile, or <code>null</code> if a matching service a c profile could not be found
	*/
	public com.liferay.portal.ac.profile.model.ServiceACProfile fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator);

	/**
	* Returns the service a c profiles before and after the current service a c profile in the ordered set where companyId = &#63;.
	*
	* @param serviceACProfileId the primary key of the current service a c profile
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next service a c profile
	* @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a service a c profile with the primary key could not be found
	*/
	public com.liferay.portal.ac.profile.model.ServiceACProfile[] findByCompanyId_PrevAndNext(
		long serviceACProfileId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator)
		throws com.liferay.portal.ac.profile.exception.NoSuchServiceACProfileException;

	/**
	* Removes all the service a c profiles where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public void removeByCompanyId(long companyId);

	/**
	* Returns the number of service a c profiles where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching service a c profiles
	*/
	public int countByCompanyId(long companyId);

	/**
	* Returns the service a c profile where companyId = &#63; and name = &#63; or throws a {@link com.liferay.portal.ac.profile.NoSuchServiceACProfileException} if it could not be found.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the matching service a c profile
	* @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a matching service a c profile could not be found
	*/
	public com.liferay.portal.ac.profile.model.ServiceACProfile findByC_N(
		long companyId, java.lang.String name)
		throws com.liferay.portal.ac.profile.exception.NoSuchServiceACProfileException;

	/**
	* Returns the service a c profile where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the matching service a c profile, or <code>null</code> if a matching service a c profile could not be found
	*/
	public com.liferay.portal.ac.profile.model.ServiceACProfile fetchByC_N(
		long companyId, java.lang.String name);

	/**
	* Returns the service a c profile where companyId = &#63; and name = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID
	* @param name the name
	* @param retrieveFromCache whether to use the finder cache
	* @return the matching service a c profile, or <code>null</code> if a matching service a c profile could not be found
	*/
	public com.liferay.portal.ac.profile.model.ServiceACProfile fetchByC_N(
		long companyId, java.lang.String name, boolean retrieveFromCache);

	/**
	* Removes the service a c profile where companyId = &#63; and name = &#63; from the database.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the service a c profile that was removed
	*/
	public com.liferay.portal.ac.profile.model.ServiceACProfile removeByC_N(
		long companyId, java.lang.String name)
		throws com.liferay.portal.ac.profile.exception.NoSuchServiceACProfileException;

	/**
	* Returns the number of service a c profiles where companyId = &#63; and name = &#63;.
	*
	* @param companyId the company ID
	* @param name the name
	* @return the number of matching service a c profiles
	*/
	public int countByC_N(long companyId, java.lang.String name);

	/**
	* Caches the service a c profile in the entity cache if it is enabled.
	*
	* @param serviceACProfile the service a c profile
	*/
	public void cacheResult(
		com.liferay.portal.ac.profile.model.ServiceACProfile serviceACProfile);

	/**
	* Caches the service a c profiles in the entity cache if it is enabled.
	*
	* @param serviceACProfiles the service a c profiles
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.ac.profile.model.ServiceACProfile> serviceACProfiles);

	/**
	* Creates a new service a c profile with the primary key. Does not add the service a c profile to the database.
	*
	* @param serviceACProfileId the primary key for the new service a c profile
	* @return the new service a c profile
	*/
	public com.liferay.portal.ac.profile.model.ServiceACProfile create(
		long serviceACProfileId);

	/**
	* Removes the service a c profile with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param serviceACProfileId the primary key of the service a c profile
	* @return the service a c profile that was removed
	* @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a service a c profile with the primary key could not be found
	*/
	public com.liferay.portal.ac.profile.model.ServiceACProfile remove(
		long serviceACProfileId)
		throws com.liferay.portal.ac.profile.exception.NoSuchServiceACProfileException;

	public com.liferay.portal.ac.profile.model.ServiceACProfile updateImpl(
		com.liferay.portal.ac.profile.model.ServiceACProfile serviceACProfile);

	/**
	* Returns the service a c profile with the primary key or throws a {@link com.liferay.portal.ac.profile.NoSuchServiceACProfileException} if it could not be found.
	*
	* @param serviceACProfileId the primary key of the service a c profile
	* @return the service a c profile
	* @throws com.liferay.portal.ac.profile.NoSuchServiceACProfileException if a service a c profile with the primary key could not be found
	*/
	public com.liferay.portal.ac.profile.model.ServiceACProfile findByPrimaryKey(
		long serviceACProfileId)
		throws com.liferay.portal.ac.profile.exception.NoSuchServiceACProfileException;

	/**
	* Returns the service a c profile with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param serviceACProfileId the primary key of the service a c profile
	* @return the service a c profile, or <code>null</code> if a service a c profile with the primary key could not be found
	*/
	public com.liferay.portal.ac.profile.model.ServiceACProfile fetchByPrimaryKey(
		long serviceACProfileId);

	@Override
	public java.util.Map<java.io.Serializable, com.liferay.portal.ac.profile.model.ServiceACProfile> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys);

	/**
	* Returns all the service a c profiles.
	*
	* @return the service a c profiles
	*/
	public java.util.List<com.liferay.portal.ac.profile.model.ServiceACProfile> findAll();

	/**
	* Returns a range of all the service a c profiles.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.ac.profile.model.impl.ServiceACProfileModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of service a c profiles
	* @param end the upper bound of the range of service a c profiles (not inclusive)
	* @return the range of service a c profiles
	*/
	public java.util.List<com.liferay.portal.ac.profile.model.ServiceACProfile> findAll(
		int start, int end);

	/**
	* Returns an ordered range of all the service a c profiles.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.ac.profile.model.impl.ServiceACProfileModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of service a c profiles
	* @param end the upper bound of the range of service a c profiles (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of service a c profiles
	*/
	public java.util.List<com.liferay.portal.ac.profile.model.ServiceACProfile> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portal.ac.profile.model.ServiceACProfile> orderByComparator);

	/**
	* Removes all the service a c profiles from the database.
	*/
	public void removeAll();

	/**
	* Returns the number of service a c profiles.
	*
	* @return the number of service a c profiles
	*/
	public int countAll();
}