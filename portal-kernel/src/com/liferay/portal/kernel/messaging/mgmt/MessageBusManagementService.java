/*
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

package com.liferay.portal.kernel.messaging.mgmt;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.messaging.Destination;
import com.liferay.portal.kernel.messaging.DestinationEventListener;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusManagement;

import javax.management.MBeanServer;
import java.util.Collection;

/**
 * <a href="MessageBusManagmentConsole.java.html"><b><i>View Source</i></b></a>
 * <p/>
 * Conveninence class to ensure we properly register all appropriate MBeans for
 * managing and monitoring the internal message bus.  This service listens to
 * destination evens so when new destinations are added, we will automatically
 * register the destinations for monitoring.
 *
 * @author Michael C. Han
 */
public class MessageBusManagementService
        implements DestinationEventListener {

    public void setMBeanServer(MBeanServer server) {
        _mbeanServer = server;
    }

    public void setMessageBus(MessageBus bus) {
        _bus = bus;
    }

    public void destinationAdded(final Destination destination) {
        try {
            _registerDestination(destination);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Unable to register destination: " +
                        destination.getName(), e);
            }
        }
    }

    public void destinationRemoved(final Destination destination) {
        try {
            _unregisterDestination(destination);
        } catch (Exception e) {
            if (log.isErrorEnabled()) {
                log.error("Unable to register destination: " +
                        destination.getName(), e);
            }
        }
    }

    public void destroy() throws Exception {
        _mbeanServer.unregisterMBean(MessageBusManager.createObjectName());
    }

    public void init() throws Exception {
        if (_mbeanServer == null || _bus == null) {
            throw new IllegalStateException("Must configure a mbean server " +
                    "and a message bus");
        }
        _mbeanServer.registerMBean(new MessageBusManager(_bus),
                                   MessageBusManager.createObjectName());

        Collection<Destination> destinations =
                ((MessageBusManagement) _bus).getDestinations();

        for (Destination destination : destinations) {
            _registerDestination(destination);
        }
    }

    private void _registerDestination(final Destination destination)
            throws Exception {
        _mbeanServer.registerMBean(new DestinationManager(destination),
                                   DestinationManager.createObjectName(
                                           destination.getName()));
        _mbeanServer.registerMBean(
                new DestinationStatisticsManager(destination),
                DestinationStatisticsManager.createObjectName(
                        destination.getName()));
    }

    private void _unregisterDestination(final Destination destination)
            throws Exception {
        _mbeanServer.unregisterMBean(DestinationManager.createObjectName(
                destination.getName()));
        _mbeanServer.unregisterMBean(
                DestinationStatisticsManager.createObjectName(
                        destination.getName()));
    }


    private static final Log log =
            LogFactoryUtil.getLog(MessageBusManagementService.class);
    private MBeanServer _mbeanServer;
    private MessageBus _bus;

}
