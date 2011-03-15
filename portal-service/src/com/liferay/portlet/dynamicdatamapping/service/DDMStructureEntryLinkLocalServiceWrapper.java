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

package com.liferay.portlet.dynamicdatamapping.service;

/**
 * <p>
 * This class is a wrapper for {@link DDMStructureEntryLinkLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DDMStructureEntryLinkLocalService
 * @generated
 */
public class DDMStructureEntryLinkLocalServiceWrapper
	implements DDMStructureEntryLinkLocalService {
	public DDMStructureEntryLinkLocalServiceWrapper(
		DDMStructureEntryLinkLocalService ddmStructureEntryLinkLocalService) {
		_ddmStructureEntryLinkLocalService = ddmStructureEntryLinkLocalService;
	}

	/**
	* Adds the d d m structure entry link to the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructureEntryLink the d d m structure entry link to add
	* @return the d d m structure entry link that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink addDDMStructureEntryLink(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink ddmStructureEntryLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureEntryLinkLocalService.addDDMStructureEntryLink(ddmStructureEntryLink);
	}

	/**
	* Creates a new d d m structure entry link with the primary key. Does not add the d d m structure entry link to the database.
	*
	* @param structureEntryLinkId the primary key for the new d d m structure entry link
	* @return the new d d m structure entry link
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink createDDMStructureEntryLink(
		long structureEntryLinkId) {
		return _ddmStructureEntryLinkLocalService.createDDMStructureEntryLink(structureEntryLinkId);
	}

	/**
	* Deletes the d d m structure entry link with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param structureEntryLinkId the primary key of the d d m structure entry link to delete
	* @throws PortalException if a d d m structure entry link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDDMStructureEntryLink(long structureEntryLinkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddmStructureEntryLinkLocalService.deleteDDMStructureEntryLink(structureEntryLinkId);
	}

	/**
	* Deletes the d d m structure entry link from the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructureEntryLink the d d m structure entry link to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDDMStructureEntryLink(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink ddmStructureEntryLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		_ddmStructureEntryLinkLocalService.deleteDDMStructureEntryLink(ddmStructureEntryLink);
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
		return _ddmStructureEntryLinkLocalService.dynamicQuery(dynamicQuery);
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
		return _ddmStructureEntryLinkLocalService.dynamicQuery(dynamicQuery,
			start, end);
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
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureEntryLinkLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
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
		return _ddmStructureEntryLinkLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the d d m structure entry link with the primary key.
	*
	* @param structureEntryLinkId the primary key of the d d m structure entry link to get
	* @return the d d m structure entry link
	* @throws PortalException if a d d m structure entry link with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink getDDMStructureEntryLink(
		long structureEntryLinkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureEntryLinkLocalService.getDDMStructureEntryLink(structureEntryLinkId);
	}

	/**
	* Gets a range of all the d d m structure entry links.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d d m structure entry links to return
	* @param end the upper bound of the range of d d m structure entry links to return (not inclusive)
	* @return the range of d d m structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink> getDDMStructureEntryLinks(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureEntryLinkLocalService.getDDMStructureEntryLinks(start,
			end);
	}

	/**
	* Gets the number of d d m structure entry links.
	*
	* @return the number of d d m structure entry links
	* @throws SystemException if a system exception occurred
	*/
	public int getDDMStructureEntryLinksCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureEntryLinkLocalService.getDDMStructureEntryLinksCount();
	}

	/**
	* Updates the d d m structure entry link in the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructureEntryLink the d d m structure entry link to update
	* @return the d d m structure entry link that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink updateDDMStructureEntryLink(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink ddmStructureEntryLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureEntryLinkLocalService.updateDDMStructureEntryLink(ddmStructureEntryLink);
	}

	/**
	* Updates the d d m structure entry link in the database. Also notifies the appropriate model listeners.
	*
	* @param ddmStructureEntryLink the d d m structure entry link to update
	* @param merge whether to merge the d d m structure entry link with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the d d m structure entry link that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink updateDDMStructureEntryLink(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink ddmStructureEntryLink,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureEntryLinkLocalService.updateDDMStructureEntryLink(ddmStructureEntryLink,
			merge);
	}

	/**
	* Gets the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _ddmStructureEntryLinkLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_ddmStructureEntryLinkLocalService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink addStructureEntryLink(
		java.lang.String structureId, java.lang.String className, long classPK,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureEntryLinkLocalService.addStructureEntryLink(structureId,
			className, classPK, serviceContext);
	}

	public void deleteStructureEntryLink(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink structureEntryLink)
		throws com.liferay.portal.kernel.exception.SystemException {
		_ddmStructureEntryLinkLocalService.deleteStructureEntryLink(structureEntryLink);
	}

	public void deleteStructureEntryLink(long structureEntryLinkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddmStructureEntryLinkLocalService.deleteStructureEntryLink(structureEntryLinkId);
	}

	public void deleteStructureEntryLink(java.lang.String structureId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddmStructureEntryLinkLocalService.deleteStructureEntryLink(structureId,
			className, classPK);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink getStructureEntryLink(
		long structureEntryLinkId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureEntryLinkLocalService.getStructureEntryLink(structureEntryLinkId);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink getStructureEntryLink(
		java.lang.String structureId, java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureEntryLinkLocalService.getStructureEntryLink(structureId,
			className, classPK);
	}

	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink> getStructureEntryLinks(
		java.lang.String structureId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureEntryLinkLocalService.getStructureEntryLinks(structureId,
			start, end);
	}

	public com.liferay.portlet.dynamicdatamapping.model.DDMStructureEntryLink updateStructureEntryLink(
		long structureEntryLinkId, java.lang.String structureId, long groupId,
		java.lang.String className, long classPK)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmStructureEntryLinkLocalService.updateStructureEntryLink(structureEntryLinkId,
			structureId, groupId, className, classPK);
	}

	public DDMStructureEntryLinkLocalService getWrappedDDMStructureEntryLinkLocalService() {
		return _ddmStructureEntryLinkLocalService;
	}

	public void setWrappedDDMStructureEntryLinkLocalService(
		DDMStructureEntryLinkLocalService ddmStructureEntryLinkLocalService) {
		_ddmStructureEntryLinkLocalService = ddmStructureEntryLinkLocalService;
	}

	private DDMStructureEntryLinkLocalService _ddmStructureEntryLinkLocalService;
}