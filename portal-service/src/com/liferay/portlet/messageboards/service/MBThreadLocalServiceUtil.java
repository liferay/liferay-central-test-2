/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.service;

/**
 * <a href="MBThreadLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be overwritten
 * the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the <code>com.liferay.portlet.messageboards.service.MBThreadLocalService</code>
 * bean. The static methods of this class calls the same methods of the bean instance.
 * It's convenient to be able to just write one line to call a method on a bean
 * instead of writing a lookup call and a method call.
 * </p>
 *
 * <p>
 * <code>com.liferay.portlet.messageboards.service.MBThreadLocalServiceFactory</code>
 * is responsible for the lookup of the bean.
 * </p>
 *
 * @author Brian Wing Shun Chan
 *
 * @see com.liferay.portlet.messageboards.service.MBThreadLocalService
 * @see com.liferay.portlet.messageboards.service.MBThreadLocalServiceFactory
 *
 */
public class MBThreadLocalServiceUtil {
	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer)
		throws com.liferay.portal.SystemException {
		MBThreadLocalService mbThreadLocalService = MBThreadLocalServiceFactory.getService();

		return mbThreadLocalService.dynamicQuery(queryInitializer);
	}

	public static java.util.List dynamicQuery(
		com.liferay.portal.kernel.dao.DynamicQueryInitializer queryInitializer,
		int begin, int end) throws com.liferay.portal.SystemException {
		MBThreadLocalService mbThreadLocalService = MBThreadLocalServiceFactory.getService();

		return mbThreadLocalService.dynamicQuery(queryInitializer, begin, end);
	}

	public static com.liferay.portlet.messageboards.service.persistence.MBBanPersistence getMBBanPersistence() {
		MBThreadLocalService mbThreadLocalService = MBThreadLocalServiceFactory.getService();

		return mbThreadLocalService.getMBBanPersistence();
	}

	public static void setMBBanPersistence(
		com.liferay.portlet.messageboards.service.persistence.MBBanPersistence mbBanPersistence) {
		MBThreadLocalService mbThreadLocalService = MBThreadLocalServiceFactory.getService();
		mbThreadLocalService.setMBBanPersistence(mbBanPersistence);
	}

	public static com.liferay.portlet.messageboards.service.persistence.MBCategoryPersistence getMBCategoryPersistence() {
		MBThreadLocalService mbThreadLocalService = MBThreadLocalServiceFactory.getService();

		return mbThreadLocalService.getMBCategoryPersistence();
	}

	public static void setMBCategoryPersistence(
		com.liferay.portlet.messageboards.service.persistence.MBCategoryPersistence mbCategoryPersistence) {
		MBThreadLocalService mbThreadLocalService = MBThreadLocalServiceFactory.getService();
		mbThreadLocalService.setMBCategoryPersistence(mbCategoryPersistence);
	}

	public static com.liferay.portlet.messageboards.service.persistence.MBDiscussionPersistence getMBDiscussionPersistence() {
		MBThreadLocalService mbThreadLocalService = MBThreadLocalServiceFactory.getService();

		return mbThreadLocalService.getMBDiscussionPersistence();
	}

	public static void setMBDiscussionPersistence(
		com.liferay.portlet.messageboards.service.persistence.MBDiscussionPersistence mbDiscussionPersistence) {
		MBThreadLocalService mbThreadLocalService = MBThreadLocalServiceFactory.getService();
		mbThreadLocalService.setMBDiscussionPersistence(mbDiscussionPersistence);
	}

	public static com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence getMBMessagePersistence() {
		MBThreadLocalService mbThreadLocalService = MBThreadLocalServiceFactory.getService();

		return mbThreadLocalService.getMBMessagePersistence();
	}

	public static void setMBMessagePersistence(
		com.liferay.portlet.messageboards.service.persistence.MBMessagePersistence mbMessagePersistence) {
		MBThreadLocalService mbThreadLocalService = MBThreadLocalServiceFactory.getService();
		mbThreadLocalService.setMBMessagePersistence(mbMessagePersistence);
	}

	public static com.liferay.portlet.messageboards.service.persistence.MBMessageFlagPersistence getMBMessageFlagPersistence() {
		MBThreadLocalService mbThreadLocalService = MBThreadLocalServiceFactory.getService();

		return mbThreadLocalService.getMBMessageFlagPersistence();
	}

	public static void setMBMessageFlagPersistence(
		com.liferay.portlet.messageboards.service.persistence.MBMessageFlagPersistence mbMessageFlagPersistence) {
		MBThreadLocalService mbThreadLocalService = MBThreadLocalServiceFactory.getService();
		mbThreadLocalService.setMBMessageFlagPersistence(mbMessageFlagPersistence);
	}

	public static com.liferay.portlet.messageboards.service.persistence.MBStatsUserPersistence getMBStatsUserPersistence() {
		MBThreadLocalService mbThreadLocalService = MBThreadLocalServiceFactory.getService();

		return mbThreadLocalService.getMBStatsUserPersistence();
	}

	public static void setMBStatsUserPersistence(
		com.liferay.portlet.messageboards.service.persistence.MBStatsUserPersistence mbStatsUserPersistence) {
		MBThreadLocalService mbThreadLocalService = MBThreadLocalServiceFactory.getService();
		mbThreadLocalService.setMBStatsUserPersistence(mbStatsUserPersistence);
	}

	public static com.liferay.portlet.messageboards.service.persistence.MBThreadPersistence getMBThreadPersistence() {
		MBThreadLocalService mbThreadLocalService = MBThreadLocalServiceFactory.getService();

		return mbThreadLocalService.getMBThreadPersistence();
	}

	public static void setMBThreadPersistence(
		com.liferay.portlet.messageboards.service.persistence.MBThreadPersistence mbThreadPersistence) {
		MBThreadLocalService mbThreadLocalService = MBThreadLocalServiceFactory.getService();
		mbThreadLocalService.setMBThreadPersistence(mbThreadPersistence);
	}

	public static void deleteThread(long threadId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBThreadLocalService mbThreadLocalService = MBThreadLocalServiceFactory.getService();
		mbThreadLocalService.deleteThread(threadId);
	}

	public static void deleteThread(
		com.liferay.portlet.messageboards.model.MBThread thread)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBThreadLocalService mbThreadLocalService = MBThreadLocalServiceFactory.getService();
		mbThreadLocalService.deleteThread(thread);
	}

	public static void deleteThreads(long categoryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBThreadLocalService mbThreadLocalService = MBThreadLocalServiceFactory.getService();
		mbThreadLocalService.deleteThreads(categoryId);
	}

	public static int getCategoriesThreadsCount(java.util.List categoryIds)
		throws com.liferay.portal.SystemException {
		MBThreadLocalService mbThreadLocalService = MBThreadLocalServiceFactory.getService();

		return mbThreadLocalService.getCategoriesThreadsCount(categoryIds);
	}

	public static java.util.List getGroupThreads(long groupId, int begin,
		int end) throws com.liferay.portal.SystemException {
		MBThreadLocalService mbThreadLocalService = MBThreadLocalServiceFactory.getService();

		return mbThreadLocalService.getGroupThreads(groupId, begin, end);
	}

	public static java.util.List getGroupThreads(long groupId, long userId,
		int begin, int end) throws com.liferay.portal.SystemException {
		MBThreadLocalService mbThreadLocalService = MBThreadLocalServiceFactory.getService();

		return mbThreadLocalService.getGroupThreads(groupId, userId, begin, end);
	}

	public static java.util.List getGroupThreads(long groupId, long userId,
		boolean subscribed, int begin, int end)
		throws com.liferay.portal.SystemException {
		MBThreadLocalService mbThreadLocalService = MBThreadLocalServiceFactory.getService();

		return mbThreadLocalService.getGroupThreads(groupId, userId,
			subscribed, begin, end);
	}

	public static int getGroupThreadsCount(long groupId)
		throws com.liferay.portal.SystemException {
		MBThreadLocalService mbThreadLocalService = MBThreadLocalServiceFactory.getService();

		return mbThreadLocalService.getGroupThreadsCount(groupId);
	}

	public static int getGroupThreadsCount(long groupId, long userId)
		throws com.liferay.portal.SystemException {
		MBThreadLocalService mbThreadLocalService = MBThreadLocalServiceFactory.getService();

		return mbThreadLocalService.getGroupThreadsCount(groupId, userId);
	}

	public static int getGroupThreadsCount(long groupId, long userId,
		boolean subscribed) throws com.liferay.portal.SystemException {
		MBThreadLocalService mbThreadLocalService = MBThreadLocalServiceFactory.getService();

		return mbThreadLocalService.getGroupThreadsCount(groupId, userId,
			subscribed);
	}

	public static com.liferay.portlet.messageboards.model.MBThread getThread(
		long threadId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBThreadLocalService mbThreadLocalService = MBThreadLocalServiceFactory.getService();

		return mbThreadLocalService.getThread(threadId);
	}

	public static java.util.List getThreads(long categoryId, int begin, int end)
		throws com.liferay.portal.SystemException {
		MBThreadLocalService mbThreadLocalService = MBThreadLocalServiceFactory.getService();

		return mbThreadLocalService.getThreads(categoryId, begin, end);
	}

	public static int getThreadsCount(long categoryId)
		throws com.liferay.portal.SystemException {
		MBThreadLocalService mbThreadLocalService = MBThreadLocalServiceFactory.getService();

		return mbThreadLocalService.getThreadsCount(categoryId);
	}

	public static boolean hasReadThread(long userId, long threadId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBThreadLocalService mbThreadLocalService = MBThreadLocalServiceFactory.getService();

		return mbThreadLocalService.hasReadThread(userId, threadId);
	}

	public static com.liferay.portlet.messageboards.model.MBThread updateThread(
		long threadId, int viewCount)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		MBThreadLocalService mbThreadLocalService = MBThreadLocalServiceFactory.getService();

		return mbThreadLocalService.updateThread(threadId, viewCount);
	}
}