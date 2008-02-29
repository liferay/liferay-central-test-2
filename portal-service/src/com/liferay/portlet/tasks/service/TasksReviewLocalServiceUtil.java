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
 * <a href="TasksReviewLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * <code>com.liferay.portlet.tasks.service.TasksReviewLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean
 * instance. It's convenient to be able to just write one line to call a method
 * on a bean instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.tasks.service.TasksReviewLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.tasks.service.TasksReviewLocalService
 * @see com.liferay.portlet.tasks.service.TasksReviewLocalServiceFactory
 *
 */
public class TasksReviewLocalServiceUtil {
	public static com.liferay.portlet.tasks.model.TasksReview addTasksReview(
		com.liferay.portlet.tasks.model.TasksReview tasksReview)
		throws com.liferay.portal.SystemException {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		return tasksReviewLocalService.addTasksReview(tasksReview);
	}

	public static void deleteTasksReview(long reviewId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		tasksReviewLocalService.deleteTasksReview(reviewId);
	}

	public static void deleteTasksReview(
		com.liferay.portlet.tasks.model.TasksReview tasksReview)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		tasksReviewLocalService.deleteTasksReview(tasksReview);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		return tasksReviewLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List<com.liferay.portlet.tasks.model.TasksReview> dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		return tasksReviewLocalService.dynamicQuery(queryInitializer, begin, end);
	}

	public static com.liferay.portlet.tasks.model.TasksReview updateTasksReview(
		com.liferay.portlet.tasks.model.TasksReview tasksReview)
		throws com.liferay.portal.SystemException {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		return tasksReviewLocalService.updateTasksReview(tasksReview);
	}

	public static com.liferay.portlet.tasks.service.persistence.TasksProposalPersistence getTasksProposalPersistence() {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		return tasksReviewLocalService.getTasksProposalPersistence();
	}

	public static void setTasksProposalPersistence(
		com.liferay.portlet.tasks.service.persistence.TasksProposalPersistence tasksProposalPersistence) {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		tasksReviewLocalService.setTasksProposalPersistence(tasksProposalPersistence);
	}

	public static com.liferay.portlet.tasks.service.persistence.TasksProposalFinder getTasksProposalFinder() {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		return tasksReviewLocalService.getTasksProposalFinder();
	}

	public static void setTasksProposalFinder(
		com.liferay.portlet.tasks.service.persistence.TasksProposalFinder tasksProposalFinder) {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		tasksReviewLocalService.setTasksProposalFinder(tasksProposalFinder);
	}

	public static com.liferay.portlet.tasks.service.persistence.TasksReviewPersistence getTasksReviewPersistence() {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		return tasksReviewLocalService.getTasksReviewPersistence();
	}

	public static void setTasksReviewPersistence(
		com.liferay.portlet.tasks.service.persistence.TasksReviewPersistence tasksReviewPersistence) {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		tasksReviewLocalService.setTasksReviewPersistence(tasksReviewPersistence);
	}

	public static com.liferay.portal.service.persistence.ActivityTrackerPersistence getActivityTrackerPersistence() {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		return tasksReviewLocalService.getActivityTrackerPersistence();
	}

	public static void setActivityTrackerPersistence(
		com.liferay.portal.service.persistence.ActivityTrackerPersistence activityTrackerPersistence) {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		tasksReviewLocalService.setActivityTrackerPersistence(activityTrackerPersistence);
	}

	public static com.liferay.portal.service.persistence.ActivityTrackerFinder getActivityTrackerFinder() {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		return tasksReviewLocalService.getActivityTrackerFinder();
	}

	public static void setActivityTrackerFinder(
		com.liferay.portal.service.persistence.ActivityTrackerFinder activityTrackerFinder) {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		tasksReviewLocalService.setActivityTrackerFinder(activityTrackerFinder);
	}

	public static com.liferay.portal.service.persistence.CompanyPersistence getCompanyPersistence() {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		return tasksReviewLocalService.getCompanyPersistence();
	}

	public static void setCompanyPersistence(
		com.liferay.portal.service.persistence.CompanyPersistence companyPersistence) {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		tasksReviewLocalService.setCompanyPersistence(companyPersistence);
	}

	public static com.liferay.portal.service.persistence.PortletPreferencesPersistence getPortletPreferencesPersistence() {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		return tasksReviewLocalService.getPortletPreferencesPersistence();
	}

	public static void setPortletPreferencesPersistence(
		com.liferay.portal.service.persistence.PortletPreferencesPersistence portletPreferencesPersistence) {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		tasksReviewLocalService.setPortletPreferencesPersistence(portletPreferencesPersistence);
	}

	public static com.liferay.portal.service.persistence.PortletPreferencesFinder getPortletPreferencesFinder() {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		return tasksReviewLocalService.getPortletPreferencesFinder();
	}

	public static void setPortletPreferencesFinder(
		com.liferay.portal.service.persistence.PortletPreferencesFinder portletPreferencesFinder) {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		tasksReviewLocalService.setPortletPreferencesFinder(portletPreferencesFinder);
	}

	public static com.liferay.portal.service.persistence.ResourcePersistence getResourcePersistence() {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		return tasksReviewLocalService.getResourcePersistence();
	}

	public static void setResourcePersistence(
		com.liferay.portal.service.persistence.ResourcePersistence resourcePersistence) {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		tasksReviewLocalService.setResourcePersistence(resourcePersistence);
	}

	public static com.liferay.portal.service.persistence.ResourceFinder getResourceFinder() {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		return tasksReviewLocalService.getResourceFinder();
	}

	public static void setResourceFinder(
		com.liferay.portal.service.persistence.ResourceFinder resourceFinder) {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		tasksReviewLocalService.setResourceFinder(resourceFinder);
	}

	public static com.liferay.portal.service.persistence.UserPersistence getUserPersistence() {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		return tasksReviewLocalService.getUserPersistence();
	}

	public static void setUserPersistence(
		com.liferay.portal.service.persistence.UserPersistence userPersistence) {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		tasksReviewLocalService.setUserPersistence(userPersistence);
	}

	public static com.liferay.portal.service.persistence.UserFinder getUserFinder() {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		return tasksReviewLocalService.getUserFinder();
	}

	public static void setUserFinder(
		com.liferay.portal.service.persistence.UserFinder userFinder) {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		tasksReviewLocalService.setUserFinder(userFinder);
	}

	public static void afterPropertiesSet() {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		tasksReviewLocalService.afterPropertiesSet();
	}

	public static com.liferay.portlet.tasks.model.TasksReview addReview(
		long userId, long groupId, long assigningUserId,
		java.lang.String assigningUserName, long proposalId, int stage,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		return tasksReviewLocalService.addReview(userId, groupId,
			assigningUserId, assigningUserName, proposalId, stage,
			addCommunityPermissions, addGuestPermissions);
	}

	public static com.liferay.portlet.tasks.model.TasksReview addReview(
		long userId, long groupId, long assigningUserId,
		java.lang.String assigningUserName, long proposalId, int stage,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		return tasksReviewLocalService.addReview(userId, groupId,
			assigningUserId, assigningUserName, proposalId, stage,
			communityPermissions, guestPermissions);
	}

	public static com.liferay.portlet.tasks.model.TasksReview addReview(
		long userId, long groupId, long assigningUserId,
		java.lang.String assigningUserName, long proposalId, int stage,
		java.lang.Boolean addCommunityPermissions,
		java.lang.Boolean addGuestPermissions,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		return tasksReviewLocalService.addReview(userId, groupId,
			assigningUserId, assigningUserName, proposalId, stage,
			addCommunityPermissions, addGuestPermissions, communityPermissions,
			guestPermissions);
	}

	public static void addReviewResources(long proposalId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		tasksReviewLocalService.addReviewResources(proposalId,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addReviewResources(
		com.liferay.portlet.tasks.model.TasksReview review,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		tasksReviewLocalService.addReviewResources(review,
			addCommunityPermissions, addGuestPermissions);
	}

	public static void addReviewResources(long proposalId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		tasksReviewLocalService.addReviewResources(proposalId,
			communityPermissions, guestPermissions);
	}

	public static void addReviewResources(
		com.liferay.portlet.tasks.model.TasksReview review,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		tasksReviewLocalService.addReviewResources(review,
			communityPermissions, guestPermissions);
	}

	public static com.liferay.portlet.tasks.model.TasksReview rejectReview(
		long userId, long proposalId, int stage, boolean rejected)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		return tasksReviewLocalService.rejectReview(userId, proposalId, stage,
			rejected);
	}

	public static void deleteReview(long reviewId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		tasksReviewLocalService.deleteReview(reviewId);
	}

	public static void deleteReviews(long proposalId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		tasksReviewLocalService.deleteReviews(proposalId);
	}

	public static com.liferay.portlet.tasks.model.TasksReview getReview(
		long reviewId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		return tasksReviewLocalService.getReview(reviewId);
	}

	public static com.liferay.portlet.tasks.model.TasksReview getReview(
		long proposalId, long userId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		return tasksReviewLocalService.getReview(proposalId, userId);
	}

	public static java.util.List getReviews(long proposalId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		return tasksReviewLocalService.getReviews(proposalId);
	}

	public static java.util.List getReviews(long proposalId, int stage)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		return tasksReviewLocalService.getReviews(proposalId, stage);
	}

	public static java.util.List getReviews(long proposalId, int stage,
		boolean completed)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		return tasksReviewLocalService.getReviews(proposalId, stage, completed);
	}

	public static java.util.List getReviews(long proposalId, int stage,
		int begin, int end)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		return tasksReviewLocalService.getReviews(proposalId, stage, begin, end);
	}

	public static java.util.List getReviews(long proposalId, int stage,
		boolean completed, boolean rejected)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		return tasksReviewLocalService.getReviews(proposalId, stage, completed,
			rejected);
	}

	public static java.util.List getReviews(long proposalId, int stage,
		boolean completed, int begin, int end)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		return tasksReviewLocalService.getReviews(proposalId, stage, completed,
			begin, end);
	}

	public static java.util.List getReviews(long proposalId, int stage,
		boolean completed, boolean rejected, int begin, int end)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		return tasksReviewLocalService.getReviews(proposalId, stage, completed,
			rejected, begin, end);
	}

	public static int getReviewsCount(long proposalId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		return tasksReviewLocalService.getReviewsCount(proposalId);
	}

	public static int getReviewsCount(long proposalId, int stage)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		return tasksReviewLocalService.getReviewsCount(proposalId, stage);
	}

	public static int getReviewsCount(long proposalId, int stage,
		boolean completed)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		return tasksReviewLocalService.getReviewsCount(proposalId, stage,
			completed);
	}

	public static int getReviewsCount(long proposalId, int stage,
		boolean completed, boolean rejected)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		return tasksReviewLocalService.getReviewsCount(proposalId, stage,
			completed, rejected);
	}

	public static void updateReviewers(long userId, long groupId,
		long proposalId, int stage, long[] reviewerIds, long[] removeReviewerIds)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		TasksReviewLocalService tasksReviewLocalService = TasksReviewLocalServiceFactory.getService();

		tasksReviewLocalService.updateReviewers(userId, groupId, proposalId,
			stage, reviewerIds, removeReviewerIds);
	}
}