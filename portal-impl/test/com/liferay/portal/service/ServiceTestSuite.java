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

import com.liferay.counter.service.CounterServiceTest;
import com.liferay.portal.jcr.JCRFactoryUtil;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.sender.MessageSender;
import com.liferay.portal.kernel.messaging.sender.SynchronousMessageSender;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.SchedulerEngineUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.scheduler.SchedulerEngineProxy;
import com.liferay.portal.search.lucene.LuceneUtil;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.bookmarks.service.BookmarksEntryServiceTest;
import com.liferay.portlet.bookmarks.service.BookmarksFolderServiceTest;
import com.liferay.portlet.documentlibrary.service.DLFileEntryServiceTest;
import com.liferay.portlet.imagegallery.service.IGImageServiceTest;
import com.liferay.portlet.messageboards.service.MBMessageServiceTest;
import com.liferay.portlet.social.service.SocialRelationLocalServiceTest;

import junit.framework.Test;
import junit.framework.TestSuite;

public class ServiceTestSuite extends TestSuite {

	public static Test suite() {
		InitUtil.initWithSpring();

		FileUtil.deltree(PropsValues.LIFERAY_HOME + "/data");

		// JCR

		try {
			JCRFactoryUtil.prepare();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// Lucene

		LuceneUtil.checkLuceneDir(TestPropsValues.COMPANY_ID);

		// Messaging

		MessageBus messageBus = (MessageBus)PortalBeanLocatorUtil.locate(
			MessageBus.class.getName());
		MessageSender messageSender =
			(MessageSender)PortalBeanLocatorUtil.locate(
				MessageSender.class.getName());
		SynchronousMessageSender synchronousMessageSender =
			(SynchronousMessageSender)PortalBeanLocatorUtil.locate(
				SynchronousMessageSender.class.getName());

		MessageBusUtil.init(
			messageBus, messageSender, synchronousMessageSender);

		// Scheduler

		SchedulerEngine schedulerEngine =
			(SchedulerEngine)PortalBeanLocatorUtil.locate(
				SchedulerEngine.class.getName());

		SchedulerEngineUtil.init(new SchedulerEngineProxy());

		try {
			schedulerEngine.start();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// Company

		try {
			CompanyLocalServiceUtil.checkCompany(
				TestPropsValues.COMPANY_WEB_ID);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// Tests

		TestSuite testSuite = new TestSuite();

		testSuite.addTestSuite(CounterServiceTest.class);

		testSuite.addTestSuite(ResourceLocalServiceTest.class);
		testSuite.addTestSuite(UserServiceTest.class);

		testSuite.addTestSuite(BookmarksFolderServiceTest.class);
		testSuite.addTestSuite(BookmarksEntryServiceTest.class);

		testSuite.addTestSuite(DLFileEntryServiceTest.class);

		testSuite.addTestSuite(IGImageServiceTest.class);

		testSuite.addTestSuite(MBMessageServiceTest.class);

		testSuite.addTestSuite(SocialRelationLocalServiceTest.class);

		return testSuite;
	}

}