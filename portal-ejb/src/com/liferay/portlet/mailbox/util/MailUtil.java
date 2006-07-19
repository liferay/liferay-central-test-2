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
import java.util.Collection;
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
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.apache.commons.mail.SimpleEmail;

import com.liferay.portal.util.Constants;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.WebKeys;
import com.liferay.portlet.mailbox.ContentException;
import com.liferay.portlet.mailbox.ContentPathException;
import com.liferay.portlet.mailbox.FolderException;
import com.liferay.portlet.mailbox.StoreException;
import com.liferay.util.GetterUtil;
import com.liferay.util.ListUtil;
import com.liferay.util.JNDIUtil;
import com.liferay.util.StringPool;
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

	public static void cleanup(HttpSession ses) throws StoreException {
		try {
			_closeFolder(ses);

			Store store = (Store)ses.getAttribute(WebKeys.MAIL_STORE);
			if (store != null) {
				store.close();

				ses.removeAttribute(WebKeys.MAIL_STORE);
			}
			
			ses.removeAttribute(WebKeys.MAIL_MESSAGE);
		}
		catch (MessagingException e) {
			_log.error("Unable to close store");
			throw new StoreException(e);
		}
	}

	public static void completeMessage(
			HttpSession ses, MailMessage mm, boolean send, long draftId)
		throws ContentException, ContentPathException, FolderException, 
			StoreException {

		try {
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
				he.setHtmlMsg(mm.getHtmlBody());

				List attachments = mm.getAttachments();
				for (Iterator itr = attachments.iterator(); itr.hasNext(); ) {
					MailAttachment ma = (MailAttachment)itr.next();

					DataSource ds = new ByteArrayDataSource(
						ma.getContent(), ma.getContentType());
					he.attach(ds, ma.getFilename(), ma.getFilename());
				}
				
				List remoteAttachments = mm.getRemoteAttachments();
				for (Iterator itr = remoteAttachments.iterator(); itr.hasNext(); ) {
					RemoteMailAttachment rma = (RemoteMailAttachment)itr.next();

					Object [] parts = getAttachment(ses, rma.getContentPath());
					DataSource ds = new ByteArrayDataSource(
						(byte [])parts[0], (String)parts[1]);

					he.attach(ds, rma.getFilename(), rma.getFilename());
				}

				email = he;
			}

			String fromAddy = ((InternetAddress)mm.getFrom()).getAddress();
			String fromName = ((InternetAddress)mm.getFrom()).getPersonal();

			email.setFrom(fromAddy, fromName);		
			
			Collection tos = ListUtil.fromArray(mm.getTo());
			Collection ccs = ListUtil.fromArray(mm.getCc());
			Collection bccs = ListUtil.fromArray(mm.getBcc());
			if (!tos.isEmpty()) {
				email.setTo(tos);
			}
			if (!ccs.isEmpty()) {
				email.setCc(ccs);
			}
			if (!bccs.isEmpty()) {
				email.setBcc(bccs);
			}

			email.setSubject(mm.getSubject());
			email.setHostName(_getSMTPHost());
			email.setAuthentication(_getLogin(userId), password);
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

			if (draftId > 0L) {
				setCurrentFolder(ses, MAIL_DRAFTS_NAME);
				Message msg = _getCurrentFolder(ses).getMessageByUID(draftId);

				_getCurrentFolder(ses).setFlags(
					new Message [] { msg }, new Flags(Flags.Flag.DELETED), true);
				_getCurrentFolder(ses).expunge();
			}
		} 
		catch (EmailException e) {
			_log.error("Error in building and sending email object");
			throw new ContentException(e);
		}
		catch (NamingException e) {
			_log.error("Error in building and sending email object");
			throw new ContentException(e);
		} 
		catch (MessagingException e) {
			_log.error("Error in building and sending email object");
			throw new ContentException(e);
		}
	}

	public static void createFolder(HttpSession ses, String folderName) 
		throws FolderException, StoreException {

		Folder folder = null;
		try {
			for (Iterator itr = getAllFolders(ses).iterator(); itr.hasNext(); ) {
				MailFolder mf = (MailFolder)itr.next();

				if (mf.getName().equals(folderName)) {
					throw new FolderException(
						"Folder " + folderName + " already exists");
				}
			}

			Store store = _getStore(ses);

			if (!folderName.equals(MAIL_INBOX_NAME) && 
				!folderName.startsWith(MAIL_BOX_STYLE)) {
				folderName = MAIL_BOX_STYLE + folderName;
			}

			folder = store.getFolder(folderName);
			folder.create(Folder.HOLDS_MESSAGES);
		}
		catch (MessagingException ex) {
			_log.error("Error creating new folder: " + folderName);
			throw new FolderException(ex);
		}
		finally {
			try {
				if (folder != null && folder.isOpen()) {
					folder.close(false);
				}
			} catch (Exception ex) {
			}
		}
	}

	public static void deleteMessages(HttpSession ses, long [] messageUIDs)
		throws FolderException, StoreException {

		try {
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
		catch (MessagingException ex) {
			_log.error("Unable to delete messages");
			throw new FolderException(ex);
		}
	}

	public static Object [] getAttachment(HttpSession ses, String contentPath)
		throws ContentException, ContentPathException, FolderException, 
			StoreException {

		Object [] parts = null;
		
		try {
			String [] path = RemoteMailAttachment.parsePath(contentPath);

			String folderName = path[0];
			long messageUID = GetterUtil.getLong(path[1]);
			String mimePath = path[2];

			setCurrentFolder(ses, folderName);
			Message message = 
				_getCurrentFolder(ses).getMessageByUID(messageUID);

			parts = _getAttachmentFromPath(message, mimePath);
		}
		catch (MessagingException ex) {
			_log.error(
				"Error obtaining attachment from content path: " + contentPath);
			throw new ContentException(ex);
		} catch (IOException ex) {
			_log.error(
				"Error obtaining attachment from content path: " + contentPath);
			throw new ContentException(ex);
		}
		catch (ContentPathException ex) {
			_log.error("Invalid content path: " + contentPath);
			throw ex;
		}
		
		return parts;
	}

	private static Object [] _getAttachmentFromPath(
			Part part, String mimePath)
		throws ContentPathException, IOException, MessagingException {

		int index = GetterUtil.getInteger(
			StringUtil.split(mimePath.substring(1), StringPool.PERIOD)[0]);

		if (part.getContent() instanceof Multipart) {
    		String prefix = Integer.toString(index) + StringPool.PERIOD;

            Multipart mp = (Multipart)part.getContent();

            for (int i = 0; i < mp.getCount(); i++) {
        		if (index == i) {
        			return _getAttachmentFromPath(mp.getBodyPart(i),
        				mimePath.substring(prefix.length()));
        		}
        	}
        	
        	throw new ContentPathException();
        }
		else if (index != -1) {
        	throw new ContentPathException();
		}

		InputStream in = part.getInputStream();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();

		byte[] buffer = new byte[8192];
		int count = 0;

		while ((count = in.read(buffer)) >= 0) {
			baos.write(buffer,0,count);
		}
		in.close();

		Object [] parts = new Object[] {
			baos.toByteArray(), part.getContentType()
		};
		
		return parts;
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

	public static Long getCurrentMessageId(HttpSession ses) {
		return (Long)ses.getAttribute(WebKeys.MAIL_MESSAGE);
	}

	public static Long getCurrentMessageId(PortletSession ses) {
		return (Long)ses.getAttribute(
			WebKeys.MAIL_MESSAGE, PortletSession.APPLICATION_SCOPE);
	}

	public static List getAllFolders(HttpSession ses)
		throws StoreException, FolderException {

		List list = new ArrayList();

		IMAPFolder root = null;

		try {
			root = (IMAPFolder)_getStore(ses).getDefaultFolder();

			Folder [] folders = root.list();

			_getFolders(list, folders);
		}
		catch (MessagingException ex) {
			throw new FolderException(
				"Error trying to access the default folder", ex);
		}
		finally {
			try {
				if (root != null && root.isOpen()) {
						root.close(false);
				}
			}
			catch (Exception ex) {
			}
		}

		return list;
	}

	private static void _getFolders(List list, Folder[] folders)
		throws MessagingException {
		
		for (int i = 0; i < folders.length; i++) {
			if ((folders[i].getType() & IMAPFolder.HOLDS_MESSAGES) != 0) {
				MailFolder mf = new MailFolder(
					folders[i].getMessageCount(), folders[i].getName(),
					folders[i].getNewMessageCount());

				list.add(mf);
			}
			
			if ((folders[i].getType() & IMAPFolder.HOLDS_FOLDERS) != 0) {
				Folder [] subfolders = folders[i].list();

				_getFolders(list, subfolders);
			}
		}
	}

	public static SortedSet getEnvelopes(HttpSession ses, Comparator comp)
		throws FolderException {

		SortedSet envelopes = new TreeSet(comp);

        try {
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
		} 
        catch (MessagingException ex) {
        	_log.error("Error getting envelops of current folder");
        	throw new FolderException(ex);
		}

        return envelopes;
	}

	public static MailMessage getMessage(
			HttpSession ses, String folderName, long messageUID)
		throws FolderException, StoreException, ContentException {

		setCurrentFolder(ses, folderName);
		return getMessage(ses, messageUID);
	}
	
	public static MailMessage getMessage(HttpSession ses, long messageUID)
		throws FolderException, StoreException, ContentException {

		MailMessage mm = null;

		try {
			String contentPath = RemoteMailAttachment.buildContentPath(
				_getCurrentFolder(ses).getName(), messageUID);

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
			mm = _getContent(message, mm, contentPath);

			_setCurrentMessage(ses, messageUID);
		}
		catch (MessagingException ex) {
			_log.error("Error trying to get message with UID " + messageUID);
			throw new FolderException(ex);
		}

		return mm;
	}

	public static void moveMessages(
			HttpSession ses, long [] messageUIDs, String toFolderName)
		throws FolderException, StoreException {

		IMAPFolder toFolder = null;
		try {
			if (!toFolderName.equals(MAIL_INBOX_NAME) && 
				!toFolderName.startsWith(MAIL_BOX_STYLE)) {
				toFolderName = MAIL_BOX_STYLE + toFolderName;
			}

			if (_getCurrentFolder(ses).getName().equals(toFolderName)) {
				return;
			}

			if ((_getCurrentFolder(ses).getName().equals(MAIL_DRAFTS_NAME) ||
				toFolderName.equals(MAIL_DRAFTS_NAME)) && 
					!toFolderName.equals(MAIL_TRASH_NAME)) {

				_log.error("Cannot move message to/from the '" + 
					MAIL_DRAFTS_NAME + "' folder");
				throw new FolderException();
			}
			
			toFolder = (IMAPFolder)_getStore(ses).getFolder(toFolderName);
			toFolder.open(IMAPFolder.READ_WRITE);

			Message [] msgs =
				_getCurrentFolder(ses).getMessagesByUID(messageUIDs);

			_getCurrentFolder(ses).copyMessages(msgs, toFolder);
			_getCurrentFolder(ses).setFlags(
				msgs, new Flags(Flags.Flag.DELETED), true);
			_getCurrentFolder(ses).expunge();
		}
		catch (MessagingException ex) {
			_log.error("Unable to move messages");

			throw new FolderException(ex);
		}
		finally {
			try {
				if (toFolder != null && toFolder.isOpen()) {
					toFolder.close(true);
				}
			}
			catch (Exception ex) {
			}
		}
	}

	public static void removeFolder(HttpSession ses, String folderName) 
		throws FolderException, StoreException {

		try {
			if (!folderName.equals(MAIL_INBOX_NAME) && 
				!folderName.startsWith(MAIL_BOX_STYLE)) {
				folderName = MAIL_BOX_STYLE + folderName;
			}

			for (int i = 0; i < DEFAULT_FOLDERS.length; i++) {
				if (DEFAULT_FOLDERS[i].equals(folderName)) {
					_log.error("The folder " + folderName + 
						" is a system-defined folder and cannot be changed");
					throw new FolderException();
				}
			}

			Store store = _getStore(ses);
			Folder folder = store.getFolder(folderName);
			if (!folder.exists()) {
				_log.error("The folder " + folderName + 
					" does not currently exist in the system");
				throw new FolderException();
			}

			folder.delete(true);
		}
		catch (MessagingException ex) {
			_log.error("Error trying to remove the folder: " + folderName);
			throw new FolderException(ex);
		}
	}

	public static void renameFolder(
			HttpSession ses, String oldFolderName, String newFolderName)
		throws FolderException, StoreException {

		try {
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
					_log.error("The folder " + oldFolderName + 
						" is a system-defined folder and cannot be changed");
					throw new FolderException();
				}
				else if (DEFAULT_FOLDERS[i].equals(newFolderName)) {
					_log.error("The folder " + newFolderName + 
						" is a system-defined folder and cannot be changed");
					throw new FolderException();
				}
			}
			
			Store store = _getStore(ses);
			Folder oldFolder = store.getFolder(oldFolderName);
			Folder newFolder = store.getFolder(newFolderName);
			if (!oldFolder.exists()) {
				_log.error("The folder " + oldFolderName + 
					" does not exist in the system");
				throw new FolderException();
			}
			else if (newFolder.exists()) {
				_log.error("The folder " + newFolderName + 
					" already exists in the system");
				throw new FolderException();
			}

			if (!oldFolder.isOpen()) {
				oldFolder.open(Folder.READ_WRITE);
			}

			oldFolder.renameTo(newFolder);
			
			if (_getCurrentFolder(ses).getName().equals(oldFolderName)) {
				setCurrentFolder(ses, newFolderName);
			}
		}
		catch (MessagingException ex) {
			_log.error("Error trying to rename folder from '" + oldFolderName + 
				"' to '" + newFolderName + "'");
			throw new FolderException(ex);
		}
	}

	public static void setCurrentFolder(HttpSession ses, String folderName)
		throws FolderException, StoreException {

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

	private static RemoteMailAttachment _getRemoteAttachment(
			Part part, String contentPath)
		throws ContentException {

		RemoteMailAttachment rma = new RemoteMailAttachment();

		try {
			rma.setFilename(part.getFileName());
			rma.setContentPath(contentPath);
		}
		catch (MessagingException ex) {
			_log.error("Unable to properly get file name of MIME attachment");
			throw new ContentException(ex);
		}

		return rma;
	}

	private static MailMessage _getContent(
			Part part, MailMessage mm, String contentPath)
		throws ContentException {

        try {
			String contentType = part.getContentType().toLowerCase();
			if (part.getContent() instanceof Multipart) {
			    Multipart mp = (Multipart)part.getContent();

			    if (contentType.startsWith(Constants.MULTIPART_ALTERNATIVE)) {
			        for (int i = 0; i < mp.getCount(); i++) {
			        	Part mpbp = mp.getBodyPart(i);

			        	String subcontent = mpbp.getContentType().toLowerCase();
			            if (subcontent.startsWith(Constants.TEXT_PLAIN)) {
			            	mm.setPlainBody((String)mpbp.getContent());
			            }
			            else if (subcontent.startsWith(Constants.TEXT_HTML)) {
			            	mm.setHtmlBody((String)mpbp.getContent());
			            }
			            else {
			            	mm = _getContent(
			            		mpbp, mm, contentPath + StringPool.PERIOD + i);
			            }
			        }
			    }
			    else {
			    	for (int i = 0; i < mp.getCount(); i++) {
			        	Part mpbp = mp.getBodyPart(i);
			        	mm = _getContent(
			        		mpbp, mm, contentPath + StringPool.PERIOD + i);
			    	}
			    }
			}
			else if (contentType.startsWith(Constants.TEXT_PLAIN)) {
				mm.appendPlainBody((String)part.getContent());
			}
			else if (contentType.startsWith(Constants.TEXT_HTML)) {
				mm.appendHtmlBody((String)part.getContent());
			}
			else if (contentType.startsWith(Constants.MESSAGE_RFC822)) {
				// TODO: nested
			}
			else {
			    mm.appendRemoteAttachment(_getRemoteAttachment(
			    	part, contentPath + StringPool.PERIOD + -1));
			}
		} 
        catch (MessagingException ex) {
			_log.error("Error extracting MIME content");
			throw new ContentException(ex);
		} 
		catch (IOException ex) {
			_log.error("Error extracting MIME content");
			throw new ContentException(ex);
		}

        return mm;
	}

	private static IMAPFolder _getCurrentFolder(IMAPFolder folder)
		throws FolderException {
		
		if (folder != null) {
			return folder;
		}
		else {
			_log.error("A folder must first be selected");
			throw new FolderException();
		}
	}

	private static IMAPFolder _getCurrentFolder(HttpSession ses)
		throws FolderException {
		
		IMAPFolder folder = (IMAPFolder)ses.getAttribute(WebKeys.MAIL_FOLDER);
		
		return _getCurrentFolder(folder);
	}

	private static IMAPFolder _getCurrentFolder(PortletSession ses)
		throws FolderException {

		IMAPFolder folder = (IMAPFolder)ses.getAttribute(
			WebKeys.MAIL_FOLDER, PortletSession.APPLICATION_SCOPE);

		return _getCurrentFolder(folder);
	}

	private static IMAPFolder _getFolder(HttpSession ses, String folderName)
		throws FolderException, StoreException {

		IMAPFolder folder = null;

		try {
			if (Validator.isNull(folderName)) {
				folderName = MAIL_INBOX_NAME;
			}
			else if (!folderName.equals(MAIL_INBOX_NAME) &&
				!folderName.startsWith(MAIL_BOX_STYLE)) {
	
				folderName = MAIL_BOX_STYLE + folderName;
			}
	
			folder = (IMAPFolder)ses.getAttribute(WebKeys.MAIL_FOLDER);
	
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
		}
		catch (MessagingException ex) {
			_log.error("Error trying to access folder: " + folderName);
			throw new FolderException(ex);
		}

		return folder;
	}

	private static Store _getStore(HttpSession ses)
		throws FolderException, StoreException {

		Store store = (Store)ses.getAttribute(WebKeys.MAIL_STORE);
		String userId = (String)ses.getAttribute(WebKeys.USER_ID);
		String password = (String)ses.getAttribute(WebKeys.USER_PASSWORD);

		try {
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
		}
		catch (NamingException ex) {
			_log.error("Error in accessing IMAP store");
			throw new StoreException(ex);
		}
		catch (MessagingException ex) {
			_log.error("Error initializing store");
			throw new StoreException(ex);
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