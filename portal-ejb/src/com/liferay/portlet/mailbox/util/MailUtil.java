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

package com.liferay.portlet.mailbox.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.mail.Address;
import javax.mail.FetchProfile;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Flags.Flag;
import javax.mail.Message.RecipientType;
import javax.mail.internet.InternetAddress;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

import com.liferay.portal.util.Constants;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.util.GetterUtil;
import com.liferay.util.JNDIUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;
import com.liferay.util.mail.MailEngine;
import com.sun.mail.imap.IMAPFolder;

/**
 * <a href="MailUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Alexander Chow
 *
 */
public class MailUtil {

	public static final String SESSION_FOLDER = 
		MailUtil.class.getName() + "_MAIL_FOLDER_";
	
	public static final String SESSION_STORE = 
		MailUtil.class.getName() + "_MAIL_STORE_";

	public static final String MAIL_INBOX_NAME =
		PropsUtil.get(PropsUtil.MAIL_INBOX_NAME);

	public static final String MAIL_JUNK_NAME =
		PropsUtil.get(PropsUtil.MAIL_JUNK_NAME);

	public static final String MAIL_SENT_NAME =
		PropsUtil.get(PropsUtil.MAIL_SENT_NAME);

	public static final String MAIL_DRAFTS_NAME =
		PropsUtil.get(PropsUtil.MAIL_DRAFTS_NAME);

	public static final String MAIL_TRASH_NAME =
		PropsUtil.get(PropsUtil.MAIL_TRASH_NAME);

	public static final String [] DEFAULT_FOLDERS = {
		MAIL_INBOX_NAME, MAIL_JUNK_NAME, MAIL_SENT_NAME, MAIL_DRAFTS_NAME,
		MAIL_TRASH_NAME
	};

	public static List getAllFolders(Store store) 
		throws Exception {
		
		List list = new ArrayList();

		Folder root = store.getDefaultFolder();

		try {
			Folder [] folders = root.list();

			for (int i = 0; i < folders.length; i++) {
				if ((folders[i].getType() & Folder.HOLDS_MESSAGES) != 0) {
					list.add(folders[i]);
				}
				else {
					// TODO: add ability to have a hierarchy of folders
				}
			}
		}
		finally {
			if (root != null && root.isOpen()) {
				root.close(false);
			}
		}

		return list;
	}

	public static MailMessage getMessage(Folder folder, int messageUID)
		throws Exception {

		Message message = folder.getMessage(messageUID);
		
		MailMessage mm = new MailMessage();
		mm.setSubject(message.getSubject());
		mm.setFrom(message.getFrom()[0]);
		mm.setTo(message.getRecipients(RecipientType.TO));
		mm.setCc(message.getRecipients(RecipientType.CC));
		mm.setBcc(message.getRecipients(RecipientType.BCC));
		mm = _getContent(message, mm);

		return mm;
	}

	public static void deleteMessages(
			Store store, Folder folder, int [] messageUIDs) 
		throws Exception {

		if (!folder.getName().equals(MAIL_TRASH_NAME)) {
			moveMessages(store, folder, messageUIDs, MAIL_TRASH_NAME);
		}
		else {
			Message [] msgs = folder.getMessages(messageUIDs);
			folder.setFlags(msgs, new Flags(Flags.Flag.DELETED), true);
			folder.expunge();
		}
	}

	public static void moveMessages(
			Store store, Folder folder, int [] messageUIDs, String toFolderName)
		throws Exception {
		
		if (folder.getName().equals(toFolderName)) {
			return;
		}

		Folder toFolder = null;
		try {
			toFolder = store.getFolder(toFolderName);
			toFolder.open(Folder.READ_WRITE);
			
			Message [] msgs = folder.getMessages(messageUIDs);
	
			folder.copyMessages(msgs, toFolder);
			folder.setFlags(msgs, new Flags(Flags.Flag.DELETED), true);
			folder.expunge();
		}
		finally {
			if (toFolder != null && toFolder.isOpen()) {
				toFolder.close(false);
			}
		}
	}
	
