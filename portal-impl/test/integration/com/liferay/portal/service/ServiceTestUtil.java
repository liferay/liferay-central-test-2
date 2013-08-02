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

package com.liferay.portal.service;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.NoSuchLayoutException;
import com.liferay.portal.NoSuchRoleException;
import com.liferay.portal.jcr.JCRFactoryUtil;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.freemarker.FreeMarkerEngineUtil;
import com.liferay.portal.kernel.messaging.MessageBus;
import com.liferay.portal.kernel.messaging.MessageBusUtil;
import com.liferay.portal.kernel.messaging.sender.MessageSender;
import com.liferay.portal.kernel.messaging.sender.SynchronousMessageSender;
import com.liferay.portal.kernel.scheduler.SchedulerEngineHelperUtil;
import com.liferay.portal.kernel.search.IndexerRegistryUtil;
import com.liferay.portal.kernel.search.SearchContext;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.FriendlyURLNormalizerUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.velocity.VelocityEngineUtil;
import com.liferay.portal.kernel.workflow.WorkflowHandlerRegistryUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.GroupConstants;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutConstants;
import com.liferay.portal.model.LayoutPrototype;
import com.liferay.portal.model.LayoutSetPrototype;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.Role;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.search.lucene.LuceneHelperUtil;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.security.permission.PermissionChecker;
import com.liferay.portal.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.security.permission.PermissionThreadLocal;
import com.liferay.portal.security.permission.ResourceActionsUtil;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.util.InitUtil;
import com.liferay.portal.util.PortalInstances;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.TestPropsValues;
import com.liferay.portlet.asset.AssetRendererFactoryRegistryUtil;
import com.liferay.portlet.blogs.asset.BlogsEntryAssetRendererFactory;
import com.liferay.portlet.blogs.util.BlogsIndexer;
import com.liferay.portlet.blogs.workflow.BlogsEntryWorkflowHandler;
import com.liferay.portlet.bookmarks.util.BookmarksIndexer;
import com.liferay.portlet.directory.workflow.UserWorkflowHandler;
import com.liferay.portlet.documentlibrary.asset.DLFileEntryAssetRendererFactory;
import com.liferay.portlet.documentlibrary.util.DLIndexer;
import com.liferay.portlet.documentlibrary.workflow.DLFileEntryWorkflowHandler;
import com.liferay.portlet.journal.workflow.JournalArticleWorkflowHandler;
import com.liferay.portlet.messageboards.util.MBIndexer;
import com.liferay.portlet.messageboards.workflow.MBDiscussionWorkflowHandler;
import com.liferay.portlet.messageboards.workflow.MBMessageWorkflowHandler;
import com.liferay.portlet.usersadmin.util.OrganizationIndexer;
import com.liferay.portlet.usersadmin.util.UserIndexer;
import com.liferay.util.PwdGenerator;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @author Michael Young
 * @author Alexander Chow
 * @author Manuel de la Peña
 */
public class ServiceTestUtil {

	public static final int THREAD_COUNT = 25;

	public static Group addGroup() throws Exception {
		return addGroup(randomString());
	}

	public static Group addGroup(String name) throws Exception {
		Group group = GroupLocalServiceUtil.fetchGroup(
			TestPropsValues.getCompanyId(), name);

		if (group != null) {
			return group;
		}

		String description = "This is a test group.";
		int type = GroupConstants.TYPE_SITE_OPEN;
		String friendlyURL =
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(name);
		boolean site = true;
		boolean active = true;

		return GroupLocalServiceUtil.addGroup(
			TestPropsValues.getUserId(), null, 0, name, description, type,
			friendlyURL, site, active, getServiceContext());
	}

	public static Layout addLayout(long groupId, String name) throws Exception {
		return addLayout(groupId, name, false);
	}

	public static Layout addLayout(
			long groupId, String name, boolean privateLayout)
		throws Exception {

		return addLayout(groupId, name, privateLayout, null, false);
	}

	public static Layout addLayout(
			long groupId, String name, boolean privateLayout,
			LayoutPrototype layoutPrototype, boolean linkEnabled)
		throws Exception {

		String friendlyURL =
			StringPool.SLASH + FriendlyURLNormalizerUtil.normalize(name);

		Layout layout = null;

		try {
			layout = LayoutLocalServiceUtil.getFriendlyURLLayout(
				groupId, false, friendlyURL);

			return layout;
		}
		catch (NoSuchLayoutException nsle) {
		}

		String description = "This is a test page.";

		ServiceContext serviceContext = getServiceContext();

		if (layoutPrototype != null) {
			serviceContext.setAttribute(
				"layoutPrototypeLinkEnabled", linkEnabled);
			serviceContext.setAttribute(
				"layoutPrototypeUuid", layoutPrototype.getUuid());
		}

		return LayoutLocalServiceUtil.addLayout(
			TestPropsValues.getUserId(), groupId, privateLayout,
			LayoutConstants.DEFAULT_PARENT_LAYOUT_ID, name, null, description,
			LayoutConstants.TYPE_PORTLET, false, friendlyURL, serviceContext);
	}

