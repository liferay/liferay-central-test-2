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

package com.liferay.portlet.polls.service;


/**
 * <a href="PollsChoiceLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class is a wrapper for {@link PollsChoiceLocalService}.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PollsChoiceLocalService
 * @generated
 */
public class PollsChoiceLocalServiceWrapper implements PollsChoiceLocalService {
	public PollsChoiceLocalServiceWrapper(
		PollsChoiceLocalService pollsChoiceLocalService) {
		_pollsChoiceLocalService = pollsChoiceLocalService;
	}

	public com.liferay.portlet.polls.model.PollsChoice addPollsChoice(
		com.liferay.portlet.polls.model.PollsChoice pollsChoice)
		throws com.liferay.portal.SystemException {
		return _pollsChoiceLocalService.addPollsChoice(pollsChoice);
	}

	public com.liferay.portlet.polls.model.PollsChoice createPollsChoice(
		long choiceId) {
		return _pollsChoiceLocalService.createPollsChoice(choiceId);
	}

	public void deletePollsChoice(long choiceId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		_pollsChoiceLocalService.deletePollsChoice(choiceId);
	}

	public void deletePollsChoice(
		com.liferay.portlet.polls.model.PollsChoice pollsChoice)
		throws com.liferay.portal.SystemException {
		_pollsChoiceLocalService.deletePollsChoice(pollsChoice);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return _pollsChoiceLocalService.dynamicQuery(dynamicQuery);
	}

	public java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return _pollsChoiceLocalService.dynamicQuery(dynamicQuery, start, end);
	}

	public com.liferay.portlet.polls.model.PollsChoice getPollsChoice(
		long choiceId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _pollsChoiceLocalService.getPollsChoice(choiceId);
	}

	public java.util.List<com.liferay.portlet.polls.model.PollsChoice> getPollsChoices(
		int start, int end) throws com.liferay.portal.SystemException {
		return _pollsChoiceLocalService.getPollsChoices(start, end);
	}

	public int getPollsChoicesCount() throws com.liferay.portal.SystemException {
		return _pollsChoiceLocalService.getPollsChoicesCount();
	}

	public com.liferay.portlet.polls.model.PollsChoice updatePollsChoice(
		com.liferay.portlet.polls.model.PollsChoice pollsChoice)
		throws com.liferay.portal.SystemException {
		return _pollsChoiceLocalService.updatePollsChoice(pollsChoice);
	}

	public com.liferay.portlet.polls.model.PollsChoice updatePollsChoice(
		com.liferay.portlet.polls.model.PollsChoice pollsChoice, boolean merge)
		throws com.liferay.portal.SystemException {
		return _pollsChoiceLocalService.updatePollsChoice(pollsChoice, merge);
	}

	public com.liferay.portlet.polls.model.PollsChoice addChoice(
		java.lang.String uuid, long questionId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _pollsChoiceLocalService.addChoice(uuid, questionId, name,
			description);
	}

	public com.liferay.portlet.polls.model.PollsChoice getChoice(long choiceId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _pollsChoiceLocalService.getChoice(choiceId);
	}

	public java.util.List<com.liferay.portlet.polls.model.PollsChoice> getChoices(
		long questionId) throws com.liferay.portal.SystemException {
		return _pollsChoiceLocalService.getChoices(questionId);
	}

	public int getChoicesCount(long questionId)
		throws com.liferay.portal.SystemException {
		return _pollsChoiceLocalService.getChoicesCount(questionId);
	}

	public com.liferay.portlet.polls.model.PollsChoice updateChoice(
		long choiceId, long questionId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return _pollsChoiceLocalService.updateChoice(choiceId, questionId,
			name, description);
	}

	public PollsChoiceLocalService getWrappedPollsChoiceLocalService() {
		return _pollsChoiceLocalService;
	}

	private PollsChoiceLocalService _pollsChoiceLocalService;
}