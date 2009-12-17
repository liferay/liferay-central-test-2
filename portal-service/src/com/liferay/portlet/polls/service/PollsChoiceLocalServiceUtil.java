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

package com.liferay.portlet.polls.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="PollsChoiceLocalServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link PollsChoiceLocalService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PollsChoiceLocalService
 * @generated
 */
public class PollsChoiceLocalServiceUtil {
	public static com.liferay.portlet.polls.model.PollsChoice addPollsChoice(
		com.liferay.portlet.polls.model.PollsChoice pollsChoice)
		throws com.liferay.portal.SystemException {
		return getService().addPollsChoice(pollsChoice);
	}

	public static com.liferay.portlet.polls.model.PollsChoice createPollsChoice(
		long choiceId) {
		return getService().createPollsChoice(choiceId);
	}

	public static void deletePollsChoice(long choiceId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().deletePollsChoice(choiceId);
	}

	public static void deletePollsChoice(
		com.liferay.portlet.polls.model.PollsChoice pollsChoice)
		throws com.liferay.portal.SystemException {
		getService().deletePollsChoice(pollsChoice);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery)
		throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery);
	}

	public static java.util.List<Object> dynamicQuery(
		com.liferay.portal.kernel.dao.orm.DynamicQuery dynamicQuery, int start,
		int end) throws com.liferay.portal.SystemException {
		return getService().dynamicQuery(dynamicQuery, start, end);
	}

	public static com.liferay.portlet.polls.model.PollsChoice getPollsChoice(
		long choiceId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getPollsChoice(choiceId);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsChoice> getPollsChoices(
		int start, int end) throws com.liferay.portal.SystemException {
		return getService().getPollsChoices(start, end);
	}

	public static int getPollsChoicesCount()
		throws com.liferay.portal.SystemException {
		return getService().getPollsChoicesCount();
	}

	public static com.liferay.portlet.polls.model.PollsChoice updatePollsChoice(
		com.liferay.portlet.polls.model.PollsChoice pollsChoice)
		throws com.liferay.portal.SystemException {
		return getService().updatePollsChoice(pollsChoice);
	}

	public static com.liferay.portlet.polls.model.PollsChoice updatePollsChoice(
		com.liferay.portlet.polls.model.PollsChoice pollsChoice, boolean merge)
		throws com.liferay.portal.SystemException {
		return getService().updatePollsChoice(pollsChoice, merge);
	}

	public static com.liferay.portlet.polls.model.PollsChoice addChoice(
		java.lang.String uuid, long questionId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().addChoice(uuid, questionId, name, description);
	}

	public static com.liferay.portlet.polls.model.PollsChoice getChoice(
		long choiceId)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().getChoice(choiceId);
	}

	public static java.util.List<com.liferay.portlet.polls.model.PollsChoice> getChoices(
		long questionId) throws com.liferay.portal.SystemException {
		return getService().getChoices(questionId);
	}

	public static int getChoicesCount(long questionId)
		throws com.liferay.portal.SystemException {
		return getService().getChoicesCount(questionId);
	}

	public static com.liferay.portlet.polls.model.PollsChoice updateChoice(
		long choiceId, long questionId, java.lang.String name,
		java.lang.String description)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().updateChoice(choiceId, questionId, name, description);
	}

	public static PollsChoiceLocalService getService() {
		if (_service == null) {
			_service = (PollsChoiceLocalService)PortalBeanLocatorUtil.locate(PollsChoiceLocalService.class.getName());
		}

		return _service;
	}

	public void setService(PollsChoiceLocalService service) {
		_service = service;
	}

	private static PollsChoiceLocalService _service;
}