	public static void sendMessage(
			String userId, String password, MailMessage mm) 
		throws Exception {

		Email email = null;
		if (mm.isSimple()) {
			SimpleEmail se = new SimpleEmail();
			se.setMsg(mm.getPlainBody());

			email = se;
		}
		else {
			HtmlEmail he = new HtmlEmail();
			he.setMsg(mm.getPlainBody());
			he.setHtmlMsg(mm.getHtmlBody());

			email = he;
		}

		String fromAddy = ((InternetAddress)mm.getFrom()).getAddress();
		String fromName = ((InternetAddress)mm.getFrom()).getPersonal();

		email.setSubject(mm.getSubject());
		email.setHostName(_getSMTPHost());
		email.setAuthentication(_getLogin(userId), password);
		email.setFrom(fromAddy, fromName);

		Address [] tos = mm.getTo();
		for (int i = 0; i < tos.length; i++) {
			String toAddy = ((InternetAddress)tos[i]).getAddress();
			String toName = ((InternetAddress)tos[i]).getPersonal();
			email.addTo(toAddy, toName);
		}

		Address [] ccs = mm.getTo();
		for (int i = 0; i < ccs.length; i++) {
			String ccAddy = ((InternetAddress)ccs[i]).getAddress();
			String ccName = ((InternetAddress)ccs[i]).getPersonal();
			email.addCc(ccAddy, ccName);
		}

		Address [] bccs = mm.getTo();
		for (int i = 0; i < bccs.length; i++) {
			String bccAddy = ((InternetAddress)bccs[i]).getAddress();
			String bccName = ((InternetAddress)bccs[i]).getPersonal();
			email.addBcc(bccAddy, bccName);
		}

		email.send();
	}

	public static List getEnvelopes(Folder folder) 
		throws Exception {

		List envelopes = new ArrayList();

        Message [] msgs = folder.getMessages();

        FetchProfile fp = new FetchProfile();
        fp.add(FetchProfile.Item.ENVELOPE);
        fp.add(FetchProfile.Item.FLAGS);

        folder.fetch(msgs, fp);

        for (int i = 0; i < msgs.length; i++) {
        	MailEnvelope me = new MailEnvelope();

        	if (MAIL_SENT_NAME.equals(folder.getName()) ||
        		MAIL_DRAFTS_NAME.equals(folder.getName())) {
        		
	        	Address [] recipients = msgs[i].getAllRecipients();

	        	StringBuffer email = new StringBuffer();

	        	for (int j = 0; j < recipients.length; j++) {
	        		InternetAddress ia = (InternetAddress)recipients[j];
	        		email.append(
	        			GetterUtil.get(ia.getPersonal(), ia.getAddress()));

	        		if (j < recipients.length - 1) {
	        			email.append(", ");
	        		}
	        	}

	        	me.setEmail(email.toString());
        	}
        	else {
        		Address [] from = msgs[i].getFrom();
        		if (from.length > 0) {
        			InternetAddress ia = (InternetAddress)from[0];
        			me.setEmail(
        				GetterUtil.get(ia.getPersonal(), ia.getAddress()));
        		}
        	}

        	me.setDate(msgs[i].getSentDate());
        	me.setSubject(msgs[i].getSubject());
        	me.setUID(((IMAPFolder)folder).getUID(msgs[i]));
        	me.setRecent(msgs[i].isSet(Flag.RECENT));
        	me.setFlagged(msgs[i].isSet(Flag.FLAGGED));
        	me.setAnswered(msgs[i].isSet(Flag.ANSWERED));

        	envelopes.add(me);
        }

        return envelopes;
	}

	public static Store getStore(HttpServletRequest req) throws Exception {		
		HttpSession ses = req.getSession();

		Store store = (Store)ses.getAttribute(SESSION_STORE);

		if (store == null) {
			String userId = PortalUtil.getUserId(req);
			String password = PortalUtil.getUserPassword(req);
			
			store = _getSession().getStore("imap");
			store.connect(_getIMAPHost(), _getLogin(userId), password);
			
			ses.setAttribute(SESSION_STORE, store);
		}

		return store;
	}

	public static void closeStore(HttpServletRequest req) throws Exception {
		closeFolder(req);

		HttpSession ses = req.getSession();
		
		Store store = (Store)ses.getAttribute(SESSION_STORE);

		if (store != null) {
			ses.setAttribute(SESSION_STORE, null);
			
			store.close();
		}
	}

