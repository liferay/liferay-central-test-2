/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.messageboards.action;

import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.workflow.WorkflowConstants;
import com.liferay.portal.model.User;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.security.auth.PrincipalThreadLocal;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.service.UserLocalServiceUtil;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.messageboards.DiscussionMaxCommentsException;
import com.liferay.portlet.messageboards.MessageBodyException;
import com.liferay.portlet.messageboards.NoSuchMessageException;
import com.liferay.portlet.messageboards.RequiredMessageException;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBDiscussionLocalServiceUtil;
import com.liferay.portlet.messageboards.service.MBMessageServiceUtil;

import java.io.IOException;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletContext;
import javax.portlet.PortletException;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * @author Brian Wing Shun Chan
 * @author Sergio González
 */
public class EditDiscussionAction extends PortletAction {

	@Override
	public void processAction(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ActionRequest actionRequest,
			ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			String redirect = PortalUtil.escapeRedirect(
				ParamUtil.getString(actionRequest, "redirect"));

			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				MBMessage message = updateMessage(actionRequest);

				boolean ajax = ParamUtil.getBoolean(actionRequest, "ajax");

				if (ajax) {
					String randomNamespace = ParamUtil.getString(
						actionRequest, "randomNamespace");

					JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

					jsonObject.put("messageId", message.getMessageId());
					jsonObject.put("randomNamespace", randomNamespace);

					writeJSON(actionRequest, actionResponse, jsonObject);

					return;
				}
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteMessage(actionRequest);
			}
			else if (cmd.equals(Constants.SUBSCRIBE_TO_COMMENTS)) {
				subscribeToComments(actionRequest, true);
			}
			else if (cmd.equals(Constants.UNSUBSCRIBE_FROM_COMMENTS)) {
				subscribeToComments(actionRequest, false);
			}

