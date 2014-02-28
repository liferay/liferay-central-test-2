/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for ExportImportConfiguration. This utility wraps
 * {@link com.liferay.portal.service.impl.ExportImportConfigurationLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see ExportImportConfigurationLocalService
 * @see com.liferay.portal.service.base.ExportImportConfigurationLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.ExportImportConfigurationLocalServiceImpl
 * @generated
 */
@ProviderType
public class ExportImportConfigurationLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.ExportImportConfigurationLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the export import configuration to the database. Also notifies the appropriate model listeners.
	*
	* @param exportImportConfiguration the export import configuration
	* @return the export import configuration that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ExportImportConfiguration addExportImportConfiguration(
		com.liferay.portal.model.ExportImportConfiguration exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addExportImportConfiguration(exportImportConfiguration);
	}

	/**
	* Creates a new export import configuration with the primary key. Does not add the export import configuration to the database.
	*
	* @param exportImportConfigurationId the primary key for the new export import configuration
	* @return the new export import configuration
	*/
	public static com.liferay.portal.model.ExportImportConfiguration createExportImportConfiguration(
		long exportImportConfigurationId) {
		return getService()
				   .createExportImportConfiguration(exportImportConfigurationId);
	}

	/**
	* Deletes the export import configuration with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param exportImportConfigurationId the primary key of the export import configuration
	* @return the export import configuration that was removed
	* @throws PortalException if a export import configuration with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ExportImportConfiguration deleteExportImportConfiguration(
		long exportImportConfigurationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .deleteExportImportConfiguration(exportImportConfigurationId);
	}

	/**
	* Deletes the export import configuration from the database. Also notifies the appropriate model listeners.
	*
	* @param exportImportConfiguration the export import configuration
	* @return the export import configuration that was removed
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ExportImportConfiguration deleteExportImportConfiguration(
		com.liferay.portal.model.ExportImportConfiguration exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .deleteExportImportConfiguration(exportImportConfiguration);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ExportImportConfigurationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ExportImportConfigurationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param dynamicQuery the dynamic query
	* @param start the lower bound of the range of model instances
	* @param end the upper bound of the range of model instances (not inclusive)
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
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	/**
	* Returns the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query
	* @param projection the projection to apply to the query
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery,
		com.liferay.portal.kernel.dao.orm.Projection projection)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery, projection);
	}

	public static com.liferay.portal.model.ExportImportConfiguration fetchExportImportConfiguration(
		long exportImportConfigurationId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .fetchExportImportConfiguration(exportImportConfigurationId);
	}

	/**
	* Returns the export import configuration with the primary key.
	*
	* @param exportImportConfigurationId the primary key of the export import configuration
	* @return the export import configuration
	* @throws PortalException if a export import configuration with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ExportImportConfiguration getExportImportConfiguration(
		long exportImportConfigurationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getExportImportConfiguration(exportImportConfigurationId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	/**
	* Returns a range of all the export import configurations.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portal.model.impl.ExportImportConfigurationModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of export import configurations
	* @param end the upper bound of the range of export import configurations (not inclusive)
	* @return the range of export import configurations
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.ExportImportConfiguration> getExportImportConfigurations(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getExportImportConfigurations(start, end);
	}

	/**
	* Returns the number of export import configurations.
	*
	* @return the number of export import configurations
	* @throws SystemException if a system exception occurred
	*/
	public static int getExportImportConfigurationsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getExportImportConfigurationsCount();
	}

	/**
	* Updates the export import configuration in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param exportImportConfiguration the export import configuration
	* @return the export import configuration that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.ExportImportConfiguration updateExportImportConfiguration(
		com.liferay.portal.model.ExportImportConfiguration exportImportConfiguration)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateExportImportConfiguration(exportImportConfiguration);
	}

	/**
	* Returns the Spring bean ID for this bean.
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

	public static com.liferay.portal.model.ExportImportConfiguration addExportImportConfiguration(
		long userId, long groupId, java.lang.String name,
		java.lang.String description, int type,
		java.util.Map<java.lang.String, java.io.Serializable> settingsMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addExportImportConfiguration(userId, groupId, name,
			description, type, settingsMap, serviceContext);
	}

	public static void deleteExportImportConfigurations(long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteExportImportConfigurations(groupId);
	}

	public static java.util.List<com.liferay.portal.model.ExportImportConfiguration> getExportImportConfigurations(
		long groupId, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getExportImportConfigurations(groupId, type);
	}

	public static java.util.List<com.liferay.portal.model.ExportImportConfiguration> getExportImportConfigurations(
		long groupId, int type, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getExportImportConfigurations(groupId, type, start, end,
			orderByComparator);
	}

	public static int getExportImportConfigurationsCount(long groupId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getExportImportConfigurationsCount(groupId);
	}

	public static int getExportImportConfigurationsCount(long groupId, int type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getExportImportConfigurationsCount(groupId, type);
	}

	public static com.liferay.portal.model.ExportImportConfiguration moveExportImportConfigurationToTrash(
		long userId, long exportImportConfigurationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .moveExportImportConfigurationToTrash(userId,
			exportImportConfigurationId);
	}

	public static com.liferay.portal.model.ExportImportConfiguration restoreExportImportConfigurationFromTrash(
		long userId, long exportImportConfigurationId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .restoreExportImportConfigurationFromTrash(userId,
			exportImportConfigurationId);
	}

	public static com.liferay.portal.model.ExportImportConfiguration updateExportImportConfiguration(
		long exportImportConfigurationId, java.lang.String name,
		java.lang.String description,
		java.util.Map<java.lang.String, java.io.Serializable> settingsMap,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateExportImportConfiguration(exportImportConfigurationId,
			name, description, settingsMap, serviceContext);
	}

	public static com.liferay.portal.model.ExportImportConfiguration updateStatus(
		long userId, long exportImportConfigurationId, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateStatus(userId, exportImportConfigurationId, status);
	}

	public static ExportImportConfigurationLocalService getService() {
		if (_service == null) {
			_service = (ExportImportConfigurationLocalService)PortalBeanLocatorUtil.locate(ExportImportConfigurationLocalService.class.getName());

			ReferenceRegistry.registerReference(ExportImportConfigurationLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(ExportImportConfigurationLocalService service) {
	}

	private static ExportImportConfigurationLocalService _service;
}