/**
 * Copyright (c) 2000-2006 Liferay, Inc. All rights reserved.
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

import com.liferay.documentlibrary.NoSuchDirectoryException;
import com.liferay.documentlibrary.service.DLServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.model.impl.CompanyImpl;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.service.ResourceLocalServiceUtil;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.model.MBThread;
import com.liferay.portlet.messageboards.model.impl.MBMessageImpl;
import com.liferay.portlet.messageboards.service.MBThreadLocalService;
import com.liferay.portlet.messageboards.service.persistence.MBMessageFlagFinder;
import com.liferay.portlet.messageboards.service.persistence.MBMessageFlagUtil;
import com.liferay.portlet.messageboards.service.persistence.MBMessagePK;
import com.liferay.portlet.messageboards.service.persistence.MBMessageUtil;
import com.liferay.portlet.messageboards.service.persistence.MBThreadFinder;
import com.liferay.portlet.messageboards.service.persistence.MBThreadUtil;
import com.liferay.portlet.messageboards.util.Indexer;
import com.liferay.util.Validator;

import java.io.IOException;

import java.rmi.RemoteException;

import java.util.Iterator;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.queryParser.ParseException;

/**
 * <a href="MBThreadLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Brian Wing Shun Chan
 *
 */
public class MBThreadLocalServiceImpl implements MBThreadLocalService {

	public void deleteThread(String threadId)
		throws PortalException, SystemException {

		MBThread thread = MBThreadUtil.findByPrimaryKey(threadId);

		deleteThread(thread);
	}

	public void deleteThread(MBThread thread)
		throws PortalException, SystemException {

		MBMessage rootMessage = MBMessageUtil.findByPrimaryKey(
			new MBMessagePK(MBMessageImpl.DEPRECATED_TOPIC_ID,
			thread.getRootMessageId()));

		// Lucene

		try {
			Indexer.deleteMessages(
				rootMessage.getCompanyId(), thread.getThreadId());
		}
		catch (IOException ioe) {
			_log.error(ioe.getMessage());
		}
		catch (ParseException pe) {
			_log.error(pe.getMessage());
		}

		// File attachments

		String companyId = rootMessage.getCompanyId();
		String portletId = CompanyImpl.SYSTEM;
		String repositoryId = CompanyImpl.SYSTEM;
		String dirName = thread.getAttachmentsDir();

		try {
			DLServiceUtil.deleteDirectory(
				companyId, portletId, repositoryId, dirName);
		}
		catch (NoSuchDirectoryException nsde) {
		}
		catch (RemoteException re) {
			throw new SystemException(re);
		}

		// Messages

		Iterator itr = MBMessageUtil.findByThreadId(
			thread.getThreadId()).iterator();

		while (itr.hasNext()) {
			MBMessage message = (MBMessage)itr.next();

			// Message flags

			MBMessageFlagUtil.removeByT_M(
				message.getTopicId(), message.getMessageId());

			// Resources

			if (!message.isDiscussion()) {
				ResourceLocalServiceUtil.deleteResource(
					message.getCompanyId(), MBMessage.class.getName(),
					ResourceImpl.TYPE_CLASS, ResourceImpl.SCOPE_INDIVIDUAL,
					message.getPrimaryKey().toString());
			}

			// Message

			MBMessageUtil.remove(message.getPrimaryKey());
		}

		// Thread

		MBThreadUtil.remove(thread.getThreadId());
	}

	public void deleteThreads(String categoryId)
		throws PortalException, SystemException {

		Iterator itr = MBThreadUtil.findByCategoryId(categoryId).iterator();

		while (itr.hasNext()) {
			MBThread thread = (MBThread)itr.next();

			deleteThread(thread);
		}
	}

	public int getCategoriesThreadsCount(List categoryIds)
		throws SystemException {

		return MBThreadFinder.countByCategoryIds(categoryIds);
	}

	public List getGroupThreads(String groupId, int begin, int end)
		throws SystemException {

		return MBThreadFinder.findByGroupId(groupId, begin, end);
	}

	public List getGroupThreads(
			String groupId, String userId, int begin, int end)
		throws SystemException {

		if (Validator.isNull(userId)) {
			return MBThreadFinder.findByGroupId(groupId, begin, end);
		}
		else {
			return MBThreadFinder.findByG_U(groupId, userId, begin, end);
		}
	}

	public int getGroupThreadsCount(String groupId) throws SystemException {
		return MBThreadFinder.countByGroupId(groupId);
	}

	public int getGroupThreadsCount(String groupId, String userId)
		throws SystemException {

		if (Validator.isNull(userId)) {
			return MBThreadFinder.countByGroupId(groupId);
		}
		else {
			return MBThreadFinder.countByG_U(groupId, userId);
		}
	}

	public MBThread getThread(String threadId)
		throws PortalException, SystemException {

		return MBThreadUtil.findByPrimaryKey(threadId);
	}

	public List getThreads(String categoryId, int begin, int end)
		throws SystemException {

		return MBThreadUtil.findByCategoryId(categoryId, begin, end);
	}

	public int getThreadsCount(String categoryId) throws SystemException {
		return MBThreadUtil.countByCategoryId(categoryId);
	}

	public boolean hasReadThread(String userId, String threadId)
		throws SystemException {

		if (userId == null) {

			// Unauthenticated users do not have a record of read messages

			return true;
		}

		int total = MBMessageUtil.countByThreadId(threadId);
		int read = MBMessageFlagFinder.countByT_U(threadId, userId);

		if (total != read) {
			return false;
		}
		else {
			return true;
		}
	}

	private static Log _log = LogFactory.getLog(MBThreadLocalServiceImpl.class);

}