/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.polls.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="PollsVoteLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link PollsVoteLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PollsVoteLocalService
 * @generated
 */
public class PollsVoteLocalServiceUtil {
	public static com.liferay.portlet.polls.model.PollsVote addPollsVote(
		com.liferay.portlet.polls.model.PollsVote pollsVote)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().addPollsVote(pollsVote);
	}

	public static com.liferay.portlet.polls.model.PollsVote createPollsVote(
		long voteId) {
		return getService().createPollsVote(voteId);
	}

	public static void deletePollsVote(long voteId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		getService().deletePollsVote(voteId);
	}

	public static void deletePollsVote(
		com.liferay.portlet.polls.model.PollsVote pollsVote)
		throws com.liferay.portal.kernel.exception.SystemException {
		getService().deletePollsVote(pollsVote);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsVote> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsVote> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsVote> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end,
		com.liferay.portal.kernel.util.OrderByComparator orderByComparator)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService()
				   .dynamicQuery(dynamicQuery, start, end, orderByComparator);
	}

	public static long dynamicQueryCount(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().dynamicQueryCount(dynamicQuery);
	}

	public static com.liferay.portlet.polls.model.PollsVote getPollsVote(
		long voteId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getPollsVote(voteId);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsVote> getPollsVotes(
		int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPollsVotes(start, end);
	}

	public static int getPollsVotesCount()
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getPollsVotesCount();
	}

	public static com.liferay.portlet.polls.model.PollsVote updatePollsVote(
		com.liferay.portlet.polls.model.PollsVote pollsVote)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updatePollsVote(pollsVote);
	}

	public static com.liferay.portlet.polls.model.PollsVote updatePollsVote(
		com.liferay.portlet.polls.model.PollsVote pollsVote, boolean merge)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().updatePollsVote(pollsVote, merge);
	}

	public static com.liferay.portlet.polls.model.PollsVote addVote(
		long userId, long questionId, long choiceId,
		com.liferay.portal.service.ServiceContext serviceContext)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addVote(userId, questionId, choiceId, serviceContext);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsVote> getChoiceVotes(
		long choiceId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getChoiceVotes(choiceId, start, end);
	}

	public static int getChoiceVotesCount(long choiceId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getChoiceVotesCount(choiceId);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsVote> getQuestionVotes(
		long questionId, int start, int end)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getQuestionVotes(questionId, start, end);
	}

	public static int getQuestionVotesCount(long questionId)
		throws com.liferay.portal.kernel.exception.SystemException {
		return getService().getQuestionVotesCount(questionId);
	}

	public static com.liferay.portlet.polls.model.PollsVote getVote(
		long questionId, long userId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().getVote(questionId, userId);
	}

	public static PollsVoteLocalService getService() {
		if (_service == null) {
			_service = (PollsVoteLocalService)PortalBeanLocatorUtil.locate(PollsVoteLocalService.class.getName());
		}

		return _service;
	}

	public void setService(PollsVoteLocalService service) {
		_service = service;
	}

	private static PollsVoteLocalService _service;
}