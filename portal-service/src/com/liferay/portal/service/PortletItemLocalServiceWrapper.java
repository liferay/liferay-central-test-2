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
 * <a href="PortletItemLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link PortletItemLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PortletItemLocalService
 * @generated
 */
public class PortletItemLocalServiceWrapper implements PortletItemLocalService {
	public PortletItemLocalServiceWrapper(
		PortletItemLocalService portletItemLocalService) {
		_portletItemLocalService = portletItemLocalService;
	}

	public com.liferay.portal.model.PortletItem addPortletItem(
		com.liferay.portal.model.PortletItem portletItem)
		throws com.liferay.portal.SystemException {
		return _portletItemLocalService.addPortletItem(portletItem);
	}

	public com.liferay.portal.model.PortletItem createPortletItem(
		long portletItemId) {
		return _portletItemLocalService.createPortletItem(portletItemId);
	}

	public void deletePortletItem(long portletItemId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_portletItemLocalService.deletePortletItem(portletItemId);
	}

	public void deletePortletItem(
		com.liferay.portal.model.PortletItem portletItem)
		throws com.liferay.portal.SystemException {
		_portletItemLocalService.deletePortletItem(portletItem);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _portletItemLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _portletItemLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portal.model.PortletItem getPortletItem(
		long portletItemId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _portletItemLocalService.getPortletItem(portletItemId);
	}

	public java.util.List<com.liferay.portal.model.PortletItem> getPortletItems(
		int start, int end) throws com.liferay.portal.SystemException {
		return _portletItemLocalService.getPortletItems(start, end);
	}

	public int getPortletItemsCount() throws com.liferay.portal.SystemException {
		return _portletItemLocalService.getPortletItemsCount();
	}

	public com.liferay.portal.model.PortletItem updatePortletItem(
		com.liferay.portal.model.PortletItem portletItem)
		throws com.liferay.portal.SystemException {
		return _portletItemLocalService.updatePortletItem(portletItem);
	}

	public com.liferay.portal.model.PortletItem updatePortletItem(
		com.liferay.portal.model.PortletItem portletItem, boolean merge)
		throws com.liferay.portal.SystemException {
		return _portletItemLocalService.updatePortletItem(portletItem, merge);
	}

	public com.liferay.portal.model.PortletItem addPortletItem(long userId,
		long groupId, java.lang.String name, java.lang.String portletId,
		java.lang.String className)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _portletItemLocalService.addPortletItem(userId, groupId, name,
			portletId, className);
	}

	public com.liferay.portal.model.PortletItem getPortletItem(long groupId,
		java.lang.String name, java.lang.String portletId,
		java.lang.String className)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _portletItemLocalService.getPortletItem(groupId, name,
			portletId, className);
	}

	public java.util.List<com.liferay.portal.model.PortletItem> getPortletItems(
		long groupId, java.lang.String className)
		throws com.liferay.portal.SystemException {
		return _portletItemLocalService.getPortletItems(groupId, className);
	}

	public java.util.List<com.liferay.portal.model.PortletItem> getPortletItems(
		long groupId, java.lang.String portletId, java.lang.String className)
		throws com.liferay.portal.SystemException {
		return _portletItemLocalService.getPortletItems(groupId, portletId,
			className);
	}

	public com.liferay.portal.model.PortletItem updatePortletItem(long userId,
		long groupId, java.lang.String name, java.lang.String portletId,
		java.lang.String className)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _portletItemLocalService.updatePortletItem(userId, groupId,
			name, portletId, className);
	}

	public PortletItemLocalService getWrappedPortletItemLocalService() {
		return _portletItemLocalService;
	}

	private PortletItemLocalService _portletItemLocalService;
}