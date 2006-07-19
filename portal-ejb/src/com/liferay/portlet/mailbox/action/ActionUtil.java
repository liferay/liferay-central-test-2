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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.mail.Address;
import javax.servlet.http.HttpSession;

import com.liferay.portal.util.ContentTypeUtil;
import com.liferay.portal.util.InternetAddressUtil;
import com.liferay.portlet.mailbox.util.MailAttachment;
import com.liferay.portlet.mailbox.util.MailMessage;
import com.liferay.portlet.mailbox.util.MailUtil;
import com.liferay.util.FileUtil;
import com.liferay.util.servlet.UploadPortletRequest;

/**
 * <a href="ActionUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Alexander Chow
 *
 */
public class ActionUtil {

	public static void completeMessage(
			Address from, String tos, String ccs, String bccs, 
			String subject, String body, Map attachments, HttpSession ses, 
			boolean send, long draftMessageUID) 
		throws Exception {

		MailMessage mm = new MailMessage();
		mm.setFrom(from);
		mm.setTo(InternetAddressUtil.getAddresses(tos));
		mm.setCc(InternetAddressUtil.getAddresses(ccs));
		mm.setBcc(InternetAddressUtil.getAddresses(bccs));
		mm.setSubject(subject);
		mm.setHtmlBody(body);
		mm.setMessageUID(draftMessageUID);

		Set filenames = attachments.keySet();
		for (Iterator itr = filenames.iterator(); itr.hasNext(); ) {
			String filename = (String)itr.next();
			byte [] attachment = (byte [])attachments.get(filename);

			MailAttachment ma = new MailAttachment();
			ma.setFilename(filename);
			ma.setContentType(ContentTypeUtil.getContentType(filename));
			ma.setContent(attachment);

			mm.appendAttachment(ma);
		}

		MailUtil.completeMessage(ses, mm, send);
	}

	public static Map getAttachments(UploadPortletRequest uploadReq) 
		throws Exception {
		
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
}
