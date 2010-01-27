/**
 * Copyright (c) 2000-2009 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.annotation.Isolation;
import com.liferay.portal.kernel.annotation.Propagation;
import com.liferay.portal.kernel.annotation.Transactional;

/**
 * <a href="MBMessageLocalService.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This interface defines the service. The default implementation is
 * {@link
 * com.liferay.portlet.messageboards.service.impl.MBMessageLocalServiceImpl}}.
 * Modify methods in that class and rerun ServiceBuilder to populate this class
 * and all other generated classes.
 * </p>
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBMessageLocalServiceUtil
 * @generated
 */
@Transactional(isolation = Isolation.PORTAL, rollbackFor =  {
	PortalException.class, SystemException.class})
public interface MBMessageLocalService {
	public com.liferay.portlet.messageboards.model.MBMessage addMBMessage(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage createMBMessage(
		long messageId);

	public void deleteMBMessage(long messageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void deleteMBMessage(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException;

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.messageboards.model.MBMessage getMBMessage(
		long messageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getMBMessages(
		int start, int end) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getMBMessagesCount() throws com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage updateMBMessage(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage)
		throws com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage updateMBMessage(
		com.liferay.portlet.messageboards.model.MBMessage mbMessage,
		boolean merge) throws com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage addDiscussionMessage(
		long userId, java.lang.String userName, java.lang.String className,
		long classPK, int status)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage addDiscussionMessage(
		long userId, java.lang.String userName, java.lang.String className,
		long classPK, long threadId, long parentMessageId,
		java.lang.String subject, java.lang.String body,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long userId, java.lang.String userName, long groupId, long categoryId,
		java.lang.String subject, java.lang.String body,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<String, byte[]>> files,
		boolean anonymous, double priority,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		long userId, java.lang.String userName, long groupId, long categoryId,
		long threadId, long parentMessageId, java.lang.String subject,
		java.lang.String body,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<String, byte[]>> files,
		boolean anonymous, double priority,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage addMessage(
		java.lang.String uuid, long userId, java.lang.String userName,
		long groupId, long categoryId, long threadId, long parentMessageId,
		java.lang.String subject, java.lang.String body,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<String, byte[]>> files,
		boolean anonymous, double priority,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void addMessageResources(long messageId,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void addMessageResources(
		com.liferay.portlet.messageboards.model.MBMessage message,
		boolean addCommunityPermissions, boolean addGuestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void addMessageResources(long messageId,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void addMessageResources(
		com.liferay.portlet.messageboards.model.MBMessage message,
		java.lang.String[] communityPermissions,
		java.lang.String[] guestPermissions)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void deleteDiscussionMessage(long messageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void deleteDiscussionMessages(java.lang.String className,
		long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void deleteMessage(long messageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void deleteMessage(
		com.liferay.portlet.messageboards.model.MBMessage message)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getCategoryMessages(
		long groupId, long categoryId, int status, int start, int end)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getCategoryMessages(
		long groupId, long categoryId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCategoryMessagesCount(long groupId, long categoryId,
		int status) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getCompanyMessages(
		long companyId, int status, int start, int end)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getCompanyMessages(
		long companyId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getCompanyMessagesCount(long companyId, int status)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.messageboards.model.MBMessageDisplay getDiscussionMessageDisplay(
		long userId, java.lang.String className, long classPK, int status)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.messageboards.model.MBMessageDisplay getDiscussionMessageDisplay(
		long userId, java.lang.String className, long classPK, int status,
		java.lang.String threadView)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getDiscussionMessagesCount(long classNameId, long classPK,
		int status) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.messageboards.model.MBDiscussion> getDiscussions(
		java.lang.String className) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getGroupMessages(
		long groupId, int status, int start, int end)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getGroupMessages(
		long groupId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getGroupMessages(
		long groupId, long userId, int status, int start, int end)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getGroupMessages(
		long groupId, long userId, int status, int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator obc)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupMessagesCount(long groupId, int status)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getGroupMessagesCount(long groupId, long userId, int status)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.messageboards.model.MBMessage getMessage(
		long messageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getMessages(
		java.lang.String className, long classPK, int status)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.messageboards.model.MBMessageDisplay getMessageDisplay(
		long messageId, int status, java.lang.String threadView)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public com.liferay.portlet.messageboards.model.MBMessageDisplay getMessageDisplay(
		com.liferay.portlet.messageboards.model.MBMessage message, int status,
		java.lang.String threadView)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getNoAssetMessages()
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getPositionInThread(long messageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getThreadMessages(
		long threadId, int status) throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getThreadMessages(
		long threadId, int status,
		java.util.Comparator<com.liferay.portlet.messageboards.model.MBMessage> comparator)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getThreadMessages(
		long threadId, int status, int start, int end)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public int getThreadMessagesCount(long threadId, int status)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public java.util.List<com.liferay.portlet.messageboards.model.MBMessage> getThreadRepliesMessages(
		long threadId, int status, int start, int end)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void reindex(long messageId)
		throws com.liferay.portal.SystemException;

	@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
	public void reindex(
		com.liferay.portlet.messageboards.model.MBMessage message)
		throws com.liferay.portal.SystemException;

	public void subscribeMessage(long userId, long messageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void unsubscribeMessage(long userId, long messageId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public void updateAsset(long userId,
		com.liferay.portlet.messageboards.model.MBMessage message,
		long[] assetCategoryIds, java.lang.String[] assetTagNames)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage updateDiscussionMessage(
		long userId, long messageId, java.lang.String subject,
		java.lang.String body, int status)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		long userId, long messageId, java.lang.String subject,
		java.lang.String body,
		java.util.List<com.liferay.portal.kernel.util.ObjectValuePair<String, byte[]>> files,
		java.util.List<String> existingFiles, double priority,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		long messageId, java.util.Date createDate, java.util.Date modifiedDate)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage updateMessage(
		long messageId, java.lang.String body)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage updateStatus(
		long userId, com.liferay.portlet.messageboards.model.MBMessage message,
		com.liferay.portal.service.ServiceContext serviceContext,
		boolean reindex)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;

	public com.liferay.portlet.messageboards.model.MBMessage updateStatus(
		long userId, long messageId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException;
}