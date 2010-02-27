/**
 * Copyright (c) 2000-2010 Liferay, Inc. All rights reserved.
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

import com.liferay.documentlibrary.FileNameException;
import com.liferay.documentlibrary.FileSizeException;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceContextFactory;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.messageboards.MessageBodyException;
import com.liferay.portlet.messageboards.MessageSubjectException;
import com.liferay.portlet.messageboards.NoSuchMessageException;
import com.liferay.portlet.messageboards.RequiredMessageException;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageServiceUtil;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditDiscussionAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 */
public class EditDiscussionAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ActionResponseImpl actionResponseImpl =
			(ActionResponseImpl)actionResponse;

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			String redirect = ParamUtil.getString(actionRequest, "redirect");

			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				MBMessage message = updateMessage(actionRequest);

				redirect +=
					"#" + actionResponseImpl.getNamespace() + "messageScroll" +
						message.getMessageId();
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteMessage(actionRequest);
			}

			sendRedirect(actionRequest, actionResponse, redirect);
		}
		catch (Exception e) {
			if (e instanceof NoSuchMessageException ||
				e instanceof PrincipalException ||
				e instanceof RequiredMessageException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.message_boards.error");
			}
			else if (e instanceof FileNameException ||
					 e instanceof FileSizeException ||
					 e instanceof MessageBodyException ||
					 e instanceof MessageSubjectException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				sendRedirect(actionRequest, actionResponse);
			}
			else {
				throw e;
			}
		}
	}

	protected void deleteMessage(ActionRequest actionRequest) throws Exception {
		long groupId = PortalUtil.getScopeGroupId(actionRequest);
		String className = ParamUtil.getString(actionRequest, "className");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");

		long messageId = ParamUtil.getLong(actionRequest, "messageId");

		MBMessageServiceUtil.deleteDiscussionMessage(
			groupId, className, classPK, messageId);
	}

	protected boolean isCheckMethodOnProcessAction() {
		return _CHECK_METHOD_ON_PROCESS_ACTION;
	}

	protected MBMessage updateMessage(ActionRequest actionRequest)
		throws Exception {

		String className = ParamUtil.getString(actionRequest, "className");
		long classPK = ParamUtil.getLong(actionRequest, "classPK");

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

			message = MBMessageServiceUtil.addDiscussionMessage(
				className, classPK, threadId, parentMessageId, subject, body,
				serviceContext);
		}
		else {

			// Update message

			message = MBMessageServiceUtil.updateDiscussionMessage(
				className, classPK, messageId, subject, body, serviceContext);
		}

		return message;
	}

	private static final boolean _CHECK_METHOD_ON_PROCESS_ACTION = false;

}