/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.communities.action;

import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.staging.StagingUtil;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.Portlet;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.PortletLocalServiceUtil;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.tasks.DuplicateReviewUserIdException;
import com.liferay.portlet.tasks.NoSuchProposalException;
import com.liferay.portlet.tasks.ProposalDueDateException;
import com.liferay.portlet.tasks.ProposalPublishDateException;
import com.liferay.portlet.tasks.model.TasksProposal;
import com.liferay.portlet.tasks.service.TasksProposalLocalServiceUtil;
import com.liferay.portlet.tasks.service.TasksProposalServiceUtil;
import com.liferay.portlet.tasks.service.TasksReviewServiceUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Raymond Augé
 */
public class EditProposalAction extends EditPagesAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		try {
			checkPermissions(actionRequest);
		}
		catch (PrincipalException pe) {
			return;
		}

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateProposal(actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.APPROVE)) {
				approveReview(actionRequest);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteProposal(actionRequest);
			}
			else if (cmd.equals(Constants.PUBLISH)) {
				publishProposal(actionRequest);
			}
			else if (cmd.equals(Constants.REJECT)) {
				rejectReview(actionRequest);
			}

			String redirect = ParamUtil.getString(
				actionRequest, "pagesRedirect");

			if (Validator.isNull(redirect)) {
				redirect = ParamUtil.getString(actionRequest, "redirect");
			}

			sendRedirect(actionRequest, actionResponse, redirect);
		}
		catch (Exception e) {
			if (e instanceof NoSuchProposalException ||
				e instanceof PrincipalException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.layouts_admin.error");
			}
			else if (e instanceof DuplicateReviewUserIdException ||
					 e instanceof ProposalDueDateException ||
					 e instanceof ProposalPublishDateException) {

				SessionErrors.add(actionRequest, e.getClass().getName(), e);
			}
			else {
				throw e;
			}
		}
	}

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			RenderRequest renderRequest, RenderResponse renderResponse)
		throws Exception {

		try {
			checkPermissions(renderRequest);
		}
		catch (PrincipalException pe) {
			SessionErrors.add(
				renderRequest, PrincipalException.class.getName());

			return mapping.findForward("portlet.layouts_admin.error");
		}

		try {
			ActionUtil.getGroup(renderRequest);

			long proposalId = ParamUtil.getLong(renderRequest, "proposalId");

			TasksProposal proposal = null;

			if (proposalId > 0) {
				proposal = TasksProposalLocalServiceUtil.getProposal(
					proposalId);
			}

			renderRequest.setAttribute(WebKeys.TASKS_PROPOSAL, proposal);
		}
		catch (Exception e) {
			if (e instanceof NoSuchProposalException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.layouts_admin.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(renderRequest, "portlet.layouts_admin.edit_proposal"));
	}

	protected void approveReview(ActionRequest actionRequest) throws Exception {
		long proposalId = ParamUtil.getLong(actionRequest, "proposalId");

		int stage = ParamUtil.getInteger(actionRequest, "stage");

		TasksReviewServiceUtil.approveReview(proposalId, stage);
	}

	protected void deleteProposal(ActionRequest actionRequest)
		throws Exception {

		long proposalId = ParamUtil.getLong(actionRequest, "proposalId");

		TasksProposalServiceUtil.deleteProposal(proposalId);
	}

	protected void publishProposal(ActionRequest actionRequest)
		throws Exception {

		long proposalId = ParamUtil.getLong(actionRequest, "proposalId");

		TasksProposal proposal = TasksProposalLocalServiceUtil.getProposal(
			proposalId);

		String className = PortalUtil.getClassName(proposal.getClassNameId());

		if (className.equals(Layout.class.getName())) {
			StagingUtil.publishToLive(actionRequest);
		}
		else if (className.equals(Portlet.class.getName())) {
			String classPK = proposal.getClassPK();

			String portletId = classPK.substring(
				classPK.indexOf(PortletConstants.LAYOUT_SEPARATOR) +
					PortletConstants.LAYOUT_SEPARATOR.length());

			Portlet portlet = PortletLocalServiceUtil.getPortletById(
				proposal.getCompanyId(), portletId);

			StagingUtil.publishToLive(actionRequest, portlet);
		}

		TasksProposalServiceUtil.deleteProposal(proposal.getProposalId());
	}

	protected void rejectReview(ActionRequest actionRequest) throws Exception {
		long proposalId = ParamUtil.getLong(actionRequest, "proposalId");

		int stage = ParamUtil.getInteger(actionRequest, "stage");

		TasksReviewServiceUtil.rejectReview(proposalId, stage);
	}

	protected void updateProposal(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		long proposalId = ParamUtil.getLong(actionRequest, "proposalId");

		String description = ParamUtil.getString(actionRequest, "description");

		if (proposalId <= 0) {
			long groupId = ParamUtil.getLong(actionRequest, "groupId");

			long reviewUserId = ParamUtil.getLong(
				actionRequest, "reviewUserId");

			String className = ParamUtil.getString(actionRequest, "className");
			String classPK = ParamUtil.getString(actionRequest, "classPK");

			String name = StringPool.BLANK;

			if (className.equals(Layout.class.getName())) {
				long plid = GetterUtil.getLong(classPK);

				Layout layout = LayoutLocalServiceUtil.getLayout(plid);

				name = layout.getName(themeDisplay.getLocale());
			}
			else if (className.equals(Portlet.class.getName())) {
				String portletId = classPK.substring(
					classPK.indexOf(PortletConstants.LAYOUT_SEPARATOR) +
						PortletConstants.LAYOUT_SEPARATOR.length());

				name = PortalUtil.getPortletTitle(
					portletId, themeDisplay.getLocale());
			}

			boolean addCommunityPermissions = true;
			boolean addGuestPermissions = true;

			TasksProposalServiceUtil.addProposal(
				groupId, className, classPK, name, description, reviewUserId,
				addCommunityPermissions, addGuestPermissions);
		}
		else {
			int dueDateMonth = ParamUtil.getInteger(
				actionRequest, "dueDateMonth");
			int dueDateDay = ParamUtil.getInteger(
				actionRequest, "dueDateDay");
			int dueDateYear = ParamUtil.getInteger(
				actionRequest, "dueDateYear");
			int dueDateHour = ParamUtil.getInteger(
				actionRequest, "dueDateHour");
			int dueDateMinute = ParamUtil.getInteger(
				actionRequest, "dueDateMinute");

			TasksProposalServiceUtil.updateProposal(
				proposalId, description, dueDateMonth, dueDateDay, dueDateYear,
				dueDateHour, dueDateMinute);

			long groupId = ParamUtil.getLong(actionRequest, "groupId");

			Group group = GroupLocalServiceUtil.getGroup(groupId);

			int workflowStages = group.getWorkflowStages();

			long[][] userIdsPerStage = new long[workflowStages][0];

			for (int i = 2; i <= workflowStages; i++) {
				long[] userIds = StringUtil.split(ParamUtil.getString(
					actionRequest, "reviewUserIds_" + i), 0L);

				userIdsPerStage[i - 2] = userIds;
			}

			TasksReviewServiceUtil.updateReviews(proposalId, userIdsPerStage);
		}
	}

}