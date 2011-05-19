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

package com.liferay.portlet.dynamicdatalists.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the d d l record local service. This utility wraps {@link com.liferay.portlet.dynamicdatalists.service.impl.DDLRecordLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDLRecordLocalService
 * @see com.liferay.portlet.dynamicdatalists.service.base.DDLRecordLocalServiceBaseImpl
 * @see com.liferay.portlet.dynamicdatalists.service.impl.DDLRecordLocalServiceImpl
 * @generated
 */
public class DDLRecordLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.dynamicdatalists.service.impl.DDLRecordLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the d d l record to the database. Also notifies the appropriate model listeners.
	*
	* @param ddlRecord the d d l record to add
	* @return the d d l record that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord addDDLRecord(
		com.liferay.portlet.dynamicdatalists.model.DDLRecord ddlRecord)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addDDLRecord(ddlRecord);
	}

	/**
	* Creates a new d d l record with the primary key. Does not add the d d l record to the database.
	*
	* @param recordId the primary key for the new d d l record
	* @return the new d d l record
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord createDDLRecord(
		long recordId) {
		return getService().createDDLRecord(recordId);
	}

	/**
	* Deletes the d d l record with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param recordId the primary key of the d d l record to delete
	* @throws PortalException if a d d l record with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteDDLRecord(long recordId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteDDLRecord(recordId);
	}

	/**
	* Deletes the d d l record from the database. Also notifies the appropriate model listeners.
	*
	* @param ddlRecord the d d l record to delete
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteDDLRecord(
		com.liferay.portlet.dynamicdatalists.model.DDLRecord ddlRecord)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteDDLRecord(ddlRecord);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	/**
	* Performs a dynamic query on the database and returns a range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @return the range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	/**
	* Performs a dynamic query on the database and returns an ordered range of the matching rows.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param dynamicQuery the dynamic query to search with
	* @param start the lower bound of the range of model instances to return
	* @param end the upper bound of the range of model instances to return (not inclusive)
	* @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	* @return the ordered range of matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	/**
	* Counts the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the d d l record with the primary key.
	*
	* @param recordId the primary key of the d d l record to get
	* @return the d d l record
	* @throws PortalException if a d d l record with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord getDDLRecord(
		long recordId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDLRecord(recordId);
	}

	/**
	* Gets the d d l record with the UUID and group id.
	*
	* @param uuid the UUID of d d l record to get
	* @param groupId the group id of the d d l record to get
	* @return the d d l record
	* @throws PortalException if a d d l record with the UUID and group id could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord getDDLRecordByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDLRecordByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Gets a range of all the d d l records.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d d l records to return
	* @param end the upper bound of the range of d d l records to return (not inclusive)
	* @return the range of d d l records
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> getDDLRecords(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDLRecords(start, end);
	}

	/**
	* Gets the number of d d l records.
	*
	* @return the number of d d l records
	* @throws SystemException if a system exception occurred
	*/
	public static int getDDLRecordsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getDDLRecordsCount();
	}

	/**
	* Updates the d d l record in the database. Also notifies the appropriate model listeners.
	*
	* @param ddlRecord the d d l record to update
	* @return the d d l record that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord updateDDLRecord(
		com.liferay.portlet.dynamicdatalists.model.DDLRecord ddlRecord)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateDDLRecord(ddlRecord);
	}

	/**
	* Updates the d d l record in the database. Also notifies the appropriate model listeners.
	*
	* @param ddlRecord the d d l record to update
	* @param merge whether to merge the d d l record with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the d d l record that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord updateDDLRecord(
		com.liferay.portlet.dynamicdatalists.model.DDLRecord ddlRecord,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateDDLRecord(ddlRecord, merge);
	}

	/**
	* Gets the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public static java.lang.String getBeanIdentifier() {
		return getService().getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord addRecord(
		long userId, long groupId, long recordSetId,
		com.liferay.portlet.dynamicdatamapping.storage.Fields fields,
		int displayIndex,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addRecord(userId, groupId, recordSetId, fields,
			displayIndex, serviceContext);
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord addRecord(
		long userId, long groupId, long recordSetId,
		java.util.Map<java.lang.String, java.io.Serializable> fieldsMap,
		int displayIndex,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addRecord(userId, groupId, recordSetId, fieldsMap,
			displayIndex, serviceContext);
	}

	public static void deleteRecord(
		com.liferay.portlet.dynamicdatalists.model.DDLRecord record)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRecord(record);
	}

	public static void deleteRecord(long recordId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRecord(recordId);
	}

	public static void deleteRecords(long recordSetId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRecords(recordSetId);
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord getRecord(
		long recordId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getRecord(recordId);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> getRecords(
		long recordSetId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRecords(recordSetId);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatalists.model.DDLRecord> getRecords(
		long recordSetId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getRecords(recordSetId, status, start, end,
			orderByComparator);
	}

	public static int getRecordsCount(long recordSetId, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRecordsCount(recordSetId, status);
	}

	public static void updateAsset(long userId,
		com.liferay.portlet.dynamicdatalists.model.DDLRecord record,
		java.util.Locale locale, long[] assetCategoryIds,
		java.lang.String[] assetTagNames)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService()
			.updateAsset(userId, record, locale, assetCategoryIds, assetTagNames);
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord updateRecord(
		long userId, long recordId,
		com.liferay.portlet.dynamicdatamapping.storage.Fields fields,
		int displayIndex, boolean mergeFields,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateRecord(userId, recordId, fields, displayIndex,
			mergeFields, serviceContext);
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord updateRecord(
		long userId, long recordId,
		java.util.Map<java.lang.String, java.io.Serializable> fieldsMap,
		int displayIndex, boolean mergeFields,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateRecord(userId, recordId, fieldsMap, displayIndex,
			mergeFields, serviceContext);
	}

	public static com.liferay.portlet.dynamicdatalists.model.DDLRecord updateStatus(
		long userId, long recordId, int status,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateStatus(userId, recordId, status, serviceContext);
	}

	public static DDLRecordLocalService getService() {
		if (_service == null) {
			_service = (DDLRecordLocalService)PortalBeanLocatorUtil.locate(DDLRecordLocalService.class.getName());

			ReferenceRegistry.registerReference(DDLRecordLocalServiceUtil.class,
				"_service");
			MethodCache.remove(DDLRecordLocalService.class);
		}

		return _service;
	}

	public void setService(DDLRecordLocalService service) {
		MethodCache.remove(DDLRecordLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(DDLRecordLocalServiceUtil.class,
			"_service");
		MethodCache.remove(DDLRecordLocalService.class);
	}

	private static DDLRecordLocalService _service;
}