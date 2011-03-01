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

package com.liferay.portal.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.util.MethodCache;
import com.liferay.portal.kernel.util.ReferenceRegistry;

/**
 * The utility for the layout revision local service. This utility wraps {@link com.liferay.portal.service.impl.LayoutRevisionLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LayoutRevisionLocalService
 * @see com.liferay.portal.service.base.LayoutRevisionLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.LayoutRevisionLocalServiceImpl
 * @generated
 */
public class LayoutRevisionLocalServiceUtil {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.LayoutRevisionLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	* Adds the layout revision to the database. Also notifies the appropriate model listeners.
	*
	* @param layoutRevision the layout revision to add
	* @return the layout revision that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision addLayoutRevision(
		com.liferay.portal.model.LayoutRevision layoutRevision)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addLayoutRevision(layoutRevision);
	}

	/**
	* Creates a new layout revision with the primary key. Does not add the layout revision to the database.
	*
	* @param layoutRevisionId the primary key for the new layout revision
	* @return the new layout revision
	*/
	public static com.liferay.portal.model.LayoutRevision createLayoutRevision(
		long layoutRevisionId) {
		return getService().createLayoutRevision(layoutRevisionId);
	}

	/**
	* Deletes the layout revision with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutRevisionId the primary key of the layout revision to delete
	* @throws PortalException if a layout revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteLayoutRevision(long layoutRevisionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteLayoutRevision(layoutRevisionId);
	}

	/**
	* Deletes the layout revision from the database. Also notifies the appropriate model listeners.
	*
	* @param layoutRevision the layout revision to delete
	* @throws PortalException
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteLayoutRevision(
		com.liferay.portal.model.LayoutRevision layoutRevision)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteLayoutRevision(layoutRevision);
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
	* Gets the layout revision with the primary key.
	*
	* @param layoutRevisionId the primary key of the layout revision to get
	* @return the layout revision
	* @throws PortalException if a layout revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision getLayoutRevision(
		long layoutRevisionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getLayoutRevision(layoutRevisionId);
	}

	/**
	* Gets a range of all the layout revisions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of layout revisions to return
	* @param end the upper bound of the range of layout revisions to return (not inclusive)
	* @return the range of layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.LayoutRevision> getLayoutRevisions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLayoutRevisions(start, end);
	}

	/**
	* Gets the number of layout revisions.
	*
	* @return the number of layout revisions
	* @throws SystemException if a system exception occurred
	*/
	public static int getLayoutRevisionsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLayoutRevisionsCount();
	}

	/**
	* Updates the layout revision in the database. Also notifies the appropriate model listeners.
	*
	* @param layoutRevision the layout revision to update
	* @return the layout revision that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision updateLayoutRevision(
		com.liferay.portal.model.LayoutRevision layoutRevision)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateLayoutRevision(layoutRevision);
	}

	/**
	* Updates the layout revision in the database. Also notifies the appropriate model listeners.
	*
	* @param layoutRevision the layout revision to update
	* @param merge whether to merge the layout revision with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the layout revision that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.LayoutRevision updateLayoutRevision(
		com.liferay.portal.model.LayoutRevision layoutRevision, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateLayoutRevision(layoutRevision, merge);
	}

	/**
	* Gets the Spring bean id for this ServiceBean.
	*
	* @return the Spring bean id for this ServiceBean
	*/
	public static java.lang.String getIdentifier() {
		return getService().getIdentifier();
	}

	/**
	* Sets the Spring bean id for this ServiceBean.
	*
	* @param identifier the Spring bean id for this ServiceBean
	*/
	public static void setIdentifier(java.lang.String identifier) {
		getService().setIdentifier(identifier);
	}

	public static com.liferay.portal.model.LayoutRevision addLayoutRevision(
		long userId, long layoutSetBranchId, long parentLayoutRevisionId,
		boolean head, long plid, java.lang.String name, java.lang.String title,
		java.lang.String description, java.lang.String typeSettings,
		boolean iconImage, long iconImageId, java.lang.String themeId,
		java.lang.String colorSchemeId, java.lang.String wapThemeId,
		java.lang.String wapColorSchemeId, java.lang.String css,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addLayoutRevision(userId, layoutSetBranchId,
			parentLayoutRevisionId, head, plid, name, title, description,
			typeSettings, iconImage, iconImageId, themeId, colorSchemeId,
			wapThemeId, wapColorSchemeId, css, serviceContext);
	}

	public static com.liferay.portal.model.LayoutRevision checkLatestLayoutRevision(
		long layoutRevisionId, long layoutSetBranchId, long plid,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .checkLatestLayoutRevision(layoutRevisionId,
			layoutSetBranchId, plid, serviceContext);
	}

	public static void deleteLayoutSetBranchLayoutRevisions(
		long layoutSetBranchId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteLayoutSetBranchLayoutRevisions(layoutSetBranchId);
	}

	public static void deleteLayoutLayoutRevisions(long plid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteLayoutLayoutRevisions(plid);
	}

	public static void deleteLayoutRevisions(long layoutSetBranchId, long plid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteLayoutRevisions(layoutSetBranchId, plid);
	}

	public static com.liferay.portal.model.LayoutRevision getLayoutRevision(
		long layoutSetBranchId, long plid, boolean head)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getLayoutRevision(layoutSetBranchId, plid, head);
	}

	public static java.util.List<com.liferay.portal.model.LayoutRevision> getLayoutRevisions(
		long plid) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLayoutRevisions(plid);
	}

	public static java.util.List<com.liferay.portal.model.LayoutRevision> getLayoutRevisions(
		long layoutSetBranchId, long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLayoutRevisions(layoutSetBranchId, plid);
	}

	public static java.util.List<com.liferay.portal.model.LayoutRevision> getLayoutRevisions(
		long layoutSetBranchId, long plid, int status)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getLayoutRevisions(layoutSetBranchId, plid, status);
	}

	public static java.util.List<com.liferay.portal.model.LayoutRevision> getLayoutRevisions(
		long layoutSetBranchId, long parentLayoutRevisionId, long plid)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .getLayoutRevisions(layoutSetBranchId,
			parentLayoutRevisionId, plid);
	}

	public static com.liferay.portal.model.LayoutRevision updateLayoutRevision(
		long userId, long layoutRevisionId, java.lang.String name,
		java.lang.String title, java.lang.String description,
		java.lang.String keywords, java.lang.String robots,
		java.lang.String typeSettings, boolean iconImage, long iconImageId,
		java.lang.String themeId, java.lang.String colorSchemeId,
		java.lang.String wapThemeId, java.lang.String wapColorSchemeId,
		java.lang.String css,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateLayoutRevision(userId, layoutRevisionId, name, title,
			description, keywords, robots, typeSettings, iconImage,
			iconImageId, themeId, colorSchemeId, wapThemeId, wapColorSchemeId,
			css, serviceContext);
	}

	public static com.liferay.portal.model.LayoutRevision updateStatus(
		long userId, long layoutRevisionId, int status,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateStatus(userId, layoutRevisionId, status,
			serviceContext);
	}

	public static LayoutRevisionLocalService getService() {
		if (_service == null) {
			_service = (LayoutRevisionLocalService)PortalBeanLocatorUtil.locate(LayoutRevisionLocalService.class.getName());

			ReferenceRegistry.registerReference(LayoutRevisionLocalServiceUtil.class,
				"_service");
			MethodCache.remove(LayoutRevisionLocalService.class);
		}

		return _service;
	}

	public void setService(LayoutRevisionLocalService service) {
		MethodCache.remove(LayoutRevisionLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(LayoutRevisionLocalServiceUtil.class,
			"_service");
		MethodCache.remove(LayoutRevisionLocalService.class);
	}

	private static LayoutRevisionLocalService _service;
}