/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.messaging.jmx;

import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.test.TestCase;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;

import org.jmock.integration.junit4.JUnit4Mockery;

/**
 * <a href="MessageBusManagerTest.java.html"><b><i>View Source</i></b></a>
 *
 * @author Michael C. Han
 */
public class MessageBusManagerTest extends TestCase {

	public void setUp() throws Exception {
		_jUnit4Mockery = new JUnit4Mockery();
		_mBeanServer = ManagementFactory.getPlatformMBeanServer();
	}

	public void testRegisterMBean() throws Exception {
		_mBeanServer.registerMBean(
			new MessageBusManager(_jUnit4Mockery.mock(MessageBus.class)),
			MessageBusManager.createObjectName());

		assertTrue(
			_mBeanServer.isRegistered(MessageBusManager.createObjectName()));
	}

	private JUnit4Mockery _jUnit4Mockery;
	private MBeanServer _mBeanServer;

}