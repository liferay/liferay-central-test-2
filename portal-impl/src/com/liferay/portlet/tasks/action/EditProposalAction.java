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

package com.liferay.portlet.tasks.action;

import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.Layout;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.communities.action.ActionUtil;
import com.liferay.portlet.communities.action.EditPagesAction;
import com.liferay.portlet.tasks.NoSuchProposalException;
import com.liferay.portlet.tasks.ProposalDueDateException;
import com.liferay.portlet.tasks.ProposalPublishDateException;
import com.liferay.portlet.tasks.model.TasksProposal;
import com.liferay.portlet.tasks.service.TasksProposalLocalServiceUtil;
import com.liferay.portlet.tasks.service.TasksProposalServiceUtil;
import com.liferay.portlet.tasks.service.TasksReviewServiceUtil;
import com.liferay.util.servlet.SessionErrors;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditProposalAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Raymond Augé
 *
 */
public class EditProposalAction extends EditPagesAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		try {
			checkPermissions(req);
		}
		catch (PrincipalException pe) {
			return;
		}

		String cmd = ParamUtil.getString(req, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateProposal(req, res);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteProposal(req);
			}
			else if (cmd.equals("reject_review")) {
				rejectReview(req);
			}
			else if (cmd.equals("publish_proposal")) {
				publishProposal(req);
			}

			//String redirect = ParamUtil.getString(req, "pagesRedirect");

			sendRedirect(req, res);
		}
		catch (Exception e) {
			if (e instanceof NoSuchProposalException ||
				e instanceof PrincipalException) {

				SessionErrors.add(req, e.getClass().getName());

				setForward(req, "portlet.tasks.error");
			}
			else if (e instanceof ProposalDueDateException ||
					 e instanceof ProposalPublishDateException) {

				SessionErrors.add(req, e.getClass().getName(), e);
			}
			else {
				throw e;
			}
		}
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			RenderRequest req, RenderResponse res)
		throws Exception {

		try {
			checkPermissions(req);
		}
		catch (PrincipalException pe) {
			SessionErrors.add(req, PrincipalException.class.getName());

			return mapping.findForward("portlet.tasks.error");
		}

		try {
			ActionUtil.getGroup(req);

			long proposalId = ParamUtil.getLong(req, "proposalId");

			TasksProposal proposal = null;

			if (proposalId > 0) {
				proposal = TasksProposalLocalServiceUtil.getProposal(proposalId);
			}

			req.setAttribute(WebKeys.TASKS_PROPOSAL, proposal);
		}
		catch (Exception e) {
			if (e instanceof NoSuchProposalException ||
				e instanceof PrincipalException) {

				SessionErrors.add(req, e.getClass().getName());

				return mapping.findForward("portlet.tasks.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(req, "portlet.tasks.edit_proposal"));
	}

	protected void deleteProposal(ActionRequest req) throws Exception {
		long liveGroupId = ParamUtil.getLong(req, "liveGroupId");

		long proposalId = ParamUtil.getLong(req, "proposalId");

		TasksProposalServiceUtil.deleteProposal(liveGroupId, proposalId);
	}

	protected void publishProposal(ActionRequest req) throws Exception {
		long liveGroupId = ParamUtil.getLong(req, "liveGroupId");
		long stagingGroupId = ParamUtil.getLong(req, "stagingGroupId");

		long proposalId = ParamUtil.getLong(req, "proposalId");

		TasksProposal proposal =
			TasksProposalLocalServiceUtil.getProposal(proposalId);

		Layout layout = LayoutLocalServiceUtil.getLayout(proposal.getClassPK());

		Map<Long,Boolean> layoutIdMap = new LinkedHashMap<Long,Boolean>();

		layoutIdMap.put(new Long(layout.getPlid()), Boolean.FALSE);

		publishLayouts(
			layoutIdMap, stagingGroupId, liveGroupId, layout.isPrivateLayout());

		TasksProposalServiceUtil.deleteProposal(
			liveGroupId, proposal.getProposalId());
	}

	protected void rejectReview(ActionRequest req) throws Exception {
		long liveGroupId = ParamUtil.getLong(req, "liveGroupId");

		long proposalId = ParamUtil.getLong(req, "proposalId");

		boolean rejected = ParamUtil.getBoolean(req, "rejected");

		int stage = ParamUtil.getInteger(req, "stage");

		TasksReviewServiceUtil.rejectReview(
			liveGroupId, proposalId, stage, rejected);
	}

	protected void updateProposal(ActionRequest req, ActionResponse res)
		throws Exception {

		long liveGroupId = ParamUtil.getLong(req, "liveGroupId");

		long proposalId = ParamUtil.getLong(req, "proposalId");

		String description = ParamUtil.getString(req, "description");

		int stage = ParamUtil.getInteger(req, "stage");

		int dueDateMonth = ParamUtil.getInteger(req, "dueDateMonth");
		int dueDateDay = ParamUtil.getInteger(req, "dueDateDay");
		int dueDateYear = ParamUtil.getInteger(req, "dueDateYear");
		int dueDateHour = ParamUtil.getInteger(req, "dueDateHour");
		int dueDateMinute = ParamUtil.getInteger(req, "dueDateMinute");

		String cmd = ParamUtil.getString(req, Constants.CMD);

		if (cmd.equals(Constants.ADD)) {
			long selPlid = ParamUtil.getLong(req, "selPlid");

			Layout layout = LayoutLocalServiceUtil.getLayout(selPlid);

			long classNameId =
				PortalUtil.getClassNameId(Layout.class.getName());

			long reviewerId = ParamUtil.getLong(req, "reviewerId");

			TasksProposalServiceUtil.addProposal(
				liveGroupId, layout.getName(), description, classNameId,
				layout.getPlid(), reviewerId, true, true);
		}
		else {
			long[] reviewerIds = StringUtil.split(
				ParamUtil.getString(req, "reviewerIds"), 0L);
			long[] removeReviewerIds = StringUtil.split(
				ParamUtil.getString(req, "removeReviewerIds"), 0L);

			TasksProposalServiceUtil.updateProposal(
				liveGroupId, proposalId, description, dueDateMonth, dueDateDay,
				dueDateYear, dueDateHour, dueDateMinute);

			TasksReviewServiceUtil.updateReviewers(
				liveGroupId, proposalId, stage, reviewerIds,
				removeReviewerIds);
		}
	}

}