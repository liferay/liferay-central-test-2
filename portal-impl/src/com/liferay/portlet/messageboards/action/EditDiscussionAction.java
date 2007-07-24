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

package com.liferay.portlet.messageboards.action;

import com.liferay.documentlibrary.FileNameException;
import com.liferay.documentlibrary.FileSizeException;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.messageboards.MessageBodyException;
import com.liferay.portlet.messageboards.MessageSubjectException;
import com.liferay.portlet.messageboards.NoSuchMessageException;
import com.liferay.portlet.messageboards.RequiredMessageException;
import com.liferay.portlet.messageboards.service.MBMessageServiceUtil;
import com.liferay.util.ParamUtil;
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
 * <a href="EditDiscussionAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class EditDiscussionAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		String cmd = ParamUtil.getString(req, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateMessage(req);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteMessage(req);
			}

			sendRedirect(req, res);
		}
		catch (Exception e) {
			if (e instanceof NoSuchMessageException ||
				e instanceof PrincipalException ||
				e instanceof RequiredMessageException) {

				SessionErrors.add(req, e.getClass().getName());

				setForward(req, "portlet.message_boards.error");
			}
			else if (e instanceof FileNameException ||
					 e instanceof FileSizeException ||
					 e instanceof MessageBodyException ||
					 e instanceof MessageSubjectException) {

				SessionErrors.add(req, e.getClass().getName());
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

		return mapping.findForward(getForward(req));
	}

	protected void deleteMessage(ActionRequest req) throws Exception {
		long groupId = PortalUtil.getPortletGroupId(req);
		String className = ParamUtil.getString(req, "className");
		long classPK = ParamUtil.getLong(req, "classPK");

		long messageId = ParamUtil.getLong(req, "messageId");

		MBMessageServiceUtil.deleteDiscussionMessage(
			groupId, className, classPK, messageId);
	}

	protected void updateMessage(ActionRequest req) throws Exception {
		long groupId = PortalUtil.getPortletGroupId(req);
		String className = ParamUtil.getString(req, "className");
		long classPK = ParamUtil.getLong(req, "classPK");

		long messageId = ParamUtil.getLong(req, "messageId");

		long threadId = ParamUtil.getLong(req, "threadId");
		long parentMessageId = ParamUtil.getLong(req, "parentMessageId");
		String subject = ParamUtil.getString(req, "subject");
		String body = ParamUtil.getString(req, "body");

		if (messageId <= 0) {

			// Add message

			MBMessageServiceUtil.addDiscussionMessage(
				groupId, className, classPK, threadId, parentMessageId, subject,
				body);
		}
		else {

			// Update message

			MBMessageServiceUtil.updateDiscussionMessage(
				groupId, className, classPK, messageId, subject, body);
		}
	}

}