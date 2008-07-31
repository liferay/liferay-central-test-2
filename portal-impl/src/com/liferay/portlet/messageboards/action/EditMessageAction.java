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

package com.liferay.portlet.messageboards.action;

import com.liferay.documentlibrary.FileNameException;
import com.liferay.documentlibrary.FileSizeException;
import com.liferay.portal.captcha.CaptchaTextException;
import com.liferay.portal.captcha.CaptchaUtil;
import com.liferay.portal.kernel.servlet.SessionErrors;
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.Constants;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ObjectValuePair;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.security.auth.PrincipalException;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.theme.ThemeDisplay;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.ActionResponseImpl;
import com.liferay.portlet.messageboards.MessageBodyException;
import com.liferay.portlet.messageboards.MessageSubjectException;
import com.liferay.portlet.messageboards.NoSuchMessageException;
import com.liferay.portlet.messageboards.RequiredMessageException;
import com.liferay.portlet.messageboards.model.MBMessage;
import com.liferay.portlet.messageboards.service.MBMessageServiceUtil;
import com.liferay.portlet.tags.TagsEntryException;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;
import javax.portlet.PortletPreferences;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditMessageAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author Brian Wing Shun Chan
 *
 */
public class EditMessageAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig portletConfig,
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		String cmd = ParamUtil.getString(actionRequest, Constants.CMD);

		try {
			if (cmd.equals(Constants.ADD) || cmd.equals(Constants.UPDATE)) {
				updateMessage(actionRequest, actionResponse);
			}
			else if (cmd.equals(Constants.DELETE)) {
				deleteMessage(actionRequest);
			}
			else if (cmd.equals(Constants.SUBSCRIBE)) {
				subscribeMessage(actionRequest);
			}
			else if (cmd.equals(Constants.UNSUBSCRIBE)) {
				unsubscribeMessage(actionRequest);
			}

			if (cmd.equals(Constants.DELETE) ||
				cmd.equals(Constants.SUBSCRIBE) ||
				cmd.equals(Constants.UNSUBSCRIBE)) {

				sendRedirect(actionRequest, actionResponse);
			}
		}
		catch (Exception e) {
			if (e instanceof NoSuchMessageException ||
				e instanceof PrincipalException ||
				e instanceof RequiredMessageException) {

				SessionErrors.add(actionRequest, e.getClass().getName());

				setForward(actionRequest, "portlet.message_boards.error");
			}
			else if (e instanceof CaptchaTextException ||
					 e instanceof FileNameException ||
					 e instanceof FileSizeException ||
					 e instanceof MessageBodyException ||
					 e instanceof MessageSubjectException) {

				SessionErrors.add(actionRequest, e.getClass().getName());
			}
			else if (e instanceof TagsEntryException) {
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
			ActionUtil.getMessage(renderRequest);
		}
		catch (Exception e) {
			if (e instanceof NoSuchMessageException ||
				e instanceof PrincipalException) {

				SessionErrors.add(renderRequest, e.getClass().getName());

				return mapping.findForward("portlet.message_boards.error");
			}
			else {
				throw e;
			}
		}

		return mapping.findForward(
			getForward(renderRequest, "portlet.message_boards.edit_message"));
	}

	protected void deleteMessage(ActionRequest actionRequest) throws Exception {
		long messageId = ParamUtil.getLong(actionRequest, "messageId");

		MBMessageServiceUtil.deleteMessage(messageId);
	}

	protected void subscribeMessage(ActionRequest actionRequest)
		throws Exception {

		long messageId = ParamUtil.getLong(actionRequest, "messageId");

		MBMessageServiceUtil.subscribeMessage(messageId);
	}

	protected void unsubscribeMessage(ActionRequest actionRequest)
		throws Exception {

		long messageId = ParamUtil.getLong(actionRequest, "messageId");

		MBMessageServiceUtil.unsubscribeMessage(messageId);
	}

	protected void updateMessage(
			ActionRequest actionRequest, ActionResponse actionResponse)
		throws Exception {

		ThemeDisplay themeDisplay = (ThemeDisplay)actionRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		PortletPreferences prefs = actionRequest.getPreferences();

		long messageId = ParamUtil.getLong(actionRequest, "messageId");

		long categoryId = ParamUtil.getLong(actionRequest, "categoryId");
		long threadId = ParamUtil.getLong(actionRequest, "threadId");
		long parentMessageId = ParamUtil.getLong(
			actionRequest, "parentMessageId");
		String subject = ParamUtil.getString(actionRequest, "subject");
		String body = ParamUtil.getString(actionRequest, "body");
		boolean attachments = ParamUtil.getBoolean(
			actionRequest, "attachments");

		List<ObjectValuePair<String, byte[]>> files =
			new ArrayList<ObjectValuePair<String, byte[]>>();

		if (attachments) {
			UploadPortletRequest uploadRequest =
				PortalUtil.getUploadPortletRequest(actionRequest);

			for (int i = 1; i <= 5; i++) {
				File file = uploadRequest.getFile("msgFile" + i);
				String fileName = uploadRequest.getFileName("msgFile" + i);
				byte[] bytes = FileUtil.getBytes(file);

				if ((bytes != null) && (bytes.length > 0)) {
					ObjectValuePair<String, byte[]> ovp =
						new ObjectValuePair<String, byte[]>(fileName, bytes);

					files.add(ovp);
				}
			}
		}

		boolean anonymous = ParamUtil.getBoolean(actionRequest, "anonymous");
		double priority = ParamUtil.getDouble(actionRequest, "priority");

		String[] tagsEntries = StringUtil.split(
			ParamUtil.getString(actionRequest, "tagsEntries"));

		String[] communityPermissions = actionRequest.getParameterValues(
			"communityPermissions");
		String[] guestPermissions = actionRequest.getParameterValues(
			"guestPermissions");

		MBMessage message = null;

		if (messageId <= 0) {
			if (PropsValues.CAPTCHA_CHECK_PORTLET_MESSAGE_BOARDS_EDIT_MESSAGE) {
				CaptchaUtil.check(actionRequest);
			}

			if (threadId <= 0) {

				// Post new thread

				message = MBMessageServiceUtil.addMessage(
					categoryId, subject, body, files, anonymous, priority,
					tagsEntries, prefs, communityPermissions, guestPermissions,
					themeDisplay);
			}
			else {

				// Post reply

				message = MBMessageServiceUtil.addMessage(
					categoryId, threadId, parentMessageId, subject, body, files,
					anonymous, priority, tagsEntries, prefs,
					communityPermissions, guestPermissions, themeDisplay);
			}
		}
		else {
			List<String> existingFiles = new ArrayList<String>();

			for (int i = 1; i <= 5; i++) {
				String path = ParamUtil.getString(
					actionRequest, "existingPath" + i);

				if (Validator.isNotNull(path)) {
					existingFiles.add(path);
				}
			}

			// Update message

			message = MBMessageServiceUtil.updateMessage(
				messageId, subject, body, files, existingFiles, priority,
				tagsEntries, prefs, themeDisplay);
		}

		PortletURL portletURL =
			((ActionResponseImpl)actionResponse).createRenderURL();

		portletURL.setParameter(
			"struts_action", "/message_boards/view_message");
		portletURL.setParameter(
			"messageId", String.valueOf(message.getMessageId()));

		actionResponse.sendRedirect(portletURL.toString());
	}

}