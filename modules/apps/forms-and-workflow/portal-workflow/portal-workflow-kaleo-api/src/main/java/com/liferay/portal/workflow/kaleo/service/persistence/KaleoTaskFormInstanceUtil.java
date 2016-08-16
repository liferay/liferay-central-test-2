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
import com.liferay.portal.workflow.kaleo.model.KaleoTaskFormInstance;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the kaleo task form instance service. This utility wraps {@link com.liferay.portal.workflow.kaleo.service.persistence.impl.KaleoTaskFormInstancePersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see KaleoTaskFormInstancePersistence
 * @see com.liferay.portal.workflow.kaleo.service.persistence.impl.KaleoTaskFormInstancePersistenceImpl
 * @generated
 */
@ProviderType
public class KaleoTaskFormInstanceUtil {
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
	public static void clearCache(KaleoTaskFormInstance kaleoTaskFormInstance) {
		getPersistence().clearCache(kaleoTaskFormInstance);
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
	public static List<KaleoTaskFormInstance> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<KaleoTaskFormInstance> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<KaleoTaskFormInstance> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static KaleoTaskFormInstance update(
		KaleoTaskFormInstance kaleoTaskFormInstance) {
		return getPersistence().update(kaleoTaskFormInstance);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static KaleoTaskFormInstance update(
		KaleoTaskFormInstance kaleoTaskFormInstance,
		ServiceContext serviceContext) {
		return getPersistence().update(kaleoTaskFormInstance, serviceContext);
	}

	/**
	* Returns all the kaleo task form instances where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching kaleo task form instances
	*/
	public static List<KaleoTaskFormInstance> findByCompanyId(long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the kaleo task form instances where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @return the range of matching kaleo task form instances
	*/
	public static List<KaleoTaskFormInstance> findByCompanyId(long companyId,
		int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the kaleo task form instances where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching kaleo task form instances
	*/
	public static List<KaleoTaskFormInstance> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the kaleo task form instances where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching kaleo task form instances
	*/
	public static List<KaleoTaskFormInstance> findByCompanyId(long companyId,
		int start, int end,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator,
			retrieveFromCache);
	}

	/**
	* Returns the first kaleo task form instance in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo task form instance
	* @throws NoSuchTaskFormInstanceException if a matching kaleo task form instance could not be found
	*/
	public static KaleoTaskFormInstance findByCompanyId_First(long companyId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormInstanceException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first kaleo task form instance in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo task form instance, or <code>null</code> if a matching kaleo task form instance could not be found
	*/
	public static KaleoTaskFormInstance fetchByCompanyId_First(long companyId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last kaleo task form instance in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo task form instance
	* @throws NoSuchTaskFormInstanceException if a matching kaleo task form instance could not be found
	*/
	public static KaleoTaskFormInstance findByCompanyId_Last(long companyId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormInstanceException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last kaleo task form instance in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo task form instance, or <code>null</code> if a matching kaleo task form instance could not be found
	*/
	public static KaleoTaskFormInstance fetchByCompanyId_Last(long companyId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the kaleo task form instances before and after the current kaleo task form instance in the ordered set where companyId = &#63;.
	*
	* @param kaleoTaskFormInstanceId the primary key of the current kaleo task form instance
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next kaleo task form instance
	* @throws NoSuchTaskFormInstanceException if a kaleo task form instance with the primary key could not be found
	*/
	public static KaleoTaskFormInstance[] findByCompanyId_PrevAndNext(
		long kaleoTaskFormInstanceId, long companyId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormInstanceException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(kaleoTaskFormInstanceId,
			companyId, orderByComparator);
	}

	/**
	* Removes all the kaleo task form instances where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of kaleo task form instances where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching kaleo task form instances
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns all the kaleo task form instances where kaleoDefinitionId = &#63;.
	*
	* @param kaleoDefinitionId the kaleo definition ID
	* @return the matching kaleo task form instances
	*/
	public static List<KaleoTaskFormInstance> findByKaleoDefinitionId(
		long kaleoDefinitionId) {
		return getPersistence().findByKaleoDefinitionId(kaleoDefinitionId);
	}

	/**
	* Returns a range of all the kaleo task form instances where kaleoDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoDefinitionId the kaleo definition ID
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @return the range of matching kaleo task form instances
	*/
	public static List<KaleoTaskFormInstance> findByKaleoDefinitionId(
		long kaleoDefinitionId, int start, int end) {
		return getPersistence()
				   .findByKaleoDefinitionId(kaleoDefinitionId, start, end);
	}

	/**
	* Returns an ordered range of all the kaleo task form instances where kaleoDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoDefinitionId the kaleo definition ID
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching kaleo task form instances
	*/
	public static List<KaleoTaskFormInstance> findByKaleoDefinitionId(
		long kaleoDefinitionId, int start, int end,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator) {
		return getPersistence()
				   .findByKaleoDefinitionId(kaleoDefinitionId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the kaleo task form instances where kaleoDefinitionId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoDefinitionId the kaleo definition ID
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching kaleo task form instances
	*/
	public static List<KaleoTaskFormInstance> findByKaleoDefinitionId(
		long kaleoDefinitionId, int start, int end,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByKaleoDefinitionId(kaleoDefinitionId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first kaleo task form instance in the ordered set where kaleoDefinitionId = &#63;.
	*
	* @param kaleoDefinitionId the kaleo definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo task form instance
	* @throws NoSuchTaskFormInstanceException if a matching kaleo task form instance could not be found
	*/
	public static KaleoTaskFormInstance findByKaleoDefinitionId_First(
		long kaleoDefinitionId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormInstanceException {
		return getPersistence()
				   .findByKaleoDefinitionId_First(kaleoDefinitionId,
			orderByComparator);
	}

	/**
	* Returns the first kaleo task form instance in the ordered set where kaleoDefinitionId = &#63;.
	*
	* @param kaleoDefinitionId the kaleo definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo task form instance, or <code>null</code> if a matching kaleo task form instance could not be found
	*/
	public static KaleoTaskFormInstance fetchByKaleoDefinitionId_First(
		long kaleoDefinitionId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator) {
		return getPersistence()
				   .fetchByKaleoDefinitionId_First(kaleoDefinitionId,
			orderByComparator);
	}

	/**
	* Returns the last kaleo task form instance in the ordered set where kaleoDefinitionId = &#63;.
	*
	* @param kaleoDefinitionId the kaleo definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo task form instance
	* @throws NoSuchTaskFormInstanceException if a matching kaleo task form instance could not be found
	*/
	public static KaleoTaskFormInstance findByKaleoDefinitionId_Last(
		long kaleoDefinitionId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormInstanceException {
		return getPersistence()
				   .findByKaleoDefinitionId_Last(kaleoDefinitionId,
			orderByComparator);
	}

	/**
	* Returns the last kaleo task form instance in the ordered set where kaleoDefinitionId = &#63;.
	*
	* @param kaleoDefinitionId the kaleo definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo task form instance, or <code>null</code> if a matching kaleo task form instance could not be found
	*/
	public static KaleoTaskFormInstance fetchByKaleoDefinitionId_Last(
		long kaleoDefinitionId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator) {
		return getPersistence()
				   .fetchByKaleoDefinitionId_Last(kaleoDefinitionId,
			orderByComparator);
	}

	/**
	* Returns the kaleo task form instances before and after the current kaleo task form instance in the ordered set where kaleoDefinitionId = &#63;.
	*
	* @param kaleoTaskFormInstanceId the primary key of the current kaleo task form instance
	* @param kaleoDefinitionId the kaleo definition ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next kaleo task form instance
	* @throws NoSuchTaskFormInstanceException if a kaleo task form instance with the primary key could not be found
	*/
	public static KaleoTaskFormInstance[] findByKaleoDefinitionId_PrevAndNext(
		long kaleoTaskFormInstanceId, long kaleoDefinitionId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormInstanceException {
		return getPersistence()
				   .findByKaleoDefinitionId_PrevAndNext(kaleoTaskFormInstanceId,
			kaleoDefinitionId, orderByComparator);
	}

	/**
	* Removes all the kaleo task form instances where kaleoDefinitionId = &#63; from the database.
	*
	* @param kaleoDefinitionId the kaleo definition ID
	*/
	public static void removeByKaleoDefinitionId(long kaleoDefinitionId) {
		getPersistence().removeByKaleoDefinitionId(kaleoDefinitionId);
	}

	/**
	* Returns the number of kaleo task form instances where kaleoDefinitionId = &#63;.
	*
	* @param kaleoDefinitionId the kaleo definition ID
	* @return the number of matching kaleo task form instances
	*/
	public static int countByKaleoDefinitionId(long kaleoDefinitionId) {
		return getPersistence().countByKaleoDefinitionId(kaleoDefinitionId);
	}

	/**
	* Returns all the kaleo task form instances where kaleoInstanceId = &#63;.
	*
	* @param kaleoInstanceId the kaleo instance ID
	* @return the matching kaleo task form instances
	*/
	public static List<KaleoTaskFormInstance> findByKaleoInstanceId(
		long kaleoInstanceId) {
		return getPersistence().findByKaleoInstanceId(kaleoInstanceId);
	}

	/**
	* Returns a range of all the kaleo task form instances where kaleoInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoInstanceId the kaleo instance ID
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @return the range of matching kaleo task form instances
	*/
	public static List<KaleoTaskFormInstance> findByKaleoInstanceId(
		long kaleoInstanceId, int start, int end) {
		return getPersistence()
				   .findByKaleoInstanceId(kaleoInstanceId, start, end);
	}

	/**
	* Returns an ordered range of all the kaleo task form instances where kaleoInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoInstanceId the kaleo instance ID
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching kaleo task form instances
	*/
	public static List<KaleoTaskFormInstance> findByKaleoInstanceId(
		long kaleoInstanceId, int start, int end,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator) {
		return getPersistence()
				   .findByKaleoInstanceId(kaleoInstanceId, start, end,
			orderByComparator);
	}

	/**
	* Returns an ordered range of all the kaleo task form instances where kaleoInstanceId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoInstanceId the kaleo instance ID
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching kaleo task form instances
	*/
	public static List<KaleoTaskFormInstance> findByKaleoInstanceId(
		long kaleoInstanceId, int start, int end,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByKaleoInstanceId(kaleoInstanceId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first kaleo task form instance in the ordered set where kaleoInstanceId = &#63;.
	*
	* @param kaleoInstanceId the kaleo instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo task form instance
	* @throws NoSuchTaskFormInstanceException if a matching kaleo task form instance could not be found
	*/
	public static KaleoTaskFormInstance findByKaleoInstanceId_First(
		long kaleoInstanceId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormInstanceException {
		return getPersistence()
				   .findByKaleoInstanceId_First(kaleoInstanceId,
			orderByComparator);
	}

	/**
	* Returns the first kaleo task form instance in the ordered set where kaleoInstanceId = &#63;.
	*
	* @param kaleoInstanceId the kaleo instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo task form instance, or <code>null</code> if a matching kaleo task form instance could not be found
	*/
	public static KaleoTaskFormInstance fetchByKaleoInstanceId_First(
		long kaleoInstanceId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator) {
		return getPersistence()
				   .fetchByKaleoInstanceId_First(kaleoInstanceId,
			orderByComparator);
	}

	/**
	* Returns the last kaleo task form instance in the ordered set where kaleoInstanceId = &#63;.
	*
	* @param kaleoInstanceId the kaleo instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo task form instance
	* @throws NoSuchTaskFormInstanceException if a matching kaleo task form instance could not be found
	*/
	public static KaleoTaskFormInstance findByKaleoInstanceId_Last(
		long kaleoInstanceId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormInstanceException {
		return getPersistence()
				   .findByKaleoInstanceId_Last(kaleoInstanceId,
			orderByComparator);
	}

	/**
	* Returns the last kaleo task form instance in the ordered set where kaleoInstanceId = &#63;.
	*
	* @param kaleoInstanceId the kaleo instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo task form instance, or <code>null</code> if a matching kaleo task form instance could not be found
	*/
	public static KaleoTaskFormInstance fetchByKaleoInstanceId_Last(
		long kaleoInstanceId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator) {
		return getPersistence()
				   .fetchByKaleoInstanceId_Last(kaleoInstanceId,
			orderByComparator);
	}

	/**
	* Returns the kaleo task form instances before and after the current kaleo task form instance in the ordered set where kaleoInstanceId = &#63;.
	*
	* @param kaleoTaskFormInstanceId the primary key of the current kaleo task form instance
	* @param kaleoInstanceId the kaleo instance ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next kaleo task form instance
	* @throws NoSuchTaskFormInstanceException if a kaleo task form instance with the primary key could not be found
	*/
	public static KaleoTaskFormInstance[] findByKaleoInstanceId_PrevAndNext(
		long kaleoTaskFormInstanceId, long kaleoInstanceId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormInstanceException {
		return getPersistence()
				   .findByKaleoInstanceId_PrevAndNext(kaleoTaskFormInstanceId,
			kaleoInstanceId, orderByComparator);
	}

	/**
	* Removes all the kaleo task form instances where kaleoInstanceId = &#63; from the database.
	*
	* @param kaleoInstanceId the kaleo instance ID
	*/
	public static void removeByKaleoInstanceId(long kaleoInstanceId) {
		getPersistence().removeByKaleoInstanceId(kaleoInstanceId);
	}

	/**
	* Returns the number of kaleo task form instances where kaleoInstanceId = &#63;.
	*
	* @param kaleoInstanceId the kaleo instance ID
	* @return the number of matching kaleo task form instances
	*/
	public static int countByKaleoInstanceId(long kaleoInstanceId) {
		return getPersistence().countByKaleoInstanceId(kaleoInstanceId);
	}

	/**
	* Returns all the kaleo task form instances where kaleoTaskId = &#63;.
	*
	* @param kaleoTaskId the kaleo task ID
	* @return the matching kaleo task form instances
	*/
	public static List<KaleoTaskFormInstance> findByKaleoTaskId(
		long kaleoTaskId) {
		return getPersistence().findByKaleoTaskId(kaleoTaskId);
	}

	/**
	* Returns a range of all the kaleo task form instances where kaleoTaskId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoTaskId the kaleo task ID
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @return the range of matching kaleo task form instances
	*/
	public static List<KaleoTaskFormInstance> findByKaleoTaskId(
		long kaleoTaskId, int start, int end) {
		return getPersistence().findByKaleoTaskId(kaleoTaskId, start, end);
	}

	/**
	* Returns an ordered range of all the kaleo task form instances where kaleoTaskId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoTaskId the kaleo task ID
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching kaleo task form instances
	*/
	public static List<KaleoTaskFormInstance> findByKaleoTaskId(
		long kaleoTaskId, int start, int end,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator) {
		return getPersistence()
				   .findByKaleoTaskId(kaleoTaskId, start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the kaleo task form instances where kaleoTaskId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoTaskId the kaleo task ID
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching kaleo task form instances
	*/
	public static List<KaleoTaskFormInstance> findByKaleoTaskId(
		long kaleoTaskId, int start, int end,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByKaleoTaskId(kaleoTaskId, start, end,
			orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first kaleo task form instance in the ordered set where kaleoTaskId = &#63;.
	*
	* @param kaleoTaskId the kaleo task ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo task form instance
	* @throws NoSuchTaskFormInstanceException if a matching kaleo task form instance could not be found
	*/
	public static KaleoTaskFormInstance findByKaleoTaskId_First(
		long kaleoTaskId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormInstanceException {
		return getPersistence()
				   .findByKaleoTaskId_First(kaleoTaskId, orderByComparator);
	}

	/**
	* Returns the first kaleo task form instance in the ordered set where kaleoTaskId = &#63;.
	*
	* @param kaleoTaskId the kaleo task ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo task form instance, or <code>null</code> if a matching kaleo task form instance could not be found
	*/
	public static KaleoTaskFormInstance fetchByKaleoTaskId_First(
		long kaleoTaskId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator) {
		return getPersistence()
				   .fetchByKaleoTaskId_First(kaleoTaskId, orderByComparator);
	}

	/**
	* Returns the last kaleo task form instance in the ordered set where kaleoTaskId = &#63;.
	*
	* @param kaleoTaskId the kaleo task ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo task form instance
	* @throws NoSuchTaskFormInstanceException if a matching kaleo task form instance could not be found
	*/
	public static KaleoTaskFormInstance findByKaleoTaskId_Last(
		long kaleoTaskId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormInstanceException {
		return getPersistence()
				   .findByKaleoTaskId_Last(kaleoTaskId, orderByComparator);
	}

	/**
	* Returns the last kaleo task form instance in the ordered set where kaleoTaskId = &#63;.
	*
	* @param kaleoTaskId the kaleo task ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo task form instance, or <code>null</code> if a matching kaleo task form instance could not be found
	*/
	public static KaleoTaskFormInstance fetchByKaleoTaskId_Last(
		long kaleoTaskId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator) {
		return getPersistence()
				   .fetchByKaleoTaskId_Last(kaleoTaskId, orderByComparator);
	}

	/**
	* Returns the kaleo task form instances before and after the current kaleo task form instance in the ordered set where kaleoTaskId = &#63;.
	*
	* @param kaleoTaskFormInstanceId the primary key of the current kaleo task form instance
	* @param kaleoTaskId the kaleo task ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next kaleo task form instance
	* @throws NoSuchTaskFormInstanceException if a kaleo task form instance with the primary key could not be found
	*/
	public static KaleoTaskFormInstance[] findByKaleoTaskId_PrevAndNext(
		long kaleoTaskFormInstanceId, long kaleoTaskId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormInstanceException {
		return getPersistence()
				   .findByKaleoTaskId_PrevAndNext(kaleoTaskFormInstanceId,
			kaleoTaskId, orderByComparator);
	}

	/**
	* Removes all the kaleo task form instances where kaleoTaskId = &#63; from the database.
	*
	* @param kaleoTaskId the kaleo task ID
	*/
	public static void removeByKaleoTaskId(long kaleoTaskId) {
		getPersistence().removeByKaleoTaskId(kaleoTaskId);
	}

	/**
	* Returns the number of kaleo task form instances where kaleoTaskId = &#63;.
	*
	* @param kaleoTaskId the kaleo task ID
	* @return the number of matching kaleo task form instances
	*/
	public static int countByKaleoTaskId(long kaleoTaskId) {
		return getPersistence().countByKaleoTaskId(kaleoTaskId);
	}

	/**
	* Returns all the kaleo task form instances where kaleoTaskInstanceTokenId = &#63;.
	*
	* @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	* @return the matching kaleo task form instances
	*/
	public static List<KaleoTaskFormInstance> findByKaleoTaskInstanceTokenId(
		long kaleoTaskInstanceTokenId) {
		return getPersistence()
				   .findByKaleoTaskInstanceTokenId(kaleoTaskInstanceTokenId);
	}

	/**
	* Returns a range of all the kaleo task form instances where kaleoTaskInstanceTokenId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @return the range of matching kaleo task form instances
	*/
	public static List<KaleoTaskFormInstance> findByKaleoTaskInstanceTokenId(
		long kaleoTaskInstanceTokenId, int start, int end) {
		return getPersistence()
				   .findByKaleoTaskInstanceTokenId(kaleoTaskInstanceTokenId,
			start, end);
	}

	/**
	* Returns an ordered range of all the kaleo task form instances where kaleoTaskInstanceTokenId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching kaleo task form instances
	*/
	public static List<KaleoTaskFormInstance> findByKaleoTaskInstanceTokenId(
		long kaleoTaskInstanceTokenId, int start, int end,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator) {
		return getPersistence()
				   .findByKaleoTaskInstanceTokenId(kaleoTaskInstanceTokenId,
			start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the kaleo task form instances where kaleoTaskInstanceTokenId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of matching kaleo task form instances
	*/
	public static List<KaleoTaskFormInstance> findByKaleoTaskInstanceTokenId(
		long kaleoTaskInstanceTokenId, int start, int end,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findByKaleoTaskInstanceTokenId(kaleoTaskInstanceTokenId,
			start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Returns the first kaleo task form instance in the ordered set where kaleoTaskInstanceTokenId = &#63;.
	*
	* @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo task form instance
	* @throws NoSuchTaskFormInstanceException if a matching kaleo task form instance could not be found
	*/
	public static KaleoTaskFormInstance findByKaleoTaskInstanceTokenId_First(
		long kaleoTaskInstanceTokenId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormInstanceException {
		return getPersistence()
				   .findByKaleoTaskInstanceTokenId_First(kaleoTaskInstanceTokenId,
			orderByComparator);
	}

	/**
	* Returns the first kaleo task form instance in the ordered set where kaleoTaskInstanceTokenId = &#63;.
	*
	* @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching kaleo task form instance, or <code>null</code> if a matching kaleo task form instance could not be found
	*/
	public static KaleoTaskFormInstance fetchByKaleoTaskInstanceTokenId_First(
		long kaleoTaskInstanceTokenId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator) {
		return getPersistence()
				   .fetchByKaleoTaskInstanceTokenId_First(kaleoTaskInstanceTokenId,
			orderByComparator);
	}

	/**
	* Returns the last kaleo task form instance in the ordered set where kaleoTaskInstanceTokenId = &#63;.
	*
	* @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo task form instance
	* @throws NoSuchTaskFormInstanceException if a matching kaleo task form instance could not be found
	*/
	public static KaleoTaskFormInstance findByKaleoTaskInstanceTokenId_Last(
		long kaleoTaskInstanceTokenId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormInstanceException {
		return getPersistence()
				   .findByKaleoTaskInstanceTokenId_Last(kaleoTaskInstanceTokenId,
			orderByComparator);
	}

	/**
	* Returns the last kaleo task form instance in the ordered set where kaleoTaskInstanceTokenId = &#63;.
	*
	* @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching kaleo task form instance, or <code>null</code> if a matching kaleo task form instance could not be found
	*/
	public static KaleoTaskFormInstance fetchByKaleoTaskInstanceTokenId_Last(
		long kaleoTaskInstanceTokenId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator) {
		return getPersistence()
				   .fetchByKaleoTaskInstanceTokenId_Last(kaleoTaskInstanceTokenId,
			orderByComparator);
	}

	/**
	* Returns the kaleo task form instances before and after the current kaleo task form instance in the ordered set where kaleoTaskInstanceTokenId = &#63;.
	*
	* @param kaleoTaskFormInstanceId the primary key of the current kaleo task form instance
	* @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next kaleo task form instance
	* @throws NoSuchTaskFormInstanceException if a kaleo task form instance with the primary key could not be found
	*/
	public static KaleoTaskFormInstance[] findByKaleoTaskInstanceTokenId_PrevAndNext(
		long kaleoTaskFormInstanceId, long kaleoTaskInstanceTokenId,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormInstanceException {
		return getPersistence()
				   .findByKaleoTaskInstanceTokenId_PrevAndNext(kaleoTaskFormInstanceId,
			kaleoTaskInstanceTokenId, orderByComparator);
	}

	/**
	* Removes all the kaleo task form instances where kaleoTaskInstanceTokenId = &#63; from the database.
	*
	* @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	*/
	public static void removeByKaleoTaskInstanceTokenId(
		long kaleoTaskInstanceTokenId) {
		getPersistence()
			.removeByKaleoTaskInstanceTokenId(kaleoTaskInstanceTokenId);
	}

	/**
	* Returns the number of kaleo task form instances where kaleoTaskInstanceTokenId = &#63;.
	*
	* @param kaleoTaskInstanceTokenId the kaleo task instance token ID
	* @return the number of matching kaleo task form instances
	*/
	public static int countByKaleoTaskInstanceTokenId(
		long kaleoTaskInstanceTokenId) {
		return getPersistence()
				   .countByKaleoTaskInstanceTokenId(kaleoTaskInstanceTokenId);
	}

	/**
	* Returns the kaleo task form instance where kaleoTaskFormId = &#63; or throws a {@link NoSuchTaskFormInstanceException} if it could not be found.
	*
	* @param kaleoTaskFormId the kaleo task form ID
	* @return the matching kaleo task form instance
	* @throws NoSuchTaskFormInstanceException if a matching kaleo task form instance could not be found
	*/
	public static KaleoTaskFormInstance findByKaleoTaskFormId(
		long kaleoTaskFormId)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormInstanceException {
		return getPersistence().findByKaleoTaskFormId(kaleoTaskFormId);
	}

	/**
	* Returns the kaleo task form instance where kaleoTaskFormId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	*
	* @param kaleoTaskFormId the kaleo task form ID
	* @return the matching kaleo task form instance, or <code>null</code> if a matching kaleo task form instance could not be found
	*/
	public static KaleoTaskFormInstance fetchByKaleoTaskFormId(
		long kaleoTaskFormId) {
		return getPersistence().fetchByKaleoTaskFormId(kaleoTaskFormId);
	}

	/**
	* Returns the kaleo task form instance where kaleoTaskFormId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	*
	* @param kaleoTaskFormId the kaleo task form ID
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the matching kaleo task form instance, or <code>null</code> if a matching kaleo task form instance could not be found
	*/
	public static KaleoTaskFormInstance fetchByKaleoTaskFormId(
		long kaleoTaskFormId, boolean retrieveFromCache) {
		return getPersistence()
				   .fetchByKaleoTaskFormId(kaleoTaskFormId, retrieveFromCache);
	}

	/**
	* Removes the kaleo task form instance where kaleoTaskFormId = &#63; from the database.
	*
	* @param kaleoTaskFormId the kaleo task form ID
	* @return the kaleo task form instance that was removed
	*/
	public static KaleoTaskFormInstance removeByKaleoTaskFormId(
		long kaleoTaskFormId)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormInstanceException {
		return getPersistence().removeByKaleoTaskFormId(kaleoTaskFormId);
	}

	/**
	* Returns the number of kaleo task form instances where kaleoTaskFormId = &#63;.
	*
	* @param kaleoTaskFormId the kaleo task form ID
	* @return the number of matching kaleo task form instances
	*/
	public static int countByKaleoTaskFormId(long kaleoTaskFormId) {
		return getPersistence().countByKaleoTaskFormId(kaleoTaskFormId);
	}

	/**
	* Caches the kaleo task form instance in the entity cache if it is enabled.
	*
	* @param kaleoTaskFormInstance the kaleo task form instance
	*/
	public static void cacheResult(KaleoTaskFormInstance kaleoTaskFormInstance) {
		getPersistence().cacheResult(kaleoTaskFormInstance);
	}

	/**
	* Caches the kaleo task form instances in the entity cache if it is enabled.
	*
	* @param kaleoTaskFormInstances the kaleo task form instances
	*/
	public static void cacheResult(
		List<KaleoTaskFormInstance> kaleoTaskFormInstances) {
		getPersistence().cacheResult(kaleoTaskFormInstances);
	}

	/**
	* Creates a new kaleo task form instance with the primary key. Does not add the kaleo task form instance to the database.
	*
	* @param kaleoTaskFormInstanceId the primary key for the new kaleo task form instance
	* @return the new kaleo task form instance
	*/
	public static KaleoTaskFormInstance create(long kaleoTaskFormInstanceId) {
		return getPersistence().create(kaleoTaskFormInstanceId);
	}

	/**
	* Removes the kaleo task form instance with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param kaleoTaskFormInstanceId the primary key of the kaleo task form instance
	* @return the kaleo task form instance that was removed
	* @throws NoSuchTaskFormInstanceException if a kaleo task form instance with the primary key could not be found
	*/
	public static KaleoTaskFormInstance remove(long kaleoTaskFormInstanceId)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormInstanceException {
		return getPersistence().remove(kaleoTaskFormInstanceId);
	}

	public static KaleoTaskFormInstance updateImpl(
		KaleoTaskFormInstance kaleoTaskFormInstance) {
		return getPersistence().updateImpl(kaleoTaskFormInstance);
	}

	/**
	* Returns the kaleo task form instance with the primary key or throws a {@link NoSuchTaskFormInstanceException} if it could not be found.
	*
	* @param kaleoTaskFormInstanceId the primary key of the kaleo task form instance
	* @return the kaleo task form instance
	* @throws NoSuchTaskFormInstanceException if a kaleo task form instance with the primary key could not be found
	*/
	public static KaleoTaskFormInstance findByPrimaryKey(
		long kaleoTaskFormInstanceId)
		throws com.liferay.portal.workflow.kaleo.exception.NoSuchTaskFormInstanceException {
		return getPersistence().findByPrimaryKey(kaleoTaskFormInstanceId);
	}

	/**
	* Returns the kaleo task form instance with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param kaleoTaskFormInstanceId the primary key of the kaleo task form instance
	* @return the kaleo task form instance, or <code>null</code> if a kaleo task form instance with the primary key could not be found
	*/
	public static KaleoTaskFormInstance fetchByPrimaryKey(
		long kaleoTaskFormInstanceId) {
		return getPersistence().fetchByPrimaryKey(kaleoTaskFormInstanceId);
	}

	public static java.util.Map<java.io.Serializable, KaleoTaskFormInstance> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the kaleo task form instances.
	*
	* @return the kaleo task form instances
	*/
	public static List<KaleoTaskFormInstance> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the kaleo task form instances.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @return the range of kaleo task form instances
	*/
	public static List<KaleoTaskFormInstance> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the kaleo task form instances.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of kaleo task form instances
	*/
	public static List<KaleoTaskFormInstance> findAll(int start, int end,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Returns an ordered range of all the kaleo task form instances.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link KaleoTaskFormInstanceModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of kaleo task form instances
	* @param end the upper bound of the range of kaleo task form instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @param retrieveFromCache whether to retrieve from the finder cache
	* @return the ordered range of kaleo task form instances
	*/
	public static List<KaleoTaskFormInstance> findAll(int start, int end,
		OrderByComparator<KaleoTaskFormInstance> orderByComparator,
		boolean retrieveFromCache) {
		return getPersistence()
				   .findAll(start, end, orderByComparator, retrieveFromCache);
	}

	/**
	* Removes all the kaleo task form instances from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of kaleo task form instances.
	*
	* @return the number of kaleo task form instances
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static KaleoTaskFormInstancePersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<KaleoTaskFormInstancePersistence, KaleoTaskFormInstancePersistence> _serviceTracker =
		ServiceTrackerFactory.open(KaleoTaskFormInstancePersistence.class);
}