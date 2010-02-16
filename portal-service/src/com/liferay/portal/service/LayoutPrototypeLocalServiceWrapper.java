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