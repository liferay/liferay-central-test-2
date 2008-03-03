/**
 * Copyright (c) 2000-2008 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portlet.tasks.model.TasksProposal;
import com.liferay.portlet.tasks.model.TasksReview;
import com.liferay.portlet.tasks.service.TasksReviewLocalServiceUtil;
import com.liferay.portlet.tasks.util.TasksUtil;
import com.liferay.util.LocalizationUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * <a href="TasksProposalImpl.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class TasksProposalImpl extends TasksProposalModelImpl
	implements TasksProposal {

	public static final String STAGE_REVIEW_APPROVED =
		"stage-x-review-approved";

	public static final String STAGE_REVIEW_REJECTED =
		"stage-x-review-rejected";

	public static final String STAGE_REVIEW_PENDING = "stage-x-pending-review";

	public static final String STAGE_REVIEW_UNASSIGNED =
		"stage-x-review-unassigned";

	public TasksProposalImpl() {
	}

	public int getCurrentStage() {
		int numberOfApprovalStages =
			TasksUtil.getNumberOfApprovalStages(getGroupId(), getCompanyId());

		int currentStage = 1;

		String statusKey = null;

		do {
			statusKey = getCurrentStatus(currentStage);

			if (statusKey.equals(STAGE_REVIEW_REJECTED)) {
				break;
			}
			else if (statusKey.equals(STAGE_REVIEW_PENDING)) {
				break;
			}
			else if (statusKey.equals(STAGE_REVIEW_APPROVED)) {
				if (numberOfApprovalStages > currentStage) {
					currentStage++;
				}
				else {
					break;
				}
			}
			else if (statusKey.equals(STAGE_REVIEW_UNASSIGNED) &&
					currentStage > 1) {
				currentStage++;
			}
			else if (currentStage == 1) {
				break;
			}
		}
		while (currentStage <= numberOfApprovalStages);

		return currentStage;
	}

	public String getCurrentStatus() {
		int numberOfApprovalStages =
			TasksUtil.getNumberOfApprovalStages(getGroupId(), getCompanyId());

		int currentStage = 1;

		String statusKey = null;

		do {
			statusKey = getCurrentStatus(currentStage);

			if (statusKey.equals(STAGE_REVIEW_REJECTED)) {
				break;
			}
			else if (statusKey.equals(STAGE_REVIEW_PENDING)) {
				break;
			}
			else if (statusKey.equals(STAGE_REVIEW_APPROVED)) {
				if (numberOfApprovalStages > currentStage) {
					currentStage++;
				}
				else {
					break;
				}
			}
			else if (statusKey.equals(STAGE_REVIEW_UNASSIGNED) &&
					currentStage > 1) {
				currentStage++;
			}
			else if (currentStage == 1) {
				break;
			}
		}
		while (currentStage <= numberOfApprovalStages);

		return statusKey;
	}

	public String getCurrentStatus(int stage) {
		if (stage <= 0) {
			throw new IllegalArgumentException(
				"Illegal value " + stage + " set for approval stage.");
		}

		List<TasksReview> reviews = new ArrayList<TasksReview>();

		try {
			reviews =
				TasksReviewLocalServiceUtil.getReviews(getProposalId(), stage);
		}
		catch (Exception e) {
		}

		if (reviews.size() <= 0) {
			return STAGE_REVIEW_UNASSIGNED;
		}

		List<TasksReview> completedReviews = new ArrayList<TasksReview>();

		try {
			completedReviews =
				TasksReviewLocalServiceUtil.getReviews(
					getProposalId(), stage, true);
		}
		catch (Exception e) {
		}

		if (completedReviews.size() < reviews.size()) {
			return STAGE_REVIEW_PENDING;
		}

		List<TasksReview> completedRejectedReviews =
			new ArrayList<TasksReview>();

		try {
			completedRejectedReviews =
				TasksReviewLocalServiceUtil.getReviews(
					getProposalId(), stage, true, true);
		}
		catch (Exception e) {
		}

		if (completedRejectedReviews.size() > 0) {
			return STAGE_REVIEW_REJECTED;
		}
		else {
			return STAGE_REVIEW_APPROVED;
		}
	}

	public String getName(Locale locale) {
		String localeLanguageId = LocaleUtil.toLanguageId(locale);

		return getName(localeLanguageId);
	}

	public String getName(String localeLanguageId) {
		return LocalizationUtil.getLocalization(getName(), localeLanguageId);
	}

	public String getName(Locale locale, boolean useDefault) {
		String localeLanguageId = LocaleUtil.toLanguageId(locale);

		return getName(localeLanguageId, useDefault);
	}

	public String getName(String localeLanguageId, boolean useDefault) {
		return LocalizationUtil.getLocalization(
			getName(), localeLanguageId, useDefault);
	}

	public void setName(String name, Locale locale) {
		String localeLanguageId = LocaleUtil.toLanguageId(locale);

		setName(
			LocalizationUtil.updateLocalization(
				getName(), "name", name, localeLanguageId));
	}

}