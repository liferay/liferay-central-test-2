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

package com.liferay.portlet.softwarecatalog.service;


/**
 * <a href="SCFrameworkVersionServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link SCFrameworkVersionService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       SCFrameworkVersionService
 * @generated
 */
public class SCFrameworkVersionServiceWrapper
	implements SCFrameworkVersionService {
	public SCFrameworkVersionServiceWrapper(
		SCFrameworkVersionService scFrameworkVersionService) {
		_scFrameworkVersionService = scFrameworkVersionService;
	}

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion addFrameworkVersion(
		java.lang.String name, java.lang.String url, boolean active,
		int priority, com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _scFrameworkVersionService.addFrameworkVersion(name, url,
			active, priority, serviceContext);
	}

	public void deleteFrameworkVersion(long frameworkVersionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_scFrameworkVersionService.deleteFrameworkVersion(frameworkVersionId);
	}

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion getFrameworkVersion(
		long frameworkVersionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _scFrameworkVersionService.getFrameworkVersion(frameworkVersionId);
	}

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getFrameworkVersions(
		long groupId, boolean active) throws com.liferay.portal.SystemException {
		return _scFrameworkVersionService.getFrameworkVersions(groupId, active);
	}

	public java.util.List<com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion> getFrameworkVersions(
		long groupId, boolean active, int start, int end)
		throws com.liferay.portal.SystemException {
		return _scFrameworkVersionService.getFrameworkVersions(groupId, active,
			start, end);
	}

	public com.liferay.portlet.softwarecatalog.model.SCFrameworkVersion updateFrameworkVersion(
		long frameworkVersionId, java.lang.String name, java.lang.String url,
		boolean active, int priority)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _scFrameworkVersionService.updateFrameworkVersion(frameworkVersionId,
			name, url, active, priority);
	}

	public SCFrameworkVersionService getWrappedSCFrameworkVersionService() {
		return _scFrameworkVersionService;
	}

	private SCFrameworkVersionService _scFrameworkVersionService;
}