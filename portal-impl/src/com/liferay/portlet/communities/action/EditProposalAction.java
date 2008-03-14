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

package com.liferay.portlet.communities.action;

import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.impl.PortletImpl;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.communities.util.StagingUtil;
import com.liferay.portlet.tasks.DuplicateReviewUserIdException;
import com.liferay.portlet.tasks.NoSuchProposalException;
import com.liferay.portlet.tasks.ProposalDueDateException;
import com.liferay.portlet.tasks.ProposalPublishDateException;
import com.liferay.portlet.tasks.model.TasksProposal;
import com.liferay.portlet.tasks.service.TasksProposalLocalServiceUtil;
import com.liferay.portlet.tasks.service.TasksProposalServiceUtil;
import com.liferay.portlet.tasks.service.TasksReviewServiceUtil;
import com.liferay.util.servlet.SessionErrors;

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
			else if (cmd.equals(Constants.APPROVE)) {
				approveReview(req);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteProposal(req);
			}
			else if (cmd.equals(Constants.PUBLISH)) {
				publishProposal(req);
			}
			else if (cmd.equals(Constants.REJECT)) {
				rejectReview(req);
			}

			String redirect = ParamUtil.getString(req, "pagesRedirect");

			if (Validator.isNull(redirect)) {
				redirect = ParamUtil.getString(req, "redirect");
			}

			sendRedirect(req, res, redirect);
		}
		catch (Exception e) {
			if (e instanceof NoSuchProposalException ||
				e instanceof PrincipalException) {

				SessionErrors.add(req, e.getClass().getName());

				setForward(req, "portlet.communities.error");
			}
			else if (e instanceof DuplicateReviewUserIdException ||
					 e instanceof ProposalDueDateException ||
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

			return mapping.findForward("portlet.communities.error");
		}

		try {
			ActionUtil.getGroup(req);

			long proposalId = ParamUtil.getLong(req, "proposalId");

			TasksProposal proposal = null;

			if (proposalId > 0) {
				proposal = TasksProposalLocalServiceUtil.getProposal(
					proposalId);
			}

			req.setAttribute(WebKeys.TASKS_PROPOSAL, proposal);
		}
		catch (Exception e) {
			if (e instanceof NoSuchProposalException ||
				e instanceof PrincipalException) {

				SessionErrors.add(req, e.getClass().getName());

				return mapping.findForward("portlet.communities.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(req, "portlet.communities.edit_proposal"));
	}

	protected void approveReview(ActionRequest req) throws Exception {
		long proposalId = ParamUtil.getLong(req, "proposalId");

		int stage = ParamUtil.getInteger(req, "stage");

		TasksReviewServiceUtil.approveReview(proposalId, stage);
	}

	protected void deleteProposal(ActionRequest req) throws Exception {
		long proposalId = ParamUtil.getLong(req, "proposalId");

		TasksProposalServiceUtil.deleteProposal(proposalId);
	}

	protected void publishProposal(ActionRequest req) throws Exception {
		long proposalId = ParamUtil.getLong(req, "proposalId");

		long groupId = ParamUtil.getLong(req, "groupId");

		Group group = GroupLocalServiceUtil.getGroup(groupId);

		Group liveGroup = group;
		Group stagingGroup = liveGroup.getStagingGroup();

		TasksProposal proposal = TasksProposalLocalServiceUtil.getProposal(
			proposalId);

		String className = PortalUtil.getClassName(proposal.getClassNameId());

		if (className.equals(Layout.class.getName())) {
			StagingUtil.publishToLive(req);
		}
		else if (className.equals(Portlet.class.getName())) {
			String classPK = proposal.getClassPK();

			String portletId = classPK.substring(
				classPK.indexOf(PortletImpl.LAYOUT_SEPARATOR) +
					PortletImpl.LAYOUT_SEPARATOR.length());

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				proposal.getCompanyId(), portletId);

			StagingUtil.publishToLive(req, portlet);
		}

		TasksProposalServiceUtil.deleteProposal(proposal.getProposalId());
	}

	protected void rejectReview(ActionRequest req) throws Exception {
		long proposalId = ParamUtil.getLong(req, "proposalId");

		int stage = ParamUtil.getInteger(req, "stage");

		TasksReviewServiceUtil.rejectReview(proposalId, stage);
	}

	protected void updateProposal(ActionRequest req, ActionResponse res)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)req.getAttribute(
			WebKeys.THEME_DISPLAY);

		long proposalId = ParamUtil.getLong(req, "proposalId");

		String description = ParamUtil.getString(req, "description");

		if (proposalId <= 0) {
			long groupId = ParamUtil.getLong(req, "groupId");

			long reviewUserId = ParamUtil.getLong(req, "reviewUserId");

			String className = ParamUtil.getString(req, "className");
			String classPK = ParamUtil.getString(req, "classPK");

			String name = StringPool.BLANK;

			if (className.equals(Layout.class.getName())) {
				long plid = GetterUtil.getLong(classPK);

				Layout layout = LayoutLocalServiceUtil.getLayout(plid);

				name = layout.getName(themeDisplay.getLocale());
			}
			else if (className.equals(Portlet.class.getName())) {
				String portletId = classPK.substring(
					classPK.indexOf(PortletImpl.LAYOUT_SEPARATOR) +
						PortletImpl.LAYOUT_SEPARATOR.length());

				name = PortalUtil.getPortletTitle(
					portletId, themeDisplay.getCompanyId(),
					themeDisplay.getLocale());
			}

			boolean addCommunityPermissions = true;
			boolean addGuestPermissions = true;

			TasksProposalServiceUtil.addProposal(
				groupId, className, classPK, name, description, reviewUserId,
				addCommunityPermissions, addGuestPermissions);
		}
		else {
			int dueDateMonth = ParamUtil.getInteger(req, "dueDateMonth");
			int dueDateDay = ParamUtil.getInteger(req, "dueDateDay");
			int dueDateYear = ParamUtil.getInteger(req, "dueDateYear");
			int dueDateHour = ParamUtil.getInteger(req, "dueDateHour");
			int dueDateMinute = ParamUtil.getInteger(req, "dueDateMinute");

			TasksProposalServiceUtil.updateProposal(
				proposalId, description, dueDateMonth, dueDateDay, dueDateYear,
				dueDateHour, dueDateMinute);

			long groupId = ParamUtil.getLong(req, "groupId");

			Group group = GroupLocalServiceUtil.getGroup(groupId);

			int workflowStages = group.getWorkflowStages();

			long[][] userIdsPerStage = new long[workflowStages][0];

			for (int i = 2; i <= workflowStages; i++) {
				long[] userIds = StringUtil.split(
					ParamUtil.getString(req, "reviewUserIds_" + i), 0L);

				userIdsPerStage[i - 2] = userIds;
			}

			TasksReviewServiceUtil.updateReviews(proposalId, userIdsPerStage);
		}
	}

}