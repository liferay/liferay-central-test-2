/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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
 * <a href="MBDiscussionLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link MBDiscussionLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       MBDiscussionLocalService
 * @generated
 */
public class MBDiscussionLocalServiceWrapper implements MBDiscussionLocalService {
	public MBDiscussionLocalServiceWrapper(
		MBDiscussionLocalService mbDiscussionLocalService) {
		_mbDiscussionLocalService = mbDiscussionLocalService;
	}

	public com.liferay.portlet.messageboards.model.MBDiscussion addMBDiscussion(
		com.liferay.portlet.messageboards.model.MBDiscussion mbDiscussion)
		throws com.liferay.portal.SystemException {
		return _mbDiscussionLocalService.addMBDiscussion(mbDiscussion);
	}

	public com.liferay.portlet.messageboards.model.MBDiscussion createMBDiscussion(
		long discussionId) {
		return _mbDiscussionLocalService.createMBDiscussion(discussionId);
	}

	public void deleteMBDiscussion(long discussionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_mbDiscussionLocalService.deleteMBDiscussion(discussionId);
	}

	public void deleteMBDiscussion(
		com.liferay.portlet.messageboards.model.MBDiscussion mbDiscussion)
		throws com.liferay.portal.SystemException {
		_mbDiscussionLocalService.deleteMBDiscussion(mbDiscussion);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _mbDiscussionLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _mbDiscussionLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.messageboards.model.MBDiscussion getMBDiscussion(
		long discussionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _mbDiscussionLocalService.getMBDiscussion(discussionId);
	}

	public java.util.List<com.liferay.portlet.messageboards.model.MBDiscussion> getMBDiscussions(
		int start, int end) throws com.liferay.portal.SystemException {
		return _mbDiscussionLocalService.getMBDiscussions(start, end);
	}

	public int getMBDiscussionsCount()
		throws com.liferay.portal.SystemException {
		return _mbDiscussionLocalService.getMBDiscussionsCount();
	}

	public com.liferay.portlet.messageboards.model.MBDiscussion updateMBDiscussion(
		com.liferay.portlet.messageboards.model.MBDiscussion mbDiscussion)
		throws com.liferay.portal.SystemException {
		return _mbDiscussionLocalService.updateMBDiscussion(mbDiscussion);
	}

	public com.liferay.portlet.messageboards.model.MBDiscussion updateMBDiscussion(
		com.liferay.portlet.messageboards.model.MBDiscussion mbDiscussion,
		boolean merge) throws com.liferay.portal.SystemException {
		return _mbDiscussionLocalService.updateMBDiscussion(mbDiscussion, merge);
	}

	public com.liferay.portlet.messageboards.model.MBDiscussion addDiscussion(
		long classNameId, long classPK, long threadId)
		throws com.liferay.portal.SystemException {
		return _mbDiscussionLocalService.addDiscussion(classNameId, classPK,
			threadId);
	}

	public com.liferay.portlet.messageboards.model.MBDiscussion getDiscussion(
		long discussionId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _mbDiscussionLocalService.getDiscussion(discussionId);
	}

	public com.liferay.portlet.messageboards.model.MBDiscussion getDiscussion(
		java.lang.String className, long classPK)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _mbDiscussionLocalService.getDiscussion(className, classPK);
	}

	public com.liferay.portlet.messageboards.model.MBDiscussion getThreadDiscussion(
		long threadId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _mbDiscussionLocalService.getThreadDiscussion(threadId);
	}

	public MBDiscussionLocalService getWrappedMBDiscussionLocalService() {
		return _mbDiscussionLocalService;
	}

	private MBDiscussionLocalService _mbDiscussionLocalService;
}