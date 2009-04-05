/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="ResourcePermissionLocalServiceFactory.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is responsible for the lookup of the implementation for
 * <code>com.liferay.portal.service.ResourcePermissionLocalService</code>.
 * Spring manages the lookup and lifecycle of the beans. This means you can
 * modify the Spring configuration files to return a different implementation or
 * to inject additional behavior.
 * </p>
 *
 * <p>
 * See the <code>spring.configs</code> property in portal.properties for
 * additional information on how to customize the Spring XML files.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.ResourcePermissionLocalService
 * @see com.liferay.portal.service.ResourcePermissionLocalServiceUtil
 *
 */
public class ResourcePermissionLocalServiceFactory {
	/**
	 * @deprecated
	 */
	public static ResourcePermissionLocalService getService() {
		return _getFactory()._service;
	}

	/**
	 * @deprecated
	 */
	public static ResourcePermissionLocalService getImpl() {
		if (_impl == null) {
			_impl = (ResourcePermissionLocalService)PortalBeanLocatorUtil.locate(_IMPL);
		}

		return _impl;
	}

	/**
	 * @deprecated
	 */
	public static ResourcePermissionLocalService getTxImpl() {
		if (_txImpl == null) {
			_txImpl = (ResourcePermissionLocalService)PortalBeanLocatorUtil.locate(_TX_IMPL);
		}

		return _txImpl;
	}

	/**
	 * @deprecated
	 */
	public void setService(ResourcePermissionLocalService service) {
		_service = service;
	}

	private static ResourcePermissionLocalServiceFactory _getFactory() {
		if (_factory == null) {
			_factory = (ResourcePermissionLocalServiceFactory)PortalBeanLocatorUtil.locate(_FACTORY);
		}

		return _factory;
	}

	private static final String _FACTORY = ResourcePermissionLocalServiceFactory.class.getName();
	private static final String _IMPL = ResourcePermissionLocalService.class.getName() +
		".impl";
	private static final String _TX_IMPL = ResourcePermissionLocalService.class.getName() +
		".transaction";
	private static ResourcePermissionLocalServiceFactory _factory;
	private static ResourcePermissionLocalService _impl;
	private static ResourcePermissionLocalService _txImpl;
	private ResourcePermissionLocalService _service;
}