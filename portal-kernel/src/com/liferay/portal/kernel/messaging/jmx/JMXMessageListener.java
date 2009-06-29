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

package com.liferay.portal.kernel.messaging.jmx;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationEventListener;
import com.liferay.portal.kernel.messaging.MessageBus;

import java.util.Collection;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanServer;

/**
 * <a href="JMXMessageListener.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 * @author Brian Wing Shun Chan
 *
 */
public class JMXMessageListener implements DestinationEventListener {

	public void destinationAdded(Destination destination) {
		try {
			registerDestination(destination);
		}
		catch (Exception e) {
			log.error(
				"Unable to register destination " + destination.getName(), e);
		}
	}

	public void destinationRemoved(Destination destination) {
		try {
			unregisterDestination(destination);
		}
		catch (Exception e) {
			log.error(
				"Unable to unregister destination " + destination.getName(), e);
		}
	}

	public void destroy() throws Exception {
		Collection<Destination> destinations = _messageBus.getDestinations();

		for (Destination destination : destinations) {
			try {
				unregisterDestination(destination);
			}
			catch (Exception e) {
				if (log.isWarnEnabled()) {
				    log.warn(
						"Unable to unregister destination:" +
						destination.getName(), e);
				}
			}
		}

		try {
			_mBeanServer.unregisterMBean(
				MessageBusManager.createObjectName());
		}
		catch (Exception e) {
			if (log.isWarnEnabled()) {
				log.warn("Unable to unregister message bus manager", e);
			}
		}
	}

	public void init() throws Exception {
		if ((_mBeanServer == null) || (_messageBus == null)) {
			throw new IllegalStateException(
				"MBean server and message bus are not configured");
		}

		try {
			_mBeanServer.registerMBean(
				new MessageBusManager(_messageBus),
				MessageBusManager.createObjectName());
		}
		catch (InstanceAlreadyExistsException e) {
			//remove and attempt to rebind
			_mBeanServer.unregisterMBean(MessageBusManager.createObjectName());

			_mBeanServer.registerMBean(
				new MessageBusManager(_messageBus),
				MessageBusManager.createObjectName());
		}
		catch (Exception e) {
			if (log.isWarnEnabled()) {
					log.warn("Unable to register Message Bus Manager", e);
				}
		}

		Collection<Destination> destinations = _messageBus.getDestinations();

		for (Destination destination : destinations) {
			try {
				registerDestination(destination);
			}
			catch (Exception e) {
				if (log.isWarnEnabled()) {
					log.warn(
						"Unable to register destination:" +
						destination.getName(), e);
				}
			}
		}
	}

	public void setMBeanServer(MBeanServer mBeanServer) {
		_mBeanServer = mBeanServer;
	}

	public void setMessageBus(MessageBus messageBus) {
		_messageBus = messageBus;
	}

	protected void registerDestination(Destination destination)
		throws Exception {

		String destinationName = destination.getName();

		try {
			_mBeanServer.registerMBean(
			new DestinationManager(destination),
				DestinationManager.createObjectName(destinationName));
		}
		catch (InstanceAlreadyExistsException e) {
			//remove and attempt to rebind
			_mBeanServer.unregisterMBean(
				DestinationManager.createObjectName(destinationName));

			_mBeanServer.registerMBean(
			new DestinationManager(destination),
				DestinationManager.createObjectName(destinationName));
		}

		try {
			_mBeanServer.registerMBean(
			new DestinationStatisticsManager(destination),
				DestinationStatisticsManager.createObjectName(destinationName));
		}
		catch (InstanceAlreadyExistsException e) {
			//remove and attempt to rebind
			_mBeanServer.unregisterMBean(
				DestinationStatisticsManager.createObjectName(destinationName));

			_mBeanServer.registerMBean(
			new DestinationStatisticsManager(destination),
				DestinationStatisticsManager.createObjectName(destinationName));
		}
	}

	protected void unregisterDestination(Destination destination)
		throws Exception {

		String destinationName = destination.getName();

		_mBeanServer.unregisterMBean(
			DestinationManager.createObjectName(destinationName));

		_mBeanServer.unregisterMBean(
			DestinationStatisticsManager.createObjectName(destinationName));
	}

	private static Log log = LogFactoryUtil.getLog(JMXMessageListener.class);

	private MBeanServer _mBeanServer;
	private MessageBus _messageBus;

}