/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.jcr.JCRFactoryUtil;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.sender.MessageSender;
import com.liferay.portal.kernel.messaging.sender.SynchronousMessageSender;
import com.liferay.portal.kernel.scheduler.SchedulerEngine;
import com.liferay.portal.kernel.scheduler.SchedulerEngineUtil;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletCategory;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.scheduler.SchedulerEngineProxy;
import com.liferay.portal.search.lucene.LuceneHelperUtil;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portal.util.WebAppPool;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.bookmarks.util.BookmarksIndexer;
import com.liferay.portlet.documentlibrary.util.DLIndexer;
import com.liferay.portlet.enterpriseadmin.util.UserIndexer;
import com.liferay.portlet.imagegallery.util.IGIndexer;
import com.liferay.portlet.messageboards.util.MBIndexer;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * <a href="ServiceTestUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 * @author Michael Young
 * @author Alexander Chow
 */
public class ServiceTestUtil {

	public static void destroyServices() {
		LuceneHelperUtil.shutdown();

		FileUtil.deltree(PropsValues.LIFERAY_HOME + "/data");
	}

	public static void initPermissions() {
		if (System.getProperty("external-properties") == null) {
			System.setProperty("external-properties", "portal-test.properties");
		}

		InitUtil.initWithSpring();

		PortalInstances.addCompanyId(TestPropsValues.COMPANY_ID);

		PrincipalThreadLocal.setName(TestPropsValues.USER_ID);

		try {
			User user = UserLocalServiceUtil.getUserById(
				TestPropsValues.USER_ID);

			PermissionChecker permissionChecker =
				PermissionCheckerFactoryUtil.create(user, true);

			PermissionThreadLocal.setPermissionChecker(permissionChecker);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void initServices() {
		InitUtil.initWithSpring();

		FileUtil.deltree(PropsValues.LIFERAY_HOME + "/data");

		// JCR

		try {
			JCRFactoryUtil.prepare();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// Upgrade

		try {
			DBUpgrader.upgrade();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

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

		// Verify

		try {
			DBUpgrader.verify();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// Resource actions

		try {
			_checkResourceActions();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// Indexers

		IndexerRegistryUtil.register(new UserIndexer());
		IndexerRegistryUtil.register(new BookmarksIndexer());
		IndexerRegistryUtil.register(new DLIndexer());
		IndexerRegistryUtil.register(new IGIndexer());
		IndexerRegistryUtil.register(new MBIndexer());

		// Company

		try {
			CompanyLocalServiceUtil.checkCompany(
				TestPropsValues.COMPANY_WEB_ID);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void _checkResourceActions() throws Exception {
		if (PropsValues.PERMISSIONS_USER_CHECK_ALGORITHM != 6) {
			return;
		}

		WebAppPool.put(
			String.valueOf(TestPropsValues.COMPANY_ID),
			WebKeys.PORTLET_CATEGORY, new PortletCategory());

		for (int i = 0; i < 200; i++) {
			String portletId = String.valueOf(i);

			Portlet portlet = new PortletImpl();

			portlet.setPortletId(portletId);
			portlet.setCompanyId(TestPropsValues.COMPANY_ID);
			portlet.setPortletModes(new HashMap<String, Set<String>>());

			PortletLocalServiceUtil.deployRemotePortlet(portlet);

			List<String> portletActions =
				ResourceActionsUtil.getPortletResourceActions(portletId);

			ResourceActionLocalServiceUtil.checkResourceActions(
				portletId, portletActions);

			List<String> modelNames =
				ResourceActionsUtil.getPortletModelResources(portletId);

			for (String modelName : modelNames) {
				List<String> modelActions =
					ResourceActionsUtil.getModelResourceActions(modelName);

				ResourceActionLocalServiceUtil.checkResourceActions(
					modelName, modelActions);
			}
		}
	}

}