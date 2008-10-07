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

package com.liferay.wsrp.service;


/**
 * <a href="WSRPConfiguredProducerLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.wsrp.service.WSRPConfiguredProducerLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.wsrp.service.WSRPConfiguredProducerLocalService
 *
 */
public class WSRPConfiguredProducerLocalServiceUtil {
	public static com.liferay.wsrp.model.WSRPConfiguredProducer addWSRPConfiguredProducer(
		com.liferay.wsrp.model.WSRPConfiguredProducer wsrpConfiguredProducer)
		throws com.liferay.portal.SystemException {
		return getService().addWSRPConfiguredProducer(wsrpConfiguredProducer);
	}

	public static com.liferay.wsrp.model.WSRPConfiguredProducer createWSRPConfiguredProducer(
		long configuredProducerId) {
		return getService().createWSRPConfiguredProducer(configuredProducerId);
	}

	public static void deleteWSRPConfiguredProducer(long configuredProducerId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteWSRPConfiguredProducer(configuredProducerId);
	}

	public static void deleteWSRPConfiguredProducer(
		com.liferay.wsrp.model.WSRPConfiguredProducer wsrpConfiguredProducer)
		throws com.liferay.portal.SystemException {
		getService().deleteWSRPConfiguredProducer(wsrpConfiguredProducer);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.wsrp.model.WSRPConfiguredProducer getWSRPConfiguredProducer(
		long configuredProducerId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getWSRPConfiguredProducer(configuredProducerId);
	}

	public static java.util.List<com.liferay.wsrp.model.WSRPConfiguredProducer> getWSRPConfiguredProducers(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getWSRPConfiguredProducers(start, end);
	}

	public static int getWSRPConfiguredProducersCount()
		throws com.liferay.portal.SystemException {
		return getService().getWSRPConfiguredProducersCount();
	}

	public static com.liferay.wsrp.model.WSRPConfiguredProducer updateWSRPConfiguredProducer(
		com.liferay.wsrp.model.WSRPConfiguredProducer wsrpConfiguredProducer)
		throws com.liferay.portal.SystemException {
		return getService().updateWSRPConfiguredProducer(wsrpConfiguredProducer);
	}

	public static void addWSRPConfiguredProducer(java.lang.String name,
		java.lang.String portalId, java.lang.String namespace,
		java.lang.String producerURL, java.lang.String producerVersion,
		java.lang.String markupURL, int status,
		java.lang.String registrationData,
		java.lang.String registrationContext,
		java.lang.String serviceDescription,
		java.lang.String userCategoryMapping,
		java.lang.String customUserProfile,
		java.lang.String identityPropagationType, long sdLastModified,
		int entityVersion)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addWSRPConfiguredProducer(name, portalId, namespace, producerURL,
			producerVersion, markupURL, status, registrationData,
			registrationContext, serviceDescription, userCategoryMapping,
			customUserProfile, identityPropagationType, sdLastModified,
			entityVersion);
	}

	public static java.util.List<com.liferay.wsrp.model.WSRPConfiguredProducer> findByP_N(
		java.lang.String portalId, java.lang.String namespace)
		throws com.liferay.portal.SystemException {
		return getService().findByP_N(portalId, namespace);
	}

	public static WSRPConfiguredProducerLocalService getService() {
		if (_service == null) {
			throw new RuntimeException(
				"WSRPConfiguredProducerLocalService is not set");
		}

		return _service;
	}

	public void setService(WSRPConfiguredProducerLocalService service) {
		_service = service;
	}

	private static WSRPConfiguredProducerLocalService _service;
}