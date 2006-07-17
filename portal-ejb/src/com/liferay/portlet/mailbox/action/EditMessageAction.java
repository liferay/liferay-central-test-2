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

import java.io.File;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.mail.Address;
import javax.mail.internet.InternetAddress;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionMapping;

import com.liferay.portal.model.User;
import com.liferay.portal.struts.PortletAction;
import com.liferay.portal.util.Constants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.mailbox.util.MailAttachment;
import com.liferay.portlet.mailbox.util.MailMessage;
import com.liferay.portlet.mailbox.util.MailUtil;
import com.liferay.util.FileUtil;
import com.liferay.util.ParamUtil;
import com.liferay.util.StringPool;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;
import com.liferay.util.servlet.UploadPortletRequest;

/**
 * <a href="EditMessageAction.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Ming-Gih Lam
 * @author 	Alexander Chow
 *
 */
public class EditMessageAction extends PortletAction {

	public void processAction(
			ActionMapping mapping, ActionForm form, PortletConfig config,
			ActionRequest req, ActionResponse res)
		throws Exception {

		String cmd = ParamUtil.getString(req, Constants.CMD);

		try {
			if (cmd.equals(Constants.SEND)) {
				Address [] tos = _getAddresses(ParamUtil.getString(req, "tos"));
				Address [] ccs = _getAddresses(ParamUtil.getString(req, "ccs"));
				Address [] bccs = 
					_getAddresses(ParamUtil.getString(req, "bccs"));
				String subject = ParamUtil.getString(req, "subject");
				String body = ParamUtil.getString(req, "body");
				Map attachments = _getAttachments(
					PortalUtil.getUploadPortletRequest(req));

				User user = PortalUtil.getUser(req);				
				Address from = new InternetAddress(
					user.getEmailAddress(), user.getFullName());

				MailMessage mm = new MailMessage();
				mm.setFrom(from);
				mm.setTo(tos);
				mm.setCc(ccs);
				mm.setBcc(bccs);
				mm.setSubject(subject);
				mm.setHtmlBody(body);
				
				Set filenames = attachments.keySet();
				for (Iterator itr = filenames.iterator(); itr.hasNext(); ) {
					String filename = (String)itr.next();
					byte [] attachment = (byte [])attachments.get(filename);
					
					MailAttachment ma = new MailAttachment();
					ma.setFilename(filename);
					ma.setContentType(_getContentType(filename));
					ma.setContent(attachment);
					
					mm.appendAttachment(ma);
				}
				
				MailUtil.sendMessage(
					user.getUserId(), PortalUtil.getUserPassword(req), mm);

				setForward(req, "portlet.mailbox.view");
			}
			else {
				if (Validator.isNotNull(cmd)) {
				}				

				setForward(req, "portlet.mailbox.edit_message");
			}
		}
		catch (Exception e) {
			_log.error("Unable to send message");
			//setForward(req, "portlet.mailbox.error");
		}
	}

	private String _getContentType(String filename) {
		String [] fa = StringUtil.split(filename, StringPool.PERIOD);

		String ext = fa[fa.length-1];
		
		if (ext.equals("doc")) {
			return "application/msword";
		}
		else if (ext.equals("pdf")) {
			return "application/pdf";
		}
		else if (ext.equals("txt")) {
			return Constants.TEXT_PLAIN;
		}
		else if (ext.equals("html") || ext.equals("htm")) {
			return Constants.TEXT_HTML;
		}

		return "application/octet-stream";
	}

	private Address [] _getAddresses(String mailingList) throws Exception {
		String [] entries = mailingList.split(",|;");
		
		List addresses = new ArrayList();
		for (int i = 0; i < entries.length; i++) {
			String entry = entries[i].trim();
			if (Validator.isNotNull(entry)) {
				addresses.add(_getAddress(entry));
			}
		}

		return (Address [])addresses.toArray(new Address [] {});
	}
	
	private InternetAddress _getAddress(String entry) throws Exception {
		InternetAddress ia = new InternetAddress();
		
		String [] parts = entry.split(StringPool.LESS_THAN);
		
		if (parts.length == 2) {
			String name = parts[0].trim();

			if (name.endsWith(StringPool.QUOTE) || 
				name.endsWith(StringPool.APOSTROPHE)) {
				name = name.substring(0, name.length() - 1);
			}
			
			if (name.startsWith(StringPool.QUOTE) ||
				name.startsWith(StringPool.APOSTROPHE)) {
				name = name.substring(1);
			}
			
			String address = parts[1].trim();

			if (address.endsWith(StringPool.GREATER_THAN)) {
				address = address.substring(0, address.length() - 1);
			}

			if (!Validator.isAddress(address)) {
				// throw exception
			}

			ia.setAddress(address.trim());
			ia.setPersonal(name.trim());
		}
		else if (parts.length == 1) {
			if (!Validator.isAddress(parts[0])) {
				// throw exception
			}
			ia.setAddress(parts[0].trim());
		}
		else {
			// throw exception
		}
		
		return ia;
	}
	
	private Map _getAttachments(UploadPortletRequest uploadReq) throws Exception {
		Map attachments = new HashMap();

		String prefix = "attachment";

		Enumeration enu = uploadReq.getParameterNames();

		while (enu.hasMoreElements()) {
			String name = (String)enu.nextElement();

			if (name.startsWith(prefix)) {
				File file = uploadReq.getFile(name);
				byte[] bytes = FileUtil.getBytes(file);

				if (bytes != null && bytes.length > 0) {
					attachments.put(uploadReq.getFileName(name), bytes);
				}
			}
		}

		return attachments;
	}

	private static Log _log = LogFactory.getLog(EditMessageAction.class);
	
}