/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet.messageboards.service.spring;

/**
 * <a href="MBTopicLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBTopicLocalServiceUtil {
	public static com.liferay.portlet.messageboards.model.MBTopic addTopic(
		java.lang.String userId, java.lang.String categoryId,
		java.lang.String name, java.lang.String description,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			MBTopicLocalService mbTopicLocalService = MBTopicLocalServiceFactory.getService();

			return mbTopicLocalService.addTopic(userId, categoryId, name,
				description, addCommunityPermissions, addGuestPermissions);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void addTopicResources(java.lang.String topicId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			MBTopicLocalService mbTopicLocalService = MBTopicLocalServiceFactory.getService();
			mbTopicLocalService.addTopicResources(topicId,
				addCommunityPermissions, addGuestPermissions);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void addTopicResources(
		com.liferay.portlet.messageboards.model.MBCategory category,
		com.liferay.portlet.messageboards.model.MBTopic topic,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			MBTopicLocalService mbTopicLocalService = MBTopicLocalServiceFactory.getService();
			mbTopicLocalService.addTopicResources(category, topic,
				addCommunityPermissions, addGuestPermissions);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void deleteTopic(java.lang.String topicId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			MBTopicLocalService mbTopicLocalService = MBTopicLocalServiceFactory.getService();
			mbTopicLocalService.deleteTopic(topicId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void deleteTopic(
		com.liferay.portlet.messageboards.model.MBTopic topic)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			MBTopicLocalService mbTopicLocalService = MBTopicLocalServiceFactory.getService();
			mbTopicLocalService.deleteTopic(topic);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static void deleteTopics(java.lang.String categoryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			MBTopicLocalService mbTopicLocalService = MBTopicLocalServiceFactory.getService();
			mbTopicLocalService.deleteTopics(categoryId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static int getCategoriesTopicsCount(java.util.List categoryIds)
		throws com.liferay.portal.SystemException {
		try {
			MBTopicLocalService mbTopicLocalService = MBTopicLocalServiceFactory.getService();

			return mbTopicLocalService.getCategoriesTopicsCount(categoryIds);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static int getGroupTopicsCount(java.lang.String groupId)
		throws com.liferay.portal.SystemException {
		try {
			MBTopicLocalService mbTopicLocalService = MBTopicLocalServiceFactory.getService();

			return mbTopicLocalService.getGroupTopicsCount(groupId);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portlet.messageboards.model.MBTopic getTopic(
		java.lang.String topicId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			MBTopicLocalService mbTopicLocalService = MBTopicLocalServiceFactory.getService();

			return mbTopicLocalService.getTopic(topicId);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static java.util.List getTopics(java.lang.String categoryId,
		int begin, int end) throws com.liferay.portal.SystemException {
		try {
			MBTopicLocalService mbTopicLocalService = MBTopicLocalServiceFactory.getService();

			return mbTopicLocalService.getTopics(categoryId, begin, end);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static int getTopicsCount(java.lang.String categoryId)
		throws com.liferay.portal.SystemException {
		try {
			MBTopicLocalService mbTopicLocalService = MBTopicLocalServiceFactory.getService();

			return mbTopicLocalService.getTopicsCount(categoryId);
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}

	public static com.liferay.portlet.messageboards.model.MBTopic updateTopic(
		java.lang.String topicId, java.lang.String categoryId,
		java.lang.String name, java.lang.String description)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		try {
			MBTopicLocalService mbTopicLocalService = MBTopicLocalServiceFactory.getService();

			return mbTopicLocalService.updateTopic(topicId, categoryId, name,
				description);
		}
		catch (com.liferay.portal.PortalException pe) {
			throw pe;
		}
		catch (com.liferay.portal.SystemException se) {
			throw se;
		}
		catch (Exception e) {
			throw new com.liferay.portal.SystemException(e);
		}
	}
}