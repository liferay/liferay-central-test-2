/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.util;

import com.liferay.mail.service.MailService;
import com.liferay.mail.service.MailServiceUtil;
import com.liferay.portal.kernel.log.Jdk14LogFactoryImpl;
import com.liferay.portal.kernel.log.LogFactory;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.mail.MailMessage;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.Sync;
import com.liferay.portal.test.SynchronousDestinationExecutionTestListener;
import com.liferay.portal.test.mail.MockMailServiceImpl;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Sergio González
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		SynchronousDestinationExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
@Sync
public abstract class BaseSubscriptionTestCase {

	public abstract long addContainer(long containerId) throws Exception;

	public abstract long addEntry(long containerId) throws Exception;

	public abstract void addSubscriptionContainer(long containerId)
		throws Exception;

	public abstract void addSubscriptionEntry(long entryId)
		throws Exception;

	@Before
	public void setUpClass() throws Exception {
		group = GroupTestUtil.addGroup();

		_logFactory = LogFactoryUtil.getLogFactory();

		LogFactoryUtil.setLogFactory(new Jdk14LogFactoryImpl());

		_mailService = MailServiceUtil.getService();

		MailServiceUtil mailServiceUtil = new MailServiceUtil();

		mailServiceUtil.setService(new LoggerMockMailServiceImpl());
	}

	@After
	public void tearDownClass() throws Exception {
		GroupLocalServiceUtil.deleteGroup(group);

		LogFactoryUtil.setLogFactory(_logFactory);

		MailServiceUtil mailServiceUtil = new MailServiceUtil();

		mailServiceUtil.setService(_mailService);
	}

	@Test
	public void testSubscriptionContainerWhenAddEntryInContainer()
		throws Exception {

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			LoggerMockMailServiceImpl.class.getName(), Level.INFO);

		long containerId = addContainer(DEFAULT_PARENT_CONTAINER_ID);

		addSubscriptionContainer(containerId);

		addEntry(containerId);

		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals("Sending email", logRecord.getMessage());
	}

	@Test
	public void testSubscriptionContainerWhenAddEntryInRootContainer()
		throws Exception {

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			LoggerMockMailServiceImpl.class.getName(), Level.INFO);

		long containerId = addContainer(DEFAULT_PARENT_CONTAINER_ID);

		addSubscriptionContainer(containerId);

		addEntry(DEFAULT_PARENT_CONTAINER_ID);

		Assert.assertEquals(0, logRecords.size());
	}

	@Test
	public void testSubscriptionContainerWhenAddEntryInSubcontainer()
		throws Exception {

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			LoggerMockMailServiceImpl.class.getName(), Level.INFO);

		long containerId = addContainer(DEFAULT_PARENT_CONTAINER_ID);

		addSubscriptionContainer(containerId);

		long subcontainerId = addContainer(containerId);

		addEntry(subcontainerId);

		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals("Sending email", logRecord.getMessage());
	}

	@Test
	public void testSubscriptionEntryWhenAddEntryInContainer()
		throws Exception {

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			LoggerMockMailServiceImpl.class.getName(), Level.INFO);

		long containerId = addContainer(DEFAULT_PARENT_CONTAINER_ID);

		long entryId = addEntry(containerId);

		addSubscriptionEntry(entryId);

		updateEntry(entryId);

		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals("Sending email", logRecord.getMessage());
	}

	@Test
	public void testSubscriptionEntryWhenAddEntryInRootContainer()
		throws Exception {

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			LoggerMockMailServiceImpl.class.getName(), Level.INFO);

		long entryId = addEntry(DEFAULT_PARENT_CONTAINER_ID);

		addSubscriptionEntry(entryId);

		updateEntry(entryId);

		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals("Sending email", logRecord.getMessage());
	}

	@Test
	public void testSubscriptionRootContainerWhenAddEntryInContainer()
		throws Exception {

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			LoggerMockMailServiceImpl.class.getName(), Level.INFO);

		addSubscriptionContainer(DEFAULT_PARENT_CONTAINER_ID);

		long containerId = addContainer(DEFAULT_PARENT_CONTAINER_ID);

		addEntry(containerId);

		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals("Sending email", logRecord.getMessage());
	}

	@Test
	public void testSubscriptionRootContainerWhenAddEntryInRootContainer()
		throws Exception {

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			LoggerMockMailServiceImpl.class.getName(), Level.INFO);

		addSubscriptionContainer(DEFAULT_PARENT_CONTAINER_ID);

		addEntry(DEFAULT_PARENT_CONTAINER_ID);

		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals("Sending email", logRecord.getMessage());
	}

	@Test
	public void testSubscriptionRootContainerWhenAddEntryInSubcontainer()
		throws Exception {

		List<LogRecord> logRecords = JDKLoggerTestUtil.configureJDKLogger(
			LoggerMockMailServiceImpl.class.getName(), Level.INFO);

		addSubscriptionContainer(DEFAULT_PARENT_CONTAINER_ID);

		long containerId = addContainer(DEFAULT_PARENT_CONTAINER_ID);

		long subcontainerId = addContainer(containerId);

		addEntry(subcontainerId);

		Assert.assertEquals(1, logRecords.size());

		LogRecord logRecord = logRecords.get(0);

		Assert.assertEquals("Sending email", logRecord.getMessage());
	}

	public abstract long updateEntry(long entryId) throws Exception;

	protected static final long DEFAULT_PARENT_CONTAINER_ID = 0;

	protected Group group;

	private LogFactory _logFactory;
	private MailService _mailService;

	private static class LoggerMockMailServiceImpl extends MockMailServiceImpl {

		public LoggerMockMailServiceImpl() {
			_logger.setLevel(Level.INFO);
		}

		@Override
		public void sendEmail(MailMessage mailMessage) {
			_logger.info("Sending email");
		}

		private Logger _logger = Logger.getLogger(
			LoggerMockMailServiceImpl.class.getName());

	}

}