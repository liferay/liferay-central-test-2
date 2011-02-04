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

package com.liferay.portlet.forms.service.persistence;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ReferenceRegistry;
import com.liferay.portal.service.ServiceContext;

import com.liferay.portlet.forms.model.FormStructureLink;

import java.util.List;

/**
 * The persistence utility for the form structure link service. This utility wraps {@link FormStructureLinkPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FormStructureLinkPersistence
 * @see FormStructureLinkPersistenceImpl
 * @generated
 */
public class FormStructureLinkUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#clearCache(com.liferay.portal.model.BaseModel)
	 */
	public static void clearCache(FormStructureLink formStructureLink) {
		getPersistence().clearCache(formStructureLink);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public long countWithDynamicQuery(DynamicQuery dynamicQuery)
		throws SystemException {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<FormStructureLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<FormStructureLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<FormStructureLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static FormStructureLink remove(FormStructureLink formStructureLink)
		throws SystemException {
		return getPersistence().remove(formStructureLink);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static FormStructureLink update(
		FormStructureLink formStructureLink, boolean merge)
		throws SystemException {
		return getPersistence().update(formStructureLink, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static FormStructureLink update(
		FormStructureLink formStructureLink, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence().update(formStructureLink, merge, serviceContext);
	}

	/**
	* Caches the form structure link in the entity cache if it is enabled.
	*
	* @param formStructureLink the form structure link to cache
	*/
	public static void cacheResult(
		com.liferay.portlet.forms.model.FormStructureLink formStructureLink) {
		getPersistence().cacheResult(formStructureLink);
	}

	/**
	* Caches the form structure links in the entity cache if it is enabled.
	*
	* @param formStructureLinks the form structure links to cache
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.forms.model.FormStructureLink> formStructureLinks) {
		getPersistence().cacheResult(formStructureLinks);
	}

	/**
	* Creates a new form structure link with the primary key. Does not add the form structure link to the database.
	*
	* @param formStructureLinkId the primary key for the new form structure link
	* @return the new form structure link
	*/
	public static com.liferay.portlet.forms.model.FormStructureLink create(
		long formStructureLinkId) {
		return getPersistence().create(formStructureLinkId);
	}

	/**
	* Removes the form structure link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param formStructureLinkId the primary key of the form structure link to remove
	* @return the form structure link that was removed
	* @throws com.liferay.portlet.forms.NoSuchFormStructureLinkException if a form structure link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.forms.model.FormStructureLink remove(
		long formStructureLinkId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.forms.NoSuchFormStructureLinkException {
		return getPersistence().remove(formStructureLinkId);
	}

	public static com.liferay.portlet.forms.model.FormStructureLink updateImpl(
		com.liferay.portlet.forms.model.FormStructureLink formStructureLink,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(formStructureLink, merge);
	}

	/**
	* Finds the form structure link with the primary key or throws a {@link com.liferay.portlet.forms.NoSuchFormStructureLinkException} if it could not be found.
	*
	* @param formStructureLinkId the primary key of the form structure link to find
	* @return the form structure link
	* @throws com.liferay.portlet.forms.NoSuchFormStructureLinkException if a form structure link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.forms.model.FormStructureLink findByPrimaryKey(
		long formStructureLinkId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.forms.NoSuchFormStructureLinkException {
		return getPersistence().findByPrimaryKey(formStructureLinkId);
	}

	/**
	* Finds the form structure link with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param formStructureLinkId the primary key of the form structure link to find
	* @return the form structure link, or <code>null</code> if a form structure link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.forms.model.FormStructureLink fetchByPrimaryKey(
		long formStructureLinkId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(formStructureLinkId);
	}

	/**
	* Finds the form structure link where formStructureLinkId = &#63; or throws a {@link com.liferay.portlet.forms.NoSuchFormStructureLinkException} if it could not be found.
	*
	* @param formStructureLinkId the form structure link ID to search with
	* @return the matching form structure link
	* @throws com.liferay.portlet.forms.NoSuchFormStructureLinkException if a matching form structure link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.forms.model.FormStructureLink findByFormStructureLinkId(
		long formStructureLinkId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.forms.NoSuchFormStructureLinkException {
		return getPersistence().findByFormStructureLinkId(formStructureLinkId);
	}

	/**
	* Finds the form structure link where formStructureLinkId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param formStructureLinkId the form structure link ID to search with
	* @return the matching form structure link, or <code>null</code> if a matching form structure link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.forms.model.FormStructureLink fetchByFormStructureLinkId(
		long formStructureLinkId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByFormStructureLinkId(formStructureLinkId);
	}

	/**
	* Finds the form structure link where formStructureLinkId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param formStructureLinkId the form structure link ID to search with
	* @return the matching form structure link, or <code>null</code> if a matching form structure link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.forms.model.FormStructureLink fetchByFormStructureLinkId(
		long formStructureLinkId, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByFormStructureLinkId(formStructureLinkId,
			retrieveFromCache);
	}

	/**
	* Finds all the form structure links where formStructureId = &#63;.
	*
	* @param formStructureId the form structure ID to search with
	* @return the matching form structure links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.forms.model.FormStructureLink> findByFormStructureId(
		java.lang.String formStructureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByFormStructureId(formStructureId);
	}

	/**
	* Finds a range of all the form structure links where formStructureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param formStructureId the form structure ID to search with
	* @param start the lower bound of the range of form structure links to return
	* @param end the upper bound of the range of form structure links to return (not inclusive)
	* @return the range of matching form structure links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.forms.model.FormStructureLink> findByFormStructureId(
		java.lang.String formStructureId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByFormStructureId(formStructureId, start, end);
	}

	/**
	* Finds an ordered range of all the form structure links where formStructureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param formStructureId the form structure ID to search with
	* @param start the lower bound of the range of form structure links to return
	* @param end the upper bound of the range of form structure links to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching form structure links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.forms.model.FormStructureLink> findByFormStructureId(
		java.lang.String formStructureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByFormStructureId(formStructureId, start, end,
			orderByComparator);
	}

	/**
	* Finds the first form structure link in the ordered set where formStructureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param formStructureId the form structure ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching form structure link
	* @throws com.liferay.portlet.forms.NoSuchFormStructureLinkException if a matching form structure link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.forms.model.FormStructureLink findByFormStructureId_First(
		java.lang.String formStructureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.forms.NoSuchFormStructureLinkException {
		return getPersistence()
				   .findByFormStructureId_First(formStructureId,
			orderByComparator);
	}

	/**
	* Finds the last form structure link in the ordered set where formStructureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param formStructureId the form structure ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching form structure link
	* @throws com.liferay.portlet.forms.NoSuchFormStructureLinkException if a matching form structure link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.forms.model.FormStructureLink findByFormStructureId_Last(
		java.lang.String formStructureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.forms.NoSuchFormStructureLinkException {
		return getPersistence()
				   .findByFormStructureId_Last(formStructureId,
			orderByComparator);
	}

	/**
	* Finds the form structure links before and after the current form structure link in the ordered set where formStructureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param formStructureLinkId the primary key of the current form structure link
	* @param formStructureId the form structure ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next form structure link
	* @throws com.liferay.portlet.forms.NoSuchFormStructureLinkException if a form structure link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.forms.model.FormStructureLink[] findByFormStructureId_PrevAndNext(
		long formStructureLinkId, java.lang.String formStructureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.forms.NoSuchFormStructureLinkException {
		return getPersistence()
				   .findByFormStructureId_PrevAndNext(formStructureLinkId,
			formStructureId, orderByComparator);
	}

	/**
	* Filters by the user's permissions and finds all the form structure links where formStructureId = &#63;.
	*
	* @param formStructureId the form structure ID to search with
	* @return the matching form structure links that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.forms.model.FormStructureLink> filterFindByFormStructureId(
		java.lang.String formStructureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterFindByFormStructureId(formStructureId);
	}

	/**
	* Filters by the user's permissions and finds a range of all the form structure links where formStructureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param formStructureId the form structure ID to search with
	* @param start the lower bound of the range of form structure links to return
	* @param end the upper bound of the range of form structure links to return (not inclusive)
	* @return the range of matching form structure links that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.forms.model.FormStructureLink> filterFindByFormStructureId(
		java.lang.String formStructureId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByFormStructureId(formStructureId, start, end);
	}

	/**
	* Filters by the user's permissions and finds an ordered range of all the form structure links where formStructureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param formStructureId the form structure ID to search with
	* @param start the lower bound of the range of form structure links to return
	* @param end the upper bound of the range of form structure links to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching form structure links that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.forms.model.FormStructureLink> filterFindByFormStructureId(
		java.lang.String formStructureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .filterFindByFormStructureId(formStructureId, start, end,
			orderByComparator);
	}

	/**
	* Finds the form structure link where formStructureId = &#63; and className = &#63; and classPK = &#63; or throws a {@link com.liferay.portlet.forms.NoSuchFormStructureLinkException} if it could not be found.
	*
	* @param formStructureId the form structure ID to search with
	* @param className the class name to search with
	* @param classPK the class p k to search with
	* @return the matching form structure link
	* @throws com.liferay.portlet.forms.NoSuchFormStructureLinkException if a matching form structure link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.forms.model.FormStructureLink findByF_C_C(
		java.lang.String formStructureId, java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.forms.NoSuchFormStructureLinkException {
		return getPersistence().findByF_C_C(formStructureId, className, classPK);
	}

	/**
	* Finds the form structure link where formStructureId = &#63; and className = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param formStructureId the form structure ID to search with
	* @param className the class name to search with
	* @param classPK the class p k to search with
	* @return the matching form structure link, or <code>null</code> if a matching form structure link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.forms.model.FormStructureLink fetchByF_C_C(
		java.lang.String formStructureId, java.lang.String className,
		long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByF_C_C(formStructureId, className, classPK);
	}

	/**
	* Finds the form structure link where formStructureId = &#63; and className = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param formStructureId the form structure ID to search with
	* @param className the class name to search with
	* @param classPK the class p k to search with
	* @return the matching form structure link, or <code>null</code> if a matching form structure link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.forms.model.FormStructureLink fetchByF_C_C(
		java.lang.String formStructureId, java.lang.String className,
		long classPK, boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByF_C_C(formStructureId, className, classPK,
			retrieveFromCache);
	}

	/**
	* Finds all the form structure links.
	*
	* @return the form structure links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.forms.model.FormStructureLink> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Finds a range of all the form structure links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of form structure links to return
	* @param end the upper bound of the range of form structure links to return (not inclusive)
	* @return the range of form structure links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.forms.model.FormStructureLink> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Finds an ordered range of all the form structure links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of form structure links to return
	* @param end the upper bound of the range of form structure links to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of form structure links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.forms.model.FormStructureLink> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes the form structure link where formStructureLinkId = &#63; from the database.
	*
	* @param formStructureLinkId the form structure link ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByFormStructureLinkId(long formStructureLinkId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.forms.NoSuchFormStructureLinkException {
		getPersistence().removeByFormStructureLinkId(formStructureLinkId);
	}

	/**
	* Removes all the form structure links where formStructureId = &#63; from the database.
	*
	* @param formStructureId the form structure ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByFormStructureId(java.lang.String formStructureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByFormStructureId(formStructureId);
	}

	/**
	* Removes the form structure link where formStructureId = &#63; and className = &#63; and classPK = &#63; from the database.
	*
	* @param formStructureId the form structure ID to search with
	* @param className the class name to search with
	* @param classPK the class p k to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByF_C_C(java.lang.String formStructureId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.forms.NoSuchFormStructureLinkException {
		getPersistence().removeByF_C_C(formStructureId, className, classPK);
	}

	/**
	* Removes all the form structure links from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Counts all the form structure links where formStructureLinkId = &#63;.
	*
	* @param formStructureLinkId the form structure link ID to search with
	* @return the number of matching form structure links
	* @throws SystemException if a system exception occurred
	*/
	public static int countByFormStructureLinkId(long formStructureLinkId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByFormStructureLinkId(formStructureLinkId);
	}

	/**
	* Counts all the form structure links where formStructureId = &#63;.
	*
	* @param formStructureId the form structure ID to search with
	* @return the number of matching form structure links
	* @throws SystemException if a system exception occurred
	*/
	public static int countByFormStructureId(java.lang.String formStructureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByFormStructureId(formStructureId);
	}

	/**
	* Filters by the user's permissions and counts all the form structure links where formStructureId = &#63;.
	*
	* @param formStructureId the form structure ID to search with
	* @return the number of matching form structure links that the user has permission to view
	* @throws SystemException if a system exception occurred
	*/
	public static int filterCountByFormStructureId(
		java.lang.String formStructureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().filterCountByFormStructureId(formStructureId);
	}

	/**
	* Counts all the form structure links where formStructureId = &#63; and className = &#63; and classPK = &#63;.
	*
	* @param formStructureId the form structure ID to search with
	* @param className the class name to search with
	* @param classPK the class p k to search with
	* @return the number of matching form structure links
	* @throws SystemException if a system exception occurred
	*/
	public static int countByF_C_C(java.lang.String formStructureId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByF_C_C(formStructureId, className, classPK);
	}

	/**
	* Counts all the form structure links.
	*
	* @return the number of form structure links
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static FormStructureLinkPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (FormStructureLinkPersistence)PortalBeanLocatorUtil.locate(FormStructureLinkPersistence.class.getName());

			ReferenceRegistry.registerReference(FormStructureLinkUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(FormStructureLinkPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(FormStructureLinkUtil.class,
			"_persistence");
	}

	private static FormStructureLinkPersistence _persistence;
}