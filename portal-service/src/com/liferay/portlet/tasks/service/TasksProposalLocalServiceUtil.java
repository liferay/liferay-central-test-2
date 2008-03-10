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

package com.liferay.portlet.tasks.service;


/**
 * <a href="TasksProposalLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.tasks.service.TasksProposalLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.tasks.service.TasksProposalLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.tasks.service.TasksProposalLocalService
 * @see com.liferay.portlet.tasks.service.TasksProposalLocalServiceFactory
 *
 */
public class TasksProposalLocalServiceUtil {
	public static com.liferay.portlet.tasks.model.TasksProposal addTasksProposal(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal)
		throws com.liferay.portal.SystemException {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		return tasksProposalLocalService.addTasksProposal(tasksProposal);
	}

	public static void deleteTasksProposal(long proposalId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		tasksProposalLocalService.deleteTasksProposal(proposalId);
	}

	public static void deleteTasksProposal(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		tasksProposalLocalService.deleteTasksProposal(tasksProposal);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksProposal> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		return tasksProposalLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksProposal> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		return tasksProposalLocalService.dynamicQuery(queryInitializer, begin,
			end);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal updateTasksProposal(
		com.liferay.portlet.tasks.model.TasksProposal tasksProposal)
		throws com.liferay.portal.SystemException {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		return tasksProposalLocalService.updateTasksProposal(tasksProposal);
	}

	public static com.liferay.portlet.tasks.service.persistence.TasksReviewPersistence getTasksReviewPersistence() {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		return tasksProposalLocalService.getTasksReviewPersistence();
	}

	public static void setTasksReviewPersistence(
		com.liferay.portlet.tasks.service.persistence.TasksReviewPersistence tasksReviewPersistence) {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		tasksProposalLocalService.setTasksReviewPersistence(tasksReviewPersistence);
	}

	public static com.liferay.portlet.tasks.service.persistence.TasksProposalPersistence getTasksProposalPersistence() {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		return tasksProposalLocalService.getTasksProposalPersistence();
	}

	public static void setTasksProposalPersistence(
		com.liferay.portlet.tasks.service.persistence.TasksProposalPersistence tasksProposalPersistence) {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		tasksProposalLocalService.setTasksProposalPersistence(tasksProposalPersistence);
	}

	public static com.liferay.portlet.tasks.service.persistence.TasksProposalFinder getTasksProposalFinder() {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		return tasksProposalLocalService.getTasksProposalFinder();
	}

	public static void setTasksProposalFinder(
		com.liferay.portlet.tasks.service.persistence.TasksProposalFinder tasksProposalFinder) {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		tasksProposalLocalService.setTasksProposalFinder(tasksProposalFinder);
	}

	public static com.liferay.portal.service.persistence.ActivityTrackerPersistence getActivityTrackerPersistence() {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		return tasksProposalLocalService.getActivityTrackerPersistence();
	}

	public static void setActivityTrackerPersistence(
		com.liferay.portal.service.persistence.ActivityTrackerPersistence activityTrackerPersistence) {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		tasksProposalLocalService.setActivityTrackerPersistence(activityTrackerPersistence);
	}

	public static com.liferay.portal.service.persistence.ActivityTrackerFinder getActivityTrackerFinder() {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		return tasksProposalLocalService.getActivityTrackerFinder();
	}

	public static void setActivityTrackerFinder(
		com.liferay.portal.service.persistence.ActivityTrackerFinder activityTrackerFinder) {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		tasksProposalLocalService.setActivityTrackerFinder(activityTrackerFinder);
	}

	public static com.liferay.portal.service.persistence.ResourcePersistence getResourcePersistence() {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		return tasksProposalLocalService.getResourcePersistence();
	}

	public static void setResourcePersistence(
		com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence) {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		tasksProposalLocalService.setResourcePersistence(resourcePersistence);
	}

	public static com.liferay.portal.service.persistence.ResourceFinder getResourceFinder() {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		return tasksProposalLocalService.getResourceFinder();
	}

	public static void setResourceFinder(
		com.liferay.portal.service.persistence.ResourceFinder resourceFinder) {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		tasksProposalLocalService.setResourceFinder(resourceFinder);
	}

	public static com.liferay.portal.service.persistence.UserPersistence getUserPersistence() {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		return tasksProposalLocalService.getUserPersistence();
	}

	public static void setUserPersistence(
		com.liferay.portal.service.persistence.UserPersistence userPersistence) {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		tasksProposalLocalService.setUserPersistence(userPersistence);
	}

	public static com.liferay.portal.service.persistence.UserFinder getUserFinder() {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		return tasksProposalLocalService.getUserFinder();
	}

	public static void setUserFinder(
		com.liferay.portal.service.persistence.UserFinder userFinder) {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		tasksProposalLocalService.setUserFinder(userFinder);
	}

	public static com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence getMBMessagePersistence() {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		return tasksProposalLocalService.getMBMessagePersistence();
	}

	public static void setMBMessagePersistence(
		com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence mbMessagePersistence) {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		tasksProposalLocalService.setMBMessagePersistence(mbMessagePersistence);
	}

	public static com.liferay.portlet.messageboards.service.persistence.MBMessageFinder getMBMessageFinder() {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		return tasksProposalLocalService.getMBMessageFinder();
	}

	public static void setMBMessageFinder(
		com.liferay.portlet.messageboards.service.persistence.MBMessageFinder mbMessageFinder) {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		tasksProposalLocalService.setMBMessageFinder(mbMessageFinder);
	}

	public static void afterPropertiesSet() {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		tasksProposalLocalService.afterPropertiesSet();
	}

	public static com.liferay.portlet.tasks.model.TasksProposal addProposal(
		long userId, long groupId, java.lang.String className, long classPK,
		java.lang.String name, java.lang.String description, long reviewUserId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		return tasksProposalLocalService.addProposal(userId, groupId,
			className, classPK, name, description, reviewUserId,
			addCommunityPermissions, addGuestPermissions);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal addProposal(
		long userId, long groupId, java.lang.String className, long classPK,
		java.lang.String name, java.lang.String description, long reviewUserId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		return tasksProposalLocalService.addProposal(userId, groupId,
			className, classPK, name, description, reviewUserId,
			communityPermissions, guestPermissions);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal addProposal(
		long userId, long groupId, java.lang.String className, long classPK,
		java.lang.String name, java.lang.String description, long reviewUserId,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		return tasksProposalLocalService.addProposal(userId, groupId,
			className, classPK, name, description, reviewUserId,
			addCommunityPermissions, addGuestPermissions, communityPermissions,
			guestPermissions);
	}

	public static void addProposalResources(long proposalId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		tasksProposalLocalService.addProposalResources(proposalId,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addProposalResources(
		com.liferay.portlet.tasks.model.TasksProposal proposal,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		tasksProposalLocalService.addProposalResources(proposal,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addProposalResources(long proposalId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		tasksProposalLocalService.addProposalResources(proposalId,
			communityPermissions, guestPermissions);
	}

	public static void addProposalResources(
		com.liferay.portlet.tasks.model.TasksProposal proposal,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		tasksProposalLocalService.addProposalResources(proposal,
			communityPermissions, guestPermissions);
	}

	public static void deleteProposal(java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		tasksProposalLocalService.deleteProposal(className, classPK);
	}

	public static void deleteProposal(long classNameId, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		tasksProposalLocalService.deleteProposal(classNameId, classPK);
	}

	public static void deleteProposal(long proposalId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		tasksProposalLocalService.deleteProposal(proposalId);
	}

	public static void deleteProposal(
		com.liferay.portlet.tasks.model.TasksProposal proposal)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		tasksProposalLocalService.deleteProposal(proposal);
	}

	public static void deleteProposals(long groupId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		tasksProposalLocalService.deleteProposals(groupId);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal getProposal(
		long proposalId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		return tasksProposalLocalService.getProposal(proposalId);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal getProposal(
		java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		return tasksProposalLocalService.getProposal(className, classPK);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal getProposal(
		long classNameId, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		return tasksProposalLocalService.getProposal(classNameId, classPK);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksProposal> getProposals(
		long groupId, int begin, int end)
		throws com.liferay.portal.SystemException {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		return tasksProposalLocalService.getProposals(groupId, begin, end);
	}

	public static int getProposalsCount(long groupId)
		throws com.liferay.portal.SystemException {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		return tasksProposalLocalService.getProposalsCount(groupId);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksProposal> getReviewProposals(
		long groupId, long userId, int begin, int end)
		throws com.liferay.portal.SystemException {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		return tasksProposalLocalService.getReviewProposals(groupId, userId,
			begin, end);
	}

	public static int getReviewProposalsCount(long groupId, long userId)
		throws com.liferay.portal.SystemException {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		return tasksProposalLocalService.getReviewProposalsCount(groupId, userId);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksProposal> getUserProposals(
		long groupId, long userId, int begin, int end)
		throws com.liferay.portal.SystemException {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		return tasksProposalLocalService.getUserProposals(groupId, userId,
			begin, end);
	}

	public static int getUserProposalsCount(long groupId, long userId)
		throws com.liferay.portal.SystemException {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		return tasksProposalLocalService.getUserProposalsCount(groupId, userId);
	}

	public static com.liferay.portlet.tasks.model.TasksProposal updateProposal(
		long userId, long proposalId, java.lang.String description,
		int dueDateMonth, int dueDateDay, int dueDateYear, int dueDateHour,
		int dueDateMinute)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksProposalLocalService tasksProposalLocalService = TasksProposalLocalServiceFactory.getService();

		return tasksProposalLocalService.updateProposal(userId, proposalId,
			description, dueDateMonth, dueDateDay, dueDateYear, dueDateHour,
			dueDateMinute);
	}
}