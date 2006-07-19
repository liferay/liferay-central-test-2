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
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.activation.DataSource;
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
import javax.mail.util.ByteArrayDataSource;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.portlet.PortletSession;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

import com.liferay.portal.util.Constants;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.util.GetterUtil;
import com.liferay.util.JNDIUtil;
import com.liferay.util.StringUtil;
import com.liferay.util.Validator;
import com.sun.mail.imap.IMAPFolder;

/**
 * <a href="MailUtil.java.html"><b><i>View Source</i></b></a>
 *
 * @author  Alexander Chow
 *
 */
public class MailUtil {

	public static final String MAIL_BOX_STYLE =
		PropsUtil.get(PropsUtil.MAIL_BOX_STYLE);

	public static final String MAIL_INBOX_NAME =
		PropsUtil.get(PropsUtil.MAIL_INBOX_NAME);

	public static final String MAIL_JUNK_NAME = MAIL_BOX_STYLE + 
		PropsUtil.get(PropsUtil.MAIL_JUNK_NAME);

	public static final String MAIL_SENT_NAME = MAIL_BOX_STYLE +
		PropsUtil.get(PropsUtil.MAIL_SENT_NAME);

	public static final String MAIL_DRAFTS_NAME = MAIL_BOX_STYLE +
		PropsUtil.get(PropsUtil.MAIL_DRAFTS_NAME);

	public static final String MAIL_TRASH_NAME = MAIL_BOX_STYLE +
		PropsUtil.get(PropsUtil.MAIL_TRASH_NAME);

	public static final String [] DEFAULT_FOLDERS = {
		MAIL_INBOX_NAME, MAIL_JUNK_NAME, MAIL_SENT_NAME, MAIL_DRAFTS_NAME,
		MAIL_TRASH_NAME
	};

	public static final String MAIL_SESSION = "java:comp/env/mail/MailSession";

	public static void cleanup(HttpSession ses) throws Exception {
		_closeFolder(ses);

		Store store = (Store)ses.getAttribute(WebKeys.MAIL_STORE);
		if (store != null) {
			store.close();

			ses.removeAttribute(WebKeys.MAIL_STORE);
		}
		
		ses.removeAttribute(WebKeys.MAIL_MESSAGE);
	}

	public static void completeMessage(
			HttpSession ses, MailMessage mm, boolean send)
		throws Exception {

		String userId = (String)ses.getAttribute(WebKeys.USER_ID);
		String password = (String)ses.getAttribute(WebKeys.USER_PASSWORD);

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

			List attachments = mm.getAttachments();
			for (Iterator itr = attachments.iterator(); itr.hasNext(); ) {
				MailAttachment ma = (MailAttachment)itr.next();

				DataSource ds = new ByteArrayDataSource(
					ma.getContent(), ma.getContentType());
				he.attach(ds, ma.getFilename(), ma.getFilename());
			}

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

		Address [] ccs = mm.getCc();
		for (int i = 0; i < ccs.length; i++) {
			String ccAddy = ((InternetAddress)ccs[i]).getAddress();
			String ccName = ((InternetAddress)ccs[i]).getPersonal();
			email.addCc(ccAddy, ccName);
		}

		Address [] bccs = mm.getBcc();
		for (int i = 0; i < bccs.length; i++) {
			String bccAddy = ((InternetAddress)bccs[i]).getAddress();
			String bccName = ((InternetAddress)bccs[i]).getPersonal();
			email.addBcc(bccAddy, bccName);
		}
		
		email.setSentDate(new Date());
		email.buildMimeMessage();
		
		if (send) {
			email.send();
			setCurrentFolder(ses, MAIL_SENT_NAME);
		}
		else {
			setCurrentFolder(ses, MAIL_DRAFTS_NAME);
		}
		
		Message [] mimeMsg = { email.getMimeMessage() };
		_getCurrentFolder(ses).appendMessages(mimeMsg);

		if (send && mm.getMessageUID() > 0L) {
			setCurrentFolder(ses, MAIL_DRAFTS_NAME);

			Message msg = 
				_getCurrentFolder(ses).getMessageByUID(mm.getMessageUID());
			_getCurrentFolder(ses).setFlags(
				new Message [] { msg }, new Flags(Flags.Flag.DELETED), true);
			_getCurrentFolder(ses).expunge();
		}
	}

