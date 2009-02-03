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

package com.liferay.portal.kernel.messaging.jmx;

import com.liferay.portal.kernel.messaging.MessageBus;
import org.jmock.integration.junit4.JMock;
import org.jmock.integration.junit4.JUnit4Mockery;
import org.junit.After;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;

/**
 * <a href="TestMessageBusMBeanImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
@RunWith(JMock.class)
public class MessageBusManagerTest {

    @Test()
    public void testRegisterMBean()
            throws Exception {
        _mbeanServer.registerMBean(
                new MessageBusManager(_context.mock(MessageBus.class)),
                                   MessageBusManager.createObjectName());

        assertTrue(
                _mbeanServer.isRegistered(MessageBusManager.createObjectName()));
    }



    @Before
    public void setup() {
        _mbeanServer = ManagementFactory.getPlatformMBeanServer();
        _context = new JUnit4Mockery();
    }

    @After
    public void tearDown() {
    }

    private MBeanServer _mbeanServer;
    private JUnit4Mockery _context;
}