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

package com.liferay.portal.workflow.kaleo.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskForm;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the kaleo task form service. This utility wraps {@link com.liferay.portal.workflow.kaleo.service.persistence.impl.KaleoTaskFormPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTaskFormPersistence
 * @see com.liferay.portal.workflow.kaleo.service.persistence.impl.KaleoTaskFormPersistenceImpl
 * @generated
 */
@ProviderType
public class KaleoTaskFormUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(KaleoTaskForm kaleoTaskForm) {
		getPersistence().clearCache(kaleoTaskForm);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<KaleoTaskForm> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<KaleoTaskForm> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<KaleoTaskForm> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<KaleoTaskForm> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static KaleoTaskForm update(KaleoTaskForm kaleoTaskForm) {
		return getPersistence().update(kaleoTaskForm);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static KaleoTaskForm update(KaleoTaskForm kaleoTaskForm,
		ServiceContext serviceContext) {
		return getPersistence().update(kaleoTaskForm, serviceContext);
	}

	/**
	* Returns all the kaleo task forms where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching kaleo task forms
	*/
	public static List<KaleoTaskForm> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the kaleo task forms where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of kaleo task forms
	* @param end the upper bound of the range of kaleo task forms (not inclusive)
	* @return the range of matching kaleo task forms
	*/
	public static List<KaleoTaskForm> findByCompanyId(long companyId,
		int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the kaleo task forms where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of kaleo task forms
	* @param end the upper bound of the range of kaleo task forms (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching kaleo task forms
	*/
	public static List<KaleoTaskForm> findByCompanyId(long companyId,
		int start, int end, OrderByComparator<KaleoTaskForm> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the kaleo task forms where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of kaleo task forms
	* @param end the upper bound of the range of kaleo task forms (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching kaleo task forms
	*/
	public static List<KaleoTaskForm> findByCompanyId(long companyId,
		int start, int end, OrderByComparator<KaleoTaskForm> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first kaleo task form in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo task form
	* @throws NoSuchTaskFormException if a matching kaleo task form could not be found
	*/
	public static KaleoTaskForm findByCompanyId_First(long companyId,
		OrderByComparator<KaleoTaskForm> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first kaleo task form in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo task form, or <code>null</code> if a matching kaleo task form could not be found
	*/
	public static KaleoTaskForm fetchByCompanyId_First(long companyId,
		OrderByComparator<KaleoTaskForm> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last kaleo task form in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo task form
	* @throws NoSuchTaskFormException if a matching kaleo task form could not be found
	*/
	public static KaleoTaskForm findByCompanyId_Last(long companyId,
		OrderByComparator<KaleoTaskForm> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last kaleo task form in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo task form, or <code>null</code> if a matching kaleo task form could not be found
	*/
	public static KaleoTaskForm fetchByCompanyId_Last(long companyId,
		OrderByComparator<KaleoTaskForm> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the kaleo task forms before and after the current kaleo task form in the ordered set where companyId = &#63;.
	*
	* @param kaleoTaskFormId the primary key of the current kaleo task form
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next kaleo task form
	* @throws NoSuchTaskFormException if a kaleo task form with the primary key could not be found
	*/
	public static KaleoTaskForm[] findByCompanyId_PrevAndNext(
		long kaleoTaskFormId, long companyId,
		OrderByComparator<KaleoTaskForm> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(kaleoTaskFormId, companyId,
			orderByComparator);
	}

	/**
	* Removes all the kaleo task forms where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of kaleo task forms where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching kaleo task forms
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns all the kaleo task forms where kaleoDefinitionId = &#63;.
	*
	* @param kaleoDefinitionId the kaleo definition ID
	* @return the matching kaleo task forms
	*/
	public static List<KaleoTaskForm> findByKaleoDefinitionId(
		long kaleoDefinitionId) {
		return getPersistence().findByKaleoDefinitionId(kaleoDefinitionId);
	}

	/**
	* Returns a range of all the kaleo task forms where kaleoDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoDefinitionId the kaleo definition ID
	* @param start the lower bound of the range of kaleo task forms
	* @param end the upper bound of the range of kaleo task forms (not inclusive)
	* @return the range of matching kaleo task forms
	*/
	public static List<KaleoTaskForm> findByKaleoDefinitionId(
		long kaleoDefinitionId, int start, int end) {
		return getPersistence()
				   .findByKaleoDefinitionId(kaleoDefinitionId, start, end);
	}

	/**
	* Returns an ordered range of all the kaleo task forms where kaleoDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoDefinitionId the kaleo definition ID
	* @param start the lower bound of the range of kaleo task forms
	* @param end the upper bound of the range of kaleo task forms (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching kaleo task forms
	*/
	public static List<KaleoTaskForm> findByKaleoDefinitionId(
		long kaleoDefinitionId, int start, int end,
		OrderByComparator<KaleoTaskForm> orderByComparator) {
		return getPersistence()
				   .findByKaleoDefinitionId(kaleoDefinitionId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the kaleo task forms where kaleoDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoDefinitionId the kaleo definition ID
	* @param start the lower bound of the range of kaleo task forms
	* @param end the upper bound of the range of kaleo task forms (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching kaleo task forms
	*/
	public static List<KaleoTaskForm> findByKaleoDefinitionId(
		long kaleoDefinitionId, int start, int end,
		OrderByComparator<KaleoTaskForm> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByKaleoDefinitionId(kaleoDefinitionId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first kaleo task form in the ordered set where kaleoDefinitionId = &#63;.
	*
	* @param kaleoDefinitionId the kaleo definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo task form
	* @throws NoSuchTaskFormException if a matching kaleo task form could not be found
	*/
	public static KaleoTaskForm findByKaleoDefinitionId_First(
		long kaleoDefinitionId,
		OrderByComparator<KaleoTaskForm> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormException {
		return getPersistence()
				   .findByKaleoDefinitionId_First(kaleoDefinitionId,
			orderByComparator);
	}

	/**
	* Returns the first kaleo task form in the ordered set where kaleoDefinitionId = &#63;.
	*
	* @param kaleoDefinitionId the kaleo definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo task form, or <code>null</code> if a matching kaleo task form could not be found
	*/
	public static KaleoTaskForm fetchByKaleoDefinitionId_First(
		long kaleoDefinitionId,
		OrderByComparator<KaleoTaskForm> orderByComparator) {
		return getPersistence()
				   .fetchByKaleoDefinitionId_First(kaleoDefinitionId,
			orderByComparator);
	}

	/**
	* Returns the last kaleo task form in the ordered set where kaleoDefinitionId = &#63;.
	*
	* @param kaleoDefinitionId the kaleo definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo task form
	* @throws NoSuchTaskFormException if a matching kaleo task form could not be found
	*/
	public static KaleoTaskForm findByKaleoDefinitionId_Last(
		long kaleoDefinitionId,
		OrderByComparator<KaleoTaskForm> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormException {
		return getPersistence()
				   .findByKaleoDefinitionId_Last(kaleoDefinitionId,
			orderByComparator);
	}

	/**
	* Returns the last kaleo task form in the ordered set where kaleoDefinitionId = &#63;.
	*
	* @param kaleoDefinitionId the kaleo definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo task form, or <code>null</code> if a matching kaleo task form could not be found
	*/
	public static KaleoTaskForm fetchByKaleoDefinitionId_Last(
		long kaleoDefinitionId,
		OrderByComparator<KaleoTaskForm> orderByComparator) {
		return getPersistence()
				   .fetchByKaleoDefinitionId_Last(kaleoDefinitionId,
			orderByComparator);
	}

	/**
	* Returns the kaleo task forms before and after the current kaleo task form in the ordered set where kaleoDefinitionId = &#63;.
	*
	* @param kaleoTaskFormId the primary key of the current kaleo task form
	* @param kaleoDefinitionId the kaleo definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next kaleo task form
	* @throws NoSuchTaskFormException if a kaleo task form with the primary key could not be found
	*/
	public static KaleoTaskForm[] findByKaleoDefinitionId_PrevAndNext(
		long kaleoTaskFormId, long kaleoDefinitionId,
		OrderByComparator<KaleoTaskForm> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormException {
		return getPersistence()
				   .findByKaleoDefinitionId_PrevAndNext(kaleoTaskFormId,
			kaleoDefinitionId, orderByComparator);
	}

	/**
	* Removes all the kaleo task forms where kaleoDefinitionId = &#63; from the database.
	*
	* @param kaleoDefinitionId the kaleo definition ID
	*/
	public static void removeByKaleoDefinitionId(long kaleoDefinitionId) {
		getPersistence().removeByKaleoDefinitionId(kaleoDefinitionId);
	}

	/**
	* Returns the number of kaleo task forms where kaleoDefinitionId = &#63;.
	*
	* @param kaleoDefinitionId the kaleo definition ID
	* @return the number of matching kaleo task forms
	*/
	public static int countByKaleoDefinitionId(long kaleoDefinitionId) {
		return getPersistence().countByKaleoDefinitionId(kaleoDefinitionId);
	}

	/**
	* Returns all the kaleo task forms where kaleoNodeId = &#63;.
	*
	* @param kaleoNodeId the kaleo node ID
	* @return the matching kaleo task forms
	*/
	public static List<KaleoTaskForm> findByKaleoNodeId(long kaleoNodeId) {
		return getPersistence().findByKaleoNodeId(kaleoNodeId);
	}

	/**
	* Returns a range of all the kaleo task forms where kaleoNodeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoNodeId the kaleo node ID
	* @param start the lower bound of the range of kaleo task forms
	* @param end the upper bound of the range of kaleo task forms (not inclusive)
	* @return the range of matching kaleo task forms
	*/
	public static List<KaleoTaskForm> findByKaleoNodeId(long kaleoNodeId,
		int start, int end) {
		return getPersistence().findByKaleoNodeId(kaleoNodeId, start, end);
	}

	/**
	* Returns an ordered range of all the kaleo task forms where kaleoNodeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoNodeId the kaleo node ID
	* @param start the lower bound of the range of kaleo task forms
	* @param end the upper bound of the range of kaleo task forms (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching kaleo task forms
	*/
	public static List<KaleoTaskForm> findByKaleoNodeId(long kaleoNodeId,
		int start, int end, OrderByComparator<KaleoTaskForm> orderByComparator) {
		return getPersistence()
				   .findByKaleoNodeId(kaleoNodeId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the kaleo task forms where kaleoNodeId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoNodeId the kaleo node ID
	* @param start the lower bound of the range of kaleo task forms
	* @param end the upper bound of the range of kaleo task forms (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching kaleo task forms
	*/
	public static List<KaleoTaskForm> findByKaleoNodeId(long kaleoNodeId,
		int start, int end, OrderByComparator<KaleoTaskForm> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByKaleoNodeId(kaleoNodeId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first kaleo task form in the ordered set where kaleoNodeId = &#63;.
	*
	* @param kaleoNodeId the kaleo node ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo task form
	* @throws NoSuchTaskFormException if a matching kaleo task form could not be found
	*/
	public static KaleoTaskForm findByKaleoNodeId_First(long kaleoNodeId,
		OrderByComparator<KaleoTaskForm> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormException {
		return getPersistence()
				   .findByKaleoNodeId_First(kaleoNodeId, orderByComparator);
	}

	/**
	* Returns the first kaleo task form in the ordered set where kaleoNodeId = &#63;.
	*
	* @param kaleoNodeId the kaleo node ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo task form, or <code>null</code> if a matching kaleo task form could not be found
	*/
	public static KaleoTaskForm fetchByKaleoNodeId_First(long kaleoNodeId,
		OrderByComparator<KaleoTaskForm> orderByComparator) {
		return getPersistence()
				   .fetchByKaleoNodeId_First(kaleoNodeId, orderByComparator);
	}

	/**
	* Returns the last kaleo task form in the ordered set where kaleoNodeId = &#63;.
	*
	* @param kaleoNodeId the kaleo node ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo task form
	* @throws NoSuchTaskFormException if a matching kaleo task form could not be found
	*/
	public static KaleoTaskForm findByKaleoNodeId_Last(long kaleoNodeId,
		OrderByComparator<KaleoTaskForm> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormException {
		return getPersistence()
				   .findByKaleoNodeId_Last(kaleoNodeId, orderByComparator);
	}

	/**
	* Returns the last kaleo task form in the ordered set where kaleoNodeId = &#63;.
	*
	* @param kaleoNodeId the kaleo node ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo task form, or <code>null</code> if a matching kaleo task form could not be found
	*/
	public static KaleoTaskForm fetchByKaleoNodeId_Last(long kaleoNodeId,
		OrderByComparator<KaleoTaskForm> orderByComparator) {
		return getPersistence()
				   .fetchByKaleoNodeId_Last(kaleoNodeId, orderByComparator);
	}

	/**
	* Returns the kaleo task forms before and after the current kaleo task form in the ordered set where kaleoNodeId = &#63;.
	*
	* @param kaleoTaskFormId the primary key of the current kaleo task form
	* @param kaleoNodeId the kaleo node ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next kaleo task form
	* @throws NoSuchTaskFormException if a kaleo task form with the primary key could not be found
	*/
	public static KaleoTaskForm[] findByKaleoNodeId_PrevAndNext(
		long kaleoTaskFormId, long kaleoNodeId,
		OrderByComparator<KaleoTaskForm> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormException {
		return getPersistence()
				   .findByKaleoNodeId_PrevAndNext(kaleoTaskFormId, kaleoNodeId,
			orderByComparator);
	}

	/**
	* Removes all the kaleo task forms where kaleoNodeId = &#63; from the database.
	*
	* @param kaleoNodeId the kaleo node ID
	*/
	public static void removeByKaleoNodeId(long kaleoNodeId) {
		getPersistence().removeByKaleoNodeId(kaleoNodeId);
	}

	/**
	* Returns the number of kaleo task forms where kaleoNodeId = &#63;.
	*
	* @param kaleoNodeId the kaleo node ID
	* @return the number of matching kaleo task forms
	*/
	public static int countByKaleoNodeId(long kaleoNodeId) {
		return getPersistence().countByKaleoNodeId(kaleoNodeId);
	}

	/**
	* Returns all the kaleo task forms where kaleoTaskId = &#63;.
	*
	* @param kaleoTaskId the kaleo task ID
	* @return the matching kaleo task forms
	*/
	public static List<KaleoTaskForm> findByKaleoTaskId(long kaleoTaskId) {
		return getPersistence().findByKaleoTaskId(kaleoTaskId);
	}

	/**
	* Returns a range of all the kaleo task forms where kaleoTaskId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoTaskId the kaleo task ID
	* @param start the lower bound of the range of kaleo task forms
	* @param end the upper bound of the range of kaleo task forms (not inclusive)
	* @return the range of matching kaleo task forms
	*/
	public static List<KaleoTaskForm> findByKaleoTaskId(long kaleoTaskId,
		int start, int end) {
		return getPersistence().findByKaleoTaskId(kaleoTaskId, start, end);
	}

	/**
	* Returns an ordered range of all the kaleo task forms where kaleoTaskId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoTaskId the kaleo task ID
	* @param start the lower bound of the range of kaleo task forms
	* @param end the upper bound of the range of kaleo task forms (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching kaleo task forms
	*/
	public static List<KaleoTaskForm> findByKaleoTaskId(long kaleoTaskId,
		int start, int end, OrderByComparator<KaleoTaskForm> orderByComparator) {
		return getPersistence()
				   .findByKaleoTaskId(kaleoTaskId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the kaleo task forms where kaleoTaskId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoTaskId the kaleo task ID
	* @param start the lower bound of the range of kaleo task forms
	* @param end the upper bound of the range of kaleo task forms (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching kaleo task forms
	*/
	public static List<KaleoTaskForm> findByKaleoTaskId(long kaleoTaskId,
		int start, int end, OrderByComparator<KaleoTaskForm> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByKaleoTaskId(kaleoTaskId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first kaleo task form in the ordered set where kaleoTaskId = &#63;.
	*
	* @param kaleoTaskId the kaleo task ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo task form
	* @throws NoSuchTaskFormException if a matching kaleo task form could not be found
	*/
	public static KaleoTaskForm findByKaleoTaskId_First(long kaleoTaskId,
		OrderByComparator<KaleoTaskForm> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormException {
		return getPersistence()
				   .findByKaleoTaskId_First(kaleoTaskId, orderByComparator);
	}

	/**
	* Returns the first kaleo task form in the ordered set where kaleoTaskId = &#63;.
	*
	* @param kaleoTaskId the kaleo task ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo task form, or <code>null</code> if a matching kaleo task form could not be found
	*/
	public static KaleoTaskForm fetchByKaleoTaskId_First(long kaleoTaskId,
		OrderByComparator<KaleoTaskForm> orderByComparator) {
		return getPersistence()
				   .fetchByKaleoTaskId_First(kaleoTaskId, orderByComparator);
	}

	/**
	* Returns the last kaleo task form in the ordered set where kaleoTaskId = &#63;.
	*
	* @param kaleoTaskId the kaleo task ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo task form
	* @throws NoSuchTaskFormException if a matching kaleo task form could not be found
	*/
	public static KaleoTaskForm findByKaleoTaskId_Last(long kaleoTaskId,
		OrderByComparator<KaleoTaskForm> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormException {
		return getPersistence()
				   .findByKaleoTaskId_Last(kaleoTaskId, orderByComparator);
	}

	/**
	* Returns the last kaleo task form in the ordered set where kaleoTaskId = &#63;.
	*
	* @param kaleoTaskId the kaleo task ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo task form, or <code>null</code> if a matching kaleo task form could not be found
	*/
	public static KaleoTaskForm fetchByKaleoTaskId_Last(long kaleoTaskId,
		OrderByComparator<KaleoTaskForm> orderByComparator) {
		return getPersistence()
				   .fetchByKaleoTaskId_Last(kaleoTaskId, orderByComparator);
	}

	/**
	* Returns the kaleo task forms before and after the current kaleo task form in the ordered set where kaleoTaskId = &#63;.
	*
	* @param kaleoTaskFormId the primary key of the current kaleo task form
	* @param kaleoTaskId the kaleo task ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next kaleo task form
	* @throws NoSuchTaskFormException if a kaleo task form with the primary key could not be found
	*/
	public static KaleoTaskForm[] findByKaleoTaskId_PrevAndNext(
		long kaleoTaskFormId, long kaleoTaskId,
		OrderByComparator<KaleoTaskForm> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormException {
		return getPersistence()
				   .findByKaleoTaskId_PrevAndNext(kaleoTaskFormId, kaleoTaskId,
			orderByComparator);
	}

	/**
	* Removes all the kaleo task forms where kaleoTaskId = &#63; from the database.
	*
	* @param kaleoTaskId the kaleo task ID
	*/
	public static void removeByKaleoTaskId(long kaleoTaskId) {
		getPersistence().removeByKaleoTaskId(kaleoTaskId);
	}

	/**
	* Returns the number of kaleo task forms where kaleoTaskId = &#63;.
	*
	* @param kaleoTaskId the kaleo task ID
	* @return the number of matching kaleo task forms
	*/
	public static int countByKaleoTaskId(long kaleoTaskId) {
		return getPersistence().countByKaleoTaskId(kaleoTaskId);
	}

	/**
	* Returns the kaleo task form where kaleoTaskId = &#63; and formUuid = &#63; or throws a {@link NoSuchTaskFormException} if it could not be found.
	*
	* @param kaleoTaskId the kaleo task ID
	* @param formUuid the form uuid
	* @return the matching kaleo task form
	* @throws NoSuchTaskFormException if a matching kaleo task form could not be found
	*/
	public static KaleoTaskForm findByFormUuid_KTI(long kaleoTaskId,
		java.lang.String formUuid)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormException {
		return getPersistence().findByFormUuid_KTI(kaleoTaskId, formUuid);
	}

	/**
	* Returns the kaleo task form where kaleoTaskId = &#63; and formUuid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param kaleoTaskId the kaleo task ID
	* @param formUuid the form uuid
	* @return the matching kaleo task form, or <code>null</code> if a matching kaleo task form could not be found
	*/
	public static KaleoTaskForm fetchByFormUuid_KTI(long kaleoTaskId,
		java.lang.String formUuid) {
		return getPersistence().fetchByFormUuid_KTI(kaleoTaskId, formUuid);
	}

	/**
	* Returns the kaleo task form where kaleoTaskId = &#63; and formUuid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param kaleoTaskId the kaleo task ID
	* @param formUuid the form uuid
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching kaleo task form, or <code>null</code> if a matching kaleo task form could not be found
	*/
	public static KaleoTaskForm fetchByFormUuid_KTI(long kaleoTaskId,
		java.lang.String formUuid, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByFormUuid_KTI(kaleoTaskId, formUuid, retrieveFromCache);
	}

	/**
	* Removes the kaleo task form where kaleoTaskId = &#63; and formUuid = &#63; from the database.
	*
	* @param kaleoTaskId the kaleo task ID
	* @param formUuid the form uuid
	* @return the kaleo task form that was removed
	*/
	public static KaleoTaskForm removeByFormUuid_KTI(long kaleoTaskId,
		java.lang.String formUuid)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormException {
		return getPersistence().removeByFormUuid_KTI(kaleoTaskId, formUuid);
	}

	/**
	* Returns the number of kaleo task forms where kaleoTaskId = &#63; and formUuid = &#63;.
	*
	* @param kaleoTaskId the kaleo task ID
	* @param formUuid the form uuid
	* @return the number of matching kaleo task forms
	*/
	public static int countByFormUuid_KTI(long kaleoTaskId,
		java.lang.String formUuid) {
		return getPersistence().countByFormUuid_KTI(kaleoTaskId, formUuid);
	}

	/**
	* Caches the kaleo task form in the entity cache if it is enabled.
	*
	* @param kaleoTaskForm the kaleo task form
	*/
	public static void cacheResult(KaleoTaskForm kaleoTaskForm) {
		getPersistence().cacheResult(kaleoTaskForm);
	}

	/**
	* Caches the kaleo task forms in the entity cache if it is enabled.
	*
	* @param kaleoTaskForms the kaleo task forms
	*/
	public static void cacheResult(List<KaleoTaskForm> kaleoTaskForms) {
		getPersistence().cacheResult(kaleoTaskForms);
	}

	/**
	* Creates a new kaleo task form with the primary key. Does not add the kaleo task form to the database.
	*
	* @param kaleoTaskFormId the primary key for the new kaleo task form
	* @return the new kaleo task form
	*/
	public static KaleoTaskForm create(long kaleoTaskFormId) {
		return getPersistence().create(kaleoTaskFormId);
	}

	/**
	* Removes the kaleo task form with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param kaleoTaskFormId the primary key of the kaleo task form
	* @return the kaleo task form that was removed
	* @throws NoSuchTaskFormException if a kaleo task form with the primary key could not be found
	*/
	public static KaleoTaskForm remove(long kaleoTaskFormId)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormException {
		return getPersistence().remove(kaleoTaskFormId);
	}

	public static KaleoTaskForm updateImpl(KaleoTaskForm kaleoTaskForm) {
		return getPersistence().updateImpl(kaleoTaskForm);
	}

	/**
	* Returns the kaleo task form with the primary key or throws a {@link NoSuchTaskFormException} if it could not be found.
	*
	* @param kaleoTaskFormId the primary key of the kaleo task form
	* @return the kaleo task form
	* @throws NoSuchTaskFormException if a kaleo task form with the primary key could not be found
	*/
	public static KaleoTaskForm findByPrimaryKey(long kaleoTaskFormId)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormException {
		return getPersistence().findByPrimaryKey(kaleoTaskFormId);
	}

	/**
	* Returns the kaleo task form with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param kaleoTaskFormId the primary key of the kaleo task form
	* @return the kaleo task form, or <code>null</code> if a kaleo task form with the primary key could not be found
	*/
	public static KaleoTaskForm fetchByPrimaryKey(long kaleoTaskFormId) {
		return getPersistence().fetchByPrimaryKey(kaleoTaskFormId);
	}

	public static java.util.Map<java.io.Serializable, KaleoTaskForm> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the kaleo task forms.
	*
	* @return the kaleo task forms
	*/
	public static List<KaleoTaskForm> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the kaleo task forms.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of kaleo task forms
	* @param end the upper bound of the range of kaleo task forms (not inclusive)
	* @return the range of kaleo task forms
	*/
	public static List<KaleoTaskForm> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the kaleo task forms.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of kaleo task forms
	* @param end the upper bound of the range of kaleo task forms (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of kaleo task forms
	*/
	public static List<KaleoTaskForm> findAll(int start, int end,
		OrderByComparator<KaleoTaskForm> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the kaleo task forms.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of kaleo task forms
	* @param end the upper bound of the range of kaleo task forms (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of kaleo task forms
	*/
	public static List<KaleoTaskForm> findAll(int start, int end,
		OrderByComparator<KaleoTaskForm> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the kaleo task forms from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of kaleo task forms.
	*
	* @return the number of kaleo task forms
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static KaleoTaskFormPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<KaleoTaskFormPersistence, KaleoTaskFormPersistence> _serviceTracker =
		ServiceTrackerFactory.open(KaleoTaskFormPersistence.class);
}