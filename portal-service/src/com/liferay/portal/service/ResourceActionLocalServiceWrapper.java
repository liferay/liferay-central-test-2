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
 * <a href="ResourceActionLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link ResourceActionLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       ResourceActionLocalService
 * @generated
 */
public class ResourceActionLocalServiceWrapper
	implements ResourceActionLocalService {
	public ResourceActionLocalServiceWrapper(
		ResourceActionLocalService resourceActionLocalService) {
		_resourceActionLocalService = resourceActionLocalService;
	}

	public com.liferay.portal.model.ResourceAction addResourceAction(
		com.liferay.portal.model.ResourceAction resourceAction)
		throws com.liferay.portal.SystemException {
		return _resourceActionLocalService.addResourceAction(resourceAction);
	}

	public com.liferay.portal.model.ResourceAction createResourceAction(
		long resourceActionId) {
		return _resourceActionLocalService.createResourceAction(resourceActionId);
	}

	public void deleteResourceAction(long resourceActionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_resourceActionLocalService.deleteResourceAction(resourceActionId);
	}

	public void deleteResourceAction(
		com.liferay.portal.model.ResourceAction resourceAction)
		throws com.liferay.portal.SystemException {
		_resourceActionLocalService.deleteResourceAction(resourceAction);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _resourceActionLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _resourceActionLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portal.model.ResourceAction getResourceAction(
		long resourceActionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _resourceActionLocalService.getResourceAction(resourceActionId);
	}

	public java.util.List<com.liferay.portal.model.ResourceAction> getResourceActions(
		int start, int end) throws com.liferay.portal.SystemException {
		return _resourceActionLocalService.getResourceActions(start, end);
	}

	public int getResourceActionsCount()
		throws com.liferay.portal.SystemException {
		return _resourceActionLocalService.getResourceActionsCount();
	}

	public com.liferay.portal.model.ResourceAction updateResourceAction(
		com.liferay.portal.model.ResourceAction resourceAction)
		throws com.liferay.portal.SystemException {
		return _resourceActionLocalService.updateResourceAction(resourceAction);
	}

	public com.liferay.portal.model.ResourceAction updateResourceAction(
		com.liferay.portal.model.ResourceAction resourceAction, boolean merge)
		throws com.liferay.portal.SystemException {
		return _resourceActionLocalService.updateResourceAction(resourceAction,
			merge);
	}

	public void checkResourceActions()
		throws com.liferay.portal.SystemException {
		_resourceActionLocalService.checkResourceActions();
	}

	public void checkResourceActions(java.lang.String name,
		java.util.List<String> actionIds)
		throws com.liferay.portal.SystemException {
		_resourceActionLocalService.checkResourceActions(name, actionIds);
	}

	public com.liferay.portal.model.ResourceAction getResourceAction(
		java.lang.String name, java.lang.String actionId)
		throws com.liferay.portal.PortalException {
		return _resourceActionLocalService.getResourceAction(name, actionId);
	}

	public java.util.List<com.liferay.portal.model.ResourceAction> getResourceActions(
		java.lang.String name) throws com.liferay.portal.SystemException {
		return _resourceActionLocalService.getResourceActions(name);
	}

	public ResourceActionLocalService getWrappedResourceActionLocalService() {
		return _resourceActionLocalService;
	}

	private ResourceActionLocalService _resourceActionLocalService;
}