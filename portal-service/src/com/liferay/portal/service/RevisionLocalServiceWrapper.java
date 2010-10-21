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

/**
 * <p>
 * This class is a wrapper for {@link RevisionLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       RevisionLocalService
 * @generated
 */
public class RevisionLocalServiceWrapper implements RevisionLocalService {
	public RevisionLocalServiceWrapper(
		RevisionLocalService revisionLocalService) {
		_revisionLocalService = revisionLocalService;
	}

	/**
	* Adds the revision to the database. Also notifies the appropriate model listeners.
	*
	* @param revision the revision to add
	* @return the revision that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Revision addRevision(
		com.liferay.portal.model.Revision revision)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _revisionLocalService.addRevision(revision);
	}

	/**
	* Creates a new revision with the primary key. Does not add the revision to the database.
	*
	* @param revisionId the primary key for the new revision
	* @return the new revision
	*/
	public com.liferay.portal.model.Revision createRevision(long revisionId) {
		return _revisionLocalService.createRevision(revisionId);
	}

	/**
	* Deletes the revision with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param revisionId the primary key of the revision to delete
	* @throws PortalException if a revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteRevision(long revisionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_revisionLocalService.deleteRevision(revisionId);
	}

	/**
	* Deletes the revision from the database. Also notifies the appropriate model listeners.
	*
	* @param revision the revision to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteRevision(com.liferay.portal.model.Revision revision)
		throws com.liferay.portal.kernel.exception.SystemException {
		_revisionLocalService.deleteRevision(revision);
	}

	/**
	* Performs a dynamic query on the database and returns the matching rows.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the matching rows
	* @throws SystemException if a system exception occurred
	*/
	@SuppressWarnings("rawtypes")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _revisionLocalService.dynamicQuery(dynamicQuery);
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
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _revisionLocalService.dynamicQuery(dynamicQuery, start, end);
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
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _revisionLocalService.dynamicQuery(dynamicQuery, start, end,
			orderByComparator);
	}

	/**
	* Counts the number of rows that match the dynamic query.
	*
	* @param dynamicQuery the dynamic query to search with
	* @return the number of rows that match the dynamic query
	* @throws SystemException if a system exception occurred
	*/
	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _revisionLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the revision with the primary key.
	*
	* @param revisionId the primary key of the revision to get
	* @return the revision
	* @throws PortalException if a revision with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Revision getRevision(long revisionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _revisionLocalService.getRevision(revisionId);
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
	public java.util.List<com.liferay.portal.model.Revision> getRevisions(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _revisionLocalService.getRevisions(start, end);
	}

	/**
	* Gets the number of revisions.
	*
	* @return the number of revisions
	* @throws SystemException if a system exception occurred
	*/
	public int getRevisionsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _revisionLocalService.getRevisionsCount();
	}

	/**
	* Updates the revision in the database. Also notifies the appropriate model listeners.
	*
	* @param revision the revision to update
	* @return the revision that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Revision updateRevision(
		com.liferay.portal.model.Revision revision)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _revisionLocalService.updateRevision(revision);
	}

	/**
	* Updates the revision in the database. Also notifies the appropriate model listeners.
	*
	* @param revision the revision to update
	* @param merge whether to merge the revision with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the revision that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portal.model.Revision updateRevision(
		com.liferay.portal.model.Revision revision, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _revisionLocalService.updateRevision(revision, merge);
	}

	public com.liferay.portal.model.Revision addRevision(long branchId,
		long plid, long groupId, java.lang.String name, java.lang.String title,
		java.lang.String description, java.lang.String typeSettings,
		boolean iconImage, long iconImageId, java.lang.String themeId,
		java.lang.String colorSchemeId, java.lang.String wapThemeId,
		java.lang.String wapColorSchemeId, java.lang.String css, boolean head,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _revisionLocalService.addRevision(branchId, plid, groupId, name,
			title, description, typeSettings, iconImage, iconImageId, themeId,
			colorSchemeId, wapThemeId, wapColorSchemeId, css, head,
			serviceContext);
	}

	public void deleteRevisionsByBranch(long branchId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_revisionLocalService.deleteRevisionsByBranch(branchId);
	}

	public void deleteRevisionsByLayout(long plid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_revisionLocalService.deleteRevisionsByLayout(plid);
	}

	public void deleteRevisions(long branchId, long plid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_revisionLocalService.deleteRevisions(branchId, plid);
	}

	public com.liferay.portal.model.Revision checkLatestRevision(
		long branchId, long plid,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _revisionLocalService.checkLatestRevision(branchId, plid,
			serviceContext);
	}

	public com.liferay.portal.model.Revision getHeadRevision(long branchId,
		long plid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _revisionLocalService.getHeadRevision(branchId, plid);
	}

	public java.util.List<com.liferay.portal.model.Revision> getRevisions(
		long plid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _revisionLocalService.getRevisions(plid);
	}

	public java.util.List<com.liferay.portal.model.Revision> getRevisions(
		long branchId, long plid)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _revisionLocalService.getRevisions(branchId, plid);
	}

	public java.util.List<com.liferay.portal.model.Revision> getRevisions(
		long branchId, long plid, int status)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _revisionLocalService.getRevisions(branchId, plid, status);
	}

	public com.liferay.portal.model.Revision revertToRevision(long revisionId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _revisionLocalService.revertToRevision(revisionId);
	}

	public com.liferay.portal.model.Revision updateRevision(long revisionId,
		java.lang.String name, java.lang.String title,
		java.lang.String description, java.lang.String typeSettings,
		boolean iconImage, long iconImageId, java.lang.String themeId,
		java.lang.String colorSchemeId, java.lang.String wapThemeId,
		java.lang.String wapColorSchemeId, java.lang.String css,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _revisionLocalService.updateRevision(revisionId, name, title,
			description, typeSettings, iconImage, iconImageId, themeId,
			colorSchemeId, wapThemeId, wapColorSchemeId, css, serviceContext);
	}

	public com.liferay.portal.model.Revision updateStatus(long userId,
		long revisionId, int status,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _revisionLocalService.updateStatus(userId, revisionId, status,
			serviceContext);
	}

	public RevisionLocalService getWrappedRevisionLocalService() {
		return _revisionLocalService;
	}

	private RevisionLocalService _revisionLocalService;
}