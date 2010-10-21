/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
 * The utility for the revision local service. This utility wraps {@link com.liferay.portal.service.impl.RevisionLocalServiceImpl} and is the primary access point for service operations in application layer code running on the local server.
 *
 * <p>
 * Never modify this class directly. Add custom service methods to {@link com.liferay.portal.service.impl.RevisionLocalServiceImpl} and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see RevisionLocalService
 * @see com.liferay.portal.service.base.RevisionLocalServiceBaseImpl
 * @see com.liferay.portal.service.impl.RevisionLocalServiceImpl
 * @generated
 */
public class RevisionLocalServiceUtil {
	/**
	* Adds the revision to the database. Also notifies the appropriate model listeners.
	*
	* @param revision the revision to add
	* @return the revision that was added
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Revision addRevision(
		com.liferay.portal.model.Revision revision)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addRevision(revision);
	}

	/**
	* Creates a new revision with the primary key. Does not add the revision to the database.
	*
	* @param revisionId the primary key for the new revision
	* @return the new revision
	*/
	public static com.liferay.portal.model.Revision createRevision(
		long revisionId) {
		return getService().createRevision(revisionId);
	}

	/**
	* Deletes the revision with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param revisionId the primary key of the revision to delete
	* @throws PortalException if a revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteRevision(long revisionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRevision(revisionId);
	}

	/**
	* Deletes the revision from the database. Also notifies the appropriate model listeners.
	*
	* @param revision the revision to delete
	* @throws SystemException if a system exception occurred
	*/
	public static void deleteRevision(
		com.liferay.portal.model.Revision revision)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRevision(revision);
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
	* @param orderByComparator the comparator to order the results by
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
	* Gets the revision with the primary key.
	*
	* @param revisionId the primary key of the revision to get
	* @return the revision
	* @throws PortalException if a revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Revision getRevision(long revisionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getRevision(revisionId);
	}

	/**
	* Gets a range of all the revisions.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of revisions to return
	* @param end the upper bound of the range of revisions to return (not inclusive)
	* @return the range of revisions
	* @throws SystemException if a system exception occurred
	*/
	public static java.util.List<com.liferay.portal.model.Revision> getRevisions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRevisions(start, end);
	}

	/**
	* Gets the number of revisions.
	*
	* @return the number of revisions
	* @throws SystemException if a system exception occurred
	*/
	public static int getRevisionsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getRevisionsCount();
	}

	/**
	* Updates the revision in the database. Also notifies the appropriate model listeners.
	*
	* @param revision the revision to update
	* @return the revision that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Revision updateRevision(
		com.liferay.portal.model.Revision revision)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateRevision(revision);
	}

	/**
	* Updates the revision in the database. Also notifies the appropriate model listeners.
	*
	* @param revision the revision to update
	* @param merge whether to merge the revision with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the revision that was updated
	* @throws SystemException if a system exception occurred
	*/
	public static com.liferay.portal.model.Revision updateRevision(
		com.liferay.portal.model.Revision revision, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updateRevision(revision, merge);
	}

	public static com.liferay.portal.model.Revision addRevision(long branchId,
		long plid, long groupId, java.lang.String name, java.lang.String title,
		java.lang.String description, java.lang.String typeSettings,
		boolean iconImage, long iconImageId, java.lang.String themeId,
		java.lang.String colorSchemeId, java.lang.String wapThemeId,
		java.lang.String wapColorSchemeId, java.lang.String css, boolean head,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .addRevision(branchId, plid, groupId, name, title,
			description, typeSettings, iconImage, iconImageId, themeId,
			colorSchemeId, wapThemeId, wapColorSchemeId, css, head,
			serviceContext);
	}

	public static void deleteRevisionsByBranch(long branchId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRevisionsByBranch(branchId);
	}

	public static void deleteRevisionsByLayout(long plid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRevisionsByLayout(plid);
	}

	public static void deleteRevisions(long branchId, long plid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deleteRevisions(branchId, plid);
	}

	public static com.liferay.portal.model.Revision checkLatestRevision(
		long branchId, long plid,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().checkLatestRevision(branchId, plid, serviceContext);
	}

	public static com.liferay.portal.model.Revision getHeadRevision(
		long branchId, long plid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getHeadRevision(branchId, plid);
	}

	public static java.util.List<com.liferay.portal.model.Revision> getRevisions(
		long plid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getRevisions(plid);
	}

	public static java.util.List<com.liferay.portal.model.Revision> getRevisions(
		long branchId, long plid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getRevisions(branchId, plid);
	}

	public static java.util.List<com.liferay.portal.model.Revision> getRevisions(
		long branchId, long plid, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getRevisions(branchId, plid, status);
	}

	public static com.liferay.portal.model.Revision revertToRevision(
		long revisionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().revertToRevision(revisionId);
	}

	public static com.liferay.portal.model.Revision updateRevision(
		long revisionId, java.lang.String name, java.lang.String title,
		java.lang.String description, java.lang.String typeSettings,
		boolean iconImage, long iconImageId, java.lang.String themeId,
		java.lang.String colorSchemeId, java.lang.String wapThemeId,
		java.lang.String wapColorSchemeId, java.lang.String css,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateRevision(revisionId, name, title, description,
			typeSettings, iconImage, iconImageId, themeId, colorSchemeId,
			wapThemeId, wapColorSchemeId, css, serviceContext);
	}

	public static com.liferay.portal.model.Revision updateStatus(long userId,
		long revisionId, int status,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .updateStatus(userId, revisionId, status, serviceContext);
	}

	public static RevisionLocalService getService() {
		if (_service == null) {
			_service = (RevisionLocalService)PortalBeanLocatorUtil.locate(RevisionLocalService.class.getName());

			ReferenceRegistry.registerReference(RevisionLocalServiceUtil.class,
				"_service");
			MethodCache.remove(RevisionLocalService.class);
		}

		return _service;
	}

	public void setService(RevisionLocalService service) {
		MethodCache.remove(RevisionLocalService.class);

		_service = service;

		ReferenceRegistry.registerReference(RevisionLocalServiceUtil.class,
			"_service");
		MethodCache.remove(RevisionLocalService.class);
	}

	private static RevisionLocalService _service;
}