	public static Folder getFolder(HttpServletRequest req, String folderName) 
		throws Exception {

		if (Validator.isNull(folderName)) {
			folderName = MAIL_INBOX_NAME;
		}

		HttpSession ses = req.getSession();

		Folder folder = (Folder)ses.getAttribute(SESSION_FOLDER);
		
		if (folder != null) {
			if (!folder.getName().equals(folderName)) {
				closeFolder(req);
			}
		}
		
		if (folder == null) {
			folder = getStore(req).getFolder(folderName);
			folder.open(Folder.READ_WRITE);
		}
		
		return folder;
	}
	
	public static void closeFolder(HttpServletRequest req) throws Exception {
		HttpSession ses = req.getSession();

		Folder folder = (Folder)ses.getAttribute(SESSION_FOLDER);

		if (folder != null && folder.isOpen()) {
			ses.setAttribute(SESSION_FOLDER, null);

			folder.close(true);
		}
	}

	private static String _getSMTPHost() throws NamingException {
		return _getSession().getProperty("mail.smtp.host");
	}

	private static String _getIMAPHost() throws NamingException {
		return _getSession().getProperty("mail.imap.host");
	}

	private static String _getLogin(String userId) {
		String login = userId;
		if (GetterUtil.getBoolean(
				PropsUtil.get(PropsUtil.MAIL_USERNAME_REPLACE))) {
			login = StringUtil.replace(login, ".", "_");
		}

		return login;
	}

	private static Session _getSession() throws NamingException {
		Session session = (Session)JNDIUtil.lookup(
			new InitialContext(), MailEngine.MAIL_SESSION);

		session.setDebug(GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.MAIL_SMTP_DEBUG)));

		if (_log.isDebugEnabled()) {
			session.getProperties().list(System.out);
		}

		return session;
	}

	private static MailMessage _getContent(Part part, MailMessage mm)
		throws IOException, MessagingException {

		if (part.getContent() instanceof Multipart) {
            Multipart mp = (Multipart)part.getContent();

            if (part.isMimeType(Constants.MULTIPART_ALTERNATE)) {
	            for (int i = 0; i < mp.getCount(); i++) {
	            	Part mpbp = mp.getBodyPart(i);
	            	
	                if (part.isMimeType(Constants.TEXT_PLAIN)) {
	                	mm.setPlainBody((String)mpbp.getContent());
	                }
	                else if (part.isMimeType(Constants.TEXT_HTML)) {
	                	mm.setHtmlBody((String)mpbp.getContent());
	                }
	                else {
	                	mm = _getContent(mpbp, mm);
	                }
	            }
            }
            else {
            	for (int i = 0; i < mp.getCount(); i++) {
	            	Part mpbp = mp.getBodyPart(i);
                	mm = _getContent(mpbp, mm);
            	}
            }
        }
        else if (part.isMimeType(Constants.TEXT_PLAIN)) {
        	mm.appendPlainBody((String)part.getContent());
        }
        else if (part.isMimeType(Constants.TEXT_HTML)) {
        	mm.appendHtmlBody((String)part.getContent());
        }
        else if (part.isMimeType(Constants.MESSAGE_RFC822)) {
        	// TODO: nested
        }
        else {
            mm.appendAttachment(_getAttachment(part));
        }

        return mm;
	}

	private static MailAttachment _getAttachment(Part part) 
		throws IOException, MessagingException {
		
		MailAttachment ma = new MailAttachment();
		ma.setContentType(part.getContentType());
		ma.setFilename(part.getFileName());

		String [] headers = part.getHeader("Content-id");
		if (headers != null && headers.length > 0) {
		    ma.setContentID(headers[0]);
		}

		InputStream in = part.getInputStream();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		byte[] buffer = new byte[8192];
		int count = 0;

		while ((count = in.read(buffer)) >= 0) {
			baos.write(buffer,0,count);
		}

		in.close();

		ma.setContent(baos.toByteArray());

		return ma;
	}

	private static Log _log = LogFactory.getLog(MailUtil.class);
	
}