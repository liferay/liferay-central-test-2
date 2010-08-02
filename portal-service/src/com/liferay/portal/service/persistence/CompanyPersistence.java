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

package com.liferay.portal.service.persistence;

import com.liferay.portal.model.Company;

/**
 * The persistence interface for the company service.
 *
 * <p>
 * Never modify or reference this interface directly. Always use {@link CompanyUtil} to access the company persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
 * </p>
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see CompanyPersistenceImpl
 * @see CompanyUtil
 * @generated
 */
public interface CompanyPersistence extends BasePersistence<Company> {
	/**
	* Caches the company in the entity cache if it is enabled.
	*
	* @param company the company to cache
	*/
	public void cacheResult(com.liferay.portal.model.Company company);

	/**
	* Caches the companies in the entity cache if it is enabled.
	*
	* @param companies the companies to cache
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.Company> companies);

	/**
	* Creates a new company with the primary key. Does not add the company to the database.
	*
	* @param companyId the primary key for the new company
	* @return the new company
	*/
	public com.liferay.portal.model.Company create(long companyId);

	/**
	* Removes the company with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param companyId the primary key of the company to remove
	* @return the company that was removed
	* @throws com.liferay.portal.NoSuchCompanyException if a company with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Company remove(long companyId)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Company updateImpl(
		com.liferay.portal.model.Company company, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the company with the primary key or throws a {@link com.liferay.portal.NoSuchCompanyException} if it could not be found.
	*
	* @param companyId the primary key of the company to find
	* @return the company
	* @throws com.liferay.portal.NoSuchCompanyException if a company with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Company findByPrimaryKey(long companyId)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the company with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param companyId the primary key of the company to find
	* @return the company, or <code>null</code> if a company with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Company fetchByPrimaryKey(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the company where webId = &#63; or throws a {@link com.liferay.portal.NoSuchCompanyException} if it could not be found.
	*
	* @param webId the web id to search with
	* @return the matching company
	* @throws com.liferay.portal.NoSuchCompanyException if a matching company could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Company findByWebId(java.lang.String webId)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the company where webId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param webId the web id to search with
	* @return the matching company, or <code>null</code> if a matching company could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Company fetchByWebId(java.lang.String webId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the company where webId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param webId the web id to search with
	* @return the matching company, or <code>null</code> if a matching company could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Company fetchByWebId(
		java.lang.String webId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the company where virtualHost = &#63; or throws a {@link com.liferay.portal.NoSuchCompanyException} if it could not be found.
	*
	* @param virtualHost the virtual host to search with
	* @return the matching company
	* @throws com.liferay.portal.NoSuchCompanyException if a matching company could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Company findByVirtualHost(
		java.lang.String virtualHost)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the company where virtualHost = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param virtualHost the virtual host to search with
	* @return the matching company, or <code>null</code> if a matching company could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Company fetchByVirtualHost(
		java.lang.String virtualHost)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the company where virtualHost = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param virtualHost the virtual host to search with
	* @return the matching company, or <code>null</code> if a matching company could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Company fetchByVirtualHost(
		java.lang.String virtualHost, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the company where mx = &#63; or throws a {@link com.liferay.portal.NoSuchCompanyException} if it could not be found.
	*
	* @param mx the mx to search with
	* @return the matching company
	* @throws com.liferay.portal.NoSuchCompanyException if a matching company could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Company findByMx(java.lang.String mx)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the company where mx = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param mx the mx to search with
	* @return the matching company, or <code>null</code> if a matching company could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Company fetchByMx(java.lang.String mx)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the company where mx = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param mx the mx to search with
	* @return the matching company, or <code>null</code> if a matching company could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Company fetchByMx(java.lang.String mx,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the company where logoId = &#63; or throws a {@link com.liferay.portal.NoSuchCompanyException} if it could not be found.
	*
	* @param logoId the logo id to search with
	* @return the matching company
	* @throws com.liferay.portal.NoSuchCompanyException if a matching company could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Company findByLogoId(long logoId)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the company where logoId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param logoId the logo id to search with
	* @return the matching company, or <code>null</code> if a matching company could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Company fetchByLogoId(long logoId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the company where logoId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param logoId the logo id to search with
	* @return the matching company, or <code>null</code> if a matching company could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Company fetchByLogoId(long logoId,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the companies where system = &#63;.
	*
	* @param system the system to search with
	* @return the matching companies
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Company> findBySystem(
		boolean system)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the companies where system = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param system the system to search with
	* @param start the lower bound of the range of companies to return
	* @param end the upper bound of the range of companies to return (not inclusive)
	* @return the range of matching companies
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Company> findBySystem(
		boolean system, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the companies where system = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param system the system to search with
	* @param start the lower bound of the range of companies to return
	* @param end the upper bound of the range of companies to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching companies
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Company> findBySystem(
		boolean system, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first company in the ordered set where system = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param system the system to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching company
	* @throws com.liferay.portal.NoSuchCompanyException if a matching company could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Company findBySystem_First(boolean system,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the last company in the ordered set where system = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param system the system to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching company
	* @throws com.liferay.portal.NoSuchCompanyException if a matching company could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Company findBySystem_Last(boolean system,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the companies before and after the current company in the ordered set where system = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the primary key of the current company
	* @param system the system to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next company
	* @throws com.liferay.portal.NoSuchCompanyException if a company with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Company[] findBySystem_PrevAndNext(
		long companyId, boolean system,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the companies.
	*
	* @return the companies
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Company> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the companies.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of companies to return
	* @param end the upper bound of the range of companies to return (not inclusive)
	* @return the range of companies
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Company> findAll(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the companies.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of companies to return
	* @param end the upper bound of the range of companies to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of companies
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Company> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the company where webId = &#63; from the database.
	*
	* @param webId the web id to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByWebId(java.lang.String webId)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the company where virtualHost = &#63; from the database.
	*
	* @param virtualHost the virtual host to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByVirtualHost(java.lang.String virtualHost)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the company where mx = &#63; from the database.
	*
	* @param mx the mx to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByMx(java.lang.String mx)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the company where logoId = &#63; from the database.
	*
	* @param logoId the logo id to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByLogoId(long logoId)
		throws com.liferay.portal.NoSuchCompanyException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the companies where system = &#63; from the database.
	*
	* @param system the system to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeBySystem(boolean system)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the companies from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the companies where webId = &#63;.
	*
	* @param webId the web id to search with
	* @return the number of matching companies
	* @throws SystemException if a system exception occurred
	*/
	public int countByWebId(java.lang.String webId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the companies where virtualHost = &#63;.
	*
	* @param virtualHost the virtual host to search with
	* @return the number of matching companies
	* @throws SystemException if a system exception occurred
	*/
	public int countByVirtualHost(java.lang.String virtualHost)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the companies where mx = &#63;.
	*
	* @param mx the mx to search with
	* @return the number of matching companies
	* @throws SystemException if a system exception occurred
	*/
	public int countByMx(java.lang.String mx)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the companies where logoId = &#63;.
	*
	* @param logoId the logo id to search with
	* @return the number of matching companies
	* @throws SystemException if a system exception occurred
	*/
	public int countByLogoId(long logoId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the companies where system = &#63;.
	*
	* @param system the system to search with
	* @return the number of matching companies
	* @throws SystemException if a system exception occurred
	*/
	public int countBySystem(boolean system)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the companies.
	*
	* @return the number of companies
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}