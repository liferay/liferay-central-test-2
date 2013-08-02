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
 * This class is a wrapper for {@link ResourceService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourceService
 * @generated
 */
public class ResourceServiceWrapper implements ResourceService,
	ServiceWrapper<ResourceService> {
	public ResourceServiceWrapper(ResourceService resourceService) {
		_resourceService = resourceService;
	}

	/**
	* Returns the Spring bean ID for this bean.
	*
	* @return the Spring bean ID for this bean
	*/
	public java.lang.String getBeanIdentifier() {
		return _resourceService.getBeanIdentifier();
	}

	/**
	* Sets the Spring bean ID for this bean.
	*
	* @param beanIdentifier the Spring bean ID for this bean
	*/
	public void setBeanIdentifier(java.lang.String beanIdentifier) {
		_resourceService.setBeanIdentifier(beanIdentifier);
	}

	public com.liferay.portal.model.Resource getResource(long companyId,
		java.lang.String name, int scope, java.lang.String primKey)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _resourceService.getResource(companyId, name, scope, primKey);
	}

	/**
	 * @deprecated Renamed to {@link #getWrappedService}
	 */
	public ResourceService getWrappedResourceService() {
		return _resourceService;
	}

	/**
	 * @deprecated Renamed to {@link #setWrappedService}
	 */
	public void setWrappedResourceService(ResourceService resourceService) {
		_resourceService = resourceService;
	}

	public ResourceService getWrappedService() {
		return _resourceService;
	}

	public void setWrappedService(ResourceService resourceService) {
		_resourceService = resourceService;
	}

	private ResourceService _resourceService;
}