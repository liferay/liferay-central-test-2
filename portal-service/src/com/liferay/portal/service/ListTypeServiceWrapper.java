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

/**
 * <p>
 * This class is a wrapper for {@link ListTypeService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ListTypeService
 * @generated
 */
public class ListTypeServiceWrapper implements ListTypeService,
	ServiceWrapper<ListTypeService> {
	public ListTypeServiceWrapper(ListTypeService listTypeService) {
		_listTypeService = listTypeService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _listTypeService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_listTypeService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portal.model.ListType getListType(int listTypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _listTypeService.getListType(listTypeId);
	}

	public java.util.List<com.liferay.portal.model.ListType> getListTypes(
		java.lang.String type)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _listTypeService.getListTypes(type);
	}

	public void validate(int listTypeId, long classNameId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_listTypeService.validate(listTypeId, classNameId, type);
	}

	public void validate(int listTypeId, java.lang.String type)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_listTypeService.validate(listTypeId, type);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public ListTypeService getWrappedListTypeService() {
		return _listTypeService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedListTypeService(ListTypeService listTypeService) {
		_listTypeService = listTypeService;
	}

	public ListTypeService getWrappedService() {
		return _listTypeService;
	}

	public void setWrappedService(ListTypeService listTypeService) {
		_listTypeService = listTypeService;
	}

	private ListTypeService _listTypeService;
}