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

package com.liferay.portlet.messageboards.service.ejb;

import com.liferay.portal.spring.util.SpringUtil;

import com.liferay.portlet.messageboards.service.spring.MBTopicLocalService;

import org.springframework.context.ApplicationContext;

import javax.ejb.CreateException;
import javax.ejb.SessionBean;
import javax.ejb.SessionContext;

/**
 * <a href="MBTopicLocalServiceEJBImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBTopicLocalServiceEJBImpl implements MBTopicLocalService,
	SessionBean {
	public static final String CLASS_NAME = MBTopicLocalService.class.getName() +
		".transaction";

	public static MBTopicLocalService getService() {
		ApplicationContext ctx = SpringUtil.getContext();

		return (MBTopicLocalService)ctx.getBean(CLASS_NAME);
	}

	public com.liferay.portlet.messageboards.model.MBTopic addTopic(
		java.lang.String userId, java.lang.String categoryId,
		java.lang.String name, java.lang.String description,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().addTopic(userId, categoryId, name, description,
			addCommunityPermissions, addGuestPermissions);
	}

	public void addTopicResources(java.lang.String topicId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().addTopicResources(topicId, addCommunityPermissions,
			addGuestPermissions);
	}

	public void addTopicResources(
		com.liferay.portlet.messageboards.model.MBCategory category,
		com.liferay.portlet.messageboards.model.MBTopic topic,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().addTopicResources(category, topic,
			addCommunityPermissions, addGuestPermissions);
	}

	public void deleteTopic(java.lang.String topicId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deleteTopic(topicId);
	}

	public void deleteTopic(
		com.liferay.portlet.messageboards.model.MBTopic topic)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deleteTopic(topic);
	}

	public void deleteTopics(java.lang.String categoryId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		getService().deleteTopics(categoryId);
	}

	public int getCategoriesTopicsCount(java.util.List categoryIds)
		throws com.liferay.portal.SystemException {
		return getService().getCategoriesTopicsCount(categoryIds);
	}

	public com.liferay.portlet.messageboards.model.MBTopic getTopic(
		java.lang.String topicId)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().getTopic(topicId);
	}

	public java.util.List getTopics(java.lang.String categoryId, int begin,
		int end) throws com.liferay.portal.SystemException {
		return getService().getTopics(categoryId, begin, end);
	}

	public int getTopicsCount(java.lang.String categoryId)
		throws com.liferay.portal.SystemException {
		return getService().getTopicsCount(categoryId);
	}

	public com.liferay.portlet.messageboards.model.MBTopic updateTopic(
		java.lang.String topicId, java.lang.String categoryId,
		java.lang.String name, java.lang.String description)
		throws com.liferay.portal.PortalException, 
			com.liferay.portal.SystemException {
		return getService().updateTopic(topicId, categoryId, name, description);
	}

	public void ejbCreate() throws CreateException {
	}

	public void ejbRemove() {
	}

	public void ejbActivate() {
	}

	public void ejbPassivate() {
	}

	public SessionContext getSessionContext() {
		return _sc;
	}

	public void setSessionContext(SessionContext sc) {
		_sc = sc;
	}

	private SessionContext _sc;
}