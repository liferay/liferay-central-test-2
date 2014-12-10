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

package com.liferay.portlet.dynamicdatamapping.service;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * Provides the local service utility for DDMTemplateVersion. This utility wraps
 * {@link com.liferay.portlet.dynamicdatamapping.service.impl.DDMTemplateVersionLocalServiceImpl} and is the
 * primary access point for service operations in application layer code running
 * on the local server. Methods of this service will not have security checks
 * based on the propagated JAAS credentials because this service can only be
 * accessed from within the same VM.
 *
 * @author Brian Wing Shun Chan
 * @see DDMTemplateVersionLocalService
 * @see com.liferay.portlet.dynamicdatamapping.service.base.DDMTemplateVersionLocalServiceBaseImpl
 * @see com.liferay.portlet.dynamicdatamapping.service.impl.DDMTemplateVersionLocalServiceImpl
 * @generated
 */
@ProviderType
public class DDMTemplateVersionLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portlet.dynamicdatamapping.service.impl.DDMTemplateVersionLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the d d m template version to the database. Also notifies the appropriate model listeners.
	*
	* @param ddmTemplateVersion the d d m template version
	* @return the d d m template version that was added
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplateVersion addDDMTemplateVersion(
		com.liferay.portlet.dynamicdatamapping.model.DDMTemplateVersion ddmTemplateVersion) {
		return getService().addDDMTemplateVersion(ddmTemplateVersion);
	}

	/**
	* Creates a new d d m template version with the primary key. Does not add the d d m template version to the database.
	*
	* @param templateVersionId the primary key for the new d d m template version
	* @return the new d d m template version
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplateVersion createDDMTemplateVersion(
		long templateVersionId) {
		return getService().createDDMTemplateVersion(templateVersionId);
	}

	/**
	* Deletes the d d m template version from the database. Also notifies the appropriate model listeners.
	*
	* @param ddmTemplateVersion the d d m template version
	* @return the d d m template version that was removed
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplateVersion deleteDDMTemplateVersion(
		com.liferay.portlet.dynamicdatamapping.model.DDMTemplateVersion ddmTemplateVersion) {
		return getService().deleteDDMTemplateVersion(ddmTemplateVersion);
	}

	/**
	* Deletes the d d m template version with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param templateVersionId the primary key of the d d m template version
	* @return the d d m template version that was removed
	* @throws PortalException if a d d m template version with the primary key could not be found
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplateVersion deleteDDMTemplateVersion(
		long templateVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deleteDDMTemplateVersion(templateVersionId);
	}

	/**
	* @throws PortalException
	*/
	public static com.liferay.portal.model.PersistedModel deletePersistedModel(
		com.liferay.portal.model.PersistedModel persistedModel)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().deletePersistedModel(persistedModel);
	}

	public static com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery() {
		return getService().dynamicQuery();
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatamapping.model.impl.DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatamapping.model.impl.DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
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

	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplateVersion fetchDDMTemplateVersion(
		long templateVersionId) {
		return getService().fetchDDMTemplateVersion(templateVersionId);
	}

	public static com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery getActionableDynamicQuery() {
		return getService().getActionableDynamicQuery();
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
	* Returns the d d m template version with the primary key.
	*
	* @param templateVersionId the primary key of the d d m template version
	* @return the d d m template version
	* @throws PortalException if a d d m template version with the primary key could not be found
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplateVersion getDDMTemplateVersion(
		long templateVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getDDMTemplateVersion(templateVersionId);
	}

	/**
	* Returns a range of all the d d m template versions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatamapping.model.impl.DDMTemplateVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	* </p>
	*
	* @param start the lower bound of the range of d d m template versions
	* @param end the upper bound of the range of d d m template versions (not inclusive)
	* @return the range of d d m template versions
	*/
	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplateVersion> getDDMTemplateVersions(
		int start, int end) {
		return getService().getDDMTemplateVersions(start, end);
	}

	/**
	* Returns the number of d d m template versions.
	*
	* @return the number of d d m template versions
	*/
	public static int getDDMTemplateVersionsCount() {
		return getService().getDDMTemplateVersionsCount();
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplateVersion getLatestTemplateVersion(
		long templateId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getLatestTemplateVersion(templateId);
	}

	public static com.liferay.portal.model.PersistedModel getPersistedModel(
		java.io.Serializable primaryKeyObj)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getPersistedModel(primaryKeyObj);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplateVersion getTemplateVersion(
		long templateId, java.lang.String version)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getTemplateVersion(templateId, version);
	}

	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplateVersion getTemplateVersion(
		long templateVersionId)
		throws com.liferay.portal.kernel.exception.PortalException {
		return getService().getTemplateVersion(templateVersionId);
	}

	public static java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMTemplateVersion> getTemplateVersions(
		long templateId, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<com.liferay.portlet.dynamicdatamapping.model.DDMTemplateVersion> orderByComparator) {
		return getService()
				   .getTemplateVersions(templateId, start, end,
			orderByComparator);
	}

	public static int getTemplateVersionsCount(long templateId) {
		return getService().getTemplateVersionsCount(templateId);
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public static void setBeanIdentifier(java.lang.String beanIdentifier) {
		getService().setBeanIdentifier(beanIdentifier);
	}

	/**
	* Updates the d d m template version in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	*
	* @param ddmTemplateVersion the d d m template version
	* @return the d d m template version that was updated
	*/
	public static com.liferay.portlet.dynamicdatamapping.model.DDMTemplateVersion updateDDMTemplateVersion(
		com.liferay.portlet.dynamicdatamapping.model.DDMTemplateVersion ddmTemplateVersion) {
		return getService().updateDDMTemplateVersion(ddmTemplateVersion);
	}

	public static DDMTemplateVersionLocalService getService() {
		if (_service == null) {
			_service = (DDMTemplateVersionLocalService)PortalBeanLocatorUtil.locate(DDMTemplateVersionLocalService.class.getName());

			ReferenceRegistry.registerReference(DDMTemplateVersionLocalServiceUtil.class,
				"_service");
		}

		return _service;
	}

	/**
	 * @deprecated As of 6.2.0
	 */
	@Deprecated
	public void setService(DDMTemplateVersionLocalService service) {
	}

	private static DDMTemplateVersionLocalService _service;
}