	public static void createFolder(HttpSession ses, String folderName) 
		throws Exception {

		for (Iterator itr = getAllFolders(ses).iterator(); itr.hasNext(); ) {
			MailFolder mf = (MailFolder)itr.next();

			if (mf.getName().equals(folderName)) {
				throw new Exception("Folder " + folderName + " already exists");
			}
		}

		Store store = _getStore(ses);

		Folder folder = null;
		try {
			if (!folderName.equals(MAIL_INBOX_NAME) && 
				!folderName.startsWith(MAIL_BOX_STYLE)) {
				folderName = MAIL_BOX_STYLE + folderName;
			}

			folder = store.getFolder(folderName);
			folder.create(Folder.HOLDS_MESSAGES);
		}
		finally {
			if (folder != null && folder.isOpen()) {
				folder.close(false);
			}
		}
	}

	public static void deleteMessages(HttpSession ses, long [] messageUIDs)
		throws Exception {

		IMAPFolder folder = _getCurrentFolder(ses);
		if (!folder.getName().equals(MAIL_TRASH_NAME)) {
			moveMessages(ses, messageUIDs, MAIL_TRASH_NAME);
		}
		else {
			Message [] msgs =
				_getCurrentFolder(ses).getMessagesByUID(messageUIDs);

			_getCurrentFolder(ses).setFlags(
				msgs, new Flags(Flags.Flag.DELETED), true);
			_getCurrentFolder(ses).expunge();
		}
	}
	
	public static String getCurrentFolderName(PortletSession ses) {
		try {
			Folder folder = _getCurrentFolder(ses);
			return(folder.getName());
		}
		catch (Exception e) {
			return null;
		}
	}

	public static Long getCurrentMessageId(PortletSession ses) {
		return (Long)ses.getAttribute(
			WebKeys.MAIL_MESSAGE, PortletSession.APPLICATION_SCOPE);
	}

	public static List getAllFolders(HttpSession ses)
		throws Exception {

		List list = new ArrayList();

		IMAPFolder root = (IMAPFolder)_getStore(ses).getDefaultFolder();

		try {
			Folder [] folders = root.list();

			_getFolders(list, folders);
		}
		finally {
			if (root != null && root.isOpen()) {
				root.close(false);
			}
		}

		return list;
	}

	private static void _getFolders(List list, Folder[] folders)
		throws MessagingException {
		
		for (int i = 0; i < folders.length; i++) {
			if ((folders[i].getType() & IMAPFolder.HOLDS_MESSAGES) != 0) {
				MailFolder mf = new MailFolder(
					folders[i].getNewMessageCount(), folders[i].getName(),
					folders[i].getMessageCount());

				list.add(mf);
			}
			
			if ((folders[i].getType() & IMAPFolder.HOLDS_FOLDERS) != 0) {
				Folder [] subfolders = folders[i].list();

				_getFolders(list, subfolders);
			}
		}
	}

	public static SortedSet getEnvelopes(HttpSession ses, Comparator comp)
		throws Exception {

		SortedSet envelopes = new TreeSet(comp);

        Message [] msgs = _getCurrentFolder(ses).getMessages();

        FetchProfile fp = new FetchProfile();
        fp.add(FetchProfile.Item.ENVELOPE);
        fp.add(FetchProfile.Item.FLAGS);

        _getCurrentFolder(ses).fetch(msgs, fp);

        for (int ii = msgs.length - 1; ii >= 0; ii--) {
			if (msgs[ii].isExpunged()) {
				continue;
			}

        	MailEnvelope me = new MailEnvelope();

        	if (MAIL_SENT_NAME.equals(_getCurrentFolder(ses).getName()) ||
        		MAIL_DRAFTS_NAME.equals(_getCurrentFolder(ses).getName())) {

	        	Address [] recipients = msgs[ii].getAllRecipients();

	        	StringBuffer email = new StringBuffer();

	        	for (int j = 0; j < recipients.length; j++) {
	        		InternetAddress ia = (InternetAddress)recipients[j];
	        		email.append(
	        			GetterUtil.get(ia.getPersonal(), ia.getAddress()));

	        		if (j < recipients.length - 1) {
	        			email.append(", ");
	        		}
	        	}

	        	me.setRecipient(email.toString());
        	}
        	else {
        		Address [] from = msgs[ii].getFrom();
        		if (from.length > 0) {
        			InternetAddress ia = (InternetAddress)from[0];
        			me.setRecipient(
        				GetterUtil.get(ia.getPersonal(), ia.getAddress()));
        		}
        	}

        	me.setDate(msgs[ii].getSentDate());
        	me.setSubject(msgs[ii].getSubject());
        	me.setMsgUID(_getCurrentFolder(ses).getUID(msgs[ii]));
        	me.setRecent(msgs[ii].isSet(Flag.RECENT));
        	me.setFlagged(msgs[ii].isSet(Flag.FLAGGED));
        	me.setAnswered(msgs[ii].isSet(Flag.ANSWERED));

        	envelopes.add(me);
        }

        return envelopes;
	}