			sendRedirect(actionRequest, actionResponse, redirect);
		}
		catch (Exception e) {
			if (e instanceof DiscussionMaxCommentsException ||
				e instanceof MessageBodyException ||
				e instanceof NoSuchMessageException ||
				e instanceof PrincipalException ||
				e instanceof RequiredMessageException) {

				JSONObject jsonObject = JSONFactoryUtil.createJSONObject();

				jsonObject.putException(e);

				writeJSON(actionRequest, actionResponse, jsonObject);
			}
			else {
				throw e;
			}
		}
	}

	@Override
	public ActionForward render(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, RenderRequest renderRequest,
			RenderResponse renderResponse)
		throws Exception {

		try {
			ActionUtil.getMessage(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchMessageException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass());

				return actionMapping.findForward(
					"portlet.message_boards.error");
			}
			else {
				throw e;
			}
		}

		return actionMapping.findForward(
			getForward(
				renderRequest, "portlet.message_boards.edit_discussion"));
	}

	@Override
	public void serveResource(
			ActionMapping actionMapping, ActionForm actionForm,
			PortletConfig portletConfig, ResourceRequest resourceRequest,
			ResourceResponse resourceResponse)
		throws IOException, PortletException {

		String className = ParamUtil.getString(resourceRequest, "className");
		long classPK = ParamUtil.getLong(resourceRequest, "classPK");
		boolean hideControls = ParamUtil.getBoolean(
			resourceRequest, "hideControls");
		String permissionClassName = ParamUtil.getString(
			resourceRequest, "permissionClassName");
		long permissionClassPK = ParamUtil.getLong(
			resourceRequest, "permissionClassPK");
		boolean ratingsEnabled = ParamUtil.getBoolean(
			resourceRequest, "ratingsEnabled");
		long userId = ParamUtil.getLong(resourceRequest, "userId");

		HttpServletRequest request = PortalUtil.getHttpServletRequest(
			resourceRequest);

		request.setAttribute("liferay-ui:discussion:className", className);
		request.setAttribute(
			"liferay-ui:discussion:classPK", String.valueOf(classPK));
		request.setAttribute(
			"liferay-ui:discussion:hideControls", String.valueOf(hideControls));
		request.setAttribute(
			"liferay-ui:discussion:permissionClassName", permissionClassName);
		request.setAttribute(
			"liferay-ui:discussion:permissionClassPK",
			String.valueOf(permissionClassPK));
		request.setAttribute(
			"liferay-ui:discussion:ratingsEnabled",
			String.valueOf(ratingsEnabled));
		request.setAttribute(
			"liferay-ui:discussion:userId", String.valueOf(userId));

		int index = ParamUtil.getInteger(resourceRequest, "index");

		request.setAttribute(
			"liferay-ui:discussion:index", String.valueOf(index));

		String randomNamespace = ParamUtil.getString(
			resourceRequest, "randomNamespace");

		request.setAttribute(
			"liferay-ui:discussion:randomNamespace", randomNamespace);

		int rootIndexPage = ParamUtil.getInteger(
			resourceRequest, "rootIndexPage");

		request.setAttribute(
			"liferay-ui:discussion:rootIndexPage",
			String.valueOf(rootIndexPage));

		PortletContext portletContext = portletConfig.getPortletContext();

		PortletRequestDispatcher portletRequestDispatcher =
			portletContext.getRequestDispatcher(
				"/html/taglib/ui/discussion/page_resources.jsp");

		portletRequestDispatcher.include(resourceRequest, resourceResponse);
	}

	protected void deleteMessage(ActionRequest actionRequest) throws Exception {
		long groupId = PortalUtil.getScopeGroupId(actionRequest);

		String className = ParamUtil.getString(actionRequest, "className");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");
		String permissionClassName = ParamUtil.getString(
			actionRequest, "permissionClassName");
		long permissionClassPK = ParamUtil.getLong(
			actionRequest, "permissionClassPK");
		long permissionOwnerId = ParamUtil.getLong(
			actionRequest, "permissionOwnerId");

		long messageId = ParamUtil.getLong(actionRequest, "messageId");

		MBMessageServiceUtil.deleteDiscussionMessage(
			groupId, className, classPK, permissionClassName, permissionClassPK,
			permissionOwnerId, messageId);
	}

	@Override
	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	protected void subscribeToComments(
			ActionRequest actionRequest, boolean subscribe)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String className = ParamUtil.getString(actionRequest, "className");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");

		if (subscribe) {
			MBDiscussionLocalServiceUtil.subscribeDiscussion(
				themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
				className, classPK);
		}
		else {
			MBDiscussionLocalServiceUtil.unsubscribeDiscussion(
				themeDisplay.getUserId(), className, classPK);
		}
	}

	protected MBMessage updateMessage(ActionRequest actionRequest)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		String className = ParamUtil.getString(actionRequest, "className");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");
		String permissionClassName = ParamUtil.getString(
			actionRequest, "permissionClassName");
		long permissionClassPK = ParamUtil.getLong(
			actionRequest, "permissionClassPK");
		long permissionOwnerId = ParamUtil.getLong(
			actionRequest, "permissionOwnerId");

		long messageId = ParamUtil.getLong(actionRequest, "messageId");

		long threadId = ParamUtil.getLong(actionRequest, "threadId");
		long parentMessageId = ParamUtil.getLong(
			actionRequest, "parentMessageId");
		String subject = ParamUtil.getString(actionRequest, "subject");
		String body = ParamUtil.getString(actionRequest, "body");

		ServiceContext serviceContext = ServiceContextFactory.getInstance(
			MBMessage.class.getName(), actionRequest);

		MBMessage message = null;

		if (messageId <= 0) {

			// Add message

			User user = null;

			if (themeDisplay.isSignedIn()) {
				user = themeDisplay.getUser();
			}
			else {
				String emailAddress = ParamUtil.getString(
					actionRequest, "emailAddress");

				user = UserLocalServiceUtil.fetchUserByEmailAddress(
					themeDisplay.getCompanyId(), emailAddress);

				if ((user == null) ||
					(user.getStatus() != WorkflowConstants.STATUS_INCOMPLETE)) {

					return null;
				}
			}

			String name = PrincipalThreadLocal.getName();

			PrincipalThreadLocal.setName(user.getUserId());

			try {
				message = MBMessageServiceUtil.addDiscussionMessage(
					serviceContext.getScopeGroupId(), className, classPK,
					permissionClassName, permissionClassPK, permissionOwnerId,
					threadId, parentMessageId, subject, body, serviceContext);
			}
			finally {
				PrincipalThreadLocal.setName(name);
			}
		}
		else {

			// Update message

			message = MBMessageServiceUtil.updateDiscussionMessage(
				className, classPK, permissionClassName, permissionClassPK,
				permissionOwnerId, messageId, subject, body, serviceContext);
		}

		// Subscription

		boolean subscribe = ParamUtil.getBoolean(actionRequest, "subscribe");

		if (subscribe) {
			MBDiscussionLocalServiceUtil.subscribeDiscussion(
				themeDisplay.getUserId(), themeDisplay.getScopeGroupId(),
				className, classPK);
		}

		return message;
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

}