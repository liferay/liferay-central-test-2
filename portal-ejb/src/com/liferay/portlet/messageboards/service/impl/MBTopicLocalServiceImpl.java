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

package com.liferay.portlet.messageboards.service.impl;

import com.liferay.counter.service.spring.CounterServiceUtil;
import com.liferay.documentlibrary.NoSuchDirectoryException;
import com.liferay.documentlibrary.service.spring.DLServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.Company;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.User;
import com.liferay.portal.service.persistence.UserUtil;
import com.liferay.portal.service.spring.ResourceLocalServiceUtil;
import com.liferay.portlet.messageboards.TopicNameException;
import com.liferay.portlet.messageboards.model.MBCategory;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBTopic;
import com.liferay.portlet.messageboards.service.persistence.MBCategoryUtil;
import com.liferay.portlet.messageboards.service.persistence.MBMessageFlagUtil;
import com.liferay.portlet.messageboards.service.persistence.MBMessageUtil;
import com.liferay.portlet.messageboards.service.persistence.MBThreadUtil;
import com.liferay.portlet.messageboards.service.persistence.MBTopicFinder;
import com.liferay.portlet.messageboards.service.persistence.MBTopicUtil;
import com.liferay.portlet.messageboards.service.spring.MBTopicLocalService;
import com.liferay.portlet.messageboards.util.Indexer;
import com.liferay.util.Validator;

import java.io.IOException;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.queryParser.ParseException;

/**
 * <a href="MBTopicLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBTopicLocalServiceImpl implements MBTopicLocalService {

	public MBTopic addTopic(
			String userId, String categoryId, String name, String description,
			boolean addCommunityPermissions, boolean addGuestPermissions)
		throws PortalException, SystemException {

		// Topic

		User user = UserUtil.findByPrimaryKey(userId);
		MBCategory category = MBCategoryUtil.findByPrimaryKey(categoryId);
		Date now = new Date();

		validate(name);

		String topicId = Long.toString(CounterServiceUtil.increment(
			MBTopic.class.getName()));

		MBTopic topic = MBTopicUtil.create(topicId);

		topic.setCompanyId(user.getCompanyId());
		topic.setUserId(user.getUserId());
		topic.setUserName(user.getFullName());
		topic.setCreateDate(now);
		topic.setModifiedDate(now);
		topic.setCategoryId(categoryId);
		topic.setName(name);
		topic.setDescription(description);

		MBTopicUtil.update(topic);

		// Resources

		addTopicResources(
			category, topic, addCommunityPermissions, addGuestPermissions);

		return topic;
	}

	public void addTopicResources(
			String topicId, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		MBTopic topic = MBTopicUtil.findByPrimaryKey(topicId);
		MBCategory category = topic.getCategory();

		addTopicResources(
			category, topic, addCommunityPermissions, addGuestPermissions);
	}

	public void addTopicResources(
			MBCategory category, MBTopic topic, boolean addCommunityPermissions,
			boolean addGuestPermissions)
		throws PortalException, SystemException {

		ResourceLocalServiceUtil.addResources(
			topic.getCompanyId(), category.getGroupId(), topic.getUserId(),
			MBTopic.class.getName(), topic.getPrimaryKey().toString(), false,
			addCommunityPermissions, addGuestPermissions);
	}

	public void deleteTopic(String topicId)
		throws PortalException, SystemException {

		MBTopic topic = MBTopicUtil.findByPrimaryKey(topicId);

		deleteTopic(topic);
	}

	public void deleteTopic(MBTopic topic)
		throws PortalException, SystemException {

		// Lucene

		try {
			Indexer.deleteMessages(topic.getCompanyId(), topic.getTopicId());
		}
		catch (IOException ioe) {
			_log.error(ioe.getMessage());
		}
		catch (ParseException pe) {
			_log.error(pe.getMessage());
		}

		// File attachments

		String companyId = topic.getCompanyId();
		String portletId = Company.SYSTEM;
		String repositoryId = Company.SYSTEM;
		String dirName = topic.getAttachmentsDir();

		try {
			DLServiceUtil.deleteDirectory(
				companyId, portletId, repositoryId, dirName);
		}
		catch (NoSuchDirectoryException nsde) {
		}

		// Threads

		MBThreadUtil.removeByTopicId(topic.getTopicId());

		// Message flags

		MBMessageFlagUtil.removeByTopicId(topic.getTopicId());

		// Messages

		Iterator itr = MBMessageUtil.findByTopicId(
			topic.getTopicId()).iterator();

		while (itr.hasNext()) {
			MBMessage message = (MBMessage)itr.next();

			ResourceLocalServiceUtil.deleteResource(
				message.getCompanyId(), MBMessage.class.getName(),
				Resource.TYPE_CLASS, Resource.SCOPE_INDIVIDUAL,
				message.getPrimaryKey().toString());
		}

		MBMessageUtil.removeByTopicId(topic.getTopicId());

		// Counter

		CounterServiceUtil.reset(
			MBMessage.class.getName() + "." + topic.getTopicId());

		// Resources

		ResourceLocalServiceUtil.deleteResource(
			topic.getCompanyId(), MBTopic.class.getName(), Resource.TYPE_CLASS,
			Resource.SCOPE_INDIVIDUAL, topic.getPrimaryKey().toString());

		// Topic

		MBTopicUtil.remove(topic.getTopicId());
	}

	public void deleteTopics(String categoryId)
		throws PortalException, SystemException {

		Iterator itr = MBTopicUtil.findByCategoryId(categoryId).iterator();

		while (itr.hasNext()) {
			MBTopic topic = (MBTopic)itr.next();

			deleteTopic(topic);
		}
	}

	public int getCategoriesTopicsCount(List categoryIds)
		throws SystemException {

		return MBTopicFinder.countByCategoryIds(categoryIds);
	}

	public MBTopic getTopic(String topicId)
		throws PortalException, SystemException {

		return MBTopicUtil.findByPrimaryKey(topicId);
	}

	public List getTopics(String categoryId, int begin, int end)
		throws SystemException {

		return MBTopicUtil.findByCategoryId(categoryId, begin, end);
	}

	public int getTopicsCount(String categoryId) throws SystemException {
		return MBTopicUtil.countByCategoryId(categoryId);
	}

	public MBTopic updateTopic(
			String topicId, String categoryId, String name, String description)
		throws PortalException, SystemException {

		validate(name);

		MBTopic topic = MBTopicUtil.findByPrimaryKey(topicId);

		topic.setCategoryId(categoryId);
		topic.setModifiedDate(new Date());
		topic.setName(name);
		topic.setDescription(description);

		MBTopicUtil.update(topic);

		return topic;
	}

	protected void validate(String name) throws PortalException {
		if (Validator.isNull(name)) {
			throw new TopicNameException();
		}
	}

	private static Log _log = LogFactory.getLog(MBTopicLocalServiceImpl.class);

}