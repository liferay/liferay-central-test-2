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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;

/**
 * <a href="PollsVoteServiceUtil.java.html"><b><i>View Source</i></b></a>
 *
 * <p>
 * ServiceBuilder generated this class. Modifications in this class will be
 * overwritten the next time is generated.
 * </p>
 *
 * <p>
 * This class provides static methods for the
 * {@link PollsVoteService} bean. The static methods of
 * this class calls the same methods of the bean instance. It's convenient to be
 * able to just write one line to call a method on a bean instead of writing a
 * lookup call and a method call.
 * </p>
 *
 * @author    Brian Wing Shun Chan
 * @see       PollsVoteService
 * @generated
 */
public class PollsVoteServiceUtil {
	public static com.liferay.portlet.polls.model.PollsVote addVote(
		long questionId, long choiceId)
		throws com.liferay.portal.kernel.exception.PortalException,
			com.liferay.portal.kernel.exception.SystemException {
		return getService().addVote(questionId, choiceId);
	}

	public static PollsVoteService getService() {
		if (_service == null) {
			_service = (PollsVoteService)PortalBeanLocatorUtil.locate(PollsVoteService.class.getName());
		}

		return _service;
	}

	public void setService(PollsVoteService service) {
		_service = service;
	}

	private static PollsVoteService _service;
}