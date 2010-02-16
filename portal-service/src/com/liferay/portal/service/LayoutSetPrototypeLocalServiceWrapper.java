/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
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

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutSetPrototypeLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return _layoutSetPrototypeLocalService.dynamicQuery(dynamicQuery,
			start, end);
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