	public static MailMessage getMessage(
			HttpSession ses, String folderName, long messageUID)
		throws Exception {

		setCurrentFolder(ses, folderName);
		return getMessage(ses, messageUID);
	}
	
	public static MailMessage getMessage(HttpSession ses, long messageUID)
		throws Exception {

		MailMessage mm = null;

		try {
			Message message =
				_getCurrentFolder(ses).getMessageByUID(messageUID);

			mm = new MailMessage();
			mm.setSubject(message.getSubject());
			mm.setFrom(message.getFrom()[0]);
			mm.setTo(message.getRecipients(RecipientType.TO));
			mm.setCc(message.getRecipients(RecipientType.CC));
			mm.setBcc(message.getRecipients(RecipientType.BCC));
			mm.setSentDate(message.getSentDate());
			mm.setReplyTo(message.getReplyTo());
			mm = _getContent(message, mm);

			MailUtil._setCurrentMessage(ses, messageUID);
		}
		catch (MessagingException me) {
			_log.error(me);
			throw me;
		}

		return mm;
	}

	public static void moveMessages(
			HttpSession ses, long [] messageUIDs, String toFolderName)
		throws Exception {

		if (!toFolderName.equals(MAIL_INBOX_NAME) && 
			!toFolderName.startsWith(MAIL_BOX_STYLE)) {
			toFolderName = MAIL_BOX_STYLE + toFolderName;
		}

		if (_getCurrentFolder(ses).getName().equals(toFolderName)) {
			return;
		}

		if (_getCurrentFolder(ses).getName().equals(MAIL_DRAFTS_NAME) ||
			toFolderName.equals(MAIL_DRAFTS_NAME)) {
			throw new Exception(
				"Cannot move message to or from the 'Drafts' folder");
		}

		IMAPFolder toFolder = null;
		try {
			toFolder = (IMAPFolder)_getStore(ses).getFolder(toFolderName);
			toFolder.open(IMAPFolder.READ_WRITE);

			Message [] msgs =
				_getCurrentFolder(ses).getMessagesByUID(messageUIDs);

			_getCurrentFolder(ses).copyMessages(msgs, toFolder);
			_getCurrentFolder(ses).setFlags(
				msgs, new Flags(Flags.Flag.DELETED), true);
			_getCurrentFolder(ses).expunge();
		}
		catch(Exception e) {
			_log.error(e);
			throw e;
		}
		finally {
			if (toFolder != null && toFolder.isOpen()) {
				toFolder.close(true);
			}
		}
	}

	public static void removeFolder(HttpSession ses, String folderName) 
		throws Exception {

		if (!folderName.equals(MAIL_INBOX_NAME) && 
			!folderName.startsWith(MAIL_BOX_STYLE)) {
			folderName = MAIL_BOX_STYLE + folderName;
		}

		for (int i = 0; i < DEFAULT_FOLDERS.length; i++) {
			if (DEFAULT_FOLDERS[i].equals(folderName)) {
				throw new Exception("The folder " + folderName + 
					" is a system-defined folder and cannot be changed");
			}
		}

		Store store = _getStore(ses);
		Folder folder = store.getFolder(folderName);
		if (!folder.exists()) {
			throw new Exception("The folder " + folderName + 
				" does not currently exist in the system");
		}

		folder.delete(true);
	}

	public static void renameFolder(
			HttpSession ses, String oldFolderName, String newFolderName)
		throws Exception {

		if (!oldFolderName.equals(MAIL_INBOX_NAME) && 
			!oldFolderName.startsWith(MAIL_BOX_STYLE)) {
			oldFolderName = MAIL_BOX_STYLE + oldFolderName;
		}

		if (!newFolderName.equals(MAIL_INBOX_NAME) && 
			!newFolderName.startsWith(MAIL_BOX_STYLE)) {
			newFolderName = MAIL_BOX_STYLE + newFolderName;
		}

		for (int i = 0; i < DEFAULT_FOLDERS.length; i++) {
			if (DEFAULT_FOLDERS[i].equals(oldFolderName)) {
				throw new Exception("The folder " + oldFolderName + 
					" is a system-defined folder and cannot be changed");
			}
			else if (DEFAULT_FOLDERS[i].equals(newFolderName)) {
				throw new Exception("The folder " + newFolderName + 
				" is a system-defined folder and cannot be changed");
			}
		}
		
		Store store = _getStore(ses);
		Folder oldFolder = store.getFolder(oldFolderName);
		Folder newFolder = store.getFolder(newFolderName);
		if (!oldFolder.exists()) {
			throw new Exception("The folder " + oldFolderName + 
				" does not exist in the system");
		}
		else if (newFolder.exists()) {
			throw new Exception("The folder " + newFolderName + 
				" already exists in the system");
		}

		if (!oldFolder.isOpen()) {
			oldFolder.open(Folder.READ_WRITE);
		}

		oldFolder.renameTo(newFolder);
		
		if (_getCurrentFolder(ses).getName().equals(oldFolderName)) {
			setCurrentFolder(ses, newFolderName);
		}
	}

