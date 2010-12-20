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

import com.liferay.portal.model.Portlet;

/**
 * The persistence interface for the portlet service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see PortletPersistenceImpl
 * @see PortletUtil
 * @generated
 */
public interface PortletPersistence extends BasePersistence<Portlet> {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link PortletUtil} to access the portlet persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	* Caches the portlet in the entity cache if it is enabled.
	*
	* @param portlet the portlet to cache
	*/
	public void cacheResult(com.liferay.portal.model.Portlet portlet);

	/**
	* Caches the portlets in the entity cache if it is enabled.
	*
	* @param portlets the portlets to cache
	*/
	public void cacheResult(
		java.util.List<com.liferay.portal.model.Portlet> portlets);

	/**
	* Creates a new portlet with the primary key. Does not add the portlet to the database.
	*
	* @param id the primary key for the new portlet
	* @return the new portlet
	*/
	public com.liferay.portal.model.Portlet create(long id);

	/**
	* Removes the portlet with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param id the primary key of the portlet to remove
	* @return the portlet that was removed
	* @throws com.liferay.portal.NoSuchPortletException if a portlet with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Portlet remove(long id)
		throws com.liferay.portal.NoSuchPortletException,
			com.liferay.portal.kernel.exception.SystemException;

	public com.liferay.portal.model.Portlet updateImpl(
		com.liferay.portal.model.Portlet portlet, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the portlet with the primary key or throws a {@link com.liferay.portal.NoSuchPortletException} if it could not be found.
	*
	* @param id the primary key of the portlet to find
	* @return the portlet
	* @throws com.liferay.portal.NoSuchPortletException if a portlet with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Portlet findByPrimaryKey(long id)
		throws com.liferay.portal.NoSuchPortletException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the portlet with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param id the primary key of the portlet to find
	* @return the portlet, or <code>null</code> if a portlet with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Portlet fetchByPrimaryKey(long id)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the portlets where companyId = &#63;.
	*
	* @param companyId the company ID to search with
	* @return the matching portlets
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Portlet> findByCompanyId(
		long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the portlets where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param start the lower bound of the range of portlets to return
	* @param end the upper bound of the range of portlets to return (not inclusive)
	* @return the range of matching portlets
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Portlet> findByCompanyId(
		long companyId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the portlets where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param start the lower bound of the range of portlets to return
	* @param end the upper bound of the range of portlets to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching portlets
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Portlet> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the first portlet in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching portlet
	* @throws com.liferay.portal.NoSuchPortletException if a matching portlet could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Portlet findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the last portlet in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param companyId the company ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching portlet
	* @throws com.liferay.portal.NoSuchPortletException if a matching portlet could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Portlet findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the portlets before and after the current portlet in the ordered set where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param id the primary key of the current portlet
	* @param companyId the company ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next portlet
	* @throws com.liferay.portal.NoSuchPortletException if a portlet with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Portlet[] findByCompanyId_PrevAndNext(
		long id, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.NoSuchPortletException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the portlet where companyId = &#63; and portletId = &#63; or throws a {@link com.liferay.portal.NoSuchPortletException} if it could not be found.
	*
	* @param companyId the company ID to search with
	* @param portletId the portlet ID to search with
	* @return the matching portlet
	* @throws com.liferay.portal.NoSuchPortletException if a matching portlet could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Portlet findByC_P(long companyId,
		java.lang.String portletId)
		throws com.liferay.portal.NoSuchPortletException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the portlet where companyId = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param companyId the company ID to search with
	* @param portletId the portlet ID to search with
	* @return the matching portlet, or <code>null</code> if a matching portlet could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Portlet fetchByC_P(long companyId,
		java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds the portlet where companyId = &#63; and portletId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param companyId the company ID to search with
	* @param portletId the portlet ID to search with
	* @return the matching portlet, or <code>null</code> if a matching portlet could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Portlet fetchByC_P(long companyId,
		java.lang.String portletId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds all the portlets.
	*
	* @return the portlets
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Portlet> findAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds a range of all the portlets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of portlets to return
	* @param end the upper bound of the range of portlets to return (not inclusive)
	* @return the range of portlets
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Portlet> findAll(int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Finds an ordered range of all the portlets.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of portlets to return
	* @param end the upper bound of the range of portlets to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of portlets
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portal.model.Portlet> findAll(int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the portlets where companyId = &#63; from the database.
	*
	* @param companyId the company ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes the portlet where companyId = &#63; and portletId = &#63; from the database.
	*
	* @param companyId the company ID to search with
	* @param portletId the portlet ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public void removeByC_P(long companyId, java.lang.String portletId)
		throws com.liferay.portal.NoSuchPortletException,
			com.liferay.portal.kernel.exception.SystemException;

	/**
	* Removes all the portlets from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the portlets where companyId = &#63;.
	*
	* @param companyId the company ID to search with
	* @return the number of matching portlets
	* @throws SystemException if a system exception occurred
	*/
	public int countByCompanyId(long companyId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the portlets where companyId = &#63; and portletId = &#63;.
	*
	* @param companyId the company ID to search with
	* @param portletId the portlet ID to search with
	* @return the number of matching portlets
	* @throws SystemException if a system exception occurred
	*/
	public int countByC_P(long companyId, java.lang.String portletId)
		throws com.liferay.portal.kernel.exception.SystemException;

	/**
	* Counts all the portlets.
	*
	* @return the number of portlets
	* @throws SystemException if a system exception occurred
	*/
	public int countAll()
		throws com.liferay.portal.kernel.exception.SystemException;
}