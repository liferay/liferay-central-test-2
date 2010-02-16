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

package com.liferay.portlet.tasks.model.impl;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.language.LanguageUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portlet.tasks.model.TasksProposal;
import com.liferay.portlet.tasks.model.TasksReview;
import com.liferay.portlet.tasks.service.TasksReviewLocalServiceUtil;

import java.util.List;
import java.util.Locale;

/**
 * <a href="TasksProposalImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class TasksProposalImpl
	extends TasksProposalModelImpl implements TasksProposal {

	public TasksProposalImpl() {
	}

	public String getStatus(Locale locale)
		throws PortalException, SystemException {

		String status = null;
		int stage = 1;

		Group group = GroupLocalServiceUtil.getGroup(getGroupId());

		int stages = group.getWorkflowStages();

		for (; stage <= stages; stage++) {
			status = getStatus(stage);

			if (status.equals(_STATUS_APPROVED)) {
			}
			else if (status.equals(_STATUS_PENDING) ||
					 status.equals(_STATUS_REJECTED)) {

				break;
			}
			else if ((status.equals(_STATUS_UNASSIGNED)) &&
					 (stage > 1)) {
			}
			else if (stage == 0) {
				break;
			}

			if (stage == stages) {
				break;
			}
		}

		return LanguageUtil.format(locale, status, String.valueOf(stage + 1));
	}

	protected String getStatus(int stage) throws SystemException {
		List<TasksReview> reviews = TasksReviewLocalServiceUtil.getReviews(
			getProposalId(), stage);

		if (reviews.size() <= 0) {
			return _STATUS_UNASSIGNED;
		}

		List<TasksReview> completedReviews =
			TasksReviewLocalServiceUtil.getReviews(
				getProposalId(), stage, true);

		if (completedReviews.size() < reviews.size()) {
			return _STATUS_PENDING;
		}

		List<TasksReview> completedRejectedReviews =
			TasksReviewLocalServiceUtil.getReviews(
				getProposalId(), stage, true, true);

		if (completedRejectedReviews.size() > 0) {
			return _STATUS_REJECTED;
		}
		else {
			return _STATUS_APPROVED;
		}
	}

	private static final String _STATUS_APPROVED = "stage-x-review-approved";

	private static final String _STATUS_PENDING = "stage-x-pending-review";

	private static final String _STATUS_REJECTED = "stage-x-review-rejected";

	private static final String _STATUS_UNASSIGNED =
		"stage-x-review-unassigned";

}