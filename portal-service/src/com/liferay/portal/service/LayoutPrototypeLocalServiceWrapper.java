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
 * <a href="LayoutPrototypeLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link LayoutPrototypeLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       LayoutPrototypeLocalService
 * @generated
 */
public class LayoutPrototypeLocalServiceWrapper
	implements LayoutPrototypeLocalService {
	public LayoutPrototypeLocalServiceWrapper(
		LayoutPrototypeLocalService layoutPrototypeLocalService) {
		_layoutPrototypeLocalService = layoutPrototypeLocalService;
	}

	public com.liferay.portal.model.LayoutPrototype addLayoutPrototype(
		com.liferay.portal.model.LayoutPrototype layoutPrototype)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutPrototypeLocalService.addLayoutPrototype(layoutPrototype);
	}

	public com.liferay.portal.model.LayoutPrototype createLayoutPrototype(
		long layoutPrototypeId) {
		return _layoutPrototypeLocalService.createLayoutPrototype(layoutPrototypeId);
	}

	public void deleteLayoutPrototype(long layoutPrototypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		_layoutPrototypeLocalService.deleteLayoutPrototype(layoutPrototypeId);
	}

	public void deleteLayoutPrototype(
		com.liferay.portal.model.LayoutPrototype layoutPrototype)
		throws com.liferay.portal.kernel.exception.SystemException {
		_layoutPrototypeLocalService.deleteLayoutPrototype(layoutPrototype);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutPrototypeLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutPrototypeLocalService.dynamicQuery(dynamicQuery, start,
			end);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutPrototypeLocalService.dynamicQuery(dynamicQuery, start,
			end, orderByComparator);
	}

	public int dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutPrototypeLocalService.dynamicQueryCount(dynamicQuery);
	}

	public com.liferay.portal.model.LayoutPrototype getLayoutPrototype(
		long layoutPrototypeId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutPrototypeLocalService.getLayoutPrototype(layoutPrototypeId);
	}

	public java.util.List<com.liferay.portal.model.LayoutPrototype> getLayoutPrototypes(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutPrototypeLocalService.getLayoutPrototypes(start, end);
	}

	public int getLayoutPrototypesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutPrototypeLocalService.getLayoutPrototypesCount();
	}

	public com.liferay.portal.model.LayoutPrototype updateLayoutPrototype(
		com.liferay.portal.model.LayoutPrototype layoutPrototype)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutPrototypeLocalService.updateLayoutPrototype(layoutPrototype);
	}

	public com.liferay.portal.model.LayoutPrototype updateLayoutPrototype(
		com.liferay.portal.model.LayoutPrototype layoutPrototype, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutPrototypeLocalService.updateLayoutPrototype(layoutPrototype,
			merge);
	}

	public com.liferay.portal.model.LayoutPrototype addLayoutPrototype(
		long userId, long companyId,
		java.util.Map<java.util.Locale, String> nameMap,
		java.lang.String description, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutPrototypeLocalService.addLayoutPrototype(userId,
			companyId, nameMap, description, active);
	}

	public java.util.List<com.liferay.portal.model.LayoutPrototype> search(
		long companyId, java.lang.Boolean active, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutPrototypeLocalService.search(companyId, active, start,
			end, obc);
	}

	public int searchCount(long companyId, java.lang.Boolean active)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutPrototypeLocalService.searchCount(companyId, active);
	}

	public com.liferay.portal.model.LayoutPrototype updateLayoutPrototype(
		long layoutPrototypeId,
		java.util.Map<java.util.Locale, String> nameMap,
		java.lang.String description, boolean active)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return _layoutPrototypeLocalService.updateLayoutPrototype(layoutPrototypeId,
			nameMap, description, active);
	}

	public LayoutPrototypeLocalService getWrappedLayoutPrototypeLocalService() {
		return _layoutPrototypeLocalService;
	}

	private LayoutPrototypeLocalService _layoutPrototypeLocalService;
}