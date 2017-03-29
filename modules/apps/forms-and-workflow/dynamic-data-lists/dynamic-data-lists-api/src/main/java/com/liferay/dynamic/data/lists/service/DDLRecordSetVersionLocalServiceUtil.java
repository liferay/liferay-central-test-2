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

package com.liferay.dynamic.data.lists.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;

import org.osgi.util.tracker.ServiceTracker;

/**
 * Provides the local service utility for DDLRecordSetVersion. This utility wraps
 * {@link com.liferay.dynamic.data.lists.service.impl.DDLRecordSetVersionLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordSetVersionLocalService
 * @see com.liferay.dynamic.data.lists.service.base.DDLRecordSetVersionLocalServiceBaseImpl
 * @see com.liferay.dynamic.data.lists.service.impl.DDLRecordSetVersionLocalServiceImpl
 * @generated
 */
@ProviderType
public class DDLRecordSetVersionLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.dynamic.data.lists.service.impl.DDLRecordSetVersionLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the ddl record set version to the database. Also notifies the appropriate model listeners.
	*
	* @param ddlRecordSetVersion the ddl record set version
	* @return the ddl record set version that was added
	*/
	public static com.liferay.dynamic.data.lists.model.DDLRecordSetVersion addDDLRecordSetVersion(
		com.liferay.dynamic.data.lists.model.DDLRecordSetVersion ddlRecordSetVersion) {
		return getService().addDDLRecordSetVersion(ddlRecordSetVersion);
	}

	/**
	* Creates a new ddl record set version with the primary key. Does not add the ddl record set version to the database.
	*
	* @param recordSetVersionId the primary key for the new ddl record set version
	* @return the new ddl record set version
	*/
	public static com.liferay.dynamic.data.lists.model.DDLRecordSetVersion createDDLRecordSetVersion(
		long recordSetVersionId) {
		return getService().createDDLRecordSetVersion(recordSetVersionId);
	}

	/**
	* Deletes the ddl record set version from the database. Also notifies the appropriate model listeners.
	*
	* @param ddlRecordSetVersion the ddl record set version
	* @return the ddl record set version that was removed
	*/
	public static com.liferay.dynamic.data.lists.model.DDLRecordSetVersion deleteDDLRecordSetVersion(
		com.liferay.dynamic.data.lists.model.DDLRecordSetVersion ddlRecordSetVersion) {
		return getService().deleteDDLRecordSetVersion(ddlRecordSetVersion);
	}

	/**
	* Deletes the ddl record set version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param recordSetVersionId the primary key of the ddl record set version
	* @return the ddl record set version that was removed
	* @throws PortalException if a ddl record set version with the primary key could not be found
	*/
	public static com.liferay.dynamic.data.lists.model.DDLRecordSetVersion deleteDDLRecordSetVersion(
		long recordSetVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteDDLRecordSetVersion(recordSetVersionId);
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecordSetVersion fetchDDLRecordSetVersion(
		long recordSetVersionId) {
		return getService().fetchDDLRecordSetVersion(recordSetVersionId);
	}

	/**
	* Returns the ddl record set version with the primary key.
	*
	* @param recordSetVersionId the primary key of the ddl record set version
	* @return the ddl record set version
	* @throws PortalException if a ddl record set version with the primary key could not be found
	*/
	public static com.liferay.dynamic.data.lists.model.DDLRecordSetVersion getDDLRecordSetVersion(
		long recordSetVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDDLRecordSetVersion(recordSetVersionId);
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecordSetVersion getLatestRecordSetVersion(
		long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getLatestRecordSetVersion(recordSetId);
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecordSetVersion getRecordSetVersion(
		long recordSetId, java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getRecordSetVersion(recordSetId, version);
	}

	public static com.liferay.dynamic.data.lists.model.DDLRecordSetVersion getRecordSetVersion(
		long recordSetVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getRecordSetVersion(recordSetVersionId);
	}

	/**
	* Updates the ddl record set version in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddlRecordSetVersion the ddl record set version
	* @return the ddl record set version that was updated
	*/
	public static com.liferay.dynamic.data.lists.model.DDLRecordSetVersion updateDDLRecordSetVersion(
		com.liferay.dynamic.data.lists.model.DDLRecordSetVersion ddlRecordSetVersion) {
		return getService().updateDDLRecordSetVersion(ddlRecordSetVersion);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	public static com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		return getService().getIndexableActionableDynamicQuery();
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.kernel.model.PersistedModel deletePersistedModel(
		com.liferay.portal.kernel.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns the number of ddl record set versions.
	*
	* @return the number of ddl record set versions
	*/
	public static int getDDLRecordSetVersionsCount() {
		return getService().getDDLRecordSetVersionsCount();
	}

	public static int getRecordSetVersionsCount(long recordSetId) {
		return getService().getRecordSetVersionsCount(recordSetId);
	}

	/**
	* Returns the OSGi service identifier.
	*
	* @return the OSGi service identifier
	*/
	public static java.lang.String getOSGiServiceIdentifier() {
		return getService().getOSGiServiceIdentifier();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
	* @return the matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.lists.model.impl.DDLRecordSetVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @return the range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.lists.model.impl.DDLRecordSetVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	*/
	public static <T> java.util.List<T> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator<T> orderByComparator) {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Returns a range of all the ddl record set versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.dynamic.data.lists.model.impl.DDLRecordSetVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of ddl record set versions
	* @param end the upper bound of the range of ddl record set versions (not inclusive)
	* @return the range of ddl record set versions
	*/
	public static java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordSetVersion> getDDLRecordSetVersions(
		int start, int end) {
		return getService().getDDLRecordSetVersions(start, end);
	}

	public static java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordSetVersion> getRecordSetVersions(
		long recordSetId) {
		return getService().getRecordSetVersions(recordSetId);
	}

	public static java.util.List<com.liferay.dynamic.data.lists.model.DDLRecordSetVersion> getRecordSetVersions(
		long recordSetId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.dynamic.data.lists.model.DDLRecordSetVersion> orderByComparator) {
		return getService()
				   .getRecordSetVersions(recordSetId, start, end,
			orderByComparator);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery) {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows matching the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows matching the dynamic query
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection) {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static void deleteByRecordSetId(long recordSetId) {
		getService().deleteByRecordSetId(recordSetId);
	}

	public static DDLRecordSetVersionLocalService getService() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker<DDLRecordSetVersionLocalService, DDLRecordSetVersionLocalService> _serviceTracker =
		ServiceTrackerFactory.open(DDLRecordSetVersionLocalService.class);
}