/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portal.workflow.kaleo.runtime.integration.impl.internal.test;

import com.liferay.blogs.model.BlogsEntry;
import com.liferay.blogs.service.BlogsEntryLocalServiceUtil;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.model.DDLRecordConstants;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalServiceUtil;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalServiceUtil;
import com.liferay.dynamic.data.mapping.model.DDMForm;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.model.DDMTemplate;
import com.liferay.dynamic.data.mapping.storage.DDMFormFieldValue;
import com.liferay.dynamic.data.mapping.storage.DDMFormValues;
import com.liferay.dynamic.data.mapping.storage.StorageType;
import com.liferay.dynamic.data.mapping.test.util.DDMFormTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMFormValuesTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestHelper;
import com.liferay.dynamic.data.mapping.test.util.DDMStructureTestUtil;
import com.liferay.dynamic.data.mapping.test.util.DDMTemplateTestUtil;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.model.JournalFolder;
import com.liferay.journal.model.JournalFolderConstants;
import com.liferay.journal.service.JournalArticleLocalServiceUtil;
import com.liferay.journal.service.JournalFolderLocalServiceUtil;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.model.OrganizationConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.model.RoleConstants;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.model.UserNotificationDeliveryConstants;
import com.liferay.portal.kernel.model.UserNotificationEvent;
import com.liferay.portal.kernel.security.permission.PermissionChecker;
import com.liferay.portal.kernel.security.permission.PermissionCheckerFactoryUtil;
import com.liferay.portal.kernel.security.permission.PermissionThreadLocal;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.service.RoleLocalServiceUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.UserGroupRoleLocalServiceUtil;
import com.liferay.portal.kernel.service.UserLocalServiceUtil;
import com.liferay.portal.kernel.service.UserNotificationEventLocalServiceUtil;
import com.liferay.portal.kernel.service.WorkflowDefinitionLinkLocalServiceUtil;
import com.liferay.portal.kernel.test.rule.DeleteAfterTestRun;
import com.liferay.portal.kernel.test.util.GroupTestUtil;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.test.util.UserTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.workflow.WorkflowDefinitionManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowException;
import com.liferay.portal.kernel.workflow.WorkflowInstance;
import com.liferay.portal.kernel.workflow.WorkflowInstanceManagerUtil;
import com.liferay.portal.kernel.workflow.WorkflowTask;
import com.liferay.portal.kernel.workflow.WorkflowTaskManagerUtil;
import com.liferay.portal.test.log.CaptureAppender;
import com.liferay.portal.test.log.Log4JLoggerTestUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import org.apache.log4j.Level;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;

/**
 * @author Inácio Nery
 */
public abstract class BaseWorkflowTaskManagerTestCase {

	@Before
	public void setUp() throws Exception {
		group = GroupTestUtil.addGroup();

		serviceContext = ServiceContextTestUtil.getServiceContext(
			group.getGroupId());

		_originalPermissionChecker =
			PermissionThreadLocal.getPermissionChecker();

		setUpUsers();
	}

	@After
	public void tearDown() throws PortalException {
		PermissionThreadLocal.setPermissionChecker(_originalPermissionChecker);
	}

	protected void activateSingleApproverWorkflow(
			long groupId, String className, long classPK, long typePK)
		throws PortalException {

		activateWorkflow(
			groupId, className, classPK, typePK, "Single Approver", 1);
	}

	protected void activateSingleApproverWorkflow(
			String className, long classPK, long typePK)
		throws PortalException {

		activateWorkflow(
			group.getGroupId(), className, classPK, typePK, "Single Approver",
			1);
	}

	protected void activateWorkflow(
			long groupId, String className, long classPK, long typePK,
			String workflowDefinitionName, int workflowDefinitionVersion)
		throws PortalException {

		WorkflowDefinitionLinkLocalServiceUtil.updateWorkflowDefinitionLink(
			adminUser.getUserId(), TestPropsValues.getCompanyId(), groupId,
			className, classPK, typePK, workflowDefinitionName,
			workflowDefinitionVersion);
	}

