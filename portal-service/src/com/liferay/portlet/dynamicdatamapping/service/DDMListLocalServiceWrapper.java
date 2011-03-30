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
 * This class is a wrapper for {@link DDMListLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       DDMListLocalService
 * @generated
 */
public class DDMListLocalServiceWrapper implements DDMListLocalService {
	public DDMListLocalServiceWrapper(DDMListLocalService ddmListLocalService) {
		_ddmListLocalService = ddmListLocalService;
	}

	/**
	* Adds the d d m list to the database. Also notifies the appropriate model listeners.
	*
	* @param ddmList the d d m list to add
	* @return the d d m list that was added
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMList addDDMList(
		com.liferay.portlet.dynamicdatamapping.model.DDMList ddmList)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmListLocalService.addDDMList(ddmList);
	}

	/**
	* Creates a new d d m list with the primary key. Does not add the d d m list to the database.
	*
	* @param listId the primary key for the new d d m list
	* @return the new d d m list
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMList createDDMList(
		long listId) {
		return _ddmListLocalService.createDDMList(listId);
	}

	/**
	* Deletes the d d m list with the primary key from the database. Also notifies the appropriate model listeners.
	*
	* @param listId the primary key of the d d m list to delete
	* @throws PortalException if a d d m list with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDDMList(long listId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_ddmListLocalService.deleteDDMList(listId);
	}

	/**
	* Deletes the d d m list from the database. Also notifies the appropriate model listeners.
	*
	* @param ddmList the d d m list to delete
	* @throws SystemException if a system exception occurred
	*/
	public void deleteDDMList(
		com.liferay.portlet.dynamicdatamapping.model.DDMList ddmList)
		throws com.liferay.portal.kernel.exception.SystemException {
		_ddmListLocalService.deleteDDMList(ddmList);
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
		return _ddmListLocalService.dynamicQuery(dynamicQuery);
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
		return _ddmListLocalService.dynamicQuery(dynamicQuery, start, end);
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
		return _ddmListLocalService.dynamicQuery(dynamicQuery, start, end,
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
		return _ddmListLocalService.dynamicQueryCount(dynamicQuery);
	}

	/**
	* Gets the d d m list with the primary key.
	*
	* @param listId the primary key of the d d m list to get
	* @return the d d m list
	* @throws PortalException if a d d m list with the primary key could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMList getDDMList(
		long listId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmListLocalService.getDDMList(listId);
	}

	/**
	* Gets the d d m list with the UUID and group id.
	*
	* @param uuid the UUID of d d m list to get
	* @param groupId the group id of the d d m list to get
	* @return the d d m list
	* @throws PortalException if a d d m list with the UUID and group id could not be found
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMList getDDMListByUuidAndGroupId(
		java.lang.String uuid, long groupId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _ddmListLocalService.getDDMListByUuidAndGroupId(uuid, groupId);
	}

	/**
	* Gets a range of all the d d m lists.
	*
	* <p>
	* Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
	* </p>
	*
	* @param start the lower bound of the range of d d m lists to return
	* @param end the upper bound of the range of d d m lists to return (not inclusive)
	* @return the range of d d m lists
	* @throws SystemException if a system exception occurred
	*/
	public java.util.List<com.liferay.portlet.dynamicdatamapping.model.DDMList> getDDMLists(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmListLocalService.getDDMLists(start, end);
	}

	/**
	* Gets the number of d d m lists.
	*
	* @return the number of d d m lists
	* @throws SystemException if a system exception occurred
	*/
	public int getDDMListsCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmListLocalService.getDDMListsCount();
	}

	/**
	* Updates the d d m list in the database. Also notifies the appropriate model listeners.
	*
	* @param ddmList the d d m list to update
	* @return the d d m list that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMList updateDDMList(
		com.liferay.portlet.dynamicdatamapping.model.DDMList ddmList)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmListLocalService.updateDDMList(ddmList);
	}

	/**
	* Updates the d d m list in the database. Also notifies the appropriate model listeners.
	*
	* @param ddmList the d d m list to update
	* @param merge whether to merge the d d m list with the current session. See {@link com.liferay.portal.service.persistence.BatchSession#update(com.liferay.portal.kernel.dao.orm.Session, com.liferay.portal.model.BaseModel, boolean)} for an explanation.
	* @return the d d m list that was updated
	* @throws SystemException if a system exception occurred
	*/
	public com.liferay.portlet.dynamicdatamapping.model.DDMList updateDDMList(
		com.liferay.portlet.dynamicdatamapping.model.DDMList ddmList,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _ddmListLocalService.updateDDMList(ddmList, merge);
	}

	/**
	* Gets the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _ddmListLocalService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_ddmListLocalService.setBeanIdentifier(beanIdentifier);
	}

	public DDMListLocalService getWrappedDDMListLocalService() {
		return _ddmListLocalService;
	}

	public void setWrappedDDMListLocalService(
		DDMListLocalService ddmListLocalService) {
		_ddmListLocalService = ddmListLocalService;
	}

	private DDMListLocalService _ddmListLocalService;
}