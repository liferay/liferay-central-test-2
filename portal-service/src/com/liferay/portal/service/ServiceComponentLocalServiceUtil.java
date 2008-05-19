/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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
 * <a href="ServiceComponentLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portal.service.ServiceComponentLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portal.service.ServiceComponentLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portal.service.ServiceComponentLocalService
 * @see com.liferay.portal.service.ServiceComponentLocalServiceFactory
 *
 */
public class ServiceComponentLocalServiceUtil {
	public static com.liferay.portal.model.ServiceComponent addServiceComponent(
		com.liferay.portal.model.ServiceComponent serviceComponent)
		throws com.liferay.portal.SystemException {
		ServiceComponentLocalService serviceComponentLocalService = ServiceComponentLocalServiceFactory.getService();

		return serviceComponentLocalService.addServiceComponent(serviceComponent);
	}

	public static void deleteServiceComponent(long serviceComponentId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ServiceComponentLocalService serviceComponentLocalService = ServiceComponentLocalServiceFactory.getService();

		serviceComponentLocalService.deleteServiceComponent(serviceComponentId);
	}

	public static void deleteServiceComponent(
		com.liferay.portal.model.ServiceComponent serviceComponent)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ServiceComponentLocalService serviceComponentLocalService = ServiceComponentLocalServiceFactory.getService();

		serviceComponentLocalService.deleteServiceComponent(serviceComponent);
	}

	public static java.util.List<com.liferay.portal.model.ServiceComponent> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		ServiceComponentLocalService serviceComponentLocalService = ServiceComponentLocalServiceFactory.getService();

		return serviceComponentLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portal.model.ServiceComponent> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int start, int end) throws com.liferay.portal.SystemException {
		ServiceComponentLocalService serviceComponentLocalService = ServiceComponentLocalServiceFactory.getService();

		return serviceComponentLocalService.dynamicQuery(queryInitializer,
			start, end);
	}

	public static com.liferay.portal.model.ServiceComponent updateServiceComponent(
		com.liferay.portal.model.ServiceComponent serviceComponent)
		throws com.liferay.portal.SystemException {
		ServiceComponentLocalService serviceComponentLocalService = ServiceComponentLocalServiceFactory.getService();

		return serviceComponentLocalService.updateServiceComponent(serviceComponent);
	}

	public static com.liferay.portal.model.ServiceComponent updateServiceComponent(
		javax.servlet.ServletContext ctx,
		java.lang.ClassLoader portletClassLoader,
		java.lang.String buildNamespace, long buildNumber, long buildDate)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		ServiceComponentLocalService serviceComponentLocalService = ServiceComponentLocalServiceFactory.getService();

		return serviceComponentLocalService.updateServiceComponent(ctx,
			portletClassLoader, buildNamespace, buildNumber, buildDate);
	}
}