	protected BlogsEntry addBlogsEntry() throws PortalException {
		return addBlogsEntry(adminUser);
	}

	protected BlogsEntry addBlogsEntry(User user) throws PortalException {
		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_MAIL_ENGINE_CLASS_NAME, Level.OFF)) {

			return BlogsEntryLocalServiceUtil.addEntry(
				user.getUserId(), StringUtil.randomString(),
				StringUtil.randomString(), new Date(), serviceContext);
		}
	}

	protected JournalArticle addJournalArticle(long folderId) throws Exception {
		DDMStructure ddmStructure = DDMStructureTestUtil.addStructure(
			group.getGroupId(), JournalArticle.class.getName());

		return addJournalArticle(folderId, ddmStructure);
	}

	protected JournalArticle addJournalArticle(
			long folderId, DDMStructure ddmStructure)
		throws Exception {

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_MAIL_ENGINE_CLASS_NAME, Level.OFF)) {

			DDMTemplate ddmTemplate = DDMTemplateTestUtil.addTemplate(
				group.getGroupId(), ddmStructure.getStructureId(),
				PortalUtil.getClassNameId(JournalArticle.class));

			Map<Locale, String> titleMap = new HashMap<>();

			titleMap.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString());

			Map<Locale, String> descriptionMap = new HashMap<>();

			descriptionMap.put(
				LocaleUtil.getDefault(), RandomTestUtil.randomString());

			String content = DDMStructureTestUtil.getSampleStructuredContent();

			return JournalArticleLocalServiceUtil.addArticle(
				adminUser.getUserId(), group.getGroupId(), folderId, titleMap,
				descriptionMap, content, ddmStructure.getStructureKey(),
				ddmTemplate.getTemplateKey(), serviceContext);
		}
	}

	protected JournalFolder addJournalFolder() throws PortalException {
		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_MAIL_ENGINE_CLASS_NAME, Level.OFF)) {

			return JournalFolderLocalServiceUtil.addFolder(
				adminUser.getUserId(), group.getGroupId(),
				JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				RandomTestUtil.randomString(), RandomTestUtil.randomString(),
				serviceContext);
		}
	}

	protected JournalFolder addJournalFolder(
			long ddmStructureId, int restrictionType)
		throws PortalException {

		long[] ddmStructureIds = {ddmStructureId};

		JournalFolder folder = addJournalFolder();

		return JournalFolderLocalServiceUtil.updateFolder(
			adminUser.getUserId(), group.getGroupId(), folder.getFolderId(),
			JournalFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			RandomTestUtil.randomString(), RandomTestUtil.randomString(),
			ddmStructureIds, restrictionType, false, serviceContext);
	}

	protected DDLRecord addRecord(DDLRecordSet recordSet)
		throws PortalException {

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_MAIL_ENGINE_CLASS_NAME, Level.OFF)) {

			DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
				RandomTestUtil.randomString());

			DDMFormValues ddmFormValues = createDDMFormValues(ddmForm);

			return DDLRecordLocalServiceUtil.addRecord(
				adminUser.getUserId(), group.getGroupId(),
				recordSet.getRecordSetId(),
				DDLRecordConstants.DISPLAY_INDEX_DEFAULT, ddmFormValues,
				serviceContext);
		}
	}

	protected DDLRecordSet addRecordSet() throws Exception {
		DDMForm ddmForm = DDMFormTestUtil.createDDMForm(
			RandomTestUtil.randomString());

		DDMStructureTestHelper ddmStructureTestHelper =
			new DDMStructureTestHelper(
				PortalUtil.getClassNameId(DDLRecordSet.class), group);

		DDMStructure ddmStructure = ddmStructureTestHelper.addStructure(
			ddmForm, StorageType.JSON.toString());

		Map<Locale, String> nameMap = new HashMap<>();

		nameMap.put(LocaleUtil.US, RandomTestUtil.randomString());

		return DDLRecordSetLocalServiceUtil.addRecordSet(
			adminUser.getUserId(), group.getGroupId(),
			ddmStructure.getStructureId(), null, nameMap, null,
			DDLRecordSetConstants.MIN_DISPLAY_ROWS_DEFAULT,
			DDLRecordSetConstants.SCOPE_DYNAMIC_DATA_LISTS, serviceContext);
	}

	protected void assignWorkflowTaskToUser(User user, User assigneeUser)
		throws Exception {

		assignWorkflowTaskToUser(user, assigneeUser, null);
	}

	protected void assignWorkflowTaskToUser(
			User user, User assigneeUser, String taskName)
		throws Exception {

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_MAIL_ENGINE_CLASS_NAME, Level.OFF)) {

			WorkflowTask workflowTask = getWorkflowTask(user, taskName, false);

			PermissionChecker userPermissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			PermissionThreadLocal.setPermissionChecker(userPermissionChecker);

			WorkflowTaskManagerUtil.assignWorkflowTaskToUser(
				group.getCompanyId(), user.getUserId(),
				workflowTask.getWorkflowTaskId(), assigneeUser.getUserId(),
				StringPool.BLANK, null, null);
		}
	}

	protected void checkUserNotificationEventsByUsers(User... users) {
		for (User user : users) {
			List<UserNotificationEvent> userNotificationEvents =
				UserNotificationEventLocalServiceUtil.
					getArchivedUserNotificationEvents(
						user.getUserId(),
						UserNotificationDeliveryConstants.TYPE_WEBSITE, false);

			Assert.assertEquals(
				userNotificationEvents.toString(), 1,
				userNotificationEvents.size());

			UserNotificationEvent userNotificationEvent =
				userNotificationEvents.get(0);

			userNotificationEvent.setArchived(true);

			UserNotificationEventLocalServiceUtil.updateUserNotificationEvent(
				userNotificationEvent);
		}
	}

	protected void checkWorkflowInstance(String className, long classPK)
		throws WorkflowException {

		List<WorkflowInstance> workflowInstances =
			WorkflowInstanceManagerUtil.getWorkflowInstances(
				adminUser.getCompanyId(), adminUser.getUserId(), className,
				classPK, true, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);

		Assert.assertEquals(
			workflowInstances.toString(), 1, workflowInstances.size());
	}

	protected void completeWorkflowTask(User user, String transition)
		throws Exception {

		completeWorkflowTask(user, transition, null);
	}

	protected void completeWorkflowTask(
			User user, String transition, String taskName)
		throws Exception {

		try (CaptureAppender captureAppender =
				Log4JLoggerTestUtil.configureLog4JLogger(
					_MAIL_ENGINE_CLASS_NAME, Level.OFF)) {

			WorkflowTask workflowTask = getWorkflowTask(user, taskName, false);

			PermissionChecker userPermissionChecker =
				PermissionCheckerFactoryUtil.create(user);

			PermissionThreadLocal.setPermissionChecker(userPermissionChecker);

			WorkflowTaskManagerUtil.completeWorkflowTask(
				group.getCompanyId(), user.getUserId(),
				workflowTask.getWorkflowTaskId(), transition, StringPool.BLANK,
				null);
		}
	}

	protected DDMFormValues createDDMFormValues(DDMForm ddmForm) {
		DDMFormValues ddmFormValues = DDMFormValuesTestUtil.createDDMFormValues(
			ddmForm);

		DDMFormFieldValue ddmFormFieldValue =
			DDMFormValuesTestUtil.createLocalizedDDMFormFieldValue(
				RandomTestUtil.randomString(), StringPool.BLANK);

		ddmFormValues.addDDMFormFieldValue(ddmFormFieldValue);

		return ddmFormValues;
	}

	protected void createJoinXorWorkflow() throws Exception {
		String content = read("join-xor-definition.xml");

		WorkflowDefinitionManagerUtil.deployWorkflowDefinition(
			adminUser.getCompanyId(), adminUser.getUserId(), "Join Xor",
			content.getBytes());
	}

	protected Organization createOrganization() throws PortalException {
		return createOrganization(
			OrganizationConstants.DEFAULT_PARENT_ORGANIZATION_ID);
	}

	protected Organization createOrganization(long parentOrganizationId)
		throws PortalException {

		return OrganizationLocalServiceUtil.addOrganization(
			adminUser.getUserId(), parentOrganizationId,
			StringUtil.randomString(), true);
	}

	protected User createUser(String roleName) throws Exception {
		return createUser(roleName, group);
	}

	protected User createUser(String roleName, Group group) throws Exception {
		User user = UserTestUtil.addUser(group.getGroupId());

		Role role = RoleLocalServiceUtil.getRole(
			TestPropsValues.getCompanyId(), roleName);

		UserLocalServiceUtil.addRoleUser(role.getRoleId(), user);

		long[] userIds = {user.getUserId()};

		UserGroupRoleLocalServiceUtil.addUserGroupRoles(
			userIds, group.getGroupId(), role.getRoleId());

		return user;
	}

	protected void deactivateWorkflow(
			long groupId, String className, long classPK, long typePK)
		throws PortalException {

		WorkflowDefinitionLinkLocalServiceUtil.updateWorkflowDefinitionLink(
			adminUser.getUserId(), TestPropsValues.getCompanyId(), groupId,
			className, classPK, typePK, null);
	}

	protected void deactivateWorkflow(
			String className, long classPK, long typePK)
		throws PortalException {

		deactivateWorkflow(group.getGroupId(), className, classPK, typePK);
	}

	protected String getBasePath() {
		return "com/liferay/portal/workflow/kaleo/dependencies/";
	}

	protected WorkflowTask getWorkflowTask() throws WorkflowException {
		return getWorkflowTask(adminUser, null, false);
	}

	protected WorkflowTask getWorkflowTask(
			User user, String taskName, boolean completed)
		throws WorkflowException {

		List<WorkflowTask> workflowTasks = new ArrayList<>();

		workflowTasks.addAll(
			WorkflowTaskManagerUtil.getWorkflowTasksByUserRoles(
				user.getCompanyId(), user.getUserId(), completed,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null));

		workflowTasks.addAll(
			WorkflowTaskManagerUtil.getWorkflowTasksByUser(
				user.getCompanyId(), user.getUserId(), completed,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null));

		for (WorkflowTask workflowTask : workflowTasks) {
			if (Objects.equals(taskName, workflowTask.getName())) {
				return workflowTask;
			}
		}

		Assert.assertEquals(workflowTasks.toString(), 1, workflowTasks.size());

		return workflowTasks.get(0);
	}

	protected String read(String fileName) throws Exception {
		Class<?> clazz = getClass();

		return StringUtil.read(
			clazz.getClassLoader(), getBasePath() + fileName);
	}

	protected void setUpUsers() throws Exception {
		adminUser = createUser(RoleConstants.ADMINISTRATOR);

		_users.add(adminUser);

		portalContentReviewerUser = createUser(
			RoleConstants.PORTAL_CONTENT_REVIEWER);

		_users.add(portalContentReviewerUser);

		siteAdminUser = createUser(RoleConstants.SITE_ADMINISTRATOR);

		_users.add(siteAdminUser);
	}

	protected User adminUser;

	@DeleteAfterTestRun
	protected Group group;

	protected User portalContentReviewerUser;
	protected ServiceContext serviceContext;
	protected User siteAdminUser;

	private static final String _MAIL_ENGINE_CLASS_NAME =
		"com.liferay.util.mail.MailEngine";

	private PermissionChecker _originalPermissionChecker;

	@DeleteAfterTestRun
	private final List<User> _users = new ArrayList<>();

}