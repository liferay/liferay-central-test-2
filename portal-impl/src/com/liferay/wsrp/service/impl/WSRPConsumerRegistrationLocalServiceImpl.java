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

package com.liferay.wsrp.service.impl;

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.wsrp.model.WSRPConsumerRegistration;
import com.liferay.wsrp.service.base.WSRPConsumerRegistrationLocalServiceBaseImpl;

/**
 * <a href="WSRPConsumerRegistrationLocalServiceImpl.java.html">
 * <b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Manish Gupta
 *
 */
public class WSRPConsumerRegistrationLocalServiceImpl
	extends WSRPConsumerRegistrationLocalServiceBaseImpl {

	public void addConsumerRegistration(
			String consumerName, boolean status, String registrationHandle,
			String consumerAgent, boolean methodGetSupported,
			String consumerModes, String consumerWindowStates,
			String consumerUserScopes, String customUserProfileData,
			String lifetimeTermination, String producerKey)
		throws PortalException,	SystemException {

		long consumerRegistrationId = counterLocalService.increment();
		WSRPConsumerRegistration consumerRegistration =
				wsrpConsumerRegistrationPersistence.create(
					consumerRegistrationId);

		consumerRegistration.setConsumerAgent(consumerAgent);
		consumerRegistration.setConsumerName(consumerName);
		consumerRegistration.setConsumerModes(consumerModes);
		consumerRegistration.setConsumerWindowStates(consumerWindowStates);
		consumerRegistration.setCustomUserProfileData(customUserProfileData);
		consumerRegistration.setConsumerUserScopes(consumerUserScopes);
		consumerRegistration.setLifetimeTerminationTime(lifetimeTermination);
		consumerRegistration.setMethodGetSupported(methodGetSupported);
		consumerRegistration.setProducerKey(producerKey);
		consumerRegistration.setRegistrationHandle(registrationHandle);
		consumerRegistration.setStatus(status);

		wsrpConsumerRegistrationPersistence.update(consumerRegistration, false);
	}

}