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
 * <a href="LayoutSetPrototypeLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link LayoutSetPrototypeLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutSetPrototypeLocalService
 * @generated
 */
public class LayoutSetPrototypeLocalServiceWrapper
	implements LayoutSetPrototypeLocalService {
	public LayoutSetPrototypeLocalServiceWrapper(
		LayoutSetPrototypeLocalService layoutSetPrototypeLocalService) {
		_layoutSetPrototypeLocalService = layoutSetPrototypeLocalService;
	}

	public com.liferay.portal.model.LayoutSetPrototype addLayoutSetPrototype(
		com.liferay.portal.model.LayoutSetPrototype layoutSetPrototype)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutSetPrototypeLocalService.addLayoutSetPrototype(layoutSetPrototype);
	}

	public com.liferay.portal.model.LayoutSetPrototype createLayoutSetPrototype(
		long layoutSetPrototypeId) {
		return _layoutSetPrototypeLocalService.createLayoutSetPrototype(layoutSetPrototypeId);
	}

	public void deleteLayoutSetPrototype(long layoutSetPrototypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_layoutSetPrototypeLocalService.deleteLayoutSetPrototype(layoutSetPrototypeId);
	}

	public void deleteLayoutSetPrototype(
		com.liferay.portal.model.LayoutSetPrototype layoutSetPrototype)
		throws com.liferay.portal.kernel.exception.SystemException {
		_layoutSetPrototypeLocalService.deleteLayoutSetPrototype(layoutSetPrototype);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutSetPrototypeLocalService.dynamicQuery(dynamicQuery);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutSetPrototypeLocalService.dynamicQuery(dynamicQuery,
			start, end);
	}

	@SuppressWarnings("unchecked")
	public java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutSetPrototypeLocalService.dynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	public long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutSetPrototypeLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portal.model.LayoutSetPrototype getLayoutSetPrototype(
		long layoutSetPrototypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutSetPrototypeLocalService.getLayoutSetPrototype(layoutSetPrototypeId);
	}

	public java.util.List<com.liferay.portal.model.LayoutSetPrototype> getLayoutSetPrototypes(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutSetPrototypeLocalService.getLayoutSetPrototypes(start, end);
	}

	public int getLayoutSetPrototypesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutSetPrototypeLocalService.getLayoutSetPrototypesCount();
	}

	public com.liferay.portal.model.LayoutSetPrototype updateLayoutSetPrototype(
		com.liferay.portal.model.LayoutSetPrototype layoutSetPrototype)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutSetPrototypeLocalService.updateLayoutSetPrototype(layoutSetPrototype);
	}

	public com.liferay.portal.model.LayoutSetPrototype updateLayoutSetPrototype(
		com.liferay.portal.model.LayoutSetPrototype layoutSetPrototype,
		boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutSetPrototypeLocalService.updateLayoutSetPrototype(layoutSetPrototype,
			merge);
	}

	public com.liferay.portal.model.LayoutSetPrototype addLayoutSetPrototype(
		long userId, long companyId,
		java.util.Map<java.util.Locale, String> nameMap,
		java.lang.String description, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutSetPrototypeLocalService.addLayoutSetPrototype(userId,
			companyId, nameMap, description, active);
	}

	public java.util.List<com.liferay.portal.model.LayoutSetPrototype> search(
		long companyId, java.lang.Boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutSetPrototypeLocalService.search(companyId, active, start,
			end, obc);
	}

	public int searchCount(long companyId, java.lang.Boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutSetPrototypeLocalService.searchCount(companyId, active);
	}

	public com.liferay.portal.model.LayoutSetPrototype updateLayoutSetPrototype(
		long layoutSetPrototypeId,
		java.util.Map<java.util.Locale, String> nameMap,
		java.lang.String description, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutSetPrototypeLocalService.updateLayoutSetPrototype(layoutSetPrototypeId,
			nameMap, description, active);
	}

	public LayoutSetPrototypeLocalService getWrappedLayoutSetPrototypeLocalService() {
		return _layoutSetPrototypeLocalService;
	}

	private LayoutSetPrototypeLocalService _layoutSetPrototypeLocalService;
}