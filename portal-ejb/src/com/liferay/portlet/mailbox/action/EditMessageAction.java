/**
 * Copyright (c) 2000-2006 Liferay, LLC. All rights reserved.
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

package com.liferay.portlet.mailbox.action;

import java.text.DateFormat;

import com.liferay.portal.model.User;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.DateFormats;
import com.liferay.portal.util.InternetAddressUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.RenderRequestImpl;
import com.liferay.portlet.mailbox.util.MailMessage;
import com.liferay.portlet.mailbox.util.MailUtil;
import com.liferay.util.ParamUtil;
import com.liferay.util.StringPool;
import com.liferay.util.Validator;

import javax.mail.internet.InternetAddress;
import javax.portlet.PortletConfig;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

/**
 * <a href="EditMessageAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Ming-Gih Lam
 * @author 	Alexander Chow
 *
 */
public class EditMessageAction extends PortletAction {

	public ActionForward render(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			RenderRequest req, RenderResponse res)
		throws Exception {

		String composeAction = ParamUtil.getString(req, "composeAction");
		
		if ("forward".equals(composeAction) || "reply".equals(composeAction)) {
			long messageId = ParamUtil.getLong(req, "messageId");
			String folderId = ParamUtil.getString(req, "folderId");
	
			RenderRequestImpl reqImpl = (RenderRequestImpl)req;
			HttpServletRequest svltReq = reqImpl.getHttpServletRequest();
	
			MailMessage mm = 
				MailUtil.getMessage(svltReq.getSession(), folderId, messageId);
	
			User user = PortalUtil.getUser(req);
			DateFormat dateFormatter = 
				DateFormats.getDateTime(user.getLocale(), user.getTimeZone());
			req.setAttribute(
				WebKeys.MAIL_MESSAGE, _buildBody(mm, dateFormatter));
	
			if ("reply".equals(composeAction)) {
				String userEmail = PortalUtil.getUser(req).getEmailAddress();
	
				InternetAddress [] tos = (InternetAddress [])mm.getTo();
				InternetAddress [] ccs = (InternetAddress [])mm.getCc();
				InternetAddress [] rtos = (InternetAddress [])mm.getReplyTo();
	
				tos = InternetAddressUtil.removeEntries(tos, userEmail);
				ccs = InternetAddressUtil.removeEntries(ccs, userEmail);
				
				String tosStr = InternetAddressUtil.toString(tos);
				String rtosStr = InternetAddressUtil.toString(rtos);
				String ccsStr = InternetAddressUtil.toString(ccs);
				
				if (Validator.isNotNull(rtosStr)) {
					tosStr = 
						rtosStr + StringPool.COMMA + StringPool.SPACE + tosStr;
				}
	
				String [] recipients = { tosStr, ccsStr };

				req.setAttribute(WebKeys.MAIL_RECIPIENTS, recipients);
				req.setAttribute(WebKeys.MAIL_SUBJECT, 
					"Re: " + _removeSubjectPrefix(mm.getSubject()));
			}
			else {
				req.setAttribute(WebKeys.MAIL_SUBJECT,
					"Fw: " + _removeSubjectPrefix(mm.getSubject()));
				req.setAttribute(WebKeys.MAIL_ATTACHMENTS, 
					mm.getRemoteAttachments());
			}		
		}

		return mapping.findForward(
			getForward(req, "portlet.mailbox.edit_message"));
	}

	private String _buildBody(MailMessage mm, DateFormat dateFormatter) {
		InternetAddress from = ((InternetAddress)mm.getFrom());

		StringBuffer body = new StringBuffer();
		body.append("<br /><br />On " + dateFormatter.format(mm.getSentDate()));
		body.append(StringPool.COMMA + StringPool.NBSP + from.getPersonal());
		body.append("&lt;<a href=\"mailto:" + from.getAddress() + "\">");
		body.append(from.getAddress() + "</a>&gt; wrote:<br />");
		body.append("<div style=\"");
		body.append("border-left: 1px solid rgb(204, 204, 204); ");
		body.append("margin: 0pt 0pt 0pt 1ex; ");
		body.append("padding-left: 1ex; \">");
		body.append(mm.getHtmlBody() + "</div>");;

		return body.toString();
	}

	private String _removeSubjectPrefix(String subject) {
		if (Validator.isNotNull(subject)) {
			String subjectLowerCase = subject.toLowerCase(); 
			while (subjectLowerCase.startsWith("re:") || 
				subjectLowerCase.startsWith("re>") || 
				subjectLowerCase.startsWith("fw:") || 
				subjectLowerCase.startsWith("fw>"))	{
	
				subject = subject.substring(3).trim();
				subjectLowerCase = subject.toLowerCase();
			}
		}
		
		return subject;
	}

}