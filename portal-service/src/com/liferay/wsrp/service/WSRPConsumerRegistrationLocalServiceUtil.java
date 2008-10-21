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
 * <a href="WSRPConsumerRegistrationLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.wsrp.service.WSRPConsumerRegistrationLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.wsrp.service.WSRPConsumerRegistrationLocalService
 *
 */
public class WSRPConsumerRegistrationLocalServiceUtil {
	public static com.liferay.wsrp.model.WSRPConsumerRegistration addWSRPConsumerRegistration(
		com.liferay.wsrp.model.WSRPConsumerRegistration wsrpConsumerRegistration)
		throws com.liferay.portal.SystemException {
		return getService().addWSRPConsumerRegistration(wsrpConsumerRegistration);
	}

	public static com.liferay.wsrp.model.WSRPConsumerRegistration createWSRPConsumerRegistration(
		long registrationId) {
		return getService().createWSRPConsumerRegistration(registrationId);
	}

	public static void deleteWSRPConsumerRegistration(long registrationId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deleteWSRPConsumerRegistration(registrationId);
	}

	public static void deleteWSRPConsumerRegistration(
		com.liferay.wsrp.model.WSRPConsumerRegistration wsrpConsumerRegistration)
		throws com.liferay.portal.SystemException {
		getService().deleteWSRPConsumerRegistration(wsrpConsumerRegistration);
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

	public static com.liferay.wsrp.model.WSRPConsumerRegistration getWSRPConsumerRegistration(
		long registrationId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getWSRPConsumerRegistration(registrationId);
	}

	public static java.util.List<com.liferay.wsrp.model.WSRPConsumerRegistration> getWSRPConsumerRegistrations(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getWSRPConsumerRegistrations(start, end);
	}

	public static int getWSRPConsumerRegistrationsCount()
		throws com.liferay.portal.SystemException {
		return getService().getWSRPConsumerRegistrationsCount();
	}

	public static com.liferay.wsrp.model.WSRPConsumerRegistration updateWSRPConsumerRegistration(
		com.liferay.wsrp.model.WSRPConsumerRegistration wsrpConsumerRegistration)
		throws com.liferay.portal.SystemException {
		return getService()
				   .updateWSRPConsumerRegistration(wsrpConsumerRegistration);
	}

	public static void addConsumerRegistration(java.lang.String consumerName,
		boolean status, java.lang.String registrationHandle,
		java.lang.String consumerAgent, boolean methodGetSupported,
		java.lang.String consumerModes, java.lang.String consumerWindowStates,
		java.lang.String consumerUserScopes,
		java.lang.String customUserProfileData,
		java.lang.String lifetimeTermination, java.lang.String producerKey)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService()
			.addConsumerRegistration(consumerName, status, registrationHandle,
			consumerAgent, methodGetSupported, consumerModes,
			consumerWindowStates, consumerUserScopes, customUserProfileData,
			lifetimeTermination, producerKey);
	}

	public static WSRPConsumerRegistrationLocalService getService() {
		if (_service == null) {
			throw new RuntimeException(
				"WSRPConsumerRegistrationLocalService is not set");
		}

		return _service;
	}

	public void setService(WSRPConsumerRegistrationLocalService service) {
		_service = service;
	}

	private static WSRPConsumerRegistrationLocalService _service;
}