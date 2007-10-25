/**
 * Copyright (c) 2000-2007 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.polls.service.impl;

import com.liferay.counter.service.CounterLocalServiceUtil;
import com.liferay.portal.PortalException;
import com.liferay.portal.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portlet.polls.QuestionChoiceException;
import com.liferay.portlet.polls.model.PollsChoice;
import com.liferay.portlet.polls.service.base.PollsChoiceLocalServiceBaseImpl;
import com.liferay.portlet.polls.service.persistence.PollsChoiceUtil;
import com.liferay.portlet.polls.service.persistence.PollsQuestionUtil;

import java.util.List;

/**
 * <a href="PollsChoiceLocalServiceImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class PollsChoiceLocalServiceImpl
	extends PollsChoiceLocalServiceBaseImpl {

	public PollsChoice addChoice(
			long questionId, String name, String description)
		throws PortalException, SystemException {

		validate(name, description);

		PollsQuestionUtil.findByPrimaryKey(questionId);

		long choiceId = CounterLocalServiceUtil.increment();

		PollsChoice choice = PollsChoiceUtil.create(choiceId);

		choice.setQuestionId(questionId);
		choice.setName(name);
		choice.setDescription(description);

		PollsChoiceUtil.update(choice);

		return choice;
	}

	public PollsChoice getChoice(long choiceId)
		throws PortalException, SystemException {

		return PollsChoiceUtil.findByPrimaryKey(choiceId);
	}

	public List getChoices(long questionId) throws SystemException {
		return PollsChoiceUtil.findByQuestionId(questionId);
	}

	protected void validate(String name, String description)
		throws PortalException {

		if (Validator.isNull(name) ||
			Validator.isNull(description)) {

			throw new QuestionChoiceException();
		}
	}

}