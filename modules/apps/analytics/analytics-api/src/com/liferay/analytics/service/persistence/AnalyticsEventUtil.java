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

package com.liferay.analytics.service.persistence;

import aQute.bnd.annotation.ProviderType;

import com.liferay.analytics.model.AnalyticsEvent;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.service.ServiceContext;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;

import org.osgi.util.tracker.ServiceTracker;

import java.util.List;

/**
 * The persistence utility for the analytics event service. This utility wraps {@link AnalyticsEventPersistenceImpl} and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see AnalyticsEventPersistence
 * @see AnalyticsEventPersistenceImpl
 * @generated
 */
@ProviderType
public class AnalyticsEventUtil {
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
	public static void clearCache(AnalyticsEvent analyticsEvent) {
		getPersistence().clearCache(analyticsEvent);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<AnalyticsEvent> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {
		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<AnalyticsEvent> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {
		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<AnalyticsEvent> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<AnalyticsEvent> orderByComparator) {
		return getPersistence()
				   .findWithDynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel)
	 */
	public static AnalyticsEvent update(AnalyticsEvent analyticsEvent) {
		return getPersistence().update(analyticsEvent);
	}

	/**
	 * @see com.liferay.portal.service.persistence.BasePersistence#update(com.liferay.portal.model.BaseModel, ServiceContext)
	 */
	public static AnalyticsEvent update(AnalyticsEvent analyticsEvent,
		ServiceContext serviceContext) {
		return getPersistence().update(analyticsEvent, serviceContext);
	}

	/**
	* Returns all the analytics events where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the matching analytics events
	*/
	public static java.util.List<com.liferay.analytics.model.AnalyticsEvent> findByCompanyId(
		long companyId) {
		return getPersistence().findByCompanyId(companyId);
	}

	/**
	* Returns a range of all the analytics events where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.analytics.model.impl.AnalyticsEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of analytics events
	* @param end the upper bound of the range of analytics events (not inclusive)
	* @return the range of matching analytics events
	*/
	public static java.util.List<com.liferay.analytics.model.AnalyticsEvent> findByCompanyId(
		long companyId, int start, int end) {
		return getPersistence().findByCompanyId(companyId, start, end);
	}

	/**
	* Returns an ordered range of all the analytics events where companyId = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.analytics.model.impl.AnalyticsEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param start the lower bound of the range of analytics events
	* @param end the upper bound of the range of analytics events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching analytics events
	*/
	public static java.util.List<com.liferay.analytics.model.AnalyticsEvent> findByCompanyId(
		long companyId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator) {
		return getPersistence()
				   .findByCompanyId(companyId, start, end, orderByComparator);
	}

	/**
	* Returns the first analytics event in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching analytics event
	* @throws com.liferay.analytics.NoSuchEventException if a matching analytics event could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent findByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator)
		throws com.liferay.analytics.NoSuchEventException {
		return getPersistence()
				   .findByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the first analytics event in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching analytics event, or <code>null</code> if a matching analytics event could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent fetchByCompanyId_First(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_First(companyId, orderByComparator);
	}

	/**
	* Returns the last analytics event in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching analytics event
	* @throws com.liferay.analytics.NoSuchEventException if a matching analytics event could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent findByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator)
		throws com.liferay.analytics.NoSuchEventException {
		return getPersistence()
				   .findByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the last analytics event in the ordered set where companyId = &#63;.
	*
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching analytics event, or <code>null</code> if a matching analytics event could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent fetchByCompanyId_Last(
		long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator) {
		return getPersistence()
				   .fetchByCompanyId_Last(companyId, orderByComparator);
	}

	/**
	* Returns the analytics events before and after the current analytics event in the ordered set where companyId = &#63;.
	*
	* @param analyticsEventId the primary key of the current analytics event
	* @param companyId the company ID
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next analytics event
	* @throws com.liferay.analytics.NoSuchEventException if a analytics event with the primary key could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent[] findByCompanyId_PrevAndNext(
		long analyticsEventId, long companyId,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator)
		throws com.liferay.analytics.NoSuchEventException {
		return getPersistence()
				   .findByCompanyId_PrevAndNext(analyticsEventId, companyId,
			orderByComparator);
	}

	/**
	* Removes all the analytics events where companyId = &#63; from the database.
	*
	* @param companyId the company ID
	*/
	public static void removeByCompanyId(long companyId) {
		getPersistence().removeByCompanyId(companyId);
	}

	/**
	* Returns the number of analytics events where companyId = &#63;.
	*
	* @param companyId the company ID
	* @return the number of matching analytics events
	*/
	public static int countByCompanyId(long companyId) {
		return getPersistence().countByCompanyId(companyId);
	}

	/**
	* Returns all the analytics events where companyId = &#63; and createDate &gt; &#63;.
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @return the matching analytics events
	*/
	public static java.util.List<com.liferay.analytics.model.AnalyticsEvent> findByC_GtCD(
		long companyId, java.util.Date createDate) {
		return getPersistence().findByC_GtCD(companyId, createDate);
	}

	/**
	* Returns a range of all the analytics events where companyId = &#63; and createDate &gt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.analytics.model.impl.AnalyticsEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param start the lower bound of the range of analytics events
	* @param end the upper bound of the range of analytics events (not inclusive)
	* @return the range of matching analytics events
	*/
	public static java.util.List<com.liferay.analytics.model.AnalyticsEvent> findByC_GtCD(
		long companyId, java.util.Date createDate, int start, int end) {
		return getPersistence().findByC_GtCD(companyId, createDate, start, end);
	}

	/**
	* Returns an ordered range of all the analytics events where companyId = &#63; and createDate &gt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.analytics.model.impl.AnalyticsEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param start the lower bound of the range of analytics events
	* @param end the upper bound of the range of analytics events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching analytics events
	*/
	public static java.util.List<com.liferay.analytics.model.AnalyticsEvent> findByC_GtCD(
		long companyId, java.util.Date createDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator) {
		return getPersistence()
				   .findByC_GtCD(companyId, createDate, start, end,
			orderByComparator);
	}

	/**
	* Returns the first analytics event in the ordered set where companyId = &#63; and createDate &gt; &#63;.
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching analytics event
	* @throws com.liferay.analytics.NoSuchEventException if a matching analytics event could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent findByC_GtCD_First(
		long companyId, java.util.Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator)
		throws com.liferay.analytics.NoSuchEventException {
		return getPersistence()
				   .findByC_GtCD_First(companyId, createDate, orderByComparator);
	}

	/**
	* Returns the first analytics event in the ordered set where companyId = &#63; and createDate &gt; &#63;.
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching analytics event, or <code>null</code> if a matching analytics event could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent fetchByC_GtCD_First(
		long companyId, java.util.Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator) {
		return getPersistence()
				   .fetchByC_GtCD_First(companyId, createDate, orderByComparator);
	}

	/**
	* Returns the last analytics event in the ordered set where companyId = &#63; and createDate &gt; &#63;.
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching analytics event
	* @throws com.liferay.analytics.NoSuchEventException if a matching analytics event could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent findByC_GtCD_Last(
		long companyId, java.util.Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator)
		throws com.liferay.analytics.NoSuchEventException {
		return getPersistence()
				   .findByC_GtCD_Last(companyId, createDate, orderByComparator);
	}

	/**
	* Returns the last analytics event in the ordered set where companyId = &#63; and createDate &gt; &#63;.
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching analytics event, or <code>null</code> if a matching analytics event could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent fetchByC_GtCD_Last(
		long companyId, java.util.Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator) {
		return getPersistence()
				   .fetchByC_GtCD_Last(companyId, createDate, orderByComparator);
	}

	/**
	* Returns the analytics events before and after the current analytics event in the ordered set where companyId = &#63; and createDate &gt; &#63;.
	*
	* @param analyticsEventId the primary key of the current analytics event
	* @param companyId the company ID
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next analytics event
	* @throws com.liferay.analytics.NoSuchEventException if a analytics event with the primary key could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent[] findByC_GtCD_PrevAndNext(
		long analyticsEventId, long companyId, java.util.Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator)
		throws com.liferay.analytics.NoSuchEventException {
		return getPersistence()
				   .findByC_GtCD_PrevAndNext(analyticsEventId, companyId,
			createDate, orderByComparator);
	}

	/**
	* Removes all the analytics events where companyId = &#63; and createDate &gt; &#63; from the database.
	*
	* @param companyId the company ID
	* @param createDate the create date
	*/
	public static void removeByC_GtCD(long companyId, java.util.Date createDate) {
		getPersistence().removeByC_GtCD(companyId, createDate);
	}

	/**
	* Returns the number of analytics events where companyId = &#63; and createDate &gt; &#63;.
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @return the number of matching analytics events
	*/
	public static int countByC_GtCD(long companyId, java.util.Date createDate) {
		return getPersistence().countByC_GtCD(companyId, createDate);
	}

	/**
	* Returns all the analytics events where companyId = &#63; and createDate &lt; &#63;.
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @return the matching analytics events
	*/
	public static java.util.List<com.liferay.analytics.model.AnalyticsEvent> findByC_LtCD(
		long companyId, java.util.Date createDate) {
		return getPersistence().findByC_LtCD(companyId, createDate);
	}

	/**
	* Returns a range of all the analytics events where companyId = &#63; and createDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.analytics.model.impl.AnalyticsEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param start the lower bound of the range of analytics events
	* @param end the upper bound of the range of analytics events (not inclusive)
	* @return the range of matching analytics events
	*/
	public static java.util.List<com.liferay.analytics.model.AnalyticsEvent> findByC_LtCD(
		long companyId, java.util.Date createDate, int start, int end) {
		return getPersistence().findByC_LtCD(companyId, createDate, start, end);
	}

	/**
	* Returns an ordered range of all the analytics events where companyId = &#63; and createDate &lt; &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.analytics.model.impl.AnalyticsEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param start the lower bound of the range of analytics events
	* @param end the upper bound of the range of analytics events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching analytics events
	*/
	public static java.util.List<com.liferay.analytics.model.AnalyticsEvent> findByC_LtCD(
		long companyId, java.util.Date createDate, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator) {
		return getPersistence()
				   .findByC_LtCD(companyId, createDate, start, end,
			orderByComparator);
	}

	/**
	* Returns the first analytics event in the ordered set where companyId = &#63; and createDate &lt; &#63;.
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching analytics event
	* @throws com.liferay.analytics.NoSuchEventException if a matching analytics event could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent findByC_LtCD_First(
		long companyId, java.util.Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator)
		throws com.liferay.analytics.NoSuchEventException {
		return getPersistence()
				   .findByC_LtCD_First(companyId, createDate, orderByComparator);
	}

	/**
	* Returns the first analytics event in the ordered set where companyId = &#63; and createDate &lt; &#63;.
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching analytics event, or <code>null</code> if a matching analytics event could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent fetchByC_LtCD_First(
		long companyId, java.util.Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator) {
		return getPersistence()
				   .fetchByC_LtCD_First(companyId, createDate, orderByComparator);
	}

	/**
	* Returns the last analytics event in the ordered set where companyId = &#63; and createDate &lt; &#63;.
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching analytics event
	* @throws com.liferay.analytics.NoSuchEventException if a matching analytics event could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent findByC_LtCD_Last(
		long companyId, java.util.Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator)
		throws com.liferay.analytics.NoSuchEventException {
		return getPersistence()
				   .findByC_LtCD_Last(companyId, createDate, orderByComparator);
	}

	/**
	* Returns the last analytics event in the ordered set where companyId = &#63; and createDate &lt; &#63;.
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching analytics event, or <code>null</code> if a matching analytics event could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent fetchByC_LtCD_Last(
		long companyId, java.util.Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator) {
		return getPersistence()
				   .fetchByC_LtCD_Last(companyId, createDate, orderByComparator);
	}

	/**
	* Returns the analytics events before and after the current analytics event in the ordered set where companyId = &#63; and createDate &lt; &#63;.
	*
	* @param analyticsEventId the primary key of the current analytics event
	* @param companyId the company ID
	* @param createDate the create date
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next analytics event
	* @throws com.liferay.analytics.NoSuchEventException if a analytics event with the primary key could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent[] findByC_LtCD_PrevAndNext(
		long analyticsEventId, long companyId, java.util.Date createDate,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator)
		throws com.liferay.analytics.NoSuchEventException {
		return getPersistence()
				   .findByC_LtCD_PrevAndNext(analyticsEventId, companyId,
			createDate, orderByComparator);
	}

	/**
	* Removes all the analytics events where companyId = &#63; and createDate &lt; &#63; from the database.
	*
	* @param companyId the company ID
	* @param createDate the create date
	*/
	public static void removeByC_LtCD(long companyId, java.util.Date createDate) {
		getPersistence().removeByC_LtCD(companyId, createDate);
	}

	/**
	* Returns the number of analytics events where companyId = &#63; and createDate &lt; &#63;.
	*
	* @param companyId the company ID
	* @param createDate the create date
	* @return the number of matching analytics events
	*/
	public static int countByC_LtCD(long companyId, java.util.Date createDate) {
		return getPersistence().countByC_LtCD(companyId, createDate);
	}

	/**
	* Returns all the analytics events where createDate &gt; &#63; and elementKey = &#63; and type = &#63;.
	*
	* @param createDate the create date
	* @param elementKey the element key
	* @param type the type
	* @return the matching analytics events
	*/
	public static java.util.List<com.liferay.analytics.model.AnalyticsEvent> findByGtCD_E_E(
		java.util.Date createDate, java.lang.String elementKey,
		java.lang.String type) {
		return getPersistence().findByGtCD_E_E(createDate, elementKey, type);
	}

	/**
	* Returns a range of all the analytics events where createDate &gt; &#63; and elementKey = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.analytics.model.impl.AnalyticsEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param createDate the create date
	* @param elementKey the element key
	* @param type the type
	* @param start the lower bound of the range of analytics events
	* @param end the upper bound of the range of analytics events (not inclusive)
	* @return the range of matching analytics events
	*/
	public static java.util.List<com.liferay.analytics.model.AnalyticsEvent> findByGtCD_E_E(
		java.util.Date createDate, java.lang.String elementKey,
		java.lang.String type, int start, int end) {
		return getPersistence()
				   .findByGtCD_E_E(createDate, elementKey, type, start, end);
	}

	/**
	* Returns an ordered range of all the analytics events where createDate &gt; &#63; and elementKey = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.analytics.model.impl.AnalyticsEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param createDate the create date
	* @param elementKey the element key
	* @param type the type
	* @param start the lower bound of the range of analytics events
	* @param end the upper bound of the range of analytics events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching analytics events
	*/
	public static java.util.List<com.liferay.analytics.model.AnalyticsEvent> findByGtCD_E_E(
		java.util.Date createDate, java.lang.String elementKey,
		java.lang.String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator) {
		return getPersistence()
				   .findByGtCD_E_E(createDate, elementKey, type, start, end,
			orderByComparator);
	}

	/**
	* Returns the first analytics event in the ordered set where createDate &gt; &#63; and elementKey = &#63; and type = &#63;.
	*
	* @param createDate the create date
	* @param elementKey the element key
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching analytics event
	* @throws com.liferay.analytics.NoSuchEventException if a matching analytics event could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent findByGtCD_E_E_First(
		java.util.Date createDate, java.lang.String elementKey,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator)
		throws com.liferay.analytics.NoSuchEventException {
		return getPersistence()
				   .findByGtCD_E_E_First(createDate, elementKey, type,
			orderByComparator);
	}

	/**
	* Returns the first analytics event in the ordered set where createDate &gt; &#63; and elementKey = &#63; and type = &#63;.
	*
	* @param createDate the create date
	* @param elementKey the element key
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching analytics event, or <code>null</code> if a matching analytics event could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent fetchByGtCD_E_E_First(
		java.util.Date createDate, java.lang.String elementKey,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator) {
		return getPersistence()
				   .fetchByGtCD_E_E_First(createDate, elementKey, type,
			orderByComparator);
	}

	/**
	* Returns the last analytics event in the ordered set where createDate &gt; &#63; and elementKey = &#63; and type = &#63;.
	*
	* @param createDate the create date
	* @param elementKey the element key
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching analytics event
	* @throws com.liferay.analytics.NoSuchEventException if a matching analytics event could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent findByGtCD_E_E_Last(
		java.util.Date createDate, java.lang.String elementKey,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator)
		throws com.liferay.analytics.NoSuchEventException {
		return getPersistence()
				   .findByGtCD_E_E_Last(createDate, elementKey, type,
			orderByComparator);
	}

	/**
	* Returns the last analytics event in the ordered set where createDate &gt; &#63; and elementKey = &#63; and type = &#63;.
	*
	* @param createDate the create date
	* @param elementKey the element key
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching analytics event, or <code>null</code> if a matching analytics event could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent fetchByGtCD_E_E_Last(
		java.util.Date createDate, java.lang.String elementKey,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator) {
		return getPersistence()
				   .fetchByGtCD_E_E_Last(createDate, elementKey, type,
			orderByComparator);
	}

	/**
	* Returns the analytics events before and after the current analytics event in the ordered set where createDate &gt; &#63; and elementKey = &#63; and type = &#63;.
	*
	* @param analyticsEventId the primary key of the current analytics event
	* @param createDate the create date
	* @param elementKey the element key
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next analytics event
	* @throws com.liferay.analytics.NoSuchEventException if a analytics event with the primary key could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent[] findByGtCD_E_E_PrevAndNext(
		long analyticsEventId, java.util.Date createDate,
		java.lang.String elementKey, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator)
		throws com.liferay.analytics.NoSuchEventException {
		return getPersistence()
				   .findByGtCD_E_E_PrevAndNext(analyticsEventId, createDate,
			elementKey, type, orderByComparator);
	}

	/**
	* Removes all the analytics events where createDate &gt; &#63; and elementKey = &#63; and type = &#63; from the database.
	*
	* @param createDate the create date
	* @param elementKey the element key
	* @param type the type
	*/
	public static void removeByGtCD_E_E(java.util.Date createDate,
		java.lang.String elementKey, java.lang.String type) {
		getPersistence().removeByGtCD_E_E(createDate, elementKey, type);
	}

	/**
	* Returns the number of analytics events where createDate &gt; &#63; and elementKey = &#63; and type = &#63;.
	*
	* @param createDate the create date
	* @param elementKey the element key
	* @param type the type
	* @return the number of matching analytics events
	*/
	public static int countByGtCD_E_E(java.util.Date createDate,
		java.lang.String elementKey, java.lang.String type) {
		return getPersistence().countByGtCD_E_E(createDate, elementKey, type);
	}

	/**
	* Returns all the analytics events where createDate &gt; &#63; and className = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param createDate the create date
	* @param className the class name
	* @param classPK the class p k
	* @param type the type
	* @return the matching analytics events
	*/
	public static java.util.List<com.liferay.analytics.model.AnalyticsEvent> findByGtCD_C_C_E(
		java.util.Date createDate, java.lang.String className, long classPK,
		java.lang.String type) {
		return getPersistence()
				   .findByGtCD_C_C_E(createDate, className, classPK, type);
	}

	/**
	* Returns a range of all the analytics events where createDate &gt; &#63; and className = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.analytics.model.impl.AnalyticsEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param createDate the create date
	* @param className the class name
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of analytics events
	* @param end the upper bound of the range of analytics events (not inclusive)
	* @return the range of matching analytics events
	*/
	public static java.util.List<com.liferay.analytics.model.AnalyticsEvent> findByGtCD_C_C_E(
		java.util.Date createDate, java.lang.String className, long classPK,
		java.lang.String type, int start, int end) {
		return getPersistence()
				   .findByGtCD_C_C_E(createDate, className, classPK, type,
			start, end);
	}

	/**
	* Returns an ordered range of all the analytics events where createDate &gt; &#63; and className = &#63; and classPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.analytics.model.impl.AnalyticsEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param createDate the create date
	* @param className the class name
	* @param classPK the class p k
	* @param type the type
	* @param start the lower bound of the range of analytics events
	* @param end the upper bound of the range of analytics events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching analytics events
	*/
	public static java.util.List<com.liferay.analytics.model.AnalyticsEvent> findByGtCD_C_C_E(
		java.util.Date createDate, java.lang.String className, long classPK,
		java.lang.String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator) {
		return getPersistence()
				   .findByGtCD_C_C_E(createDate, className, classPK, type,
			start, end, orderByComparator);
	}

	/**
	* Returns the first analytics event in the ordered set where createDate &gt; &#63; and className = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param createDate the create date
	* @param className the class name
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching analytics event
	* @throws com.liferay.analytics.NoSuchEventException if a matching analytics event could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent findByGtCD_C_C_E_First(
		java.util.Date createDate, java.lang.String className, long classPK,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator)
		throws com.liferay.analytics.NoSuchEventException {
		return getPersistence()
				   .findByGtCD_C_C_E_First(createDate, className, classPK,
			type, orderByComparator);
	}

	/**
	* Returns the first analytics event in the ordered set where createDate &gt; &#63; and className = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param createDate the create date
	* @param className the class name
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching analytics event, or <code>null</code> if a matching analytics event could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent fetchByGtCD_C_C_E_First(
		java.util.Date createDate, java.lang.String className, long classPK,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator) {
		return getPersistence()
				   .fetchByGtCD_C_C_E_First(createDate, className, classPK,
			type, orderByComparator);
	}

	/**
	* Returns the last analytics event in the ordered set where createDate &gt; &#63; and className = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param createDate the create date
	* @param className the class name
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching analytics event
	* @throws com.liferay.analytics.NoSuchEventException if a matching analytics event could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent findByGtCD_C_C_E_Last(
		java.util.Date createDate, java.lang.String className, long classPK,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator)
		throws com.liferay.analytics.NoSuchEventException {
		return getPersistence()
				   .findByGtCD_C_C_E_Last(createDate, className, classPK, type,
			orderByComparator);
	}

	/**
	* Returns the last analytics event in the ordered set where createDate &gt; &#63; and className = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param createDate the create date
	* @param className the class name
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching analytics event, or <code>null</code> if a matching analytics event could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent fetchByGtCD_C_C_E_Last(
		java.util.Date createDate, java.lang.String className, long classPK,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator) {
		return getPersistence()
				   .fetchByGtCD_C_C_E_Last(createDate, className, classPK,
			type, orderByComparator);
	}

	/**
	* Returns the analytics events before and after the current analytics event in the ordered set where createDate &gt; &#63; and className = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param analyticsEventId the primary key of the current analytics event
	* @param createDate the create date
	* @param className the class name
	* @param classPK the class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next analytics event
	* @throws com.liferay.analytics.NoSuchEventException if a analytics event with the primary key could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent[] findByGtCD_C_C_E_PrevAndNext(
		long analyticsEventId, java.util.Date createDate,
		java.lang.String className, long classPK, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator)
		throws com.liferay.analytics.NoSuchEventException {
		return getPersistence()
				   .findByGtCD_C_C_E_PrevAndNext(analyticsEventId, createDate,
			className, classPK, type, orderByComparator);
	}

	/**
	* Removes all the analytics events where createDate &gt; &#63; and className = &#63; and classPK = &#63; and type = &#63; from the database.
	*
	* @param createDate the create date
	* @param className the class name
	* @param classPK the class p k
	* @param type the type
	*/
	public static void removeByGtCD_C_C_E(java.util.Date createDate,
		java.lang.String className, long classPK, java.lang.String type) {
		getPersistence().removeByGtCD_C_C_E(createDate, className, classPK, type);
	}

	/**
	* Returns the number of analytics events where createDate &gt; &#63; and className = &#63; and classPK = &#63; and type = &#63;.
	*
	* @param createDate the create date
	* @param className the class name
	* @param classPK the class p k
	* @param type the type
	* @return the number of matching analytics events
	*/
	public static int countByGtCD_C_C_E(java.util.Date createDate,
		java.lang.String className, long classPK, java.lang.String type) {
		return getPersistence()
				   .countByGtCD_C_C_E(createDate, className, classPK, type);
	}

	/**
	* Returns all the analytics events where createDate &gt; &#63; and className = &#63; and classPK = &#63; and referrerClassName = &#63; and referrerClassPK = &#63; and type = &#63;.
	*
	* @param createDate the create date
	* @param className the class name
	* @param classPK the class p k
	* @param referrerClassName the referrer class name
	* @param referrerClassPK the referrer class p k
	* @param type the type
	* @return the matching analytics events
	*/
	public static java.util.List<com.liferay.analytics.model.AnalyticsEvent> findByGtCD_C_C_R_R_E(
		java.util.Date createDate, java.lang.String className, long classPK,
		java.lang.String referrerClassName, long referrerClassPK,
		java.lang.String type) {
		return getPersistence()
				   .findByGtCD_C_C_R_R_E(createDate, className, classPK,
			referrerClassName, referrerClassPK, type);
	}

	/**
	* Returns a range of all the analytics events where createDate &gt; &#63; and className = &#63; and classPK = &#63; and referrerClassName = &#63; and referrerClassPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.analytics.model.impl.AnalyticsEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param createDate the create date
	* @param className the class name
	* @param classPK the class p k
	* @param referrerClassName the referrer class name
	* @param referrerClassPK the referrer class p k
	* @param type the type
	* @param start the lower bound of the range of analytics events
	* @param end the upper bound of the range of analytics events (not inclusive)
	* @return the range of matching analytics events
	*/
	public static java.util.List<com.liferay.analytics.model.AnalyticsEvent> findByGtCD_C_C_R_R_E(
		java.util.Date createDate, java.lang.String className, long classPK,
		java.lang.String referrerClassName, long referrerClassPK,
		java.lang.String type, int start, int end) {
		return getPersistence()
				   .findByGtCD_C_C_R_R_E(createDate, className, classPK,
			referrerClassName, referrerClassPK, type, start, end);
	}

	/**
	* Returns an ordered range of all the analytics events where createDate &gt; &#63; and className = &#63; and classPK = &#63; and referrerClassName = &#63; and referrerClassPK = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.analytics.model.impl.AnalyticsEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param createDate the create date
	* @param className the class name
	* @param classPK the class p k
	* @param referrerClassName the referrer class name
	* @param referrerClassPK the referrer class p k
	* @param type the type
	* @param start the lower bound of the range of analytics events
	* @param end the upper bound of the range of analytics events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching analytics events
	*/
	public static java.util.List<com.liferay.analytics.model.AnalyticsEvent> findByGtCD_C_C_R_R_E(
		java.util.Date createDate, java.lang.String className, long classPK,
		java.lang.String referrerClassName, long referrerClassPK,
		java.lang.String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator) {
		return getPersistence()
				   .findByGtCD_C_C_R_R_E(createDate, className, classPK,
			referrerClassName, referrerClassPK, type, start, end,
			orderByComparator);
	}

	/**
	* Returns the first analytics event in the ordered set where createDate &gt; &#63; and className = &#63; and classPK = &#63; and referrerClassName = &#63; and referrerClassPK = &#63; and type = &#63;.
	*
	* @param createDate the create date
	* @param className the class name
	* @param classPK the class p k
	* @param referrerClassName the referrer class name
	* @param referrerClassPK the referrer class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching analytics event
	* @throws com.liferay.analytics.NoSuchEventException if a matching analytics event could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent findByGtCD_C_C_R_R_E_First(
		java.util.Date createDate, java.lang.String className, long classPK,
		java.lang.String referrerClassName, long referrerClassPK,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator)
		throws com.liferay.analytics.NoSuchEventException {
		return getPersistence()
				   .findByGtCD_C_C_R_R_E_First(createDate, className, classPK,
			referrerClassName, referrerClassPK, type, orderByComparator);
	}

	/**
	* Returns the first analytics event in the ordered set where createDate &gt; &#63; and className = &#63; and classPK = &#63; and referrerClassName = &#63; and referrerClassPK = &#63; and type = &#63;.
	*
	* @param createDate the create date
	* @param className the class name
	* @param classPK the class p k
	* @param referrerClassName the referrer class name
	* @param referrerClassPK the referrer class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching analytics event, or <code>null</code> if a matching analytics event could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent fetchByGtCD_C_C_R_R_E_First(
		java.util.Date createDate, java.lang.String className, long classPK,
		java.lang.String referrerClassName, long referrerClassPK,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator) {
		return getPersistence()
				   .fetchByGtCD_C_C_R_R_E_First(createDate, className, classPK,
			referrerClassName, referrerClassPK, type, orderByComparator);
	}

	/**
	* Returns the last analytics event in the ordered set where createDate &gt; &#63; and className = &#63; and classPK = &#63; and referrerClassName = &#63; and referrerClassPK = &#63; and type = &#63;.
	*
	* @param createDate the create date
	* @param className the class name
	* @param classPK the class p k
	* @param referrerClassName the referrer class name
	* @param referrerClassPK the referrer class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching analytics event
	* @throws com.liferay.analytics.NoSuchEventException if a matching analytics event could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent findByGtCD_C_C_R_R_E_Last(
		java.util.Date createDate, java.lang.String className, long classPK,
		java.lang.String referrerClassName, long referrerClassPK,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator)
		throws com.liferay.analytics.NoSuchEventException {
		return getPersistence()
				   .findByGtCD_C_C_R_R_E_Last(createDate, className, classPK,
			referrerClassName, referrerClassPK, type, orderByComparator);
	}

	/**
	* Returns the last analytics event in the ordered set where createDate &gt; &#63; and className = &#63; and classPK = &#63; and referrerClassName = &#63; and referrerClassPK = &#63; and type = &#63;.
	*
	* @param createDate the create date
	* @param className the class name
	* @param classPK the class p k
	* @param referrerClassName the referrer class name
	* @param referrerClassPK the referrer class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching analytics event, or <code>null</code> if a matching analytics event could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent fetchByGtCD_C_C_R_R_E_Last(
		java.util.Date createDate, java.lang.String className, long classPK,
		java.lang.String referrerClassName, long referrerClassPK,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator) {
		return getPersistence()
				   .fetchByGtCD_C_C_R_R_E_Last(createDate, className, classPK,
			referrerClassName, referrerClassPK, type, orderByComparator);
	}

	/**
	* Returns the analytics events before and after the current analytics event in the ordered set where createDate &gt; &#63; and className = &#63; and classPK = &#63; and referrerClassName = &#63; and referrerClassPK = &#63; and type = &#63;.
	*
	* @param analyticsEventId the primary key of the current analytics event
	* @param createDate the create date
	* @param className the class name
	* @param classPK the class p k
	* @param referrerClassName the referrer class name
	* @param referrerClassPK the referrer class p k
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next analytics event
	* @throws com.liferay.analytics.NoSuchEventException if a analytics event with the primary key could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent[] findByGtCD_C_C_R_R_E_PrevAndNext(
		long analyticsEventId, java.util.Date createDate,
		java.lang.String className, long classPK,
		java.lang.String referrerClassName, long referrerClassPK,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator)
		throws com.liferay.analytics.NoSuchEventException {
		return getPersistence()
				   .findByGtCD_C_C_R_R_E_PrevAndNext(analyticsEventId,
			createDate, className, classPK, referrerClassName, referrerClassPK,
			type, orderByComparator);
	}

	/**
	* Removes all the analytics events where createDate &gt; &#63; and className = &#63; and classPK = &#63; and referrerClassName = &#63; and referrerClassPK = &#63; and type = &#63; from the database.
	*
	* @param createDate the create date
	* @param className the class name
	* @param classPK the class p k
	* @param referrerClassName the referrer class name
	* @param referrerClassPK the referrer class p k
	* @param type the type
	*/
	public static void removeByGtCD_C_C_R_R_E(java.util.Date createDate,
		java.lang.String className, long classPK,
		java.lang.String referrerClassName, long referrerClassPK,
		java.lang.String type) {
		getPersistence()
			.removeByGtCD_C_C_R_R_E(createDate, className, classPK,
			referrerClassName, referrerClassPK, type);
	}

	/**
	* Returns the number of analytics events where createDate &gt; &#63; and className = &#63; and classPK = &#63; and referrerClassName = &#63; and referrerClassPK = &#63; and type = &#63;.
	*
	* @param createDate the create date
	* @param className the class name
	* @param classPK the class p k
	* @param referrerClassName the referrer class name
	* @param referrerClassPK the referrer class p k
	* @param type the type
	* @return the number of matching analytics events
	*/
	public static int countByGtCD_C_C_R_R_E(java.util.Date createDate,
		java.lang.String className, long classPK,
		java.lang.String referrerClassName, long referrerClassPK,
		java.lang.String type) {
		return getPersistence()
				   .countByGtCD_C_C_R_R_E(createDate, className, classPK,
			referrerClassName, referrerClassPK, type);
	}

	/**
	* Returns all the analytics events where createDate &gt; &#63; and referrerClassName = &#63; and referrerClassPK = &#63; and elementKey = &#63; and type = &#63;.
	*
	* @param createDate the create date
	* @param referrerClassName the referrer class name
	* @param referrerClassPK the referrer class p k
	* @param elementKey the element key
	* @param type the type
	* @return the matching analytics events
	*/
	public static java.util.List<com.liferay.analytics.model.AnalyticsEvent> findByGtCD_R_R_E_E(
		java.util.Date createDate, java.lang.String referrerClassName,
		long referrerClassPK, java.lang.String elementKey, java.lang.String type) {
		return getPersistence()
				   .findByGtCD_R_R_E_E(createDate, referrerClassName,
			referrerClassPK, elementKey, type);
	}

	/**
	* Returns a range of all the analytics events where createDate &gt; &#63; and referrerClassName = &#63; and referrerClassPK = &#63; and elementKey = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.analytics.model.impl.AnalyticsEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param createDate the create date
	* @param referrerClassName the referrer class name
	* @param referrerClassPK the referrer class p k
	* @param elementKey the element key
	* @param type the type
	* @param start the lower bound of the range of analytics events
	* @param end the upper bound of the range of analytics events (not inclusive)
	* @return the range of matching analytics events
	*/
	public static java.util.List<com.liferay.analytics.model.AnalyticsEvent> findByGtCD_R_R_E_E(
		java.util.Date createDate, java.lang.String referrerClassName,
		long referrerClassPK, java.lang.String elementKey,
		java.lang.String type, int start, int end) {
		return getPersistence()
				   .findByGtCD_R_R_E_E(createDate, referrerClassName,
			referrerClassPK, elementKey, type, start, end);
	}

	/**
	* Returns an ordered range of all the analytics events where createDate &gt; &#63; and referrerClassName = &#63; and referrerClassPK = &#63; and elementKey = &#63; and type = &#63;.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.analytics.model.impl.AnalyticsEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param createDate the create date
	* @param referrerClassName the referrer class name
	* @param referrerClassPK the referrer class p k
	* @param elementKey the element key
	* @param type the type
	* @param start the lower bound of the range of analytics events
	* @param end the upper bound of the range of analytics events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching analytics events
	*/
	public static java.util.List<com.liferay.analytics.model.AnalyticsEvent> findByGtCD_R_R_E_E(
		java.util.Date createDate, java.lang.String referrerClassName,
		long referrerClassPK, java.lang.String elementKey,
		java.lang.String type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator) {
		return getPersistence()
				   .findByGtCD_R_R_E_E(createDate, referrerClassName,
			referrerClassPK, elementKey, type, start, end, orderByComparator);
	}

	/**
	* Returns the first analytics event in the ordered set where createDate &gt; &#63; and referrerClassName = &#63; and referrerClassPK = &#63; and elementKey = &#63; and type = &#63;.
	*
	* @param createDate the create date
	* @param referrerClassName the referrer class name
	* @param referrerClassPK the referrer class p k
	* @param elementKey the element key
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching analytics event
	* @throws com.liferay.analytics.NoSuchEventException if a matching analytics event could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent findByGtCD_R_R_E_E_First(
		java.util.Date createDate, java.lang.String referrerClassName,
		long referrerClassPK, java.lang.String elementKey,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator)
		throws com.liferay.analytics.NoSuchEventException {
		return getPersistence()
				   .findByGtCD_R_R_E_E_First(createDate, referrerClassName,
			referrerClassPK, elementKey, type, orderByComparator);
	}

	/**
	* Returns the first analytics event in the ordered set where createDate &gt; &#63; and referrerClassName = &#63; and referrerClassPK = &#63; and elementKey = &#63; and type = &#63;.
	*
	* @param createDate the create date
	* @param referrerClassName the referrer class name
	* @param referrerClassPK the referrer class p k
	* @param elementKey the element key
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the first matching analytics event, or <code>null</code> if a matching analytics event could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent fetchByGtCD_R_R_E_E_First(
		java.util.Date createDate, java.lang.String referrerClassName,
		long referrerClassPK, java.lang.String elementKey,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator) {
		return getPersistence()
				   .fetchByGtCD_R_R_E_E_First(createDate, referrerClassName,
			referrerClassPK, elementKey, type, orderByComparator);
	}

	/**
	* Returns the last analytics event in the ordered set where createDate &gt; &#63; and referrerClassName = &#63; and referrerClassPK = &#63; and elementKey = &#63; and type = &#63;.
	*
	* @param createDate the create date
	* @param referrerClassName the referrer class name
	* @param referrerClassPK the referrer class p k
	* @param elementKey the element key
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching analytics event
	* @throws com.liferay.analytics.NoSuchEventException if a matching analytics event could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent findByGtCD_R_R_E_E_Last(
		java.util.Date createDate, java.lang.String referrerClassName,
		long referrerClassPK, java.lang.String elementKey,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator)
		throws com.liferay.analytics.NoSuchEventException {
		return getPersistence()
				   .findByGtCD_R_R_E_E_Last(createDate, referrerClassName,
			referrerClassPK, elementKey, type, orderByComparator);
	}

	/**
	* Returns the last analytics event in the ordered set where createDate &gt; &#63; and referrerClassName = &#63; and referrerClassPK = &#63; and elementKey = &#63; and type = &#63;.
	*
	* @param createDate the create date
	* @param referrerClassName the referrer class name
	* @param referrerClassPK the referrer class p k
	* @param elementKey the element key
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the last matching analytics event, or <code>null</code> if a matching analytics event could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent fetchByGtCD_R_R_E_E_Last(
		java.util.Date createDate, java.lang.String referrerClassName,
		long referrerClassPK, java.lang.String elementKey,
		java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator) {
		return getPersistence()
				   .fetchByGtCD_R_R_E_E_Last(createDate, referrerClassName,
			referrerClassPK, elementKey, type, orderByComparator);
	}

	/**
	* Returns the analytics events before and after the current analytics event in the ordered set where createDate &gt; &#63; and referrerClassName = &#63; and referrerClassPK = &#63; and elementKey = &#63; and type = &#63;.
	*
	* @param analyticsEventId the primary key of the current analytics event
	* @param createDate the create date
	* @param referrerClassName the referrer class name
	* @param referrerClassPK the referrer class p k
	* @param elementKey the element key
	* @param type the type
	* @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	* @return the previous, current, and next analytics event
	* @throws com.liferay.analytics.NoSuchEventException if a analytics event with the primary key could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent[] findByGtCD_R_R_E_E_PrevAndNext(
		long analyticsEventId, java.util.Date createDate,
		java.lang.String referrerClassName, long referrerClassPK,
		java.lang.String elementKey, java.lang.String type,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator)
		throws com.liferay.analytics.NoSuchEventException {
		return getPersistence()
				   .findByGtCD_R_R_E_E_PrevAndNext(analyticsEventId,
			createDate, referrerClassName, referrerClassPK, elementKey, type,
			orderByComparator);
	}

	/**
	* Removes all the analytics events where createDate &gt; &#63; and referrerClassName = &#63; and referrerClassPK = &#63; and elementKey = &#63; and type = &#63; from the database.
	*
	* @param createDate the create date
	* @param referrerClassName the referrer class name
	* @param referrerClassPK the referrer class p k
	* @param elementKey the element key
	* @param type the type
	*/
	public static void removeByGtCD_R_R_E_E(java.util.Date createDate,
		java.lang.String referrerClassName, long referrerClassPK,
		java.lang.String elementKey, java.lang.String type) {
		getPersistence()
			.removeByGtCD_R_R_E_E(createDate, referrerClassName,
			referrerClassPK, elementKey, type);
	}

	/**
	* Returns the number of analytics events where createDate &gt; &#63; and referrerClassName = &#63; and referrerClassPK = &#63; and elementKey = &#63; and type = &#63;.
	*
	* @param createDate the create date
	* @param referrerClassName the referrer class name
	* @param referrerClassPK the referrer class p k
	* @param elementKey the element key
	* @param type the type
	* @return the number of matching analytics events
	*/
	public static int countByGtCD_R_R_E_E(java.util.Date createDate,
		java.lang.String referrerClassName, long referrerClassPK,
		java.lang.String elementKey, java.lang.String type) {
		return getPersistence()
				   .countByGtCD_R_R_E_E(createDate, referrerClassName,
			referrerClassPK, elementKey, type);
	}

	/**
	* Caches the analytics event in the entity cache if it is enabled.
	*
	* @param analyticsEvent the analytics event
	*/
	public static void cacheResult(
		com.liferay.analytics.model.AnalyticsEvent analyticsEvent) {
		getPersistence().cacheResult(analyticsEvent);
	}

	/**
	* Caches the analytics events in the entity cache if it is enabled.
	*
	* @param analyticsEvents the analytics events
	*/
	public static void cacheResult(
		java.util.List<com.liferay.analytics.model.AnalyticsEvent> analyticsEvents) {
		getPersistence().cacheResult(analyticsEvents);
	}

	/**
	* Creates a new analytics event with the primary key. Does not add the analytics event to the database.
	*
	* @param analyticsEventId the primary key for the new analytics event
	* @return the new analytics event
	*/
	public static com.liferay.analytics.model.AnalyticsEvent create(
		long analyticsEventId) {
		return getPersistence().create(analyticsEventId);
	}

	/**
	* Removes the analytics event with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param analyticsEventId the primary key of the analytics event
	* @return the analytics event that was removed
	* @throws com.liferay.analytics.NoSuchEventException if a analytics event with the primary key could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent remove(
		long analyticsEventId)
		throws com.liferay.analytics.NoSuchEventException {
		return getPersistence().remove(analyticsEventId);
	}

	public static com.liferay.analytics.model.AnalyticsEvent updateImpl(
		com.liferay.analytics.model.AnalyticsEvent analyticsEvent) {
		return getPersistence().updateImpl(analyticsEvent);
	}

	/**
	* Returns the analytics event with the primary key or throws a {@link com.liferay.analytics.NoSuchEventException} if it could not be found.
	*
	* @param analyticsEventId the primary key of the analytics event
	* @return the analytics event
	* @throws com.liferay.analytics.NoSuchEventException if a analytics event with the primary key could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent findByPrimaryKey(
		long analyticsEventId)
		throws com.liferay.analytics.NoSuchEventException {
		return getPersistence().findByPrimaryKey(analyticsEventId);
	}

	/**
	* Returns the analytics event with the primary key or returns <code>null</code> if it could not be found.
	*
	* @param analyticsEventId the primary key of the analytics event
	* @return the analytics event, or <code>null</code> if a analytics event with the primary key could not be found
	*/
	public static com.liferay.analytics.model.AnalyticsEvent fetchByPrimaryKey(
		long analyticsEventId) {
		return getPersistence().fetchByPrimaryKey(analyticsEventId);
	}

	public static java.util.Map<java.io.Serializable, com.liferay.analytics.model.AnalyticsEvent> fetchByPrimaryKeys(
		java.util.Set<java.io.Serializable> primaryKeys) {
		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	* Returns all the analytics events.
	*
	* @return the analytics events
	*/
	public static java.util.List<com.liferay.analytics.model.AnalyticsEvent> findAll() {
		return getPersistence().findAll();
	}

	/**
	* Returns a range of all the analytics events.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.analytics.model.impl.AnalyticsEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of analytics events
	* @param end the upper bound of the range of analytics events (not inclusive)
	* @return the range of analytics events
	*/
	public static java.util.List<com.liferay.analytics.model.AnalyticsEvent> findAll(
		int start, int end) {
		return getPersistence().findAll(start, end);
	}

	/**
	* Returns an ordered range of all the analytics events.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.analytics.model.impl.AnalyticsEventModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of analytics events
	* @param end the upper bound of the range of analytics events (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of analytics events
	*/
	public static java.util.List<com.liferay.analytics.model.AnalyticsEvent> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.analytics.model.AnalyticsEvent> orderByComparator) {
		return getPersistence().findAll(start, end, orderByComparator);
	}

	/**
	* Removes all the analytics events from the database.
	*/
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	* Returns the number of analytics events.
	*
	* @return the number of analytics events
	*/
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static AnalyticsEventPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setPersistence(AnalyticsEventPersistence persistence) {
	}

	private static ServiceTracker<AnalyticsEventPersistence, AnalyticsEventPersistence> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(AnalyticsEventUtil.class);

		_serviceTracker = new ServiceTracker<AnalyticsEventPersistence, AnalyticsEventPersistence>(bundle.getBundleContext(),
				AnalyticsEventPersistence.class, null);

		_serviceTracker.open();
	}
}