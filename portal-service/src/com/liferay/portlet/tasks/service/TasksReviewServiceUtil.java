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

package com.liferay.portlet.tasks.service;

public class TasksReviewServiceUtil {
	public static com.liferay.portlet.tasks.model.TasksReview approveReview(
		long proposalId, int stage)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().approveReview(proposalId, stage);
	}

	public static com.liferay.portlet.tasks.model.TasksReview rejectReview(
		long proposalId, int stage)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		return getService().rejectReview(proposalId, stage);
	}

	public static void updateReviews(long proposalId, long[][] userIdsPerStage)
		throws com.liferay.portal.PortalException,
			com.liferay.portal.SystemException {
		getService().updateReviews(proposalId, userIdsPerStage);
	}

	public static TasksReviewService getService() {
		if (_service == null) {
			throw new RuntimeException("TasksReviewService is not set");
		}

		return _service;
	}

	public void setService(TasksReviewService service) {
		_service = service;
	}

	private static TasksReviewService _service;
}