	public static LayoutPrototype addLayoutPrototype(String name)
		throws Exception {

		HashMap<Locale, String> nameMap = new HashMap<Locale, String>();

		nameMap.put(LocaleUtil.getDefault(), name);

		return LayoutPrototypeLocalServiceUtil.addLayoutPrototype(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
			nameMap, null, true);
	}

	public static LayoutSetPrototype addLayoutSetPrototype(String name)
		throws Exception {

		HashMap<Locale, String> nameMap = new HashMap<Locale, String>();

		nameMap.put(LocaleUtil.getDefault(), name);

		return LayoutSetPrototypeLocalServiceUtil.addLayoutSetPrototype(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
			nameMap, null, true, true, getServiceContext());
	}

	public static void addResourcePermission(
			Role role, String resourceName, int scope, String primKey,
			String actionId)
		throws Exception {

		ResourcePermissionLocalServiceUtil.addResourcePermission(
			role.getCompanyId(), resourceName, scope, primKey, role.getRoleId(),
			actionId);
	}

	public static void addResourcePermission(
			String roleName, String resourceName, int scope, String primKey,
			String actionId)
		throws Exception {

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), roleName);

		addResourcePermission(role, resourceName, scope, primKey, actionId);
	}

	public static Role addRole(String roleName, int roleType) throws Exception {
		Role role = null;

		try {
			role = RoleLocalServiceUtil.getRole(
				TestPropsValues.getCompanyId(), roleName);
		}
		catch (NoSuchRoleException nsre) {
			role = RoleLocalServiceUtil.addRole(
				TestPropsValues.getUserId(), null, 0, roleName, null, null,
				roleType, null);
		}

		return role;
	}

	public static Role addRole(
			String roleName, int roleType, String resourceName, int scope,
			String primKey, String actionId)
		throws Exception {

		Role role = addRole(roleName, roleType);

		addResourcePermission(role, resourceName, scope, primKey, actionId);

		return role;
	}

	public static User addUser(
			String screenName, boolean autoScreenName, long[] groupIds)
		throws Exception {

		User user = UserLocalServiceUtil.fetchUserByScreenName(
			TestPropsValues.getCompanyId(), screenName);

		if (user != null) {
			return user;
		}

		boolean autoPassword = true;
		String password1 = StringPool.BLANK;
		String password2 = StringPool.BLANK;
		String emailAddress = "ServiceTestSuite." + nextLong() + "@liferay.com";
		long facebookId = 0;
		String openId = StringPool.BLANK;
		Locale locale = LocaleUtil.getDefault();
		String firstName = "ServiceTestSuite";
		String middleName = StringPool.BLANK;
		String lastName = "ServiceTestSuite";
		int prefixId = 0;
		int suffixId = 0;
		boolean male = true;
		int birthdayMonth = Calendar.JANUARY;
		int birthdayDay = 1;
		int birthdayYear = 1970;
		String jobTitle = StringPool.BLANK;
		long[] organizationIds = null;
		long[] roleIds = null;
		long[] userGroupIds = null;
		boolean sendMail = false;

		return UserLocalServiceUtil.addUser(
			TestPropsValues.getUserId(), TestPropsValues.getCompanyId(),
			autoPassword, password1, password2, autoScreenName, screenName,
			emailAddress, facebookId, openId, locale, firstName, middleName,
			lastName, prefixId, suffixId, male, birthdayMonth, birthdayDay,
			birthdayYear, jobTitle, groupIds, organizationIds, roleIds,
			userGroupIds, sendMail, getServiceContext());
	}

	public static User addUser(String screenName, long groupId)
		throws Exception {

		if (Validator.isNull(screenName)) {
			return addUser(null, true, new long[] {groupId});
		}
		else {
			return addUser(screenName, false, new long[] {groupId});
		}
	}

	public static void destroyServices() {
		_deleteDLDirectories();
	}

	public static SearchContext getSearchContext() throws Exception {
		return getSearchContext(TestPropsValues.getGroupId());
	}

	public static SearchContext getSearchContext(long groupId)
		throws Exception {

		SearchContext searchContext = new SearchContext();

		searchContext.setCompanyId(TestPropsValues.getCompanyId());
		searchContext.setGroupIds(new long[] {groupId});
		searchContext.setUserId(TestPropsValues.getUserId());

		return searchContext;
	}

	public static ServiceContext getServiceContext() throws Exception {
		return getServiceContext(TestPropsValues.getGroupId());
	}

	public static ServiceContext getServiceContext(long groupId)
		throws Exception {

		return getServiceContext(groupId, TestPropsValues.getUserId());
	}

	public static ServiceContext getServiceContext(long groupId, long userId)
		throws Exception {

		ServiceContext serviceContext = new ServiceContext();

		serviceContext.setAddGroupPermissions(true);
		serviceContext.setAddGuestPermissions(true);
		serviceContext.setCompanyId(TestPropsValues.getCompanyId());
		serviceContext.setScopeGroupId(groupId);
		serviceContext.setUserId(userId);

		return serviceContext;
	}

	public static void initPermissions() {
		if (System.getProperty("external-properties") == null) {
			System.setProperty("external-properties", "portal-test.properties");
		}

		InitUtil.initWithSpring();

		try {
			PortalInstances.addCompanyId(TestPropsValues.getCompanyId());

			setUser(TestPropsValues.getUser());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void initServices() {
		InitUtil.initWithSpring();

		_deleteDLDirectories();

		// JCR

		try {
			JCRFactoryUtil.prepare();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// Lucene

		try {
			FileUtil.mkdirs(
				PropsValues.LUCENE_DIR + TestPropsValues.getCompanyId());

			LuceneHelperUtil.startup(TestPropsValues.getCompanyId());
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// FreeMarker

		try {
			FreeMarkerEngineUtil.init();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// Velocity

		try {
			VelocityEngineUtil.init();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// Indexers

		IndexerRegistryUtil.register(new BlogsIndexer());
		IndexerRegistryUtil.register(new BookmarksIndexer());
		IndexerRegistryUtil.register(new DLIndexer());
		IndexerRegistryUtil.register(new MBIndexer());
		IndexerRegistryUtil.register(new OrganizationIndexer());
		IndexerRegistryUtil.register(new UserIndexer());

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

		try {
			SchedulerEngineHelperUtil.start();
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

		// Class names

		_checkClassNames();

		// Resource actions

		try {
			_checkResourceActions();
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		// Asset

		AssetRendererFactoryRegistryUtil.register(
			new BlogsEntryAssetRendererFactory());
		AssetRendererFactoryRegistryUtil.register(
			new DLFileEntryAssetRendererFactory());

		// Workflow

		WorkflowHandlerRegistryUtil.register(new BlogsEntryWorkflowHandler());
		WorkflowHandlerRegistryUtil.register(new DLFileEntryWorkflowHandler());
		WorkflowHandlerRegistryUtil.register(
			new JournalArticleWorkflowHandler());
		WorkflowHandlerRegistryUtil.register(new MBDiscussionWorkflowHandler());
		WorkflowHandlerRegistryUtil.register(new MBMessageWorkflowHandler());
		WorkflowHandlerRegistryUtil.register(new UserWorkflowHandler());

		// Company

		try {
			CompanyLocalServiceUtil.checkCompany(
				TestPropsValues.COMPANY_WEB_ID);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Date newDate() throws Exception {
		return new Date();
	}

	public static Date newDate(int month, int day, int year) throws Exception {
		Calendar calendar = new GregorianCalendar();

		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.DATE, day);
		calendar.set(Calendar.YEAR, year);

		return calendar.getTime();
	}

	public static Date nextDate() throws Exception {
		return new Date();
	}

	public static double nextDouble() throws Exception {
		return CounterLocalServiceUtil.increment();
	}

	public static int nextInt() throws Exception {
		return (int)CounterLocalServiceUtil.increment();
	}

	public static long nextLong() throws Exception {
		return CounterLocalServiceUtil.increment();
	}

	public static boolean randomBoolean() throws Exception {
		return _random.nextBoolean();
	}

	public static String randomString() throws Exception {
		return PwdGenerator.getPassword();
	}

	public static void setUser(User user) throws Exception {
		PrincipalThreadLocal.setName(user.getUserId());

		PermissionChecker permissionChecker =
			PermissionCheckerFactoryUtil.create(user);

		PermissionThreadLocal.setPermissionChecker(permissionChecker);
	}

	private static void _checkClassNames() {
		PortalUtil.getClassNameId(LiferayRepository.class.getName());
	}

	private static void _checkResourceActions() throws Exception {
		for (int i = 0; i < 200; i++) {
			String portletId = String.valueOf(i);

			Portlet portlet = new PortletImpl();

			portlet.setPortletId(portletId);
			portlet.setPortletModes(new HashMap<String, Set<String>>());

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

	private static void _deleteDLDirectories() {
		FileUtil.deltree(PropsValues.DL_STORE_FILE_SYSTEM_ROOT_DIR);

		FileUtil.deltree(
			PropsUtil.get(PropsKeys.JCR_JACKRABBIT_REPOSITORY_ROOT));

		try {
			FileUtil.deltree(
				PropsValues.LUCENE_DIR + TestPropsValues.getCompanyId());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static Random _random = new Random();

}