	public static void setCurrentFolder(HttpSession ses, String folderName)
		throws Exception {

		_getFolder(ses, folderName);
	}

	private static void _setCurrentMessage(HttpSession ses, long messageId) {
		ses.setAttribute(WebKeys.MAIL_MESSAGE, new Long(messageId));
	}

	private static void _closeFolder(HttpSession ses) {
		IMAPFolder folder = (IMAPFolder)ses.getAttribute(WebKeys.MAIL_FOLDER);

		if (folder != null && folder.isOpen()) {
			try {
				folder.close(false);
			}
			catch (MessagingException me) {
				_log.error("Unknown error while closing current folder");
			}

			ses.removeAttribute(WebKeys.MAIL_FOLDER);
		}
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

	private static IMAPFolder _getCurrentFolder(IMAPFolder folder)
		throws Exception {
		
		if (folder != null) {
			return folder;
		}
		else {
			throw new Exception("A folder must first be selected");
		}
	}

	private static IMAPFolder _getCurrentFolder(HttpSession ses)
		throws Exception {
		
		IMAPFolder folder = (IMAPFolder)ses.getAttribute(WebKeys.MAIL_FOLDER);
		
		return _getCurrentFolder(folder);
	}

	private static IMAPFolder _getCurrentFolder(PortletSession ses)
		throws Exception {

		IMAPFolder folder = (IMAPFolder)ses.getAttribute(
			WebKeys.MAIL_FOLDER, PortletSession.APPLICATION_SCOPE);

		return _getCurrentFolder(folder);
	}

	private static IMAPFolder _getFolder(HttpSession ses, String folderName)
		throws Exception {

		if (Validator.isNull(folderName)) {
			folderName = MAIL_INBOX_NAME;
		}
		else if (!folderName.equals(MAIL_INBOX_NAME) &&
			!folderName.startsWith(MAIL_BOX_STYLE)) {

			folderName = MAIL_BOX_STYLE + folderName;
		}

		IMAPFolder folder = (IMAPFolder)ses.getAttribute(WebKeys.MAIL_FOLDER);

		if (folder != null) {
			if (!folder.getName().equals(folderName)) {
				_closeFolder(ses);

				folder = null;
			}
		}

		if (folder == null) {
			folder = (IMAPFolder)_getStore(ses).getFolder(folderName);
			folder.open(IMAPFolder.READ_WRITE);

			ses.setAttribute(WebKeys.MAIL_FOLDER, folder);
			ses.removeAttribute(WebKeys.MAIL_MESSAGE);
		}

		return folder;
	}

	private static Store _getStore(HttpSession ses)
		throws Exception {

		Store store = (Store)ses.getAttribute(WebKeys.MAIL_STORE);
		String userId = (String)ses.getAttribute(WebKeys.USER_ID);
		String password = (String)ses.getAttribute(WebKeys.USER_PASSWORD);

		if (store == null) {
			store = _getSession().getStore("imap");
			store.connect(_getIMAPHost(), _getLogin(userId), password);

			ses.setAttribute(WebKeys.MAIL_STORE, store);

			List list = getAllFolders(ses);
			for (int i = 0; i < DEFAULT_FOLDERS.length; i++) {
				boolean exists = false;

				for (Iterator itr = list.iterator(); itr.hasNext(); ) {
					MailFolder mf = (MailFolder)itr.next();

					if (DEFAULT_FOLDERS[i].equals(mf.getName())) {
						exists = true;
						break;
					}
				}

				if (!exists) {
					createFolder(ses, DEFAULT_FOLDERS[i]);
				}
			}

			if (ses.getAttribute(WebKeys.MAIL_FOLDER) == null) {
				setCurrentFolder(ses, MAIL_INBOX_NAME);
			}
		}

		return store;
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
			new InitialContext(), MAIL_SESSION);

		session.setDebug(GetterUtil.getBoolean(
			PropsUtil.get(PropsUtil.MAIL_SMTP_DEBUG)));

		if (_log.isDebugEnabled()) {
			session.getProperties().list(System.out);
		}

		return session;
	}

	private static String _getSMTPHost() throws NamingException {
		return _getSession().getProperty("mail.smtp.host");
	}

	private static String _getIMAPHost() throws NamingException {
		return _getSession().getProperty("mail.imap.host");
	}

	private static Log _log = LogFactory.getLog(MailUtil.class);

}
