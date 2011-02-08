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

import com.liferay.portlet.forms.model.FormsStructureEntryLink;

import java.util.List;

/**
 * The persistence utility for the forms structure entry link service. This utility wraps {@link FormsStructureEntryLinkPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see FormsStructureEntryLinkPersistence
 * @see FormsStructureEntryLinkPersistenceImpl
 * @generated
 */
public class FormsStructureEntryLinkUtil {
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
	public static void clearCache(
		FormsStructureEntryLink formsStructureEntryLink) {
		getPersistence().clearCache(formsStructureEntryLink);
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
	public static List<FormsStructureEntryLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery) throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<FormsStructureEntryLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end)
		throws SystemException {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<FormsStructureEntryLink> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator orderByComparator) throws SystemException {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#remove(com.liferay.portal.model.BaseModel)
	 */
	public static FormsStructureEntryLink remove(
		FormsStructureEntryLink formsStructureEntryLink)
		throws SystemException {
		return getPersistence().remove(formsStructureEntryLink);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean)
	 */
	public static FormsStructureEntryLink update(
		FormsStructureEntryLink formsStructureEntryLink, boolean merge)
		throws SystemException {
		return getPersistence().update(formsStructureEntryLink, merge);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, boolean, ServiceContext)
	 */
	public static FormsStructureEntryLink update(
		FormsStructureEntryLink formsStructureEntryLink, boolean merge,
		ServiceContext serviceContext) throws SystemException {
		return getPersistence()
				   .update(formsStructureEntryLink, merge, serviceContext);
	}

	/**
	* Caches the forms structure entry link in the entity cache if it is enabled.
	*
	* @param formsStructureEntryLink the forms structure entry link to cache
	*/
	public static void cacheResult(
		com.liferay.portlet.forms.model.FormsStructureEntryLink formsStructureEntryLink) {
		getPersistence().cacheResult(formsStructureEntryLink);
	}

	/**
	* Caches the forms structure entry links in the entity cache if it is enabled.
	*
	* @param formsStructureEntryLinks the forms structure entry links to cache
	*/
	public static void cacheResult(
		java.util.List<com.liferay.portlet.forms.model.FormsStructureEntryLink> formsStructureEntryLinks) {
		getPersistence().cacheResult(formsStructureEntryLinks);
	}

	/**
	* Creates a new forms structure entry link with the primary key. Does not add the forms structure entry link to the database.
	*
	* @param structureEntryLinkId the primary key for the new forms structure entry link
	* @return the new forms structure entry link
	*/
	public static com.liferay.portlet.forms.model.FormsStructureEntryLink create(
		long structureEntryLinkId) {
		return getPersistence().create(structureEntryLinkId);
	}

	/**
	* Removes the forms structure entry link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param structureEntryLinkId the primary key of the forms structure entry link to remove
	* @return the forms structure entry link that was removed
	* @throws com.liferay.portlet.forms.NoSuchStructureEntryLinkException if a forms structure entry link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.forms.model.FormsStructureEntryLink remove(
		long structureEntryLinkId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.forms.NoSuchStructureEntryLinkException {
		return getPersistence().remove(structureEntryLinkId);
	}

	public static com.liferay.portlet.forms.model.FormsStructureEntryLink updateImpl(
		com.liferay.portlet.forms.model.FormsStructureEntryLink formsStructureEntryLink,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().updateImpl(formsStructureEntryLink, merge);
	}

	/**
	* Finds the forms structure entry link with the primary key or throws a {@link com.liferay.portlet.forms.NoSuchStructureEntryLinkException} if it could not be found.
	*
	* @param structureEntryLinkId the primary key of the forms structure entry link to find
	* @return the forms structure entry link
	* @throws com.liferay.portlet.forms.NoSuchStructureEntryLinkException if a forms structure entry link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.forms.model.FormsStructureEntryLink findByPrimaryKey(
		long structureEntryLinkId)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.forms.NoSuchStructureEntryLinkException {
		return getPersistence().findByPrimaryKey(structureEntryLinkId);
	}

	/**
	* Finds the forms structure entry link with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param structureEntryLinkId the primary key of the forms structure entry link to find
	* @return the forms structure entry link, or <code>null</code> if a forms structure entry link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.forms.model.FormsStructureEntryLink fetchByPrimaryKey(
		long structureEntryLinkId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByPrimaryKey(structureEntryLinkId);
	}

	/**
	* Finds all the forms structure entry links where structureId = &#63;.
	*
	* @param structureId the structure ID to search with
	* @return the matching forms structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.forms.model.FormsStructureEntryLink> findByStructureId(
		java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByStructureId(structureId);
	}

	/**
	* Finds a range of all the forms structure entry links where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID to search with
	* @param start the lower bound of the range of forms structure entry links to return
	* @param end the upper bound of the range of forms structure entry links to return (not inclusive)
	* @return the range of matching forms structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.forms.model.FormsStructureEntryLink> findByStructureId(
		java.lang.String structureId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findByStructureId(structureId, start, end);
	}

	/**
	* Finds an ordered range of all the forms structure entry links where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID to search with
	* @param start the lower bound of the range of forms structure entry links to return
	* @param end the upper bound of the range of forms structure entry links to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of matching forms structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.forms.model.FormsStructureEntryLink> findByStructureId(
		java.lang.String structureId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .findByStructureId(structureId, start, end, orderByComparator);
	}

	/**
	* Finds the first forms structure entry link in the ordered set where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the first matching forms structure entry link
	* @throws com.liferay.portlet.forms.NoSuchStructureEntryLinkException if a matching forms structure entry link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.forms.model.FormsStructureEntryLink findByStructureId_First(
		java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.forms.NoSuchStructureEntryLinkException {
		return getPersistence()
				   .findByStructureId_First(structureId, orderByComparator);
	}

	/**
	* Finds the last forms structure entry link in the ordered set where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureId the structure ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the last matching forms structure entry link
	* @throws com.liferay.portlet.forms.NoSuchStructureEntryLinkException if a matching forms structure entry link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.forms.model.FormsStructureEntryLink findByStructureId_Last(
		java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.forms.NoSuchStructureEntryLinkException {
		return getPersistence()
				   .findByStructureId_Last(structureId, orderByComparator);
	}

	/**
	* Finds the forms structure entry links before and after the current forms structure entry link in the ordered set where structureId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param structureEntryLinkId the primary key of the current forms structure entry link
	* @param structureId the structure ID to search with
	* @param orderByComparator the comparator to order the set by
	* @return the previous, current, and next forms structure entry link
	* @throws com.liferay.portlet.forms.NoSuchStructureEntryLinkException if a forms structure entry link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.forms.model.FormsStructureEntryLink[] findByStructureId_PrevAndNext(
		long structureEntryLinkId, java.lang.String structureId,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.forms.NoSuchStructureEntryLinkException {
		return getPersistence()
				   .findByStructureId_PrevAndNext(structureEntryLinkId,
			structureId, orderByComparator);
	}

	/**
	* Finds the forms structure entry link where structureId = &#63; and className = &#63; and classPK = &#63; or throws a {@link com.liferay.portlet.forms.NoSuchStructureEntryLinkException} if it could not be found.
	*
	* @param structureId the structure ID to search with
	* @param className the class name to search with
	* @param classPK the class p k to search with
	* @return the matching forms structure entry link
	* @throws com.liferay.portlet.forms.NoSuchStructureEntryLinkException if a matching forms structure entry link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.forms.model.FormsStructureEntryLink findByS_C_C(
		java.lang.String structureId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.forms.NoSuchStructureEntryLinkException {
		return getPersistence().findByS_C_C(structureId, className, classPK);
	}

	/**
	* Finds the forms structure entry link where structureId = &#63; and className = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param structureId the structure ID to search with
	* @param className the class name to search with
	* @param classPK the class p k to search with
	* @return the matching forms structure entry link, or <code>null</code> if a matching forms structure entry link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.forms.model.FormsStructureEntryLink fetchByS_C_C(
		java.lang.String structureId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().fetchByS_C_C(structureId, className, classPK);
	}

	/**
	* Finds the forms structure entry link where structureId = &#63; and className = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param structureId the structure ID to search with
	* @param className the class name to search with
	* @param classPK the class p k to search with
	* @return the matching forms structure entry link, or <code>null</code> if a matching forms structure entry link could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.forms.model.FormsStructureEntryLink fetchByS_C_C(
		java.lang.String structureId, java.lang.String className, long classPK,
		boolean retrieveFromCache)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence()
				   .fetchByS_C_C(structureId, className, classPK,
			retrieveFromCache);
	}

	/**
	* Finds all the forms structure entry links.
	*
	* @return the forms structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.forms.model.FormsStructureEntryLink> findAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll();
	}

	/**
	* Finds a range of all the forms structure entry links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of forms structure entry links to return
	* @param end the upper bound of the range of forms structure entry links to return (not inclusive)
	* @return the range of forms structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.forms.model.FormsStructureEntryLink> findAll(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end);
	}

	/**
	* Finds an ordered range of all the forms structure entry links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of forms structure entry links to return
	* @param end the upper bound of the range of forms structure entry links to return (not inclusive)
	* @param orderByComparator the comparator to order the results by
	* @return the ordered range of forms structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.forms.model.FormsStructureEntryLink> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the forms structure entry links where structureId = &#63; from the database.
	*
	* @param structureId the structure ID to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByStructureId(java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeByStructureId(structureId);
	}

	/**
	* Removes the forms structure entry link where structureId = &#63; and className = &#63; and classPK = &#63; from the database.
	*
	* @param structureId the structure ID to search with
	* @param className the class name to search with
	* @param classPK the class p k to search with
	* @throws SystemException if a system exception occurred
	*/
	public static void removeByS_C_C(java.lang.String structureId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException,
			com.liferay.portlet.forms.NoSuchStructureEntryLinkException {
		getPersistence().removeByS_C_C(structureId, className, classPK);
	}

	/**
	* Removes all the forms structure entry links from the database.
	*
	* @throws SystemException if a system exception occurred
	*/
	public static void removeAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		getPersistence().removeAll();
	}

	/**
	* Counts all the forms structure entry links where structureId = &#63;.
	*
	* @param structureId the structure ID to search with
	* @return the number of matching forms structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public static int countByStructureId(java.lang.String structureId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByStructureId(structureId);
	}

	/**
	* Counts all the forms structure entry links where structureId = &#63; and className = &#63; and classPK = &#63;.
	*
	* @param structureId the structure ID to search with
	* @param className the class name to search with
	* @param classPK the class p k to search with
	* @return the number of matching forms structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public static int countByS_C_C(java.lang.String structureId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countByS_C_C(structureId, className, classPK);
	}

	/**
	* Counts all the forms structure entry links.
	*
	* @return the number of forms structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public static int countAll()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getPersistence().countAll();
	}

	public static FormsStructureEntryLinkPersistence getPersistence() {
		if (_persistence == null) {
			_persistence = (FormsStructureEntryLinkPersistence)PortalBeanLocatorUtil.locate(FormsStructureEntryLinkPersistence.class.getName());

			ReferenceRegistry.registerReference(FormsStructureEntryLinkUtil.class,
				"_persistence");
		}

		return _persistence;
	}

	public void setPersistence(FormsStructureEntryLinkPersistence persistence) {
		_persistence = persistence;

		ReferenceRegistry.registerReference(FormsStructureEntryLinkUtil.class,
			"_persistence");
	}

	private static FormsStructureEntryLinkPersistence _persistence;
}